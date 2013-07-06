package br.unb.cic.lp.states;

public class CellState_Dead extends CellState{
	public CellState_Dead(){
		CellText = "|     |";
		CellStateName = "dead";
		CellImageName = " ";
	}
	public String getName(){
		return "Dead Cell";
	}
}
