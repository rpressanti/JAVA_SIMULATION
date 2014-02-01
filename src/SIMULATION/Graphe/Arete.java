package SIMULATION.Graphe;

//CLASS DONE

public class Arete<E> {
	
	private Noeud<E> origine ;
	private Noeud<E> destination ;
	private Double weight ;
	
	
	public Arete ( Noeud<E> origine , Noeud<E> destination , double weight ) {
		
		this.origine = origine ;
		this.destination = destination ;
		this.weight = weight ;
		
		this.origine.enregistrer( this );
		this.destination.enregistrer_inverse( this );
		
	}
	
	public String toString() {
		return this.getWeight().toString() + " : " + this.getOrigine().getContent().toString() + " => " + this.getDestination().getContent().toString() ;
	}
	
	public Noeud<E> getOrigine() {
		return this.origine ;
	}
	
	public Noeud<E> getDestination() {
		return this.destination ;
	}

	public Double getWeight() {
		return this.weight ;
	}

	public boolean isTrivial() {
		return this.getOrigine() == this.getDestination() ;
	}
}
