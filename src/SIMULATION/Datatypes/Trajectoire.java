package SIMULATION.Datatypes;

import java.util.ArrayList;

import SIMULATION.Graphe.*;


@SuppressWarnings("serial")
public class Trajectoire extends Chemin<Trajectoire,Segment,NoeudTrajectoire,Point> {
	
	
	public Trajectoire() {
		super( Trajectoire.class , Segment.class , NoeudTrajectoire.class ) ;
	}
	

	public String toString() {

		String string = "" ;
		
		if( this.isEmpty() )
			return "" ;
		else
			string = this.get( 0 ).getOrigine().getContent().toString() ;
			
		
		for( Segment arete : this ) 
		{
			if ( string != "" )
				string += "|" ;
			string += arete.getDestination().getContent().toString() ;
		}
		
		return string ;
	}
	
	
	public ArrayList<Repere> getRepereValide() {
		
		ArrayList<Repere> result = new ArrayList<Repere>() ;
		
		for( Segment segment : this )
		{
			Point candidat = segment.getDestination().getContent() ;
			if( candidat instanceof Repere )
				result.add( (Repere) candidat ) ;
			
		}
		
		return result ;
		
	}
	
	

}
