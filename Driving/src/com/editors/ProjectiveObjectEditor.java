package com.editors;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
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
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.AbstractButton;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.Point3D;
import com.Texture;



/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ProjectiveObjectEditor extends JFrame implements MenuListener,ActionListener,MouseListener,MouseWheelListener,MouseMotionListener,KeyListener, ItemListener{


	private JPanel center;
	int HEIGHT=550;
	int WIDTH=600;

	int FRONT_VIEW_HEIGHT=300;
	int FRONT_VIEW_WIDTH=300;

	int TOP_VIEW_HEIGHT=HEIGHT-FRONT_VIEW_HEIGHT;
	int TOP_VIEW_WIDTH=300;
	
	int y0Front=50;
	int z0Front=10;
	int x0Top=10;
	int y0Top=y0Front;
	int x0Side=x0Top;
	int z0Side=z0Front;
	

	int RIGHT_BORDER=320;
	int BOTTOM_BORDER=100;
	int NX=4;
	int NY=0;

	int MOVX=0;
	int MOVY=0;

	int dx=2;
	int dy=2;

	int deltay=30;
	int deltax=150;

	Graphics2D g2;

	ArrayList points=new ArrayList();
	ArrayList lines=new ArrayList();

	private JFileChooser fc;
	private JMenuBar jmb;
	private JMenu jm;
	private JPanel right;
	private JTextField coordinatesx;
	private JTextField coordinatesy;
	private JTextField coordinatesz;

	private JButton addPoint;
	private JButton deleteSelection;
	private JButton changePoint;
	private JButton deselectAll;;
	private JCheckBox checkMultipleSelection;
	private JList pointList=null;


	private JButton joinPoints;
	private JButton buildPolygon;
	
	private boolean redrawAfterMenu=false;
	private AbstractButton jmt12;
	private JMenuItem jmt21;
	private JMenu jm2;

	public boolean ISDEBUG=false;
	private JPanel bottom;
	private JLabel screenPoint;
	private JCheckBox checkCoordinatesx;
	private JCheckBox checkCoordinatesz;
	private JCheckBox checkCoordinatesy;


	boolean isUseTextures=true;

	private JList lineList;
	private JButton selectAll;
	private LineData polygon=new LineData();


	

	public static Texture[] worldTextures;
	public static BufferedImage[] worldImages;


	public static Color BACKGROUND_COLOR=new Color(0,0,0);

	public static void main(String[] args) {

		ProjectiveObjectEditor re=new ProjectiveObjectEditor();
		re.initialize();
	}

	public ProjectiveObjectEditor(){

		super("Object editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		center=new JPanel();
		center.setBackground(BACKGROUND_COLOR);
		center.setBounds(0,0,WIDTH,HEIGHT);
		center.addMouseListener(this);
		center.addMouseWheelListener(this);
		center.addMouseMotionListener(this);
		addKeyListener(this);
		add(center);
		buildMenuBar();
		buildRightPanel();
		buildBottomPanel();

		RepaintManager.setCurrentManager( 
				new RepaintManager(){

					@Override
					public void paintDirtyRegions() {


						super.paintDirtyRegions();
						if(redrawAfterMenu ) {redrawAfterMenu=false;}
					}

				}				
		);

		setVisible(true);


	}

	/**
	 * 
	 */
	public void initialize() {


		g2=(Graphics2D) center.getGraphics();
		
		

	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		displayAll();
	}

	private void displayAll() {


		BufferedImage buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

		Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();


		drawSeparators(bufGraphics);
		displayPoints(bufGraphics);
		displayLines(bufGraphics);

		g2.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		
		resetLists();

	}


	private void resetLists() {
		
		DefaultListModel dlm=new DefaultListModel();
		int sel=pointList.getSelectedIndex();
		

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);
		    dlm.addElement(new PointListItem(p)) ; 
		}
		
		pointList.setModel(dlm);
		
		if(sel>=0 && sel<pointList.getModel().getSize())
			pointList.setSelectedIndex(sel);
		
		///////////////////////
		
		DefaultListModel dflm=new DefaultListModel();
		int selec=lineList.getSelectedIndex();
		

		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.get(i);
			dflm.addElement(ld) ; 
		}
		
		lineList.setModel(dflm);
		
		if(selec>=0 && selec<lineList.getModel().getSize())
			lineList.setSelectedIndex(selec);
		
		
	}

	private void drawSeparators(Graphics2D bufGraphics) {
		
		bufGraphics.drawLine(0,FRONT_VIEW_HEIGHT,WIDTH,FRONT_VIEW_HEIGHT);
		bufGraphics.drawLine(FRONT_VIEW_WIDTH,0,FRONT_VIEW_WIDTH,HEIGHT);
		
		bufGraphics.setColor(Color.blue);
		
		//x axes
		bufGraphics.drawLine(y0Front,FRONT_VIEW_HEIGHT-z0Front,FRONT_VIEW_WIDTH,FRONT_VIEW_HEIGHT-z0Front);
		bufGraphics.drawLine(y0Top,FRONT_VIEW_HEIGHT+x0Top,FRONT_VIEW_WIDTH,FRONT_VIEW_HEIGHT+x0Top);
		bufGraphics.drawLine(FRONT_VIEW_WIDTH+x0Side,FRONT_VIEW_HEIGHT-z0Front,WIDTH,FRONT_VIEW_HEIGHT-z0Front);
		
		//y axes
		bufGraphics.drawLine(y0Front,0,y0Front,FRONT_VIEW_HEIGHT-z0Front);
		bufGraphics.drawLine(y0Front,FRONT_VIEW_HEIGHT+x0Top,y0Front,HEIGHT);
		bufGraphics.drawLine(FRONT_VIEW_WIDTH+x0Side,0,FRONT_VIEW_WIDTH+x0Side,FRONT_VIEW_HEIGHT-z0Front);
	}

	public boolean isFRONT(int x,int y){

		if(x>=0 && x<FRONT_VIEW_WIDTH && y<FRONT_VIEW_HEIGHT) 
			return true;
		else
			return false;

	}

	public boolean isTOP(int x,int y){

		if(x>=0 && x<FRONT_VIEW_WIDTH && y>FRONT_VIEW_HEIGHT) 
			return true;
		else
			return false;

	}

	public boolean isSIDE(int x,int y){

		if(x>FRONT_VIEW_WIDTH && y<FRONT_VIEW_HEIGHT) 
			return true;
		else
			return false;

	}

	private void displayLines(Graphics2D bufGraphics) {


		
		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.get(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();
			
			if(ld.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.WHITE);


			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)points.get(ld.getIndex(j));
				Point3D p1=(Point3D)points.get(ld.getIndex((j+1)%ld.size()));

				//TOP VIEW
				bufGraphics.drawLine(convertTopX(p0.y),convertTopY(p0.x),convertTopX(p1.y),convertTopY(p1.x));
				//FRONT VIEW 
				bufGraphics.drawLine(convertTopX(p0.y),convertFrontY(p0.z),convertTopX(p1.y),convertFrontY(p1.z));
				//SIDE VIEW
				bufGraphics.drawLine(convertSideX(p0.x),convertFrontY(p0.z),convertSideX(p1.x),convertFrontY(p1.z));
			}


		}	

	}








	private void displayPoints(Graphics2D bufGraphics) {

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);

			if(p.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.white);

			//TOP
			bufGraphics.fillOval(convertTopX(p.y)-2,convertTopY(p.x)-2,5,5);
			//FRONT  
			bufGraphics.fillOval(convertTopX(p.y)-2,convertFrontY(p.z)-2,5,5);
			//SIDE
			bufGraphics.fillOval(convertSideX(p.x)-2,convertFrontY(p.z)-2,5,5);


		}



	}





	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}


	private void buildMenuBar() {
		jmb=new JMenuBar();
		jm=new JMenu("Load");
		jm.addMenuListener(this);
		jmb.add(jm);

		/*jmt11 = new JMenuItem("Load road");
		jmt11.addActionListener(this);
		jm.add(jmt11);*/

		jmt12 = new JMenuItem("Load lines");
		jmt12.addActionListener(this);
		jm.add(jmt12);

		jm2=new JMenu("Save");
		jm2.addMenuListener(this);

		jmt21 = new JMenuItem("Save lines");
		jmt21.addActionListener(this);
		jm2.add(jmt21);

		/*jmt22 = new JMenuItem("Save objects");
		jmt22.addActionListener(this);
		jm2.add(jmt22);*/

		jmb.add(jm2);

	
		setJMenuBar(jmb);
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
		checkCoordinatesx.setBounds(200,r,50,20);
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
		checkCoordinatesy.setBounds(200,r,50,20);
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
		checkCoordinatesz.setBounds(200,r,50,20);
		checkCoordinatesz.addKeyListener(this);
		right.add(checkCoordinatesz);

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

		r+=30;

		joinPoints=new JButton(header+"<u>J</u>oin sel points"+footer);
		joinPoints.addActionListener(this);
		joinPoints.addKeyListener(this);
		joinPoints.setFocusable(false);
		joinPoints.setBounds(5,r,150,20);
		right.add(joinPoints);

		r+=30;
		
		buildPolygon=new JButton(header+"<u>B</u>uild polygon"+footer);
		buildPolygon.addActionListener(this);
		buildPolygon.addKeyListener(this);
		buildPolygon.setFocusable(false);
		buildPolygon.setBounds(5,r,150,20);
		right.add(buildPolygon);

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
		
		selectAll=new JButton(header+"Selec<u>t</u> all"+footer);
		selectAll.addActionListener(this);
		selectAll.addKeyListener(this);
		selectAll.setFocusable(false);
		selectAll.setBounds(120,r,100,20);
		right.add(selectAll);

		r+=30;

		pointList=new JList();
        JScrollPane jsp=new JScrollPane(pointList);
        JTabbedPane jtpane=new JTabbedPane();
        jtpane.setBounds(5,r,200,240);
        
		right.add(jtpane);
		jtpane.add("Points",jsp);

		pointList.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e){

				int index=pointList.getSelectedIndex();
				for(int i=0;i<points.size();i++){

					Point3D p=(Point3D) points.get(i);
					if(index==i)
						selectPoint(p);
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
				for(int i=0;i<lines.size();i++){

					LineData ld=(LineData) lines.get(i);
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
		jtpane.add("Lines",jscp);


		add(right);


	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==addPoint) addPoint();
		else if(o==deleteSelection) delete();

		else if(o==jmt21){
			saveLines();
		}
		else if(o==jmt12){
			loadPointsFromFile();
			displayAll();
		}
		else if(o==changePoint){
			changeSelectedPoint();
			displayAll();
		}
		else if(o==joinPoints){
			joinSelectedPoints();
			displayAll();
		}
		else if(o==buildPolygon){
			buildPolygon();
			displayAll();
		}
		else if(o==deselectAll){
			deselectAll();
			displayAll();
		}
		else if(o==selectAll){
			selectAllPoints();
			displayAll();
		}

	}






	private void addPoint(MouseEvent arg0) {
		Point p=arg0.getPoint();

		if(isTOP((int)p.getX(),(int)p.getY())){

			int y=invertTopX((int)p.getX());
			int x=invertTopY((int)p.getY());
			addPoint(x,y,0);
		

		}
		else if(isFRONT((int)p.getX(),(int)p.getY())){

			int y=invertTopX((int)p.getX());
			int z=invertFrontY((int)p.getY());
			addPoint(0,y,z);
			

		}
		else if(isSIDE((int)p.getX(),(int)p.getY())){

			int x=invertSideX((int)p.getX());
			int z=invertFrontY((int)p.getY());
			addPoint(x,0,z);
			

		}


	}

	private void delete() {

		ArrayList newPoints=new ArrayList();
		ArrayList newLines=new ArrayList();

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) points.get(ld.getIndex(j));
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.get(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k);
							break;
						}
					}	

			}
			if(newLd.size()>1)
				newLines.add(newLd);




		}

		points=newPoints;
		lines=newLines;
        deselectAll();
		

		displayAll();

	}

	private void addPoint() {

		double x=Double.parseDouble(coordinatesx.getText());
		double y=Double.parseDouble(coordinatesy.getText());
		double z=Double.parseDouble(coordinatesz.getText());

		addPoint(x,y,z);

	}


	private void joinSelectedPoints() {
		for(int i=0;i<points.size();i++){

			Point3D p0=(Point3D) points.get(i);

			for(int j=0;j<points.size();j++){

				Point3D p1=(Point3D) points.get(j);

				if(p0.isSelected && p1.isSelected() && i<j)
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
	
	private void buildPolygon() {


        lines.add(polygon);
        

		deselectAll();

	}

	private void addPoint(double x, double y, double z) {

		if(!"".equals(coordinatesx.getText()))
			x=Double.parseDouble(coordinatesx.getText());
		if(!"".equals(coordinatesy.getText()))
			y=Double.parseDouble(coordinatesy.getText());
		if(!"".equals(coordinatesz.getText()))
			z=Double.parseDouble(coordinatesz.getText());

		Point3D p=new Point3D(x,y,z);

		points.add(p);
        
		deselectAll();
		displayAll();
		
		pointList.ensureIndexIsVisible(pointList.getModel().getSize()-1); 

	}

	private void selectPoint(int x, int y) {

		//select point from lines
		if(!checkMultipleSelection.isSelected()) 
			polygon=new LineData();
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);



			int xo=-1;
			int yo=-1;

			if(isTOP(x,y)){

				xo=convertTopX(p.y);
				yo=convertTopY(p.x);
			}
			else if(isFRONT(x,y)){

				xo=convertTopX(p.y);
				yo=convertFrontY(p.z);
			}
			else if(isSIDE(x,y)){

				xo=convertSideX(p.x);
				yo=convertFrontY(p.z);
			}

			Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
			if(rect.contains(x,y)){

				selectPoint(p);

			    polygon.addIndex(i);

			}
			else if(!checkMultipleSelection.isSelected()) 
				p.setSelected(false);
		}

	}
	



	private void selectPoint(Point3D p) {
		
		coordinatesx.setText(""+p.x);
		coordinatesy.setText(""+p.y);
		coordinatesz.setText(""+p.z);

		deselectAllLines();

		p.setSelected(true);
		
	}

	private void deselectAll() {
		
		clean();
		deselectAllLines();
		deselectAllPoints();
		polygon=new LineData();

	
		
	
	}

	private void deselectAllPoints() {
		
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);
			p.setSelected(false);
		}
		
	}
	
	private void selectAllPoints() {
		
		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);
			p.setSelected(true);
		}
		
	}

	private void deselectAllLines() {
		
		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.get(i);
			ld.setSelected(false);
		}
		
	}

	private void changeSelectedPoint() {

		for(int i=0;i<points.size();i++){

			Point3D p=(Point3D) points.get(i);

			if(p.isSelected()){

				if(!"".equals(coordinatesx.getText()))
					p.x=Double.parseDouble(coordinatesx.getText());
				if(!"".equals(coordinatesy.getText()))
					p.y=Double.parseDouble(coordinatesy.getText());
				if(!"".equals(coordinatesz.getText()))
					p.z=Double.parseDouble(coordinatesz.getText());
			}

		}

		deselectAll();

	}


	private int convertTopX(double i) {

		return (int) (y0Top+i/dx+MOVX);
	}

	private int invertTopX(int i) {

		return (i-MOVX-y0Top)*dx;
	}

	private int convertTopY(double j) {

		return (int) (j/dy+x0Top+MOVY)+FRONT_VIEW_HEIGHT;
	}

	private int invertTopY(int j) {

		return dy*(j-x0Top-MOVY-FRONT_VIEW_HEIGHT);
	}

	private int convertFrontY(double j) {

		return (int) (FRONT_VIEW_HEIGHT-j/dy-z0Front);
	}

	private int invertFrontY(double j) {

		return (int) (FRONT_VIEW_HEIGHT-z0Front-j+MOVY)*dy;
	}

	private int convertSideX(double i) {

		return (int) (x0Side+FRONT_VIEW_WIDTH+i/dx);
	}

	private int invertSideX(int i) {

		return (i-x0Side-FRONT_VIEW_WIDTH)*dx;
	}

	private void saveLines() {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save lines");
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			saveLines(file);

		} 


	}

	public void saveLines(File file) {



		PrintWriter pr;
		try {
			pr = new PrintWriter(new FileOutputStream(file));
			pr.print("P=");

			for(int i=0;i<points.size();i++){

				Point3D p=(Point3D) points.get(i);

				pr.print(decomposePoint(p));
				if(i<points.size()-1)
					pr.print(" ");
			}	

			pr.print("\nL=");

			for(int i=0;i<lines.size();i++){

				LineData ld=(LineData) lines.get(i);

				pr.print(decomposeLineData(ld));
				if(i<lines.size()-1)
					pr.print(" ");
			}	

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}



	public void loadPointsFromFile(){	

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load lines ");

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			loadPointsFromFile(file);


		}
	}

	public void loadPointsFromFile(File file){

		points=new ArrayList();
		lines=new ArrayList();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("P="))
					buildPoints(points,str.substring(2));
				else if(str.startsWith("L="))
					buildLines(lines,str.substring(2));


			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}



	private void buildPoints(ArrayList points, String str) {

		StringTokenizer sttoken=new StringTokenizer(str," ");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(" ");

			Point3D p=new Point3D();

			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			points.add(p);
		}




	}

	private void buildLines(ArrayList lines, String str) {

		StringTokenizer sttoken=new StringTokenizer(str," ");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(" ");

			LineData ld=new LineData();

			for(int i=0;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			lines.add(ld);
		}




	}

	private String decomposePoint(Point3D p) {
		String str="";

		str=p.x+","+p.y+","+p.z;

		return str;
	}

	private String decomposeLineData(LineData ld) {

		String str="";

		for(int j=0;j<ld.size();j++){

			if(j>0)
				str+=",";
			str+=ld.getIndex(j);

		}

		return str;
	}


	public void clean(){

		if(!checkCoordinatesx.isSelected())	coordinatesx.setText("");
		if(!checkCoordinatesy.isSelected())coordinatesy.setText("");
		if(!checkCoordinatesz.isSelected())coordinatesz.setText("");
		/*if(!checkCoordinatesdx.isSelected())coordinatesdx.setText("");
		if(!checkCoordinatesdy.isSelected())coordinatesdy.setText("");
		if(!checkCoordinatesdz.isSelected())coordinatesdz.setText("");
		if(!checkObjectIndex.isSelected())objectIndex.setText("");
		if(!checkColor.isSelected())checkColor.setBackground(ZBuffer.fromHexToColor("FFFFFF"));
		 */
		

	}
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void menuSelected(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseClicked(MouseEvent arg0) {

		int buttonNum=arg0.getButton();
		//right button click
		if(buttonNum==MouseEvent.BUTTON3)
			addPoint(arg0);

		else
			selectPoint(arg0.getX(),arg0.getY());
		displayAll();

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
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

		int pix=arg0.getUnitsToScroll();
		if(pix>0) up();
		else down();

	}
	@Override
	public void mouseDragged(MouseEvent arg0) {

	}
	@Override
	public void mouseMoved(MouseEvent e) {

		Point p=e.getPoint();
		
		int x=(int)p.getX();
		int y=(int)p.getY();
		
		if(isTOP(x,y)){
			
			screenPoint.setText(invertTopY(y)+","+invertTopX(x)+",0");
		}
	    else if(isFRONT(x,y)){
			
			screenPoint.setText("0,"+invertTopX(x)+","+invertFrontY(y));
		}
	    else if(isSIDE(x,y)){
			
			screenPoint.setText(invertSideX(x)+",0,"+invertFrontY(y));
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
		{	left(); 
		   
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	
			right();   
		
		}
		else if(code==KeyEvent.VK_PAGE_UP )
		{	
			upTop();
		}
		else if(code==KeyEvent.VK_PAGE_DOWN  )
		{	
			downTop();   
		
		}
		else if(code==KeyEvent.VK_C  )
		{	
			changeSelectedPoint();   
		    displayAll();   
		}
		else if(code==KeyEvent.VK_J )
		{	
			joinSelectedPoints(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_I )
		{	
			addPoint();
		}
		else if(code==KeyEvent.VK_P )
		{	
			buildPolygon(); 
			displayAll(); 
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
			delete();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F1 )
		{	
			checkMultipleSelection.setSelected(true);  
		}
		else if(code==KeyEvent.VK_F2 )
		{	
			 
			checkMultipleSelection.setSelected(false);  
		}

	}

	private void right() {
		
		
		y0Front=y0Front-5;
		y0Top=y0Front;
		displayAll();
		
	}

	private void left() {
		y0Front=y0Front+5;
		y0Top=y0Front;
		displayAll();
		
	}

	public void up(){
		z0Front=z0Front+2;
		z0Side=z0Front;
		displayAll();

	}

	public void down(){
		
		z0Front=z0Front-2;
		z0Side=z0Front;
		
		displayAll();

	}
	


	private void upTop() {
		
		
		x0Side=x0Side-5;
		x0Top=x0Side;
		displayAll();
		
	}

	private void downTop() {
		x0Side=x0Side+5;
		x0Top=x0Side;
		displayAll();
		
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
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}


	public class LineData{

		ArrayList lineDatas=new ArrayList();
		boolean isSelected=false;

		public int size(){

			return lineDatas.size();
		}

		public void addIndex(int n){
			lineDatas.add(new Integer(n));
		}

		public int getIndex(int i){
			return ((Integer)lineDatas.get(i)).intValue();
		}
		
		@Override
		public String toString() {
			
			return decomposeLineData(this);
		}

		public LineData(){}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		} 
	}
	
	public class PointListItem{
		
		private DecimalFormat df=new DecimalFormat("000");

		Point3D p;

		public PointListItem(Point3D p) {
			super();
			this.p = p;
		}
		
		@Override
		public String toString() {
			
			return df.format(p.x)+","+df.format(p.y)+","+df.format(p.z);
		}
		
	}



}
