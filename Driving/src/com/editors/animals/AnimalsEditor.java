package com.editors.animals;

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
import javax.swing.JCheckBoxMenuItem;
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
import com.editors.animals.data.Animal;
import com.editors.object.ObjectEditorPreviewPanel;
import com.main.Renderer3D;

public class AnimalsEditor extends CustomEditor implements MenuListener, ActionListener, KeyListener, MouseWheelListener, ItemListener{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	AnimalsJPanel center=null;
	
	public boolean redrawAfterMenu=false;

	private JMenuBar jmb;
	private JMenu jm_file;
	private JMenuItem jmt_load_file;
	private JMenuItem jmt_save_file;
	private JMenu jm_view;
	private JMenuItem jmt_preview;
	private JMenu jm_change;
	private JMenuItem jmt_undo_last;
	private JMenuItem jmt_save_mesh;
	
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	private DoubleTextField z_side;
	private JComboBox animal_type;

	private DoubleTextField head_DZ;
	private DoubleTextField head_DY;
	private DoubleTextField head_DX;
	
	private DoubleTextField neck_length;
	private DoubleTextField neck_side;
	
	private DoubleTextField humerus_length;
	private DoubleTextField radius_length;
	
	private DoubleTextField femur_length;
	private DoubleTextField shinbone_length;
	private DoubleTextField foot_length;
	
	private DoubleTextField leg_side;
	
	private JButton generate;
	
	JFileChooser fc = new JFileChooser();
	File currentDirectory=new File("lib");
	
	public Stack oldAnimal=null;
	int max_stack_size=10;
	
	Animal animal=null;
	private JMenu jm_filter;
	private JCheckBoxMenuItem[] jm_filters;
	
