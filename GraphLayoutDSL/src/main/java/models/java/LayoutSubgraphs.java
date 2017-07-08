package models.java;

import java.util.List;

import interfaces.ILayoutGraph;
import interfaces.ILayoutSubgraphs;

public class LayoutSubgraphs implements ILayoutSubgraphs{

	private List<ILayoutGraph> subgraphs;
	
	public LayoutSubgraphs(List<ILayoutGraph> subgraphs){
		this.subgraphs = subgraphs;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ret = "";
		for (ILayoutGraph subgraph : subgraphs)
			ret += subgraph.toString();
		return ret;
	}


	public List<ILayoutGraph> getSubgraphs() {
		return subgraphs;
	}

}
