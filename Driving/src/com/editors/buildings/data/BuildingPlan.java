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
	public static int ROOF_TYPE_HIP=1;
	public static int ROOF_TYPE_SHED=2;
	public static int ROOF_TYPE_GAMBREL=3;
	
	public int roof_type=ROOF_TYPE_HIP;
	
	double roof_top_height=0;
	double roof_top_width=0;
	
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
		grid.setRoof_type(getRoof_type());
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
	
	public String getRoofData() {
		String str=getRoof_type()+","+getRoof_top_height()+","+getRoof_top_width();
		return str;
	}
	
	
	public void buildRoof(String str) {
		
		String[] vals = str.split(",");
		
		int roType= Integer.parseInt(vals[0]);
		double rooHeight = Double.parseDouble(vals[1]);
		double rooWidth =Double.parseDouble(vals[2]);
		
		setRoof(roType,rooHeight,rooWidth);
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
	
	

	public int getRoof_type() {
		return roof_type;
	}

	public void setRoof_type(int roof_type) {
		this.roof_type = roof_type;
	}
	
	
	public void setRoof(int roof_type,double roof_top_height,double roof_top_width){
		
		this.roof_type = roof_type;
		this.roof_top_height = roof_top_height;
		this.roof_top_width = roof_top_width;
	}



	public double getRoof_top_height() {
		return roof_top_height;
	}

	public void setRoof_top_height(double roof_top_height) {
		this.roof_top_height = roof_top_height;
	}

	public double getRoof_top_width() {
		return roof_top_width;
	}

	public void setRoof_top_width(double roof_top_width) {
		this.roof_top_width = roof_top_width;
	}


	
	public PolygonMesh buildMesh(){

		int xnum=1;
		int ynum=1;


		Vector points=new Vector();
		points.setSize(14);

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
			
		}else if( roof_type==ROOF_TYPE_HIP || roof_type==ROOF_TYPE_SHED){
			
				
			
			double y_indentation=(getY_side()-getRoof_top_width())/2.0;
		 
			BPoint pr001=null;
			BPoint pr011=null;
			
			if(roof_type==ROOF_TYPE_HIP){
				pr001=new BPoint((p001.x+p101.x)/2.0,(p001.y+p101.y)/2.0+y_indentation,roof_top_height+(p001.z+p101.z)/2.0,8);
				pr011=new BPoint((p011.x+p111.x)/2.0,(p011.y+p111.y)/2.0-y_indentation,roof_top_height+(p011.z+p111.z)/2.0,9);
			}
			else if(roof_type==ROOF_TYPE_SHED){
				
				pr001=new BPoint(p001.x,(p001.y+p101.y)/2.0+y_indentation,roof_top_height+(p001.z+p101.z)/2.0,8);
				pr011=new BPoint(p011.x,(p011.y+p111.y)/2.0-y_indentation,roof_top_height+(p011.z+p111.z)/2.0,9);
				
			}
			
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
		
		}else if( roof_type==ROOF_TYPE_GAMBREL){
			
			double teta=Math.PI/4.0;
			
			BPoint pr001=null;
			BPoint pr101=null;
			BPoint pr201=null;
	
			BPoint pr011=null;
			BPoint pr111=null;
			BPoint pr211=null;
			
			double r=Math.abs((p001.x-p101.x)/2.0);
			
			double xc=(p001.x+p101.x)/2.0;
			double yc=(p001.y+p101.y)/2.0;
			double zc=(p001.z+p101.z)/2.0;
			
			pr001=new BPoint(xc-r*Math.cos(teta),yc,zc+r*Math.sin(teta),8);
			pr101=new BPoint(xc-r*Math.cos(2*teta),yc,zc+r*Math.sin(2*teta),9);
			pr201=new BPoint(xc-r*Math.cos(3*teta),yc,zc+r*Math.sin(3*teta),10);
			
			points.setElementAt(pr001,pr001.getIndex());
			points.setElementAt(pr101,pr101.getIndex());
			points.setElementAt(pr201,pr201.getIndex());
			
			xc=(p011.x+p111.x)/2.0;
			yc=(p011.y+p111.y)/2.0;
			zc=(p011.z+p111.z)/2.0;
			
			pr011=new BPoint(xc-r*Math.cos(teta),yc,zc+r*Math.sin(teta),11);
			pr111=new BPoint(xc-r*Math.cos(2*teta),yc,zc+r*Math.sin(2*teta),12);
			pr211=new BPoint(xc-r*Math.cos(3*teta),yc,zc+r*Math.sin(3*teta),13);
			
			points.setElementAt(pr011,pr011.getIndex());
			points.setElementAt(pr111,pr111.getIndex());
			points.setElementAt(pr211,pr211.getIndex());
			
			LineData backRoof=buildLine(p001,p101,pr201,pr101,Renderer3D.CAR_BACK);
			backRoof.addIndex(pr001.getIndex());
			polyData.add(backRoof);
			LineData frontRoof=buildLine(p011,pr011,pr111,pr211,Renderer3D.CAR_FRONT);
			frontRoof.addIndex(p111.getIndex());
			polyData.add(frontRoof);
			
			LineData topRoof1=buildLine(p001,pr001,pr011,p011,Renderer3D.CAR_TOP);
			polyData.add(topRoof1);	
			LineData topRoof2=buildLine(pr001,pr101,pr111,pr011,Renderer3D.CAR_TOP);
			polyData.add(topRoof2);
			LineData topRoof3=buildLine(pr101,pr201,pr211,pr111,Renderer3D.CAR_TOP);
			polyData.add(topRoof3);
			LineData topRoof4=buildLine(p101,p111,pr211,pr201,Renderer3D.CAR_TOP);
			polyData.add(topRoof4);
			
		}
		
		translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}

	private void translatePoints(Vector points, double dx, double dy) { 
		
		for (int i = 0; i < points.size(); i++) {
			BPoint point = (BPoint) points.elementAt(i);
			if(point==null)
				continue;
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