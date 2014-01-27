package SIMULATION.View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLayeredPane;

import SIMULATION.Datatypes.Coordonnees;
import SIMULATION.Datatypes.Point;
import SIMULATION.Modele.Simulation;

@SuppressWarnings("serial")
public class PanelAffichage extends JLayeredPane {

	// TODO ATTRIBUTS COORDONNEES GPS
	@SuppressWarnings("unused")
	private Coordonnees gauche_haut ;
	@SuppressWarnings("unused")
	private Coordonnees droit_bas ;
	
	private Simulation modele ;
	
	private AerodromesPanel aerodromes ;
	private BalisesPanel balises ;
	private TrajectoiresPanel trajectoires ;
	private AvionsPanel avions ;
	
	
	
	private class Resizer implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub
			Dimension dim = ( (Component) e.getSource()).getSize() ;
			
			PanelAffichage.this.balises.setSize( dim );
			PanelAffichage.this.balises.revalidate();
			PanelAffichage.this.balises.repaint();
			
			PanelAffichage.this.aerodromes.setSize( dim ) ;
			PanelAffichage.this.aerodromes.revalidate();
			PanelAffichage.this.aerodromes.repaint();
			
			PanelAffichage.this.trajectoires.setSize( dim ) ;
			PanelAffichage.this.trajectoires.revalidate();
			PanelAffichage.this.trajectoires.repaint();
			
			
			PanelAffichage.this.avions.setSize( dim );
			PanelAffichage.this.avions.revalidate();
			PanelAffichage.this.avions.repaint();
			
		}
	


		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	
	
	public PanelAffichage( Simulation modele) {
		this.modele = modele ;
	
		// Panels a rafraichir apres chaque changement du modele
		this.aerodromes = new AerodromesPanel( this.modele.getAerodromes() ) ;
		this.setLayer( this.aerodromes , JLayeredPane.DEFAULT_LAYER ) ;
		this.add( this.aerodromes , JLayeredPane.DEFAULT_LAYER ) ;
				
		this.balises = new BalisesPanel( this.modele.getBalises() ) ;
		this.setLayer( this.balises , new Integer(1) ) ;
		this.add( this.balises , new Integer( 1) ) ;
				
		this.trajectoires = new TrajectoiresPanel( this.modele.getTrajectoires() ) ;
		this.setLayer( this.trajectoires , new Integer(2) );
		this.add( this.trajectoires , new Integer( 2) ) ;
				
		this.avions = new AvionsPanel( this.modele.getAvions() ) ;
		this.setLayer( this.avions , new Integer( 3) ) ;
		this.add( this.avions ,new Integer( 3 ) ) ;
				
		this.addComponentListener( new Resizer() );
		
	}
	
	
	
	// TODO  METHODE CALCULER COORD_IHM d'un POINT A PARTIR
	// TODO DES COORDONNEES DU CADRE
	public Dimension coordonnees_IHM( Point p ) {
	
		
		return new Dimension(0 ,0 ) ;
	}

}
