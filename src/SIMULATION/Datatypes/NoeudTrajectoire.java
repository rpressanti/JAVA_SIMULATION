package SIMULATION.Datatypes;

import SIMULATION.Graphe.Noeud;

public class NoeudTrajectoire extends Noeud<Segment,NoeudTrajectoire,Point>{

	public NoeudTrajectoire(Point content) {
		super( Segment.class , NoeudTrajectoire.class , Point.class , content);
	}

	public NoeudTrajectoire clone() {
		return new NoeudTrajectoire( this.content )  ;
	}
	
}
