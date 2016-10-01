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
import com.editors.models.Dinosaur0Model;
import com.editors.models.Head1Model;
import com.editors.models.HeadModel;
import com.editors.models.Man1Model;
import com.editors.models.Man2Model;
import com.editors.models.Man3Model;
import com.editors.models.ManModel;
import com.editors.models.QuadrupedModel;
import com.editors.models.RiderManModel;

public class AnimalMeshEditor extends MeshModelEditor implements KeyListener, ItemListener{

	String title="Animal model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;
	private DoubleTextField leg_length_text;
	private DoubleTextField arm_length_text;
	private JComboBox chooseAnimal;

	private boolean skipItemChanged=false;


	public static int MAN0=0;
	public static int MAN1=1;
	public static int MAN2=2;
	public static int MAN3=5;
	public static int HEAD0=3;
	public static int HEAD1=4;
	public static int QUADRUPED=6;
	public static int RIDERMAN=7;
	public static int DINOSAUR0=8;

	public static void main(String[] args) {

		AnimalMeshEditor fm=new AnimalMeshEditor(350,320);
	}


	public AnimalMeshEditor(int W, int H) {
		super(W, H);
		setTitle(title);
	}


	@Override
	public void buildCenter() {

		double dx=100;
		double dy=100;
		double dz=100;

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
		dx_text.setBounds(30,r,100,20);
		dx_text.setText(dx);
		center.add(dx_text);

		JLabel llegs=new JLabel("Legs len:");
		llegs.setBounds(140,r,70,20);
		center.add(llegs);
		leg_length_text=new DoubleTextField(8);
		leg_length_text.setBounds(210,r,100,20);
		leg_length_text.setText(0);
		center.add(leg_length_text);

		r+=30;

		JLabel ly=new JLabel("dy:");
		ly.setBounds(5,r,20,20);
		center.add(ly);
		dy_text=new DoubleTextField(8);
		dy_text.setBounds(30,r,100,20);
		dy_text.setText(dy);
		center.add(dy_text);

		JLabel larms=new JLabel("Arms len:");
		larms.setBounds(140,r,70,20);
		center.add(larms);
		arm_length_text=new DoubleTextField(8);
		arm_length_text.setBounds(210,r,100,20);
		arm_length_text.setText(0);
		center.add(arm_length_text);


		r+=30;

		JLabel lz=new JLabel("dz:");
		lz.setBounds(5,r,20,20);
		center.add(lz);
		dz_text=new DoubleTextField(8);
		dz_text.setBounds(30,r,100,20);
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
		chooseAnimal.addItem(new ValuePair(""+DINOSAUR0,Dinosaur0Model.NAME));
		chooseAnimal.addItem(new ValuePair(""+MAN0,ManModel.NAME));
		chooseAnimal.addItem(new ValuePair(""+MAN1,Man1Model.NAME));
		chooseAnimal.addItem(new ValuePair(""+MAN2,Man2Model.NAME));
		chooseAnimal.addItem(new ValuePair(""+MAN3,Man3Model.NAME));
		chooseAnimal.addItem(new ValuePair(""+HEAD0,HeadModel.NAME));
		chooseAnimal.addItem(new ValuePair(""+HEAD1,Head1Model.NAME));
		chooseAnimal.addItem(new ValuePair(""+QUADRUPED,QuadrupedModel.NAME));
		chooseAnimal.addItem(new ValuePair(""+RIDERMAN,RiderManModel.NAME));
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

	@Override
	public void initMesh() {


		double dx = dx_text.getvalue();
		double dy = dy_text.getvalue();
		double dz = dz_text.getvalue();

		double darm=arm_length_text.getvalue();
		double dlegs=leg_length_text.getvalue();

		ValuePair vp= (ValuePair)chooseAnimal.getSelectedItem();

		int val=Integer.parseInt(vp.getId());
		if(val<0)
			val=MAN0;

		if(val==MAN0){
			meshModel=new ManModel(dx,dy,dz);
		}else if(val==MAN1){
			meshModel=new Man1Model(dx,dy,dz);
		}else if(val==MAN2){
			meshModel=new Man2Model(dx,dy,dz,dlegs,darm);
		}else if(val==MAN3){
			meshModel=new Man3Model(
					dx,dy,dz,
					20,20,darm,
					20,20,dlegs,
					20,20,50
					);
		}else if(val==RIDERMAN){
			meshModel=new RiderManModel(
					dx,dy,dz,
					20,20,darm,
					20,20,dlegs,
					20,20,50
					);
		}else if(val==DINOSAUR0){
			meshModel=new Dinosaur0Model(
					dx,dy,dz,
					20,20,darm,
					20,20,dlegs,
					20,20,50
					);
		}else if(val==HEAD0){
			meshModel=new HeadModel(dx,dy,dz);
		}else if(val==HEAD1){
			meshModel=new Head1Model(dx,dy,dz);
		}else if(val==QUADRUPED){
			meshModel=new QuadrupedModel(
					dx,dy,dz,
					20,20,darm,
					20,20,dlegs,
					20,20,50
					);
		}else{
			meshModel=new ManModel(dx,dy,dz);
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

		if(skipItemChanged)
			return;

		Object obj = arg0.getSource();

		if(obj==chooseAnimal){

			ValuePair vp= (ValuePair)chooseAnimal.getSelectedItem();

			int val=Integer.parseInt(vp.getId());
			if(val<0)
				val=MAN0;

			if(MAN0==val){
				setRightData(100,10,180,//dx, dy, dz
						0,0);
			}
			else if(MAN1==val){
				setRightData(100,10,180,//dx, dy, dz
						0,0);
			}
			else if(MAN2==val){
				setRightData(100,20,180,//dx, dy, dz
						180,100// leg_length, arm_length)
						);
			}
			else if(MAN3==val){
				setRightData(60,20,100,//dx, dy, dz
						100,50// leg_length, arm_length)
						);
			}
			else if(RIDERMAN==val){
				setRightData(60,20,100,//dx, dy, dz
						100,50// leg_length, arm_length)
						);
			}
			else if(QUADRUPED==val){
				setRightData(60,20,100,//dx, dy, dz
						100,50// leg_length, arm_length)
						);
			}
			else if(DINOSAUR0==val){
				setRightData(60,20,100,//dx, dy, dz
						100,50// leg_length, arm_length)
						);
			}
			else if(HEAD0==val){
				setRightData(200,200,284,//dx, dy, dz
						0,0);
			}
			else if(HEAD1==val){
				setRightData(200,200,284,//dx, dy, dz
						0,0);
			}
		}

	}


	private void setRightData(int dx, int dy, int dz,int leg_length,int arm_length){

		dx_text.setText(dx);
		dy_text.setText(dy);
		dz_text.setText(dz);
		leg_length_text.setText(leg_length);
		arm_length_text.setText(arm_length);
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
