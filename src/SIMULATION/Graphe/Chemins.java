package SIMULATION.Graphe;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Chemins<E> extends ArrayList<Chemin<E>> {

	
	public Chemins<E> minimizeNbBalises() {
		
		return this ;
	}
	
	public Chemin<E> random() {
		return this.get( (int) ( Math.random() * this.size())  ) ;
	}
	
	
}
