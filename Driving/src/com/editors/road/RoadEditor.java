package com.editors.road;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Plain;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.main.CarFrame;
import com.main.HelpPanel;




/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class RoadEditor extends Editor implements ActionListener,MouseListener,MouseWheelListener,PropertyChangeListener,MouseMotionListener,KeyListener, ItemListener{


	private JPanel center;
	int HEIGHT=660;
	int WIDTH=700;
	int LEFT_BORDER=240;
	int RIGHT_BORDER=240;
	int BOTTOM_BORDER=100;
	int RIGHT_SKYP=10;

	int MOVX=-50;
	int MOVY=100;

	int dx=2;
	int dy=2;

	int deltay=200;
	int deltax=200;

	Vector drawObjects=new Vector();
	Graphics2D g2;
	Graphics2D g2Alias;
	Stack oldObjects=new Stack();
	Stack oldRoads=new Stack();
	int MAX_STACK_SIZE=10;


	private JFileChooser fc;
	private JMenuBar jmb;
	private JMenu jm_file;
	private JTabbedPane right;
	private DoubleTextField[] coordinatesx;
	private DoubleTextField[] coordinatesy;
	private DoubleTextField[] coordinatesz;
	private JCheckBox[] checkCoordinatesx;
	private JCheckBox[] checkCoordinatesy;
	private JCheckBox[] checkCoordinatesz;
	private JButton[] changePoint;
	private JButton[] mergeSelectedPoints;
	private JTextField[] colorRoadChoice;
	private JCheckBox[] checkRoadColor;
	private JCheckBox[] checkMultiplePointsSelection;
	private JButton[] changePolygon;
	private JButton[] startBuildPolygon;
	private JButton[] buildPolygon;
	private JButton[] deleteSelection;
	private JButton[] addPoint;
	private JButton[] polygonDetail;
	private JComboBox[] chooseTexture;
	private JButton[] choosePanelTexture;
	private JButton[] chooseNextTexture;
	private JButton[] choosePrevTexture;
	private JButton[] deselectAll;
	private JLabel[] textureLabel;
	
	private DoubleTextField[] roadMove;
	private JButton[] moveRoadUp;
	private JButton[] moveRoadDown;
	private JButton[] moveRoadRight;
	private JButton[] moveRoadLeft;
	private JButton[] moveRoadTop;
	private JButton[] moveRoadBottom;
	
	private JButton deselectAllObjects;
	
	private JButton addObject;
	private JButton delObject;
	private JMenu jm5;
	private JMenuItem jmtPreview;

	public boolean ISDEBUG=false;
	
	private JPanel bottom;
	private JLabel screenPoint;


	private JLabel objectLabel;
	private JComboBox chooseObject;
	private JButton chooseObjectPanel;
	private JButton choosePrevObject;
	private JButton chooseNextObject;
	private JCheckBox checkMultipleObjectsSelection;

	boolean isUseTextures=true;

	
	private JMenu jm3;
	private JCheckBoxMenuItem jmtIsUSeTextures;
	
	private JMenu jm4;
	private JMenuItem jmtUndoObjects;
	private JMenuItem jmtUndoRoad;	
	private JPanel left;
	
	private DoubleTextField objcoordinatesx;
	private JCheckBox objcheckCoordinatesx;
	private DoubleTextField objcoordinatesy;	
	private JCheckBox objcheckCoordinatesy;
	private DoubleTextField objcoordinatesz;
	private JCheckBox objcheckCoordinatesz;
	
	private JButton changeObject;
	private JTextField colorObjChoice;
	private JCheckBox checkObjColor;
	
	private DoubleTextField objcoordinatesdx;
	private DoubleTextField objcoordinatesdy;
	private DoubleTextField objcoordinatesdz;
	private JCheckBox objcheckCoordinatesdx;
	private JCheckBox objcheckCoordinatesdy;
	private JCheckBox objcheckCoordinatesdz;
	private Rectangle currentRect;
	private boolean isDrawCurrentRect=false;
	private DoubleTextField objMove;
	private JButton moveObjUp;
	private JButton moveObjDown;
	private JButton moveObjLeft;
	private JButton moveObjRight;
	private JButton moveObjTop;
	private JButton moveObjBottom;
	private CubicMesh[] objectMeshes;	

	public static Texture[] worldTextures;
	public static BufferedImage[] worldImages;
	public static BufferedImage[] objectImages;

	public static Color BACKGROUND_COLOR=new Color(0,0,0);
	private JMenuItem jmtShowAltimetry;
	private JMenuItem jmtBuildNewGrid;
	private JMenu help_jm;
	private AbstractButton jmtAddBendMesh;
	private JMenuItem jmt_load_landscape;
	private JMenuItem jmt_save_landscape;


	
	String[] panelsTitles={"Terrain","Road"};
	
	Color alphaRed=new Color(Color.RED.getRed(),0,0,100);
	private JMenuItem jmtAddGrid;
	private JMenu jmSpecial;
	private JMenuItem jmtLevelRoadTerrain;
	private JMenuItem jmLevelTerrainRoad;
	
	public static ZBuffer[] landscapeZbuffer;
	public int blackRgb= Color.BLACK.getRGB();
	public int[] rgb=null;
	public Color selectionColor=null;
	
	public RoadEditor(String title){
		
		setTitle(title);
		
		if(!DrawObject.IS_3D)
		   setTitle("Road editor 2D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocation(10,10);
		setSize(WIDTH+RIGHT_BORDER+LEFT_BORDER+RIGHT_SKYP,HEIGHT+BOTTOM_BORDER);
		center=new JPanel(){
			
			public void paint(Graphics g) {
				super.paint(g);
			
					displayAll();
			}
		};
		center.setBackground(BACKGROUND_COLOR);
		center.setBounds(LEFT_BORDER,0,WIDTH,HEIGHT);
		center.addMouseListener(this);
		center.addMouseWheelListener(this);
		center.addMouseMotionListener(this);
		addKeyListener(this);
		addPropertyChangeListener(this);
		add(center);
		buildMenuBar();
		buildLeftObjectPanel();
		
		right=new JTabbedPane();
		right.addChangeListener(
				
			new ChangeListener() {
				
				public void stateChanged(ChangeEvent arg0) {
					
					ACTIVE_PANEL=right.getSelectedIndex();
					
				}
			}
				
		);
		
		
		
		right.setBounds(WIDTH+LEFT_BORDER,0,RIGHT_BORDER,HEIGHT);
		
		add(right);
		
		buildFieldsArrays();
		buildRightPanel(0); 
		buildRightPanel(1); 
		
		buildBottomPanel();

		RepaintManager.setCurrentManager( 
				new RepaintManager(){

					public void paintDirtyRegions() {


						super.paintDirtyRegions();
						firePropertyChange("paintDirtyRegions",false,true);
						//if(redrawAfterMenu ) {displayAll();redrawAfterMenu=false;}
					}

				}				
		);
		
		currentDirectory=new File("lib");

		setVisible(true);


	}

	private void buildFieldsArrays() {

		coordinatesx=new DoubleTextField[numPanels];
		coordinatesy=new DoubleTextField[numPanels];
		coordinatesz=new DoubleTextField[numPanels];

		checkCoordinatesx=new JCheckBox[numPanels];
		checkCoordinatesy=new JCheckBox[numPanels];
		checkCoordinatesz=new JCheckBox[numPanels];

		changePoint=new JButton[numPanels];		
		colorRoadChoice= new  JTextField[numPanels];
		checkRoadColor= new JCheckBox[numPanels];
		checkMultiplePointsSelection= new JCheckBox[numPanels];
		mergeSelectedPoints=new JButton[numPanels];
		changePolygon=new JButton[numPanels];	
		startBuildPolygon=new JButton[numPanels];	
		buildPolygon=new JButton[numPanels];	
		deleteSelection=new JButton[numPanels];	
		addPoint=new JButton[numPanels];	
		polygonDetail=new JButton[numPanels];	
		
		chooseTexture=new JComboBox[numPanels];	
		choosePanelTexture=new JButton[numPanels];		
		chooseNextTexture=new JButton[numPanels];
		choosePrevTexture=new JButton[numPanels];
		
		deselectAll=new JButton[numPanels];
		textureLabel=new JLabel[numPanels];
		
		roadMove=new DoubleTextField[numPanels];
		moveRoadUp=new JButton[numPanels];
		moveRoadDown=new JButton[numPanels];
		moveRoadRight=new JButton[numPanels];
		moveRoadLeft=new JButton[numPanels];
		moveRoadTop=new JButton[numPanels];
		moveRoadBottom=new JButton[numPanels];
		
		
		
	}


	/**
	 * 
	 */
	public void initialize() {

		landscapeZbuffer=new ZBuffer[WIDTH*HEIGHT];
		rgb=new int[WIDTH*HEIGHT];
		buildNewZBuffers();
		selectionColor=new Color(255,0,0,127);
		
		g2=(Graphics2D) center.getGraphics();
		g2Alias=(Graphics2D) center.getGraphics();
		g2Alias.setColor(Color.GRAY);
		Stroke stroke=new BasicStroke(0.1f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
		g2Alias.setStroke(stroke);
		
		File directoryImg=new File("lib");
		File[] files=directoryImg.listFiles();
		
		Vector vObjects=new Vector();
		
		if(DrawObject.IS_3D)
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("object3D_")
				   && 	!files[i].getName().startsWith("object3D_texture")	
				){
					
					vObjects.add(files[i]);
					
				}		
			}
		else{
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("object_")
					
				){
					
					vObjects.add(files[i]);
					
				}		
			}
			
		}
		
		try{	


		
			
			Vector vRoadTextures=new Vector();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("road_texture_")){
					
					vRoadTextures.add(files[i]);
					
				}		
			}
			
			worldImages=new BufferedImage[vRoadTextures.size()];
			worldTextures=new Texture[vRoadTextures.size()];
			
			
			if(isUseTextures){
				
				
				for(int i=0;i<vRoadTextures.size();i++){
					
					worldImages[i]=ImageIO.read(new File("lib/road_texture_"+i+".jpg"));
					
					for (int j = 0; j < numPanels; j++) {
						chooseTexture[j].addItem(new ValuePair(""+i,""+i));
					}
				
					
					
					
				}

				
				if(isUseTextures){
					
					
					for(int i=0;i<vRoadTextures.size();i++){
						
						worldTextures[i]=new Texture(ImageIO.read(new File("lib/road_texture_"+i+".jpg")));
					}
					
				
					
			
					
				}

				
			}
			
		
			
			objectImages=new BufferedImage[vObjects.size()];
			objectMeshes=new CubicMesh[vObjects.size()];
			
			for(int i=0;i<vObjects.size();i++){
				
				chooseObject.addItem(new ValuePair(""+i,""+i));
				objectImages[i]=ImageIO.read(new File("lib/object_"+i+".gif"));
					
				
				if(DrawObject.IS_3D){
					objectMeshes[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i));
				}
				
					
					
					
				
			}
						
			
		} catch (IOException e) {
			e.printStackTrace();
		}	

	}


	public void buildNewZBuffers() {


		for(int i=0;i<landscapeZbuffer.length;i++){

			landscapeZbuffer[i]=new ZBuffer(blackRgb,0);
            

		}
	

	}
	
	public void buildScreen(BufferedImage buf) {

		int length=rgb.length;
		
		for(int i=0;i<length;i++){


			ZBuffer zb=landscapeZbuffer[i];
			//set
			rgb[i]=zb.getRgbColor(); 
			
			//clean
			zb.set(0,0,0,blackRgb);
              
		

		}
		
		buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,rgb);
		//buf.setRGB(0,0,WIDTH,HEIGHT,rgb,0,WIDTH);


	}


	private void displayObjects(ZBuffer[] landscapeZbuffer) {

		Rectangle totalVisibleField=new Rectangle(0,0,WIDTH,HEIGHT);

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);

			int y=convertY(dro.y);
			int x=convertX(dro.x);

			int index=dro.getIndex();

			int dw=(int) (dro.dx/dx);
			int dh=(int) (dro.dy/dy);


			if(!totalVisibleField.intersects(new Rectangle(x,y,dw,dh)) && 
					!totalVisibleField.contains(x,y)	)
				continue;

			/*if(dro.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.WHITE);
			bufGraphics.drawString(""+index,x-5,y+5);*/
			drawObject(landscapeZbuffer,dro);

		}	
	}




	private void deselectAll() {
		cleanPoints();
		deselectAllPoints();
		displayAll();
		coordinatesx[ACTIVE_PANEL].requestFocus();
		
		polygon=new LineData();
	}
	
	private void startBuildPolygon() {
		
		deselectAll();
		displayAll();
		
		if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected())
			checkMultiplePointsSelection[ACTIVE_PANEL].setSelected(true);
	}
	
	private void polygonDetail() {
	
		
		int sizel=lines[ACTIVE_PANEL].size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(i);
			if(!ld.isSelected())
				continue;
			
			RoadEditorPolygonDetail repd=new RoadEditorPolygonDetail(this,ld);
	
			
			if(repd.getModifiedLineData()!=null){
				
				lines[ACTIVE_PANEL].setElementAt(repd.getModifiedLineData(),i);
				
			}
			
			break;
		}
		displayAll();
		
	}


	private void displayAll() {


		BufferedImage buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

		Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
		bufGraphics.setColor(BACKGROUND_COLOR);
		bufGraphics.fillRect(0,0,WIDTH,HEIGHT);

		//draw first the ground and the road above
		displayRoad(landscapeZbuffer,0);
		displayRoad(landscapeZbuffer,1);
		displayObjects(landscapeZbuffer);
		
		buildScreen(buf);

		g2.drawImage(buf,0,0,WIDTH,HEIGHT,null);

	}




	private void displayRoad(ZBuffer[] landscapeZbuffer,int index) {

				
		int lsize=lines[index].size();
		

		for(int j=0;j<lsize;j++){
			
			
			LineData ld=(LineData) lines[index].elementAt(j);

			drawPolygon(ld,points[index],landscapeZbuffer,index);

		} 

		//mark row angles
		
		int size=points[index].size();
		for(int j=0;j<size;j++){


		   /* Point4D p=(Point4D) points[index].elementAt(j);

				int xo=convertX(p.x);
				int yo=convertY(p.y);

				if(p.isSelected()){
					landscapeZbuffer.setColor(Color.RED);

				}	
				else
					landscapeZbuffer.setColor(Color.white);
	
				landscapeZbuffer.fillOval(xo-2,yo-2,5,5);*/

			}
		
		
		//drawCurrentRect(landscapeZbuffer);
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



	public void paint(Graphics g) {
		super.paint(g);
		displayAll();
	}

	private void drawObject(ZBuffer[] landscapeZbuffer, DrawObject dro) {

		int[] cx=new int[4];
		int[] cy=new int[4];
		
		int versus=1;
		if(!DrawObject.IS_3D)
			versus=-1;

		cx[0]=convertX(dro.x);
		cy[0]=convertY(dro.y);
		cx[1]=convertX(dro.x);
		cy[1]=convertY(dro.y+versus*dro.dy);
		cx[2]=convertX(dro.x+dro.dx);
		cy[2]=convertY(dro.y+versus*dro.dy);
		cx[3]=convertX(dro.x+dro.dx);
		cy[3]=convertY(dro.y);

		Polygon p_in=new Polygon(cx,cy,4);

		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty())
			return;

		Polygon pTot=Polygon3D.fromAreaToPolygon2D(partialArea);
		//if(cy[0]<0 || cy[0]>HEIGHT || cx[0]<0 || cx[0]>WIDTH) return;
		
		
		Color pColor=ZBuffer.fromHexToColor(dro.getHexColor());
		
		
		if(dro.isSelected())
		{
			pColor=Color.RED;
			
		}
		int rgbColor = pColor.getRGB();
		drawPolygon(landscapeZbuffer,pTot,rgbColor);
		
		if(!DrawObject.IS_3D){
	
			drawImage(landscapeZbuffer,DrawObject.fromImageToBufferedImage(objectImages[dro.index],Color.WHITE)
					,cx[0],cy[0],cx[2]-cx[0],cy[2]-cy[0],null);
		}
		

	}


	private void drawImage(ZBuffer[] landscapeZbuffer2,
			BufferedImage bufferedImage, int x, int y, int dx, int dy,
			Object object) {
	
		for(int i=x;i<dx+x;i++)
			for(int j=y;j<dy+y;j++){
				
				int rgbColor=bufferedImage.getRGB(i,j);
				
				int tot=(int)(x+y*WIDTH);
				landscapeZbuffer[tot].setRgbColor(rgbColor);
			}
		
		
	}

	private void drawPolygon(ZBuffer[] landscapeZbuffer, Polygon pTot, int rgbColor) {
		
		
		
		for (int n = 0; n < pTot.npoints; n++) {
			
			int i=pTot.xpoints[n];
			int j=pTot.ypoints[n];
			
			int ii=pTot.xpoints[(n+1)%pTot.npoints];
			int jj=pTot.ypoints[(n+1)%pTot.npoints];
			
			drawLine(landscapeZbuffer,i,j,ii,jj,rgbColor);
			
			
		
		}
		
	}

	private void drawLine(ZBuffer[] landscapeZbuffer2, int i, int j, int ii,
			int jj, int rgbColor) {
		
		int mini=i;
		int maxi=ii;
		
		int minj=j;
		int maxj=jj;
		
		if(ii<i){
			
			mini=ii;
			maxi=i;
			
		}
		
		if(jj<j){
			
			minj=jj;
			maxj=j;
			
		}
		
		if(j==jj){			
			
			for (int k = mini; k < maxi; k++) {
				
				int tot=k+j*WIDTH;
				
				landscapeZbuffer[tot].setRgbColor(rgbColor);
				
			}
		}
		else if(i==ii){
			
			for (int k = minj; k < maxj; k++) {
					
					int tot=i+k*WIDTH;
					
					landscapeZbuffer[tot].setRgbColor(rgbColor);
					
				}
				
		}
		else{
			
			for (int k = mini; k < maxi; k++) {
				
				double l=(i-mini)/(maxi-mini);
				
				
				if(ii>i)
				{
					double y=(1-l)*j+l*jj;
					int tot=(int)(k+y*WIDTH);
					landscapeZbuffer[tot].setRgbColor(rgbColor);
					
				}	
				else{
					
					double y=(1-l)*jj+l*j;
					int tot=(int)(k+y*WIDTH);
					landscapeZbuffer[tot].setRgbColor(rgbColor);
					
				}
				
				
				
				
			}
			
			
		}
		
	}

	private void drawPolygon(LineData ld,Vector points,ZBuffer[] landscapeZbuffer,int indx) {


		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points.elementAt(num);

			//bufGraphics.setColor(ZBuffer.fromHexToColor(is[i].getHexColor()));

			cx[i]=convertX(p.x);
			cy[i]=convertY(p.y);


			//real coordinates
			cxr[i]=(int)(p.x);
			cyr[i]=(int)(p.y);


		}

		Polygon p_in=new Polygon(cx,cy,ld.size());
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty())
			return;

		Polygon3D p3d=new Polygon3D(size,cx,cy,cz);
		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

		//calculate texture angle


		if(isUseTextures){

			

			int index=ld.getTexture_index();//????
			Color selected=null;
			
			if(ld.isSelected()){
				
				if(indx<0 || indx==ACTIVE_PANEL){
					selected=selectionColor;
				}
			}
			


			drawTexture(worldTextures[index],p3d,p3dr,landscapeZbuffer,selected);
			
		}
		else{
			/*bufGraphics.setColor(ZBuffer.fromHexToColor(ld.getHexColor()));
			bufGraphics.fill(partialArea); */
		}


	}

	private void drawTexture(Texture texture,  Polygon3D p3d, Polygon3D p3dr,ZBuffer[] landscapeZbuffe, Color selected) {

		Point3D normal=Polygon3D.findNormal(p3dr);



		if(texture!=null){
			
			Rectangle rect = p3d.getBounds();
			
			
			/*int i0=Math.min(p3d.xpoints[0],p3d.xpoints[3]);
			int i1=Math.max(p3d.xpoints[1],p3d.xpoints[2]);
					
			int j0=Math.min(p3d.ypoints[3],p3d.ypoints[2]);
			int j1=Math.max(p3d.ypoints[0],p3d.ypoints[1]);*/
			
			int i0=rect.x;
			int i1=rect.x+rect.width;
					
			int j0=rect.y;
			int j1=rect.y+rect.height;

			//System.out.println(i0+" "+i1+" "+j0+" "+j1);
			
			
			Point3D p0r=new Point3D(p3dr.xpoints[0],p3dr.ypoints[0],p3dr.zpoints[0]);
			Point3D p1r=new Point3D(p3dr.xpoints[1],p3dr.ypoints[1],p3dr.zpoints[1]);
			Point3D pNr=new Point3D(p3dr.xpoints[p3dr.npoints-1],p3dr.ypoints[p3dr.npoints-1],p3dr.zpoints[p3dr.npoints-1]);

			Point3D xDirection = (p1r.substract(p0r)).calculateVersor();
			//Point3D yDirection= (pNr.substract(p0r)).calculateVersor();
			Point3D yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();
			
			/*System.out.println(normal.x+" "+normal.y+" "+normal.z);
			System.out.println(xDirection.x+" "+xDirection.y);
			System.out.println(yDirection.x+" "+yDirection.y);*/

			
			int mask = 0xFF;
			double a1=0.6;
			double a2=1.0-a1;
			
			double rr=0;
			double gg=0;
			double bb=0;
			if(selected!=null){
				
				int rgbSelection=selected.getRGB();
				rr=a2*(rgbSelection>>16 & mask);
				gg=a2*(rgbSelection>>8 & mask);
				bb=a2*(rgbSelection & mask);
			}
			
			for(int i=i0;i<i1;i++){
				
				//how to calculate the real limits?
				
			
				for (int j = j0; j < j1; j++) {
					
					if(p3d.contains(i,j))
					{	

						
						int ii=invertX(i);
						int jj=invertY(j);
				
						if(i>=0 && i<WIDTH && j>=0 && j<HEIGHT){
							
							int tot=i+j*WIDTH;											

							int rgbColor = ZBuffer.pickRGBColorFromTexture(texture,ii,jj,p3d.zpoints[0],xDirection,yDirection,p0r,0,0);
							
							if(selected!=null){
								
								
								
							    //int a=(int) (a1*(rgbColor>>32 & mask)+a2*(rgbSelection>>32 & mask));
							    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
							    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
							    int b=(int) (a1*(rgbColor & mask)+bb);
								
								rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
							}

							landscapeZbuffer[tot].setRgbColor(rgbColor);
				
						}
						
						
						
					}
					//bufGraphics.setColor(new Color(rgbColor));
					//bufGraphics.fillRect(i,j,1,1);
					


				}

			}

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
		jm_file=new JMenu("File");
		jm_file.addMenuListener(this);
		jmb.add(jm_file);
		
		jmt_load_landscape = new JMenuItem("Load landscape");
		jmt_load_landscape.addActionListener(this);
		jm_file.add(jmt_load_landscape);
		
		jmt_save_landscape = new JMenuItem("Save landscape");
		jmt_save_landscape.addActionListener(this);
		jm_file.add(jmt_save_landscape);


		jm3=new JMenu("Textures");
		jm3.addMenuListener(this);

		jmtIsUSeTextures = new JCheckBoxMenuItem("Use textures");
		jmtIsUSeTextures.setState(true);
		jmtIsUSeTextures.addActionListener(this);
		jm3.add(jmtIsUSeTextures);

		jmb.add(jm3);

		jm4=new JMenu("Change");
		jm4.addMenuListener(this);
		jmtUndoObjects = new JMenuItem("Undo last object");
		jmtUndoObjects.setEnabled(false);
		jmtUndoObjects.addActionListener(this);
		jm4.add(jmtUndoObjects);
		jmtUndoRoad = new JMenuItem("Undo last road");
		jmtUndoRoad.setEnabled(false);
		jmtUndoRoad.addActionListener(this);
		jm4.add(jmtUndoRoad);	
		
		jmb.add(jm4);
		
		jm5=new JMenu("Editing");
		jm5.addMenuListener(this);
		
		jmtBuildNewGrid = new JMenuItem("New Grid");
		jmtBuildNewGrid.addActionListener(this);
		jm5.add(jmtBuildNewGrid);
		
		jm5.addSeparator();
		
		jmtAddGrid = new JMenuItem("Add grid");
		jmtAddGrid.addActionListener(this);
		jm5.add(jmtAddGrid);
		
		jm5.addSeparator();
		
		if(DrawObject.IS_3D)
		{
			jmtPreview = new JMenuItem("Preview");
			jmtPreview.addActionListener(this);
			jm5.add(jmtPreview);
	
			jmtShowAltimetry = new JMenuItem("Advanced Altimetry");
			jmtShowAltimetry.addActionListener(this);
			jm5.add(jmtShowAltimetry);
		}
		

		jm5.addSeparator();
		
		jmtAddBendMesh = new JMenuItem("Add bend");
		jmtAddBendMesh.addActionListener(this);
		jm5.add(jmtAddBendMesh);
		
		
		jmb.add(jm5);
		
		jmSpecial=new JMenu("Special");
		jmSpecial.addMenuListener(this);		
		jmb.add(jmSpecial);
		
		jmtLevelRoadTerrain=new JMenuItem("Level road->ground");
		jmtLevelRoadTerrain.addActionListener(this);		
		jmSpecial.add(jmtLevelRoadTerrain);
		
		jmLevelTerrainRoad=new JMenuItem("Level ground->road");
		jmLevelTerrainRoad.addActionListener(this);		
		jmSpecial.add(jmLevelTerrainRoad);
		
		help_jm=new JMenu("Help");
		help_jm.addMenuListener(this);		
		jmb.add(help_jm);

		setJMenuBar(jmb);
	}

	private void buildLeftObjectPanel() {

		String header="<html><body>";
		String footer="</body></html>";

		left=new JPanel();
		left.setBounds(0,0,LEFT_BORDER,HEIGHT);
		left.setLayout(null);
        Border leftBorder=BorderFactory.createTitledBorder("Objects");
        left.setBorder(leftBorder);
		        
		int r=5;


		r+=30;
		
		checkMultipleObjectsSelection=new JCheckBox("Multiple selection");
		checkMultipleObjectsSelection.setBounds(30,r,150,20);
		checkMultipleObjectsSelection.addKeyListener(this);
		left.add(checkMultipleObjectsSelection);
		
		r+=30;

		JLabel lx=new JLabel("x:");
		lx.setBounds(5,r,20,20);
		left.add(lx);
		objcoordinatesx=new DoubleTextField(8);
		objcoordinatesx.setBounds(30,r,120,20);
		objcoordinatesx.addKeyListener(this);
		left.add(objcoordinatesx);
		objcheckCoordinatesx=new JCheckBox();
		objcheckCoordinatesx.setBounds(170,r,50,20);
		objcheckCoordinatesx.addKeyListener(this);
		left.add(objcheckCoordinatesx);

		r+=30;

		JLabel ly=new JLabel("y:");
		ly.setBounds(5,r,20,20);
		left.add(ly);
		objcoordinatesy=new DoubleTextField(8);
		objcoordinatesy.setBounds(30,r,120,20);
		objcoordinatesy.addKeyListener(this);
		left.add(objcoordinatesy);
		objcheckCoordinatesy=new JCheckBox();
		objcheckCoordinatesy.setBounds(170,r,50,20);
		objcheckCoordinatesy.addKeyListener(this);
		left.add(objcheckCoordinatesy);

		r+=30;

		JLabel lz=new JLabel("z:");
		lz.setBounds(5,r,20,20);
		left.add(lz);
		objcoordinatesz=new DoubleTextField(8);
		objcoordinatesz.setBounds(30,r,120,20);
		objcoordinatesz.addKeyListener(this);
		left.add(objcoordinatesz);
		objcheckCoordinatesz=new JCheckBox();
		objcheckCoordinatesz.setBounds(170,r,50,20);
		objcheckCoordinatesz.addKeyListener(this);
		left.add(objcheckCoordinatesz);

		r+=30;

		JLabel ldx=new JLabel("dx:");
		ldx.setBounds(5,r,20,20);
		left.add(ldx);
		objcoordinatesdx=new DoubleTextField(8);
		objcoordinatesdx.setBounds(30,r,120,20);
		objcoordinatesdx.addKeyListener(this);
		left.add(objcoordinatesdx);
		objcheckCoordinatesdx=new JCheckBox();
		objcheckCoordinatesdx.setBounds(170,r,50,20);
		objcheckCoordinatesdx.addKeyListener(this);
		left.add(objcheckCoordinatesdx);

		r+=30;

		JLabel ldy=new JLabel("dy:");
		ldy.setBounds(5,r,20,20);
		left.add(ldy);
		objcoordinatesdy=new DoubleTextField(8);
		objcoordinatesdy.setBounds(30,r,120,20);
		objcoordinatesdy.addKeyListener(this);
		left.add(objcoordinatesdy);
		objcheckCoordinatesdy=new JCheckBox();
		objcheckCoordinatesdy.setBounds(170,r,50,20);
		objcheckCoordinatesdy.addKeyListener(this);
		left.add(objcheckCoordinatesdy);

		r+=30;

		JLabel ldz=new JLabel("dz:");
		ldz.setBounds(5,r,20,20);
		left.add(ldz);
		objcoordinatesdz=new DoubleTextField(8);
		objcoordinatesdz.setBounds(30,r,120,20);
		objcoordinatesdz.addKeyListener(this);
		left.add(objcoordinatesdz);
		objcheckCoordinatesdz=new JCheckBox();
		objcheckCoordinatesdz.setBounds(170,r,50,20);
		objcheckCoordinatesdz.addKeyListener(this);
		left.add(objcheckCoordinatesdz);
		
		if(DrawObject.IS_3D){
			
			objcoordinatesdx.setEnabled(false);
			objcoordinatesdy.setEnabled(false);
			objcoordinatesdz.setEnabled(false);
			objcheckCoordinatesdx.setEnabled(false);
			objcheckCoordinatesdy.setEnabled(false);
			objcheckCoordinatesdz.setEnabled(false);
		}

		r+=30;


		chooseObject=new JComboBox();
		chooseObject.addItem(new ValuePair("",""));
		//chooseObject.setBounds(50,r,50,20);
		chooseObject.addItemListener(this);
		chooseObject.addKeyListener(this);
		//left.add(chooseObject);
		
		
		chooseObjectPanel=new JButton("Object");
		chooseObjectPanel.setBounds(10,r,100,20);
		chooseObjectPanel.addActionListener(this);
		chooseObjectPanel.addKeyListener(this);
		left.add(chooseObjectPanel);

		r+=30;

		objectLabel=new JLabel();
		objectLabel.setFocusable(false);
		objectLabel.setBounds(10,r,100,100);

		Border border=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		objectLabel.setBorder(border);
		left.add(objectLabel);
		
		JPanel moveObject=buildObjectMovePanel(120,r);
		left.add(moveObject);

		r+=100;

		choosePrevObject=new JButton("<");
		choosePrevObject.setBounds(10,r,50,20);
		choosePrevObject.addActionListener(this);
		choosePrevObject.addKeyListener(this);
		left.add(choosePrevObject);

		chooseNextObject=new JButton(">");
		chooseNextObject.setBounds(60,r,50,20);
		chooseNextObject.addActionListener(this);
		chooseNextObject.addKeyListener(this);
		left.add(chooseNextObject);		

		r+=30;
		
		colorObjChoice=new JTextField();
		colorObjChoice.setBounds(30,r,150,20);
		colorObjChoice.addKeyListener(this);
		colorObjChoice.setToolTipText("Opt. background color");
		left.add(colorObjChoice);
		JButton cho = new JButton(">");
		cho.setBorder(new LineBorder(Color.gray,1));
		cho.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						Color tcc = JColorChooser.showDialog(null,"Choose color",null);
						if(tcc!=null) {
							colorObjChoice.setBackground(tcc);
						}

					}


				}
		);
		cho.addKeyListener(this);
		cho.setBounds(5,r,20,20);
		left.add(cho);
		checkObjColor=new JCheckBox();
		checkObjColor.setBounds(200,r,50,20);
		checkObjColor.addKeyListener(this);
		checkObjColor.setOpaque(false);
		left.add(checkObjColor);

		r+=30;

		changeObject=new JButton(header+"Change O<u>b</u>ject"+footer);
		changeObject.addActionListener(this);
		changeObject.setFocusable(false);
		changeObject.setBounds(5,r,150,20);
		left.add(changeObject);

		r+=30;

		addObject=new JButton(header+"<u>I</u>nsert object"+footer);
		addObject.addActionListener(this);
		addObject.setFocusable(false);
		addObject.setBounds(5,r,150,20);
		left.add(addObject);

		r+=30;

		delObject=new JButton("Del object");
		delObject.addActionListener(this);
		delObject.setFocusable(false);
		delObject.setBounds(5,r,150,20);
		left.add(delObject);

		r+=30;
		
		deselectAllObjects=new JButton("Deselect all");
		deselectAllObjects.addActionListener(this);
		deselectAllObjects.setFocusable(false);
		deselectAllObjects.setBounds(5,r,150,20);
		left.add(deselectAllObjects);

		r+=30;

		add(left);

	}

	private void buildRightPanel(int index) {

		String header="<html><body>";
		String footer="</body></html>";
		
		JPanel panel=new JPanel();

		panel.setBounds(WIDTH+LEFT_BORDER,0,RIGHT_BORDER,HEIGHT);
		panel.setLayout(null);
		
		Border leftBorder=BorderFactory.createTitledBorder(panelsTitles[index]);
		panel.setBorder(leftBorder);

		int r=25;

		checkMultiplePointsSelection[index]=new JCheckBox("Multiple selection");
		checkMultiplePointsSelection[index].setBounds(30,r,150,20);
		checkMultiplePointsSelection[index].addKeyListener(this);
		panel.add(checkMultiplePointsSelection[index]);

		r+=30;

		JLabel lx=new JLabel("x:");
		lx.setBounds(5,r,20,20);
		panel.add(lx);
		coordinatesx[index]=new DoubleTextField(8);
		coordinatesx[index].setBounds(30,r,120,20);
		coordinatesx[index].addKeyListener(this);
		panel.add(coordinatesx[index]);
		checkCoordinatesx[index]=new JCheckBox();
		checkCoordinatesx[index].setBounds(180,r,30,20);
		checkCoordinatesx[index].addKeyListener(this);
		panel.add(checkCoordinatesx[index]);

		r+=30;

		JLabel ly=new JLabel("y:");
		ly.setBounds(5,r,20,20);
		panel.add(ly);
		coordinatesy[index]=new DoubleTextField(8);
		coordinatesy[index].setBounds(30,r,120,20);
		coordinatesy[index].addKeyListener(this);
		panel.add(coordinatesy[index]);
		checkCoordinatesy[index]=new JCheckBox();
		checkCoordinatesy[index].setBounds(180,r,30,20);
		checkCoordinatesy[index].addKeyListener(this);
		panel.add(checkCoordinatesy[index]);

		r+=30;

		JLabel lz=new JLabel("z:");
		lz.setBounds(5,r,20,20);
		panel.add(lz);
		coordinatesz[index]=new DoubleTextField(8);
		coordinatesz[index].setBounds(30,r,120,20);
		coordinatesz[index].addKeyListener(this);
		panel.add(coordinatesz[index]);
		checkCoordinatesz[index]=new JCheckBox();
		checkCoordinatesz[index].setBounds(180,r,30,20);
		checkCoordinatesz[index].addKeyListener(this);
		panel.add(checkCoordinatesz[index]);

		r+=30;



		chooseTexture[index]=new JComboBox();
		chooseTexture[index].addItem(new ValuePair("",""));
		/*chooseTexture.setBounds(35,r,50,20);*/
		chooseTexture[index].addItemListener(this);
		chooseTexture[index].addKeyListener(this);
		/*panel.add(chooseTexture);*/

		choosePanelTexture[index]=new JButton("Texture");
		choosePanelTexture[index].setBounds(5,r,100,20);
		choosePanelTexture[index].addActionListener(this);
		choosePanelTexture[index].addKeyListener(this);
		panel.add(choosePanelTexture[index]);
		
		r+=30;

		textureLabel[index]=new JLabel();
		textureLabel[index].setFocusable(false);
		textureLabel[index].setBounds(5,r,100,100);
		Border border=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		textureLabel[index].setBorder(border);
		panel.add(textureLabel[index]);

		JPanel moveRoad=buildRoadMovePanel(120,r,index);
		panel.add(moveRoad);

		r+=100;
		
		choosePrevTexture[index]=new JButton("<");
		choosePrevTexture[index].setBounds(5,r,50,20);
		choosePrevTexture[index].addActionListener(this);
		choosePrevTexture[index].addKeyListener(this);
		panel.add(choosePrevTexture[index]);

		chooseNextTexture[index]=new JButton(">");
		chooseNextTexture[index].setBounds(55,r,50,20);
		chooseNextTexture[index].addActionListener(this);
		chooseNextTexture[index].addKeyListener(this);
		panel.add(chooseNextTexture[index]);
		
	
		
		r+=30;

		colorRoadChoice[index]=new JTextField();
		colorRoadChoice[index].setBounds(30,r,120,20);
		colorRoadChoice[index].addKeyListener(this);
		colorRoadChoice[index].setToolTipText("Opt. background color");
		panel.add(colorRoadChoice[index]);
		
		final JTextField crc=colorRoadChoice[index];
		
		JButton cho = new JButton(">");
		cho.setBorder(new LineBorder(Color.gray,1));
		cho.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						Color tcc = JColorChooser.showDialog(null,"Choose color",null);
						if(tcc!=null) {
							crc.setBackground(tcc);
						}

					}


				}
		);
		cho.addKeyListener(this);
		cho.setBounds(5,r,20,20);
		panel.add(cho);
		checkRoadColor[index]=new JCheckBox();
		checkRoadColor[index].setBounds(180,r,30,20);
		checkRoadColor[index].addKeyListener(this);
		checkRoadColor[index].setOpaque(false);
		panel.add(checkRoadColor[index]);

		r+=30;

		addPoint[index]=new JButton(header+"Insert point"+footer);
		addPoint[index].addActionListener(this);
		addPoint[index].setFocusable(false);
		addPoint[index].setBounds(5,r,150,20);
		panel.add(addPoint[index]);

		r+=30;

		changePoint[index]=new JButton(header+"Change <u>P</u>oint"+footer);
		changePoint[index].addActionListener(this);
		changePoint[index].setFocusable(false);
		changePoint[index].setBounds(5,r,150,20);
		panel.add(changePoint[index]);

		r+=30;
		

		changePolygon[index]=new JButton(header+"Change Pol<u>y</u>gon"+footer);
		changePolygon[index].addActionListener(this);
		changePolygon[index].setFocusable(false);
		changePolygon[index].setBounds(5,r,150,20);
		panel.add(changePolygon[index]);

		r+=30;
		
		startBuildPolygon[index]=new JButton(header+"Start polygo<u>n</u> <br/> points sequence"+footer);
		startBuildPolygon[index].addActionListener(this);
		startBuildPolygon[index].addKeyListener(this);
		startBuildPolygon[index].setFocusable(false);
		startBuildPolygon[index].setBounds(5,r,150,35);
		panel.add(startBuildPolygon[index]);

		r+=45;
			
		buildPolygon[index]=new JButton(header+"Bui<u>l</u>d polygon"+footer);
		buildPolygon[index].addActionListener(this);
		buildPolygon[index].addKeyListener(this);
		buildPolygon[index].setFocusable(false);
		buildPolygon[index].setBounds(5,r,150,20);
		panel.add(buildPolygon[index]);
		

		r+=30;
		
		polygonDetail[index]=new JButton(header+"Polygon detail"+footer);
		polygonDetail[index].addActionListener(this);
		polygonDetail[index].addKeyListener(this);
		polygonDetail[index].setFocusable(false);
		polygonDetail[index].setBounds(5,r,150,20);
		panel.add(polygonDetail[index]);
		

		r+=30;

	

		deselectAll[index]=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAll[index].addActionListener(this);
		deselectAll[index].setFocusable(false);
		deselectAll[index].setBounds(5,r,150,20);
		panel.add(deselectAll[index]);
		
		
		r+=30;
		
		deleteSelection[index]=new JButton(header+"<u>D</u>elete selection"+footer);
		deleteSelection[index].addActionListener(this);
		deleteSelection[index].addKeyListener(this);
		deleteSelection[index].setFocusable(false);
		deleteSelection[index].setBounds(5,r,150,20);
		panel.add(deleteSelection[index]);

		r+=30;
		
		mergeSelectedPoints[index]=new JButton(header+"<u>M</u>erge selected<br/>points"+footer);
		mergeSelectedPoints[index].addActionListener(this);
		mergeSelectedPoints[index].addKeyListener(this);
		mergeSelectedPoints[index].setFocusable(false);
		mergeSelectedPoints[index].setBounds(5,r,150,35);
		panel.add(mergeSelectedPoints[index]);

		
		right.add(panelsTitles[index],panel);


	}

	
	

	private JPanel buildRoadMovePanel(int i, int r, int index) {

		JPanel move=new JPanel();
		move.setBounds(i,r,100,100);
		move.setLayout(null);

		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);
		
		roadMove[index]=new DoubleTextField();
		roadMove[index].setBounds(30,40,40,20);
		roadMove[index].setToolTipText("Position increment");
		move.add(roadMove[index]);
		roadMove[index].addKeyListener(this);
		
		moveRoadUp[index]=new JButton(new ImageIcon("lib/trianglen.jpg"));
		moveRoadUp[index].setBounds(40,10,20,20);
		moveRoadUp[index].addActionListener(this);
		moveRoadUp[index].setFocusable(false);
		move.add(moveRoadUp[index]);
		
		moveRoadDown[index]=new JButton(new ImageIcon("lib/triangles.jpg"));
		moveRoadDown[index].setBounds(40,70,20,20);
		moveRoadDown[index].addActionListener(this);
		moveRoadDown[index].setFocusable(false);
		move.add(moveRoadDown[index]);
		
		moveRoadLeft[index]=new JButton(new ImageIcon("lib/triangleo.jpg"));
		moveRoadLeft[index].setBounds(5,40,20,20);
		moveRoadLeft[index].addActionListener(this);
		moveRoadLeft[index].setFocusable(false);
		move.add(moveRoadLeft[index]);
		
		moveRoadRight[index]=new JButton(new ImageIcon("lib/trianglee.jpg"));
		moveRoadRight[index].setBounds(75,40,20,20);
		moveRoadRight[index].addActionListener(this);
		moveRoadRight[index].setFocusable(false);
		move.add(moveRoadRight[index]);
		
		moveRoadTop[index]=new JButton(new ImageIcon("lib/up.jpg"));
		moveRoadTop[index].setBounds(5,70,20,20);
		moveRoadTop[index].addActionListener(this);
		moveRoadTop[index].setFocusable(false);
		move.add(moveRoadTop[index]);
		
		moveRoadBottom[index]=new JButton(new ImageIcon("lib/down.jpg"));
		moveRoadBottom[index].setBounds(75,70,20,20);
		moveRoadBottom[index].addActionListener(this);
		moveRoadBottom[index].setFocusable(false);
		move.add(moveRoadBottom[index]);

		return move;

	}

	private JPanel buildObjectMovePanel(int i, int r) {

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
		
		moveObjUp=new JButton(new ImageIcon("lib/trianglen.jpg"));
		moveObjUp.setBounds(40,10,20,20);
		moveObjUp.addActionListener(this);
		moveObjUp.setFocusable(false);
		move.add(moveObjUp);
		
		moveObjDown=new JButton(new ImageIcon("lib/triangles.jpg"));
		moveObjDown.setBounds(40,70,20,20);
		moveObjDown.addActionListener(this);
		moveObjDown.setFocusable(false);
		move.add(moveObjDown);
		
		moveObjLeft=new JButton(new ImageIcon("lib/triangleo.jpg"));
		moveObjLeft.setBounds(5,40,20,20);
		moveObjLeft.addActionListener(this);
		moveObjLeft.setFocusable(false);
		move.add(moveObjLeft);
		
		moveObjRight=new JButton(new ImageIcon("lib/trianglee.jpg"));
		moveObjRight.setBounds(75,40,20,20);
		moveObjRight.addActionListener(this);
		moveObjRight.setFocusable(false);
		move.add(moveObjRight);
		
		moveObjTop=new JButton(new ImageIcon("lib/up.jpg"));
		moveObjTop.setBounds(5,70,20,20);
		moveObjTop.addActionListener(this);
		moveObjTop.setFocusable(false);
		move.add(moveObjTop);
		
		moveObjBottom=new JButton(new ImageIcon("lib/down.jpg"));
		moveObjBottom.setBounds(75,70,20,20);
		moveObjBottom.addActionListener(this);
		moveObjBottom.setFocusable(false);
		move.add(moveObjBottom);

		return move;

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
		screenPoint.setBounds(120,2,300,20);
		bottom.add(screenPoint);
		add(bottom);
	}

	private void undoObjects() {


		drawObjects=(Vector) oldObjects.pop();
		if(oldObjects.size()==0)
			jmtUndoObjects.setEnabled(false);
	}

	private void undoRoad() {

		super.undo();
		
		firePropertyChange("RoadEditorUndo", false, true);
	}

	public void prepareUndo() {
		prepareUndoObjects();
		prepareUndoRoad();
	}

	private void prepareUndoRoad() {
		jmtUndoRoad.setEnabled(true);
		
		super.prepareUndo();


	}

	private void prepareUndoObjects() {
		jmtUndoObjects.setEnabled(true);
		if(oldObjects.size()==MAX_STACK_SIZE){
			oldObjects.removeElementAt(0);
		}
		oldObjects.push(cloneObjectsVector(drawObjects));
	}



	private Vector cloneObjectsVector(Vector drawObjects) {
		Vector newDrawObjects=new Vector();

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			newDrawObjects.add(dro.clone());

		}

		return newDrawObjects;
	}

	private void changeSelectedObject() {
		
		prepareUndoObjects();

		for(int i=0;i<drawObjects.size();i++){
			
			DrawObject dro=(DrawObject) drawObjects.elementAt(i);

			if(!dro.isSelected())
				continue;

			(dro).setX(Double.parseDouble(objcoordinatesx.getText()));
			(dro).setY(Double.parseDouble(objcoordinatesy.getText()));
			(dro).setZ(Double.parseDouble(objcoordinatesz.getText()));
			
			if(!DrawObject.IS_3D){
				
				(dro).setDx(Double.parseDouble(objcoordinatesdx.getText()));
				(dro).setDy(Double.parseDouble(objcoordinatesdy.getText()));
				(dro).setDz(Double.parseDouble(objcoordinatesdz.getText()));
			 
			}
			ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
			if(vp!=null && !vp.getValue().equals("")){
				
				int index=Integer.parseInt(vp.getId());
				 dro.setIndex(index);
				 
				 if(DrawObject.IS_3D){
					 
				     CubicMesh cm=objectMeshes[index]; 
				     dro.setDx(cm.getDeltaX2()-cm.getDeltaX());
				     dro.setDy(cm.getDeltaY2()-cm.getDeltaY());
				     dro.setDz(cm.getDeltaX());
				 }
			}	 
			dro.setHexColor(ZBuffer.fromColorToHex(colorObjChoice.getBackground()));

			dro.setSelected(false);
		}

		cleanObjects();
		displayAll();

	}
	

	private void moveSelectedObject(int dx, int dy, int dk) { 
		
		String sqty=objMove.getText();
		
		if(sqty==null || sqty.equals(""))
			return;
		
		double qty=Double.parseDouble(sqty);
		
		prepareUndoObjects();
		
		for(int i=0;i<drawObjects.size();i++){
			
			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			
			if(!dro.isSelected())
				continue;

			dro.setX(dro.getX()+dx*qty);
			dro.setY(dro.getY()+dy*qty);
			dro.setZ(dro.getZ()+dk*qty);
			//dro.setSelected(false);
		}

		//cleanObjects();
		displayAll();
		
	}

	private void changeSelectedRoadPoint() {

		prepareUndoRoad();

		int size=points[ACTIVE_PANEL].size();
		
		for(int j=0;j<size;j++){


			    Point4D p=(Point4D) points[ACTIVE_PANEL].elementAt(j);

				if(p.isSelected()){

					if(!"".equals(coordinatesx[ACTIVE_PANEL].getText()))
						p.x=Double.parseDouble(coordinatesx[ACTIVE_PANEL].getText());
					if(!"".equals(coordinatesy[ACTIVE_PANEL].getText()))
						p.y=Double.parseDouble(coordinatesy[ACTIVE_PANEL].getText());
					if(!"".equals(coordinatesz[ACTIVE_PANEL].getText()))
						p.z=Double.parseDouble(coordinatesz[ACTIVE_PANEL].getText());

					p.setHexColor(ZBuffer.fromColorToHex(colorRoadChoice[ACTIVE_PANEL].getBackground()));

					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
					if(!vp.getId().equals(""))
						p.setIndex(Integer.parseInt(vp.getId()));

					p.setSelected(false);
				}

		}	
        firePropertyChange("RoadEditorUpdate", false, true);

		cleanPoints();
		displayAll();
	}
	
	private void changeSelectedRoadPolygon() {
		
		prepareUndoRoad();
		
		int sizel=lines[ACTIVE_PANEL].size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(j);
		    
		    
		    if(ld.isSelected){
		    	
		    	int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
		    	
		    	if(indx!=0){
	
					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(indx);
						
						
					ld.setTexture_index(Integer.parseInt(vp.getId()));
				
		    	}
		    	ld.setHexColor(ZBuffer.fromColorToHex(colorRoadChoice[ACTIVE_PANEL].getBackground()));
		    	
		    	ld.setSelected(false);
		    	
		    }
			
		}	
		
	}
	
	private void invertSelectedRoadPolygon() {
		
		prepareUndoRoad();
		
		int sizel=lines[ACTIVE_PANEL].size();
		for(int i=0;i<sizel;i++){
			
			
			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(i);
		    
		    
		    if(ld.isSelected){
		    	
		    	LineData invertedLd=new LineData();
				
				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				lines[ACTIVE_PANEL].setElementAt(invertedLd,i);
		    	
		    }
			
		}	
		
	}

	private void mergeSelectedPoints() {
	
		prepareUndoRoad();
		
		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		
		int firstPoint=-1;
		
		for(int i=0;i<points[ACTIVE_PANEL].size();i++){

			Point3D p=(Point3D) points[ACTIVE_PANEL].elementAt(i);
			if(!p.isSelected()) 
				newPoints.add(p);
			else if(firstPoint==-1){
				firstPoint=newPoints.size();
				newPoints.add(p);
			}
			else{
				
				
				
			}
				

		}


		for(int i=0;i<lines[ACTIVE_PANEL].size();i++){

			LineData ld=(LineData)lines[ACTIVE_PANEL].elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			newLd.setTexture_index(ld.getTexture_index());
			boolean insertedFirst=false;

			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) points[ACTIVE_PANEL].elementAt(ld.getIndex(j));
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

		points[ACTIVE_PANEL]=newPoints;
		lines[ACTIVE_PANEL]=newLines;
        deselectAll();
		
	
	}
	

	private void deleteSelection() {
		
		prepareUndo();
		
		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		int size=points[ACTIVE_PANEL].size();
		
		for(int i=0;i<size;i++){

			Point3D p=(Point3D) points[ACTIVE_PANEL].elementAt(i);
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		
		int sizel=lines[ACTIVE_PANEL].size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			boolean gotAllPoint=true;
			
			for(int j=0;j<ld.size();j++){

				Point3D p0=(Point3D) points[ACTIVE_PANEL].elementAt(ld.getIndex(j));
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
				
				newLd.setTexture_index(ld.getTexture_index());
				newLd.setHexColor(ld.getHexColor());

			}
			if(newLd.size()>1 && gotAllPoint)
				newLines.add(newLd);




		}

		points[ACTIVE_PANEL]=newPoints;
		lines[ACTIVE_PANEL]=newLines;
        deselectAll();
	
		
	}
	
	private void moveSelectedRoadPoints(int dx, int dy,int dk) {

		String sqty=roadMove[ACTIVE_PANEL].getText();

		if(sqty==null || sqty.equals(""))
			return;

		double qty=Double.parseDouble(sqty);



		prepareUndoRoad();

		int size=points[ACTIVE_PANEL].size();
		for(int j=0;j<size;j++){


			Point4D p=(Point4D) points[ACTIVE_PANEL].elementAt(j);

			if(p.isSelected()){


				p.x+=qty*dx;

				p.y+=qty*dy;

				p.z+=qty*dk;

				//p.setSelected(false);



			}
		}	
		firePropertyChange("RoadEditorUpdate", false, true);

		cleanPoints();
		displayAll();

	}

	private void deleteObject() {

		prepareUndoObjects();
		
		for(int i=0;i<drawObjects.size();i++){
			
			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			if(dro.isSelected())
				drawObjects.remove(dro);
		}

	}


	private void addObject() {
	
		double x=objcoordinatesx.getvalue();
		double y=objcoordinatesy.getvalue();
		double z=objcoordinatesz.getvalue();
		
		cleanObjects();
		int index=0;
		ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
		if(vp!=null && !vp.getValue().equals(""))
			index=Integer.parseInt(vp.getId());
		
		int dim_x=objectMeshes[index].getDeltaX2()-objectMeshes[index].getDeltaX();
		int dim_y=objectMeshes[index].getDeltaY2()-objectMeshes[index].getDeltaY();
		int dim_z=objectMeshes[index].getDeltaX();
		
		addObject(x,y,z,dim_x,dim_y,dim_z,index);

	}

	private void addObject(double x, double y, double z, int dx, int dy, int dz,int index) {

		prepareUndoObjects();

		DrawObject dro=new DrawObject();
		dro.setIndex(index);
		dro.x=x;
		dro.y=y;
		dro.z=z;
		dro.dx=dx;
		dro.dy=dy;
		dro.dz=dz;
		dro.setHexColor("FFFFFF");


		if(!"".equals(objcoordinatesx.getText()))
			dro.x=Double.parseDouble(objcoordinatesx.getText());
		if(!"".equals(objcoordinatesy.getText()))
			dro.y=Double.parseDouble(objcoordinatesy.getText());
		if(!"".equals(objcoordinatesz.getText()))
			dro.z=Double.parseDouble(objcoordinatesz.getText());
		if(!"".equals(objcoordinatesdx.getText()))
			dro.dx=Double.parseDouble(objcoordinatesdx.getText());
		if(!"".equals(objcoordinatesdy.getText()))
			dro.dy=Double.parseDouble(objcoordinatesdy.getText());
		if(!"".equals(objcoordinatesdz.getText()))
			dro.dz=Double.parseDouble(objcoordinatesdz.getText());

		ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
		if(vp!=null && !vp.getValue().equals("")){
			
			
			dro.index=Integer.parseInt(vp.getId());
		
		
		}	
		if(DrawObject.IS_3D){
			
			
			CubicMesh mesh=objectMeshes[dro.index].clone();
			dro.setMesh(mesh);
		}
		dro.setHexColor(ZBuffer.fromColorToHex(colorObjChoice.getBackground()));

		drawObjects.add(dro);


	}



	public void saveObjects() throws FileNotFoundException{
		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save objects");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			PrintWriter pr = new PrintWriter(new FileOutputStream(file));
			saveObjects(pr);
			pr.close(); 

		} 
	}

	private void saveObjects(PrintWriter pr) {
		
		try {

            pr.println("<objects>");			 
			for(int i=0;i<drawObjects.size();i++){

				DrawObject dro=(DrawObject) drawObjects.elementAt(i);
				String str=dro.getX()+"_"+dro.getY()+"_"+dro.getZ()+"_"+
				dro.getDx()+"_"+dro.getDy()+"_"+dro.getDz()+"_"+dro.getIndex()+"_"+dro.getHexColor();
				pr.println(str);
			}
			pr.println("</objects>");
				

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void loadObjectsFromFile(){	

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load objects ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			loadObjectsFromFile(file);


		}
	}
	
	

	private void saveLandscape()  {
		
		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save landscape");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);
		
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			
			try{			
				PrintWriter pr = new PrintWriter(new FileOutputStream(file));
				
				setACTIVE_PANEL(0);
				saveLines(pr);
				setACTIVE_PANEL(1);
				saveLines(pr);
				
				setACTIVE_PANEL(right.getSelectedIndex());
				
				saveObjects(pr);
				
				pr.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}



	private void loadLanscape() {
		
		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load landscape ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			
			setACTIVE_PANEL(0);
			loadPointsFromFile(file);	
			setACTIVE_PANEL(1);
			loadPointsFromFile(file);			
			setACTIVE_PANEL(0);
			
			right.setSelectedIndex(0);
			
            loadObjectsFromFile(file); 

		}
		
	}

	
	public void buildPoints(Vector points, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			Point4D p=new Point4D();
			
			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			points.add(p);
		}




	}
	
	public String decomposeLineData(LineData ld) {

		String str="";
		
		str+="T"+ld.texture_index;
		str+=",C"+ld.getHexColor();

		for(int j=0;j<ld.size();j++){
			
			
			
			str+=",";
			
			str+=ld.getIndex(j);

		}

		return str;
	}
	
	public void buildLines(Vector lines, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			LineData ld=new LineData();

			ld.texture_index=Integer.parseInt(vals[0].substring(1));
			
			ld.hexColor=vals[1].substring(1);
			
			for(int i=2;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			lines.add(ld);
		}




	}
	


	public void loadObjectsFromFile(File file){

		oldRoads=new Stack();
		drawObjects=new Vector();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));

			boolean read=false;

			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf("objects")>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				
				DrawObject dro=buildDrawObject(str);
				drawObjects.add(dro);

				if(DrawObject.IS_3D){
					CubicMesh mesh=objectMeshes[dro.getIndex()].clone();
					dro.setMesh(mesh);
				}
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}



	private DrawObject buildDrawObject(String str) {
		DrawObject dro=new DrawObject();

		StringTokenizer tok=new StringTokenizer(str,"_");
		dro.x=Double.parseDouble(tok.nextToken());
		dro.y=Double.parseDouble(tok.nextToken());
		dro.z=Double.parseDouble(tok.nextToken());
		dro.dx=Double.parseDouble(tok.nextToken());
		dro.dy=Double.parseDouble(tok.nextToken());
		dro.dz=Double.parseDouble(tok.nextToken());
		dro.index=Integer.parseInt(tok.nextToken());
		dro.hexColor=tok.nextToken();
		return dro;
	}



	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==jmt_load_landscape){
			
				loadLanscape();
		}
		else if(o==jmt_save_landscape){
			
				saveLandscape();

		}		
		else if(o==jmtIsUSeTextures){

			isUseTextures=jmtIsUSeTextures.isSelected();
			displayAll();
			

		}
		else if(o==jmtUndoObjects){
			undoObjects();
		}
		else if(o==jmtUndoRoad){
			undoRoad();
		}
		else if(o==jmtPreview){
			showPreview();
		}
		else if(o==jmtShowAltimetry){
			showAltimetry();
		}
		else if(o==jmtBuildNewGrid){
			buildNewGrid();
		}
		else if(o==jmtAddGrid){
			addGrid();
		}
		else if(o==jmtAddBendMesh){
			addBendMesh();
		}
		else if(o==addPoint[ACTIVE_PANEL]){
			addPoint();
			displayAll();
		}
		else if(o==mergeSelectedPoints[ACTIVE_PANEL]){			
			
			mergeSelectedPoints();
			displayAll();
		}
		else if(o==changePoint[ACTIVE_PANEL] ){
			changeSelectedRoadPoint();
			displayAll();
		}
		else if(o==changePolygon[ACTIVE_PANEL]){
			changeSelectedRoadPolygon();
			displayAll();
		}	
		else if(o==startBuildPolygon[ACTIVE_PANEL]){
			startBuildPolygon();
			
			displayAll();
		}	
		else if(o==buildPolygon[ACTIVE_PANEL]){
			buildPolygon();
			displayAll();
		}
		else if(o==polygonDetail[ACTIVE_PANEL]){
			polygonDetail();
			//displayAll();
		}
		else if(o==deleteSelection[ACTIVE_PANEL]){
			deleteSelection();
			displayAll();
		}
		else if(o==deselectAll[ACTIVE_PANEL]){
			deselectAll();
		}
		else if(o==addObject){
			addObject();
			displayAll();
		}
		else if(o==delObject){
			deleteObject();
			displayAll();
		}
		else if(o==changeObject){
			changeSelectedObject();
			displayAll();
		}
		else if(o==deselectAllObjects){
			cleanObjects();
		}
		else if(o==choosePanelTexture[ACTIVE_PANEL]){
			
			TexturesPanel tp=new TexturesPanel(worldImages,100,100);
			
			int indx=tp.getSelectedIndex();
			if(indx!=-1)
				chooseTexture[ACTIVE_PANEL].setSelectedIndex(indx+1);
			
		}
		else if(o==chooseNextTexture[ACTIVE_PANEL]){
			int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
			if(indx<chooseTexture[ACTIVE_PANEL].getItemCount()-1)
				chooseTexture[ACTIVE_PANEL].setSelectedIndex(indx+1);
		}
		else if(o==chooseObjectPanel){
			
			TexturesPanel tp=new TexturesPanel(objectImages,100,100);
			
			int indx=tp.getSelectedIndex();
			if(indx!=-1)
				chooseObject.setSelectedIndex(indx+1);
		}
		else if(o==choosePrevTexture[ACTIVE_PANEL]){
			int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
			if(indx>0)
				chooseTexture[ACTIVE_PANEL].setSelectedIndex(indx-1);
		}
		else if(o==chooseNextObject){
			int indx=chooseObject.getSelectedIndex();
			if(indx<chooseObject.getItemCount()-1)
				chooseObject.setSelectedIndex(indx+1);
		}
		else if(o==choosePrevObject){
			int indx=chooseObject.getSelectedIndex();
			if(indx>0)
				chooseObject.setSelectedIndex(indx-1);
		}
		else if(o==moveRoadUp[ACTIVE_PANEL]){

			moveSelectedRoadPoints(0,1,0);

		}
		else if(o==moveRoadDown[ACTIVE_PANEL]){

			moveSelectedRoadPoints(0,-1,0);

		}
		else if(o==moveRoadLeft[ACTIVE_PANEL]){

			moveSelectedRoadPoints(-1,0,0);

		}
		else if(o==moveRoadRight[ACTIVE_PANEL]){

			moveSelectedRoadPoints(+1,0,0);

		}
		else if(o==moveRoadTop[ACTIVE_PANEL]){

			moveSelectedRoadPoints(0,0,1);

		}
		else if(o==moveRoadBottom[ACTIVE_PANEL]){

			moveSelectedRoadPoints(0,0,-1);

		}
		else if(o==moveObjUp){

			moveSelectedObject(0,1,0);

		}
		else if(o==moveObjDown){

			moveSelectedObject(0,-1,0);

		}
		else if(o==moveObjLeft){

			moveSelectedObject(-1,0,0);

		}
		else if(o==moveObjRight){

			moveSelectedObject(+1,0,0);

		}
		else if(o==moveObjTop){

			moveSelectedObject(0,0,1);

		}
		else if(o==moveObjBottom){

			moveSelectedObject(0,0,-1);

		}
		else if(o==jmtLevelRoadTerrain){

			levelRoadTerrain(1);

		}
		else if(o==jmLevelTerrainRoad){

			levelRoadTerrain(-1);

		}
		
		
	}







	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent e) {
		redrawAfterMenu=true;

	}

    public void menuSelected(MenuEvent arg0) {
    	
    	super.menuSelected(arg0);
    	
    	Object o = arg0.getSource();
    	
		if(o==help_jm){
			
			help();
			
		}
    }
    
	private void help() {
	
		
		HelpPanel hp=new HelpPanel(300,200,this.getX()+100,this.getY(),HelpPanel.ROAD_EDITOR_HELP_TEXT,this);
		
	}

	public static void main(String[] args) {

		RoadEditor re=new RoadEditor("New road editor");
		re.initialize();
	}
	
	
	private void buildNewGrid() { 
		
		RoadEditorGridManager regm=new RoadEditorGridManager(null);
		
		if(regm.getReturnValue()!=null){
			
			RoadEditorGridManager roadEGM=(RoadEditorGridManager) regm.getReturnValue();
			


			
			double z_value=0;
			
			int numx=roadEGM.NX;
			int numy=roadEGM.NY;
			
			double dx=roadEGM.DX;
			double dy=roadEGM.DY;
			
			double x_0=roadEGM.X0;
			double y_0=roadEGM.Y0;
			
			int tot=numx*numy;
			
			points[ACTIVE_PANEL]=new Vector();
			lines[ACTIVE_PANEL]=new Vector();
			
			points[ACTIVE_PANEL].setSize(numy*numx);
			
			
			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{
					
					Point4D p=new Point4D(i*dx+x_0,j*dy+y_0,z_value);
				
					points[ACTIVE_PANEL].setElementAt(p,i+j*numx);

				}

			
			for(int i=0;i<numx-1;i++)
				for(int j=0;j<numy-1;j++){

	
					//lower base
					int pl1=i+numx*j;
					int pl2=i+numx*(j+1);
					int pl3=i+1+numx*(j+1);
					int pl4=i+1+numx*j;
					
					LineData ld=new LineData(pl1, pl4, pl3, pl2);
					
					if(ACTIVE_PANEL==1)
						ld.setTexture_index(0);
					else
						ld.setTexture_index(2);
					
					lines[ACTIVE_PANEL].add(ld);
					
					
					
				}
			
			
		}
		
		displayAll();
		
	}
	
	private void addGrid() { 
		
		RoadEditorGridManager regm=new RoadEditorGridManager(null);
		
		if(regm.getReturnValue()!=null){
			
			RoadEditorGridManager roadEGM=(RoadEditorGridManager) regm.getReturnValue();
			


			
			double z_value=0;
			
			int numx=roadEGM.NX;
			int numy=roadEGM.NY;
			
			double dx=roadEGM.DX;
			double dy=roadEGM.DY;
			
			double x_0=roadEGM.X0;
			double y_0=roadEGM.Y0;
			
			int tot=numx*numy;
			
			int sz=points[ACTIVE_PANEL].size();
			

			points[ACTIVE_PANEL].setSize(sz+numy*numx);
			
			
			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{
					
					Point4D p=new Point4D(i*dx+x_0,j*dy+y_0,z_value);
				
					points[ACTIVE_PANEL].setElementAt(p,sz+i+j*numx);

				}

			
			for(int i=0;i<numx-1;i++)
				for(int j=0;j<numy-1;j++){

	
					//lower base
					int pl1=sz+i+numx*j;
					int pl2=sz+i+numx*(j+1);
					int pl3=sz+i+1+numx*(j+1);
					int pl4=sz+i+1+numx*j;
					
					LineData ld=new LineData(pl1, pl4, pl3, pl2);
					
					if(ACTIVE_PANEL==1)
						ld.setTexture_index(0);
					else
						ld.setTexture_index(2);
					
					lines[ACTIVE_PANEL].add(ld);
					
					
					
				}
			
			
		}
		
		displayAll();
		
	}
	

	private void levelRoadTerrain(int action) {

		//jmtLevelRoadTerrain
		int from=1;
		int to=0;
		//jmtLevelTerrainRoad

		if(action<0){

			from=0;
			to=1;

		}	




		for (int i = 0; i < points[from].size(); i++) {

			Point3D point=(Point3D)points[from].elementAt(i);
			
			if(!point.isSelected)
				continue;
			
			

			int lsize=lines[to].size();


			for(int j=0;j<lsize;j++){


				LineData ld=(LineData) lines[to].elementAt(j);

				Polygon3D p3D=levelGetPolygon(ld,points[to]);

				if(p3D.contains(point.x,point.y)){
					
					int posz=(int)interpolate(point.x,point.y,p3D);
					point.z=posz;
					
				}


			} 


		}

	}
	
	protected double interpolate(double px, double py, Polygon3D p3d) {
	       
		/*Plain plain=Plain.calculatePlain(p3d);
		return plain.calculateZ(px,py);

		 */
		Polygon3D p1=Polygon3D.extractSubPolygon3D(p3d,3,0);

		if(p1.hasInsidePoint(px,py)){

			Plain plane1=Plain.calculatePlain(p1);

			return plane1.calculateZ(px,py);

		}

		Polygon3D p2=Polygon3D.extractSubPolygon3D(p3d,3,2);

		if(p2.hasInsidePoint(px,py)){

			Plain plane2=Plain.calculatePlain(p2);

			return plane2.calculateZ(px,py);

		}


		return 0;
	}
	

	private Polygon3D levelGetPolygon(LineData ld, Vector points) {
		
	

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points.elementAt(num);
			


			//bufGraphics.setColor(ZBuffer.fromHexToColor(is[i].getHexColor()));

			cx[i]=(int)p.x;
			cy[i]=(int)p.y;
			cz[i]=(int)p.z;

	

		}

		Polygon3D p_in=new Polygon3D(ld.size(),cx,cy,cz);
		
		return p_in;
	}

	private void addBendMesh() {
		
		if(ACTIVE_PANEL==0){
			
			JOptionPane.showMessageDialog(this,"Select road panel to add a bend");
			return;
		}
		
		
		prepareUndo();
		
		
		Point3D origin=null;
		int originPos=-1;
		
		int size=points[ACTIVE_PANEL].size();
		
		for(int i=0;i<size;i++){

			Point3D p=(Point3D) points[ACTIVE_PANEL].elementAt(i);
			if(p.isSelected()) 
				{	
					origin=p;
					originPos=i;
					break;
				
				}

		}
		
		if(origin==null){
			
			JOptionPane.showMessageDialog(this,"Select a center");
			return;
		}
		
		BendBuilder bb=new BendBuilder(origin);
		
		if(bb.getBendMesh()!=null){
			
			//////////////
			
			PolygonMesh bm=bb.getBendMesh();
			
			///delete points in the bend area
			
			double lineWidth=bb.getLineWidth();
			double innerRadius=bb.getInnerRadius();
			double teta=bb.getTeta();
			
			double sinTeta=Math.sin(teta);
			double cosTeta=Math.cos(teta);
			
			deselectAll();
			
			int prevOriginPos=originPos;
			
			for (int i = 0; i < points[ACTIVE_PANEL].size(); i++) {
				
				Point3D point = (Point3D) points[ACTIVE_PANEL].elementAt(i);
				double distance =Point3D.distance(point,origin);
				
				if(i==prevOriginPos)
					continue;
				
				double sinus=(point.y-origin.y)/distance;
				double cosinus=(point.x-origin.x)/distance;
				
						
				if( (sinus*sinTeta>=-.1 && cosinus*cosTeta>=-.1 ) 
						&& distance<=lineWidth+2*innerRadius){
					point.setSelected(true);
				    
					if(prevOriginPos>i)
						originPos--;
				}	
			}
			
			deleteSelection();
			
			///////////
			
			
			int base=points[ACTIVE_PANEL].size()-1;
			
			//do not recreate the origin!
			for (int i =1; i < bm.points.length; i++) {
				
				Point4D p=(Point4D) bm.points[i];
								
				points[ACTIVE_PANEL].add(p);
				
				
			}
			
			for (int i = 0; i < bm.polygonData.size(); i++) {
				LineData ld = (LineData) bm.polygonData.elementAt(i);
				
				for (int j = 0; j < ld.size(); j++) {
					
					int index=ld.getIndex(j);
					if(index==0)
						index=originPos;
					else
						index+=base;
					
					ld.setIndex(j,index);
				}
				
				lines[ACTIVE_PANEL].add(ld);
			}
			
			displayAll();
			
		}
		
	}
	
	public void buildPolygon() {
		
		prepareUndo();

		if(polygon.lineDatas.size()<3){
			deselectAll();
			return;
		}	
       lines[ACTIVE_PANEL].add(polygon);

		deselectAll();

	}
	
	private void showPreview() {
		if(points[ACTIVE_PANEL].size()==0)
			return;
		RoadEditorPreviewPanel preview=new RoadEditorPreviewPanel(this);
		
	}
	
	private void showAltimetry() {
		
		if(points[ACTIVE_PANEL].size()==0)
			return;
		RoadAltimetryPanel altimetry=new RoadAltimetryPanel(this);
	}


	public void mouseClicked(MouseEvent arg0) {

		int buttonNum=arg0.getButton();
		//right button click
		if(buttonNum==MouseEvent.BUTTON3)
			//addObject(arg0);
			addPoint(arg0);
		else{
			selectPoint(arg0.getX(),arg0.getY());			
		}	
		displayAll();
	}
	
	private void addPoint(MouseEvent arg0) {
		
		prepareUndo();
		
		Point p=arg0.getPoint();

		int x=invertX((int)p.getX());
		int y=invertY((int)p.getY());
		
		int index=0;
		ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
		if(vp!=null && !vp.getValue().equals(""))
			index=Integer.parseInt(vp.getId());
		else
			index=0;
		
		Point4D point=new Point4D(x,y,0,LineData.GREEN_HEX,index);
		points[ACTIVE_PANEL].add(point);
		

	}
	

	public void addPoint() {
		
		prepareUndo();
			

		if("".equals(coordinatesx[ACTIVE_PANEL].getText()) |
				"".equals(coordinatesy[ACTIVE_PANEL].getText()) |
				"".equals(coordinatesz[ACTIVE_PANEL].getText())
		)
			return;
		double x=Double.parseDouble(coordinatesx[ACTIVE_PANEL].getText());
		double y=Double.parseDouble(coordinatesy[ACTIVE_PANEL].getText());
		double z=Double.parseDouble(coordinatesz[ACTIVE_PANEL].getText());

		int index=0;
		ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
		if(vp!=null && !vp.getValue().equals(""))
			index=Integer.parseInt(vp.getId());
		else
			index=0;
		
		Point4D point=new Point4D(x,y,0,LineData.GREEN_HEX,index);
		points[ACTIVE_PANEL].add(point);

	}

	private void deselectAllPoints(){

		
		int size=points[ACTIVE_PANEL].size();
		for(int j=0;j<size;j++){


		    Point4D p=(Point4D) points[ACTIVE_PANEL].elementAt(j);

			p.setSelected(false);
				
		}	
		int sizel=lines[ACTIVE_PANEL].size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(j);
		    ld.setSelected(false);	
		}

	}

	private void deselectAllLines(){
		
		int sizel=lines[ACTIVE_PANEL].size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(j);
		    ld.setSelected(false);	
		}
	}


	private void selectPoint(int x, int y) {
		
		if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected()) 
			polygon=new LineData();

		//select point from road
		boolean found=false;
		
		int size=points[ACTIVE_PANEL].size();
		
		for(int j=0;j<size;j++){


		    Point4D p=(Point4D) points[ACTIVE_PANEL].elementAt(j);

				int xo=convertX(p.x);
				int yo=convertY(p.y);

				Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
				if(rect.contains(x,y)){

					if(!checkCoordinatesx[ACTIVE_PANEL].isSelected())
						coordinatesx[ACTIVE_PANEL].setText(p.x);
					if(!checkCoordinatesy[ACTIVE_PANEL].isSelected())
						coordinatesy[ACTIVE_PANEL].setText(p.y);
					if(!checkCoordinatesz[ACTIVE_PANEL].isSelected())
						coordinatesz[ACTIVE_PANEL].setText(p.z);
				

					p.setSelected(true);
					
					polygon.addIndex(j);

					found=true;

				}
				else if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected())
					p.setSelected(false);
			

		}
	
		//select object

		for(int i=0;i<drawObjects.size() && !found;i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			if(!checkMultipleObjectsSelection.isSelected())
				dro.setSelected(false);
			
			
			int[] cx=new int[4];
			int[] cy=new int[4];
			
			int versus=1;
			if(!DrawObject.IS_3D)
				versus=-1;

			cx[0]=convertX(dro.x);
			cy[0]=convertY(dro.y);
			cx[1]=convertX(dro.x);
			cy[1]=convertY(dro.y+versus*dro.dy);
			cx[2]=convertX(dro.x+dro.dx);
			cy[2]=convertY(dro.y+versus*dro.dy);
			cx[3]=convertX(dro.x+dro.dx);
			cy[3]=convertY(dro.y);

			Polygon p_in=new Polygon(cx,cy,4);
			
			if(p_in.contains(x,y))
			{
				dro.setSelected(true);
				if(!objcheckCoordinatesx.isSelected())
					objcoordinatesx.setText(""+dro.x);
				if(!objcheckCoordinatesy.isSelected())
					objcoordinatesy.setText(""+dro.y);
				if(!objcheckCoordinatesz.isSelected())
					objcoordinatesz.setText(""+dro.z);
				if(!objcheckCoordinatesdx.isSelected())
					objcoordinatesdx.setText(""+dro.dx);
				if(!objcheckCoordinatesdy.isSelected())
					objcoordinatesdy.setText(""+dro.dy);
				if(!objcheckCoordinatesdz.isSelected())
					objcoordinatesdz.setText(""+dro.dz);
				for(int k=0;k<chooseObject.getItemCount();k++){

					ValuePair vp=(ValuePair) chooseObject.getItemAt(k);
					if(vp.getId().equals(""+dro.index) )
						chooseObject.setSelectedItem(vp);
				}
				colorObjChoice.setBackground(ZBuffer.fromHexToColor(dro.hexColor));
				if(checkMultipleObjectsSelection.isSelected()){
					found=true;
					break;
				}	
			}

		}
		if(found){
			deselectAllLines();
			return;
		}
		
		//select polygon
		int sizel=lines[ACTIVE_PANEL].size();

		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) lines[ACTIVE_PANEL].elementAt(j);
		    Polygon3D pol=buildPolygon(ld,points[ACTIVE_PANEL],false);
		    
		    if(pol.contains(x,y)){
		    	
		    	found=true;
		    	
				for(int k=0;k<chooseTexture[ACTIVE_PANEL].getItemCount();k++){

					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(k);
					if(vp.getId().equals(""+ld.getTexture_index())) 
						chooseTexture[ACTIVE_PANEL].setSelectedItem(vp);
				}
				
				if(ld.hexColor!=null)
					colorRoadChoice[ACTIVE_PANEL].setBackground(ZBuffer.fromHexToColor(ld.hexColor));
		    	
		    	ld.setSelected(true);
		    	
		    }
			else if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected())
				ld.setSelected(false);
			
		}	
		if(!found)
			deselectAllPoints();
		

	}
	
	public Polygon3D buildPolygon(LineData ld,Vector points, boolean isReal) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points.elementAt(num);


			if(isReal){

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				
			}
			else {
				
				cxr[i]=convertX(p.x);
				cyr[i]=convertY(p.y);
				
			}
			


		}


		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

        return p3dr;

	}
	
	public void deselectAllObjects(){
		
		int size=drawObjects.size();
		
		for(int i=0;i<size;i++){
			
			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			dro.setSelected(false);
		}
	}

	public void cleanObjects(){
		
		if(!objcheckCoordinatesx.isSelected())	objcoordinatesx.setText("");
		if(!objcheckCoordinatesy.isSelected())objcoordinatesy.setText("");
		if(!objcheckCoordinatesz.isSelected())objcoordinatesz.setText("");
		
		if(!objcheckCoordinatesdx.isSelected())objcoordinatesdx.setText("");
		if(!objcheckCoordinatesdy.isSelected())objcoordinatesdy.setText("");
		if(!objcheckCoordinatesdz.isSelected())objcoordinatesdz.setText("");
		
		
		if(!checkObjColor.isSelected())checkObjColor.setBackground(ZBuffer.fromHexToColor("FFFFFF"));
		
		deselectAllObjects();
		displayAll();
	}
	
	public void cleanPoints(){
		
		if(!checkCoordinatesx[ACTIVE_PANEL].isSelected())coordinatesx[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesy[ACTIVE_PANEL].isSelected())coordinatesy[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesz[ACTIVE_PANEL].isSelected())coordinatesz[ACTIVE_PANEL].setText("");
		
		if(!checkRoadColor[ACTIVE_PANEL].isSelected())checkRoadColor[ACTIVE_PANEL].setBackground(ZBuffer.fromHexToColor("FFFFFF"));
	}
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void mousePressed(MouseEvent arg0) {
	
		  int x = arg0.getX();
	      int y = arg0.getY();
	      currentRect = new Rectangle(x, y, 0, 0);


	}








	private void selectPointsWithRectangle() {

		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);

		if(!checkCoordinatesx[ACTIVE_PANEL].isSelected())
			coordinatesx[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesy[ACTIVE_PANEL].isSelected())
			coordinatesy[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesz[ACTIVE_PANEL].isSelected())
			coordinatesz[ACTIVE_PANEL].setText("");

		//select point from road
		boolean found=false;

		int size=points[ACTIVE_PANEL].size();
		
		for(int j=0;j<size;j++){


			Point4D p=(Point4D) points[ACTIVE_PANEL].elementAt(j);

			int xo=convertX(p.x);
			int yo=convertY(p.y);


			if(xo>=x0 && xo<=x1 && yo>=y0 && yo<=y1  ){

				p.setSelected(true);
				found=true;


			}
			else if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected())
				p.setSelected(false);


		}
		if(found){
			deselectAllObjects();
		    deselectAllLines();  
		}
		

		

	}



	public void keyPressed(KeyEvent arg0) {

		int code =arg0.getKeyCode();
		if(code==KeyEvent.VK_DOWN )
			down();
		else if(code==KeyEvent.VK_UP  )
			up();
		else if(code==KeyEvent.VK_LEFT )
		{	
			MOVX=MOVX-10; 
			displayAll();
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	 
			MOVX=MOVX+10;   
			displayAll();
		}
		else if(code==KeyEvent.VK_D  )
		{	
			deleteSelection();
			displayAll();
		}
		else if(code==KeyEvent.VK_N  )
		{	
			startBuildPolygon();
			displayAll();
		}
		else if(code==KeyEvent.VK_P  )
		{	
			changeSelectedRoadPoint();
			displayAll();
		}
		else if(code==KeyEvent.VK_B  )
		{	
			changeSelectedObject();
			displayAll();
		}
		else if(code==KeyEvent.VK_I  )
		{ 
			addObject();
			displayAll();
		}
		else if(code==KeyEvent.VK_L  )
		{  
			buildPolygon();
			displayAll();
		}
		else if(code==KeyEvent.VK_Y  )
		{ 
			changeSelectedRoadPolygon();
			displayAll();
		}
		else if(code==KeyEvent.VK_E  )
		{ 
			deselectAll();
		}
		else if(code==KeyEvent.VK_F1  )
		{ 
			zoom(+1);
			displayAll();
		}
		else if(code==KeyEvent.VK_F2  )
		{  
			zoom(-1);
			displayAll();
		}
		else if(code==KeyEvent.VK_LESS )
		{	
			 
			invertSelectedRoadPolygon(); 
			displayAll();
		}

	}




	public void up(){
		MOVY=MOVY-10;
		displayAll();

	}

	public void down(){
		MOVY=MOVY+10;
		displayAll();

	}


	private void zoom(int i) {
		
		
		
		double alfa=1.0;
		if(i>0){
			alfa=0.5;
			
			if(dx==1 || dy==1)
				return;
		}
		else {
			alfa=2.0;
		      	
		}
			
				
		dx=(int) (dx*alfa);
		dy=(int) (dy*alfa);
		
		MOVX+=(int) ((WIDTH/2+MOVX)*(1.0/alfa-1.0));
		MOVY+=(int) ((-HEIGHT/2+MOVY)*(1.0/alfa-1.0));
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}





	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}





	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int pix=arg0.getUnitsToScroll();
		if(pix>0) up();
		else down();

	}





	public void mouseDragged(MouseEvent e) {
		
		
		isDrawCurrentRect=true;
		updateSize(e);
		displayAll();

	}
	
	private void drawCurrentRect(Graphics2D bufGraphics) {
		
		if(!isDrawCurrentRect)
			return;
		//System.out.println(isDrawCurrentRect);
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		
		bufGraphics.setColor(Color.WHITE);
		bufGraphics.drawRect(x0,y0,x1-x0,y1-y0);
		
	}

	
	public void mouseReleased(MouseEvent arg0) {
		
		isDrawCurrentRect=false;
		updateSize(arg0);
        selectPointsWithRectangle();
        displayAll();
       
	}

    void updateSize(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentRect.setSize(x - currentRect.x,
                            y - currentRect.y);
        
   	   
   
    }




	public void mouseMoved(MouseEvent e) {
		Point p=e.getPoint();
		screenPoint.setText(invertX((int)p.getX())+","+invertY((int)p.getY()));

	}

	public void itemStateChanged(ItemEvent arg0) {

		Object o=arg0.getSource();
		if(o==chooseTexture[ACTIVE_PANEL]){

			ValuePair val=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
			if(!val.getId().equals("")){

				int num=Integer.parseInt(val.getId());
				
				BufferedImage icon=new BufferedImage(100,100,BufferedImage.TYPE_3BYTE_BGR);
				icon.getGraphics().drawImage(worldImages[num],0,0,objectLabel.getWidth(),objectLabel.getHeight(),null);
				ImageIcon ii=new ImageIcon(icon);
				textureLabel[ACTIVE_PANEL].setIcon(ii);


			}
			else
				textureLabel[ACTIVE_PANEL].setIcon(null);

		}
		else if(o==chooseObject){

			ValuePair val=(ValuePair) chooseObject.getSelectedItem();
			if(!val.getId().equals("")){

				int num=Integer.parseInt(val.getId());
			 
				BufferedImage icon=new BufferedImage(100,100,BufferedImage.TYPE_3BYTE_BGR);
				icon.getGraphics().drawImage(objectImages[num],0,0,objectLabel.getWidth(),objectLabel.getHeight(),null);
				ImageIcon ii=new ImageIcon(icon);
				objectLabel.setIcon(ii);	
				
				int dim_x=objectMeshes[num].getDeltaX2()-objectMeshes[num].getDeltaX();
				int dim_y=objectMeshes[num].getDeltaY2()-objectMeshes[num].getDeltaY();
				int dim_z=objectMeshes[num].getDeltaX();
				
				objcoordinatesdx.setText(dim_x);
				objcoordinatesdy.setText(dim_y);
				objcoordinatesdz.setText(dim_z);

			}
			else
				{
				objectLabel.setIcon(null);
			     
				}
		}

	}
	
	public void propertyChange(PropertyChangeEvent arg0) {
		
		//System.out.println(arg0.getSource().getClass());
		if("paintDirtyRegions".equals(arg0.getPropertyName()) && redrawAfterMenu)
		{
			 displayAll();
			 redrawAfterMenu=false;
		}
		else if("roadUpdate".equals(arg0.getPropertyName()))
		{
			 displayAll();
		}
		
	}

	public class ValuePair{


		String id;
		String value;

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

		public ValuePair(String id, String value) {
			super();
			this.id = id;
			this.value = value;
		}


		public String toString() {

			return this.value;
		}


	}

	/**
	 * 
	 * old-> new road migration function 
	 * 
	 * @param fileOut
	 * @param NX
	 * @param NY
	 * @param roadData
	 */
	
	public void migrateRoadToNewFormat(File fileIn,File fileOut,Point4D[][] roadData ) {

		int NX=0;
		int NY=0;
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(fileIn));

			String snx=br.readLine();
			String sny=br.readLine();

			if(snx==null || sny==null) {

				br.close();
				return;
			}

			NX=Integer.parseInt(snx.substring(4));
			NY=Integer.parseInt(sny.substring(4));
			roadData=new Point4D[NY][NX];

			String str=null;
			int rows=0;
			while((str=br.readLine())!=null){

				roadData[rows]=buildRow(str,NX);

				rows++;	

			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		Vector points=new Vector();
		Vector lines=new Vector();
		
		points.setSize(NX*NY);
		
		
		for(int i=0;i<NX;i++)
			for(int j=0;j<NY;j++)
			{
				
				Point4D p=new Point4D(roadData[j][i].x,roadData[j][i].y,roadData[j][i].z,roadData[j][i].hexColor,roadData[j][i].getIndex());
			
				points.setElementAt(p,i+j*NX);

			}

		
		for(int i=0;i<NX-1;i++)
			for(int j=0;j<NY-1;j++){


				//lower base
				int pl1=i+NX*j;
				int pl2=i+NX*(j+1);
				int pl3=i+1+NX*(j+1);
				int pl4=i+1+NX*j;
									
				lines.add(new LineData(pl1, pl4, pl3, pl2));
				
			}


		PrintWriter pr;
		try {
			
			pr = new PrintWriter(new FileOutputStream(fileOut));
			pr.print("P=");

			int size=points.size();
			
			for(int i=0;i<size;i++){

				Point3D p=(Point3D) points.elementAt(i);

				pr.print(decomposePoint(p));
				if(i<size-1)
					pr.print("_");
			}	

			pr.print("\nL=");

			int sizel=lines.size();
			for(int i=0;i<sizel;i++){

				LineData ld=(LineData) lines.elementAt(i);
				
				Point4D p=(Point4D) points.elementAt(ld.getIndex(0));
				
				ld.setHexColor(p.getHexColor());
                ld.setTexture_index(p.getIndex());
				
				pr.print(decomposeLineData(ld));
				if(i<sizel-1)
					pr.print("_");
			}	

			pr.close(); 	
	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
	
	private Point4D[] buildRow(String string,int NX) {
		StringTokenizer stk=new StringTokenizer(string,"_");

		Point4D[] row = new Point4D[NX];
		int columns=0;
		while(stk.hasMoreTokens()){

			String[] vals=stk.nextToken().split(",");

			row[columns]=new Point4D();

			row[columns].x=Double.parseDouble(vals[0]);
			row[columns].y=Double.parseDouble(vals[1]);
			row[columns].z=Double.parseDouble(vals[2]);
			row[columns].setHexColor(vals[3]);
			row[columns].setIndex(Integer.parseInt(vals[4]));
			columns++;
		}

		return row;
	}



	public void setRoadData(String string, Vector lines, Vector points) {
		
		
		this.lines[ACTIVE_PANEL]=lines;
		this.points[ACTIVE_PANEL]=points;
		
		displayAll();
		
	}

}
