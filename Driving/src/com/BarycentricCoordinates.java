package com;

public class BarycentricCoordinates {
	
	Point3D p0=null;
	Point3D p1=null;
	Point3D p2=null;
	
	Point3D n=null;
	double inv_nSquare=0;
	private Point3D nda;
	private Point3D ndb;
	private Point3D ndc;
	

	public static void main(String[] args) {
		
		
		BarycentricCoordinates bc=new BarycentricCoordinates(
				new Point3D(0,0.0,0.0),
				new Point3D(1.0,0.0,0.0),
				new Point3D(0,2.0,0.0));
		
		Point3D p=new Point3D(0.0,1.0,0.0);
		System.out.println("p0="+p);
		
		Point3D p0=bc.getBarycentricCoordinates(p);
		System.out.println("pc="+p0);
		
		Point3D p1=bc.getRealCoordinates(p0);
		System.out.println("p1="+p1);
		
	}
	
	public BarycentricCoordinates(Point3D p0,Point3D p1,Point3D p2) {
		
		
		
		this.p0=p0;
		this.p1=p1;
		this.p2=p2;
		
		n=Point3D.calculateCrossProduct(p1.substract(p0), p2.substract(p0));
		inv_nSquare=1.0/Point3D.calculateDotProduct(n,n);
		nda=p2.substract(p1);
		ndb=p0.substract(p2);
		ndc=p1.substract(p0);
	}
	
	public Point3D getBarycentricCoordinates(Point3D p){
		
				
		Point3D pc=new Point3D();
	
		
		Point3D na=Point3D.calculateCrossProduct(nda, p.substract(p1));
		Point3D nb=Point3D.calculateCrossProduct(ndb, p.substract(p2));
		Point3D nc=Point3D.calculateCrossProduct(ndc, p.substract(p0));
		
		pc.x=Point3D.calculateDotProduct(n,na)*inv_nSquare;
		pc.y=Point3D.calculateDotProduct(n,nb)*inv_nSquare;
		pc.z=Point3D.calculateDotProduct(n,nc)*inv_nSquare;
		
		return pc;
	}
	
	public Point3D getRealCoordinates(Point3D p){
		
		Point3D pr=new Point3D();
		pr.x=p.x*(p0.x)+p.y*p1.x+(1-p.x-p.y)*p2.x;
		pr.y=p.x*(p0.y)+p.y*p1.y+(1-p.x-p.y)*p2.y;
		pr.z=p.x*(p0.z)+p.y*p1.z+(1-p.x-p.y)*p2.z;
		
		return pr;
	}

}
