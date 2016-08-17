package graph.algorithms.planarity;

import graph.algorithms.numbering.STNumbering;
import graph.elements.Edge;
import graph.elements.Graph;
import graph.elements.Vertex;
import graph.traversal.DijkstraAlgorithm;
import graph.tree.pq.PQNodeType;
import graph.tree.pq.PQTree;
import graph.tree.pq.PQTreeEdge;
import graph.tree.pq.PQTreeNode;
import graph.tree.pq.PQTreeReduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PQTreePlanarity<V extends Vertex, E extends Edge<V>> extends PlanarityTestingAlgorithm<V, E>{

	private List<V> stOrder;
	private Graph<V,E> graph;
	
	/**
	 * A map containing graph (subgraphs G1,G2...Gn)
	 * and the lists of virtual edges turning them into
	 * Gk'
	 */
	private Map<Graph<V,E>, List<E>> gPrimMap; 

	private DijkstraAlgorithm<PQTreeNode, PQTreeEdge> dijkstra = new DijkstraAlgorithm<PQTreeNode, PQTreeEdge>();
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isPlannar(Graph<V, E> graph) {

		this.graph = graph;
		gPrimMap = new HashMap<Graph<V,E>, List<E>>();

		//assign st-numbers to all vertices of G
		//s and t should be connected, but it is not stated
		//that they should meet any special condition
		//so, let st be the first edge
		E st = graph.getEdges().get(0);
		V s = st.getOrigin();
		V t = st.getDestination();
		STNumbering<V, E> stNumbering = new STNumbering<V,E>(graph, s, t);
		stOrder = stNumbering.getOrder();
		final Map<V, Integer> stMapping = stNumbering.getNumbering();

		System.out.println("s " + s);
		System.out.println("t " + t);
		System.out.println("st order: " + stOrder);

		//construct a PQ-tree corresponding to G1'
		Graph<V,E> g = constructGk(1);
		PQTree<V, E> T = new PQTree<>(g, gPrimMap.get(g), stMapping);
		List<E> U = gPrimMap.get(g); //the list of edges whose lower numbered vertex is 1

		//S - the set of edges whose higher-numbered vertex is j
		List<PQTreeNode> S = new ArrayList<PQTreeNode>();
		//S' - the set of edges whose lower-numbered vertex is j
		List<PQTreeNode> Sprim = new ArrayList<PQTreeNode>();

		PQTreeReduction<V, E> treeReduction = new PQTreeReduction<V,E>();
		
		//S sets should contains leaves
		//leaves have higher indexes
		//if j is higher, add j node
		//if j is lower, add the other node 

		List<PQTreeNode> nodesWithContent = new ArrayList<PQTreeNode>();
		for (int j = 1; j < stOrder.size() - 1; j++){

			System.out.println("Current j: " + j);
			System.out.println("Current tree:" + T);
			//pq nodes adjacent to the j vertex will be added to S

			//pay attention to the fact that there could be more leaves that contain the same vertex

			nodesWithContent.clear();
			for (PQTreeNode node : T.getVertices())
				if (node.getContent() == stOrder.get(j))
					nodesWithContent.add(node);

			S.clear();
			Sprim.clear();

			for (PQTreeNode jNode : nodesWithContent){
				for (PQTreeEdge edge : T.adjacentEdges(jNode)){
					PQTreeNode other = edge.getOrigin() == jNode ? edge.getDestination() : edge.getOrigin();
					if (stMapping.get(((V)other.getContent())) < j)
						S.add(jNode);
					else
						Sprim.add(other);
				}
			}
			
			//sort S so that lower indexes are processed later
			//bottom up
			
			Collections.sort(S, new Comparator<PQTreeNode>() {

				@Override
				public int compare(PQTreeNode node1, PQTreeNode node2) {
					PQTreeNode parent1 = node1.getParent(), parent2 = node2.getParent();
					
					if (parent1.getType() == PQNodeType.Q)
						parent1 = parent1.getParent();
					
					if (parent2.getType() == PQNodeType.Q)
						parent2 = parent2.getParent();
					
					//parent are now P nodes
					V v1 = (V) parent1.getContent();
					V v2 = (V) parent2.getContent();
					
					if  (stMapping.get(v1) < stMapping.get(v2))
						return 1;
					else if  (stMapping.get(v1) == stMapping.get(v2))
						return 0;
					else return -1;
					
				}
			});
			
			System.out.println("S: " + S);

			//bubble(T,S)
			//reduce(T,S)
			if (!treeReduction.bubble(T, S) ||
					!treeReduction.reduce(T, S))
				return false;

			//S' - the set of edges whose lower-numbered index is i
			//if root(T,S) is a Q-node
			//that is, the check if the root of the pertinent subtree is a Q-node
			//a pertinent subtree is a tree of minimal height whose frontier contains all S nodes
			
			PQTreeNode pertRoot = pertinentSubtreeRoot(T, S);
			System.out.println("PERTINENT SUBTREE ROOT: " + pertRoot);
			
			if (pertRoot.getType() == PQNodeType.Q){
				//replace the full children of root(T,S) and their
				//descendants by T(S', S')
				System.out.println("Replace the full children of root(T,s) and their descentants by T(S', S')");
			}
			else{
				//else replace root(T,S) and its descendants by T(S', S')
				g = constructGk(j + 1);
				T = new PQTree<>(g, gPrimMap.get(g), stMapping);
				//U = U - S union S'
			}

		}



		return false;
	}


	private Graph<V,E> constructGk(int k){
		//vertices in the subgraph
		List<V> vertices = stOrder.subList(0,k); //the second index is exclusive

		Graph<V,E> Gk = graph.subgraph(vertices);
		List<E> virtualEdges = new ArrayList<E>();
		for (E e : graph.getEdges())
			if (vertices.contains(e.getOrigin()) && !vertices.contains(e.getDestination()) || 
					vertices.contains(e.getDestination()) && !vertices.contains(e.getOrigin()))
				virtualEdges.add(e);

		gPrimMap.put(Gk, virtualEdges);
		return Gk;

	}
	
	/**
	 * Find the root of the subtree of minimum height that contains all nodes of S
	 * @param T Tree
	 * @param S Set of nodes that should be contained
	 * @return root of the pertinent subtree
	 */
	private PQTreeNode pertinentSubtreeRoot(PQTree<V,E> T, List<PQTreeNode> S){
		
		//find path from each of the nodes of the set to the root of the tree and
		//see where they come together
		dijkstra.setEdges(T.getEdges());
		List<List<PQTreeNode>> paths = new ArrayList<List<PQTreeNode>>();
		
		//System.out.println(T.getEdges());
		
		for (PQTreeNode node : S){
			paths.add(dijkstra.getPath(node, T.getRoot()).pathVertices());
		}
		
		List<PQTreeNode> aPath = paths.get(0);
		
		PQTreeNode root = null;
		
		for (int i = aPath.size() - 1; i >0; i--){
			boolean doesnContain = false;
			for (List<PQTreeNode> path : paths){
				if (path.contains(aPath.get(i)))
					root = aPath.get(i);
				else{
					doesnContain = true;
					break;
				}
				if (doesnContain)
					break;
			}
		}
		
		return root;
	}
	
}