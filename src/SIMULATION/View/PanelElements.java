/**
 * @author Pressanti Richard
 */
package SIMULATION.View;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelElements extends JPanel {

	protected boolean afficher_nom ;
	protected boolean afficher_coordonnees ;

	public PanelElements() {

		super() ;
		
		this.afficher_nom = false ;
		this.afficher_coordonnees = false ;

	}
		
	public void setAffichage( boolean afficher_nom , boolean afficher_coordonnees ) {

		this.afficher_nom = afficher_nom ;
		this.afficher_coordonnees = afficher_coordonnees ;
		
	}
	
	
	
	
}
