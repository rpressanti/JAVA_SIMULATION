package SIMULATION.Datatypes ;

import java.util.ArrayList;
import java.util.Date;

import SIMULATION.Modele.Simulation;


public class Avion {
	
	
	public static final Integer NbPlots = 4  ;
	
	private String nom ;
	private Date heure_depart ;
	
	final private Repere depart ;
	final private Repere arrivee ;
	final private int flight_level ;
	final private double vitesse ;
	private Trajectoire trajectoire ;
	
	private boolean en_conflit ;

	
	
	
	
	
	public Avion( String nom , Repere depart , Repere arrivee , int flight_level , double vitesse , Date heure_depart) {
		
		this.nom = nom ;
		this.arrivee = arrivee ;
		this.depart = depart ;
		this.flight_level = flight_level ;
		this.vitesse = vitesse ;
		this.heure_depart = heure_depart ;
		
		this.trajectoire = new Trajectoire()  ;
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
	
	public int getFL() {
		return this.flight_level ;
	}
	
	// DONE
	public boolean iterer( double intervalle_de_temps , Date heure_courante) 
	{
		//System.out.println( "Iteration d'un avion" );
		
		int plots_restants = Avion.NbPlots , indice_segment = 0 ;
		
		if ( this.heure_depart.after( heure_courante ))
			return false ;
		
		if( this.trajectoire.isEmpty() )
		{
			//System.out.println( "Fin du trajet" );
			return false ;
		}
		
		Segment segment = this.trajectoire.element() ;

		if( segment.totalement_parcouru() )
		{
			//System.out.println( "Suppression d'un segment" );
			this.trajectoire.remove();
		} else {
			//System.out.println( "DistanceRestante : " + segment.getDistanceRestante() );
		}
		
		
		while( ( plots_restants >= 0) && ( indice_segment < this.trajectoire.size() ) )
		{	
			segment = this.trajectoire.get( indice_segment++ ) ;
			plots_restants = segment.iterer(plots_restants , this.vitesse , intervalle_de_temps ) ;
			//System.out.println( "Plots_restants:" + plots_restants) ;
		}
				
		return true ;
	}

	
	
	
	// DONE
	public boolean setTrajectoire( Trajectoire trajectoire ) {

		this.trajectoire = trajectoire.clone() ;
		
		if( this.trajectoire.isEmpty() )
			return false ;
		
		for( Segment segment : this.trajectoire )
			segment.setVitesse( this.vitesse , Simulation.intervalle_d_iteration ) ;
		
		return true ;

	}
	
	// DONE
	public ArrayList< Plot > getPlots() {		
		
		int indice_segment = 0 , nb_plots = 0 ;
		ArrayList<Plot> plots = new ArrayList<Plot>() ;

		if( this.trajectoire.isEmpty() )
		{
			//System.out.println( "Trajectoire vide!" );
			return plots ;
		}
		
		Segment current_segment = null ;
		
		for( indice_segment = 0 , nb_plots = 0 ; ( nb_plots < Avion.NbPlots) && ( indice_segment < this.trajectoire.size() ) ; indice_segment++)
		{
			current_segment = this.trajectoire.get( indice_segment ) ;
			for( Plot plot : current_segment.getPlots() )
			{
				plots.add( plot ) ;
				nb_plots ++ ;
			}
		} 
		
		
		//System.out.println( "Nb de plots:" + plots.size() ) ;
		return plots ;
	}
	
	
	public ArrayList<Repere> getRepereValide() {
		return this.trajectoire.getRepereValide() ;
	}
	
	public boolean devier( Point deviation , Repere retour) {
		
		Trajectoire new_traj = new Trajectoire() ;
		
		//Construction des nouveaux segment initiaux
		Point point_courant = this.trajectoire.get(0).getPointCourant() ;
		Segment new_segment_initial = new Segment( 
				new NoeudTrajectoire( point_courant ) ,
				new NoeudTrajectoire( deviation ) ,
				point_courant.distanceTo( deviation )
				) ;
		new_traj.add( new_segment_initial ) ;
		
		Segment segment_retour = new Segment(
				new NoeudTrajectoire( deviation ) ,
				new NoeudTrajectoire( (Point) retour ) ,
				deviation.distanceTo( (Point) retour )
			) ;
		new_traj.add( segment_retour ) ;
		
		
		// Elimination des segments précédant la balise de retour
		Segment buffer = null ;
		do {
			buffer = this.trajectoire.remove() ;
		} while( buffer.getDestination().getContent() != retour ) ;
		
		
		// Empilement du chemin normal
		while( ! this.trajectoire.isEmpty() )
			new_traj.add( this.trajectoire.remove() ) ;
		
		this.setTrajectoire(new_traj) ;
		
		return true ;
	}
	
	
	public void setEnConflit( boolean en_conflit )  {
		this.en_conflit = en_conflit ;
	}
	
	public boolean getEnConflit()  {
		return this.en_conflit ;
	}
}
