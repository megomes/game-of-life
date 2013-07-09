package br.unb.cic.lp.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unb.cic.lp.states.*;

public class GameRule_Generic extends GameRule{
	
	List<CellState_Alive> cellStates;
	List<Integer> reviveCases;
	List<Integer> keepAliveCases;
	public GameRule_Generic(){
		cellStates = new ArrayList<CellState_Alive>();
		reviveCases = new ArrayList<Integer>();
		keepAliveCases = new ArrayList<Integer>();
	}
	protected CellState shouldKeepAlive(HashMap<CellState, Integer> dictState,
			int depth) {
		
		int[] counters = calculateNeighbors(dictState);
		
		/* Calcular se continua vivo */
		int total = 0;
		for(int i = 0; i < cellStates.size(); i++){
			total += counters[i];
		}
		for(int i : keepAliveCases){
			if(total == i){
				return getStateMajority(counters);
			}
		}
		
		return new CellState_Dead();
	}
	private CellState getStateMajority(int[] counters){
		int imaior = 0;
		for(int i = 1; i < cellStates.size(); i++){
			if(counters[i] > counters[imaior]){
				imaior = i;
			}
		}
		return cellStates.get(imaior);
	}

	protected CellState shouldRevive(HashMap<CellState, Integer> dictState, int depth) {
		
		int[] counters = calculateNeighbors(dictState);
		
		/* Calcular se continua vivo */
		int total = 0;
		for(int i = 0; i < cellStates.size(); i++){
			total += counters[i];
		}
		for(int i : reviveCases){
			if(total == i){
				return getStateMajority(counters);
			}
		}
		
		return new CellState_Dead();
	}
	private int[] calculateNeighbors(HashMap<CellState, Integer> dictState){
		/* Contar tipos de vizinhos */
		int[] counters = new int[cellStates.size()];
		//Inicializar todos os nœmeros
		for(int i = 0; i < cellStates.size(); i++){
			counters[i] = 0;
		}
		for(CellState cell : dictState.keySet()){
			String coisa = cell.getCellStateName() + " -> " + dictState.get(cell);
			for(CellState_Alive state : cellStates){
				if(cell.getCellStateName() == state.getCellStateName()){
					counters[cellStates.indexOf(state)] += dictState.get(cell);
				}
			}
		}
		return counters;
	}
	public List<CellState_Alive> getOptions() {
		return cellStates;
	}

	public HashMap<String, String> getImageOptions() {
		HashMap<String, String> list = new HashMap<String,String>();
		list.put(new CellState_Dead().getCellStateName(), new CellState_Dead().getCellImageName());
		for(CellState_Alive state : cellStates){
			list.put(state.getCellStateName(), state.getCellImageName());
		}
		return list;
	}
	
	public void addReviveCase(int count){
		reviveCases.add(count);
	}
	public void addKeepAliveCase(int count){
		keepAliveCases.add(count);
	}
	public void addCellState_Alive(CellState_Alive state){
		cellStates.add(state);
	}

}
