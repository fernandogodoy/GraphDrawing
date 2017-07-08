package gui.properties;

import javax.swing.JPanel;

import gui.model.IGraphElement;

public abstract class PropertiesPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public abstract void setValues();
	
	public abstract void setElement(IGraphElement element);

}
