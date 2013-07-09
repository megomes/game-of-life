package br.unb.cic.lp.savedStates;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.lp.gol.Cell;
/**
 * Classe controle da arquitetura Memento.
 * Controla os estados dispon’veis para serem recuperados.
 *
 */
public class Caretaker {
	private List<Memento[][][]> savedStates;
	private int width, height, depth;
	
	/**
	 * Construtor da classe
	 * @param width largura do tabuleiro
	 * @param height altura do tabuleiro
	 * @param depth profundidade do tabuleiro
	 */
	public Caretaker(int width, int height, int depth){
		savedStates = new ArrayList<Memento[][][]>();
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	/* Salva um estado */
	public void saveState(Cell[][][] cells){
		if (savedStates.size() == 6){
			savedStates.remove(0);
		}
		Memento[][][] memento = new Memento[depth][height][width];
		for (int k = 0; k < depth; k++){
			for (int i = 0; i < height; i++){
				for (int j = 0; j < width; j++){
					memento[k][i][j] = new Memento(cells[k][i][j].getState());
				}
			}
		}
		savedStates.add(memento);
	}
	
	/* Recupera um estado */
	public Cell[][][] restoreState(Cell[][][] cells){
		Memento[][][] memento = savedStates.get(savedStates.size() - 2);
		savedStates.remove(savedStates.size() - 1);
		for (int k = 0; k < depth; k++){
			for (int i = 0; i < height; i++){
				for (int j = 0; j < width; j++){
					cells[k][i][j].setState(memento[k][i][j].getSavedState());
				}
			}
		}
		return cells;
	}
	
	/* Indica se Ž poss’vel restaurar um estado */
	public boolean canRestoreState(){
		return savedStates.size() > 1;
	}
}
