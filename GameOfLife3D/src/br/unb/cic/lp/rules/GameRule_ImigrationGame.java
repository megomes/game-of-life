package br.unb.cic.lp.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unb.cic.lp.states.*;
/**
 * Essa classe representa a regra ImmigrationGame do Game of Life
 * Se a célula estiver viva e 2/3 celulas vizinhas estiverem vivas, ela permanecerá viva.
 * Se ela estiver morta e 3 vizinhas estiverem vivas, ela renascerá.
 * 
 * A célula irá adotar as características da maioridade de sua vizinhança.
 * A vida A[o] irá prevalecer diante a uma igualdade de valores.
 * 
 * @author Matheus Ervilha
 *
 */
public class GameRule_ImigrationGame extends GameRule {

	protected CellState shouldKeepAlive(HashMap<CellState, Integer> dictState, int depth) {
		int Alive_A_count = 0;
		int Alive_B_count = 0;
		for(CellState cell : dictState.keySet()){
			if (cell.getCellStateName() == "alive_a"){
				Alive_A_count = dictState.get(cell);
			}
			if (cell.getCellStateName() == "alive_b"){
				Alive_B_count = dictState.get(cell);
			}
		}
		if(Alive_B_count + Alive_A_count == 2 || Alive_B_count + Alive_A_count == 3){
			if (Alive_A_count >= Alive_B_count){
				return new CellState_Alive();
			}else{
				return new CellState_Alive_B();
			}
		}
		return new CellState_Dead();
	}

	protected CellState shouldRevive(HashMap<CellState, Integer> dictState, int depth) {
		int Alive_A_count = 0;
		int Alive_B_count = 0;
		for(CellState cell : dictState.keySet()){
			if (cell.getCellStateName() == "alive_a"){
				Alive_A_count = dictState.get(cell);
			}
			if (cell.getCellStateName() == "alive_b"){
				Alive_B_count = dictState.get(cell);
			}
		}
		if(Alive_B_count + Alive_A_count == 3){
			if (Alive_A_count >= Alive_B_count){
				return new CellState_Alive();
			}else{
				return new CellState_Alive_B();
			}
		}
		return new CellState_Dead();
	}

	public List<CellState> getOptions() {
		List<CellState> options = new ArrayList<CellState>();
		options.add(new CellState_Alive());
		options.add(new CellState_Alive_B());

		return options;
	}
	public HashMap<String,String> getImageOptions(){
		HashMap<String, String> list = new HashMap<String,String>();
		list.put(new CellState_Dead().getCellStateName(), new CellState_Dead().getCellImageName());
		list.put(new CellState_Alive().getCellStateName(), new CellState_Alive().getCellImageName());
		list.put(new CellState_Alive_B().getCellStateName(), new CellState_Alive_B().getCellImageName());

		return list;

	}
}
