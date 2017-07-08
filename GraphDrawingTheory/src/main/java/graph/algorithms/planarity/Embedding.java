package graph.algorithms.planarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.elements.Edge;
import graph.elements.Vertex;

public class Embedding<V extends Vertex, E extends Edge<V>> {
	
	private Map<V,List<E>> embedding = new HashMap<V, List<E>>();
	private Map<V, Integer> stNumbering;
	
	public Embedding(Map<V, List<E>> embedding, Map<V, Integer> stNumbering) {
		super();
		this.embedding = embedding;
		this.stNumbering = stNumbering;
	}

	/**
	 * @return the embedding
	 */
	public Map<V, List<E>> getEmbedding() {
		return embedding;
	}

	/**
	 * @param embedding the embedding to set
	 */
	public void setEmbedding(Map<V, List<E>> embedding) {
		this.embedding = embedding;
	}

	/**
	 * @return the stOrder
	 */
	public Map<V, Integer> getStNumbering() {
		return stNumbering;
	}

	/**
	 * @param stOrder the stOrder to set
	 */
	public void setStNumbering(Map<V, Integer> stOrder) {
		this.stNumbering = stOrder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Embedding [embedding=" + embedding + "]";
	}
	
	
	
	

}
