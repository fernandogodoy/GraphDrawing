package graph.symmetry.nauty;

import java.util.List;

import graph.elements.Edge;
import graph.elements.Graph;
import graph.elements.Vertex;

public class BinaryRepresentation<V extends Vertex, E extends Edge<V>> {

	private Graph<V,E> graph;
	
	public BinaryRepresentation(Graph<V,E> graph){
		this.graph = graph;
	}
	
	
	public String binaryRepresenatation(List<V> verticeList){
		String representation = "";
		for (int i = 0; i < verticeList.size(); i++){
			V v1 = verticeList.get(i);
			for (int j = i + 1; j < verticeList.size(); j++){
				V v2 = verticeList.get(j);
				if (graph.edgeBetween(v1, v2) != null)
					representation += "1";
				else
					representation += "0";
			}
		}
		return representation;
	}
}
