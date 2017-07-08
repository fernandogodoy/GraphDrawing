package graph.algorithms.planarity.dual;

import java.util.ArrayList;
import java.util.List;

import graph.algorithms.planarity.PlanarFaces;
import graph.elements.Edge;
import graph.elements.Graph;
import graph.elements.Vertex;
import graph.exception.NotPlanarException;

/**
 * Given a planar st-graph G, the dual planar st-graph G = (V ∗, E∗)
is a digraph with the following properties:
• V* is the set of faces in G with the addition that the external face (s, . . . , t, . . . , s)
is broken into two parts s∗ representing the portion of the face from s to t and
t∗ representing the portion from t to s.
• For every edge e in E, we have an edge e* = (f, g) ∈ E∗
 where f is the face the left of e and g is the face to the right of e.
 * @author xx
 *
 * @param <V>
 * @param <E>
 */
public class STDualGraph<V extends Vertex, E extends Edge<V>> extends Graph<DualGraphVertex<V, E>, DualGraphEdge<V, E>>{

	private Graph<V,E> graph;
	private List<E> externalFace;
	private V s, t;
	private PlanarFaces<V,E> planarFaces;
	private List<E> sStar, tStar;


	public STDualGraph(Graph<V,E> graph, List<E> externalFace, V s, V t){
		this.graph = graph;
		this.externalFace = externalFace;
		this.s = s;
		this.t = t;
		setDirected(true);
		constructDualGraph();
	}

	public STDualGraph(Graph<V,E> graph, List<E> externalFace, V s, V t, PlanarFaces<V,E> planarFaces){
		this.graph = graph;
		this.externalFace = externalFace;
		this.s = s;
		this.t = t;
		setDirected(true);
		constructDualGraph();
	}

	@SuppressWarnings("unchecked")
	private void constructDualGraph(){

		if (planarFaces == null){
			planarFaces = new PlanarFaces<V,E>(graph);
			try {
				planarFaces.formFaces(s, t);
			} catch (NotPlanarException e) {
				e.printStackTrace();
			}
		}

		//The external face s...t...s should be divided into two parts
		//s* is a part from s to t
		//and t* from t to s

		sStar = new ArrayList<E>();
		tStar = new ArrayList<E>();
		int sIndex = -1, tIndex = -1;

		//go from the first edge, when s is encountered, keep adding edges to s* until
		//t is encountered
		//if t is encountered first, keep adding edges to t* until s is encountered
		//then go from the first edge again and completed the uncompleted part

		V first = externalFace.get(0).getOrigin();
		if (externalFace.get(1).getOrigin() == first || externalFace.get(1).getDestination() == first)
			first = externalFace.get(0).getDestination();

		V current = first;
		boolean sCompleted = false, tCompleted = false;

		for (int i = 0; i < externalFace.size(); i++){
			E e = externalFace.get(i);
			if (current == s && sIndex == -1){
				sIndex = i;
				if (tIndex != -1)
					tCompleted = true;
			}
			else if (current == t && tIndex == -1){
				tIndex = i;
				if (sIndex != -1)
					sCompleted = true;
			}
			if (sIndex != -1 && tIndex == -1)
				sStar.add(e);
			else if (tIndex != -1)
				tStar.add(e);
			current = e.getOrigin() == current ? e.getDestination() : e.getOrigin();
		}

		if (!sCompleted)
			for (int i = 0; i < tIndex; i++)
				sStar.add(externalFace.get(i));

		if (!tCompleted)
			for (int i = 0; i < sIndex; i++)
				tStar.add(externalFace.get(i));

		System.out.println(sStar);
		System.out.println(tStar);


		List<List<E>> allFaces = planarFaces.getAllFaces();


		//won't be the same reference, so find the external face in the list of all faces
		List<E> externalFaceInList = null;


		for (List<E> face : allFaces){
			if (externalFaceInList == null && face.size() == externalFace.size()){
				//check if this is external face
				boolean ok = true;
				for (E e : face)
					if (!externalFace.contains(e)){
						ok = false;
						break;
					}
				if (ok){
					externalFaceInList = face;
					continue;
				}
			}
			DualGraphVertex<V, E> vert = new DualGraphVertex<>(face);
			addVertex(vert);
		}

		//create vertices for s* and t*

		DualGraphVertex<V, E> sVert = new DualGraphVertex<>(sStar);
		addVertex(sVert);
		DualGraphVertex<V, E> tVert = new DualGraphVertex<>(tStar);
		addVertex(tVert);

		//now form edges
		//link left and right faces for each edge of graph
		//if one of the faces is the external face
		//link the other face with either s* or t*
		for (E e : graph.getEdges()){
			DualGraphEdge<V, E> edge;
			List<E> leftFace = planarFaces.leftFaceOf(e);
			List<E> rightFace = planarFaces.rightFaceOf(e);
			if (leftFace != externalFaceInList && rightFace != externalFaceInList){
				edge = new DualGraphEdge<>(getVertexByContent(leftFace), getVertexByContent(rightFace));
			}
			else if (leftFace == externalFaceInList){
				if (sStar.contains(e))
						edge = new DualGraphEdge<>(sVert, getVertexByContent(rightFace));
					else
						edge = new DualGraphEdge<>(tVert, getVertexByContent(rightFace));
			}
			else{
				if (sStar.contains(e))
					edge = new DualGraphEdge<>(getVertexByContent(leftFace), sVert);
				else
					edge = new DualGraphEdge<>(getVertexByContent(leftFace), tVert);
			}
			addEdge(edge);
		}




	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ret = "STDualGraph vertices: \n";
		for (DualGraphVertex<V, E> vert : vertices)
			ret += vert.toString() + " \n";
		ret += " edges: \n";
		for (DualGraphEdge<V, E> e : edges)
			ret += e.toString() + " \n";
		
		return ret;
	}

	public List<E> getExternalFace() {
		return externalFace;
	}

	public V getS() {
		return s;
	}

	public V getT() {
		return t;
	}

	public List<E> getsStar() {
		return sStar;
	}

	public List<E> gettStar() {
		return tStar;
	}


}
