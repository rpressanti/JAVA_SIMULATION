package SIMULATION.Datatypes;

import SIMULATION.Graphe.Noeud;

public class NoeudTrajectoire extends Noeud<Segment,NoeudTrajectoire,Point>{

	public NoeudTrajectoire(Point content) {
		super(content);
		// TODO Auto-generated constructor stub
	}

	public NoeudTrajectoire clone() {
		return (NoeudTrajectoire) super.clone() ;
	}
	
}
