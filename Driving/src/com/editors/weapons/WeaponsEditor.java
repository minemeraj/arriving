package com.editors.weapons;

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
import com.editors.object.ObjectEditorPreviewPanel;
import com.editors.weapons.data.Weapon;


public class WeaponsEditor extends CustomEditor implements ItemListener{
	
	public static int HEIGHT=800;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	private DoubleTextField barrel_length;
	private DoubleTextField barrel_radius;
	private IntegerTextField barrel_meridians;
	
	private DoubleTextField breech_length;
	private DoubleTextField breech_width;
	private DoubleTextField breech_height;
	private DoubleTextField butt_length;
	private DoubleTextField butt_width;
	private DoubleTextField butt_height;
	private DoubleTextField butt_end_width;
	private DoubleTextField butt_end_length;
	private DoubleTextField butt_end_height;
	private DoubleTextField rear_overhang;
	private DoubleTextField trigger_length;
	private DoubleTextField trigger_height;
	private DoubleTextField trigger_width;
	private DoubleTextField forearm_length;
	private DoubleTextField forearm_width;
	private DoubleTextField forearm_height;
	private DoubleTextField magazine_length;
	private DoubleTextField magazine_width;
	private DoubleTextField magazine_height;
	

	
	public Stack oldWeapon=null;
	int max_stack_size=10;
	
	Weapon weapon=null;
	private JComboBox weapon_type;



	public WeaponsEditor(){
		
		setTitle("Weapon editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new WeaponsJPanel();
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
							center.draw(weapon);
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
		
		oldWeapon=new Stack();
	}

	public static void main(String[] args) {
		
		WeaponsEditor be=new WeaponsEditor();
	}
	
	
	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
	}

	public void draw() {
		
		center.draw(weapon);
		
	}
	
