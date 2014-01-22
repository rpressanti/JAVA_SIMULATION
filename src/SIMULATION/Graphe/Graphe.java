package SIMULATION.Graphe;

import java.util.ArrayList;
import java.util.HashMap;

public class Graphe<E> {

	protected HashMap<E,Noeud<E>> noeuds ;
	
	public Graphe() {
		this.noeuds = new HashMap<E,Noeud<E>> () ;
	}

	public Noeud<E> add( E content ) {
		Noeud<E> node = new Noeud<E>( content ) ;
		this.noeuds.put( content , node ) ;
		return node ;
	}
	
	public boolean add( Noeud<E> node ) {
		this.noeuds.put( node.getContent() , node ) ;
		return true ;
	}
	
	
	public boolean add( Arete<E> arete ) {
		
		Noeud<E> origine     = arete.getOrigine();
		Noeud<E> destination = arete.getDestination();
		
		if ( !( this.noeuds.containsValue( origine ) && this.noeuds.containsValue( destination ) ) )
			return false ;
	
		this.noeuds.get( origine ).enregistrer( arete ) ;
	
		return true ;
	}
	

	
	ArrayList<Arete<E>> djikstra() {

		return null ;
	}

	
}
