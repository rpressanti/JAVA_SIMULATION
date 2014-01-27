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
	
		System.out.println( "Refresh") ;
	}
	
	
	
}
