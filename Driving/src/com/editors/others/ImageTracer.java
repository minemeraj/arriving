package com.editors.others;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RepaintManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.Point3D;
import com.PolygonMesh;
import com.editors.Editor;
import com.editors.IntegerTextField;

public class ImageTracer extends Editor implements MenuListener,PropertyChangeListener, ActionListener, MouseListener,KeyListener{



	private int HEIGHT=600;
	private int WIDTH=600;
	private int LEFT_BORDER=200;
	private int RIGHT_BORDER=200;
	private int BOTTOM_BORDER=100;

	private BufferedImage buf;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color POINTS_COLOR = Color.WHITE;

	private Graphics2D graphics;
	private JPanel center;
	private JMenuBar jmb;
	private JMenu jm_file;
	private JMenuItem jmt_load_image;

	public boolean redrawAfterMenu=false;
	private BufferedImage backgroundImage;
	private JMenuItem jmt_load_points;
	private JMenuItem jmt_save_points;

	ArrayList<Point3D> points=null;

	private JList pointList=null;
	private IntegerTextField image_width;
	private IntegerTextField image_height;
	private IntegerTextField image_x;
	private IntegerTextField image_y;
	private int imageX=0;
	private int imageY=0;
	private int imageWidth=0;
	private int imageHeight=0;
	private JButton btnChangeImage;

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
						//if(redrawAfterMenu ) {displayAll();redrawAfterMenu=false;}
					}

				}				
				);

		initialize();

		setVisible(true);
	}

	private void buildPointsMenu() {
		
		JPanel right=new JPanel();
		right.setBounds(0, LEFT_BORDER+WIDTH,RIGHT_BORDER,HEIGHT);
		right.setLayout(null);
		
		pointList=new JList();
		
		int r=10;
		
		JScrollPane jsp=new JScrollPane(pointList);
		JPanel lowpane=new JPanel();
		lowpane.setBounds(5,r,300,220); 
		
		right.add(lowpane);
		
		getContentPane().add(right);
		
		
	}

	private void buildImageMenu() {
		
		
		JPanel left=new JPanel();
		left.setBounds(0, 0, LEFT_BORDER, HEIGHT);
		left.setLayout(null);
		
		int r=10;
		int col0=5;		
		int col1=90;
		
		JLabel label=new JLabel("Img width");
		label.setBounds(col0, r, 80, 20);
		left.add(label);
		
		image_width = new IntegerTextField(6);
		image_width.setBounds(col1, r, 100, 20);
		left.add(image_width);
		
		r+=30;
		
		label=new JLabel("Img heigth");
		label.setBounds(col0, r, 80, 20);
		left.add(label);
		
		image_height= new IntegerTextField(6);
		image_height.setBounds(col1, r, 100, 20);
		left.add(image_height);
		
		r+=30;
		
		label=new JLabel("Img x");
		label.setBounds(col0, r, 80, 20);
		left.add(label);
		
		image_x= new IntegerTextField(6);
		image_x.setBounds(col1, r, 100, 20);
		image_x.setText(imageX);
		left.add(image_x);
		
		r+=30;
		
		label=new JLabel("Img y");
		label.setBounds(col0, r, 80, 20);
		left.add(label);
		
		image_y= new IntegerTextField(6);
		image_y.setBounds(col1, r, 100, 20);
		image_y.setText(imageY);
		left.add(image_y);
		
		r+=30;
		btnChangeImage=new JButton("Change image");
		btnChangeImage.setBounds(col0, r, 120, 20);
		btnChangeImage.addActionListener(this);
		left.add(btnChangeImage);
		
		getContentPane().add(left);
		
	}

	private void setImagedata() {
		
		imageWidth=backgroundImage.getWidth();		
		image_width.setText(imageWidth);
		image_x.setText(0);
		
		imageHeight=backgroundImage.getHeight();
		image_height.setText(imageHeight);
		image_y.setText(0);
		
		draw();
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

		points=new ArrayList<Point3D>();
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

		if(backgroundImage!=null)
			bufferGraphics.drawImage(backgroundImage,imageX,imageY,imageWidth,imageHeight,null);

		bufferGraphics.setColor(POINTS_COLOR);

		int sz=points.size();
		for (int i = 0; i < sz; i++) {

			Point3D p=points.get(i);

			int x=(int) p.getX();
			int y=(int) p.getY();

			bufferGraphics.drawOval(x-2, y-2, 5, 5);

		}


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
		}
		else if("roadUpdate".equals(arg0.getPropertyName()))
		{
			draw();
		}

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

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			setTitle(file.getName());

			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();

			try{			
				PrintWriter pr = new PrintWriter(new FileOutputStream(file));

				int sz=points.size();
				for (int i = 0; i < sz; i++) {

					Point3D p=points.get(i);

					String str=decomposePoint(p);

					pr.println(str);

				}
				
				pr.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
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

				readPointsfromfile(file);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

	private void readPointsfromfile(File file) throws IOException {


		points=new ArrayList<Point3D>();

		BufferedReader br=new BufferedReader(new FileReader(file));


		String line=null;

		while((line=br.readLine())!=null){

			PolygonMesh.buildPoint(points,line);

		}

		br.close();
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
			try {
				backgroundImage=ImageIO.read(file);
				setImagedata();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			points.add(new Point3D(x,y,0));
		}

		draw();

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
			
		}
		draw();
	}

	private void zoom(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
