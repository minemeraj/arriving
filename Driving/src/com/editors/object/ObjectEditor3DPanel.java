package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.DoubleTextField;
import com.main.AbstractRenderer3D;



/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ObjectEditor3DPanel extends ObjectEditorPanel implements AbstractRenderer3D{




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
	
	public BufferedImage buf=null;
	
	public static ZBuffer[] roadZbuffer;
	public int[] rgb;
	
	public double DEPTH_DISTANCE=1000;
	
	public double s2=Math.sqrt(2);
	public  Point3D pAsso;
	
	public double lightIntensity=1.0;
	
	
	public int greenRgb= Color.BLACK.getRGB();


	public ObjectEditor3DPanel(ObjectEditor oe){

	    super(oe);
		this.oe=oe;
		

		buildRightPanel();
		buildBottomPanel();

		
		initialize();


	}
	
	@Override
	public void initialize() {

		super.initialize();
		

		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		roadZbuffer=new ZBuffer[WIDTH*HEIGHT];
		pAsso=new Point3D(Math.cos(alfa)/s2,Math.sin(alfa)/s2,1/s2);
			
		buildNewZBuffers();
	}

	public void displayAll() {

		
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		if(oe.jmt_show_shading.isSelected()){
			
			buildShading(buf);
			
		}
		else{
			
			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			draw3Daxis(bufGraphics,WIDTH,HEIGHT);
			displayPoints(bufGraphics);
			displayLines(bufGraphics);
			displayCurrentRect(bufGraphics);
			
		}

		g2.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		resetLists();
		
		firePropertyChange("ObjectEditorUpdate",false,true);
	}
	

	/*private void buildShading(BufferedImage buf) {
		
		
		for(int i=0;i<oe.lines.size();i++){

			LineData ld=(LineData) oe.lines.elementAt(i);
			Polygon3D p3D=LineData.buildPolygon(ld,oe.points);
			decomposeClippedPolygonIntoZBuffer(p3D,Color.GRAY,null,roadZbuffer);
		}	
		
	

		buildScreen(buf); 
		
	}*/
	
	
	private void buildShading(BufferedImage buf) {
		
		Vector clonedPoints=clonePoints(oe.points);
		PolygonMesh pm=new PolygonMesh(clonedPoints,oe.lines);
		Vector polygons = PolygonMesh.getBodyPolygons(pm);
		
		for(int i=0;i<oe.lines.size();i++){

			LineData ld=(LineData) oe.lines.elementAt(i);
			Polygon3D p3D=LineData.buildPolygon(ld,clonedPoints);
			Color col=Color.GRAY;
			
			if(ld.isSelected)
				col=Color.RED;
			
			decomposeClippedPolygonIntoZBuffer(p3D,col,null,roadZbuffer);
		}	
		
		int length=60;
		
		Color clr= Color.WHITE;
		
		decomposeLine(0,0,0,length,0,0,roadZbuffer,clr.getRGB()); 
		decomposeLine(0,0,0,0,length,0,roadZbuffer,clr.getRGB()); 
		decomposeLine(0,0,0,0,0,length,roadZbuffer,clr.getRGB()); 

		for(int i=0;i<clonedPoints.size();i++){

			Point3D p=(Point3D) clonedPoints.elementAt(i);
			
			Color col=Color.white;

			if(p.isSelected())
				col=Color.RED;

			//TOP
			decomposePointIntoZBfuffer(p,2,col,roadZbuffer);

		}

		buildScreen(buf); 
		
	}
	
	private void decomposePointIntoZBfuffer(Point3D p, int radius, Color col, ZBuffer[] zbuffer) {
		
				
				double x=calcProspX(p);
				double y=calcProspY(p);
				
				for(int k=-radius;k<=radius;k++){
					
					for(int l=-radius;l<=radius;l++){
						
						
						int i=(int) x+k;
						int j=(int) y+l;
						
						if(i<0 || i>=WIDTH || j<0 || j>= HEIGHT)
							return;
						
						int tot=WIDTH*j+i;
						//System.out.println(x+" "+y+" "+tot);
		
						ZBuffer zb=zbuffer[tot];
		
						zb.update(p.x,DEPTH_DISTANCE-p.y-radius,p.z,col.getRGB());
						
					}

				}
		
	}

	public Vector clonePoints(Vector points) {
		
		Vector clonePoints=new Vector();
		
		for (int i = 0; i < points.size(); i++) {
			
			Point3D p = (Point3D) points.elementAt(i);
			
		
			double xx=(cosf*(p.x)-sinf*(p.y));
			double yy=(cosf*(p.y)+sinf*(p.x));
			double zz=p.z;
			
		
			double xxx=(zz)*sinteta+(xx)*costeta;
			double yyy= yy;
			double zzz=(zz)*costeta-(xx)*sinteta;
			
			Point3D pp=new Point3D(xxx,yyy,zzz);
			pp.setSelected(p.isSelected());
			
			clonePoints.add(pp);

			
		}
		
	
		
		return clonePoints;
	
	}

	public void draw3Daxis(Graphics2D graphics2D, int i, int j) {
		
		int length=60;
		graphics2D.setColor(Color.white);
		
		// x axis
        int x1=(int) (calcAssX(-length,0,0));
        int y1=(int)(calcAssY(-length,0,0));
        int x2=(int)(calcAssX(length,0,0));
        int y2=(int)(calcAssY(length,0,0));
	
        if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		 graphics2D.drawLine(x1,y1,x2,y2);
		
		//y axis
		 x1=(int)(calcAssX(0,-length,0));
		 y1=(int)(calcAssY(0,-length,0));
		 x2=(int)(calcAssX(0,length,0));
		 y2=(int)(calcAssY(0,length,0));
		 
		 if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		  graphics2D.drawLine(x1,y1,x2,y2);
		 
		 //z axis
		 x1=(int)(calcAssX(0,0,-length)); 
		 y1=(int)(calcAssY(0,0,-length));
		 x2=(int)(calcAssX(0,0,length));
		 y2=(int)(calcAssY(0,0,length));
	
		 if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		   graphics2D.drawLine(x1,y1,x2,y2);
	
	}

	public void resetLists() {
		
		DefaultListModel dlm=new DefaultListModel();
		int sel=pointList.getSelectedIndex();
		

		for(int i=0;i<oe.points.size();i++){

			Point3D p=(Point3D) oe.points.elementAt(i);
		    dlm.addElement(new PointListItem(p,i)) ; 
		}
		
		pointList.setModel(dlm);
		
		if(sel>=0 && sel<pointList.getModel().getSize())
			pointList.setSelectedIndex(sel);
		
		///////////////////////
		
		DefaultListModel dflm=new DefaultListModel();
		int selec=lineList.getSelectedIndex();
		

		for(int i=0;i<oe.lines.size();i++){

			LineData ld=(LineData) oe.lines.elementAt(i);
			dflm.addElement(ld) ; 
		}
		
		lineList.setModel(dflm);
		
		if(selec>=0 && selec<lineList.getModel().getSize())
			lineList.setSelectedIndex(selec);
		
		
	}





	private void displayLines(Graphics2D bufGraphics) {



		for(int i=0;i<oe.lines.size();i++){

			LineData ld=(LineData) oe.lines.elementAt(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();


			bufGraphics.setColor(Color.WHITE);


			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)oe.points.elementAt(ld.getIndex(j));
				Point3D p1=(Point3D)oe.points.elementAt(ld.getIndex((j+1)%ld.size()));


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			if(oe.jmt_show_normals.isSelected())
				showNormals(oe.points,ld,bufGraphics);

		}	

		for(int i=0;i<oe.lines.size();i++){

			LineData ld=(LineData) oe.lines.elementAt(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();

			if(!ld.isSelected())
				continue;

			bufGraphics.setColor(Color.RED);

			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)oe.points.elementAt(ld.getIndex(j));
				Point3D p1=(Point3D)oe.points.elementAt(ld.getIndex((j+1)%ld.size()));


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

			Point3D p0=(Point3D)oe.points.elementAt(ld.getIndex(j));
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

		for(int i=0;i<oe.points.size();i++){

			Point3D p=(Point3D) oe.points.elementAt(i);

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

		/*joinPoints=new JButton(header+"<u>J</u>oin sel points"+footer);
		joinPoints.addActionListener(this);
		joinPoints.addKeyListener(this);
		joinPoints.setFocusable(false);
		joinPoints.setBounds(5,r,150,20);
		right.add(joinPoints);*/

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
				for(int i=0;i<oe.points.size();i++){

					Point3D p=(Point3D) oe.points.elementAt(i);
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
				for(int i=0;i<oe.lines.size();i++){

					LineData ld=(LineData) oe.lines.elementAt(i);
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

		//select point from lines
		if(!checkMultipleSelection.isSelected()) 
			polygon=new LineData();
		
		boolean found=false;
		
		for(int i=0;i<oe.points.size();i++){

			Point3D p=(Point3D) oe.points.elementAt(i);



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
	
	
	public double calculateScreenDistance(Polygon3D p3d, int xp, int yp) { 
		
		//calculate screen projection of each face
		


		//calculate the distance of the face in the selected point
		
		Point3D centroid=Polygon3D.findCentroid(p3d);
		
		double xx=(cosf*(centroid.x)-sinf*(centroid.y));
		double yy=(cosf*(centroid.y)+sinf*(centroid.x));
		double zz=centroid.z;
		
		double xxx=(zz)*sinteta+(xx)*costeta;

		
		return xxx;

	}
	

	
	

	public void selectPointsWithRectangle() {
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
        
        for (int i = 0; i < oe.points.size(); i++) {
        
    	Point3D p = (Point3D) oe.points.elementAt(i);


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
		
		//if(deltax==0.125)
		//	return;
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
		
		double xxx=(zz)*sinteta+(xx)*costeta;
		double yyy= yy;
		double zzz=(zz)*costeta-(xx)*sinteta;
		
		return (int) ((yyy-xxx*sinAlfa)/deltax+x0);
	}
	
	public int calcAssY(double x, double y, double z) {
		
		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		
		double xxx=(zz)*sinteta+(xx)*costeta;
		double yyy= yy;
		double zzz=(zz)*costeta-(xx)*sinteta;
		 
		return HEIGHT-(int) ((zzz-xxx*cosAlfa)/deltay+y0);
	}
	
	private double calcProspX(Point3D p) {
	
		return (int) ((p.y-p.x*sinAlfa)/deltax+x0);
	}

	private double calcProspY(Point3D p) {
		
		return HEIGHT-(int)((p.z-p.x*cosAlfa)/deltay+y0);
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
		
		for(int i=0;i<oe.lines.size();i++){
			LineData ld=(LineData) oe.lines.elementAt(i);
			Polygon3D pol=getBodyPolygon(ld);
			//System.out.println(i+": "+Road.findBoxFace(pol));
		}
		
		
	}

	public Polygon3D getBodyPolygon(LineData ld) {
		
		Polygon3D pol=new Polygon3D(ld.size());
		
		for(int i=0;i<ld.size();i++){
			int index=ld.getIndex(i);
			Point3D p=(Point3D) oe.points.elementAt(index);			
			pol.xpoints[i]=(int) p.x;
			pol.ypoints[i]=(int) p.y;
			pol.zpoints[i]=(int) p.z;
		} 
		
		return pol;
		
	}


	

	@Override
	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d, Color color,
			Texture texture, ZBuffer[] zbuffer) {
		
	  	Point3D origin=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
    	decomposeClippedPolygonIntoZBuffer(p3d, color, texture,zbuffer,null,null,origin,0,0);
		
	}

	@Override
	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d, Color color,
			Texture texture, ZBuffer[] zbuffer, Point3D xDirection,
			Point3D yDirection, Point3D origin, int deltaX, int deltaY) {

    	//avoid clipping:
    	Polygon3D clippedPolygon=p3d;//Polygon3D.clipPolygon3DInY(p3d,0);

    	Polygon3D[] triangles = Polygon3D.divideIntoTriangles(clippedPolygon);

    	double cosin=0;

    	if(texture!=null && xDirection==null && yDirection==null){

    		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
    		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);

    		xDirection=(p1.substract(p0)).calculateVersor();
    		Point3D normal=Polygon3D.findNormal(p3d);
    		yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

    		//yDirection=Point3D.calculateOrthogonal(xDirection);
    	}
    	else{
    		
    		Point3D normal=Polygon3D.findNormal(p3d);
    		cosin=Point3D.calculateCosin(normal,pAsso);
    		
    	}
    	
    	

    	for(int i=0;i<triangles.length;i++){

    		decomposeTriangleIntoZBufferEdgeWalking( triangles[i],calculateShadowColor(cosin,color.getRGB()), texture,zbuffer, xDirection,yDirection,origin, deltaX, deltaY);

    	}

    }
	
	public int calculateShadowColor(double cosin, int argbs) {

		if(false) 
			return argbs;
		else{

			double factor=(lightIntensity*(0.75+0.25*cosin));

			int alphas=0xff & (argbs>>24);
			int rs = 0xff & (argbs>>16);
			int gs = 0xff & (argbs >>8);
			int bs = 0xff & argbs;

			rs=(int) (factor*rs);
			gs=(int) (factor*gs);
			bs=(int) (factor*bs);

			return alphas <<24 | rs <<16 | gs <<8 | bs;
		}
	}

	@Override
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,
			int rgbColor, Texture texture, ZBuffer[] zbuffer,
			Point3D xDirection, Point3D yDirection, Point3D origin, int deltaX,
			int deltaY) {

		
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);
		
	    

		//System.out.println(p3d+" "+rgbColor);

		double x0=calcProspX(p0);
		double y0=calcProspY(p0);
		double z0=p0.z;


		double x1=calcProspX(p1);
		double y1=calcProspY(p1);
		double z1=p1.z;

		double x2=calcProspX(p2);
		double y2=calcProspY(p2);
		double z2=p2.z;
		
		Point3D[] points=new Point3D[3];
		
		points[0]=new Point3D(x0,y0,z0,p0.x,p0.y,p0.z);
		points[1]=new Point3D(x1,y1,z1,p1.x,p1.y,p1.z);
		points[2]=new Point3D(x2,y2,z2,p2.x,p2.y,p2.z);
		
		
		int upper=0;
		int middle=1;
		int lower=2;
		
		for(int i=0;i<3;i++){
			
			if(points[i].y>points[upper].y)
				upper=i;
			
			if(points[i].y<points[lower].y)
				lower=i;
			
		}
		for(int i=0;i<3;i++){
			
			if(i!=upper && i!=lower)
				middle=i;
		}
	
		
    	//double i_depth=1.0/zs;
    	//UPPER TRIANGLE
		
		double inv_up_lo_y=1.0/(points[upper].y-points[lower].y);
		double inv_up_mi_y=1.0/(points[upper].y-points[middle].y);
		double inv_lo_mi_y=1.0/(points[lower].y-points[middle].y);
		
    	for(int j=(int) points[middle].y;j<points[upper].y;j++){
    		
    		//if(j>258)
    		//	continue;

    		double middlex=Point3D.foundXIntersection(points[upper],points[lower],j);
    		double middlex2=Point3D.foundXIntersection(points[upper],points[middle],j);

    		double l1=(j-points[lower].y)*inv_up_lo_y;
			double l2=(j-points[middle].y)*inv_up_mi_y;
			double zs=l1*points[upper].p_z+(1-l1)*points[lower].p_z;
			double ze=l2*points[upper].p_z+(1-l2)*points[middle].p_z;
			double ys=l1*points[upper].p_y+(1-l1)*points[lower].p_y;
			double ye=l2*points[upper].p_y+(1-l2)*points[middle].p_y;
			double xs=l1*points[upper].p_x+(1-l1)*points[lower].p_x;
			double xe=l2*points[upper].p_x+(1-l2)*points[middle].p_x;
    		
			Point3D pstart=new Point3D(middlex,j,zs,xs,ys,zs);
    		Point3D pend=new Point3D(middlex2,j,ze,xe,ye,ze);
    		
    		if(pstart.x>pend.x){

    			Point3D swap= pend.clone();
    			pend= pstart.clone();
    			pstart=swap;

    		}
    		
    		int start=(int)pstart.x;
    		int end=(int)pend.x;
    		
    		double inverse=1.0/(end-start);
    		//double i_pstart_p_y=1.0/(SCREEN_DISTANCE+pstart.p_y);
    		//double i_end_p_y=1.0/(SCREEN_DISTANCE+pend.p_y);
    		
    		for(int i=start;i<end;i++){

    			if(i<0 || i>=WIDTH || j<0 || j>= HEIGHT)
    				continue;
               
    			double l=(i-start)*inverse;
    			
    			
    			double xi=(1-l)*pstart.p_x+l*pend.p_x;
    			double yi=(1-l)*pstart.p_y+l*pend.p_y;
    			double zi=(1-l)*pstart.p_z+l*pend.p_z;
    			
    			
    			if(texture!=null)
    			  rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY);
    			if(rgbColor==greenRgb)
    				continue;
    			int tot=WIDTH*j+i;
    			//System.out.println(x+" "+y+" "+tot);

    			ZBuffer zb=zbuffer[tot];

    			zb.update(xi,DEPTH_DISTANCE-yi,zi,rgbColor);
    			
    		} 


    	}
    
      	//LOWER TRIANGLE
    	for(int j=(int) points[lower].y;j<points[middle].y;j++){

    		double middlex=Point3D.foundXIntersection(points[upper],points[lower],j);
    		double middlex2=Point3D.foundXIntersection(points[lower],points[middle],j);

			double l1=(j-points[lower].y)*inv_up_lo_y;
			double l2=(j-points[middle].y)*inv_lo_mi_y;
			double zs=l1*points[upper].p_z+(1-l1)*points[lower].p_z;
			double ze=l2*points[lower].p_z+(1-l2)*points[middle].p_z;
			double ys=l1*points[upper].p_y+(1-l1)*points[lower].p_y;
			double ye=l2*points[lower].p_y+(1-l2)*points[middle].p_y;
			double xs=l1*points[upper].p_x+(1-l1)*points[lower].p_x;
			double xe=l2*points[lower].p_x+(1-l2)*points[middle].p_x;
    		
			Point3D pstart=new Point3D(middlex,j,zs,xs,ys,zs);
    		Point3D pend=new Point3D(middlex2,j,ze,xe,ye,ze);
    	
    		    		
       		if(pstart.x>pend.x){
       			
       		
    			Point3D swap= pend.clone();
    			pend= pstart.clone();
    			pstart=swap;

    		}
       	
    		int start=(int)pstart.x;
    		int end=(int)pend.x;
        		
    		double inverse=1.0/(end-start);
    		
    		//double i_pstart_p_y=1.0/(SCREEN_DISTANCE+pstart.p_y);
    		//double i_end_p_y=1.0/(SCREEN_DISTANCE+pend.p_y);
    		
    		for(int i=start;i<end;i++){
    			
    			if(i<0 || i>=WIDTH || j<0 || j>= HEIGHT)
    				continue;
    			
    			double l=(i-start)*inverse;
    			
    			double xi=(1-l)*pstart.p_x+l*pend.p_x;
    			double yi=(1-l)*pstart.p_y+l*pend.p_y;
    			double zi=(1-l)*pstart.p_z+l*pend.p_z;
    		
    			
    			if(texture!=null)
    			  rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY);
    			if(rgbColor==greenRgb)
    				continue;
    			int tot=WIDTH*j+i;
    			//System.out.println(x+" "+y+" "+tot);

    			ZBuffer zb=zbuffer[tot];

    			zb.update(xi,DEPTH_DISTANCE-yi,zi,rgbColor);
    			
    		}


    	}	




	}

	public void decomposeLine( double x1,
			double y1,double z1,double x2,double y2,double z2,ZBuffer[] zbuffer,int rgbColor) {

		int xx1=(int)calcAssX(x1,y1,z1);
		int yy1=(int)calcAssY(x1,y1,z1);

		int xx2=(int)calcAssX(x2,y2,z2);
		int yy2=(int)calcAssY(x2,y2,z2);



		if(yy1!=yy2){

			double i_yy=1.0/(yy2-yy1);

			if(yy2>yy1)

				for (int yy = yy1; yy <= yy2; yy++) { 
					
			

					double l=(yy-yy1)*i_yy;

					int xx=(int) (xx2*l+xx1*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);

					int tot=WIDTH*yy+xx;

					ZBuffer zb=zbuffer[tot];

					if(!zb.isToUpdate(yi))
						continue;			

					zb.set(xx,yi,yy,rgbColor);
				}
			else
				for (int yy = yy2; yy <= yy1; yy++) {

					double l=-(yy-yy2)*i_yy;

					int xx=(int) (xx1*l+xx2*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

	    			double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);

					int tot=WIDTH*yy+xx;

					ZBuffer zb=zbuffer[tot];

					if(!zb.isToUpdate(yi))
						continue;			


					zb.set(xx,yi,yy,rgbColor);
				}

		}
		else if(xx1!=xx2){
			
			double i_xx=1.0/(xx2-xx1);

			if(xx2>xx1)
				for (int xx = xx1; xx <= xx2; xx++) {

					double l=(xx-xx1)*i_xx;
					double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);

					int yy=(int) (yy2*l+yy1*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					int tot=WIDTH*yy+xx;
 
					ZBuffer zb=zbuffer[tot];

					if(!zb.isToUpdate(yi))
						continue;


					zb.set(xx,yi,yy,rgbColor);
				}
			else
				for (int xx = xx2; xx <= xx1; xx++) {

					double l=-(xx-xx2)*i_xx;
					double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);
 
					int yy=(int) (yy1*l+yy2*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					int tot=WIDTH*yy+xx;

					ZBuffer zb=zbuffer[tot];

					if(!zb.isToUpdate(yi))
						continue;


					zb.set(xx,yi,yy,rgbColor);
				}

		}
		else {
			
			if(xx1<0 || yy1<0 )
				return;
			
			if(xx1>=WIDTH || yy1>= HEIGHT)
				return;

			int tot=WIDTH*yy1+xx1;

			ZBuffer zb=zbuffer[tot];

			if(!zb.isToUpdate(DEPTH_DISTANCE+y1))
				return;


			zb.set(xx1,y1,yy1,rgbColor);

		}


	}
	

	@Override
	public void buildNewZBuffers() {
		
       
		
		for(int i=0;i<roadZbuffer.length;i++){
			
			roadZbuffer[i]=new ZBuffer(greenRgb,0);
			
			
		}
		 int lenght=roadZbuffer.length;
		 rgb = new int[lenght];	

				
	}

	@Override
	public void buildScreen(BufferedImage buf) {

		int length=rgb.length;
		
		for(int i=0;i<length;i++){
			   
			  
			 	   ZBuffer zb=roadZbuffer[i];
				   //set
			 	   rgb[i]=zb.getRgbColor(); 
				   //clean
				   zb.set(0,0,0,greenRgb);
				  
 
		 }	   
		 buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,rgb);    
	              

	}
}
