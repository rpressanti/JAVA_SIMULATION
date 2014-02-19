package SIMULATION.Modele;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner ;
import java.util.regex.MatchResult;

import SIMULATION.Datatypes.*;
import SIMULATION.Graphe.* ;
import SIMULATION.View.InterfaceModele;


public class Simulation implements InterfaceModele {

	public enum PHASE { INIT ,
						CHARGEMENT_AD , CHARGEMENT_BALISES , 
						CHARGEMENT_REPERES , CHARGEMENT_AVION ,
						CALCUL_TRAJECTOIRES , DEMARRAGE } ;
	private PHASE phase_prete ;

	private Calendar heure_courante ;
	
	private ArrayList<ViewSimulation> vues ;
	
	private HashMap<String,Balise> balises ;
	private HashMap<String , Aerodrome> aerodromes ;
	
	private GrapheComplet<Trajectoire,Segment,NoeudTrajectoire,Point> grapheComplet ;
	private GrapheComplet<Trajectoire,Segment,NoeudTrajectoire,Point> grapheFiltre ;
	
	private HashMap<String,Avion> avions ;
	private HashMap<Avion,Trajectoire> trajectoires ;
	
	private double distance_max ;
	public static final int intervalle_d_iteration = 30 ;
	private double distanceEntreAvions ;
	
	// DONE
	public Simulation () {
		
		this.heure_courante = new GregorianCalendar() ;
		
		this.reinitialiser() ;
		
	}
	
	public boolean reinitialiser() {
		
		this.balises    = new HashMap<String,Balise>() ;
		this.aerodromes = new HashMap<String,Aerodrome>() ;
		
		this.avions = new HashMap<String,Avion>() ;
		this.trajectoires = new HashMap<Avion,Trajectoire>() ;
		
		this.grapheComplet = new GrapheComplet<Trajectoire,Segment,NoeudTrajectoire,Point>( Trajectoire.class , Segment.class , NoeudTrajectoire.class , Point.class) ;
		this.grapheFiltre = new GrapheComplet<Trajectoire,Segment,NoeudTrajectoire,Point> ( Trajectoire.class , Segment.class , NoeudTrajectoire.class , Point.class ) ;
		
		this.distance_max = 0 ;
		
		this.phase_prete = PHASE.CHARGEMENT_REPERES ;
		this.vues = new ArrayList<ViewSimulation>() ;
	
		return true ;
	}
	
	public void setHeureCourante( Calendar courant ) {
		this.heure_courante = courant ;
	}
	
	public Calendar getHeureCourante() {
		return this.heure_courante ;
	}
	
	
	public ArrayList<PHASE> phasePossible( PHASE phase ) {
		
		ArrayList<PHASE> a_faire = new ArrayList<PHASE>() ;
		
		switch( phase ) {
		
			case DEMARRAGE :
				
				if ( this.phase_prete != PHASE.CALCUL_TRAJECTOIRES)
					a_faire.add( PHASE.CALCUL_TRAJECTOIRES ) ;
				else
					break ;

				
				
			case CALCUL_TRAJECTOIRES :
				
				if ( this.phase_prete != PHASE.CHARGEMENT_AVION)
					a_faire.add( PHASE.CHARGEMENT_AVION ) ;
				else
					break ;
				
				
				
			case CHARGEMENT_AVION :

				if ( this.balises.isEmpty() )
					a_faire.add( PHASE.CHARGEMENT_BALISES ) ;
				if ( this.aerodromes.isEmpty() )
					a_faire.add( PHASE.CHARGEMENT_AD ) ;
				
				break ;

				
			default :
				break ;
		}
		
		return a_faire ;
	}
	
	
	
	
	
	
	public boolean enregistrer( ViewSimulation vue) {
		return this.vues.add( vue ) ;
	}
	
	private boolean rafraichir() {
		
		if( this.phase_prete != PHASE.DEMARRAGE )
			return false ;
		
		boolean result = true ;
		for( ViewSimulation vue : this.vues )
			result &= vue.rafraichir() ;
		return result ;
		
	}
	
	
	// DONE
	@SuppressWarnings("unchecked")
	public HashMap<String,Balise> getBalises() {
		return ( HashMap<String,Balise> ) this.balises.clone()  ;
	}
	
