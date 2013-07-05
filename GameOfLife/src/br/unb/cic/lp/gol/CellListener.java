package br.unb.cic.lp.gol;

import java.util.EventListener;

public interface CellListener extends EventListener{
	void cellRevived(CellEvent e);
	void cellKilled(CellEvent e);
}
