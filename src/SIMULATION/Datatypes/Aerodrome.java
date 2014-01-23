package SIMULATION.Datatypes;

// CLASS DONE

public class Aerodrome extends Point implements Repere{

	private String code_OACI ;
	
	public Aerodrome( String code_OACI , double latitude , double longitude ) {
		super( latitude , longitude ) ;
		this.code_OACI = code_OACI ;
	}
	
	public String get_code_OACI() {
		return this.code_OACI ;
	}

	public Aerodrome clone() {
		return new Aerodrome( this.get_code_OACI() , this.getLatitude().getValue() , this.getLongitude().getValue() ) ;		
	}
	
}
