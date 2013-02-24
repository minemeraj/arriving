package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;
import com.editors.EditorPanel;
import com.editors.road.RoadEditorPolygonDetail;

public class ObjectEditorPanel extends JPanel implements EditorPanel,ActionListener,KeyListener, ItemListener,MouseListener,MouseWheelListener,MouseMotionListener{


	public ObjectEditor oe=null;
	
	
	public int HEIGHT=ObjectEditor.HEIGHT;
	public int WIDTH=ObjectEditor.WIDTH;


	public int RIGHT_BORDER=320;
	public int BOTTOM_BORDER=100;

	Graphics2D g2;

	public JPanel right;
	public JTextField coordinatesx;
	public JTextField coordinatesy;
	public JTextField coordinatesz;
	public JTextField extraData=null;

	public JButton addPoint;
	public JButton deleteSelection;
	public JButton changePoint;
	public JButton deselectAll;;
	public JCheckBox checkMultipleSelection;
	public JList pointList=null;


	//public JButton joinPoints;
	public JButton buildPolygon;
	

	public boolean ISDEBUG=false;
	public JPanel bottom;
	public JLabel screenPoint;
	public JCheckBox checkCoordinatesx;
	public JCheckBox checkCoordinatesz;
	public JCheckBox checkCoordinatesy;
	public JCheckBox checkExtraData;


	public boolean isUseTextures=true;


	public JList lineList;
	public JButton selectAll;
	public LineData polygon=new LineData();


	public double alfa=Math.PI/4;
	public double sinAlfa=Math.sin(alfa);
	public double cosAlfa=Math.cos(alfa);
	public double fi=0;
	public double sinf=Math.sin(fi);
	public double cosf=Math.cos(fi);
	public JButton rescale;
	public DoubleTextField rescaleValue;

	public DoubleTextField objMove;
	public JButton movePointUp;
	public JButton movePointDown;
	public JButton movePointLeft;
	public JButton movePointRight;
	public JButton movePointTop;
	public JButton movePointBottom;
	public JButton startBuildPolygon;
	public JButton mergeSelectedPoints;
	public JButton polygonDetail;
	
	public File currentDirectory=null;

	public DecimalFormat dfc=null;
	
	public Rectangle currentRect=null;
	public boolean isDrawCurrentRect=false;


	public double teta;
	double costeta=Math.cos(teta);
	double sinteta=Math.sin(teta);



