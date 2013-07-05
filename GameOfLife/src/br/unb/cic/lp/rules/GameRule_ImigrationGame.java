package br.unb.cic.lp.rules;

import java.util.HashMap;

import br.unb.cic.lp.states.*;
/**
 * Essa classe representa a regra ImmigrationGame do Game of Life
 * Se a célula estiver viva e 2/3 celulas vizinhas estiverem vivas, ela permanecerá viva.
 * Se ela estiver morta e 3 vizinhas estiverem vivas, ela renascerá.
 * 
 * Quando nascer, a célula irá adotar as características da maioridade de sua vizinhança.
 * A vida A[o] irá prevalecer diante a uma igualdade de valores.
 * 
 * @author Matheus Ervilha
 *
 */
public class GameRule_ImigrationGame extends GameRule {

	protected CellState shouldKeepAlive(HashMap<CellState, Integer> dictState) {
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

	protected CellState shouldRevive(HashMap<CellState, Integer> dictState) {
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

	public HashMap<Integer, CellState> getOptions() {
		HashMap<Integer, CellState> options = new HashMap<Integer, CellState>();
		options.put(1, new CellState_Alive());
		options.put(2, new CellState_Alive_B());

		return options;
	}

}
