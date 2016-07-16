package com;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class Point3D {


	public double x;
	public double y;
	public double z;

	public double p_x;
	public double p_y;
	public double p_z;
	
	public double texture_x;
	public double texture_y;
	
	Object data=null;
	
	public Point3D(double x, double y, double z, double pX, double pY,
			double pZ, double textureX, double textureY) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		p_x = pX;
		p_y = pY;
		p_z = pZ;
		texture_x = textureX;
		texture_y = textureY;
	}
	
	


	public boolean isSelected=false;




	public Point3D(double x, double y, double z, double p_x, double p_y,
			double p_z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.p_x = p_x;
		this.p_y = p_y;
		this.p_z = p_z;
	}


	public Point3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public Point3D() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals(Point3D p){
		
		return this.x==p.x && this.y==p.y && this.z==p.z;
		
	}
	
	@Override
	public Point3D clone()  {

		Point3D p=new Point3D(this.x,this.y,this.z,this.p_x,this.p_y,this.p_z,this.texture_x,this.texture_y);
		p.setData(getData());
		p.setSelected(isSelected());
		return p;
	}
	

	
	public boolean isSelected() {
		return isSelected;
	}


	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public static double calculateCosin(Point3D a, Point3D b) {

		double prod=-(calculateSquareNorm(b.substract(a))-calculateSquareNorm(a)-calculateSquareNorm(b))
		/(2*calculateNorm(a)*calculateNorm(b));

		return prod;
	}

	public static double calculateDotProduct(Point3D a,
			Point3D b) {

		return a.x*b.x+a.y*b.y+a.z*b.z;
	}

	public static double calculateNorm(Point3D a) {

		return Math.sqrt(calculateDotProduct(a,a));
		
	}

	private static double calculateSquareNorm(Point3D a) {

		return calculateDotProduct(a,a);
	}

	private static double distance(Point3D a,Point3D b){


		return calculateNorm(a.substract(b));
	}


	public static double distance(double x1,double y1,double z1,double x2,double y2,double z2){


		return distance(new Point3D (x1,y1,z1),new Point3D (x2,y2,z2));
	}


	public Point3D calculateVersor(){

		double norm=calculateNorm(this);
		if(norm==0)
			return new Point3D(0,0,0);
		double i_norm=1.0/norm;
		Point3D versor=new Point3D(this.x*i_norm,this.y*i_norm,this.z*i_norm);

		return versor;
	}

	public static Point3D calculateCrossProduct(Point3D a,
			Point3D b) {

		double x=a.y*b.z-b.y*a.z;
		double y=b.x*a.z-a.x*b.z;
		double z=a.x*b.y-b.x*a.y;

		Point3D pRes=new Point3D(x,y,z);

		return pRes;
	}

	public Point3D substract(Point3D p0) {

		Point3D pRes=new Point3D(this.x-p0.x,this.y-p0.y,this.z-p0.z);

		return pRes;
	}
	
	public Point3D sum(Point3D p0) {

		Point3D pRes=new Point3D(this.x+p0.x,this.y+p0.y,this.z+p0.z);

		return pRes;
	}
	
	public Point3D multiply(Double factor) {

		
		Point3D pRes=new Point3D(this.x*factor,this.y*factor,this.z*factor);

		return pRes;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}


	public double getP_z() {
		return p_z;
	}


	public void setP_z(double p_z) {
		this.p_z = p_z;
	}

	public static double foundXIntersection(Point3D p1, Point3D p2,
			double y) {

		if(p2.y-p1.y<1 && p2.y-p1.y>-1)
			return p1.x;

		return p1.x+((p2.x-p1.x)*(y-p1.y))*(1.0/(p2.y-p1.y));
	

	}
	
	public static Point3D foundPX_PY_PZ_TEXTURE_Intersection(Point3D pstart, Point3D pend,
			double y) {
		
		Point3D intersect=new Point3D(); 

		double i_pstart_p_y=1.0/(pstart.p_y);
		double i_end_p_y=1.0/(pend.p_y);
		
		double l=(y-pstart.y)/(pend.y-pstart.y);
	
		double yi=1.0/((1-l)*i_pstart_p_y+l*i_end_p_y);
		
		intersect.p_x= ((1-l)*pstart.p_x*i_pstart_p_y+l*pend.p_x*i_end_p_y)*yi;
		intersect.p_y=  1.0/((1-l)*i_pstart_p_y+l*i_end_p_y);		
		intersect.p_z=  ((1-l)*pstart.p_z*i_pstart_p_y+l*pend.p_z*i_end_p_y)*yi;
		
		intersect.texture_x=  ((1-l)*pstart.texture_x*i_pstart_p_y+l*pend.texture_x*i_end_p_y)*yi;
		intersect.texture_y=  ((1-l)*pstart.texture_y*i_pstart_p_y+l*pend.texture_y*i_end_p_y)*yi;
		
		return intersect;

	}
	
	public void rotate(double x0, double y0,double cos, double sin ) {
		
		double xx=this.x;
		double yy=this.y;
		double zz=this.z;
		
		this.x=x0+(xx-x0)*cos-(yy-y0)*sin;
		this.y=y0+(yy-y0)*cos+(xx-x0)*sin;
	}

    public void translate(double dx, double dy, double dz) {
		
		setX(this.getX()+dx);
    	setY(this.getY()+dy);
    	setZ(this.getZ()+dz);
		
	}
	
	public void setTexurePositions(double textureX, double textureY){
		
		texture_x = textureX;
		texture_y = textureY;
	}

	public void setTexurePositions(Point3D p){
		
		texture_x = p.x;
		texture_y = p.y;
	}
	
	@Override
	public String toString() {

		return x+" "+y+" "+z;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}

}
