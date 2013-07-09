package br.unb.cic.lp.rules;

import br.unb.cic.lp.states.*;

public class GameRule_HighLife_ extends GameRule_Generic {
	public GameRule_HighLife_ (){
		addCellState_Alive(new CellState_Alive_A());

		addKeepAliveCase(2);
		addKeepAliveCase(3);
		
		addReviveCase(3);
		addReviveCase(6);
	}
}
