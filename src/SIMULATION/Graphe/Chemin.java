package SIMULATION.Graphe;

// CLASS DONE

//import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("serial")

public class Chemin<C extends Chemin<C,A,N,P> , A extends Arete<A,N,P> , N extends Noeud<A,N,P>, P> extends LinkedList<A> {
	
	protected Class<C> classe_chemin ;
	protected Class<A> classe_arete ;
	protected Class<N> classe_noeud ;
	protected Double length ;
	
	
	public Chemin( Class<C> classe_chemin , Class<A> classe_arete , Class<N> classe_noeud ) {
		super() ;
		this.classe_chemin = classe_chemin ;
		this.classe_arete = classe_arete ;
		this.classe_noeud = classe_noeud ;
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
		
		for( A arete : this.last().getAretes().values() )
		{
			//System.out.println( "Succ" + arete.getDestination().getContent()) ;
			C tmp = this.clone() ;
			tmp.add( arete ) ;
			result.add( tmp ) ;
		}
		
		return result ;
	}
	
	
	public N last() {
		N result = null ;
		
		if( ! this.isEmpty() )
			result = this.get( this.size() -1 ).getDestination() ;
	
		return result ;
	}
	
	
	public boolean isTrivial() {
		return ( this.size() == 1 ) && this.get( 0 ).isTrivial() ;
	}
	
	public void untrivial() {
		this.remove( this.get(0) ) ;
	}
	
	public C clone() {
		
		C result = null ;
		N noeud_origine = null , noeud_destination = null ;
		A arete_tmp = null ;
		
		try {
		
			result = (C) this.classe_chemin.newInstance() ;
			
			if( this.isEmpty() )
				return result ;
			
			noeud_origine = this.get(0).getOrigine().clone();
			
			for( A arete : this)
			{
				noeud_destination = arete.getDestination().clone();
				arete_tmp = this.classe_arete.getDeclaredConstructor( 
						new Class[] { this.classe_noeud , this.classe_noeud , Double.class } 
					).newInstance( noeud_origine , noeud_destination , arete.weight ) ;

				noeud_origine = noeud_destination ;

				result.add( arete_tmp ) ;
			}
			
			result.classe_chemin = this.classe_chemin ;
			result.length = this.length ;
		
		} catch (Exception e ) {
			System.out.println( "Clonage du chemin échoué" );
		}
		
		
		return result ;
	}
	
}
