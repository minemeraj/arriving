package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;



/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ObjectEditorLeftRightPanel extends ObjectEditorPanel {




	int NX=4;
	int NY=0;

	int MOVX=0;
	int MOVY=0;
	
	static int y0=250;
	static int x0=350;

	int dx=2;
	int dy=2;

	double deltay=0.5;
	double deltax=0.5;


	public ObjectEditorLeftRightPanel(ObjectEditor oe){

	    super(oe);
		this.oe=oe;
		

		buildRightPanel();
		buildBottomPanel();

		
		initialize();


	}



	public void displayAll() {


		BufferedImage buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

		Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
        

		draw2Daxis(bufGraphics,WIDTH,HEIGHT);
		displayPoints(bufGraphics);
		displayLines(bufGraphics);
		displayCurrentRect(bufGraphics);
		
		if(g2==null)
			g2=(Graphics2D) getGraphics();
		
		g2.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		resetLists();

	}


	public void draw2Daxis(Graphics2D graphics2D, int i, int j) {
		
		int length=60;
		graphics2D.setColor(Color.white);
		
		// z axis
        int x1=(int) (calcAssX(0,0,-length));
        int y1=(int)(calcAssY(0,0,-length));
        int x2=(int)(calcAssX(0,0,length));
        int y2=(int)(calcAssY(0,0,length));
	
        if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		 graphics2D.drawLine(x1,y1,x2,y2);
		
		//y axis
		 x1=(int)(calcAssX(0,-length,0));
		 y1=(int)(calcAssY(0,-length,0));
		 x2=(int)(calcAssX(0,length,0));
		 y2=(int)(calcAssY(0,length,0));
		 
		 if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		  graphics2D.drawLine(x1,y1,x2,y2);
		 
		
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





	private void displayLines(Graphics2D bufGraphics) {



		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();


			bufGraphics.setColor(Color.WHITE);


			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)oe.points[0].elementAt(ld.getIndex(j));
				Point3D p1=(Point3D)oe.points[0].elementAt(ld.getIndex((j+1)%ld.size()));


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			if(oe.jmt_show_normals.isSelected())
				showNormals(oe.points[0],ld,bufGraphics);

		}	

		for(int i=0;i<oe.lines[0].size();i++){

			LineData ld=(LineData) oe.lines[0].elementAt(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();

			if(!ld.isSelected())
				continue;

			bufGraphics.setColor(Color.RED);

			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)oe.points[0].elementAt(ld.getIndex(j));
				Point3D p1=(Point3D)oe.points[0].elementAt(ld.getIndex((j+1)%ld.size()));


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			

		}	

	}








	private void showNormals(Vector points, LineData ld, Graphics2D bufGraphics) {
		
		Polygon3D p3d=new Polygon3D();
		int numLInes=ld.size();
		
		if(numLInes<3)
			return;
		
		for(int j=0;j<numLInes;j++){

			Point3D p0=(Point3D)oe.points[0].elementAt(ld.getIndex(j));
			p3d.addPoint(p0);
		}
		
		Point3D normal = Polygon3D.findNormal(p3d).calculateVersor();
		normal=normal.multiply(50.0);
		Point3D centroid=Polygon3D.findCentroid(p3d);
		Point3D normalTip=centroid.sum(normal);
		bufGraphics.setColor(Color.GREEN);
		bufGraphics.drawLine(calcAssX(centroid),calcAssY(centroid),calcAssX(normalTip),calcAssY(normalTip));
		
	}

	private void displayPoints(Graphics2D bufGraphics) {

		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);

			if(p.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.white);

			//TOP
			bufGraphics.fillOval(calcAssX(p)-2,calcAssY(p)-2,5,5);
		


		}



	}





	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}


	private void buildBottomPanel() {
		bottom=new JPanel();
		bottom.setBounds(0,HEIGHT,WIDTH+RIGHT_BORDER,BOTTOM_BORDER);
		bottom.setLayout(null);
		JLabel lscreenpoint = new JLabel();
		lscreenpoint.setText("Position x,y,z: ");
		lscreenpoint.setBounds(2,2,100,20);
		bottom.add(lscreenpoint);
		screenPoint=new JLabel();
		screenPoint.setText(",");
		screenPoint.setBounds(120,2,300,20);
		bottom.add(screenPoint);
		add(bottom);
	}

	private void buildRightPanel() {
		
		String header="<html><body>";
		String footer="</body></html>";

		right=new JPanel();
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		right.setLayout(null);

		int r=5;

		JLabel lx=new JLabel("x:");
		lx.setBounds(5,r,20,20);
		right.add(lx);
		coordinatesx=new DoubleTextField(8);
		coordinatesx.setBounds(30,r,150,20);
		coordinatesx.addKeyListener(this);
		right.add(coordinatesx);
		checkCoordinatesx=new JCheckBox();
		checkCoordinatesx.setBounds(200,r,30,20);
		checkCoordinatesx.addKeyListener(this);
		right.add(checkCoordinatesx);
	
	

		r+=30;

		JLabel ly=new JLabel("y:");
		ly.setBounds(5,r,20,20);
		right.add(ly);
		coordinatesy=new DoubleTextField(8);
		coordinatesy.setBounds(30,r,150,20);
		coordinatesy.addKeyListener(this);
		right.add(coordinatesy);
		checkCoordinatesy=new JCheckBox();
		checkCoordinatesy.setBounds(200,r,30,20);
		checkCoordinatesy.addKeyListener(this);
		right.add(checkCoordinatesy);
		

		r+=30;

		JLabel lz=new JLabel("z:");
		lz.setBounds(5,r,20,20);
		right.add(lz);
		coordinatesz=new DoubleTextField(8);
		coordinatesz.setBounds(30,r,150,20);
		coordinatesz.addKeyListener(this);
		right.add(coordinatesz);
		checkCoordinatesz=new JCheckBox();
		checkCoordinatesz.setBounds(200,r,30,20);
		checkCoordinatesz.addKeyListener(this);
		right.add(checkCoordinatesz);


		r+=30;
		
		JLabel ed=new JLabel("Ex:");
		ed.setBounds(5,r,20,20);
		right.add(ed);
		extraData=new JTextField();
		extraData.setBounds(30,r,150,20);
		extraData.addKeyListener(this);
		extraData.setToolTipText("Optional extra value");
		right.add(extraData);
		checkExtraData=new JCheckBox();
		checkExtraData.setBounds(200,r,30,20);
		checkExtraData.addKeyListener(this);
		right.add(checkExtraData);

		r+=30;

		JLabel lmul=new JLabel("Multiple selection:");
		lmul.setBounds(5,r,150,20);
		right.add(lmul);		
		checkMultipleSelection=new JCheckBox();
		checkMultipleSelection.setSelected(true);
		checkMultipleSelection.addKeyListener(this);
		checkMultipleSelection.setBounds(160,r,20,20);
		right.add(checkMultipleSelection);




		r+=30;

		addPoint=new JButton(header+"<u>I</u>nsert point"+footer);
		addPoint.addKeyListener(this);
		addPoint.addActionListener(this);
		addPoint.setFocusable(false);
		addPoint.setBounds(5,r,150,20);
		right.add(addPoint);
		
		rescale=new JButton(header+"<u>R</u>escale all"+footer);
		rescale.addKeyListener(this);
		rescale.addActionListener(this);
		rescale.setFocusable(false);
		rescale.setBounds(160,r,150,20);
		right.add(rescale);
		
		rescaleValue=new DoubleTextField();
		rescaleValue.setBounds(200,r+30,70,20);
		rescaleValue.addKeyListener(this);
		rescaleValue.setToolTipText("Scale factor");
		right.add(rescaleValue);

		r+=30;

		startBuildPolygon=new JButton(header+"Start polygo<u>n</u> <br/> points sequence"+footer);
		startBuildPolygon.addActionListener(this);
		startBuildPolygon.addKeyListener(this);
		startBuildPolygon.setFocusable(false);
		startBuildPolygon.setBounds(5,r,150,35);
		right.add(startBuildPolygon);

		r+=45;
			
		buildPolygon=new JButton(header+"Build <u>p</u>olygon"+footer);
		buildPolygon.addActionListener(this);
		buildPolygon.addKeyListener(this);
		buildPolygon.setFocusable(false);
		buildPolygon.setBounds(5,r,150,20);
		right.add(buildPolygon);
		
		r+=30;
		
		polygonDetail=new JButton(header+"Polygon detail"+footer);
		polygonDetail.addActionListener(this);
		polygonDetail.addKeyListener(this);
		polygonDetail.setFocusable(false);
		polygonDetail.setBounds(5,r,150,20);
		right.add(polygonDetail);
		
		JPanel movePanel=buildPointsMovePanel(185,r);
		right.add(movePanel);

		r+=30;

		deleteSelection=new JButton(header+"<u>D</u>elete selection"+footer);
		deleteSelection.addActionListener(this);
		deleteSelection.addKeyListener(this);
		deleteSelection.setFocusable(false);
		deleteSelection.setBounds(5,r,150,20);
		right.add(deleteSelection);



		r+=30;

		changePoint=new JButton(header+"<u>C</u>hange sel Point"+footer);
		changePoint.addActionListener(this);
		changePoint.addKeyListener(this);
		changePoint.setFocusable(false);
		changePoint.setBounds(5,r,150,20);
		right.add(changePoint);


		r+=30;

		deselectAll=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAll.addActionListener(this);
		deselectAll.addKeyListener(this);
		deselectAll.setFocusable(false);
		deselectAll.setBounds(5,r,100,20);
		right.add(deselectAll);
		
		r+=30;
		
		selectAll=new JButton(header+"Selec<u>t</u> all"+footer);
		selectAll.addActionListener(this);
		selectAll.addKeyListener(this);
		selectAll.setFocusable(false);
		selectAll.setBounds(5,r,100,20);
		right.add(selectAll);
		
		mergeSelectedPoints=new JButton(header+"<u>M</u>erge selected<br/>points"+footer);
		mergeSelectedPoints.addActionListener(this);
		mergeSelectedPoints.addKeyListener(this);
		mergeSelectedPoints.setFocusable(false);
		mergeSelectedPoints.setBounds(160,r,150,35);
		right.add(mergeSelectedPoints);

		r+=35;
	
		right.add(buildLowerRightPanel(r));

		add(right);

 
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

	private Component buildLowerRightPanel(int r) {
		pointList=new JList();
        JScrollPane jsp=new JScrollPane(pointList);
        JPanel lowpane=new JPanel();
        lowpane.setBounds(5,r,300,220);        
		right.add(lowpane);
		
		lowpane.setLayout(null);
		JLabel jlb=new JLabel("Points:");
		jlb.setBounds(20,0,100,20);
		lowpane.add(jlb);
		jsp.setBounds(0,25,140,180);
		lowpane.add(jsp);

		pointList.addMouseListener(new MouseAdapter(){
			
			
			public void mouseClicked(MouseEvent e){

				if(!checkMultipleSelection.isSelected()) 
					polygon=new LineData();
				
				int index=pointList.getSelectedIndex();
				for(int i=0;i<oe.points[0].size();i++){

					Point3D p=(Point3D) oe.points[0].elementAt(i);
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
				for(int i=0;i<oe.lines[0].size();i++){

					LineData ld=(LineData) oe.lines[0].elementAt(i);
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
		
		return lowpane;
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

	public void selectPoint(int x, int y) {
		
		boolean found=false;

		//select point from lines
		if(!checkMultipleSelection.isSelected()) 
			polygon=new LineData();
		for(int i=0;i<oe.points[0].size();i++){

			Point3D p=(Point3D) oe.points[0].elementAt(i);



			int xo=calcAssX(p);
			int yo=calcAssY(p);

	
			Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
			if(rect.contains(x,y)){

				selectPoint(p);

			    polygon.addIndex(i);
			    
			    found=true;

			}
			else if(!checkMultipleSelection.isSelected()) 
				p.setSelected(false);
		}
		
		if(!found)
			selectPolygon(x,y);

	}
	
	public double calculateScreenDistance(Polygon3D p3d, int x, int y) {

		Point3D centroid=Polygon3D.findCentroid(p3d);
		
		double xx=(cosf*(centroid.x)-sinf*(centroid.y));
		
		return xx;
	}
	
	public void selectPointsWithRectangle() {
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
        
        for (int i = 0; i < oe.points[0].size(); i++) {
        
    	Point3D p = (Point3D) oe.points[0].elementAt(i);


    	int x=calcAssX(p);
		int y=calcAssY(p);

			if(x>=x0 && x<=x1 && y>=y0 && y<=y1  ){

				p.setSelected(true);

			}

		}
		
	}

	
	public void zoomOut(){
		
		deltay=deltax=deltax*2;
	
		moveCenter(2.0);
	}
	
	public void zoomIn(){
		
		deltay=deltax=deltax/2;
	
		 moveCenter(0.5);
	}

    private void moveCenter(double d) {
		
    	
    	int dx=(int) ((getWidth()/2-x0)*(1-1.0/d));
		int dy=(int) ((getHeight()/2-y0)*(1-1.0/d));
		moveCenter(dx,dy);
		
	}



	public void moveCenter(int dx, int dy) {
    
    	x0+=dx;
    	y0+=dy;
    }
	
	public int calcAssX(double x, double y, double z) {
		 

		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		
		return (int) (yy/deltax+x0);
	}
	
	public int calcAssY(double x, double y, double z) {
		
		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		 
		return HEIGHT-(int) (zz/deltay+y0);
	}
	

	
	private int calcAssX(Point3D p) {
		 
		return calcAssX(p.x,p.y,p.z);
	}
	
	private int calcAssY(Point3D p) {
		 
		return  calcAssY(p.x,p.y,p.z);
	}

	public void right() {
		
		
		x0=x0-5;
		displayAll();
		
	}

	public void left() {
		x0=x0+5;
		displayAll();
		
	}

	public void up(){
		y0=y0+2;
		displayAll();

	}

	public void down(){
		
		y0=y0-2;
		
		displayAll();

	}
	

		
	public void checkNormals(){ 
		
		for(int i=0;i<oe.lines[0].size();i++){
			LineData ld=(LineData) oe.lines[0].elementAt(i);
			Polygon3D pol=getBodyPolygon(ld);
			//System.out.println(i+": "+Road.findBoxFace(pol));
		}
		
		
	}

	public Polygon3D getBodyPolygon(LineData ld) {
		
		Polygon3D pol=new Polygon3D(ld.size());
		
		for(int i=0;i<ld.size();i++){
			int index=ld.getIndex(i);
			Point3D p=(Point3D) oe.points[0].elementAt(index);			
			pol.xpoints[i]=(int) p.x;
			pol.ypoints[i]=(int) p.y;
			pol.zpoints[i]=(int) p.z;
		} 
		
		return pol;
		
	}


}
