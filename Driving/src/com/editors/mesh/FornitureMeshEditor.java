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
import com.editors.models.TableModel;

public class FornitureMeshEditor extends MeshModelEditor{
	
	String title="Cube model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private JRadioButton jcbCube;
	private JRadioButton jcbTable;
	
	public static void main(String[] args) {
		
		FornitureMeshEditor fm=new FornitureMeshEditor(200,280);
	}
	
	
	public FornitureMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}

	
	 public void buildCenter() {

		 double dx=100;
		 double dy=200;
		 double dz=50;

		 int r=10;

		 JLabel lx=new JLabel("dx:");
		 lx.setBounds(5,r,20,20);
		 center.add(lx);
		 dx_text=new DoubleTextField(8);
		 dx_text.setBounds(30,r,120,20);
		 dx_text.setText(dx);
		 center.add(dx_text);

		 r+=30;

		 JLabel ly=new JLabel("dy:");
		 ly.setBounds(5,r,20,20);
		 center.add(ly);
		 dy_text=new DoubleTextField(8);
		 dy_text.setBounds(30,r,120,20);
		 dy_text.setText(dy);
		 center.add(dy_text);


		 r+=30;

		 JLabel lz=new JLabel("dz:");
		 lz.setBounds(5,r,20,20);
		 center.add(lz);
		 dz_text=new DoubleTextField(8);
		 dz_text.setBounds(30,r,120,20);
		 dz_text.setText(dz);
		 center.add(dz_text);
		 
		 r+=30;
		 
		 jcbCube=new JRadioButton("Cube");
		 jcbCube.setBounds(5,r,80,20);
		 jcbCube.setSelected(true);
		 center.add(jcbCube);
		 
		 r+=30;
		 
		 jcbTable=new JRadioButton("Table");
		 jcbTable.setBounds(5,r,80,20);
		 center.add(jcbTable);
		 
		 ButtonGroup bg=new ButtonGroup();
		 bg.add(jcbCube);
		 bg.add(jcbTable);

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
	    
	    if(jcbCube.isSelected())
	    	meshModel=new CubeModel(dx,dy,dz);
	    else
	    	meshModel=new TableModel(dx,dy,dz);
	    
	    meshModel.initMesh();
	}
	
	public void printTexture(File file){

		meshModel.printTexture(file);

	}
	
	public void printMeshData(PrintWriter pw) { 

		meshModel.printMeshData(pw);
	}

}
