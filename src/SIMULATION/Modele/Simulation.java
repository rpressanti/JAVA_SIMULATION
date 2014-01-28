package SIMULATION.Modele;

import java.util.ArrayList;
import java.util.HashMap;

import SIMULATION.Datatypes.*;
import SIMULATION.Graphe.* ;

@SuppressWarnings("unused")
public class Simulation {

	// TODO PHASE
	public enum PHASE { CHARGEMENT_REPERES_MOITIE , CHARGEMENT_REPERES , CHARGEMENT_AVION ,
						CALCUL_TRAJECTOIRES , DEMARRAGE } ;
	private PHASE phase_prete ;
	
	private ArrayList<ViewSimulation> vues ;
	
	private HashMap<String,Balise> balises ;
	private HashMap<String , Aerodrome> aerodromes ;
	
	private GrapheComplet<Point> grapheComplet ;
	private Graphe<Point> grapheFiltre ;
	
	private HashMap<String,Avion> avions ;
	private ArrayList<Trajectoire> trajectoires ;
	
	private double distance_max ;
	private double intervalle_d_iteration ;
	private double distanceEntreAvions ;
	
	// DONE
	public Simulation () {
		
		this.balises    = new HashMap<String,Balise>() ;
		this.aerodromes = new HashMap<String,Aerodrome>() ;
		
		this.avions = new HashMap<String,Avion>() ;
		this.trajectoires = new ArrayList<Trajectoire>() ;
		
		this.grapheComplet = new GrapheComplet<Point>() ;
		this.grapheFiltre = new Graphe<Point> () ;
		
		this.distance_max = 0 ;
		
		this.phase_prete = PHASE.CHARGEMENT_REPERES_MOITIE ;
		this.vues = new ArrayList<ViewSimulation>() ;
		
	}
	
	public boolean enregistrer( ViewSimulation vue) {
		return this.vues.add( vue ) ;
	}
	
	private boolean rafraichir() {
		
		boolean result = true ;
		for( ViewSimulation vue : this.vues )
			result &= vue.rafraichir() ;
		return result ;
		
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
	public ArrayList<Trajectoire> getTrajectoires() {
		return this.trajectoires ;
	}
	
	
	// DONE
	public boolean iterer() {

		boolean result = true ;

		if( this.phase_prete != PHASE.DEMARRAGE )
			return false ;
		
		// On bouge les avions
		for( Avion avion : this.avions.values() )
			result &= avion.iterer( this.intervalle_d_iteration ) ;
	
		// On detecte les conflits
		result &= this.detecter_conflits() ;
		// On rafraichit les vues
		result &= this.rafraichir() ;
		
		return result ;
	}
	
	// DONE
	public boolean detecter_conflits() {
		
		boolean en_conflit = false ;
		
		for( Avion avion_1 : this.avions.values() )
			for( Avion avion_2 : this.avions.values() )
				if ( avion_1 != avion_2 )
				{
					en_conflit = false ;
					
					for( Plot plot_1 : avion_1.getPlots() )
						for( Plot plot_2 : avion_1.getPlots() )
							en_conflit |= ( plot_1.distanceTo( plot_2) < this.distanceEntreAvions ) ;
								
					avion_1.setEnConflit( true ) ;
				}
		
		return true ;
	}
	
	
	// TODO RM :: PHASE POSSIBLE
//	public boolean phasePossible( PHASE phase ) {
//		
//		boolean ready = true ;
//		
//		switch( phase ) {
//		
//			case DEMARRAGE :
//				ready &= ! this.trajectoires.isEmpty() ; 
//				
//			case CALCUL_TRAJECTOIRES :
//				ready &=  ! this.avions.isEmpty() ;
//				
//			case CHARGEMENT_AVION :
//				ready &= ! this.balises.isEmpty() ;
//				ready &= ! this.aerodromes.isEmpty() ;
//				
//			case CHARGEMENT_REPERES :
//		
//				
//		}
//		
//		return ready ;
//	}
	
	
	// TODO IMPORT BALISES
	public boolean charger_balises( String ficname ) {
		// ITERER SUR LES LIGNES DU FICHIER
		// CREER BALISES
		// L'AJOUTER A :
		// 	  1) this.balises
		//    2) this.grapheComplet
				
		return true ;
	}
	
	
	// TODO IMPORT AERODROMES
	public boolean charger_aerodromes( String ficname ) {
		// IDEM BALISES
		
		return true ;
	}
	
	// TODO IMPORT AVIONS
	public boolean charger_avions( String ficname) {
		
		if ( this.phase_prete != PHASE.CHARGEMENT_AVION )
			return false ;
		
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
				graphe_buffer.add( aerodrome.getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
		
		// Idem pour les dependances inverses
		for( Arete<Point> arete : aerodrome.getAretesInverses().values() )
			if ( arete.getWeight() < this.distance_max )
				graphe_buffer.add( arete.getOrigine().getContent() , aerodrome.getContent() , arete.getWeight() ) ;
		
	}
	
}
