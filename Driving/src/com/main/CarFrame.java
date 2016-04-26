package com.main;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.DrawObject;
import com.Engine;
import com.Texture;
import com.main.loader.GameLoader;
import com.sound.AdvancedGameSound;
import com.sound.GameSound;

public class CarFrame extends Road implements KeyListener {

	
	private String VERSION="CarDriving 9.0.3";
	
	private JPanel center=null;
	private Graphics2D graphics2D;
	private static final int HEIGHT=500;
	private static final int WIDTH=800;

	private int back_num=0;

	private double CAR_SPEED=0;
	
	private static final int BUTTOMBORDER=100;
	private static final int UPBORDER=40;
	private static final int LEFTBORDER=0;
	private static final int RIGHTBORDER=0;
	
	public static final Color BACKGROUND_COLOR=Color.GREEN;
	
	static Texture background=null;
	
	static Texture[] carTextures=null;

	private String IMAGES_PATH="lib/";
	private Engine engine=null;

	private JPanel bottom;
	private JPanel up;
	private JLabel speedometer;
	private DecimalFormat df=new DecimalFormat("####");
	
	public Properties p;

	private JLabel forward;
		
	private boolean isUseTextures=true;
	private static JLabel steerAngle;
	private static JLabel movingDirection;
	
	private String SOUNDS_FOLDER="lib/";
	private File hornFile=null;
	private File engineFile=null;
	private AdvancedGameSound engineSound=null;
	private GameSound hornSound;
	private Date t;
	private transient BufferedImage buf;
	
	//steering angle,positive anti-clockwise
	private double delta=0.30;
	private boolean isProgramPaused=true;
	
