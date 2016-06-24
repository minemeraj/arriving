package com.editors.others;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.RepaintManager;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.Point3D;
import com.PointListItem;
import com.PolygonMesh;
import com.editors.DoubleTextField;
import com.editors.Editor;
import com.editors.IntegerTextField;

public class ImageTracer extends Editor implements MenuListener,PropertyChangeListener, ActionListener, MouseListener,MouseWheelListener,KeyListener{



	private int HEIGHT=600;
	private int WIDTH=600;
	private int LEFT_BORDER=200;
	private int RIGHT_BORDER=370;
	private int BOTTOM_BORDER=100;

	private BufferedImage buf;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color POINTS_COLOR = Color.WHITE;
	private static final Color SELECTED_POINTS_COLOR = Color.RED;
	private static final Color LINES_COLOR = Color.WHITE;
	
	private static final int IMAGE_MODE_FIXED_VALUES = 0;
	private static final int IMAGE_MODE_SCALE = 1;
	private static final int MOVE_POINT_UP = -1;
	private static final int MOVE_POINT_DOWN = +1;
	

	private Graphics2D graphics;
	private JPanel center;
	private JMenuBar jmb;
	private JMenu jm_file;
	private JMenuItem jmt_load_image;

	public boolean redrawAfterMenu=false;
	private BufferedImage backgroundImage;
	private JMenuItem jmt_load_points;
	private JMenuItem jmt_save_points;

	ArrayList<ArrayList<Point3D>> lines=null;

	private JList pointList=null;
	private IntegerTextField image_width;
	private IntegerTextField image_height;
	private IntegerTextField image_x;
	private IntegerTextField image_y;
	private int imageX=0;
	private int imageY=0;
	private int imageWidth=0;
	private int imageHeight=0;
	private double outputScale=0;
	private double outputDX=0;
	private double outputDY=0;
	private JButton btnChangeImage;
	private DoubleTextField xcoordinates;
	private JCheckBox checkMultiplePointsSelection;
	private DoubleTextField ycoordinates;
	private DoubleTextField zcoordinates;
	private JButton deselectAllPoints;
	private JButton selectAllPoints;
	private JButton deleteSelectedPoints;
	private DoubleTextField output_scale;
	private DoubleTextField output_dx;
	private DoubleTextField output_dy;

	private int x0=0;
	private int y0=0;

	protected int xMovement=0;
	protected int yMovement=0;

	private int minMovement=2;

	private int deltax=1;
	private int deltay=1;
	private JMenu jm_view;
	private JMenuItem jmt_faster_motion;
	private JMenuItem jmt_slower_motion;
	private JRadioButton imgModeFixedValues;
	private JRadioButton imgModeScale;
	private DoubleTextField image_scale;
	private JButton changePoint;
	private DoubleTextField pointsMove;
	private JButton movePointsUp;
	private JButton movePointsDown;
	private JButton movePointsLeft;
	private JButton movePointsRight;
	private JButton movePointsTop;
	private JButton movePointsBottom;

	public int MAX_STACK_SIZE = 10;
	private Stack<ArrayList<ArrayList<Point3D>>> oldLines=new Stack<ArrayList<ArrayList<Point3D>>>();
	private JMenuItem jmt_undo;
	private JButton btnImageDefault;
	private JButton moveListPointDown;
	private JButton moveListPointUp;
	private File imageFile=null;
	private JCheckBoxMenuItem jmt_lines_mode;
	private JButton addNewLine;
	private JButton addPointAfter;


	public static void main(String[] args) {

		ImageTracer it=new ImageTracer();

	}

