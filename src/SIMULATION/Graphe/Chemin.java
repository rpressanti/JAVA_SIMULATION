package SIMULATION.Graphe;

// CLASS DONE

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Chemin<E> extends ArrayList<Arete<E>> {

	private Double length ;
	
	
	public Chemin() {
		super() ;
		this.length = new Double( 0.0 ) ;
	}
	
	public String toString() {
		String string = "Chemin de longueur " + this.getLength().toString() + "\n" ;
		Integer indice_arete = 0 ;
		
		for( Arete<E> arete : this )
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
	
	public boolean add( Arete<E> arete ) {
		
		boolean result = super.add( arete ) ;
		if ( result )
			this.length += arete.getWeight() ;
		return result ;
	}
	
	public Chemins<E> successeurs() {
		
		Chemins<E> result = new Chemins<E>() ;
		
		for( Arete<E> arete : this.last().getAretes().values() )
		{
			Chemin<E> tmp = this.clone() ;
			tmp.add( arete ) ;
			result.add( tmp ) ;
		}
		
		return result ;
	}
	
	public Noeud<E> last() {
		return this.get( this.size() -1 ).getDestination() ;
	}
	
	
	public boolean isTrivial() {
		return ( this.size() == 1 ) && this.get( 0 ).isTrivial() ;
	}
	
	public boolean untrivial() {
		
		boolean result = false ;
		
		for( Arete<E> arete : this )
			if( arete.isTrivial() ) 
				this.remove( arete ) ;
		
		return result ;
	}
	
	public Chemin<E> clone() {
		
		Chemin<E> result = new Chemin<E>() ;
		
		for( Arete<E> arete : this)
			result.add( arete ) ;
		
		return result ;
	}
	
}
