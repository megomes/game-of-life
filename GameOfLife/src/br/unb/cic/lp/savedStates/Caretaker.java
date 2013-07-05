package br.unb.cic.lp.savedStates;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.lp.gol.Cell;

public class Caretaker {
	private List<Memento[][]> savedStates;
	private int width, height;
	public Caretaker(int width, int height){
		savedStates = new ArrayList<Memento[][]>();
		this.width = width;
		this.height = height;
	}
	
	public void saveState(Cell[][] cells){
		if (savedStates.size() == 6){
			savedStates.remove(0);
		}
		Memento[][] memento = new Memento[height][width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				memento[i][j] = new Memento(cells[i][j].getState());
			}
		}
		savedStates.add(memento);
	}
	
	public Cell[][] restoreState(Cell[][] cells){
		Memento[][] memento = savedStates.get(savedStates.size() - 2);
		savedStates.remove(savedStates.size() - 1);
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				cells[i][j].setState(memento[i][j].getSavedState());
			}
		}
		return cells;
	}
	
	public boolean canRestoreState(){
		return savedStates.size() > 1;
	}
}
