package br.unb.cic.lp.rules;

import br.unb.cic.lp.states.*;

import java.util.HashMap;

public abstract class GameRule {
	public CellState shouldChange(HashMap<CellState, Integer> dictState, CellState actualState){
		CellState state;
		if(actualState instanceof CellState_Dead){
			state = shouldRevive(dictState);
		}else{
			state = shouldKeepAlive(dictState);
		}
		return state;
	}
	protected abstract CellState shouldKeepAlive(HashMap<CellState, Integer> dictState);
	protected abstract CellState shouldRevive(HashMap<CellState, Integer> dictState);
	
	public abstract HashMap<Integer, CellState> getOptions();
}
