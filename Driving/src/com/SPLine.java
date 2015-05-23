package com;

import java.util.Vector;

public class SPLine {
	
	Point3D starPoint=null;
	Point3D starTangent=null;
	Point3D endPoint=null;
	Point3D endTangent=null;
	
	PolygonMesh mesh=null;
	
	public SPLine(Point4D p0){
		
		
		int index=0;
		setStarPoint(p0);
		
		double x=p0.x;
		double y=p0.y;
		
		Point4D p1=new Point4D(x+100,y,0,LineData.GREEN_HEX,index);
		Point4D p2=new Point4D(x+100,y+100,0,LineData.GREEN_HEX,index);
		Point4D p3=new Point4D(x,y+100,0,LineData.GREEN_HEX,index);
		
		Vector points=new Vector();
		points.add(p0);
		points.add(p1);
		points.add(p2);
		points.add(p3);
		
		Vector polygonData=new Vector();
		LineData ld=new LineData();
		ld.addIndex(0);
		ld.addIndex(1);
		ld.addIndex(2);
		ld.addIndex(3);
		polygonData.add(ld);
		
		PolygonMesh mesh=new PolygonMesh(points,polygonData);
		
		setMesh(mesh);
	}
	
	
	public Point3D getStarPoint() {
		return starPoint;
	}
	public void setStarPoint(Point3D starPoint) {
		this.starPoint = starPoint;
	}
	public Point3D getStarTangent() {
		return starTangent;
	}
	public void setStarTangent(Point3D starTangent) {
		this.starTangent = starTangent;
	}
	public Point3D getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point3D endPoint) {
		this.endPoint = endPoint;
	}
	public Point3D getEndTangent() {
		return endTangent;
	}
	public void setEndTangent(Point3D endTangent) {
		this.endTangent = endTangent;
	}


	public PolygonMesh getMesh() {
		return mesh;
	}


	public void setMesh(PolygonMesh mesh) {
		this.mesh = mesh;
	}
}
