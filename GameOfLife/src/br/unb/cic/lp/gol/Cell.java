package br.unb.cic.lp.gol;

import java.util.ArrayList;
import java.util.Collection;

public class Cell {
	private boolean alive = false;
	private Collection<CellListener> cellListeners = new ArrayList<CellListener>();

	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		this.alive = false;
		
		CellEvent evento = new CellEvent(this);
		for (CellListener listener : cellListeners){
			listener.cellKilled(evento);
		}
	}
	
	public void revive() {
		this.alive = true;
		
		CellEvent evento = new CellEvent(this);
		for (CellListener listener : cellListeners){
			listener.cellRevived(evento);
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
