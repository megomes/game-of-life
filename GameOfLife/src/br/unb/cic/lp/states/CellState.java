package br.unb.cic.lp.states;

public abstract class CellState {
	protected String CellText;
	protected String CellStateName;
	protected String CellImageName;
	
	public String getCellText(){
		return CellText;
	}
	public String getCellImageName(){
		return CellImageName;
	}
	public String getCellStateName(){
		return CellStateName;
	}
	public abstract String getName();
}
