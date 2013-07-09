package br.unb.cic.lp.gol;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.*;
/**
 * Main da aplicação.
 *
 */
public class Main {

	public static void main(String args[]) {
		
		/* Game Rule */
		GameRule gameRule = new GameRule_Standard_(); //Jogo com as regras Padrões
		//GameRule gameRule = new GameRule_HighLife_(); //Jogo com as regras do HighLife
		//GameRule gameRule = new GameRule_ImigrationGame_(); //Jogo com as regras do ImmigrationGame
		
		/*
		GameRule_Generic gameRule = new GameRule_Generic();
		gameRule.addCellState_Alive(new CellState_Alive_A());
		//gameRule.addCellState_Alive(new CellState_Alive_B());

		gameRule.addKeepAliveCase(2);
		gameRule.addKeepAliveCase(3);
		
		gameRule.addReviveCase(3);
		
		CellState_Alive CellState_Alive_C = new CellState_Generic("c", "alive_c", "cell_alive_c.png", "cell_3_alive_c.png", "Red");
		//gameRule.addCellState_Alive(CellState_Alive_C);
		*/
		/* Game */
		Game game = new Game(10, 10, gameRule, true); 
		
		/* Start Game */
		game.start();
	}
}
