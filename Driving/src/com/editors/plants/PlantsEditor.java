package com.editors.plants;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.PolygonMesh;
import com.editors.CustomEditor;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.IntegerTextField;
import com.editors.object.ObjectEditorPreviewPanel;
import com.editors.plants.data.Plant;

public class PlantsEditor extends CustomEditor {
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;
	
	private DoubleTextField trunck_length;
	private DoubleTextField trunk_radius;
	private DoubleTextField foliage_length;
	private DoubleTextField foliage_radius;	
	private IntegerTextField foliage_meridians;
	private IntegerTextField foliage_parallels;
	private IntegerTextField foliage_lobes;
	
	public Stack oldPlant=null;
	int max_stack_size=10;
	
	Plant plant=null;

	
	
	
	public PlantsEditor(){
		
		setTitle("Plant editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new PlantsJPanel();
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
							center.draw(plant);
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
		
		oldPlant=new Stack();
	}

	public static void main(String[] args) {
		
		PlantsEditor be=new PlantsEditor();
	}
	
	
	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
	}

	public void draw() {
		
		center.draw(plant);
		
	}
	
	public void buildRightPanel() {
		
		
		JPanel right=new JPanel(null);
		
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		
		int r=10;
		
		int column=100;
		

		JLabel jlb=new JLabel("Trunk len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trunck_length=new DoubleTextField();
		trunck_length.setBounds(column, r, 100, 20);
		trunck_length.addKeyListener(this);
		right.add(trunck_length);

		r+=30;
		
		jlb=new JLabel("Trunk rad");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trunk_radius=new DoubleTextField();
		trunk_radius.setBounds(column, r, 100, 20);
		trunk_radius.addKeyListener(this);
		right.add(trunk_radius);
		
		r+=30;
		
		jlb=new JLabel("Foliage len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foliage_length=new DoubleTextField();
		foliage_length.setBounds(column, r, 100, 20);
		foliage_length.addKeyListener(this);
		right.add(foliage_length);
		
		r+=30;
		
		jlb=new JLabel("Foliage rad");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foliage_radius=new DoubleTextField();
		foliage_radius.setBounds(column, r, 100, 20);
		foliage_radius.addKeyListener(this);
		right.add(foliage_radius);
		
		r+=30;
		
		jlb=new JLabel("Foliage merid");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foliage_meridians=new IntegerTextField();
		foliage_meridians.setBounds(column, r, 100, 20);
		foliage_meridians.addKeyListener(this);
		right.add(foliage_meridians);
		
		r+=30;
		
		jlb=new JLabel("Foliage parall");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foliage_parallels=new IntegerTextField();
		foliage_parallels.setBounds(column, r, 100, 20);
		foliage_parallels.addKeyListener(this);
		right.add(foliage_parallels);
		
		r+=30;
		
		jlb=new JLabel("Foliage lobes");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foliage_lobes=new IntegerTextField();
		foliage_lobes.setBounds(column, r, 100, 20);
		foliage_lobes.addKeyListener(this);
		right.add(foliage_lobes);
		
		r+=30;
			
        generate=new JButton("Update");
        generate.setBounds(10,r,100,20);
        generate.addActionListener(this);
        generate.addKeyListener(this);
        right.add(generate);
        
        r+=30;
		
		jlb=new JLabel("Scale");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		scaleValue=new DoubleTextField();
		scaleValue.setBounds(column, r, 100, 20);
		scaleValue.addKeyListener(this);
		scaleValue.setToolTipText("If empty=1.0");
		right.add(scaleValue);
		
		add(right);
		
		initRightData();
		
	}
	

	public void initRightData() {
	
		trunck_length.setText(200);
		trunk_radius.setText(50);
		foliage_length.setText(200);
		foliage_radius.setText(200);
		foliage_meridians.setText(12);
		foliage_parallels.setText(8);
		foliage_lobes.setText(0);
	}
	
	private void setRightData(Plant plant) {
	
		trunck_length.setText(plant.getTrunk_lenght());
		trunk_radius.setText(plant.getTrunk_radius());
		foliage_length.setText(plant.getFoliage_length());
		foliage_radius.setText(plant.getFoliage_radius());
		
		foliage_meridians.setText(plant.getFoliage_meridians());
		foliage_parallels.setText(plant.getFoliage_parallels());
		foliage_lobes.setText(plant.getFoliage_lobes());
	}

	
	public void preview() {
		
		if(plant==null)
			return;
		
		Editor editor=new Editor();
		editor.meshes[0]=buildMesh();
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(editor);
		
	}
	
	@Override
	public PolygonMesh buildMesh() {
		if(scaleValue!=null){
			
			double val=scaleValue.getvalue();
			
			if(val>0)
				scale=val;
			else
				scale=1.0;
		}
		return plant.buildMesh(scale);
	}
	
	public void generate() {
		
			prepareUndo();
			
			double trunckLength=trunck_length.getvalue();
			double trunkRadius=trunk_radius.getvalue();
			double foliageLength=foliage_length.getvalue();
			double foliageRadius=foliage_radius.getvalue();
			int foliageParallels=foliage_parallels.getvalue();
			int foliageMeridians=foliage_meridians.getvalue();
			int foliageLobes=foliage_lobes.getvalue();
			
			if(plant==null){
				
				plant=new Plant(trunckLength,trunkRadius,foliageLength,foliageRadius,
						foliageMeridians,foliageParallels,foliageLobes
						);
				
			}else{
				
				Plant expPlant = new Plant(trunckLength,trunkRadius,foliageLength,foliageRadius,
						foliageMeridians,foliageParallels,foliageLobes
						);
				
				plant=expPlant;
			} 
			
			draw();
			setRightData(plant);
	}
	
	public void loadData(File file) {
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			
			String str=null;
			
			while((str=br.readLine())!=null){
				
				int indx=str.indexOf("F=");
				if(indx>=0){
					
					String value=str.substring(indx+2);
					plant=Plant.buildPlant(value);
					
					setRightData(plant);
					
				}
	
			}
			br.close();
			

			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}

	public void saveMesh(File file) {
		
		
		if(plant==null)
			return;
		
	
		PrintWriter pw; 
		try {

			meshes[0]=buildMesh();
			pw = new PrintWriter(new FileOutputStream(file));
			forceReading=true;
			saveLines(pw);
			pw.close();
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
	}

	public void saveData(File file) {
		
		
		if(plant==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			pw.println(plant.toString());
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void undo() {

		if(oldPlant.size()>0)
			plant=(Plant) oldPlant.pop();
		
		if(oldPlant.size()==0)
			jmt_undo_last.setEnabled(false);
		
		draw();
	}
	
	public void prepareUndo() {
		
		if(plant==null)
			return;
		
		jmt_undo_last.setEnabled(true);
		
		if(oldPlant.size()>=max_stack_size)
			oldPlant.removeElementAt(0);
		
		oldPlant.push(plant.clone());
		
		
	}
	

}
