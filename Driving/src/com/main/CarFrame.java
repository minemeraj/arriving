package com.main;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.CubicMesh;
import com.DrawObject;
import com.Engine;
import com.Texture;
import com.Transparency;
import com.sound.AdvancedGameSound;
import com.sound.GameSound;

public class CarFrame extends JFrame implements KeyListener,ActionListener {

	

	
    int counter=0;
	String VERSION="CarDriving 8.1.0";
	
	JPanel center=null;
	private Graphics2D graphics2D;
	public static int HEIGHT=500;
	public static int WIDTH=800;

	
	int car_num=0;
	int back_num=0;
	int obj_num=0;
	
	public static double CAR_SPEED=0;

	
	
	public static int BUTTOMBORDER=100;
	public static int UPBORDER=40;
	public static int LEFTBORDER=0;
	public static int RIGHTBORDER=0;
	
	public static Color BACKGROUND_COLOR=Color.GREEN;
	//public static double CURVATURE_RADIUS = WIDTH/2;
	
	int CAR_X=150;
	int CAR_Y=150;
	int CAR_WIDTH=90;
	int CAR_HEIGHT=90;
	
	JButton resetCar=null;

	
	public static Texture background=null;
	public static Texture[] worldTextures;
	public static Texture[] objects=null;
	public static CubicMesh[] object3D=null;
	public static Texture[] objectTextures=null;
	public static Texture[] carTextures=null;
	String IMAGES_PATH="lib/";
	Engine engine=null;

	private JPanel bottom;
	private Road road;
	private JPanel up;
	private Transparency transparency;
	private JLabel speedometer;
	DecimalFormat df=new DecimalFormat("####");
	
	public Properties p;

	private JLabel forward;
	
	
	
	public static boolean isUseTextures=true;
	private static JLabel steerAngle;
	
	String SOUNDS_FOLDER="lib/";
	File hornFile=null;
	File engineFile=null;
	AdvancedGameSound engineSound=null;
	private GameSound hornSound;
	private Date t;
	private BufferedImage buf;
	
	double inverse_car_mass=0.05;
	
	//steering angle,positive anti-clockwise
    double delta=0.30;
	private boolean isProgramPaused=true;
	
	
	public boolean skipShading=false;
	
	 public static void main(String[] args) {
		 
		boolean  skipShading=false;
		
		if(args.length==1)
			skipShading="-s".equals(args[0]);
		 
		CarFrame ff=new CarFrame(skipShading);
		//ff.initialize();
	
	}
	 
	 
	public CarFrame(boolean skipShading){
		
	 super();
	 
	 this.skipShading=skipShading;	
	 
	 setTitle(VERSION);
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 setLayout(null);
	 center=new JPanel();
	 center.setBackground(BACKGROUND_COLOR);
	 center.setBounds(LEFTBORDER,UPBORDER,WIDTH,HEIGHT);
	 add(center);
	
	 
	 bottom=new JPanel();
	 resetCar= new JButton("Reset car");
	 resetCar.addActionListener(this);
	 resetCar.addKeyListener(this);
	 bottom.add(resetCar);
	 bottom.setBounds(0,UPBORDER+HEIGHT,LEFTBORDER+WIDTH+RIGHTBORDER,BUTTOMBORDER);
	 add(bottom);
	 setSize(LEFTBORDER+WIDTH+RIGHTBORDER,UPBORDER+HEIGHT+BUTTOMBORDER);
	 buildUpPannel();
	 
	 initialize();
	 setVisible(true);
	 
	 start();
	 
	} 

	private void buildUpPannel() {
		
		up=new JPanel();
		JLabel speed=new JLabel("Speed:");
		speedometer=new JLabel("");
		up.add(speed);
		up.add(speedometer);
		forward=new JLabel("(F)");
		up.add(forward);
		
		JLabel steer=new JLabel("Steer:");
		steerAngle=new JLabel("0");
		up.add(steer);
		up.add(steerAngle);
		
		up.setBounds(0,0,LEFTBORDER+WIDTH+RIGHTBORDER,UPBORDER);
		add(up);
	}

	
	

	/**
	 * 
	 */
	private void initialize() {
		
		loadProperties();
		
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		
		try {

		
			background=
				DrawObject.fromImageToTexture(
						ImageIO.read(new File("lib/background_"+back_num+".jpg")),Color.BLUE
						
				);
			
			File directoryImg=new File("lib");
			File[] files=directoryImg.listFiles();
			
			Vector vObjects=new Vector();
			
				for(int i=0;i<files.length;i++){
					if(files[i].getName().startsWith("object_")){
						
						vObjects.add(files[i]);
					}		
				}
			
			
			objects=new Texture[vObjects.size()];
			
			for(int j=0;j<vObjects.size();j++){
				
				File fileObj=(File) vObjects.elementAt(j);
				
				objects[j]=new Texture(ImageIO.read(fileObj));
						
						
			}
		
			Vector vRoadTextures=new Vector();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("road_texture_")){
					
					vRoadTextures.add(files[i]);
					
				}		
			}
			
