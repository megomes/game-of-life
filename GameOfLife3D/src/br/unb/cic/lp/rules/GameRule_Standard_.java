package br.unb.cic.lp.rules;

import br.unb.cic.lp.states.*;

public class GameRule_Standard_ extends GameRule_Generic {
	public GameRule_Standard_ (){
		addCellState_Alive(new CellState_Alive_A());

		addKeepAliveCase(2);
		addKeepAliveCase(3);
		
		addReviveCase(3);
	}
}
