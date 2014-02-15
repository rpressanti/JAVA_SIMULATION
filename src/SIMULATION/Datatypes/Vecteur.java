package SIMULATION.Datatypes;

// CLASS DONE

public class Vecteur extends Point{

	private double module ;
	private double argument ;
	
	
	//  DONE
	public Vecteur( Point a , Point b)
	{	
		super(  b.getLongitude().getValue() - a.getLongitude().getValue() ,
				b.getLatitude().getValue() - a.getLatitude().getValue()
				) ;
		this.module = a.distanceTo( b );
		
		double dep_lat = b.getLatitude().getValue() - a.getLatitude().getValue();
		double dep_long = b.getLongitude().getValue() - a.getLongitude().getValue() ;
		
		if( dep_long != 0)
			this.argument = Coordonnees.radianToDecimal( Math.atan( dep_lat / dep_long ) ) ;
		else 
			this.argument = 90 ;
		
		//System.out.println( "Avant" + this.argument ) ;
		
		if( dep_long < 0)
			this.argument += 180 ;
		
		if( this.argument > 180 )
			this.argument -= 360 ;
	
		//System.out.println( "Apres " + this.argument ) ;
	}	
	
	public Vecteur( Point p ) {	
		this( Point.ORIGINE , p ) ;
	}
	
	
	
	// DONE
	public Vecteur( double module , double argument )
	{
		super( module * Math.cos( Coordonnees.decimalToRadian( argument) ), 
				module * Math.sin( Coordonnees.decimalToRadian( argument ) ) ) ;
		setModule( module );
		setArgument( argument );
	}
	
	public void setModule( double new_module ) {

		if( this.module != 0) 
			this.multiply( new_module / this.module ) ;
			
	}
	
	// DONE
	public void setArgument( double arg) {
		this.argument=arg;
		this.latitude =new Latitude( 
				this.latitude.getValue() * Math.cos( Coordonnees.decimalToRadian( this.argument ) ) ) ;
			
		this.longitude =new Longitude( this.longitude.getValue()
				* Math.cos( Coordonnees.decimalToRadian( this.argument ) ) ) ;
	}
	
	public double getModule() {
		return this.module ;
	}
	
	public double getArgument() {
		return this.argument ;
	}
	
	public double multiply(double mult)
	{
		this.module *= mult;
		this.latitude.multiply(  mult ) ;
		this.longitude.multiply( mult ) ;
		
		return this.module;
	}
	
	public String toString() {
		return "Vecteur de coordonnees cartesienne :\n" 
				+ super.toString() + "\n"
				+ "module : " + this.module
				+ "\t argument : " +this.argument 
				+ "\n" ;
	}
	
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		Point p1=new Point("4°56'24.0\" N 4°56'24.0\" E") ;
		@SuppressWarnings("unused")
		Point p2= new Point(-4.42194 , 48.44722); //Brest
		@SuppressWarnings("unused")
		Point p3=new Point(-1.73222, 48.07194); //Rennes
		Point p4=new Point(3.08694, 50.56361); //Lille
		Point p5=new Point(1.36778, 43.63528); //Toulouse
		@SuppressWarnings("unused")
		Point p6=new Point(1.37833, 44.02750); //Montauban
		Vecteur v1= new Vecteur(p4,p5);
		
		double d1=p5.distanceTo(p4);
		System.out.println( v1 + "Distance en NM : " + d1 + "\n" ) ;
		
		System.out.println(d1*1.852+" distance entre Brest et Rennes en km");
		
		System.out.println( "===============" );
		
		System.out.println( p4 ) ;
		Vecteur v2 = new Vecteur( p4 ) ;
		System.out.println( v2 ) ;

	}

}