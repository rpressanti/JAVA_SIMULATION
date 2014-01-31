package SIMULATION.View;

import SIMULATION.Datatypes.* ;
import SIMULATION.Modele.Simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;


@SuppressWarnings({ "serial", "unused" })
public class TrajectoiresPanel extends JPanel {

	private PanelAffichage parent ;
	private Simulation model ;
	
	public TrajectoiresPanel( PanelAffichage parent ) {
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		this.setOpaque( false ) ;
	}
	
	
	
	
	// TODO
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		Graphics2D g2d = (Graphics2D) g ;
	
	
	}
	
	
}
