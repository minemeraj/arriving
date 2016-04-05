package com.editors.forniture;

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
import com.editors.IntegerTextField;
import com.editors.ValuePair;
import com.editors.forniture.data.Forniture;

public class FornitureEditor extends CustomEditor implements ItemListener{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;
	
	private JComboBox chooseForniture;
	private DoubleTextField leg_length;
	private DoubleTextField leg_side;
	private DoubleTextField front_height;
	private DoubleTextField back_height;
	private DoubleTextField side_width;
	private DoubleTextField side_length;
	private DoubleTextField side_height;
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	private DoubleTextField z_side;
	public Stack oldForniture=null;
	int max_stack_size=10;
	
	Forniture forniture=null;
	private DoubleTextField upper_width;
	private DoubleTextField upper_length;
	private DoubleTextField upper_height;
	private IntegerTextField num_parallels;
	private IntegerTextField num_meridians;

	

	
	
	public FornitureEditor(){
		
		setTitle("Forniture editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new FornitureJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		buildMenuBar();
		
		buildRightPanel();
		
		addKeyListener(this);
		addMouseWheelListener(this);
		
		
		
		RepaintManager.setCurrentManager( 
				new RepaintManager(){
					@Override
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
	
	@Override
	public void buildRightPanel() {
		
		
		JPanel right=new JPanel(null);
		
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		
		int r=10;
		
		int column=100;
		

		JLabel jlb=new JLabel("X side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		x_side=new DoubleTextField();
		x_side.setBounds(column, r, 100, 20);
		x_side.addKeyListener(this);
		right.add(x_side);

		r+=30;
		
		jlb=new JLabel("Y side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		y_side=new DoubleTextField();
		y_side.setBounds(column, r, 100, 20);
		y_side.addKeyListener(this);
		right.add(y_side);
		
		r+=30;
		
		jlb=new JLabel("Z side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		z_side=new DoubleTextField();
		z_side.setBounds(column, r, 100, 20);
		z_side.addKeyListener(this);
		right.add(z_side);
		
		r+=30;
		
		jlb=new JLabel("Forniture type:");
		jlb.setBounds(5, r, 120, 20);
		right.add(jlb);

		chooseForniture=new JComboBox();
		chooseForniture.setBounds(column, r, 100, 20);
		chooseForniture.addKeyListener(this);
		chooseForniture.addItem(new ValuePair("-1",""));
		
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_BARREL,"Barrel"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_BED,"Bed"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_BOOKCASE,"Bookcase"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_CHAIR,"Chair"));	
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_CUPBOARD,"Cupboard"));	
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_GLOBE0,"Globe0"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_GLOBE1,"Globe1"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_SOFA,"Sofa"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_STREETLIGHT,"Streetlight"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_TABLE,"Table"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_TOILET,"Toilet"));
		chooseForniture.addItem(new ValuePair(""+Forniture.FORNITURE_TYPE_WARDROBE,"Wardrobe"));
		chooseForniture.addItemListener(this);
		chooseForniture.setSelectedIndex(0);
		right.add(chooseForniture);
		
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
		
		jlb=new JLabel("Front height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		front_height=new DoubleTextField();
		front_height.setBounds(column, r, 100, 20);
		front_height.addKeyListener(this);
		right.add(front_height);
		
		r+=30;
		
		jlb=new JLabel("Back height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		back_height=new DoubleTextField();
		back_height.setBounds(column, r, 100, 20);
		back_height.addKeyListener(this);
		right.add(back_height);
		
		r+=30;
		
		jlb=new JLabel("Side width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		side_width=new DoubleTextField();
		side_width.setBounds(column, r, 100, 20);
		side_width.addKeyListener(this);
		right.add(side_width);
		
		r+=30;
		
		jlb=new JLabel("Side length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		side_length=new DoubleTextField();
		side_length.setBounds(column, r, 100, 20);
		side_length.addKeyListener(this);
		right.add(side_length);
		
		r+=30;
		
		jlb=new JLabel("Side height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		side_height=new DoubleTextField();
		side_height.setBounds(column, r, 100, 20);
		side_height.addKeyListener(this);
		right.add(side_height);

		
		r+=30;
		
		jlb=new JLabel("Upper width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		upper_width=new DoubleTextField();
		upper_width.setBounds(column, r, 100, 20);
		upper_width.addKeyListener(this);
		right.add(upper_width);
		
		r+=30;
		
		jlb=new JLabel("Upper length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		upper_length=new DoubleTextField();
		upper_length.setBounds(column, r, 100, 20);
		upper_length.addKeyListener(this);
		right.add(upper_length);
		
		r+=30;
		
		jlb=new JLabel("Upper height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		upper_height=new DoubleTextField();
		upper_height.setBounds(column, r, 100, 20);
		upper_height.addKeyListener(this);
		right.add(upper_height);
		
		r+=30;
		
		jlb=new JLabel("Num parall");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		num_parallels=new IntegerTextField();
		num_parallels.setBounds(column, r, 100, 20);
		num_parallels.addKeyListener(this);
		right.add(num_parallels);
		
		r+=30;
		
		jlb=new JLabel("Num merid");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		num_meridians=new IntegerTextField();
		num_meridians.setBounds(column, r, 100, 20);
		num_meridians.addKeyListener(this);
		right.add(num_meridians);

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
		
		initRightTableData();
		
	}
	

	private void initRightBedData() {
		
		x_side.setText(89);
		y_side.setText(200);
		z_side.setText(19);
		
		leg_length.setText(34);
		leg_side.setText(6);
		back_height.setText(49);
		front_height.setText(35);
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);

	}


	private void initRightSofaData() {
		
		x_side.setText(250);
		y_side.setText(100);
		z_side.setText(70);
		
		leg_length.setText(50);
		leg_side.setText(10);
		back_height.setText(100);
		front_height.setText(0);
		
		side_width.setText(20);
		side_length.setText(90);
		side_height.setText(50);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}

	public void initRightTableData() {
		

		x_side.setText(113);
		y_side.setText(200);
		z_side.setText(9);
		
		leg_length.setText(106);
		leg_side.setText(9);
		back_height.setText(0);
		front_height.setText(0);
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
	}
	
	private void initRightChairData() {
		
		x_side.setText(84);
		y_side.setText(92);
		z_side.setText(14);
		
		leg_length.setText(100);
		leg_side.setText(7);
		back_height.setText(104);
		front_height.setText(0);
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}



	private void initRightWardrobeData() {
		
		x_side.setText(137);
		y_side.setText(89);
		z_side.setText(398);
		
		leg_length.setText(0);
		leg_side.setText(0);
		back_height.setText(0);
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}
	

	private void initRightBookcaseData() {
		
		x_side.setText(69);
		y_side.setText(52);
		z_side.setText(153);
		
		leg_length.setText(110);
		leg_side.setText(5);
		back_height.setText(0);
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
	}
	
	

	private void initRightCupboardData() {
		
		x_side.setText(171);
		y_side.setText(76);
		z_side.setText(150);
		
		leg_length.setText(0);
		leg_side.setText(0);
		back_height.setText(0);
		
		
		side_width.setText(10);
		side_length.setText(20);
		side_height.setText(46);
		
		upper_width.setText(156);
		upper_length.setText(55);
		upper_height.setText(119);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}
	
	private void initRightToiletData() {
		x_side.setText(104);
		y_side.setText(200);
		z_side.setText(106);
		
		leg_length.setText(0);
		leg_side.setText(0);
		back_height.setText(0);
		
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}
	
	private void initRightBarrelData() {
		
		x_side.setText(0);
		y_side.setText(0);
		z_side.setText(266);
		
		leg_length.setText(0);
		leg_side.setText(0);
		back_height.setText(0);
		
		
		side_width.setText(118);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(76);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}
	
    private void initRightGlobeData() {
		
		x_side.setText(200);
		y_side.setText(0);
		z_side.setText(0);
		
		leg_length.setText(0);
		leg_side.setText(0);
		back_height.setText(0);
		
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(0);
		upper_length.setText(0);
		upper_height.setText(0);
		
		num_meridians.setText(10);
		num_parallels.setText(10);
		
	}

	
	private void initRightStretLightData() {
		
		x_side.setText(12);
		y_side.setText(0);
		z_side.setText(600);
		
		leg_length.setText(0);
		leg_side.setText(0);
		back_height.setText(0);
		
		
		side_width.setText(0);
		side_length.setText(0);
		side_height.setText(0);
		
		upper_width.setText(38);
		upper_length.setText(66);
		upper_height.setText(19);
		
		num_meridians.setText(0);
		num_parallels.setText(0);
		
	}
	
	private void setRightData(Forniture forniture) {


		
		for (int i = 0; i < chooseForniture.getItemCount(); i++) {
			ValuePair vp= (ValuePair) chooseForniture.getItemAt(i);
			if(vp.getId().equals(""+forniture.getForniture_type()))
			{
				chooseForniture.setSelectedIndex(i);
				break;
			}	
		}

		x_side.setText(forniture.getX_side());
		y_side.setText(forniture.getY_side());
		z_side.setText(forniture.getZ_side());
		leg_length.setText(forniture.getLeg_length());
		leg_side.setText(forniture.getLeg_side());
		back_height.setText(forniture.getBack_height());
	}

	@Override
	public void initialize() {
		
		center.initialize();
		
		oldForniture=new Stack();
	}

	public static void main(String[] args) {
		
		FornitureEditor be=new FornitureEditor();
	}
	
	@Override
	public void preview() {
		
		if(forniture==null)
			return;
		
		
		super.preview();
		
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
		return forniture.buildMesh(scale);
	}
	@Override
	public void generate() {
		
			prepareUndo();
			
			double xside=x_side.getvalue();
			double yside=y_side.getvalue();
			double zside=z_side.getvalue();
			double legSide=leg_side.getvalue();
			double legLength=leg_length.getvalue();
			double frontHeight=front_height.getvalue();
			double backHeight=back_height.getvalue();
			double sideWidth=side_width.getvalue();
			double sideLength=side_length.getvalue();
			double sideHeight=side_height.getvalue();
			double upperWidth=upper_width.getvalue();
			double upperLength=upper_length.getvalue();
			double upperHeight=upper_height.getvalue();
			int nMeridians=num_meridians.getvalue();
			int nParallels=num_parallels.getvalue();
			
			 ValuePair vp= (ValuePair)chooseForniture.getSelectedItem();
			 
			 int type=Integer.parseInt(vp.getId());
			    if(type<0)
			    	type=Forniture.FORNITURE_TYPE_TABLE;
		
			
			if(forniture==null){
								
				forniture=new Forniture(xside,yside,zside,type,
						legLength,legSide,
						frontHeight,backHeight,
						sideWidth,sideLength,sideHeight,
						upperWidth,upperLength,upperHeight,
						nMeridians,nParallels
						);
			
				
			}else{				
				
				Forniture expForniture = new Forniture(xside,yside,zside,type,
						legLength,legSide,
						frontHeight,backHeight,
						sideWidth,sideLength,sideHeight,
						upperWidth,upperLength,upperHeight,
						nMeridians,nParallels
						);
				
				
				forniture=expForniture;
			}
			
			draw();
			setRightData(forniture);
	}
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
	}
	@Override
	public void draw() {
		
		
		center.draw(forniture);
		
	}
	@Override
	public void loadData(File file) {
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			
			String str=null;
			
			while((str=br.readLine())!=null){
				
				int indx=str.indexOf("F=");
				if(indx>=0){
					
					String value=str.substring(indx+2);
					forniture=Forniture.buildForniture(value);
					setRightData(forniture);
					
				}
	
			}
			br.close();
			

			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}

	public void saveMesh(File file) {
		
		
		if(forniture==null)
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
	@Override
	public void saveData(File file) {
		
		
		if(forniture==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			pw.println(forniture.toString());
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void undo() {

		if(oldForniture.size()>0)
			forniture=(Forniture) oldForniture.pop();
		
		if(oldForniture.size()==0)
			jmt_undo_last.setEnabled(false);
		
		draw();
	}
	@Override
	public void prepareUndo() {
		
		if(forniture==null)
			return;
		
		jmt_undo_last.setEnabled(true);
		
		if(oldForniture.size()>=max_stack_size)
			oldForniture.removeElementAt(0);
		
		oldForniture.push(forniture.clone());
		
		
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object obj = arg0.getSource();
		
		if(obj==chooseForniture){
			
			center.setTeta(0);
			
			 ValuePair vp= (ValuePair)chooseForniture.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			   if(type<0)
			    	type=Forniture.FORNITURE_TYPE_TABLE;
			   
			   if(type==Forniture.FORNITURE_TYPE_TABLE)
				   initRightTableData();
			   else if(type==Forniture.FORNITURE_TYPE_CHAIR)
				   initRightChairData();
			   else if(type==Forniture.FORNITURE_TYPE_SOFA)
				   initRightSofaData();
			   else if(type==Forniture.FORNITURE_TYPE_BED)
				   initRightBedData();
			   else if(type==Forniture.FORNITURE_TYPE_WARDROBE)
				   initRightWardrobeData();	
			   else if(type==Forniture.FORNITURE_TYPE_BOOKCASE)
				   initRightBookcaseData();	
			   else if(type==Forniture.FORNITURE_TYPE_CUPBOARD)
				   initRightCupboardData();	
			   else if(type==Forniture.FORNITURE_TYPE_STREETLIGHT)
				   initRightStretLightData();	
			   else if(type==Forniture.FORNITURE_TYPE_TOILET)
				   initRightToiletData();	
			   else if(type==Forniture.FORNITURE_TYPE_BARREL)
				   initRightBarrelData();	
			   else if(type==Forniture.FORNITURE_TYPE_GLOBE0 ||
					   type==Forniture.FORNITURE_TYPE_GLOBE1)
				   initRightGlobeData();	
		
		}
		
	}




	@Override
    public void saveBaseCubicTexture(File file) {
    	if(forniture.getForniture_type()==Forniture.FORNITURE_TYPE_BARREL || 
			forniture.getForniture_type()==Forniture.FORNITURE_TYPE_TABLE || 
			forniture.getForniture_type()==Forniture.FORNITURE_TYPE_WARDROBE ||
			forniture.getForniture_type()==Forniture.FORNITURE_TYPE_CHAIR ||
			forniture.getForniture_type()==Forniture.FORNITURE_TYPE_SOFA ||
			forniture.getForniture_type()==Forniture.FORNITURE_TYPE_GLOBE0 ||
			forniture.getForniture_type()==Forniture.FORNITURE_TYPE_GLOBE1 
    			){
    		
    		PolygonMesh pm = buildMesh();		
    		forniture.getSpecificData().saveBaseCubicTexture(pm,file);
    	}
    	else
    		super.saveBaseCubicTexture(file);
    }


	@Override
	public void initRightData() {
		// TODO Auto-generated method stub
		
	} 

}
