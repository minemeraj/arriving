package com.editors.mesh;

import java.io.File;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.editors.DoubleTextField;
import com.editors.models.CubeModel;
import com.editors.models.Gambrel0Model;
import com.editors.models.House0Model;
import com.editors.models.House1Model;
import com.editors.models.Mansard0Model;
import com.editors.models.TableModel;

public class BulldingMeshEditor extends MeshModelEditor{
	
	String title="Building model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private JRadioButton jcbHouse0;
	private DoubleTextField roof_height;
	private JRadioButton jcbHouse1;
	private DoubleTextField dx1_text;
	private DoubleTextField dy1_text;
	private JRadioButton jcbGambrel0;
	private JRadioButton jcbMansard0;
	
	public static void main(String[] args) {
		
		BulldingMeshEditor fm=new BulldingMeshEditor(470,290);
	}
	
	
	public BulldingMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}

	
	 public void buildCenter() {

		 double dx=100;
		 double dy=200;
		 double dz=50;
		 double dr=50;
		 double dx1=50;
		 double dy1=200;
		 
		 int r=10;
		 
		 int c0=220;
		 int c1=310;

		 JLabel lx=new JLabel("dx:");
		 lx.setBounds(5,r,80,20);
		 center.add(lx);
		 dx_text=new DoubleTextField(8);
		 dx_text.setBounds(90,r,120,20);
		 dx_text.setText(dx);
		 center.add(dx_text);		
		 
		 lx=new JLabel("dx1:");
		 lx.setBounds(c0,r,80,20);
		 center.add(lx);
		 dx1_text=new DoubleTextField(8);
		 dx1_text.setBounds(c1,r,120,20);
		 dx1_text.setText(dx1);
		 center.add(dx1_text);

		 r+=30;

		 JLabel ly=new JLabel("dy:");
		 ly.setBounds(5,r,80,20);
		 center.add(ly);
		 dy_text=new DoubleTextField(8);
		 dy_text.setBounds(90,r,120,20);
		 dy_text.setText(dy);
		 center.add(dy_text);
		 
		 ly=new JLabel("dy1:");
		 ly.setBounds(c0,r,80,20);
		 center.add(ly);
		 dy1_text=new DoubleTextField(8);
		 dy1_text.setBounds(c1,r,120,20);
		 dy1_text.setText(dy1);
		 center.add(dy1_text);


		 r+=30;

		 JLabel lz=new JLabel("dz:");
		 lz.setBounds(5,r,80,20);
		 center.add(lz);
		 dz_text=new DoubleTextField(8);
		 dz_text.setBounds(90,r,120,20);
		 dz_text.setText(dz);
		 center.add(dz_text);
		 
		 r+=30;
		 

		 JLabel lr=new JLabel("Roof h:");
		 lr.setBounds(5,r,80,20);
		 center.add(lr);
		 roof_height=new DoubleTextField(8);
		 roof_height.setBounds(90,r,120,20);
		 roof_height.setText(dr);
		 center.add(roof_height);
		 
		 r+=30;	 
		 
		 
		 jcbHouse0=new JRadioButton(House0Model.NAME);
		 jcbHouse0.setBounds(5,r,80,20);
		 jcbHouse0.setSelected(true);
		 center.add(jcbHouse0);
		 
		 jcbGambrel0=new JRadioButton(Gambrel0Model.NAME);
		 jcbGambrel0.setBounds(90,r,80,20);
		 jcbGambrel0.setSelected(true);
		 center.add(jcbGambrel0);	

		 r+=30;
		 
		 jcbHouse1=new JRadioButton(House1Model.NAME);
		 jcbHouse1.setBounds(5,r,80,20);
		 jcbHouse1.setSelected(true);
		 center.add(jcbHouse1);
		 
		 jcbMansard0=new JRadioButton(Mansard0Model.NAME);
		 jcbMansard0.setBounds(90,r,90,20);
		 center.add(jcbMansard0);	
		 
		 ButtonGroup bg=new ButtonGroup();
		 bg.add(jcbHouse0);
		 bg.add(jcbHouse1);		 
		 bg.add(jcbGambrel0);
		 bg.add(jcbMansard0);
		 
		 r+=30;

		 meshButton=new JButton("Mesh");
		 meshButton.setBounds(10,r,80,20);
		 meshButton.addActionListener(this);
		 center.add(meshButton);

		 r+=30;

		 textureButton=new JButton("Texture");
		 textureButton.setBounds(10,r,90,20);
		 textureButton.addActionListener(this);
		 center.add(textureButton);

	 }

	public void initMesh() {
		
	    	
	    double dx = dx_text.getvalue();
	    double dy = dy_text.getvalue();
	    double dz = dz_text.getvalue();
	    double rh = roof_height.getvalue();
	    
	    double dx1 = dx1_text.getvalue();
	    double dy1 = dy1_text.getvalue();
	    
	    if(jcbHouse0.isSelected())
	    	meshModel=new House0Model(dx,dy,dz,rh,dy1);
	    else if(jcbHouse1.isSelected())
	    	meshModel=new House1Model(dx,dy,dz,rh,dx1,dy1);
	    else if(jcbMansard0.isSelected())
	    	meshModel=new Mansard0Model(dx,dy,dz,rh,dx1,dy1);
	    else if(jcbGambrel0.isSelected())
	    	meshModel=new Gambrel0Model(dx,dy,dz);
	    	
	    
	    meshModel.initMesh();
	}
	
	public void printTexture(File file){

		meshModel.printTexture(file);

	}
	
	public void printMeshData(PrintWriter pw) { 

		meshModel.printMeshData(pw);
	}

}
