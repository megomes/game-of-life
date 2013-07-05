package br.unb.cic.lp.states;

public abstract class CellState {
	protected String CELULA_text;
	protected String CellStateName;
	
	public String getCellText(){
		return CELULA_text;
	}
	public String getCellStateName(){
		return CellStateName;
	}
	public abstract String getName();
}
