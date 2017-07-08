package graph.layout.straight.line;

import java.awt.geom.Point2D;
import java.util.Map;

import graph.algorithms.drawing.ConvexDrawing;
import graph.drawing.Drawing;
import graph.elements.Edge;
import graph.elements.Graph;
import graph.elements.Vertex;
import graph.layout.AbstractLayouter;
import graph.layout.GraphLayoutProperties;

public class ConvexLayouter<V extends Vertex, E extends Edge<V>> extends AbstractLayouter<V, E> {

	@Override
	public Drawing<V, E> layout(Graph<V, E> graph,
			GraphLayoutProperties layoutProperties) {
		
		ConvexDrawing<V, E> convexDrawing = new ConvexDrawing<V, E>(graph);

		Drawing<V,E> drawing = new Drawing<V,E>();

		Map<V, Point2D> vertexPositions;
		vertexPositions = convexDrawing.execute();
		System.out.println(vertexPositions);
		drawing.getVertexMappings().putAll(vertexPositions);

		return drawing;
	}

}
