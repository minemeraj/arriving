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
import com.editors.models.ManModel;

public class AnimalMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

	String title="Animal model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private JComboBox chooseAnimal;

	private boolean skipItemChanged=false;

	public static int MAN0=0;


	public static void main(String[] args) {

		AnimalMeshEditor fm=new AnimalMeshEditor(250,280);
	}


	public AnimalMeshEditor(int W, int H) {
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

		JLabel jlb=new JLabel("Animal type:");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);

		chooseAnimal=new JComboBox();
		chooseAnimal.setBounds(115, r, 100, 20);
		chooseAnimal.addKeyListener(this);
		chooseAnimal.addItem(new ValuePair("-1",""));
		chooseAnimal.addItem(new ValuePair(""+MAN0,"Man0"));
		chooseAnimal.addItemListener(this);		

		chooseAnimal.setSelectedIndex(0);
		center.add(chooseAnimal);

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

		ValuePair vp= (ValuePair)chooseAnimal.getSelectedItem();

		int val=Integer.parseInt(vp.getId());
		if(val<0)
			val=MAN0;

		if(val==MAN0)
			meshModel=new ManModel(dx,dy,dz);
		else
			meshModel=new ManModel(dx,dy,dz);

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

		if(obj==chooseAnimal){

			ValuePair vp= (ValuePair)chooseAnimal.getSelectedItem();

			int val=Integer.parseInt(vp.getId());
			if(val<0)
				val=MAN0;

			if(MAN0==val)
				setRightData(100,20,170);
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
