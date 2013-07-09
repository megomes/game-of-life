package br.unb.cic.lp.rules;

import br.unb.cic.lp.states.*;

public class GameRule_ImmigrationGame_ extends GameRule_Generic {
	public GameRule_ImmigrationGame_ (){
		addCellState_Alive(new CellState_Alive_A());
		addCellState_Alive(new CellState_Alive_B());

		addKeepAliveCase(2);
		addKeepAliveCase(3);
		
		addReviveCase(3);
	}
}
