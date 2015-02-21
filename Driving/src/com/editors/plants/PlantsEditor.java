package com.editors.plants;

import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import com.PolygonMesh;
import com.editors.CustomEditor;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.IntegerTextField;
import com.editors.ValuePair;
import com.editors.forniture.data.Forniture;
import com.editors.object.ObjectEditorPreviewPanel;
import com.editors.plants.data.Plant;

public class PlantsEditor extends CustomEditor implements ItemListener {
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;
	
	private DoubleTextField trunck_length;
	private DoubleTextField trunk_upper_radius;
	private DoubleTextField foliage_length;
	private DoubleTextField foliage_radius;	
	private IntegerTextField foliage_meridians;
	private IntegerTextField foliage_parallels;
	private IntegerTextField foliage_lobes;
	
	private DoubleTextField trunk_lower_radius;
	private IntegerTextField trunk_meridians;
	private IntegerTextField trunk_parallels;
	private DoubleTextField lobe_percentage_depth;
	
	Plant plant=null;

	private JComboBox plant_type;
	
	public Stack oldPlant=null;
	int max_stack_size=10;
	private DoubleTextField foliage_barycenter;
	

	
	
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
		
		JLabel jlb=new JLabel("Plant type:");
		jlb.setBounds(5, r, 120, 20);
		right.add(jlb);
		
		plant_type=new JComboBox();
		plant_type.setBounds(column, r, 110, 20);
		plant_type.addKeyListener(this);
		plant_type.addItem(new ValuePair("-1",""));
		plant_type.addItem(new ValuePair(""+Plant.PLANT_TYPE_0,"Plant0"));
		
		plant_type.addItemListener(this);
		
		plant_type.setSelectedIndex(0);
		right.add(plant_type);
		
		r+=30;
		

		jlb=new JLabel("Trunk len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trunck_length=new DoubleTextField();
		trunck_length.setBounds(column, r, 100, 20);
		trunck_length.addKeyListener(this);
		right.add(trunck_length);

		r+=30;
		
		jlb=new JLabel("Trunk up rad");
		jlb.setBounds(5, r, 110, 20);
		right.add(jlb);
		trunk_upper_radius=new DoubleTextField();
		trunk_upper_radius.setBounds(column, r, 100, 20);
		trunk_upper_radius.addKeyListener(this);
		right.add(trunk_upper_radius);
		
		r+=30;
		
		jlb=new JLabel("Trunk lw rad");
		jlb.setBounds(5, r, 110, 20);
		right.add(jlb);
		trunk_lower_radius=new DoubleTextField();
		trunk_lower_radius.setBounds(column, r, 100, 20);
		trunk_lower_radius.addKeyListener(this);
		right.add(trunk_lower_radius);
		
	
		
		r+=30;
		
		jlb=new JLabel("Trunk parall");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trunk_parallels=new IntegerTextField();
		trunk_parallels.setBounds(column, r, 100, 20);
		trunk_parallels.addKeyListener(this);
		right.add(trunk_parallels);
		
		r+=30;
		
		jlb=new JLabel("Trunk merid");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trunk_meridians=new IntegerTextField();
		trunk_meridians.setBounds(column, r, 100, 20);
		trunk_meridians.addKeyListener(this);
		right.add(trunk_meridians);
		
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
		
		jlb=new JLabel("Foliage baryc.");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		foliage_barycenter=new DoubleTextField();
		foliage_barycenter.setBounds(column, r, 100, 20);
		foliage_barycenter.addKeyListener(this);
		right.add(foliage_barycenter);
		
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
		
		jlb=new JLabel("Lobe depth %");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		lobe_percentage_depth=new DoubleTextField();
		lobe_percentage_depth.setBounds(column, r, 100, 20);
		lobe_percentage_depth.addKeyListener(this);
		right.add(lobe_percentage_depth);
		
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
		
		initRightPlant0Data();
		
	}
	

