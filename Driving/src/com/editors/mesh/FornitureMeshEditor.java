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
import com.editors.models.CubeModelCompactTexture;
import com.editors.models.StreetLightModel;
import com.editors.models.TableModel;

public class FornitureMeshEditor extends MeshModelEditor implements ItemListener{


	String title="Forniture model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;

	private JComboBox chooseObject;
	private boolean skipItemChanged;
	private DoubleTextField dxRoof_text;
	private DoubleTextField dyRoof_text;
	private DoubleTextField dzRoof_text;

	private static final int CUBE0 = 0;
	private static final int STREETLIGHT0 = 1;
	private static final int TABLE0 = 2;

	public static void main(String[] args) {

		FornitureMeshEditor fm=new FornitureMeshEditor(550,300);
	}


	public FornitureMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}


	@Override
	public void buildCenter() {

		double dx=100;
		double dy=200;
		double dz=50;

		int col0=5;
		int col1=50;
		int col2=170;
		int col3=200;
		int col4=320;
		int col5=350;

		int r=10;

		JLabel name=new JLabel("Description:");
		name.setBounds(col0,r,120,20);
		center.add(name);

		r+=30;

		description=new JTextField();
		description.setBounds(col1,r,420,20);
		description.setToolTipText("Description");
		description.setText("");
		center.add(description);

		r+=30;

		JLabel lx=new JLabel("dx:");
		lx.setBounds(col0,r,30,20);
		center.add(lx);
		dx_text=new DoubleTextField(8);
		dx_text.setBounds(col1,r,120,20);
		dx_text.setText(dx);
		center.add(dx_text);

		JLabel ly=new JLabel("dy:");
		ly.setBounds(col2,r,30,20);
		center.add(ly);
		dy_text=new DoubleTextField(8);
		dy_text.setBounds(col3,r,120,20);
		dy_text.setText(dy);
		center.add(dy_text);

		JLabel lz=new JLabel("dz:");
		lz.setBounds(col4,r,30,20);
		center.add(lz);
		dz_text=new DoubleTextField(8);
		dz_text.setBounds(col5,r,120,20);
		dz_text.setText(dz);
		center.add(dz_text);

		r+=30;

		lx=new JLabel("dxr:");
		lx.setBounds(col0,r,30,20);
		center.add(lx);
		dxRoof_text=new DoubleTextField(8);
		dxRoof_text.setBounds(col1,r,120,20);
		dxRoof_text.setText(dx);
		center.add(dxRoof_text);

		ly=new JLabel("dyr:");
		ly.setBounds(col2,r,30,20);
		center.add(ly);
		dyRoof_text=new DoubleTextField(8);
		dyRoof_text.setBounds(col3,r,120,20);
		dyRoof_text.setText(dy);
		center.add(dyRoof_text);

		lz=new JLabel("dzr:");
		lz.setBounds(col4,r,30,20);
		center.add(lz);
		dzRoof_text=new DoubleTextField(8);
		dzRoof_text.setBounds(col5,r,120,20);
		dzRoof_text.setText(dz);
		center.add(dzRoof_text);

		r+=30;

		JLabel lb=new JLabel("Type:");
		lb.setBounds(col0,r,40,20);
		center.add(lb);

		chooseObject=new JComboBox();
		chooseObject.addItemListener(this);
		chooseObject.setBounds(50,r,120,20);
		chooseObject.addItem(new ValuePair("-1",""));
		chooseObject.addItem(new ValuePair(""+CUBE0,"Cube"));
		chooseObject.addItem(new ValuePair(""+STREETLIGHT0,"Street light"));
		chooseObject.addItem(new ValuePair(""+TABLE0,"Table"));
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

	@Override
	public void initMesh() {



		double dx = dx_text.getvalue();
		double dy = dy_text.getvalue();
		double dz = dz_text.getvalue();

		double dxRoof = dxRoof_text.getvalue();
		double dyRoof = dyRoof_text.getvalue();
		double dzRoof = dzRoof_text.getvalue();

		ValuePair vp= (ValuePair)chooseObject.getSelectedItem();

		int val=Integer.parseInt(vp.getId());
		if(val<0) {
			val=CUBE0;
		}

		if(val==CUBE0) {
			meshModel=new CubeModelCompactTexture(dx,dy,dz);
		}else  if(val==STREETLIGHT0) {
			meshModel=new StreetLightModel(dx,dy,dz,dxRoof,dyRoof,dzRoof);
		} else  if(val==TABLE0) {
			meshModel=new TableModel(dx,dy,dz);
		}

		meshModel.setDescription(description.getText());

		meshModel.initMesh();
	}

	@Override
	public void printTexture(File file){

		meshModel.printTexture(file);

	}

	@Override
	public void printMeshData(PrintWriter pw) {

		meshModel.printMeshData(pw);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {

		if(skipItemChanged) {
			return;
		}

		Object obj = arg0.getSource();

		if(obj==chooseObject){

			ValuePair vp= (ValuePair)chooseObject.getSelectedItem();

			int val=Integer.parseInt(vp.getId());
			if(val<0) {
				val=CUBE0;
			}

			if(CUBE0==val){
				setRightData(100,200,50,0,0,0);
			}
			else if(STREETLIGHT0==val){
				setRightData(12,0,600,38,66,19);
			}
			else if(TABLE0==val){
				setRightData(100,200,50,0,0,0);
			}

		}

	}


	private void setRightData(int dx, int dy, int dz,int dxRoof, int dyRoof, int dzRoof) {

		dx_text.setText(dx);
		dy_text.setText(dy);
		dz_text.setText(dz);

		dxRoof_text.setText(dxRoof);
		dyRoof_text.setText(dyRoof);
		dzRoof_text.setText(dzRoof);

	}

}
