package br.unb.cic.lp.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unb.cic.lp.states.*;
/**
 * Essa classe representa a regra padrão do Game of Life
 * Se a célula estiver viva e 2/3 celulas vizinhas estiverem vivas, ela permanecerá viva.
 * Se ela estiver morta e 3 vizinhas estiverem vivas, ela renascerá.
 * 
 * @author Matheus Ervilha
 *
 */
public class GameRule_Standard extends GameRule{
	/*
	 * Esses dois métodos retornarão o estado em que a célula deverá ficar, dependendo se suas vizinhas
	 * 
	 *  @param dictState HashMap contendo <CellState, Integer> Um contador de estado das suas células vizinhas.
	 *  Sendo CellState o Estado e Integer o valor correspondente na contagem
	 * 
	 */
	protected CellState shouldKeepAlive(HashMap<CellState, Integer> dictState){
		for(CellState cell : dictState.keySet()){
			if (cell.getCellStateName() == "alive_a"){
				if (dictState.get(cell) == 2 || dictState.get(cell) == 3){
					return new CellState_Alive();
				}
				break;
			}
		}
		return new CellState_Dead();
	}
	protected CellState shouldRevive(HashMap<CellState, Integer> dictState){
		for(CellState cell : dictState.keySet()){
			if (cell.getCellStateName() == "alive_a"){
				if (dictState.get(cell) == 3){
					return new CellState_Alive();
				}
				break;
			}
		}
		return new CellState_Dead();
	}
	/*
	 * Retorna um HashMap contendo o ID da opção e a opção de VIDA existente na Regra.
	 * Como a regra Padrão apenas contem um modo de VIDA, retornamos NULL 
	 */
	public List<CellState> getOptions(){
		return null;
	}
	
	public HashMap<String,String> getImageOptions(){
		HashMap<String, String> list = new HashMap<String,String>();
		list.put(new CellState_Dead().getCellStateName(), new CellState_Dead().getCellImageName());
		list.put(new CellState_Alive().getCellStateName(), new CellState_Alive().getCellImageName());
		
		return list;

	}

}
