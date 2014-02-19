package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Dimension;
import java.awt.Graphics;


// CLASS DONE

@SuppressWarnings("serial")
public class BalisesPanel extends PanelElements {

	private PanelAffichage parent ;
	private InterfaceModele model ;
	
	public BalisesPanel( PanelAffichage parent ) {
		
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		
		this.setOpaque( false ) ;
	}
	
	
	

	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		
		if( this.model.getBalises().isEmpty() )
			return ;
		
		
		for( Balise balise : this.model.getBalises().values() )
		{
			//System.out.println( balise );
			
			Dimension dim = parent.coordonnees_IHM( balise) ;
			
			//System.out.println( dim ) ;
			
			int base_x = dim.width ;
			int base_y = dim.height ;
			
			int[] x_points = new int[] { base_x -5 , base_x , base_x+5 } ;
			int[] y_points = new int[] { base_y , base_y - 7 , base_y } ;
			
			g.drawPolygon(x_points , y_points , 3);
			//g.drawString(balise.getIndicatif(), base_x+5, base_y);
			
			if( this.afficher_nom )
				g.drawString(balise.getIndicatif(), base_x - 5 , base_y - 10);
			
			if( this.afficher_coordonnees )
			{
				g.drawString( balise.getLongitude().toString() , base_x - 5, base_y - 25 );
				g.drawString( balise.getLatitude().toString()  , base_x - 5, base_y - 40 );
			}
			
			
				
		}
		
		//System.out.println( "OK" ) ;
		
	}
	
}
