package graph.symmetry.nauty;

import java.util.ArrayList;
import java.util.List;

import graph.elements.Vertex;

public class SearchTree<V extends Vertex>{
	
	private SearchTreeNode<V> root;
	
	public SearchTree(OrderedPartition<V> rootPartition){
		root = new SearchTreeNode<V>(rootPartition, null, null);

	}
	
	
	public List<SearchTreeNode<V>> getTerminalNodes(){
		List<SearchTreeNode<V>> ret = new ArrayList<SearchTreeNode<V>>();
		getTerminalNodes(ret, root);
		return ret;
		
	}
	
	private void getTerminalNodes(List<SearchTreeNode<V>> terminalNodes, SearchTreeNode<V> currentNode){
		if (currentNode.getChildren().size() == 0)
			terminalNodes.add(currentNode);
		else{
			for (SearchTreeNode<V> child : currentNode.getChildren())
				getTerminalNodes(terminalNodes, child);
		}
			
		
	}

	public SearchTreeNode<V> getRoot() {
		return root;
	}

	public void setRoot(SearchTreeNode<V> root) {
		this.root = root;
	}
	

}
