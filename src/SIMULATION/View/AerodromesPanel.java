package SIMULATION.View;

import SIMULATION.Datatypes.* ;
import SIMULATION.Modele.Simulation;

import java.awt.Dimension;
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
	}
	
	
	

	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		Graphics2D g2d = (Graphics2D) g ;
		
		if( this.model.getAerodromes().values().isEmpty() )
			return ;
		
		for ( Aerodrome aerodrome : this.model.getAerodromes().values() )
		{
			
			Dimension dim = parent.coordonnees_IHM( aerodrome ) ;
			
			int base_x = dim.width ;
			int base_y = dim.height ;
				
			// 2 traits paralleles pour aerodrme piste
			g.drawLine(base_x, base_y, base_x+10, base_y-5);
			g.drawLine(base_x+5, base_y, base_x+15, base_y-5);
			
		}
		
	}

}
