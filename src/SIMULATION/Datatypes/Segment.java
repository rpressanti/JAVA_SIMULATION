package SIMULATION.Datatypes;

import java.util.ArrayList;

import SIMULATION.Graphe.*;

public class Segment extends Arete<Point> {
	
	private ArrayList<Plot> plots ;
	private Double distanceIntraSegment ;
	private Double distance_restante ;
	private Vecteur vecteur_vitesse ;
	private Segment next ;
	
	public Segment( Arete<Point> arete , double distanceIntraSegment, double vitesse)
	{
		super( arete.getOrigine() , arete.getDestination() , arete.getWeight() ) ;
		this.next = null ;
		this.plots = new ArrayList<Plot>() ;
		this.distanceIntraSegment = distanceIntraSegment ;
		this.distance_restante = 0.0 ;
		this.vecteur_vitesse = new Vecteur( this.getOrigine().getContent() , this.getDestination().getContent() ) ;
		this.vecteur_vitesse.setModule( vitesse );
	}
	
	
	public void setNext( Segment next) {
		this.next = next ;
	}
	
	public Segment getNext() {
		return this.next ;
	}
	
	public ArrayList<Plot> getPlots() {
		return this.plots ;
	}
	
	public double getDistanceRestante() {
		return this.distance_restante ;
	}
	
	public boolean parcourue() {
		return this.distanceIntraSegment == this.vecteur_vitesse.getModule() ;
	}
	
	public int iterer( double intervalle_de_temps , int nb_plots ) {
		
		Point tmp_point = null ;
		int plot_indice = 1 , plots_restants = nb_plots ;
		boolean has_next = false ;
		
		if( this.plots.isEmpty() )
		{
			tmp_point = this.getOrigine().getContent() ;
			tmp_point.deplacerDe( this.vecteur_vitesse) ;
			this.plots.add( new Plot( tmp_point , 0 )) ;
		}
		
		if( nb_plots <= 1 )
			return 0 ;
		
		
		for( plot_indice = 1 ; ( plot_indice < nb_plots ) && ! has_next ; plot_indice++)
		{
			this.distanceIntraSegment += this.vecteur_vitesse.getModule() ;
			
			
			if ( this.distanceIntraSegment < 0) {
				this.distance_restante = - this.distanceIntraSegment ;
				this.distanceIntraSegment = this.vecteur_vitesse.getModule() ;
				has_next = true ;
			} else
			{
				this.plots.add( this.plots.get( plot_indice -1 ).suivant( this.vecteur_vitesse) ) ;
				plots_restants -- ;
			}
		}
		
		return plots_restants ;
	}
	
	
	
	
	
}
