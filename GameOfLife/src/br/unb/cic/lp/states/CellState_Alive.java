package br.unb.cic.lp.states;

public class CellState_Alive extends CellState{
	public CellState_Alive(){
		CELULA_text = "|  o  |";
		CellStateName = "alive_a";
	}
	public String getName(){
		return "Alive Cell A [o]";
	}
}
