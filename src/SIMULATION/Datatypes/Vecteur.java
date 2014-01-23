package SIMULATION.Datatypes;


public class Vecteur extends Point{

	private double module ;
	private double argument ;
	
	// TODO
	public Vecteur( Point a , Point b)
	{
		super( b.getLatitude().getValue() - a.getLatitude().getValue() , 
				b.getLongitude().getValue() - a.getLongitude().getValue() ) ;
	}
	
	// TODO
	public void setModule( double module) {
		
	}
	
	public double getModule() {
		return this.module ;
	}
	
	public double getArgument() {
		return this.argument ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}