package gui.actions.palette;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.main.frame.MainFrame;

public class LinkAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LinkAction(){
		putValue(NAME, "Edge");
		putValue(SHORT_DESCRIPTION, "Add edge");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.getInstance().changeToLink();
		
	}

}
