package gui.actions.palette;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.main.frame.MainFrame;

public class SelectAction  extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectAction(){
		putValue(NAME, "Select");
		putValue(SHORT_DESCRIPTION, "Select elements");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.getInstance().changeToSelect();

	}
}