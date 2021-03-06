package com.editors.mesh;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.editors.DoubleTextField;
import com.editors.ValuePair;

public class PlantMeshEditor extends MeshModelEditor implements ItemListener{

	String title="Plant model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private JComboBox choosePlant;

	private boolean skipItemChanged=false;

	public static void main(String[] args) {

		PlantMeshEditor fm=new PlantMeshEditor(250,350);
	}


	public PlantMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}


	public void buildCenter() {

		double dx=100;
		double dy=200;
		double dz=50;

		int r=10;

		JLabel name=new JLabel("Description:");
		name.setBounds(5,r,120,20);
		center.add(name);

		r+=30;

		description=new JTextField();
		description.setBounds(30,r,180,20);
		description.setToolTipText("Description");
		description.setText("");
		center.add(description);

		r+=30;

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

		JLabel jlb=new JLabel("Plant type:");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);

		choosePlant=new JComboBox();
		choosePlant.setBounds(115, r, 100, 20);
		choosePlant.addItem(new ValuePair("-1",""));

		choosePlant.addItemListener(this);
		choosePlant.setSelectedIndex(0);
		center.add(choosePlant);



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

		ValuePair vp= (ValuePair)choosePlant.getSelectedItem();

		int val=Integer.parseInt(vp.getId());
		if(val<0)
			val=0;


		meshModel.setDescription(description.getText());

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

		if(obj==choosePlant){

		}

	}

}
