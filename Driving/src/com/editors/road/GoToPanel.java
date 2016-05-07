package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.editors.IntegerTextField;
import com.editors.road.panel.RoadEditorPanel;

public class GoToPanel extends JDialog implements ActionListener{
	
	private JPanel center;
	
	private int WIDTH=230;
	private int HEIGHT=200;

	private JButton update;

	private JButton cancel;
	
	private Object returnValue=null;

	private IntegerTextField POSX_Field;

	private IntegerTextField POSY_Field;
	
	int goPOSX=0;
	int goPOSY=0;
	
	GoToPanel(RoadEditorPanel roadEditorPanel){
		
		setTitle("Go to...");
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setModal(true);
		center=new JPanel(null);
		center.setBounds(0,0,WIDTH,HEIGHT);
		
		int r=10;
		
		JLabel jlb=new JLabel("POSX:");
		jlb.setBounds(10,r,60,20);
		center.add(jlb);
		
		POSX_Field=new IntegerTextField();
		POSX_Field.setBounds(80,r,100,20);
		POSX_Field.setText(roadEditorPanel.getPOSX());
		
		center.add(POSX_Field);
		
		r+=30;
		
		jlb=new JLabel("POSY:");
		jlb.setBounds(10,r,60,20);
		center.add(jlb);
				
		POSY_Field=new IntegerTextField();
		POSY_Field.setBounds(80,r,100,20);
		POSY_Field.setText(roadEditorPanel.getPOSY());
		center.add(POSY_Field);
		
		r+=30;
		
		update=new JButton("Update");
		update.setBounds(10,r,80,20);
		center.add(update);
		update.addActionListener(this);
		
		cancel=new JButton("Cancel");
		cancel.setBounds(100,r,80,20);
		center.add(cancel);
		cancel.addActionListener(this);
		
		add(center);
		
		returnValue=null;
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		
		if(obj==update){
			
			update();
			dispose();

		}
		else if(obj==cancel){
			returnValue=null;
			dispose();
		}
		
	}
	
	private void update() {
		
		goPOSX=POSX_Field.getvalue();
		goPOSY=POSY_Field.getvalue();
		returnValue=this;
	}
	
	public Object getReturnValue() {
		return returnValue;
	}

	public int getGoPOSX() {
		return goPOSX;
	}

	public void setGoPOSX(int goPOSX) {
		this.goPOSX = goPOSX;
	}

	public int getGoPOSY() {
		return goPOSY;
	}

	public void setGoPOSY(int goPOSY) {
		this.goPOSY = goPOSY;
	}

}
