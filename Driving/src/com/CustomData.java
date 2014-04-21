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

	public BPoint addBPoint( double x, double y, double z,Segments s){
		
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
	
	public void addCylinder(double cyx0, double cyy0,double cyz0,
			double cylinder_radius,double cylinder_lenght){
		
		    int barrel_meridians=20;
	
			BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
			BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
			
			for (int i = 0; i < barrel_meridians; i++) {
			
				
				double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/barrel_meridians*i);
				double z=cyz0+cylinder_radius*Math.sin(2*Math.PI/barrel_meridians*i);
				
				uTrunkpoints[i]=addBPoint(x,cyy0+cylinder_lenght,z);
				
				
			}


			LineData topLD=new LineData();
			
			
				
				for (int i = barrel_meridians-1; i >=0; i--) {
				
				topLD.addIndex(uTrunkpoints[i].getIndex());
				
			}
			topLD.setData(""+Renderer3D.CAR_TOP);
			polyData.add(topLD);
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/barrel_meridians*i);
				double z=cyz0+cylinder_radius*Math.sin(2*Math.PI/barrel_meridians*i);
				
				bTrunkpoints[i]=addBPoint(x,cyy0,z);
				
			}


			LineData bottomLD=new LineData();
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				bottomLD.addIndex(bTrunkpoints[i].getIndex());
				
			}
			bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
			polyData.add(bottomLD);
			
			
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				
				sideLD.addIndex(bTrunkpoints[(i+1)%barrel_meridians].getIndex());
				sideLD.addIndex(bTrunkpoints[i].getIndex());
				sideLD.addIndex(uTrunkpoints[i].getIndex());
				sideLD.addIndex(uTrunkpoints[(i+1)%barrel_meridians].getIndex());
				sideLD.setData(""+getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
	}

	public void addPrism(Prism prism){
		
	
		int size=prism.getSize();
		


			LineData topLD=new LineData();
			
			
				
			for (int i = size-1; i >=0; i--) {
				
				topLD.addIndex(((BPoint) prism.upperBase[i]).getIndex());
				
			}
			topLD.setData(""+Renderer3D.CAR_TOP);
			polyData.add(topLD);
			
			LineData bottomLD=new LineData();
			
			for (int i = 0; i < size; i++) {
				
				bottomLD.addIndex(((BPoint) prism.lowerBase[i]).getIndex());
				
			}
			bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
			polyData.add(bottomLD);
			
			
			
			for (int i = 0; i < size; i++) {
				
				LineData sideLD=new LineData();
				
				
				sideLD.addIndex(((BPoint) prism.lowerBase[(i+1)%size]).getIndex());
				sideLD.addIndex(((BPoint) prism.lowerBase[i]).getIndex());
				sideLD.addIndex(((BPoint) prism.upperBase[i]).getIndex());
				sideLD.addIndex(((BPoint) prism.upperBase[(i+1)%size]).getIndex());
				sideLD.setData(""+getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
	}
}
