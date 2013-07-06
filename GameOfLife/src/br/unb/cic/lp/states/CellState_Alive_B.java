package br.unb.cic.lp.states;

public class CellState_Alive_B  extends CellState{
	public CellState_Alive_B(){
		CellText = "|  x  |";
		CellStateName = "alive_b";
		CellImageName = "X";
	}
	public String getName(){
		return "Alive Cell B [x]";
	}
}