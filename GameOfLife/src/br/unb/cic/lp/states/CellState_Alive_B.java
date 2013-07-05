package br.unb.cic.lp.states;

public class CellState_Alive_B  extends CellState{
	public CellState_Alive_B(){
		CELULA_text = "|  x  |";
		CellStateName = "alive_b";
	}
	public String getName(){
		return "Alive Cell B [x]";
	}
}