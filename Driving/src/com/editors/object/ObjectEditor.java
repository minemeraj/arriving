package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.RepaintManager;
import javax.swing.TransferHandler;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.editors.Editor;
import com.main.HelpPanel;



/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ObjectEditor extends Editor implements ActionListener{


	public ObjectEditor3DPanel center3D;
	public ObjectEditorTopBottomPanel centerTop;
	public ObjectEditorFrontBackPanel centerFront;
	public ObjectEditorLeftRightPanel centerLeft;
	
	public static int HEIGHT=700;
	public static int WIDTH=800;

	public int RIGHT_BORDER=330;
	public int BOTTOM_BORDER=100;
	public int NX=4;
	public int NY=0;

	public int MOVX=0;
	public int MOVY=0;
	
	public static int y0=250;
	public static int x0=250;

	public int dx=2;
	public int dy=2;

	public double deltay=0.5;
	public double deltax=0.5;

	


	public JMenuBar jmb;
	public JMenu jm_load;
	public JMenuItem jmt11;

	public AbstractButton jmt_load_mesh;
	public JMenuItem jmt_save_mesh;
	public JMenuItem jmt22;
	public JMenuItem jmt_save_base_texture;
	public JMenu jm_save;

	public JMenu jm_change;
	public JList lineList;
	public JButton selectAll;

	public JMenuItem jmt_undo_last;
	public JMenuItem jmt_transform;

	public JMenu jm_edit;
	public JMenuItem jmt_insert_template;
	public JCheckBoxMenuItem jmt_show_normals;
	public JMenu jm_view;
	public JMenuItem jmt_3Dview;
	public JMenuItem jmt_top_view;
	public JMenuItem jmt_left_view;
	public JMenuItem jmt_front_view;



	public static Color BACKGROUND_COLOR=new Color(0,0,0);
	
	
	int VIEW_TYPE_3D=0;
	int VIEW_TYPE_TOP=1;
	int VIEW_TYPE_LEFT=2;
	int VIEW_TYPE_FRONT=3;
	int VIEW_TYPE=VIEW_TYPE_3D;
	public JMenuItem hmt_preview;
	public JMenuItem jmt_rescale_selected;
	public JMenuItem jmt_copy_selection;
	public JCheckBoxMenuItem jmt_show_shading;
	private JMenu jmt_other;
	private JMenuItem jmt_help;
	private JMenuItem jmt_load_texture;
	private JMenuItem jmt_discharge_texture; 
	
	public Texture currentTexture=null;
	public JCheckBoxMenuItem jmt_show_texture;
	

	public static void main(String[] args) {

		ObjectEditor re=new ObjectEditor();
		
	}

	private void initialize() {
		
		center3D.initialize();
		centerTop.initialize();
	}

	public ObjectEditor(){

		setTitle("Object editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		
		center3D=new ObjectEditor3DPanel(this);
		center3D.setBackground(BACKGROUND_COLOR);
		center3D.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		center3D.setTransferHandler(new FileTransferhandler());
		
		centerTop=new ObjectEditorTopBottomPanel(this);
		centerTop.setBackground(BACKGROUND_COLOR);
		centerTop.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		centerTop.setTransferHandler(new FileTransferhandler());
		
		centerLeft=new ObjectEditorLeftRightPanel(this);
		centerLeft.setBackground(BACKGROUND_COLOR);
		centerLeft.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		centerLeft.setTransferHandler(new FileTransferhandler());
		
		centerFront=new ObjectEditorFrontBackPanel(this);
		centerFront.setBackground(BACKGROUND_COLOR);
		centerFront.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		centerFront.setTransferHandler(new FileTransferhandler());
		

		add(center3D);

		
		buildMenuBar();

		RepaintManager.setCurrentManager( 
				new RepaintManager(){

					public void paintDirtyRegions() {


						super.paintDirtyRegions();
						if(redrawAfterMenu ) {
							getCenter().displayAll();
							redrawAfterMenu=false;						    
						}
					}

				}				
		);
		
		currentDirectory=new File("lib");
		forceReading=true;
		setVisible(true);
		initialize();

	}
	
	public ObjectEditorPanel getCenter(){
		
		if(VIEW_TYPE==VIEW_TYPE_3D)
			return center3D;
		else if(VIEW_TYPE==VIEW_TYPE_TOP)
		    return centerTop;
		else if(VIEW_TYPE==VIEW_TYPE_LEFT)
		    return centerLeft;
		else 
		    return centerFront;
		
	}

	public void paint(Graphics arg0) {
		super.paint(arg0);
		getCenter().displayAll();
		
		
	}

	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}


	private void buildMenuBar() {
		jmb=new JMenuBar();
		jm_load=new JMenu("Load");
		jm_load.addMenuListener(this);
		jmb.add(jm_load);

		/*jmt11 = new JMenuItem("Load road");
		jmt11.addActionListener(this);
		jm.add(jmt11);*/

		jmt_load_mesh = new JMenuItem("Load Mesh");
		jmt_load_mesh.addActionListener(this);
		jm_load.add(jmt_load_mesh);
		
		jm_load.addSeparator();
		
		jmt_load_texture=new JMenuItem("Load texture");
		jmt_load_texture.addActionListener(this);		
		jm_load.add(jmt_load_texture);
		
		jmt_discharge_texture=new JMenuItem("Discharge texture");
		jmt_discharge_texture.addActionListener(this);		
		jm_load.add(jmt_discharge_texture);
		
		

		jm_save=new JMenu("Save");
		jm_save.addMenuListener(this);

		jmt_save_mesh = new JMenuItem("Save mesh");
		jmt_save_mesh.addActionListener(this);
		jm_save.add(jmt_save_mesh); 

		jmt_save_base_texture = new JMenuItem("Save base texture");
		jmt_save_base_texture.addActionListener(this);
		jm_save.add(jmt_save_base_texture);
		
		jmb.add(jm_save);

		jm_change=new JMenu("Change");
		jm_change.addMenuListener(this);
		jmt_undo_last = new JMenuItem("Undo last");
		jmt_undo_last.setEnabled(false);
		jmt_undo_last.addActionListener(this);
		jm_change.add(jmt_undo_last);
		jmt_transform = new JMenuItem("Transform");
		jmt_transform.addActionListener(this);
		jm_change.add(jmt_transform);
		jmt_rescale_selected = new JMenuItem("Rescale Selected");
		jmt_rescale_selected.addActionListener(this);
		jm_change.add(jmt_rescale_selected);
	
	
		jmb.add(jm_change);
		
		jm_edit=new JMenu("Edit");
		jm_edit.addMenuListener(this);
		jmt_insert_template = new JMenuItem("Insert template");	
		jm_edit.add(jmt_insert_template);	
		jmt_insert_template.addActionListener(this);
		
		
		jmt_copy_selection = new JMenuItem("Copy selection");	
		jm_edit.add(jmt_copy_selection);	
		jmt_copy_selection.addActionListener(this);
		
		jm_edit.addSeparator();
		
		jmt_show_shading = new JCheckBoxMenuItem("Show shading");
		jmt_show_shading.setState(false);
		jmt_show_shading.addActionListener(this);
		jm_edit.add(jmt_show_shading);
		
		jmt_show_normals = new JCheckBoxMenuItem("Show normals");
		jmt_show_normals.setState(false);
		jmt_show_normals.addActionListener(this);
		jm_edit.add(jmt_show_normals);
		
		jmt_show_texture = new JCheckBoxMenuItem("Show texture");
		jmt_show_texture.setState(false);
		jmt_show_texture.addActionListener(this);
		jm_edit.add(jmt_show_texture);
		 
		jmb.add(jm_edit);
		
		jm_view=new JMenu("View");
		jm_view.addMenuListener(this);
		jmt_3Dview = new JMenuItem("3D view");	
		jm_view.add(jmt_3Dview);	
		jmt_3Dview.addActionListener(this);
		
		jmt_top_view = new JMenuItem("Top view");
		jmt_top_view.addActionListener(this);
		jm_view.add(jmt_top_view); 
		
		jmt_left_view = new JMenuItem("Left view");
		jmt_left_view.addActionListener(this);
		jm_view.add(jmt_left_view);
		
		jmt_front_view = new JMenuItem("Front view");
		jmt_front_view.addActionListener(this);
		jm_view.add(jmt_front_view);
				
		jm_view.addSeparator();
		
		hmt_preview = new JMenuItem("Preview");
		hmt_preview.addActionListener(this);
		jm_view.add(hmt_preview);
		
		jmb.add(jm_view);
		
		jmt_other=new JMenu("Other");
		jmt_other.addMenuListener(this);	
		
		jmb.add(jmt_other);

		
		jmt_help=new JMenuItem("Help");
		jmt_help.addActionListener(this);		
		jmt_other.add(jmt_help);
		
		
		
		setJMenuBar(jmb);
	}


	
	private void help() {
	
		
		HelpPanel hp=new HelpPanel(300,200,this.getX()+100,this.getY(),HelpPanel.OBJECT_EDITOR_HELP_TEXT,this);
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==jmt_save_mesh){
			saveLines();
		}
		else if(o==jmt_save_base_texture){
			saveBaseCubicTexture();
		}
		else if(o==jmt_load_mesh){
			
			jmt_show_texture.setSelected(false);
			currentTexture=null;
			loadPointsFromFile();
		}
		else if(o==jmt_undo_last){
			undo();
		}
		else if(o==jmt_transform){
			transform();
		}
		else if(o==jmt_rescale_selected){
		    rescaleSelcted();
		}
		else if(o==jmt_insert_template){
			getTemplate();
		}
		else if(o==jmt_copy_selection){
			copySelection();
		}
		else if(o==jmt_3Dview){
			set3DView();
		}
		else if(o==jmt_top_view){
			
			setTopView();
		}
		else if(o==jmt_left_view){
			
			setLeftView();
		}
		else if(o==jmt_front_view){
			
			setFrontView();
		}
		else if(o==hmt_preview){
			
			preview();
		}
		else if(o==jmt_help){
			
			help();
			
		}
		else if(o==jmt_load_texture){
			
			loadTexture();
			
		}
		else if(o==jmt_discharge_texture){
			
			dischargeTexture();
			
		}
	}







	public void preview() {
		
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(this);
		
	}
	

	private void dischargeTexture() {
		
		jmt_show_texture.setSelected(false);
		currentTexture=null;
		
	}

	private void loadTexture() {
		
		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load texture");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			loadTexture(file);


		}
		
	}

	private void loadTexture(File file) {
		
		try {
			
			currentTexture=new Texture(ImageIO.read(file));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void set3DView() {
		
		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_3D;
		add(center3D);		
		center3D.grabFocus();
		repaint();
	}
	
	public void setTopView() {

		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_TOP;
		add(centerTop);		
		centerTop.grabFocus();
		repaint();
	}
	
	public void setLeftView() {
		
		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_LEFT;
		add(centerLeft);		
		centerLeft.grabFocus();
		repaint();
	}
	
	public void setFrontView() {
		
		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_FRONT;
		add(centerFront);		
		centerFront.grabFocus();
		repaint();
	}

	public void getTemplate() {
		
		ObjectEditorTemplatePanel oetp=new ObjectEditorTemplatePanel();
		
		PolygonMesh pm=oetp.getTemplate();
		
				
		if(pm!=null){
			
			PolygonMesh mesh=meshes[ACTIVE_PANEL];
			
			Vector vPoints=new Vector();
			for (int i = 0; i < pm.points.length; i++) {
				vPoints.add(pm.points[i]);
			}
			mesh.setPoints(vPoints);
			mesh.polygonData=pm.polygonData;
			getCenter().displayAll();
		}
		
	}
	
	
	private void copySelection() {
		
		prepareUndo();
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		ObjectEditorCopyPanel oetp=new ObjectEditorCopyPanel(mesh.points,mesh.polygonData);
		
		PolygonMesh pm=oetp.getCopy();
		
		if(pm!=null){
			Vector vPoints=new Vector();
			for (int i = 0; i < pm.points.length; i++) {
				vPoints.add(pm.points[i]);
			}
			mesh.setPoints(vPoints);
			mesh.polygonData=pm.polygonData;
			getCenter().displayAll();
		}
		
	}

	private void transform() {
		
		
		ObjectEditorTransformPanel oetp=new ObjectEditorTransformPanel(this);
		
	}

	private void rescaleSelcted() {
		
		
		ObjectEditorRescalePanel oerp=new ObjectEditorRescalePanel(this);
		
	}


	/*public void savePolyFormat() {
		
		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save other format");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			savePolyFormat(file);

		} 
		
	}
	
	private void savePolyFormat(File file) {

		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		PrintWriter pr;
		try {
			pr = new PrintWriter(new FileOutputStream(file));
		
			for(int i=0;i<mesh.polygonData.size();i++){

				LineData ld=(LineData) mesh.polygonData.elementAt(i);

				pr.println(decomposePolyFormat(ld));
			
			}	

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
*/





	private void saveBaseCubicTexture() {
		
		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save basic texture");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			saveBaseCubicTexture(file);

		}
		
	}


	private void saveBaseCubicTexture(File file) {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		Color backgroundColor=Color.green;
		Color back_color=Color.BLUE;
		Color top_color=Color.RED;
		Color bottom_color=Color.MAGENTA;
		Color left_color=Color.YELLOW;
		Color right_color=Color.ORANGE;
		Color front_color=Color.CYAN;
		
		int IMG_WIDTH=0;
		int IMG_HEIGHT=0;

		int deltaX=0;
		int deltaY=0;
		int deltaX2=0;

		double minx=0;
		double miny=0;
		double minz=0;
		
		double maxx=0;
		double maxy=0;
		double maxz=0;
		
		
	      //find maxs
		for(int j=0;j<mesh.points.length;j++){
			
			Point3D point=mesh.points[j];
			
			if(j==0){
				
				minx=point.x;
				miny=point.y;
				minz=point.z;
				
				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{
				
				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);
				
				
				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}
			
	
		}
		
		deltaX2=(int)(maxx-minx)+1;
		deltaX=(int)(maxz-minz)+1; 
		deltaY=(int)(maxy-miny)+1;
		
		deltaX2=deltaX2+deltaX;
		
		IMG_HEIGHT=(int) deltaY+deltaX+deltaX;
		IMG_WIDTH=(int) (deltaX2+deltaX2);
		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {

	


				int DX=0;

				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(DX+0,0,IMG_WIDTH,IMG_HEIGHT);
				
				/*bufGraphics.setColor(front_color);
				bufGraphics.fillRect(DX+deltaX,0,deltaX2-deltaX,deltaX);
				bufGraphics.setColor(top_color);
				bufGraphics.fillRect(DX+deltaX,deltaX,deltaX2-deltaX,deltaY);
				bufGraphics.setColor(left_color);
				bufGraphics.fillRect(DX+0,deltaX,deltaX,deltaY);
				bufGraphics.setColor(right_color);
				bufGraphics.fillRect(DX+deltaX2,deltaX,deltaX,deltaY);
				bufGraphics.setColor(back_color);
				bufGraphics.fillRect(DX+deltaX,deltaY+deltaX,deltaX2-deltaX,deltaX);*/


				//draw lines for reference

				bufGraphics.setColor(new Color(0,0,0));
				bufGraphics.setStroke(new BasicStroke(0.1f));
				for(int j=0;j<mesh.polygonData.size();j++){ 

					LineData ld=(LineData) mesh.polygonData.elementAt(j);

					for (int k = 0; k < ld.size(); k++) {


						Point3D point0=  mesh.points[ld.getIndex(k)];
						Point3D point1=  mesh.points[ld.getIndex((k+1)%ld.size())];
						//top
						bufGraphics.setColor(top_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.x-minx+deltaX),(int)(-point1.y+maxy+deltaX));
						//front
						bufGraphics.setColor(front_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(point0.z-minz),DX+(int)(point1.x-minx+deltaX),(int)(point1.z-minz));
						//left
						bufGraphics.setColor(left_color);
						bufGraphics.drawLine(DX+(int)(point0.z-minz),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.z-minz),(int)(-point1.y+maxy+deltaX));
						//right
						bufGraphics.setColor(right_color);
						bufGraphics.drawLine(DX+(int)(-point0.z+maxz+deltaX2),(int)(-point0.y+maxy+deltaX),DX+(int)(-point1.z+maxz+deltaX2),(int)(-point1.y+maxy+deltaX));
						//back
						bufGraphics.setColor(back_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(-point0.z+maxz+deltaY+deltaX),DX+(int)(point1.x-minx+deltaX),(int)(-point1.z+maxz+deltaY+deltaX));
						//bottom
						bufGraphics.setColor(bottom_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX+deltaX2),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.x-minx+deltaX+deltaX2),(int)(-point1.y+maxy+deltaX));
				
					}

				}	
			
			ImageIO.write(buf,"gif",file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}


	

	public void undo() {
		super.undo();
		if(oldMeshes[ACTIVE_PANEL].size()==0)
			jmt_undo_last.setEnabled(false);
		
		firePropertyChange("ObjectEditorUndo",false,true);
	}
	
	public void prepareUndo() {
		jmt_undo_last.setEnabled(true);
		super.prepareUndo();
		
		
	}

	public void decomposeObjVertices(PrintWriter pr,PolygonMesh mesh) {
		
		int DX=0;

		
		int deltaX=0;
		int deltaY=0;
		int deltaX2=0;

		double minx=0;
		double miny=0;
		double minz=0;
		
		double maxx=0;
		double maxy=0;
		double maxz=0;
		
		
	      //find maxs
		for(int j=0;j<mesh.points.length;j++){
			
			Point3D point=mesh.points[j];
			
			if(j==0){
				
				minx=point.x;
				miny=point.y;
				minz=point.z;
				
				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{
				
				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);
				
				
				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}
			
	
		}
		
		deltaX2=(int)(maxx-minx)+1;
		deltaX=(int)(maxz-minz)+1; 
		deltaY=(int)(maxy-miny)+1;

		for(int i=0;i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			/*public static final int CAR_BACK=0;
			public static final int CAR_TOP=1;
			public static final int CAR_LEFT=2;
			public static final int CAR_RIGHT=3;
			public static final int CAR_FRONT=4;
			public static final int CAR_BOTTOM=5;*/
		
			//back
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(p.z-minz));					
			//top
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(p.y-miny+deltaX));
			//left
			pr.print("\nvt=");
			pr.print(DX+(int)(p.z-minz)+" "+(int)(p.y-miny+deltaX));			
			//right
			pr.print("\nvt=");
			pr.print(DX+(int)(-p.z+maxz+deltaX2+deltaX)+" "+(int)(p.y-miny+deltaX));		
			//front
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(-p.z+maxz+deltaY+deltaX));	
			//bottom
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+2*deltaX+deltaX2)+" "+(int)(p.y-miny+deltaX));

		}	
		
		
		
	}

	
	class FileTransferhandler extends TransferHandler{
		
		
		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
			
			for(int i=0;i<transferFlavors.length;i++){
				
				 if (!transferFlavors[i].equals(DataFlavor.javaFileListFlavor))
					 return false;
			}
		    return true;
		}
	
		public boolean importData(JComponent comp, Transferable t) {
		
			try {
				List list=(List) t.getTransferData(DataFlavor.javaFileListFlavor);
				Iterator itera = list.iterator();
				while(itera.hasNext()){
					
					Object o=itera.next();
					if(!(o instanceof File))
						continue;
					File file=(File) o;
					currentDirectory=file.getParentFile();
					currentFile=file;
					
					loadPointsFromFile(file);
					getCenter().displayAll();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}

	public Polygon3D getBodyPolygon(LineData ld) {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		Polygon3D pol=new Polygon3D(ld.size());
		
		for(int i=0;i<ld.size();i++){
			int index=ld.getIndex(i);
			Point3D p=mesh.points[index];		
			pol.xpoints[i]=(int) p.x;
			pol.ypoints[i]=(int) p.y;
			pol.zpoints[i]=(int) p.z;
		} 
		
		return pol;
		
	}
	
	public static double[][] getRotationMatrix(Point3D versor,double teta){

		return getRotationMatrix(versor.x,versor.y,versor.z,teta);
	}

	/**
	 * 
	 * 
	 *  ROTATION MATRIX OF TETA AROUND (X,Y,Z) AXIS
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param teta
	 * @return
	 */



	public static double[][] getRotationMatrix(double x,double y,double z,double teta){

		double[][] matrix=new double[3][3];
		matrix[0][0]=Math.cos(teta)+x*x*(1.0-Math.cos(teta));
		matrix[0][1]=y*x*(1.0-Math.cos(teta))-z*Math.sin(teta);
		matrix[0][2]=z*x*(1.0-Math.cos(teta))+y*Math.sin(teta);
		matrix[1][0]=y*x*(1.0-Math.cos(teta))+z*Math.sin(teta);
		matrix[1][1]=Math.cos(teta)+y*y*(1.0-Math.cos(teta));
		matrix[1][2]=z*y*(1.0-Math.cos(teta))-x*Math.sin(teta);
		matrix[2][0]=z*x*(1.0-Math.cos(teta))-y*Math.sin(teta);
		matrix[2][1]=z*y*(1.0-Math.cos(teta))+x*Math.sin(teta);
		matrix[2][2]=Math.cos(teta)+z*z*(1.0-Math.cos(teta));
		return matrix;
	} 
	
	/**
	 * ROTATE AROUND POINT (X0,Y0,Z0) WITH MATRIX MATRIX
	 * 
	 * 
	 * @param ds
	 * @param matrix
	 * @param x0
	 * @param y0
	 * @param z0
	 */

	public static void rotate(Point3D p,double[][] matrix, double x0,double y0, double z0) {

		double xx=(p.x-x0);
		double yy=(p.y-y0);
		double zz=(p.z-z0);

		p.x=matrix[0][0]*xx+matrix[0][1]*yy+matrix[0][2]*zz+x0;
		p.y=matrix[1][0]*xx+matrix[1][1]*yy+matrix[1][2]*zz+y0;
		p.z=matrix[2][0]*xx+matrix[2][1]*yy+matrix[2][2]*zz+z0;
		


	}



	public static void rotate(Point3D[] ds,double[][] matrix, double x0,double y0, double z0) {

		for(int i=0;i<ds.length;i++){

			rotate( ds[i],matrix,x0,y0,z0);
		}

	}

	public static void rotate(Vector vector,double[][] matrix, double x0,double y0, double z0) {

		for(int i=0;i<vector.size();i++){

			rotate( (Point3D) vector.elementAt(i),matrix,x0,y0,z0);
		}

	}
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    	
    	center3D.addPropertyChangeListener(listener);
    	centerTop.addPropertyChangeListener(listener);
    	centerLeft.addPropertyChangeListener(listener);
    	centerFront.addPropertyChangeListener(listener);
    }

    @Override
    public void menuSelected(MenuEvent arg0) {
    	
    	super.menuSelected(arg0);
    	
    	Object o = arg0.getSource();
    	

    }

    public Polygon3D buildPolygon(LineData ld,Point3D[] points, boolean isReal) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point3D p= points[num];


			if(isReal){

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				czr[i]=(int)(p.z);
				
			}
			else {
				
				//cxr[i]=convertX(p.x);
				//cyr[i]=convertY(p.y);
				
			}
			


		}


		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

        return p3dr;

	}

}
