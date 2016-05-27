package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class RoadEditorMassModifiy extends JDialog implements ActionListener{
	
	private int WIDTH=230;
	private int HEIGHT=260;
	
	public RoadEditorMassModifiy(){
		
		setTitle("Mass modify");
		setLayout(null);
		
		setSize(WIDTH,HEIGHT);
		setModal(true);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
