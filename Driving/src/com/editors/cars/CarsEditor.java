package com.editors.cars;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.editors.CustomEditor;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.ValuePair;
import com.editors.cars.data.Car;
import com.editors.object.ObjectEditorPreviewPanel;

public class CarsEditor extends CustomEditor implements ItemListener{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	private JComboBox car_type;
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	private DoubleTextField z_side;
	private DoubleTextField back_width;
	private DoubleTextField back_length;
	private DoubleTextField back_height;
	private DoubleTextField front_width;
	private DoubleTextField front_length;	
	private DoubleTextField front_height;
	private DoubleTextField roof_width;
	private DoubleTextField roof_length;
	private DoubleTextField roof_height;
	private DoubleTextField wheel_radius;
	private DoubleTextField wheel_width;
	private DoubleTextField front_overhang;
	private DoubleTextField wheel_base;
	private DoubleTextField rear_overhang;
	
	private JButton restoreDefault;
	
	public Stack oldCar=null;
	int max_stack_size=10;
	
	Car car=null;

	

	
	
	public CarsEditor(){
		
		setTitle("Cars editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new CarsEditorJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
	
		buildMenuBar();
		
		buildRightPanel();
		
		addKeyListener(this);
		addMouseWheelListener(this);
		
		RepaintManager.setCurrentManager( 
				new RepaintManager(){

					public void paintDirtyRegions() {


						super.paintDirtyRegions();
						if(redrawAfterMenu ) {
							center.draw(car);
							redrawAfterMenu=false;						    
						}
					}

				}				
		);
		
		setVisible(true);
		
		initialize();
	}

	
	public void initialize() {
		
		oldCar=new Stack();
		center.initialize();
	}
	

	
	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
		
	}

	public void draw() {
		
		center.draw(car);
		
	}
	
