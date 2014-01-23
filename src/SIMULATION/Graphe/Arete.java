package SIMULATION.Graphe;

//CLASS DONE

public class Arete<E> {
	
	private Noeud<E> origine ;
	private Noeud<E> destination ;
	private double weight ;
	
	
	public Arete ( Noeud<E> origine , Noeud<E> destination , double weight ) {
		
		this.origine = origine ;
		this.destination = destination ;
		this.weight = weight ;
		
		this.origine.enregistrer( this );
		this.destination.enregistrer_inverse( this );
		
	}
	
	public Noeud<E> getOrigine() {
		return this.origine ;
	}
	
	public Noeud<E> getDestination() {
		return this.destination ;
	}

	public double getWeight() {
		return this.weight ;
	}

}
