package gui.properties;

import java.util.HashMap;
import java.util.Map;

import gui.model.GraphEdge;
import gui.model.GraphModel;
import gui.model.GraphVertex;
import gui.model.IGraphElement;

public class PropertiesFactory {
	
	private static Map<Class<?>, PropertiesPanel> map = new HashMap<Class<?>, PropertiesPanel>();
	
	public static PropertiesPanel getPropertiesPanel(IGraphElement element){
		PropertiesPanel panel = map.get(element.getClass());
		if (panel == null){
			if (element instanceof GraphVertex)
				panel = new VertexPropertiesPanel();
			else if (element instanceof GraphEdge)
				panel = new EdgePropertiesPanel();
			else if (element instanceof GraphModel)
				panel = new GraphModelPropertiesPanel();
		}
		panel.setElement(element);
		return panel;
		
	}

}
