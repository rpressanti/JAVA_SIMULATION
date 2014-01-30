package SIMULATION.Datatypes;

// CLASS DONE

public class Aerodrome extends Point implements Repere{

	private String nom ;
	private String code_OACI ;
	
	public Aerodrome( String nom , String code_OACI , double latitude , double longitude ) {
		super( latitude , longitude ) ;
		this.nom = nom ; 
		this.code_OACI = code_OACI ;
	}
	
	public String get_code_OACI() {
		return this.code_OACI ;
	}

	public String get_nom() {
		return this.code_OACI ;
	}
	
	public Aerodrome clone() {
		return new Aerodrome( this.nom , this.get_code_OACI() , this.getLatitude().getValue() , this.getLongitude().getValue() ) ;		
	}
	
}
