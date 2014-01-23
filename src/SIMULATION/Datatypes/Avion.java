package SIMULATION.Datatypes;

//import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import SIMULATION.Graphe.Arete;
import SIMULATION.Graphe.Chemin;

public class Avion {

	@SuppressWarnings("unused")
	private Date heure_depart ;
	
	final private Repere depart ;
	final private Repere arrivee ;
	@SuppressWarnings("unused")
	final private int flight_level ;
	final private double vitesse ;
	
	
	private ListIterator<Arete<Point>> trajectoire ;
	private Arete<Point> segment_courant ;
	private double distanceIntraSegment ;
	
	public Avion( Repere depart , Repere arrivee , int flight_level , double vitesse ) {
		
		this.arrivee = arrivee ;
		this.depart = depart ;
		this.flight_level = flight_level ;
		this.vitesse = vitesse ;
		
		this.trajectoire = null ;
		
	}
	
	public Repere getDepart() {
		return this.depart ;
	}
	
	public Repere getArrivee() {
		return this.arrivee ;
	}
	
	// TODO UPDATE POSITION
	public boolean iterer( double intervalle_de_temps ) {
		
		this.distanceIntraSegment -= this.distance_parcourue(intervalle_de_temps) ;
		if ( this.distanceIntraSegment < 0 )
		{
			if( this.trajectoire.hasNext() ) 
				this.segment_courant = this.trajectoire.next() ;
			else
				this.distanceIntraSegment =0 ;
			this.distanceIntraSegment *= -1 ;
		}
		
		return true ;
	}

	private double distance_parcourue( double intervalle_de_temps )
	{
		return this.vitesse * intervalle_de_temps ;
	}
	
	
	// TODO
	@SuppressWarnings("unused")
	private Vecteur getDeplacement( double intervalle_de_temps ) {	
	
		Vecteur direction = new Vecteur( this.segment_courant.getOrigine().getContent() , 
				this.segment_courant.getDestination().getContent() ) ;
		direction.setModule( this.distance_parcourue(intervalle_de_temps )) ;
		return direction ;

	}
	
	// DONE
	public boolean setTrajectoire( Chemin<Point> trajectoire ) {
		this.trajectoire = trajectoire.listIterator() ;
		boolean result = true ;	
		
		if ( !this.trajectoire.hasNext() )
			this.segment_courant = this.trajectoire.next() ;
		else
			result = false ;
		
		return result ;
	}
	
}
