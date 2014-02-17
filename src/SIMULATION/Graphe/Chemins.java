package SIMULATION.Graphe;

import java.util.PriorityQueue;

// CLASS DONE

@SuppressWarnings("serial")
public class Chemins<C extends Chemin<C,A,N,P> , A extends Arete<A,N,P>, N extends Noeud<A,N,P> ,P> extends PriorityQueue<C> {

	protected Class<C> classe_chemin ;
	
	public Chemins( Class<C> classe_chemin ) {
		super( 1 , new OrderByBalisesNb<C,A,N,P>()) ;
		this.classe_chemin = classe_chemin ;
	}
	
	public String toString() {
		String string = this.size() + " chemins" + "\n" ;
		Integer indice_chemin = new Integer( 0 ) ;
		
		for( Chemin<C,A,N,P> chemin : this )
		{
			indice_chemin ++ ;
			string += "Chemin d'indice " + indice_chemin.toString() + "\n" ;
			string += chemin.toString() ;
		}
		
		string += "\n" ;
	
		return string ;
	}
	
		
	public Chemins<C,A,N,P> minimizeNbBalises() {	
		Chemins<C,A,N,P> result = new Chemins<C,A,N,P>( this.classe_chemin) ;

		if( this.isEmpty() )
			return result ;
		
		C current_elem =this.poll()  ;
		double length = current_elem.getLength() ;
		
		do {
			result.add( current_elem ) ;
		}
		while( !this.isEmpty() && (current_elem = this.poll() ).getLength() == length ) ;
			
		//System.out.println( "Taille apres min:" + result.size() ) ;
		
		return result ;
	}
	

	public C random() {
		
		int nb_iter = (int) ( Math.random() * this.size() ) ;
		System.out.println( "Random:" + nb_iter ) ;			
		@SuppressWarnings("unchecked")
		C result = (C) this.toArray()[0] ; //[nb_iter] ;	
		
		return result ;
		 
	}
	
	
}