	public AnimalsEditor(){
		
		setTitle("Animals editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new AnimalsJPanel();
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
							draw();
							redrawAfterMenu=false;						    
						}
					}

				}				
		);
		
		setVisible(true);
		
		initialize();
	}
	
	public void initialize() {
		
		center.initialize();
		
		oldAnimal=new Stack();
	}

	public static void main(String[] args) {
		
		AnimalsEditor be=new AnimalsEditor();
	}
	
	
	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
	}

	private void draw() {
		
		center.draw(animal);
		
	}
	
	public void buildRightPanel() {
		
		
		JPanel right=new JPanel(null);
		
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		
		int r=10;
		
		int column=100;
		
		JLabel jlb=new JLabel("Animal type:");
		jlb.setBounds(5, r, 120, 20);
		right.add(jlb);

		animal_type=new JComboBox();
		animal_type.setBounds(column, r, 100, 20);
		animal_type.addKeyListener(this);
		animal_type.addItem(new ValuePair("-1",""));
		animal_type.addItem(new ValuePair(""+Animal.ANIMAL_TYPE_QUADRUPED,"Quadruped"));
		animal_type.addItem(new ValuePair(""+Animal.ANIMAL_TYPE_HUMAN,"Human"));
		animal_type.addItem(new ValuePair(""+Animal.ANIMAL_TYPE_MANHEAD,"Man head"));
		animal_type.addItem(new ValuePair(""+Animal.ANIMAL_TYPE_MANHAND,"Man hand"));
		animal_type.addItemListener(this);
		
		animal_type.setSelectedIndex(0);
		right.add(animal_type);
		
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
		
		
		jlb=new JLabel("Head DZ");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		head_DZ=new DoubleTextField();
		head_DZ.setBounds(column, r, 100, 20);
		head_DZ.addKeyListener(this);
		right.add(head_DZ);
		
		r+=30;
		
		jlb=new JLabel("Head DX");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		head_DX=new DoubleTextField();
		head_DX.setBounds(column, r, 100, 20);
		head_DX.addKeyListener(this);
		right.add(head_DX);
		
		r+=30;
		
		jlb=new JLabel("Head DY");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		head_DY=new DoubleTextField();
		head_DY.setBounds(column, r, 100, 20);
		head_DY.addKeyListener(this);
		right.add(head_DY);
		
		r+=30;
		
		jlb=new JLabel("Neck len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		neck_length=new DoubleTextField();
		neck_length.setBounds(column, r, 100, 20);
		neck_length.addKeyListener(this);
		right.add(neck_length);
		
		r+=30;
		
		jlb=new JLabel("Neck side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		neck_side=new DoubleTextField();
		neck_side.setBounds(column, r, 100, 20);
		neck_side.addKeyListener(this);
		right.add(neck_side);
		
		r+=30;
		
		jlb=new JLabel("Humerus length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		humerus_length=new DoubleTextField();
		humerus_length.setBounds(column, r, 100, 20);
		humerus_length.addKeyListener(this);
		right.add(humerus_length);
		
		r+=30;
		
		jlb=new JLabel("Radius length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		radius_length=new DoubleTextField(); 
		radius_length.setBounds(column, r, 100, 20);
		radius_length.addKeyListener(this);
		right.add(radius_length);
		
		r+=30;
		
		jlb=new JLabel("Femur length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		femur_length=new DoubleTextField();
		femur_length.setBounds(column, r, 100, 20);
		femur_length.addKeyListener(this);
		right.add(femur_length);
		
		r+=30;
		
		jlb=new JLabel("Shinbone length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		shinbone_length=new DoubleTextField();
		shinbone_length.setBounds(column, r, 100, 20);
		shinbone_length.addKeyListener(this);
		right.add(shinbone_length);
		
		r+=30;
		
		jlb=new JLabel("Foot length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foot_length=new DoubleTextField();
		foot_length.setBounds(column, r, 100, 20);
		foot_length.addKeyListener(this);
		right.add(foot_length);
		
		r+=30;
		
		jlb=new JLabel("Leg side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		leg_side=new DoubleTextField();
		leg_side.setBounds(column, r, 100, 20);
		leg_side.addKeyListener(this);
		right.add(leg_side);
		
		r+=30;
			
        generate=new JButton("Update");
        generate.setBounds(10,r,100,20);
        generate.addActionListener(this);
        generate.addKeyListener(this);
        right.add(generate);
		
		add(right);
		
		initRightDataHuman();
		
	}
	

	public void initRightData() {

		if(animal==null)
			return;

		int type=animal.getAnimal_type();

		if(type==Animal.ANIMAL_TYPE_HUMAN)
			initRightDataHuman();
		else if(type==Animal.ANIMAL_TYPE_QUADRUPED)
			initRightDataQuadruped();
		else if(type==Animal.ANIMAL_TYPE_MANHEAD)
			initRightDataManHead();
		else if(type==Animal.ANIMAL_TYPE_MANHAND)
			initRightDataManHand();
	}



	public void initRightDataHuman() {
		

		x_side.setText(100);
		y_side.setText(40);
		z_side.setText(130);
	
		
		head_DZ.setText(50);
		head_DX.setText(50);
		head_DY.setText(50);
		
		neck_length.setText(20);
		neck_side.setText(40);
		
		
		leg_side.setText(20);
		humerus_length.setText(100);
		radius_length.setText(50);
		
		femur_length.setText(100);
		shinbone_length.setText(100);
		
		foot_length.setText(30);
	}
	
	public void initRightDataQuadruped() { 
		
	 
		x_side.setText(100);
		y_side.setText(200);
		z_side.setText(100);
		
		neck_length.setText(100);
		neck_side.setText(50);
		head_DZ.setText(50);
		head_DX.setText(80);
		head_DY.setText(80);
		
		femur_length.setText(100);
		shinbone_length.setText(100);
		leg_side.setText(20);
		
		humerus_length.setText(100);
		radius_length.setText(50);
				
		foot_length.setText(30);
	}
	
	private void initRightDataManHead() {
		
		x_side.setText(0);
		y_side.setText(0);
		z_side.setText(0);
	
		
		head_DZ.setText(400);
		head_DX.setText(400);
		head_DY.setText(400);
		
		neck_length.setText(200);
		neck_side.setText(400);
		
		
		leg_side.setText(20);
		humerus_length.setText(100);
		radius_length.setText(50);
		
		femur_length.setText(100);
		shinbone_length.setText(100);
		
		foot_length.setText(30);
		
	}
	
	private void initRightDataManHand() {
		
		x_side.setText(160);
		y_side.setText(200);
		z_side.setText(20);
	
		
		head_DZ.setText(400);
		head_DX.setText(400);
		head_DY.setText(400);
		
		neck_length.setText(200);
		neck_side.setText(400);
		
		
		leg_side.setText(20);
		humerus_length.setText(100);
		radius_length.setText(50);
		
		femur_length.setText(100);
		shinbone_length.setText(100);
		
		foot_length.setText(30);
		
	}
	
	private void setRightData(Animal animal) {
		
		for (int i = 0; i < animal_type.getItemCount(); i++) {
			ValuePair vp= (ValuePair) animal_type.getItemAt(i);
			if(vp.getId().equals(""+animal.getAnimal_type()))
			{
				animal_type.setSelectedIndex(i);
				break;
			}	
		}
	
		x_side.setText(animal.getX_side());
		y_side.setText(animal.getY_side());
		z_side.setText(animal.getZ_side());
		
		head_DZ.setText(animal.getHead_DZ());
		head_DX.setText(animal.getHead_DX());
		head_DY.setText(animal.getHead_DY());
		neck_length.setText(animal.getNeck_length());
		neck_side.setText(animal.getNeck_side());
		
		leg_side.setText(animal.getLeg_side());
		femur_length.setText(animal.getFemur_length());
		shinbone_length.setText(animal.getShinbone_length());
			
		humerus_length.setText(animal.getHumerus_length());
		radius_length.setText(animal.getRadius_length());		
		foot_length.setText(animal.getFoot_length());
		


	}

	
	public void buildMenuBar() {
		
		jmb=new JMenuBar();
		
		jm_file=new JMenu("File");
		jm_file.addMenuListener(this);
		jmb.add(jm_file);
		
		jmt_load_file = new JMenuItem("Load file");
		jmt_load_file.addActionListener(this);
		jm_file.add(jmt_load_file);
		
		jmt_save_file = new JMenuItem("Save file");
		jmt_save_file.addActionListener(this);
		jm_file.add(jmt_save_file);
		
		jm_file.addSeparator();
		

		
		jmt_save_mesh = new JMenuItem("Save mesh");
		jmt_save_mesh.addActionListener(this);
		jm_file.add(jmt_save_mesh);
		
		jm_change=new JMenu("Change");
		jm_change.addMenuListener(this);
		jmb.add(jm_change);
		
		jmt_undo_last = new JMenuItem("Undo last");
		jmt_undo_last.setEnabled(false);
		jmt_undo_last.addActionListener(this);
		jm_change.add(jmt_undo_last);
		
		
		jm_view=new JMenu("View");
		jm_view.addMenuListener(this);
		jmb.add(jm_view);
		
		jmt_preview = new JMenuItem("Preview");
		jmt_preview.addActionListener(this);
		jm_view.add(jmt_preview);
		
		
		jm_filter=new JMenu("Filter");
		jm_filter.addMenuListener(this);
		jmb.add(jm_filter);
		
		jm_filters=new JCheckBoxMenuItem[6];
		
		jm_filters[0] = new JCheckBoxMenuItem("Filter back");
		jm_filters[0].addActionListener(this);
		jm_filters[0].setActionCommand(""+Renderer3D.CAR_BACK);
		jm_filter.add(jm_filters[0]);

		jm_filters[1] = new JCheckBoxMenuItem("Filter front");
		jm_filters[1].addActionListener(this);
		jm_filters[1].setActionCommand(""+Renderer3D.CAR_FRONT);
		jm_filter.add(jm_filters[1]);
		
		jm_filters[2] = new JCheckBoxMenuItem("Filter top");
		jm_filters[2].addActionListener(this);
		jm_filters[2].setActionCommand(""+Renderer3D.CAR_TOP);
		jm_filter.add(jm_filters[2]);
		
		jm_filters[3] = new JCheckBoxMenuItem("Filter bottom");
		jm_filters[3].addActionListener(this);
		jm_filters[3].setActionCommand(""+Renderer3D.CAR_BOTTOM);
		jm_filter.add(jm_filters[3]);
		
		jm_filters[4] = new JCheckBoxMenuItem("Filter left");
		jm_filters[4].addActionListener(this);
		jm_filters[4].setActionCommand(""+Renderer3D.CAR_LEFT);
		jm_filter.add(jm_filters[4]);
		
		jm_filters[5]= new JCheckBoxMenuItem("Filter right");
		jm_filters[5].addActionListener(this);
		jm_filters[5].setActionCommand(""+Renderer3D.CAR_RIGHT);
		jm_filter.add(jm_filters[5]);
		
		setJMenuBar(jmb);
		
	}
	

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object obj = arg0.getSource();
		
		if(obj==jmt_load_file){
			
			loadData();
			
			
			
		}else if(obj==jmt_save_file){
			
			saveData();
			
		}else if(obj==jmt_save_mesh){
			
			saveMesh(); 
			
		}
		else if(obj==jmt_preview){
			
			preview();
			
		}else if (obj==jmt_undo_last){
			
			undo();
			
		}else if(obj==generate){
			
			generate();
		}else{
			
			boolean found=false;
		
			for (int i = 0; i < jm_filters.length; i++) {
				
				
				if(obj==jm_filters[i]){
					
				
					if(jm_filters[i].isSelected()){
						center.setFilter(jm_filters[i].getActionCommand());
					    found=true;
					}	
					else
						center.setFilter(null);
				}else{
					
					jm_filters[i].setSelected(false);
				}		
	
				
			}
			draw();
		}
	}
	
	public void preview() {
		
		if(animal==null)
			return;
		
		Editor editor=new Editor();
		editor.meshes[0]=animal.buildMesh();
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(editor);
		
	}
	
	public void generate() {
		
			prepareUndo();
			
			double xside=x_side.getvalue();
			double yside=y_side.getvalue();
			double zside=z_side.getvalue();
		
			double headDZ = head_DZ.getvalue();
			double headDX = head_DX.getvalue(); 
			double headDY = head_DY.getvalue(); 
			double neckLength= neck_length.getvalue();
			double neckSide= neck_side.getvalue();
			
			double humerusLength = humerus_length.getvalue();
			double radiusLength = radius_length.getvalue();
			
			double femurLength=femur_length.getvalue();
			double shinboneLength=shinbone_length.getvalue();
			double legSide=leg_side.getvalue();
			
			double footLength=foot_length.getvalue();
			
			 ValuePair vp= (ValuePair)animal_type.getSelectedItem();
			
			 int type=Integer.parseInt(vp.getId());
			    if(type<0)
			    	type=Animal.ANIMAL_TYPE_HUMAN;
			
			if(animal==null){
				
				animal=new Animal(xside,yside,zside,type,
						femurLength,shinboneLength,legSide,
						headDZ,headDX,headDY,neckLength,neckSide,
						humerusLength,radiusLength,footLength);
				
			}else{
				
				Animal expAnimal = new Animal(xside,yside,zside,type,
						femurLength,shinboneLength,legSide,
						headDZ,headDX,headDY,neckLength,neckSide,
						humerusLength,radiusLength,footLength);
				
				animal=expAnimal;
			}
			
			draw();
			setRightData(animal);
	}
	
	public void loadData() {

		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Load Track");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			loadData(file);
			draw();	
			currentDirectory=new File(file.getParent());
		} 
		
	}

	public void loadData(File file) {
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			
			String str=null;
			
			while((str=br.readLine())!=null){
				
				int indx=str.indexOf("A=");
				if(indx>=0){
					
					String value=str.substring(indx+2);
					animal=Animal.buildAnimal(value);
					setRightData(animal);
					
				}
	
			}
			br.close();
			

			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}


	private void saveMesh() {
		
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save mesh");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			saveMesh(file);
			currentDirectory=new File(file.getParent());
		} 
		
	}


	public void saveMesh(File file) {
		
		
		if(animal==null)
			return;
		
	
		PrintWriter pw;
		try {
			Editor editor=new Editor();
			editor.meshes[0]=animal.buildMesh();
			pw = new PrintWriter(new FileOutputStream(file));
			editor.forceReading=true;
			editor.saveLines(pw);
			pw.close();
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
	}

	public void saveData() {
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save data");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			saveData(file);
			currentDirectory=new File(file.getParent());
		} 
		
	}

	public void saveData(File file) {
		
		
		if(animal==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			pw.println(animal.toString());
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void undo() {

		if(oldAnimal.size()>0)
			animal=(Animal) oldAnimal.pop();
		
		setRightData(animal);
		
		if(oldAnimal.size()==0)
			jmt_undo_last.setEnabled(false);
		
		draw();
	}
	
	public void prepareUndo() {
		
		if(animal==null)
			return;
		
		jmt_undo_last.setEnabled(true);
		
		if(oldAnimal.size()>=max_stack_size)
			oldAnimal.removeElementAt(0);
		
		oldAnimal.push(animal.clone());
		
		
	}
	
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		redrawAfterMenu=true;
		
	}

	@Override
	public void menuSelected(MenuEvent arg0) {
		redrawAfterMenu=false;
		
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		if(arg0.getUnitsToScroll()<0)
			center.translate(0,-1);
		else
			center.translate(0,1);
		
		draw();
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		int code=arg0.getKeyCode();
		
		
		if(code==KeyEvent.VK_LEFT)
			center.translate(+1,0);
		else if(code==KeyEvent.VK_RIGHT)
			center.translate(-1,0);
		else if(code==KeyEvent.VK_UP)
			center.translate(0,-1);
		else if(code==KeyEvent.VK_DOWN)
			center.translate(0,+1);
		else if(code==KeyEvent.VK_F1)
			center.zoom(+1);		
		else if(code==KeyEvent.VK_F2)
			center.zoom(-1);
		
		draw();
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		Object obj = arg0.getSource();
		
		if(obj==animal_type){
			
			 ValuePair vp= (ValuePair)animal_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			   if(type<0)
			    	type=Animal.ANIMAL_TYPE_HUMAN;
			   
			   if(type==Animal.ANIMAL_TYPE_HUMAN)
				   initRightDataHuman();
			   else if(type==Animal.ANIMAL_TYPE_QUADRUPED)
				   initRightDataQuadruped();
			   else if(type==Animal.ANIMAL_TYPE_MANHEAD)
				   initRightDataManHead();
			   else if(type==Animal.ANIMAL_TYPE_MANHAND)
				   initRightDataManHand();
			
		}
		
	}


}
