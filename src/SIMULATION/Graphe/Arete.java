package SIMULATION.Graphe;

//CLASS DONE

public class Arete<N extends Noeud<?,N,E> , E> {
	
	private N origine ;
	private N destination ;
	private Double weight ;
	
	
	public Arete ( N origine , N destination , double weight ) {
		
		this.origine = origine ;
		this.destination = destination ;
		this.weight = weight ;
		
		// TODO CORRECT TYPE
		this.origine.enregistrer( this );
		this.destination.enregistrer_inverse( this );
		
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
