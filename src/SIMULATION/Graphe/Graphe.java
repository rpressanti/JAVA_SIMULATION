package SIMULATION.Graphe;

import java.util.HashMap;

public class Graphe<E> {

	protected HashMap<E,Noeud<E>> noeuds ;
	
	// DONE
	public Graphe() {
		this.noeuds = new HashMap<E,Noeud<E>> () ;
	}

	
	// DONE
	public Noeud<E> add( E content ) {
		Noeud<E> node = new Noeud<E>( content ) ;
		this.noeuds.put( content , node ) ;
		return node ;
	}
	
	// DONE
	public boolean add( Noeud<E> node ) {
		this.noeuds.put( node.getContent() , node ) ;
		return true ;
	}
	
	// DONE
	public boolean add( Arete<E> arete ) {
		
		Noeud<E> origine     = arete.getOrigine();
		Noeud<E> destination = arete.getDestination();
		
		if ( !( this.noeuds.containsValue( origine ) && this.noeuds.containsValue( destination ) ) )
			return false ;
	
		this.noeuds.get( origine ).enregistrer( arete ) ;
	
		return true ;
	}
	
	
	
	// DONE
	public boolean add( E origine , E destination , double weight ) {
		
		boolean result = true ;
		
		Noeud<E> noeud_origine = this.noeuds.get( origine ) ;
		Noeud<E> noeud_destination = this.noeuds.get( destination ) ;
		
		if( noeud_origine != null && noeud_destination != null )
			this.add( new Arete<E>( noeud_origine , noeud_destination , weight ) ) ;
		else
			result = false ;
		
		return result ;
	}
	
	
	// DONE
	public Noeud<E> getNoeud( E content) {
		if ( this.noeuds.containsKey( content ) )
			return this.noeuds.get( content );
		else	
			return null ;
	}
	
	// TODO
	public Chemins<E> djikstra() {

		return null ;
	}

	
	// DONE
	public boolean filtrer( double distance ) {
		
		boolean suppression_realisee = false ;
		
		for( Noeud<E> noeud : this.noeuds.values() )
			for( Arete<E> arete : noeud.getAretes().values() )
				if ( arete.getWeight() > distance )
					suppression_realisee |= noeud.supprimmer(arete);
		
		return suppression_realisee ;
	}
	
	// DONE
	public Graphe<E> clone() {
		
		Graphe<E> result = new Graphe<E>() ;
		
		// Copier les noueds
		for( Noeud<E> noeud : this.noeuds.values() )
			result.add( noeud ) ;

		// Recreer les aretes
		for( Noeud<E> noeud : this.noeuds.values() )
			for( Arete<E> arete : noeud.getAretes().values() )
				result.add( arete.getOrigine().getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
		
		return result ;
	}
}
