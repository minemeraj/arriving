package com.editors.buildings.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class BuildingPlan {
	


	double x_side=0;
	double y_side=0;
	double z_side=0;
	
	double nw_x=0;
	double nw_y=0;
	
	public static int ROOF_TYPE_FLAT=0;
	public static int ROOF_TYPE_GABLE=1;
	public static int ROOF_TYPE_HIP=2;
	public static int ROOF_TYPE_SHED=3;
	public static int ROOF_TYPE_GAMBREL=4;
	
	public int roof_type=ROOF_TYPE_GABLE;
	
	public BuildingPlan(){}
	
	public BuildingPlan( double nw_x, double nw_y,double x_side, double y_side,double z_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.nw_x = nw_x;
		this.nw_y = nw_y;
		

	}
	
	public Object clone(){
		
		BuildingPlan grid=new BuildingPlan(nw_x,nw_y,x_side,y_side,z_side);

		return grid;
		
	}
	

	public double getX_side() {
		return x_side;
	}
	public void setX_side(double x_side) {
		this.x_side = x_side;
	}
	public double getY_side() {
		return y_side;
	}
	public void setY_side(double y_side) {
		this.y_side = y_side;
	}


	public String toString() {
		
		return nw_x+","+nw_y+","+x_side+","+y_side+","+z_side;
	}
	
	public static BuildingPlan buildPlan(String str) {
		
		String[] vals = str.split(",");
		
		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]);

		
		BuildingPlan grid=new BuildingPlan(nw_x,nw_y,x_side,y_side,z_side);
	
		return grid;
	}


	
	public PolygonMesh buildMesh(){

		int xnum=1;
		int ynum=1;


		Vector points=new Vector();
		points.setSize(10);

		Vector polyData=new Vector();
		
		 //basic sides:
				
		BPoint p000=new BPoint(0,0,0,0);
		BPoint p100=new BPoint(x_side,0,0,1);
		BPoint p010=new BPoint(0,y_side,0,2);
		BPoint p001=new BPoint(0,0,z_side,3);
		BPoint p110=new BPoint(x_side,y_side,0,4);
		BPoint p011=new BPoint(0,y_side,z_side,5);
		BPoint p101=new BPoint(x_side,0,z_side,6);
		BPoint p111=new BPoint(x_side,y_side,z_side,7);

		points.setElementAt(p000,p000.getIndex());
		points.setElementAt(p100,p100.getIndex());
		points.setElementAt(p010,p010.getIndex());
		points.setElementAt(p001,p001.getIndex());
		points.setElementAt(p110,p110.getIndex());
		points.setElementAt(p011,p011.getIndex());
		points.setElementAt(p101,p101.getIndex());
		points.setElementAt(p111,p111.getIndex());
     
		
		LineData topLD=buildLine(p001,p101,p111,p011,Renderer3D.CAR_TOP);
		polyData.add(topLD);


		
		
		LineData leftLD=buildLine(p000,p001,p011,p010,Renderer3D.CAR_LEFT);
		polyData.add(leftLD);
		
		
		LineData rightLD=buildLine(p100,p110,p111,p101,Renderer3D.CAR_RIGHT);
		polyData.add(rightLD);
		
		
		LineData backLD=buildLine(p000,p100,p101,p001,Renderer3D.CAR_BACK);
		polyData.add(backLD);
		
		LineData frontLD=buildLine(p010,p011,p111,p110,Renderer3D.CAR_FRONT);
		polyData.add(frontLD);
		
		//roof:
		
		if(roof_type==ROOF_TYPE_FLAT){
			
			LineData bottomLD=buildLine(p000,p010,p110,p100,Renderer3D.CAR_TOP);
			polyData.add(bottomLD);
			
		}else if(roof_type==ROOF_TYPE_GABLE){
		 
			BPoint pr001=new BPoint((p001.x+p101.x)/2.0,(p001.y+p101.y)/2.0,30+(p001.z+p101.z)/2.0,8);
			BPoint pr011=new BPoint((p011.x+p111.x)/2.0,(p011.y+p111.y)/2.0,30+(p011.z+p111.z)/2.0,9);
			
			points.setElementAt(pr001,pr001.getIndex());
			points.setElementAt(pr011,pr011.getIndex());
			
			LineData backRoof=buildLine(p001,p101,pr001,null,Renderer3D.CAR_BACK);
			polyData.add(backRoof);
			LineData frontRoof=buildLine(p011,pr011,p111,null,Renderer3D.CAR_FRONT);
			polyData.add(frontRoof);
			LineData topRoof1=buildLine(p001,pr001,pr011,p011,Renderer3D.CAR_TOP);
			polyData.add(topRoof1);
			LineData topRoof2=buildLine(p101,p111,pr011,pr001,Renderer3D.CAR_TOP);
			polyData.add(topRoof2);
		
		}
		///////////////
		
		translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}

	private void translatePoints(Vector points, double dx, double dy) { 
		
		for (int i = 0; i < points.size(); i++) {
			BPoint point = (BPoint) points.elementAt(i);
			point.translate(dx,dy,0);
		}
		
	}

	private LineData buildLine(BPoint p0, BPoint p1, BPoint p2,
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

	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}

	public double getNw_x() {
		return nw_x;
	}

	public void setNw_x(double nw_x) {
		this.nw_x = nw_x;
	}

	public double getNw_y() {
		return nw_y;
	}

	public void setNw_y(double nw_y) {
		this.nw_y = nw_y;
	}
	
	public int pos(int i, int j, int k){
		
		return (i+(1+1)*j)*2+k;
	}
	
	 class BPoint extends Point3D{
		 
		 public BPoint(double x, double y, double z, int index) {
			super(x, y, z);
			this.index = index;
		}

		int index=-1;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		 
		 
	 }

}
