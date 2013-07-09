package br.unb.cic.lp.gol;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.*;

public class Main {

	public static void main(String args[]) {
		
		/* Game Rule */
		//GameRule gameRule = new GameRule_Standard(); //Jogo com as regras Padr›es
		//GameRule gameRule = new GameRule_HighLife(); //Jogo com as regras do HighLife
		//GameRule gameRule = new GameRule_ImigrationGame();
		
		GameRule_Generic gameRule = new GameRule_Generic();
		gameRule.addCellState_Alive(new CellState_Alive_A());
		gameRule.addCellState_Alive(new CellState_Alive_B());

		gameRule.addKeepAliveCase(2);
		gameRule.addKeepAliveCase(3);
		
		gameRule.addReviveCase(3);
		
		CellState_Alive CellState_Alive_C = new CellState_Generic("c", "alive_c", "cell_alive_c.png", "cell_3d_alive_c.png", "Red");
		gameRule.addCellState_Alive(CellState_Alive_C);
		
		/* Game */
		Game game = new Game(10, 10, 10, gameRule);
		
		/* Start Game */
		game.start();
	}
}
