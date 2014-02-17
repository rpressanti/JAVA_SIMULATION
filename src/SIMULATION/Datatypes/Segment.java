package SIMULATION.Datatypes;

import java.util.ArrayList;

import SIMULATION.Graphe.*;

public class Segment extends Arete<Segment,NoeudTrajectoire,Point> {
	
	private Point position_courante ;
	private ArrayList<Plot> plots ;
	private Double distance_totale ;
	private Double distance_parcourue ;
	private Double distance_restante ;
	private Vecteur vecteur_vitesse ;
	private boolean totalement_parcouru ;
	
	public Segment( NoeudTrajectoire origine , NoeudTrajectoire destination , Double weight) {
		
		super( Segment.class , NoeudTrajectoire.class , Point.class , origine , destination , weight ) ;
		this.position_courante = origine.getContent() ;
		this.totalement_parcouru = false ;
		this.plots = new ArrayList<Plot>() ;
		//this.distance_totale = this.getOrigine().getContent().distanceTo( this.getDestination().getContent() ) ; 
		this.distance_totale = this.weight ;
		this.distance_parcourue = 0.0 ;
		this.distance_restante = 0.0 ;
		this.vecteur_vitesse = new Vecteur( this.getOrigine().getContent() , this.getDestination().getContent() ) ;	
	}
	
	
	
	public void setVitesse( double vitesse , double intervalle_en_secondes) {
		this.vecteur_vitesse.setModule( vitesse * intervalle_en_secondes / 3600 ) ;
	}
	
	public ArrayList<Plot> getPlots() {
		return this.plots ;
	}
	
	public double getDistanceRestante() {
		return this.distance_restante ;
	}
	
	public boolean totalement_parcouru() {
		//return this.distance_parcourue >= this.distance_totale ;
		return this.totalement_parcouru ;
	}
	
	
	public int iterer( int nb_plots , Double vitesse , double intervalle_de_temps ) {
		
		int plot_indice = 1 , plots_restants = nb_plots ;
		this.plots = new ArrayList<Plot>() ;
		
		this.distance_parcourue += this.vecteur_vitesse.getModule() ;

		if ( this.distance_parcourue >= this.distance_totale) {
			//System.out.println( "On dépasse la longueur du segment" );
			this.distance_restante = this.distance_parcourue - this.distance_totale ;
			this.distance_parcourue = this.distance_totale ;
			this.totalement_parcouru = true ;
		}		

		//System.out.println( "Vecteur vitesse : " + this.vecteur_vitesse ) ; 
		this.position_courante.deplacerDe( this.vecteur_vitesse) ;
		//System.out.println( this.position_courante );
		this.plots.add( new Plot( this.position_courante  )) ;
		
		
		if( nb_plots <= 1 )
			return nb_plots ;
		
		
		for( plot_indice = 0 ; plot_indice < nb_plots - 1 ; plot_indice++)
		{
			this.plots.add( this.plots.get( plot_indice ).suivant( this.vecteur_vitesse) ) ;
			plots_restants -- ;
		}
		
		//System.out.println( "Plots restants segments: " + plots_restants) ;
		
		return plots_restants ;
	}
	
	public Point getPointCourant() {

		return this.position_courante ;

	}
	
	
	
}
