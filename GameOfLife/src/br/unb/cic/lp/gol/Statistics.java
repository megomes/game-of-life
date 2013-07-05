package br.unb.cic.lp.gol;

import br.unb.cic.lp.states.*;

public class Statistics implements CellListener{
	private int revivedCells;
	private int killedCells;
	private int influencedCells;
	
	public Statistics(){
		revivedCells = 0;
		killedCells = 0;
		influencedCells = 0;
	}

	public void cellChanged(CellEvent e, CellState currentState, CellState oldState) {
		if(currentState.getCellStateName() == "dead"){
			killedCells ++;
		}else{
			if(oldState.getCellStateName() == "dead"){
				revivedCells ++;
			}else{
				influencedCells++;
			}
		}
	}

	public int getRevivedCells() {
		return revivedCells;
	}

	public int getKilledCells() {
		return killedCells;
	}

	public int getInfluencedCells() {
		return influencedCells;
	}


}
