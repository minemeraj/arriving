package com.editors.iperview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;

import com.LineData;
import com.LineDataVertex;
import com.Point3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.EditorPanel;
import com.editors.object.ObjectEditorPreviewPanel;
import com.editors.object.ObjectEditorTemplatePanel;
import com.main.HelpPanel;


public class IperviewEditor extends Editor implements EditorPanel,KeyListener, ActionListener{
	
	int CENTER_WIDTH=500;
	int CENTER_HEIGHT=550;
	int BOTTOM_BORDER=300;
	int RIGHT_BORDER=350;
	int LEFT_BORDER=350;
	
	private JPanel bottom;
	private JButton exit=null;
	

	IperviewPanel leftPanel=null;
	IperviewPanel centerPanel=null;
	IperviewPanel rightPanel=null;
	
	private JLabel screenPoint=null;

	
	int MAX_STACK_SIZE=10;
	
	private JMenuBar jmb;

	private int zdepth=0;
	private JMenuItem jmtUndo;
	private JButton deleteSelection;
	DoubleTextField coordinatesx;
	DoubleTextField coordinatesy;
	public DoubleTextField coordinatesz;
	public JTextField extraData;	
	JCheckBox checkMultipleSelection;
	private JButton addPoint;
	private JButton rescale;
	private com.editors.DoubleTextField rescaleValue;
	//private JButton joinPoints;
	private AbstractButton buildPolygon;
	private JButton changePoint;
	private JButton deselectAll;
	private JButton selectAll;
	private DoubleTextField objMove;
	private JButton movePointLeft;
	private JButton movePointRight;
	private JButton movePointTop;
	private JButton movePointBottom;
	private JButton movePointDown;
	private JButton movePointUp;
	private JButton startBuildPolygon;
	private JButton mergeSelectedPoints;

	
	
	DecimalFormat dfc=null;
	
	private JList lineList;
	private JList pointList=null;
	private JMenu jmLines;
	private JMenuItem jmt_load_mesh;
	private JMenuItem jmt_save_mesh;
	private JMenu jmView;
	private JMenuItem jmtPreview;
	private JCheckBoxMenuItem jmt42;
	private JMenu jmChange;
	private JMenuItem jmtGetTemplate;


	private BufferedImage backgroundImage=null;
	private JMenuItem jmt54;
	
	public JCheckBox checkCoordinatesx;
	public JCheckBox checkCoordinatesz;
	public JCheckBox checkCoordinatesy;
	public JCheckBox checkExtraData; 
	
	int ACTIVE_KEY=-1;
	boolean isControlMode=false;
	private JMenuItem jmt_append_lines;
	Point3D selectedPoint=null;

	
	public static void main(String args[]){

		
		IperviewEditor editor=new IperviewEditor();
		editor.setVisible(true);
			

	
	}

