package com.main.loader;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LoadingProgressPanel extends JDialog{

	private JPanel center;
	
	private int WIDTH=240;
	private int HEIGHT=150;

	private JProgressBar progressBar;
	
	String progressDataString="Loading data <PERC>%";

	private JLabel progressLabal;

	public LoadingProgressPanel(){

		setTitle("Loading...");
		setLocation(GameLoader.LOC_X, GameLoader.LOC_Y);

		setLayout(null);
		setSize(WIDTH,HEIGHT);
		center=new JPanel(null);
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		int r=10;
		
		progressLabal=new JLabel();
		progressLabal.setBounds(10,r,200,20);
		center.add(progressLabal);
		
		r+=30;
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10,r,200,20);
		center.add(progressBar);
		
		setValue(0);

		setVisible(true);
	}
	
	public void setValue(int value){
		
		progressBar.setValue(value);
		progressLabal.setText(progressDataString.replaceAll("<PERC>", ""+value));
	}

	public int getValue(){
		return progressBar.getValue();
	}
	
	public void incrementValue(int increment){
		
		setValue(getValue()+increment);
	}

}
