package br.unb.cic.lp.states;
/**
 * Classe abstrata do estado de uma célula.
 *
 */
public abstract class CellState {
	protected String CellText;
	protected String CellStateName;
	protected String CellImageName;
	protected String Cell3DImageName;
	protected String CellStateColorName;
	
	public String getCellText(){
		return CellText;
	}
	public String getCellImageName(){
		return CellImageName;
	}
	public String getCellStateName(){
		return CellStateName;
	}
	public String getCell3DImageName(){
		return Cell3DImageName;
	}
	public String getCellStateColorName(){
		return CellStateColorName;
	}
	public abstract String getName();
}
