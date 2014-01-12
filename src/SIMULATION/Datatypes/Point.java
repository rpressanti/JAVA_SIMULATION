package SIMULATION.Datatypes;



public class Point {

	protected double x ;
	protected double y ;
	
	
	
	
	Point( ) {
		this.x = this.y = 0 ;
	}
	
	
	
	Point( double x , double y ) {
		this.x = x ;
		this.y = y ;
	}

	
	
	
	public boolean update( double x , double y) {
		
		boolean modifie = false ;
		
		if( this.x != x ) {
			this.x = x ;
			modifie = true ;
		}

		if( this.y != y ) {
			this.y = y ;
			modifie = true ;
		}
		
		return modifie ;
	}
	
	public double getX() {
		return this.x ;
	}
	
	public double getY() {
		return this.y ;
	}
	
	
	public double distanceTo( Point p ) {
		return  Math.sqrt( 
					Math.pow( this.getX() - p.getX() , 2 ) 
				+	Math.pow( this.getY() - p.getY() , 2 )
				)  ;
	}
}
