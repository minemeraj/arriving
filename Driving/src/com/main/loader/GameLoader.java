package com.main.loader;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GameLoader extends JDialog implements ActionListener{
	
	int WIDTH=200;
	int HEIGHT=60;
	
	int LOC_X=200;
	int LOC_Y=200;
	
	private JPanel center;
	private JButton goButton;
	
	public static Color BACKGROUND_COLOR=new Color(255,255,255);
	
	JRadioButton[] mapRadios=null;
	
	public static String DEFAULT_MAP="default";

	public GameLoader(){
		
		 setTitle("Game loader");
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		 Vector mapNames=loadMapNames();
		 int addedHeight=50*mapNames.size();
		 HEIGHT=HEIGHT+addedHeight;
		 
		 setLayout(null);
		 setSize(WIDTH,HEIGHT);
		 setLocation(LOC_X,LOC_Y);
		 setModal(true);
		 		 
		 center=new JPanel();
		 center.setLayout(null);
		 center.setBounds(0,0,WIDTH,HEIGHT);
		 center.setBackground(BACKGROUND_COLOR);
		 add(center);
		 
		 mapRadios=new JRadioButton[mapNames.size()];
		 
		 int r=10;
		 
		 ButtonGroup bg=new ButtonGroup();
		 
		 for (int i = 0; i < mapNames.size(); i++) {
			 
			String mName = (String) mapNames.elementAt(i);
			
			mapRadios[i]=new JRadioButton();
			mapRadios[i].setBounds(10,r,50,20);
			if(i==0)
				mapRadios[i].setSelected(true);
			mapRadios[i].setActionCommand(mName);
			
			bg.add(mapRadios[i]);
			center.add(mapRadios[i]);
			
			JLabel label=new JLabel();
			label.setBounds(70,r,100,20);
			label.setText(mName);
			center.add(label);
					
			
			r+=30;
			
			
		}
		 
		 goButton=new JButton("Ok");
		 goButton.addActionListener(this);
		 goButton.setBounds(10,r,50,20);
		 center.add(goButton);
		 setVisible(true);
	}


	private Vector loadMapNames() {
		
		 Vector vec=new Vector();
		 
		 vec.add(DEFAULT_MAP);
		 
		File directoryImg=new File("lib");
		File[] files=directoryImg.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			String fName=files[i].getName();
			
			if(fName.startsWith("landscape_") && !fName.endsWith(DEFAULT_MAP)){
				
				String mName=fName.substring(fName.indexOf("_")+1);
				vec.add(mName);
				
			}
			
		}
		
		 return vec;
		
	}


	public void actionPerformed(ActionEvent arg0) {
	
		Object obj = arg0.getSource();
		
		if(obj==goButton){
			go();
		}
		
	}


	private void go() {
	
		dispose();
		
	}
	
	public String getMap(){
		
		for (int i = 0; i < mapRadios.length; i++) {
			
			if(mapRadios[i].isSelected()){
				
				return mapRadios[i].getActionCommand();
			}
			
		}
		
		
		return DEFAULT_MAP;
		
	}

}
