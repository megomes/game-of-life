package br.unb.cic.lp.gol;

public abstract class GameRule {
	public abstract boolean shouldKeepAlive(int numberOfNeighborhoodAliveCells, boolean alive);
	public abstract boolean shouldRevive(int numberOfNeighborhoodAliveCells, boolean alive);
}
