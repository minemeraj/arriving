package com.editors.mesh;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.editors.DoubleTextField;
import com.editors.ValuePair;
import com.editors.models.Airplane0Model;
import com.editors.models.Car0Model;
import com.editors.models.Ship0Model;
import com.editors.models.StarShip0Model;
import com.editors.models.Truck0Model;

public class CarMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

	String title="Car model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private JComboBox chooseObject;

	private boolean skipItemChanged=false;
	private DoubleTextField dx_front_text;
	private DoubleTextField dy_front_text;
	private DoubleTextField dz_front_text;
	private DoubleTextField dx_rear_text;
	private DoubleTextField dy_rear_text;
	private DoubleTextField dz_rear_text;
	private DoubleTextField dx_roof_text;
	private DoubleTextField dy_roof_text;
	private DoubleTextField dz_roof_text;

	public static int CAR0=0;
	public static int TRUCK0=1;
	public static int SHIP0=2;
	public static int STARSHIP0=3;
	public static int TANK0=4;
	public static int AIRPLANE0=4;
	

	public static void main(String[] args) {

		CarMeshEditor fm=new CarMeshEditor(650,350);
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

		JLabel name=new JLabel("Description:");
		name.setBounds(10,r,120,20);
		center.add(name);

		r+=30;

		description=new JTextField();
		description.setBounds(10,r,480,20);
		description.setToolTipText("Description");
		description.setText("");
		center.add(description);

		r+=30;
		
		int col0=10;
		int col1=80;
		int col2=210;
		int col3=280;
		int col4=410;
		int col5=480;

		JLabel lx=new JLabel("dx:");
		lx.setBounds(col0,r,70,20);
		center.add(lx);
		dx_text=new DoubleTextField(8);
		dx_text.setBounds(col1,r,100,20);
		dx_text.setText(dx);
		center.add(dx_text);


		JLabel ly=new JLabel("dy:");
		ly.setBounds(col2,r,70,20);
		center.add(ly);
		dy_text=new DoubleTextField(8);
		dy_text.setBounds(col3,r,100,20);
		dy_text.setText(dy);
		center.add(dy_text);


		JLabel lz=new JLabel("dz:");
		lz.setBounds(col4,r,70,20);
		center.add(lz);
		dz_text=new DoubleTextField(8);
		dz_text.setBounds(col5,r,100,20);
		dz_text.setText(dz);
		center.add(dz_text);
		
		r+=30;
		
		
		lx=new JLabel("dxFront:");
		lx.setBounds(col0,r,70,20);
		center.add(lx);
		dx_front_text=new DoubleTextField(8);
		dx_front_text.setBounds(col1,r,100,20);
		dx_front_text.setText(dx);
		center.add(dx_front_text);


		ly=new JLabel("dyFront:");
		ly.setBounds(col2,r,70,20);
		center.add(ly);
		dy_front_text=new DoubleTextField(8);
		dy_front_text.setBounds(col3,r,100,20);
		dy_front_text.setText(dy);
		center.add(dy_front_text);


		lz=new JLabel("dzFront:");
		lz.setBounds(col4,r,70,20);
		center.add(lz);
		dz_front_text=new DoubleTextField(8);
		dz_front_text.setBounds(col5,r,100,20);
		dz_front_text.setText(dz);
		center.add(dz_front_text);

		r+=30;
		
		
		lx=new JLabel("dxRear:");
		lx.setBounds(col0,r,70,20);
		center.add(lx);
		dx_rear_text=new DoubleTextField(8);
		dx_rear_text.setBounds(col1,r,100,20);
		dx_rear_text.setText(dx);
		center.add(dx_rear_text);


		ly=new JLabel("dyRear:");
		ly.setBounds(col2,r,70,20);
		center.add(ly);
		dy_rear_text=new DoubleTextField(8);
		dy_rear_text.setBounds(col3,r,100,20);
		dy_rear_text.setText(dy);
		center.add(dy_rear_text);


		lz=new JLabel("dzRear:");
		lz.setBounds(col4,r,70,20);
		center.add(lz);
		dz_rear_text=new DoubleTextField(8);
		dz_rear_text.setBounds(col5,r,100,20);
		dz_rear_text.setText(dz);
		center.add(dz_rear_text);
		
		
		r+=30;
		
		
		lx=new JLabel("dxRoof:");
		lx.setBounds(col0,r,70,20);
		center.add(lx);
		dx_roof_text=new DoubleTextField(8);
		dx_roof_text.setBounds(col1,r,100,20);
		dx_roof_text.setText(0);
		center.add(dx_roof_text);


		ly=new JLabel("dyRoof:");
		ly.setBounds(col2,r,70,20);
		center.add(ly);
		dy_roof_text=new DoubleTextField(8);
		dy_roof_text.setBounds(col3,r,100,20);
		dy_roof_text.setText(0);
		center.add(dy_roof_text);


		lz=new JLabel("dzRoof:");
		lz.setBounds(col4,r,70,20);
		center.add(lz);
		dz_roof_text=new DoubleTextField(8);
		dz_roof_text.setBounds(col5,r,100,20);
		dz_roof_text.setText(0);
		center.add(dz_roof_text);

		r+=30;

		JLabel jlb=new JLabel("Car type:");
		jlb.setBounds(col0, r, 100, 20);
		center.add(jlb);

		chooseObject=new JComboBox();
		chooseObject.setBounds(col1, r, 100, 20);
		chooseObject.addKeyListener(this);
		chooseObject.addItem(new ValuePair("-1",""));
		chooseObject.addItem(new ValuePair(""+AIRPLANE0,"Airplane0"));
		chooseObject.addItem(new ValuePair(""+CAR0,"Car0"));	
		chooseObject.addItem(new ValuePair(""+TRUCK0,"Truck0"));
		chooseObject.addItem(new ValuePair(""+SHIP0,"Ship0"));
		//chooseObject.addItem(new ValuePair(""+TANK0,"Tank0"));
		chooseObject.addItemListener(this);		

		chooseObject.setSelectedIndex(0);
		center.add(chooseObject);

		r+=40;

		meshButton=new JButton("Mesh");
		meshButton.setBounds(10,r,80,20);
		meshButton.addActionListener(this);
		center.add(meshButton);

		textureButton=new JButton("Texture");
		textureButton.setBounds(120,r,90,20);
		textureButton.addActionListener(this);
		center.add(textureButton);

	}

	public void initMesh() {


		double dx = dx_text.getvalue();
		double dy = dy_text.getvalue();
		double dz = dz_text.getvalue();
		
		double dxf = dx_front_text.getvalue();
		double dyf = dy_front_text.getvalue();
		double dzf = dz_front_text.getvalue();
		
		double dxr = dx_rear_text.getvalue();
		double dyr = dy_rear_text.getvalue();
		double dzr = dz_rear_text.getvalue();
		
		double dxRoof = dx_roof_text.getvalue();
		double dyRoof  = dy_roof_text.getvalue();
		double dzRoof  = dz_roof_text.getvalue();

		ValuePair vp= (ValuePair)chooseObject.getSelectedItem();

		int val=Integer.parseInt(vp.getId());
		if(val<0)
			val=CAR0;

		if(val==CAR0)
			meshModel=new Car0Model(dx,dy,dz);
		else if(val==TRUCK0)
			meshModel=new Truck0Model(dx,dy,dz,dxf,dyf,dzf,dxr,dyr,dzr);
		else if(val==SHIP0)
			meshModel=new Ship0Model(dx,dy,dz,dxr,dyr,dzr,dxRoof,dyRoof,dzRoof);
		else if(val==STARSHIP0)
			meshModel=new StarShip0Model(dx,dy,dz,dxf,dyf,dzf,dxr,dyr,dzr);
		else if(val==AIRPLANE0){
			
			meshModel=new Airplane0Model(
					dx,dy,dz,
					dxf,dyf,dzf,
					dxr,dyr,dzr,
					dxRoof,dyRoof,dzRoof
					);
		}
		else
			meshModel=new Car0Model(dx,dy,dz);

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

		if(obj==chooseObject){

			ValuePair vp= (ValuePair)chooseObject.getSelectedItem();

			int val=Integer.parseInt(vp.getId());
			if(val<0)
				val=CAR0;

			if(CAR0==val)
				setRightData(151,400,109,0,0,0,0,0,0,0,0,0);
			else if(TRUCK0==val)
				setRightData(151,350,109,151,150,109,151,400,50,0,0,0);
			else if(SHIP0==val)
				setRightData(718,4550,458,
						0,0,0,
						0,161,0,
						718,364,598);
			else if(STARSHIP0==val)
				setRightData(50,200,50,150,150,50,50,250,50,0,0,0);
			else if(AIRPLANE0==val)
				setRightData(48,250,45,25,57,25,21,143,13,181,119,29);
		}

	}


	private void setRightData(
			int dx, int dy, int dz,
			int dxf, int dyf, int dzf,
			int dxr, int dyr, int dzr,
			int dxRoof, int dyRoof, int dzRoof
			){ 

		dx_text.setText(dx);
		dy_text.setText(dy);
		dz_text.setText(dz);
		
		dx_front_text.setText(dxf);
		dy_front_text.setText(dyf);
		dz_front_text.setText(dzf);
		
		dx_rear_text.setText(dxr);
		dy_rear_text.setText(dyr);
		dz_rear_text.setText(dzr);
		
		dx_roof_text.setText(dxRoof);
		dy_roof_text.setText(dyRoof);
		dz_roof_text.setText(dzRoof);
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
