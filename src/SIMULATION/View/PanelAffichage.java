package SIMULATION.View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


import javax.swing.JLayeredPane;

import SIMULATION.Datatypes.Point;
import SIMULATION.Modele.Simulation;

@SuppressWarnings("serial")
public class PanelAffichage extends JLayeredPane {

	// TODO ATTRIBUTS COORDONNEES GPS
	private Point gauche_haut ;
	private Point droit_bas ;
	private Point centre ;
	
	private Simulation modele ;
	
	private AerodromesPanel aerodromes ;
	private BalisesPanel balises ;
	private TrajectoiresPanel trajectoires ;
	private AvionsPanel avions ;
	
	
	
	
	
	
	
	public class Resizer implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			JLayeredPane source = ( JLayeredPane) e.getSource() ;
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
		// TODO IMPLEMENTER CADRE DANS MODELE
		this.gauche_haut = new Point( -10 , 55 ) ;
		this.droit_bas =   new Point(  10 , 40 ) ;
		this.centre = new Point(
				( this.droit_bas.getLongitude().getValue() - this.gauche_haut.getLongitude().getValue() ) / 2
				,
				( this.droit_bas.getLatitude().getValue() - this.gauche_haut.getLatitude().getValue() ) / 2
				) ;
		
		// Panels a rafraichir apres chaque changement du modele
		this.aerodromes = new AerodromesPanel( this ) ;
		this.setLayer( this.aerodromes , JLayeredPane.DEFAULT_LAYER ) ;
		this.add( this.aerodromes , JLayeredPane.DEFAULT_LAYER ) ;
				
		this.balises = new BalisesPanel( this ) ;
		this.setLayer( this.balises , new Integer(1) ) ;
		this.add( this.balises , new Integer( 1) ) ;
				
		this.trajectoires = new TrajectoiresPanel( this ) ;
		this.setLayer( this.trajectoires , new Integer(2) );
		this.add( this.trajectoires , new Integer( 2) ) ;
				
		this.avions = new AvionsPanel( this ) ;
		this.setLayer( this.avions , new Integer( 3) ) ;
		this.add( this.avions ,new Integer( 3 ) ) ;
				
		this.addComponentListener( new Resizer() );
		
	}
	
	public Simulation modele() {
		return this.modele ;
	}
	
	
	public boolean zoomer( double niveau_de_zoom ) {
		
		double demi_largeur = this.droit_bas.getLongitude().getValue() - this.centre.getLongitude().getValue() ; 
		double demi_hauteur = this.droit_bas.getLatitude().getValue()  - this.centre.getLatitude().getValue()  ;
		
		demi_largeur /= niveau_de_zoom ;
		demi_hauteur /= niveau_de_zoom ;
		
		this.gauche_haut = new Point( 
				this.centre.getLongitude().getValue() - demi_largeur
				,
				this.centre.getLatitude().getValue() - demi_hauteur
				) ;
		this.droit_bas = new Point( 
				this.centre.getLongitude().getValue() + demi_largeur
				,
				this.centre.getLatitude().getValue() + demi_hauteur
				) ;
		
		return true ;
	}
	
	
	public Dimension coordonnees_IHM( Point p ) {

		Dimension dim = this.getSize() ;
		
		int x = ( int ) ( dim.width *
				( p.getLongitude().getValue() - this.gauche_haut.getLongitude().getValue() ) 
				/ 
				( this.droit_bas.getLongitude().getValue() - this.gauche_haut.getLongitude().getValue() ) 
			) ;
		int y = ( int ) ( dim.height *
				( p.getLatitude().getValue() - this.gauche_haut.getLatitude().getValue() )
				/ 
				( this.droit_bas.getLatitude().getValue() - this.gauche_haut.getLatitude().getValue() ) 
			) ; 
		
		return new Dimension( x , y ) ;
	}

}