	public IperviewEditor(){

		
		setTitle("Iperview 1.0.0");
		
		meshes[ACTIVE_PANEL]=new PolygonMesh();
	
		setSize(LEFT_BORDER+CENTER_WIDTH+RIGHT_BORDER,CENTER_HEIGHT+BOTTOM_BORDER);
		setLocation(10, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		leftPanel=new IperviewPanel(this,IperviewPanel.TYPE_LEFT,LEFT_BORDER,CENTER_HEIGHT);
		leftPanel.setBounds(0, 0, LEFT_BORDER, CENTER_HEIGHT);
		leftPanel.setMesh(meshes[ACTIVE_PANEL]);
		leftPanel.setBackground(Color.GRAY);
		add(leftPanel);
		
		centerPanel=new IperviewPanel(this,IperviewPanel.TYPE_FRONT,CENTER_WIDTH,CENTER_HEIGHT);
		centerPanel.setBounds(LEFT_BORDER, 0, CENTER_WIDTH, CENTER_HEIGHT);
		centerPanel.setMesh(meshes[ACTIVE_PANEL]);
		centerPanel.setBackground(Color.BLACK);
		add(centerPanel);
		
		rightPanel=new IperviewPanel(this,IperviewPanel.TYPE_TOP,RIGHT_BORDER,CENTER_HEIGHT);
		rightPanel.setBounds(LEFT_BORDER+CENTER_WIDTH, 0, RIGHT_BORDER, CENTER_HEIGHT);
		rightPanel.setMesh(meshes[ACTIVE_PANEL]);
		rightPanel.setBackground(Color.GRAY);
		add(rightPanel);
	

		
		
		buildBottom();

	
		screenPoint=new JLabel();
		screenPoint.setBounds(120,10,100,20);
		bottom.add(screenPoint);
	
        
	
		addMenuBar();
	
		//		override to avoid a tricky paint problem
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
		initialize();
		forceReading=true;
		setVisible(true);


	}
	
	String header="<html><body>";
	String footer="</body></html>";
	private JMenu help_jm;
	private JMenuItem jmt_save_custom_mesh;
	private JMenu jmEdit;

	
	private void buildBottom() {
		
		bottom=new JPanel(null);
		
		bottom.setBounds(0,CENTER_HEIGHT,LEFT_BORDER+CENTER_WIDTH+RIGHT_BORDER,BOTTOM_BORDER);
		
		add(bottom);
	

		
		int r0=10;
		
		int r=r0;
		int c=5;
		
		JLabel lmul=new JLabel("Multiple selection:");
		lmul.setBounds(c,r,150,20);
		bottom.add(lmul);		
		checkMultipleSelection=new JCheckBox();
		checkMultipleSelection.setSelected(true);
		checkMultipleSelection.addKeyListener(this);
		checkMultipleSelection.setBounds(c+160,r,20,20);
		bottom.add(checkMultipleSelection);
		
		r+=30;

		JLabel lx=new JLabel("x:");
		lx.setBounds(c,r,20,20);
		bottom.add(lx);
		coordinatesx=new DoubleTextField(8);
		coordinatesx.setBounds(c+30,r,150,20);
		coordinatesx.addKeyListener(this);
		bottom.add(coordinatesx);
		
		checkCoordinatesx=new JCheckBox();
		checkCoordinatesx.setBounds(c+200,r,30,20);
		checkCoordinatesx.addKeyListener(this);
		bottom.add(checkCoordinatesx);


		r+=30;

		JLabel ly=new JLabel("y:");
		ly.setBounds(c,r,20,20);
		bottom.add(ly);
		coordinatesy=new DoubleTextField(8);
		coordinatesy.setBounds(c+30,r,150,20);
		coordinatesy.addKeyListener(this);
		bottom.add(coordinatesy);
		
		checkCoordinatesy=new JCheckBox();
		checkCoordinatesy.setBounds(c+200,r,30,20);
		checkCoordinatesy.addKeyListener(this);
		bottom.add(checkCoordinatesy);

		
		r+=30;
		
		JLabel lz=new JLabel("z:");
		lz.setBounds(c,r,20,20);
		bottom.add(lz);
		coordinatesz=new DoubleTextField(8);
		coordinatesz.setBounds(c+30,r,150,20);
		coordinatesz.addKeyListener(this);
		bottom.add(coordinatesz);
		
		checkCoordinatesz=new JCheckBox();
		checkCoordinatesz.setBounds(c+200,r,30,20);
		checkCoordinatesz.addKeyListener(this);
		bottom.add(checkCoordinatesz);
		
		
		r+=30;

		JLabel ed=new JLabel("Ex:");
		ed.setBounds(c,r,20,20);
		bottom.add(ed);
		extraData=new JTextField();
		extraData.setBounds(c+30,r,150,20);
		extraData.addKeyListener(this);
		extraData.setToolTipText("Optional extra value");
		bottom.add(extraData);
		checkExtraData=new JCheckBox();
		checkExtraData.setBounds(c+200,r,30,20);
		checkExtraData.addKeyListener(this);
		bottom.add(checkExtraData);
		
		
		r=r0;
		c+=300;

		addPoint=new JButton(header+"<u>I</u>nsert point"+footer);
		addPoint.addKeyListener(this);
		addPoint.addActionListener(this);
		addPoint.setFocusable(false);
		addPoint.setBounds(c,r,150,20);
		bottom.add(addPoint);
		
		rescale=new JButton(header+"<u>R</u>escale all"+footer);
		rescale.addKeyListener(this);
		rescale.addActionListener(this);
		rescale.setFocusable(false);
		rescale.setBounds(c+160,r,150,20);
		bottom.add(rescale);
		
		rescaleValue=new DoubleTextField();
		rescaleValue.setBounds(c+200,r+30,70,20);
		rescaleValue.addKeyListener(this);
		rescaleValue.setToolTipText("Scale factor");
		bottom.add(rescaleValue);

		r+=30;

		/*joinPoints=new JButton(header+"<u>J</u>oin sel points[0]"+footer);
		joinPoints.addActionListener(this);
		joinPoints.addKeyListener(this);
		joinPoints.setFocusable(false);
		joinPoints.setBounds(c,r,150,20);
		bottom.add(joinPoints);*/
		
		startBuildPolygon=new JButton(header+"Start polygo<u>n</u> <br/> points sequence"+footer);
		startBuildPolygon.addActionListener(this);
		startBuildPolygon.addKeyListener(this);
		startBuildPolygon.setFocusable(false);
		startBuildPolygon.setBounds(c,r,150,35);
		bottom.add(startBuildPolygon);

		r+=45;
			
		buildPolygon=new JButton(header+"Build <u>p</u>olygon"+footer);
		buildPolygon.addActionListener(this);
		buildPolygon.addKeyListener(this);
		buildPolygon.setFocusable(false);
		buildPolygon.setBounds(c,r,150,20);
		bottom.add(buildPolygon);
		
		JPanel movePanel=buildPointsMovePanel(c+185,r);
		bottom.add(movePanel);

		r+=30;

		deleteSelection=new JButton(header+"<u>D</u>elete selection"+footer);
		deleteSelection.addActionListener(this);
		deleteSelection.addKeyListener(this);
		deleteSelection.setFocusable(false);
		deleteSelection.setBounds(c,r,150,20);
		bottom.add(deleteSelection);
		
		r+=30;

		changePoint=new JButton(header+"<u>C</u>hange sel Point"+footer);
		changePoint.addActionListener(this);
		changePoint.addKeyListener(this);
		changePoint.setFocusable(false);
		changePoint.setBounds(c,r,150,20);
		bottom.add(changePoint);


		r+=30;

		deselectAll=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAll.addActionListener(this);
		deselectAll.addKeyListener(this);
		deselectAll.setFocusable(false);
		deselectAll.setBounds(c,r,100,20);
		bottom.add(deselectAll);
		
		r+=30;
		
		selectAll=new JButton(header+"Selec<u>t</u> all"+footer);
		selectAll.addActionListener(this);
		selectAll.addKeyListener(this);
		selectAll.setFocusable(false);
		selectAll.setBounds(c,r,100,20);
		bottom.add(selectAll);

		
		r=r0;
		c+=350;
		
		bottom.add(buildLowerRightPanel(r,c));
	
	}
	
	private JPanel buildPointsMovePanel(int i, int r) {

		JPanel move=new JPanel();
		move.setBounds(i,r,100,100);
		move.setLayout(null);

		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);
		
		objMove=new DoubleTextField();
		objMove.setBounds(30,40,40,20);
		objMove.setToolTipText("Position increment");
		move.add(objMove);
		objMove.addKeyListener(this);

		movePointUp=new JButton("+Z");
		movePointUp.setBorder(null);
		movePointUp.setBounds(40,10,20,20);
		movePointUp.addActionListener(this);
		movePointUp.setFocusable(false);
		move.add(movePointUp);
		
		movePointDown=new JButton("-Z");
		movePointDown.setBorder(null);
		movePointDown.setBounds(40,70,20,20);
		movePointDown.addActionListener(this);
		movePointDown.setFocusable(false);
		move.add(movePointDown);
		
		movePointLeft=new JButton("-Y");
		movePointLeft.setBorder(null);
		movePointLeft.setBounds(5,40,20,20);
		movePointLeft.addActionListener(this);
		movePointLeft.setFocusable(false);
		move.add(movePointLeft);
		
		movePointRight=new JButton("+Y");
		movePointRight.setBorder(null);
		movePointRight.setBounds(75,40,20,20);
		movePointRight.addActionListener(this);
		movePointRight.setFocusable(false);
		move.add(movePointRight);
		
		movePointTop=new JButton("+X");
		movePointTop.setBorder(null);
		movePointTop.setBounds(5,70,20,20);
		movePointTop.addActionListener(this);
		movePointTop.setFocusable(false);
		move.add(movePointTop);
		
		movePointBottom=new JButton("-X");
		movePointBottom.setBorder(null);
		movePointBottom.setBounds(75,70,20,20);
		movePointBottom.addActionListener(this);
		movePointBottom.setFocusable(false);
		move.add(movePointBottom);

		return move;

	}

