package graph.algorithms.numbering;

import java.util.List;

import graph.elements.Edge;
import graph.elements.Vertex;

public abstract class Numbering<V extends Vertex,  E extends Edge<V>> {
	
	protected List<V> order;
	
	public int numberOf(V v){
		return order.indexOf(v) + 1;
	}

	public List<V> getOrder() {
		return order;
	}
	
	

}
