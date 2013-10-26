package com.main;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.RepaintManager;

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
import com.editors.Editor;







/*
 * Created on 12/apr/08
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class Road extends Shader{


	int y_edge=5+SCREEN_DISTANCE;
	int NX=2;
	int NY=80;

	
	int NXVISIBLE=NX;
	int NYVISIBLE=20;//20 orig

	int dx=0;
	int dy=3000/NYVISIBLE;//600 orig

	int ROAD_LENGHT=600;
	public static int ROAD_THICKNESS=20;

	int CAR_WIDTH=100;
	
	double TURNING_RADIUS=100;
	
	public static int FORWARD=1;
	
	public static final int EXTERNAL_CAMERA=0;
	public static final int DRIVER_CAMERA=1;
	public static int CAMERA_TYPE=EXTERNAL_CAMERA;
	
		
	int TILE_SIDE=4;
	CarData carData=null;
		
	public PolygonMesh[] meshes=new PolygonMesh[2];
	
	public PolygonMesh[] oldMeshes=new PolygonMesh[2];
	
   
	//public static String[] hexRoadColors={"888888","888888","888888","CCCCCC"};
	DrawObject[] drawObjects=null;
	DrawObject[]  oldDrawObjects=null;
	

	private JFileChooser fc;
	boolean start=true;
	

	
	private int APOSX;


	public static double turningAngle=0;
	public static double dTurningAngle=0.1;
	public static boolean steer=false;
	public static double SPACE_SCALE_FACTOR=26.0;
	public static double SPEED_SCALE=1.0/1.13;

	
	int whiteRGB=Color.WHITE.getRGB();
	
	int SELECTED_CAR=0;

	private Texture carTexture=null;
	private Date time;
	private Area totalVisibleField=null;
	
	int start_car_x=WIDTH/2-XFOCUS;
	int start_car_y=y_edge;
	
	public static Autocar[] autocars=null;
    double pi_2=Math.PI/2.0;
    
	int PARTIAL_MOVZ=0;
    boolean initMOVZ=true;
    
    Point3D terrainNormal=null;
    double[][] rot=new double[3][3];
		 
	public Road(){}

	public Road(int WITDH,int HEIGHT){

		dx=WITDH/(NXVISIBLE-1);
		//dy=HEIGHT/(NYVISIBLE-1);

		this.HEIGHT=HEIGHT;
		this.WIDTH=WITDH;
		YFOCUS=HEIGHT/2;
		XFOCUS=WITDH/2;
		loadRoad();
		
		totalVisibleField=buildVisibileArea(SCREEN_DISTANCE,NYVISIBLE*dy+SCREEN_DISTANCE);
		
		
	}


	public void loadRoad() {

		observerPoint=new Point3D(0,0,0);
		
		lightPoint=new LightSource(
				new Point3D(0,0,0),
				new Point3D(0,1,0)
		);
		
	 
		try {
			File file=new File("lib/landscape_default");
			
			loadPointsFromFile(file,0);		
			loadPointsFromFile(file,1);	
			loadObjectsFromFile(file);			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		calculateShadowCosines();
		
		roadZbuffer=new ZBuffer[WIDTH*HEIGHT];
		lightZbuffer=new ZBuffer[WIDTH*HEIGHT];
	
		buildNewZBuffers();	    
		
		
		//calculateShadowMap();
		calculateShadowVolumes(drawObjects);
		
		loadAutocars(new File("lib/autocars"));
		
		//showMemoryUsage();
		//buildDefaultRoad(); 
		

	}

	public void calculateShadowMap() {

		isShadowMap=true;
		
		int index=0;
		
		for (int ii = 0; ii < 2; ii++) {
			
			int size=meshes[index].polygonData.size();

			for(int j=0;j<size;j++){

				LineData ld=(LineData) meshes[index].polygonData.elementAt(j);

				Polygon3D p3D=buildLightTransformedPolygon3D(ld,ii);


				decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),CarFrame.worldTextures[p3D.getIndex()],lightZbuffer);


			}
			
		}

		for(int ii=0;ii<drawObjects.length;ii++){

			DrawObject dro=drawObjects[ii];

			CubicMesh cm = (CubicMesh) dro.getMesh();
	
			
			Point3D point000=calculateLightTransformedPoint(cm.point000,true);				

			Point3D point011=calculateLightTransformedPoint(cm.point011,true);

			Point3D point001=calculateLightTransformedPoint(cm.point001,true);
			

			Point3D xVersor=cm.getXAxis();
			Point3D yVersor=cm.getYAxis();
			
			Point3D zVersor=new Point3D(0,0,1);
			Point3D zMinusVersor=new Point3D(0,0,-1);


			//////
			
			if(VIEW_TYPE==REAR_VIEW){
				///???
				yVersor=new Point3D(-yVersor.x,-yVersor.y,yVersor.z);
				xVersor=new Point3D(-xVersor.x,-xVersor.y,xVersor.z);
			}
			int polSize=cm.polygonData.size();	

			Texture texture = CarFrame.objectTextures[dro.getIndex()];

			for(int i=0;i<polSize;i++){


				LineData ld=cm.polygonData.elementAt(i);

				//Point3D normal= cm.normals.elementAt(i).clone();


				int due=(int)(255-i%15);
				Color col=new Color(due,0,0);

				Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld);
				calculateLightTransformedPolygon(polRotate,true);
				polRotate.setShadowCosin(ld.getShadowCosin());

				//int face=findBoxFace(normal,xVersor0,yVersor0,zVersor);
				int face=cm.boxFaces[i];





				decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,texture,lightZbuffer);
				
			}	


		}
		
		isShadowMap=false;
		
	}



	private void loadCar(int k) {

			carData=loadCarFromFile(new File("lib/cardefault3D_"+k));
			CAR_WIDTH=carData.carMesh.deltaX2-carData.carMesh.deltaY;
			carData.getCarMesh().translate(WIDTH/2-CAR_WIDTH/2-XFOCUS,y_edge,-YFOCUS);


	}
	
	public void initCar(){
		
			initCar(0);
		
		
	}
	
	public void selectNextCar() {
		
		SELECTED_CAR+=1;
		if(!(new File("lib/cardefault3D_"+SELECTED_CAR)).exists())
			SELECTED_CAR=0;
	
			initCar(SELECTED_CAR);
		
		
	}

	public void initCar(int k) {

		loadCar(k);
		carTexture =null;
		
		try {
			carTexture=CarFrame.carTextures[k];
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*carZbuffer=new ZBuffer[WIDTH*HEIGHT];
		for(int i=0;i<WIDTH*HEIGHT;i++){


			carZbuffer[i]=new ZBuffer(greenRgb,0);

		}
		buildCar(0);
	    */
	}

	public void buildCar(double directionAngle){
		
		Point3D steeringCenter=new Point3D(start_car_x, start_car_y,-YFOCUS);

		//putting the car right in front of the view point
		CubicMesh cm = carData.getCarMesh().clone();
        //fake steering: eliminate?
		//cm.rotate(steeringCenter.x,steeringCenter.y,Math.cos(directionAngle),Math.sin(directionAngle));		
		
		Point3D point000=cm.point000;				

		Point3D point011=cm.point011;

		Point3D point001=cm.point001;
		

		Point3D xVersor=cm.getXAxis();
		Point3D yVersor=cm.getYAxis();
		
		Point3D zVersor=new Point3D(0,0,1);
		Point3D zMinusVersor=new Point3D(0,0,-1);
		
		//////
		
		if(VIEW_TYPE==REAR_VIEW){
			///???
			yVersor=new Point3D(-yVersor.x,-yVersor.y,yVersor.z);
			xVersor=new Point3D(-xVersor.x,-xVersor.y,xVersor.z);
		}
		
		double dx=WIDTH/2-CAR_WIDTH/2-XFOCUS;
		double dy=y_edge;
		double dz=-YFOCUS;
		
	    //apply terrain following
        if(terrainNormal!=null){
        	
        	double a=terrainNormal.x;
        	double b=terrainNormal.y;
        	double c=terrainNormal.z;
        	
        	double i_v1=1.0/Math.sqrt(a*a+b*b+c*c);
        	double v2=Math.sqrt(a*a+c*c);
        	
        	
        	
        	rot[0][0]=c/v2;
			rot[0][1]=-b*a*i_v1/v2;
			rot[0][2]=a*i_v1;
			rot[1][0]=0;
			rot[1][1]=v2*i_v1;
			rot[1][2]=b*i_v1;
			rot[2][0]=-a/v2;
			rot[2][1]=-b*c*i_v1/v2;
			rot[2][2]=c*i_v1;
        	
			point000=rotoTranslate(rot,point000,dx,dy,dz);
        	point011=rotoTranslate(rot,point011,dx,dy,dz);
        	point001=rotoTranslate(rot,point001,dx,dy,dz);
        	
        	xVersor=rotate(rot,xVersor);
        	yVersor=rotate(rot,yVersor);
        	zVersor=rotate(rot,zVersor);
        	zMinusVersor=rotate(rot,zMinusVersor);
        	
        	
        } 
        
        
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){
			
	
			
			int due=(int)(255-i%15);			
			Color col=new Color(due,0,0);
			
			LineData ld=cm.polygonData.elementAt(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld);
			polRotate.setShadowCosin(ld.getShadowCosin());
			
		
			int face=cm.boxFaces[i];
			
		    //apply terrain following
	         if(terrainNormal!=null){
	        	
	        	rototranslate(polRotate,rot,dx,dy,dz);
	        	
	        }   
			
			
			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,carTexture,roadZbuffer);
			
          
				
		}
		

	
	}




	private Point3D rotoTranslate(double[][] rot, Point3D point, double dx,
			
			double dy, double dz) {
		
		double[] p={point.x-dx,point.y-dy,point.z-dz};
		
	
		double[] res=rotate(rot,p);
		
		return new Point3D(res[0]+dx,res[1]+dy,res[2]+dz);
	}
	
	double[] pRot=new double[3];

	private void rototranslate(Polygon3D polRotate, double[][] rot,double dx, double dy,double dz) {

    	for(int l=0;l<polRotate.npoints;l++){
    		
    		pRot[0]=polRotate.xpoints[l]-dx;
    		pRot[1]=polRotate.ypoints[l]-dy;
    		pRot[2]=polRotate.zpoints[l]-dz;
    		
            pRot=rotate(rot,pRot);    
    		
            polRotate.xpoints[l]=(int)(pRot[0]+dx);
            polRotate.ypoints[l]=(int)(pRot[1]+dy);
            polRotate.zpoints[l]=(int)(pRot[2]+dz);
 
    	}
		
	}

	private Point3D rotate(double[][] rot, Point3D point) {
		
		pRot[0]=point.x;
		pRot[1]=point.y;
		pRot[2]=point.z;
		
		double[] res=new double[3];
		
		for (int i = 0; i < res.length; i++) {
			
			for (int j = 0; j < res.length; j++) {
				res[i]+=rot[i][j]*pRot[j];
			}
			
		}
		
		return new Point3D(res[0],res[1],res[2]);
	}

	private double[]  rotate(double[][] rot,  double[] p) {
		
		
		double[] res=new double[3];
		
		for (int i = 0;i < res.length; i++) {
			
			for (int j = 0; j < res.length; j++) {
				res[i]+=rot[i][j]*p[j];
			}
			
		}
	    
		return res;
	}

	public void drawRoad(BufferedImage buf){


		//cleanZBuffer();
		drawSky();

		
		MOVZ=0;

     
	    int TRANSZ=PARTIAL_MOVZ;
	    boolean start_max_calculus=true;
		
		for(int index=0;index<2;index++){
	
			PolygonMesh mesh=meshes[index];
			
			int size=mesh.polygonData.size();
	
			for(int j=0;j<size;j++){
				
				
	
				LineData ld=(LineData) meshes[index].polygonData.elementAt(j);
	
				Polygon3D p3D=buildTransformedPolygon3D(ld,mesh.points);
	
					if(p3D.contains(start_car_x,start_car_y)){
	
						int zz=(int)interpolate(start_car_x,start_car_y,p3D);
						
						
						//find max possible z altitude
						if(initMOVZ){							
					
							TRANSZ=zz;
							PARTIAL_MOVZ=zz;
							
							initMOVZ=false;
							start_max_calculus=false;
							terrainNormal=Polygon3D.findNormal(p3D);
						} 						
						else if(zz<=PARTIAL_MOVZ+ROAD_THICKNESS){
							
							if(start_max_calculus){
								TRANSZ=zz;
								start_max_calculus=false;
								terrainNormal=Polygon3D.findNormal(p3D);
							}
							else if(zz>=TRANSZ){
								TRANSZ=zz;
								terrainNormal=Polygon3D.findNormal(p3D);
							}	
							
						}	
						//break;
	
					}
					
					if(autocars!=null){
						
						
						for (int i = 0; i < autocars.length; i++) {
							
							Point3D transformedCenter=buildTransformedPoint(autocars[i].center);
						
							if(p3D.contains(transformedCenter.x,transformedCenter.y)){
								
							    int posz=(int)interpolate(transformedCenter.x,transformedCenter.y,p3D);
								
								autocars[i].center.z=autocars[i].car_height*0.5+
										(posz);
								
								//if(i==1)
								//	System.out.println(autocars[i].center.x+","+autocars[i].center.y+","+autocars[i].center.z);
							}
						}
						
					}
	
			}
			
		}
			//changing altitutude with the movements		
		    if(TRANSZ>=PARTIAL_MOVZ-ROAD_THICKNESS)
		    	PARTIAL_MOVZ=TRANSZ;
		    else //minus to simulate gravitational fall
		    	PARTIAL_MOVZ=PARTIAL_MOVZ-ROAD_THICKNESS;
		    
			MOVZ=-(PARTIAL_MOVZ+YFOCUS);
			
			
			drawObjects(drawObjects,totalVisibleField,roadZbuffer);

			if(autocars!=null){
				
				drawAutocars(autocars,totalVisibleField,roadZbuffer);
				
			}
			
			for(int index=0;index<2;index++){
				
				PolygonMesh mesh=meshes[index];
				int size=mesh.polygonData.size();
			
				for(int j=0;j<size;j++){
					
				
					LineData ld=(LineData) mesh.polygonData.elementAt(j);
	
					Polygon3D p3D=buildTransformedPolygon3D(ld,mesh.points);
	
	
					if(!p3D.clipPolygonToArea2D(totalVisibleField).isEmpty()){
							decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),CarFrame.worldTextures[p3D.getIndex()],roadZbuffer);
				
							
						    if(index==1){
						    	
						    	//build road polgyons
						    	
						    	Polygon3D[] polygons=buildAdditionalRoadPolygons(p3D);
						    	
						    	for (int i = 0; i < polygons.length; i++) {
										decomposeClippedPolygonIntoZBuffer(polygons[i],Color.DARK_GRAY,null,roadZbuffer);
								}
						    	
						    }
					}		
							

					
				} 
		
			}


		drawCar();

		
		calculateStencilBuffer();
		buildScreen(buf); 
		//showMemoryUsage();

	}
	

	

	
	public static Polygon3D[] buildAdditionalRoadPolygons(Polygon3D p3d) {
		
		Polygon3D[] pols=new Polygon3D[p3d.npoints];
		
		for(int i=0;i<p3d.npoints;i++){
			
			pols[i]=new Polygon3D();
			
			pols[i].addPoint(p3d.xpoints[i],p3d.ypoints[i],p3d.zpoints[i]);
			pols[i].addPoint(p3d.xpoints[i],p3d.ypoints[i],p3d.zpoints[i]-ROAD_THICKNESS);
			pols[i].addPoint(p3d.xpoints[(i+1)%p3d.npoints],p3d.ypoints[(i+1)%p3d.npoints],p3d.zpoints[(i+1)%p3d.npoints]-ROAD_THICKNESS);
			pols[i].addPoint(p3d.xpoints[(i+1)%p3d.npoints],p3d.ypoints[(i+1)%p3d.npoints],p3d.zpoints[(i+1)%p3d.npoints]);

			
		}
		
		return pols;
	}

	private Polygon3D buildLightTransformedPolygon3D(LineData ld,int index) {
		

		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];

		PolygonMesh mesh=meshes[index];
		
		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) mesh.points[num];

			Point4D p_light=calculateLightTransformedPoint(p,true);
				
			cxr[i]=(int)(p_light.x);
			cyr[i]=(int)(p_light.y);
			czr[i]=(int)(p_light.z);

		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());
		
        return p3dr;
	}
	



	private void drawCar() {
	
		if(VIEW_TYPE==REAR_VIEW || CAMERA_TYPE==DRIVER_CAMERA)
			return;
		if(!steer){

			buildCar(0);
		
		}
		else {
		
			if(turningAngle==0){
				
				
				return;
			}
			else
				buildCar(-dTurningAngle*Math.signum(turningAngle));
		
		} 

		if(turningAngle!=0 && !steer )
		{   turningAngle=0;
			buildCar(0);
			CarFrame.setSteeringAngle();
		    
		}
		
	}



	private Area buildVisibileArea(int y1, int y2) {
		int[] cx=new int[4];
		int[] cy=new int[4];



		cx[0]=limVisibileL(y1);
		cy[0]=y1;
		cx[1]=limVisibileR(y1);
		cy[1]=y1;
		cx[2]=limVisibileR(y2);
		cy[2]=y2;
		cx[3]=limVisibileL(y2);
		cy[3]=y2;

		Polygon p=new Polygon(cx,cy,4);

		Area a = new Area(p);

		//System.out.println(Polygon3D.fromAreaToPolygon2D(a));

		return a;
	}

	private int limVisibileL(double y1) {

		return (int) ((y1)*(-XFOCUS)/SCREEN_DISTANCE);
	}

	private int limVisibileR(double y1) {

		return (int) ((y1)*(WIDTH-XFOCUS)/SCREEN_DISTANCE);
	}





	private boolean isVisible(Rectangle rect, Polygon3D translatedPolygon) {
		
		int maxY=rect.y+rect.height;
		
		for(int i=0;i<translatedPolygon.npoints;i++){
			
			if(translatedPolygon.ypoints[0]<maxY && translatedPolygon.ypoints[0]>rect.y)
				return true;
		}
		
		return false;
	}

	public void rotate(){
		
		if(turningAngle==0){
			
			
			return;
		}
		
		double dTeta=turningAngle/(Math.PI*2);

		APOSX+=(int) (-dTeta*CarFrame.background.getWidth());
			
	
		double xo=POSX+viewDirectionCos;
		//if(turningAngle*FORWARD>0) xo=POSX+WIDTH-XFOCUS;
		//double yo=POSY+y_edge;
		
		double yo=POSY+viewDirectionSin;
		
		/*if(CAMERA_TYPE==DRIVER_CAMERA){
			
			yo=POSY-SCREEN_DISTANCE;
		}*/
		
		double ct=Math.cos(-turningAngle);
		double st=Math.sin(-turningAngle);	
		
		if(turningAngle*FORWARD>0)
			st=-st;
		
		double obsTeta=getViewDirection()-turningAngle;
		
		setViewDirection(obsTeta);
		

		
		double DPOSX=((POSX-xo)*ct+(POSY-yo)*st+xo)-POSX;
		double DPOSY=(-(POSX-xo)*st+(POSY-yo)*ct+yo)-POSY;
		
		
		POSX+=DPOSX;
		POSY+=DPOSY;
				
		/*
		 * int size=points.size();
		 * 
		 * for(int j=0;j<size;j++){


		    Point4D p=(Point4D) points.elementAt(j);



				double x=p.x;
				double y=p.y;
				

				p.x=xo+(x-xo)*ct-(y-yo)*st;
				p.y=yo+(y-yo)*ct+(x-xo)*st;
		


		}
		
		//rotate objects

		for(int i=0;i<drawObjects.length;i++){

			DrawObject dro=drawObjects[i];

			double x=dro.x;
			double y=dro.y;	

			dro.x=xo+(x-xo)*ct-(y-yo)*st;
			dro.y=yo+(y-yo)*ct+(x-xo)*st;

			rotatePolygon(dro,xo,yo,ct,st);
			rotateMesh((CubicMesh) dro.getMesh(),xo,yo,ct,st);

		}
		
		//rotate light
				
		double obx=lightPoint.position.x;
		double oby=lightPoint.position.y;
		

		lightPoint.position.x=xo+(obx-xo)*ct-(oby-yo)*st;
		lightPoint.position.y=yo+(oby-yo)*ct+(obx-xo)*st;
		
		double ayx=lightPoint.yAxis.x;
		double ayy=lightPoint.yAxis.y;
		
		
		lightPoint.yAxis.x=ayx*ct-ayy*st;
		lightPoint.yAxis.y=ayy*ct+ayx*st;
		
		lightPoint.calculateLightAxes();
		
		//rotate shadow volumes
		
		for (int i = 0; i < shadowVolumes.length; i++) {
	
			for (int j = 0; j < shadowVolumes[i].allPolygons.size(); j++) {
				Polygon3D sv = (Polygon3D) shadowVolumes[i].allPolygons.elementAt(j);
				
				rotatePolygon(sv,xo,yo,ct,st);
				
			}
			
			
			
		}*/
 
	}



	public void rotateSky(double rotationAngle){
		APOSX+=(int) (-rotationAngle/(Math.PI*2)*CarFrame.background.getWidth());
	}
	
	public void drawSky() {

		//g2.setColor(Color.BLUE);
		//g2.fillRect(0,0,WIDTH,HEIGHT-YFOCUS);

		int dw=CarFrame.background.getWidth();
		int dh=CarFrame.HEIGHT-YFOCUS;
		
		int height=CarFrame.background.getHeight();
		
		//System.out.println(APOSX);
		for(int i=0;i<dw;i++){

			int i_set=(i+APOSX)%dw;
			if(i_set<0) i_set=i_set+dw;

			if(i_set>=CarFrame.WIDTH)
				continue;
			
			
			
			for(int j=height-dh;j<height;j++){

				int tot=(dh+j-height)*WIDTH+i_set;
									
				int rgb=CarFrame.background.getRGB(i,j);
				roadZbuffer[tot].setRgbColor(rgb);
				

			}
		}	

	}

	public void reset(Graphics2D g2) {
		
		CarFrame.CAR_SPEED=0;
		g2.setColor(CarFrame.BACKGROUND_COLOR);
		g2.fillRect(0,YFOCUS,WIDTH,HEIGHT-YFOCUS);
		Road.turningAngle=0;
		steer=false;
		POSX=0;
		POSY=0;
		APOSX=0;
		
		//loadRoad();
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		resetRoadData(0);
		drawObjects=DrawObject.cloneObjectsArray(oldDrawObjects);
		
		lightPoint=new LightSource(
				new Point3D(0,0,0),
				new Point3D(0,1,0)
				);
		
		setViewDirection(0);
	}



	public void up(Graphics2D graphics2D) {

        
		if(!steer){

			
			POSY=(int) (POSY+FORWARD*CarFrame.CAR_SPEED*SPEED_SCALE*viewDirectionCos);
			POSX=(int) (POSX-FORWARD*CarFrame.CAR_SPEED*SPEED_SCALE*viewDirectionSin);
			//debug
			//if(POSY>4000)
           	//System.out.println("1-"+(System.currentTimeMillis()-time.getTime()));
		}
		else {
			rotate();
	
		} 


	}
	public void start(){
		//for debug
		//time=new Date();

	}
	

	public void down(Graphics2D graphics2D) {

		if(POSY>0)
			POSY=POSY-dy;


	}

	
	public void loadPointsFromFile(File file,int index){

		meshes[index]=new PolygonMesh();		

		

		try {
			
			BufferedReader br=new BufferedReader(new FileReader(file));
			
			boolean read=false;

			String str=null;
			int rows=0;
			
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf(Editor.TAG[index])>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;

				if(str.startsWith("P="))
					buildPoints(meshes[index],str.substring(2),index);
				else if(str.startsWith("L="))
					buildLines(meshes[index],str.substring(2),index);


			}
            br.close();

			
			oldMeshes[index]=cloneMesh(meshes[index]);
		
			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	private PolygonMesh cloneMesh(PolygonMesh polygonMesh) {
		
		return polygonMesh.clone();
	}

	private void calculateShadowCosines() {
	
		
		for(int i=0;i<drawObjects.length;i++){

			DrawObject dro=drawObjects[i];
			calculateShadowCosines(dro);
			
	
		}	
		
	}


	public void buildPoints(PolygonMesh mesh, String str,int index) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");
		
		Vector vPoints=new Vector();

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			Point4D p=new Point4D();
			
			
			//translate world in the point (XFOCUS,SCREEN_DISTANCE,YFOCUS)
			
			p.x=Double.parseDouble(vals[0])-XFOCUS;
			p.y=Double.parseDouble(vals[1])+SCREEN_DISTANCE;
			p.z=Double.parseDouble(vals[2])-YFOCUS;
			
			if (index==1)
				p.z+=ROAD_THICKNESS;

			vPoints.add(p);
		}

		mesh.setPoints(vPoints);
		
	}
	
	public String decomposeLineData(LineData ld) {

		String str="";
		
		str+="T"+ld.texture_index;
		str+=",C"+ld.getHexColor();
		
		int size=ld.size();

		for(int j=0;j<size;j++){
			
			
			
			str+=",";
			
			str+=ld.getIndex(j);

		}

		return str;
	}
	
	public void buildLines(PolygonMesh meshes, String str,int index) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			LineData ld=new LineData();

			ld.texture_index=Integer.parseInt(vals[0].substring(1));
			
			ld.hexColor=vals[1].substring(1);
			
			for(int i=2;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			meshes.polygonData.add(ld);
		}




	}


	private Point4D[][] cloneRoadData(Point4D[][] roadData2) {

		Point4D[][] newRoadData=new Point4D[NY][NX];


		for(int j=0;j<NY;j++)
			for(int i=0;i<NX;i++)
				for(int k=0;k<3;k++)
					newRoadData[j][i]=(Point4D) roadData2[j][i].clone();

		return newRoadData;
	}
	
	public Vector clonePoints(Vector oldPoints) {
		
		Vector newPoints=new Vector();
		
		for(int i=0;i<oldPoints.size();i++){
			
			Point3D p=(Point3D) oldPoints.elementAt(i);
			newPoints.add(p.clone());
		}
		
		return newPoints;
	}
	
	public Vector cloneLineData(Vector oldLines) {
		
		Vector newLineData=new Vector();
		
		for(int i=0;i<oldLines.size();i++){

			LineData ld=(LineData) oldLines.elementAt(i);
		
			
			newLineData.add(ld.clone());
		
			
		}
		return newLineData;
	}
	
	private void resetRoadData(int index) {
		
		meshes[index]=cloneMesh(oldMeshes[index]);
	
		
	}


	public static  CarData loadCarFromFile(File file) {
		
		
		CarData carData=new CarData();


		Vector <Point3D>points = new Vector <Point3D>();
		Vector <LineData>lines = new Vector <LineData>();
		

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("P="))
					PolygonMesh.buildPoints(points,str.substring(2));
				else if(str.startsWith("L="))
					PolygonMesh.buildLines(lines,str.substring(2));


			}
			
			PolygonMesh pm=new PolygonMesh(points,lines);
		    
	        
			carData.carMesh=CubicMesh.buildCubicMesh(pm);
			
			
		
			
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return carData;
		
	}
	
	
	
	public void loadObjectsFromFile(File file){
		
		Vector vdrawObjects=new Vector();
		
	
	
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));

			boolean read=false;
			
			String str=null;
			int rows=0;
			while((str=br.readLine())!=null ){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf("objects")>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;
				
				DrawObject dro=buildDrawObject(str);
				
				buildRectanglePolygons(dro.getPolygons(),dro.x,dro.y,dro.z,dro.dx,dro.dy,dro.dz);
								
				vdrawObjects.add(dro);
				
				CubicMesh cm=(CubicMesh) CarFrame.object3D[dro.getIndex()].clone();
				
				Point3D point = cm.point000;
				
				double dx=-point.x+dro.x;
				double dy=-point.y+dro.y;
				double dz=-point.z+dro.z;
				
				cm.translate(dx,dy,dz);
				
				dro.setMesh(cm);
				

			}
			br.close();
			
			drawObjects=new DrawObject[vdrawObjects.size()];
			
			
			for (int i = 0; i < vdrawObjects.size(); i++) {
				drawObjects[i]=(DrawObject) vdrawObjects.elementAt(i);
			}
			
			oldDrawObjects=DrawObject.cloneObjectsArray(drawObjects);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}



	private DrawObject buildDrawObject(String str) {
		DrawObject dro=new DrawObject();

		StringTokenizer tok=new StringTokenizer(str,"_");
		
		//translate world in the point (XFOCUS,SCREEN_DISTANCE,YFOCUS)
		dro.x=Double.parseDouble(tok.nextToken())-XFOCUS;
		dro.y=Double.parseDouble(tok.nextToken())+SCREEN_DISTANCE;
		dro.z=Double.parseDouble(tok.nextToken())-YFOCUS;
		
		dro.dx=Double.parseDouble(tok.nextToken());
		dro.dy=Double.parseDouble(tok.nextToken());
		dro.dz=Double.parseDouble(tok.nextToken());
		dro.index=Integer.parseInt(tok.nextToken());
		dro.hexColor=tok.nextToken();
		return dro;
	}

	private Point4D[] buildRow(String string) {
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

	public static String decomposeColor(Color tcc) {
		return addZeros(tcc.getRed())+","+addZeros(tcc.getGreen())+","+addZeros(tcc.getBlue());

	}

	public static String addZeros(int numb){
		String newStr=""+numb;
		newStr="00"+newStr;
		newStr=newStr.substring(newStr.length()-3,newStr.length());

		return newStr;

	}

	public static Color buildColor(String colorString) {

		if(colorString==null) return null;
		Color tcc=null;
		String[] colorComponents = colorString.split(",");
		tcc=new Color(Integer.parseInt(colorComponents[0]),Integer.parseInt(colorComponents[1]),Integer.parseInt(colorComponents[2]));
		return tcc;

	}




	/*public static void main(String[] args) {
		Road road=new Road(500,600);
		//road.saveRoad();
		//road.loadRoadFromFile();
		road.loadRoad();

	}*/





	public void changeCamera(int camera_type) {
		
		if(camera_type==EXTERNAL_CAMERA){
			
			CAMERA_TYPE=EXTERNAL_CAMERA;
			POSY=POSY-(int) SCREEN_DISTANCE;
			YFOCUS=HEIGHT/2;
		}
		else if(camera_type==DRIVER_CAMERA){
			
			CAMERA_TYPE=DRIVER_CAMERA;
			POSY= POSY+(int)SCREEN_DISTANCE;
			YFOCUS=HEIGHT/5;
			
		}	
	
		
	}

	private void drawAutocars(Autocar[] autocars, Area totalVisibleField2,
			ZBuffer[] roadZbuffer) {
		
		for (int i = 0; i < autocars.length; i++) {
			
			
			drawAutocar(autocars[i],roadZbuffer);
			
		}
		
	}

	private void drawAutocar(Autocar autocar, ZBuffer[] roadZbuffer) {
		
		CubicMesh cm=autocar.carData.carMesh.clone();
		
		cm.translate(autocar.center.x-autocar.car_width*0.5,autocar.center.y-autocar.car_length*0.5,autocar.center.z-autocar.car_height*0.5);
		cm.rotate(autocar.center.x,autocar.center.y,Math.cos(autocar.fi-pi_2),Math.sin(autocar.fi-pi_2));
		decomposeCubicMesh(cm,autocar.texture,roadZbuffer);
		
		

	}

	private void loadAutocars(File file) {
	

		if(file.exists())	{	
			autocars=Autocar.buildAutocars(file);
			
			for (int i = 0; i < autocars.length; i++) {
				
				Autocar autoCar=autocars[i];
				
				autoCar.texture=CarFrame.carTextures[autoCar.car_type_index];
								
				autoCar.setRoad(this);
				
				//translate all:
				
				autoCar.center.x=autoCar.center.x-XFOCUS;
				autoCar.center.y=autoCar.center.y+SCREEN_DISTANCE;
				autoCar.center.z=autoCar.center.z+autoCar.car_height*0.5-YFOCUS;
				
				for (int j = 0; j < autoCar.car_road.length; j++) {
					
					
					autoCar.car_road[j].x=autoCar.car_road[j].x-XFOCUS;
					autoCar.car_road[j].y=autoCar.car_road[j].y+SCREEN_DISTANCE;
					autoCar.car_road[j].z=autoCar.car_road[j].z-YFOCUS;
					
				}
				
				autocars[i].start();
				
			}
			
		}
		
	}
	

	

}
