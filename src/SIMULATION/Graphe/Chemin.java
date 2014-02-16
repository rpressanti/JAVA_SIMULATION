package SIMULATION.Graphe;

// CLASS DONE

//import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("serial")

public class Chemin<C extends Chemin<C,A,N,P> , A extends Arete<A,N,P> , N extends Noeud<A,N,P>, P> extends LinkedList<A> {
	
	protected Class<C> classe_chemin ;
	private Double length ;
	
	
	public Chemin( Class<C> classe_chemin ) {
		super() ;
		this.classe_chemin = classe_chemin ;
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
	
	public Chemins<C,A,N,P> successeurs() {
		
		Chemins<C,A,N,P> result = new Chemins<C,A,N,P>( this.classe_chemin ) ;
		
		for( A arete : (Iterable<A>) this.last().getAretes().values() )
		{
			C tmp = this.clone() ;
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
		
		for( A arete : this )
			if( arete.isTrivial() ) 
				this.remove( arete ) ;
		
		return result ;
	}
	
	public C clone() {
		
		C result = null ;
		
		try {

			result = (C) this.classe_chemin.newInstance() ;
			for( A arete : this)
				result.add( arete ) ;
		
		} catch (Exception e ) {
			System.out.println( "Clonage du chemin échoué" );
		}
		
		
		return result ;
	}
	
}
