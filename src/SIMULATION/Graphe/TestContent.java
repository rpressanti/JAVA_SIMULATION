package SIMULATION.Graphe;


public class TestContent implements Distance<TestContent> {

	private static Integer effectif = 0 ;
	
	private Integer id ;
	
	
	public TestContent() {
		this.id = ++ TestContent.effectif ;
	}
	
	public Integer getId() {
		return this.id ;
	}
	
	public String toString() {
		return this.id.toString() ;
	}
	
	@Override
	public double distanceTo(TestContent other) {
		return Math.abs( other.getId() - this.getId() ) ;
	}

}
