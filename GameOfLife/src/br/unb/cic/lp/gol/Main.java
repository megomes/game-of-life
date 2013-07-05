package br.unb.cic.lp.gol;

public class Main {

	public static void main(String args[]) {
		GameController controller = new GameController();
				
		GameEngine engine = new GameEngine(10, 10);	
		
		GameView board = new GameView(controller, engine);
		
		controller.setBoard(board);
		controller.setEngine(engine);
		
		controller.start();
	}
}
