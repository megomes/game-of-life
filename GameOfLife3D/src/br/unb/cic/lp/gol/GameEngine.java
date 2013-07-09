package br.unb.cic.lp.gol;

import java.security.InvalidParameterException;
import java.util.HashMap;

import br.unb.cic.lp.rules.GameRule;
import br.unb.cic.lp.savedStates.Caretaker;
import br.unb.cic.lp.states.*;

/**
 * Representa um ambiente (environment) do jogo GameOfLife.
 * 
 * Essa implementacao eh nao inifinita, ou seja, nem todas as celulas possuem
 * oito celulas vizinhas. Por exemplo, a celula de coordenada (0,0) possui
 * apenas tres celulas vizinhas, (0,1), (1,0) e (1,1).
 * 
 * Um ambiente eh representado como um array bidimensional de celulas, com
 * altura (height) e comprimento (width).
 * 
 * @author rbonifacio
 */
public class GameEngine{
	private int height;
	private int width;
	private int depth;
	private Cell[][][] cells;
	private Statistics statistics;
	
	/* Implementação do TemplateMethod/Strategy */
	private GameRule gameRule;
	private Caretaker caretaker;

	/**
	 * Construtor da classe Environment.
	 * 
	 * @param height
	 *            dimensao vertical do ambiente
	 * @param width
	 *            dimensao horizontal do ambiente
	 */
	public GameEngine(int height, int width, int depth, GameRule gameRule) {
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.gameRule = gameRule;
		statistics = new Statistics();
		caretaker = new Caretaker(width, height, depth);

		cells = new Cell[depth][height][width];

		for(int k = 0; k < depth; k++){
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					cells[k][i][j] = new Cell();
					cells[k][i][j].addCellListener(statistics);
				}
			}
		}
		
		caretaker.saveState(cells);
	}

	/**
	 * Calcula uma nova geracao do ambiente. Baseado na regra de negócio implementada em GameRule.
	 */
	public void nextGeneration() {
		HashMap<Cell, CellState> mustChange = new HashMap<Cell, CellState>();
		CellState state;
		for(int k = 0; k < depth; k++){
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					state = gameRule.shouldChange(numberOfNeighborhoodAliveCells(i, j, k), cells[k][i][j].getState(), depth);
					if(!(state == cells[k][i][j].getState())){
						mustChange.put(cells[k][i][j], state);
					}
				}
			}
		}

		
		for (Cell cell : mustChange.keySet()){
			cell.setState(mustChange.get(cell));
		}
		caretaker.saveState(cells);
	}

	/*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 * 
	 * ALTERAÇÃO: Retorna um HashMap com os estados existentes e sua respectiva contagem
	 */
	private HashMap<CellState, Integer> numberOfNeighborhoodAliveCells(int i, int j, int k) {
		HashMap<CellState, Integer> dictStates = new HashMap<CellState, Integer>();
		boolean passou;
		for (int c = k -1; c <= k + 1; c++){
			for (int a = i - 1; a <= i + 1; a++) {
				for (int b = j - 1; b <= j + 1; b++) {
					if (validPosition(a, b, c)  && (!(a==i && b==j && c==k))) {
						CellState state = cells[c][a][b].getState();
						if (dictStates.keySet().size() == 0){
							dictStates.put(state, 1);
						}else{
							passou = false;
							for(CellState cellState : dictStates.keySet()){
								if(cellState.getCellStateName() == state.getCellStateName()){
									dictStates.put(cellState, dictStates.get(cellState) + 1);
									passou = true;
									break;
								}
							}
							if(!passou){
								dictStates.put(state, 1);
							}
						}
					}
				}	
			}
		}
		
		return dictStates;
	}
	
	/*
	 * Altera o estado de uma célula.
	 */
	public void changeCell(int i, int j, int k, CellState state){
		if(i < width && j < height && k < depth){
			cells[k][i][j].setState(state);
			caretaker.saveState(cells);
		}else{
			throw new InvalidParameterException("Invalid position (" + i + ", " + j + ")");
		}
	}
	
	/*
	 * Retorna o estado da celula (i,j)
	 */
	public CellState getCellState(int i, int j, int k){
		return cells[k][i][j].getState();
	}
	
	/*
	 * Funções para UNDO
	 */
	public void restoreState(){
		cells = caretaker.restoreState(cells);
	}
	public boolean canRestoreState(){
		return caretaker.canRestoreState();
	}
	
	/*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
	public boolean validPosition(int a, int b, int c) {
		return a >= 0 && a < height && b >= 0 && b < width && c >= 0 && c < depth;
	}

	/* Metodos de acesso as propriedades height e width */
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	/* Método de Acesso as Estatísticas */
	public int getRevivedCells() {
		return statistics.getRevivedCells();
	}
	
	public int getKilledCells() {
		return statistics.getKilledCells();
	}
	
	public int getInfluencedCells(){
		return statistics.getInfluencedCells();
	}
}
