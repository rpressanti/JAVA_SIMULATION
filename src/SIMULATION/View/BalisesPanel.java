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
	}
	
	
	
	// TODO
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		@SuppressWarnings("unused")
		Graphics2D g2d = (Graphics2D) g ;
	
	
	}
	
	
}
