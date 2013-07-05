package br.unb.cic.lp.gol;

import java.util.EventObject;
/**
 * Observer para eventos da CŽlula
 * 
 * @author Matheus Ervilha
 *
 */
public class CellEvent extends EventObject{
	/**
	 * Quem receber o evento, vai saber a origem via getSource();
	 * 
	 * @param source origem do evento
	 */
	public CellEvent (Cell source){
		super(source);
	}

}