	private ImageTracer(){

		setTitle("Image tracer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocation(10,10);
		setSize(WIDTH+LEFT_BORDER+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);

		center=new JPanel();
		center.setBounds(LEFT_BORDER, 0, WIDTH, HEIGHT);
		center.addMouseListener(this);
		center.addMouseWheelListener(this);
		getContentPane().add(center);

		buildMenuBar();

		buildImageMenu();

		buildPointsMenu();

		RepaintManager.setCurrentManager( 
				new RepaintManager(){
					@Override
					public void paintDirtyRegions() {


						super.paintDirtyRegions();
						firePropertyChange("paintDirtyRegions",false,true);

					}

				}				
				);

		initialize();
		
		addPropertyChangeListener(this);
		
		setVisible(true);
	}

	private void buildPointsMenu() {

		JPanel right=new JPanel();
		right.setBounds(LEFT_BORDER+WIDTH,0 ,RIGHT_BORDER,HEIGHT);
		right.setLayout(null);


		int r=10;
		int col0=5;
		int col1=40;

		JLabel label=new JLabel("x:");
		label.setBounds(col0,r,30,20);
		right.add(label);

		xcoordinates=new DoubleTextField(8);
		xcoordinates.setBounds(col1,r,120,20);
		xcoordinates.addKeyListener(this);
		right.add(xcoordinates);

		r+=30;

		label=new JLabel("y:");
		label.setBounds(col0,r,30,20);
		right.add(label);

		ycoordinates=new DoubleTextField(8);
		ycoordinates.setBounds(col1,r,120,20);
		ycoordinates.addKeyListener(this);
		right.add(ycoordinates);

		r+=30;

		label=new JLabel("z:");
		label.setBounds(col0,r,30,20);
		right.add(label);

		zcoordinates=new DoubleTextField(8);
		zcoordinates.setBounds(col1,r,120,20);
		zcoordinates.addKeyListener(this);
		right.add(zcoordinates);

		r += 30;

		changePoint = new JButton("Change point");
		changePoint.setBounds(10, r, 150, 20);
		changePoint.addActionListener(this);
		changePoint.addKeyListener(this);
		right.add(changePoint);

		r+=30;

		checkMultiplePointsSelection=new JCheckBox("Multiple selection");
		checkMultiplePointsSelection.setBounds(col0,r,150,20);
		checkMultiplePointsSelection.addKeyListener(this);
		right.add(checkMultiplePointsSelection);


		r+=30;

		selectAllPoints=new JButton("Select all");
		selectAllPoints.addActionListener(this);
		selectAllPoints.setFocusable(false);
		selectAllPoints.setBounds(col0,r,150,20);
		right.add(selectAllPoints);
		
		r+=30;

		addNewLine=new JButton("Add new line");
		addNewLine.addActionListener(this);
		addNewLine.setFocusable(false);
		addNewLine.setBounds(col0,r,150,20);
		addNewLine.setEnabled(false);
		right.add(addNewLine);
		
		r+=30;

		addPointAfter=new JButton("Add point after");
		addPointAfter.addActionListener(this);
		addPointAfter.setFocusable(false);
		addPointAfter.setBounds(col0,r,150,20);
		right.add(addPointAfter);

		r+=30;

		deselectAllPoints=new JButton("Deselect all");
		deselectAllPoints.addActionListener(this);
		deselectAllPoints.setFocusable(false);
		deselectAllPoints.setBounds(col0,r,150,20);
		right.add(deselectAllPoints);

		r+=30;

		deleteSelectedPoints=new JButton("Delete points");
		deleteSelectedPoints.addActionListener(this);
		deleteSelectedPoints.setFocusable(false);
		deleteSelectedPoints.setBounds(col0,r,150,20);
		right.add(deleteSelectedPoints);

		r+=30;

		right.add(buildPointsMovePanel(30, r));	

		r+=120;

		label=new JLabel("Output scale:");
		label.setBounds(col0,r,80,20);
		right.add(label);

		output_scale=new DoubleTextField(8);
		output_scale.setBounds(90,r,80,20);
		output_scale.addKeyListener(this);
		right.add(output_scale);

		r+=30;

		label=new JLabel("Output dx:");
		label.setBounds(col0,r,80,20);
		right.add(label);

		output_dx=new DoubleTextField(8);
		output_dx.setBounds(90,r,80,20);
		output_dx.addKeyListener(this);
		right.add(output_dx);

		r+=30;

		label=new JLabel("Output dy:");
		label.setBounds(col0,r,80,20);
		right.add(label);

		output_dy=new DoubleTextField(8);
		output_dy.setBounds(90,r,80,20);
		output_dy.addKeyListener(this);
		right.add(output_dy);


		int col2=180;

		r=10;

		moveListPointUp=new JButton("Move point up");
		moveListPointUp.setBounds(col2+5,r,130,20);
		moveListPointUp.addActionListener(this);
		right.add(moveListPointUp);

		r+=30;

		moveListPointDown=new JButton("Move point down");
		moveListPointDown.setBounds(col2+5,r,130,20);
		moveListPointDown.addActionListener(this);
		right.add(moveListPointDown);
		

		r+=30;

		pointList=new JList();
		pointList.setFocusable(false);

		JPanel lowpane=new JPanel();
		lowpane.setBounds(col2,r,300,220);

		lowpane.setLayout(null);
		JLabel jlb=new JLabel("Points:");
		jlb.setBounds(5,0,100,20);
		lowpane.add(jlb);
		JScrollPane jsp=new JScrollPane(pointList);
		jsp.setBounds(5,25,140,180);
		lowpane.add(jsp);
		pointList.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e){

				int index=pointList.getSelectedIndex();
				if(lines.size()>1 || jmt_lines_mode.isSelected())
					return;

				int sz=lines.size();

				for (int ii = 0; ii < sz; ii++) {

					ArrayList<Point3D> points=lines.get(ii);

					for(int i=0;points!=null && i<points.size();i++){

						Point3D p=points.get(i);
						if(index==i){
							selectPoint(p);
						}
						else if(!checkMultiplePointsSelection.isSelected())
							p.setSelected(false);

					}
				}
				draw();
			}




		});

		right.add(lowpane);





		getContentPane().add(right);


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

	private void buildImageMenu() {


		JPanel left=new JPanel();
		left.setBounds(0, 0, LEFT_BORDER, HEIGHT);
		left.setLayout(null);

		int r=10;
		int col0=5;		
		int col1=90;

		imgModeFixedValues=new JRadioButton("Fixed values");
		imgModeFixedValues.setBounds(col0, r, 150, 20);
		imgModeFixedValues.addActionListener(this);
		imgModeFixedValues.setFocusable(false);
		imgModeFixedValues.setSelected(true);
		left.add(imgModeFixedValues);

		r+=30;

		JLabel label=new JLabel("Img width");
		label.setBounds(col0, r, 80, 20);
		left.add(label);

		image_width = new IntegerTextField(6);
		image_width.setBounds(col1, r, 100, 20);
		image_width.addKeyListener(this);
		left.add(image_width);

		r+=30;

		label=new JLabel("Img heigth");
		label.setBounds(col0, r, 80, 20);
		left.add(label);

		image_height= new IntegerTextField(6);
		image_height.setBounds(col1, r, 100, 20);
		image_height.addKeyListener(this);
		left.add(image_height);

		r+=30;

		imgModeScale=new JRadioButton("Scale");
		imgModeScale.setBounds(col0, r, 60, 20);
		imgModeScale.addActionListener(this);
		imgModeScale.setFocusable(false);
		left.add(imgModeScale);

		ButtonGroup bg=new ButtonGroup();
		bg.add(imgModeFixedValues);
		bg.add(imgModeScale);

		image_scale= new DoubleTextField(6);
		image_scale.setBounds(col1, r, 100, 20);
		image_scale.setText("");
		image_scale.addKeyListener(this);
		left.add(image_scale);

		r+=30;

		label=new JLabel("Img x");
		label.setBounds(col0, r, 80, 20);
		left.add(label);

		image_x= new IntegerTextField(6);
		image_x.setBounds(col1, r, 100, 20);
		image_x.setText(imageX);
		image_x.addKeyListener(this);
		left.add(image_x);

		r+=30;

		label=new JLabel("Img y");
		label.setBounds(col0, r, 80, 20);
		left.add(label);

		image_y= new IntegerTextField(6);
		image_y.setBounds(col1, r, 100, 20);
		image_y.setText(imageY);
		image_y.addKeyListener(this);
		left.add(image_y);

		r+=30;

		btnChangeImage=new JButton("Change image");
		btnChangeImage.setBounds(col0, r, 120, 20);
		btnChangeImage.addActionListener(this);
		left.add(btnChangeImage);

		r+=30;

		btnImageDefault=new JButton("Image default");
		btnImageDefault.setBounds(col0, r, 120, 20);
		btnImageDefault.addActionListener(this);
		left.add(btnImageDefault);
		getContentPane().add(left);

	}

	private void setImageDefaults() {

		if(backgroundImage!=null)
			setImagedata(backgroundImage);

	}

	private void setImagedata(BufferedImage backgroundImg) {

		if(backgroundImg!=null){

			imageWidth=backgroundImg.getWidth();	
			imageHeight=backgroundImg.getHeight();

		}

		image_width.setText(imageWidth);
		image_x.setText(0);


		image_height.setText(imageHeight);
		image_y.setText(0);

		draw();
	}


	private void changeSelectedPoint() {

		prepareUndo();

		int sz=lines.size();

		for (int index = 0; index < sz; index++) {

			ArrayList<Point3D> points=lines.get(index);

			int size = points.size();

			for (int i = 0; i < size; i++) {

				Point3D point = (Point3D) points.get(i);

				if (point.isSelected()) {

					try {
						if (xcoordinates.getText() != null
								&& !xcoordinates.getText().equals(""))
							point.setX(xcoordinates.getvalue());

						if (ycoordinates.getText() != null
								&& !ycoordinates.getText().equals(""))
							point.setY(ycoordinates.getvalue());

						if (zcoordinates.getText() != null
								&& !zcoordinates.getText().equals(""))
							point.setZ(zcoordinates.getvalue());

					} catch (Exception e) {

					}
					continue;
				}
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

		int sz=lines.size();

		for (int index = 0; index < sz; index++) {

			ArrayList<Point3D> points=lines.get(index);

			int size = points.size();

			for (int i = 0; i < size; i++) {

				Point3D point = (Point3D) points.get(i);

				if (point.isSelected()) {

					point.x += qty * dx;

					point.y += qty * dy;

					point.z += qty * dk;

					setPointsData(point);

				}
			}

		}

	}

	private void setPointsData(Point3D p) {

		xcoordinates.setText(p.getX());
		ycoordinates.setText(p.getY());
		zcoordinates.setText(p.getZ());
	}

	private void clearPointsData() {

		xcoordinates.setText("");
		ycoordinates.setText("");
		zcoordinates.setText("");

		pointList.clearSelection();
	}

	private void changeImage() {

		imageX=image_x.getvalue();
		imageY=image_y.getvalue();

		imageWidth=image_width.getvalue();
		imageHeight=image_height.getvalue();

		draw();

	}

	private void initialize() {
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

		lines=new ArrayList<ArrayList<Point3D>>();

		xMovement=2*minMovement;
		yMovement=2*minMovement;
	}

	private void buildMenuBar() {
		jmb=new JMenuBar();
		jm_file=new JMenu("File");
		jm_file.addMenuListener(this);
		jmb.add(jm_file);

		jmt_load_image = new JMenuItem("Load image");
		jmt_load_image.addActionListener(this);
		jm_file.add(jmt_load_image);

		jm_file.addSeparator();

		jmt_load_points= new JMenuItem("Load points");
		jmt_load_points.addActionListener(this);
		jm_file.add(jmt_load_points);

		jmt_save_points= new JMenuItem("Save points");
		jmt_save_points.addActionListener(this);
		jm_file.add(jmt_save_points);

		jm_view=new JMenu("View");
		jm_view.addMenuListener(this);
		jmb.add(jm_view);
		
		jmt_lines_mode=new JCheckBoxMenuItem("Lines mode");
		jmt_lines_mode.addActionListener(this);
		jm_view.add(jmt_lines_mode);
		
		jm_view.addSeparator();

		jmt_faster_motion=new JMenuItem("+ motion");
		jmt_faster_motion.addActionListener(this);
		jm_view.add(jmt_faster_motion);

		jmt_slower_motion=new JMenuItem("- motion");
		jmt_slower_motion.addActionListener(this);
		jm_view.add(jmt_slower_motion);

		JMenu jm2 = new JMenu("Do");
		jm2.addMenuListener(this);
		jmb.add(jm2);

		jmt_undo = new JMenuItem("Undo");
		jmt_undo.addActionListener(this);
		jmt_undo.setEnabled(false);
		jm2.add(jmt_undo);

		setJMenuBar(jmb);

	}	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		draw();
	}

	private void draw() {

		Graphics bufferGraphics = buf.getGraphics();

		buildScreen(bufferGraphics);
		if(graphics==null)
			graphics=(Graphics2D) center.getGraphics();

		graphics.drawImage(buf,0,0,null);	


	}

	private void buildScreen(Graphics bufferGraphics) {

		bufferGraphics.setColor(BACKGROUND_COLOR);
		bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);

		int ds=1;

		if(backgroundImage!=null){

			if(imgModeFixedValues.isSelected()){

				int locX=calcX(imageX*ds,(HEIGHT-imageY)*ds,0);
				int locY=calcY(imageX*ds,(HEIGHT-imageY)*ds,0);
				int w=(int) (imageWidth*1.0/deltax);
				int h=(int) (imageHeight*1.0/deltay);
				bufferGraphics.drawImage(backgroundImage,locX,locY,w,h,null);

			}else if(imgModeScale.isSelected()){

				double factor=image_scale.getvalue();
				if(factor==0)
					factor=1.0;		

				int locX=calcX(imageX*ds,(HEIGHT-imageY)*ds,0);
				int locY=calcY(imageX*ds,(HEIGHT-imageY)*ds,0);
				int w=(int) (backgroundImage.getWidth()*factor/deltax);
				int h=(int) (backgroundImage.getHeight()*factor/deltay);

				bufferGraphics.drawImage(backgroundImage,locX,locY,w,h,null);

			}
		}


		if(lines==null)
			return;

		int size=lines.size();
		
		//draw lines
		if(jmt_lines_mode.isSelected()){
			
			for (int index = 0; index < size; index++) {

				ArrayList<Point3D> points=lines.get(index);

				int sz=points.size();
				for (int i = 0; i < sz-1; i++) {

					bufferGraphics.setColor(LINES_COLOR);

					Point3D p0=points.get(i);
					Point3D p1=points.get(i+1);

					int x0=calcX(p0);
					int y0=calcY(p0); 
					
					int x1=calcX(p1);
					int y1=calcY(p1); 

					bufferGraphics.drawLine(x0, y0, x1, y1);

				}

			}
		}

		//draw points
		for (int index = 0; index < size; index++) {

			ArrayList<Point3D> points=lines.get(index);

			int sz=points.size();
			for (int i = 0; i < sz; i++) {

				bufferGraphics.setColor(POINTS_COLOR);

				Point3D p=points.get(i);

				if(p.isSelected())
					bufferGraphics.setColor(SELECTED_POINTS_COLOR);

				int x=calcX(p);
				int y=calcY(p);

				bufferGraphics.drawOval(x-2, y-2, 5, 5);

			}

		}
		
	
	}

	private int calcY(Point3D p) {
		return calcY(p.getX(),p.getY(),p.getZ());
	}

	private int calcY(double x, double y, double z) {
		return (int)(HEIGHT-y/deltay-y0);

	}

	private int calcX(Point3D p) {
		return calcX(p.getX(),p.getY(),p.getZ());
	}

	private int calcX(double x, double y, double z) {
		return (int)(x/deltax-x0);
	}

	public int invertX(int i) {

		return (i+x0)*deltax;
	}
	public int invertY(int j) {

		return deltay*(HEIGHT-j-y0);
	}

	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		redrawAfterMenu=true;

	}
	@Override
	public void menuSelected(MenuEvent arg0) {
		redrawAfterMenu=false;

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {

		if("paintDirtyRegions".equals(arg0.getPropertyName()) && redrawAfterMenu)
		{
			draw();
			redrawAfterMenu=false;
			
		}else if("jmt_lines_mode".equals(arg0.getPropertyName()))
			setLineMode();


	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object obj = arg0.getSource();

		if(obj==jmt_load_image)
			loadBackgroundImage();
		else if(obj==jmt_load_points)
			loadPoints();
		else if(obj==jmt_save_points)
			savePoints();
		else if(obj==btnChangeImage){
			changeImage();
		}else if(obj==selectAllPoints){
			selectAllPoints();
		}else if(obj==deselectAllPoints){
			deselectAllPoints();
		}else if(obj==deleteSelectedPoints){
			deleteSelectedPoints();
		}else if(obj==jmt_faster_motion){
			changeMotionIncrement(+1);
		}else if(obj==jmt_slower_motion){
			changeMotionIncrement(-1);
		}else if(obj==imgModeFixedValues){

			enableImageMode(IMAGE_MODE_FIXED_VALUES);

		}else if (obj == changePoint){

			changeSelectedPoint();

		}else if(obj==imgModeScale){

			enableImageMode(IMAGE_MODE_SCALE);
			
		}else if (obj == btnImageDefault){

			setImageDefaults();

		} else if (obj == movePointsBottom) {

			moveSelectedPoints(0, 0, -1);

		} else if (obj == movePointsTop) {

			moveSelectedPoints(0, 0, 1);

		} else if (obj == movePointsLeft) {

			moveSelectedPoints(-1, 0, 0);

		} else if (obj == movePointsRight) {

			moveSelectedPoints(1, 0, 0);

		} else if (obj == movePointsUp) {

			moveSelectedPoints(0, 1, 0);

		} else if (obj == movePointsDown) {

			moveSelectedPoints(0, -1, 0);


		}else if(obj==moveListPointUp){

			moveListPoint(MOVE_POINT_UP);

		}else if(obj==moveListPointDown){

			moveListPoint(MOVE_POINT_DOWN);

		}else if (obj == jmt_undo){
			undo();
		}else if (obj == jmt_lines_mode){

			firePropertyChange("jmt_lines_mode", false, true);
			
		}else if (obj == addNewLine){
			
			addLineToArray();
		}else if (obj == addPointAfter){
			addPointAfter();
		}

		

		draw();
		
	}


	private void setLineMode() {
		
		boolean  selected=jmt_lines_mode.isSelected();
		
		if(selected){
			
			pointList.setEnabled(false);
			moveListPointDown.setEnabled(false);
			moveListPointUp.setEnabled(false);
			addNewLine.setEnabled(false);
			emptyPointsList();
			
		}else{
			
			pointList.setEnabled(true);
			moveListPointDown.setEnabled(true);
			moveListPointUp.setEnabled(true);
			addNewLine.setEnabled(false);
			
			resetLists();
		}

	}


	private void enableImageMode(int mode) {

		if(mode==IMAGE_MODE_FIXED_VALUES){

			image_scale.setEnabled(false);
			image_height.setEnabled(true);
			image_width.setEnabled(true);

		}else if(mode==IMAGE_MODE_SCALE){

			image_scale.setEnabled(true);
			image_height.setEnabled(false);
			image_width.setEnabled(false);

		}

		draw();
	}

	private void savePoints() {
		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save points");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);


		outputScale=output_scale.getvalue();
		if(outputScale==0)
			outputScale=1.0;

		outputDX=output_dx.getvalue();
		outputDY=output_dy.getvalue();

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			setTitle(file.getName());

			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();

			try{			
				PrintWriter pr = new PrintWriter(new FileOutputStream(file));

				pr.println("image_width="+imageWidth);
				pr.println("image_height="+imageHeight);
				pr.println("image_x="+imageX);
				pr.println("image_y="+imageY);

				int imgMode=imgModeFixedValues.isSelected()?IMAGE_MODE_FIXED_VALUES:IMAGE_MODE_SCALE;
				pr.println("image_mode="+imgMode);

				pr.println("output_scale="+outputScale);
				pr.println("output_dx="+outputDX);
				pr.println("output_dy="+outputDY);
				pr.println("image_scale="+image_scale.getvalue());
				if(imageFile!=null)
					pr.println("image_file="+imageFile.getAbsolutePath());

				int size=lines.size();

				for (int index = 0; index < size; index++) {

					ArrayList<Point3D> points=lines.get(index);
					
					if(points.size()==0)
						continue;
					
					pr.println("<line>");

					int sz=points.size();
					for (int i = 0; i < sz; i++) {

						Point3D p=points.get(i);

						String str=decomposePoint(p,outputScale,outputDX,outputDY);

						pr.println("v="+str);

					}					
					pr.println("</line>");

				}

				pr.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private static final String decomposePoint(Point3D p, double factor, double outputDX2, double outputDY2) {
		String str="";

		str=(p.x*factor+outputDX2)+" "+(p.y*factor+outputDY2)+" "+(p.z*factor);

		if(p.getData()!=null)
			str=str+" "+p.getData().toString();

		return str;
	}

	private void loadPoints() {
		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load points ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			try {

				loadDatafromfile(file);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

	private void loadDatafromfile(File file) throws IOException {


		lines=new ArrayList<ArrayList<Point3D>>();

		File newImageFile=null;

		BufferedReader br=new BufferedReader(new FileReader(file));


		String line=null;

		while((line=br.readLine())!=null){

			int indx=line.indexOf("=");

			if(line.startsWith("<line>")){
				addLineToArray();
			}
			else if(line.startsWith("v=")){
				buildPoint(lines,line.substring(indx+1),outputScale,outputDX,outputDY);
			} else if(line.startsWith("image_width=")){

				imageWidth=Integer.parseInt(line.substring(indx+1));
				image_width.setText(imageWidth);

			} else if(line.startsWith("image_height=")){

				imageHeight=Integer.parseInt(line.substring(indx+1));
				image_height.setText(imageHeight);

			}else if(line.startsWith("image_x=")){

				imageX=Integer.parseInt(line.substring(indx+1));
				image_x.setText(imageX);

			}else if(line.startsWith("image_y=")){

				imageY=Integer.parseInt(line.substring(indx+1));	
				image_y.setText(imageY);

			}else if(line.startsWith("output_scale=")){

				double factor = Double.parseDouble(line.substring(indx+1));
				outputScale=factor;
				if(outputScale==0)
					outputScale=1.0;

				output_scale.setText(outputScale);

			}else if(line.startsWith("output_dx=")){

				double translation = Double.parseDouble(line.substring(indx+1));
				outputDX=translation;					
				output_dx.setText(outputDX);

			}else if(line.startsWith("output_dy=")){

				double translation = Double.parseDouble(line.substring(indx+1));
				outputDY=translation;					
				output_dy.setText(outputDY);

			}
			else if(line.startsWith("image_scale=")){

				double factor = Double.parseDouble(line.substring(indx+1));
				image_scale.setText(factor);

			}else if(line.startsWith("image_mode=")){

				int mode=Integer.parseInt(line.substring(indx+1));

				if(IMAGE_MODE_SCALE==mode)
					imgModeScale.setSelected(true);
				else if(IMAGE_MODE_FIXED_VALUES==mode)
					imgModeFixedValues.setSelected(true);
				enableImageMode(mode);

			}else if(line.startsWith("image_file=")){
				newImageFile=new File(line.substring(indx+1));
			}


		}

		if(newImageFile!=null){

			imageFile=newImageFile;
			loadBackgroundImage(newImageFile);
		}

		resetLists();


		br.close();
	}

	private void addLineToArray() {
		
		ArrayList points= new ArrayList<Point3D>();
		lines.add(points);
		
	}

	private static final void buildPoint(ArrayList<ArrayList<Point3D>> vLines,String str, 
			double outputScaleValue, 
			double outputDX2, 
			double outputDY2) {


		ArrayList<Point3D> points=null;

		if(vLines.size()==0){

			points= new ArrayList<Point3D>();
			vLines.add(points);

		}else{

			points=vLines.get(vLines.size()-1);
		}

		if(outputScaleValue==0)
			outputScaleValue=1.0;

		String[] vals =str.split(" ");

		Point3D p=new Point3D();

		p.x=(Double.parseDouble(vals[0])-outputDX2)/outputScaleValue;
		p.y=(Double.parseDouble(vals[1])-outputDY2)/outputScaleValue;
		p.z=Double.parseDouble(vals[2])/outputScaleValue;

		if(vals.length==4)
			p.setData(vals[3]);

		points.add(p);


	}


	private void loadBackgroundImage() {

		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load image ");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			File file = fc.getSelectedFile();
			loadBackgroundImage(file);
			setImagedata(backgroundImage);
		}	



	}

	private void loadBackgroundImage(File file) {
		try {

			backgroundImage=ImageIO.read(file);			
			imageFile=file;

		} catch (IOException e) {

			imageFile=null;
			e.printStackTrace();
		}
	}

	@Override
	public void decomposeObjVertices(PrintWriter pr, PolygonMesh mesh, boolean isCustom) {
		//here useless

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		int x=arg0.getX();
		int y=arg0.getY();

		int buttonNum=arg0.getButton();

		if(buttonNum==MouseEvent.BUTTON3){

			int xx=invertX(x);
			int yy=invertY(y);

			insertPoint(xx,yy,0);


		}else{

			selectPoints(x,y);

		}

		draw();
		resetLists();
	}

	private void insertPoint(int xx, int yy, int i) {


		ArrayList<Point3D> points=null;

		if(lines.size()==0){

			points= new ArrayList<Point3D>();
			lines.add(points);

		}else{

			points=lines.get(lines.size()-1);
		}

		points.add(new Point3D(xx,yy,0));
		pointList.ensureIndexIsVisible(pointList.getModel().getSize()-1); 
	}
	




	private void addPointAfter() {
		int sz=lines.size();

		for (int index = 0; index < sz; index++) {

			ArrayList<Point3D> points=lines.get(index);
			
			boolean found=false;

			int size = points.size();

			for (int i = 0; i < size-1; i++) {

				Point3D point0 = (Point3D) points.get(i);
				Point3D point1 = (Point3D) points.get(i+1);

				if (point0.isSelected()) {
					
					Point3D p=new Point3D(
							(point0.getX()+point1.getX())*0.5,
							(point0.getY()+point1.getY())*0.5,
							(point0.getZ()+point1.getZ())*0.5
							);
					points.add(i+1, p);
					found=true;
					break;
				}
			}	
			
			if(found)
				break;
		}
		
		deselectAllPoints();
	}

	private void selectPoint(Point3D p) {
		p.setSelected(true);
		setPointsData(p);
	}

	private void selectPoints(int xPos, int yPos) {

		if(lines==null)
			return;

		boolean found=false;

		int size=lines.size();

		for (int index = 0; index < size; index++) {

			ArrayList<Point3D> points=lines.get(index);

			int sz=points.size();
			for (int i = 0; i < sz; i++) {

				Point3D p=points.get(i);

				int x=calcX(p);
				int y=calcY(p);

				Rectangle rect=new Rectangle(x-4,y-4,9,9);

				if(rect.contains(xPos,yPos)){
					p.setSelected(true);
					found=true;
					pointList.setSelectedIndex(i);

					if(!checkMultiplePointsSelection.isSelected())
						setPointsData(p);
				}
				else if(!checkMultiplePointsSelection.isSelected()){
					p.setSelected(false);
				}
			}
		}
		if(!found)
			clearPointsData();

	}


	private void deleteSelectedPoints() {

		if(lines==null)
			return;
		
		ArrayList<ArrayList<Point3D>> newLines=new ArrayList<ArrayList<Point3D>>();

		ArrayList<Point3D> newPoints=new ArrayList<Point3D>();

		int size=lines.size();

		for (int index = 0; index < size; index++) {

			ArrayList<Point3D> points=lines.get(index);

			int sz=points.size();
			for (int i = 0; i < sz; i++) {

				Point3D p=points.get(i);

				if(p.isSelected())
					continue;

				newPoints.add(p);
			}	

			newLines.add(newPoints);
		}
		lines=newLines;
		
		clearPointsData();
		
		resetLists();

	}

	private void deselectAllPoints() {

		if(lines==null)
			return;
		int size=lines.size();

		for (int index = 0; index < size; index++) {

			ArrayList<Point3D> points=lines.get(index);
			int sz=points.size();
			for (int i = 0; i < sz; i++) {

				Point3D p=points.get(i);

				p.setSelected(false);
			}	
		}
		clearPointsData();

	}

	private void selectAllPoints() {

		if(lines==null)
			return;
		int size=lines.size();

		for (int index = 0; index < size; index++) {

			ArrayList<Point3D> points=lines.get(index);
			int sz=points.size();
			for (int i = 0; i < sz; i++) {

				Point3D p=points.get(i);

				p.setSelected(true);
			}	
		}

		clearPointsData();

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
	public void keyPressed(KeyEvent arg0) {

		int code =arg0.getKeyCode();

		if(code==KeyEvent.VK_F1  )
		{ 
			zoom(+1);

		}
		else if(code==KeyEvent.VK_F2  )
		{  
			zoom(-1);

		}if(code==KeyEvent.VK_DOWN ){
			translate(0, 1);

		}else if(code==KeyEvent.VK_UP  ){
			translate(0, -1);

		}	
		else if(code==KeyEvent.VK_LEFT )
		{	
			translate(-1, 0);

		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	 
			translate(1, 0);  

		}
		draw();
		resetLists();
	}

	private void translate(int i, int j) {
		x0+=i*xMovement;
		y0+=+j*yMovement;

	}

	private void zoom(int i) {
		double alfa=1.0;
		if(i>0){
			alfa=0.5;

			if(deltax==1 || deltay==1)
				return;
		}
		else {
			alfa=2.0;

		}


		deltax=(int) (deltax*alfa);
		deltay=(int) (deltay*alfa);

		x0+=(int) ((WIDTH/2+x0)*(1.0/alfa-1.0));
		y0+=(int) ((-HEIGHT/2+y0)*(1.0/alfa-1.0));

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void mouseDown() {
		translate(0,-1);
	}


	public void mouseUp() {
		translate(0,1);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {



		int pix=arg0.getUnitsToScroll();
		if(pix>0) 
			mouseUp();
		else 
			mouseDown();

		draw();



	}

	public void changeMotionIncrement(int i) {
		if(i>0){

			xMovement=2*xMovement;
			yMovement=2*yMovement;

		}else{

			if(xMovement==minMovement)
				return;

			xMovement=xMovement/2;
			yMovement=yMovement/2;

		}

	}

	@Override
	public void prepareUndo() {

		jmt_undo.setEnabled(true);

		int sz=lines.size();

		ArrayList<ArrayList<Point3D>> newLines=new  ArrayList<ArrayList<Point3D>>();

		for (int index = 0; index < sz; index++) {

			ArrayList<Point3D> points=lines.get(index);

			ArrayList<Point3D> newPoints=new ArrayList<Point3D>();

			int size = points.size();

			for (int i = 0; i < size; i++) {

				Point3D point = (Point3D) points.get(i);

				newPoints.add(point.clone());

			}
			newLines.add(newPoints);
		}

		oldLines.push(newLines);

		if(oldLines.size()==MAX_STACK_SIZE)
			oldLines.removeElementAt(0);
	}

	@Override
	public void undo() {

		if(oldLines.size()>0)  
			lines=(ArrayList<ArrayList<Point3D>>) oldLines.pop();
	}

	public void resetLists() {

		if(lines==null || jmt_lines_mode.isSelected())
			return;
		
		int sz=lines.size();

		if(sz>1)
			return;		

		for (int ii = 0; ii < sz; ii++) {

			ArrayList<Point3D> points=lines.get(ii);

			DefaultListModel dlm=new DefaultListModel();
			int sel=pointList.getSelectedIndex();


			for(int i=0;i<points.size();i++){

				Point3D p=points.get(i);
				dlm.addElement(new PointListItem(p,i)) ; 
			}

			pointList.setModel(dlm);

			if(sel>=0 && sel<pointList.getModel().getSize())
				pointList.setSelectedIndex(sel);


		}
	}


	private void emptyPointsList() {
		
		DefaultListModel dlm=new DefaultListModel();
		pointList.setModel(dlm);
		
	}

	private void moveListPoint(int mode) {

		int sel=pointList.getSelectedIndex();

		if(lines==null || jmt_lines_mode.isSelected())
			return;

		if(lines.size()>1)
			return;

		int sz=lines.size();

		for (int ii = 0; ii < sz; ii++) {

			ArrayList<Point3D> points=lines.get(ii);


			if(	sel<0 || 
					(mode==MOVE_POINT_DOWN && sel==points.size()-1) ||
					(mode==MOVE_POINT_UP && sel==0)
					){

				return;
			}


			Point3D p=points.get(sel);


			if(mode==MOVE_POINT_DOWN){

				Point3D pp=points.get(sel+1);
				points.set(sel, pp);
				points.set(sel+1, p);
				pointList.setSelectedIndex(sel+1);

			}else if(mode==MOVE_POINT_UP){

				Point3D pp=points.get(sel-1);
				points.set(sel, pp);
				points.set(sel-1, p);
				pointList.setSelectedIndex(sel-1);
			}


			resetLists();


		}
	}
}
