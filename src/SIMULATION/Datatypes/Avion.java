package SIMULATION.Datatypes ;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;


import SIMULATION.Graphe.Chemin;

public class Avion {
	
	
	public static final Integer NbPlots = 4  ;
	

	private String nom ;
	private Date heure_depart ;
	
	final private Repere depart ;
	final private Repere arrivee ;
	@SuppressWarnings("unused")
	final private int flight_level ;
	final private double vitesse ;
	private ListIterator<Segment> trajectoire ;

	
	private LinkedList<Segment> segments ;

	
	private boolean en_conflit ;
	
	public Avion( String nom , Repere depart , Repere arrivee , int flight_level , double vitesse , Date heure_depart) {
		
		this.nom = nom ;
		this.arrivee = arrivee ;
		this.depart = depart ;
		this.flight_level = flight_level ;
		this.vitesse = vitesse ;
		this.heure_depart = heure_depart ;
		
		this.trajectoire = null ;
		this.en_conflit = false ;

		this.segments = new LinkedList<Segment>() ;
	}
	
	public String getNom() {
		return this.nom ;
	}
	
	
	public Repere getDepart() {
		return this.depart ;
	}
	
	public Repere getArrivee() {
		return this.arrivee ;
	}
	
	
	
	// DONE
	public boolean iterer( double intervalle_de_temps , Date heure_courante) 
	{
		int plots_restants = Avion.NbPlots ;
		Segment segment = null ;
		
		if ( this.heure_depart.before( heure_courante ))
			return false ;
		
		if( this.segments.isEmpty() ) 
			this.segments.push( new Segment( this.trajectoire.next() , 0 , this.vitesse ) ) ;
	
		while ( plots_restants > 0)
		{
			segment = this.segments.element() ;
			if( segment.parcourue() ) {
				this.segments.remove();
				segment = this.segments.element() ;
			}
			
			plots_restants = segment.iterer(intervalle_de_temps, plots_restants ) ;
		
			if( plots_restants != 0 )
			{
				if ( segment.getNext() != null)
					segment.setNext( this.segments.get( 1 ) );
			
				segment = segment.getNext() ;
			}	
			
		}
				
		return true ;
	}

	
	
	
	// DONE
	public boolean setTrajectoire( Chemin<Segment,NoeudTrajectoire,Point> trajectoire ) {
		this.trajectoire = trajectoire.listIterator() ;
		boolean result = true ;	

		/*
		if ( this.trajectoire.hasNext() )
			this.segment_courant = new Segment( this.trajectoire.next() );
		else
			result = false ;
		*/
		return result ;
	}
	
	
	public ArrayList< Plot > getPlots() {		
		
		ArrayList<Plot> plots = new ArrayList<Plot>() ;

		if( this.segments.isEmpty() )
			return plots ;
		
		Segment current_segment = this.segments.get( 0 ) ;
		
		do {

			for( Plot plot : current_segment.getPlots() )
				plots.add( plot ) ;

			current_segment = current_segment.getNext() ;
		
		} while ( current_segment != null ) ;
		
		
		return plots ;
	}
	
	public void setEnConflit( boolean en_conflit )  {
		this.en_conflit = en_conflit ;
	}
	
	public boolean getEnConflit()  {
		return this.en_conflit ;
	}
}
