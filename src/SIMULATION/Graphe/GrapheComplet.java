package SIMULATION.Graphe;


// CLASS DONE

public class GrapheComplet<C extends Chemin<C,A,N,E> ,A extends Arete<A,N,E>,N extends Noeud<A,N,E>, E extends Distance<E>> extends Graphe<C,A,N,E> {


	public GrapheComplet(Class<C> classeChemin , Class<A> classeArete , Class<N> classeNoeud , Class<E> classeElement) {
		super(classeChemin , classeArete, classeNoeud, classeElement);
	}

	public void generer() {
		
		System.out.println( this.classeArete.getClass() ) ;
		
		for( N noeud_1 : this.noeuds.values() )
			for( N noeud_2 : this.noeuds.values() )
				if ( ! noeud_1.equals( noeud_2 ) )
					try {
						this.add( 
							this.classeArete.getDeclaredConstructor( 
								new Class[] { 
										this.classeNoeud , this.classeNoeud , Double.class
										} 
									).newInstance( 
											noeud_1 , noeud_2 , noeud_1.getContent().distanceTo( noeud_2.getContent() )
											) 
							) ;
					} catch ( Exception e ) {
						System.err.println( "Arete non cr��e" );
						e.printStackTrace() ; 
					}
	}
	
	public String toString() {
		return super.toString() ;
	}
	
	
	// DONE
	public GrapheComplet<C,A,N,E> clone() {
		
		GrapheComplet<C,A,N,E> result = new GrapheComplet<C,A,N,E>( this.classeChemin , this.classeArete , this.classeNoeud , this.classeElement ) ;
		
		// Copier les noueds
		for( N noeud : this.noeuds.values() )
			result.add( noeud ) ;

		// Recreer les aretes
		for( N noeud : this.noeuds.values() )
			for( A arete : noeud.getAretes().values() )
				result.add( arete.getOrigine().getContent() , arete.getDestination().getContent() , arete.getWeight() ) ;
		
		return result ;
	}
	
	
	
	public static void main( String args[] ) {
		
		GrapheComplet<TestChemin,TestArete,TestPoint,TestContent> graphe = new GrapheComplet<TestChemin,TestArete,TestPoint,TestContent>( TestChemin.class , TestArete.class , TestPoint.class , TestContent.class ) ;
		
		TestPoint a0 = new TestPoint( new TestContent()) ;
		graphe.add( a0 ) ;
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
		graphe.add( a7 ) ;
		
		graphe.generer() ;
		
		
		Chemins<TestChemin,TestArete,TestPoint,TestContent> plus_courts = graphe.djikstra( a1 , a7) ;
		
		Chemin<TestChemin,TestArete,TestPoint,TestContent> test = plus_courts.random();
		System.out.println( test );
		
		//System.out.println( graphe ) ;
		//System.out.println( plus_courts ) ;
		//if ( a3.equals( a3))
		//	System.out.println( "OK" ) ;
	}
	

	
}
