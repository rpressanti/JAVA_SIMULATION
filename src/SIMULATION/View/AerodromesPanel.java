/**
 * @author Nora Gasmi
 * @author Richard Pressanti
 * Cette classe permet de symboliser un aérodrome par 2 pistes.
 * Elle permet aussi de les placer dans l'IHM.
 * Elle permet d'afficher le nom et les coordonnées d'un aérodrome
 */

package SIMULATION.View;


// CLASS DONE

import SIMULATION.Datatypes.* ;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


@SuppressWarnings({ "serial"})
public class AerodromesPanel extends PanelElements {

	private PanelAffichage parent ;
	private InterfaceModele model ;
	
	
	
	public AerodromesPanel( PanelAffichage parent ) {
		
		super() ;
		this.parent = parent ;
		this.model = this.parent.modele() ;

		//System.out.println( "Taille:" + this.parent.getSize() ) ;
		this.setBackground(Color.white);
		this.setOpaque( true ) ;
	}

	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent( g ) ;
		
		
		if( this.model.getAerodromes().isEmpty() )
			return ;
		
		for ( Aerodrome aerodrome : this.model.getAerodromes().values() )
		{
			
			Dimension dim = parent.coordonnees_IHM( aerodrome ) ;
			
			int base_x = dim.width ;
			int base_y = dim.height ;
				
			// 2 traits paralleles pour aerodrme piste
			g.drawLine(base_x, base_y, base_x+10, base_y-5);
			g.drawLine(base_x+5, base_y, base_x+15, base_y-5);
			
			if( this.afficher_nom )
				g.drawString(aerodrome.get_code_OACI(), base_x , base_y + 15 );
			
			if( this.afficher_coordonnees )
			{
				g.drawString( aerodrome.getLongitude().toString() , base_x , base_y + 30);
				g.drawString( aerodrome.getLatitude().toString() , base_x , base_y + 45);
			}
			
		}
		
	}

}
