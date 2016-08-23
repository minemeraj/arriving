package com.editors.road;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Stack;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.LineData;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.ZBuffer;
import com.editors.ComboElement;
import com.editors.DoubleTextField;
import com.main.DrivingFrame;





class RoadAltimetryPanel extends JDialog implements KeyListener, PropertyChangeListener,MouseWheelListener,MouseListener,ActionListener, MouseMotionListener, MenuListener{


	private int WIDTH=800;
	private int BOTTOM_HEIGHT=100;
	private int RIGHT_BORDER=240;
	private int HEIGHT=500;
	private int LEFT_BORDER=0;
	private int BOTTOM_BORDER=100;

	private int dx=4;
	private int dy=4;

	private int MOVX=-50;
	private int MOVY=100;

	private RoadEditor roadEditor=null;

	private Graphics2D graphics2D;
	private BufferedImage buf=null;
	private JPanel center;
	private JPanel bottom;
	private JPanel right;
	private DoubleTextField peak;
	private JRadioButton mountainMode;
	private JRadioButton slopeMode;
	private JComboBox slopeModeType;
	private JButton deform;
	private JCheckBox checkMultiplePointsSelection;

	private Rectangle currentRect;
	private Graphics2D g2Alias;

	private String DIRECTION_NORTH="N";
	private String DIRECTION_SOUTH="S";
	private String DIRECTION_WEST="W";
	private String DIRECTION_EAST="E";
	private JRadioButton absoluteZ;
	private JRadioButton relativeZ;
	private JRadioButton planMode;
	private float[] groundColorArray;
	private DoubleTextField coordinatesx;
	private JCheckBox checkCoordinatesx;
	private DoubleTextField coordinatesy;
	private JCheckBox checkCoordinatesy;
	private DoubleTextField coordinatesz;
	private JCheckBox checkCoordinatesz;
	private JButton changePoint;

	private String header="<html><body>";
	private String footer="</body></html>";

	private JMenuItem jmtUndoLast;
	
	private Stack oldMeshes=new Stack();

	private final int MAX_STACK_SIZE=10;
	private JMenuBar jMenuBar;
	private JMenu jmUndo;
	private boolean redrawAfterMenu;
	private JLabel screenPoint;

	private PolygonMesh mesh;


	RoadAltimetryPanel(RoadEditor roadEditor) {
		super();
		
		this.mesh=roadEditor.getMeshes()[DrivingFrame.TERRAIN_INDEX];

		
		setTitle("Advanced Altimetry");
		setLayout(null);
		this.roadEditor=roadEditor;		
		

		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_HEIGHT);
		center=new JPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		addMouseWheelListener(this);
		bottom=new JPanel();
		bottom.setBounds(0,HEIGHT,WIDTH,BOTTOM_HEIGHT);



		addKeyListener(this);
		center.addMouseListener(this);
		center.addMouseMotionListener(this);
		addRightPanel();
		buildBottomPanel();
		//addPropertyChangeListener(this);//useless

		this.roadEditor=roadEditor;
		if(roadEditor!=null)
			roadEditor.addPropertyChangeListener(this);
		buildMenuBar();

