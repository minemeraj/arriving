package com;

import java.util.Vector;

import com.main.Renderer3D;

public class CustomData {
	
	public Vector points=null;
	public Vector polyData=null;
	public int n=0;
	
	public void buildBox(double x , double y, double z,double x_side,double y_side, double z_side) {
		
		BPoint pLeg000=addBPoint(x,y,z);
		BPoint pLeg100=addBPoint(x+x_side,y,z);
		BPoint pLeg110=addBPoint(x+x_side,y+y_side,z);
		BPoint pLeg010=addBPoint(x,y+y_side,z);
		
	
		LineData bottom=buildLine(pLeg000,pLeg010,pLeg110,pLeg100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		BPoint pLeg001=addBPoint(x,y,z+z_side);
		BPoint pLeg101=addBPoint(x+x_side,y,z+z_side);
		BPoint pLeg111=addBPoint(x+x_side,y+y_side,z+z_side);
		BPoint pLeg011=addBPoint(x,y+y_side,z+z_side);
	
		
		addLine(pLeg001,pLeg101,pLeg111,pLeg011,Renderer3D.CAR_TOP);
		
		addLine(pLeg000,pLeg001,pLeg011,pLeg010,Renderer3D.CAR_LEFT);

		addLine(pLeg010,pLeg011,pLeg111,pLeg110,Renderer3D.CAR_FRONT);

		addLine(pLeg110,pLeg111,pLeg101,pLeg100,Renderer3D.CAR_RIGHT);

		addLine(pLeg100,pLeg101,pLeg001,pLeg000,Renderer3D.CAR_BACK);


	}
	

	public void translatePoints(Vector points, double dx, double dy) { 

		for (int i = 0; i < points.size(); i++) {
			BPoint point = (BPoint) points.elementAt(i);
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}
	
	
	public void translatePoints(Point3D[] points, double dx, double dy) { 

		for (int i = 0; i < points.length; i++) {
			Point3D point = points[i];
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}
	
	public BPoint addBPoint(double x, double y, double z){
		
		BPoint point=new BPoint(x, y, z, n++);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}

	public BPoint addBPoint(Segments s, double x, double y, double z){
		
		BPoint point=new BPoint(s.x(x), s.y(y), s.z(z), n++);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}
	
	public LineData  addLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		polyData.add(ld);
		
		return ld;
	}

	public LineData buildLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		return ld;
	}
	

	public int getFace(LineData ld,Vector points){
		
		int n=ld.size();
		
		Point3D p0=(Point3D)points.elementAt(ld.getIndex((n+0-1)%n));
		Point3D p1=(Point3D)points.elementAt(ld.getIndex(0));
		Point3D p2=(Point3D)points.elementAt(ld.getIndex((1+0)%n));

		Point3D normal=Point3D.calculateCrossProduct(p1.substract(p0),p2.substract(p1));

		normal=normal.calculateVersor();
		
		int boxFace=Renderer3D.findBoxFace(normal);
		return boxFace;

		
		
	}
	
}
