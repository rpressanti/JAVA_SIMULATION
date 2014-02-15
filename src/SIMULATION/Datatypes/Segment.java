package SIMULATION.Datatypes;

import java.util.ArrayList;

import SIMULATION.Graphe.*;

public class Segment extends Arete<Segment,NoeudTrajectoire,Point> {
	
	private ArrayList<Plot> plots ;
	private Double distance_totale ;
	private Double distance_parcourue ;
	private Double distance_restante ;
	private Vecteur vecteur_vitesse ;
	
	public Segment( NoeudTrajectoire origine , NoeudTrajectoire destination , Double weight) {
		
		super( Segment.class , NoeudTrajectoire.class , Point.class , origine , destination , weight ) ;
		this.plots = new ArrayList<Plot>() ;
		this.distance_totale = this.getOrigine().getContent().distanceTo( this.getDestination().getContent() ) ; 
		this.distance_parcourue = 0.0 ;
		this.distance_restante = 0.0 ;
		this.vecteur_vitesse = new Vecteur( this.getOrigine().getContent() , this.getDestination().getContent() ) ;	
	}
	
	
	
	public void setVitesse( double vitesse) {
		this.vecteur_vitesse.setModule( vitesse );
	}
	
	public ArrayList<Plot> getPlots() {
		return this.plots ;
	}
	
	public double getDistanceRestante() {
		return this.distance_restante ;
	}
	
	public boolean totalement_parcourue() {
		return this.distance_parcourue == this.distance_totale ;
	}
	
	
	public int iterer( int nb_plots , Double vitesse , double intervalle_de_temps ) {
		
		Point tmp_point = null ;
		int plot_indice = 1 , plots_restants = nb_plots ;
		
		tmp_point = this.getOrigine().getContent() ;
		tmp_point.deplacerDe( this.vecteur_vitesse) ;
		this.plots.add( new Plot( tmp_point  )) ;
		
		this.distance_parcourue += this.vecteur_vitesse.getModule() ;
		if ( this.distance_parcourue > this.distance_totale) {
			this.distance_restante = - this.distance_totale - this.distance_parcourue ;
			this.distance_parcourue = this.distance_totale ;
		}		
		
		if( nb_plots <= 1 )
			return nb_plots ;
		
		
		for( plot_indice = 1 ; plot_indice < nb_plots ; plot_indice++)
		{
			this.plots.add( this.plots.get( plot_indice -1 ).suivant( this.vecteur_vitesse) ) ;
			plots_restants -- ;
		}
		
		return plots_restants ;
	}
	
	public Point getPointCourant() {
		
		Point result = this.getOrigine().getContent() ;
		
		Vecteur vecteur_parcouru = this.vecteur_vitesse ;
		vecteur_parcouru.setModule( this.distance_parcourue );

		result.deplacerDe( vecteur_parcouru);
		
		return result ;
	}
	
	
	
}
