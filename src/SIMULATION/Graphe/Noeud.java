package SIMULATION.Graphe;

import java.util.HashMap;


public class Noeud<A extends Arete<A,N,E> , N extends Noeud<A,N,E> , E> {

	@SuppressWarnings("unused")
	private Class<A> classeArete ;
	private Class<N> classeNoeud ;
	private Class<E> classeElement ;
	
	protected E content ;
	protected HashMap<E,A> aretes ;
	protected HashMap<E,A> aretes_inverses ;
	
	public Noeud( Class<A> classeArete , Class<N> classeNoeud , Class<E> classeElement, E content) {
		this.content = content ;
		this.aretes = new HashMap<E,A>() ;
		this.aretes_inverses = new HashMap<E,A>() ;
		
		this.classeArete = classeArete ;
		this.classeNoeud = classeNoeud ;
		this.classeElement = classeElement ;
	}
	
	public String toString() {
		String string = "Noeud:" + this.getContent().toString() + "\n";

		string += "Aretes partantes:" + "\n" ;
		for( A arete : this.getAretes().values() )
			string += "\t" + arete.toString() + "\n";

		string += "Aretes arrivantes:" + "\n" ;
		for( A arete : this.getAretesInverses().values() )
			string += "\t" + arete.toString() + "\n";

		return string ;
	}
	
	
	public E getContent() {
		return this.content ;
	}
	
	public boolean equals( Noeud<A,N,E> other_node) {
		return this.getContent().equals( other_node.getContent() ) ;
	}
	

	public void enregistrer( A arete ) {
		this.aretes.put( arete.getDestination().getContent() , arete ) ;
	}

	public void enregistrer_inverse( A arete ) {
		this.aretes_inverses.put( arete.getOrigine().getContent() , arete ) ;
	}
	
	public boolean supprimmer( Arete<A,N,E> arete ) {
		boolean contient = this.aretes.containsValue( arete ) ;
		
		if ( contient )
			this.aretes.remove(arete) ;
		
		return contient ;
	}
	
	public boolean supprimmer_inverse( A arete ) {
		boolean suppression_effectuee = this.aretes_inverses.containsValue( arete ) ;
		this.aretes_inverses.remove(arete) ;
		return suppression_effectuee ;
	}
	
	
	public HashMap<E,A> getAretes() {
		return this.aretes ;
	}
	
	public HashMap<E,A> getAretesInverses() {
		return this.aretes_inverses ;
	}

	
	public N clone() {
		try {
		return this.classeNoeud.getDeclaredConstructor( new Class[] { this.classeElement} ).newInstance( this.content) ;
		} catch( Exception e) {
			return null ;
		}
	}
}
