package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class AvionsPanel extends JPanel {

	@SuppressWarnings("unused")
	private HashMap<String,Avion> model ;
	
	public AvionsPanel(HashMap<String,Avion> model ) {
		super() ;
		this.model = model ;
		
		this.setOpaque( false );
		
		//this.setBackground( Color.RED );
	}
	
	
	// TODO
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		@SuppressWarnings("unused")
		Graphics2D g2d = (Graphics2D) g ;
		
		
		
		
		
		   
		//for ( Avion avion : this.model.values() )
		//   for( Plot plot : avion.getPlots() )
		   //{
		   	
			//int plot_order = plot.getOrder() ;
			int plot_order = 0 ;
			// TODO TRADUIRE EN COORDONNEES IHM
			// int base_x = plot.getLongitude() ;
			// int base_y = plot.getLatitude() ;
			int base_x = 150 ;
			int base_y = 150 ;
			
			int taille = Avion.NbPlots - plot_order + 1 ;
		  
			//rectangle pour avion
		   //int[] x_points = new int[] { base_x - taille , base_x+10, base_x+20, base_x+30  } ;
		   //int[] y_points = new int[] { base_y , base_y-15} ;
		   //int[] l_points = new int[] { base_l , base_l-10, base_l-15, base_l-20  } ;
		   //int[] h_points = new int[] { base_h , base_h-5,base_h-10,base_h-15}; 
		   
		   g.drawRect( base_x - taille , base_y - taille , 2 * taille , 2 * taille);
				   			   
		  //}
		   
		  System.out.println( "rectangle") ;	
	
	}
			

}
	
	
	
