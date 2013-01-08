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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;

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
	private JMenu jm3;
	private JMenuItem jmt31;
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
	private JMenu jm1;
	private JMenuItem jmt11;
	private JMenuItem jmt12;
	private JMenuItem jmt13;
	private JMenuItem jmt14;
	private JMenu jm4;
	private JMenuItem jmt41;
	private JCheckBoxMenuItem jmt42;
	private JMenu jm5;
	private JMenuItem jmt51;


	private BufferedImage backgroundImage=null;
	private JMenuItem jmt54;
	
	public JCheckBox checkCoordinatesx;
	public JCheckBox checkCoordinatesz;
	public JCheckBox checkCoordinatesy;
	public JCheckBox checkExtraData; 
	
	int ACTIVE_KEY=-1;
	boolean isControlMode=false;
	private JMenuItem jmt15;
	Point3D selectedPoint=null;

	
	public static void main(String args[]){

		
		IperviewEditor editor=new IperviewEditor();
		editor.setVisible(true);
			

	
	}

	public IperviewEditor(){

		
		setTitle("Iperview 1.0.0");
		
		points=new Vector();
	
		setSize(LEFT_BORDER+CENTER_WIDTH+RIGHT_BORDER,CENTER_HEIGHT+BOTTOM_BORDER);
		setLocation(10, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		leftPanel=new IperviewPanel(this,IperviewPanel.TYPE_LEFT,LEFT_BORDER,CENTER_HEIGHT);
		leftPanel.setBounds(0, 0, LEFT_BORDER, CENTER_HEIGHT);
		leftPanel.setPoints(points);
		leftPanel.setLines(lines);
		leftPanel.setBackground(Color.GRAY);
		add(leftPanel);
		
		centerPanel=new IperviewPanel(this,IperviewPanel.TYPE_FRONT,CENTER_WIDTH,CENTER_HEIGHT);
		centerPanel.setBounds(LEFT_BORDER, 0, CENTER_WIDTH, CENTER_HEIGHT);
		centerPanel.setPoints(points);
		centerPanel.setLines(lines);
		centerPanel.setBackground(Color.BLACK);
		add(centerPanel);
		
		rightPanel=new IperviewPanel(this,IperviewPanel.TYPE_TOP,RIGHT_BORDER,CENTER_HEIGHT);
		rightPanel.setBounds(LEFT_BORDER+CENTER_WIDTH, 0, RIGHT_BORDER, CENTER_HEIGHT);
		rightPanel.setPoints(points);
		rightPanel.setLines(lines);
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
		
		setVisible(true);


	}
	
	String header="<html><body>";
	String footer="</body></html>";
	private JMenu help_jm;

	
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

		/*joinPoints=new JButton(header+"<u>J</u>oin sel points"+footer);
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


	public void paint(Graphics arg0) {

		super.paint(arg0);
		draw();
	}
	
	public void draw() {
		
		leftPanel.draw();
		centerPanel.draw();
		rightPanel.draw();
	}
	

	public void buildPolygon() {


		if(polygon.lineDatas.size()<3){
			deselectAll();
			return;
		}	
        lines.add(polygon);
        deselectAll();

		

	}
	
	public void initialize() {
		
		DecimalFormatSymbols dfs=new DecimalFormatSymbols(Locale.UK);
		dfc=new DecimalFormat("###.##");
		dfc.setDecimalFormatSymbols(dfs);
		
		currentDirectory=new File("lib");
		
	}

	private void addMenuBar() {
		jmb=new JMenuBar();
		

		
		
		jm1=new JMenu("Lines");
		jm1.addMenuListener(this);
		jmb.add(jm1);

		/*jmt11 = new JMenuItem("Load road");
		jmt11.addActionListener(this);
		jm.add(jmt11);*/

		jmt11 = new JMenuItem("Load lines");
		jmt11.addActionListener(this);
		jm1.add(jmt11);

		jmt12 = new JMenuItem("Save lines");
		jmt12.addActionListener(this);
		jm1.add(jmt12);
		
		jm1.addSeparator();
		
		
		jmt15 = new JMenuItem("Append lines");  
		jmt15.addActionListener(this);
		jm1.add(jmt15);
		
		
		jm3=new JMenu("Change");
		jm3.addMenuListener(this);
		jmt31 = new JMenuItem("Undo last");
		jmt31.setEnabled(false);
		jmt31.addActionListener(this);
		jm3.add(jmt31);
		
		jmb.add(jm3);
		
		jm5=new JMenu("Edit");
		jm5.addMenuListener(this);
		jmt51 = new JMenuItem("Insert template");	
		jm5.add(jmt51);	
		jmt51.addActionListener(this);
				
		jmb.add(jm5);
		
		jm4=new JMenu("View");
		jm4.addMenuListener(this);
		jmt41 = new JMenuItem("Preview");
		jmt41.addActionListener(this);
		jm4.add(jmt41);
		jmb.add(jm4);
		
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
		else if(o==jmt11) {
			loadPointsFromFile(false);
			resetLists();
		}
		else if(o==jmt12) {
			saveLines(false);
			
		}
		else if(o==jmt13) {
			loadPointsFromFile(true);
			resetLists();
		}
		else if(o==jmt15) {
			appendPointsFromFile(true);
			resetLists();
		}
		else if(o==jmt14) {
			saveLines(true);
			
		}
		else if(o==jmt31) {
			undo();
		}
		else if(o==jmt41) {
			preview();
		}
		else if(o==jmt51) {
			getTemplate();
		}
	}
		
	private void startBuildPolygon() {
		deselectAll();
		displayAll();
	}
	
	private void mergeSelectedPoints() {
	
		
		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		
		int firstPoint=-1;
		
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);
			if(!p.isSelected()) 
				newPoints.add(p);
			else if(firstPoint==-1){
				firstPoint=newPoints.size();
				newPoints.add(p);
			}
			else{
				
				
				
			}
				

		}


		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			

			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) points.elementAt(ld.getIndex(j));
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.elementAt(k);
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

		points=newPoints;
		lines=newLines;
        deselectAll();
		
		displayAll();
	}
	

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
			zoomIn();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_MINUS)
		{	
			zoomOut();
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
			zoomIn();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F2 )
		{	
			zoomOut();
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
	
	public void rotate(double df) {
		
		leftPanel.rotate(df);
		centerPanel.rotate(df);
		rightPanel.rotate(df);
	}


	public void zoomIn() {
		
		leftPanel.zoomIn();
		centerPanel.zoomIn();
		rightPanel.zoomIn();
		
	}

	
	public void zoomOut() {
		
		leftPanel.zoomOut();
		centerPanel.zoomOut();
		rightPanel.zoomOut();
		
	}

	public void translate(int i, int j) {
		leftPanel.translate( i,  j);
		centerPanel.translate(i,  j);
		rightPanel.translate(i,  j);
		
	}



	
	public void prepareUndo() {
		jmt31.setEnabled(true);
	    super.prepareUndo();
	}


	public void undo() {
		super.undo();
		if(oldPoints.size()==0)
			jmt31.setEnabled(false);
		resetLists();
	}
	
	
	public void delete() {

		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			
			boolean gotAllPoint=true;

			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) points.elementAt(ld.getIndex(j));
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.elementAt(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k);
							break;
						}
					}
				else
					gotAllPoint=false;

			}
			if(newLd.size()>1 && gotAllPoint)
				newLines.add(newLd);




		}

		points=newPoints;
		lines=newLines;
        deselectAll();
		

        displayAll();

	}
	
	public void joinSelectedPoints() {
		for(int i=0;i<points.size();i++){

			Point3D p0=(Point3D) points.elementAt(i);

			for(int j=0;j<points.size();j++){

				Point3D p1=(Point3D) points.elementAt(j);

				if(p0.isSelected() && p1.isSelected() && i<j)
				{
					LineData ld=new LineData();
					ld.addIndex(i);
					ld.addIndex(j);
					lines.add(ld);

				}

			}

		}
		deselectAll();

	}
	


	public void clean() {
		
		
		if(!checkCoordinatesx.isSelected())	coordinatesx.setText("");
		if(!checkCoordinatesy.isSelected())coordinatesy.setText("");
		if(!checkCoordinatesz.isSelected())coordinatesz.setText("");
		if(!checkExtraData.isSelected())extraData.setText("");
		
	}



	
	
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
	
	
	public void addPoint(double x, double y ,double z) {

		


		Point3D p=new Point3D(x,y,z);

		if(!"".equals(extraData.getText()))
			p.setData(extraData.getText());
		
		points.add(p);
        
		deselectAll();
		displayAll();
		
		pointList.ensureIndexIsVisible(pointList.getModel().getSize()-1); 
		
	
	}
	

	
	public void rescaleAllPoints() {
		
		String txt=rescaleValue.getText();
		
		if(txt==null || txt.equals("")){
			
			JOptionPane.showMessageDialog(this,"Please insert rescale factor under the button");
			return;
		}	
		double val=Double.parseDouble(txt);
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);
		    p.x=Math.round(p.x *val);
		    p.y=Math.round(p.y *val);
		    p.z=Math.round(p.z *val);
		}
		
	}
	
	public void changeSelectedPoint() {

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);

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
	
	public void moveSelectedPointWithMouse(Point3D p3d, int type) {

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);

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
				
				points.setElementAt(p3d,i);
				return;
			}
		}	
		
	}
	
	public void moveSelectedPoints(int dx, int dy, int dz) { 
		
		String sqty=objMove.getText();
		
		if(sqty==null || sqty.equals(""))
			return;
		
		double qty=Double.parseDouble(sqty);
		
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);

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
	
	public void displayAll(){
		
		resetLists();
		draw();
		
	}
	
	public void deselectAll() {
		
		clean();
		deselectAllLines();
		deselectAllPoints();
		polygon=new LineData();
	
		
	
	}

	public void deselectAllPoints() {
		
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);
			p.setSelected(false);
		}
		
	}
	
	public void selectAllPoints() {
		
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);
			p.setSelected(true);
		}
		
	}
	

	public void deselectAllLines() {
		
		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			ld.setSelected(false);
		}
		
	}

	
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

		pointList.addMouseListener(new MouseAdapter(){
			
		
			
			public void mouseClicked(MouseEvent e){

				if(!checkMultipleSelection.isSelected()) 
					polygon=new LineData();
				
				int index=pointList.getSelectedIndex();
				for(int i=0;i<points.size();i++){

					Point3D p=(Point3D) points.elementAt(i);
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
			
			
			public void mouseClicked(MouseEvent e){

				int index=lineList.getSelectedIndex();
				for(int i=0;i<lines.size();i++){

					LineData ld=(LineData) lines.elementAt(i);
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

	
	public void resetLists() {
		
		DefaultListModel dlm=new DefaultListModel();
		int sel=pointList.getSelectedIndex();
		

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.elementAt(i);
		    dlm.addElement(new PointListItem(p,i)) ; 
		}
		
		pointList.setModel(dlm);
		
		if(sel>=0 && sel<pointList.getModel().getSize())
			pointList.setSelectedIndex(sel);
		
		///////////////////////
		
		DefaultListModel dflm=new DefaultListModel();
		int selec=lineList.getSelectedIndex();
		

		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			dflm.addElement(ld) ; 
		}
		
		lineList.setModel(dflm);
		
		if(selec>=0 && selec<lineList.getModel().getSize())
			lineList.setSelectedIndex(selec);
		
		
	}
	
	private void moveSelectedLine(int direction) {
		
		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			if(ld.isSelected() && direction>0 && i<lines.size()-1 ){
					swapLines(lines,i+1,i);	
					lineList.setSelectedIndex(lineList.getSelectedIndex()+1);
					break;
			}
			else if(ld.isSelected() && direction<0 && i>0){
					swapLines(lines,i,i-1);	
					lineList.setSelectedIndex(lineList.getSelectedIndex()-1);
					break;
			}
		}
		//System.out.println(lineList.getSelectedIndex());
		resetLists();
	}
	
	private void swapLines(Vector lines, int i1, int i2) {
		LineData ld1=(LineData) lines.elementAt(i1);
		LineData ld2=(LineData) lines.elementAt(i2);
		lines.setElementAt(ld1,i2);
		lines.setElementAt(ld2,i1);
	}
	
	public void invertSelectedLine(){
		
		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			if(ld.isSelected()){
				
				LineData invertedLd=new LineData();
				
				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				lines.setElementAt(invertedLd,i);
			}
			
		
		}
		resetLists();
		displayAll();
	}
	
	private void saveLines(boolean withBorder) {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save lines");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			currentDirectory=fc.getCurrentDirectory();
			saveLines(file,withBorder);

		} 


	}

	public void saveLines(File file,boolean withBorder) {



		PrintWriter pr;
		try {
			pr = new PrintWriter(new FileOutputStream(file));
			pr.print("P=");

			for(int i=0;i<points.size();i++){

				Point3D p=(Point3D) points.elementAt(i);

				pr.print(decomposePoint(p,withBorder));
				if(i<points.size()-1)
					pr.print("_");
			}	

			pr.print("\nL=");

			for(int i=0;i<lines.size();i++){

				LineData ld=(LineData) lines.elementAt(i);

				pr.print(decomposeLineData(ld));
				if(i<lines.size()-1)
					pr.print("_");
			}	

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
	
	private String decomposePoint(Point3D p, boolean withBorder) {
		String str="";

		str=p.x+","+p.y+","+p.z;
		
		return str;
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

		Vector transpoints=new Vector();
		Vector translines=new Vector();
		
		oldPoints=new Stack();
		oldLines=new Stack();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("P="))
					buildPoints(transpoints,str.substring(2));
				else if(str.startsWith("L="))
					buildLines(translines,str.substring(2));


			}

			br.close();
			
            appendPoints(points,lines,transpoints,translines);
			
			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void appendPoints(Vector points2, Vector lines2,
			Vector transpoints, Vector translines) {
		
		
		int size=points2.size();
		
		for (int i = 0; i < transpoints.size(); i++) {
			
			Point3D p=(Point3D) transpoints.elementAt(i);
			points2.add(p);
			
		}


		for (int l = 0; l < translines.size(); l++) {

			LineData ld = (LineData) translines.elementAt(l);
			
			LineData newld = new LineData();

			Vector lineDatas = ld.lineDatas;
			for (int m = 0; m < lineDatas.size(); m++) {
				
				 Integer index=(Integer) ld.lineDatas.elementAt(m);
				 Integer newIndex=new Integer(index.intValue()+size);
				 newld.addIndex(newIndex);

			}
			
			lines2.add(newld);
			
		}
		
	}

	public void loadPointsFromFile(boolean withBorder){	

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load lines ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			loadPointsFromFile(file);
			
			reloadPanels();

		
		}
	}
	
	void reloadPanels() {
		
		centerPanel.setPoints(points);
		centerPanel.setLines(lines);
		leftPanel.setPoints(points);
		leftPanel.setLines(lines);
		rightPanel.setPoints(points);
		rightPanel.setLines(lines);
		
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
		
		if(pm!=null){
			points=new Vector();
			for (int i = 0; i < pm.points.length; i++) {
				points.add(pm.points[i]);
			}
			lines=pm.polygonData;
			displayAll();
		}
		
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		leftPanel.addPropertyChangeListener(listener);
		centerPanel.addPropertyChangeListener(listener);
		rightPanel.addPropertyChangeListener(listener);
	}
	
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
