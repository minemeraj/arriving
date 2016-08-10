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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;

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


	

	private int y_edge=5+SCREEN_DISTANCE;
	
	private int NYVISIBLE=40;//20 orig

	private int dy=(int) (3000*SCALE/NYVISIBLE);//600 orig

	private static final int ROAD_THICKNESS=(int) (22*SCALE);

	private int CAR_WIDTH=100;
	private int CAR_LENGTH=100;
	
	protected static final int FORWARD_REAR = -1;
	protected static final int FORWARD_FRONT = 1;
	protected static final int FORWARD_STOP = 0;
	protected int FORWARD=FORWARD_FRONT;
	
	protected final int EXTERNAL_CAMERA=0;
	protected final int DRIVER_CAMERA=1;
	
	private int CAMERA_TYPE=EXTERNAL_CAMERA;
	
	private PolygonMesh[] oldMeshes=new PolygonMesh[2];

	private CarData[] carData=null;
	private ShadowVolume[] carShadowVolume=null;

	private DrawObject[] drawObjects=null;
	private DrawObject[]  oldDrawObjects=null;

	
	static final double SPACE_SCALE_FACTOR=60.0;
	static final double SPEED_SCALE=1.0/1.13;
	
	private int SELECTED_CAR=0;

	private Texture carTexture=null;
	private Area totalVisibleField=null;
	private Rectangle totalVisibleFieldBounds=null;
	
	private int start_car_x=WIDTH/2-XFOCUS;
	private int start_car_y=y_edge;
	
	private Autocar[] autocars=null;
    private static final double pi_2=Math.PI/2.0;
    private static final double i_2pi=1.0/(Math.PI*2.0);
    
    private int CURRENT_MOVZ=0;
    private boolean initMOVZ=true;
        
    private double rearAngle;
    
    private double[][] carRot=new double[3][3];
    private double[][] autocarRot=new double[3][3];
    private double[][] autocarRo=new double[3][3];
    private double[][] autocarMinusRo=new double[3][3];
    
    private Point3D carTerrainNormal=null; 
    private Point3D[] autocarTerrainNormal=null;
    
    
    private ShadowVolume[] autocarShadowVolume=null;
    
	protected CarDynamics carDynamics=null;	
	
	public static double WATER_LEVEL=0;
	public static double WATER_FILLING=50;
	
	private ArrayList<SPLine> splines=null;
	
	public static final int ROAD_INDEX0 =0;
	public static final int ROAD_INDEX1 =1;
	
	public static final int EMPTY_LEVEL = -1;	
	public static final int GROUND_LEVEL=0;
	public static final int ROAD_LEVEL=1;
	public static final int OBJECT_LEVEL=2;
	
	protected boolean skipShading=false;
	
	private int POSSIBLE_MOVZ;
	private boolean start_max_calculus;
	
	private HashMap<Integer,Boolean> terrainVisibleIndexes=new HashMap<Integer,Boolean>();

	private Point3D startPosition;

	
	
	public Road(){}


	Road(int WITDH,int HEIGHT){

		this.HEIGHT=HEIGHT;
		this.WIDTH=WITDH;
		YFOCUS=HEIGHT/2;
		XFOCUS=WITDH/2;

		totalVisibleField=buildVisibileArea(SCREEN_DISTANCE,NYVISIBLE*dy+SCREEN_DISTANCE);
		totalVisibleFieldBounds=totalVisibleField.getBounds();
		
		WATER_LEVEL=-HEIGHT/2-WATER_FILLING;
	}
	
	@Override
	protected double calculPerspY( double x, double y, double z) {
		double newy0= ((z)*SCREEN_DISTANCE*(1.0/y))+YFOCUS;
		return HEIGHT-newy0;
	}

	@Override
	protected double calculPerspX(double x, double y, double z) {

		return ((x)*SCREEN_DISTANCE*(1.0/y))+XFOCUS;

	}


	void loadRoad(String map_name) {

		observerPoint=new Point3D(0,0,0);
		
		lightPoint=new LightSource(
				new Point3D(0,0,0),
				new Point3D(0,1,0)
		);
		
	 
		try {
			
			
			
			EditorData.initialize(loadingProgressPanel,SCALE);
			
			File file=new File("lib/landscape_"+map_name);
			
			loadPointsFromFile(file);		
			loadSPLinesFromFile(file);	
			loadObjectsFromFile(file);			
			
			startPosition = Editor.loadStartPosition(file);
			POSX=(int)( Renderer3D.SCALE*startPosition.x)-WIDTH/2-CAR_WIDTH/2;
			POSY=(int)( Renderer3D.SCALE*startPosition.y);
			
			if(startPosition.getData()!=null){
				
				setViewDirection((Double)startPosition.getData());
				
			} else
				setViewDirection(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		
		calculateShadowCosines();
		
		roadZbuffer=new ZBuffer(WIDTH*HEIGHT);
		lightZbuffer=new ZBuffer(WIDTH*HEIGHT);
	
		buildNewZBuffers();	    
		
		
		if(!isSkipShading())
			calculateShadowVolumes(drawObjects);
		
		initialiazeCarDynamics();
		


	}

	private void loadObjectsFromFile(File file) {
		
		ArrayList<DrawObject> vdrawObjects = loadObjectsFromFile(file,null,Renderer3D.SCALE);	
		
		drawObjects=new DrawObject[vdrawObjects.size()];
		
		
		for (int i = 0; i < vdrawObjects.size(); i++) {
			drawObjects[i]=(DrawObject) vdrawObjects.get(i);
		}
		
		oldDrawObjects=DrawObject.cloneObjectsArray(drawObjects);
		
	}
	
	@Override
	public void buildRectanglePolygons(ArrayList<Polygon3D> polygons, double x, double y,
			double z, double dx, double dy, double dz) {



		Polygon3D base=buildRectangleBase3D(x,y,z,dx,dy,dz);
		polygons.add(base);		
		Polygon3D ubase=base.buildTranslatedPolygon(0,0,dz);
		polygons.add(ubase);	
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,0));
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,1));
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,2));
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,3));
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
		carDynamics.setInitvalues(0, 0, 0, 0,-getViewDirection());
			
	}

	/**
	 * Unused method
	 * 
	 */
	@Override
	public void calculateShadowMap() {

		isShadowMap=true;
		
		int index=0;
		
		for (int ii = 0; ii < 2; ii++) {
			
			int size=meshes[index].polygonData.size();

			for(int j=0;j<size;j++){

				LineData ld=(LineData) meshes[index].polygonData.get(j);

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


				LineData ld=cm.polygonData.get(i);

				int due=(int)(255-i%15);
				Color col=new Color(due,0,0);

				Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.xpoints,cm.ypoints,cm.zpoints,ld,cm.getLevel());
				calculateLightTransformedPolygon(polRotate,true);
				polRotate.setShadowCosin(ld.getShadowCosin());

				int face=cm.boxFaces[i];


				decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,texture,lightZbuffer);
				
			}	


		}
		isShadowMap=false;
		
	}



	private void loadCars(ArrayList<File> vCarData) {
		
		
		carData=new CarData[vCarData.size()];
		
		carShadowVolume=new ShadowVolume[vCarData.size()] ; 
		
		for (int i = 0; i< vCarData.size(); i++) {
			File file = (File) vCarData.get(i);
			
			carData[i]=loadCarFromFile(new File("lib/cardefault3D_"+i),Renderer3D.SCALE);
			CAR_WIDTH=carData[i].carMesh.deltaX2-carData[i].carMesh.deltaX;
			CAR_LENGTH=carData[i].carMesh.deltaY2-carData[i].carMesh.deltaY;
			carData[i].getCarMesh().translate(WIDTH/2-CAR_WIDTH/2-XFOCUS,y_edge,-YFOCUS);

			if(!isSkipShading()){
				carShadowVolume[i]=initShadowVolume(carData[i].carMesh);
			}
			
		}

		

		

	}

	
	void selectNextCar() {
		
		
		if(SELECTED_CAR+1<carData.length)
			SELECTED_CAR+=1;
		else
			SELECTED_CAR=0;
		
		carTexture=CarFrame.carTextures[SELECTED_CAR];
		
	}

	void initCars(ArrayList<File> vCarData) {
		
		SELECTED_CAR=0;

		loadCars(vCarData);
		
		carTexture =null;
		
		
		carTexture=CarFrame.carTextures[SELECTED_CAR];


	}

	private void buildCar(double directionAngle){
	

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
			
			LineData ld=cm.polygonData.get(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.xpoints,cm.ypoints,cm.zpoints,ld,cm.getLevel());
			polRotate.setShadowCosin(ld.getShadowCosin());
			
		
			int face=cm.boxFaces[i];
			
			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,cm.point000,cm.point011,cm.point001,face,col,carTexture,roadZbuffer);
		
          
				
		}
		
		 cm.translate(POSX,POSY,-MOVZ);
		 cm.rotate(POSX, POSY,viewDirectionCos,viewDirectionSin);
		 if(!isSkipShading()){
			 buildShadowVolumeBox(carShadowVolume[SELECTED_CAR],cm);
		 }
		 
	}


	private Point3D rotoTranslate(double[][] rot, 

			Point3D p, 			
			double dx,double dy, double dz) {


		return rotoTranslate(rot, p.x,  p.y,  p.z, dx, dy,  dz);
	}

	private Point3D rotoTranslate(double[][] rot, 

			double x, double y,double z, 			
			double dx,double dy, double dz) {

		double[] p={x-dx,y-dy,z-dz};


		double[] res=rotate(rot,p);

		return new Point3D(res[0]+dx,res[1]+dy,res[2]+dz);
	}
	
	private double[] pRot=new double[3];

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

	private int terrainSize;
	
	void drawRoad(BufferedImage buf){



		drawSky();

		calculateDrawingData();
        
		//changing altitutude with the movements		
		if(POSSIBLE_MOVZ>=CURRENT_MOVZ-ROAD_THICKNESS)
			CURRENT_MOVZ=POSSIBLE_MOVZ;
		else //minus to simulate gravitational fall
			CURRENT_MOVZ=CURRENT_MOVZ-ROAD_THICKNESS;
       
		MOVZ=-(CURRENT_MOVZ+YFOCUS);
        
		

		drawObjects(drawObjects,totalVisibleFieldBounds,roadZbuffer);

		if(autocars!=null){

			drawAutocars(autocars,totalVisibleFieldBounds,roadZbuffer);

		}

		PolygonMesh mesh=meshes[Editor.TERRAIN_INDEX];
		
		int hashCode=mesh.hashCode();
		
		ArrayList<Polygon3D> polyToDraw=new ArrayList<Polygon3D>();
		int pSize=terrainVisibleIndexes.size();
		
		int level=mesh.getLevel();
		
		for(int j=0;j<terrainSize;j++){

			if(terrainVisibleIndexes.get(j)==null)
				continue;

			LineData ld= mesh.polygonData.get(j);	
			
			polyToDraw.add(buildTransformedPolygon3D(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,level));
			
		}	
		
		

		for(int j=0;j<pSize;j++){

			
			Polygon3D p3D=polyToDraw.get(j);


			//if(Polygon3D.isIntersect(p3D,totalVisibleField.getBounds())){

				decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),EditorData.worldTextures[p3D.getIndex()],roadZbuffer,hashCode);

				if(p3D.isFilledWithWater()){
					
					buildWaterPolygon(p3D);
					decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),EditorData.worldTextures[p3D.getIndex()],roadZbuffer,hashCode);

				}
				
			//}		



		} 


		drawSPLines(splines,totalVisibleFieldBounds,roadZbuffer);
		drawCar();

		if(!isSkipShading()){
			calculateStencilBuffer();
		}
		buildScreen(buf); 
	}
	




	private void buildWaterPolygon(Polygon3D p3d) {
		p3d.setWaterPolygon(true);
		for (int i = 0; i < p3d.npoints; i++) {
			p3d.zpoints[i]=(int) (WATER_LEVEL+MOVZ);
		}
		
		
	}


	private void calculateDrawingData() {

		MOVZ=0;

		POSSIBLE_MOVZ=CURRENT_MOVZ;
		start_max_calculus=true;


		PolygonMesh mesh=meshes[Editor.TERRAIN_INDEX];

		calculateDrawingData(mesh,true);

		int spSize=splines.size();
		for (int i = 0; i < spSize; i++) {

				SPLine sp = (SPLine) splines.get(i);

				ArrayList<PolygonMesh> meshes = sp.getMeshes();
				int szMeshes=meshes.size();

				for (int j = 0; j < szMeshes; j++) {

					mesh = (PolygonMesh) meshes.get(j);
					calculateDrawingData(mesh,false);
				}	
			}


	}

	/**
	 * 
	 * Calculate the altitude and the visible terrain polygons, marked by index
	 * 
	 * @param mesh
	 * @param isTerrain
	 */

	private void calculateDrawingData(PolygonMesh mesh,boolean isTerrain) {
		
		int size=0;
		
		if(isTerrain){
			terrainVisibleIndexes.clear();
			size=terrainSize;
		}else{
			size=mesh.polygonData.size();
		}
		
		boolean foundProjection=false;
		HashMap<Integer,Boolean> foundAutocars=new HashMap<Integer,Boolean>();

		for(int j=0;j<size;j++){

			LineData ld=mesh.polygonData.get(j);

			Polygon3D p3DProjection=buildTransformedPolygonProjection(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.getLevel());
			
			if(isTerrain && Polygon3D.isIntersect(p3DProjection,totalVisibleFieldBounds)){
				
				terrainVisibleIndexes.put(j,true);
				
			}
			
			boolean skipTerrain=isTerrain && foundProjection;

			if(!skipTerrain && p3DProjection.contains(start_car_x,start_car_y)){
				
				foundProjection=true;
				
				Polygon3D p3D=buildTransformedPolygon3DWithoutTexture(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.getLevel());

				int zz=(int)interpolate(start_car_x,start_car_y,p3D);

				//avoid to sink under the terrain
				if(isTerrain){
					POSSIBLE_MOVZ=zz;
				}
				//find max possible z altitude
				if(initMOVZ){							

					//POSSIBLE_MOVZ=zz;
					CURRENT_MOVZ=zz;

					initMOVZ=false;
					start_max_calculus=false;
					carTerrainNormal=Polygon3D.findNormal(p3D);
				}//CANNOT DO A JUMP > ROAD_THICKNESS						
				else if(zz<=CURRENT_MOVZ+ROAD_THICKNESS){
					
					if(start_max_calculus){
						POSSIBLE_MOVZ=zz;
						start_max_calculus=false;
						carTerrainNormal=Polygon3D.findNormal(p3D);
					}
					else if(zz>=POSSIBLE_MOVZ){ 
						POSSIBLE_MOVZ=zz;
						carTerrainNormal=Polygon3D.findNormal(p3D);
					}	

				}	
				

			}

			if(autocars!=null){


				for (int i = 0; i < autocars.length; i++) {
					
					if(foundAutocars.get(i)!=null)
						continue;

					Point3D transformedCenter=buildTransformedPoint(autocars[i].center);

					if(p3DProjection.contains(transformedCenter.x,transformedCenter.y)){
						
						foundAutocars.put(i,true);
						
						Polygon3D p3D=buildTransformedPolygon3DWithoutTexture(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.getLevel());
						
						Polygon3D realP3D=LineData.buildPolygon(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints);

						int posz=(int)interpolate(transformedCenter.x,transformedCenter.y,p3D);

						autocars[i].center.z=autocars[i].car_height*0.5+
								(posz);

						autocarTerrainNormal[i]=Polygon3D.findNormal(realP3D);
					}
				}

			}

		}
		
	}


	private void drawSPLines(ArrayList<SPLine> splines, Rectangle totalVisibleFieldBounds,
			ZBuffer roadZbuffer) {
		
		int szMesh=splines.size();

		for (int i = 0; i < szMesh; i++) {

			SPLine sp = (SPLine) splines.get(i);
			
			int hashCode=sp.hashCode();

			ArrayList<PolygonMesh> meshes = sp.getMeshes3D();
			
			int sizeMeshes=meshes.size();

			for (int j = 0; j <sizeMeshes ; j++) {

				PolygonMesh mesh = (PolygonMesh) meshes.get(j);
				
				

				int size=mesh.polygonData.size();
				
				//test if mesh is vibile
				boolean visible=false;
				
				for(int k=0;k<size;k++){

					LineData ld=(LineData) mesh.polygonData.get(k);

					
					Polygon3D p3DBase=buildTransformedPolygon3DWithoutTexture(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.getLevel());
					
					if(Polygon3D.isIntersect(p3DBase,totalVisibleFieldBounds)){
						visible=true;
						break;
						
					}
				}
				
				if(!visible)
					continue;
				
				
				for(int k=0;k<size;k++){

					LineData ld=(LineData) mesh.polygonData.get(k);

					Polygon3D p3D=buildTransformedPolygon3D(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.getLevel());


					decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),EditorData.splinesTextures[ld.getTexture_index()],roadZbuffer,hashCode);
					
					

				}

			}
		}
	}

	@Override
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

			Point4D p=(Point4D) new Point4D(mesh.xpoints[num],mesh.ypoints[num],mesh.zpoints[num]);

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

		return a;
	}

	private int limVisibileL(double y1) {

		return (int) ((y1)*(-XFOCUS)/SCREEN_DISTANCE);
	}

	private int limVisibileR(double y1) {

		return (int) ((y1)*(WIDTH-XFOCUS)/SCREEN_DISTANCE);
	}

    //used for read view
	void rotateSky(double rotationAngle){
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
	private void drawSky() {
		
		int YMAX=(HEIGHT-YFOCUS);

		int dw=CarFrame.background.getWidth();
		int dh=CarFrame.background.getHeight();
		
		double tetam=Math.atan(WIDTH/(2.0*SCREEN_DISTANCE));
		

		//actual value: 0,738
		double alfa=tetam*dw/(Math.PI*WIDTH);
		
		int deltah=(int) (dh-YMAX*alfa);//(int) (dh*(1-alfa)+YFOCUS*alfa);
		
		double eta=tetam*2.0/WIDTH;
		
		double i_d=1.0/SCREEN_DISTANCE;
		

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

	void reset(Graphics2D g2) {
		
		g2.setColor(CarFrame.BACKGROUND_COLOR);
		g2.fillRect(0,YFOCUS,WIDTH,HEIGHT-YFOCUS);

		POSX=(int)( Renderer3D.SCALE*startPosition.x)-WIDTH/2-CAR_WIDTH/2;
		POSY=(int)( Renderer3D.SCALE*startPosition.y);
		
		initialiazeCarDynamics();
		
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

	
	void up(Graphics2D graphics2D) {
		

		carDynamics.move(Engine.dtt);
		//from m/s to km/s
	

		//cast this way to cut off very small speeds
		int NEW_POSY=POSY+(int)( SCALE*SPACE_SCALE_FACTOR*carDynamics.dy);
		int NEW_POSX=POSX+(int)( SCALE*SPACE_SCALE_FACTOR*carDynamics.dx);
		
	
		setViewDirection(getViewDirection()-carDynamics.dpsi);
		CarFrame.setMovingAngle(getViewDirection());

		if(!checkIsWayFree(NEW_POSX,NEW_POSY,getViewDirection(),-1))
		{	

			//DO NOTHING
			
		}else{
	
			POSY=NEW_POSY;
			POSX=NEW_POSX;
		
		}
		
		if(autocars!=null){

			for (int i = 0;i < autocars.length; i++) {
				
				autocars[i].move(Engine.dtt);
				
			}
		}

	}
	
	public void setSteerAngle(double angle) {
		
		carDynamics.setDeltaGoal(angle);
		
	}

	public void setAccelerationVersus(int versus) {
		
		
		
		carDynamics.setIsbraking(false);

		carDynamics.setFx2Versus(versus);
			
		
	}

	public void setIsBraking(boolean b) {
		
		if(b)
			carDynamics.setFx2Versus(0);
		carDynamics.setIsbraking(b);
		
	}
	
	private void loadPointsFromFile(File file){		

		try {
			
			loadPointsFromFile(file,Road.TERRAIN_INDEX,false);
            

        	meshes[RoadEditor.TERRAIN_INDEX].setLevel(Road.GROUND_LEVEL);
        	
        	terrainSize=meshes[RoadEditor.TERRAIN_INDEX].polygonData.size();
            
			oldMeshes[RoadEditor.TERRAIN_INDEX]=cloneMesh(meshes[RoadEditor.TERRAIN_INDEX]);

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

		for(int j=0;j<size;j++){


			LineData ld=(LineData) mesh.polygonData.get(j);
			
			Polygon3D polygon = PolygonMesh.getBodyPolygon(mesh.xpoints,mesh.ypoints,mesh.zpoints,ld,mesh.getLevel());
			
			Point3D centroid = Polygon3D.findCentroid(polygon);
			
			Point3D normal=(Polygon3D.findNormal(polygon)).calculateVersor();
			
			ld.setShadowCosin(Point3D.calculateCosin(lightPoint.position.substract(centroid),normal));
			
		}

	}

	@Override
	public void buildPoint(List<Point3D> vPoints, String str) {

	

			String[] vals = str.split(" ");

			Point4D p=new Point4D();
			
			
			//translate world in the point (XFOCUS,SCREEN_DISTANCE,YFOCUS)
			
			p.x=SCALE*Double.parseDouble(vals[0])-XFOCUS;
			p.y=SCALE*Double.parseDouble(vals[1])+SCREEN_DISTANCE;
			p.z=SCALE*Double.parseDouble(vals[2])-YFOCUS;

			vPoints.add(p);
		

		
		
	}
	
	
	@Override
	
	public void buildLine(ArrayList<LineData> polygonData, String str,ArrayList<Point3D> vTexturePoints) {
		
		buildStaticLine( polygonData,  str, vTexturePoints);
	}
	public static void buildStaticLine(List<LineData> polygonData, String str,ArrayList<Point3D> vTexturePoints) {



			String[] vals = str.split(" ");

			LineData ld=new LineData();

			ld.texture_index=Integer.parseInt(vals[0].substring(1));
			
			ld.hexColor=vals[1].substring(1);
			ld.setFilledWithWater("W1".equals(vals[2]));
			
			for(int i=3;i<vals.length;i++){

				String val=vals[i];
				if(val.indexOf("/")>0){


					String val0=val.substring(0,val.indexOf("/"));
					String val1=val.substring(1+val.indexOf("/"));

					int indx0=Integer.parseInt(val0);	
					int indx1=Integer.parseInt(val1);					
					Point3D pt=(Point3D) vTexturePoints.get(indx1);					

					ld.addIndex(indx0,indx1,pt.x,pt.y);
				}
				else
					ld.addIndex(Integer.parseInt(val));

			}
			polygonData.add(ld);
	}
		
	private void resetRoadData(int index) {
		
		meshes[index]=cloneMesh(oldMeshes[index]);

	}


	public static  CarData loadCarFromFile(File file,double scale) {
		
		
		CarData carData=new CarData();


		ArrayList <Point3D>points = new ArrayList <Point3D>();
		ArrayList <LineData>lines = new ArrayList <LineData>();
		ArrayList <Point3D> aTexturePoints=new ArrayList<Point3D>();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			String description=null;

			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("v="))
					PolygonMesh.buildPoint(points,str.substring(2),scale); 
				else if(str.startsWith("vt="))
					PolygonMesh.buildTexturePoint(aTexturePoints,str.substring(3));
				else if(str.startsWith("f="))
					PolygonMesh.buildLine(lines,str.substring(2),aTexturePoints);
				else if(str.startsWith("DESCRIPTION=")){
					int index=str.indexOf("=");
					description=str.substring(index+1);
				}			


			}
			
			PolygonMesh pm=new PolygonMesh(points,lines);
		    pm.setDescription(description);
	        
			carData.carMesh=CubicMesh.buildCubicMesh(pm);
			
			
			br.close();
			
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return carData;
		
	}


	private void loadSPLinesFromFile(File file) {
		
		try {
			boolean forceReading=false;
			splines=loadSPLinesFromFile(file,forceReading,Renderer3D.SCALE);
			
			//translation to fit view screen
			if(splines.size()>0){


				for (int i = 0; i < splines.size(); i++) {
					SPLine sp = (SPLine) splines.get(i);
					
					
					for (int j = 0; sp.ribs!=null && j < sp.ribs.size(); j++) {
						
						ArrayList nodeRibs=(ArrayList) sp.ribs.get(j);
						
						for (int r = 0; r< nodeRibs.size(); r++) {
							
							Rib rib= (Rib) nodeRibs.get(r);
							for (int k = 0; k < rib.getPoints().length; k++) {
								rib.getPoints()[k].translate(-XFOCUS,+SCREEN_DISTANCE,-YFOCUS);
							}
							
						}
					
					
					}
                     sp.calculate3DMeshes();

				}

			}


		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public DrawObject buildDrawObject(String str,CubicMesh[] objectMeshes,double scale) {
		DrawObject dro=new DrawObject();
		
		String properties0=str.substring(0,str.indexOf("["));
		String properties1=str.substring(str.indexOf("[")+1,str.indexOf("]"));

		StringTokenizer tok0=new StringTokenizer(properties0," "); 
		
		//translate world in the point (XFOCUS,SCREEN_DISTANCE,YFOCUS)
		dro.setX(SCALE*Double.parseDouble(tok0.nextToken())-XFOCUS);
		dro.setY(SCALE*Double.parseDouble(tok0.nextToken())+SCREEN_DISTANCE);
		dro.setZ(SCALE*Double.parseDouble(tok0.nextToken())-YFOCUS);
		
		dro.setIndex(Integer.parseInt(tok0.nextToken()));
		
		
		StringTokenizer tok1=new StringTokenizer(properties1," "); 
		dro.setRotation_angle(Double.parseDouble(tok1.nextToken()));
		dro.setHexColor(tok1.nextToken());
		dro.calculateBase();
		
		return dro;
	}

	public static void main(String[] args) {
		
		generateDefault0();

	}





	void changeCamera(int camera_type) {
		
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

	private void drawAutocars(Autocar[] autocars, Rectangle totalVisibleFieldBounds,
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
	

				xVersor=rotate(aRotation,xVersor);
	        	yVersor=rotate(aRotation,yVersor);
	        	zVersor=rotate(aRotation,zVersor);
	        	zMinusVersor=rotate(aRotation,zMinusVersor);
	        	
	        	rotoTranslate(aRotation,cm,dx,dy,dz);
	        	
	
			}

			decomposeCubicMesh(cm,autocar.texture,roadZbuffer);

			if(!isSkipShading())
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
		
		for (int j = 0; j < cm.xpoints.length; j++) {
			
			Point3D pj=rotoTranslate(aRotation,cm.xpoints[j],cm.ypoints[j],cm.zpoints[j],dx,dy,dz);
			cm.xpoints[j]=pj.x;
			cm.ypoints[j]=pj.y;
			cm.zpoints[j]=pj.z;
		}
	}

	void loadAutocars(File file) {
	

		if(file.exists())	{	
			autocars=Autocar.buildAutocars(file,SCALE);
			
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
		
		if(!isSkipShading()){
			
			for (int i = 0; i < autocarShadowVolume.length; i++) {
				autocarShadowVolume[i]=initShadowVolume(autocars[i].carData.carMesh);
			}
		
		}
		autocarTerrainNormal=new Point3D[autocars.length];
	}

	boolean checkIsWayFree(int new_posx, int new_posy,double newViewDirection,  int autocar_index) {


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
				
				if(getIntersection(objBorder,CAR_BORDER)!=null){
    				return false;
		     	}	
			}

		}


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



	private static void generateDefault0() {
			
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
	
	public boolean isSkipShading() {
		return skipShading;
	}
	
	private class PolyDrawingThread extends Thread{

		private CountDownLatch latch=null;
		private ArrayList<Polygon3D> polyList=new ArrayList<Polygon3D>();
		private int start;
		private PolygonMesh mesh;

		private PolyDrawingThread(CountDownLatch latch,PolygonMesh mesh,int start) {

			this.latch = latch;
			this.start = start;
			this.mesh = mesh;
		}

		@Override
		public void run() {

			for(int j=start;j<terrainSize;j+=3){

				LineData ld= mesh.polygonData.get(j);	

				polyList.add(buildTransformedPolygon3D(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.getLevel()));

			}

			latch.countDown();
		}





		public ArrayList<Polygon3D> getPolyList() {
			return polyList;
		}

	}
		

}