	private String map_name=GameLoader.DEFAULT_MAP;
	

	
	 public static void main(String[] args) {
		 
		boolean skipShading=true;
		
		if(args.length==1)
			skipShading=!("+s".equals(args[0]));
		
		GameLoader gl=new GameLoader();		
		gl.setSkipShading(skipShading);
		 
		CarFrame ff=new CarFrame(gl,WIDTH,HEIGHT);
		//ff.initialize();
	
	}
	 
	 
	 private CarFrame(GameLoader gameLoader,int WITDH,int HEIGHT){

	 super( WITDH, HEIGHT);
	
	 
	 this.map_name=gameLoader.getMap();
	 this.skipShading=gameLoader.isSkipShading();	
	 
	 setTitle(VERSION);
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 setLayout(null);
	 center=new JPanel();
	 center.setBackground(BACKGROUND_COLOR);
	 center.setBounds(LEFTBORDER,UPBORDER,WIDTH,HEIGHT);
	 add(center);
	
	 
	 bottom=new JPanel();
	 bottom.setBounds(0,UPBORDER+HEIGHT,LEFTBORDER+WIDTH+RIGHTBORDER,BUTTOMBORDER);
	 add(bottom);
	 setSize(LEFTBORDER+WIDTH+RIGHTBORDER,UPBORDER+HEIGHT+BUTTOMBORDER);
	 buildUpPannel();
	 
	 addKeyListener(this);
	 
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
		
		movingDirection=new JLabel(getMovingDirection(0));
		up.add(movingDirection);
		
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

			ArrayList<File> vCarData=new ArrayList<File>();

			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("cardefault3D_")){

					vCarData.add(files[i]);

				}		
			}
			
			ArrayList<File> vCarTextures=new ArrayList<File>();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("car_texture_t")){
					
					vCarTextures.add(files[i]);
					
				}		
			}
			
			carTextures=new Texture[vCarTextures.size()];

			for(int i=0;i<vCarTextures.size();i++){
				
				File file=(File) vCarTextures.get(i);
				carTextures[i]=new Texture(ImageIO.read(file));
			}

			hornFile=new File(SOUNDS_FOLDER+"horn.wav");
			engineFile=new File(SOUNDS_FOLDER+"shortDiesel.wav");
			engineSound=new AdvancedGameSound(engineFile,-1,false);
			engineSound.filter(getEngineModulation());

			graphics2D=(Graphics2D) center.getGraphics();
			setCarSpeed(0);
			initCars(vCarData);

			hornSound = new GameSound(hornFile,true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadRoad();
	}


	private void loadRoad() {
		
		super.loadRoad(getMap_name());
		loadAutocars(new File("lib/autocars_"+getMap_name()));
	}

	private void loadProperties(){
		
		p=new Properties();
		try {
			p.load(new FileInputStream("lib/driving.properties"));
			
			if("true".equals(p.getProperty("ISUSETEXTURE")))
					isUseTextures=true;
			else
				isUseTextures=false;	
			

			if( p.getProperty("INSTRUMENT_CODE")==null)
				p.setProperty("INSTRUMENT_CODE",Integer.toString(0));

			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	
	}


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0)  {
	
		int code=arg0.getKeyCode();

		if(code==KeyEvent.VK_R) 
			reset();
		else if(code==KeyEvent.VK_UP || code==KeyEvent.VK_W)
		{	
			setAccelerationVersus(1);


		}
		else if(code==KeyEvent.VK_DOWN || code==KeyEvent.VK_S)
		{			
			setIsBraking(true);

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
			
            selectNextCar();
            
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
			FORWARD=1;
			forward.setText("(F)");
		}
		else if(code==KeyEvent.VK_M)
		{			
			FORWARD=-1;
			forward.setText("(R)");
		}
		else if(code==KeyEvent.VK_H)
		{			
			playHorn();
		
		}
		else if(code==KeyEvent.VK_Z)
		{			
			if(Road.VIEW_TYPE==Road.FRONT_VIEW)
				rotateSky(Math.PI);
			Road.VIEW_TYPE=Road.REAR_VIEW;
		   
		}
		else if(code==KeyEvent.VK_1)
		{
			 changeCamera(0);
			 drawRoad();
		}
		else if(code==KeyEvent.VK_2)
		{
			 changeCamera(1);
			 drawRoad();
		}
		else if(code==KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	private double getEngineModulation(){
		
		double ratio = 80.0/CAR_SPEED;
		
		if(ratio>5)
			return 5;
		else if(ratio<0)
			return 1;
		else
			return ratio;
	}
	
	public double getcarSpeed(){
		
		return CAR_SPEED;
	}
	
	private void setCarSpeed(double d) { 
		
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
		up(graphics2D);
		
		setCarSpeed(3.6*Math.abs(carDynamics.u));
		
		drawRoad();

	}
	
	private void steer(double angle) {
		
		setSteerAngle(angle);
	
	
	}
	
	static void setMovingAngle(double d) {
		
		DecimalFormat df=new DecimalFormat("##.##");
		steerAngle.setText(df.format(d));
		movingDirection.setText(getMovingDirection(d));

	}

	static String getMovingDirection(double d) {
		
		if(d<Math.PI/4.0 || d>Math.PI*7.0/4.0)
			return "N";
		else if(d>=Math.PI/4.0 && d<=Math.PI*3.0/4.0)
			return "W";
		else if(d>Math.PI*3.0/4.0 && d<Math.PI*5.0/4.0)
			return "S";
		else if(d>=Math.PI*5.0/4.0 && d<=Math.PI*7.0/4.0)
			return "E";
				
		return  "N";
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
		int code=arg0.getKeyCode();
		
		
		if(code==KeyEvent.VK_LEFT ||code==KeyEvent.VK_RIGHT || code==KeyEvent.VK_A ||code==KeyEvent.VK_D)
		{			
			
		
			steer(0);
			
		}
		else if(code==KeyEvent.VK_Z){
			
						
			if(Road.VIEW_TYPE==Road.REAR_VIEW)
				rotateSky(0);
			Road.VIEW_TYPE=Road.FRONT_VIEW;
			
		}
		else if((code==KeyEvent.VK_UP ||code==KeyEvent.VK_DOWN || code==KeyEvent.VK_W ||code==KeyEvent.VK_S) && engine!=null)
		{			
			setAccelerationVersus(0);
			
			steer(0);			

		}

	}
	
	private void reset(){
		
		isProgramPaused=true;
		
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		reset(graphics2D);
	
		
		setCarSpeed(0);
		setMovingAngle(0);

		isProgramPaused=false;
		
		drawRoad();
	}

	private void drawRoad()  {
		
		if(graphics2D==null)
			graphics2D=(Graphics2D) center.getGraphics();
			
		drawRoad(buf);
		graphics2D.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		//drawRoad();
		
	}
	
	private void playHorn() {

		try {

			
			hornSound.makeSound();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void start() {
		
		engine=new Engine(this);
		engine.start();	
		
		isProgramPaused=false;
	}


	public String getMap_name() {
		return map_name;
	}







}
