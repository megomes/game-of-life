package br.unb.cic.lp.gol;

import br.unb.cic.lp.states.*;
/**
 * Classe de Estatística do jogo. 
 * Calcula quantas células foram mortas, quantas foram revividas e quantas foram influênciadas
 * pelos seus vizinhos
 *
 */
public class Statistics implements CellListener{
	private int revivedCells;
	private int killedCells;
	private int influencedCells;
	
	/**
	 * Construtor da classe
	 */
	public Statistics(){
		revivedCells = 0;
		killedCells = 0;
		influencedCells = 0;
	}

	/**
	 * Aumenta o contador.
	 */
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
