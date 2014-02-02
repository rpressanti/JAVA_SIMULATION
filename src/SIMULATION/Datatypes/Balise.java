package SIMULATION.Datatypes;

// CLASS DONE

public class Balise extends Point implements Repere {

	private String indicatif ;
	
	public Balise( String indicatif , String coordonnees) {
		super( coordonnees ) ;
		//System.out.println( "Point créé" ) ;
		this.indicatif = indicatif ;
	}
	
	public String toString() {
		return this.getIndicatif() ;
	}
	
	
	public String getIndicatif() {
		return this.indicatif ;
	}

	public Balise clone() {
		return new Balise( this.getIndicatif() , super.toString() ) ;
	}
	
}
