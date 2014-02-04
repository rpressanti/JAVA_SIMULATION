package SIMULATION.Graphe;

// CLASS DONE

import java.util.ArrayList;

@SuppressWarnings("serial")
//public class Chemin<F<E> extends Arete<E>> extends ArrayList<Arete<E>> {
public class Chemin<A extends Arete<N,P> , N extends Noeud<A,N,P>, P> extends ArrayList<A> {
	private Double length ;
	
	
	public Chemin() {
		super() ;
		this.length = new Double( 0.0 ) ;
	}
	
	public String toString() {
		String string = "Chemin de longueur " + this.getLength().toString() + "\n" ;
		Integer indice_arete = 0 ;
		
		for( A arete : this )
		{
			indice_arete ++ ;
			string += "Arete d'indice: " + indice_arete.toString() + "\n" ;
			string += arete.toString(); 
		}
		
		return string ;
	}
	
	public Double getLength() {
		return this.length ;
	}
	
	public boolean add( A arete ) {
		
		boolean result = super.add( arete ) ;
		if ( result )
			this.length += arete.getWeight() ;
		return result ;
	}
	
	public Chemins<A,N,P> successeurs() {
		
		Chemins<A,N,P> result = new Chemins<A,N,P>() ;
		
		for( A arete : (Iterable<A>) this.last().getAretes().values() )
		{
			Chemin<A,N,P> tmp = this.clone() ;
			tmp.add( arete ) ;
			result.add( tmp ) ;
		}
		
		return result ;
	}
	
	public N last() {
		return this.get( this.size() -1 ).getDestination() ;
	}
	
	
	public boolean isTrivial() {
		return ( this.size() == 1 ) && this.get( 0 ).isTrivial() ;
	}
	
	public boolean untrivial() {
		
		boolean result = false ;
		
		for( Arete<N,P> arete : this )
			if( arete.isTrivial() ) 
				this.remove( arete ) ;
		
		return result ;
	}
	
	public Chemin<A,N,P> clone() {
		
		Chemin<A,N,P> result = new Chemin<A,N,P>() ;
		
		for( A arete : this)
			result.add( arete ) ;
		
		return result ;
	}
	
}
