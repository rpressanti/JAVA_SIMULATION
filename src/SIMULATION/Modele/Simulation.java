package SIMULATION.Modele;

import java.util.ArrayList;
import java.util.HashMap;

import SIMULATION.Datatypes.Aerodrome;
import SIMULATION.Datatypes.Point;
import SIMULATION.Datatypes.Balise ;
import SIMULATION.Datatypes.Avion ;
import SIMULATION.Graphe.Graphe;

public class Simulation {

	public enum PHASE { CHARGEMENT_REPERES, CHARGEMENT_AVION , CALCUL_TRAJECTOIRES , DEMARRAGE } ;
	
	private HashMap<String,Balise> balises ;
	private HashMap<String , Aerodrome> aerodromes ;
	
	private Graphe<Point> grapheComplet ;
	private Graphe<Point> grapheFiltre ;
	
	private ArrayList<Avion> avions ;
	
	private double distance_max ;
	private double intervalle_d_iteration ;
	
	public Simulation () {
		
		this.balises    = new HashMap<String,Balise>() ;
		this.aerodromes = new HashMap<String,Aerodrome>() ;
		
		this.avions = new ArrayList<Avion>() ;
		
		this.grapheComplet = new Graphe<Point>() ;
		this.grapheFiltre = new Graphe<Point> () ;
		
		this.distance_max = 0 ;
		
	}
	
	public void setDistanceMax( float distance )  {
		this.distance_max = distance ;
	}
	
	public boolean  iterer() {

		boolean result = true ;
		
		for( Avion avion : this.avions)
			result &= avion.iterer( this.intervalle_d_iteration ) ;
		
		return result ;
	}
	
	
	public boolean phasePossible( PHASE phase ) {
		
		boolean ready = true ;
		
		switch( phase ) {
		
			case DEMARRAGE :
				//ready &=  ; 
				
			case CALCUL_TRAJECTOIRES :
				ready &=  ! this.avions.isEmpty() ;
				
			case CHARGEMENT_AVION :
				ready &= !this.balises.isEmpty() ;
				ready &= !this.aerodromes.isEmpty() ;
				
			case CHARGEMENT_REPERES :
		
				
		}
		
		return ready ;
	}
	
	public boolean charger_balises() {
		
		return true ;
	}
	
	public boolean charger_aerodromes() {
		
		return true ;
	}
	
	public boolean charger_avions() {
		
		
		
		
		
		return true ;
	}

	public boolean calculer_trajectoires() {
		
		boolean result = true ;
		
		this.grapheFiltre = this.grapheComplet.clone() ;
		this.grapheFiltre.filtrer( this.distance_max ) ;
		
		for( Avion avion : this.avions )
		{
			Graphe<Point> graphe_buffer = this.grapheFiltre.clone() ;
			
			// TODO ajouter les reperes de depart et arrivee
			
			
			avion.setTrajectoire( graphe_buffer.djikstra() ) ;
		}
		
		return result ;
	}


}
