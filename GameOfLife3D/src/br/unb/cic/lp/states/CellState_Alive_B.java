package br.unb.cic.lp.states;

public class CellState_Alive_B  extends CellState{
	public CellState_Alive_B(){
		CellText = "|  x  |";
		CellStateName = "alive_b";
		CellImageName = "cell_alive_b.png";
		Cell3DImageName = "cell_3d_alive_b.png";
		CellStateColorName = "Verde";
	}
	public String getName(){
		return "Alive Cell B [x]";
	}
}