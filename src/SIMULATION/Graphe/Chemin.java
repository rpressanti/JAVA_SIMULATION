package SIMULATION.Graphe;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Chemin<E> extends ArrayList<Arete<E>> {

	private double length ;
	
	
	public Chemin() {
		super() ;
		this.length = 0 ;
	}
	
	public boolean add( Arete<E> arete ) {
		
		boolean result = super.add( arete ) ;
		if ( result )
			this.length += arete.getWeight() ;
		return result ;
	}
	
	public Chemins<E> successeurs() {
		
		Chemins<E> result = new Chemins<E>() ;
		
		for( Arete<E> arete : this )
		{
			Chemin<E> tmp = this.clone() ;
			tmp.add( arete ) ;
			result.add( tmp ) ;
		}
		
		return result ;
	}
	
	public Chemin<E> clone() {
		
		Chemin<E> result = new Chemin<E>() ;
		
		for( Arete<E> arete : this)
			result.add( arete ) ;
		
		return result ;
	}
}
