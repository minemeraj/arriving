package com.editors.autocars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.BarycentricCoordinates;
import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPNode;
//import com.Renderer2D;
import com.SPLine;
import com.Texture;
import com.ZBuffer;
import com.editors.ComboElement;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.EditorData;
import com.editors.ValuePair;
import com.editors.road.RoadEditor;
import com.main.Autocar;
import com.main.HelpPanel;
import com.main.Road;

public class AutocarEditor extends Editor implements MouseListener,
		ActionListener, MenuListener, KeyListener, MouseWheelListener,
		MouseMotionListener, FocusListener, PropertyChangeListener {
	
	
	int WIDTH = 820;
	int HEIGHT = 880;

	int LEFT_BORDER = 180;
	int RIGHT_BORDER = 200;

	JPanel center = null;
	JPanel right = null;

	Color background_color = Color.BLACK;

	Graphics2D graph = null;

	JButton deleteSelection = null;
	private JMenuBar jmb;
	private JMenuItem jmt_load_autocar;
	private JMenuItem jmt_save_autocar;

	JFileChooser fc = new JFileChooser();
	File currentDirectory = null;

	int x0 = 0;
	int y0 = 0;

	double dx = 2.0;
	double dy = 2.0;

	int deltax = 10;
	int deltay = 10;
	private DoubleTextField xcoordinate;
	private DoubleTextField ycoordinate;
	private DoubleTextField zcoordinate;
	private JCheckBox checkMultipleSelection;

	private JButton changePoint;
	private JButton deselectAll;

	public int MAX_STACK_SIZE = 10;
	private JMenuItem jmt_undo;
	private DoubleTextField pointsMove;
	private JButton movePointsUp;
	private JButton movePointsDown;
	private JButton movePointsLeft;
	private JButton movePointsRight;
	private JButton movePointsTop;
	private JButton movePointsBottom;

	public Rectangle currentRect = null;
	public boolean isDrawCurrentRect = false;

	
	public Stack oldAutocarsData=new Stack();
	public Stack oldAutolinesData=new Stack();

	
	private DoubleTextField centerX;
	private DoubleTextField centerY;
	private DoubleTextField fi_angle;
	private DoubleTextField steering_angle;
	private DoubleTextField longitudinal_velocity;
	private DoubleTextField lateral_velocity;

	public JButton rescale;
	public DoubleTextField rescaleValue;
	public JButton selectAll;
	private JMenuItem jmt_template;
	private JMenuItem jmt_load_landscape;

	private BufferedImage buf = null;

	int[] rgb = null;

	private JMenuItem jmt_refresh;

	private JMenu jmt_help;

	private boolean redrawAfterMenu = false;

	private JMenuItem jmt_advance_road;
	
	private Vector autocarsData = null;
	private Vector autolinesData = null;

	private JComboBox chooseAutoline;

	private JMenuItem jmt_addautocar;

	private JMenuItem jmt_deleteautocar;

	private JButton chooseAutolineMinus;

	private JButton chooseAutolinePlus;
	private JButton updateCar;

	private JComboBox car_type_index;

	private JComboBox chooseAutocar;
	private JButton chooseAutocarMinus;
	private JButton chooseAutocarPlus;
	private JButton moveAutocarsUp;
	private JButton moveAutocarsDown;
	private JButton moveAutocarsLeft;
	private JButton moveAutocarsRight;
	private JPanel left;
	private JCheckBox is_parked;
	private JMenuItem jmt_addautoline;
	private JMenuItem jmt_deleteautoline;
	private JComboBox chooseAutocarline;
	private JMenuItem jmt_help_show;
	private DoubleTextField autocarsMove;
	private JButton addCarToAutoline;
	private Vector drawObjects=null;
	private Texture[] worldTextures;
	private BufferedImage[] objectImages;
	private CubicMesh[] objectMeshes;
	private Texture[] objectIndexes;
	
	int indexWidth=40;
	int indexHeight=18;
	
	public Area totalVisibleField=null;
	public static ZBuffer landscapeZbuffer;
	public int blackRgb= Color.BLACK.getRGB();
	
	public Color selectionColor=null;
	
	public int mask = 0xFF;
	public double a1=0.6;
	public double a2=1.0-a1;
	
	public double rr=0;
	public double gg=0;
	public double bb=0;
	
	public int greenRgb= Color.GREEN.getRGB();
	
	public double fi=0;
	public double sinf=Math.sin(fi);
	public double cosf=Math.cos(fi);
	private String header;
	private JButton insertPoint;

	public static void main(String[] args) {

		AutocarEditor te = new AutocarEditor();

	}

	public AutocarEditor() {
		
		EditorData.initialize();

		WIDTH = 820;
		HEIGHT = 880;

		setTitle("Autocar editor");
		setSize(LEFT_BORDER+WIDTH + RIGHT_BORDER, HEIGHT);
		setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildMenuBar();

		autocarsData = new Vector();
		autolinesData=new Vector();
		
	
		
		left = new JPanel(null);
		left.setBounds(0, 0, LEFT_BORDER, HEIGHT);
		left.addMouseWheelListener(this);
		left.add(buildLeftAutocarsPanel());
		add(left);
		

		center = new JPanel(null);
		center.setBounds(LEFT_BORDER, 0, WIDTH, HEIGHT);
		center.setBackground(background_color);
		center.addMouseListener(this);
		center.addMouseMotionListener(this);
		center.setTransferHandler(new FileTransferhandler());
		center.addMouseWheelListener(this);
		center.addKeyListener(this);
		add(center);

		right = new JPanel(null);
		right.setBounds(LEFT_BORDER+WIDTH, 0, RIGHT_BORDER, HEIGHT);
		right.addMouseWheelListener(this);
		add(right);

		buildRightAutolinePanel();

		currentDirectory = new File("lib");
		addPropertyChangeListener(this);

		RepaintManager.setCurrentManager(new RepaintManager() {

			public void paintDirtyRegions() {

				super.paintDirtyRegions();
				firePropertyChange("paintDirtyRegions", false, true);
				/*
				 * if(redrawAfterMenu ) { draw(); redrawAfterMenu=false; }
				 */
			}

		});

		try {
			initialize();

		} catch (IOException e) {

			e.printStackTrace();
		}

		setVisible(true);
	}
	
	public void buildNewZBuffers() {


		for(int i=0;i<landscapeZbuffer.getSize();i++){

			landscapeZbuffer.rgbColor[i]=blackRgb;
            

		}
	

	}
	


	private void initialize() throws IOException {

		totalVisibleField = buildVisibileArea(0, HEIGHT);
		buf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		landscapeZbuffer=new ZBuffer(WIDTH*HEIGHT);
		rgb = new int[buf.getWidth() * buf.getHeight()];

		File directoryImg = new File("lib");
		File[] files = directoryImg.listFiles();

		Vector vRoadTextures = new Vector();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().startsWith("road_texture_")) {

				vRoadTextures.add(files[i]);

			}
		}

		worldTextures = new Texture[vRoadTextures.size()];



		for (int i = 0; i < vRoadTextures.size(); i++) {

			worldTextures[i] = new Texture(
					ImageIO.read(new File("lib/road_texture_" + i + ".jpg")));

		}
		
		Vector vObjects=new Vector();
		
		
		for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("object3D_")
				   && 	!files[i].getName().startsWith("object3D_texture")	
				){
					
					vObjects.add(files[i]);
					
				}		
		}
		
		
		objectImages=new BufferedImage[vObjects.size()];
		objectMeshes=new CubicMesh[vObjects.size()];
		objectIndexes=new Texture[vObjects.size()];

		
		for(int i=0;i<vObjects.size();i++){
			
			objectImages[i]=ImageIO.read(new File("lib/object_"+i+".gif"));
						
			
			objectMeshes[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i));
		
				
			BufferedImage boi=new BufferedImage(indexWidth,indexHeight,BufferedImage.TYPE_INT_RGB);
			boi.getGraphics().setColor(Color.white);
			boi.getGraphics().drawString(""+i,0,indexHeight);
			objectIndexes[i]=new Texture(boi);	
			
		}
		
		selectionColor=new Color(255,0,0,127);

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

	private void buildMenuBar() {

		jmb = new JMenuBar();
		JMenu jm = new JMenu("File");
		jm.addMenuListener(this);
		jmb.add(jm);

		jmt_load_autocar = new JMenuItem("Load data");
		jmt_load_autocar.addActionListener(this);
		jm.add(jmt_load_autocar);

		jmt_save_autocar = new JMenuItem("Save data");
		jmt_save_autocar.addActionListener(this);
		jm.add(jmt_save_autocar);

		jm.addSeparator();

		jmt_load_landscape = new JMenuItem("Load landscape");
		jmt_load_landscape.addActionListener(this);
		jm.add(jmt_load_landscape);

		JMenu jm2 = new JMenu("Do");
		jm2.addMenuListener(this);
		jmb.add(jm2);

		jmt_undo = new JMenuItem("Undo");
		jmt_undo.addActionListener(this);
		jmt_undo.setEnabled(false);
		jm2.add(jmt_undo);

		JMenu jm3 = new JMenu("Other");
		jm3.addMenuListener(this);
		jmb.add(jm3);		
		
		jmt_addautoline = new JMenuItem("Add autoline");
		jmt_addautoline.addActionListener(this);
		jm3.add(jmt_addautoline);

		jmt_deleteautoline = new JMenuItem("Delete this autoline");
		jmt_deleteautoline.addActionListener(this);
		jm3.add(jmt_deleteautoline);
		
		jm3.addSeparator();

		jmt_addautocar = new JMenuItem("Add autocar");
		jmt_addautocar.addActionListener(this);
		jm3.add(jmt_addautocar);

		jmt_deleteautocar = new JMenuItem("Delete this autocar");
		jmt_deleteautocar.addActionListener(this);
		jm3.add(jmt_deleteautocar);

		jm3.addSeparator();

		jmt_template = new JMenuItem("Template");
		jmt_template.addActionListener(this);
		jm3.add(jmt_template);

		jmt_advance_road = new JMenuItem("Manage road");
		jmt_advance_road.addActionListener(this);
		jm3.add(jmt_advance_road);

		jm3.addSeparator();

		jmt_refresh = new JMenuItem("Refresh");
		jmt_refresh.addActionListener(this);
		jm3.add(jmt_refresh);

		jmt_help = new JMenu("Help");
		jmt_help.addMenuListener(this);
		jmt_help_show = new JMenuItem("Show help");
		jmt_help_show.addActionListener(this);
		jmt_help.add(jmt_help_show);
		jmb.add(jmt_help);

		setJMenuBar(jmb);

	}

	private void buildRightAutolinePanel() {

		int r = 10;

		checkMultipleSelection = new JCheckBox("Multiple selection");
		checkMultipleSelection.setBounds(10, r, 150, 20);
		checkMultipleSelection.addKeyListener(this);
		right.add(checkMultipleSelection);

		r += 30;

		JLabel jlb = new JLabel("X:");
		jlb.setBounds(10, r, 30, 20);
		right.add(jlb);

		xcoordinate = new DoubleTextField();
		xcoordinate.setBounds(50, r, 100, 20);
		xcoordinate.addKeyListener(this);
		right.add(xcoordinate);

		r += 30;

		jlb = new JLabel("Y:");
		jlb.setBounds(10, r, 30, 20);
		right.add(jlb);

		ycoordinate = new DoubleTextField();
		ycoordinate.setBounds(50, r, 100, 20);
		ycoordinate.addKeyListener(this);
		right.add(ycoordinate);

		r += 30;

		jlb = new JLabel("Z:");
		jlb.setBounds(10, r, 30, 20);
		right.add(jlb);

		zcoordinate = new DoubleTextField();
		zcoordinate.setBounds(50, r, 100, 20);
		zcoordinate.addKeyListener(this);
		right.add(zcoordinate);

		r += 30;

		changePoint = new JButton("Change point");
		changePoint.setBounds(10, r, 150, 20);
		changePoint.addActionListener(this);
		changePoint.addKeyListener(this);
		right.add(changePoint);

		r += 30;
		
		insertPoint=new JButton("Insert point after");
		insertPoint.addActionListener(this);
		insertPoint.addKeyListener(this);
		insertPoint.setFocusable(false);
		insertPoint.setBounds(10,r,150,20);
		right.add(insertPoint);


		r+=30;

		selectAll = new JButton("Select all");
		selectAll.addActionListener(this);
		selectAll.addKeyListener(this);
		selectAll.setFocusable(false);
		selectAll.setBounds(10, r, 150, 20);
		right.add(selectAll);

		r += 30;

		deselectAll = new JButton("Deselect all");
		deselectAll.setBounds(10, r, 150, 20);
		deselectAll.addActionListener(this);
		deselectAll.addKeyListener(this);
		deselectAll.setFocusable(false);
		right.add(deselectAll);

		r += 30;

		deleteSelection = new JButton("Delete selected");
		deleteSelection.setBounds(10, r, 150, 20);
		deleteSelection.addActionListener(this);
		deleteSelection.addKeyListener(this);
		right.add(deleteSelection);

		r += 30;

		rescale = new JButton("Rescale selected");
		rescale.addKeyListener(this);
		rescale.addActionListener(this);
		rescale.setFocusable(false);
		rescale.setBounds(10, r, 150, 20);
		right.add(rescale);

		r += 30;

		rescaleValue = new DoubleTextField();
		rescaleValue.setBounds(50, r, 70, 20);
		rescaleValue.addKeyListener(this);
		right.add(rescaleValue);
		
		r += 30;
		
		right.add(buildPointsMovePanel(30, r));

		r += 120;
		
		jlb = new JLabel("Autoline");
		jlb.setBounds(10, r, 60, 20);
		right.add(jlb);
		
		r += 30;

		chooseAutolineMinus = new JButton("<");
		chooseAutolineMinus.setBounds(10, r, 40, 20);
		chooseAutolineMinus.setMargin(new Insets(0, 0, 0, 0));
		chooseAutolineMinus.addActionListener(

		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int indx = chooseAutoline.getSelectedIndex();
				if (indx > 0)
					chooseAutoline.setSelectedIndex(indx - 1);
			}
		}

		);
		chooseAutolineMinus.addKeyListener(this);
		right.add(chooseAutolineMinus);

		chooseAutoline = new JComboBox();
		chooseAutoline.setBounds(50, r, 60, 20);
		chooseAutoline.addKeyListener(this);
		setAutolineSelectionModel();
		chooseAutoline.addItemListener(

		new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {

				changeActiveAutoline();

			}

		}

		);
		right.add(chooseAutoline);

		chooseAutolinePlus = new JButton(">");
		chooseAutolinePlus.setBounds(110, r, 40, 20);
		chooseAutolinePlus.setMargin(new Insets(0, 0, 0, 0));
		chooseAutolinePlus.addActionListener(

		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int indx = chooseAutoline.getSelectedIndex();
				if (indx < chooseAutoline.getModel().getSize() - 1)
					chooseAutoline.setSelectedIndex(indx + 1);

			}
		}

		);
		chooseAutolinePlus.addKeyListener(this);
		right.add(chooseAutolinePlus);

		r += 30;
		
		addCarToAutoline = new JButton("Add car to point");
		addCarToAutoline.setBounds(10, r, 150, 20);
		addCarToAutoline.addActionListener(this);
		addCarToAutoline.addKeyListener(this);
		right.add(addCarToAutoline);
		
	
	}

	private JPanel buildLeftAutocarsPanel() {

		JPanel autocarPanel = new JPanel();
		autocarPanel.setBounds(0, 0, 200, 520);
		autocarPanel.setLayout(null);

		int l = 10;

		JLabel jlb = new JLabel("Autocar");
		jlb.setBounds(10, l, 60, 20);
		autocarPanel.add(jlb);
		
		l += 30;
		
		chooseAutocarMinus = new JButton("<");
		chooseAutocarMinus.setBounds(10, l, 40, 20);
		chooseAutocarMinus.setMargin(new Insets(0, 0, 0, 0));
		chooseAutocarMinus.addActionListener(

		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int indx = chooseAutocar.getSelectedIndex();
				if (indx > 0)
					chooseAutocar.setSelectedIndex(indx - 1);
			}
		}

		);
		chooseAutocarMinus.addKeyListener(this);
		autocarPanel.add(chooseAutocarMinus);
		
		chooseAutocar = new JComboBox();
		chooseAutocar.setBounds(50, l, 60, 20);
		chooseAutocar.addKeyListener(this);
		chooseAutocar.addItemListener(

		new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {

				//System.out.println("changeActiveCar->");
				changeActiveCar();

			}

		}

		);
		autocarPanel.add(chooseAutocar);
		
		chooseAutocarPlus = new JButton(">");
		chooseAutocarPlus.setBounds(110, l, 40, 20);
		chooseAutocarPlus.setMargin(new Insets(0, 0, 0, 0));
		chooseAutocarPlus.addActionListener(

		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int indx = chooseAutocar.getSelectedIndex();
				if (indx < chooseAutocar.getModel().getSize() - 1)
					chooseAutocar.setSelectedIndex(indx + 1);

			}
		}

		);
		chooseAutocarPlus.addKeyListener(this);
		autocarPanel.add(chooseAutocarPlus);
		
		setAutocarSelectionModel();
		
		l += 30;
		
		jlb = new JLabel("Type");
		jlb.setBounds(10, l, 40, 20);
		autocarPanel.add(jlb);

		car_type_index = new JComboBox();
		car_type_index.setBounds(50, l, 100, 20);
		car_type_index.setToolTipText("Autocar type");
		car_type_index.addKeyListener(this);
		
		
		car_type_index.addItemListener(

		new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {


			}

		}

		);
		autocarPanel.add(car_type_index);
		
		File directoryImg=new File("lib");
		File[] files=directoryImg.listFiles();	

		int count=0;
		
		for(int f=0;f<files.length;f++){

			if(files[f].getName().startsWith("cardefault3D_")){
				
				car_type_index.addItem(new ComboElement(""+count,""+count));
				count++;
			}		
		}	

		

		l += 30;

		jlb = new JLabel("X:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		centerX = new DoubleTextField();
		centerX.setBounds(50, l, 100, 20);
		centerX.setToolTipText("Center X");
		centerX.addFocusListener(this);
		centerX.addKeyListener(this);
		autocarPanel.add(centerX);

		l += 30;

		jlb = new JLabel("Y:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		centerY = new DoubleTextField();
		centerY.setBounds(50, l, 100, 20);
		centerY.setToolTipText("Center Y");
		centerY.addFocusListener(this);
		centerY.addKeyListener(this);
		autocarPanel.add(centerY);

		l += 30;

		
		jlb = new JLabel("Fi:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		fi_angle = new DoubleTextField();
		fi_angle.setBounds(50, l, 100, 20);
		fi_angle.setToolTipText("Orientation");
		fi_angle.addKeyListener(this);
		fi_angle.addFocusListener(this);
		autocarPanel.add(fi_angle);

		l += 30;

		jlb = new JLabel("St:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		steering_angle = new DoubleTextField();
		steering_angle.setBounds(50, l, 100, 20);
		steering_angle.setToolTipText("Steering angle");
		steering_angle.addKeyListener(this);
		autocarPanel.add(steering_angle);

		l += 30;

		jlb = new JLabel("U:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		longitudinal_velocity = new DoubleTextField();
		longitudinal_velocity.setBounds(50, l, 100, 20);
		longitudinal_velocity.setToolTipText("Longitudinal vel");
		longitudinal_velocity.addKeyListener(this);
		autocarPanel.add(longitudinal_velocity);

		l += 30;

		jlb = new JLabel("Nu:");
		jlb.setBounds(10, l, 30, 20);
		autocarPanel.add(jlb);

		lateral_velocity = new DoubleTextField();
		lateral_velocity.setBounds(50, l, 100, 20);
		lateral_velocity.setToolTipText("Lateral vel");
		lateral_velocity.addKeyListener(this);
		autocarPanel.add(lateral_velocity);
		

		l += 30;

		jlb = new JLabel("Parked:");
		jlb.setBounds(10, l, 80, 20);
		autocarPanel.add(jlb);

		is_parked = new JCheckBox();
		is_parked.setBounds(90, l, 100, 20);
		is_parked.setToolTipText("Is parked");
		is_parked.addKeyListener(this);
		
		is_parked.addItemListener(
				
				
				new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
					
						
						if(is_parked.isSelected())
							chooseAutocarline.setEnabled(false);
						else
							chooseAutocarline.setEnabled(true);
						
					}
				}
				
				
		);
		

		
		autocarPanel.add(is_parked);

		l += 30;

		jlb = new JLabel("Car line:");
		jlb.setBounds(10, l, 80, 20);
		autocarPanel.add(jlb);
		
		chooseAutocarline = new JComboBox();
		chooseAutocarline.setBounds(90, l, 60, 20);
		chooseAutocarline.addKeyListener(this);
		chooseAutocarline.addItemListener(

		new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {

			}

		}

		);
		autocarPanel.add(chooseAutocarline);
		
		l += 30;
		
		updateCar = new JButton("Change car");
		updateCar.setBounds(10, l, 150, 20);
		updateCar.addActionListener(this);
		updateCar.addKeyListener(this);
		autocarPanel.add(updateCar);
		
		l += 30;

		autocarPanel.add(buildAuotcarMovePanel(10,l));

		return autocarPanel;
	}

	private JPanel buildPointsMovePanel(int i, int r) {

		JPanel move = new JPanel();
		move.setBounds(i, r, 100, 100);
		move.setLayout(null);

		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);

		pointsMove = new DoubleTextField();
		pointsMove.setBounds(30, 40, 40, 20);
		move.add(pointsMove);
		pointsMove.addKeyListener(this);

		movePointsUp = new JButton(new ImageIcon("lib/trianglen.jpg"));
		movePointsUp.setBounds(40, 10, 20, 20);
		movePointsUp.addActionListener(this);
		movePointsUp.setFocusable(false);
		move.add(movePointsUp);

		movePointsDown = new JButton(new ImageIcon("lib/triangles.jpg"));
		movePointsDown.setBounds(40, 70, 20, 20);
		movePointsDown.addActionListener(this);
		movePointsDown.setFocusable(false);
		move.add(movePointsDown);

		movePointsLeft = new JButton(new ImageIcon("lib/triangleo.jpg"));
		movePointsLeft.setBounds(5, 40, 20, 20);
		movePointsLeft.addActionListener(this);
		movePointsLeft.setFocusable(false);
		move.add(movePointsLeft);

		movePointsRight = new JButton(new ImageIcon("lib/trianglee.jpg"));
		movePointsRight.setBounds(75, 40, 20, 20);
		movePointsRight.addActionListener(this);
		movePointsRight.setFocusable(false);
		move.add(movePointsRight);

		movePointsTop = new JButton(new ImageIcon("lib/up.jpg"));
		movePointsTop.setBounds(5, 70, 20, 20);
		movePointsTop.addActionListener(this);
		movePointsTop.setFocusable(false);
		move.add(movePointsTop);

		movePointsBottom = new JButton(new ImageIcon("lib/down.jpg"));
		movePointsBottom.setBounds(75, 70, 20, 20);
		movePointsBottom.addActionListener(this);
		movePointsBottom.setFocusable(false);
		move.add(movePointsBottom);

		return move;

	}
	
	
	private JPanel buildAuotcarMovePanel(int i, int r) {

		JPanel move = new JPanel();
		move.setBounds(i, r, 100, 100);
		move.setLayout(null);

		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);

		autocarsMove = new DoubleTextField();
		autocarsMove.setBounds(30, 40, 40, 20);
		move.add(autocarsMove);
		autocarsMove.addKeyListener(this);

		moveAutocarsUp = new JButton(new ImageIcon("lib/trianglen.jpg"));
		moveAutocarsUp.setBounds(40, 10, 20, 20);
		moveAutocarsUp.addActionListener(this);
		moveAutocarsUp.setFocusable(false);
		move.add(moveAutocarsUp);

		moveAutocarsDown = new JButton(new ImageIcon("lib/triangles.jpg"));
		moveAutocarsDown.setBounds(40, 70, 20, 20);
		moveAutocarsDown.addActionListener(this);
		moveAutocarsDown.setFocusable(false);
		move.add(moveAutocarsDown);

		moveAutocarsLeft = new JButton(new ImageIcon("lib/triangleo.jpg"));
		moveAutocarsLeft.setBounds(5, 40, 20, 20);
		moveAutocarsLeft.addActionListener(this);
		moveAutocarsLeft.setFocusable(false);
		move.add(moveAutocarsLeft);

		moveAutocarsRight = new JButton(new ImageIcon("lib/trianglee.jpg"));
		moveAutocarsRight.setBounds(75, 40, 20, 20);
		moveAutocarsRight.addActionListener(this);
		moveAutocarsRight.setFocusable(false);
		move.add(moveAutocarsRight);


		return move;

	}


	public void paint(Graphics g) {
		super.paint(g);
		draw();
	}

	private void draw() {

		if (graph == null)
			graph = (Graphics2D) center.getGraphics();

		drawRoad(meshes,drawObjects,splines,landscapeZbuffer);
		
		buildScreen(buf); 
        drawAutolines(buf);  
		

		graph.drawImage(buf,0,0,null);

		//graph.drawImage(buf, 0, 0, WIDTH, HEIGHT, null);

	}

	
	public int convertX(Point3D p){


		return convertX(p.x,p.y,p.z);
	}
	public int convertY(Point3D p){


		return convertY(p.x,p.y,p.z);
	}


	public int convertX(double x,double y, double z) {

		return (int) (x / dx - x0);
	}

	public int convertY(double x,double y, double z) {

		return (int) (HEIGHT - (y / dy - y0));
	}

	public int invertX(int x) {

		return (int) (dx * (x + x0));
	}

	public int invertY(int y) {

		return (int) (dy * (HEIGHT + y0 - y));
	}

	private void drawAutolines(BufferedImage buf) {
		

		Graphics2D graph2 = (Graphics2D) buf.getGraphics();


		// draw all lines

		for (int l = 0; l < autolinesData.size(); l++) {
			LinkedList linkedList = (LinkedList) autolinesData.elementAt(l);

			int size = linkedList.size();

			for (int i = 0; i < size; i++) {

				Point3D point1 = (Point3D) linkedList.get(i);

				Point3D point2 = (Point3D) linkedList.get((i + 1) % size);

				if (l != chooseAutoline.getSelectedIndex())
					graph2.setColor(Color.BLUE);
				else if (i == size - 1)
					graph2.setColor(Color.MAGENTA);
				else
					graph2.setColor(Color.WHITE);

				graph2.drawLine(convertX(point1), convertY(point1),
						convertX(point2), convertY(point2));
			}

		}

		if(autolinesData.size()>0){
			
			LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());
		
			int size = linkedList.size();
	
			// draw points
			for (int i = 0; i < size; i++) {
	
				Point3D point = (Point3D) linkedList.get(i);
	
				if (point.isSelected())
					graph2.setColor(Color.RED);
				else
					graph2.setColor(Color.WHITE);
	
				graph2.fillOval(convertX(point) - 2, (int) convertY(point) - 2,
						5, 5);
			}
	
	
		
		}
		
		// draw initial position and direction
		
		for (int i = 0; i < autocarsData.size(); i++) {
			AutocarData ad = (AutocarData) autocarsData.elementAt(i);
	
			if(i==chooseAutocar.getSelectedIndex())
				graph2.setColor(Color.BLUE);
			else
				graph2.setColor(Color.CYAN);

			
			double x0 = ad.x;
			double y0 = ad.y;

			double orientation=ad.fi;
			
			double x1 =x0+20*Math.cos(orientation);
			double y1 =y0+20*Math.sin(orientation);
			
			graph2.drawLine(convertX(x0,y0,0),convertY(x0,y0,0),convertX(x1,y1,0),convertY(x1,y1,0));
			
			graph2.fillOval(convertX(x0,y0,0) - 2, convertY(x0,y0,0) - 2, 5, 5);
		}
		
		/*if (autocarData!=null ) {

			graph2.setColor(Color.CYAN);
			double x0 = centerX.getvalue();
			double y0 = centerY.getvalue();

			double orientation=fi_angle.getvalue();
			
			double x1 =x0+10*Math.cos(orientation);
			double y1 =y0+10*Math.sin(orientation);
			
			graph2.drawLine(convertX(x0),convertY(y0),convertX(x1),convertY(y1));
			
			graph2.fillOval(convertX(x0) - 2, convertY(y0) - 2, 5, 5);
			
			
		}*/
		
		displayCurrentRect(graph2);

	}


	private void drawRoad(PolygonMesh[] meshes, Vector drawObjects2,
			Vector splines, ZBuffer landscapeZbuffer2) {
		
		
		displayTerrain(landscapeZbuffer,meshes);
		displaySPLines(landscapeZbuffer,splines);
		displayObjects(landscapeZbuffer,drawObjects);
	
	}
	
	private void displayObjects(ZBuffer landscapeZbuffer2, Vector drawObjects) {
		
		if(drawObjects==null)
			return;

		Rectangle totalVisibleField=new Rectangle(0,0,WIDTH,HEIGHT);

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);

			int y=convertY(dro.x,dro.y,dro.z);
			int x=convertX(dro.x,dro.y,dro.z);

			int index=dro.getIndex();

			int dw=(int) (dro.dx/dx);
			int dh=(int) (dro.dy/dy);

			
			if(!totalVisibleField.intersects(new Rectangle(x,y-dh,dw,dh))){
				
				//System.out.println(totalVisibleField+" "+new Rectangle(x,y,dw,dh));
				
				continue;
			}
			
			
		
			
			drawObject(landscapeZbuffer,dro);

		}	
	}

	private void displaySPLines(ZBuffer landscapeZbuffer2, Vector splines) {
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
			
			Vector meshes = sp.getMeshes();
			
			for (int j = 0; j < meshes.size(); j++) {//if(j!=4)continue;
				
				PolygonMesh mesh = (PolygonMesh) meshes.elementAt(j);
				            
				drawSPLinePolygon(mesh,landscapeZbuffer,1);
                 
				
			}



			
		}
		
		
	}

	private void displayTerrain(ZBuffer landscapeZbuffer, PolygonMesh[] meshes) {

	
		PolygonMesh mesh=meshes[RoadEditor.TERRAIN_INDEX];
		
		if(mesh.points==null)
			return;
		
		int lsize=mesh.polygonData.size();
		

		for(int j=0;j<lsize;j++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(j);

	
			Texture texture = EditorData.worldTextures[ld.getTexture_index()];
			drawPolygon(ld,mesh.points,landscapeZbuffer,texture,RoadEditor.TERRAIN_INDEX);

		} 

		//mark row angles
		
		int size=mesh.points.length;
		for(int j=0;j<size;j++){


		    Point4D p=(Point4D) mesh.points[j];

				int xo=convertX(p.x,p.y,p.z);
				int yo=convertY(p.x,p.y,p.z);
				
				int rgbColor=Color.white.getRGB();

				if(p.isSelected()){
					rgbColor=Color.RED.getRGB();

				}	

			}
		
		
		/*drawCurrentRect(landscapeZbuffer);*/
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
	

	private void drawObject(ZBuffer landscapeZbuffer, DrawObject dro) {

		int[] cx=new int[4];
		int[] cy=new int[4];
		
		int versus=1;
		if(!DrawObject.IS_3D)
			versus=-1;

		cx[0]=convertX(dro.x,dro.y,dro.z);
		cy[0]=convertY(dro.x,dro.y,dro.z);
		cx[1]=convertX(dro.x,dro.y,dro.z);
		cy[1]=convertY(dro.x,dro.y+versus*dro.dy,dro.z);
		cx[2]=convertX(dro.x+dro.dx,dro.y,dro.z);
		cy[2]=convertY(dro.x,dro.y+versus*dro.dy,dro.z);
		cx[3]=convertX(dro.x+dro.dx,dro.y,dro.z);
		cy[3]=convertY(dro.x,dro.y,dro.z);

		Polygon p_in=new Polygon(cx,cy,4);
		
		Point3D center=Polygon3D.findCentroid(p_in);
		Polygon3D.rotate(p_in,center,dro.rotation_angle);
	

		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty()){
			
			return;
		}	

		Polygon pTot=Polygon3D.fromAreaToPolygon2D(partialArea);
		//if(cy[0]<0 || cy[0]>HEIGHT || cx[0]<0 || cx[0]>WIDTH) return;
		
		
		Color pColor=ZBuffer.fromHexToColor(dro.getHexColor());
		
		
		if(dro.isSelected())
		{
			pColor=Color.RED;
			
		}
		int rgbColor = pColor.getRGB();
		drawObjectPolygon(landscapeZbuffer,pTot,rgbColor);

	
		

	}
	
	private void drawObjectPolygon(ZBuffer landscapeZbuffer, Polygon pTot, int rgbColor) {
		
		
		
		for (int n = 0; n < pTot.npoints; n++) {
			
			int i=pTot.xpoints[n];
			int j=pTot.ypoints[n];
			
			int ii=pTot.xpoints[(n+1)%pTot.npoints];
			int jj=pTot.ypoints[(n+1)%pTot.npoints];
			
			drawLine(landscapeZbuffer,i,j,ii,jj,rgbColor);
			
			
		
		}
		
	}
	
	private void drawSPLinePolygon(PolygonMesh mesh, ZBuffer landscapeZbuffer,
			int rgbColor) {

		
		int lsize=mesh.polygonData.size();
		

		for(int k=0;k<lsize;k++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(k);
			Texture texture = EditorData.splinesEditorTextures[ld.getTexture_index()];
			
			drawPolygon(ld,mesh.points,landscapeZbuffer,texture,0);

		} 
		
	}
	
	private void drawPolygon(LineData ld,Point3D[] points,ZBuffer landscapeZbuffer,Texture texture,int indx) {


		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points[num];

			//bufGraphics.setColor(ZBuffer.fromHexToColor(is[i].getHexColor()));

			cx[i]=convertX(p);
			cy[i]=convertY(p);
			cz[i]=(int)p.z;



		}

		Polygon p_in=new Polygon(cx,cy,ld.size());
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty())
			return;

		Polygon3D p3d=new Polygon3D(size,cx,cy,cz);
		Polygon3D p3dr=PolygonMesh.getBodyPolygon(points,ld);

		//calculate texture angle

		
		Color selected=null;

		if(ld.isSelected()){

			if(indx<0 || indx==ACTIVE_PANEL){
				selected=selectionColor;
			}
		}
		



		drawTexture(texture,p3d,p3dr,landscapeZbuffer,selected);



	}
	
	private void drawTexture(Texture texture,  Polygon3D p3d, Polygon3D p3dr,ZBuffer landscapeZbuffer, Color selected) {

		Point3D normal=Polygon3D.findNormal(p3dr);



		if(texture!=null){
			
			Rectangle rect = p3d.getBounds();
			
			
			
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

	
			
			Polygon3D[] triangles=Polygon3D.divideIntoTriangles(p3dr);
			
			for (int i = 0; i < triangles.length; i++) {
				
				Polygon3D tria=triangles[i];
				
				BarycentricCoordinates bc=new BarycentricCoordinates(triangles[i]);
				
					
				int rgb=-1;
				
				if(selected!=null)
					rgb=selected.getRGB();
				
				decomposeTriangleIntoZBufferEdgeWalking(tria,rgb,texture,landscapeZbuffer, xDirection, yDirection, p0r, 0, 0,bc); 

				
			}


		}

	}
	
	private void drawLine(ZBuffer landscapeZbuffer, int i, int j, int ii,
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

				if(k>=0 && k<WIDTH && j>=0 && j<HEIGHT){

					int tot=k+j*WIDTH;
					landscapeZbuffer.setRgbColor(rgbColor,tot);
				}
			}
		}
		else if(i==ii){

			for (int k = minj; k < maxj; k++) {
				if(i>=0 && i<WIDTH && k>=0 && k<HEIGHT){

					int tot=i+k*WIDTH;

					landscapeZbuffer.setRgbColor(rgbColor,tot);
				}
			}

		}
		else{

			//use coordinate with more points:
			if(Math.abs(ii-i)>Math.abs(jj-j)){

				double dy=(jj-j)*1.0/(ii-i);

				if(ii>i){
					for (int k = i; k < ii; k++) {

						int y=(int) (dy*(k-i)+j);

						if(k>=0 && k<WIDTH && y>=0 && y<HEIGHT){
							int tot=(k+y*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}	
				}
				else{
					for (int k = ii; k < i; k++) {

						int y=(int) (dy*(k-i)+j);

						if(k>=0 && k<WIDTH && y>=0 && y<HEIGHT){
							int tot=(k+y*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}			

				}


			}else{
				
				double dx=(ii-i)*1.0/(jj-j);

				if(jj>j){
					for (int q = j; q < jj; q++) {

						int x=(int) (dx*(q-j)+i);

						if(x>=0 && x<WIDTH && q>=0 && q<HEIGHT){
							int tot=(x+q*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}	
				}
				else{
					for (int q = jj; q < j; q++) {

						int x=(int) (dx*(q-j)+i);

						if(x>=0 && x<WIDTH && q>=0 && q<HEIGHT){
							int tot=(x+q*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}			

				}

				
			}

		}

	}
	
	/**
	 * 
	 * DECOMPOSE PROJECTED TRIANGLE USING EDGE WALKING AND
	 * PERSPECTIVE CORRECT MAPPING
	 * 
	 * @param p3d
	 * @param color
	 * @param texture
	 * @param useLowResolution
	 * @param xDirection
	 * @param yDirection
	 * @param origin 
	 */
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int selected,Texture texture,ZBuffer zb,  
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc) {

		int rgbColor=selected;
		
		rr=a2*(selected>>16 & mask);
		gg=a2*(selected>>8 & mask);
		bb=a2*(selected & mask);

		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
		
		//boolean isFacing=isFacing(p3d,normal,observerPoint);


	
		double cosin=calculateCosin(p3d);
		
		
		Point3D po0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D po1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D po2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);
	
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);

		p0.rotate(x0,y0,cosf,sinf);
		p1.rotate(x0,y0,cosf,sinf);
		p2.rotate(x0,y0,cosf,sinf);

		double x0=(int)convertX(p0.x,p0.y,p0.z);
		double y0=(int)convertY(p0.x,p0.y,p0.z);
		double z0=p0.z;

		double x1=(int)convertX(p1.x,p1.y,p1.z);
		double y1=(int)convertY(p1.x,p1.y,p1.z);
		double z1=p1.z;

		
		double x2=(int)convertX(p2.x,p2.y,p2.z);
		double y2=(int)convertY(p2.x,p2.y,p2.z);
		double z2=p2.z;
        //System.out.println(x0+" "+y0+", "+x1+" "+y1+", "+x2+" "+y2);
		
		//check if triangle is visible
		double maxX=Math.max(x0,x1);
		maxX=Math.max(x2,maxX);
		double maxY=Math.max(y0,y1);
		maxY=Math.max(y2,maxY);
		double minX=Math.min(x0,x1);
		minX=Math.min(x2,minX);
		double minY=Math.min(y0,y1);
		minY=Math.min(y2,minY);
		
		if(maxX<0 || minX>WIDTH || maxY<0 || minY>HEIGHT)
			return;

		Point3D[] points=new Point3D[3];

		points[0]=new Point3D(x0,y0,z0,p0.x,p0.y,p0.z);
		points[1]=new Point3D(x1,y1,z1,p1.x,p1.y,p1.z);
		points[2]=new Point3D(x2,y2,z2,p2.x,p2.y,p2.z);

		
		if(texture!=null){
			
			int w=texture.getWidth();
			int h=texture.getHeight();
			
			Point3D pt0=bc.pt0;
			Point3D pt1=bc.pt1;
			Point3D pt2=bc.pt2;
			
			Point3D p=bc.getBarycentricCoordinates(new Point3D(po0.x,po0.y,po0.z));
			double x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			double y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[0].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(po1.x,po1.y,po1.z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);	
			points[1].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(po2.x,po2.y,po2.z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[2].setTexurePositions(x,texture.getHeight()-y);
				
		}
		
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
		
		Point3D lowP=points[lower];
		Point3D upP=points[upper];
		Point3D midP=points[middle];
		
		int j0=midP.y>0?(int)midP.y:0;
		int j1=upP.y<HEIGHT?(int)upP.y:HEIGHT;

		for(int j=j0;j<j1;j++){

			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(upP,midP,j);
			Point3D intersecte = foundPX_PY_PZ_TEXTURE_Intersection(upP,midP,j);
	
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,intersecte.p_z,intersecte.p_x,intersecte.p_y,intersecte.p_z,intersecte.texture_x,intersecte.texture_y);
			
			
			//pstart.p_y=pstart.p_x*projectionNormal.x+pstart.p_y*projectionNormal.y+pstart.p_z*projectionNormal.z;
			//pend.p_y=pend.p_x*projectionNormal.x+pend.p_y*projectionNormal.y+pend.p_z*projectionNormal.z;

			if(pstart.x>pend.x){

				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;



			double inverse=1.0/(end-start);


			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){

				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i;

				double l=(i-start)*inverse;

				double yi=((1-l)*pstart.p_y+l*pend.p_y);
				double zi=((1-l)*pstart.p_z+l*pend.p_z);
				double xi=((1-l)*pstart.p_x+l*pend.p_x);  
		
				if(!zb.isToUpdate(-zi,tot)){
				
					continue;
				}	

			
				
                double texture_x=(1-l)*pstart.texture_x+l*pend.texture_x;
                double texture_y=(1-l)*pstart.texture_y+l*pend.texture_y;

			

				if(texture!=null)
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);  
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY);
				if(rgbColor==greenRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);    	
				
				zb.set(xi,yi,zi,-zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),false,tot);
				
				
			}


		}
		//LOWER TRIANGLE
		j0=lowP.y>0?(int)lowP.y:0;
		j1=midP.y<HEIGHT?(int)midP.y:HEIGHT;
		
		for(int j=j0;j<j1;j++){
		
			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(lowP,midP,j);
			
			Point3D insersecte = foundPX_PY_PZ_TEXTURE_Intersection(lowP,midP,j);
			
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,insersecte.p_z,insersecte.p_x,insersecte.p_y,insersecte.p_z,insersecte.texture_x,insersecte.texture_y);


			//pstart.p_y=pstart.p_x*projectionNormal.x+pstart.p_y*projectionNormal.y+pstart.p_z*projectionNormal.z;
			//pend.p_y=pend.p_x*projectionNormal.x+pend.p_y*projectionNormal.y+pend.p_z*projectionNormal.z;

			
			if(pstart.x>pend.x){


				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;

			double inverse=1.0/(end-start);

			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){
				
				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i; 

				double l=(i-start)*inverse;

				double yi=((1-l)*pstart.p_y+l*pend.p_y);

				double zi=((1-l)*pstart.p_z+l*pend.p_z);
				double xi=((1-l)*pstart.p_x+l*pend.p_x);  
				
				
				if(!zb.isToUpdate(-zi,tot) ){
					
					continue;
				}	



                double texture_x=(1-l)*pstart.texture_x+l*pend.texture_x;
                double texture_y=(1-l)*pstart.texture_y+l*pend.texture_y;


				if(texture!=null)
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY);
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);   
				if(rgbColor==greenRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);


			
				zb.set(xi,yi,zi,-zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),false,tot);
				
			}


		}	




	}
	
	
	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}

	


	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(autolinesData.size()==0)
			return;
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int x = e.getX();
		int y = e.getY();

		if (e.getButton() == MouseEvent.BUTTON3) {

			Point3D point = new Point3D(invertX(x), invertY(y), 0);

			linkedList.push(point);

			setPointCoordinates(point);

			draw();
			return;
		}

		if (!checkMultipleSelection.isSelected())
			deselectAllPoints();

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			Rectangle rect = new Rectangle(convertX(point.x-2, point.y,point.z),
					convertY(point.x, point.y-2, point.z), 5, 5);

			if (rect.contains(x, y)) {
				point.setSelected(true);

				setPointCoordinates(point);
			

				break;
			} else if (!checkMultipleSelection.isSelected()) {

				point.setSelected(false);

			}

		}

		draw();
	}

	private void setPointCoordinates(Point3D point) {
	
		xcoordinate.setText(point.x);
		ycoordinate.setText(point.y);
		zcoordinate.setText(point.z);
		
	}

	public void selectPointsWithRectangle() {
		
		
		if(autolinesData.size()==0)
			return;
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int x0 = Math.min(currentRect.x, currentRect.x + currentRect.width);
		int x1 = Math.max(currentRect.x, currentRect.x + currentRect.width);
		int y0 = Math.min(currentRect.y, currentRect.y + currentRect.height);
		int y1 = Math.max(currentRect.y, currentRect.y + currentRect.height);

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D p = (Point3D) linkedList.get(i);

			int x = convertX(p.x,p.y,p.z);
			int y = convertY(p.x,p.y,p.z);

			if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {

				p.setSelected(true);

			}

		}

	}

	private void deselectAllPoints() {
		
		if(autolinesData.size()==0)
			return;
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			Rectangle rect = new Rectangle();

			point.setSelected(false);

		}

	}

	public void selectAllPoints() {
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D p = (Point3D) linkedList.get(i);
			p.setSelected(true);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		int xp = arg0.getX();
		int yp = arg0.getY();
		currentRect = new Rectangle(xp, yp, 0, 0);

	}

	public void displayCurrentRect(Graphics2D bufGraphics) {

		if (!isDrawCurrentRect)
			return;

		int x0 = Math.min(currentRect.x, currentRect.x + currentRect.width);
		int x1 = Math.max(currentRect.x, currentRect.x + currentRect.width);
		int y0 = Math.min(currentRect.y, currentRect.y + currentRect.height);
		int y1 = Math.max(currentRect.y, currentRect.y + currentRect.height);

		bufGraphics.setColor(Color.WHITE);
		bufGraphics.drawRect(x0, y0, x1 - x0, y1 - y0);

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		updateSize(arg0);
		selectPointsWithRectangle();
		isDrawCurrentRect = false;
		draw();

	}

	public void prepareUndo() {

		jmt_undo.setEnabled(true);

		Vector clonedAutolinesData=new Vector();
		Vector clonedAutocarsData=new Vector();

		for (int l= 0; l < autolinesData.size(); l++) {


			LinkedList linkedList=(LinkedList) autolinesData.elementAt(l);


			LinkedList newLinkedList=new LinkedList();
			
			int size = linkedList.size();

			for (int i = 0; i < size; i++) {

				Point3D point = (Point3D) linkedList.get(i);

				newLinkedList.push(point.clone());

			}
			clonedAutolinesData.add(newLinkedList);
		}

		for (int l= 0; l < autocarsData.size(); l++) {

			AutocarData autocarData=(AutocarData) autocarsData.elementAt(l);

			clonedAutocarsData.add(autocarData.clone());

		}
		
		if(oldAutocarsData.size()==MAX_STACK_SIZE)
			oldAutocarsData.removeElementAt(0);
		oldAutocarsData.push(clonedAutocarsData);
		
		if(oldAutolinesData.size()==MAX_STACK_SIZE)
			oldAutolinesData.removeElementAt(0);
		oldAutolinesData.push(clonedAutolinesData);
	}

	public void undo() {
		
	    if(oldAutocarsData.size()>0)  
	    	autocarsData=(Vector) oldAutocarsData.pop();
	    else
	    	autocarsData=new Vector();
	    
	    if(autolinesData.size()>0)  
	    	autolinesData=(Vector) oldAutolinesData.pop();
	    else
	    	autolinesData=new Vector(); 
        
        if(autocarsData.size()>0)
             chooseAutocar.setSelectedIndex(0);
        if(autolinesData.size()>0)
        	chooseAutoline.setSelectedIndex(0);
        
        setSelectionModels();
		
		draw();

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object obj = arg0.getSource();

		if (obj == deleteSelection)
			deleteSelectedPoints();
		else if (obj == jmt_load_autocar)
			loadAutocars();
		else if (obj == jmt_save_autocar)
			saveAutocars();
		else if (obj == jmt_undo)
			undo();
		else if (obj == jmt_template)
			buildTemplate();
		else if (obj == jmt_load_landscape)
			loadLandScape();
		else if (obj == jmt_refresh)
			draw();
		else if (obj == jmt_advance_road)
			manageRoad();
		else if (obj == jmt_addautoline) {
			addAutoline(); 
		} else if (obj == jmt_deleteautoline) {
			deleteActiveAutoline();
		} 
		else if (obj == jmt_addautocar) {
			addAutocar(); 
		} else if (obj == jmt_deleteautocar) {
			deleteActiveAutocar();
		}
		else if (obj == jmt_help_show)
			help();		
		else if (obj == changePoint)
			changeSelectedPoint();
		else if(obj==insertPoint){
			insertPointAfter();
		}
		else if (obj == deselectAll) {
			deselectAllPoints();
			draw();
		} else if (obj == selectAll) {
			selectAllPoints();
			draw();
		} else if (obj == movePointsBottom) {

			moveSelectedPoints(0, 0, -1);
			draw();
		} else if (obj == movePointsTop) {

			moveSelectedPoints(0, 0, 1);
			draw();
		} else if (obj == movePointsLeft) {

			moveSelectedPoints(-1, 0, 0);
			draw();
		} else if (obj == movePointsRight) {

			moveSelectedPoints(1, 0, 0);
			draw();
		} else if (obj == movePointsUp) {

			moveSelectedPoints(0, 1, 0);
			draw();
		} else if (obj == movePointsDown) {

			moveSelectedPoints(0, -1, 0);
			draw();			
		
		} else if (obj == addCarToAutoline) {

			addAutocarToAutoline();
			draw();			
		
		} 
		else if (obj == moveAutocarsLeft) {
	
			moveSelectedAutocars(-1, 0, 0);
			draw();
			
		} else if (obj == moveAutocarsRight) {
	
			moveSelectedAutocars(1, 0, 0);
			draw();
		} else if (obj == moveAutocarsUp) {
	
			moveSelectedAutocars(0, 1, 0);
			draw();
		} else if (obj == moveAutocarsDown) {
	
			moveSelectedAutocars(0, -1, 0);
			draw();
		} 
		
		else if (obj == rescale) {

			rescaleAllPoints();
			draw();
		}else if (obj == updateCar) {
			
			updateCarData();
			draw();
		}

	}



	private void loadLandScape() {

		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Load road");
		if (currentDirectory != null)
			fc.setCurrentDirectory(currentDirectory);

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			loadLanscape(file);
			draw();
			//currentDirectory = new File(file.getParent());
		}

	}

	private void loadLanscape(File file) { 

		setACTIVE_PANEL(0);
		loadPointsFromFile(file);	
		setACTIVE_PANEL(1);
		//loadPointsFromFile(file);
		loadSPLinesFromFile(file);
		setACTIVE_PANEL(0);
		
		loadObjectsFromFile(file); 

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
				
				DrawObject dro=RoadEditor.buildDrawObject(str,objectMeshes);
				drawObjects.add(dro);

			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void manageRoad() {
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		AdvanceRoadManagement arm = new AdvanceRoadManagement(linkedList);

		if (arm.getList() != null) {

			linkedList = arm.getList();
			repaint();
		}

	}

	private void buildTemplate() {
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		AutocarTemplatePanel atp = new AutocarTemplatePanel();

		if (atp.getTemplate() != null && atp.getTemplate().size() > 0) {

			linkedList = atp.getTemplate();
			draw();

		}

	}

	private void saveAutocars() {

		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save Track");
		if (currentDirectory != null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			saveAutocars(file);
			currentDirectory = new File(file.getParent());
		}

	}

	private void saveAutocars(File file) {

		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			
			

			for (int l = 0; l < autolinesData.size(); l++) {
				
				LinkedList linkedList = (LinkedList) autolinesData.elementAt(l);
			
				int size = linkedList.size();
	
				pw.print("ROAD_" + l + "=");
	
				for (int i = 0; i < size; i++) {
	
					Point3D point = (Point3D) linkedList.get(i);
	
					if (i > 0)
						pw.print("_");
	
					pw.print(decomposePoint(point));
	
				}
				
				pw.println();
			
			}

			for (int l = 0; l < autocarsData.size(); l++) {
				AutocarData aData = (AutocarData) autocarsData.elementAt(l);

				double x = aData.x;
				double y = aData.y;

				double fi = aData.fi;
				double steering = aData.steering;

				int car_type=aData.car_type;

				// longitudinal velocity
				double u = aData.u;
				// lateral velocity
				double nu = aData.nu;

				pw.print("DATA_" + l + "=");
				pw.println(car_type);

				pw.print("INIT_" + l + "=");
				pw.print(x + "," + y + "," + u + "," + nu + "," + fi + ","
						+ steering+","+aData.autoline_index);

	

				pw.println();

			}

			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAutocarData(AutocarData ad) {

		
		car_type_index.setSelectedIndex(ad.car_type);
		
		centerX.setText(ad.x);
		centerY.setText(ad.y);
		longitudinal_velocity.setText(ad.u);
		lateral_velocity.setText(ad.nu);
		fi_angle.setText(ad.fi);
		steering_angle.setText(ad.steering);
		
		if(ad.autoline_index==ad.AUTOLINE_PARKED){
			is_parked.setSelected(true);
		}	
		else{
			
			is_parked.setSelected(false);
			if(chooseAutocarline.getModel().getSize()>0)
				chooseAutocarline.setSelectedIndex(ad.autoline_index);
		}
		
		

		repaint();
	}

	private void addAutoline() {

		
	

		autolinesData.add(new LinkedList());		
		chooseAutoline.setSelectedIndex(chooseAutoline.getModel().getSize() - 1);
		setAutolineSelectionModel();
	}
	
	private void addAutocar() {
		
		AutocarBuildPanel abp=new AutocarBuildPanel(0,0,0);
		
		if(abp.getAutocar()!=null){
		
				
		
			AutocarData autocarData=abp.getAutocar();
			
			autocarsData.add(autocarData);
			
			setAutocarSelectionModel();
			
			chooseAutocar.setSelectedIndex(chooseAutocar.getModel().getSize() - 1);
			
			//autocarData=(AutocarData) autocarsData.elementAt(chooseAutocar.getSelectedIndex());
			
			setAutocarData(autocarData);

			draw();

			
		}
	}
	

	private void addAutocarToAutoline() {
		
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			if (point.isSelected()) {

				try {
						
					
					AutocarBuildPanel abp=new AutocarBuildPanel(point.x,point.y,1.0);
					
					if(abp.getAutocar()!=null){
					
							
					
						AutocarData autocarData=abp.getAutocar();
						autocarData.autoline_index=chooseAutoline.getSelectedIndex();
												
						autocarsData.add(autocarData);
						
						setAutocarSelectionModel();
						
						chooseAutocar.setSelectedIndex(chooseAutocar.getModel().getSize() - 1);
						
						//autocarData=(AutocarData) autocarsData.elementAt(chooseAutocar.getSelectedIndex());
						
						setAutocarData(autocarData);

						draw();

						
					}

				} catch (Exception e) {

				}
				break;
			}
		}
		
	}

	private void deleteActiveAutoline() {

		if (autolinesData.size() > 1) {
			
			prepareUndo();
			
			int sel = chooseAutoline.getSelectedIndex();
			autolinesData.removeElementAt(sel);
			
			setAutolineSelectionModel();
			

		}
	}
	

	private void deleteActiveAutocar() {
		
		if (autocarsData.size() > 0) {
			
			prepareUndo();

			int sel = chooseAutocar.getSelectedIndex();
			autocarsData.removeElementAt(sel);

			setAutocarSelectionModel();
			
			AutocarData autocarData = (AutocarData) autocarsData.firstElement();
			setAutocarData(autocarData);
			
		}
		
	}

	private void changeActiveCar() {


		//System.out.println("changeActiveCar.getSelectedIndex="+chooseAutocar.getSelectedIndex());
		
		AutocarData autocarData =(AutocarData) autocarsData.get(chooseAutocar.getSelectedIndex());

		setAutocarData(autocarData);

	}

	private void changeActiveAutoline() {
		
		draw();
	}
	

	private void updateCarData() {

		if(autocarsData.size()==0)
			return;
		
		AutocarData autocarData=(AutocarData) autocarsData.elementAt(chooseAutocar.getSelectedIndex());

		autocarData.x = centerX.getvalue();
		autocarData.y = centerY.getvalue();

		autocarData.fi = fi_angle.getvalue();
		autocarData.steering = steering_angle.getvalue();

		// longitudinal velocity
		autocarData.u = longitudinal_velocity.getvalue();
		// lateral velocity
		autocarData.nu = lateral_velocity.getvalue();
		
		if(car_type_index.getSelectedIndex()>=0)
			autocarData.car_type= car_type_index.getSelectedIndex();
		
		if(is_parked.isSelected())
			autocarData.autoline_index=AutocarData.AUTOLINE_PARKED;
		else
			autocarData.autoline_index=chooseAutocarline.getSelectedIndex();

	}
	
	private void setSelectionModels() { 
		
		setAutolineSelectionModel() ;
		setAutocarSelectionModel();
	}

	private void setAutolineSelectionModel() { 

		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

		for (int i = 0; i < autolinesData.size(); i++) {

			LinkedList ll = (LinkedList) autolinesData.elementAt(i);

			dcbm.addElement(new ComboItem("" + i, "" + i));

		}
		chooseAutoline.setModel(dcbm);
		
		
		DefaultComboBoxModel dcba = new DefaultComboBoxModel();

		for (int i = 0; i < autolinesData.size(); i++) {

			LinkedList ll = (LinkedList) autolinesData.elementAt(i);

			dcba.addElement(new ComboItem("" + i, "" + i));

		}
		
		chooseAutocarline.setModel(dcba);
	}
	
	private void setAutocarSelectionModel() { 


		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

		for (int i = 0; i < autocarsData.size(); i++) {

			AutocarData autocar = (AutocarData) autocarsData.elementAt(i);

			dcbm.addElement(new ComboItem("" + i, "" + i));

		}
		chooseAutocar.setModel(dcbm);

	}

	public String decomposePoint(Point3D point) {

		String pstr = point.x + "," + point.y + "," + point.z;
		return pstr;
	}

	private void loadAutocars() {

		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Load autocar");
		if (currentDirectory != null)
			fc.setCurrentDirectory(currentDirectory);

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			loadAutocars(file);
			draw();
			currentDirectory = new File(file.getParent());
		}

	}

	private void loadAutocars(File file) {

		autocarsData = new Vector();
		autolinesData=new Vector();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			String str = null;

			while ((str = br.readLine()) != null) {

				if (str.indexOf("DATA") >= 0) {

					AutocarData aData = new AutocarData();
					autocarsData.add(aData);

					str = str.substring(str.indexOf("=") + 1);

					String[] vals = str.split(",");

					aData.car_type = Integer.parseInt(vals[0]);
			
				} else if (str.indexOf("INIT") >= 0) {

					AutocarData aData = (AutocarData) autocarsData
							.lastElement();

					str = str.substring(str.indexOf("=") + 1);
					String[] vals = str.split(",");

					aData.x = Double.parseDouble(vals[0]);
					aData.y = Double.parseDouble(vals[1]);
					aData.u = Double.parseDouble(vals[2]);
					aData.nu = Double.parseDouble(vals[3]);
					aData.fi = Double.parseDouble(vals[4]);
					aData.steering = Double.parseDouble(vals[5]);
					aData.autoline_index=Integer.parseInt(vals[6]);

				} else if (str.indexOf("ROAD") >= 0) {

										
					LinkedList ll=new LinkedList();

					str = str.substring(str.indexOf("=") + 1);

					StringTokenizer stk = new StringTokenizer(str, "_");

					while (stk.hasMoreTokens()) {

						Point3D p = composePoint(stk.nextToken());
						ll.add(p);

					}
					
					autolinesData.add(ll);

				}

			}

			br.close();

			setSelectionModels();
			
			AutocarData autocarData=(AutocarData) autocarsData.elementAt(chooseAutocar.getSelectedIndex());
			setAutocarData(autocarData);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static Point3D composePoint(String str) {

		String[] data = str.split(",");

		Point3D p = new Point3D();
		p.x = Double.parseDouble(data[0]);
		p.y = Double.parseDouble(data[1]);
		p.z = Double.parseDouble(data[2]);

		return p;
	}

	public static LineData composeLineData(String str) {

		str = str.substring(1 + str.indexOf("\t"));

		String[] data = str.split(",");

		LineData ld = new LineData();

		for (int i = 0; i < data.length; i++) {

			ld.addIndex(Integer.parseInt(data[i]));
		}

		return ld;
	}

	private void deleteSelectedPoints() {

		prepareUndo();

		LinkedList newLinkedList = new LinkedList();
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			if (point.isSelected()) {

				continue;

			}

			newLinkedList.push(point);

		}

		autolinesData.setElementAt(newLinkedList,chooseAutoline.getSelectedIndex());
		
		draw();

	}

	private void changeSelectedPoint() {

		prepareUndo();
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			if (point.isSelected()) {

				try {
					if (xcoordinate.getText() != null
							&& !xcoordinate.getText().equals(""))
						point.setX(xcoordinate.getvalue());

					if (ycoordinate.getText() != null
							&& !ycoordinate.getText().equals(""))
						point.setY(ycoordinate.getvalue());

					if (zcoordinate.getText() != null
							&& !zcoordinate.getText().equals(""))
						point.setZ(zcoordinate.getvalue());

				} catch (Exception e) {

				}
				continue;
			}

		}

		draw();

	}
	
	private void insertPointAfter() {
		
		prepareUndo();		
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			if (point.isSelected()) {

				
				Point3D nextPoint=null;
				
				if(i+1< size){
					
					nextPoint=  (Point3D) linkedList.get(i+1);
					
				}else{
					
					nextPoint=  (Point3D) linkedList.get(0);
				}
				
				
				
				double x=(point.x+nextPoint.x)*0.5;
				double y=(point.y+nextPoint.y)*0.5;
				double z=(point.z+nextPoint.z)*0.5;
				
				
				Point3D intermediatePoint=new Point3D(x,y,z);
				linkedList.add(i+1,intermediatePoint);
				
				break;
			}

		}


		draw();
	}

	private void moveSelectedPoints(int dx, int dy, int dk) {

		String sqty = pointsMove.getText();

		if (sqty == null || sqty.equals(""))
			return;

		double qty = Double.parseDouble(sqty);

		prepareUndo();
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D point = (Point3D) linkedList.get(i);

			if (point.isSelected()) {

				point.x += qty * dx;

				point.y += qty * dy;

				point.z += qty * dk;
				
				setPointCoordinates(point);

			}
		}

	}
	
	private void moveSelectedAutocars(int dx, int dy, int dk) {


		if(autocarsData.size()==0)
			return;



		AutocarData ad = (AutocarData) autocarsData.elementAt(chooseAutocar.getSelectedIndex());

		String sqty = autocarsMove.getText();

		if (sqty == null || sqty.equals(""))
			return;

		double qty = Double.parseDouble(sqty);

		prepareUndo();


		ad.x += qty * dx;

		ad.y += qty * dy;
		
		setAutocarData(ad);


	}


	@Override
	public void menuCanceled(MenuEvent arg0) {

		redrawAfterMenu = true;
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {

		redrawAfterMenu = true;
	}

	@Override
	public void menuSelected(MenuEvent arg0) {

		Object obj = arg0.getSource();



		redrawAfterMenu = false;

	}

	public class FileTransferhandler extends TransferHandler {

		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {

			for (int i = 0; i < transferFlavors.length; i++) {

				if (!transferFlavors[i].equals(DataFlavor.javaFileListFlavor))
					return false;
			}
			return true;
		}

		public boolean importData(JComponent comp, Transferable t) {

			try {
				List list = (List) t
						.getTransferData(DataFlavor.javaFileListFlavor);
				Iterator itera = list.iterator();
				while (itera.hasNext()) {

					Object o = itera.next();
					if (!(o instanceof File))
						continue;
					File file = (File) o;
					currentDirectory = file.getParentFile();
					loadAutocars(file);
					draw();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		int code = arg0.getKeyCode();

		if (code == KeyEvent.VK_LEFT)
			translate(-1, 0);
		else if (code == KeyEvent.VK_RIGHT)
			translate(+1, 0);
		else if (code == KeyEvent.VK_UP)
			translate(0, 1);
		else if (code == KeyEvent.VK_DOWN)
			translate(0, -1);
		else if (code == KeyEvent.VK_F1) {
			zoom(+1);
		} else if (code == KeyEvent.VK_F2) {
			zoom(-1);
		}
	}

	private void translate(int i, int j) {

		x0 += i * deltax;
		y0 += j * deltay;

		draw();
	}

	private void zoom(int i) {

		double alfa = 1.0;
		if (i > 0) {
			alfa = 0.5;

			if (dx == 1 || dy == 1)
				return;
		} else {
			alfa = 2.0;

		}

		dx = (int) (dx * alfa);
		dy = (int) (dy * alfa);

		x0 += (int) ((WIDTH / 2 + x0) * (1.0 / alfa - 1.0));
		y0 += (int) ((HEIGHT / 2 + y0) * (1.0 / alfa - 1.0));

		draw();
	}

	public void rescaleAllPoints() {

		prepareUndo();
		
		LinkedList linkedList=(LinkedList) autolinesData.elementAt(chooseAutoline.getSelectedIndex());

		String txt = rescaleValue.getText();

		if (txt == null || txt.equals(""))
			return;
		double val = Double.parseDouble(txt);
		int size = linkedList.size();

		for (int i = 0; i < size; i++) {

			Point3D p = (Point3D) linkedList.get(i);

			if (!p.isSelected())
				continue;

			p.x = Math.round(p.x * val);
			p.y = Math.round(p.y * val);
			p.z = Math.round(p.z * val);
		}


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

		if (arg0.getUnitsToScroll() < 0)
			translate(0, 1);
		else
			translate(0, -1);

	}

	public void mouseDragged(MouseEvent e) {

		isDrawCurrentRect = true;
		updateSize(e);

	}

	void updateSize(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		currentRect.setSize(x - currentRect.x, y - currentRect.y);

		draw();

	}
	
	public Area buildVisibileArea(int y1, int y2) {
		int[] cx=new int[4];
		int[] cy=new int[4];



		cx[0]=0;
		cy[0]=y1;
		cx[1]=WIDTH;
		cy[1]=y1;
		cx[2]=WIDTH;
		cy[2]=y2;
		cx[3]=0;
		cy[3]=y2;

		Polygon p=new Polygon(cx,cy,4);

		Area a = new Area(p);

		//System.out.println(Polygon3D.fromAreaToPolygon2D(a));

		return a;
	}
	
	public static Point3D foundPX_PY_PZ_TEXTURE_Intersection(Point3D pstart, Point3D pend,
			double y) {
		
		Point3D intersect=new Point3D(); 


		
		double l=(y-pstart.y)/(pend.y-pstart.y);
	
		
		intersect.p_x= (1-l)*pstart.p_x+l*pend.p_x;
		intersect.p_y= (1-l)*pstart.p_y+l*pend.p_y;		
		intersect.p_z= (1-l)*pstart.p_z+l*pend.p_z;
		
		intersect.texture_x=  (1-l)*pstart.texture_x+l*pend.texture_x;
		intersect.texture_y=  (1-l)*pstart.texture_y+l*pend.texture_y;
		
		return intersect;

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent arg0) {


	}	

	private void help() {

		HelpPanel hp = new HelpPanel(300, 200, this.getX() + 100, this.getY(),
				HelpPanel.AUTOCAR_EDITOR_HELP_TEXT, this);

	}

	public void propertyChange(PropertyChangeEvent arg0) {

		// System.out.println(arg0.getSource().getClass());
		if ("paintDirtyRegions".equals(arg0.getPropertyName())
				&& redrawAfterMenu) {
			// System.out.println("propertyChange");
			redrawAfterMenu = false;
			draw();
		}

	}

	class ComboItem {

		String value = null;
		String code = null;

		public ComboItem(String value, String code) {
			super();
			this.value = value;
			this.code = code;
		}

		@Override
		public String toString() {
			return value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}

}
