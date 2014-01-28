package SIMULATION.Datatypes;

// CLASS DONE

public class Plot extends Point {

	
	private final int order ;
	
	public Plot( double latitude , double longitude , int order) {
		super( latitude ,longitude ) ;
		this.order = order ;
	}
	
	
	public int getOrder() {
		return this.order ;
	}
	
}
