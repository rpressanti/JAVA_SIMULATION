package SIMULATION.Datatypes;

public class Aerodrome extends Point implements Repere{

	private String code_OACI ;
	
	public Aerodrome( String code_OACI , double longitude , double latitude ) {
		super( longitude , latitude ) ;
		this.code_OACI = code_OACI ;
	}
	
	public String get_code_OACI() {
		return this.code_OACI ;
	}
	
}
