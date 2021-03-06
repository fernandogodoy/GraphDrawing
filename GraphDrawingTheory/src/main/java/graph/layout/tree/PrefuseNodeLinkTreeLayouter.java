package graph.layout.tree;

import graph.elements.Edge;
import graph.elements.Vertex;
import graph.layout.AbstractPrefuseLayouter;
import graph.layout.GraphLayoutProperties;
import graph.layout.PropertyEnums.NodeLinkTreeProperties;
import prefuse.action.layout.graph.NodeLinkTreeLayout;

public class PrefuseNodeLinkTreeLayouter<V extends Vertex, E extends Edge<V>> extends AbstractPrefuseLayouter<V, E>{

	@Override
	protected void initLayouter(GraphLayoutProperties layoutProperties) {
		
		NodeLinkTreeLayout nodeLinkTreeLayouter = new NodeLinkTreeLayout("graph");
		
		
		Object orientation = layoutProperties.getProperty(NodeLinkTreeProperties.ORIENTATION);
		if (orientation != null)
			nodeLinkTreeLayouter.setOrientation((int) orientation);
		
		Object bspace = layoutProperties.getProperty(NodeLinkTreeProperties.SPACING_SIBLINGS);
		if (bspace != null)
			nodeLinkTreeLayouter.setBreadthSpacing((double) bspace);
		
		Object dspace = layoutProperties.getProperty(NodeLinkTreeProperties.SPACING_DEPTH_LEVELS);
		if (dspace != null)
			nodeLinkTreeLayouter.setDepthSpacing((double) dspace);
		
		Object tspace = layoutProperties.getProperty(NodeLinkTreeProperties.SPACING_SUBTREES);
		if (tspace != null)
			nodeLinkTreeLayouter.setSubtreeSpacing((double) tspace);
		
		Object offset = (Double) layoutProperties.getProperty(NodeLinkTreeProperties.SPACING_ROOT_NODE);
		if (offset != null)
			nodeLinkTreeLayouter.setRootNodeOffset((double) offset);
		
		layouter = nodeLinkTreeLayouter;
		
	}

}
