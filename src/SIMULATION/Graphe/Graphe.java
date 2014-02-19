package SIMULATION.Graphe;

// CLASS DONE

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Graphe<C extends Chemin<C,A,N,E> , A extends Arete<A,N,E>,N extends Noeud<A,N,E>, E> {

	protected Class<C> classeChemin ;
	protected Class<A> classeArete ;
	protected Class<N> classeNoeud ;
	protected Class<E> classeElement ;
	
	
	protected HashMap<E,N> noeuds ;
	
	protected class DestinationFirst implements Comparator<Chemin<C,A,N,E>> {
		
		private N destination ;
		
		public DestinationFirst( N destination ) {
			this.destination = destination ;
		}

		@Override
		public int compare(Chemin<C,A,N,E> chemin_1, Chemin<C,A,N,E> chemin_2) {
			
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
	public Graphe( Class<C> classeChemin , Class<A> classeArete , Class<N> classeNoeud , Class<E> classeElement) {
		this.noeuds = new HashMap<E,N> () ;

		this.classeChemin = classeChemin ;
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
			System.err.println( "Noaud non cr��: " + content );
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
				System.err.println( "Arete non cr��e" ) ;
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
	public Chemins<C,A,N,E> djikstra( N origine , N destination) {

		HashMap<N,Double> distance_connue = new HashMap<N,Double>() ;
		HashMap<N,Double> distance_provisoire = new HashMap<N,Double>() ;
		Chemins<C,A,N,E> plus_courts = new Chemins<C,A,N,E>( this.classeChemin) ;
		
		//Chemin<C,A,N,E> chemin_trivial = new Chemin<C,A,N,E>( this.classeChemin ) ;
		C chemin_trivial = null ;
		C chemin_courant = null ;
		// Initialisation de la Queue de priorite
		PriorityQueue<C> a_traiter = new PriorityQueue<C>( 1 , new DestinationFirst( destination ) ) ;
		
		
		try {
			
			chemin_trivial = this.classeChemin.newInstance() ;
			System.out.println( "Création chemin trivial" );
			A arete_triviale = this.classeArete.getDeclaredConstructor(
						new Class[] { this.classeNoeud , this.classeNoeud , Double.class }
					).newInstance( origine , origine , 0.0 ) ;
			chemin_trivial.add( arete_triviale ) ;
			chemin_courant = this.classeChemin.newInstance() ; 
		
		} catch( Exception e) {
			System.err.println( "Arete triviale non créée" ) ;
			e.printStackTrace();
		}
		
		
		
		a_traiter.add( chemin_trivial ) ;

		
		
		System.out.println( "Debut algo" ) ;
		
		while ( ! a_traiter.isEmpty() )
		{	
			//System.out.println( "Entrée dans la boucle|A_triater:" + a_traiter.size() + "|Connue:" + distance_connue.size() ) ;
			
			// On commence par récupérer ceux allant à la destination parmi les sous-chemins les plus courts
			while( (! a_traiter.isEmpty() ) && (chemin_courant = a_traiter.remove()).last().getContent() == destination.getContent() )
			{	
				System.out.println( "plus court trouvé" ) ;
				System.out.println( chemin_courant );
				
				if ( ! chemin_courant.isTrivial() )
					chemin_courant.untrivial();
				
				plus_courts.add( chemin_courant ) ;
			}
			
			//System.out.println( chemin_courant.last().getContent() ) ;
			
			// S'il y en a, le traitement est fini
			if( ! plus_courts.isEmpty() )
				return plus_courts ;
	
			// Sinon, on itere
			distance_connue.put( chemin_courant.last() , chemin_courant.getLength() ) ;				
			
			
			
			if( ! distance_provisoire.containsKey( chemin_courant.last() )
					|| 
				distance_provisoire.get( chemin_courant.last() ) >= chemin_courant.getLength() 
					)
			{
				distance_provisoire.put( chemin_courant.last() , chemin_courant.getLength() ) ;				
				//System.out.println( "Mise à jour distance provisoire" );
				//System.out.println( "Last" + chemin_courant.last().getContent() ) ;
 
				for( C nouveau : chemin_courant.successeurs() )
					if( ! distance_connue.containsKey( nouveau.last() ) )
					{	
						a_traiter.add( nouveau ) ;
						distance_provisoire.put( nouveau.last() , nouveau.getLength() ) ;				
						//System.out.println( "Ajout de:" + nouveau.last().getContent() );
					} else {
						//System.out.println( "Element deja présent" + nouveau.last().getContent() );
					}
			}
			
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
	

	
	

	
	
 	
	
	
}
