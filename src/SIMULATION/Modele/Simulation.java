package SIMULATION.Modele;

import java.util.ArrayList;
import java.util.HashMap;

import SIMULATION.Datatypes.*;
import SIMULATION.Graphe.* ;

@SuppressWarnings("unused")
public class Simulation {

	public enum PHASE { CHARGEMENT_REPERES, CHARGEMENT_AVION , CALCUL_TRAJECTOIRES , DEMARRAGE } ;
	
	private HashMap<String,Balise> balises ;
	private HashMap<String , Aerodrome> aerodromes ;
	
	private GrapheComplet<Point> grapheComplet ;
	private Graphe<Point> grapheFiltre ;
	
	private HashMap<String,Avion> avions ;
	private ArrayList<Trajectoire> trajectoires ;
	
	private double distance_max ;
	private double intervalle_d_iteration ;
	
	// DONE
	public Simulation () {
		
		this.balises    = new HashMap<String,Balise>() ;
		this.aerodromes = new HashMap<String,Aerodrome>() ;
		
		this.avions = new HashMap<String,Avion>() ;
		this.trajectoires = new ArrayList<Trajectoire>() ;
		
		this.grapheComplet = new GrapheComplet<Point>() ;
		this.grapheFiltre = new Graphe<Point> () ;
		
		this.distance_max = 0 ;
		
	}
	
	
	// DONE
	public void setDistanceMax( float distance )  {
		this.distance_max = distance ;
	}
	
	// DONE
	public HashMap<String,Balise> getBalises() {
		return this.balises ;
	}
	
	// DONE
	public HashMap<String,Aerodrome> getAerodromes() {
		return this.aerodromes ;
	}
	
	
	// DONE
	public HashMap<String,Avion> getAvions() {
		return this.avions ;
	}
	
	// DONE
	public boolean iterer() {

		boolean result = true ;
		
		for( Avion avion : this.avions.values() )
			result &= avion.iterer( this.intervalle_d_iteration ) ;
	
		result &= this.detecter_conflits() ;
		
		return result ;
	}
	
	// TODO
	public boolean detecter_conflits() {
		
		
		return true ;
	}
	
	
	// TODO
	public boolean phasePossible( PHASE phase ) {
		
		boolean ready = true ;
		
		switch( phase ) {
		
			case DEMARRAGE :
				ready &= ! this.trajectoires.isEmpty() ; 
				
			case CALCUL_TRAJECTOIRES :
				ready &=  ! this.avions.isEmpty() ;
				
			case CHARGEMENT_AVION :
				ready &= ! this.balises.isEmpty() ;
				ready &= ! this.aerodromes.isEmpty() ;
				
			case CHARGEMENT_REPERES :
		
				
		}
		
		return ready ;
	}
	
	
	// TODO
	public boolean charger_balises( String ficname ) {
		// ITERER SUR LES LIGNES DU FICHIER
		// CREER BALISES
		// L'AJOUTER A :
		// 	  1) this.balises
		//    2) this.grapheComplet
				
		return true ;
	}
	
	
	// TODO
	public boolean charger_aerodromes( String ficname ) {
		// IDEM BALISES
		
		return true ;
	}
	
	// TODO
	public boolean charger_avions( String ficname) {
		
		
		
		return true ;
	}

	// TODO Verifier Phase
	private boolean genererGrapheTotal() {
		this.grapheComplet.generer() ; 
		return true ;
	}
	
	// DONE
	public boolean setDistanceMax( double distance_max ) 
	{
		this.distance_max = distance_max ;
		this.grapheFiltre = this.grapheComplet.clone() ;
		this.grapheFiltre.filtrer( this.distance_max ) ;
		
		return calculer_trajectoires() ;
	}
	
	// DONE
	public boolean calculer_trajectoires() {
		
		boolean result = true ;
			
		for( Avion avion : this.avions.values() )
		{
			Graphe<Point> graphe_buffer = this.grapheFiltre.clone() ;
			
			if( avion.getDepart() instanceof Aerodrome)
				this.ajouterAerodrome( graphe_buffer , this.grapheComplet.getNoeud( (Point) avion.getDepart() )  );
			if( avion.getArrivee() instanceof Aerodrome)
				this.ajouterAerodrome( graphe_buffer , this.grapheComplet.getNoeud( (Point) avion.getArrivee() )  );
			
			avion.setTrajectoire( graphe_buffer.djikstra().minimizeNbBalises().random() ) ;
		}
		
		return result ;
	}

	// TODO
	private void ajouterAerodrome( Graphe<Point> graphe_buffer , Noeud<Point> aerodrome ) {
		
		Noeud<Point> copie = aerodrome.clone() ;
		graphe_buffer.add( copie ) ;
		for( Arete<Point> arete : aerodrome.getAretes().values() )
			if ( arete.getWeight() < this.distance_max ) 
			{
				Noeud<Point> nouvelle_destination = graphe_buffer.getNoeud( arete.getDestination().getContent() ) ;
				Arete<Point> nouvelle_arete = new Arete<Point>( aerodrome , arete.getDestination() , arete.getWeight() ) ;
			}
		
		// Idem pour les dependances inverses
		for( Arete<Point> arete : aerodrome.getAretesInverses().values() )
			if ( arete.getWeight() < this.distance_max )
			{
				Noeud<Point> nouvelle_origine = graphe_buffer.getNoeud( arete.getOrigine().getContent() ) ;
				Arete<Point> nouvelle_arete = new Arete<Point>( nouvelle_origine , aerodrome , arete.getWeight() ) ;
			}
		
	}
	
}
