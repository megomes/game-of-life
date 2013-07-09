package br.unb.cic.lp.states;
/**
 * Implementação da uma célula viva.
 *
 */
public class CellState_Alive_A extends CellState_Alive{
	public CellState_Alive_A(){
		CellText = "|  o  |";
		CellStateName = "alive_a";
		CellImageName = "cell_alive_a.png";
		Cell3DImageName = "cell_3d_alive_a.png";
		CellStateColorName = "Blue";
	}
	public String getName(){
		return "Alive Cell A [o]";
	}
}
