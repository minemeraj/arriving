package com.main;


import java.awt.Dimension;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import com.Engine;
import com.Point3D;
import com.Polygon3D;
import com.Texture;

public class Autocar extends Thread{
	
	Road road;
	
	public Autocar(int car_width, int car_length,int car_type_index) {
		super();
		this.car_width = car_width;
		this.car_length = car_length;
		this.car_type_index = car_type_index;
	}
	
	public Autocar(int car_width, int car_length) {
		super();
		this.car_width = car_width;
		this.car_length = car_length;
	}
	
	Point3D center=null;

	double fi=0; 
	double steering=0; 
	
	int car_width=0;
	int car_length=0;
	int car_height=0;
	
	double linePosition=-1;
	
	// longitudinal  velocity
	double u=0;
	// lateral  velocity
	double nu=0;
	//line to follow
	Point3D[] car_road=null;
	
	CarData carData=null;
	
	public Texture texture=null;
	
	int car_type_index=0;
	
	boolean isParked=false;

	Date stopTime=null;
	int TIME_OUT=3000;
	
	int autocar_index=-1;


	public void init(double x,double y,double u,double nu,double fi,double steering,double linePosition,Point3D[] car_road){
		
	
		center=new Point3D(x,y,0);
		
		this.u=u;
		this.nu=nu;
		this.fi=fi;
		this.steering=steering;
		this.linePosition=linePosition;
		this.car_road=car_road;
		
		this.carData=Road.loadCarFromFile(new File("lib/cardefault3D_"+car_type_index));
		
		this.car_width=this.carData.getDeltaX2()-this.carData.getDeltaX();
		this.car_length=this.carData.getDeltaY2()-this.carData.getDeltaY();
		this.car_height=this.carData.getDeltaY();
}
	
	public void init(double x,double y,double u,double nu,double fi,double steering,double linePosition){
		
		
		center=new Point3D(x,y,0);
		
		this.u=u;
		this.nu=nu;
		this.fi=fi;
		this.steering=steering;
		this.linePosition=linePosition;
		this.carData=Road.loadCarFromFile(new File("lib/cardefault3D_"+car_type_index));
		
		this.car_width=this.carData.getDeltaX2()-this.carData.getDeltaX();
		this.car_length=this.carData.getDeltaY2()-this.carData.getDeltaY();
		this.car_height=this.carData.getDeltaY();

	}

	public void move(double dt){
		
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
	

		Polygon polyCar=buildCarBox((int)center.x,(int)center.y,(int)center.z);
		
		double position=calculateLinePosition(polyCar);

		if(position/car_width>=0.25 && position/car_width<=0.75){

			linePosition=position;
			
			steering=0;
			
		}
		else if(linePosition>=0) {
			
			if(linePosition>car_width/2.0)
				steering=-1;
			else
				steering=+1;

			
		}
				
		//System.out.println(position);

		if(steering==0){
			
			
		
		}else{
			
			
			fi+=steering*dt*u;
			
		}
		
		
		
		double vx=u*Math.cos(fi);
		double vy=u*Math.sin(fi);

		center.y= (center.y+vy*dt*Road.SPACE_SCALE_FACTOR/Road.SPEED_SCALE);
		center.x= (center.x+vx*dt*Road.SPACE_SCALE_FACTOR/Road.SPEED_SCALE);

	}
	
	
	public int getAutocar_index() {
		return autocar_index;
	}

	public void setAutocar_index(int autocar_index) {
		this.autocar_index = autocar_index;
	}

	public Polygon3D buildCarBox(int xx0, int yy0, int zz0) {
		
		return buildCarBox( xx0,  yy0,zz0,car_length,car_width,fi);
	}
	
