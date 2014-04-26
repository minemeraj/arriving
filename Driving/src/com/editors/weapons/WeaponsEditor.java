package com.editors.weapons;

import java.awt.Component;
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
import com.editors.IntegerTextField;
import com.editors.ValuePair;
import com.editors.object.ObjectEditorPreviewPanel;
import com.editors.weapons.data.Weapon;


public class WeaponsEditor extends CustomEditor implements MenuListener, ActionListener, KeyListener, MouseWheelListener, ItemListener{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	WeaponsJPanel center=null;
	
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
	
	private JButton generate;
	
	JFileChooser fc = new JFileChooser();
	File currentDirectory=new File("lib");
	
	public Stack oldWeapon=null;
	int max_stack_size=10;
	
	Weapon weapon=null;
	private JComboBox weapon_type;
	private DoubleTextField rear_overhang;

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

	private void draw() {
		
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
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_GUN,"Gun"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_SHOTGUN,"Shotgun"));	
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_DOUBLE_BARREL_SHOTGUN,"Double barrel Shotgun"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_REVOLVER,"Revolver"));
		weapon_type.addItem(new ValuePair(""+Weapon.WEAPON_TYPE_CHAINGUN,"Chaingun"));
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
		
		add(right);
		
		initRightData();
		
	}

	public void initRightData() {
		
		initRightGunData();
	
	}
	

	public void initRightShotgunData() {
	
		barrel_length.setText(200);
		barrel_radius.setText(30);	
		barrel_meridians.setText(12);

		breech_length.setText(100);
		breech_width.setText(80);
		breech_height.setText(100);
		
		butt_length.setText(200);
		butt_width.setText(80);		
		butt_height.setText(50);		
		
		butt_end_length.setText(200);
		butt_end_width.setText(100);
		butt_end_height.setText(100);

	}
	
	private void initRightDoubleBarrelShotgunData() {

		
		barrel_length.setText(200);
		barrel_radius.setText(20);	
		barrel_meridians.setText(12);

		breech_length.setText(100);
		breech_width.setText(80);
		breech_height.setText(100);
		
		butt_length.setText(200);
		butt_width.setText(80);		
		butt_height.setText(50);		
		
		butt_end_length.setText(200);
		butt_end_width.setText(100);
		butt_end_height.setText(100);

	
		
	}
	
	public void initRightGunData() {
		
		barrel_length.setText(200);
		barrel_radius.setText(27);	
		barrel_meridians.setText(12);

		breech_length.setText(68);
		breech_width.setText(80);
		breech_height.setText(54);
		
		butt_length.setText(68);
		butt_width.setText(80);		
		butt_height.setText(125);
		
		butt_end_length.setText(81);
		butt_end_width.setText(80);
		butt_end_height.setText(125);

		rear_overhang.setText(49);
	}
	
	private void initRightChaingunData() {

		
		barrel_length.setText(200);
		barrel_radius.setText(40);	
		barrel_meridians.setText(12);

		breech_length.setText(100);
		breech_width.setText(80);
		breech_height.setText(100);
		
		butt_length.setText(200);
		butt_width.setText(80);		
		butt_height.setText(50);		
		
		butt_end_length.setText(200);
		butt_end_width.setText(100);
		butt_end_height.setText(100);
		
		
	}

	private void initRightRevolverData() {
	
		
		barrel_length.setText(200);
		barrel_radius.setText(40);	
		barrel_meridians.setText(12);

		breech_length.setText(100);
		breech_width.setText(80);
		breech_height.setText(100);
		
		butt_length.setText(50);
		butt_width.setText(80);
		butt_height.setText(200);
		
		butt_end_length.setText(80);
		butt_end_width.setText(100);
		butt_end_height.setText(200);
		
		
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
		
		if(weapon==null)
			return;
		
		Editor editor=new Editor();
		editor.meshes[0]=weapon.buildMesh();
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(editor);
		
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
			
	
			if(weapon==null){
				
				weapon=new Weapon(
							type,
							barrelLength,barrelRadius,barrelMeridians,
							breechLength,breechWidth,breechHeight,
							buttLength,buttWidth,buttHeight,
							buttEndLength,buttEndWidth,buttEndHeight,
							rearOverhang
						);
				
			}else{
				
				Weapon expWeapon = new Weapon(
							type,
							barrelLength,barrelRadius,barrelMeridians,
							breechLength,breechWidth,breechHeight,
							buttLength,buttWidth,buttHeight,
							buttEndLength,buttEndWidth,buttEndHeight,
							rearOverhang
						);
				
				weapon=expWeapon;
			} 
			
			draw();
			setRightData(weapon);
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
		
		
		if(weapon==null)
			return;
		
	
		PrintWriter pw; 
		try {
			Editor editor=new Editor();
			editor.meshes[0]=weapon.buildMesh();
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
		
		if(obj==weapon_type){
			
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
			   else if(type==Weapon.WEAPON_TYPE_CHAINGUN)
				   initRightChaingunData();
			   else
				   initRightGunData(); 
			
		}

		
	}




}
