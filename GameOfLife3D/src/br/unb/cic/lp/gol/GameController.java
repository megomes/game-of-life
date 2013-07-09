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
	private GameWindow window = null;

	public void setGameWindow(GameWindow window){
		this.window = window;
	}
	
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
		reloadViews();
	}
	
	public void halt() {
		System.exit(0);
	}
	public void undo(){
		engine.restoreState();
		reloadViews();
	}
	public void changeCell(int i, int j, int k, CellState state, boolean reload) {
		try {
			engine.changeCell(i, j, k, state);
			if (reload) reloadViews();
		}
		catch(InvalidParameterException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void nextGeneration() {
		engine.nextGeneration();
		reloadViews();
	}
	
	public void reloadViews(){
		if (window != null){
			window.reloadScreen();
			board.update(false);
		}else{
			board.update(true);
		}
	}
	
}
