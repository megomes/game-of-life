package br.unb.cic.lp.gol;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unb.cic.lp.rules.GameRule;
import br.unb.cic.lp.states.*;

/**
 * Essa classe atua como o controle da View na Janela Java Swing
 * Caso a grade seja 3D, essa janela será criada
 * 
 */
@SuppressWarnings("serial")
public class GameViewWindow3D extends GameWindow implements ActionListener{
	private static final int BORDER = 20;
	private int width;
	private int height;
	private int actualX, actualY, actualZ;

	private GameEngine engine;
	private GameController controller;
	private GameRule gameRule;
	
	private List<CellState_Alive> options;
	
	private JPanel panel;
	
	/* Botões */
	private JButton btnUndo;
	private MButton[][] buttons;
	
	/* Labels */
	private JLabel lblx, lbly, lblz;
	
	/**
	 * construtor da janela
	 * @param width largura a janela em px
	 * @param height altura da janela em px
	 * @param gameEngine gameEngine utilizada
	 * @param gameController gameController utilizado
	 * @param gameRule gameRule utilizado
	 */
	public GameViewWindow3D(int width, int height, GameEngine gameEngine, GameController gameController, GameRule gameRule){
		this.height = height;
		this.width = width;
		this.engine = gameEngine;
		this.controller = gameController;
		this.gameRule = gameRule;
		configWindow();
	}
	
	/**
	 * Configuração inicial da janela
	 * Cria panel, guarda as opções de Estados existentes, Print inicial
	 */
	private void configWindow(){
		options = gameRule.getOptions();

		this.setSize(width,height + 20 + 50 + (options.size()*10)); //Tamanho da janela
		this.setLocationRelativeTo(null); //Posicionado no centro
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		actualX = -1;
		actualY = -1;
		actualZ = -1;
		
		
		panel = new JPanel();
	    getContentPane().add(panel);
	    panel.setLayout(null);
		panel.setBackground(new Color(51, 51, 51));
		
		int numero = engine.getDepth();
		if(numero < engine.getHeight()) numero = engine.getHeight();
		if(numero < engine.getWidth()) numero = engine.getWidth();
		buttons = new MButton[3][numero];

		printCells();
		printGrid();
		addNextButton();
		btnUndo = addUndoButton();
		printMenu();
		
		setVisible(true);
	}
	
	/* Imprime a grid do jogo */
	private void printGrid(){
		int pontaPrimeiroX = width/2 - 25;
		int pontaPrimeiroY = height/2 - 28;
		float alpha = 1;
		int i = 0,j = 0,k = 0;
		for(k = 0; k < engine.getDepth(); k++){
			for(i = 0; i < engine.getHeight(); i++){
				for(j = 0; j < engine.getWidth(); j++){
					alpha = (float) 0.8f - (((k + i + j) * 0.8f) / ((float) engine.getDepth() - 1 + (float) engine.getHeight() - 1 + (float) engine.getWidth() - 1));
					JLabel picLabel = new JLabel(new AlphaImageIcon(new ImageIcon(getClass().getResource("cell_3_grid.png")), alpha));
					picLabel.setBounds(pontaPrimeiroX + (24*j) - (24*k), pontaPrimeiroY - (14*j) + (27*i) - (14*k), 50, 57);
					panel.add(picLabel);
				}
			}
		}
	}
	
