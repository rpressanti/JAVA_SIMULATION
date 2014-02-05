package SIMULATION.Graphe;

//CLASS DONE

public class Arete<A extends Arete<A,N,E> , N extends Noeud<A,N,E> , E> {
	
	protected N origine ;
	protected N destination ;
	protected Double weight ;
	
	
	@SuppressWarnings("unchecked")
	public Arete ( N origine , N destination , double weight ) {
		
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
