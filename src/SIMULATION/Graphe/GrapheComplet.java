package SIMULATION.Graphe;

// CLASS DONE

public class GrapheComplet<A extends Arete<A,N,E>,N extends Noeud<A,N,E>, E extends Distance<E>> extends Graphe<A,N,E> {


	@SuppressWarnings("unchecked")
	public void generer() {
		
		for( N noeud_1 : this.noeuds.values() )
			for( N noeud_2 : this.noeuds.values() )
				if ( ! noeud_1.equals( noeud_2 ) )
					this.add( (A) new Arete<A,N,E>( noeud_1 , noeud_2 , noeud_1.getContent().distanceTo( noeud_2.getContent() ) ) ); 
			
	}
	
	public String toString() {
		return super.toString() ;
	}
	
	
	public static void main( String args[] ) {
		
		GrapheComplet<TestArete,TestPoint,TestContent> graphe = new GrapheComplet<TestArete,TestPoint,TestContent>() ;
		
		//Noeud<TestContent> a0 = new Noeud<TestContent>( new TestContent()) ;
		//graphe.add( a0 ) ;
		//System.out.println( a0 ) ;
		
		TestPoint a1 = new TestPoint( new TestContent()) ;
		graphe.add( a1 ) ;
		TestPoint a2 = new TestPoint( new TestContent()) ;
		graphe.add( a2 ) ;
		TestPoint a3 = new TestPoint( new TestContent()) ;
		graphe.add( a3 ) ;
		TestPoint a4 = new TestPoint( new TestContent()) ;
		graphe.add( a4 ) ;
		TestPoint a5 = new TestPoint( new TestContent()) ;
		graphe.add( a5 ) ;
		TestPoint a6 = new TestPoint( new TestContent()) ;
		graphe.add( a6 ) ;
		TestPoint a7 = new TestPoint( new TestContent()) ;
		//graphe.add( a7 ) ;
		
		graphe.generer() ;
		
		
		Chemins<TestArete,TestPoint,TestContent> plus_courts = graphe.djikstra( a1 , a7) ;
		
		//System.out.println( graphe ) ;
		System.out.println( plus_courts ) ;
		if ( a3.equals( a3))
			System.out.println( "OK" ) ;
	}
	

	
}
