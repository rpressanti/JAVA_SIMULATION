package SIMULATION.Datatypes ;

import java.util.ArrayList;
import java.util.Date;



import SIMULATION.Graphe.Chemin;

public class Avion {
	
	
	public static final Integer NbPlots = 4  ;
	

	private String nom ;
	private Date heure_depart ;
	
	final private Repere depart ;
	final private Repere arrivee ;
	final private int flight_level ;
	final private double vitesse ;
	private Chemin<Segment,NoeudTrajectoire,Point> trajectoire ;

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
		int plots_restants = Avion.NbPlots , indice_segment = 0 ;
		Segment segment = this.trajectoire.element() ;
		
		if ( this.heure_depart.before( heure_courante ))
			return false ;

		segment = this.trajectoire.element() ;

		if( segment.totalement_parcourue() )
			this.trajectoire.remove();
		
		while( ( plots_restants >= 0) &&( indice_segment < this.trajectoire.size() ) )
		{	
			segment = this.trajectoire.get( indice_segment++ ) ;
			plots_restants = segment.iterer(plots_restants , this.vitesse , intervalle_de_temps ) ;
		}
				
		return true ;
	}

	
	
	
	// DONE
	public boolean setTrajectoire( Chemin<Segment,NoeudTrajectoire,Point> trajectoire ) {

		this.trajectoire = trajectoire ;
		
		for( Segment segment : this.trajectoire )
			segment.setVitesse( this.vitesse ) ;
		
		return true ;

	}
	
	// DONE
	public ArrayList< Plot > getPlots() {		
		
		int indice_segment = 0 , nb_plots = 0 ;
		ArrayList<Plot> plots = new ArrayList<Plot>() ;

		if( this.trajectoire.isEmpty() )
			return plots ;
		
		Segment current_segment = this.trajectoire.get( 0 ) ;
		
		for( indice_segment = 0 , nb_plots = 0 ; ( nb_plots < Avion.NbPlots) && ( indice_segment < this.trajectoire.size() ) ; indice_segment++)
		{
			current_segment = this.trajectoire.get( indice_segment ) ;
			for( Plot plot : current_segment.getPlots() )
			{
				plots.add( plot ) ;
				nb_plots ++ ;
			}
		} 
		
		return plots ;
	}
	
	
	
	
	public void setEnConflit( boolean en_conflit )  {
		this.en_conflit = en_conflit ;
	}
	
	public boolean getEnConflit()  {
		return this.en_conflit ;
	}
}
