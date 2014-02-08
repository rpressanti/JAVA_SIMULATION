package SIMULATION.View;

import java.util.Date;
import java.util.HashMap;

import SIMULATION.Datatypes.*;
import SIMULATION.Modele.ViewSimulation;

public interface InterfaceModele {

	boolean enregistrer( ViewSimulation vue );
	
	HashMap< String , Balise > getBalises() ;
	HashMap< String , Aerodrome > getAerodromes() ;
	HashMap< String , Repere > getReperes() ;
	HashMap< String , Avion > getAvions() ;
	HashMap< Avion , Trajectoire > getTrajectoires() ;

	boolean setDistanceMax( double distance ) ;
	boolean creer_avion( String name , Repere depart , Repere arrivee , int FLightLevel , double vitesse, Date heure_depart ) ;
	
	boolean charger_balises( String ficname ) ;
	boolean charger_aerodromes( String ficname ) ;
	boolean charger_avions( String ficname ) ; 
	
	boolean calculer_trajectoires() ;
	
	boolean iterer() ;
	
}
