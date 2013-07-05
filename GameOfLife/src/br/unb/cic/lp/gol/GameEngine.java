package br.unb.cic.lp.gol;

import java.security.InvalidParameterException;
import java.util.HashMap;

import br.unb.cic.lp.rules.GameRule;
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
public class GameEngine implements CellListener {
	private int height;
	private int width;
	private Cell[][] cells;
	/**
	 * Nota: [Falha 1] A classe Statistics foi mesclada em parte com essa classe.
	 * O controle da estatística agora é parte responsável do Model
	 */
	private int revivedCells;
	private int killedCells;
	/* Implementação do TemplateMethod/Strategy */
	private GameRule gameRule;

	/**
	 * Construtor da classe Environment.
	 * 
	 * @param height
	 *            dimensao vertical do ambiente
	 * @param width
	 *            dimensao horizontal do ambiente
	 */
	public GameEngine(int height, int width, GameRule gameRule) {
		this.height = height;
		this.width = width;
		
		this.gameRule = gameRule;

		cells = new Cell[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = new Cell();
				cells[i][j].addCellListener(this);
			}
		}
		
		revivedCells = 0;
		killedCells = 0;
	}

	/**
	 * Calcula uma nova geracao do ambiente. Essa implementacao utiliza o
	 * algoritmo do Conway, ou seja:
	 * 
	 * a) uma celula morta com exatamente tres celulas vizinhas vivas se torna
	 * uma celula viva.
	 * 
	 * b) uma celula viva com duas ou tres celulas vizinhas vivas permanece
	 * viva.
	 * 
	 * c) em todos os outros casos a celula morre ou continua morta.
	 */
	public void nextGeneration() {
		HashMap<Cell, CellState> mustChange = new HashMap<Cell, CellState>();
		CellState state;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				state = gameRule.shouldChange(numberOfNeighborhoodAliveCells(i, j), cells[i][j].getState());
				if(!(state == cells[i][j].getState())){
					mustChange.put(cells[i][j], state);
				}
			}
		}
		
		for (Cell cell : mustChange.keySet()){
			cell.setState(mustChange.get(cell));
			//TODO: Descobrir se foi morta e revivida para as estatísticas
		}
		
	}

	/*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 * 
	 * ALTERAÇÃO: Retorna um HashMap com os estados existentes e sua respectiva contagem
	 */
	private HashMap<CellState, Integer> numberOfNeighborhoodAliveCells(int i, int j) {
		HashMap<CellState, Integer> dictStates = new HashMap<CellState, Integer>();
		boolean passou;
		for (int a = i - 1; a <= i + 1; a++) {
			for (int b = j - 1; b <= j + 1; b++) {
				if (validPosition(a, b)  && (!(a==i && b == j))) {
					CellState state = cells[a][b].getState();
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
		return dictStates;
	}
	
	/*
	 * Altera o estado de uma célula.
	 */
	public void changeCell(int i, int j, CellState state){
		if(i < width && j < height){
			cells[i][j].setState(state);
		}else{
			throw new InvalidParameterException("Invalid position (" + i + ", " + j + ")");
		}
	}
	
	/*
	 * Retorna o estado da celula (i,j)
	 */
	CellState getCellState(int i, int j){
		return cells[i][j].getState();
	}

	/*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
	private boolean validPosition(int a, int b) {
		return a >= 0 && a < height && b >= 0 && b < width;
	}
	
	/* Métodos de CellListener e implementação dos Métodos de Estatística */
	
	public void cellRevived(CellEvent e){
		this.revivedCells++;
	}
	public void cellKilled(CellEvent e){
		this.killedCells++;
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
	
	public int getRevivedCells() {
		return revivedCells;
	}
	
	public int getKilledCells() {
		return killedCells;
	}
}
