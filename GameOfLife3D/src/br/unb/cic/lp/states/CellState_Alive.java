package br.unb.cic.lp.states;

public class CellState_Alive extends CellState{
	public CellState_Alive(){
		CellText = "|  o  |";
		CellStateName = "alive_a";
		CellImageName = "cell_alive_a.png";
		Cell3DImageName = "cell_3d_alive_a.png";
		CellStateColorName = "Azul";
	}
	public String getName(){
		return "Alive Cell A [o]";
	}
}
