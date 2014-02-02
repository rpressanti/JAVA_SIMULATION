package SIMULATION.Datatypes;

import java.util.ArrayList;

import SIMULATION.Graphe.*;

public class Segment extends Arete<Point> {
	
	private boolean has_next ;
	private ArrayList<Plot> plots ;

	public Segment( Arete<Point> arete )
	{
		super( arete.getOrigine() , arete.getDestination() , arete.getWeight() ) ;
		this.has_next = false ;
		this.plots = new ArrayList<Plot>() ;
	}
	
	public boolean has_next() {
		return this.has_next ;
	}
	
	public ArrayList<Plot> getPlots() {
		return this.plots ;
	}
}