	public ObjectEditorPanel(ObjectEditor oe){

	
		this.oe=oe;
		
		setLayout(null);
		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);



	}
	
	/**
	 * 
	 */
	public void initialize() {


		g2=(Graphics2D) getGraphics();
		
		DecimalFormatSymbols dfs=new DecimalFormatSymbols(Locale.UK);
		dfc=new DecimalFormat("###.##");
		dfc.setDecimalFormatSymbols(dfs);
		

	}


	public void displayAll() {
 
		
	}


	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}
	
	public static Polygon3D buildPolygon(LineData ld,Vector points) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point3D p=(Point3D) points.elementAt(num);


			//real coordinates
			cxr[i]=(int)(p.x);
			cyr[i]=(int)(p.y);
			czr[i]=(int)(p.z);
		}


		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

        return p3dr;

	}


	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==addPoint){ 
			oe.prepareUndo();
			addPoint();
		}	
		else if(o==deleteSelection) {
			oe.prepareUndo();
			delete();
		}	
		else if(o==changePoint){
			oe.prepareUndo();
			changeSelectedPoint();
			displayAll();
		}
		else if(o==mergeSelectedPoints){
			
			oe.prepareUndo();
			mergeSelectedPoints();
			
		}
		else if(o==startBuildPolygon){
			startBuildPolygon();
		}		
		else if(o==buildPolygon){
			oe.prepareUndo();
			buildPolygon();
			displayAll();
		}
		else if(o==polygonDetail){
			polygonDetail();
			//displayAll();
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
			oe.prepareUndo();
			rescaleAllPoints();
			displayAll();
		}
		else if(o==movePointUp){
			oe.prepareUndo();
			moveSelectedPoints(0,0,1);
			displayAll();
		}
		else if(o==movePointDown){
			oe.prepareUndo();
			moveSelectedPoints(0,0,-1);
			displayAll();
		}
		else if(o==movePointLeft){
			oe.prepareUndo();
			moveSelectedPoints(0,-1,0);
			displayAll();
		}
		else if(o==movePointRight){
			oe.prepareUndo();
			moveSelectedPoints(0,+1,0);
			displayAll();
		}
		else if(o==movePointTop){
			oe.prepareUndo();
			moveSelectedPoints(1,0,0);
			displayAll();
		}
		else if(o==movePointBottom){
			oe.prepareUndo();
			moveSelectedPoints(-1,0,0);
			displayAll();
		}

	}

	
	public void moveSelectedPoints(int dx, int dy, int dz) { 
		
		String sqty=objMove.getText();
		
		if(sqty==null || sqty.equals(""))
			return;
		
		double qty=Double.parseDouble(sqty);
		
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);

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


	public void rescaleAllPoints() {
		
		String txt=rescaleValue.getText();
		
		if(txt==null || txt.equals("")){
			
			JOptionPane.showMessageDialog(this,"Please insert rescale factor under the button");
			return;
		}	
		double val=Double.parseDouble(txt);
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);
		    p.x=Math.round(p.x *val);
		    p.y=Math.round(p.y *val);
		    p.z=Math.round(p.z *val);
		}
		
	}


	
	public void selectAllPoints() {
		
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);
			p.setSelected(true);
		}
		
	}

	public void buildPolygon() {

		if(polygon.lineDatas.size()<3){
			deselectAll();
			return;
		}	
        oe.lines[0].add(polygon);

		deselectAll();

	}
	
	private void polygonDetail() {
	
		
		int sizel=oe.lines[0].size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			if(!ld.isSelected())
				continue;
			
			RoadEditorPolygonDetail repd=new RoadEditorPolygonDetail(oe,ld);
	
			if(repd.getModifiedLineData()!=null){
				
				oe.lines[0].setElementAt(repd.getModifiedLineData(),i);
				
			}
			
			break;
		}
		displayAll();
		
	}
	
	


	public void joinSelectedPoints() {
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p0=(Point3D) oe.points[0].elementAt(i);

			for(int j=0;j<oe.points[0].size();j++){

				Point3D p1=(Point3D) oe.points[0].elementAt(j);

				if(p0.isSelected() && p1.isSelected() && i<j)
				{
					LineData ld=new LineData();
					ld.addIndex(i);
					ld.addIndex(j);
					oe.lines[0].add(ld);

				}

			}

		}
		deselectAll();

	}


	public void changeSelectedPoint() {

		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);

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


	public void addPoint() {

		if("".equals(coordinatesx.getText()) |
				"".equals(coordinatesy.getText()) |
				"".equals(coordinatesz.getText())
		)
			return;
		double x=Double.parseDouble(coordinatesx.getText());
		double y=Double.parseDouble(coordinatesy.getText());
		double z=Double.parseDouble(coordinatesz.getText());

		
		addPoint(x,y,z);

	}
	
	public void addPoint(double x, double y, double z) {

		if(!"".equals(coordinatesx.getText()))
			x=Double.parseDouble(coordinatesx.getText());
		if(!"".equals(coordinatesy.getText()))
			y=Double.parseDouble(coordinatesy.getText());
		if(!"".equals(coordinatesz.getText()))
			z=Double.parseDouble(coordinatesz.getText());

		Point3D p=new Point3D(x,y,z);
		
		if(!"".equals(extraData.getText()))
			p.setData(extraData.getText());

		int sz= oe.points[0].size();
		for (int i = 0; i <sz; i++) {
			Point3D old_p = (Point3D) oe.points[0].elementAt(i);
			
			if(old_p.x==p.x && old_p.y==p.y && old_p.z==p.z){
				
				JOptionPane.showMessageDialog(this,"Point already present!");
				return;
				
				
			}
		}
		
		oe.points[0].add(p);
        
		deselectAll();
		displayAll();
		
		pointList.ensureIndexIsVisible(pointList.getModel().getSize()-1); 

	}

	public void getTemplate() {
		
		ObjectEditorTemplatePanel oetp=new ObjectEditorTemplatePanel();
		
		PolygonMesh pm=oetp.getTemplate();
		
		if(pm!=null){
			oe.points[0]=new Vector();
			for (int i = 0; i < pm.points.length; i++) {
				oe.points[0].add(pm.points[i]);
			}
			oe.lines[0]=pm.polygonData;
			displayAll();
		}
		
	}



	public void delete() {

		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			boolean gotAllPoint=true;
			
			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) oe.points[0].elementAt(ld.getIndex(j));
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

		oe.points[0]=newPoints;
		oe.lines[0]=newLines;
        deselectAll();
		

		displayAll();

	}
	
	private void mergeSelectedPoints() {
	
		
		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		
		int firstPoint=-1;
		
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);
			if(!p.isSelected()) 
				newPoints.add(p);
			else if(firstPoint==-1){
				firstPoint=newPoints.size();
				newPoints.add(p);
			}
			else{
				
				
				
			}
				

		}


		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			
			boolean insertedFirst=false;
			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) oe.points[0].elementAt(ld.getIndex(j));
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.elementAt(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k);
							break;
						}
					}
				else{
					//cause using convex net polygons a point can't appear more than one time
					if(insertedFirst)
						continue;
					
					newLd.addIndex(firstPoint);
					
					insertedFirst=true;
				}

			}
			if(newLd.size()>1 )
				newLines.add(newLd);




		}

		oe.points[0]=newPoints;
		oe.lines[0]=newLines;
        deselectAll();
		
		displayAll();
	}

	public void deselectAll() {
		
		clean();
		deselectAllLines();
		deselectAllPoints();
		polygon=new LineData();

	
		
	
	}

	public void deselectAllPoints() {
		
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);
			p.setSelected(false);
		}
		
	}
	
	public void deselectAllLines() {
		
		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			ld.setSelected(false);
		}
		
	}


	


	
	
	public void clean(){

		if(!checkCoordinatesx.isSelected())	coordinatesx.setText("");
		if(!checkCoordinatesy.isSelected())coordinatesy.setText("");
		if(!checkCoordinatesz.isSelected())coordinatesz.setText("");
		if(!checkExtraData.isSelected())extraData.setText("");
		/*if(!checkCoordinatesdx.isSelected())coordinatesdx.setText("");
		if(!checkCoordinatesdy.isSelected())coordinatesdy.setText("");
		if(!checkCoordinatesdz.isSelected())coordinatesdz.setText("");
		if(!checkObjectIndex.isSelected())objectIndex.setText("");
		if(!checkColor.isSelected())checkColor.setBackground(ZBuffer.fromHexToColor("FFFFFF"));
		 */
		

	}

	public void keyPressed(KeyEvent arg0) {

		int code =arg0.getKeyCode();

		if(code==KeyEvent.VK_DOWN )
			down();
		else if(code==KeyEvent.VK_UP  )
			up();
		else if(code==KeyEvent.VK_LEFT )
		{	left(); 
		   
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	
			right();   
		
		}
		else if(code==KeyEvent.VK_C  )
		{	
			oe.prepareUndo();
			changeSelectedPoint();   
		    displayAll();   
		}
		else if(code==KeyEvent.VK_J )
		{	
			oe.prepareUndo();
			joinSelectedPoints(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_I )
		{	
			oe.prepareUndo();
			addPoint();
		}
		else if(code==KeyEvent.VK_P )
		{	
			oe.prepareUndo();
			buildPolygon(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_E )
		{	
			deselectAll();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_T )
		{	
			selectAllPoints();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_D )
		{	
			oe.prepareUndo();
			delete();
			displayAll(); 
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
		else if(code==KeyEvent.VK_A){
			rotateTeta(+1);
			displayAll();
		}	
		else if(code==KeyEvent.VK_S){
			rotateTeta(-1);
			displayAll();
		}	
		else if(code==KeyEvent.VK_M )
		{	
			mergeSelectedPoints();
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
		else if(code==KeyEvent.VK_N )
		{	
			startBuildPolygon();
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
		else if(code==KeyEvent.VK_PAGE_UP )
		{	
			moveSelectedLine(-1); 
		}
		else if(code==KeyEvent.VK_PAGE_DOWN )
		{	
			 
			moveSelectedLine(+1); 
		}
		else if(code==KeyEvent.VK_LESS )
		{	
			 
			invertSelectedLine(); 
		}
		/*else if(code==KeyEvent.VK_1 )
		{	
			 
			oe.set3DView(); 
		}
		else if(code==KeyEvent.VK_2 )
		{	
			 
			oe.setTopView(); 
		}
		else if(code==KeyEvent.VK_3 )
		{	
			 
			oe.setLeftView(); 
		}
		else if(code==KeyEvent.VK_4)
		{	
			 
			oe.setFrontView(); 
		}*/

	}

	private void startBuildPolygon() {
		
		deselectAll();
		displayAll();
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void moveSelectedLine(int direction) {
		
		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			if(ld.isSelected() && direction>0 && i<oe.lines[0].size()-1 ){
					swapLines(oe.lines[0],i+1,i);	
					lineList.setSelectedIndex(lineList.getSelectedIndex()+1);
					break;
			}
			else if(ld.isSelected() && direction<0 && i>0){
					swapLines(oe.lines[0],i,i-1);	
					lineList.setSelectedIndex(lineList.getSelectedIndex()-1);
					break;
			}
		}
		//System.out.println(lineList.getSelectedIndex());
		resetLists();
	}
	
	public void swapLines(Vector lines, int i1, int i2) {
		LineData ld1=(LineData) oe.lines[0].elementAt(i1);
		LineData ld2=(LineData) oe.lines[0].elementAt(i2);
		oe.lines[0].setElementAt(ld1,i2);
		oe.lines[0].setElementAt(ld2,i1);
	}

	public void invertSelectedLine(){
		
		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			if(ld.isSelected()){
				
				LineData invertedLd=new LineData();
				
				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				oe.lines[0].setElementAt(invertedLd,i);
			}
			
		
		}
		resetLists();
		displayAll();
	}
	
	public void resetLists() {
		
		DefaultListModel dlm=new DefaultListModel();
		int sel=pointList.getSelectedIndex();
		

		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);
		    dlm.addElement(new PointListItem(p,i)) ; 
		}
		
		pointList.setModel(dlm);
		
		if(sel>=0 && sel<pointList.getModel().getSize())
			pointList.setSelectedIndex(sel);
		
		///////////////////////
		
		DefaultListModel dflm=new DefaultListModel();
		int selec=lineList.getSelectedIndex();
		

		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			dflm.addElement(ld) ; 
		}
		
		lineList.setModel(dflm);
		
		if(selec>=0 && selec<lineList.getModel().getSize())
			lineList.setSelectedIndex(selec);
		
		
	}

	public void mouseClicked(MouseEvent arg0) {

		int buttonNum=arg0.getButton();
		//right button click

		selectPoint(arg0.getX(),arg0.getY());
		displayAll();

	}
	
	public void mouseDragged(MouseEvent e) {

		isDrawCurrentRect=true;
		updateSize(e);
		
	}
	
    void updateSize(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentRect.setSize(x - currentRect.x,
                            y - currentRect.y);
       
        
        displayAll(); 
        
   
    }

	public void mouseMoved(MouseEvent e) {



	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		
		int xp=arg0.getX();
		int yp=arg0.getY();
		currentRect = new Rectangle(xp, yp, 0, 0);

	}

	public void mouseReleased(MouseEvent arg0) {
		
		updateSize(arg0);
        selectPointsWithRectangle();
        isDrawCurrentRect=false;
        displayAll();

	}
	
	public void displayCurrentRect(Graphics2D bufGraphics) {
		
		if(!isDrawCurrentRect)
			return;
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		
		bufGraphics.setColor(Color.WHITE);
		bufGraphics.drawRect(x0,y0,x1-x0,y1-y0);
		
	}
	
	public void selectPointsWithRectangle() {
		
	
		
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {

		int pix=arg0.getUnitsToScroll();
		if(pix>0) up();
		else down();

	}
	
	public void rotate(double df){
		
		 fi+=df;
		 sinf=Math.sin(fi);
		 cosf=Math.cos(fi);
		
	}
	
	private void rotateTeta(int i) {
		
		teta+=0.1*i;
		costeta=Math.cos(teta);
		sinteta=Math.sin(teta);
		
	}	

	public void zoomOut() {
		// TODO Auto-generated method stub
		
	}


	public void zoomIn() {
		// TODO Auto-generated method stub
		
	}

	public void right() {
		// TODO Auto-generated method stub
		
	}


	public void left() {
		// TODO Auto-generated method stub
		
	}


	public void up() {
		// TODO Auto-generated method stub
		
	}


	public void down() {
		// TODO Auto-generated method stub
		
	}

	public void moveCenter(int dx,int dy) {
		// TODO Auto-generated method stub
	}


	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void selectPoint(int x, int y){
		// TODO Auto-generated method stub
	}
		
	public class PointListItem{
		
		public DecimalFormat df=new DecimalFormat("000");

		Point3D p;
		int index=-1;

		public PointListItem(Point3D p, int index) {	
			super();
			this.p = p;
			this.index=index;
		}
		
	
		public String toString() {
			
			return df.format(p.x)+","+df.format(p.y)+","+df.format(p.z)+"("+index+")";
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
	
	public void moveSelectedPointWithMouse(Point3D p3d, int type) {
		// TODO Auto-generated method stub
		
	}

	public void draw() {
		// TODO Auto-generated method stub
		
	}

	public void translate(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	public class PolygonComparator implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			
			LineDataWithDistance pol1=(LineDataWithDistance) o1;
			LineDataWithDistance pol2=(LineDataWithDistance) o2;
			
			if(pol1.getScreen_distance()>pol2.getScreen_distance())
				return 1;
			else if(pol1.getScreen_distance()<pol2.getScreen_distance())
				return -1;
				
			
			return 0;
		}
		
	}
	

	public class LineDataWithDistance {
		

		public LineDataWithDistance(LineData line_data, double screen_distance) {
			super();
			this.line_data = line_data;
			this.screen_distance = screen_distance;
		}
		LineData line_data=null;
		double screen_distance=0;
		

		public double getScreen_distance() {
			return screen_distance;
		}
		public void setScreen_distance(double screen_distance) {
			this.screen_distance = screen_distance;
		}
		public LineData getLine_data() {
			return line_data;
		}
		public void setLine_data(LineData line_data) {
			this.line_data = line_data;
		}

		
	}
	
	

	public void selectPolygon(int x, int y) {
		
		Vector polygonsInLine=new Vector();
	
		
		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
		
			if(ld.size()>2){
				
				Polygon3D p3d=buildPolygon(ld,oe.points[0]);
	
				Polygon poly=buildProjection(p3d);


				if(poly.contains(x,y)){
	
				
					double current_distance=calculateScreenDistance(p3d,x,y);
	
					polygonsInLine.add(new LineDataWithDistance(ld,current_distance));
					
				
				}
				
			}
			
			if(!checkMultipleSelection.isSelected())
				ld.setSelected(false);
		}
		PolygonComparator cc=new PolygonComparator();
		
		if(polygonsInLine.size()>0){
			
			Collections.sort(polygonsInLine,cc);
     
			LineData ld=((LineDataWithDistance)polygonsInLine.lastElement()).getLine_data();
			ld.setSelected(true);
		}
		
	}

	
	public double calculateScreenDistance(Polygon3D p3d, int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Polygon buildProjection(Polygon3D p3d) { 



		int size=p3d.npoints;

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];
		
		//Point3D pn= Polygon3D.findNormal(p3d);
		//boolean clock_versus=pn.z>=0;	
		
		for(int i=0;i<size;i++){
			
			double x=p3d.xpoints[i];
			double y=p3d.ypoints[i];
			double z=p3d.zpoints[i];

			//if(clock_versus){
				cxr[i]=calcAssX(x,y,z); 
				cyr[i]=calcAssY(x,y,z);
				czr[i]=0;
			/*}
			else{
				cxr[size-1-i]=(int)netRenderer.calculPerspX(p.x,p.z,p.y); 
				cyr[size-1-i]=(int)netRenderer.calculPerspY(p.x,p.z,p.y);
				czr[size-1-i]=0;
			}*/
		}



		Polygon poly=new Polygon(cxr,cyr,size);

		
        return poly;

	}

	public int calcAssY(double x, double y, double z) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int calcAssX(double x, double y, double z) {
		// TODO Auto-generated method stub
		return 0;
	}


}
