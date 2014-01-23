package SIMULATION.Datatypes;

import java.util.Scanner;
import java.util.regex.MatchResult;

import SIMULATION.Graphe.Distance;




public class Point implements Distance<Point> {
	
	static final int metres_par_NM = 1852 ;
	
	public static final String regexp_point = Coordonnees.regexp_latitude 
				+ " " + Coordonnees.regexp_longitude;
	
	// RMQ : Pour balise
	// regexp "([A-Z]{3}) (.*)"
	// nom = result.group( 1 )
	// point = result.group(2 ) ;
	
	protected Latitude latitude;
	protected Longitude longitude;
	
	// Utilisé pour les aérodromes
	public Point( double x , double y )
	{
		this.longitude = new Longitude(x);
		this.latitude  = new Latitude(y);
		
	}
	
	
	public Point( String arg )
	{
		int degre_lat = 0 , minute_lat = 0 , sec_lat =0 , dix_sec_lat = 0 , cent_sec_lat = 0 ;
		int degre_long = 0 , minute_long = 0 , sec_long =0 , dix_sec_long = 0 , cent_sec_long = 0 ;
		String direction_lat = "" , direction_long ;
		
		Scanner s = new Scanner( arg );
		s.findInLine( Point.regexp_point + "$") ;
		MatchResult result = s.match();
		
		degre_lat  = Integer.parseInt( result.group( 1 ) ) ;
		if( result.group( 2 ) != null ) {
			minute_lat = Integer.parseInt( result.group( 2 ) ) ;
	    	if( result.group( 3 ) != null ) { 
	    		sec_lat = Integer.parseInt( result.group( 3 ) ) ;
	    		if( result.group( 4 ) != null ) {
	    			dix_sec_lat = Integer.parseInt( result.group( 4 ) ) ;
	    		 	if( result.group( 5 ) != null ) {
	    		 		cent_sec_lat = Integer.parseInt( result.group( 5 ) ) ;
	    		 	}
	    		 }		
	    	 }
	     }
		direction_lat = result.group( 6 ) ;
		
		degre_long  = Integer.parseInt( result.group( 7 ) ) ;
		if( result.group( 8 ) != null ) {
			minute_long = Integer.parseInt( result.group( 8 ) ) ;
	    	if( result.group( 9 ) != null ) { 
	    		sec_long = Integer.parseInt( result.group( 9 ) ) ;
	    		if( result.group( 10 ) != null ) {
	    			dix_sec_long = Integer.parseInt( result.group( 10 ) ) ;
	    		 	if( result.group( 5 ) != null ) {
	    		 		cent_sec_long = Integer.parseInt( result.group( 11 ) ) ;
	    		 	}
	    		 }		
	    	 }
	     }
		direction_long = result.group( 12 ) ;
		
		
		
		s.close();
		
		this.longitude = new Longitude( degre_long , minute_long , sec_long , dix_sec_long ,cent_sec_long , direction_long ) ;
		this.latitude  = new Latitude( degre_lat , minute_lat , sec_lat , dix_sec_lat ,cent_sec_lat , direction_lat ) ;	
		
	}
		
	// TODO Verifier formule
	public double distanceTo( Point p )
	{
		double a,b,c,d,e,f,g,h,i;
		a=p.longitude.getValue();
		b=p.latitude.getValue();
		c=this.longitude.getValue();
		d=this.latitude.getValue();
		double d1=a-c;
		e=Math.sin(d);
		f=Math.sin(b);
		g=Math.cos(d);
		h=Math.cos(b);
		i=Math.cos(d1);
		// Calcul en m puis conversion en NM
		return Math.acos((e*f)+(g*h*i)) / Point.metres_par_NM ; 
	}
	
	// TODO
	public void deplacerDe( Vecteur v ) //utiliser Coordonnes.add()
	{
		this.longitude.add(v.longitude.getValue());
		this.latitude.add(v.latitude.getValue());
	}

	public Latitude getLatitude() {
		return this.latitude ;
	}
	
	public Longitude getLongitude() {
		return this.longitude ;
	}

	public static void main(String[] args) {
		//p1=new Point("")

	}


}