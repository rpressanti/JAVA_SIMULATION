package SIMULATION.Datatypes;

import SIMULATION.Graphe.Noeud;

public class NoeudTrajectoire extends Noeud<Segment,NoeudTrajectoire,Point>{

	public NoeudTrajectoire(Point content) {
		super( Segment.class , NoeudTrajectoire.class , Point.class , content);
		// TODO Auto-generated constructor stub
	}

	public NoeudTrajectoire clone() {
		return new NoeudTrajectoire( this.content )  ;
	}
	
}
