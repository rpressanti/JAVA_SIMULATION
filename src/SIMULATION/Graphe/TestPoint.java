package SIMULATION.Graphe;


public class TestPoint extends Noeud<TestArete,TestPoint,TestContent>{

	public TestPoint(TestContent content) {
		super( TestArete.class , TestPoint.class , TestContent.class , content );
	}

}
