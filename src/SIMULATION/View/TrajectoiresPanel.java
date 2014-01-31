package SIMULATION.View;

import SIMULATION.Datatypes.* ;
import SIMULATION.Modele.Simulation;
import SIMULATION.Graphe.* ;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


@SuppressWarnings({ "serial" })
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
		
		for( Trajectoire trajectoire : this.model.getTrajectoires() )
		{
			for( Arete<Point> segment : trajectoire )
			{
				Dimension origine = this.parent.coordonnees_IHM( segment.getOrigine().getContent() ) ;
				Dimension destination = this.parent.coordonnees_IHM( segment.getDestination().getContent() ) ;
				g2d.drawLine( origine.width , origine.height , destination.width , destination.height ) ;
			}
		}
	
	
	}
	
	
}
