package SIMULATION.View;

import java.awt.Dimension;

import javax.swing.JPanel;

import SIMULATION.Datatypes.Coordonnees;
import SIMULATION.Datatypes.Point;

@SuppressWarnings("serial")
public class PanelAffichage extends JPanel {

	// TODO ATTRIBUTS COORDONNEES GPS
	@SuppressWarnings("unused")
	private Coordonnees gauche_haut ;
	@SuppressWarnings("unused")
	private Coordonnees droit_bas ;
	
	
	// TODO  METHODE CALCULER COORD_IHM d'un POINT A PARTIR
	// TODO DES COORDONNEES DU CADRE
	public Dimension coordonnees_IHM( Point p ) {
	
		
		return new Dimension(0 ,0 ) ;
	}

}
