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
			
			Noeud<Point >noeud_depart  = this.grapheComplet.getNoeud( (Point) avion.getDepart()  ) ;
			Noeud<Point> noeud_arrivee = this.grapheComplet.getNoeud( (Point) avion.getArrivee() ) ;
			
			if( avion.getDepart() instanceof Aerodrome)
				this.ajouterAerodrome( graphe_buffer , noeud_depart );
			if( avion.getArrivee() instanceof Aerodrome)
				this.ajouterAerodrome( graphe_buffer , noeud_arrivee );
			
			avion.setTrajectoire( graphe_buffer.djikstra( noeud_depart , noeud_arrivee ).minimizeNbBalises().random() ) ;
		}
		
		return result ;
	}

	// DONE
	private void ajouterAerodrome( Graphe<Point> graphe_buffer , Noeud<Point> aerodrome ) {
		
		Noeud<Point> copie = aerodrome.clone() ;
		graphe_buffer.add( copie ) ;
		for( Arete<Point> arete : aerodrome.getAretes().values() )
			if ( arete.getWeight() < this.distance_max ) 
			{
				// TODO RM
				//Noeud<Point> destination = graphe_buffer.getNoeud( arete.getDestination().getContent() ) ;
				//Arete<Point> nouvelle_arete = new Arete<Point>( aerodrome , destination , arete.getWeight() ) ;
				graphe_buffer.add( aerodrome.getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
			}
		
		// Idem pour les dependances inverses
		for( Arete<Point> arete : aerodrome.getAretesInverses().values() )
			if ( arete.getWeight() < this.distance_max )
			{
				// TODO RM
				//Noeud<Point> origine = graphe_buffer.getNoeud( arete.getOrigine().getContent() ) ;
				//Arete<Point> nouvelle_arete = new Arete<Point>( origine , aerodrome , arete.getWeight() ) ;
				graphe_buffer.add( arete.getOrigine().getContent() , aerodrome.getContent() , arete.getWeight() ) ;
			}
		
	}
	
}
