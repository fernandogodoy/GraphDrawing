package gui.util;

import javax.swing.JOptionPane;

import gui.main.frame.MainFrame;

public class GuiUtil {

	public static int showConfirmDialogCancel(String message){
		return JOptionPane.showConfirmDialog(MainFrame.getInstance(), message, "Confirm", 
				JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public static int showConfirmDialog(String message){
		return JOptionPane.showConfirmDialog(MainFrame.getInstance(), message, "Confirm",
				JOptionPane.YES_NO_OPTION);
	}
	
}
