/**
 * @author Pressanti Richard
 */
package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Color;
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
		
		//System.out.println( "Rafraichissement des avions" );
		
		Dimension dim = null ;
		
		
		for ( Avion avion : this.model.getAvions().values() )
		   for( Plot plot : avion.getPlots() )
		   {
			//System.out.println( "Affichage d'un plot d'ordre:" + plot.getOrder() ) ;   
			   
		   	Color color = Color.GREEN;
		   	
			 if( avion.getEnConflit() )
				 color = Color.RED ;
			   
			int plot_order = plot.getOrder() ;
			int taille = Avion.NbPlots - plot_order + 1 ;
			 
			dim = this.parent.coordonnees_IHM( plot ) ;
			int base_x = dim.width ;
			int base_y = dim.height ;

			g.setColor( color ) ;
			g.fillRect( base_x - taille , base_y - taille , 2 * taille , 2 * taille);
				   			   
		  }
		   
		  
	}
			

}
	
	
	
