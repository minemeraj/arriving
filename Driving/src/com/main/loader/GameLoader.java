package com.main.loader;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameLoader extends JDialog implements ActionListener{

	private int WIDTH=220;
	private int HEIGHT=100;

	private int LOC_X=200;
	private int LOC_Y=200;

	private JPanel center;

	private static final Color BACKGROUND_COLOR=new Color(255,255,255);

	JButton[] mapRadios=null;

	public static final String DEFAULT_MAP="default";

	private int selectedIndex=0;
	
	private boolean skipShading=false;

	private String header="<html><body>";
	private String footer="</body></html>";

	public GameLoader(){

		selectedIndex=0;

		setTitle("Game loader");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ArrayList mapNames=loadMapNames();
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


		mapRadios=new JButton[mapNames.size()];

		int r=10;

		JLabel label=new JLabel();
		label.setBounds(30,r,170,50);
		label.setText(getLoadGameText());
		center.add(label);

		r+=60;

		for (int i = 0; i < mapNames.size(); i++) {

			String mName = (String) mapNames.get(i);

			mapRadios[i]=new JButton("X");
			mapRadios[i].setBounds(10,r,50,20);
			if(i==0)
				mapRadios[i].setSelected(true);
			mapRadios[i].setActionCommand(mName);
			mapRadios[i].addActionListener(this);
			center.add(mapRadios[i]);

			label=new JLabel();
			label.setBounds(70,r,100,20);
			label.setText(mName);
			center.add(label);

			r+=30;


		}

		setVisible(true);
	}


	private ArrayList loadMapNames() {

		ArrayList vec=new ArrayList();

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

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object obj = arg0.getSource();



		for (int i = 0; i < mapRadios.length; i++) {

			if(obj==mapRadios[i]){
				selectedIndex=i;
				go();
			}

		}

	}


	private void go() {

		dispose();

	}

	public String getMap(){

		if(selectedIndex>=0)		
			return mapRadios[selectedIndex].getActionCommand();

		return DEFAULT_MAP;


	}

	private String getLoadGameText() {

		Random rn = new Random();
		int index=rn.nextInt(quotes.length);
		String msg=quotes[index];

		msg=header+
				"<font color='#FF0000'>"+msg+"</font>"+
				footer;

		return msg;
	}


	private final String[] quotes={


			"Choose you world...",
			"Where do you wonna go?"

	};

	public boolean isSkipShading() {
		return skipShading;
	}


	public void setSkipShading(boolean skipShading) {
		this.skipShading = skipShading;
	}

}
