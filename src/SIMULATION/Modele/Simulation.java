package SIMULATION.Modele;



import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner ;
import java.util.regex.MatchResult;

import SIMULATION.Datatypes.*;
import SIMULATION.Graphe.* ;

@SuppressWarnings("unused")
public class Simulation {

	// TODO PHASE
	public enum PHASE { CHARGEMENT_REPERES_MOITIE , CHARGEMENT_REPERES , CHARGEMENT_AVION ,
						CALCUL_TRAJECTOIRES , DEMARRAGE } ;
	private PHASE phase_prete ;

	private Calendar heure_courante ;
	
	private ArrayList<ViewSimulation> vues ;
	
	private HashMap<String,Balise> balises ;
	private HashMap<String , Aerodrome> aerodromes ;
	
	private GrapheComplet<Segment,NoeudTrajectoire,Point> grapheComplet ;
	private Graphe<Segment,NoeudTrajectoire,Point> grapheFiltre ;
	
	private HashMap<String,Avion> avions ;
	private ArrayList<Trajectoire> trajectoires ;
	
	private double distance_max ;
	private static final int intervalle_d_iteration = 0 ;
	private double distanceEntreAvions ;
	
	// DONE
	public Simulation () {
		
		this.heure_courante = new GregorianCalendar() ;
		
		this.balises    = new HashMap<String,Balise>() ;
		this.aerodromes = new HashMap<String,Aerodrome>() ;
		
		this.avions = new HashMap<String,Avion>() ;
		this.trajectoires = new ArrayList<Trajectoire>() ;
		
		this.grapheComplet = new GrapheComplet<Segment,NoeudTrajectoire,Point>( Segment.class , NoeudTrajectoire.class , Point.class) ;
		this.grapheFiltre = new Graphe<Segment,NoeudTrajectoire,Point> ( Segment.class , NoeudTrajectoire.class , Point.class ) ;
		
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
		
		this.heure_courante.add( Calendar.SECOND , Simulation.intervalle_d_iteration ) ;
		
		// On bouge les avions
		for( Avion avion : this.avions.values() )
			result &= avion.iterer( Simulation.intervalle_d_iteration , this.heure_courante.getTime() ) ;
	
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
	
	
	// DONE
	public boolean charger_balises( String ficname ) {
				
		Scanner scan_balise = null ;
		String indicatif = "" , coord = "" , tmp_line = "";
		Balise new_balise =  null ;
		
		try {
			
			FileReader fr = new FileReader( ficname ) ;
			//System.out.println( "Fichier ouvert: "  + ficname ) ;
			BufferedReader is = new BufferedReader( fr ) ;
			//System.out.println( "Buffer ouvert: "  + ficname ) ;
			
			do {

				tmp_line = is.readLine() ;
				
				if( tmp_line != null )
				{
					//System.out.println("tmp_line:" + tmp_line);
					
					scan_balise = new Scanner( tmp_line );
					
					try  {
						scan_balise.findInLine(  "(\\w{1,3}) (.+)" ) ;
						MatchResult result = scan_balise.match();
						
						indicatif = result.group( 1 ) ;
						coord = result.group( 2 ) ;
				
						//System.out.println("indicatif:" + indicatif + "|coord:" + coord) ;
						
						scan_balise.close() ;
						
						new_balise = new Balise( indicatif , coord ) ;
						this.balises.put( indicatif , new_balise ) ;
						this.grapheComplet.add( new_balise ) ;
					
					} catch( Exception e)
					{
						System.out.println( "Erreur cr�ation balise:" + indicatif + coord );
					}
				}
				
			} while (tmp_line != null ) ; 
				
			is.close();
			
		} catch ( Exception e) {
			System.err.println("Erreur de chargement") ;
			e.printStackTrace() ;
		}
		
		System.out.println( "Chargement balises termin�." );
		return this.rafraichir() ;
	}
	
	// DONE
	public boolean charger_aerodromes( String ficname ) {
		
		Scanner scan_ad = null ;
		String tmp_line = "" , nom = "" , code_OACI = "" ;
		Double longitude = null , latitude = null ;
		Aerodrome new_ad =  null ;
		
		try {
			
			BufferedReader is = new BufferedReader( new FileReader( ficname ) ) ;
		
			
			
			// On ignore l'en-tete et les lignes vides suivantes
			do { 
				tmp_line = is.readLine() ; 
			} while ( tmp_line == "" || tmp_line.startsWith( ";" ) ) ;
			
			
			do {

				tmp_line = is.readLine() ;
				
				if( tmp_line != null )
				{
					scan_ad = new Scanner( tmp_line ) ;
					
					try {
						
						scan_ad.findInLine( "(-?\\d+\\.\\d+), (-?\\d+\\.\\d+), \"(.+)(?: \\((?:code )?([\\w]{4})\\))?\"" ) ;
						MatchResult result = scan_ad.match();
						
						longitude = Double.parseDouble( result.group( 1 ) ) ;
						latitude  = Double.parseDouble( result.group( 2 ) ) ;
						nom = result.group( 3 ) ;
						code_OACI = result.group( 4 ) ;
						
						
						new_ad = new Aerodrome( nom , code_OACI , longitude , latitude ) ;
						this.aerodromes.put( nom , new_ad ) ;
						this.grapheComplet.add( new_ad ) ;
					
					} catch ( Exception e) {
						System.out.println( "Erreur création aérodrome" );
					}
					
					
					scan_ad.close();
				}
				
			} while (tmp_line != null) ; 
				
			is.close();
			
		} catch ( Exception e) {
			System.err.println("Erreur de chargement") ; 
			e.printStackTrace() ;
		}
		
		System.out.println( "Chargement aérodrome terminé" ) ;
		return this.rafraichir() ;
	}
	
	// TODO IMPORT AVIONS
	public boolean charger_avions( String ficname) {
		
		if ( this.phase_prete != PHASE.CHARGEMENT_AVION )
			return false ;
		
		return true ;
	}

	
	
	// TODO EXPORT :: IMPORTANT
	public boolean exporter_trajectoires( String ficname ) {
		
		
		return true ;
	}

	
	
	

	
	
	
	// TODO Cr�er avion
	public boolean creer_avion( String nom , Repere depart , Repere arrivee , int flight_level , double vitesse , Date heure_depart) {
		
		Avion new_avion = new Avion( nom , depart , arrivee , flight_level , vitesse , heure_depart) ;
		
		this.avions.put( nom , new_avion ) ;
		
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
			Graphe<Segment,NoeudTrajectoire,Point> graphe_buffer = this.grapheFiltre.clone() ;
			
			NoeudTrajectoire noeud_depart  = this.grapheComplet.getNoeud( (Point) avion.getDepart()  ) ;
			NoeudTrajectoire noeud_arrivee = this.grapheComplet.getNoeud( (Point) avion.getArrivee() ) ;
			
			if( avion.getDepart() instanceof Aerodrome)
				this.ajouterAerodrome( graphe_buffer , noeud_depart );
			if( avion.getArrivee() instanceof Aerodrome)
				this.ajouterAerodrome( graphe_buffer , noeud_arrivee );
			
			avion.setTrajectoire( graphe_buffer.djikstra( noeud_depart , noeud_arrivee ).minimizeNbBalises().random() ) ;
		}
		
		return result ;
	}

