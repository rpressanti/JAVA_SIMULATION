package SIMULATION.Datatypes;

// CLASS DONE

public class Latitude extends Coordonnees {

static public enum LATITUDE { NORD , SUD } ;
	
	// Utilisé pour les balises
	public Latitude(double value) {
		super(value);
	}

	public Latitude( int degre , int minute , int sec , int dix_sec , int cent_sec , String direction )
	{
		super( degre , minute , sec , dix_sec , cent_sec , direction ) ;		
	}
	// Utilisé pour les aérodromes
	public Latitude( String value )
	{
		super( value , Coordonnees.TYPE.LATITUDE );
	}

	public Latitude.LATITUDE getDirection() {
		if ( this.estPositif() )
			return Latitude.LATITUDE.NORD ;
		else
			return Latitude.LATITUDE.SUD ;
	}
	
	public String toString()
	{
		String str = super.toString()+" ";
		
		switch( this.getDirection() ) 
		{
			case NORD :
				str += "N" ;
				break ;
		
			case SUD :
				str += "S" ;
				break ;
		}
		
		return str ;
	}
		
	public static void main( String args[] )
	{
		String s = "4°56'24,3\" N";
		Latitude l=new Latitude(s);
		//System.out.println( "Direction : " + l.getDirection() ) ;
		System.out.println( l);
		double a= Latitude.sexagedecimalToDecimal(s, Coordonnees.TYPE.LATITUDE);
		System.out.println( a );
		System.out.println(decimalToRadian(a));
		l.add(0.5);
		System.out.println( l);
		System.out.println( l.getValue() ) ;
		System.out.println( l.toRadians() ) ;
	}
	
	
}