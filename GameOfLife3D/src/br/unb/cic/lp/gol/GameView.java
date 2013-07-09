package br.unb.cic.lp.gol;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.*;

/**
 * Atua como um componente de apresentacao (view), exibindo o estado atual do
 * game com uma implementacao baseada em caracteres ASCII.
 * 
 * @author rbonifacio
 */
public class GameView {
	private static final String LINE = "+-----+";
	
	private static final int INVALID_OPTION = 0;
	private static final int MAKE_CELL_ALIVE = 1;
	private static final int NEXT_GENERATION = 2;
	private static final int HALT = 3; 
	private static final int UNDO = 4;

	private GameEngine engine;
	private GameController controller;
	private GameRule rule;

	/**
	 * Construtor da classe GameBoard
	 */
	public GameView(GameController controller, GameEngine engine, GameRule rule) {
		this.controller = controller;
		this.engine = engine;
		this.rule = rule;
	}

	/**
	 * Atualiza o componente view (representado pela classe GameBoard),
	 * possivelmente como uma resposta a uma atualização do jogo.
	 */
	public void update(boolean printOptions) {
		for (int k = 0; k < engine.getDepth(); k++){
			printFirstRow();
			printLine();
			for (int i = 0; i < engine.getHeight(); i++) {
				for (int j = 0; j < engine.getWidth(); j++) {
					System.out.print(engine.getCellState(i, j, k).getCellText());
				}
				System.out.println("   " + i);
				printLine();
			}
		}

		if (printOptions) printOptions();
	}

	private void printOptions() {
		Scanner s = new Scanner(System.in);
		int option;
		System.out.println("\n \n");
		
		do {
			System.out.println("Select one of the options: \n \n"); 
			System.out.println("[1] Make a cell alive");
			System.out.println("[2] Next generation");
			System.out.println("[3] Halt");
			if(engine.canRestoreState()) System.out.println("[4] Undo (ctrl+z)");
		
			System.out.print("\n \n Option: ");
			
			option = parseOption(s.nextLine());
		}while(option == 0);
		
		switch(option) {
			case MAKE_CELL_ALIVE : makeCellAlive(); break;
			case NEXT_GENERATION : nextGeneration(); break;
			case HALT : halt();
			case UNDO : undo();
		}
	}
	private void undo(){
		controller.undo();
	}
	private void makeCellAlive() {
		int i, j = 0, k = 0, option = 0, contador = 0;
		CellState state;
		Scanner s = new Scanner(System.in);
		
		do {
			System.out.print("\n Inform the row number (0 - " + (engine.getHeight() - 1) + "): " );
			
			i = s.nextInt();
			
			System.out.print("\n Inform the column number (0 - " + (engine.getWidth() - 1) + "): " );
			
			j = s.nextInt();
			
			if (engine.getDepth() > 1){
				System.out.print("\n Inform the depth number (0 - " + (engine.getDepth() - 1) + "): " );
				
				k = s.nextInt();
			}else{
				k = 0;
			}
		}while(!validPosition(i,j,k));
		// Se existir mais de um modelo de célula viva
		List<CellState_Alive> options = rule.getOptions();
		if(options.size() > 0){
			do {
				contador = 0;
				System.out.print("\n Inform the cell state {");
				for (CellState cellState : options){
					contador++;
					System.out.print("\n\t[" + contador + "] - " + cellState.getName());
				}
				System.out.print("\n} (1 - " + contador + "): ");
				option = s.nextInt();
			}while(option <= 0 || option > contador);
			state = options.get(option - 1);
		}else{
			state = options.get(0);
		}
		
		controller.changeCell(i, j, k, state, true);
	}
	
	private void nextGeneration() {
		controller.nextGeneration();
	}
	
	private void halt() {
		System.out.println("\n \n");
		displayStatistics();
		controller.halt();
	}
	
	private boolean validPosition(int i, int j, int k) {
		return engine.validPosition(i, j, k);
	}

	private int parseOption(String option) {
		if(option.equals("1")) {
			return MAKE_CELL_ALIVE;
		}
		else if (option.equals("2")) {
			return NEXT_GENERATION;
		}
		else if (option.equals("3")) {
			return HALT;
		}
		else if (option.equals("4") && engine.canRestoreState()) {
			return UNDO;
		}
		else return INVALID_OPTION;
	}
	
	/* Imprime as estatísticas finais da aplicação */
	public void displayStatistics() {
		System.out.println("=================================");
		System.out.println("           Statistics            ");
		System.out.println("=================================");
		System.out.println("Revived cells: " + engine.getRevivedCells());
		System.out.println("Killed cells: " + engine.getKilledCells());
		if(engine.getInfluencedCells() != 0) System.out.println("Influenced cells: " + engine.getInfluencedCells());
		System.out.println("=================================");
	}

	/* Imprime uma linha usada como separador das linhas do tabuleiro */
	private void printLine() {
		for (int j = 0; j < engine.getWidth(); j++) {
			System.out.print(LINE);
		}
		System.out.print("\n");
	}

	/*
	 * Imprime os identificadores das colunas na primeira linha do tabuleiro
	 */
	private void printFirstRow() {
		System.out.println("\n \n");
		for (int j = 0; j < engine.getWidth(); j++) {
			System.out.print("   " + j + "   ");
		}
		System.out.print("\n");
	}
}