	// DONE
	private void ajouterAerodrome( Graphe<Segment,NoeudTrajectoire,Point> graphe_buffer , NoeudTrajectoire aerodrome ) {
		
		NoeudTrajectoire copie = aerodrome.clone() ;
		graphe_buffer.add( copie ) ;
		for( Segment arete : aerodrome.getAretes().values() )
			if ( arete.getWeight() < this.distance_max ) 
				graphe_buffer.add( aerodrome.getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
		
		// Idem pour les dependances inverses
		for( Segment arete : aerodrome.getAretesInverses().values() )
			if ( arete.getWeight() < this.distance_max )
				graphe_buffer.add( arete.getOrigine().getContent() , aerodrome.getContent() , arete.getWeight() ) ;
		
	}
	
	
	
	public static void main( String args[] ) {
		
		Simulation simulation = new Simulation() ;
		//simulation.charger_balises( "/home/eleve/IESSA/pressari/balises_fr.txt" ) ;
		//simulation.charger_aerodromes( "/Users/richard/Desktop/aerodromes_fr.txt" ) ;
		//simulation.charger_aerodromes( "/home/eleve/IESSA/pressari/PROJET_JAVA/aerodromes_fr.txt" ) ;
		simulation.charger_aerodromes( "C:/Users/nono/git/JAVA_SIMULATION/fichiers/aerodromes_fr.txt" ) ;
		simulation.charger_balises( "C:/Users/nono/git/JAVA_SIMULATION/fichiers/balises_fr.txt" ) ;
	}
	
	
}
