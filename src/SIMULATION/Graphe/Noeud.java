package SIMULATION.Graphe;

import java.util.HashMap;


public class Noeud<E> {

	private E content ;
	private HashMap<E,Arete<E>> aretes ;
	private HashMap<E,Arete<E>> aretes_inverses ;
	
	public Noeud( E content) {
		this.content = content ;
		this.aretes = new HashMap<E,Arete<E>>() ;
	}
	
	public E getContent() {
		return this.content ;
	}
	
	public boolean equals( Noeud<E> other_node) {
		return this.getContent().equals( other_node.getContent() ) ;
	}
	
	public void enregistrer( Arete<E> arete ) {
		this.aretes.put( arete.getDestination().getContent() , arete ) ;
	}

	public void enregistrer_inverse( Arete<E> arete ) {
		this.aretes.put( arete.getOrigine().getContent() , arete ) ;
	}
	
	public boolean supprimmer( Arete<E> arete ) {
		boolean suppression_effectuee = this.aretes.containsValue( arete ) ;
		this.aretes.remove(arete) ;
		return suppression_effectuee ;
	}
	
	public boolean supprimmer_inverse( Arete<E> arete ) {
		boolean suppression_effectuee = this.aretes_inverses.containsValue( arete ) ;
		this.aretes_inverses.remove(arete) ;
		return suppression_effectuee ;
	}
	
	
	public HashMap<E,Arete<E>> getAretes() {
		return this.aretes ;
	}
	
	public HashMap<E,Arete<E>> getAretesInverses() {
		return this.aretes_inverses ;
	}
	
	public Noeud<E> clone() {
		return new Noeud<E>( this.getContent() ) ;
	}
}
