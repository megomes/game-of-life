package br.unb.cic.lp.gol;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.CellState_Alive_A;

/**
 * Classe Game implementa o Padrão Façade, tornando a criação
 * de um Game mais fácil de ser utilizada.
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
	 * @param depth Profundidade do tabuleiro (3D)
	 * @param gameRule Regra do jogo
	 * 
	 */
	public Game(int height, int width, int depth, GameRule gameRule, boolean showWindow){
		controller = new GameController();
		
		GameEngine engine = new GameEngine(height, width, depth, gameRule);	
		
		GameView board = new GameView(controller, engine, gameRule);
		
		controller.setBoard(board);
		controller.setEngine(engine);
		if(showWindow){
			GameWindow window;
			if (depth > 1){
				window = new GameViewWindow3D(600, 600, engine, controller, gameRule);
			}else{
				window = new GameViewWindow(600, 600, engine, controller, gameRule);	
			}
			controller.setGameWindow(window);

		}

		
	}
	/* Criação secundária. De um game 2D */
	public Game(int height, int width, GameRule gameRule, boolean showWindow){
		this(height, width, 1, gameRule, showWindow);
	}
	
	/* Inicia todo o processo do jogo */
	public void start(){
		controller.start();
	}

}
