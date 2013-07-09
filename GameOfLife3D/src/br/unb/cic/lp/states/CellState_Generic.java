package br.unb.cic.lp.states;

public class CellState_Generic extends CellState_Alive{
	public CellState_Generic(String CellSimbol, String CellStateName, String CellImageName, String Cell3DImageName, String CellStateColorName){
		this.CellText = "|  " + CellSimbol + "  |";
		this.CellStateName = CellStateName;
		this.CellImageName = CellImageName;
		this.Cell3DImageName = Cell3DImageName;
		this.CellStateColorName = CellStateColorName;
	}
	public String getName(){
		return CellStateName + " " + CellText ;
	}
}
