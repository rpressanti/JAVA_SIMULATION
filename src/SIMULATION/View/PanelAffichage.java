package SIMULATION.View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLayeredPane;

import SIMULATION.Datatypes.Point;

// CLASS DONE

@SuppressWarnings("serial")
public class PanelAffichage extends JLayeredPane implements MouseWheelListener {

	public static final Double echelle_min_aff_nom = 75.0 ;
	public static final Double echelle_min_aff_coord = 250.0 ;
	
	private Point gauche_haut ;
	private Point droit_bas ;
	private Point centre ;

	private Double echelle ;
	
	private InterfaceModele modele ;
	
	private Dimension dimension ;
	
	private AerodromesPanel aerodromes ;
	private BalisesPanel balises ;
	private TrajectoiresPanel trajectoires ;
	private AvionsPanel avions ;
	
	
	
	
	
	
	
	public class Resizer implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			
			Dimension dim = ( (Component) e.getSource()).getSize() ;
			
			// On rend les panels carr�s
			int min = dim.width ;
			if ( dim.height < dim.width )
				min = dim.height ;
			
			PanelAffichage.this.dimension  = new Dimension( min , min ) ;  

			
			
	
			PanelAffichage.this.balises.setSize( PanelAffichage.this.dimension  );
			PanelAffichage.this.aerodromes.setSize( PanelAffichage.this.dimension  ) ;
			PanelAffichage.this.trajectoires.setSize( PanelAffichage.this.dimension  ) ;
			PanelAffichage.this.avions.setSize( PanelAffichage.this.dimension  );

			
			PanelAffichage.this.rafraichir() ;
			
		}
	


		@Override
		public void componentMoved(ComponentEvent e) {
			
		}

		@Override
		public void componentShown(ComponentEvent e) {
			
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			
		}
		
	}
	
	
	
	
	
	
	
	
	public PanelAffichage( InterfaceModele modele) {

		this.modele = modele ;
		// TODO IMPLEMENTER CADRE DANS MODELE
		this.gauche_haut = new Point( -10 , 55 ) ;
		this.droit_bas =   new Point(  15 , 40 ) ;
		this.centre = new Point(
				( this.droit_bas.getLongitude().getValue() + this.gauche_haut.getLongitude().getValue() ) / 2
				,
				( this.droit_bas.getLatitude().getValue() + this.gauche_haut.getLatitude().getValue() ) / 2
				) ;
		
		this.setPreferredSize( 
				new Dimension( Fenetre.width_panel , Fenetre.height_panel )
			);

		this.echelle = this.getSize().width / ( this.droit_bas.getLongitude().getValue() - this.gauche_haut.getLongitude().getValue() ) ;
		
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
		this.addMouseWheelListener( this );
		
		this.revalidate();
		this.repaint();
		
	}
	
	public InterfaceModele modele() {
		return this.modele ;
	}
	
	
	public void rafraichir() {
		
		boolean afficher_nom = false ;
		boolean afficher_coord = false ;
		
		// On recalcule l'echelle
		this.echelle = this.getSize().width / ( this.droit_bas.getLongitude().getValue() - this.gauche_haut.getLongitude().getValue() ) ;
		if( this.echelle > PanelAffichage.echelle_min_aff_nom )
			afficher_nom = true ;
		if( this.echelle > PanelAffichage.echelle_min_aff_coord )
			afficher_coord = true ;
		//System.out.println( "Echelle:" + echelle );
					
		this.balises.setAffichage( afficher_nom , afficher_coord );
		this.aerodromes.setAffichage( afficher_nom , afficher_coord );			
		this.trajectoires.setAffichage( afficher_nom , afficher_coord );
		this.avions.setAffichage( afficher_nom , afficher_coord );
				
		
		this.balises.revalidate();
		this.balises.repaint();
		
		this.aerodromes.revalidate();
		this.aerodromes.repaint();
		
		this.trajectoires.revalidate();
		this.trajectoires.repaint();
		
		this.avions.revalidate();
		this.avions.repaint();
				
	}
	
	
	
	
	public boolean zoomer( double niveau_de_zoom ) {
		
		double demi_largeur = this.droit_bas.getLongitude().getValue() - this.centre.getLongitude().getValue() ; 
		double demi_hauteur = this.droit_bas.getLatitude().getValue()  - this.centre.getLatitude().getValue()  ;
		
		//System.out.println( "Gauche Haut:" + this.gauche_haut ) ;
		//System.out.println( "Droit Bas  :" + this.droit_bas ) ;
		//System.out.println( this.centre ) ;
		
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
		
		//System.out.println( "Gauche Haut:" + this.gauche_haut ) ;
		//System.out.println( "Droit Bas  :" + this.droit_bas ) ;
		//System.out.println( this.centre ) ;
		
		this.rafraichir() ;
		
		return true ;
	}
	
	
	public Dimension coordonnees_IHM( Point p ) {

		Dimension dim = this.dimension ;
		
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	
		double zoom = 1.0 ;
		double rotation = arg0.getWheelRotation() ;
		if ( rotation > 0 )
			zoom = 1.1 ;
		else
			zoom = 0.9 ;
		
		this.zoomer( zoom * Math.abs(rotation) ) ;

		}

}