		setVisible(true);
		initialize();
	}

	private void addRightPanel() {

		right=new JPanel();
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		right.setLayout(null);

		int r=10;

		checkMultiplePointsSelection=new JCheckBox("Multiple selection");
		checkMultiplePointsSelection.setBounds(10,r,150,20);
		checkMultiplePointsSelection.addKeyListener(this);
		right.add(checkMultiplePointsSelection);

		r+=30;

		JLabel jlb=new JLabel("Z peak:");
		jlb.setBounds(10,r,50,20);
		right.add(jlb);
		peak=new DoubleTextField(10);
		peak.setBounds(60,r,100,20);
		peak.addKeyListener(this);
		right.add(peak);

		r+=30;

		absoluteZ=new JRadioButton("Absolute Z");
		relativeZ=new JRadioButton("Relative Z");

		absoluteZ.setBounds(10,r,150,20);
		absoluteZ.setFocusable(false);
		right.add(absoluteZ);	

		r+=30;
		relativeZ.setBounds(10,r,150,20);
		relativeZ.setSelected(true);
		relativeZ.setFocusable(false);
		right.add(relativeZ);	

		ButtonGroup bgr=new ButtonGroup();
		bgr.add(absoluteZ);
		bgr.add(relativeZ);

		r+=30;

		jlb=new JLabel("Deformation mode:");
		jlb.setBounds(10,r,150,20);
		right.add(jlb);	

		r+=30;

		planMode=new JRadioButton("Plan");
		mountainMode=new JRadioButton("Mountain");
		slopeMode=new JRadioButton("Slope");

		planMode.setFocusable(false);
		mountainMode.setFocusable(false);
		slopeMode.setFocusable(false);

		ButtonGroup bg=new ButtonGroup();
		bg.add(mountainMode);
		bg.add(slopeMode);
		bg.add(planMode);

		planMode.setBounds(10,r,150,20);
		right.add(planMode);	
		planMode.setSelected(true);

		r+=30;

		mountainMode.setBounds(10,r,150,20);
		right.add(mountainMode);	


		r+=30;
		slopeMode.setBounds(10,r,150,20);
		right.add(slopeMode);	

		r+=30;
		slopeModeType=new JComboBox();
		Vector data=buildSlopeTypeVector();
		slopeModeType.setModel(new DefaultComboBoxModel(data));
		slopeModeType.setBounds(10,r,150,20);	
		right.add(slopeModeType);
		slopeModeType.setFocusable(false);

		r+=30;
		deform = new JButton("Deform");
		deform.setBounds(10,r,150,20);
		deform.setFocusable(false);
		deform.addKeyListener(this);
		deform.addActionListener(this);
		right.add(deform);

		r+=30;


		JLabel lx=new JLabel("x:");
		lx.setBounds(5,r,20,20);
		right.add(lx);
		coordinatesx=new DoubleTextField(8);
		coordinatesx.setBounds(30,r,120,20);
		coordinatesx.addKeyListener(this);
		right.add(coordinatesx);
		checkCoordinatesx=new JCheckBox();
		checkCoordinatesx.setBounds(180,r,30,20);
		checkCoordinatesx.addKeyListener(this);
		right.add(checkCoordinatesx);

		r+=30;

		JLabel ly=new JLabel("y:");
		ly.setBounds(5,r,20,20);
		right.add(ly);
		coordinatesy=new DoubleTextField(8);
		coordinatesy.setBounds(30,r,120,20);
		coordinatesy.addKeyListener(this);
		right.add(coordinatesy);
		checkCoordinatesy=new JCheckBox();
		checkCoordinatesy.setBounds(180,r,30,20);
		checkCoordinatesy.addKeyListener(this);
		right.add(checkCoordinatesy);

		r+=30;

		JLabel lz=new JLabel("z:");
		lz.setBounds(5,r,20,20);
		right.add(lz);
		coordinatesz=new DoubleTextField(8);
		coordinatesz.setBounds(30,r,120,20);
		coordinatesz.addKeyListener(this);
		right.add(coordinatesz);
		checkCoordinatesz=new JCheckBox();
		checkCoordinatesz.setBounds(180,r,30,20);
		checkCoordinatesz.addKeyListener(this);
		right.add(checkCoordinatesz);

		r+=30;

		changePoint=new JButton(header+"Change <u>P</u>oints"+footer);
		changePoint.addActionListener(this);
		changePoint.setFocusable(false);
		changePoint.setBounds(5,r,150,20);
		right.add(changePoint);

		r+=30;


		right.addKeyListener(this);

		add(right);

	}

	private void buildBottomPanel() {
		bottom=new JPanel();
		bottom.setBounds(0,HEIGHT,LEFT_BORDER+WIDTH+RIGHT_BORDER,BOTTOM_BORDER);
		bottom.setLayout(null);
		JLabel lscreenpoint = new JLabel();
		lscreenpoint.setText("Position x,y: ");
		lscreenpoint.setBounds(2,2,100,20);
		bottom.add(lscreenpoint);
		screenPoint=new JLabel();
		screenPoint.setText(",");
		screenPoint.setBounds(120,2,200,20);
		bottom.add(screenPoint);


		JLabel usage=new JLabel();
		usage.setText("Move with arrow keys,zoom with F1,F2");
		usage.setBounds(350,2,300,20);
		bottom.add(usage);
		add(bottom);

		add(bottom);
	}

	private Vector<ComboElement> buildSlopeTypeVector() {

		Vector<ComboElement> data=new Vector<ComboElement>();
		data.add(new ComboElement(DIRECTION_NORTH,"North Slope"));
		data.add(new ComboElement(DIRECTION_SOUTH,"South Slope"));
		data.add(new ComboElement(DIRECTION_WEST,"West Slope"));
		data.add(new ComboElement(DIRECTION_EAST,"East Slope"));
		return data;
	}

	private void buildMenuBar() {

		jMenuBar=new JMenuBar();


		jmUndo=new JMenu("Change");
		jmUndo.addMenuListener(this);
		jmtUndoLast = new JMenuItem("Undo last ");
		jmtUndoLast.setEnabled(false);
		jmtUndoLast.addActionListener(this);
		jmUndo.add(jmtUndoLast);	

		jMenuBar.add(jmUndo);


		setJMenuBar(jMenuBar);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawRoad();
	}

	private void drawRoad() {

		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		if(graphics2D==null)
			graphics2D=(Graphics2D) center.getGraphics();


		drawRoad(buf);


		graphics2D.drawImage(buf,0,0,WIDTH,HEIGHT,null);

	}

	private void drawRoad(BufferedImage buf) {

		Graphics2D g2 = (Graphics2D) buf.getGraphics();

		for(int j=0;j<mesh.polygonData.size();j++){


			LineData ld=(LineData) mesh.polygonData.get(j);

			drawPolygon(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,g2);

		} 

		g2.setColor(Color.WHITE);
		if(dx<16){
			for(int j=0;j<mesh.xpoints.length;j++){
	
	
				Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);
	
	
	
				if(mesh.selected[j])
					g2.setColor(Color.RED);
				else
					g2.setColor(Color.WHITE);
	
				double x=p.x;
				double y=p.y;
	
				int xp=convertX(x);
				int yp=convertY(y);
				//System.out.println(xp+" "+yp);
	
				if(xp>=0 && xp<WIDTH && yp>=0 && yp<HEIGHT)
					g2.fillOval(xp-2, yp-2,5,5);
	
			}
		}





	}

	private void drawPolygon(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints, Graphics2D bufGraphics) {
		


			bufGraphics.setColor(ZBuffer.fromHexToColor(ld.getHexColor()));

			int size=ld.size();

			int[] cxr=new int[size];
			int[] cyr=new int[size];
			int[] czr=new int[size];
			
			for(int i=0;i<size;i++){


				int num=ld.getIndex(i);

				Point4D p= new Point4D(xpoints[num],ypoints[num],zpoints[num]);



				//real coordinates
				cxr[i]=convertX(p.x);
				cyr[i]=convertY(p.y);
				czr[i]=(int) p.z;	
			
			}


			Polygon3D p_in=new Polygon3D(size,cxr,cyr,czr);

			Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));
			Area partialArea = clipPolygonToArea2D( p_in,totArea);

			if(partialArea.isEmpty())
				return;

			double z=calculateAverageZ(p_in);

			bufGraphics.setColor(calculateZColor(z));
			bufGraphics.fill(partialArea); 

	}

	private Color calculateZColor(double z) {




		//double h=groundColorArray[0];
		double alfa=1.0/500;
		int minh=10;
		int maxh=150;

		double h=((maxh-minh)*0.5*(1-Math.tanh(alfa*z))+minh)/255.0;


		return Color.getHSBColor( (float)h,  groundColorArray[1],   groundColorArray[2]);


	}

	private Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}

	private void deformTerrain() {

		prepareUndo();

		String speak=peak.getText();
		if("".equals(speak))
			return;

		double altitude=Double.parseDouble(speak);

		if(mountainMode.isSelected()){

			deformMountain(altitude);


		}
		else if(slopeMode.isSelected()){

			deformSlope(altitude);


		}
		else if(planMode.isSelected()){

			deformPlan(altitude);


		}

		drawRoad();
	}

	private void deformPlan(double altitude) {


		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

			if(mesh.selected[j]){

				if(relativeZ.isSelected())
					mesh.zpoints[j]+=altitude;
				else if(absoluteZ.isSelected())
					mesh.zpoints[j]=altitude;

			}	
		}
	}

	private void deformSlope(double altitude) {

		ComboElement ce=(ComboElement) slopeModeType.getSelectedItem();
		String direction=ce.getCode();  

		Bounds bounds=calculateSelectedBounds();

		double deltaX=(bounds.getMaxX()-bounds.getMinX());
		double deltaY=(bounds.getMaxY()-bounds.getMinY());	
		
	
		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);


			if(mesh.selected[j]){

				int q=1;

				if(relativeZ.isSelected())
					q=0;
				else if(absoluteZ.isSelected())
					q=1;

				if(direction.equals( DIRECTION_EAST) && bounds.getMaxX()!=bounds.getMinX()){

					mesh.zpoints[j]+=(altitude-q*p.z)*(p.x-bounds.getMinX())/deltaX;

				}
				else if(direction.equals(DIRECTION_WEST) && bounds.getMaxX()!=bounds.getMinX()){

					mesh.zpoints[j]+=(altitude-q*p.z)*(bounds.getMaxX()-p.x)/deltaX;

				}
				else if(direction.equals(DIRECTION_NORTH) && bounds.getMaxY()!=bounds.getMinY()){

					mesh.zpoints[j]+=(altitude-q*p.z)*(p.y-bounds.getMinY())/deltaY;

				}
				else if(direction.equals(DIRECTION_SOUTH) && bounds.getMaxY()!=bounds.getMinY()){

					mesh.zpoints[j]+=(altitude-q*p.z)*(bounds.getMaxY()-p.y)/deltaY;

				}
			}

		}	






	}

	private void changeSelectedRoadPoint() {

		prepareUndo();
		
	
		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

			if(mesh.selected[j]){

				if(!"".equals(coordinatesx.getText()))
					p.x=Double.parseDouble(coordinatesx.getText());
				if(!"".equals(coordinatesy.getText()))
					p.y=Double.parseDouble(coordinatesy.getText());
				if(!"".equals(coordinatesz.getText()))
					p.z=Double.parseDouble(coordinatesz.getText());

				mesh.selected[j]=false;



			}
		}	

		drawRoad();
	}

	private void deformMountain(double altitude) {

		Bounds bounds=calculateSelectedBounds();

		double centerX=(bounds.getMaxX()+bounds.getMinX())/2.0;
		double deltaX=(bounds.getMaxX()-bounds.getMinX())/2.0;

		double centerY=(bounds.getMaxY()+bounds.getMinY())/2.0;	
		double deltaY=(bounds.getMaxY()-bounds.getMinY())/2.0;	

		if(deltaX==0 || deltaY==0)
			return;

		double factorx=Math.PI/(2*deltaX);
		double factory=Math.PI/(2*deltaY);

		
		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);


			if(mesh.selected[j]){

				if(relativeZ.isSelected())
					mesh.zpoints[j]+=altitude*Math.cos(factorx*(p.x-centerX))*Math.cos(factory*(p.y-centerY));					
				else if(absoluteZ.isSelected())	
					mesh.zpoints[j]+=(altitude-p.z)*Math.cos(factorx*(p.x-centerX))*Math.cos(factory*(p.y-centerY));	


			}

		}	



	}

	private Bounds calculateSelectedBounds() {

		Bounds bounds=new Bounds();
		boolean first=true;

		
		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

			if(mesh.selected[j]){

				if(first){

					bounds=new Bounds(p.x,p.x,p.y,p.y);
					first=false;
				}	    	
				else{

					if(p.x>bounds.getMaxX())
						bounds.setMaxX(p.x);

					if(p.x<bounds.getMinX())
						bounds.setMinX(p.x);

					if(p.y>bounds.getMaxY())
						bounds.setMaxY(p.y);


					if(p.y<bounds.getMinY())
						bounds.setMinY(p.y);



				}


			}

		}

		//System.out.println(bounds);
		return bounds;
	}





	private int convertX(double i) {

		return (int) (i/dx-MOVX);
	}
	private int convertY(double j) {

		return (int) (HEIGHT-(j/dy+MOVY));
	}

	private int invertX(int i) {

		return (i+MOVX)*dx;
	}
	private int invertY(int j) {

		return dy*(HEIGHT-j-MOVY);
	}

	private void initialize() {

		g2Alias=(Graphics2D) center.getGraphics();
		g2Alias.setColor(Color.GRAY);
		Stroke stroke=new BasicStroke(0.1f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
		g2Alias.setStroke(stroke);

		groundColorArray=new float[3];
		Color.RGBtoHSB(12,200,73,groundColorArray);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object o=arg0.getSource();
		if(o==deform)
			deformTerrain();
		else if(o==changePoint){

			changeSelectedRoadPoint();
		}
		else if(o==jmtUndoLast){

			undoRoad();
		}


		deselectAll();

	}



	private void deselectAll() {



		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);


			mesh.selected[j]=false;




		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {


		int code =arg0.getKeyCode();
		if(code==KeyEvent.VK_DOWN )
			down();
		else if(code==KeyEvent.VK_UP  )
			up();
		else if(code==KeyEvent.VK_LEFT )
		{	MOVX=MOVX-10; 
		drawRoad();
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	MOVX=MOVX+10;   
		drawRoad();
		}
		else if(code==KeyEvent.VK_P  )
		{	
			changeSelectedRoadPoint();		
		}
		else if(code==KeyEvent.VK_F1  )
		{ 
			zoom(+1);
			drawRoad();
		}
		else if(code==KeyEvent.VK_F2  )
		{  zoom(-1);
		drawRoad();
		}

	}

	private void up(){
		MOVY=MOVY-10;
		drawRoad();

	}

	private void down(){
		MOVY=MOVY+10;
		drawRoad();

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
		int pix=arg0.getUnitsToScroll();
		if(pix<0) up();
		else down();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		selectPoint(arg0.getX(),arg0.getY());	
		drawRoad();

	}

	private void selectPoint(int x, int y) {

		//select point from road
		

		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

			int xo=convertX(p.x);
			int yo=convertY(p.y);
			//System.out.println(xo+" "+yo);

			Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
			if(rect.contains(x,y)){


				mesh.selected[j]=true;

				if(!checkCoordinatesx.isSelected())
					coordinatesx.setText(p.x);
				if(!checkCoordinatesy.isSelected())
					coordinatesy.setText(p.y);
				if(!checkCoordinatesz.isSelected())
					coordinatesz.setText(p.z);

			}
			else if(!checkMultiplePointsSelection.isSelected())
				mesh.selected[j]=false;


		}
	}	

	private void clean() {

		if(!checkCoordinatesx.isSelected())
			coordinatesx.setText("");
		if(!checkCoordinatesy.isSelected())
			coordinatesy.setText("");
		if(!checkCoordinatesz.isSelected())
			coordinatesz.setText("");


	}

	private void zoom(int i) {

		double alfa=1.0;
		if(i>0){
			alfa=0.5;
		}
		else {
			alfa=2.0;

		}

		dx=(int) (dx*alfa);
		dy=(int) (dy*alfa);

		MOVX+=(int) ((WIDTH/2+MOVX)*(1.0/alfa-1.0));
		MOVY+=(int) ((-HEIGHT/2+MOVY)*(1.0/alfa-1.0));
	}

	private void prepareUndo() {

		prepareUndoRoad();
	}

	private void prepareUndoRoad() {
		jmtUndoLast.setEnabled(true);
		if(oldMeshes.size()==MAX_STACK_SIZE){

			oldMeshes.removeElementAt(0);

		}
		oldMeshes.push(mesh.clone());
	

	}




	private void undoRoad() {

		mesh= (PolygonMesh) oldMeshes.pop();
	


		if(oldMeshes.size()==0)
			jmtUndoLast.setEnabled(false);


		roadEditor.setRoadData("RoadAltimetryUndo",mesh); 



	}
	@Override
	public void mouseDragged(MouseEvent e) {
		updateSize(e);

		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		g2Alias.drawRect(x0,y0,x1-x0,y1-y0);


	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		updateSize(arg0);
		selectPointsWithRectangle();
		drawRoad();

	}

	private void selectPointsWithRectangle() {

		clean();

		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);

	
		for(int j=0;j<mesh.xpoints.length;j++){


			Point4D p=new Point4D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

			int xo=convertX(p.x);
			int yo=convertY(p.y);


			if(xo>=x0 && xo<=x1 && yo>=y0 && yo<=y1  ){

				mesh.selected[j]=true;


			}
			else if(!checkMultiplePointsSelection.isSelected())
				mesh.selected[j]=false;


		}


	}

	private void updateSize(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		currentRect.setSize(x - currentRect.x,
				y - currentRect.y);
		drawRoad();


	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		int x = arg0.getX();
		int y = arg0.getY();
		currentRect = new Rectangle(x, y, 0, 0);

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

		Point p=arg0.getPoint();
		screenPoint.setText(invertX((int)p.getX())+","+invertY((int)p.getY()));



	}

	private double calculateAverageZ(Polygon3D p3d) {


		double z=0;
		for (int i = 0; i < p3d.npoints; i++) {

			z+=p3d.zpoints[i];
		}

		return z/p3d.npoints;

	}




	@Override
	public void propertyChange(PropertyChangeEvent arg0) {		

		if("paintDirtyRegions".equals(arg0.getPropertyName()) && redrawAfterMenu)
		{
			drawRoad();
			redrawAfterMenu=false;
		}
		else if("RoadEditorUndo".equals(arg0.getPropertyName())

				|| "RoadEditorUpdate".equals(arg0.getPropertyName())
				)
		{

			this.mesh=roadEditor.getMeshes()[roadEditor.getACTIVE_PANEL()];
			drawRoad();
		}

	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void menuDeselected(MenuEvent e) {
		redrawAfterMenu=true;

	}
	@Override
	public void menuSelected(MenuEvent e) {
		redrawAfterMenu=false;	

	}
	
	private class Bounds{
		
		private double maxX=0;
		private double maxY=0;
		private double minY=0;
		private double minX=0;
		
		public Bounds( double minX,double maxX, double minY, double maxY) {
			super();
			this.maxX = maxX;
			this.minX = minX;
			this.minY = minY;
			this.maxY = maxY;
		}
		
		public Bounds() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			String str="\nmaxX="+maxX+
					"\nminX="+minX+
					"\nminY="+minY+
					"\nmaxY="+maxY;

			return str;
		}

		public double getMaxX() {

			return maxX;
		}

		public double getMaxY() {

			return maxY;
		}

		public double getMinX() {

			return minX;
		}

		public double getMinY() {

			return minY;
		}

		public void setMaxX(double maxX) {
			this.maxX = maxX;
		}

		public void setMaxY(double maxY) {
			this.maxY = maxY;
		}

		public void setMinY(double minY) {
			this.minY = minY;
		}

		public void setMinX(double minX) {
			this.minX = minX;
		}
		
	}


}
