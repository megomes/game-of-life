package br.unb.cic.lp.gol;

public class GameRuleHighLife extends GameRule{
	public boolean shouldKeepAlive(int numberOfNeighborhoodAliveCells, boolean alive) {
		return alive && (numberOfNeighborhoodAliveCells == 2 || numberOfNeighborhoodAliveCells == 3);
	}

	public boolean shouldRevive(int numberOfNeighborhoodAliveCells, boolean alive) {
		return alive && (numberOfNeighborhoodAliveCells == 3 || numberOfNeighborhoodAliveCells == 6);
	}

}
