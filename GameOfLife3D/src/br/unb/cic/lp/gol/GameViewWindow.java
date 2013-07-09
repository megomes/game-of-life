package br.unb.cic.lp.gol;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.*;
/**
 * Essa classe atua como o controle da View na Janela Java Swing
 * Caso a grade seja 2D, essa janela será criada
 * 
 */
public class GameViewWindow extends GameWindow implements ActionListener{
	private static final int BORDER = 20;
	private int width;
	private int height;
	private MButton[][] buttons;
	private GameEngine engine;
	private GameController controller;
	private GameRule gameRule;
	private HashMap<String, String> images_src;
	private List<Image> images;
	/* Botões */
	private JButton btnUndo;
	
	/**
	 * Construtor da janela
	 * 
	 * @param width tamanho em px
	 * @param height altura em px
	 * @param gameEngine gameEngine utilizada
	 * @param gameController gameController utilizado
	 * @param gameRule regras implementadas
	 */
	public GameViewWindow(int width, int height, GameEngine gameEngine, GameController gameController, GameRule gameRule){
		this.height = height;
		this.width = width;
		this.engine = gameEngine;
		this.controller = gameController;
		this.gameRule = gameRule;
		configWindow();
	}

	/**
	 *  Configuração inicial da janela 
	 *	Criação das células (botões)
	 * 	Criação do Menu
	 * 	Load das imagens. Para que não seja necessário carregá-las sempre  
	 */
	private void configWindow(){
		this.setSize(width,height + 20 + 50); //Tamanho da janela
		this.setLocationRelativeTo(null); //Posicionado no centro
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		int tamX = (width - BORDER * 2)/engine.getWidth();
		int tamY = (height - BORDER * 2)/engine.getHeight();
		
		JPanel panel = new JPanel();
	    getContentPane().add(panel);
	    panel.setLayout(null);
	    /* Load Images */
	    
	    images_src = gameRule.getImageOptions();
	    images = new ArrayList<Image>();
	    for(String src : images_src.keySet()){
	    	images.add(new ImageIcon(getClass().getResource(images_src.get(src))).getImage());
	    }
	       
		/* Botoes */
	    buttons = new MButton[engine.getHeight()][engine.getWidth()];
		for(int i = 0; i < engine.getHeight(); i++){
			for(int j = 0; j < engine.getWidth(); j++){
				buttons[i][j] = new MButton(i,j);
				buttons[i][j].setBounds(j * tamX + BORDER, i * tamY + BORDER, tamX, tamY);
				buttons[i][j].setBackgroundImage(getLoadedImage("dead"));
				buttons[i][j].addActionListener(this);
				buttons[i][j].setBorder(BorderFactory.createEmptyBorder());
				panel.add(buttons[i][j]);
			}
		}
		addNextButton(panel);
		btnUndo = addUndoButton(panel);
		addRandomButton(panel);
		if(engine.getWidth() == 17 && engine.getHeight() == 17) addShowOffButton(panel);
		
		setVisible(true);
	}
	/**
	 * Cria o botão de Next Generation
	 * @param panel destino
	 * @return botão criado
	 */
	public JButton addNextButton(JPanel panel){
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
	/**
	 * Criação do botão de Next Generation
	 * @param panel destino
	 * @return botão criado
	 */
	public JButton addRandomButton(JPanel panel){
		JButton button = new JButton("Random");
		button.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	        	   int total = engine.getHeight() * engine.getWidth();
	        	   //Gerar 5% do total
	        	   int gerar = (int) (total * 25.0) / 100;
	        	   System.out.println("total: " + total + " , " + gerar );
	        	   for(int i = 0; i < gerar; i++){
	        		   Random random = new Random();
	        	       int x = random.nextInt(engine.getWidth());
	        	       int y = random.nextInt(engine.getHeight());
	        	       CellState state = null;
	        	       List<CellState_Alive> options = gameRule.getOptions();
	        	       if(options.size() == 1){
	        	    	   state = options.get(0);
	        	       }else{
	        	    	   int state_i = random.nextInt(options.size());
	        	    	   state = options.get(state_i);
	        	       }
	        	       controller.changeCell(y, x, 0, state, false);
	        	   }
	        	   controller.reloadViews();
	          }
	     });
		button.setBounds(width - BORDER - 150 - 160 - 160, height - BORDER + 10, 150, 20);
		panel.add(button);
		return button;
	}
	
	/**
	 * Criação do Botão de Show off da aplicação
	 * Esse botão só é criado em um tabuleiro 17x17 e cria células de estados
	 * aleatórios em uma posição calculada para que fique em um Loop infinito
	 * entre figuras
	 * 
	 * @param panel destino
	 * @return botão criado
	 */
	public JButton addShowOffButton(JPanel panel){
		JButton button = new JButton("Show Off");
		button.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	        	   int[][] padrao = {{0,0,0,0,0,0,0,0}, 
	        			   			 {0,0,0,0,0,0,0,0}, 
	        			   			 {0,0,0,0,1,1,1,0}, 
	        			   			 {0,0,0,0,0,0,0,0},
	        			   			 {0,0,1,0,0,0,0,1},
	        			   			 {0,0,1,0,0,0,0,1},
	        			   			 {0,0,1,0,0,0,0,1},
	        			   			 {0,0,0,0,1,1,1,0}};
	        	   
        	       List<CellState_Alive> options = gameRule.getOptions();
        	       CellState_Alive state;
        		   Random random = new Random();
	        	   if(options.size() == 1){
        	    	   state = options.get(0);
        	       }else{
        	    	   int state_i = random.nextInt(options.size());
        	    	   state = options.get(state_i);
        	       }
	        	   
        		   for(int i = 0; i < 8; i++){
        			   for(int j = 0; j < 8; j++){
        				   if(padrao[i][j] == 1){
        					   if(options.size() == 1){
        	        	    	   state = options.get(0);
        	        	       }else{
        	        	    	   int state_i = random.nextInt(options.size());
        	        	    	   state = options.get(state_i);
        	        	       }
        					   controller.changeCell(i, j, 0, state, false);
        				   }
        			   }
        		   }
        		   for(int i = 0; i < 8; i++){
        			   for(int j = 0; j < 8; j++){
        				   if(padrao[i][j] == 1){
        					   if(options.size() == 1){
        	        	    	   state = options.get(0);
        	        	       }else{
        	        	    	   int state_i = random.nextInt(options.size());
        	        	    	   state = options.get(state_i);
        	        	       }
        					   controller.changeCell(16 - i, j, 0, state, false);
        				   }
        			   }
        		   }
        		   for(int i = 0; i < 8; i++){
        			   for(int j = 0; j < 8; j++){
        				   if(padrao[i][j] == 1){
        					   if(options.size() == 1){
        	        	    	   state = options.get(0);
        	        	       }else{
        	        	    	   int state_i = random.nextInt(options.size());
        	        	    	   state = options.get(state_i);
        	        	       }
        					   controller.changeCell(16 - i, 16 - j, 0, state, false);
        				   }
        			   }
        		   }
        		   for(int i = 0; i < 8; i++){
        			   for(int j = 0; j < 8; j++){
        				   if(padrao[i][j] == 1){
        					   if(options.size() == 1){
        	        	    	   state = options.get(0);
        	        	       }else{
        	        	    	   int state_i = random.nextInt(options.size());
        	        	    	   state = options.get(state_i);
        	        	       }
        					   controller.changeCell(i, 16 - j, 0, state, false);
        				   }
        			   }
        		   }
	        	   controller.reloadViews();
	          }
	     });
		button.setBounds(width - BORDER - 150 - 160 - 160, height - BORDER + 35, 150, 20);
		panel.add(button);
		return button;
	}
	
	/**
	 * Criação do botão de Undo
	 * @param panel destino
	 * @return botão criado (utilizado para desabilitar o botão, caso não exista estados anteriores
	 */
	public JButton addUndoButton(JPanel panel){
		JButton button = new JButton("Undo changes");
		button.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent event) {
	               controller.undo();
	          }
	     });
		button.setBounds(width - BORDER - 150 - 160, height - BORDER + 10, 150, 20);
		panel.add(button);
		return button;
	}
	
	/* Retorna uma imagem que já tinha sido carregada */
	private Image getLoadedImage(String stateName){
		int contador = 0;
		for(String src : images_src.keySet()){
			if (src == stateName){
				return images.get(contador);
			}else{
				contador++;
			}
		}
		return null;
	}
	
	/* Reload screen. Altera a imagem de todos os botões que foram alterados (Células) */
	public void reloadScreen(){
		btnUndo.setEnabled(engine.canRestoreState());

		for(int i = 0; i < engine.getHeight(); i++){
			for(int j = 0; j < engine.getWidth(); j++){
				buttons[i][j].setBackgroundImage(getLoadedImage(engine.getCellState(i, j, 0).getCellStateName()));

			}
		}
	}
	

	/* Evento Listener do botão da célula. Troca o estado do botão(célula) clicado */
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() instanceof MButton){
			MButton button = (MButton) e.getSource();
			CellState state = engine.getCellState(button.getI(), button.getJ(), 0);
			List<CellState_Alive> options = gameRule.getOptions();
			if(options.size() == 1){
				if (state instanceof CellState_Alive_A){
					controller.changeCell(button.getI(), button.getJ(), 0, new CellState_Dead(), true);
				}else{
					controller.changeCell(button.getI(), button.getJ(), 0, options.get(0), true);
				}
			}else{
				if (state instanceof CellState_Dead){
					controller.changeCell(button.getI(), button.getJ(), 0, options.get(0), true);
				}else{
					for(int i = 0; i < options.size() - 1; i++){
						if (options.get(i).getCellStateName() == state.getCellStateName()){
							controller.changeCell(button.getI(), button.getJ(), 0, options.get(i + 1), true);
							return;
						}
					}
					controller.changeCell(button.getI(), button.getJ(), 0, new CellState_Dead(), true);
				}
			}
		}
	}
	
}
