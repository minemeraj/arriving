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
	private DoubleTextField leg_length;
	private DoubleTextField leg_side;
	private DoubleTextField head_length;
	private DoubleTextField head_side;
	private DoubleTextField neck_length;
	private DoubleTextField arm_length;
	
	private JButton generate;
	
	JFileChooser fc = new JFileChooser();
	File currentDirectory=new File("lib");
	
	public Stack oldAnimal=null;
	int max_stack_size=10;
	
	Animal animal=null;


	
	
	
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
		

		JLabel jlb=new JLabel("Body DX");
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
		
		jlb=new JLabel("Animal type:");
		jlb.setBounds(5, r, 120, 20);
		right.add(jlb);

		animal_type=new JComboBox();
		animal_type.setBounds(column, r, 100, 20);
		animal_type.addKeyListener(this);
		animal_type.addItem(new ValuePair("-1",""));
		animal_type.addItem(new ValuePair(""+Animal.ANIMAL_TYPE_QUADRUPED,"Quadruped"));
		animal_type.addItem(new ValuePair(""+Animal.ANIMAL_TYPE_HUMAN,"Human"));
		animal_type.addItemListener(this);
		
		animal_type.setSelectedIndex(0);
		right.add(animal_type);
		
		r+=30;
		
		jlb=new JLabel("Leg length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		leg_length=new DoubleTextField();
		leg_length.setBounds(column, r, 100, 20);
		leg_length.addKeyListener(this);
		right.add(leg_length);
		
		r+=30;
		
		jlb=new JLabel("Leg side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		leg_side=new DoubleTextField();
		leg_side.setBounds(column, r, 100, 20);
		leg_side.addKeyListener(this);
		right.add(leg_side);
		
		r+=30;
		
		jlb=new JLabel("Head len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		head_length=new DoubleTextField();
		head_length.setBounds(column, r, 100, 20);
		head_length.addKeyListener(this);
		right.add(head_length);
		
		r+=30;
		
		jlb=new JLabel("Head side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		head_side=new DoubleTextField();
		head_side.setBounds(column, r, 100, 20);
		head_side.addKeyListener(this);
		right.add(head_side);
		
		r+=30;
		
		jlb=new JLabel("Neck len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		neck_length=new DoubleTextField();
		neck_length.setBounds(column, r, 100, 20);
		neck_length.addKeyListener(this);
		right.add(neck_length);
		
		r+=30;
		
		jlb=new JLabel("Arm length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		arm_length=new DoubleTextField();
		arm_length.setBounds(column, r, 100, 20);
		arm_length.addKeyListener(this);
		right.add(arm_length);
		
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
	}

	public void initRightDataHuman() {
		

		x_side.setText(100);
		y_side.setText(40);
		z_side.setText(150);
		leg_length.setText(150);
		leg_side.setText(20);
		arm_length.setText(150);
		neck_length.setText(50);
		head_length.setText(50);
		head_side.setText(50);
	}
	
	public void initRightDataQuadruped() {
		
	
		x_side.setText(100);
		y_side.setText(200);
		z_side.setText(100);
		leg_length.setText(100);
		leg_side.setText(20);
		arm_length.setText(150);
		neck_length.setText(100);
		head_length.setText(50);
		head_side.setText(80);
	}
	
	private void setRightData(Animal animal) {
	
		x_side.setText(animal.getX_side());
		y_side.setText(animal.getY_side());
		z_side.setText(animal.getZ_side());
		leg_side.setText(animal.getLeg_side());
		leg_length.setText(animal.getLeg_length());
		
		for (int i = 0; i < animal_type.getItemCount(); i++) {
			ValuePair vp= (ValuePair) animal_type.getItemAt(i);
			if(vp.getId().equals(""+animal.getAnimal_type()))
			{
				animal_type.setSelectedIndex(i);
				break;
			}	
		}

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
			
			double xside=x_side.getvalue();
			double yside=y_side.getvalue();
			double zside=z_side.getvalue();
			double legLength=leg_length.getvalue();
			double legSide=leg_side.getvalue();
			double headLength = head_length.getvalue();
			double headSide = head_side.getvalue(); 
			double neckLength= neck_length.getvalue();
			double armLength = arm_length.getvalue();
			
			 ValuePair vp= (ValuePair)animal_type.getSelectedItem();
			
			 int type=Integer.parseInt(vp.getId());
			    if(type<0)
			    	type=Animal.ANIMAL_TYPE_HUMAN;
			
			if(animal==null){
				
				animal=new Animal(xside,yside,zside,type,legLength,legSide,headLength,headSide,neckLength,armLength);
				
			}else{
				
				Animal expAnimal = new Animal(xside,yside,zside,type,legLength,legSide,headLength,headSide,neckLength,armLength);
				
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

		/*if(oldplan.size()>0)
			plan=(Buildingplan) oldplan.pop();
		
		if(oldplan.size()==0)
			jmt_undo_last.setEnabled(false);*/
		
		draw();
	}
	
	public void prepareUndo() {
		
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
			
		}
		
	}
}
