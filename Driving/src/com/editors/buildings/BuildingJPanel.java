package com.editors.buildings;

import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BuildingJPanel extends JPanel{

	private Graphics2D graph;

	public void initialize() {
		
		graph=(Graphics2D)getGraphics();
		
	}

}