	public void initRightPlant0Data() {
	
		trunck_length.setText(200);
		trunk_upper_radius.setText(24);
		trunk_lower_radius.setText(30);
		trunk_meridians.setText(10);
		trunk_parallels.setText(4);
		
		foliage_length.setText(200);
		foliage_radius.setText(200);
		foliage_barycenter.setText(100);
		foliage_meridians.setText(12);
		foliage_parallels.setText(8);
		foliage_lobes.setText(0);
		lobe_percentage_depth.setText(80);
	}
	
	private void setRightData(Plant plant) {
		
		for (int i = 0; i < plant_type.getItemCount(); i++) {
			ValuePair vp= (ValuePair) plant_type.getItemAt(i);
			if(vp.getId().equals(""+plant.getPlant_type()))
			{
				plant_type.setSelectedIndex(i);
				break;
			}	
		}
	
		trunck_length.setText(plant.getTrunk_lenght());
		trunk_upper_radius.setText(plant.getTrunk_upper_radius());
		trunk_lower_radius.setText(plant.getTrunk_lower_radius());
		trunk_meridians.setText(plant.getTrunk_meridians());
		trunk_parallels.setText(plant.getTrunk_parallels());
		
		foliage_length.setText(plant.getFoliage_length());
		foliage_radius.setText(plant.getFoliage_radius());
		
		foliage_meridians.setText(plant.getFoliage_meridians());
		foliage_parallels.setText(plant.getFoliage_parallels());
		foliage_lobes.setText(plant.getFoliage_lobes());
		lobe_percentage_depth.setText(plant.getLobe_percentage_depth());
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
			
			 ValuePair vp= (ValuePair)plant_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			    if(type<0)
			    	type=Plant.PLANT_TYPE_0;
			
			double trunckLength=trunck_length.getvalue();
			double trunkUpRadius=trunk_upper_radius.getvalue();
			double trunkLwRadius=trunk_lower_radius.getvalue();
			int trunkParallels=trunk_parallels.getvalue();
			int trunkMeridians=trunk_meridians.getvalue();
			
			double foliageLength=foliage_length.getvalue();
			double foliageRadius=foliage_radius.getvalue();
			double foliageBarycenter=foliage_barycenter.getvalue();
			int foliageParallels=foliage_parallels.getvalue();
			int foliageMeridians=foliage_meridians.getvalue();
			int foliageLobes=foliage_lobes.getvalue();
			double lobePercentageDepth=lobe_percentage_depth.getvalue();
			
			if(lobePercentageDepth>100.0 || lobePercentageDepth<0)
			{
				lobePercentageDepth=100.0;
				lobe_percentage_depth.setText(lobePercentageDepth);
				
			}
			
			if(foliageBarycenter>foliageLength){
				
				foliageBarycenter=foliageLength;
				foliage_barycenter.setText(foliageBarycenter);
				
			}
			
			if(plant==null){
				
				plant=new Plant(type,
						trunckLength,trunkUpRadius,trunkLwRadius,
						trunkMeridians,trunkParallels,
						foliageLength,foliageRadius,foliageBarycenter,
						foliageMeridians,foliageParallels,foliageLobes,lobePercentageDepth
						);
				
			}else{
				
				Plant expPlant = new Plant(
						type,
						trunckLength,trunkUpRadius,trunkLwRadius,
						trunkMeridians,trunkParallels,
						foliageLength,foliageRadius,foliageBarycenter,
						foliageMeridians,foliageParallels,foliageLobes,lobePercentageDepth
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
			boolean isCustom=(meshes[0].getTexturePoints()!=null 
					&& meshes[0].getTexturePoints().size()>0); 
			saveLines(pw,isCustom);
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

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object obj = arg0.getSource();
		
		if(obj==plant_type){
			
			 center.setTeta(0);
			 
			 ValuePair vp= (ValuePair)plant_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			 
			   if(type==Plant.PLANT_TYPE_0)
				   initRightPlant0Data();
			   else
				   initRightPlant0Data();
		}	 
		
	}
	
	@Override
	public void saveBaseCubicTexture(File file) {
		
    	if(plant.getPlant_type()==Plant.PLANT_TYPE_0 
			
    	){
    		
    		PolygonMesh pm = buildMesh();		
    		plant.getSpecificData().saveBaseCubicTexture(pm,file);
    	}
    	else
    		super.saveBaseCubicTexture(file);
	}
	

}
