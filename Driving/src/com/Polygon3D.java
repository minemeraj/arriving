package com;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class Polygon3D  extends Polygon implements Cloneable{


	public int[] 	zpoints=null;
	private String hexColor="FFFFFF";
	private int index=0;
	private int level=0;
	
	private double shadowCosin=1;
	
	//textures point:
	public int[] xtpoints=null;
	public int[] ytpoints=null;
	
	private String data=null;
	
	private boolean isFilledWithWater=false;
	private boolean isWaterPolygon=false;

	
	public Polygon3D(int npoints, int[] xpoints, int[] ypoints, int[] zpoints,int[] xtpoints, int[] ytpoints) {
		
		super(xpoints,ypoints,npoints);
		this.zpoints = zpoints;
		
		this.xtpoints = xtpoints;
		this.ytpoints = ytpoints;
	}

	public Polygon3D(int npoints, int[] xpoints, int[] ypoints, int[] zpoints) {
		
		super(xpoints,ypoints,npoints);
		this.zpoints = zpoints;
		
		this.xtpoints = new int[npoints];
		this.ytpoints = new int[npoints];
	}

	public Polygon3D(int npoints) {
		this.xpoints = new int[npoints];
		this.ypoints = new int[npoints];
		this.zpoints = new int[npoints];
		this.xtpoints = new int[npoints];
		this.ytpoints = new int[npoints];
		this.npoints=npoints;
	}
	
	public Polygon3D(ArrayList points) {

		this.npoints=points.size();
		this.xpoints = new int[this.npoints];
		this.ypoints = new int[this.npoints];
		this.zpoints = new int[this.npoints];
		this.xtpoints = new int[npoints];
		this.ytpoints = new int[npoints];


		for(int i=0;i<this.npoints;i++){

			Point3D p=(Point3D) points.get(i);

			this.xpoints[i]=(int) p.x;
			this.ypoints[i]=(int) p.y;
			this.zpoints[i]=(int) p.z;
			
			this.xtpoints[i]=(int) p.x;
			this.ytpoints[i]=(int) p.y;
		}


	}
	
	public void addPoint(Point3D p) {
		
		addPoint((int)p.x,(int)p.y,(int)p.z);
	}
	
	Point3D getPoint(int index){
		
		if(index>=npoints)
			return null;
		
		return new Point3D(xpoints[index],ypoints[index],zpoints[index]);
		
	}
	
	Point3D getTexturePoint(int index){
		
		if(index>=npoints)
			return null;
		
		return new Point3D(xtpoints[index],ytpoints[index],0);
		
	}
	
	public void addPoint(int x, int y,int z, int xt,int yt) {
		
		Polygon3D new_pol=new Polygon3D(this.npoints+1);

		for(int i=0;i<this.npoints;i++){
			new_pol.xpoints[i]=this.xpoints[i];
			new_pol.ypoints[i]=this.ypoints[i];
			new_pol.zpoints[i]=this.zpoints[i];
			new_pol.xtpoints[i]=this.xtpoints[i];
			new_pol.ytpoints[i]=this.ytpoints[i];
		}
		new_pol.xpoints[this.npoints]=x;
		new_pol.ypoints[this.npoints]=y;
		new_pol.zpoints[this.npoints]=z;
		new_pol.xtpoints[this.npoints]=xt;
		new_pol.ytpoints[this.npoints]=yt;
		
		this.setNpoints(new_pol.npoints);
		this.setXpoints(new_pol.xpoints);
		this.setYpoints(new_pol.ypoints);
		this.setZpoints(new_pol.zpoints);
		this.setXtpoints(new_pol.xtpoints);
		this.setYtpoints(new_pol.ytpoints);
		
	}
	
	public void addPoint(int x, int y,int z) {
		
	
		addPoint(x,  y, z,0,0);
		
	}
	@Override
	public Polygon3D clone(){

		return buildTranslatedPolygon(0,0,0);

	}


	public Polygon3D() {

	}
	
	public static Polygon3D[] divideIntoTriangles(Polygon3D pol){
		
		if(pol.npoints<3)
			return new Polygon3D[0];
		
		Polygon3D[] triangles=new Polygon3D[pol.npoints-2];
		
		if(pol.npoints==3){
			triangles[0]=pol;
			return triangles;
			
		}
		
		for(int i=1;i<pol.npoints-1;i++){
			
			int[] xpoints = new int[3];
			int[] ypoints = new int[3];
			int[] zpoints = new int[3];
			int[] xtpoints = new int[3];
			int[] ytpoints = new int[3];
			
			xpoints[0]=pol.xpoints[0];
			ypoints[0]=pol.ypoints[0];
			zpoints[0]=pol.zpoints[0];
			xtpoints[0]=pol.xtpoints[0];
			ytpoints[0]=pol.ytpoints[0];
			
			xpoints[1]=pol.xpoints[i];
			ypoints[1]=pol.ypoints[i];
			zpoints[1]=pol.zpoints[i];
			xtpoints[1]=pol.xtpoints[i];
			ytpoints[1]=pol.ytpoints[i];
			
			xpoints[2]=pol.xpoints[i+1];
			ypoints[2]=pol.ypoints[i+1];
			zpoints[2]=pol.zpoints[i+1];
			xtpoints[2]=pol.xtpoints[i+1];
			ytpoints[2]=pol.ytpoints[i+1];
			
			Polygon3D triangle=new Polygon3D(3,xpoints,ypoints,zpoints,xtpoints,ytpoints);
			triangle.setShadowCosin(pol.getShadowCosin());
			triangle.setLevel(pol.getLevel());			
			triangle.setIsFilledWithWater(pol.isFilledWithWater());
			triangle.setWaterPolygon(pol.isWaterPolygon());
			
			triangles[i-1]=triangle;
			
		}
		
		return triangles;
		
	}
	
	public static Polygon3D extractSubPolygon3D(Polygon3D pol,int numAngles,int startAngle){



		int[] xpoints = new int[numAngles];
		int[] ypoints = new int[numAngles];
		int[] zpoints = new int[numAngles];

		int counter=0;

		for(int i=startAngle;i<numAngles+startAngle;i++){

			xpoints[counter]=pol.xpoints[i%pol.npoints];
			ypoints[counter]=pol.ypoints[i%pol.npoints];
			zpoints[counter]=pol.zpoints[i%pol.npoints];

			counter++;	
		}

		Polygon3D new_pol = new Polygon3D(numAngles,xpoints,ypoints,zpoints);
		new_pol.setIsFilledWithWater(pol.isFilledWithWater());
		new_pol.setWaterPolygon(pol.isWaterPolygon());
		new_pol.setLevel(pol.getLevel());
		new_pol.setHexColor(pol.getHexColor());
		return new_pol;
	}

	public static Polygon3D fromAreaToPolygon2D(Area area){

		Polygon3D pol=new Polygon3D();

		PathIterator pathIter = area.getPathIterator(null);

		while(!pathIter.isDone()){

			double[] coords = new double[6];

			int type=pathIter.currentSegment(coords);
			double px= coords[0];
			double py= coords[1];	


			if(type==PathIterator.SEG_MOVETO || type==PathIterator.SEG_LINETO)
			{		

				pol.addPoint((int)px,(int)py);
				//System.out.println(x+" "+y);
			}
			pathIter.next();
		}
		Polygon3D pol2=removeRedundant(pol);
		return pol2;
	}


	private static Polygon3D removeRedundant(Polygon3D pol) {

		boolean redundant=false;
		
		if(pol.npoints==0)
			return pol;

		if(pol.xpoints[0]==pol.xpoints[pol.npoints-1]
		                               &&    pol.ypoints[0]==pol.ypoints[pol.npoints-1]                            
		)	
			redundant=true;

		if(!redundant)
			return pol;
		else{


			Polygon3D new_pol=new Polygon3D(pol.npoints-1);

			for(int i=0;i<pol.npoints-1;i++){

				new_pol.xpoints[i]=pol.xpoints[i];
				new_pol.ypoints[i]=pol.ypoints[i];

			}

			return new_pol;

		}

	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();

		if(zpoints!=null)

			for(int i=0;i<npoints;i++){
				sb.append(xpoints[i]+","+ypoints[i]+","+zpoints[i]+" ");

			}

		else 

			for(int i=0;i<npoints;i++){
				sb.append(xpoints[i]+","+ypoints[i]+" ");

			}	

		return sb.toString();
	}
	
	public static boolean isIntersect(Polygon p_in, Rectangle bounds) {
		Rectangle polBounds =p_in.getBounds();
		
		if(polBounds.getMaxY()>=bounds.getMinY() &&
			polBounds.getMinY()<=bounds.getMaxY() &&
			polBounds.getMaxX()>=bounds.getMinX() &&
			polBounds.getMinX()<=bounds.getMaxX() 
		)
			return true;
		
		return false;
	}
	
	public static boolean isIntersect(Point3D p, Rectangle bounds) {

		if(p.getY()>=bounds.getMinY() &&
				p.getY()<=bounds.getMaxY() &&
				p.getX()>=bounds.getMinX() &&
				p.getX()<=bounds.getMaxX() 
				)
			return true;

		return false;
	}

	public static Polygon3D clipPolygon3DInY(Polygon3D  p_old,int y){


		
		ArrayList<Point3D> newPoints=new ArrayList<Point3D>();


		for(int i=0;i<p_old.npoints;i++){



			int x1=p_old.xpoints[i];
			int y1=p_old.ypoints[i];
			int z1=p_old.zpoints[i];
			
			int ii=i+1;
			if(ii==p_old.npoints)
				ii=0;


			int x2=p_old.xpoints[ii];
			int y2=p_old.ypoints[ii];
			int z2=p_old.zpoints[ii];
			
			
			if((y1-y)>=0 && (y2-y)>=0){
				
				newPoints.add(new Point3D(x1,y1,z1));
							
			}
			else if(((y1-y)<0 && (y2-y)>=0) || ((y1-y)>=0 && (y2-y)<0)){
				
				if((y1-y)>=0)
				   newPoints.add(new Point3D(x1,y1,z1));
				
				if(y1!=y2){
					
					double l=(y-y1)*1.0/(y2-y1);
					double yn=y;
					double zn=z1+l*(z2-z1);
					double xn=x1+l*(x2-x1);
					
					newPoints.add(new Point3D(xn,yn,zn));
					
				}
			}
			

		}
		
		Polygon3D p_new=new Polygon3D(newPoints);
		p_new.setShadowCosin(p_old.getShadowCosin());
		p_new.setLevel(p_old.getLevel());
		p_new.setIsFilledWithWater(p_old.isFilledWithWater());
		p_new.setWaterPolygon(p_old.isWaterPolygon());
		return p_new;
	}
	
	public static Point3D findNormal(Polygon3D pol){

		Point3D p0=new Point3D(pol.xpoints[0],pol.ypoints[0],pol.zpoints[0]);

		Point3D p1=new Point3D(pol.xpoints[1],pol.ypoints[1],pol.zpoints[1]);
		Point3D p2=new Point3D(pol.xpoints[2],pol.ypoints[2],pol.zpoints[2]);

		Point3D vector1=p1.substract(p0); 
		Point3D vector2=p2.substract(p0);

		

		Point3D normal=Point3D.calculateCrossProduct(vector1,vector2);
		
		return normal;
	}


	public boolean hasInsidePoint(double x,double y){

		for(int i=0;i<npoints;i++){

			AnalyticLine line=new AnalyticLine(xpoints[i],ypoints[i],xpoints[(i+1)%npoints],ypoints[(i+1)%npoints]);


			double valPoint=line.signum(x,y);

			//near the border the precise calcutation is very difficult
			if(Math.abs(valPoint)<0.01) 
				valPoint=0;

			for(int j=2;j<npoints;j++){

				double valVertex = line.signum(xpoints[(j+i)%npoints],ypoints[(j+i)%npoints]);


				if(valVertex*valPoint<0)
					return false;
			}

		}

		return true;
	}


	


	private class AnalyticLine{


		private double a;
		private double b;
		private double c;

		private AnalyticLine(double x1, double y1, double x0,double y0) {
			super();
			this.a = (y0-y1);
			this.b = -(x0-x1);
			this.c = (x0*y1-y0*x1);
		}

		private double signum(double x,double y){

			return a*x+b*y+c;

		}


	}

	public static void main(String[] args) {

		int[] cx=new int[4];
		int[] cy=new int[4];
		int[] cz=new int[4];


		cx[0]=10;
		cy[0]=10;
		cx[1]=0;
		cy[1]=50;
		cx[2]=50;
		cy[2]=60;
		cx[3]=50;
		cy[3]=-10;



		int[] cx1=new int[3];
		int[] cy1=new int[3];
		int[] cz1=new int[3];

		Polygon3D p1=new Polygon3D(4,cx,cy,cz);

		cx1[0]=10;
		cy1[0]=-20;
		cx1[1]=50;
		cy1[1]=40;
		cx1[2]=30;
		cy1[2]=-40;

		Polygon3D p2=new Polygon3D(3,cx1,cy1,cz1);

		//System.out.println(p2.hasInsidePoint(20,0));
		//System.out.println(p2.hasInsidePoint(30,40));
		//System.out.println(p2.hasInsidePoint(40,10));

		/*Area out=new Area(p1); 
		Area in=new Area(p2);


		Polygon3D p3=fromAreaToPolygon2D(out);

		System.out.println(p3);
		out.intersect(in);
		Polygon3D p_res=clipPolygon3D(p1,p2);*/
		System.out.println(p2);
		Polygon3D p3=clipPolygon3DInY(p2,0);
		System.out.println(p3);
	}
	
	
	public Polygon3D buildTranslatedPolygon(double dx,double dy,double dz){

		Polygon3D translatedPolygon=new Polygon3D(this.npoints);

		for(int i=0;i<this.npoints;i++){

			translatedPolygon.xpoints[i]=(int) (this.xpoints[i]+dx);
			translatedPolygon.ypoints[i]=(int) (this.ypoints[i]+dy);
			translatedPolygon.zpoints[i]=(int) (this.zpoints[i]+dz);

		}

		return translatedPolygon;

	}
	
	public static Polygon3D buildPrismIFace(Polygon3D upperBase,Polygon3D lowerBase,int i){

		int n=upperBase.npoints;

		int[] cx=new int[4];
		int[] cy=new int[4];
		int[] cz=new int[4];
		
		
		cx[0]=upperBase.xpoints[i%n];
		cy[0]=upperBase.ypoints[i%n];
		cz[0]=upperBase.zpoints[i%n];

		cx[1]=upperBase.xpoints[(i+1)%n];
		cy[1]=upperBase.ypoints[(i+1)%n];
		cz[1]=upperBase.zpoints[(i+1)%n];

		cx[2]=lowerBase.xpoints[(i+1)%n];
		cy[2]=lowerBase.ypoints[(i+1)%n];
		cz[2]=lowerBase.zpoints[(i+1)%n];

		cx[3]=lowerBase.xpoints[i%n];
		cy[3]=lowerBase.ypoints[i%n];
		cz[3]=lowerBase.zpoints[i%n];
		

		
		

		Polygon3D base=new Polygon3D(4,cx,cy,cz);

		return base;

	}
	
	 

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setNpoints(int npoints) {
		this.npoints = npoints;
	}



	public int[] getXpoints() {
		return xpoints;
	}

	public void setXpoints(int[] xpoints) {
		this.xpoints = xpoints;
	}

	public int[] getYpoints() {
		return ypoints;
	}

	public void setYpoints(int[] ypoints) {
		this.ypoints = ypoints;
	}

	public int[] getZpoints() {
		return zpoints;
	}

	public void setZpoints(int[] zpoints) {
		this.zpoints = zpoints;
	}

	public static Point3D findCentroid(Polygon3D p3d) {
		
		
		double x=0;
		double y=0;
		double z=0;

		int n=p3d.npoints;
		for (int i = 0; i <n;  i++) {
           x+=p3d.xpoints[i];	
           y+=p3d.ypoints[i];	
           z+=p3d.zpoints[i];	
		}

        return new Point3D(x/n,y/n,z/n);

	}
	
	
	public static Point3D findCentroid(Polygon p3d) {
		
		
		double x=0;
		double y=0;
		double z=0;

		int n=p3d.npoints;
		for (int i = 0; i <n;  i++) {
           x+=p3d.xpoints[i];	
           y+=p3d.ypoints[i];	

		}

        return new Point3D(x/n,y/n,z/n);

	}
	
	public static void rotate(Polygon p3d, Point3D p0,
			double rotation_angle) {

		int n=p3d.npoints;
		
		double ct=Math.cos(rotation_angle);
		double st=Math.sin(rotation_angle);
		
		for (int i = 0; i <n;  i++) {
	           double xx=p3d.xpoints[i];
	           double yy=p3d.ypoints[i];
	           
	           p3d.xpoints[i]=(int) (ct*(xx-p0.x)-st*(yy-p0.y)+p0.x);
	           p3d.ypoints[i]=(int) (st*(xx-p0.x)+ct*(yy-p0.y)+p0.y);	

			}
		
	}

	public double getShadowCosin() {
		return shadowCosin;
	}

	public void setShadowCosin(double shadowCosin) {
		this.shadowCosin = shadowCosin;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int[] getXtpoints() {
		return xtpoints;
	}

	public void setXtpoints(int[] xtpoints) {
		this.xtpoints = xtpoints;
	}

	public int[] getYtpoints() {
		return ytpoints;
	}

	public void setYtpoints(int[] ytpoints) {
		this.ytpoints = ytpoints;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isFilledWithWater() {
		return isFilledWithWater;
	}

	public void setIsFilledWithWater(boolean hasWater) {
		this.isFilledWithWater = hasWater;
	}

	public boolean isWaterPolygon() {
		return isWaterPolygon;
	}

	public void setWaterPolygon(boolean isWaterPolygon) {
		this.isWaterPolygon = isWaterPolygon;
	}



}