	public void buildRightPanel() {
		
		
		JPanel right=new JPanel(null);
		
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		
		int r=10;
		
		int column=100;
		
		JLabel jlb=new JLabel("Car type:");
		jlb.setBounds(5, r, 120, 20);
		right.add(jlb);
		
		car_type=new JComboBox();
		car_type.setBounds(column, r, 110, 20);
		car_type.addKeyListener(this);
		car_type.addItem(new ValuePair("-1",""));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_CAR,"Car"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_TRUCK,"Truck"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_BYKE,"Byke"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_TRACTOR,"Tractor"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_RAILROAD_CAR_0,"Railroad car 0"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_RAILROAD_CAR_1,"Railroad car 1"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_RAILROAD_CAR_2,"Railroad car 2"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_RAILROAD_CAR_3,"Railroad car 3"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_RAILROAD_CAR_4,"Railroad car 4"));
		car_type.addItem(new ValuePair(""+Car.CAR_TYPE_AIRPLANE,"Airplane"));
		car_type.addItemListener(this);
		
		car_type.setSelectedIndex(0);
		right.add(car_type);
		
		r+=30;
		

		jlb=new JLabel("Body DX");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		x_side=new DoubleTextField();
		x_side.setBounds(column, r, 100, 20);
		x_side.addKeyListener(this);
		right.add(x_side);

		r+=30;
		
		jlb=new JLabel("Body DY");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		y_side=new DoubleTextField();
		y_side.setBounds(column, r, 100, 20);
		y_side.addKeyListener(this);
		right.add(y_side);
		
		r+=30;
		
		jlb=new JLabel("Body DZ");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		z_side=new DoubleTextField();
		z_side.setBounds(column, r, 100, 20);
		z_side.addKeyListener(this);
		right.add(z_side);
		
		r+=30;
		
		jlb=new JLabel("Back width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		back_width=new DoubleTextField();
		back_width.setBounds(column, r, 100, 20);
		back_width.addKeyListener(this);
		right.add(back_width);
		
		r+=30;
		
		jlb=new JLabel("Rear length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		back_length=new DoubleTextField();
		back_length.setBounds(column, r, 100, 20);
		back_length.addKeyListener(this);
		right.add(back_length);
		
		r+=30;
		
		jlb=new JLabel("Rear height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		back_height=new DoubleTextField();
		back_height.setBounds(column, r, 100, 20);
		back_height.addKeyListener(this);
		right.add(back_height);
		
		r+=30;
		
		jlb=new JLabel("Rear width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		front_width=new DoubleTextField();
		front_width.setBounds(column, r, 100, 20);
		front_width.addKeyListener(this);
		right.add(front_width);
		
		r+=30;
		
		jlb=new JLabel("Front length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		front_length=new DoubleTextField();
		front_length.setBounds(column, r, 100, 20);
		front_length.addKeyListener(this);
		right.add(front_length);
		
		r+=30;
		
		jlb=new JLabel("Front height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		front_height=new DoubleTextField();
		front_height.setBounds(column, r, 100, 20);
		front_height.addKeyListener(this);
		right.add(front_height);
		
		r+=30;
		
		jlb=new JLabel("Roof width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		roof_width=new DoubleTextField();
		roof_width.setBounds(column, r, 100, 20);
		roof_width.addKeyListener(this);
		right.add(roof_width);
		
		r+=30;
		
		jlb=new JLabel("Roof length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		roof_length=new DoubleTextField();
		roof_length.setBounds(column, r, 100, 20);
		roof_length.addKeyListener(this);
		right.add(roof_length);
		
		r+=30;
		
		jlb=new JLabel("Roof height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		roof_height=new DoubleTextField();
		roof_height.setBounds(column, r, 100, 20);
		roof_height.addKeyListener(this);
		right.add(roof_height);
		
		r+=30;
		
		jlb=new JLabel("Wheel radius");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		wheel_radius=new DoubleTextField();
		wheel_radius.setBounds(column, r, 100, 20);
		wheel_radius.addKeyListener(this);
		right.add(wheel_radius);
		
		r+=30;
		
		jlb=new JLabel("Wheel width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		wheel_width=new DoubleTextField();
		wheel_width.setBounds(column, r, 100, 20);
		wheel_width.addKeyListener(this);
		right.add(wheel_width);
		
		r+=30;
		
		jlb=new JLabel("Front overhang");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		front_overhang=new DoubleTextField();
		front_overhang.setBounds(column, r, 100, 20);
		front_overhang.addKeyListener(this);
		right.add(front_overhang);
		
		r+=30;
		
		jlb=new JLabel("Wheel base");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		wheel_base=new DoubleTextField();
		wheel_base.setBounds(column, r, 100, 20);
		wheel_base.addKeyListener(this);
		right.add(wheel_base);
		
		r+=30;
		
		jlb=new JLabel("Rear overhang");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		rear_overhang=new DoubleTextField();
		rear_overhang.setBounds(column, r, 100, 20);
		rear_overhang.addKeyListener(this);
		right.add(rear_overhang);
		
		r+=30;
			
        generate=new JButton("Update");
        generate.setBounds(10,r,100,20);
        generate.addActionListener(this);
        generate.addKeyListener(this);
        right.add(generate);
        
        restoreDefault=new JButton("Default");
        restoreDefault.setBounds(140,r,100,20);
        restoreDefault.addActionListener(this);
        restoreDefault.addKeyListener(this); 
        right.add(restoreDefault);
		
		add(right);
		
		initRightCarData();
		
	}
	

	public void initRightCarData() { 
		
	
		x_side.setText(130);
		y_side.setText(200);
		z_side.setText(72);
		back_width.setText(114);
		back_length.setText(43);
		back_height.setText(76);
		front_width.setText(110);
		front_length.setText(100);
		front_height.setText(54);
		
		roof_width.setText(75);
		roof_length.setText(118);
		roof_height.setText(34);
		
		wheel_radius.setText(25);
		wheel_width.setText(18);
	}
	

	public void initRightTruckData() { 
		
	
		x_side.setText(94);
		y_side.setText(100);
		z_side.setText(22);
		
		back_width.setText(94);
		back_length.setText(219);
		back_height.setText(22);
		
		front_width.setText(94);
		front_length.setText(61);
		front_height.setText(118);
		
		roof_width.setText(111);
		roof_length.setText(319);
		roof_height.setText(116);
		
		wheel_radius.setText(19);
		wheel_width.setText(12);
		
		front_overhang.setText(0);
		wheel_base.setText(0);
		rear_overhang.setText(0);
	}
	
	public void initRightBykeData() { 
		
		
		x_side.setText(50);
		y_side.setText(150);
		z_side.setText(50);
		
		back_width.setText(120);
		back_length.setText(100);
		back_height.setText(100);
		
		front_width.setText(100);
		front_length.setText(50);
		front_height.setText(25);
		
		roof_width.setText(120);
		roof_length.setText(200);
		roof_height.setText(100);
		
		wheel_radius.setText(50);
		wheel_width.setText(20);
		
		front_overhang.setText(0);
		wheel_base.setText(0);
		rear_overhang.setText(0);
	
	}
	
	private void initRightTractorData() {
 
		
		
		x_side.setText(66);
		y_side.setText(250);
		z_side.setText(29);
		
		back_width.setText(195);
		back_length.setText(215);
		back_height.setText(79);
		
		front_width.setText(55);
		front_length.setText(162);
		front_height.setText(79);
		
		roof_width.setText(66);
		roof_length.setText(135);
		roof_height.setText(155);
		
		wheel_radius.setText(86);
		wheel_width.setText(58);
		
		front_overhang.setText(0);
		wheel_base.setText(250);
		rear_overhang.setText(0);

		
	}
	
	private void initRightAirplaneData() {
		
		x_side.setText(48);
		y_side.setText(250);
		z_side.setText(45);
		
		back_width.setText(21);
		back_length.setText(143);
		back_height.setText(13);
		front_width.setText(25);
		front_length.setText(57);
		front_height.setText(25);
		
		roof_width.setText(181);
		roof_length.setText(119);
		roof_height.setText(29);
		
		wheel_radius.setText(22);
		wheel_width.setText(0);
		
		front_overhang.setText(0);
		wheel_base.setText(0);
		rear_overhang.setText(0);
		
	}


	private void initRightRailroadCarData(int type) {
		
		
		if(type==Car.CAR_TYPE_RAILROAD_CAR_0 ){
			
			x_side.setText(69);
			y_side.setText(400);
			z_side.setText(7);
			back_width.setText(41);
			back_length.setText(51);
			back_height.setText(14);
			front_width.setText(41);
			front_length.setText(51);
			front_height.setText(14);
			
			roof_width.setText(70);
			roof_length.setText(348);
			roof_height.setText(70);
			
			wheel_radius.setText(13);
			wheel_width.setText(4);
			
			front_overhang.setText(29);
			wheel_base.setText(291);
			rear_overhang.setText(29);
			
		}else if(type==Car.CAR_TYPE_RAILROAD_CAR_1 ){
			
			x_side.setText(65);
			y_side.setText(407);
			z_side.setText(7);
			back_width.setText(41);
			back_length.setText(51);
			back_height.setText(14);
			front_width.setText(41);
			front_length.setText(51);
			front_height.setText(14);
			
			roof_width.setText(83);
			roof_length.setText(374);
			roof_height.setText(83);
			
			wheel_radius.setText(13);
			wheel_width.setText(4);
			
			front_overhang.setText(29);
			wheel_base.setText(299);
			rear_overhang.setText(29);
			
			
		}else if(type==Car.CAR_TYPE_RAILROAD_CAR_2 ){
			
			x_side.setText(78);
			y_side.setText(364);
			z_side.setText(6);
			back_width.setText(41);
			back_length.setText(57);
			back_height.setText(16);
			front_width.setText(41);
			front_length.setText(57);
			front_height.setText(16);
			
			roof_width.setText(78);
			roof_length.setText(364);
			roof_height.setText(56);
			
			wheel_radius.setText(13);
			wheel_width.setText(4);
			
			front_overhang.setText(32);
			wheel_base.setText(243);
			rear_overhang.setText(32);
			
			
		}else if(type==Car.CAR_TYPE_RAILROAD_CAR_3 ){
			
			x_side.setText(63);
			y_side.setText(263);
			z_side.setText(13);
			back_width.setText(41);
			back_length.setText(51);
			back_height.setText(16);
			front_width.setText(41);
			front_length.setText(51);
			front_height.setText(16);
			
			roof_width.setText(100);
			roof_length.setText(263);
			roof_height.setText(61);
			
			wheel_radius.setText(13);
			wheel_width.setText(4);
			
			front_overhang.setText(0);
			wheel_base.setText(193);
			rear_overhang.setText(0);
			
			
		}else if(type==Car.CAR_TYPE_RAILROAD_CAR_4 ){
			
			x_side.setText(80);
			y_side.setText(746);
			z_side.setText(70);
			back_width.setText(41);
			back_length.setText(71);
			back_height.setText(18);
			front_width.setText(41);
			front_length.setText(71);
			front_height.setText(18);
			
			roof_width.setText(80);
			roof_length.setText(746);
			roof_height.setText(14);
			
			wheel_radius.setText(13);
			wheel_width.setText(4);
			
			front_overhang.setText(64);
			wheel_base.setText(543);
			rear_overhang.setText(64);
			
			
		}




		
	}
	
	private void setRightData(Car car) {
		
		for (int i = 0; i < car_type.getItemCount(); i++) {
			ValuePair vp= (ValuePair) car_type.getItemAt(i);
			if(vp.getId().equals(""+car.getCar_type()))
			{
				car_type.setSelectedIndex(i);
				break;
			}	
		}
	
		x_side.setText(car.getX_side());
		y_side.setText(car.getY_side());
		z_side.setText(car.getZ_side());
		back_width.setText(car.getBack_width());
		back_length.setText(car.getBack_length());
		back_height.setText(car.getBack_height());
		front_width.setText(car.getFront_width());
		front_length.setText(car.getFront_length());
		front_height.setText(car.getFront_height());
		roof_width.setText(car.getRoof_width());
		roof_length.setText(car.getRoof_length());
		roof_height.setText(car.getRoof_height());
	}

	
	public static void main(String[] args) {
		
		CarsEditor be=new CarsEditor();
	}
	
	public void preview() {
		
		if(car==null)
			return;
		
		Editor editor=new Editor();
		editor.meshes[0]=car.buildMesh();
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(editor);
		
	}
	
	public void generate() {
		
			prepareUndo();
			
			double xside=x_side.getvalue();
			double yside=y_side.getvalue();
			double zside=z_side.getvalue();
			double backWidth=back_width.getvalue();
			double backLength=back_length.getvalue();
			double backHeight=back_height.getvalue();
			double frontWidth=front_width.getvalue();
			double frontLength=front_length.getvalue();
			double frontHeight=front_height.getvalue();
			double roofWidth=roof_width.getvalue();
			double roofLength=roof_length.getvalue();
			double roofHeight=roof_height.getvalue();
			double wheelRadius=wheel_radius.getvalue();
			double wheelWidth=wheel_width.getvalue();
			double frontOverhang=front_overhang.getvalue();
			double wheelBase=wheel_base.getvalue();
			double rearOverhang=rear_overhang.getvalue();
			
			 ValuePair vp= (ValuePair)car_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			    if(type<0)
			    	type=Car.CAR_TYPE_CAR;
			
			if(car==null){
				
				car=new Car(
						type,
						xside,yside,zside,
						frontWidth,frontLength,frontHeight,
						backWidth,backLength,backHeight,
						roofWidth,roofLength,roofHeight,
						wheelRadius,wheelWidth,
						frontOverhang,wheelBase,rearOverhang
						
						);
				
			}else{
				
				Car expCar =new Car(
						type,
						xside,yside,zside,
						frontWidth,frontLength,frontHeight,
						backWidth,backLength,backHeight,
						roofWidth,roofLength,roofHeight,
						wheelRadius,wheelWidth,
						frontOverhang,wheelBase,rearOverhang
						);
				
				car=expCar;
			}
			
			draw();
			setRightData(car);
	}
	

	public void loadData(File file) {
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			
			String str=null;
			
			while((str=br.readLine())!=null){
				
				int indx=str.indexOf("C=");
				if(indx>=0){
					
					String value=str.substring(indx+2);
					car=Car.buildCar(value);
					setRightData(car);
					
				}
	
			}
			br.close();
			

			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}

	public void saveMesh(File file) {
		
		
		if(car==null)
			return;
		
	
		PrintWriter pw;
		try {
			Editor editor=new Editor();
			editor.meshes[0]=car.buildMesh();
			pw = new PrintWriter(new FileOutputStream(file));
			editor.forceReading=true;
			editor.saveLines(pw);
			pw.close();
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
	}

	public void saveData(File file) {
		
		
		if(car==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			pw.println(car.toString());
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void undo() {

		if(oldCar.size()>0)
			car=(Car) oldCar.pop();
		
		if(oldCar.size()==0)
			jmt_undo_last.setEnabled(false);
		
		draw();
	}
	
	public void prepareUndo() {
		
		if(car==null)
			return;
		
		jmt_undo_last.setEnabled(true);
		
		if(oldCar.size()>=max_stack_size)
			oldCar.removeElementAt(0);
		
		oldCar.push(car.clone());
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object obj = arg0.getSource();
		
		if(obj==car_type){
			
			 center.setTeta(0);
			
			 ValuePair vp= (ValuePair)car_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			   if(type<0)
			    	type=Car.CAR_TYPE_CAR;
			   
			   if(type==Car.CAR_TYPE_CAR)
				   initRightCarData();
			   else if(type==Car.CAR_TYPE_TRUCK)
				   initRightTruckData();
			   else if(type==Car.CAR_TYPE_BYKE)
				   initRightBykeData();
			   else if(type==Car.CAR_TYPE_TRACTOR)
				   initRightTractorData();
			   else if(type==Car.CAR_TYPE_RAILROAD_CAR_0 || 
					   type==Car.CAR_TYPE_RAILROAD_CAR_1 ||
					   type==Car.CAR_TYPE_RAILROAD_CAR_2 ||
					   type==Car.CAR_TYPE_RAILROAD_CAR_3 ||
					   type==Car.CAR_TYPE_RAILROAD_CAR_4 
					   )
				   initRightRailroadCarData(type); 
			   else if(type==Car.CAR_TYPE_AIRPLANE)
				   initRightAirplaneData();
			
		}

		
	}







}
