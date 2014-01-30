package com.editors.buildings;

import java.awt.peer.PanelPeer;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class OutlinePanel extends JDialog {
	
	
	int WIDTH=300;
	int HEIGHT=400;
	private JPanel center;
	
	public OutlinePanel(){
		
		setTitle("Outline");
		setLocation(50,50);
		setSize(WIDTH,HEIGHT);
		setLayout(null);
		
		center=new JPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		setModal(true);
		
				
		
		setVisible(true);
	}

}
