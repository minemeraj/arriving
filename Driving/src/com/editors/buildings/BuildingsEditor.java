package com.editors.buildings;

import java.awt.Graphics;
import java.awt.Point;
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

import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.buildings.data.BuildingCell;
import com.editors.buildings.data.BuildingPlan;
import com.editors.object.ObjectEditorPreviewPanel;

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
	private JMenu jm_grid;
	private JMenuItem jmt_add_grid;
	private JMenuItem jmt_new_grid;
	private JMenuItem jmt_expand_grid;
	private JMenu jm_view;
	private JMenuItem jmt_preview;
	private JMenu jm_change;
	private JMenuItem jmt_undo_last;
	private JMenuItem jmt_save_mesh;
	
	public boolean redrawAfterMenu=false;
	
	public BuildingPlan grid =null;	
	
	JFileChooser fc = new JFileChooser();
	File currentDirectory=new File("lib");
	private DoubleTextField nw_x;
	private DoubleTextField nw_y;
	private DoubleTextField x_side;
	private DoubleTextField y_side;
	private DoubleTextField z_side;
	private JButton emptyCell; 
	private JButton fillCell;

	public Stack oldGrid=null;
	int max_stack_size=10;
	
	
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
							center.draw(grid);
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
		
		jlb=new JLabel("Z side");
		jlb.setBounds(5, r, 100, 20);
		right.add(jlb);
		z_side=new DoubleTextField();
		z_side.setBounds(column, r, 100, 20);
		z_side.setEditable(false);
		z_side.addKeyListener(this);
		right.add(z_side);
		
		r+=30;
		
        fillCell=new JButton("Fill cell");
        fillCell.setBounds(10,r,100,20);
        fillCell.addActionListener(this);
        fillCell.addKeyListener(this);
        right.add(fillCell);
		
		r+=30;
		
        emptyCell=new JButton("Empty cell");
        emptyCell.setBounds(10,r,100,20);
        emptyCell.addActionListener(this);
        emptyCell.addKeyListener(this);
        right.add(emptyCell);
        

		
		add(right);
		
	}
	

	private void setRightData(BuildingCell cell) {
		nw_x.setText(cell.getNw_x());
		nw_y.setText(cell.getNw_y());
		x_side.setText(cell.getX_side());
		y_side.setText(cell.getY_side());
		z_side.setText(cell.getZ_side());
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
		
		jmt_new_grid = new JMenuItem("New");
		jmt_new_grid.addActionListener(this);
		jm_file.add(jmt_new_grid);
		
		jm_file.addSeparator();
		
		jmt_save_mesh = new JMenuItem("Save mesh");
		jmt_save_mesh.addActionListener(this);
		jm_file.add(jmt_save_mesh);
		
		jm_grid=new JMenu("Grid");
		jm_grid.addMenuListener(this);
		jmb.add(jm_grid);
		
		jmt_add_grid = new JMenuItem("Add grid");
		jmt_add_grid.addActionListener(this);
		jm_grid.add(jmt_add_grid);
		
		jmt_expand_grid = new JMenuItem("Expand grid");
		jmt_expand_grid.addActionListener(this);
		jm_grid.add(jmt_expand_grid);
		
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

	private void initialize() {
		
		center.initialize();
		
		oldGrid=new Stack();
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
			
		}else if(obj==jmt_save_mesh){
			
			saveMesh();
			
		}
		else if(obj==jmt_add_grid){
			
			addGrid();
		}	
		else if(obj==jmt_expand_grid){
			
			expandGrid();
		}	
		else if(obj==jmt_new_grid){
			
			grid=null;
			addGrid();
		}
		else if(obj==jmt_preview){
			
			preview();
			
		}else if (obj==jmt_undo_last){
			
			undo();
			
		}
		else if (obj==emptyCell){
			
			fillSelected(false);
			draw();
			
		}
		else if (obj==fillCell){
			
			fillSelected(true);
			draw();
			
		}
	}
	




	private void fillSelected(boolean b) {
		
		if(grid==null)
			return;
		
		prepareUndo();
		
		/*for (int i = 0; i < grid.getXnum(); i++) {
			
			for (int j = 0; j < grid.getYnum(); j++) {
				BuildingCell bc=grid.cells[i][j];
			    if(bc.isSelected())
			    	bc.setFilled(b);
					
			}
			
		}*/
		
	}


	private void addGrid() {


		PlanPanel op=new PlanPanel(null);

		/*BuildingGrid newGrid = op.getNewGrid();
		if(newGrid!=null)
		{
			grid=newGrid;

		}*/


		draw();

	}

	private void expandGrid() {
		
		PlanPanel op=new PlanPanel(grid);

		/*BuildingGrid newGrid = op.getNewGrid();
		if(newGrid!=null)
		{
			grid=newGrid;

		}*/
		draw();
	}
	

	private void draw() {
		center.draw(grid);
		
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
			
			while((str=br.readLine())!=null){
				
				int indx=str.indexOf("G=");
				/*if(indx>=0){
					
					String value=str.substring(indx+2);
					grid=BuildingGrid.buildGrid(value);
					
				}else{
					
					String[] vals = str.split(",");
					int i=Integer.parseInt(vals[0]);
					int j=Integer.parseInt(vals[1]);
					boolean filled=Boolean.parseBoolean(vals[2]);
					
					grid.cells[i][j].setFilled(filled);
				}*/
				
				
				
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


	private void saveMesh(File file) {
		
		
		if(grid==null)
			return;
		
	
		PrintWriter pw;
		try {
			Editor editor=new Editor();
			editor.meshes[0]=grid.buildMesh();
			pw = new PrintWriter(new FileOutputStream(file));
			editor.forceReading=true;
			editor.saveLines(pw);
			pw.close();
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
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
		
		
		if(grid==null)
			return;
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);			

			
			
			pw.println("G="+grid.toString());
			
			/*for (int i = 0; i < grid.getXnum(); i++) {
				
				for (int j = 0; j < grid.getYnum(); j++) {
					BuildingCell bc=grid.cells[i][j];				
					pw.println(bc.toString());
						
				}
				
			}*/
			
			
			pw.close();
						
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		Point p=e.getPoint();
		
		
		if(grid!=null){
			
			cleanRightData();
			Point pt=new Point((int)center.invertX(p.x,p.y),(int)center.invertY(p.x,p.y));
			
			/*for (int i = 0; i < grid.getXnum(); i++) {
				
				for (int j = 0; j < grid.getYnum(); j++) {
					BuildingCell bc=grid.cells[i][j];
					if(bc.contains(pt)){
						bc.setSelected(true);
						setRightData(bc);
					}	
					else
						bc.setSelected(false);
						
				}
				
			}*/
			
			draw();
		}
		
	}

	public void preview() {
		
		if(grid==null)
			return;
		
		Editor editor=new Editor();
		editor.meshes[0]=grid.buildMesh();
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(editor);
		
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
	
	public void undo() {

		/*if(oldGrid.size()>0)
			grid=(BuildingGrid) oldGrid.pop();
		
		if(oldGrid.size()==0)
			jmt_undo_last.setEnabled(false);*/
		
		draw();
	}
	
	public void prepareUndo() {
		
		jmt_undo_last.setEnabled(true);
		
		if(oldGrid.size()>=max_stack_size)
			oldGrid.removeElementAt(0);
		
		oldGrid.push(grid.clone());
		
		
	}


}
