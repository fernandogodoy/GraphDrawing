package gui.actions.toolbar;

import gui.commands.CommandExecutor;
import gui.commands.RemoveCommand;
import gui.main.frame.MainFrame;
import gui.model.GraphElement;
import gui.view.GraphView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

public class RemoveAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		List<GraphElement> elements = new ArrayList<GraphElement>();
		GraphView view = MainFrame.getInstance().getCurrentView();
		elements.addAll(view.getSelectionModel().getSelectedVertices());
		elements.addAll(view.getSelectionModel().getSelectedEdges());
		CommandExecutor.getInstance().execute(new RemoveCommand(elements, view));
		
	}

}