	public static Polygon3D buildCarBox(int xx0, int yy0, int zz0,int car_length, int car_width,double fi) {
		
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
	
	public static void rotate(Polygon polyCar,double fi,int x0,int y0) {
		

		double sinfi=Math.sin(fi);
		double cosfi=Math.cos(fi);
		
		for (int j = 0; j < polyCar.npoints; j++) {
			
			double xx=(polyCar.xpoints[j]-x0)*cosfi-(polyCar.ypoints[j]-y0)*sinfi+x0;
			double yy=(polyCar.xpoints[j]-x0)*sinfi+(polyCar.ypoints[j]-y0)*cosfi+y0;
			
			polyCar.xpoints[j]=(int) xx;
			polyCar.ypoints[j]=(int) yy;
			
		}
	
		
	}
	
	private double calculateLinePosition(Polygon p) {
		
		double pos=-1;
		
		Point3D p3=new Point3D(p.xpoints[2],p.ypoints[2],0);
		Point3D p4=new Point3D(p.xpoints[1],p.ypoints[1],0);
		
		
		for (int j = 0; j < car_road.length; j++) {

			Point3D p1=car_road[j];
			Point3D p2=car_road[(j+1)%car_road.length];				
			
			Point3D intersection=calculateLineIntersection(p1,p2,p3,p4);
		
			//System.out.println(p1+","+p2);
			
			if(intersection!=null){
				
				pos=distance(p3.x,p3.y,intersection.x,intersection.y);
				//System.out.println(intersection.x+","+intersection.y);

			
			}
			
		}
	
	
		
		
		return pos;
	}
	
	private double distance(double x0, double y0, double x1, double y1) {
		
		return Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
	}
	
	public static Point3D calculateLineIntersection(Point3D p1,Point3D p2,Point3D p3,Point3D p4) {
		
		
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

	public static Autocar[] buildAutocars(File file) {

		
		Vector autocars=new Vector();
	
		try {
			
			
			Vector roads=new Vector();
			
			BufferedReader br=new BufferedReader(new FileReader(file));


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
					
					Autocar car=(Autocar) autocars.lastElement();
					
					
					double x=Double.parseDouble(vals[0]);
					double y=Double.parseDouble(vals[1]);
					double u=Double.parseDouble(vals[2]);
					double nu=Double.parseDouble(vals[3]);
					double fi=Double.parseDouble(vals[4]);
					double steering=Double.parseDouble(vals[5]);
					double linePosition=-1;
					
					int autoline_index=Integer.parseInt(vals[6]);
					
					car.init( x, y, u, nu, fi, steering, linePosition);

					if(autoline_index>=0){
					
						Point3D[] car_road=(Point3D[]) roads.elementAt(autoline_index);
						car.setCar_road(car_road);
						car.isParked=false;
						
					}else{
						
						car.isParked=true;
						
					}
				}				
				else if(str.startsWith("ROAD")){
					
					str=str.substring(str.indexOf("=")+1);
					StringTokenizer stk=new StringTokenizer(str,"_");
					
					Vector road_points=new Vector();
					
					while(stk.hasMoreTokens()){			
						
						
						String[] vals=stk.nextToken().split(",");

						Point3D p=new Point3D();

						p.x=Double.parseDouble(vals[0]);
						p.y=Double.parseDouble(vals[1]);
						p.z=Double.parseDouble(vals[2]);
						
						
						road_points.add(p);
					}
					
					Point3D[] car_road=new Point3D[road_points.size()];
					
					for (int i = 0; i < road_points.size(); i++) {
						car_road[i]=(Point3D) road_points.elementAt(i);
					}
					roads.add(car_road);
	
					
					
				}


			}

			br.close();
			
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		if(autocars.size()==0)
			return null;
		
		Autocar[] acars=new Autocar[autocars.size()];
		
		for (int i = 0; i < autocars.size(); i++) {
			acars[i]=(Autocar) autocars.elementAt(i);
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
	
	public void run() {
	
		
		while(true){
			
			move(Engine.dtt);
			
			try {
				
				sleep((int)(Engine.dtt*1000));
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		
		
				
		}
		
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
