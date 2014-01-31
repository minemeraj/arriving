package com.editors.buildings;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.editors.Editor;
import com.editors.buildings.data.BuildingCell;

public class BuildingsEditor extends JFrame implements MenuListener,MouseWheelListener, ActionListener, KeyListener{
	
	public static int HEIGHT=700;
	public static int WIDTH=800;
	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;

	BuildingJPanel center=null;
	private JMenuBar jmb;
	private JMenu jm_file;
	private JMenuItem jmt_load_file;
	private JMenuItem jmt_save_file;
	private JMenu jm_cell;
	private JMenuItem jmt_add_center_cell;
	private JMenuItem jmt_add_north_cell;
	private JMenuItem jmt_add_south_cell;
	private JMenuItem jmt_add_west_cell;
	private JMenuItem jmt_add_east_cell;
	
	public boolean redrawAfterMenu=false;
	
	public BuildingCell centerCell=null;	
	
	JFileChooser fc = new JFileChooser();
	File currentDirectory=new File("lib");

	
	
	public BuildingsEditor(){
		
		setTitle("Buildings editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new BuildingJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		buildMenuBar();
		
		addKeyListener(this);
		addMouseWheelListener(this);
		
		setVisible(true);
		
		RepaintManager.setCurrentManager( 
				new RepaintManager(){

					public void paintDirtyRegions() {


						super.paintDirtyRegions();
						if(redrawAfterMenu ) {
							center.draw(centerCell);
							redrawAfterMenu=false;						    
						}
					}

				}				
		);
		
		initialize();
	}
	
	private void buildMenuBar() {
	
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
		
		jm_cell=new JMenu("Cell");
		jm_cell.addMenuListener(this);
		jmb.add(jm_cell);
		
		jmt_add_center_cell = new JMenuItem("Add center cell");
		jmt_add_center_cell.addActionListener(this);
		jm_cell.add(jmt_add_center_cell);
		
		jm_cell.addSeparator();
		
		jmt_add_north_cell = new JMenuItem("Add north cell");
		jmt_add_north_cell.addActionListener(this);
		jm_cell.add(jmt_add_north_cell);
		
		jmt_add_south_cell = new JMenuItem("Add south cell");
		jmt_add_south_cell.addActionListener(this);
		jm_cell.add(jmt_add_south_cell);
		
		jmt_add_west_cell = new JMenuItem("Add west cell");
		jmt_add_west_cell.addActionListener(this);
		jm_cell.add(jmt_add_west_cell);
		
		jmt_add_east_cell = new JMenuItem("Add east cell");
		jmt_add_east_cell.addActionListener(this);
		jm_cell.add(jmt_add_east_cell);
		
		setJMenuBar(jmb);
		
	}

	private void initialize() {
		
		center.initialize();
	}

	public static void main(String[] args) {
		
		BuildingsEditor be=new BuildingsEditor();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		Object obj = arg0.getSource();
		
		if(obj==jmt_load_file){
			
			loadData();
			
			
			
		}else if(obj==jmt_save_file){
			
			saveData();
			
		}else if(obj==jmt_add_center_cell){
			
			addCell(BuildingCell.CENTER);
		}
		else if(obj==jmt_add_north_cell){
			
			addCell(BuildingCell.NORTH);
		}
		else if(obj==jmt_add_south_cell){
			
			addCell(BuildingCell.SOUTH);
		}
		else if(obj==jmt_add_east_cell){
			
			addCell(BuildingCell.EAST);
		}
		else if(obj==jmt_add_west_cell){
			
			addCell(BuildingCell.WEST);
		}
	}
	


	private void addCell(int position) {

		
		if(position!=BuildingCell.CENTER && centerCell==null){
			JOptionPane.showMessageDialog(this,"First add a center!","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		

		if(position!=BuildingCell.CENTER && !centerCell.isSelected()){

			JOptionPane.showMessageDialog(this,"Select a cell to add!","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}


		if(position==BuildingCell.CENTER){
			
			CellPanel op=new CellPanel();
			
			BuildingCell newCell = op.getNewCell();
			if(newCell!=null )
			{
				centerCell=newCell;
				jmt_add_center_cell.setEnabled(false);
				centerCell.setSelected(true);
			}

		}else {
			
			centerCell.addCell(position);
		}
		
		draw();

	}

	private void draw() {
		center.draw(centerCell);
		
	}

	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
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
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		
		if(arg0.getUnitsToScroll()<0)
			center.translate(0,-1);
		else
			center.translate(0,1);
		
		draw();
		
	}
	
	private void loadData() {

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

	private void loadData(File file) {
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			String str=null;
			
			if((str=br.readLine())!=null){
				
				buildCenterCell(str);
			}
			br.close();
			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}

	private void buildCenterCell(String str) {
		
		String[] vals = str.split(",");
		
		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		
		centerCell=new BuildingCell(nw_x,nw_y,x_side,y_side);
		jmt_add_center_cell.setEnabled(false);
		centerCell.setSelected(true);
		

	}

	private void saveData() {
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

	private void saveData(File file) {
		
		
		if(centerCell==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			pw.println(centerCell.toString());
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
