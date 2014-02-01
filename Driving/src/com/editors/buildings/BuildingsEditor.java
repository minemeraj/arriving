package com.editors.buildings;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.buildings.data.BuildingCell;

public class BuildingsEditor extends JFrame implements MenuListener, MouseListener, MouseWheelListener, ActionListener, KeyListener{
	
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
	private JMenuItem jmt_new;
	
	public boolean redrawAfterMenu=false;
	
	public BuildingCell centerCell=null;	
	
	JFileChooser fc = new JFileChooser();
	File currentDirectory=new File("lib");
	private DoubleTextField nw_x;
	private DoubleTextField nw_y;
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	private JButton deleteCell;
	

	
	
	public BuildingsEditor(){
		
		setTitle("Buildings editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center=new BuildingJPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		center.addMouseListener(this);
		
		buildMenuBar();
		
		buildRightPanel();
		
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
	
	private void buildRightPanel() {
		
		
		JPanel right=new JPanel(null);
		
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		
		int r=10;
		
		int column=100;
		
		JLabel jlb=new JLabel("NW X");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);

		nw_x=new DoubleTextField();
		nw_x.setBounds(column, r, 100, 20);
		nw_x.setEditable(false);
		nw_x.addKeyListener(this);
		right.add(nw_x);

		r+=30;
		
		jlb=new JLabel("NW Y");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);

		nw_y=new DoubleTextField();
		nw_y.setBounds(column, r, 100, 20);
		nw_y.setEditable(false);
		nw_y.addKeyListener(this);
		right.add(nw_y);
		
		r+=30;

		jlb=new JLabel("X side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		x_side=new DoubleTextField();
		x_side.setBounds(column, r, 100, 20);
		x_side.setEditable(false);
		x_side.addKeyListener(this);
		right.add(x_side);

		r+=30;
		
		jlb=new JLabel("Y side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		y_side=new DoubleTextField();
		y_side.setBounds(column, r, 100, 20);
		y_side.setEditable(false);
		y_side.addKeyListener(this);
		right.add(y_side);
		
		r+=30;
		
        deleteCell=new JButton("Delete cell");
        deleteCell.setBounds(10,r,100,20);
        deleteCell.addActionListener(this);
        deleteCell.addKeyListener(this);
        right.add(deleteCell);
		
		add(right);
		
	}
	

	private void setRightData(BuildingCell cell) {
		nw_x.setText(cell.getNw_x());
		nw_y.setText(cell.getNw_y());
		x_side.setText(cell.getX_side());
		y_side.setText(cell.getY_side());
		
	}

	

	private void cleanRightData() {
		
		nw_x.setText("");
		nw_y.setText("");
		x_side.setText("");
		y_side.setText("");
		
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
		
		jm_file.addSeparator();
		
		jmt_new = new JMenuItem("New");
		jmt_new.addActionListener(this);
		jm_file.add(jmt_new);
		
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
		else if(obj==jmt_new){
			
			centerCell=null;
			addCell(BuildingCell.CENTER);
		}else if (obj==deleteCell){
			
			deleteSelectedCell(centerCell);
			System.gc();
			draw();
			
		}
	}
	

	private void deleteSelectedCell(BuildingCell cell) {



		if(cell.getNorthCell()!=null ){

			if(cell.getNorthCell().isSelected())
				cell.setNorthCell(null);
			else
				deleteSelectedCell(cell.getNorthCell());
		}	

		if(cell.getSouthCell()!=null){


			if(cell.getSouthCell().isSelected())
				cell.setSouthCell(null);
			else
				deleteSelectedCell(cell.getSouthCell());
		}	

		if(cell.getWestCell()!=null){


			if(cell.getWestCell().isSelected())
				cell.setWestCell(null);
			else
				deleteSelectedCell(cell.getWestCell());
		}	

		if(cell.getEastCell()!=null){


			if(cell.getEastCell().isSelected())
				cell.setEastCell(null);
			else
				deleteSelectedCell(cell.getEastCell());
		}	




	}


	private void addCell(int position) {

		
		if(position!=BuildingCell.CENTER && centerCell==null){
			JOptionPane.showMessageDialog(this,"First add a center!","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		BuildingCell selCell=getSelectedCell();
		

		if(position!=BuildingCell.CENTER && selCell==null){

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
				setRightData(centerCell);
			}

		}else {
			
			selCell.addCell(position);
		}
		
		draw();

	}

	private BuildingCell getSelectedCell() {
		
		if(centerCell==null)
			return null;
		
		return findSelectedCell(centerCell);
		
	
	}

	private BuildingCell findSelectedCell(BuildingCell cell) {
		
		if(cell.isSelected())
			return cell;

		BuildingCell selCell=null;
		
		if(cell.getNorthCell()!=null)
			selCell=findSelectedCell(cell.getNorthCell());
		if(selCell!=null)
			return selCell;
		
		
		if(cell.getSouthCell()!=null)
			selCell=findSelectedCell(cell.getSouthCell());
		if(selCell!=null)
			return selCell;
		
		if(cell.getWestCell()!=null)
			selCell=findSelectedCell(cell.getWestCell());
		if(selCell!=null)
			return selCell;
		
		if(cell.getEastCell()!=null)
			selCell=findSelectedCell(cell.getEastCell());
		if(selCell!=null)
			return selCell;
		
		return null;
		
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

			Hashtable rects=new Hashtable();
			
			String str=null;
			
			while((str=br.readLine())!=null){
				
				int indx=str.indexOf("=");
				String tag=str.substring(0,indx);
				String value=str.substring(indx+1);
				
				rects.put(tag,buildCell(value));
				
				
			}
			br.close();
			
			
			String tag=BuildingCell.CENTER_TAG;
			centerCell=(BuildingCell) rects.get(tag);
			if(centerCell==null)
				return;
			
			jmt_add_center_cell.setEnabled(false);
			centerCell.setSelected(true);
			setRightData(centerCell);
			
			buildTree(rects,tag,centerCell);
			
		} catch (Exception e) { 
		
			e.printStackTrace();
		}
		
	}

	private void buildTree(Hashtable rects, String tag,BuildingCell cell) {
		
		if(rects.get(tag+BuildingCell.NORTH_TAG)!=null){
			
			BuildingCell bCell=(BuildingCell) rects.get(tag+BuildingCell.NORTH_TAG);
			cell.setNorthCell(bCell);
			buildTree(rects,tag+BuildingCell.NORTH_TAG,bCell);
			
		}
		
		if(rects.get(tag+BuildingCell.SOUTH_TAG)!=null){
			
			BuildingCell bCell=(BuildingCell) rects.get(tag+BuildingCell.SOUTH_TAG);
			cell.setSouthCell(bCell);
			buildTree(rects,tag+BuildingCell.SOUTH_TAG,bCell);
			
		}
		
		if(rects.get(tag+BuildingCell.EAST_TAG)!=null){
			
			BuildingCell bCell=(BuildingCell) rects.get(tag+BuildingCell.EAST_TAG);
			cell.setEastCell(bCell);
			buildTree(rects,tag+BuildingCell.EAST_TAG,bCell);
			
		}
		
		if(rects.get(tag+BuildingCell.WEST_TAG)!=null){
			
			BuildingCell bCell=(BuildingCell) rects.get(tag+BuildingCell.WEST_TAG);
			cell.setWestCell(bCell);
			buildTree(rects,tag+BuildingCell.WEST_TAG,bCell);
			
		}
		
	}

	private BuildingCell buildCell(String str) {
		String[] vals = str.split(",");
		
		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		
		BuildingCell cell=new BuildingCell(nw_x,nw_y,x_side,y_side);
		
		return cell;
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

			
			String tag=BuildingCell.CENTER_TAG;
			saveCell(centerCell,tag,pw);
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void saveCell(BuildingCell cell, String tag, PrintWriter pw) {
		
		pw.println(tag+"="+cell.toString());
		
		if(cell.getNorthCell()!=null)
			saveCell(cell.getNorthCell(),tag+BuildingCell.NORTH_TAG,pw);
		
		if(cell.getSouthCell()!=null)
			saveCell(cell.getSouthCell(),tag+BuildingCell.SOUTH_TAG,pw);
		
		if(cell.getWestCell()!=null)
			saveCell(cell.getWestCell(),tag+BuildingCell.WEST_TAG,pw);
		
		if(cell.getEastCell()!=null)
			saveCell(cell.getEastCell(),tag+BuildingCell.EAST_TAG,pw);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Point p=e.getPoint();
		
		
		if(centerCell!=null){
			
			cleanRightData();
			Point pt=new Point((int)center.invertX(p.x,p.y),(int)center.invertY(p.x,p.y));
			clickCell(centerCell,pt);
			
			draw();
		}
		
	}

	private void clickCell(BuildingCell cell, Point p) {

		
		
		if(cell.contains(p)){
			cell.setSelected(true);
			setRightData(cell);
		}	
		else
			cell.setSelected(false);

		
		if(cell.getNorthCell()!=null)
			clickCell(cell.getNorthCell(),p);
		
		if(cell.getSouthCell()!=null)
			clickCell(cell.getSouthCell(),p);
		
		if(cell.getWestCell()!=null)
			clickCell(cell.getWestCell(),p);
		
		if(cell.getEastCell()!=null)
			clickCell(cell.getEastCell(),p);
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
