package SIMULATION.Datatypes;

// CLASS DONE

public class Plot extends Point {

	
	private final int order ;
	
	public Plot( double latitude , double longitude , int order) {
		super( latitude ,longitude ) ;
		this.order = order ;
	}
	
	public Plot( Point point ) {
		super( point.getLongitude().getValue() , point.getLatitude().getValue() ) ;
		this.order = 0 ;
	}
	
	
	public Plot( Point point , int order ) {
		super( point.getLongitude().getValue() , point.getLatitude().getValue() ) ;
		this.order = order ;
	}
	
	
	public int getOrder() {
		return this.order ;
	}
	
	
	public Plot suivant( Vecteur vecteur_vitesse ) {
		
		Point tmp_point = ( (Point) this ).clone() ;
		tmp_point.deplacerDe( vecteur_vitesse) ;
		return new Plot( tmp_point , this.order + 1 ) ;
		
	}
	
	
	
}
