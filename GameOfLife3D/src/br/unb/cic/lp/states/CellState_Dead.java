package br.unb.cic.lp.states;
/**
 * Implementação da uma célula morta.
 *
 */
public class CellState_Dead extends CellState{
	public CellState_Dead(){
		CellText = "|     |";
		CellStateName = "dead";
		CellImageName = "cell_dead.png";
		Cell3DImageName = null;
	}
	public String getName(){
		return "Dead Cell";
	}
}
