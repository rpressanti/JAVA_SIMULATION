package SIMULATION.Datatypes;

public class Plot extends Point {

	private int order ;
	
	public Plot( double latitude , double longitude , int order) {
		super( latitude ,longitude ) ;
		this.order = order ;
	}
	
	
	public int getOrder() {
		return this.order ;
	}
	
}
