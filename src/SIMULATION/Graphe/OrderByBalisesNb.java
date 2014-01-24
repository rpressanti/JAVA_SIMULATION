package SIMULATION.Graphe;

import java.util.Comparator;

public class OrderByBalisesNb<E> implements Comparator<Chemin<E>>{

	public OrderByBalisesNb() {
		super() ;
	}

	@Override
	public int compare(Chemin<E> chemin_1, Chemin<E> chemin_2)
	{
		Double length_1 = chemin_1.getLength() ;
		Double length_2 = chemin_2.getLength() ;
		
		return length_1.compareTo( length_2) ;
	}
	
	
}
