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
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;
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

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
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
import com.editors.IntegerTextField;
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



	private int HEIGHT=600;
	private int WIDTH=900;
	private int LEFT_BORDER=240;
	private int BOTTOM_BORDER=100;
	private int RIGHT_SKYP=10;

	private ArrayList<DrawObject> drawObjects=new ArrayList<DrawObject>();

	private Stack<ArrayList<DrawObject>> oldObjects=new Stack<ArrayList<DrawObject>>();
	private Stack<ArrayList<SPLine>> oldSpline=new Stack<ArrayList<SPLine>>();
	private int MAX_STACK_SIZE=10;

	private JMenuBar jmb;
	private JMenu jm_file;

	public DoubleTextField[] coordinatesx;
	public DoubleTextField[] coordinatesy;
	public DoubleTextField[] coordinatesz;
	public JCheckBox[] checkCoordinatesx;
	public JCheckBox[] checkCoordinatesy;
	public JCheckBox[] checkCoordinatesz;
	private JButton[] mergeSelectedPoints;

	public JCheckBox[] checkMultiplePointsSelection;
	private JButton[] changePolygon;
	private JButton[] deleteSelection;
	private JCheckBox[] checkMultiplePolygonsSelection;
	private JButton[] polygonDetail;
	public JComboBox[] chooseTexture;
	private JButton[] choosePanelTexture;
	private JButton[] chooseNextTexture;
	private JButton[] choosePrevTexture;
	private JButton[] deselectAllTerrainPoints;
	private JButton[] deselectAllTerrainPolygons;	
	private JLabel[] textureLabel;
	public JCheckBox[] fillWithWater;
	
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
	
	private static BufferedImage[] worldImages;	
	private static BufferedImage[] splinesImages;	
	private static BufferedImage[] objectImages;
	public static Texture[] objectIndexes; 
	
	public final int indexWidth=40;
	public final int indexHeight=18;

	private JMenuItem jmtShowAltimetry;
	private JMenuItem jmtBuildNewGrid;
	private JMenu help_jm;
	private JMenuItem jmt_load_landscape;
	private JMenuItem jmt_save_landscape;


	
	private String[] panelsTitles={"Terrain","Road"};
	
	private JMenuItem jmtAddGrid;
	
	private static ZBuffer landscapeZbuffer;
	private int blackRgb= Color.BLACK.getRGB();
	private int[] rgb=null;
	private Color selectionColor=null;
	private JMenuItem jmtExpandGrid;

	private JMenuItem jmtBuildCity;
	private JMenuItem help_jmt;
	
	private String header="<html><body>";
	private String footer="</body></html>";
	private JToggleButton toogle_splines;
	private JToggleButton toogle_terrain_points;
	private JToggleButton toogle_terrain_polygons;
	private JToggleButton toogle_objects;
	private JPanel left_tools;
	private JPanel left_common_options;
	private JPanel left_tool_options;
	
	private JCheckBox checkHideObjects;
	private JCheckBox checkHideSplines;
	
	private final String OBJECT_MODE="OBJECT_MODE";
	private final String SPLINES_MODE="SPLINES_MODE"; 
	private final String TERRAIN_POINTS_MODE="TERRAIN_POINTS_MODE";
	private final String TERRAIN_POLYGONS_MODE="TERRAIN_POLYGONS_MODE";
	
	private String mode=TERRAIN_POINTS_MODE;
	private JMenu jm_view;
	private JMenuItem jmt_3d_view;
	private JMenuItem jmt_top_view;
	
	private final int ISO_VIEW=1;
	private final int TOP_VIEW=0;

	private int VIEW_TYPE=TOP_VIEW;
	
	private RoadEditorPanel panelIso;
	private RoadEditorPanel panelTop;
	
	private transient BufferedImage buf=null;
	private Graphics2D graphics;
	private JButton startNewSPLine;
	private JButton insertSPNode;
	private JButton mergeSPNodes;
	private JButton changeSPNode;
	private JButton changeTerrainPoint;
	private JButton setSPNodeHeight;
	private DoubleTextField setSPNodeHeightValue;
	private boolean isInit;
	
	private transient Point3D startPosition=null;
	private IntegerTextField startX;
	private IntegerTextField startY;
	private JButton updateStartPosition;

	
	

	public static void main(String[] args) {

		RoadEditor re=new RoadEditor("New road editor");
		re.initialize();
	}
	
	private RoadEditor(String title){
		
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
					@Override
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


		checkMultiplePointsSelection= new JCheckBox[numPanels];
		mergeSelectedPoints=new JButton[numPanels];
		checkMultiplePolygonsSelection= new JCheckBox[numPanels];
		changePolygon=new JButton[numPanels];
		deleteSelection=new JButton[numPanels];	
		polygonDetail=new JButton[numPanels];	
		
		chooseTexture=new JComboBox[numPanels];	
		choosePanelTexture=new JButton[numPanels];		
		chooseNextTexture=new JButton[numPanels];
		choosePrevTexture=new JButton[numPanels];
		
		deselectAllTerrainPoints=new JButton[numPanels];
		deselectAllTerrainPolygons=new JButton[numPanels];
		textureLabel=new JLabel[numPanels];
		fillWithWater=new JCheckBox[numPanels];
		
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
	private void initialize() {

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
			
			ArrayList<File> vRoadTextures=new ArrayList<File>();

			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("world_texture_")){

					vRoadTextures.add(files[i]);

				}		
			}

			worldImages=new BufferedImage[vRoadTextures.size()];

			for(int i=0;i<vRoadTextures.size();i++){

				worldImages[i]=ImageIO.read(new File("lib/world_texture_"+i+".jpg"));

				
				chooseTexture[TERRAIN_INDEX].addItem(new ValuePair(Integer.toString(i),Integer.toString(i)));
				




			}


			ArrayList<File> vObjects=new ArrayList<File>();

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

				chooseObject.addItem(new ValuePair(Integer.toString(i),Integer.toString(i)));
				objectImages[i]=ImageIO.read(new File("lib/object_"+i+".gif"));
	
	
				BufferedImage boi=new BufferedImage(indexWidth,indexHeight,BufferedImage.TYPE_INT_RGB);
				boi.getGraphics().setColor(Color.white);
				boi.getGraphics().drawString(Integer.toString(i),0,indexHeight);
				objectIndexes[i]=new Texture(boi);	

			}
			
			splinesImages=new BufferedImage[EditorData.splinesEditorTextures.length];
			for(int i=0;i<EditorData.splinesEditorTextures.length;i++){
				
				chooseTexture[ROAD_INDEX].addItem(new ValuePair(Integer.toString(i),Integer.toString(i)));
				splinesImages[i]=ImageIO.read(new File("lib/spline_editor_"+i+".jpg"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}	

	}


	private void buildNewZBuffers() {


		for(int i=0;i<landscapeZbuffer.getSize();i++){

			landscapeZbuffer.setRgbColor(blackRgb, i);
            

		}
	

	}
	
	private void buildScreen(BufferedImage buf) {

		int length=rgb.length;
		
		for(int i=0;i<length;i++){


			//set
			rgb[i]=landscapeZbuffer.getRgbColor(i); 
			
			//clean
			landscapeZbuffer.set(0,0,0,0,blackRgb,Road.EMPTY_LEVEL,i,ZBuffer.EMPTY_HASH_CODE);
              
		

		}
		
		buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,rgb);
		//buf.setRGB(0,0,WIDTH,HEIGHT,rgb,0,WIDTH);


	}


	private void deselectAll() {
		
		if(ACTIVE_PANEL==TERRAIN_INDEX){
			deselectAllPoints();
		}	
		else if(ACTIVE_PANEL==ROAD_INDEX)
			deselectAllSPNodes();
		draw();
		
		
		polygon=new LineData();
	}

	private void polygonDetail() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(!ld.isSelected())
				continue;
			
			RoadEditorPolygonDetail repd=new RoadEditorPolygonDetail(this,ld);
	
			
			if(repd.getModifiedLineData()!=null){
				
				mesh.polygonData.set(i,repd.getModifiedLineData());
				
			}
			
			break;
		}
		draw();
		
	}

	private void draw() {


		Graphics2D graph = (Graphics2D) buf.getGraphics();
		RoadEditorPanel ep=getCenter();

		ep.setHide_objects(checkHideObjects.isSelected());
		ep.setHide_splines(checkHideSplines.isSelected());
		draw(ep,graph);
		
		buildScreen(buf); 

		if(graphics==null)
			graphics=(Graphics2D) ep.getGraphics();

		graphics.drawImage(buf,0,0,null);



	}


	private void draw( RoadEditorPanel editorPanel,Graphics2D graph) {


		graph.setColor(Color.BLACK);
		graph.fillRect(0,0,WIDTH,HEIGHT);
	

		editorPanel.drawRoad(meshes,drawObjects,splines,startPosition,landscapeZbuffer,graph);
	

		editorPanel.drawCurrentRect(landscapeZbuffer);

	}


	@Override
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
		jmtUndoSPLines = new JMenuItem("Undo last spline");
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
		left_tools.setBounds(5,0,200,upper_left_height);
		left.add(left_tools);
				
		left_common_options=new JPanel(null);
		left_common_options.setBounds(0,upper_left_height,LEFT_BORDER,middle_left_height);
		left.add(left_common_options);
		
		left_tool_options=new JPanel(new CardLayout());	
		left_tool_options.setBounds(0,middle_left_height+upper_left_height,LEFT_BORDER,lower_left_height);
		left.add(left_tool_options);
		
		
		toogle_terrain_points= new JToggleButton("POI");		
		toogle_terrain_points.setActionCommand(TERRAIN_POINTS_MODE);
		toogle_terrain_points.setSelected(true);
		toogle_terrain_points.addActionListener(this);
		toogle_terrain_points.addKeyListener(this);
		toogle_terrain_points.setToolTipText("Terrain points");
		toogle_terrain_points.setBounds(10,r,60,20);
		
		toogle_terrain_polygons= new JToggleButton("PLG");		
		toogle_terrain_polygons.setActionCommand(TERRAIN_POLYGONS_MODE);
		toogle_terrain_polygons.setSelected(true);
		toogle_terrain_polygons.addActionListener(this);
		toogle_terrain_polygons.addKeyListener(this);
		toogle_terrain_polygons.setToolTipText("Terrain polygons");
		toogle_terrain_polygons.setBounds(80,r,60,20);
		
		r+=30;
		
		toogle_splines = new JToggleButton("SPL");
		toogle_splines.setActionCommand(SPLINES_MODE);
		toogle_splines.addActionListener(this);
		toogle_splines.addKeyListener(this);
		toogle_splines.setToolTipText("Splines");
		toogle_splines.setBounds(10,r,60,20);
	
		toogle_objects = new JToggleButton("OBJ");
		toogle_objects.setActionCommand(OBJECT_MODE);
		toogle_objects.addActionListener(this);
		toogle_objects.addKeyListener(this);
		toogle_objects.setToolTipText("Ojbects");
		toogle_objects.setBounds(80,r,60,20);
		
		
		
		ButtonGroup bgb=new ButtonGroup();
		bgb.add(toogle_splines);
		bgb.add(toogle_terrain_points);
		bgb.add(toogle_terrain_polygons);
		bgb.add(toogle_objects);
				
		left_tools.add(toogle_terrain_points);
		left_tools.add(toogle_terrain_polygons);
		left_tools.add(toogle_splines);
		left_tools.add(toogle_objects);
		
		r=10;
		
		checkHideObjects=new JCheckBox(header+"Hide objects"+footer);
		checkHideObjects.setBounds(10,r,100,20);
		checkHideObjects.addKeyListener(this);
		checkHideObjects.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				draw();
			}
		});
		
		checkHideSplines=new JCheckBox(header+"Hide splines"+footer);
		checkHideSplines.setBounds(110,r,100,20);
		checkHideSplines.addKeyListener(this);
		checkHideSplines.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				draw();
			}
		});
		
		left_common_options.add(checkHideObjects);
		left_common_options.add(checkHideSplines);
		
		////OBJECTS
		        
        JPanel terrain_points_panel =  buildTerrainPointsPanel(TERRAIN_INDEX);
        left_tool_options.add(terrain_points_panel,TERRAIN_POINTS_MODE);
        
        JPanel terrain_polygons_panel =  buildTerrainPolygonsPanel(TERRAIN_INDEX);
        left_tool_options.add(terrain_polygons_panel,TERRAIN_POLYGONS_MODE);
        
        JPanel splines_panel = buildSPLinesPanel();
       
        left_tool_options.add(splines_panel,SPLINES_MODE);
        
        
        JPanel object_panel = buildObjectsPanel();
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

		chooseTexture[index]=new JComboBox();
		chooseTexture[index].addItem(new ValuePair("",""));
		//chooseTexture.setBounds(35,r,50,20);
		chooseTexture[index].addItemListener(this);
		chooseTexture[index].addKeyListener(this);
		//panel.add(chooseTexture);
		
		r+=30;

		choosePanelTexture[index]=new JButton("Texture");
		choosePanelTexture[index].setBounds(5,r,100,20);
		choosePanelTexture[index].addActionListener(this);
		choosePanelTexture[index].addKeyListener(this);
		splines_panel.add(choosePanelTexture[index]);
		
		r+=30;

		textureLabel[index]=new JLabel();
		textureLabel[index].setFocusable(false);
		textureLabel[index].setBounds(5,r,100,100);
		Border border=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		textureLabel[index].setBorder(border);
		splines_panel.add(textureLabel[index]);
		
		JPanel moveRoad=buildRoadMovePanel(120,r,index);
		splines_panel.add(moveRoad);

		r+=100;		
		
		choosePrevTexture[index]=new JButton("<");
		choosePrevTexture[index].setBounds(5,r,50,20);
		choosePrevTexture[index].addActionListener(this);
		choosePrevTexture[index].addKeyListener(this);
		splines_panel.add(choosePrevTexture[index]);

		chooseNextTexture[index]=new JButton(">");
		chooseNextTexture[index].setBounds(55,r,50,20);
		chooseNextTexture[index].addActionListener(this);
		chooseNextTexture[index].addKeyListener(this);
		splines_panel.add(chooseNextTexture[index]);
		
		r+=30;
		
		
		changeSPNode=new JButton(header+"Change node"+footer);
		changeSPNode.addActionListener(this);
		changeSPNode.addKeyListener(this);
		changeSPNode.setFocusable(false);
		changeSPNode.setBounds(5,r,150,20);
		splines_panel.add(changeSPNode);


		r+=30;
	

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

		deselectAllTerrainPoints[index]=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAllTerrainPoints[index].addActionListener(this);
		deselectAllTerrainPoints[index].setFocusable(false);
		deselectAllTerrainPoints[index].setBounds(5,r,150,20);
		splines_panel.add(deselectAllTerrainPoints[index]);

		
		r+=30;

		setSPNodeHeight=new JButton(header+"Set height"+footer);
		setSPNodeHeight.addActionListener(this);
		setSPNodeHeight.setFocusable(false);
		setSPNodeHeight.setBounds(5,r,100,20);
		splines_panel.add(setSPNodeHeight);
		
		setSPNodeHeightValue=new DoubleTextField(7);
		setSPNodeHeightValue.setBounds(110,r,120,20);
		setSPNodeHeightValue.addKeyListener(this);
		splines_panel.add(setSPNodeHeightValue);
		
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

	private JPanel buildTerrainPointsPanel(int index) {

		
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
		
		changeTerrainPoint=new JButton(header+"Change <u>P</u>oint"+footer);
		changeTerrainPoint.addActionListener(this);
		changeTerrainPoint.setFocusable(false);
		changeTerrainPoint.setBounds(5,r,150,20);
		panel.add(changeTerrainPoint);

		r+=30;		

		deselectAllTerrainPoints[index]=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAllTerrainPoints[index].addActionListener(this);
		deselectAllTerrainPoints[index].setFocusable(false);
		deselectAllTerrainPoints[index].setBounds(5,r,150,20);
		panel.add(deselectAllTerrainPoints[index]);
		
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

	private JPanel buildTerrainPolygonsPanel(int index) {

		
		JPanel panel=new JPanel();

		panel.setLayout(null);
		
		Border leftBorder=BorderFactory.createTitledBorder(panelsTitles[index]);
		panel.setBorder(leftBorder);

		int r=25;

		checkMultiplePolygonsSelection[index]=new JCheckBox("Multiple selection");
		checkMultiplePolygonsSelection[index].setBounds(30,r,150,20);
		checkMultiplePolygonsSelection[index].addKeyListener(this);
		panel.add(checkMultiplePolygonsSelection[index]);

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
		
		fillWithWater[index]=new JCheckBox("Water");
		fillWithWater[index].addKeyListener(this);
		fillWithWater[index].setFocusable(false);
		fillWithWater[index].setBounds(5,r,150,20);
		panel.add(fillWithWater[index]);

		r+=30;

		deselectAllTerrainPolygons[index]=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAllTerrainPolygons[index].addActionListener(this);
		deselectAllTerrainPolygons[index].setFocusable(false);
		deselectAllTerrainPolygons[index].setBounds(5,r,150,20);
		panel.add(deselectAllTerrainPolygons[index]);
		
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
		
		JLabel label = new JLabel();
		label.setText("Start X: ");
		label.setBounds(2,2,60,20);
		bottom.add(label);
		
		startX=new IntegerTextField();
		startX.setBounds(60,2,100,20);
		startX.addKeyListener(this);
		bottom.add(startX);
		
		
		label = new JLabel();
		label.setText("Start Y: ");
		label.setBounds(180,2,60,20);
		bottom.add(label);
	
		startY=new IntegerTextField();
		startY.setBounds(240,2,100,20);
		startY.addKeyListener(this);
		bottom.add(startY);
		
		
		updateStartPosition=new JButton("Update");
		updateStartPosition.setBounds(360,2,100,20);
		updateStartPosition.setFocusable(false);
		updateStartPosition.addActionListener(this);
		bottom.add(updateStartPosition);
		
		
		JLabel lscreenpoint = new JLabel();
		lscreenpoint.setText("Position x,y: ");
		lscreenpoint.setBounds(500,2,100,20);
		bottom.add(lscreenpoint);
		screenPoint=new JLabel();
		screenPoint.setText(",");
		screenPoint.setBounds(600,2,300,20);
		bottom.add(screenPoint);
		add(bottom);
	}

	private void undoObjects() {


		drawObjects=(ArrayList<DrawObject>) oldObjects.pop();
		if(oldObjects.size()==0)
			jmtUndoObjects.setEnabled(false);
	}

	private void undoSplines() {

		splines=(ArrayList<SPLine>) oldSpline.pop();
		if(oldSpline.size()==0)
			jmtUndoSPLines.setEnabled(false);
		
		firePropertyChange("RoadEditorUndo", false, true);
	}
	@Override
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



	private ArrayList<DrawObject> cloneObjectsVector(ArrayList<DrawObject> drawObjects) {
		ArrayList<DrawObject> newDrawObjects=new ArrayList<DrawObject>();

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.get(i);
			newDrawObjects.add((DrawObject) dro.clone());

		}

		return newDrawObjects;
	}

	private void changeSelectedObject() {
		
		prepareUndoObjects();

		for(int i=0;i<drawObjects.size();i++){
			
			DrawObject dro=(DrawObject) drawObjects.get(i);

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
			setObjectMesh(dro);

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
			
			DrawObject dro=(DrawObject) drawObjects.get(i);
			
			if(!dro.isSelected())
				continue;

			dro.setX(dro.getX()+dx*qty);
			dro.setY(dro.getY()+dy*qty);
			dro.setZ(dro.getZ()+dk*qty);
			//dro.setSelected(false);
			
			if(!checkMultipleObjectsSelection.isSelected()){
			
				setObjectData(dro);					
	
			
			}
			setObjectMesh(dro);
		}
       
		//cleanObjects();
		draw();
		
	}

	public void setObjectData(DrawObject dro) { 
		


		for(int k=0;k<chooseObject.getItemCount();k++){

			ValuePair vp=(ValuePair) chooseObject.getItemAt(k);
			if(vp.getId().equals(Integer.toBinaryString(dro.getIndex())) )
				chooseObject.setSelectedItem(vp);
		}

		rotation_angle.setText(dro.getRotation_angle());
		
	}
	

	private void changeSelectedSPNode() {
		
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.get(i);
			
			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();
			
			boolean found=false;
			
			for(int j=0;j<sz;j++){
			
				SPNode spnode =(SPNode) nodes.get(j);
				
				if(spnode.isSelected){
					
					found=true;
					
					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
					if(!vp.getId().equals(""))
						spnode.setIndex(Integer.parseInt(vp.getId()));
					
				}
				
				
				if(found)
					spline.calculateRibs();
				
			}		
		
		}
		
		draw();
		
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
		updateSPlines();
		
        firePropertyChange("RoadEditorUpdate", false, true);

		cleanPoints();
		draw();
	}
	
	private void changeSelectedTerrainPolygon() {
		
	
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData)mesh.polygonData.get(j);
		    
		    
		    if(ld.isSelected){
		    	
		    	int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
		    	
		    	if(indx!=0){
	
					ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(indx);
						
						
					ld.setTexture_index(Integer.parseInt(vp.getId()));
					ld.setFilledWithWater(fillWithWater[ACTIVE_PANEL].isSelected());
				
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
			
			
			LineData ld=(LineData) mesh.polygonData.get(i);
		    
		    
		    if(ld.isSelected){
		    	
		    	LineData invertedLd=new LineData();
				
				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				mesh.polygonData.set(i,invertedLd);
		    	
		    }
			
		}	
		
	}

	private void mergeSelectedPoints() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		ArrayList<Point3D> newPoints=new ArrayList<Point3D>();
		ArrayList<LineData> newLines=new ArrayList<LineData>();

		
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

			LineData ld=(LineData)mesh.polygonData.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();
			newLd.setTexture_index(ld.getTexture_index());
			boolean insertedFirst=false;

			for(int j=0;j<ld.size();j++){

				Point3D p0= mesh.points[ld.getIndex(j)];
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.get(k);
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
		
		ArrayList<SPLine> newSplines=new ArrayList<SPLine>();
	
		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);
			
			SPLine newSpline=new SPLine(spline.getvTexturePoints());
			
			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();

			for (int j = 0; j < sz; j++) {
				
				SPNode node = (SPNode)  nodes.get(j);
				
				if(node.isSelected)
					continue;

		
				newSpline.addSPNode(node);
			}

			
			if(newSpline.getNodes()!=null && newSpline.getNodes().size()>0){

				newSplines.add(newSpline);
			}
			
		}
		
		splines=newSplines;
		
		updateSPlines();
		
	}

	private void deleteTerrainSelection() {
		
		prepareUndo();
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		ArrayList<Point3D> newPoints=new ArrayList<Point3D>();
		ArrayList<LineData> newLines=new ArrayList<LineData>();

		for(int i=0;mesh.points!=null && i<mesh.points.length;i++){

			Point3D p=mesh.points[i];
			if(!p.isSelected()) 
				newPoints.add(p);

		}

		
		int sizel=mesh.polygonData.size();
		for(int i=0;i<sizel;i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			boolean gotAllPoint=true;
			
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
		
		updateSPlines();
		
		firePropertyChange("RoadEditorUpdate", false, true);

		cleanPoints();
		draw();

	}
	
	private void moveSelectedSplinesPoints(int dx, int dy, int dz) {
		
		prepareUndoSpline();
		
		String sqty=roadMove[ACTIVE_PANEL].getText();

		if(sqty==null || sqty.equals(""))
			return;

		double qty=Double.parseDouble(sqty);
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.get(i);
			
			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();
			
			boolean found=false;
			
			for(int j=0;j<sz;j++){
			
				SPNode spnode = (SPNode) nodes.get(j);
				
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
			
			DrawObject dro=(DrawObject) drawObjects.get(i);
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
		dro.setX(x);
		dro.setY(y);
		dro.setZ(z);
		dro.setDx(dx);
		dro.setDy(dy);
		dro.setDz(dz);
		dro.setHexColor("FFFFFF");
		dro.setRotation_angle(rot_angle);

		ValuePair vp=(ValuePair) chooseObject.getSelectedItem();
		if(vp!=null && !vp.getValue().equals("")){
			
			
			dro.setIndex(Integer.parseInt(vp.getId()));
		
		
		}	

			
		
		setObjectMesh(dro);
		

		dro.setRotation_angle(rot_angle);
		drawObjects.add(dro);


	}

	private static void setObjectMesh(DrawObject dro) {
		
		Point3D center=null;

		CubicMesh cm=(CubicMesh) EditorData.objectMeshes[dro.getIndex()].clone();

		Point3D point = cm.point000;

		double dx=-point.x+dro.getX();
		double dy=-point.y+dro.getY();
		double dz=-point.z+dro.getZ();

		cm.translate(dx,dy,dz);			

		center=cm.findCentroid();

		if(dro.getRotation_angle()!=0)
			cm.rotate(center.x,center.y,Math.cos(dro.getRotation_angle()),Math.sin(dro.getRotation_angle()));


		dro.setMesh(cm);
		
	}

	private void saveObjects(PrintWriter pr) {
		
		try {

            pr.println("<objects>");			 
			for(int i=0;i<drawObjects.size();i++){

				DrawObject dro=(DrawObject) drawObjects.get(i);

				pr.println(dro.toString());
			}
			pr.println("</objects>");
				

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	





	private void saveStartPosition(PrintWriter pr) {
		
		try {
			
			startPosition=new Point3D(startX.getvalue(),startY.getvalue(),0);

            pr.println("<startPosition>");		

			pr.println(startPosition.toString());
			
			pr.println("</startPosition>");
				

		} catch (Exception e) {

			e.printStackTrace();
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
				
				setACTIVE_PANEL(TERRAIN_INDEX);
				saveLines(pr);
				setACTIVE_PANEL(ROAD_INDEX);
				saveSPLines(pr);
				setACTIVE_PANEL(TERRAIN_INDEX);
				
				saveObjects(pr);
				
				saveStartPosition(pr);

				
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
			
			setACTIVE_PANEL(TERRAIN_INDEX);
			loadPointsFromFile(file,ACTIVE_PANEL,forceReading);	
			loadSPLinesFromFile(file);
			
			drawObjects=loadObjectsFromFile(file,EditorData.objectMeshes); 
            setStartPosition(loadStartPosition(file));
            
            oldSpline=new Stack();
            oldObjects=new Stack();
            
            isInit=true;

		}
		
	}


	@Override
	public String decomposeLineData(LineData ld) {

		String str="";
		
		str+="T"+ld.texture_index;
		str+=" C"+ld.getHexColor();
		str+=" W"+(ld.isFilledWithWater()?1:0);

		for(int j=0;j<ld.size();j++){
			
			
			
			str+=" ";
			
			str+=ld.getIndex(j)+"/"+j;

		}

		return str;
	}
	
	@Override
	public void decomposeObjVertices(PrintWriter pr, PolygonMesh mesh,boolean isCustom) {
	
		pr.print("vt=0 0\n");//0
		pr.print("vt=200 0\n");//1
		pr.print("vt=200 200\n");//2
		pr.print("vt=0 200\n");//3
	}
	
	@Override
	public void buildLine(ArrayList<LineData> polygonData, String str,ArrayList<Point3D> vTexturePoints) {



		Road.buildStaticLine(polygonData,  str, vTexturePoints);


	}
	@Override
	public void buildPoint(List<Point3D> points, String str) {



			String[] vals = str.split(" ");

			Point4D p=new Point4D();
			
			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			points.add(p);
	

	}

	
	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object obj=arg0.getSource();

		
		if(toogle_objects==obj || toogle_splines==obj || toogle_terrain_points==obj || toogle_terrain_polygons==obj){
			CardLayout cl = (CardLayout)(left_tool_options.getLayout());
			
			mode=((JToggleButton) obj).getActionCommand();
			cl.show(left_tool_options,mode);
			
			if(SPLINES_MODE.equals(mode))
				ACTIVE_PANEL=ROAD_INDEX;
			else if(TERRAIN_POINTS_MODE.equals(mode) || TERRAIN_POLYGONS_MODE.equals(mode))
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
		else if(obj==changeTerrainPoint){
			changeSelectedTerrainPoint();
			draw();
		}
		else if(obj==changeSPNode ){
			changeSelectedSPNode();
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
		else if(obj==deselectAllTerrainPoints[ACTIVE_PANEL] || obj==deselectAllTerrainPolygons[ACTIVE_PANEL]){
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
			
			TexturesPanel tp=null;
			if(ACTIVE_PANEL==TERRAIN_INDEX)
				tp=new TexturesPanel(worldImages,100,100);
			else
				tp=new TexturesPanel(splinesImages,100,100);
			
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
		else if(obj==setSPNodeHeight){
			setSPNodeHeight();
		}	
		else if(obj==updateStartPosition){
			
			updateStartPosition();
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

		PolygonMesh mesh=meshes[Editor.TERRAIN_INDEX];
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
			npm.setLevel(Road.GROUND_LEVEL);
			
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
			
			ArrayList<Point3D> vTexturePoints=buildTemplateTexturePoints(200);
					
		
			
			int count=0;
			
			for (int i = 0; i < numx-1; i++) {
				
				for (int j = 0; j < numy-1; j++) {
					
					
					//base z=0
					
					int pos0=pos(i,j,numx,numy);
					int pos1=pos(i+1,j,numx,numy);
					int pos2=pos(i+1,j+1,numx,numy);
					int pos3=pos(i,j+1,numx,numy);
					
				
					Point3D pt0=(Point3D) vTexturePoints.get(0);
					Point3D pt1=(Point3D) vTexturePoints.get(1);
					Point3D pt2=(Point3D) vTexturePoints.get(2);
					Point3D pt3=(Point3D) vTexturePoints.get(3);

					LineData ld=new LineData();
					ld.addIndex(pos0,0,pt0.x,pt0.y);
					ld.addIndex(pos1,1,pt1.x,pt1.y);
					ld.addIndex(pos2,2,pt2.x,pt2.y);
					ld.addIndex(pos3,3,pt3.x,pt3.y);
					
					
					npm.polygonData.add(ld);
					
					if(i<pm.getNumx()-1 && j<pm.getNumy()-1){
						
						LineData oldLineData=pm.polygonData.get(count++);
						ld.setTexture_index(oldLineData.getTexture_index());
					}
					else{
						
							ld.setTexture_index(0);
						
					}
						
				}
			}	
			//pm=(SquareMesh) PolygonMesh.simplifyMesh(npm);
			meshes[TERRAIN_INDEX]=npm;
		
			
			
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
			
			PolygonMesh mesh=meshes[TERRAIN_INDEX];
			mesh.setLevel(Road.GROUND_LEVEL);

			
			double z_value=0;
			
			int numx=roadEGM.NX;
			int numy=roadEGM.NY;
			
			double dx=roadEGM.DX;
			double dy=roadEGM.DY;
			
			double x_0=roadEGM.X0;
			double y_0=roadEGM.Y0;
			
			int tot=numx*numy;
			
		
			mesh.polygonData=new ArrayList<LineData>();
			
			mesh.points=new Point3D[numy*numx];
			
			
			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{
					
					Point4D p=new Point4D(i*dx+x_0,j*dy+y_0,z_value);
				
					mesh.points[i+j*numx]=p;

				}

			ArrayList<Point3D> vTexturePoints=buildTemplateTexturePoints(200);
			
			for(int i=0;i<numx-1;i++)
				for(int j=0;j<numy-1;j++){

	
					//lower base
					
					int pl1=pos(i,j,numx,numy);
					int pl2=pos(i+1,j,numx,numy);
					int pl3=pos(i+1,j+1,numx,numy);
					int pl4=pos(i,j+1,numx,numy);
					
					Point3D pt0=(Point3D) vTexturePoints.get(0);
					Point3D pt1=(Point3D) vTexturePoints.get(1);
					Point3D pt2=(Point3D) vTexturePoints.get(2);
					Point3D pt3=(Point3D) vTexturePoints.get(3);

					LineData ld=new LineData();
					ld.addIndex(pl1,0,pt0.x,pt0.y);
					ld.addIndex(pl2,1,pt1.x,pt1.y);
					ld.addIndex(pl3,2,pt2.x,pt2.y);
					ld.addIndex(pl4,3,pt3.x,pt3.y);

					
					ld.setTexture_index(0);
					
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
			RoadEditorCityManager.buildCustomCity1(meshes[0],splines,roadECM,drawObjects,EditorData.objectMeshes);
			//meshes[1]=PolygonMesh.simplifyMesh(meshes[1]);
			
			draw();
		}
		
	}
	
	
	
	private void updateSPlines(){
		
		
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.get(i);
		    sp.calculateRibs();	
			
		}
		if(meshes[TERRAIN_INDEX]==null || meshes[TERRAIN_INDEX].polygonData==null)
			return;
		
		//Editor.levelSPLinesTerrain(meshes[TERRAIN_INDEX],splines);
		
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
	
	private void startNewSPLine() {
		
		prepareUndo();

		isInit=true;

	}

	private void showAltimetry() {
		
		PolygonMesh mesh=meshes[TERRAIN_INDEX];
		
		if(mesh==null || mesh.points.length==0)
			return;
		RoadAltimetryPanel altimetry=new RoadAltimetryPanel(this);
	}

	@Override
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
		
		} else if(TERRAIN_POINTS_MODE.equals(mode)){
		
	
			selectTerrainPoint(arg0.getX(),arg0.getY());			
		
		
		
		} else if(TERRAIN_POLYGONS_MODE.equals(mode)){
			
			//right button click
			if(buttonNum==MouseEvent.BUTTON3){
	
				deselectAll();
				changePolygon(arg0.getX(),arg0.getY());
				
			}
			else{
				selectTerrainPolygons(arg0.getX(),arg0.getY());			
			}	
		
		}
		else if(OBJECT_MODE.equals(mode)){
			
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
		
		ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
		if(!vp.getId().equals(""))
			index=Integer.parseInt(vp.getId());
		
		SPNode p0=new SPNode(x,y,0,LineData.GREEN_HEX,index);
		
		if(isInit || splines.size()==0){
			
			isInit=false;
						
			ArrayList<Point3D> vTexturePoints=buildTemplateTexturePoints(200);
			
			SPLine sp=new SPLine(vTexturePoints);	

			sp.addSPNode(p0);
			splines.add(sp);
			
		}else{
			
			SPLine sp=(SPLine) splines.get(splines.size()-1);
			
			ArrayList<Point3D> vTexturePoints=buildTemplateTexturePoints(200);
			sp.addSPNode(p0);
			
		}
	
		

	}
	
	private void mergeSPNodes() {
		
		prepareUndoSpline();
		
		SPNode baseNode=null;
		
		for (int i =0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);
			
			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();
			
			
			for (int k = 0; k < sz; k++) {
				
				SPNode node = (SPNode) nodes.get(k);

				if(node.isSelected()){
					
					if(baseNode==null){
						
						baseNode=node;
						
					}else{
						
						nodes.set(k,baseNode.clone());
					}
					
				}
			
			}


			
		}
		
		updateSPlines();
		
		deselectAllSPNodes();
		draw();
		
	}

	private void insertSPNode() {
		
		prepareUndoSpline();
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);
			
			
			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();
			
			ArrayList<SPNode> newNodes=new ArrayList<SPNode>();
			
			for (int k = 0; k < sz; k++) {
				
				SPNode node = (SPNode) nodes.get(k);
			
				newNodes.add(node);

				if(node.isSelected()){
					
					SPNode nextNode=null;
					
					if(k+1< sz){
						
						nextNode= (SPNode)  nodes.get(k+1);
						
					}else{
						
						//avoid problem when updating the last node!
						break;
						
					}
					
					
					
					double x=(node.x+nextNode.x)*0.5;
					double y=(node.y+nextNode.y)*0.5;
					double z=(node.z+nextNode.z)*0.5;
					
					
					SPNode intermediateNode=new SPNode((int)x,(int)y,(int)z,"FFFFFF",node.getIndex());
					
					newNodes.add(intermediateNode);
					
				}
			
			}
			 spline.setNodes(newNodes);

			
		}
		
		updateSPlines();
		
		deselectAllSPNodes();
		draw();
	}
	
	


	private void setSPNodeHeight() {
		
		double height=setSPNodeHeightValue.getvalue();
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);
		
			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();
			
			
			for (int k = 0; k < sz; k++) {
				
				SPNode node = (SPNode) nodes.get(k);
			

				if(node.isSelected()){
					
					node.z=height;
					node.update();
				}
			
			}
		

			
		}
		
		updateSPlines();
		
		deselectAllSPNodes();
		draw();
		
	}
	

	private void putObjectInCell(int x, int y) {
		
		if(meshes==null)
			return;
		
		PolygonMesh mesh=meshes[TERRAIN_INDEX];	
		
		RoadEditorPanel ep=getCenter();	
			
		ArrayList<LineData> polygons=ep.getClickedPolygons(x,y,mesh);
		
		if(polygons.size()>0){
			
			
			LineData ld=(LineData)polygons.get(0);
			
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(mesh.points,ld,mesh.getLevel());
			
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
			
			if(ACTIVE_PANEL==TERRAIN_INDEX)
				coordinatesx[ACTIVE_PANEL].requestFocus();
			



	}
	
	private void deselectAllSPNodes() {
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.get(i);

			ArrayList<SPNode> nodes = spline.getNodes();
			int sz=nodes.size();
			
			
			for(int j=0;j<sz;j++){
			
				SPNode spnode = (SPNode) nodes.get(j);
				spnode.setSelected(false);
				
			}
		}	
		
	}

	private void deselectAllLines(){
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		int sizel=mesh.polygonData.size();
		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) mesh.polygonData.get(j);
		    ld.setSelected(false);	
		}
	}


	private void selectTerrainPoint(int x, int y) {
		
		deselectAllObjects();
				
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected()) 
			polygon=new LineData();
		
		RoadEditorPanel ep = getCenter();
		
		boolean found=ep.selectPoints(x,y,mesh,polygon);
		

	}
	
	private void selectTerrainPolygons(int x, int y) {
		
		deselectAllObjects();
				
		PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		if(!checkMultiplePointsSelection[ACTIVE_PANEL].isSelected()) 
			polygon=new LineData();
		
		RoadEditorPanel ep = getCenter();
		
		boolean found=ep.selectPolygons(x,y,mesh);

		

	}
	
	private void selectSPNode(int x, int y) {
		
		
		RoadEditorPanel ep = getCenter();
		
		boolean found=ep.selectSPNodes(x,y,splines);
		

	}
	
	public void setSPLineData(SPLine spline, SPNode spnode){
		
		
		for(int l=0;l<chooseTexture[ACTIVE_PANEL].getItemCount();l++){
			
			ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(l);
			if(vp.getId().equals(Integer.toString(spnode.getIndex()))) 
				chooseTexture[ACTIVE_PANEL].setSelectedItem(vp);
		}
		
		setSPNodeHeightValue.setText(spnode.getZ());
		
	}

	private void changePolygon(int x, int y) {

		if(meshes==null)
			return;

		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		RoadEditorPanel ep=getCenter();

		ArrayList<LineData> cPolygons=ep.getClickedPolygons(x,y,mesh);
		
		for (int i = 0;cPolygons!=null && i < cPolygons.size(); i++) {

			LineData ld =(LineData) cPolygons.get(i);


		     ///set here

	    	int indx=chooseTexture[ACTIVE_PANEL].getSelectedIndex();
	    	
	    	if(indx!=0){

				ValuePair vp=(ValuePair) chooseTexture[ACTIVE_PANEL].getItemAt(indx);
					
					
				ld.setTexture_index(Integer.parseInt(vp.getId()));
			
	    	}
	    	
	    	ld.setFilledWithWater(fillWithWater[ACTIVE_PANEL].isSelected());

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
	

	
	private void deselectAllObjects(){
		
		int size=drawObjects.size();
		
		for(int i=0;i<size;i++){
			
			DrawObject dro=(DrawObject) drawObjects.get(i);
			dro.setSelected(false);
		}
	}

	private void cleanObjects(){
		
	
		rotation_angle.setText(0);
		
		deselectAllObjects();
		draw();
	}
	
	private void cleanPoints(){
		
		if(ACTIVE_PANEL!=TERRAIN_INDEX)
			return;
		
		if(!checkCoordinatesx[ACTIVE_PANEL].isSelected())coordinatesx[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesy[ACTIVE_PANEL].isSelected())coordinatesy[ACTIVE_PANEL].setText("");
		if(!checkCoordinatesz[ACTIVE_PANEL].isSelected())coordinatesz[ACTIVE_PANEL].setText("");
		
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
	
	

	private void selectPolygonsWithRectangle() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		if(mesh.points==null)
			return;
		
		RoadEditorPanel ep = getCenter();
		
		boolean  found=ep.selectPolygonsWithRectangle(mesh);
		
		if(found){
			deselectAllObjects();
			deselectAllPoints();  
		}
	}


	@Override
	public void keyPressed(KeyEvent arg0) {

		
		RoadEditorPanel ep = getCenter();
		
		int code =arg0.getKeyCode();
		if(code==KeyEvent.VK_DOWN ){
			ep.translate(0, 1);
			draw();
		}else if(code==KeyEvent.VK_UP  ){
			ep.translate(0, -1);
			draw();
		}	
		else if(code==KeyEvent.VK_LEFT )
		{	
			ep.translate(-1, 0);
			draw();
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	 
			ep.translate(1, 0);  
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
		
		RoadEditorPanel ep = getCenter();
		
		int pix=arg0.getUnitsToScroll();
		if(pix>0) 
			ep.mouseUp();
		else 
			ep.mouseDown();
		
		draw();

	}




	@Override
	public void mouseDragged(MouseEvent e) {
		
		
		isDrawCurrentRect=true;
		updateSize(e);
		draw();

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		if(ACTIVE_PANEL==1)
			return;
		
		isDrawCurrentRect=false;
		updateSize(arg0);
		
		if(mode==TERRAIN_POINTS_MODE)
			selectPointsWithRectangle();
		else if(mode==TERRAIN_POLYGONS_MODE)
			selectPolygonsWithRectangle();
        draw();
       
	}


	private void updateSize(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentRect.setSize(x - currentRect.x,
                            y - currentRect.y);
        
   	   
   
    }



    @Override
	public void mouseMoved(MouseEvent e) {
		Point p=e.getPoint();
		
		RoadEditorPanel ep = getCenter();
		screenPoint.setText(ep.invertX((int)p.getX())+","+ep.invertY((int)p.getY()));

	}
    @Override
	public void itemStateChanged(ItemEvent arg0) {

		Object o=arg0.getSource();
		if(o==chooseTexture[ACTIVE_PANEL]){

			ValuePair val=(ValuePair) chooseTexture[ACTIVE_PANEL].getSelectedItem();
			if(!val.getId().equals("")){

				int num=Integer.parseInt(val.getId());
				
				BufferedImage icon=new BufferedImage(100,100,BufferedImage.TYPE_3BYTE_BGR);
				if(ACTIVE_PANEL==TERRAIN_INDEX)
					icon.getGraphics().drawImage(worldImages[num],0,0,objectLabel.getWidth(),objectLabel.getHeight(),null);
				else
					icon.getGraphics().drawImage(splinesImages[num],0,0,objectLabel.getWidth(),objectLabel.getHeight(),null);
				
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
	@Override
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
	
	void setRoadData(String string, PolygonMesh pMesh) {
		
		meshes[ACTIVE_PANEL]=pMesh;
		
		draw();
		
	}

	public boolean isDrawCurrentRect() {
		return isDrawCurrentRect;
	}

	public void setDrawCurrentRect(boolean isDrawCurrentRect) {
		this.isDrawCurrentRect = isDrawCurrentRect;
	}
	
	private void rotate(int signum){
		
		RoadEditorPanel ep = getCenter();
		ep.rotate(signum);
		
	
	}
	
	static ArrayList<Point3D> buildTemplateTexturePoints(double side) {
		
		ArrayList<Point3D> vPoints=new ArrayList<Point3D>();
		
		vPoints.add(new Point3D(0,0,0));
		vPoints.add(new Point3D(side,0,0));
		vPoints.add(new Point3D(side,side,0));
		vPoints.add(new Point3D(0,side,0));
		
		return vPoints;
	}
	
	private ArrayList<SPLine> cloneSPLines(ArrayList<SPLine> splines){
		
		ArrayList<SPLine> newSplines=new ArrayList<SPLine>();
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine line = (SPLine) splines.get(i);
			try {
				
				newSplines.add(line.clone());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return newSplines;
		
	}

	public Point3D getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Point3D startPosition) {
		this.startPosition = startPosition;
		startX.setText((int) this.startPosition.x);
		startY.setText((int) this.startPosition.y);
	}


	private void updateStartPosition() {
		
		startPosition=new Point3D(startX.getvalue(),startY.getvalue(),0);
		draw();
		
	}

	public ArrayList<DrawObject> getDrawObjects() {
		return drawObjects;
	}

}