	/* Imprime o menu do jogo */
	private void printMenu(){
		/* Botoes */
		addNextButton();
		addRandomButton();
		btnUndo = addUndoButton();
		btnUndo.setEnabled(engine.canRestoreState());
		
		/* Botoes de Escolha de Célula */
		lblx = new JLabel("X:");
		lblx.setForeground(Color.WHITE);
		lblx.setBounds(BORDER - 15, height - BORDER + 10, 25, 20);
		panel.add(lblx);
		for (int j = 0; j < engine.getWidth(); j++){
			buttons[0][j] = new MButton(0,j);
			buttons[0][j].setText(" " + (j) + " ");
			buttons[0][j].addActionListener(this);
			buttons[0][j].setBounds(BORDER + (j * 21), height - BORDER + 10 + (0*20), 20, 20);
			panel.add(buttons[0][j]);
		}
		lbly = new JLabel("Y:");
		lbly.setForeground(Color.WHITE);
		lbly.setBounds(BORDER - 15, height - BORDER + 30, 25, 20);
		panel.add(lbly);
		for (int j = 0; j < engine.getHeight(); j++){
			buttons[1][j] = new MButton(1,j);
			buttons[1][j].setText(" " + (j) + " ");
			buttons[1][j].addActionListener(this);
			buttons[1][j].setBounds(BORDER + (j * 21), height - BORDER + 10 + (1*20), 20, 20);
			panel.add(buttons[1][j]);
		}
		lblz = new JLabel("Z:");
		lblz.setForeground(Color.WHITE);
		lblz.setBounds(BORDER - 15, height - BORDER + 50, 25, 20);
		panel.add(lblz);
		for (int j = 0; j < engine.getDepth(); j++){
			buttons[2][j] = new MButton(2,j);
			buttons[2][j].setText(" " + (j) + " ");
			buttons[2][j].addActionListener(this);
			buttons[2][j].setBounds(BORDER + (j * 21), height - BORDER + 10 + (2*20), 20, 20);
			panel.add(buttons[2][j]);
		}
		
		/* Print Options */
		if(options.size() > 1){
			for(int i = 0; i < options.size(); i++){
				CellState_Alive state = options.get(i);
				JButton colorButton = new JButton(state.getCellStateColorName());
				colorButton.setBounds(BORDER + 240, height - BORDER + 10 + (i*20), 100, 20);
				colorButton.setName(state.getCellStateName());
				colorButton.putClientProperty("id", i);
				colorButton.addActionListener(new ActionListener() {
			           @Override
			           public void actionPerformed(ActionEvent event) {
			               if (actualX >= 0 && actualY >= 0 && actualZ >= 0){
			            	    JButton button = (JButton) event.getSource();
			            	    int id = (Integer) button.getClientProperty("id");
			            	   	controller.changeCell(actualY, actualX, actualZ, options.get(id), true);
			   					actualX = -1;
			   					actualY = -1;
			   					actualZ = -1;
			               }
			          }
			     });
				panel.add(colorButton);
			}
		}
		
	}
	/* Imprime as células vivas do jogo */
	private void printCells(){
		int pontaPrimeiroX = width/2 - 25;
		int pontaPrimeiroY = height/2 - 28;
		float alpha = 1;
		for(int k = 0; k < engine.getDepth(); k++){
			for(int i = 0; i < engine.getHeight(); i++){
				for(int j = 0; j < engine.getWidth(); j++){
					alpha = (float) 0.2f + 0.8f - (((k + i + j) * 0.8f) / ((float) engine.getDepth() - 1 + (float) engine.getHeight() - 1 + (float) engine.getWidth() - 1));
					String imageName = engine.getCellState(i, j, k).getCell3DImageName();
					if(imageName != null){
						JLabel picLabel = new JLabel(new AlphaImageIcon(new ImageIcon(getClass().getResource(imageName)), alpha));
						picLabel.setBounds(pontaPrimeiroX + (24*j) - (24*k), pontaPrimeiroY - (14*j) + (27*i) - (14*k), 50, 57);
						panel.add(picLabel);
					}
				}
			}
		}
	}
	
	/* Adiciona o botão randômico na tela */
	public JButton addRandomButton(){
		JButton button = new JButton("Random");
		button.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	        	   int total = engine.getDepth() * engine.getHeight() * engine.getWidth();
	        	   //Gerar 5% do total
	        	   int gerar = (int) (total * 5.0) / 100;
	        	   System.out.println("total: " + total + " , " + gerar );
	        	   for(int i = 0; i < gerar; i++){
	        		   Random random = new Random();
	        	       int x = random.nextInt(engine.getWidth());
	        	       int y = random.nextInt(engine.getHeight());
	        	       int z = random.nextInt(engine.getDepth());
	        	       CellState state = null;
	        	       if(options.size() == 1){
	        	    	   state = options.get(0);
	        	       }else{
	        	    	   int state_i = random.nextInt(options.size());
	        	    	   state = options.get(state_i);
	        	       }
	        	       controller.changeCell(y, x, z, state, false);
	        	   }
	        	   controller.reloadViews();
	          }
	     });
		button.setBounds(width - BORDER - 150, height - BORDER + 50, 150, 20);
		panel.add(button);
		return button;
	}
	
	/* Adiciona o botão Next Generation na tela */
	public JButton addNextButton(){
		JButton button = new JButton("Next generation");
		button.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	               controller.nextGeneration();
	          }
	     });
		button.setBounds(width - BORDER - 150, height - BORDER + 10, 150, 20);
		panel.add(button);
		return button;
	}
	
	/* Adiciona o botão Undo na tela */
	public JButton addUndoButton(){
		JButton button = new JButton("Undo changes");
		button.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	               controller.undo();
	          }
	     });
		button.setBounds(width - BORDER - 150, height - BORDER + 30, 150, 20);
		panel.add(button);
		return button;
	}
	
	/* Event Listener dos Botões de criar uma nova célula */
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() instanceof MButton){
			MButton button = (MButton) e.getSource();
			int contador = 0;
			switch(button.getI()){
				case 0:
					lblx.setText("" + (button.getJ()));
					actualX = button.getJ();
					contador = engine.getWidth();
					break;
				case 1:
					lbly.setText("" + (button.getJ()));
					actualY = button.getJ();
					contador = engine.getHeight();
					break;
				case 2:
					lblz.setText("" + (button.getJ()));
					actualZ = button.getJ();
					contador = engine.getDepth();
					break;
			}
			for(int i = 0; i < contador; i++){
				buttons[button.getI()][i].setEnabled(false);
			}
			if(actualX >= 0 && actualY >= 0 && actualZ >= 0 && options.size() == 1){
				controller.changeCell(actualY, actualX, actualZ, options.get(0), true);
				actualX = -1;
				actualY = -1;
				actualZ = -1;
			}
		}
	}

	/* Apaga toda a tela, imprime as células existentes, imprime a grade e imprime o menu */
	@Override
	public void reloadScreen() {
		panel.removeAll();
		
		printCells();
		
		printGrid();
		
		printMenu();
		
		panel.updateUI();

	}
}