package com.editors.buildings;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BuildingsEditor extends JFrame{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	BuildingJPanel center=null;
	
	
	public BuildingsEditor(){
		
		setTitle("Buildings editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new BuildingJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		setVisible(true);
		
		initialize();
	}
	
	private void initialize() {
		
		center.initialize();
	}

	public static void main(String[] args) {
		
		BuildingsEditor be=new BuildingsEditor();
	}
}
