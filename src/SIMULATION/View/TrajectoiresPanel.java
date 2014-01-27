package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class TrajectoiresPanel extends PanelAffichage {

	@SuppressWarnings("unused")
	private ArrayList<Trajectoire> model ;
	
	public TrajectoiresPanel( ArrayList<Trajectoire> model ) {
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
	
	
	}
	
	
}
