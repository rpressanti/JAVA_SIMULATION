package SIMULATION.View;

import SIMULATION.Datatypes.* ;
import SIMULATION.Modele.Simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;

@SuppressWarnings({ "serial", "unused" })
public class AerodromesPanel extends JPanel {

	private PanelAffichage parent ;
	private Simulation model ;
	
	public AerodromesPanel( PanelAffichage parent ) {
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		this.setOpaque( false ) ;
		//this.setBackground( Color.BLUE );
	}
	
	
	
	
	// TODO
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		Graphics2D g2d = (Graphics2D) g ;
		// 2 traits paralleles pour aerodrme piste
		int base_x = 150 ;
		int base_y = 150 ;
		
		// TODO INSERER DANS ITERATION SUR LE MODELE
		g.drawLine(base_x, base_y, base_x+20, base_y-15);
		g.drawLine(base_x+10, base_y, base_x+30, base_y-15);
	
	}

}
