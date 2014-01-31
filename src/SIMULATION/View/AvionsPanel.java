package SIMULATION.View;

import SIMULATION.Datatypes.* ;
import SIMULATION.Modele.Simulation;

import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JPanel;


@SuppressWarnings("serial")
public class AvionsPanel extends JPanel {


	private PanelAffichage parent ;
	private Simulation model ;
	
	public AvionsPanel( PanelAffichage parent) {
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		this.setOpaque( false );
	}
	
	
	// TODO
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		Dimension dim = null ;
		
		//@SuppressWarnings("unused")
		//Graphics2D g2d = (Graphics2D) g ;
		
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
		   
		  System.out.println( "rectangle") ;	
	
	}
			

}
	
	
	
