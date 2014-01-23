package SIMULATION.Graphe;

// CLASS DONE

public class GrapheComplet< E extends Distance<E>> extends Graphe<E> {


	public void generer() {
		
		for( Noeud<E> noeud_1 : this.noeuds.values() )
			for( Noeud<E> noeud_2 : this.noeuds.values() )
				if ( noeud_1 != noeud_2 )
				{
					noeud_1.enregistrer( new Arete<E>( noeud_1 , noeud_2 , noeud_1.getContent().distanceTo( noeud_2.getContent() ) ) ) ;
					noeud_2.enregistrer( new Arete<E>( noeud_2 , noeud_1 , noeud_2.getContent().distanceTo( noeud_1.getContent() ) ) ) ;
				}		
	}
	
	
}
