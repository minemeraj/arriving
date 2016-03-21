package com.main;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;

import com.CubicMesh;
import com.DrawObject;
import com.Engine;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Rib;
import com.SPLine;
import com.Texture;
import com.ZBuffer;
import com.editors.Editor;
import com.editors.EditorData;
import com.editors.road.RoadEditor;


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
	int NYVISIBLE=40;//20 orig

	int dx=0;
	int dy=3000/NYVISIBLE;//600 orig

	int ROAD_LENGHT=600;
	public static int ROAD_THICKNESS=22;

	int CAR_WIDTH=100;
	int CAR_LENGTH=100;
	
	public static double TURNING_RADIUS=700;
	
	public static int FORWARD=1;
	
	public static final int EXTERNAL_CAMERA=0;
	public static final int DRIVER_CAMERA=1;
	
	public static int CAMERA_TYPE=EXTERNAL_CAMERA;
	
		
	int TILE_SIDE=4;
	CarData[] carData=null;
	ShadowVolume[] carShadowVolume=null;
		
	public PolygonMesh[] meshes=new PolygonMesh[2];
	
	public PolygonMesh[] oldMeshes=new PolygonMesh[2];
	
   
	//public static String[] hexRoadColors={"888888","888888","888888","CCCCCC"};
	DrawObject[] drawObjects=null;
	DrawObject[]  oldDrawObjects=null;
	

	private JFileChooser fc;
	boolean start=true;

	public static boolean steer=false;
	public static double SPACE_SCALE_FACTOR=60.0;
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
    double i_2pi=1.0/(Math.PI*2.0);
    
	int PARTIAL_MOVZ=0;
    boolean initMOVZ=true;
        
    private double rearAngle;
    
    double[][] carRot=new double[3][3];
    double[][] autocarRot=new double[3][3];
    double[][] autocarRo=new double[3][3];
    double[][] autocarMinusRo=new double[3][3];
    
    Point3D carTerrainNormal=null; 
    Point3D[] autocarTerrainNormal=null;
    
    
    ShadowVolume[] autocarShadowVolume=null;
    
	public CarDynamics carDynamics=null;	
	public CarFrame carFrame=null;
	
	public static double WATER_LEVEL=0;
	
	public Vector splines=null;
	
	public static final int ROAD_INDEX0 =0;
	public static final int ROAD_INDEX1 =1;
	
	public static final int EMPTY_LEVEL = -1;	
	public static int GROUND_LEVEL=0;
	public static int ROAD_LEVEL=1;
	public static int OBJECT_LEVEL=2;


	public Road(int WITDH,int HEIGHT, CarFrame carFrame){
		
		this.carFrame=carFrame;

		dx=WITDH/(NXVISIBLE-1);
		//dy=HEIGHT/(NYVISIBLE-1);

		this.HEIGHT=HEIGHT;
		this.WIDTH=WITDH;
		YFOCUS=HEIGHT/2;
		XFOCUS=WITDH/2;
		loadRoad();
		totalVisibleField=buildVisibileArea(SCREEN_DISTANCE,NYVISIBLE*dy+SCREEN_DISTANCE);
		
		WATER_LEVEL=-HEIGHT/2-10;
	}


	public void loadRoad() {

		observerPoint=new Point3D(0,0,0);
		
		lightPoint=new LightSource(
				new Point3D(0,0,0),
				new Point3D(0,1,0)
		);
		
	 
		try {
			
			EditorData.initialize();
			
			File file=new File("lib/landscape_"+carFrame.map_name);
			
			loadPointsFromFile(file,RoadEditor.TERRAIN_INDEX);		
			//loadPointsFromFile(file,1);
			loadSPLinesFromFile(file);
			
			
			loadObjectsFromFile(file);			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		calculateShadowCosines();
		
		roadZbuffer=new ZBuffer(WIDTH*HEIGHT);
		lightZbuffer=new ZBuffer(WIDTH*HEIGHT);
	
		buildNewZBuffers();	    
		
		
		if(!carFrame.skipShading)
			calculateShadowVolumes(drawObjects);
		
		initialiazeCarDynamics();
		
		loadAutocars(new File("lib/autocars_"+carFrame.map_name));
		
		//showMemoryUsage();
		//buildDefaultRoad(); 
		

	}
	



	/**
	 * SCALING VALUES:
	 * 
	 * L=2.6 METRES =160 px
	 * REAL TIME dt=0.05 sec.
	 * 
	 */
	private void initialiazeCarDynamics() {
		
		carDynamics=new CarDynamics(1000,1.2,1.4,1,1,1,0,1680,100000,100000);
		carDynamics.setAerodynamics(1.3, 1.8, 0.35);
		carDynamics.setForces(3000, 3000);
		carDynamics.setInitvalues(0, 0, 0, 0);
		
			
	}

	/**
	 * Unused method
	 * 
	 */
	public void calculateShadowMap() {

		isShadowMap=true;
		
		int index=0;
		
		for (int ii = 0; ii < 2; ii++) {
			
			int size=meshes[index].polygonData.size();

			for(int j=0;j<size;j++){

				LineData ld=(LineData) meshes[index].polygonData.elementAt(j);

				Polygon3D p3D=buildLightTransformedPolygon3D(ld,ii);


				decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),EditorData.worldTextures[p3D.getIndex()],lightZbuffer,-1);


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

			Texture texture = EditorData.objectTextures[dro.getIndex()];

			for(int i=0;i<polSize;i++){


				LineData ld=cm.polygonData.elementAt(i);

				//Point3D normal= cm.normals.elementAt(i).clone();


				int due=(int)(255-i%15);
				Color col=new Color(due,0,0);

				Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld,cm.getLevel());
				calculateLightTransformedPolygon(polRotate,true);
				polRotate.setShadowCosin(ld.getShadowCosin());

				//int face=findBoxFace(normal,xVersor0,yVersor0,zVersor);
				int face=cm.boxFaces[i];





				decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,texture,lightZbuffer);
				
			}	


		}
		
		isShadowMap=false;
		
	}



	private void loadCars(Vector vCarData) {
		
		
		carData=new CarData[vCarData.size()];
		
		carShadowVolume=new ShadowVolume[vCarData.size()] ; 
		
		for (int i = 0; i< vCarData.size(); i++) {
			File file = (File) vCarData.elementAt(i);
			
			carData[i]=loadCarFromFile(new File("lib/cardefault3D_"+i));
			CAR_WIDTH=carData[i].carMesh.deltaX2-carData[i].carMesh.deltaX;
			CAR_LENGTH=carData[i].carMesh.deltaY2-carData[i].carMesh.deltaY;
			carData[i].getCarMesh().translate(WIDTH/2-CAR_WIDTH/2-XFOCUS,y_edge,-YFOCUS);

			if(!carFrame.skipShading){
				carShadowVolume[i]=initShadowVolume(carData[i].carMesh);
			}
			
		}

		

		

	}

	
	public void selectNextCar() {
		
		
		if(SELECTED_CAR+1<carData.length)
			SELECTED_CAR+=1;
		else
			SELECTED_CAR=0;
		
		carTexture=CarFrame.carTextures[SELECTED_CAR];
		
	}

	public void initCars(Vector vCarData) {
		
		SELECTED_CAR=0;

		loadCars(vCarData);
		
		carTexture =null;
		
		
		carTexture=CarFrame.carTextures[SELECTED_CAR];

		/*carZbuffer=new ZBuffer[WIDTH*HEIGHT];
		for(int i=0;i<WIDTH*HEIGHT;i++){


			carZbuffer[i]=new ZBuffer(greenRgb,0);

		}
		buildCar(0);
	    */
	}

	public void buildCar(double directionAngle){
		
		//Point3D steeringCenter=new Point3D(start_car_x, start_car_y,-YFOCUS);

		//putting the car right in front of the view point
		CubicMesh cm = carData[SELECTED_CAR].getCarMesh().clone();
        //fake steering: eliminate?
		//cm.rotate(steeringCenter.x,steeringCenter.y,Math.cos(directionAngle),Math.sin(directionAngle));		
		

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
      if(carTerrainNormal!=null){
        	
        	double a=carTerrainNormal.x;
        	double b=carTerrainNormal.y;
        	double c=carTerrainNormal.z;
        	
        	double i_v1=1.0/Math.sqrt(a*a+b*b+c*c);
        	double v2=Math.sqrt(a*a+c*c);
        	
        	
        	//rotate to align the normal
        	carRot[0][0]=c/v2;
			carRot[0][1]=-b*a*i_v1/v2;
			carRot[0][2]=a*i_v1;
			carRot[1][0]=0;
			carRot[1][1]=v2*i_v1;
			carRot[1][2]=b*i_v1;
			carRot[2][0]=-a/v2;
			carRot[2][1]=-b*c*i_v1/v2;
			carRot[2][2]=c*i_v1;
        	
        	xVersor=rotate(carRot,xVersor);
        	yVersor=rotate(carRot,yVersor);
        	zVersor=rotate(carRot,zVersor);
        	zMinusVersor=rotate(carRot,zMinusVersor);
        	
        	rotoTranslate(carRot,cm,dx,dy,dz);
        } 
        
       
        
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){
			
	
			
			int due=(int)(255-i%15);			
			Color col=new Color(due,0,0);
			
			LineData ld=cm.polygonData.elementAt(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld,cm.getLevel());
			polRotate.setShadowCosin(ld.getShadowCosin());
			
		
			int face=cm.boxFaces[i];
			
			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,cm.point000,cm.point011,cm.point001,face,col,carTexture,roadZbuffer);
			
          
				
		}
		
		 cm.translate(POSX,POSY,-MOVZ);
		 cm.rotate(POSX, POSY,viewDirectionCos,viewDirectionSin);
		 if(!carFrame.skipShading){
			 buildShadowVolumeBox(carShadowVolume[SELECTED_CAR],cm);
		 }

	}




	private Point3D rotoTranslate(double[][] rot, Point3D point, double dx,
			
			double dy, double dz) {
		
		double[] p={point.x-dx,point.y-dy,point.z-dz};
		
	
		double[] res=rotate(rot,p);
		
		return new Point3D(res[0]+dx,res[1]+dy,res[2]+dz);
	}
	
	double[] pRot=new double[3];
	private int TRANSZ;
	private boolean start_max_calculus;
	

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
	
	private double[][]  rotate(double[][] p0,  double[][] p1) {
		
		
		double[][] res=new double[3][3];
		
		for (int i = 0;i < res.length; i++) {
			
			for (int j = 0; j < res.length; j++) {
				
				for (int k = 0; k < res.length; k++) {
					
					res[i][j]+=p0[i][k]*p1[k][j];
					
				}
			}
			
		}
	    
		return res;
	}

	public void drawRoad(BufferedImage buf){


		//cleanZBuffer();
		drawSky();

		calculateAltitude();
        
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

		PolygonMesh mesh=meshes[Editor.TERRAIN_INDEX];
		int size=mesh.polygonData.size();
		
		int hashCode=mesh.hashCode();

		//for(int index=0;index<2;index++){

		//PolygonMesh mesh=meshes[index];
		//int size=mesh.polygonData.size();

		for(int j=0;j<size;j++){


			LineData ld=(LineData) mesh.polygonData.elementAt(j);
			
			Polygon3D p3D=buildTransformedPolygon3D(ld,mesh.points,mesh.getLevel());


			if(!p3D.clipPolygonToArea2D(totalVisibleField).isEmpty()){

				decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),EditorData.worldTextures[p3D.getIndex()],roadZbuffer,hashCode);

			}		



		} 

		//}

		drawSPLines(splines,totalVisibleField,roadZbuffer);
		drawCar();

		if(!carFrame.skipShading){
			calculateStencilBuffer();
		}
		buildScreen(buf); 
		//showMemoryUsage();

	}
	

	private void calculateAltitude() {

		MOVZ=0;

		TRANSZ=PARTIAL_MOVZ;
		start_max_calculus=true;

		//for(int index=0;index<2;index++){

		PolygonMesh mesh=meshes[Editor.TERRAIN_INDEX];

		calculateAltitude(mesh);

		for (int i = 0; i < splines.size(); i++) {

				SPLine sp = (SPLine) splines.elementAt(i);

				Vector meshes = sp.getMeshes();

				for (int j = 0; j < meshes.size(); j++) {

					mesh = (PolygonMesh) meshes.elementAt(j);
					calculateAltitude(mesh);
				}	
			}


	}


	private void calculateAltitude(PolygonMesh mesh) {
		
		int size=mesh.polygonData.size();

		for(int j=0;j<size;j++){



			LineData ld=(LineData) mesh.polygonData.elementAt(j);

			Polygon3D realP3D=LineData.buildPolygon(ld,mesh.points);

			Polygon3D p3D=buildTransformedPolygon3D(ld,mesh.points,mesh.getLevel());

			if(p3D.contains(start_car_x,start_car_y)){

				int zz=(int)interpolate(start_car_x,start_car_y,p3D);

				
				//find max possible z altitude
				if(initMOVZ){							

					TRANSZ=zz;
					PARTIAL_MOVZ=zz;

					initMOVZ=false;
					start_max_calculus=false;
					carTerrainNormal=Polygon3D.findNormal(p3D);
				} 						
				else if(zz<=PARTIAL_MOVZ+ROAD_THICKNESS){
					
					if(start_max_calculus){
						TRANSZ=zz;
						start_max_calculus=false;
						carTerrainNormal=Polygon3D.findNormal(p3D);
					}
					else if(zz>=TRANSZ){ 
						TRANSZ=zz;
						carTerrainNormal=Polygon3D.findNormal(p3D);
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

						autocarTerrainNormal[i]=Polygon3D.findNormal(realP3D);
					}
				}

			}

		}
		
	}


	private void drawSPLines(Vector splines, Area totalVisibleField,
			ZBuffer roadZbuffer) {

		for (int i = 0; i < splines.size(); i++) {

			SPLine sp = (SPLine) splines.elementAt(i);

			Vector meshes = sp.getMeshes3D();

			for (int j = 0; j < meshes.size(); j++) {

				PolygonMesh mesh = (PolygonMesh) meshes.elementAt(j);
				
				int hashCode=mesh.hashCode();

				int size=mesh.polygonData.size();
				
				//test if mesh is vibile
				boolean visible=false;
				
				for(int k=0;k<size;k++){

					LineData ld=(LineData) mesh.polygonData.elementAt(k);

					
					Polygon3D p3DBase=buildTransformedPolygon3D(ld,mesh.points,mesh.getLevel());
					
					//if(p3DBase.getLevel()==Road.ROAD_LEVEL)
					//	p3DBase.translate(0, 0,-ROAD_THICKNESS);
					
					if(!p3DBase.clipPolygonToArea2D(totalVisibleField).isEmpty()){
						visible=true;
						break;
						
					}
				}
				
				if(!visible)
					continue;
				
				
				for(int k=0;k<size;k++){

					LineData ld=(LineData) mesh.polygonData.elementAt(k);

					Polygon3D p3D=buildTransformedPolygon3D(ld,mesh.points,mesh.getLevel());


					decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),EditorData.splinesTextures[ld.getTexture_index()],roadZbuffer,hashCode);
					
					

				}

			}
		}
	}


	public void calculateStencilBuffer() {

		super.calculateStencilBuffer();


		isStencilBuffer=true;


		if(carShadowVolume[SELECTED_CAR]==null || carShadowVolume[SELECTED_CAR].allPolygons==null || rearAngle!=0)
		{
			//do nothing
			
		}else{
			
			for (int j = 0; j < carShadowVolume[SELECTED_CAR].allPolygons.length; j++) {

				Polygon3D pol = carShadowVolume[SELECTED_CAR].allPolygons[j];
				buildTransformedPolygon(pol);
				decomposeClippedPolygonIntoZBuffer(pol,Color.red,null,roadZbuffer,-1);
			
			}
			
			
		}	
		
		if(autocars!=null){

			for (int i = 0;i < autocarShadowVolume.length; i++) {
				
				for (int j = 0; j < autocarShadowVolume[i].allPolygons.length; j++) {
	
					Polygon3D pol = autocarShadowVolume[i].allPolygons[j];
					buildTransformedPolygon(pol);
					decomposeClippedPolygonIntoZBuffer(pol,Color.red,null,roadZbuffer,-1);
				
				}
				
			}
		
		}

		isStencilBuffer=false;
	}

	
	public static Polygon3D[] buildAdditionalRoadPolygons(Polygon3D p3d) {
		
		Polygon3D[] pols=new Polygon3D[p3d.npoints];
		
		for(int i=0;i<p3d.npoints;i++){
			
			pols[i]=new Polygon3D();
			
			pols[i].addPoint(p3d.xpoints[i],p3d.ypoints[i],p3d.zpoints[i],p3d.xtpoints[i],p3d.ytpoints[i]);
			pols[i].addPoint(p3d.xpoints[i],p3d.ypoints[i],p3d.zpoints[i]-ROAD_THICKNESS,p3d.xtpoints[i],p3d.ytpoints[i]);
			pols[i].addPoint(p3d.xpoints[(i+1)%p3d.npoints],p3d.ypoints[(i+1)%p3d.npoints],p3d.zpoints[(i+1)%p3d.npoints]-ROAD_THICKNESS,
					p3d.xtpoints[(i+1)%p3d.npoints],p3d.ytpoints[(i+1)%p3d.npoints]);
			pols[i].addPoint(p3d.xpoints[(i+1)%p3d.npoints],p3d.ypoints[(i+1)%p3d.npoints],p3d.zpoints[(i+1)%p3d.npoints],
					p3d.xtpoints[i],p3d.ytpoints[(i+1)%p3d.npoints]);

			
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
		

		buildCar(0);
		

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



    //used for read view
	public void rotateSky(double rotationAngle){
		rearAngle=rotationAngle;
	}
	/**
	 *  alfa = scale factor:
	 *  2PI:(tetam*2.0)=dw:(alfa*WIDTH)
	 *  i:WIDTH=teta:(tetam*2.0) 0<i<WIDTH, i screen x point
	 *  i_set:dw=teta:2PI i_set background x point
	 *  
	 *  texture and screen with y axis downward oriented:
	 *  
	 *  j=0 -> j_set= dh- (YFOCUS*alfa)
	 *  j=YFOCUS -> j_set= dh
	 */
	public void drawSky() {

		//g2.setColor(Color.BLUE);
		//g2.fillRect(0,0,WIDTH,HEIGHT-YFOCUS);
		
		int YMAX=(HEIGHT-YFOCUS);

		int dw=CarFrame.background.getWidth();
		int dh=CarFrame.background.getHeight();
		
		double tetam=Math.atan(WIDTH/(2.0*SCREEN_DISTANCE));
		

		//actual value: 0,738
		double alfa=tetam*dw/(Math.PI*WIDTH);
		
		//int deltah=(int) (dh*(1-alfa)+YFOCUS*alfa);
		int deltah=(int) (dh-YMAX*alfa);//(int) (dh*(1-alfa)+YFOCUS*alfa);
		
		double eta=tetam*2.0/WIDTH;
		
		double i_d=1.0/SCREEN_DISTANCE;
		
		
		
		//System.out.println(APOSX);
		for(int i=0;i<WIDTH;i++){
			
			double teta=i*eta-viewDirection-rearAngle;
			//double teta=(tetam-Math.atan((WIDTH/2.0-i)*i_d))-viewDirection-rearAngle; 
			
			if(teta<0)
				teta=teta+2*Math.PI;
			if(teta>2*Math.PI)
				teta=teta-2*Math.PI;

			int i_set=(int) (dw*teta*i_2pi);
			
			for(int j=0;j<YMAX;j++){

				int tot=i+j*WIDTH;
				
				int j_set=(int) (alfa*j)+deltah;
													
				int rgb=CarFrame.background.getRGB(i_set,j_set);
				roadZbuffer.setRgbColor(rgb,tot);
				

			}
		}	

	}

	public void reset(Graphics2D g2) {
		
		carFrame.setCarSpeed(0);
		g2.setColor(CarFrame.BACKGROUND_COLOR);
		g2.fillRect(0,YFOCUS,WIDTH,HEIGHT-YFOCUS);

		steer=false;
		POSX=0;
		POSY=0;
		
		initialiazeCarDynamics();
	
		
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



	/*public void up(Graphics2D graphics2D) {

        
		if(!steer){

			
			POSY=(int) (POSY+FORWARD*CarFrame.CAR_SPEED*SPEED_SCALE*viewDirectionCos);
			POSX=(int) (POSX-FORWARD*CarFrame.CAR_SPEED*SPEED_SCALE*viewDirectionSin);

		}
		else {
			rotate();
	
		} 


	}*/
	
	public void up(Graphics2D graphics2D) {
		

		carDynamics.move(Engine.dtt);
		//from m/s to km/s
		carFrame.setCarSpeed(3.6*Math.abs(carDynamics.u));

		//cast this way to cut off very small speeds
		int NEW_POSY=POSY+(int)( SPACE_SCALE_FACTOR*carDynamics.dy);
		int NEW_POSX=POSX+(int)( SPACE_SCALE_FACTOR*carDynamics.dx);
		
	
		setViewDirection(getViewDirection()-carDynamics.dpsi);
		carFrame.setSteeringAngle(getViewDirection());

		if(!checkIsWayFree(NEW_POSX,NEW_POSY,getViewDirection(),-1))
		{	

			
			
		}else{
	
		
			//System.out.println(NEW_POSX+" "+NEW_POSY);	
	
	
			POSY=NEW_POSY;
			POSX=NEW_POSX;
		
		}
		
		if(autocars!=null){

			for (int i = 0;i < autocars.length; i++) {
				
				autocars[i].move(Engine.dtt);
				
			}
		}

	}
	
	
	public void start(){
		//for debug

	}
	
	
	public void setSteerAngle(double angle) {
		
		carDynamics.setDelta(angle);
		
	}

	public void setAccelerationVersus(int i) {
		
		
		
		carDynamics.setIsbraking(false);
		
		if(i>0){
			carDynamics.setFx2Versus(FORWARD);
			
		}
		else
			carDynamics.setFx2Versus(0);
			
		
	}

	public void setIsBraking(boolean b) {
		
		if(b)
			carDynamics.setFx2Versus(0);
		carDynamics.setIsbraking(b);
		
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
			
			Vector vPoints=new Vector();
			Vector vTexturePoints=new Vector();
			
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;
				
				if(str.indexOf(Editor.TAG[index])>=0){
					read=!read;
				    continue;
				}	
				
				if(!read)
					continue;

				if(str.startsWith("v="))
					buildPoint(vPoints,str.substring(2),index);
				else if(str.startsWith("vt="))
					PolygonMesh.buildTexturePoint(vTexturePoints,str.substring(3));
				else if(str.startsWith("f="))
					buildLine(meshes[index].polygonData,str.substring(2),vTexturePoints);


			}
            br.close();

            meshes[index].setPoints(vPoints);
            
           if(index==RoadEditor.TERRAIN_INDEX)
        	   meshes[index].setLevel(Road.GROUND_LEVEL);
            
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
		
		PolygonMesh mesh=meshes[Editor.TERRAIN_INDEX];
		int size=mesh.polygonData.size();

		//for(int index=0;index<2;index++){

		//PolygonMesh mesh=meshes[index];
		//int size=mesh.polygonData.size();

		for(int j=0;j<size;j++){


			LineData ld=(LineData) mesh.polygonData.elementAt(j);
			
			Polygon3D polygon = PolygonMesh.getBodyPolygon(mesh.points,ld,mesh.getLevel());
			
			Point3D centroid = Polygon3D.findCentroid(polygon);
			
			Point3D normal=(Polygon3D.findNormal(polygon)).calculateVersor();
			
			ld.setShadowCosin(Point3D.calculateCosin(lightPoint.position.substract(centroid),normal));
			
		}

	}


	public void buildPoint(Vector vPoints, String str,int index) {

	

			String[] vals = str.split(" ");

			Point4D p=new Point4D();
			
			
			//translate world in the point (XFOCUS,SCREEN_DISTANCE,YFOCUS)
			
			p.x=Double.parseDouble(vals[0])-XFOCUS;
			p.y=Double.parseDouble(vals[1])+SCREEN_DISTANCE;
			p.z=Double.parseDouble(vals[2])-YFOCUS;
			
			//if (index==1)
			//	p.z+=ROAD_THICKNESS;

			vPoints.add(p);
		

		
		
	}
	
	public String decomposeLineData(LineData ld) {

		String str="";
		
		str+="T"+ld.texture_index;
		str+=" C"+ld.getHexColor();
		
		int size=ld.size();

		for(int j=0;j<size;j++){
			
			
			
			str+=" ";
			
			str+=ld.getIndex(j);

		}

		return str;
	}
	
	public static void buildLine(Vector polygonData, String str,Vector vTexturePoints) {



			String[] vals = str.split(" ");

			LineData ld=new LineData();

			ld.texture_index=Integer.parseInt(vals[0].substring(1));
			
			ld.hexColor=vals[1].substring(1);
			
			for(int i=2;i<vals.length;i++){

				String val=vals[i];
				if(val.indexOf("/")>0){


					String val0=val.substring(0,val.indexOf("/"));
					String val1=val.substring(1+val.indexOf("/"));

					int indx0=Integer.parseInt(val0);	
					int indx1=Integer.parseInt(val1);					
					Point3D pt=(Point3D) vTexturePoints.elementAt(indx1);					

					ld.addIndex(indx0,indx1,pt.x,pt.y);
				}
				else
					ld.addIndex(Integer.parseInt(val));

			}
			polygonData.add(ld);
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
		Vector vTexturePoints=new Vector();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("v="))
					PolygonMesh.buildPoint(points,str.substring(2)); 
				else if(str.startsWith("vt="))
					PolygonMesh.buildTexturePoint(vTexturePoints,str.substring(3));
				else if(str.startsWith("f="))
					PolygonMesh.buildLine(lines,str.substring(2),vTexturePoints);


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
				
				CubicMesh cm=(CubicMesh) EditorData.object3D[dro.getIndex()].clone();
				
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
			
			drawObjects=new DrawObject[vdrawObjects.size()];
			
			
			for (int i = 0; i < vdrawObjects.size(); i++) {
				drawObjects[i]=(DrawObject) vdrawObjects.elementAt(i);
			}
			
			oldDrawObjects=DrawObject.cloneObjectsArray(drawObjects);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void loadSPLinesFromFile(File file) {
		
		try {
			boolean forceReading=false;
			splines=Editor.loadStaticSPLinesFromFile(file,forceReading);
			
			//translation to fit view screen
			if(splines.size()>0){


				for (int i = 0; i < splines.size(); i++) {
					SPLine sp = (SPLine) splines.elementAt(i);
					
					
					for (int j = 0; sp.ribs!=null && j < sp.ribs.size(); j++) {
						
						Vector nodeRibs=(Vector) sp.ribs.elementAt(j);
						
						for (int r = 0; r< nodeRibs.size(); r++) {
							
							Rib rib= (Rib) nodeRibs.elementAt(r);
							for (int k = 0; k < rib.points.length; k++) {
								rib.points[k].translate(-XFOCUS,+SCREEN_DISTANCE,-YFOCUS);
							}
							
						}
					
					
					}
                     sp.calculate3DMeshes();

				}

			}
			//Editor.levelSPLinesTerrain(meshes[RoadEditor.TERRAIN_INDEX],splines);


		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private DrawObject buildDrawObject(String str) {
		DrawObject dro=new DrawObject();
		
		String properties0=str.substring(0,str.indexOf("["));
		String properties1=str.substring(str.indexOf("[")+1,str.indexOf("]"));

		StringTokenizer tok0=new StringTokenizer(properties0," "); 
		
		//translate world in the point (XFOCUS,SCREEN_DISTANCE,YFOCUS)
		dro.x=Double.parseDouble(tok0.nextToken())-XFOCUS;
		dro.y=Double.parseDouble(tok0.nextToken())+SCREEN_DISTANCE;
		dro.z=Double.parseDouble(tok0.nextToken())-YFOCUS;
		
		dro.index=Integer.parseInt(tok0.nextToken());
		
		
		StringTokenizer tok1=new StringTokenizer(properties1," "); 
		dro.rotation_angle=Double.parseDouble(tok1.nextToken());
		dro.hexColor=tok1.nextToken();
		dro.calculateBase();
		
		return dro;
	}

	private Point4D[] buildRow(String string) {
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




	public static void main(String[] args) {
		
		generateDefault0();

	}





	public void changeCamera(int camera_type) {
		
		if(camera_type==EXTERNAL_CAMERA && CAMERA_TYPE!=EXTERNAL_CAMERA){
			
			CAMERA_TYPE=EXTERNAL_CAMERA;
			POSY=POSY-(int) SCREEN_DISTANCE;
			YFOCUS=HEIGHT/2;
		}
		else if(camera_type==DRIVER_CAMERA && CAMERA_TYPE!=DRIVER_CAMERA){
			
			CAMERA_TYPE=DRIVER_CAMERA;
			POSY= POSY+(int)SCREEN_DISTANCE;
			YFOCUS=HEIGHT/5;
			
		}	
	
		
	}

	private void drawAutocars(Autocar[] autocars, Area totalVisibleField2,
			ZBuffer roadZbuffer) {		
	
		
		for (int i = 0; i < autocars.length; i++) {
			
			Autocar autocar=autocars[i];
			
			CubicMesh cm=autocar.carData.carMesh.clone();
			
			double cosRo=Math.cos(autocar.fi-pi_2);
			double sinRo=Math.sin(autocar.fi-pi_2); 
			
			double dx=autocar.center.x-autocar.car_width*0.5;
			double dy=autocar.center.y-autocar.car_length*0.5;
			double dz=autocar.center.z-autocar.car_height*0.5;

			cm.translate(dx,dy,dz);
			cm.rotate(autocar.center.x,autocar.center.y,cosRo,sinRo);
			
			
			Point3D p=new Point3D(autocar.center.x,autocar.center.y,0);
			p=buildTransformedPoint(p);
			
			if(!totalVisibleField.contains(p.x,p.y))
					continue;;

				
			Point3D xVersor=cm.getXAxis();
			Point3D yVersor=cm.getYAxis();
			
			Point3D zVersor=cm.getZAxis();
			Point3D zMinusVersor=new Point3D(-zVersor.x,-zVersor.y,-zVersor.z);
	
			

			
			if(autocarTerrainNormal[i]!=null){
				
				//transforming the coordinate system
		      	autocarRo[0][0]=cosRo; 
	        	autocarRo[0][1]=-sinRo; 
	        	autocarRo[0][2]=0; 
	        	autocarRo[1][0]=sinRo; 
	        	autocarRo[1][1]=cosRo; 
	        	autocarRo[1][2]=0; 
	        	autocarRo[2][0]=0; 
	        	autocarRo[2][1]=0; 
	        	autocarRo[2][2]=1; 
	        	
	        	//transforming back the coordinate system
	        	autocarMinusRo[0][0]=cosRo; 
	        	autocarMinusRo[0][1]=+sinRo; 
	        	autocarMinusRo[0][2]=0; 
	        	autocarMinusRo[1][0]=-sinRo; 
	        	autocarMinusRo[1][1]=cosRo; 
	        	autocarMinusRo[1][2]=0; 
	        	autocarMinusRo[2][0]=0; 
	        	autocarMinusRo[2][1]=0; 
	        	autocarMinusRo[2][2]=1; 
	        	
	        	//Point3D autocarNormal=rotate(autocarRo,new Point3D(-0.19611613513818402, 0.0, 0.9805806756909201));
	        	Point3D autocarNormal=rotate(autocarRo,autocarTerrainNormal[i]);
	        	
	        	double a=autocarNormal.x;
	        	double b=autocarNormal.y;
	        	double c=autocarNormal.z;
	        	
	        	double i_v1=1.0/Math.sqrt(a*a+b*b+c*c);
	        	double v2=Math.sqrt(a*a+c*c);	        	
	        	
	        	//rotate to align the normal
	        	autocarRot[0][0]=c/v2;
	        	autocarRot[0][1]=-b*a*i_v1/v2;
	        	autocarRot[0][2]=a*i_v1;
	        	autocarRot[1][0]=0;
	        	autocarRot[1][1]=v2*i_v1;
	        	autocarRot[1][2]=b*i_v1;
	        	autocarRot[2][0]=-a/v2;
	        	autocarRot[2][1]=-b*c*i_v1/v2;
	        	autocarRot[2][2]=c*i_v1;	        	
	  
	        	double[][] aRotation=rotate(autocarRot,autocarRo);
				aRotation=rotate(autocarMinusRo,aRotation);
				//double[][] aRotation=autocarRot;
			
		

				xVersor=rotate(aRotation,xVersor);
	        	yVersor=rotate(aRotation,yVersor);
	        	zVersor=rotate(aRotation,zVersor);
	        	zMinusVersor=rotate(aRotation,zMinusVersor);
	        	
	        	rotoTranslate(aRotation,cm,dx,dy,dz);
	        	
	
			}

			decomposeCubicMesh(cm,autocar.texture,roadZbuffer);

			if(!carFrame.skipShading)
				buildShadowVolumeBox(autocarShadowVolume[i],cm);
			
			

		}
		
	}

	private void rotoTranslate(double[][] aRotation, CubicMesh cm, double dx,
			double dy, double dz) {
		
		cm.point000=rotoTranslate(aRotation,cm.point000,dx,dy,dz);
		cm.point011=rotoTranslate(aRotation,cm.point011,dx,dy,dz);				
		cm.point001=rotoTranslate(aRotation,cm.point001,dx,dy,dz);
		cm.point101=rotoTranslate(aRotation,cm.point101,dx,dy,dz);
		cm.point110=rotoTranslate(aRotation,cm.point110,dx,dy,dz);
		cm.point100=rotoTranslate(aRotation,cm.point100,dx,dy,dz);
		cm.point010=rotoTranslate(aRotation,cm.point010,dx,dy,dz);
		cm.point111=rotoTranslate(aRotation,cm.point111,dx,dy,dz);
		
     	for (int j = 0; j < cm.points.length; j++) {
				cm.points[j]=rotoTranslate(aRotation,cm.points[j],dx,dy,dz);
		}
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
				
				if(autoCar.car_road!=null){
				
					Point3D[] newCar_road=new Point3D[autoCar.car_road.length];
					
					for (int j = 0; j < autoCar.car_road.length; j++) {
						
						newCar_road[j]=new Point3D();
						
						newCar_road[j].x=autoCar.car_road[j].x-XFOCUS;
						newCar_road[j].y=autoCar.car_road[j].y+SCREEN_DISTANCE;
						newCar_road[j].z=autoCar.car_road[j].z-YFOCUS;
						
					}
					
					autoCar.setCar_road(newCar_road);
				
				}
			
				
			}
			
		}else
			autocars=new Autocar[0];
		
		autocarShadowVolume=new ShadowVolume[autocars.length];
		
		if(!carFrame.skipShading){
			
			for (int i = 0; i < autocarShadowVolume.length; i++) {
				autocarShadowVolume[i]=initShadowVolume(autocars[i].carData.carMesh);
			}
		
		}
		autocarTerrainNormal=new Point3D[autocars.length];
	}

	public boolean checkIsWayFree(int new_posx, int new_posy,double newViewDirection,  int autocar_index) {


		// car points of border to detect collisions

		Polygon CAR_BORDER=null;
		
		if(autocar_index<0){
			
			CAR_BORDER=Autocar.buildCarBox(
					start_car_x+new_posx,
					(int) (new_posy+start_car_y+CAR_LENGTH*0.5),
					+MOVZ,
					CAR_WIDTH,CAR_LENGTH,0); 
			Polygon3D.rotate(CAR_BORDER,new Point3D(new_posx,new_posy,0),newViewDirection);
			
		}else{
			
			CAR_BORDER=Autocar.buildCarBox(
					start_car_x+POSX,
					(int) (POSY+start_car_y+CAR_LENGTH*0.5),
					+MOVZ,
					CAR_WIDTH,CAR_LENGTH,0); 
			Polygon3D.rotate(CAR_BORDER,new Point3D(POSX,POSY,0),viewDirection);
			
		}


		for(int i=0;i<drawObjects.length;i++){


			if(autocar_index<0){

				DrawObject dro=drawObjects[i];


		    	Polygon3D objBorder= dro.getBorder().clone();

		    	Point3D center=Polygon3D.findCentroid(objBorder);
				Polygon3D.rotate(objBorder,center,dro.rotation_angle);
				
				if(getIntersection(objBorder,CAR_BORDER)!=null){
					//carDynamics.stop();
    				return false;
		     	}	
			}

		}


		//System.out.println(CAR_BORDER);

		if(autocars!=null){


			for (int l = 0; l < autocars.length; l++) {

				Polygon polyCar = autocars[l].buildCarBox((int)autocars[l].center.x,(int)autocars[l].center.y,(int)autocars[l].center.z);

				if(autocar_index>=0 && l!=autocar_index)
					continue;
				
				double cz=autocars[l].center.z-autocars[l].car_height*0.5;
				
				//if(autocar_index==1)
				//	System.out.println(cz+" "+(YFOCUS+MOVZ));
	
				if(Math.abs(cz+YFOCUS+MOVZ)>2*ROAD_THICKNESS)
					continue;


				if(getIntersection(polyCar,CAR_BORDER)!=null){
					
					if(autocar_index<0){
						//carDynamics.stop();
					}
					
					return false;
				}
			}




		}





		return true;
	}
	

	/***
	 * 
	 * calculate the intersections between two polygons border
	 * 
	 * 
	 * @param polyCar
	 * @param carBorder
	 * @return
	 */
	private Point3D getIntersection(Polygon polyCar, Polygon carBorder) {
		
	
		
		for (int i = 0; i < polyCar.npoints; i++) {
			
			Point3D poly0=new Point3D(polyCar.xpoints[i],polyCar.ypoints[i],0);
			Point3D poly1=new Point3D(polyCar.xpoints[(i+1)%polyCar.npoints],polyCar.ypoints[(i+1)%polyCar.npoints],0);
			
			for (int j = 0; j < carBorder.npoints; j++) {
				
				Point3D carP0=new Point3D(carBorder.xpoints[j],carBorder.ypoints[j],0);
				Point3D carP1=new Point3D(carBorder.xpoints[(j+1)%carBorder.npoints],carBorder.ypoints[(j+1)%carBorder.npoints],0);
				
				Point3D pInter=Autocar.calculateLineIntersection(poly0,poly1,carP0,carP1);
				
				if(pInter!=null)
					return pInter;
				
			}
			
			
		}
		
		return null;
	}



	public static void generateDefault0() {
			
			int nx=44;
			int ny=44;
			
			int x0=-200;
			int y0=0;
			
			int dx=200;
			int dy=200;
			
			System.out.println("<terrain>");
			System.out.println("NX="+nx);
			System.out.println("NY="+ny);
			System.out.println("DX="+dx);
			System.out.println("DY="+dy);
			System.out.println("X0="+x0);
			System.out.println("Y0="+y0);
			
			DecimalFormatSymbols dfs=new DecimalFormatSymbols(Locale.UK);
			DecimalFormat dfc = new DecimalFormat("###.###");
			dfc.setDecimalFormatSymbols(dfs);
			
			//points
			for(int j=0;j<ny+1;j++)
				for (int i = 0; i < nx+1; i++) {
					
					double x=x0+i*dx;
					double y=y0+j*dy;
					//hills
					double z=200*(1.0+Math.cos(((y-y0)*2*Math.PI/4400.0)-Math.PI)
							*Math.cos(((x-x0)*2*Math.PI/4400.0)-Math.PI)
							);
					
					String sx=dfc.format(x);
					String sy=dfc.format(y);
					String sz=dfc.format(z);
					
					System.out.println("v="+sx+" "+sy+" "+sz);
				}
			
			
			
			//texture points
			System.out.println("vt=0 0");
			System.out.println("vt=200 0");
			System.out.println("vt=200 200");
			System.out.println("vt=0 200");
			
					
			//faces		
			
			for(int j=0;j<ny;j++)
				for (int i = 0; i < nx; i++) {
					
					int ii=i+j*(nx+1);
					
					int n0=ii;
					int n1=ii+1;
					int n2=ii+1+(nx+1);
					int n3=ii+(nx+1);
					
					System.out.println(
					"f=T2 "+
					"C00FF00 "+
					n0+"/0 "+
					n1+"/1 " +
					n2+"/2 " +
					n3+"/3"
				);		
			
			}
			
			System.out.println("</terrain>");
		}
		

}
