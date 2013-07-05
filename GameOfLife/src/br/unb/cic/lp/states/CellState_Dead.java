package br.unb.cic.lp.states;

public class CellState_Dead extends CellState{
	public CellState_Dead(){
		CELULA_text = "|     |";
		CellStateName = "dead";
	}
	public String getName(){
		return "Dead Cell";
	}
}
