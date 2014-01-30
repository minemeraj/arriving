package com.editors.forniture;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FornitureEditor extends JFrame{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	FornitureJPanel center=null;
	
	
	public FornitureEditor(){
		
		setTitle("Buildings editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new FornitureJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		setVisible(true);
		
		initialize();
	}
	
	private void initialize() {
		
		center.initialize();
	}

	public static void main(String[] args) {
		
		FornitureEditor be=new FornitureEditor();
	}
	
	
	public void paint(Graphics arg0) {
		center.draw();
	}
}
