package SIMULATION.Graphe;

import java.util.PriorityQueue;

// CLASS DONE

@SuppressWarnings("serial")
public class Chemins<E> extends PriorityQueue<Chemin<E>> {

	
	public Chemins() {
		super( 1 , new OrderByBalisesNb<E>()) ;
	}
	
	public String toString() {
		String string = this.size() + " chemins" + "\n" ;
		Integer indice_chemin = new Integer( 0 ) ;
		
		for( Chemin<E> chemin : this )
		{
			indice_chemin ++ ;
			string += "Chemin d'indice " + indice_chemin.toString() + "\n" ;
			string += chemin.toString() ;
		}
			
	
		return string ;
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
