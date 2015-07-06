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

import com.editors.DoubleTextField;
import com.editors.IntegerTextField;
import com.editors.ValuePair;
import com.editors.models.BellTowerModel;
import com.editors.models.Chimney0Model;
import com.editors.models.Courtyard0Model;
import com.editors.models.Gambrel0Model;
import com.editors.models.House0Model;
import com.editors.models.House1Model;
import com.editors.models.House2Model;
import com.editors.models.House3Model;
import com.editors.models.House4Model;
import com.editors.models.Mansard0Model;
import com.editors.models.Shed0Model;

public class BulldingMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

	String title="Building model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private DoubleTextField roof_height;
	private DoubleTextField dx1_text;
	private DoubleTextField dy1_text;
	private JComboBox chooseBuilding;
	private DoubleTextField dz1_text;
	private IntegerTextField num_meridians;
	private DoubleTextField dy2_text;
	
	public static int HOUSE0=0;
	public static int HOUSE1=1;
	public static int GAMBREL0=2;
	public static int MANSARD0=3;
	public static int BELLTOWER=4;
	public static int SHED0=5;
	public static int CHIMNEY0=6;
	public static int HOUSE2=7;
	public static int HOUSE3=8;
	public static int HOUSE4=9;
	public static int COURTYARD=10;
	
	public static void main(String[] args) {

		BulldingMeshEditor fm=new BulldingMeshEditor(600,290);
	}


	public BulldingMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}


	public void buildCenter() {

		int r=10;

		int a0=5;
		int a1=70;
		
		int c0=200;
		int c1=280;
		
		int c2=410;
		int c3=450;
		
		JLabel lx=new JLabel("dx:");
		lx.setBounds(a0,r,80,20);
		center.add(lx);
		dx_text=new DoubleTextField(8);
		dx_text.setBounds(a1,r,120,20);
		center.add(dx_text);		

		lx=new JLabel("dx1:");
		lx.setBounds(c0,r,80,20);
		center.add(lx);
		dx1_text=new DoubleTextField(8);
		dx1_text.setBounds(c1,r,120,20);
		center.add(dx1_text);

		r+=30;

		JLabel ly=new JLabel("dy:");
		ly.setBounds(a0,r,80,20);
		center.add(ly);
		dy_text=new DoubleTextField(8);
		dy_text.setBounds(a1,r,120,20);
		center.add(dy_text);

		ly=new JLabel("dy1:");
		ly.setBounds(c0,r,80,20);
		center.add(ly);
		dy1_text=new DoubleTextField(8);
		dy1_text.setBounds(c1,r,120,20);
		center.add(dy1_text);

		ly=new JLabel("dy2:");
		ly.setBounds(c2,r,80,20);
		center.add(ly);
		dy2_text=new DoubleTextField(8);
		dy2_text.setBounds(c3,r,120,20);
		center.add(dy2_text);

		r+=30;

		JLabel lz=new JLabel("dz:");
		lz.setBounds(a0,r,80,20);
		center.add(lz);
		dz_text=new DoubleTextField(8);
		dz_text.setBounds(a1,r,120,20);
		center.add(dz_text);
		
		ly=new JLabel("dz1:");
		ly.setBounds(c0,r,80,20);
		center.add(ly);
		dz1_text=new DoubleTextField(8);
		dz1_text.setBounds(c1,r,120,20);
		center.add(dz1_text);

		r+=30;


		JLabel lr=new JLabel("Roof h:");
		lr.setBounds(a0,r,80,20);
		center.add(lr);
		roof_height=new DoubleTextField(8);
		roof_height.setBounds(a1,r,120,20);
		center.add(roof_height);
		
		lr=new JLabel("N merid.");
		lr.setBounds(c0,r,80,20);
		center.add(lr);
		num_meridians=new IntegerTextField(8);
		num_meridians.setBounds(c1,r,120,20);
		center.add(num_meridians);
		
		setRightData(100,200,100,50,0,150,0,0,0);

	
		r+=30;

		JLabel jlb=new JLabel("Buiilding type:");
		jlb.setBounds(5, r, 100, 20);
		center.add(jlb);

		chooseBuilding=new JComboBox();
		chooseBuilding.setBounds(115, r, 100, 20);
		chooseBuilding.addKeyListener(this);
		chooseBuilding.addItem(new ValuePair("-1",""));
		chooseBuilding.addItem(new ValuePair(""+BELLTOWER,BellTowerModel.NAME));
		chooseBuilding.addItem(new ValuePair(""+CHIMNEY0,Chimney0Model.NAME));
		chooseBuilding.addItem(new ValuePair(""+COURTYARD,Courtyard0Model.NAME));
		chooseBuilding.addItem(new ValuePair(""+HOUSE0,House0Model.NAME));		
		chooseBuilding.addItem(new ValuePair(""+HOUSE1,House1Model.NAME));	
		chooseBuilding.addItem(new ValuePair(""+HOUSE2,House2Model.NAME));
		chooseBuilding.addItem(new ValuePair(""+HOUSE3,House3Model.NAME));
		chooseBuilding.addItem(new ValuePair(""+HOUSE4,House4Model.NAME));
		chooseBuilding.addItem(new ValuePair(""+GAMBREL0,Gambrel0Model.NAME));	
		chooseBuilding.addItem(new ValuePair(""+MANSARD0,Mansard0Model.NAME));
		chooseBuilding.addItem(new ValuePair(""+SHED0,Shed0Model.NAME));
		
		chooseBuilding.addItemListener(this);
	

		chooseBuilding.setSelectedIndex(0);
		center.add(chooseBuilding);

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
		double dz1 = dz1_text.getvalue();	
		
		double dy2 = dy2_text.getvalue();
		
		int num_mer=num_meridians.getvalue();
		
	    ValuePair vp= (ValuePair)chooseBuilding.getSelectedItem();
	    
	    int val=Integer.parseInt(vp.getId());
	    if(val<0)
	    	val=HOUSE0;

		if(HOUSE0==val)
			meshModel=new House0Model(dx,dy,dz,rh,dy1);
		else if(HOUSE1==val)
			meshModel=new House1Model(dx,dy,dz,rh,dx1,dy1);
		else if(MANSARD0==val)
			meshModel=new Mansard0Model(dx,dy,dz,rh,dx1,dy1);
		else if(GAMBREL0==val)
			meshModel=new Gambrel0Model(dx,dy,dz);
		else if(BELLTOWER==val)
			meshModel=new BellTowerModel(dx,dy,dz,rh);
		else if(SHED0==val)
			meshModel=new Shed0Model(dx,dy,dz,dz1);
		else if(CHIMNEY0==val)
			meshModel=new Chimney0Model(dx,dx1,dz,num_mer);
		else if(HOUSE2==val)
			meshModel=new House2Model(dx,dy,dz,rh,dx1,dy1,dy2);
		else if(HOUSE3==val)
			meshModel=new House3Model(dx,dy,dz,rh,dy1);
		else if(HOUSE4==val)
			meshModel=new House4Model(dx,dy,dz,rh,dy1);
		else if(COURTYARD==val)
			meshModel=new Courtyard0Model(dx,dy,dz,rh,dx1,dy1);

		meshModel.initMesh();
	}

	public void printTexture(File file){

		meshModel.printTexture(file);

	}

	public void printMeshData(PrintWriter pw) { 

		meshModel.printMeshData(pw);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		Object obj = arg0.getSource();
		
		if(obj==chooseBuilding){
			
			 ValuePair vp= (ValuePair)chooseBuilding.getSelectedItem();
				
			 int val=Integer.parseInt(vp.getId());
			   if(val<0)
			    	val=HOUSE0;
			   
				if(HOUSE0==val)
					setRightData(100,200,100,50,0,150,0,0,0);
				else if(HOUSE1==val)
					setRightData(100,200,100,50,50,150,0,0,0);
				else if(MANSARD0==val)
					setRightData(100,200,100,50,50,150,0,0,0);
				else if(GAMBREL0==val)
					setRightData(100,200,100,0,0,0,0,0,0);
				else if(BELLTOWER==val)
					setRightData(100,200,300,100,0,0,0,0,0);
				else if(SHED0==val)
					setRightData(100,200,100,0,0,0,60,0,0);
				else if(CHIMNEY0==val)
					setRightData(100,0,300,0,80,0,0,10,0);
				else if(HOUSE2==val)
					setRightData(100,200,100,50,100,150,0,0,200);
				else if(HOUSE3==val)
					setRightData(100,200,100,50,0,150,0,0,150);
				else if(HOUSE4==val)
					setRightData(100,150,100,50,0,300,0,0,0);
				else if(COURTYARD==val)
					setRightData(200,200,100,50,100,100,0,0,0);
		}		   
		
	}


	private void setRightData(
			int dx, 
			int dy, 
			int dz, 
			int roofHeight,
			int dx1, 
			int dy1,
			int dz1,
			int num_merid,
			int dy2
			) {
	
		
		dx_text.setText(dx);
		dy_text.setText(dy); 
		dz_text.setText(dz);
		roof_height.setText(roofHeight);
		dx1_text.setText(dx1);
		dy1_text.setText(dy1);
		dz1_text.setText(dz1);
		num_meridians.setText(num_merid);
		dy2_text.setText(dy2);
	}

}
