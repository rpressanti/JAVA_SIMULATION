package SIMULATION.View;

import SIMULATION.Datatypes.* ;

import java.awt.Dimension;
import java.awt.Graphics;


// CLASS DONE

@SuppressWarnings({ "serial" })
public class TrajectoiresPanel extends PanelElements {

	private PanelAffichage parent ;
	private InterfaceModele model ;
	
	public TrajectoiresPanel( PanelAffichage parent ) {
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;
		
		this.setOpaque( false ) ;
	}
	
	
	
	

	public void paintComponent(Graphics g) 
	{
		
		super.paintComponent( g ) ;
		
		for( Trajectoire trajectoire : this.model.getTrajectoires().values() )
		{
			for( Segment segment : trajectoire )
			{
				Dimension origine = this.parent.coordonnees_IHM( segment.getOrigine().getContent() ) ;
				Dimension destination = this.parent.coordonnees_IHM( segment.getDestination().getContent() ) ;
				g.drawLine( origine.width , origine.height , destination.width , destination.height ) ;
			}
		}
	
	
	}
	
	
}
