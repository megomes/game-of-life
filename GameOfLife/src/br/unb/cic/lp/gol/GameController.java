package br.unb.cic.lp.gol;

import java.security.InvalidParameterException;

import br.unb.cic.lp.states.*;

/**
 * Classe que atua como um controlador do 
 * padr‹o MVC, separando os componentes da 
 * camada de apresentacao e model. 
 * 
 * @author rbonifacio
 */
public class GameController {

	private GameEngine engine;
	private GameView board;
	
	public GameEngine getEngine() {
		return engine;
	}
	
	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}
	
	public GameView getBoard() {
		return board;
	}
	
	public void setBoard(GameView board) {
		this.board = board;
	}
	
	public void start() {
		board.update();
	}
	
	public void halt() {
		System.exit(0);
	}
	
	public void changeCell(int i, int j, CellState state) {
		try {
			engine.changeCell(i, j, state);
			board.update();
		}
		catch(InvalidParameterException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void nextGeneration() {
		engine.nextGeneration();
		board.update();
	}
	
}
