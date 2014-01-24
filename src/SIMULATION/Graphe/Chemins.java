package SIMULATION.Graphe;

import java.util.PriorityQueue;

// CLASS DONE

@SuppressWarnings("serial")
public class Chemins<E> extends PriorityQueue<Chemin<E>> {

	
	public Chemins( /*Comparator<Chemin<E>> comparator*/) {
		super( 0 , /*comparator*/ new OrderByBalisesNb<E>()) ;
	}
	
		
	public Chemins<E> minimizeNbBalises() {	
		Chemins<E> result = new Chemins<E>() ;

		Chemin<E> current_elem =this.poll()  ;
		double length = current_elem.getLength() ;
		
		while( (current_elem = this.poll() ).getLength() == length )
			result.add( current_elem ) ;
		
		return result ;
	}
	
	public Chemin<E> random() {
		
		Chemin<E> result = new Chemin<E>() ;
		int nb_iter = (int) ( Math.random() * this.size()) , i = 0 ;
		
		for( i = 0 ; i < nb_iter ; i++ )
			result = this.poll() ;

		return result ;
	}
	
	
}
