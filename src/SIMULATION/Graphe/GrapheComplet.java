package SIMULATION.Graphe;

// CLASS DONE

public class GrapheComplet< E extends Distance<E>> extends Graphe<E> {


	public void generer() {
		
		for( Noeud<E> noeud_1 : this.noeuds.values() )
			for( Noeud<E> noeud_2 : this.noeuds.values() )
				if ( ! noeud_1.equals( noeud_2 ) )
				{
					Arete<E> new_arete = new Arete<E>( noeud_1 , noeud_2 , noeud_1.getContent().distanceTo( noeud_2.getContent() ) ) ; 
					noeud_1.enregistrer( new_arete ) ;
					noeud_2.enregistrer_inverse( new_arete ) ;
				}		
	}
	
	public String toString() {
		return super.toString() ;
	}
	
	
	public static void main( String args[] ) {
		
		GrapheComplet<TestContent> graphe = new GrapheComplet<TestContent>() ;
		
		//Noeud<TestContent> a0 = new Noeud<TestContent>( new TestContent()) ;
		//graphe.add( a0 ) ;
		//System.out.println( a0 ) ;
		
		Noeud<TestContent> a1 = new Noeud<TestContent>( new TestContent()) ;
		graphe.add( a1 ) ;
		Noeud<TestContent> a2 = new Noeud<TestContent>( new TestContent()) ;
		graphe.add( a2 ) ;
		Noeud<TestContent> a3 = new Noeud<TestContent>( new TestContent()) ;
		graphe.add( a3 ) ;
		Noeud<TestContent> a4 = new Noeud<TestContent>( new TestContent()) ;
		graphe.add( a4 ) ;
		Noeud<TestContent> a5 = new Noeud<TestContent>( new TestContent()) ;
		graphe.add( a5 ) ;
		Noeud<TestContent> a6 = new Noeud<TestContent>( new TestContent()) ;
		graphe.add( a6 ) ;
		
		graphe.generer() ;
		
		
		Chemins<TestContent> plus_courts = graphe.djikstra( a1 , a6) ;
		
		//System.out.println( graphe ) ;
		System.out.println( plus_courts ) ;
		if ( a3.equals( a3))
			System.out.println( "OK" ) ;
	}
	
	
	
}
