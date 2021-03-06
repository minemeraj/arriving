package com.main;


import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.Point3D;
import com.Polygon3D;
import com.Texture;

class Autocar {
	
	private Road road;
	
	private Autocar(int car_width, int car_length,int car_type_index) {
		super();
		this.car_width = car_width;
		this.car_length = car_length;
		this.car_type_index = car_type_index;
	}
	
	Point3D center=null;

	double fi=0; 
	private double steering=0; 
	
	int car_width=0;
	int car_length=0;
	int car_height=0;
	
	private double linePosition=-1;
	
	// longitudinal  velocity
	private double u=0;
	// lateral  velocity
	private double nu=0;
	//line to follow
	Point3D[] car_road=null;
	
	CarData carData=null;
	
	Texture texture=null;
	
	int car_type_index=0;
	
	private boolean isParked=false;

	private Date stopTime=null;
	private int TIME_OUT=3000;
	
	private int autocar_index=-1;

	private int NO_TARGET=-1;
	private int current_road_point_target=NO_TARGET;
	
	private void init(double x,double y,double u,double nu,double fi,double steering,double linePosition,double scale){
		
		
		center=new Point3D(scale*x,scale*y,0);
		
		this.u=u;
		this.nu=nu;
		this.fi=fi;
		this.steering=steering;
		this.linePosition=linePosition;
		this.carData=Road.loadCarFromFile(new File("lib/cardefault3D_"+car_type_index),scale);
		
		this.car_width=this.carData.getDeltaX2()-this.carData.getDeltaX();
		this.car_length=this.carData.getDeltaY2()-this.carData.getDeltaY();
		this.car_height=this.carData.getDeltaY();

	}

	void move(double dt){
		
		if(isParked)
			return;
		
		if(stopTime!=null){
			
			
			long currTime=System.currentTimeMillis();
			
			if(currTime-stopTime.getTime()<TIME_OUT){
				return;
			}else{
				
				
				stopTime=null;
			}
			
		}
	
		
		if(road!=null && !road.checkIsWayFree((int)center.x,(int)center.y,fi,autocar_index)){
			stopTime=new Date();
			return;
		}
	
		//new steering:
		
		double deviationAngle=0;
		
		Point3D nearestPoint=null;
		
		if(current_road_point_target==NO_TARGET){		
			nearestPoint=calculateFirstTarget();
		}else{
			nearestPoint=calculateNextTarget();
		}
		if(nearestPoint!=null){
			deviationAngle=calculateSteeringCorrection(nearestPoint);
		}
		//System.out.println(nearestPoint);
		//System.out.println(current_road_point_position);
		
		if(Math.abs(deviationAngle)<0.2){
			
			
		
		}else{
			
			double dfi=dt*u;
			
			if(dfi>Math.abs(deviationAngle)){
				
				fi+=deviationAngle;
				
			}else{
				fi+=dfi*Math.signum(deviationAngle);
				
			}

			
		}

		double vx=u*Math.cos(fi);
		double vy=u*Math.sin(fi);
		
		center.y= (center.y+vy*dt*Road.SPACE_SCALE_FACTOR*Renderer3D.SCALE/Road.SPEED_SCALE);
		center.x= (center.x+vx*dt*Road.SPACE_SCALE_FACTOR*Renderer3D.SCALE/Road.SPEED_SCALE);

	}
	
	


	public int getAutocar_index() {
		return autocar_index;
	}

	public void setAutocar_index(int autocar_index) {
		this.autocar_index = autocar_index;
	}

	Polygon3D buildCarBox(int xx0, int yy0, int zz0) {
		
		return buildCarBox( xx0,  yy0,zz0,car_length,car_width,fi);
	}
	
	static Polygon3D buildCarBox(int xx0, int yy0, int zz0,int car_length, int car_width,double fi) {
		
		int[] cx=new int[4]; 
		int[] cy=new int[4];
		int[] cz=new int[4];
		
		cx[0]=xx0-car_length/2;
		cy[0]=yy0-car_width/2;
		cz[0]=zz0;

		cx[1]=xx0+car_length/2;
		cy[1]=yy0-car_width/2;
		cz[1]=zz0;
		
		cx[2]=xx0+car_length/2;
		cy[2]=yy0+car_width/2;
		cz[2]=zz0;
		
		cx[3]=xx0-car_length/2;
		cy[3]=yy0+car_width/2;
		cz[3]=zz0;
		
		Polygon3D poly=new Polygon3D(4,cx,cy,cz);

		rotate(poly,fi,xx0,yy0);
		
		return poly;
	}
	
