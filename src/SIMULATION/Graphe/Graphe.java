package SIMULATION.Graphe;

// CLASS DONE

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Graphe<A extends Arete<A,N,E>,N extends Noeud<A,N,E>, E> {

	protected Class<A> classeArete ;
	protected Class<N> classeNoeud ;
	protected Class<E> classeElement ;
	
	
	protected HashMap<E,N> noeuds ;
	
	protected class DestinationFirst implements Comparator<Chemin<A,N,E>> {
		
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
	public Graphe( Class<A> classeArete , Class<N> classeNoeud , Class<E> classeElement) {
		this.noeuds = new HashMap<E,N> () ;

		this.classeArete = classeArete ;
		this.classeNoeud = classeNoeud ;
		this.classeElement = classeElement ;
	
	}

	
	public String toString() {
		String string = "Graphe:" + "\n" ;
	
		for( N noeud : this.noeuds.values() )
			string += noeud.toString() ;
			
		return string ;
	}
	
	
	
	
	// DONE
	public N add( E content ) {
		
		N node = null ;
		
		try {
			node = this.classeNoeud.getDeclaredConstructor( new Class [] { this.classeElement } ).newInstance( content ) ;
			this.noeuds.put( content , node ) ;
		}
		catch( Exception e) {
			System.err.println( "Noaud non crŽŽ: " + content );
		}
		
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
	public A add( E origine , E destination , double weight ) {
		
		N noeud_origine = this.noeuds.get( origine ) ;
		N noeud_destination = this.noeuds.get( destination ) ;
		
		A arete = null ;
		
		if( noeud_origine != null && noeud_destination != null )
			try {
				arete = this.classeArete.getDeclaredConstructor( 
							new Class[] { this.classeNoeud ,this.classeNoeud , Double.class } 
						).newInstance( noeud_origine , noeud_destination , weight ) ;
				this.add( arete ) ;
			}
			catch( Exception e) {
				System.err.println( "Arete non crŽŽe" ) ;
			}
		
		return arete ;
	}
	
	
	// DONE
	public N getNoeud( E content) {
		if ( this.noeuds.containsKey( content ) )
			return this.noeuds.get( content );
		else	
			return null ;
	}
	
	
	
	// DONE
	public Chemins<A,N,E> djikstra( N origine , N destination) {

		Chemins<A,N,E> plus_courts = new Chemins<A,N,E>() ;
		HashMap<N,Double> distance_connue = new HashMap<N,Double>() ;
		
		PriorityQueue<Chemin<A,N,E>> a_traiter = new PriorityQueue<Chemin<A,N,E>>( 1 , new DestinationFirst( destination ) ) ;
		// Initialisation de la Queue de priorite
		Chemin<A,N,E> chemin_trivial = new Chemin<A,N,E>() ;
		try {
			A arete_triviale = this.classeArete.getDeclaredConstructor(
						new Class[] { this.classeNoeud , this.classeNoeud , Double.class }
					).newInstance( origine , origine , 0.0 ) ;
			chemin_trivial.add( arete_triviale ) ;
		} catch( Exception e) {
			System.err.println( "Arete triviale non crŽŽe" ) ;
		}
		
		
		
		a_traiter.add( chemin_trivial ) ;

		Chemin<A,N,E> chemin_courant = new Chemin<A,N,E>() ;
		
		//System.out.println( "Debut algo" ) ;
		
		while ( ! a_traiter.isEmpty() )
		{	
			//System.out.println( "EntrŽe dans la boucle" ) ;
			
			// On commence par rï¿½cupï¿½rer ceux allant ï¿½ la destination parmi les sous-chemins les plus courts
			while( (! a_traiter.isEmpty() ) && (chemin_courant = a_traiter.poll()).last().equals( destination ) )
			{	
				//System.out.println( "plus court trouvŽ");
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
		
		Graphe<A,N,E> result = new Graphe<A,N,E>( this.classeArete , this.classeNoeud , this.classeElement ) ;
		
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