	public void buildRightPanel() {
		
		
		JPanel right=new JPanel(null);
		
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		
		int r=10;
		
		int column=100;
		
		JLabel jlb=new JLabel("Weapon type:");
		jlb.setBounds(5, r, 120, 20);
		right.add(jlb);
		
		weapon_type=new JComboBox();
		weapon_type.setBounds(column, r, 180, 20);
		weapon_type.addKeyListener(this);
		weapon_type.addItem(new ValuePair("-1",""));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_AX,"Ax"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_BASEBALL_BAT,"Baseball bat"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_DOUBLE_BARREL_SHOTGUN,"Double barrel Shotgun"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_GUN,"Gun"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_REVOLVER,"Revolver"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_SHOTGUN,"Shotgun"));			
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_SUBMACHINEGUN,"Submachine gun"));
		weapon_type.addItemListener(this);
		
		weapon_type.setSelectedIndex(0);
		right.add(weapon_type);
		
		r+=30;

		jlb=new JLabel("Barrel len");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		barrel_length=new DoubleTextField();
		barrel_length.setBounds(column, r, 100, 20);
		barrel_length.addKeyListener(this);
		right.add(barrel_length);

		r+=30;
		
		jlb=new JLabel("Barrel rad");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		barrel_radius=new DoubleTextField();
		barrel_radius.setBounds(column, r, 100, 20);
		barrel_radius.addKeyListener(this);
		right.add(barrel_radius);
		
		
		r+=30;
		
		jlb=new JLabel("Barrel merid");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		barrel_meridians=new IntegerTextField();
		barrel_meridians.setBounds(column, r, 100, 20);
		barrel_meridians.addKeyListener(this);
		right.add(barrel_meridians);
		
		r+=30;
		
		jlb=new JLabel("Forearm length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		forearm_length=new DoubleTextField();
		forearm_length.setBounds(column, r, 100, 20);
		forearm_length.addKeyListener(this);
		right.add(forearm_length);
		
		r+=30;
		
		jlb=new JLabel("Forearm width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		forearm_width=new DoubleTextField();
		forearm_width.setBounds(column, r, 100, 20);
		forearm_width.addKeyListener(this);
		right.add(forearm_width);
		
		r+=30;
		
		jlb=new JLabel("Forearm height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		forearm_height=new DoubleTextField();
		forearm_height.setBounds(column, r, 100, 20);
		forearm_height.addKeyListener(this);
		right.add(forearm_height);
		
		r+=30;
		
		jlb=new JLabel("breech_length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		breech_length=new DoubleTextField();
		breech_length.setBounds(column, r, 100, 20);
		breech_length.addKeyListener(this);
		right.add(breech_length);
		
		r+=30;
		
		jlb=new JLabel("breech_width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		breech_width=new DoubleTextField();
		breech_width.setBounds(column, r, 100, 20);
		breech_width.addKeyListener(this);
		right.add(breech_width);

		
		r+=30;
		
		jlb=new JLabel("breech_height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		breech_height=new DoubleTextField();
		breech_height.setBounds(column, r, 100, 20);
		breech_height.addKeyListener(this);
		right.add(breech_height);
		
		r+=30;
		
		jlb=new JLabel("Magazine length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		magazine_length=new DoubleTextField();
		magazine_length.setBounds(column, r, 100, 20);
		magazine_length.addKeyListener(this);
		right.add(magazine_length);
		
		r+=30;
		
		jlb=new JLabel("Magazine width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		magazine_width=new DoubleTextField();
		magazine_width.setBounds(column, r, 100, 20);
		magazine_width.addKeyListener(this);
		right.add(magazine_width);
		
		r+=30;
		
		jlb=new JLabel("Magazine height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		magazine_height=new DoubleTextField();
		magazine_height.setBounds(column, r, 100, 20);
		magazine_height.addKeyListener(this);
		right.add(magazine_height);
		
		r+=30;
		
		jlb=new JLabel("Trigger length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trigger_length=new DoubleTextField();
		trigger_length.setBounds(column, r, 100, 20);
		trigger_length.addKeyListener(this);
		right.add(trigger_length);
		
		r+=30;
		
		jlb=new JLabel("Trigger width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trigger_width=new DoubleTextField();
		trigger_width.setBounds(column, r, 100, 20);
		trigger_width.addKeyListener(this);
		right.add(trigger_width);
		
		r+=30;
		
		jlb=new JLabel("Trigger height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		trigger_height=new DoubleTextField();
		trigger_height.setBounds(column, r, 100, 20);
		trigger_height.addKeyListener(this);
		right.add(trigger_height);
		
		r+=30;
		
		jlb=new JLabel("butt_length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		butt_length=new DoubleTextField();
		butt_length.setBounds(column, r, 100, 20);
		butt_length.addKeyListener(this);
		right.add(butt_length);
		
		r+=30;
		
		jlb=new JLabel("butt width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		butt_width=new DoubleTextField();
		butt_width.setBounds(column, r, 100, 20);
		butt_width.addKeyListener(this);
		right.add(butt_width);
		
		r+=30;
		
		jlb=new JLabel("butt_height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		butt_height=new DoubleTextField();
		butt_height.setBounds(column, r, 100, 20);
		butt_height.addKeyListener(this);
		right.add(butt_height);
		
		r+=30;
		
		jlb=new JLabel("butt end length");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		butt_end_length=new DoubleTextField();
		butt_end_length.setBounds(column, r, 100, 20);
		butt_end_length.addKeyListener(this);
		right.add(butt_end_length);
		
		r+=30;
		
		jlb=new JLabel("butt end width");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		butt_end_width=new DoubleTextField();
		butt_end_width.setBounds(column, r, 100, 20);
		butt_end_width.addKeyListener(this);
		right.add(butt_end_width);
		
		r+=30;
		
		jlb=new JLabel("butt end height");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		butt_end_height=new DoubleTextField();
		butt_end_height.setBounds(column, r, 100, 20);
		butt_end_height.addKeyListener(this);
		right.add(butt_end_height);
		
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
		
		initRightGunData();
	
	}
	

	public void initRightShotgunData() {
	
		barrel_length.setText(400);
		barrel_radius.setText(8);	
		barrel_meridians.setText(12);

		breech_length.setText(104);
		breech_width.setText(28);
		breech_height.setText(40);
		
		butt_length.setText(186);
		butt_width.setText(28);		
		butt_height.setText(40);		
		
		butt_end_length.setText(186);
		butt_end_width.setText(28);
		butt_end_height.setText(69);
		
		forearm_length.setText(161);
		forearm_width.setText(28);
		forearm_height.setText(28);
		
		trigger_length.setText(46);
		trigger_width.setText(28);
		trigger_height.setText(17);
		
		rear_overhang.setText(33);

		magazine_length.setText(0);
		magazine_width.setText(0);
		magazine_height.setText(0);
	}
	
	private void initRightDoubleBarrelShotgunData() {

		
		barrel_length.setText(284);
		barrel_radius.setText(7);	
		barrel_meridians.setText(12);

		breech_length.setText(46);
		breech_width.setText(28);
		breech_height.setText(41);
		
		butt_length.setText(177);
		butt_width.setText(28);		
		butt_height.setText(22);		
		
		butt_end_length.setText(177);
		butt_end_width.setText(28);
		butt_end_height.setText(69);
		
		forearm_length.setText(159);
		forearm_width.setText(28);
		forearm_height.setText(23);
		
		trigger_length.setText(46);
		trigger_width.setText(28);
		trigger_height.setText(15);
		
		rear_overhang.setText(34);
		
		magazine_length.setText(0);
		magazine_width.setText(0);
		magazine_height.setText(0);

		
	}
	
	public void initRightGunData() {
		
		barrel_length.setText(200);
		barrel_radius.setText(27);	
		barrel_meridians.setText(12);

		breech_length.setText(68);
		breech_width.setText(32);
		breech_height.setText(54);
		
		butt_length.setText(68);
		butt_width.setText(41);		
		butt_height.setText(125);
		
		butt_end_length.setText(81);
		butt_end_width.setText(41);
		butt_end_height.setText(125);

		rear_overhang.setText(49);
		
		trigger_length.setText(64);
		trigger_width.setText(32);
		trigger_height.setText(41);
		
		forearm_length.setText(0);
		forearm_width.setText(0);
		forearm_height.setText(0);
		
		magazine_length.setText(0);
		magazine_width.setText(0);
		magazine_height.setText(0);
	}
	
	private void initRightSubmachineGunData() {

		
		barrel_length.setText(141);
		barrel_radius.setText(6);	
		barrel_meridians.setText(12);

		breech_length.setText(204);
		breech_width.setText(21);
		breech_height.setText(44);
		
		butt_length.setText(126);
		butt_width.setText(27);		
		butt_height.setText(16);		
		
		butt_end_length.setText(126);
		butt_end_width.setText(33);
		butt_end_height.setText(63);
		
		forearm_length.setText(51);
		forearm_width.setText(18);
		forearm_height.setText(56);
		
		trigger_length.setText(0);
		trigger_width.setText(0);
		trigger_height.setText(0);
		
		rear_overhang.setText(13);
		
		magazine_length.setText(25);
		magazine_width.setText(14);
		magazine_height.setText(104);
		
	}
	
	private void initRightBaseballBatData() {
		
		barrel_length.setText(800);
		barrel_radius.setText(0);	
		barrel_meridians.setText(0);

		breech_length.setText(0);
		breech_width.setText(0);
		breech_height.setText(0);
		
		butt_length.setText(0);
		butt_width.setText(0);		
		butt_height.setText(0);		
		
		butt_end_length.setText(0);
		butt_end_width.setText(0);
		butt_end_height.setText(0);
		
		forearm_length.setText(0);
		forearm_width.setText(0);
		forearm_height.setText(0);
		
		trigger_length.setText(0);
		trigger_width.setText(0);
		trigger_height.setText(0);
		
		rear_overhang.setText(0);
		
		magazine_length.setText(0);
		magazine_width.setText(0);
		magazine_height.setText(0);
		
	}
	
	private void initRightAxData() {
		
		barrel_length.setText(800);
		barrel_radius.setText(0);	
		barrel_meridians.setText(0);

		breech_length.setText(0);
		breech_width.setText(0);
		breech_height.setText(0);
		
		butt_length.setText(0);
		butt_width.setText(0);		
		butt_height.setText(0);		
		
		butt_end_length.setText(0);
		butt_end_width.setText(0);
		butt_end_height.setText(0);
		
		forearm_length.setText(0);
		forearm_width.setText(0);
		forearm_height.setText(0);
		
		trigger_length.setText(0);
		trigger_width.setText(0);
		trigger_height.setText(0);
		
		rear_overhang.setText(0);
		
		magazine_length.setText(0);
		magazine_width.setText(0);
		magazine_height.setText(0);
		
	}

	private void initRightRevolverData() {
	
		
		barrel_length.setText(200);
		barrel_radius.setText(8);	
		barrel_meridians.setText(12);

		breech_length.setText(77);
		breech_width.setText(18);
		breech_height.setText(66);
		
		butt_length.setText(32);
		butt_width.setText(18);
		butt_height.setText(75);
		
		butt_end_length.setText(74);
		butt_end_width.setText(18);
		butt_end_height.setText(75);
		
		forearm_length.setText(190);
		forearm_width.setText(10);
		forearm_height.setText(9);
		
		magazine_length.setText(54);
		magazine_width.setText(42);
		magazine_height.setText(42);
		
		rear_overhang.setText(42);
	}
	
	private void setRightData(Weapon weapon) {
		
		for (int i = 0; i < weapon_type.getItemCount(); i++) {
			ValuePair vp= (ValuePair) weapon_type.getItemAt(i);
			if(vp.getId().equals(""+weapon.getWeapon_type()))
			{
				weapon_type.setSelectedIndex(i);
				break;
			}	
		}
	
		barrel_length.setText(weapon.getBarrel_lenght());
		barrel_radius.setText(weapon.getBarrel_radius());
		barrel_meridians.setText(weapon.getBarrel_meridians());
		
		breech_length.setText(weapon.getBreech_length());
		breech_width.setText(weapon.getBreech_width());
		breech_height.setText(weapon.getBreech_height());
		
		butt_length.setText(weapon.getButt_length());
		butt_width.setText(weapon.getButt_width());
		butt_height.setText(weapon.getButt_height());

	}

	
	public void preview() {
		
		if(weapon==null)
			return;
		
		Editor editor=new Editor();
		editor.meshes[0]=buildMesh();
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(editor);
		
	}
	

	public PolygonMesh buildMesh() {
		
		if(scaleValue!=null){
			
			double val=scaleValue.getvalue();
			
			if(val>0)
				scale=val;
			else
				scale=1.0;
		}
		return weapon.buildMesh(scale);
	}
	
	public void generate() {
		
			prepareUndo();
			
			 ValuePair vp= (ValuePair)weapon_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			    if(type<0)
			    	type=Weapon.WEAPON_TYPE_GUN;
			
			
			double barrelLength=barrel_length.getvalue();
			double barrelRadius=barrel_radius.getvalue();
			int barrelMeridians=barrel_meridians.getvalue();
			
			double breechLength=breech_length.getvalue();
			double breechWidth=breech_width.getvalue();
			double breechHeight=breech_height.getvalue();
			
			double buttLength=butt_length.getvalue();
			double buttWidth=butt_width.getvalue();			
			double buttHeight=butt_height.getvalue();
			
			double buttEndLength=butt_end_length.getvalue();
			double buttEndWidth=butt_end_width.getvalue();
			double buttEndHeight=butt_end_height.getvalue();
			
			double rearOverhang=rear_overhang.getvalue();
			
			double triggerLength=trigger_length.getvalue();
			double triggerWidth=trigger_width.getvalue();
			double triggerHeight=trigger_height.getvalue();
			
			double forearmLength=forearm_length.getvalue();
			double forearmWidth=forearm_width.getvalue();
			double forearmHeight=forearm_height.getvalue();
			
			double magazineLength=magazine_length.getvalue();
			double magazineWidth=magazine_width.getvalue();
			double magazineHeight=magazine_height.getvalue();

	
			if(weapon==null){
				
				weapon=new Weapon(
							type,
							barrelLength,barrelRadius,barrelMeridians,
							breechLength,breechWidth,breechHeight,
							buttLength,buttWidth,buttHeight,
							buttEndLength,buttEndWidth,buttEndHeight,
							rearOverhang,
							triggerLength,triggerWidth,triggerHeight,
							forearmLength,forearmWidth,forearmHeight,
							magazineLength,magazineWidth,magazineHeight
						);
				
			}else{
				
				Weapon expWeapon = new Weapon(
							type,
							barrelLength,barrelRadius,barrelMeridians,
							breechLength,breechWidth,breechHeight,
							buttLength,buttWidth,buttHeight,
							buttEndLength,buttEndWidth,buttEndHeight,
							rearOverhang,
							triggerLength,triggerWidth,triggerHeight,
							forearmLength,forearmWidth,forearmHeight,
							magazineLength,magazineWidth,magazineHeight
						);
				
				weapon=expWeapon;
			} 
			
			draw();
			setRightData(weapon);
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
					weapon=Weapon.buildWeapon(value);
					
					setRightData(weapon);
					
				}
	
			}
			br.close();
			

			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}




	public void saveMesh(File file) {
		
		
		if(weapon==null)
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
		
		
		if(weapon==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			pw.println(weapon.toString());
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void undo() {

		if(oldWeapon.size()>0)
			weapon=(Weapon) oldWeapon.pop();
		
		if(oldWeapon.size()==0)
			jmt_undo_last.setEnabled(false);
		
		draw();
	}
	
	public void prepareUndo() {
		
		if(weapon==null)
			return;
		
		jmt_undo_last.setEnabled(true);
		
		if(oldWeapon.size()>=max_stack_size)
			oldWeapon.removeElementAt(0);
		
		oldWeapon.push(weapon.clone());
		
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object obj = arg0.getSource();
		
		if(obj==weapon_type){
			
			center.setTeta(0);
			
			 ValuePair vp= (ValuePair)weapon_type.getSelectedItem();
				
			 int type=Integer.parseInt(vp.getId());
			   if(type<0)
			    	type=Weapon.WEAPON_TYPE_SHOTGUN;
			   
			   if(type==Weapon.WEAPON_TYPE_SHOTGUN)
				   initRightShotgunData();
			   else if(type==Weapon.WEAPON_TYPE_DOUBLE_BARREL_SHOTGUN)
				   initRightDoubleBarrelShotgunData();
			   else if(type==Weapon.WEAPON_TYPE_GUN)
				   initRightGunData();
			   else if(type==Weapon.WEAPON_TYPE_REVOLVER)
				   initRightRevolverData();
			   else if(type==Weapon.WEAPON_TYPE_BASEBALL_BAT)
				   initRightBaseballBatData();
			   else if(type==Weapon.WEAPON_TYPE_SUBMACHINEGUN)
				   initRightSubmachineGunData();
			   else if(type==Weapon.WEAPON_TYPE_AX)
				   initRightAxData();
			   else
				   initRightGunData(); 
			
		}

		
	}



	@Override
    public void saveBaseCubicTexture(File file) {
    	if(weapon.getWeapon_type()==Weapon.WEAPON_TYPE_BASEBALL_BAT
    			){
    		
    		PolygonMesh pm = buildMesh();		
    		weapon.getSpecificData().saveBaseCubicTexture(pm,file);
    	}
    	else
    		super.saveBaseCubicTexture(file);
    } 




}
