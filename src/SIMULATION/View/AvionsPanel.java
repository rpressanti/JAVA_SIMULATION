package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Dimension;
import java.awt.Graphics;


// CLASS DONE

@SuppressWarnings("serial")
public class AvionsPanel extends PanelElements {


	private PanelAffichage parent ;
	private InterfaceModele model ;
	
	public AvionsPanel( PanelAffichage parent) {
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		this.setOpaque( false );
	}
	
	

	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		Dimension dim = null ;
		
		
		for ( Avion avion : this.model.getAvions().values() )
		   for( Plot plot : avion.getPlots() )
		   {
		   	
			int plot_order = plot.getOrder() ;
			int taille = Avion.NbPlots - plot_order + 1 ;
			 
			dim = this.parent.coordonnees_IHM( plot ) ;
			int base_x = dim.width ;
			int base_y = dim.height ;
			
			g.drawRect( base_x - taille , base_y - taille , 2 * taille , 2 * taille);
				   			   
		  }
		   
		  
	}
			

}
	
	
	
