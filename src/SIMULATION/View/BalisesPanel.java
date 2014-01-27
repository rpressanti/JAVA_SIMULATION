package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class BalisesPanel extends JPanel {

	@SuppressWarnings("unused")
	private HashMap<String,Balise> model ;
	
	public BalisesPanel(HashMap<String,Balise> model ) {
		super() ;
		this.model = model ;
		
		this.setOpaque( false ) ;
	}
	
	
	
	// TODO
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		@SuppressWarnings("unused")
		Graphics2D g2d = (Graphics2D) g ;
		
		int base_x = 50 ;
		int base_y = 50 ;
		// TODO INSERER DANS ITERATION SUR LE MODELE
		int[] x_points = new int[] { base_x -5 , base_x , base_x+5 } ;
		int[] y_points = new int[] { base_y , base_y - 7 , base_y } ;
	
		g2d.drawPolygon(x_points , y_points , 3);
	}
	
	
}
