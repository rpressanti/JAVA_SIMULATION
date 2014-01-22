package SIMULATION.Graphe;

import java.util.HashMap;

public class Noeud<E> {

	private E content ;
	private HashMap<E,Arete<E>> aretes ;
	
	public Noeud( E content) {
		this.content = content ;
		this.aretes = new HashMap<E,Arete<E>>() ;
	}
	
	public E getContent() {
		return this.content ;
	}
	
	public void enregistrer( Arete<E> arete ) {
		this.aretes.put( arete.getDestination().getContent() , arete ) ;
	}

	public void supprimmer( Arete<E> arete ) {
		this.aretes.remove(arete) ;
	}
	
	public HashMap<E,Arete<E>> getAretes() {
		return this.aretes ;
	}
	
}