			worldTextures=new Texture[vRoadTextures.size()];
			
			
			if(isUseTextures){
				
				
				for(int i=0;i<vRoadTextures.size();i++){
					
					worldTextures[i]=new Texture(ImageIO.read(new File("lib/road_texture_"+i+".jpg")));
				}
				
			
				
		
				
			}
			
			Vector vCarTextures=new Vector();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("car_texture_t")){
					
					vCarTextures.add(files[i]);
					
				}		
			}
			
			carTextures=new Texture[vCarTextures.size()];
			
			
			if(isUseTextures){
				
				
				for(int i=0;i<vCarTextures.size();i++){
					
					File file=(File) vCarTextures.elementAt(i);
					carTextures[i]=new Texture(ImageIO.read(file));
				}

				
			}
			
			Vector vCarData=new Vector();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("cardefault3D_")){
					
					vCarData.add(files[i]);
					
				}		
			}
			
			
			Vector v3DObjects=new Vector();
			
				for(int i=0;i<files.length;i++){
					if(files[i].getName().startsWith("object3D_")
					   && 	!files[i].getName().startsWith("object3D_texture")	
					){
						
						v3DObjects.add(files[i]);
						
					}		
				}

			object3D=new CubicMesh[v3DObjects.size()];
			objectTextures=new Texture[v3DObjects.size()];
			
			
		
			for(int i=0;i<v3DObjects.size();i++){
					
				object3D[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i));
				if(isUseTextures)
					objectTextures[i]=new Texture(ImageIO.read(new File("lib/object3D_texture_"+i+".jpg")));
			
			}
			
			
			
			hornFile=new File(SOUNDS_FOLDER+"horn.wav");
			engineFile=new File(SOUNDS_FOLDER+"shortDiesel.wav");
			engineSound=new AdvancedGameSound(engineFile,-1,false);
			engineSound.filter(getEngineModulation());
			
			graphics2D=(Graphics2D) center.getGraphics();
			transparency=new Transparency();
			setCarSpeed(0);
			road=new Road(WIDTH,HEIGHT,this);
			road.initCars(vCarData);
			
			hornSound = new GameSound(hornFile,true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void loadProperties(){
		
		p=new Properties();
		try {
			p.load(new FileInputStream("lib/driving.properties"));
			
			if("true".equals(p.getProperty("ISUSETEXTURE")))
					isUseTextures=true;
			else
				isUseTextures=false;	
			

			if( p.getProperty("INSTRUMENT_CODE")==null)
				p.setProperty("INSTRUMENT_CODE",""+0);

			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		
	
	}


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0)  {
		char press=arg0.getKeyChar();

		int code=arg0.getKeyCode();

		if(code==KeyEvent.VK_R) 
			reset();
		else if(code==KeyEvent.VK_UP || code==KeyEvent.VK_W)
		{	
			if(getcarSpeed()==0){
				road.start();
			}

			road.setAccelerationVersus(1);


		}
		else if(code==KeyEvent.VK_DOWN || code==KeyEvent.VK_S)
		{			
			road.setIsBraking(true);

		}
		else if(code==KeyEvent.VK_LEFT || code==KeyEvent.VK_A) 
		{			
				
			//rotate(+1);
			steer(-delta);
		}
		else if(code==KeyEvent.VK_RIGHT|| code==KeyEvent.VK_D)
		{			
			
			//rotate(-1);
			steer(+delta);
		}
		else if(code==KeyEvent.VK_C)
		{
			isProgramPaused=true;
			
			setCarSpeed(0);
			
            road.selectNextCar();
            
            isProgramPaused=false;
		}
		else if(code==KeyEvent.VK_B)
		{

			isProgramPaused=true;
			
			back_num=back_num+1;
			try{

				boolean exists = (new File(IMAGES_PATH+"background_"+back_num+".jpg")).exists();
				if(exists){

					background=
						DrawObject.fromImageToTexture(
								ImageIO.read(new File("lib/background_"+back_num+".jpg")),Color.BLUE

						);
				}
				else
				{
					back_num=0;
					background=
						DrawObject.fromImageToTexture(
								ImageIO.read(new File("lib/background_"+back_num+".jpg")),Color.BLUE
						);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			reset();

		}
		else if(code==KeyEvent.VK_N)
		{			
			Road.FORWARD=1;
			forward.setText("(F)");
			//road.setAccelerationVersus(1);
		}
		else if(code==KeyEvent.VK_M)
		{			
			Road.FORWARD=-1;
			forward.setText("(R)");
			//road.setAccelerationVersus(1);
		}
		else if(code==KeyEvent.VK_H)
		{			
			playHorn();
		
		}
		else if(code==KeyEvent.VK_Z)
		{			
			if(Road.VIEW_TYPE==Road.FRONT_VIEW)
				 road.rotateSky(Math.PI);
			Road.VIEW_TYPE=Road.REAR_VIEW;
		   
		}
		else if(code==KeyEvent.VK_1)
		{
			road.changeCamera(0);
			 drawRoad();
		}
		else if(code==KeyEvent.VK_2)
		{
			road.changeCamera(1);
			 drawRoad();
		}
		else if(code==KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	private Image loadImage(String string) throws IOException {
		
		boolean exists = (new File(string)).exists();
		if(exists){
			Image img=ImageIO.read(new File(string)); 
			img=Transparency.makeColorTransparent(img,Color.WHITE);
			return img;
		}
		return null;
	}


	
	/*private void calculateSpeed(){
		
		if(isBraking){
			
			double gamma=0.1;
			double friction=-3;
			CAR_SPEED+= inverse_car_mass*(friction-CAR_SPEED*gamma);
			
		}
		else{
			
			double gamma=0.09;
			
			if(CAR_SPEED>=0)
				CAR_SPEED+=inverse_car_mass*(torque-CAR_SPEED*gamma);
			
		}
		setCarSpeed(CAR_SPEED);
		
		
	}*/

	private double getEngineModulation(){
		
		double ratio = 80.0/CAR_SPEED;
		
		if(ratio>5)
			return 5;
		else if(ratio<0)
			return 1;
		else
			return ratio;
	}
	
	private double getcarSpeed(){
		
		return CAR_SPEED;
	}
	
	public void setCarSpeed(double d) { 
		
		CAR_SPEED=d;
		if(CAR_SPEED<0)
			CAR_SPEED=0;

		speedometer.setText(df.format(CAR_SPEED*Road.SPEED_SCALE/Road.SPEED_SCALE));
		
		try {
			
			if(CAR_SPEED>0 ){
				
				if(!engineSound.isRun()){
					
					engineSound.start();
				}
			    engineSound.setPlay(true);
			    engineSound.filter(getEngineModulation());
				
			}
			else engineSound.setPlay(false);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	/*public void setTorque(double val){
		
		torque=val;
	}*/


	public void up() {
		
		if(isProgramPaused)
			return;
		
		//calculateSpeed();
		road.up(graphics2D);
		
		drawRoad();

	}
	
	public void steer(double angle) {
		
		road.setSteerAngle(angle);
	
	
	}
	
	public void down() {
		road.down(graphics2D);
		drawRoad(); 
	}
	
	public static void setSteeringAngle(double d) {
		
		DecimalFormat df=new DecimalFormat("##.##");
		steerAngle.setText(df.format(d));
		
	}


	public void keyReleased(KeyEvent arg0) {
		
		
		int code=arg0.getKeyCode();
		
		
		if(code==KeyEvent.VK_LEFT ||code==KeyEvent.VK_RIGHT || code==KeyEvent.VK_A ||code==KeyEvent.VK_D)
		{			
			
		
			steer(0);
			
		}
		else if(code==KeyEvent.VK_Z){
			
						
			if(Road.VIEW_TYPE==Road.REAR_VIEW)
				road.rotateSky(0);
			Road.VIEW_TYPE=Road.FRONT_VIEW;
			
		}
		else if((code==KeyEvent.VK_UP ||code==KeyEvent.VK_DOWN || code==KeyEvent.VK_W ||code==KeyEvent.VK_S) && engine!=null)
		{			
			road.setAccelerationVersus(0);
			
			steer(0);			

		}

	}


	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();
		if(o==resetCar)
			reset();
		
	}

	public void reset(){
		
		isProgramPaused=true;
		
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		road.reset(graphics2D);
	
		
		setCarSpeed(0);
		setSteeringAngle(0);

		isProgramPaused=false;
		
		drawRoad();
	}

	private void drawRoad()  {
		
		if(graphics2D==null)
			graphics2D=(Graphics2D) center.getGraphics();
			
		road.drawRoad(buf);
		graphics2D.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		
	}
	
	
	
	
	public void paint(Graphics g) {
		
		super.paint(g);
		//drawRoad();
		
	}


	public Road getRoad() {
		return road;
	}
	
	private void playHorn() {

		try {

			
			hornSound.makeSound();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void start() {
		
		engine=new Engine(this);
		engine.start();	
		
		isProgramPaused=false;
	}




}
