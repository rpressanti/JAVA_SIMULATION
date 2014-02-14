package SIMULATION.Datatypes;

// CLASS DONE

public class Aerodrome extends Point implements Repere{

	private static Integer effectif_codes_artificiels = 0 ;	
	
	private String nom ;
	private String code_OACI ;
	
	public Aerodrome( String nom , String code_OACI , double latitude , double longitude ) {
		super( latitude , longitude ) ;
		this.nom = nom ; 
		//System.out.println( "LEN:" + code_OACI.length() );
		if ( code_OACI != "")
			this.code_OACI = code_OACI ;
			else
		this.code_OACI = "LF" + ( ++Aerodrome.effectif_codes_artificiels ).toString() ;	
		
		//System.out.println( "Nom:" + nom );
		//System.out.println( "Code OACI:" + this.code_OACI + "|" + code_OACI);
		
	}

	
	public String toString() {
		return this.get_nom() ;
	}
	
	
	
	public String get_code_OACI() {
		return this.code_OACI ;
	}

	public String get_nom() {
		return this.nom ;
	}
	
	public Aerodrome clone() {
		return new Aerodrome( this.nom , this.get_code_OACI() , this.getLatitude().getValue() , this.getLongitude().getValue() ) ;		
	}
	
}
