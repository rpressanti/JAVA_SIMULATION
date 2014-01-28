package SIMULATION.Graphe;

// CLASS DONE

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Graphe<E> {

	protected HashMap<E,Noeud<E>> noeuds ;
	
	private class DestinationFirst implements Comparator<Chemin<E>> {
		
		private Noeud<E> destination ;
		
		public DestinationFirst( Noeud<E> destination ) {
			this.destination = destination ;
		}

		@Override
		public int compare(Chemin<E> chemin_1, Chemin<E> chemin_2) {
			
			if( chemin_1 == chemin_2 )
				return 0 ;
			
			if( chemin_1.getLength() < chemin_2.getLength() )
				return -1 ;
			else if ( chemin_1.getLength() > chemin_2.getLength() )
				return 1 ;
			else if ( chemin_1.last() == this.destination )
				return -1 ;
			else if ( chemin_2.last() == this.destination )
				return 1 ;
			else
				return 0 ;
			}
		
	}
	
	
	
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
	
	
	
	
	
	// DONE
	public Chemins<E> djikstra( Noeud<E> origine , Noeud<E> destination) {

		Chemins<E> plus_courts = new Chemins<E>() ;
		HashMap<Noeud<E>,Double> distance_connue = new HashMap<Noeud<E>,Double>() ;
		
		PriorityQueue<Chemin<E>> a_traiter = new PriorityQueue<Chemin<E>>( 1 , new DestinationFirst( destination ) ) ;
		// Initialisation de la Queue de priorite
		Chemin<E> chemin_trivial = new Chemin<E>() ;
		chemin_trivial.add( new Arete<E>( origine , origine , 0) ) ;
		a_traiter.add( chemin_trivial ) ;

		Chemin<E> chemin_courant = null ;
		
		while ( ! a_traiter.isEmpty() )
		{	
			// On commence par r�cup�rer ceux allant � la destination parmi les sous-chemins les plus courts
			while( (chemin_courant = a_traiter.poll()).last() != destination )
			{	
				chemin_courant.untrivial();
				plus_courts.add( chemin_courant ) ;
			}
			// S'il y en a, le traitement est fini
			if( ! plus_courts.isEmpty() )
				return plus_courts ;

			distance_connue.put( chemin_courant.last() , chemin_courant.getLength() ) ;
			
			// Sinon, on itere
			for( Chemin<E> nouveau : chemin_courant.successeurs() )
				if( ! distance_connue.containsKey( nouveau.last() ) )
					a_traiter.add( nouveau ) ;		
		} 
		//while ( ! a_traiter.isEmpty() ) ; 
		
		return plus_courts ;
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
