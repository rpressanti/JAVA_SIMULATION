package SIMULATION.Datatypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import SIMULATION.Graphe.Arete;
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
	
	private ListIterator<Arete<Point>> trajectoire ;
	@SuppressWarnings("unused")
	private Arete<Point> segment_courant ;
	private double distanceIntraSegment ;
	private ArrayList<Plot> plots ;
	
	private boolean en_conflit ;
	
	public Avion( String nom , Repere depart , Repere arrivee , int flight_level , double vitesse , Date heure_depart) {
		
		this.nom = nom ;
		this.arrivee = arrivee ;
		this.depart = depart ;
		this.flight_level = flight_level ;
		this.vitesse = vitesse ;
		this.heure_depart = heure_depart ;
		
		this.plots = new ArrayList<Plot>() ;
		
		this.trajectoire = null ;
		this.en_conflit = false ;
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
	
	public boolean iterer( double intervalle_de_temps , Date heure_courante) 
	{
		if ( this.heure_depart.before( heure_courante ))
			return false ;
		
		ArrayList<Plot> new_plots = new ArrayList<Plot>() ;
		
		this.distanceIntraSegment -= this.distance_parcourue(intervalle_de_temps) ;
		if ( this.distanceIntraSegment < 0 )
		{
			if( this.trajectoire.hasNext() ) 
				this.segment_courant = this.trajectoire.next() ;
			else
				this.distanceIntraSegment =0 ;
			this.distanceIntraSegment *= -1 ;
		}
		
		// TODO UPDATE POINT ATTRIBUTES
		/*
		Vecteur vecteur_vitesse = new Vecteur( this.segment_courant.getOrigine().getContent() , 
										this.segment_courant.getDestination().getContent()
				) ;
		vecteur_vitesse.setModule( this.vitesse * intervalle_de_temps );
		*/
		
		if( Avion.NbPlots > 0)
		{
			int indice_plot = 1 ;
			
			for( indice_plot = 1 ; indice_plot < Avion.NbPlots ; indice_plot++)
			{
				// TODO UPDATE PLOT
				
				
			}
		}
		
		this.plots = new_plots ;
		
		return true ;
	}

	private double distance_parcourue( double intervalle_de_temps )
	{
		return this.vitesse * intervalle_de_temps ;
	}
	
	
	// DONE
	@SuppressWarnings("unused")
	private Vecteur getDeplacement( Arete<Point> segment , double intervalle_de_temps ) {	
	
		Vecteur direction = new Vecteur( segment.getOrigine().getContent() , 
										segment.getDestination().getContent() ) ;
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
	
	
	public ArrayList< Plot > getPlots() {		
		return this.plots ;
	}
	
	public void setEnConflit( boolean en_conflit )  {
		this.en_conflit = en_conflit ;
	}
	
	public boolean getEnConflit()  {
		return this.en_conflit ;
	}
}
