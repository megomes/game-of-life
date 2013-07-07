package br.unb.cic.lp.gol;

import java.util.ArrayList;
import java.util.Collection;

import br.unb.cic.lp.states.*;

public class Cell {
	private Collection<CellListener> cellListeners = new ArrayList<CellListener>();
	private CellState actualState;

	public Cell(){
		actualState = new CellState_Dead();
	}
	public CellState getState() {
		return actualState;
	}
	public void setState(CellState state) {
		if(state.getCellStateName() != actualState.getCellStateName()){
			CellState oldState = actualState;
			actualState = state;
			
			CellEvent evento = new CellEvent(this);
			for (CellListener listener : cellListeners){
				listener.cellChanged(evento, actualState, oldState);
			}
		}
	}
	
	/* MŽtodos de CellListener */
	public void addCellListener(CellListener c){
		cellListeners.add(c);
	}
	
	public void removeCellListener(CellListener c){
		cellListeners.remove(c);
	}
}
