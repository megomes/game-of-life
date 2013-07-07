package br.unb.cic.lp.gol;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.*;
/**
 * Essa classe atua como o controle da View na Janela JFrame
 * 
 * @author Matheus Ervilha
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
	/* Bot›es */
	private JButton btnUndo;
	
	public GameViewWindow(int width, int height, GameEngine gameEngine, GameController gameController, GameRule gameRule){
		this.height = height;
		this.width = width;
		this.engine = gameEngine;
		this.controller = gameController;
		this.gameRule = gameRule;
		configWindow();
	}

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
		
		setVisible(true);
	}
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
	
	public void reloadScreen(){
		btnUndo.setEnabled(engine.canRestoreState());

		for(int i = 0; i < engine.getHeight(); i++){
			for(int j = 0; j < engine.getWidth(); j++){
				
				buttons[i][j].setBackgroundImage(getLoadedImage(engine.getCellState(i, j, 0).getCellStateName()));

			}
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() instanceof MButton){
			MButton button = (MButton) e.getSource();
			CellState state = engine.getCellState(button.getI(), button.getJ(), 0);
			List<CellState> options = gameRule.getOptions();
			if(options == null){
				if (state instanceof CellState_Alive){
					controller.changeCell(button.getI(), button.getJ(), 0, new CellState_Dead(), true);
				}else{
					controller.changeCell(button.getI(), button.getJ(), 0, new CellState_Alive(), true);
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
