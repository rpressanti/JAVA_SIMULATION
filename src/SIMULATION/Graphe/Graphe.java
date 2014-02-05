package SIMULATION.Graphe;

// CLASS DONE

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Graphe<A extends Arete<A,N,E>,N extends Noeud<A,N,E>, E> {

	protected HashMap<E,N> noeuds ;
	
	private class DestinationFirst implements Comparator<Chemin<A,N,E>> {
		
		private N destination ;
		
		public DestinationFirst( N destination ) {
			this.destination = destination ;
		}

		@Override
		public int compare(Chemin<A,N,E> chemin_1, Chemin<A,N,E> chemin_2) {
			
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
		this.noeuds = new HashMap<E,N> () ;
	}

	
	public String toString() {
		String string = "Graphe:" + "\n" ;
	
		for( N noeud : this.noeuds.values() )
			string += noeud.toString() ;
			
		return string ;
	}
	
	
	
	
	// DONE
	public N add( E content ) {
		@SuppressWarnings("unchecked")
		N node = (N) new Noeud<A,N,E>( content ) ;
		this.noeuds.put( content , node ) ;
		return node ;
	}
	
	// DONE
	public boolean add( N node ) {
		this.noeuds.put( node.getContent() , node ) ;
		return true ;
	}
	
	// DONE
	public boolean add( A arete ) {
		
		N origine     = (N) arete.getOrigine();
		N destination = (N) arete.getDestination();
		
		if ( !( this.noeuds.containsValue( origine ) && this.noeuds.containsValue( destination ) ) )
			return false ;
	
		this.noeuds.get( origine.getContent() ).enregistrer( arete ) ;
		this.noeuds.get( destination.getContent() ).enregistrer_inverse( arete ) ;
		
		
		return true ;
	}
	
	
	
	// DONE
	@SuppressWarnings("unchecked")
	public boolean add( E origine , E destination , double weight ) {
		
		boolean result = true ;
		
		N noeud_origine = this.noeuds.get( origine ) ;
		N noeud_destination = this.noeuds.get( destination ) ;
		
		if( noeud_origine != null && noeud_destination != null )
			this.add( (A) new Arete<A,N,E>( noeud_origine , noeud_destination , weight ) ) ;
		else
			result = false ;
		
		return result ;
	}
	
	
	// DONE
	public N getNoeud( E content) {
		if ( this.noeuds.containsKey( content ) )
			return this.noeuds.get( content );
		else	
			return null ;
	}
	
	
	
	// DONE
	@SuppressWarnings("unchecked")
	public Chemins<A,N,E> djikstra( N origine , N destination) {

		Chemins<A,N,E> plus_courts = new Chemins<A,N,E>() ;
		HashMap<N,Double> distance_connue = new HashMap<N,Double>() ;
		
		PriorityQueue<Chemin<A,N,E>> a_traiter = new PriorityQueue<Chemin<A,N,E>>( 1 , new DestinationFirst( destination ) ) ;
		// Initialisation de la Queue de priorite
		Chemin<A,N,E> chemin_trivial = new Chemin<A,N,E>() ;
		chemin_trivial.add( (A) new Arete<A,N,E>( origine , origine , 0) ) ;
		a_traiter.add( chemin_trivial ) ;

		Chemin<A,N,E> chemin_courant = new Chemin<A,N,E>() ;
		
		//System.out.println( "Debut algo" ) ;
		
		while ( ! a_traiter.isEmpty() )
		{	
			//System.out.println( "Entr�e dans la boucle" ) ;
			
			// On commence par r�cup�rer ceux allant � la destination parmi les sous-chemins les plus courts
			while( (! a_traiter.isEmpty() ) && (chemin_courant = a_traiter.poll()).last().equals( destination ) )
			{	
				//System.out.println( "plus court trouv�");
				if ( chemin_courant.isTrivial() )
					chemin_courant.untrivial();
				plus_courts.add( chemin_courant ) ;
			}
			// S'il y en a, le traitement est fini
			if( ! plus_courts.isEmpty() )
				return plus_courts ;
	
			distance_connue.put( chemin_courant.last() , chemin_courant.getLength() ) ;
			//System.out.println( "Last" + chemin_courant.last().getContent() ) ;
			
			// Sinon, on itere
			for( Chemin<A,N,E> nouveau : chemin_courant.successeurs() )
				if( ! distance_connue.containsKey( nouveau.last() ) )
					a_traiter.add( nouveau ) ;		

		} 
		
		return plus_courts ;
	}

	
	// DONE
	public boolean filtrer( double distance ) {
		
		boolean suppression_realisee = false ;
		
		for( N noeud : this.noeuds.values() )
			for( A arete : noeud.getAretes().values() )
				if ( arete.getWeight() > distance )
					suppression_realisee |= noeud.supprimmer(arete);
		
		return suppression_realisee ;
	}
	
	// DONE
	public Graphe<A,N,E> clone() {
		
		Graphe<A,N,E> result = new Graphe<A,N,E>() ;
		
		// Copier les noueds
		for( N noeud : this.noeuds.values() )
			result.add( noeud ) ;

		// Recreer les aretes
		for( N noeud : this.noeuds.values() )
			for( A arete : noeud.getAretes().values() )
				result.add( arete.getOrigine().getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
		
		return result ;
	}
	
	

	
	
 	
	
	
}