	private static void rotate(Polygon polyCar,double fi,int x0,int y0) {
		

		double sinfi=Math.sin(fi);
		double cosfi=Math.cos(fi);
		
		for (int j = 0; j < polyCar.npoints; j++) {
			
			double xx=(polyCar.xpoints[j]-x0)*cosfi-(polyCar.ypoints[j]-y0)*sinfi+x0;
			double yy=(polyCar.xpoints[j]-x0)*sinfi+(polyCar.ypoints[j]-y0)*cosfi+y0;
			
			polyCar.xpoints[j]=(int) xx;
			polyCar.ypoints[j]=(int) yy;
			
		}
	
		
	}
	/**
	 * 
	 * Calculate the nearer point along the road orbit
	 * 
	 * @return
	 */
	private Point3D calculateFirstTarget(){

		Point3D nearestPoint=null;
		double minDistance=-1;

		//search only point in front of the car
		//Point3D direction=getDirection();

		for (int j = 0; j < car_road.length; j++) {

			Point3D p1=car_road[j];
			double distance=Point3D.distance(p1.x, p1.y, 0,center.x,center.y,0);

			/*double cos=Point3D.calculateDotProduct(p1.substract(center), direction);

			if(cos<0)
				continue;*/

			if(nearestPoint==null){

				nearestPoint=p1;
				minDistance=distance;
				current_road_point_target=j;

			}else if(minDistance>distance){

				nearestPoint=p1;
				minDistance=distance;
				current_road_point_target=j;
			}
		}	

		current_road_point_target=getOrbitTarget(nearestPoint,current_road_point_target);

		return car_road[current_road_point_target];
	}
	/**
	 * 
	 * Calculate the nearer point along the road orbit,
	 * from an already available point
	 * 
	 * @return
	 */
	private Point3D calculateNextTarget() {
		
		Point3D pCurrentTarget=car_road[current_road_point_target];
		
		double distance=Point3D.distance(pCurrentTarget.x, pCurrentTarget.y, 0,center.x,center.y,0);

		//exclude inner or current point
		if(distance>car_length)
			return pCurrentTarget;
		
		current_road_point_target=getOrbitTarget(pCurrentTarget,current_road_point_target);
		
		return car_road[current_road_point_target];
		
		

	}
	
	private int getOrbitTarget(Point3D pCurrent,int reference_road_point_target) { 
		
		Point3D direction=getDirection();
		
		int nextIndex=(reference_road_point_target+1)%car_road.length;
		int prevIndex=NO_TARGET;
		if(reference_road_point_target-1>=0)
			prevIndex=reference_road_point_target-1;
		else
			prevIndex=car_road.length-1;
			
		Point3D pNext=car_road[nextIndex];
		Point3D pPrev=car_road[prevIndex];	
		
		double cos1=Point3D.calculateDotProduct(pNext.substract(pCurrent), direction);
		double cos2=Point3D.calculateDotProduct(pPrev.substract(pCurrent), direction);
		
		if(cos1>=cos2){
			
			reference_road_point_target=nextIndex;
			return nextIndex;
			
		}else{
			
			
			reference_road_point_target=prevIndex;
			return prevIndex;
		}
	}

	private Point3D getDirection() {
		
		Point3D direction=new Point3D(Math.cos(fi),Math.sin(fi),0);
		
		return direction;
	}


	private double calculateSteeringCorrection(Point3D targetPoint){
		
		if(targetPoint==null)
			return 0;

		Point3D direction=getDirection();
		
		Point3D relativeTarget=targetPoint.substract(center);
		
		Point3D crossProduct=Point3D.calculateCrossProduct( direction,relativeTarget);
		
		
		double angle=Point3D.calculateNorm(crossProduct)/(Point3D.calculateNorm(direction)*Point3D.calculateNorm(relativeTarget));
		

		if(crossProduct.z>0){
			
			return +angle;
			
		}else if(crossProduct.z<0){
			
			return -angle;
			
		}
		
		return angle;
		
	}
	@Deprecated
	private double calculateLinePosition(Polygon p) {
		
		double pos=-1;
		
		Point3D p3=new Point3D(p.xpoints[2],p.ypoints[2],0);
		Point3D p4=new Point3D(p.xpoints[1],p.ypoints[1],0);
		
		
		for (int j = 0; j < car_road.length; j++) {

			Point3D p1=car_road[j];
			Point3D p2=car_road[(j+1)%car_road.length];				
			
			Point3D intersection=calculateLineIntersection(p1,p2,p3,p4);
			
			if(intersection!=null){
				
				pos=distance(p3.x,p3.y,intersection.x,intersection.y);

			
			}
			
		}
	
	
		
		
		return pos;
	}
	
	private double distance(double x0, double y0, double x1, double y1) {
		
		return Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
	}
	
