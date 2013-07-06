package br.unb.cic.lp.gol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import br.unb.cic.lp.rules.*;
import br.unb.cic.lp.states.*;
/**
 * Essa classe atua como o controle da View na Janela JFrame
 * 
 * @author Matheus Ervilha
 */
public class GameViewWindow extends JFrame implements ActionListener{
	private static final int BORDER = 20;
	private int width;
	private int height;
	private MButton[][] buttons;
	private GameEngine engine;
	private GameController controller;
	private GameRule gameRule;
	
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
	       
		/* Botoes */
	    buttons = new MButton[engine.getHeight()][engine.getWidth()];
	    //ImageIcon icone = new ImageIcon("/GameOfLife/resources/cell_alive_a.png");
		for(int i = 0; i < engine.getHeight(); i++){
			for(int j = 0; j < engine.getWidth(); j++){
				buttons[i][j] = new MButton(i,j);
				//buttons[i][j].setIcon(icone);
				buttons[i][j].setText(engine.getCellState(i, j).getCellImageName());
				buttons[i][j].setBounds(j * tamX + BORDER, i * tamY + BORDER, tamX, tamY);
				buttons[i][j].addActionListener(this);
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
	
	public void reloadScreen(){
		btnUndo.setEnabled(engine.canRestoreState());
		for(int i = 0; i < engine.getHeight(); i++){
			for(int j = 0; j < engine.getWidth(); j++){
				buttons[i][j].setText(engine.getCellState(i, j).getCellImageName());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() instanceof MButton){
			MButton button = (MButton) e.getSource();
			CellState state = engine.getCellState(button.getI(), button.getJ());
			List<CellState> options = gameRule.getOptions();
			if(options == null){
				if (state instanceof CellState_Alive){
					controller.changeCell(button.getI(), button.getJ(), new CellState_Dead());
				}else{
					controller.changeCell(button.getI(), button.getJ(), new CellState_Alive());
				}
			}else{
				if (state instanceof CellState_Dead){
					controller.changeCell(button.getI(), button.getJ(), options.get(0));
				}else{
					for(int i = 0; i < options.size() - 1; i++){
						if (options.get(i).getCellStateName() == state.getCellStateName()){
							controller.changeCell(button.getI(), button.getJ(), options.get(i + 1));
							return;
						}
					}
					controller.changeCell(button.getI(), button.getJ(), new CellState_Dead());
				}
			}
		}
	}
	
}
