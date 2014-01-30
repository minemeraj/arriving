package com.editors.animals;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnimalsEditor extends JFrame{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	AnimalsJPanel center=null;
	
	
	public AnimalsEditor(){
		
		setTitle("Buildings editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new AnimalsJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		setVisible(true);
		
		initialize();
	}
	
	private void initialize() {
		
		center.initialize();
	}

	public static void main(String[] args) {
		
		AnimalsEditor be=new AnimalsEditor();
	}
	
	
	public void paint(Graphics arg0) {
		center.draw();
	}
}