	// DONE
	@SuppressWarnings("unchecked")
	public HashMap<String,Aerodrome> getAerodromes() {
		return ( HashMap<String,Aerodrome> ) this.aerodromes.clone() ;
	}
	
	
	// DONE
	public HashMap<String,Repere>getReperes() {
		
		HashMap<String,Repere> result = new HashMap<String,Repere>() ;
		
		for( Aerodrome aerodrome : this.aerodromes.values() )
			result.put( aerodrome.get_code_OACI() , (Repere) aerodrome ) ;
			
		for( Balise balise : this.balises.values() )
			result.put( balise.getIndicatif() , (Repere) balise ) ;
		
		return result ;
	}
	
	
	
	// DONE
	public HashMap<String,Avion> getAvions() {
		return this.avions ;
	}
	
	// DONE
	public HashMap<Avion,Trajectoire> getTrajectoires() {
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
			if( ! avion.iterer( Simulation.intervalle_d_iteration , this.heure_courante.getTime() ) )
				this.avions.remove( avion.getNom() ) ;

		// On detecte les conflits
		result &= this.detecter_conflits() ;
		// On rafraichit les vues
		result &= this.rafraichir() ;
		
		//System.out.println( this.heure_courante.getTime() );
		
		return result ;
	}
	
	// DONE
	public boolean detecter_conflits() {
		
		boolean avion_en_conflit = false , modele_en_conflit = false  ;
		
		if( this.phase_prete != PHASE.DEMARRAGE )
			return false ;		
		
		
		for( Avion avion_1 : this.avions.values() )
			for( Avion avion_2 : this.avions.values() )
				if ( avion_1 != avion_2 )
				{
					avion_en_conflit = false ;
					
					for( Plot plot_1 : avion_1.getPlots() )
						for( Plot plot_2 : avion_1.getPlots() )
							avion_en_conflit |= ( plot_1.distanceTo( plot_2) < this.distanceEntreAvions ) ;
								
					avion_1.setEnConflit( avion_en_conflit ) ;
					modele_en_conflit |= avion_en_conflit ;
				}
		
		return modele_en_conflit ;
	}
	
	


	
	// DONE
	public boolean charger_balises( File ficname ) {
				
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
					scan_balise = new Scanner( tmp_line );
					
					try  {
						scan_balise.findInLine(  "(\\w{1,3}) (.+)" ) ;
						MatchResult result = scan_balise.match();
						
						indicatif = result.group( 1 ) ;
						coord = result.group( 2 ) ;
				
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
	public boolean charger_aerodromes( File ficname ) {
		
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
						
						scan_ad.findInLine( "(-?\\d+\\.\\d+), (-?\\d+\\.\\d+), \"([^(]+)(?: \\((?:code )?(.{4})\\))?\"" ) ;
						MatchResult result = scan_ad.match();
						
						longitude = Double.parseDouble( result.group( 1 ) ) ;
						latitude  = Double.parseDouble( result.group( 2 ) ) ;
						nom = result.group( 3 ) ;
						code_OACI = result.group( 4 ) ;
						if ( code_OACI == null)
							code_OACI = "" ;
						//System.out.println( "result:" + code_OACI );
						
						new_ad = new Aerodrome( nom , code_OACI , longitude , latitude ) ;
						this.aerodromes.put( code_OACI , new_ad ) ;
					
					} catch ( Exception e) {
						System.out.println( "Erreur cr�ation a�rodrome:" + tmp_line );
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
	public boolean charger_avions( File ficname) {
	
		System.out.println( "Chargement avions" );
		
		Scanner scan_avion = null ;
		String indicatif = "" , coord = "" , tmp_line = "";
		
		try {
			
			FileReader fr = new FileReader( ficname ) ;
			//System.out.println( "Fichier ouvert: "  + ficname ) ;
			BufferedReader is = new BufferedReader( fr ) ;
			//System.out.println( "Buffer ouvert: "  + ficname ) ;
			
			do {

				tmp_line = is.readLine() ;
				
				if( tmp_line != null )
				{
					scan_avion = new Scanner( tmp_line );
					
					try  {
						scan_avion.findInLine(  "(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\d+)\\|(\\d+)\\|(.+)" ) ;
						MatchResult result = scan_avion.match();
						
						indicatif = result.group( 1 ) ;
						
						String depart_str = result.group( 2 ) ;
						//System.out.println( depart_str ) ;
						
						Repere depart = null ;
						if( this.aerodromes.containsKey( depart_str ))
							depart = this.aerodromes.get( depart_str ) ;
						if( this.balises.containsKey( depart_str ))
							depart = this.balises.get( depart_str ) ;
						
						String arrivee_str = result.group( 3 ) ;
						Repere arrivee = null ;
						if( this.aerodromes.containsKey( arrivee_str ))
							arrivee = this.aerodromes.get( arrivee_str ) ;
						if( this.balises.containsKey( arrivee_str ))
							arrivee = this.balises.get( arrivee_str ) ;
						
						Integer flight_level = Integer.parseInt( result.group(4) ) ;
						
						Double vitesse = Double.parseDouble( result.group(5) ) ;
						
						
						//System.out.println( "Match:" + result.group(6) ) ;
						SimpleDateFormat format_date = new SimpleDateFormat( "YYYY/MM/DD-HH:MM" ) ;
						Date heure_depart = format_date.parse( result.group(6) ) ; 
						
						scan_avion.close() ;
						
						// TODO CORRECT
						Avion new_avion = new Avion( indicatif , depart , arrivee , flight_level , vitesse , heure_depart) ; ;
						this.avions.put( indicatif , new_avion ) ;
						
					} catch( Exception e)
					{
						System.out.println( "Erreur création avion:" + indicatif + coord );
						e.printStackTrace();
					}
				}
				
			} while (tmp_line != null ) ; 
				
			is.close();
			
		} catch ( Exception e) {
			System.err.println("Erreur de chargement") ;
			e.printStackTrace() ;
		}
		
		System.out.println( "Chargement avions terminé." );
		return this.rafraichir() ;
		
	}

	

	
	
	//DONE
	public boolean exporter_trajectoires( File ficname ) {
		
		ObjectOutputStream os = null ;
    	String line = null ;
		
		try{
			os = new ObjectOutputStream( new FileOutputStream( ficname ) ) ;
		} catch ( Exception e) {
			System.err.println( "Ouverture impossible en �criture du fichier : " + ficname ) ;
		}
		
		
		for( Entry<Avion, Trajectoire> entry : this.trajectoires.entrySet() )
		{
			line = entry.getKey().getNom() + "|" ;
			line += entry.getValue().toString();
			line += "\n" ;
			
			try {
			os.writeChars( line ) ;	
			} catch( Exception e) {
				System.err.println( "Erreur �criture trajectoire:" + line );
			}
			
		}
		
		try{
			os.close() ;
		} catch( Exception e ) {
			System.err.println( "Fermeture impossible du fichier : " + ficname ) ;
		}
		
		return true ;
	}

	
	
	// DONE
	public boolean creer_avion( String nom , Repere depart , Repere arrivee , int flight_level , double vitesse , Date heure_depart) {
		
		Avion new_avion = new Avion( nom , depart , arrivee , flight_level , vitesse , heure_depart) ;
		
		this.avions.put( nom , new_avion ) ;
		
		Trajectoire new_traj = calculer_trajectoire( depart , arrivee) ;
		new_avion.setTrajectoire( new_traj.clone() ) ;
		this.trajectoires.put( new_avion, new_traj ) ;
		
		return true ;
	}
	

	// DONE
	public boolean genererGrapheTotal() {
		
		if( this.phase_prete != PHASE.CALCUL_TRAJECTOIRES )
			return false ;
		
		this.grapheComplet.generer() ; 
		
		return true ;
	}
	
	// DONE
	public boolean setDistanceMax( double distance_max ) 
	{
		this.distance_max = distance_max ;
		this.grapheFiltre = this.grapheComplet.clone() ;
		this.grapheFiltre.filtrer( this.distance_max ) ;
		return this.calculer_trajectoires();
	}
	
	public Trajectoire calculer_trajectoire( Repere origine , Repere destination ) {

		GrapheComplet<Trajectoire,Segment,NoeudTrajectoire,Point> graphe_buffer = ( GrapheComplet<Trajectoire,Segment,NoeudTrajectoire,Point> ) this.grapheFiltre.clone() ;
		NoeudTrajectoire noeud_depart  = null ;
		NoeudTrajectoire noeud_arrivee = null ;

		if( origine instanceof Aerodrome)
			noeud_depart = this.ajouterAerodrome( graphe_buffer , new NoeudTrajectoire( (Point) origine ) );
		else
			noeud_depart  = graphe_buffer.getNoeud( (Point) origine  ) ;
		
		if( destination instanceof Aerodrome)
			noeud_arrivee = this.ajouterAerodrome( graphe_buffer , new NoeudTrajectoire( (Point) destination ) );
		else
			noeud_arrivee = graphe_buffer.getNoeud( (Point) destination ) ;
		
		
		graphe_buffer.generer() ;
		graphe_buffer.filtrer( this.distance_max ) ;

				
		
		return (Trajectoire) graphe_buffer.djikstra( noeud_depart , noeud_arrivee ).minimizeNbBalises().random() ;
	}
	
	
	
	// DONE
	public boolean calculer_trajectoires() {
		
		boolean result = true ;
			
		//if( ( this.phase_prete != PHASE.CALCUL_TRAJECTOIRES ) || ( ! this.genererGrapheTotal() ) )
		//	return false ;
		
		System.out.println( "Calcul des trajectoires" );
	
		for( Avion avion : this.avions.values() )
		{
			Trajectoire trajectoire = calculer_trajectoire( avion.getDepart() , avion.getArrivee() ) ;
			avion.setTrajectoire( trajectoire.clone() ) ;
			this.trajectoires.put( avion , trajectoire ) ;
		}
		
		this.phase_prete = PHASE.DEMARRAGE ;
		
		return this.rafraichir() ;
	}

	// DONE
	private NoeudTrajectoire ajouterAerodrome( Graphe<Trajectoire,Segment,NoeudTrajectoire,Point> graphe_buffer , NoeudTrajectoire aerodrome ) {
		
		NoeudTrajectoire copie = aerodrome.clone() ;
		graphe_buffer.add( copie ) ;
		for( Segment arete : aerodrome.getAretes().values() )
			if ( arete.getWeight() < this.distance_max ) 
				graphe_buffer.add( aerodrome.getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
		
		// Idem pour les dependances inverses
		for( Segment arete : aerodrome.getAretesInverses().values() )
			if ( arete.getWeight() < this.distance_max )
				graphe_buffer.add( arete.getOrigine().getContent() , aerodrome.getContent() , arete.getWeight() ) ;
		
		return copie ;
	}
	
	
	
	public static void main( String args[] ) {
		
		Simulation simulation = new Simulation() ;
		
		simulation.charger_aerodromes( new File( "fichiers/aerodromes_fr.txt" ) ) ;
		//System.out.println( simulation.getAerodromes().values().size() ) ;
		
		simulation.charger_balises( new File("fichiers/balises_fr.txt" ) );
		//System.out.println( simulation.getBalises().values().size() ) ;

		
		
		//System.out.println( "Chargement avions" );
		simulation.charger_avions( new File( "fichiers/avions.txt") );
		//System.out.println( simulation.getAvions().size() + "Avions chargés" ) ;
		//for( Avion avion : simulation.getAvions().values() )
		//	System.out.println( avion.getNom() ) ;
	

		simulation.genererGrapheTotal() ;
		simulation.setDistanceMax( 2000 ) ;
		Trajectoire test = simulation.calculer_trajectoire( simulation.getAerodromes().get("LFOI") , simulation.getAerodromes().get("LFBA")) ;
		//Trajectoire test = simulation.calculer_trajectoire( simulation.getBalises().get("AGN") , simulation.getBalises().get("ABB")) ;
		System.out.println( "Traj:" + test ) ;
		//simulation.calculer_trajectoires() ;
		//System.out.println("Nb traj:" + simulation.trajectoires.size() ) ;
	}
	
	
}
