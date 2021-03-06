package SIMULATION.Datatypes;

import java.util.Scanner;
import java.util.regex.MatchResult;

import SIMULATION.Graphe.Distance;

// CLASS DONE


public class Point implements Distance<Point> {
	
	static final int metres_par_NM = 1852 ;
	static final int rayon_terre=6378000;

	
	public static final String regexp_point = Coordonnees.regexp_latitude 
				+ " " + Coordonnees.regexp_longitude;
	
	
	public static final Point ORIGINE = new Point( 0 , 0 ) ;
	
	
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
		
	
	public Point clone() {
		return new Point( this.getLongitude().getValue() , this.getLatitude().getValue() ) ;
	}
	
	
	
	public double distanceLoxo( Point p )
	{
		double a,b,c,d,e,f,g,h,i;
		a=Coordonnees.decimalToRadian(p.longitude.getValue());
		b=Coordonnees.decimalToRadian(p.latitude.getValue());
		c=Coordonnees.decimalToRadian(this.longitude.getValue());
		d=Coordonnees.decimalToRadian(this.latitude.getValue());
		double d1=a-c;
		e=Math.sin(d);
		f=Math.sin(b);
		g=Math.cos(d);
		h=Math.cos(b);
		i=Math.cos(d1);
		// Calcul en m puis conversion en NM
		return Point.rayon_terre*Math.acos((e*f)+(g*h*i)) / Point.metres_par_NM ; 
	}
	
	public double distanceCartesian( Point p) {
		return Math.sqrt(
			  Math.pow( p.getLongitude().getValue() - this.longitude.getValue() , 2 ) 
			+ Math.pow( p.getLatitude().getValue()  - this.latitude.getValue() ,2 )
			) ;
	}
	
	public double distanceTo( Point p) {
		return distanceLoxo( p ) ;
	}
	
	
	public void deplacerDe( Vecteur v ) {
		this.deplacerDe_cartesian( v ) ;
	}
	
	
	
	private void deplacerDe_cartesian( Vecteur v )
	{
		this.longitude.add(v.longitude.getValue());
		this.latitude.add(v.latitude.getValue());
	}

	@SuppressWarnings("unused")
	private void deplacerDe_loxodromy( Vecteur v ) {
		// TODO DEPLACER_DE_LOXODROMY PAS_URGENT
	}
	
	
	
	
	public Latitude getLatitude() {
		return this.latitude ;
	}
	
	public Longitude getLongitude() {
		return this.longitude ;
	}
	
	public String toString()
	{
		return this.latitude.toString() + "\n"
				+ this.longitude.toString() ;
	}


	
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Point p1=new Point("4°56'24.0\" N 004°56'24.0\" E") ;
		Point p2= new Point(-4.42194 , 48.44722); //Brest
		Point p3=new Point(-1.73222, 48.07194); //Rennes
		Point p4=new Point(3.08694, 50.56361); //Lille
		Point p5=new Point(1.36778, 43.63528); //Toulouse
		Point p6=new Point(1.37833, 44.02750); //Montauban
		double d1=p2.distanceTo(p3);
		System.out.println(d1*1.852+" distance entre Brest et Rennes en km");
		System.out.println(p2.longitude+ " longitude de Brest");
		System.out.println(p2.latitude+ " latitude de Brest");
		System.out.println(p3.longitude+ " longitude de Rennes");
		System.out.println(p3.latitude+ " latitude de Rennes");
		double d2=p4.distanceTo(p5);
		System.out.println(d2*1.852+" distance entre Lille et Toulouse en km");
		double d3=p5.distanceTo(p6);
		System.out.println(d3*1.852+" distance entre Montauban et Toulouse en km");
	
	}


}