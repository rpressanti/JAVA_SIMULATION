package SIMULATION.Graphe;


//CLASS DONE

public abstract class Arete<A extends Arete<A,N,E> , N extends Noeud<A,N,E> , E> {
	
	protected Class<A> classeArete ;
	protected Class<N> classeNoeud ;
	protected Class<E> classeElement ;
 	
	protected N origine ;
	protected N destination ;
	protected Double weight ;
	
		
	@SuppressWarnings("unchecked")
	public Arete ( Class<A> classeArete , Class<N> classeNoeud , Class<E> classeElement , N origine , N destination , Double weight ) {
		
		this.classeArete = classeArete ;
		this.classeNoeud = classeNoeud ;
		this.classeElement = classeElement ;
		
		this.origine = origine ;
		this.destination = destination ;
		this.weight = weight ;
		
		this.origine.enregistrer( (A) this );
		this.destination.enregistrer_inverse( (A) this );
			
	}
	
	
	public String toString() {
		return this.getWeight().toString() + " : " + this.getOrigine().getContent().toString() + " => " + this.getDestination().getContent().toString() ;
	}
	
	public N getOrigine() {
		return this.origine ;
	}
	
	public N getDestination() {
		return this.destination ;
	}

	public Double getWeight() {
		return this.weight ;
	}

	public boolean isTrivial() {
		return this.getOrigine() == this.getDestination() ;
	}



}
