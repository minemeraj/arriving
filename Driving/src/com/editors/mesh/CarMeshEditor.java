package com.editors.mesh;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.editors.DoubleTextField;
import com.editors.ValuePair;
import com.editors.models.Car0Model;
import com.editors.models.HeadModel;
import com.editors.models.Man1Model;
import com.editors.models.ManModel;

public class CarMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

	String title="Car model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private JComboBox chooseObject;

	private boolean skipItemChanged=false;

	public static int CAR0=0;


	public static void main(String[] args) {

		CarMeshEditor fm=new CarMeshEditor(250,280);
	}


	public CarMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}


	public void buildCenter() {

		double dx=100;
		double dy=100;
		double dz=100;

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

		JLabel jlb=new JLabel("Car type:");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);

		chooseObject=new JComboBox();
		chooseObject.setBounds(115, r, 100, 20);
		chooseObject.addKeyListener(this);
		chooseObject.addItem(new ValuePair("-1",""));
		chooseObject.addItem(new ValuePair(""+CAR0,"Car0"));	
		chooseObject.addItemListener(this);		

		chooseObject.setSelectedIndex(0);
		center.add(chooseObject);

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

		ValuePair vp= (ValuePair)chooseObject.getSelectedItem();

		int val=Integer.parseInt(vp.getId());
		if(val<0)
			val=CAR0;

		if(val==CAR0)
			meshModel=new Car0Model(dx,dy,dz);
		else
			meshModel=new Car0Model(dx,dy,dz);

		meshModel.initMesh();
	}

	public void printTexture(File file){

		meshModel.printTexture(file);

	}

	public void printMeshData(PrintWriter pw) { 

		meshModel.printMeshData(pw);
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {

		if(skipItemChanged)
			return;

		Object obj = arg0.getSource();

		if(obj==chooseObject){

			ValuePair vp= (ValuePair)chooseObject.getSelectedItem();

			int val=Integer.parseInt(vp.getId());
			if(val<0)
				val=CAR0;

			if(CAR0==val)
				setRightData(180,300,200);

		}

	}


	private void setRightData(int dx, int dy, int dz){ 
		
		dx_text.setText(dx);
		dy_text.setText(dy);
		dz_text.setText(dz);
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
