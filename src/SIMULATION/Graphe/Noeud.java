package SIMULATION.Graphe;

import java.util.HashMap;

// CLASS DONE

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

	public boolean supprimmer( Arete<E> arete ) {
		boolean suppression_effectuee = this.aretes.containsValue( arete ) ;
		this.aretes.remove(arete) ;
		return suppression_effectuee ;
	}
	
	public HashMap<E,Arete<E>> getAretes() {
		return this.aretes ;
	}
	
}
