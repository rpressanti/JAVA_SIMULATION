package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AerodromesPanel extends JPanel {

	
	@SuppressWarnings("unused")
	private HashMap<String,Aerodrome> model ;
	
	public AerodromesPanel(HashMap<String,Aerodrome> model ) {
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
