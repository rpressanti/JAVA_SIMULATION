package SIMULATION.Datatypes;

import java.util.Scanner;
import java.util.regex.MatchResult;

public class Coordonnees {
	
	static public enum TYPE { LONGITUDE , LATITUDE } ;
	
	// Expressions rationnelles
	// Utilisées pour les aérodromes
	public static final String regexp_coord = "([0-9]{1,3})°([0-9]{1,2})'(?:([0-9]{1,2})(?:\\.([0-9])([0-9])?)?\")? " ;
	public static final String regexp_latitude = Coordonnees.regexp_coord + "([NS])" ;
	public static final String regexp_longitude =Coordonnees.regexp_coord + "([EW])" ;
			
	private double  value ;
	
	public Coordonnees( double value ) {
		this.value = value ;
	}
	
	public Coordonnees( int degre , int minute , int sec , int dix_sec , int cent_sec , String direction )
	{
		this.value = Coordonnees.sexagedecimalToDecimal( degre , minute , sec , 
											dix_sec , cent_sec , direction ) ;
	}
	
	public Coordonnees( String string , Coordonnees.TYPE type ) {
		this.value =  Coordonnees.sexagedecimalToDecimal( string , type ) ;
		//System.out.println( "Constructeur : " + this.value) ;
	}
		
	public String toString()
	{
		double tmp = Math.abs( value ) ;
		String signe = new String();
		/*if ( value < 0 ) 
			signe = "-" ; 
		else 
			signe = "+" ;*/
		
		int deg = (int) tmp ;
		
		float min = (float) ( tmp - deg ) ;
		min *= 60;
		int min_int = (int) min ;
		
		float sec = ( min - (float) min_int ) ;
		sec *= 60 ;
		int sec_int = (int) sec ;
		
		float dix_sec = ( sec - sec_int ) ;
		//System.out.println( "Dix_sec:" + dix_sec );
		dix_sec *= 10 ;
		int dix_sec_int = Math.round( dix_sec ) ;
		//System.out.println( "Dix_sec:" + dix_sec_int );
		
		return signe + deg + "°" + min_int + "'" + sec_int + "," + dix_sec_int + "\"" ;
		
	}
	
	public static double sexagedecimalToDecimal( int degre , int minute , int sec , int dix_sec , int cent_sec , String direction )
	{
		double value_res = 0 ;
		
		//Calcul
	     value_res = sec + ( dix_sec + cent_sec / 10 ) / 10 ;
	     value_res = minute + value_res / 60 ;
	     value_res = degre + value_res / 60 ;
	     
	     if( (direction.compareTo("S") == 0) || ( direction.compareTo("O") == 0)  )
	     {
	    	 //System.out.println( "Changement de signe :" + direction ) ;
	    	 value_res *= -1  ;
	    	 //System.out.println("Value : " + value_res ) ;
	     }
	     
		return value_res ;
	}
	
	
	
	// Utilisé pour les aérodromes
	public static double sexagedecimalToDecimal( String coord , Coordonnees.TYPE format )
	{
		int degre = 0 , minute = 0 , sec =0 , dix_sec = 0 , cent_sec = 0 ;
		String direction = "" , regexp = "" ;
		
		if( format == Coordonnees.TYPE.LATITUDE )
			regexp =  Coordonnees.regexp_latitude;
		else
			regexp = Coordonnees.regexp_longitude ;
		Scanner s = new Scanner( coord);
		s.findInLine(  regexp + "$") ;
		MatchResult result = s.match();
		
		//for (int i=1; i<=result.groupCount(); i++) 
		//{
		//	System.out.println( result.group(i) );
		//}
		
		// Match
		degre  = Integer.parseInt( result.group( 1 ) ) ;
		if( result.group( 2 ) != null ) {
			minute = Integer.parseInt( result.group( 2 ) ) ;
	    	if( result.group( 3 ) != null ) { 
	    		sec = Integer.parseInt( result.group( 3 ) ) ;
	    		if( result.group( 4 ) != null ) {
	    			dix_sec = Integer.parseInt( result.group( 4 ) ) ;
	    		 	if( result.group( 5 ) != null ) {
	    		 		cent_sec = Integer.parseInt( result.group( 5 ) ) ;
	    		 	}
	    		 }		
	    	 }
	     }
	     direction = result.group( 6 ) ;
	     s.close(); 
	     
	     return Coordonnees.sexagedecimalToDecimal( degre , minute , sec , dix_sec ,cent_sec , direction ) ;
	}
	
	public static double decimalToRadian(double v)
	{
		return (v*Math.PI)/180;
	}
	
	public static double radianToDecimal( double v )
	{
		return ( v * 180 ) / Math.PI ;
	}
	
	public static void main( String args[] )
	{
		//double test_result = 0 ;
		//String str=new String("48°59'"  + "11" + ",2" + "\"" + " S");
		//test_result = Coordonnees.sexagedecimalToDecimal(str, Coordonnees.FORMAT.LATITUDE );
		//System.out.println( "Test Méthodes de classe" + test_result ) ;
		//System.out.println( Coordonnees.decimalToRadian(test_result) ) ;
		
		//System.out.println( "==============================") ;
		
		Coordonnees test = new Coordonnees( "48°59'"  + "11" + ",2" + "\"" + " O" , Coordonnees.TYPE.LONGITUDE ) ;
		System.out.println( "Test Instance" + test + " " + test.estPositif() ) ;
		
		
	}
	
	public boolean estPositif() {
		return ( this.value > 0 ) ;
	}
	
	public double getValue() {
		return this.value ;
	}
	
	public double toRadians() {
		return Coordonnees.decimalToRadian( this.value ) ;
	}
	
	// TODO RENAME CARTESIAN  COORDINATES
	public void add(double v)
	{
		this.value+=v;
	}
	
	// TODO ADD 'ADD' WITH LOXODROMY
	
	// DONE
	public double multiply( double mult )
	{
		this.value *= mult ;
		return this.value ;
	}
}