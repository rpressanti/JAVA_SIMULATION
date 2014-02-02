package SIMULATION.Datatypes;

import SIMULATION.Graphe.*;


@SuppressWarnings("serial")
public class Trajectoire extends Chemin<Point> {
	
	public String toString() {

		String string = "" ;
		
		if( this.isTrivial() )
			return "" ;
		else
			string = this.get( 0 ).getOrigine().getContent().toString() ;
			
		
		for( Arete<Point> arete : this ) 
		{
			if ( string != "" )
				string += "|" ;
			string += arete.getDestination().getContent().toString() ;
		}
		
		return string ;
	}
	
	

}
