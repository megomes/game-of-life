package br.unb.cic.lp.gol;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.CellState_Alive_A;

/**
 * Classe Game implementa o Padr‹o Faade, tornando a cria‹o
 * de um Game mais f‡cil de ser utilizada.
 * 
 * @author Matheus Ervilha
 * 
 */
public class Game {
	private GameController controller;
	
	/**
	 * Construtor do Game
	 * @param height Altura do tabuleiro
	 * @param width Largura do tabuleiro
	 * 
	 * TODO: Colocar a regra do jogo como par‰metro tbm.
	 */
	public Game(int height, int width, int depth, GameRule gameRule){
		controller = new GameController();
		
		GameEngine engine = new GameEngine(height, width, depth, gameRule);	
		
		GameView board = new GameView(controller, engine, gameRule);
		
		controller.setBoard(board);
		controller.setEngine(engine);
		
		GameWindow window;
		if (depth > 1){
			window = new GameViewWindow3D(600, 600, engine, controller, gameRule);
		}else{
			window = new GameViewWindow(600, 600, engine, controller, gameRule);	
		}
		
		controller.setGameWindow(window);
	}
	public Game(int height, int width, GameRule gameRule){
		this(height, width, 1, gameRule);
	}
	
	/* Inicia todo o processo do jogo */
	public void start(){
		controller.start();
	}

}
