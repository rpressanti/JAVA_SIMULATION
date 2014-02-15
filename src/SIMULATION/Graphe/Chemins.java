package SIMULATION.Graphe;

import java.util.PriorityQueue;

// CLASS DONE

@SuppressWarnings("serial")
public class Chemins<A extends Arete<A,N,P>, N extends Noeud<A,N,P> ,P> extends PriorityQueue<Chemin<A,N,P>> {

	
	public Chemins() {
		super( 1 , new OrderByBalisesNb<A,N,P>()) ;
	}
	
	public String toString() {
		String string = this.size() + " chemins" + "\n" ;
		Integer indice_chemin = new Integer( 0 ) ;
		
		for( Chemin<A,N,P> chemin : this )
		{
			indice_chemin ++ ;
			string += "Chemin d'indice " + indice_chemin.toString() + "\n" ;
			string += chemin.toString() ;
		}
			
	
		return string ;
	}
	
		
	public Chemins<A,N,P> minimizeNbBalises() {	
		Chemins<A,N,P> result = new Chemins<A,N,P>() ;

		Chemin<A,N,P> current_elem =this.poll()  ;
		double length = current_elem.getLength() ;
		
		while( (current_elem = this.poll() ).getLength() == length )
			result.add( current_elem ) ;
		
		return result ;
	}
	
	public Chemin<A,N,P> random() {
		
		Chemin<A,N,P> result = new Chemin<A,N,P>() ;
		int nb_iter = (int) ( Math.random() * this.size()) , i = 0 ;
		System.out.println( "Random:" + nb_iter ) ;
		
		for( i = 0 ; i < nb_iter ; i++ )
			result = this.poll() ;

		return result ;
	}
	
	
}
