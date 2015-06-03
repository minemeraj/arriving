package com.editors.road;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.RepaintManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MenuEvent;

import com.BarycentricCoordinates;
import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Plain;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;
import com.SquareMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.EditorData;
import com.editors.ValuePair;
import com.editors.road.panel.RoadEditorIsoPanel;
import com.editors.road.panel.RoadEditorPanel;
import com.editors.road.panel.RoadEditorTopPanel;
import com.main.HelpPanel;
import com.main.Road;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class RoadEditor extends Editor implements ActionListener,MouseListener,MouseWheelListener,PropertyChangeListener,MouseMotionListener,KeyListener, ItemListener{


	//private JPanel center;
	int HEIGHT=680;
	int WIDTH=900;
	int LEFT_BORDER=240;
	int BOTTOM_BORDER=100;
	int RIGHT_SKYP=10;

	int MOVX=-50;
	int MOVY=100;

	int dx=2;
	int dy=2;

	int deltay=200;
	int deltax=200;

	public Vector drawObjects=new Vector();
	//Graphics2D g2;
	//Graphics2D g2Alias;
	Stack oldObjects=new Stack();
	Stack oldSpline=new Stack();
	int MAX_STACK_SIZE=10;


	private JFileChooser fc;
	private JMenuBar jmb;
	private JMenu jm_file;

	public DoubleTextField[] coordinatesx;
	public DoubleTextField[] coordinatesy;
	public DoubleTextField[] coordinatesz;
	public JCheckBox[] checkCoordinatesx;
	public JCheckBox[] checkCoordinatesy;
	public JCheckBox[] checkCoordinatesz;
	private JButton[] changePoint;
	private JButton[] mergeSelectedPoints;

	public JCheckBox[] checkMultiplePointsSelection;
	private JButton[] changePolygon;
	private JButton[] deleteSelection;
	private JButton[] polygonDetail;
	public JComboBox[] chooseTexture;
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
	
	private JButton delObject;
	private JMenu jm_editing;

	public boolean ISDEBUG=false;
	
	private JPanel bottom;
	private JLabel screenPoint;


	private JLabel objectLabel;
	private JComboBox chooseObject;
	private JButton chooseObjectPanel;
	private JButton choosePrevObject;
	private JButton chooseNextObject;
	public JCheckBox checkMultipleObjectsSelection;
	
	private JMenu jm4;
	private JMenuItem jmtUndoObjects;
	private JMenuItem jmtUndoSPLines;	
	private JPanel left;
	
	private JButton changeObject;


	private DoubleTextField rotation_angle;
	public Rectangle currentRect;
	private boolean isDrawCurrentRect=false;
	private DoubleTextField objMove;
	private JButton moveObjUp;
	private JButton moveObjDown;
	private JButton moveObjLeft;
	private JButton moveObjRight;
	private JButton moveObjTop;
	private JButton moveObjBottom;
	
	public static BufferedImage[] worldImages;	
	public static BufferedImage[] objectImages;
	public static Texture[] objectIndexes; 
	
	public int indexWidth=40;
	public int indexHeight=18;

	public static Color BACKGROUND_COLOR=new Color(0,0,0);
	private JMenuItem jmtShowAltimetry;
	private JMenuItem jmtBuildNewGrid;
	private JMenu help_jm;
	private JMenuItem jmt_load_landscape;
	private JMenuItem jmt_save_landscape;


	
	String[] panelsTitles={"Terrain","Road"};
	
	Color alphaRed=new Color(Color.RED.getRed(),0,0,100);
	private JMenuItem jmtAddGrid;
	
	public static ZBuffer landscapeZbuffer;
	public int blackRgb= Color.BLACK.getRGB();
	public int[] rgb=null;
	public Color selectionColor=null;
	private JMenuItem jmtExpandGrid;

	private JMenuItem jmtBuildCity;
	private JMenuItem help_jmt;
	
	String header="<html><body>";
	String footer="</body></html>";
	private JToggleButton toogle_splines;
	private JToggleButton toogle_terrain;
	private JToggleButton toogle_objects;
	private JPanel left_tools;
	private JPanel left_common_options;
	private JPanel left_tool_options;
	
	private JCheckBox checkHideObjects;
	
	public static String OBJECT_MODE="OBJECT_MODE";
	public static String SPLINES_MODE="SPLINES_MODE"; 
	public static String TERRAIN_MODE="TERRAIN_MODE";
	
	public String mode=TERRAIN_MODE;
	private JMenu jm_view;
	private JMenuItem jmt_3d_view;
	private JMenuItem jmt_top_view;
	
	int ISO_VIEW=1;
	int TOP_VIEW=0;

	int VIEW_TYPE=TOP_VIEW;
	
	private RoadEditorPanel panelIso;
	private RoadEditorPanel panelTop;
	
	BufferedImage buf=null;
	private Graphics2D graphics;
	private JButton startNewSPLine;
	private JButton insertSPNode;
	private JButton mergeSPNodes;
	

	

	public static void main(String[] args) {

		RoadEditor re=new RoadEditor("New road editor");
		re.initialize();
	}
	
	public RoadEditor(String title){
		
		setTitle(title);
		
		if(!DrawObject.IS_3D)
		   setTitle("Road editor 2D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocation(10,10);
		setSize(WIDTH+LEFT_BORDER+RIGHT_SKYP,HEIGHT+BOTTOM_BORDER);
		
		panelIso=getPanel3D();
		//panelIso.setTransferHandler(new FileTransferhandler());
		panelTop=getPanelTop();
		//panelTop.setTransferHandler(new FileTransferhandler());
		add(panelTop);
		
		
		/*center=new JPanel(){
			
			public void paint(Graphics g) {
				super.paint(g);
			
					displayAll();
			}
		};
		center.setBackground(BACKGROUND_COLOR);
		center.setBounds(LEFT_BORDER,0,WIDTH,HEIGHT);
		center.addMouseListener(this);
		center.addMouseWheelListener(this);
		center.addMouseMotionListener(this);*/
		
		
		
		addKeyListener(this);
		addPropertyChangeListener(this);
		
		
		buildMenuBar();
		buildLeftPanel();

		
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
	
	private RoadEditorPanel getPanel3D() {
		RoadEditorIsoPanel panel=new RoadEditorIsoPanel(this,WIDTH,HEIGHT);
		panel.setBounds(LEFT_BORDER,0,WIDTH,HEIGHT);
		panel.addKeyListener(this);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
		return panel;
	}

	private RoadEditorPanel getPanelTop() {
		RoadEditorTopPanel panel=new RoadEditorTopPanel(this,WIDTH,HEIGHT);
		panel.setBounds(LEFT_BORDER,0,WIDTH,HEIGHT);
		panel.addKeyListener(this);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
		return panel;
	}
	


	private void buildFieldsArrays() {

		coordinatesx=new DoubleTextField[numPanels];
		coordinatesy=new DoubleTextField[numPanels];
		coordinatesz=new DoubleTextField[numPanels];

		checkCoordinatesx=new JCheckBox[numPanels];
		checkCoordinatesy=new JCheckBox[numPanels];
		checkCoordinatesz=new JCheckBox[numPanels];

		changePoint=new JButton[numPanels];		
		checkMultiplePointsSelection= new JCheckBox[numPanels];
		mergeSelectedPoints=new JButton[numPanels];
		changePolygon=new JButton[numPanels];	
		deleteSelection=new JButton[numPanels];	
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

		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

		landscapeZbuffer=new ZBuffer(WIDTH*HEIGHT);
		rgb=new int[WIDTH*HEIGHT];
		buildNewZBuffers();
		selectionColor=new Color(255,0,0,127);

		//g2=(Graphics2D) center.getGraphics();
		//g2Alias=(Graphics2D) center.getGraphics();
		//g2Alias.setColor(Color.GRAY);
		//Stroke stroke=new BasicStroke(0.1f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
		//g2Alias.setStroke(stroke);

		File directoryImg=new File("lib");
		File[] files=directoryImg.listFiles();

		try{
			
			Vector vRoadTextures=new Vector();

			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("road_texture_")){

					vRoadTextures.add(files[i]);

				}		
			}

			worldImages=new BufferedImage[vRoadTextures.size()];

			for(int i=0;i<vRoadTextures.size();i++){

				worldImages[i]=ImageIO.read(new File("lib/road_texture_"+i+".jpg"));

				/*for (int j = 0; j < numPanels; j++) {
					chooseTexture[j].addItem(new ValuePair(""+i,""+i));
				}*/




			}


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
			
			objectImages=new BufferedImage[vObjects.size()];
			objectIndexes=new Texture[vObjects.size()];
			
			for(int i=0;i<vObjects.size();i++){

				chooseObject.addItem(new ValuePair(""+i,""+i));
				objectImages[i]=ImageIO.read(new File("lib/object_"+i+".gif"));
	
	
				BufferedImage boi=new BufferedImage(indexWidth,indexHeight,BufferedImage.TYPE_INT_RGB);
				boi.getGraphics().setColor(Color.white);
				boi.getGraphics().drawString(""+i,0,indexHeight);
				objectIndexes[i]=new Texture(boi);	

			}

		} catch (Exception e) {
			e.printStackTrace();
		}	

	}


	public void buildNewZBuffers() {


		for(int i=0;i<landscapeZbuffer.getSize();i++){

			landscapeZbuffer.rgbColor[i]=blackRgb;
            

		}
	

	}
	
	public void buildScreen(BufferedImage buf) {

		int length=rgb.length;
		
		for(int i=0;i<length;i++){


			//set
			rgb[i]=landscapeZbuffer.getRgbColor(i); 
			
			//clean
			landscapeZbuffer.set(0,0,0,0,blackRgb,true,i);
              
		

		}
		
		buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,rgb);
		//buf.setRGB(0,0,WIDTH,HEIGHT,rgb,0,WIDTH);


	}


	private void deselectAll() {
		
		if(ACTIVE_PANEL==TERRAIN_INDEX)
			deselectAllPoints();
		else if(ACTIVE_PANEL==ROAD_INDEX)
			deselectAllSPNodes();
		draw();
		
		
		polygon=new LineData();
	}

	private void polygonDetail() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) mesh.polygonData.elementAt(i);
			if(!ld.isSelected())
				continue;
			
			RoadEditorPolygonDetail repd=new RoadEditorPolygonDetail(this,ld);
	
			
			if(repd.getModifiedLineData()!=null){
				
				mesh.polygonData.setElementAt(repd.getModifiedLineData(),i);
				
			}
			
			break;
		}
		draw();
		
	}

	private void draw() {


		Graphics2D graph = (Graphics2D) buf.getGraphics();
		RoadEditorPanel ep=getCenter();

		ep.setHide_objects(checkHideObjects.isSelected());
		draw(ep,graph);
		
		buildScreen(buf); 

		if(graphics==null)
			graphics=(Graphics2D) ep.getGraphics();

		graphics.drawImage(buf,0,0,null);



	}


	private void draw( RoadEditorPanel editorPanel,Graphics2D graph) {


		graph.setColor(Color.BLACK);
		graph.fillRect(0,0,WIDTH,HEIGHT);
	
		//editorPanel.setHide_objects(checkHideObjects.isSelected());
		editorPanel.drawRoad(meshes,drawObjects,splines,landscapeZbuffer,graph);
	

		editorPanel.drawCurrentRect(landscapeZbuffer);

	}



	public void paint(Graphics g) {
		super.paint(g);
		draw();
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


		jm4=new JMenu("Change");
		jm4.addMenuListener(this);
		jmtUndoObjects = new JMenuItem("Undo last object");
		jmtUndoObjects.setEnabled(false);
		jmtUndoObjects.addActionListener(this);
		jm4.add(jmtUndoObjects);
		jmtUndoSPLines = new JMenuItem("Undo last road");
		jmtUndoSPLines.setEnabled(false);
		jmtUndoSPLines.addActionListener(this);
		jm4.add(jmtUndoSPLines);	
		
		jmb.add(jm4);
		
		jm_view=new JMenu("View");
		jm_view.addMenuListener(this);
		jmb.add(jm_view);
		
		jmt_top_view=new JMenuItem("Top view");
		jmt_top_view.addActionListener(this);
		jm_view.add(jmt_top_view);

		jmt_3d_view=new JMenuItem("3D view");
		jmt_3d_view.addActionListener(this);
		jm_view.add(jmt_3d_view);
		
		jm_editing=new JMenu("Editing");
		jm_editing.addMenuListener(this);
		
		jmtBuildNewGrid = new JMenuItem("New Grid");
		jmtBuildNewGrid.addActionListener(this);
		jm_editing.add(jmtBuildNewGrid);
		
		jm_editing.addSeparator();
		
		jmtExpandGrid = new JMenuItem("Expand grid(T)");
		jmtExpandGrid.addActionListener(this);
		jm_editing.add(jmtExpandGrid);
		
		jm_editing.addSeparator();
		
		jmtAddGrid = new JMenuItem("Add grid");
		jmtAddGrid.addActionListener(this);
		jm_editing.add(jmtAddGrid);
		
		jm_editing.addSeparator();
		
		jmtBuildCity = new JMenuItem("Build Custom city");
		jmtBuildCity.addActionListener(this);
		jm_editing.add(jmtBuildCity);
		
		jm_editing.addSeparator();
	
		jmtShowAltimetry = new JMenuItem("Advanced Altimetry");
		jmtShowAltimetry.addActionListener(this);
		jm_editing.add(jmtShowAltimetry);
			
		
		jmb.add(jm_editing);
		
		help_jm=new JMenu("Help");
		help_jm.addMenuListener(this);		
		jmb.add(help_jm);
		
		help_jmt=new JMenuItem("Help");
		help_jmt.addActionListener(this);		
		help_jm.add(help_jmt);

		setJMenuBar(jmb);
	}

	private void buildLeftPanel() {
		
		ACTIVE_PANEL=TERRAIN_INDEX;
		
		buildFieldsArrays();

		int upper_left_height=100;
		int middle_left_height=40;
		int lower_left_height=500;
		
		int r=10;	
		
		left=new JPanel();
		left.setBounds(0,0,LEFT_BORDER,HEIGHT);
		left.setLayout(null);
		
		left_tools=new JPanel(null);
		//left_tools.setBorder(BorderFactory.createTitledBorder("Choose tool"));
		left_tools.setBounds(5,0,150,upper_left_height);
		left.add(left_tools);
				
		left_common_options=new JPanel(null);
		left_common_options.setBounds(0,upper_left_height,LEFT_BORDER,middle_left_height);
		left.add(left_common_options);
		
		left_tool_options=new JPanel(new CardLayout());	
		left_tool_options.setBounds(0,middle_left_height+upper_left_height,LEFT_BORDER,lower_left_height);
		left.add(left_tool_options);
		
		
		toogle_terrain= new JToggleButton("Terrain");		
		toogle_terrain.setActionCommand(TERRAIN_MODE);
		toogle_terrain.setSelected(true);
		toogle_terrain.addActionListener(this);
		toogle_terrain.addKeyListener(this);
		toogle_terrain.setBounds(10,r,100,20);
		
		r+=30;
	
		
		toogle_splines = new JToggleButton("Splines");
		toogle_splines.setActionCommand(SPLINES_MODE);
		toogle_splines.addActionListener(this);
		toogle_splines.addKeyListener(this);
		toogle_splines.setBounds(10,r,100,20);
		
		r+=30;
	
		toogle_objects = new JToggleButton("Objects");
		toogle_objects.setActionCommand(OBJECT_MODE);
		toogle_objects.addActionListener(this);
		toogle_objects.addKeyListener(this);
		toogle_objects.setBounds(10,r,100,20);
		
		
		
		ButtonGroup bgb=new ButtonGroup();
		bgb.add(toogle_splines);
		bgb.add(toogle_terrain);
		bgb.add(toogle_objects);
		
		left_tools.add(toogle_splines);
		left_tools.add(toogle_terrain);
		left_tools.add(toogle_objects);
		
		r=10;
		
		checkHideObjects=new JCheckBox(header+"Hide objects"+footer);
		checkHideObjects.setBounds(10,r,100,20);
		checkHideObjects.addKeyListener(this);
		checkHideObjects.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				draw();
			}
		});
		
		left_common_options.add(checkHideObjects);
		
		////OBJECTS
		        
        JPanel terrain_panel =  buildTerrainPanel(TERRAIN_INDEX);
        //left.setBorder(objBorder);
        left_tool_options.add(terrain_panel,TERRAIN_MODE);
        
        JPanel splines_panel = buildSPLinesPanel();
       
        left_tool_options.add(splines_panel,SPLINES_MODE);
        
        
        JPanel object_panel = buildObjectsPanel();
        //left.setBorder(objBorder);
        left_tool_options.add(object_panel,OBJECT_MODE);
        
       

		add(left);
		
		

	}

	private JPanel buildSPLinesPanel() {
		JPanel splines_panel=new JPanel(null);

		int index=ROAD_INDEX;

		int r=10;
		
		
		checkMultiplePointsSelection[index]=new JCheckBox("Multiple selection");
		checkMultiplePointsSelection[index].setBounds(30,r,150,20);
		checkMultiplePointsSelection[index].addKeyListener(this);
		splines_panel.add(checkMultiplePointsSelection[index]);

		r+=30;
		
		JPanel moveRoad=buildRoadMovePanel(10,r,index);
		splines_panel.add(moveRoad);

		r+=120;		

		insertSPNode=new JButton(header+"Insert node after"+footer);
		insertSPNode.addActionListener(this);
		insertSPNode.addKeyListener(this);
		insertSPNode.setFocusable(false);
		insertSPNode.setBounds(5,r,150,20);
		splines_panel.add(insertSPNode);


		r+=30;
		
		mergeSPNodes=new JButton(header+"Merge spnodes"+footer);
		mergeSPNodes.addActionListener(this);
		mergeSPNodes.addKeyListener(this);
		mergeSPNodes.setFocusable(false);
		mergeSPNodes.setBounds(5,r,150,20);
		splines_panel.add(mergeSPNodes);


		r+=30;

		startNewSPLine=new JButton(header+"Start new spline"+footer);
		startNewSPLine.addActionListener(this);
		startNewSPLine.addKeyListener(this);
		startNewSPLine.setFocusable(false);
		startNewSPLine.setBounds(5,r,150,20);
		splines_panel.add(startNewSPLine);


		r+=30;


		deleteSelection[index]=new JButton(header+"<u>D</u>elete selection"+footer);
		deleteSelection[index].addActionListener(this);
		deleteSelection[index].addKeyListener(this);
		deleteSelection[index].setFocusable(false);
		deleteSelection[index].setBounds(5,r,150,20);
		splines_panel.add(deleteSelection[index]);

		r+=30;

		deselectAll[index]=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAll[index].addActionListener(this);
		deselectAll[index].setFocusable(false);
		deselectAll[index].setBounds(5,r,150,20);
		splines_panel.add(deselectAll[index]);

		return splines_panel;
	}

	private JPanel buildObjectsPanel() {
		
		Border objBorder=BorderFactory.createTitledBorder("Objects");
		

		JPanel object_panel=new JPanel(null);
		 object_panel.setBorder(objBorder);

		int r=25;

		checkMultipleObjectsSelection=new JCheckBox("Multiple selection");
		checkMultipleObjectsSelection.setBounds(30,r,150,20);
		checkMultipleObjectsSelection.addKeyListener(this);
		object_panel.add(checkMultipleObjectsSelection);

	
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
		object_panel.add(chooseObjectPanel);

		r+=30;

		objectLabel=new JLabel();
		objectLabel.setFocusable(false);
		objectLabel.setBounds(10,r,100,100);

		Border border=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		objectLabel.setBorder(border);
		object_panel.add(objectLabel);

		JPanel moveObject=buildObjectMovePanel(120,r);
		object_panel.add(moveObject);

		r+=100;

		choosePrevObject=new JButton("<");
		choosePrevObject.setBounds(10,r,50,20);
		choosePrevObject.addActionListener(this);
		choosePrevObject.addKeyListener(this);
		object_panel.add(choosePrevObject);

		chooseNextObject=new JButton(">");
		chooseNextObject.setBounds(60,r,50,20);
		chooseNextObject.addActionListener(this);
		chooseNextObject.addKeyListener(this);
		object_panel.add(chooseNextObject);		

		r+=30;


		JLabel lz=new JLabel("Rotation angle:");
		lz.setBounds(5,r,90,20);
		object_panel.add(lz);
		rotation_angle=new DoubleTextField(8);
		rotation_angle.setBounds(100,r,80,20);
		rotation_angle.addKeyListener(this);
		object_panel.add(rotation_angle);

		r+=30;

		changeObject=new JButton(header+"Change O<u>b</u>ject"+footer);
		changeObject.addActionListener(this);
		changeObject.setFocusable(false);
		changeObject.setBounds(5,r,150,20);
		object_panel.add(changeObject);

		r+=30;

		delObject=new JButton("Del object");
		delObject.addActionListener(this);
		delObject.setFocusable(false);
		delObject.setBounds(5,r,150,20);
		object_panel.add(delObject);

		r+=30;

		deselectAllObjects=new JButton("Deselect all");
		deselectAllObjects.addActionListener(this);
		deselectAllObjects.setFocusable(false);
		deselectAllObjects.setBounds(5,r,150,20);
		object_panel.add(deselectAllObjects);

		r+=30;


		return object_panel;
	}

	private JPanel buildTerrainPanel(int index) {

		
		JPanel panel=new JPanel();

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
		//chooseTexture.setBounds(35,r,50,20);
		chooseTexture[index].addItemListener(this);
		chooseTexture[index].addKeyListener(this);
		//panel.add(chooseTexture);

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
		
		if(index==1){		
		
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

		}

         return panel;

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
		bottom.setBounds(0,HEIGHT,LEFT_BORDER+WIDTH,BOTTOM_BORDER);
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

	private void undoSplines() {

		splines=(Vector) oldSpline.pop();
		if(oldSpline.size()==0)
			jmtUndoSPLines.setEnabled(false);
		
		firePropertyChange("RoadEditorUndo", false, true);
	}

	public void prepareUndo() {
		prepareUndoObjects();
		prepareUndoSpline();
	}

	private void prepareUndoSpline() {
		jmtUndoSPLines.setEnabled(true);
		
		if(oldSpline.size()==MAX_STACK_SIZE){
			oldSpline.removeElementAt(0);
		}
		oldSpline.push(cloneSPLines(splines));


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
			
			ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
			if(vp!=null && !vp.getValue().equals("")){
				
				int index=Integer.parseInt(vp.getId());
				 dro.setIndex(index);
				 
				 if(DrawObject.IS_3D){
					 
				     CubicMesh cm=EditorData.objectMeshes[index]; 
				     dro.setDx(cm.getDeltaX2()-cm.getDeltaX());
				     dro.setDy(cm.getDeltaY2()-cm.getDeltaY());
				     dro.setDz(cm.getDeltaX());
				 }
			}	 

			dro.setRotation_angle(rotation_angle.getvalue());
			
			//dro.setSelected(false);
		}

		cleanObjects();
		draw();

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
			
			if(!checkMultipleObjectsSelection.isSelected()){
			
				setObjectData(dro);					
	
			
			}
			
		}
       
		//cleanObjects();
		draw();
		
	}

	public void setObjectData(DrawObject dro) { 
		


		for(int k=0;k<chooseObject.getItemCount();k++){

			ValuePair vp=(ValuePair) chooseObject.getItemAt(k);
			if(vp.getId().equals(""+dro.index) )
				chooseObject.setSelectedItem(vp);
		}

		rotation_angle.setText(dro.rotation_angle);
		
	}

	private void changeSelectedTerrainPoint() {

	
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		
		for(int j=0;mesh.points!=null && j<mesh.points.length;j++){


			    Point4D p=(Point4D)mesh.points[j];

				if(p.isSelected()){

					if(!"".equals(coordinatesx[ACTIVE_PANEL].getText()))
						p.x=Double.parseDouble(coordinatesx[ACTIVE_PANEL].getText());
					if(!"".equals(coordinatesy[ACTIVE_PANEL].getText()))
						p.y=Double.parseDouble(coordinatesy[ACTIVE_PANEL].getText());
					if(!"".equals(coordinatesz[ACTIVE_PANEL].getText()))
						p.z=Double.parseDouble(coordinatesz[ACTIVE_PANEL].getText());

					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
					if(!vp.getId().equals(""))
						p.setIndex(Integer.parseInt(vp.getId()));

					p.setSelected(false);
				}

		}	
        firePropertyChange("RoadEditorUpdate", false, true);

		cleanPoints();
		draw();
	}
	
	private void changeSelectedTerrainPolygon() {
		
	
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData)mesh.polygonData.elementAt(j);
		    
		    
		    if(ld.isSelected){
		    	
		    	int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
		    	
		    	if(indx!=0){
	
					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(indx);
						
						
					ld.setTexture_index(Integer.parseInt(vp.getId()));
				
		    	}
		      	
		    	ld.setSelected(false);
		    	
		    }
			
		}	
		
	}
	
	private void invertSelectedRoadPolygon() {
		
		prepareUndoSpline();
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int i=0;i<sizel;i++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(i);
		    
		    
		    if(ld.isSelected){
		    	
		    	LineData invertedLd=new LineData();
				
				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				mesh.polygonData.setElementAt(invertedLd,i);
		    	
		    }
			
		}	
		
	}

	private void mergeSelectedPoints() {
	
		//prepareUndo();
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		
		int firstPoint=-1;
		
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p= mesh.points[i];
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

			LineData ld=(LineData)mesh.polygonData.elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			newLd.setTexture_index(ld.getTexture_index());
			boolean insertedFirst=false;

			for(int j=0;j<ld.size();j++){

				Point3D p0= mesh.points[ld.getIndex(j)];
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

		mesh.setPoints(newPoints);
		mesh.polygonData=newLines;
        deselectAll();
		
	
	}
	
	private void deleteSelection() {
		
		if(ACTIVE_PANEL==TERRAIN_INDEX)
			deleteTerrainSelection();
		else if(ACTIVE_PANEL==ROAD_INDEX)
			deleteSelectedSPnode();
			
		
	}

	private void deleteSelectedSPnode() {
		
		prepareUndoSpline();
		
		Vector newSplines=new Vector();
	
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
			
			Vector newNodes=new Vector();
			
			for (int j = 0; j < sp.nodes.size(); j++) {
				
				SPNode node = (SPNode) sp.nodes.elementAt(j);
				
				if(node.isSelected)
					continue;

				newNodes.add(node);
			}

			
			if(newNodes.size()>0){
				
				sp.nodes=newNodes;
				sp.calculateRibs();
				newSplines.add(sp);
			}
			
		}
		
		splines=newSplines;
		
	}

	private void deleteTerrainSelection() {
		
		prepareUndo();
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		Vector newPoints=new Vector();
		Vector newLines=new Vector();

		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		
		int sizel=mesh.polygonData.size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) mesh.polygonData.elementAt(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			boolean gotAllPoint=true;
			
			for(int j=0;j<ld.size();j++){

				Point3D p0=mesh.points[ld.getIndex(j)];
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

		mesh.setPoints(newPoints);
		mesh.polygonData=newLines;
        deselectAll();
	
		
	}
	
	private void moveSelectedPoints(int dx, int dy,int dk) { 
		
		if(ACTIVE_PANEL==TERRAIN_INDEX)
			 moveSelectedTerrainPoints(dx,  dy, dk);
		else if(ACTIVE_PANEL==ROAD_INDEX)
			moveSelectedSplinesPoints(dx,  dy, dk);
		

	}
	


	private void moveSelectedTerrainPoints(int dx, int dy,int dk) { 
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		String sqty=roadMove[ACTIVE_PANEL].getText();

		if(sqty==null || sqty.equals(""))
			return;

		double qty=Double.parseDouble(sqty);



		prepareUndoSpline();

		for(int j=0;j<mesh.points.length;j++){


			Point4D p=(Point4D) mesh.points[j];

			if(p.isSelected()){


				p.x+=qty*dx;

				p.y+=qty*dy;

				p.z+=qty*dk;

				//p.setSelected(false);



			}
		}	
		firePropertyChange("RoadEditorUpdate", false, true);

		cleanPoints();
		draw();

	}
	
	private void moveSelectedSplinesPoints(int dx, int dy, int dz) {
		
		String sqty=roadMove[ACTIVE_PANEL].getText();

		if(sqty==null || sqty.equals(""))
			return;

		double qty=Double.parseDouble(sqty);
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.elementAt(i);
			
			Vector nodes=spline.nodes;
			
			boolean found=false;
			
			for(int j=0;j<nodes.size();j++){
			
				SPNode spnode = (SPNode) nodes.elementAt(j);
				
				if(spnode.isSelected){
					
					spnode.translate(qty*dx,  qty*dy, qty*dz);
					spnode.update();
					found=true;
					
					
				}
			}
			
			if(found)
				spline.calculateRibs();
			
		}
		
		draw();
	}

	private void deleteObject() {

		prepareUndoObjects();
		
		for(int i=0;i<drawObjects.size();i++){
			
			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			if(dro.isSelected())
				drawObjects.remove(dro);
		}

	}

	


	

	private void addObject(double x, double y, double z) {
		
		if(chooseObject.getSelectedIndex()<=0)
			return;
		
		int index=0;
		ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
		if(vp!=null && !vp.getValue().equals(""))
			index=Integer.parseInt(vp.getId());
		
		int dim_x=EditorData.objectMeshes[index].getDeltaX2()-EditorData.objectMeshes[index].getDeltaX();
		int dim_y=EditorData.objectMeshes[index].getDeltaY2()-EditorData.objectMeshes[index].getDeltaY();
		int dim_z=EditorData.objectMeshes[index].getDeltaX();
		
		double rot_angle=rotation_angle.getvalue();rotation_angle.getText();
		
		cleanObjects();
		
		addObject(x,y,z,dim_x,dim_y,dim_z,index,rot_angle);

	}

	
	private void addObject(double x, double y, double z, int dx, int dy, int dz,int index,double rot_angle) {

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
		dro.setRotation_angle(rot_angle);

		ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
		if(vp!=null && !vp.getValue().equals("")){
			
			
			dro.index=Integer.parseInt(vp.getId());
		
		
		}	

			
		
		setObjectMesh(dro);
		

		dro.setRotation_angle(rot_angle);
		drawObjects.add(dro);


	}

	private static void setObjectMesh(DrawObject dro) {
		
		Point3D center=null;

		CubicMesh cm=(CubicMesh) EditorData.objectMeshes[dro.getIndex()].clone();

		Point3D point = cm.point000;

		double dx=-point.x+dro.x;
		double dy=-point.y+dro.y;
		double dz=-point.z+dro.z;

		cm.translate(dx,dy,dz);			

		center=cm.findCentroid();

		if(dro.rotation_angle!=0)
			cm.rotate(center.x,center.y,Math.cos(dro.rotation_angle),Math.sin(dro.rotation_angle));


		dro.setMesh(cm);
		
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

				pr.println(dro.toString());
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
				//saveLines(pr);
				saveSPLines(pr);
				
				//setACTIVE_PANEL(right.getSelectedIndex());
				
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
			loadSPLinesFromFile(file);
			setACTIVE_PANEL(0);
			
			//right.setSelectedIndex(0);
			
            loadObjectsFromFile(file); 
            
            
            oldSpline=new Stack();
            oldObjects=new Stack();

		}
		
	}

	
	
	public String decomposeLineData(LineData ld) {

		String str="";
		
		str+="T"+ld.texture_index;
		str+=" C"+ld.getHexColor();

		for(int j=0;j<ld.size();j++){
			
			
			
			str+=" ";
			
			str+=ld.getIndex(j)+"/"+j;

		}

		return str;
	}
	

	public void decomposeObjVertices(PrintWriter pr, PolygonMesh mesh,boolean isCustom) {
	
		pr.print("vt=0 0\n");//0
		pr.print("vt=200 0\n");//1
		pr.print("vt=200 200\n");//2
		pr.print("vt=0 200\n");//3
	}
	
	
	public void buildLine(Vector polygonData, String str,Vector vTexturePoints) {



	        Road.buildLine(polygonData,  str, vTexturePoints);


	}
	
	public void buildPoint(Vector points, String str) {



			String[] vals = str.split(" ");

			Point4D p=new Point4D();
			
			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			points.add(p);
	

	}

	public void loadObjectsFromFile(File file){

		
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
				
				DrawObject dro=buildDrawObject(str,EditorData.objectMeshes);
				drawObjects.add(dro);
				
				//buildRectanglePolygons(dro.getPolygons(),dro.x,dro.y,dro.z,dro.dx,dro.dy,dro.dz);

				
				CubicMesh cm=EditorData.objectMeshes[dro.getIndex()].clone();
				Point3D point = cm.point000;
				
				
				double dx=-point.x+dro.x;
				double dy=-point.y+dro.y;
				double dz=-point.z+dro.z;
				
				cm.translate(dx,dy,dz);
				
				Point3D center=cm.findCentroid();
				
				if(dro.rotation_angle!=0)
					cm.rotate(center.x,center.y,Math.cos(dro.rotation_angle),Math.sin(dro.rotation_angle));
			
				
				dro.setMesh(cm);
				
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	


	public static DrawObject buildDrawObject(String str, CubicMesh[] objectMeshes) {
		DrawObject dro=new DrawObject();
		
		String properties0=str.substring(0,str.indexOf("["));
		String properties1=str.substring(str.indexOf("[")+1,str.indexOf("]"));

		StringTokenizer tok0=new StringTokenizer(properties0," "); 
		
		dro.x=Double.parseDouble(tok0.nextToken());
		dro.y=Double.parseDouble(tok0.nextToken());
		dro.z=Double.parseDouble(tok0.nextToken());
		dro.index=Integer.parseInt(tok0.nextToken());
		
		dro.dx=objectMeshes[dro.index].getDeltaX2()-objectMeshes[dro.index].getDeltaX();
		dro.dy=objectMeshes[dro.index].getDeltaY2()-objectMeshes[dro.index].getDeltaY();
		dro.dz=objectMeshes[dro.index].getDeltaX();
		
	
		
		StringTokenizer tok1=new StringTokenizer(properties1," "); 
		dro.rotation_angle=Double.parseDouble(tok1.nextToken());
		dro.hexColor=tok1.nextToken();
		return dro;
	}



	public void actionPerformed(ActionEvent arg0) {
		
		Object obj=arg0.getSource();

		
		if(toogle_objects==obj || toogle_splines==obj || toogle_terrain==obj){
			CardLayout cl = (CardLayout)(left_tool_options.getLayout());
			
			mode=((JToggleButton) obj).getActionCommand();
			cl.show(left_tool_options,mode);
			
			if(SPLINES_MODE.equals(mode))
				ACTIVE_PANEL=ROAD_INDEX;
			else if(TERRAIN_MODE.equals(mode))
				ACTIVE_PANEL=TERRAIN_INDEX;
			
		}
		else if(obj==jmt_load_landscape){
			
				loadLanscape();
		}
		else if(obj==jmt_save_landscape){
			
				saveLandscape();

		}		
		else if(obj==jmtUndoObjects){
			undoObjects();
		}
		else if(obj==jmtUndoSPLines){
			undoSplines();
		}
		else if(obj==jmtShowAltimetry){
			showAltimetry();
		}
		else if(obj==jmtBuildNewGrid){
			buildNewGrid();
		}
		else if(obj==jmtExpandGrid){
			expandGrid();
		}
		else if(obj==jmtAddGrid){
			addGrid();
		}
		else if(obj==jmtBuildCity){
			buildCity();
		}		
		else if(obj==mergeSelectedPoints[ACTIVE_PANEL]){			
			
			mergeSelectedPoints();
			draw();
		}
		else if(obj==changePoint[ACTIVE_PANEL] ){
			changeSelectedTerrainPoint();
			draw();
		}
		else if(obj==changePolygon[ACTIVE_PANEL]){
			changeSelectedTerrainPolygon();
			draw();
		}	
		else if(obj==startNewSPLine){
			startNewSPLine();
			draw();
		}
		else if(obj==polygonDetail[ACTIVE_PANEL]){
			polygonDetail();
			//draw();
		}
		else if(obj==deleteSelection[ACTIVE_PANEL]){
			deleteSelection();
			draw();
		}
		else if(obj==deselectAll[ACTIVE_PANEL]){
			deselectAll();
		}
		else if(obj==delObject){
			deleteObject();
			draw();
		}
		else if(obj==changeObject){
			changeSelectedObject();
			draw();
		}
		else if(obj==deselectAllObjects){
			cleanObjects();
		}
		else if(obj==choosePanelTexture[ACTIVE_PANEL]){
			
			TexturesPanel tp=new TexturesPanel(worldImages,100,100);
			
			int indx=tp.getSelectedIndex();
			if(indx!=-1)
				chooseTexture[ACTIVE_PANEL].setSelectedIndex(indx+1);
			
		}
		else if(obj==chooseNextTexture[ACTIVE_PANEL]){
			int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
			if(indx<chooseTexture[ACTIVE_PANEL].getItemCount()-1)
				chooseTexture[ACTIVE_PANEL].setSelectedIndex(indx+1);
		}
		else if(obj==chooseObjectPanel){
			
			TexturesPanel tp=new TexturesPanel(objectImages,100,100);
			
			int indx=tp.getSelectedIndex();
			if(indx!=-1)
				chooseObject.setSelectedIndex(indx+1);
		}
		else if(obj==choosePrevTexture[ACTIVE_PANEL]){
			int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
			if(indx>0)
				chooseTexture[ACTIVE_PANEL].setSelectedIndex(indx-1);
		}
		else if(obj==chooseNextObject){
			int indx=chooseObject.getSelectedIndex();
			if(indx<chooseObject.getItemCount()-1)
				chooseObject.setSelectedIndex(indx+1);
		}
		else if(obj==choosePrevObject){
			int indx=chooseObject.getSelectedIndex();
			if(indx>0)
				chooseObject.setSelectedIndex(indx-1);
		}
		else if(obj==moveRoadUp[ACTIVE_PANEL]){

			moveSelectedPoints(0,1,0);

		}
		else if(obj==moveRoadDown[ACTIVE_PANEL]){

			moveSelectedPoints(0,-1,0);

		}
		else if(obj==moveRoadLeft[ACTIVE_PANEL]){

			moveSelectedPoints(-1,0,0);

		}
		else if(obj==moveRoadRight[ACTIVE_PANEL]){

			moveSelectedPoints(+1,0,0);

		}
		else if(obj==moveRoadTop[ACTIVE_PANEL]){

			moveSelectedPoints(0,0,1);

		}
		else if(obj==moveRoadBottom[ACTIVE_PANEL]){

			moveSelectedPoints(0,0,-1);

		}
		else if(obj==moveObjUp){

			moveSelectedObject(0,1,0);

		}
		else if(obj==moveObjDown){

			moveSelectedObject(0,-1,0);

		}
		else if(obj==moveObjLeft){

			moveSelectedObject(-1,0,0);

		}
		else if(obj==moveObjRight){

			moveSelectedObject(+1,0,0);

		}
		else if(obj==moveObjTop){

			moveSelectedObject(0,0,1);

		}
		else if(obj==moveObjBottom){

			moveSelectedObject(0,0,-1);

		}
		else if(obj==help_jmt){
			help();
		}
		else if(obj==jmt_top_view){
			changeView(TOP_VIEW);
		}
		else if(obj==jmt_3d_view){
			changeView(ISO_VIEW);
		}	
		else if(obj==insertSPNode){
			insertSPNode();
		}
		else if(obj==mergeSPNodes){
			mergeSPNodes();
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
    	

    }
    
	private void changeView(int type) {
		
		remove(getCenter());

		if(type==ISO_VIEW){

			add(panelIso);
		}
		else
			add(panelTop);

		VIEW_TYPE=type;

		draw();
	}
	
	public RoadEditorPanel getCenter(){


		if(VIEW_TYPE==ISO_VIEW)
			return panelIso;
		else
			return panelTop;

	}
    
	private void help() {
	
		
		HelpPanel hp=new HelpPanel(300,200,this.getX()+100,this.getY(),HelpPanel.ROAD_EDITOR_HELP_TEXT,this);
		
	}

	
	private void expandGrid() {
 
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		if(mesh.points==null)
			return; 
		
		if(!(mesh instanceof SquareMesh))
		{
			
			JOptionPane.showMessageDialog(this,"Can't expand road grid");
			return;
		}	
		
		RoadEditorGridManager regm=new RoadEditorGridManager((SquareMesh)mesh);
		
		if(regm.getReturnValue()!=null){
			
			
            SquareMesh pm=(SquareMesh) mesh;
			
			RoadEditorGridManager roadEGM=(RoadEditorGridManager) regm.getReturnValue();

			
			double z_value=0;
			
			int numx=roadEGM.NX;
			int numy=roadEGM.NY;
			
			double dx=roadEGM.DX;
			double dy=roadEGM.DY;
			
			double x_0=roadEGM.X0;
			double y_0=roadEGM.Y0;
			
			
			if(numx<pm.getNumx() || numy<pm.getNumy()){
				
				JOptionPane.showMessageDialog(this,"Can't shrink the original map!","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//create a SquareMesh
			SquareMesh npm=new SquareMesh(numx,numy,pm.getDx(),pm.getDy(),pm.getX0(),pm.getY0());
			
			npm.points=new Point4D[numx*numy];
			
			
			for (int i = 0; i < numx; i++) {
				
				for (int j = 0; j < numy; j++) {
					
					int pos=pos(i,j,numx,numy);
					
					if(i<pm.getNumx() && j<pm.getNumy()){
						
						int oldPos=pos(i,j,pm.getNumx(),pm.getNumy());
						npm.points[pos]=pm.points[oldPos];
						
					}
					else
						npm.points[pos]=new Point4D(pm.getX0()+i*dx,pm.getY0()+j*dy,0);
		
				}
				
			}
			
		
			
			int count=0;
			
			for (int i = 0; i < numx-1; i++) {
				
				for (int j = 0; j < numy-1; j++) {
					
					
					//base z=0
					
					int pos0=pos(i,j,numx,numy);
					int pos1=pos(i+1,j,numx,numy);
					int pos2=pos(i+1,j+1,numx,numy);
					int pos3=pos(i,j+1,numx,numy);
					
					LineData ld=new LineData();
					ld.addIndex(pos0);
					ld.addIndex(pos1);
					ld.addIndex(pos2);
					ld.addIndex(pos3);
					
					npm.polygonData.add(ld);
					
					if(i<pm.getNumx()-1 && j<pm.getNumy()-1){
						
						LineData oldLineData=pm.polygonData.elementAt(count++);
						ld.setTexture_index(oldLineData.getTexture_index());
					}
					else{
						
						if(ACTIVE_PANEL==1)
							ld.setTexture_index(0);
						else
							ld.setTexture_index(2);
						
					}
						
				}
			}	
			//pm=(SquareMesh) PolygonMesh.simplifyMesh(npm);
			meshes[ACTIVE_PANEL]=npm;
		
			
			
		}
		
		draw();
		
	
		
	}
	
	private int pos(int i, int j,  int numx, int numy) {
		
		return (i+j*numx);
	}	
	
	
	private void buildNewGrid() { 
		
		RoadEditorGridManager regm=new RoadEditorGridManager(null);
		
		if(regm.getReturnValue()!=null){
			
			RoadEditorGridManager roadEGM=(RoadEditorGridManager) regm.getReturnValue();
			
			PolygonMesh mesh=meshes[ACTIVE_PANEL];

			
			double z_value=0;
			
			int numx=roadEGM.NX;
			int numy=roadEGM.NY;
			
			double dx=roadEGM.DX;
			double dy=roadEGM.DY;
			
			double x_0=roadEGM.X0;
			double y_0=roadEGM.Y0;
			
			int tot=numx*numy;
			
		
			mesh.polygonData=new Vector();
			
			mesh.points=new Point3D[numy*numx];
			
			
			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{
					
					Point4D p=new Point4D(i*dx+x_0,j*dy+y_0,z_value);
				
					mesh.points[i+j*numx]=p;

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
					
					mesh.polygonData.add(ld);
					
					
					
				}
			
			
			if(mesh instanceof SquareMesh){
				
				((SquareMesh)mesh).setNumx(numx);
				((SquareMesh)mesh).setNumy(numy);
				((SquareMesh)mesh).setX0(x_0);
				((SquareMesh)mesh).setY0(y_0);
				((SquareMesh)mesh).setDx((int)dx);
				((SquareMesh)mesh).setDy((int)dy);
			}
			
			
		}
		
		draw();
		
	}
	
	private void addGrid() { 
		
		RoadEditorGridManager regm=new RoadEditorGridManager(null);
		
		if(regm.getReturnValue()!=null){
			
			RoadEditorGridManager roadEGM=(RoadEditorGridManager) regm.getReturnValue();
			
			PolygonMesh mesh=meshes[ACTIVE_PANEL];

			
			double z_value=0;
			
			int numx=roadEGM.NX;
			int numy=roadEGM.NY;
			
			double dx=roadEGM.DX;
			double dy=roadEGM.DY;
			
			double x_0=roadEGM.X0;
			double y_0=roadEGM.Y0;
			
			int tot=numx*numy;
			
			
            int sz= mesh.points.length;
            Point3D[] newPoints = new Point3D[sz+numy*numx];
			
            
            for (int i = 0; i < sz; i++) {
            	newPoints[i]=mesh.points[i];
			}
			
			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{
					
					Point4D p=new Point4D(i*dx+x_0,j*dy+y_0,z_value);
				
					newPoints[sz+i+j*numx]=p;

				}

			mesh.points=newPoints;
			
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
					
					mesh.polygonData.add(ld);
					
					
					
				}
			
			if(mesh instanceof SquareMesh){
				
				((SquareMesh)mesh).setNumx(numx+((SquareMesh)mesh).getNumx());
				((SquareMesh)mesh).setNumy(numy+((SquareMesh)mesh).getNumy());
				((SquareMesh)mesh).setX0(x_0);
				((SquareMesh)mesh).setY0(y_0);
				((SquareMesh)mesh).setDx((int)dx);
				((SquareMesh)mesh).setDy((int)dy);
			}
			
			
		}
		
		draw();
		
	}
	
	private void buildCity() {
		
		RoadEditorCityManager regm=new RoadEditorCityManager(null);
		
		if(regm.getReturnValue()!=null){
			
			RoadEditorCityManager roadECM=(RoadEditorCityManager) regm.getReturnValue();
			RoadEditorCityManager.buildCustomCity1(meshes[0],meshes[1],roadECM,drawObjects,EditorData.objectMeshes);
			meshes[1]=PolygonMesh.simplifyMesh(meshes[1]);
			
			draw();
		}
		
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




		for (int i = 0; i < meshes[from].points.length;  i++) {

			Point3D point=meshes[from].points[i];
			
			if(!point.isSelected)
				continue;
			
			

			int lsize=meshes[to].polygonData.size();


			for(int j=0;j<lsize;j++){


				LineData ld=(LineData) meshes[to].polygonData.elementAt(j);

				Polygon3D p3D=levelGetPolygon(ld,meshes[to].points);

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
	

	private Polygon3D levelGetPolygon(LineData ld, Point3D[] points) {
		
	

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points[num];
			


			//bufGraphics.setColor(ZBuffer.fromHexToColor(is[i].getHexColor()));

			cx[i]=(int)p.x;
			cy[i]=(int)p.y;
			cz[i]=(int)p.z;

	

		}

		Polygon3D p_in=new Polygon3D(ld.size(),cx,cy,cz);
		
		return p_in;
	}

	/*private void addBendMesh() {
		
		if(ACTIVE_PANEL==0){
			
			JOptionPane.showMessageDialog(this,"Select road panel to add a bend");
			return;
		}
		
		
		prepareUndo();
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		Point3D origin=null;
		int originPos=-1;
		
	
		
		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
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
			
			for (int i = 0; i < mesh.points.length; i++) {
				
				Point3D point = mesh.points[i];
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
			
			
			int base=mesh.points.length-1;
			
			//do not recreate the origin!
			for (int i =1; i < bm.points.length; i++) {
				
				Point4D p=(Point4D) bm.points[i];
								
				mesh.addPoint(p);
				
				
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
				
				mesh.polygonData.add(ld);
			}
			
			draw();
			
		}
		
	}*/
	
	public void startNewSPLine() {
		
		prepareUndo();

		
		Vector vTexturePoints=buildTemplateTexturePoints(200);
		
		SPLine sp=new SPLine(vTexturePoints);	
		splines.add(sp);

		deselectAll();

	}
	
	private void showPreview() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		if(mesh.points.length==0)
			return;
		RoadEditorPreviewPanel preview=new RoadEditorPreviewPanel(this);
		
	}
	
	private void showAltimetry() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		if(mesh.points.length==0)
			return;
		RoadAltimetryPanel altimetry=new RoadAltimetryPanel(this);
	}


	public void mouseClicked(MouseEvent arg0) {

		int buttonNum=arg0.getButton();
		
		if(SPLINES_MODE.equals(mode)){
		
			//right button click
			if(buttonNum==MouseEvent.BUTTON3)
				//addObject(arg0);
				addSPnode(arg0);
			else{
				selectSPNode(arg0.getX(),arg0.getY());	
				
			}	
		
		} else if(TERRAIN_MODE.equals(mode)){
		
			//right button click
			if(buttonNum==MouseEvent.BUTTON3){

				deselectAll();
				changePolygon(arg0.getX(),arg0.getY());
				
			}
			else{
				selectPoint(arg0.getX(),arg0.getY());			
			}	
		
		} else if(OBJECT_MODE.equals(mode)){
			
			if(buttonNum==MouseEvent.BUTTON3){
				
				RoadEditorPanel ep = getCenter();
				
				if(ep instanceof RoadEditorTopPanel){
					
					Point p=arg0.getPoint();
					
					double xx=ep.invertX((int)p.getX());
					double yy=ep.invertY((int)p.getY());
					
					addObject(xx, yy,0);

				}else if(ep instanceof RoadEditorIsoPanel){
					
					putObjectInCell(arg0.getX(),arg0.getY());

					
				}
			}	
			else
				selectObject(arg0.getX(),arg0.getY());
			
			

		}
		draw();
	}




	private void addSPnode(MouseEvent arg0) {
		
		prepareUndoSpline();
		
		RoadEditorPanel ep = getCenter();
		
		if(ACTIVE_PANEL==TERRAIN_INDEX)
			return;
		
		prepareUndo();
		
		Point p=arg0.getPoint();

		int x=ep.invertX((int)p.getX());
		int y=ep.invertY((int)p.getY());
		
		int index=0;
		SPNode p0=new SPNode(x,y,0,LineData.GREEN_HEX,index);
		
		if(splines.size()==0){
			
						
			Vector vTexturePoints=buildTemplateTexturePoints(200);
			
			SPLine sp=new SPLine(vTexturePoints);	

			sp.addSPNode(p0);
			splines.add(sp);
			
		}else{
			
			SPLine sp=(SPLine) splines.lastElement();
			
			Vector vTexturePoints=buildTemplateTexturePoints(200);
			sp.addSPNode(p0);
			
		}
	
		

	}
	
	private void mergeSPNodes() {
		
		prepareUndoSpline();
		
		SPNode baseNode=null;
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
			
			for (int k = 0; k < sp.nodes.size(); k++) {
				
				SPNode node = (SPNode) sp.nodes.elementAt(k);

				if(node.isSelected()){
					
					if(baseNode==null){
						
						baseNode=node;
						
					}else{
						
						sp.nodes.setElementAt(baseNode.clone(),k);
					}
					
				}
			
			}


			
		}
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
		    sp.calculateRibs();	
			
		}
		
		deselectAllSPNodes();
		draw();
		
	}

	private void insertSPNode() {
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
			
			Vector newNodes=new Vector();
			
			for (int k = 0; k < sp.nodes.size(); k++) {
				
				SPNode node = (SPNode) sp.nodes.elementAt(k);
				newNodes.add(node);

				if(node.isSelected()){
					
					SPNode nextNode=null;
					
					if(k+1< sp.nodes.size()){
						
						nextNode= (SPNode) sp.nodes.elementAt(k+1);
						
					}else{
						
						nextNode= (SPNode) sp.nodes.elementAt(0);
					}
					
					
					
					double x=(node.x+nextNode.x)*0.5;
					double y=(node.y+nextNode.y)*0.5;
					double z=(node.z+nextNode.z)*0.5;
					
					
					SPNode intermediateNode=new SPNode((int)x,(int)y,(int)z,"FFFFFF",0);
					
					newNodes.add(intermediateNode);
					
				}
			
			}
			 sp.nodes=newNodes;

			
		}
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
		    sp.calculateRibs();	
			
		}	
		deselectAllSPNodes();
		draw();
	}
	

	public void addPoint() {
		
		prepareUndo();
		
	
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];	

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
		mesh.addPoint(point);

	}
	

	
	private void putObjectInCell(int x, int y) {
		
		if(meshes==null)
			return;
		
		PolygonMesh mesh=meshes[TERRAIN_INDEX];	
		
		RoadEditorPanel ep=getCenter();	
			
		Vector polygons=ep.getClickedPolygons(x,y,mesh);
		
		if(polygons.size()>0){
			
			
			LineData ld=(LineData)polygons.elementAt(0);
			
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(mesh.points,ld);
			
	        Point3D centroid=Polygon3D.findCentroid(polRotate);
				
			if(chooseObject.getSelectedIndex()<=0)
				return;
			
			int index=0;
			ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
			if(vp!=null && !vp.getValue().equals(""))
				index=Integer.parseInt(vp.getId());
			
			int dim_x=EditorData.objectMeshes[index].getDeltaX2()-EditorData.objectMeshes[index].getDeltaX();
			int dim_y=EditorData.objectMeshes[index].getDeltaY2()-EditorData.objectMeshes[index].getDeltaY();
			int dim_z=EditorData.objectMeshes[index].getDeltaX();
			
			double rot_angle=rotation_angle.getvalue();rotation_angle.getText();
			
			cleanObjects();
			
			addObject(centroid.x-dim_x*0.5,centroid.y-dim_y*0.5,centroid.z,dim_x,dim_y,dim_z,index,rot_angle);
				
	
				
		}
		
	}

	private void deselectAllPoints(){
		

			cleanPoints();
			
			PolygonMesh mesh=meshes[TERRAIN_INDEX];
			
			if(mesh.points==null)
				return; 
			
			for(int j=0;j<mesh.points.length;j++){


			    Point4D p=(Point4D) mesh.points[j];

				p.setSelected(false);
					
			}	
			int sizel=mesh.polygonData.size();
			for(int j=0;j<sizel;j++){
				
				
				LineData ld=(LineData) mesh.polygonData.elementAt(j);
			    ld.setSelected(false);	
			}
			
			coordinatesx[ACTIVE_PANEL].requestFocus();
			



	}
	
	private void deselectAllSPNodes() {
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.elementAt(i);
			
			Vector nodes=spline.nodes;
			
			for(int j=0;j<nodes.size();j++){
			
				SPNode spnode = (SPNode) nodes.elementAt(j);
				spnode.setSelected(false);
				
			}
		}	
		
	}

	private void deselectAllLines(){
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(j);
		    ld.setSelected(false);	
		}
	}


	private void selectPoint(int x, int y) {
		
		deselectAllObjects();
				
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected()) 
			polygon=new LineData();
		
		RoadEditorPanel ep = getCenter();
		
		boolean found=ep.selectPoints(x,y,mesh,polygon);

		if(found){
			deselectAllLines();
			return;
		}
		
		found=ep.selectPolygons(x,y,mesh);

		
		if(!found)
			deselectAllPoints();
		

	}
	
	private void selectSPNode(int x, int y) {
		
		
		RoadEditorPanel ep = getCenter();
		
		boolean found=ep.selectSPNodes(x,y,splines);
		

	}
	
	

	private void changePolygon(int x, int y) {

		if(meshes==null)
			return;

		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		RoadEditorPanel ep=getCenter();

		Vector cPolygons=ep.getClickedPolygons(x,y,mesh);
		
		for (int i = 0;cPolygons!=null && i < cPolygons.size(); i++) {

			LineData ld =(LineData) cPolygons.elementAt(i);


		     ///set here

	    	int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
	    	
	    	if(indx!=0){

				ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(indx);
					
					
				ld.setTexture_index(Integer.parseInt(vp.getId()));
			
	    	}

		}
		
	}
	
	
	
	private void selectObject(int x, int y) {
		
		if(checkHideObjects.isSelected())
			return;

		deselectAllPoints();
		deselectAllLines();
		
		RoadEditorPanel ep = getCenter();
		ep.selectObjects(x,y,drawObjects);

	}
	

	
	public void deselectAllObjects(){
		
		int size=drawObjects.size();
		
		for(int i=0;i<size;i++){
			
			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			dro.setSelected(false);
		}
	}

	public void cleanObjects(){
		
	
		rotation_angle.setText(0);
		
		deselectAllObjects();
		draw();
	}
	
	public void cleanPoints(){
		
		if(!checkCoordinatesx[ACTIVE_PANEL].isSelected())coordinatesx[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesy[ACTIVE_PANEL].isSelected())coordinatesy[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesz[ACTIVE_PANEL].isSelected())coordinatesz[ACTIVE_PANEL].setText("");
		
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
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		if(mesh.points==null)
			return;
		
		RoadEditorPanel ep = getCenter();
		
		boolean found=ep.selectPointsWithRectangle(mesh);
		
		if(found){
			deselectAllObjects();
		    deselectAllLines();  
		}
		

		

	}



	public void keyPressed(KeyEvent arg0) {

		
		RoadEditorPanel ep = getCenter();
		
		int code =arg0.getKeyCode();
		if(code==KeyEvent.VK_DOWN ){
			ep.down();
			draw();
		}else if(code==KeyEvent.VK_UP  ){
			ep.up();
			draw();
		}	
		else if(code==KeyEvent.VK_LEFT )
		{	
			ep.left();
			draw();
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	 
			ep.right();   
			draw();
		}
		else if(code==KeyEvent.VK_D  )
		{	
			deleteSelection();
			draw();
		}
		else if(code==KeyEvent.VK_N  )
		{	
			startNewSPLine();
			draw();
		}
		else if(code==KeyEvent.VK_P  )
		{	
			changeSelectedTerrainPoint();
			draw();
		}
		else if(code==KeyEvent.VK_B  )
		{	
			changeSelectedObject();
			draw();
		}
		else if(code==KeyEvent.VK_Y  )
		{ 
			changeSelectedTerrainPolygon();
			draw();
		}
		else if(code==KeyEvent.VK_E  )
		{ 
			deselectAll();
		}
		else if(code==KeyEvent.VK_F1  )
		{ 
			zoom(+1);
			draw();
		}
		else if(code==KeyEvent.VK_F2  )
		{  
			zoom(-1);
			draw();
		}
		else if(code==KeyEvent.VK_LESS )
		{	
			 
			invertSelectedRoadPolygon(); 
			draw();
		}else if(code==KeyEvent.VK_Q  )
		{  
			rotate(-1);
			draw();
		}
		else if(code==KeyEvent.VK_W  )
		{  
		
			rotate(+1);
			draw();
		}else if(code==KeyEvent.VK_ESCAPE )
		{  
		
			dispose();
			System.exit(0);
			
		}

	}




	private void zoom(int i) {
		
		

		 RoadEditorPanel ep = getCenter();
		 ep.zoom(i);
		 draw();
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}





	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}





	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		RoadEditorPanel ep = getCenter();
		
		int pix=arg0.getUnitsToScroll();
		if(pix>0) 
			ep.mouseUp();
		else 
			ep.mouseDown();
		
		draw();

	}





	public void mouseDragged(MouseEvent e) {
		
		
		isDrawCurrentRect=true;
		updateSize(e);
		draw();

	}
	
	public void mouseReleased(MouseEvent arg0) {
		
		if(ACTIVE_PANEL==1)
			return;
		
		isDrawCurrentRect=false;
		updateSize(arg0);
		
        selectPointsWithRectangle();
        draw();
       
	}

    void updateSize(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentRect.setSize(x - currentRect.x,
                            y - currentRect.y);
        
   	   
   
    }




	public void mouseMoved(MouseEvent e) {
		Point p=e.getPoint();
		
		RoadEditorPanel ep = getCenter();
		screenPoint.setText(ep.invertX((int)p.getX())+","+ep.invertY((int)p.getY()));

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
			 draw();
			 redrawAfterMenu=false;
		}
		else if("roadUpdate".equals(arg0.getPropertyName()))
		{
			 draw();
		}
		
	}
	
	public static int  pickRGBColorFromTexture(
			Texture texture,double px,double py,double pz,
			Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc
			){
		
		
		int x=0;
		int y=0;
		
	
		
       if(origin!=null){
			

			 x=(int) Math.round(Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,xDirection))+deltaX;
			 y=(int) Math.round(Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,yDirection))+deltaY;
			 
			 
		}
		else
		{
			
			  x=(int) Math.round(Point3D.calculateDotProduct(px,py, pz,xDirection))+deltaX;
			  y=(int) Math.round(Point3D.calculateDotProduct(px,py, pz,yDirection))+deltaY;

		}	
		
		
		int w=texture.getWidth();
		int h=texture.getHeight();
		
		//border fixed condition
		/*if(x<0) x=0;
		if(x>=w) x=w-1;
		if(y<0) y=0;
		if(y>=h) y=h-1;*/
		
		//border periodic condition
		if(x<0) x=w-1+x%w;
		if(x>=w) x=x%w;
		if(y<0) y=h-1+y%h;
		if(y>=h) y=y%h;
		
	
		
		int argb= texture.getRGB(x, h-y-1);
		
		return argb;
		
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
	
	/*public void migrateRoadToNewFormat(File fileIn,File fileOut,Point4D[][] roadData ) {

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
					pr.print(" ");
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
					pr.print(" ");
			}	

			pr.close(); 	
	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}*/
	
	private Point4D[] buildRow(String string,int NX) {
		StringTokenizer stk=new StringTokenizer(string," ");

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



	public void setRoadData(String string, PolygonMesh pMesh) {
		
		meshes[ACTIVE_PANEL]=pMesh;
		
		draw();
		
	}

	public boolean isDrawCurrentRect() {
		return isDrawCurrentRect;
	}

	public void setDrawCurrentRect(boolean isDrawCurrentRect) {
		this.isDrawCurrentRect = isDrawCurrentRect;
	}
	
	public void rotate(int signum){
		
		RoadEditorPanel ep = getCenter();
		ep.rotate(signum);
		
	
	}
	
	public static Vector buildTemplateTexturePoints(double side) {
		
		Vector vPoints=new Vector();
		
		vPoints.add(new Point3D(0,0,0));
		vPoints.add(new Point3D(side,0,0));
		vPoints.add(new Point3D(side,side,0));
		vPoints.add(new Point3D(0,side,0));
		
		return vPoints;
	}
	
	public Vector cloneSPLines(Vector splines){
		
		Vector newSplines=new Vector();
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine line = (SPLine) splines.elementAt(i);
			try {
				
				newSplines.add(line.clone());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return newSplines;
		
	}


}
