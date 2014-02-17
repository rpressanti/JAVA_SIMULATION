package SIMULATION.View;

import java.util.Date;
import java.util.HashMap;

import SIMULATION.Datatypes.*;
import SIMULATION.Modele.ViewSimulation;

public interface InterfaceModele {

	public boolean enregistrer( ViewSimulation vue );
	
	public HashMap< String , Balise > getBalises() ;
	public HashMap< String , Aerodrome > getAerodromes() ;
	public HashMap< String , Repere > getReperes() ;
	public HashMap< String , Avion > getAvions() ;
	public HashMap< Avion , Trajectoire > getTrajectoires() ;

	public boolean setDistanceMax( double distance ) ;
	public boolean creer_avion( String name , Repere depart , Repere arrivee , int FLightLevel , double vitesse, Date heure_depart ) ;
	
	public boolean charger_balises( String ficname ) ;
	public boolean charger_aerodromes( String ficname ) ;
	public boolean charger_avions( String ficname ) ; 
	
	public Trajectoire calculer_trajectoire( Repere origine , Repere destination ) ;
	public boolean calculer_trajectoires() ;
	public boolean exporter_trajectoires( String ficname ) ;
	
	public boolean iterer() ;
	
	public boolean reinitialiser() ;
	
}
