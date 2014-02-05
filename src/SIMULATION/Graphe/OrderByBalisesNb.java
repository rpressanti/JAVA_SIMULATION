package SIMULATION.Graphe;

import java.util.Comparator;

public class OrderByBalisesNb<A extends Arete<A,N,P>,N extends Noeud<A,N,P> ,P> implements Comparator<Chemin<A,N,P>>{

	public OrderByBalisesNb() {
		super() ;
	}

	@Override
	public int compare(Chemin<A,N,P> chemin_1, Chemin<A,N,P> chemin_2)
	{
		Double length_1 = chemin_1.getLength() ;
		Double length_2 = chemin_2.getLength() ;
		
		return length_1.compareTo( length_2) ;
	}
	
	
}
