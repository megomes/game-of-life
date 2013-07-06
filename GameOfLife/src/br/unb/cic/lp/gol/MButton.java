package br.unb.cic.lp.gol;

import javax.swing.JButton;

public class MButton extends JButton {
	private int i;
	private int j;
	public MButton(int i, int j){
		this.i = i;
		this.j = j;
	}
	
	public int getI(){
		return i;
	}
	public int getJ(){
		return j;
	}

}