	@Override
	public void paint(Graphics arg0) {

		super.paint(arg0);
		draw();
	}
	@Override
	public void draw() {
		
		leftPanel.draw();
		centerPanel.draw();
		rightPanel.draw();
	}
	
	@Override
	public void buildPolygon() {
		
		 PolygonMesh mesh = meshes[ACTIVE_PANEL];	
		 

		if(polygon.lineDatas.size()<3){
			deselectAll();
			return;
		}	
		mesh.polygonData.add(polygon);
        deselectAll();

		

	}
	@Override
	public void initialize() {
		
		DecimalFormatSymbols dfs=new DecimalFormatSymbols(Locale.UK);
		dfc=new DecimalFormat("###.##");
		dfc.setDecimalFormatSymbols(dfs);
		
		currentDirectory=new File("lib");
		
	}

	private void addMenuBar() {
		jmb=new JMenuBar();
		

		
		
		jmLines=new JMenu("Lines");
		jmLines.addMenuListener(this);
		jmb.add(jmLines);

		/*jmt11 = new JMenuItem("Load road");
		jmt11.addActionListener(this);
		jm.add(jmt11);*/

		jmt_load_mesh = new JMenuItem("Load mesh");
		jmt_load_mesh.addActionListener(this);
		jmLines.add(jmt_load_mesh);

		jmt_save_mesh = new JMenuItem("Save mesh");
		jmt_save_mesh.addActionListener(this);
		jmLines.add(jmt_save_mesh);

		
		jmLines.addSeparator();
		
		jmt_append_lines = new JMenuItem("Append lines");  
		jmt_append_lines.addActionListener(this);
		jmLines.add(jmt_append_lines);		
		
		jmLines.addSeparator();		
		
		jmt_save_custom_mesh = new JMenuItem("Save custom mesh");
		jmt_save_custom_mesh.addActionListener(this);
		jmLines.add(jmt_save_custom_mesh); 
		
		
		jmChange=new JMenu("Change");
		jmChange.addMenuListener(this);
		jmtUndo = new JMenuItem("Undo last");
		jmtUndo.setEnabled(false);
		jmtUndo.addActionListener(this);
		jmChange.add(jmtUndo);
		
		jmb.add(jmChange);
		
		jmEdit=new JMenu("Edit");
		jmEdit.addMenuListener(this);
		jmtGetTemplate = new JMenuItem("Insert template");	
		jmEdit.add(jmtGetTemplate);	
		jmtGetTemplate.addActionListener(this);
				
		jmb.add(jmEdit);
		
		jmView=new JMenu("View");
		jmView.addMenuListener(this);
		jmtPreview = new JMenuItem("Preview");
		jmtPreview.addActionListener(this);
		jmView.add(jmtPreview);
		jmb.add(jmView);
		
		help_jm=new JMenu("Help");
		help_jm.addMenuListener(this);		
		jmb.add(help_jm);
			
		setJMenuBar(jmb);
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		ACTIVE_KEY=-1;
		isControlMode=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object o=arg0.getSource();
		
		if( o==exit){
			
			dispose();
			System.exit(0);
		}
		else if(o==addPoint){ 
			prepareUndo();
			addPoint();
		}	
		else if(o==deleteSelection) {
			prepareUndo();
			delete();
		}
		else if(o==changePoint){
			prepareUndo();
			changeSelectedPoint();
			displayAll();
		}
		/*else if(o==joinPoints){
			prepareUndo();
			joinSelectedPoints();
			displayAll();
		}*/
		else if(o==buildPolygon){
			prepareUndo();
			buildPolygon();
			displayAll();
		}
		else if(o==startBuildPolygon){
			startBuildPolygon();
		}	
		else if(o==deselectAll){
			deselectAll();
			displayAll();
		}
		else if(o==selectAll){
			selectAllPoints();
			displayAll();
		}
		else if(o==rescale){
			prepareUndo();
			rescaleAllPoints();
			displayAll();
		}
		else if(o==movePointTop){
			prepareUndo();
			moveSelectedPoints(1,0,0);
			displayAll();
		}
		else if(o==movePointBottom){
			prepareUndo();
			moveSelectedPoints(-1,0,0);
			displayAll();
		}
		else if(o==movePointUp){
			prepareUndo();
			moveSelectedPoints(0,0,1);
			displayAll();
		}
		else if(o==movePointDown){
			prepareUndo();
			moveSelectedPoints(0,0,-1);
			displayAll();
		}
		else if(o==movePointLeft){
			prepareUndo();
			moveSelectedPoints(0,-1,0);
			displayAll();
		}
		else if(o==movePointRight){
			prepareUndo();
			moveSelectedPoints(0,+1,0);
			displayAll();
		}
		else if(o==mergeSelectedPoints){
			
			prepareUndo();
			mergeSelectedPoints();
			
		}		
		else if(o==jmt_load_mesh) {
			loadPointsFromFile();
			resetLists();
		}
		else if(o==jmt_save_mesh) {
			saveLines();			
		}
		else if(o==jmt_save_custom_mesh){
			saveLines(true);
		}	
		else if(o==jmt_append_lines) {
			appendPointsFromFile(true);
			resetLists();
		}
		else if(o==jmtUndo) {
			undo();
		}
		else if(o==jmtPreview) {
			preview();
		}
		else if(o==jmtGetTemplate) {
			getTemplate();
		}
	}
		
