package br.unb.cic.lp.savedStates;

import br.unb.cic.lp.states.*;

public class Memento {
	private CellState cellState;
	
	public Memento (CellState cellState){
		this.cellState = cellState;
	}
	
	public CellState getSavedState(){
		return cellState;
	}
}
