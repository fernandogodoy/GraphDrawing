package gui.actions.palette;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.main.frame.ElementsEnum;
import gui.main.frame.MainFrame;

public class AddVertexAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddVertexAction(){
		putValue(NAME, "Vertex");
		putValue(SHORT_DESCRIPTION, "Add vertex");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.getInstance().changeToAdd(ElementsEnum.VERTEX);
		
	}

}