	private void startBuildPolygon() {
		deselectAll();
		displayAll();
	}
	
	private void mergeSelectedPoints() {
	
		
		ArrayList newPoints=new ArrayList();
		ArrayList newLines=new ArrayList();

		 PolygonMesh mesh = meshes[ACTIVE_PANEL];	
		
		int firstPoint=-1;
		
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
			if(!p.isSelected()) 
				newPoints.add(p);
			else if(firstPoint==-1){
				firstPoint=newPoints.size();
				newPoints.add(p);
			}
			else{
				
				
				
			}
				

		}


		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			

			for(int j=0;j<ld.size();j++){

				Point3D p0=mesh.points[ld.getIndex(j)];
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.get(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k);
							break;
						}
					}
				else
					newLd.addIndex(firstPoint);

			}
			if(newLd.size()>1 )
				newLines.add(newLd);




		}

		meshes[ACTIVE_PANEL]=new PolygonMesh(newPoints,newLines);
       
		deselectAll();		
		displayAll();
	}
	
	@Override
	public void preview() {
		
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(this);
		
	}

	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		int code =arg0.getKeyCode();
		ACTIVE_KEY=code;
		
		if(code==KeyEvent.VK_RIGHT  )
		{	
			translate(+1,0);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_LEFT )
		{	
			translate(-1,0);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_DOWN   )
		{	
			translate(0,+1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_UP)
		{	
			translate(0,-1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_PLUS)
		{	
			zoom(+1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_MINUS)
		{	
			zoom(-1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_C  )
		{	
			prepareUndo();
			changeSelectedPoint();   
			displayAll();   
		}
		else if(code==KeyEvent.VK_J )
		{	
			prepareUndo();
			joinSelectedPoints(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_I )
		{	
			prepareUndo();
			addPoint();
		}
		else if(code==KeyEvent.VK_M )
		{	
			mergeSelectedPoints();
		}
		else if(code==KeyEvent.VK_P )
		{	
			prepareUndo();
			buildPolygon(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_N )
		{	
			startBuildPolygon();
		}
		else if(code==KeyEvent.VK_E )
		{	
			deselectAll();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_S )
		{	
			selectAllPoints();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_D )
		{	
			prepareUndo();
			delete();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F1 )
		{	
			zoom(+1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F2 )
		{	
			zoom(-1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_R )
		{	
			rescaleAllPoints(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F3 )
		{	
			checkMultipleSelection.setSelected(true);  
		}
		else if(code==KeyEvent.VK_F4 )
		{	
			 
			checkMultipleSelection.setSelected(false);  
		}
		else if(code==KeyEvent.VK_LESS )
		{	
			 
			invertSelectedLine(); 
		}
		else if(code==KeyEvent.VK_PAGE_UP )
		{	
			//moveSelectedLine(-1); 
		}
		else if(code==KeyEvent.VK_PAGE_DOWN )
		{	
			 
			//moveSelectedLine(+1); 
		}
		else if(code==KeyEvent.VK_Q )
		{	
			rotate(+0.1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_W )
		{	
			rotate(-0.1);
			displayAll(); 
		}
		if(code==KeyEvent.VK_CONTROL && !checkMultipleSelection.isSelected()){
			if(!isControlMode)
				prepareUndo();
			isControlMode=true;
		}
	}
	@Override
	public void rotate(double df) {
		
		leftPanel.rotate(df);
		centerPanel.rotate(df);
		rightPanel.rotate(df);
	}

	@Override
	public void zoom(int n) {


		leftPanel.zoom(n);
		centerPanel.zoom(n);
		rightPanel.zoom(n);

	}

	@Override
	public void translate(int i, int j) {
		leftPanel.translate( i,  j);
		centerPanel.translate(i,  j);
		rightPanel.translate(i,  j);
		
	}



	@Override
	public void prepareUndo() {
		jmtUndo.setEnabled(true);
	    super.prepareUndo();
	}

	@Override
	public void undo() {
		super.undo();
		if(oldMeshes[ACTIVE_PANEL].size()==0)
			jmtUndo.setEnabled(false);
		resetLists();
	}
	
	@Override
	public void delete() {

		ArrayList newPoints=new ArrayList();
		ArrayList newLines=new ArrayList();
		
		 PolygonMesh mesh = meshes[ACTIVE_PANEL];

		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			
			boolean gotAllPoint=true;

			for(int j=0;j<ld.size();j++){

				LineDataVertex ldv= ld.getItem(j);
				Point3D p0=mesh.points[ldv.getVertex_index()];
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.get(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k,ldv.getVertex_index(),ldv.getVertex_texture_x(),ldv.getVertex_texture_y());
							break;
						}
					}
				else
					gotAllPoint=false;

			}
			if(newLd.size()>1 && gotAllPoint)
				newLines.add(newLd);




		}

		mesh.setPoints(newPoints);
		mesh.polygonData=newLines;
        deselectAll();
		

        displayAll();

	}
	@Override
	public void joinSelectedPoints() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		 
		 
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p0=mesh.points[i];

			for(int j=0;mesh.points!=null && j<mesh.points.length;j++){

				Point3D p1=mesh.points[j];

				if(p0.isSelected() && p1.isSelected() && i<j)
				{
					LineData ld=new LineData();
					ld.addIndex(i);
					ld.addIndex(j);
					mesh.polygonData.add(ld);

				}

			}

		}
		deselectAll();

	}
	
	@Override
	public void clean() {
		
		
		if(!checkCoordinatesx.isSelected())	coordinatesx.setText("");
		if(!checkCoordinatesy.isSelected())coordinatesy.setText("");
		if(!checkCoordinatesz.isSelected())coordinatesz.setText("");
		if(!checkExtraData.isSelected())extraData.setText("");
		
	}



	
	@Override
	public void addPoint() {

		if("".equals(coordinatesx.getText()) ||
				"".equals(coordinatesy.getText())||
						"".equals(coordinatesz.getText()) 
		)
			return;
		double x=0;
		double y=0;
		double z=0;
	
		
		if(!"".equals(coordinatesx.getText()))
		x=Double.parseDouble(coordinatesx.getText());
		if(!"".equals(coordinatesy.getText()))
			y=Double.parseDouble(coordinatesy.getText());		
		if(!"".equals(coordinatesz.getText()))
		z=Double.parseDouble(coordinatesz.getText());	

		addPoint(x,y,z);
	}
	
	@Override
	public void addPoint(double x, double y ,double z) {

		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];

		Point3D p=new Point3D(x,y,z);

		if(!"".equals(extraData.getText()))
			p.setData(extraData.getText());
		
		mesh.addPoint(p);
        
		deselectAll();
		displayAll();
		
		pointList.ensureIndexIsVisible(pointList.getModel().getSize()-1); 
		
	
	}
	

	@Override
	public void rescaleAllPoints() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		String txt=rescaleValue.getText();
		
		if(txt==null || txt.equals("")){
			
			JOptionPane.showMessageDialog(this,"Please insert rescale factor under the button");
			return;
		}	
		double val=Double.parseDouble(txt);
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
		    p.x=Math.round(p.x *val);
		    p.y=Math.round(p.y *val);
		    p.z=Math.round(p.z *val);
		}
		
	}
	@Override
	public void changeSelectedPoint() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];

		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			if(p.isSelected()){

				if(!"".equals(coordinatesx.getText()))
					p.x=Double.parseDouble(coordinatesx.getText());
				if(!"".equals(coordinatesy.getText()))
					p.y=Double.parseDouble(coordinatesy.getText());
				if(!"".equals(coordinatesz.getText()))
					p.z=Double.parseDouble(coordinatesz.getText());
				if(!"".equals(extraData.getText()))
					p.setData(extraData.getText());
				else
					p.setData(null);
		
			}

		}

		deselectAll();

	}
	@Override
	public void moveSelectedPointWithMouse(Point3D p3d, int type) {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];

		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			if(p.isSelected()){
				prepareUndo();
				
				if(type==IperviewPanel.TYPE_FRONT){
					
					p3d.setY(p.getY());
				}
				else if(type==IperviewPanel.TYPE_LEFT){
					
					p3d.setX(p.getX());
				}
				else if(type==IperviewPanel.TYPE_RIGHT){
					
					p3d.setX(p.getX());
				}
				else if(type==IperviewPanel.TYPE_TOP){
					
					p3d.setZ(p.getZ());
				}
				
				mesh.points[i]=p3d;
				return;
			}
		}	
		
	}
	@Override
	public void moveSelectedPoints(int dx, int dy, int dz) { 
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		String sqty=objMove.getText();
		
		if(sqty==null || sqty.equals(""))
			return;
		
		double qty=Double.parseDouble(sqty);
		
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			if(p.isSelected()){

				
					p.x=p.x+qty*dx;
					p.y=p.y+qty*dy;
					p.z=p.z+qty*dz;
					
					coordinatesx.setText(dfc.format(p.x));
					coordinatesy.setText(dfc.format(p.y));
					coordinatesz.setText(dfc.format(p.z));
			}

		}
		//deselectAll();

		
	}
	@Override
	public void displayAll(){
		
		resetLists();
		draw();
		
	}
	@Override
	public void deselectAll() {
		
		clean();
		deselectAllLines();
		deselectAllPoints();
		polygon=new LineData();
	
		
	
	}
	@Override
	public void deselectAllPoints() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
			p.setSelected(false);
		}
		
	}
	@Override
	public void selectAllPoints() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
			p.setSelected(true);
		}
		
	}
	

	public void deselectAllLines() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			ld.setSelected(false);
		}
		
	}

	@Override
	public void selectPoint(Point3D p) {
		
		
	
		if(!checkCoordinatesx.isSelected())
			coordinatesx.setText(dfc.format(p.x));
		if(!checkCoordinatesy.isSelected())
			coordinatesy.setText(dfc.format(p.y));
		if(!checkCoordinatesz.isSelected())
			coordinatesz.setText(dfc.format(p.z));
		if(!checkExtraData.isSelected())
			extraData.setText(p.getData()!=null?p.getData().toString():null);
			
		
		deselectAllLines();

		p.setSelected(true);
		
	}
	@Override
	public void selectPoint(int x, int y){
		// TODO Auto-generated method stub
	}
	
	private Component buildLowerRightPanel(int r,int c) {
		pointList=new JList();
        JScrollPane jsp=new JScrollPane(pointList);
        JPanel lowpane=new JPanel();
        lowpane.setBounds(c,r,500,220);        
		bottom.add(lowpane);
		
		lowpane.setLayout(null);
		JLabel jlb=new JLabel("Points:");
		jlb.setBounds(20,0,100,20);
		lowpane.add(jlb);
		jsp.setBounds(5,25,140,180);
		lowpane.add(jsp);
		
		final PolygonMesh mesh = meshes[ACTIVE_PANEL];

		pointList.addMouseListener(new MouseAdapter(){
			
		
			@Override
			public void mouseClicked(MouseEvent e){

				if(!checkMultipleSelection.isSelected()) 
					polygon=new LineData();
				
				int index=pointList.getSelectedIndex();
				for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

					Point3D p=mesh.points[i];
					if(index==i){
						selectPoint(p);
						polygon.addIndex(i);
					}	
					else if(!checkMultipleSelection.isSelected())
						p.setSelected(false);
					
				}
				
				displayAll();


			}
			
		
		}
		); 
		pointList.setFocusable(false);

		
		lineList=new JList();
		
		lineList.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e){

				int index=lineList.getSelectedIndex();
				for(int i=0;i<mesh.polygonData.size();i++){

					LineData ld=(LineData) mesh.polygonData.get(i);
					if(index==i)
						ld.setSelected(true);
					else 
						ld.setSelected(false);
					
				}
				deselectAllPoints();
				displayAll();


			}
			
		
		}
		); 
		lineList.setFocusable(false);
		
		JScrollPane jscp=new JScrollPane(lineList);
		jlb=new JLabel("Lines:");
		jlb.setBounds(170,0,100,20);
		lowpane.add(jlb);
		jscp.setBounds(150,25,150,180);
		lowpane.add(jscp);
		
		
		
		mergeSelectedPoints=new JButton(header+"<u>M</u>erge selected points"+footer);
		mergeSelectedPoints.addActionListener(this);
		mergeSelectedPoints.addKeyListener(this);
		mergeSelectedPoints.setFocusable(false);
		mergeSelectedPoints.setBounds(320,0,130,35);
		lowpane.add(mergeSelectedPoints);
		
		return lowpane;
	}

	@Override
	public void resetLists() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		DefaultListModel dlm=new DefaultListModel();
		int sel=pointList.getSelectedIndex();
		

		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
		    dlm.addElement(new PointListItem(p,i)) ; 
		}
		
		pointList.setModel(dlm);
		
		if(sel>=0 && sel<pointList.getModel().getSize())
			pointList.setSelectedIndex(sel);
		
		///////////////////////
		
		DefaultListModel dflm=new DefaultListModel();
		int selec=lineList.getSelectedIndex();
		

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			dflm.addElement(ld) ; 
		}
		
		lineList.setModel(dflm);
		
		if(selec>=0 && selec<lineList.getModel().getSize())
			lineList.setSelectedIndex(selec);
		
		
	}
	
	private void moveSelectedLine(int direction) {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected() && direction>0 && i<mesh.polygonData.size()-1 ){
					swapLines(mesh.polygonData,i+1,i);	
					lineList.setSelectedIndex(lineList.getSelectedIndex()+1);
					break;
			}
			else if(ld.isSelected() && direction<0 && i>0){
					swapLines(mesh.polygonData,i,i-1);	
					lineList.setSelectedIndex(lineList.getSelectedIndex()-1);
					break;
			}
		}
		//System.out.println(lineList.getSelectedIndex());
		resetLists();
	}
	
	private void swapLines(ArrayList lines, int i1, int i2) {
		LineData ld1=(LineData) lines.get(i1);
		LineData ld2=(LineData) lines.get(i2);
		lines.set(i2,ld1);
		lines.set(i1,ld2);
	}
	
	public void invertSelectedLine(){
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected()){
				
				LineData invertedLd=new LineData();
				
				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				mesh.polygonData.set(i,invertedLd);
			}
			
		
		}
		resetLists();
		displayAll();
	}

	public void appendPointsFromFile(boolean withBorder){	

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load lines ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			appendPointsFromFile(file,withBorder);


		}
	}
	
	public void appendPointsFromFile(File file,boolean withBorder){

		ArrayList transpoints=new ArrayList();
		ArrayList translines=new ArrayList();
		ArrayList aTexturePoints=new ArrayList();
		
		oldMeshes[ACTIVE_PANEL]=new Stack();
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				/*if(str.startsWith("P="))
					buildPoints(transpoints,str.substring(2));
				else if(str.startsWith("L="))
					buildLines(translines,str.substring(2));*/

				if(str.startsWith("v="))
					buildPoint(transpoints,str.substring(2));
				else if(str.startsWith("vt="))
					PolygonMesh.buildTexturePoint(aTexturePoints,str.substring(3));
				else if(str.startsWith("f="))
					buildLine(translines,str.substring(2),aTexturePoints);
			}

			br.close();
			
            appendPoints(mesh,transpoints,translines);
			
			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void appendPoints(PolygonMesh mesh,
			ArrayList transpoints, ArrayList translines) {
		
		
		int size=mesh.points.length;
		
		for (int i = 0; i < transpoints.size(); i++) {
			
			Point3D p=(Point3D) transpoints.get(i);
			mesh.addPoint(p);
			
		}


		for (int l = 0; l < translines.size(); l++) {

			LineData ld = (LineData) translines.get(l);
			
			LineData newld = new LineData();

			ArrayList lineDatas = ld.lineDatas;
			for (int m = 0; m < lineDatas.size(); m++) {
				
				 Integer index=(Integer) ld.lineDatas.get(m);
				 Integer newIndex=new Integer(index.intValue()+size);
				 newld.addIndex(newIndex);

			}
			
			mesh.polygonData.add(newld);
			
		}
		
	}
	@Override
	public void loadPointsFromFile(){	
		
		

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load lines ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			currentFile=file;
			
			loadPointsFromFile(file,ACTIVE_PANEL,forceReading);
			
			reloadPanels();

		
		}
	}
	
	
	public void loadPointsFromFile(File file) {
		loadPointsFromFile(file,ACTIVE_PANEL,forceReading);
		
	}
	@Override
	public void decomposeObjVertices(PrintWriter pr,PolygonMesh mesh,boolean isCustom) {
		
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

	
	void reloadPanels() {
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		centerPanel.setMesh(meshes[ACTIVE_PANEL]);
		leftPanel.setMesh(meshes[ACTIVE_PANEL]);
		rightPanel.setMesh(meshes[ACTIVE_PANEL]);

		
	}

	public class PointListItem{
		
		private DecimalFormat df=new DecimalFormat("000");

		Point3D p;
		int index=-1;

		public PointListItem(Point3D p, int index) {	
			super();
			this.p = p;
			this.index=index;
		}
		
		@Override
		public String toString() {
			
			return df.format(p.x)+","+df.format(p.y)+"("+index+")";
		}
		
	}
	
	public String addBlanksLeft(int num,double rough){
		
		
		String str=""+rough;
		
		while(str.length()<num){
			
			
			str=" "+str;
		}
		
		return str;
		
	}
	
	public void getTemplate() {
		
		ObjectEditorTemplatePanel oetp=new ObjectEditorTemplatePanel();
		
		PolygonMesh pm=oetp.getTemplate();
		
		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		
		
		
		if(pm!=null){
			ArrayList aPoints=new ArrayList();
			for (int i = 0; i < pm.points.length; i++) {
				aPoints.add(pm.points[i]);
			}
			mesh.setPoints(aPoints);
			mesh.polygonData=pm.polygonData;
			displayAll();
		}
		
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		leftPanel.addPropertyChangeListener(listener);
		centerPanel.addPropertyChangeListener(listener);
		rightPanel.addPropertyChangeListener(listener);
	}
	@Override
    public void menuSelected(MenuEvent arg0) {
    	
    	super.menuSelected(arg0);
    	
    	Object o = arg0.getSource();
    	
		if(o==help_jm){
			
			help();
			
		}
    }
    
	private void help() {
	
		
		HelpPanel hp=new HelpPanel(300,200,this.getX()+100,this.getY(),HelpPanel.IPERVIEW_EDITOR_HELP_TEXT,this);
		
	}



}