	static Point3D calculateLineIntersection(Point3D p1,Point3D p2,Point3D p3,Point3D p4) {
		
		
		double det=(p4.y-p3.y)*(p2.x-p1.x)-(p4.x-p3.x)*(p2.y-p1.y);
		double ua=(p4.x-p3.x)*(p1.y-p3.y)-(p4.y-p3.y)*(p1.x-p3.x);
		double ub=(p2.x-p1.x)*(p1.y-p3.y)-(p2.y-p1.y)*(p1.x-p3.x);
		
		if(det==0)
			return null;
		
		double xx=p1.x+ua/det*(p2.x-p1.x);
		double yy=p1.y+ua/det*(p2.y-p1.y);
		
	
		
		Point3D intersection=new Point3D(xx,yy,0);
		
		if (intersection.x < Math.min(p3.x,p4.x) || intersection.x  > Math.max(p3.x,p4.x))
			return null;
		
		if (intersection.y < Math.min(p3.y,p4.y) || intersection.y  > Math.max(p3.y,p4.y))
			return null;
		
		
		if (intersection.x < Math.min(p1.x,p2.x) || intersection.x  > Math.max(p1.x,p2.x))
			return null;
		
		if (intersection.y < Math.min(p1.y,p2.y) || intersection.y  > Math.max(p1.y,p2.y))
			return null;
		
		return intersection;
	}

	static Autocar[] buildAutocars(File file,double scale) {

		
		ArrayList<Autocar> autocars=new ArrayList<Autocar>();
		
		
		FileReader fr=null;
		BufferedReader br=null;
		
		try {
			
			
			ArrayList<Point3D[]> roads=new ArrayList<Point3D[]>();
			
			fr=new FileReader(file);
			br=new BufferedReader(fr);


			String str=null;

			while((str=br.readLine())!=null){
				
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

			
				if(str.startsWith("DATA")){
					
					str=str.substring(str.indexOf("=")+1);	
					
					String[] vals=str.split(",");
					
					int type=Integer.parseInt(vals[0]);
					
					File carFile=new File("lib/cardefault3D_"+type);
					
					if(!carFile.exists())
						type=0;
										
					Autocar car=new Autocar(0,0,type);
					
					autocars.add(car);
				}
				else if(str.startsWith("INIT")){
					
					str=str.substring(str.indexOf("=")+1);							
					String[] vals=str.split(",");
					
					Autocar car=(Autocar) autocars.get(autocars.size()-1);
					
					
					double x=Double.parseDouble(vals[0]);
					double y=Double.parseDouble(vals[1]);
					double u=Double.parseDouble(vals[2]);
					double nu=Double.parseDouble(vals[3]);
					double fi=Double.parseDouble(vals[4]);
					double steering=Double.parseDouble(vals[5]);
					double linePosition=-1;
					
					int autoline_index=Integer.parseInt(vals[6]);
					
					car.init( x, y, u, nu, fi, steering, linePosition,scale);

					if(autoline_index>=0){
					
						Point3D[] car_road=(Point3D[]) roads.get(autoline_index);
						car.setCar_road(car_road);
						car.isParked=false;
						
					}else{
						
						car.isParked=true;
						
					}
				}				
				else if(str.startsWith("ROAD")){
					
					str=str.substring(str.indexOf("=")+1);
					StringTokenizer stk=new StringTokenizer(str,"_");
					
					ArrayList road_points=new ArrayList();
					
					while(stk.hasMoreTokens()){			
						
						
						String[] vals=stk.nextToken().split(",");

						Point3D p=new Point3D();

						p.x=scale*Double.parseDouble(vals[0]);
						p.y=scale*Double.parseDouble(vals[1]);
						p.z=scale*Double.parseDouble(vals[2]);
						
						
						road_points.add(p);
					}
					
					Point3D[] car_road=new Point3D[road_points.size()];
					
					for (int i = 0; i < road_points.size(); i++) {
						car_road[i]=(Point3D) road_points.get(i);
					}
					roads.add(car_road);
	
					
					
				}


			}

		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		if(autocars.size()==0)
			return null;
		
		Autocar[] acars=new Autocar[autocars.size()];
		
		for (int i = 0; i < autocars.size(); i++) {
			acars[i]=(Autocar) autocars.get(i);
			acars[i].setAutocar_index(i);
		}
		
		return acars;
	}

	public Point3D[] getCar_road() {
		return car_road;
	}

	public void setCar_road(Point3D[] car_road) {
		this.car_road = car_road;
	}
	
	
	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}
	
	public CarData getCarData() {
		return carData;
	}

	public void setCarData(CarData carData) {
		this.carData = carData;
	}

	public int getCar_type_index() {
		return car_type_index;
	}

	public void setCar_type_index(int car_type_index) {
		this.car_type_index = car_type_index;
	}
	
	public boolean isParked() {
		return isParked;
	}

	public void setParked(boolean isParked) {
		this.isParked = isParked;
	}
	
	
}
