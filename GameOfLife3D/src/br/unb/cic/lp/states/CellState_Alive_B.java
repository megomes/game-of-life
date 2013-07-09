package br.unb.cic.lp.states;
/**
 * Implementação da uma célula viva.
 *
 */
public class CellState_Alive_B  extends CellState_Alive{
	public CellState_Alive_B(){
		CellText = "|  x  |";
		CellStateName = "alive_b";
		CellImageName = "cell_alive_b.png";
		Cell3DImageName = "cell_3_alive_b.png";
		CellStateColorName = "Green";
	}
	public String getName(){
		return CellStateName + " " + CellText ;
	}
}