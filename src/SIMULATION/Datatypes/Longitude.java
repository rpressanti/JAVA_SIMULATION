package SIMULATION.Datatypes;

// CLASS DONE

public class Longitude extends Coordonnees{

	static public enum LONGITUDE { EST , OUEST } ;
	
	// Utilisé pour les balises
	public Longitude(double value) {
		super(value);
	}
	
	// Utilisé pour les aérodromes
	public Longitude( String value )
	{
		super( value , Coordonnees.TYPE.LONGITUDE );
	}

	public Longitude( int degre , int minute , int sec , int dix_sec , int cent_sec , String direction )
	{
		super( degre , minute , sec , dix_sec , cent_sec , direction ) ;		
	}
	
	public Longitude.LONGITUDE getDirection() {
		if ( this.estPositif() )
			return Longitude.LONGITUDE.EST ;
		else
			return Longitude.LONGITUDE.OUEST ;
	}
	
	public String toString()
	{
		String str = super.toString()+" ";
		
		//Longitude.LONGITUDE direction = getDirection()  ;
		//System.out.println( direction ) ;
		
		switch( this.getDirection() ) 
		{
			case EST :
				//System.out.println(":EST:");
				str += "E" ;
				break ;
		
			case OUEST :
				//System.out.println(":OUEST:");
				str += "W" ;
				break ;
		}
		
		return str ;
	}
	
	public static void main( String args[] )
	{
		String s = "48°59'11,2\" E";
		Longitude l1=new Longitude(s);
		//System.out.println( "Direction : " + l.getDirection() ) ;
		System.out.println( l1);
		double a= Longitude.sexagedecimalToDecimal(s, Coordonnees.TYPE.LONGITUDE);
		System.out.println( a );
		System.out.println( decimalToRadian(a));
		l1.add(0.5);
		System.out.println( l1);
		System.out.println( l1.getValue() ) ;
		System.out.println( l1.toRadians() ) ;
	}
}