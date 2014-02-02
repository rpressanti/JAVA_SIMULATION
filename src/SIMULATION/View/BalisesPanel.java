package SIMULATION.View;

import SIMULATION.Datatypes.* ;
import SIMULATION.Modele.Simulation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class BalisesPanel extends JPanel {

	private PanelAffichage parent ;
	private Simulation model ;
	
	public BalisesPanel( PanelAffichage parent ) {
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		this.setOpaque( false ) ;
	}
	
	
	

	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		Graphics2D g2d = (Graphics2D) g ;
		
		for( Balise balise : this.model.getBalises().values() )
		{
			Dimension dim = parent.coordonnees_IHM( balise) ;
			
			int base_x = dim.width ;
			int base_y = dim.height ;
			
			int[] x_points = new int[] { base_x -5 , base_x , base_x+5 } ;
			int[] y_points = new int[] { base_y , base_y - 7 , base_y } ;
			
			g2d.drawPolygon(x_points , y_points , 3);
		}
	}
	
}
