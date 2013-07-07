package br.unb.cic.lp.rules;

import br.unb.cic.lp.states.*;

import java.util.HashMap;
import java.util.List;

public abstract class GameRule {
	public CellState shouldChange(HashMap<CellState, Integer> dictState, CellState actualState, int depth){
		CellState state;
		if(actualState instanceof CellState_Dead){
			state = shouldRevive(dictState, depth);
		}else{
			state = shouldKeepAlive(dictState, depth);
		}
		return state;
	}
	protected abstract CellState shouldKeepAlive(HashMap<CellState, Integer> dictState, int depth);
	protected abstract CellState shouldRevive(HashMap<CellState, Integer> dictState, int depth);
	
	public abstract List<CellState> getOptions();
	public abstract HashMap<String, String> getImageOptions();
}
