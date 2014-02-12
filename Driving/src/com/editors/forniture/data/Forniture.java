package com.editors.forniture.data;

import java.util.Vector;

import com.CustomData;
import com.PolygonMesh;

public class Forniture extends CustomData{



	double x_side=0;
	double y_side=0;
	double z_side=0;

	double nw_x=0;
	double nw_y=0;
		
	public static int FORNITURE_TYPE_TABLE=0;
	public static int FORNITURE_TYPE_CHAIR=1;
	public static int FORNITURE_TYPE_BED=2;
	public static int FORNITURE_TYPE_SOFA=3;
	
	public int forniture_type=FORNITURE_TYPE_TABLE;
	
	double leg_side=0;
	double leg_length=0;
	double back_length=0;

	public Forniture(){}

	public Forniture( double nw_x, double nw_y,double x_side, double y_side,double z_side,int forniture_type,
			double legLength, double legSide, double backLength
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.nw_x = nw_x;
		this.nw_y = nw_y;
		this.forniture_type = forniture_type;
		this.leg_length = legLength;
		this.leg_side = legSide;
		this.back_length = backLength;

	}
	

	public Object clone(){

		Forniture grid=new Forniture(nw_x,nw_y,x_side,y_side,z_side,forniture_type,leg_length,leg_side,back_length);
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

		String ret="F="+ nw_x+","+nw_y+","+x_side+","+y_side+","+z_side+","+forniture_type;
		ret+=","+leg_length+","+leg_side+","+back_length;
				
		return ret;
	}

	public static Forniture buildForniture(String str) {

		String[] vals = str.split(",");

		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]); 
		int forniture_type=Integer.parseInt(vals[5]);
		double legLength = Double.parseDouble(vals[6]); 
		double legSide = Double.parseDouble(vals[7]); 
		double backLength = Double.parseDouble(vals[8]); 

		Forniture grid=new Forniture(nw_x,nw_y,x_side,y_side,z_side,forniture_type,legLength,legSide,backLength);

		return grid;
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
	

	public int getForniture_type() {
		return forniture_type;
	}

	public void setForniture_type(int forniture_type) {
		this.forniture_type = forniture_type;
	}

	public double getLeg_side() {
		return leg_side;
	}

	public void setLeg_side(double leg_side) {
		this.leg_side = leg_side;
	}

	public double getLeg_length() {
		return leg_length;
	}

	public void setLeg_length(double leg_length) {
		this.leg_length = leg_length;
	}

	public double getBack_length() {
		return back_length;
	}

	public void setBack_length(double back_length) {
		this.back_length = back_length;
	}

	public int pos(int i, int j, int k){

		return (i+(1+1)*j)*2+k;
	}


	public PolygonMesh buildMesh(){
		
		if(FORNITURE_TYPE_TABLE==forniture_type){			
			
			return buildTableMesh();
			
		}
		else if(FORNITURE_TYPE_CHAIR==forniture_type){			
			
			return buildChairMesh();
			
		}
		else if(FORNITURE_TYPE_BED==forniture_type){			
			
			return buildBedMesh();
			
		}
		else if(FORNITURE_TYPE_SOFA==forniture_type){			
			
			return buildSofaMesh();
			
		}
		else
			return null;


	}

	private PolygonMesh buildSofaMesh() {

		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int n=0;

		//basic sides:
		n=buildBox(0,0,leg_length,points,polyData,n,x_side,y_side,z_side);
		
		//legs:	
		//backLeftLeg
		n=buildBox(0,0,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		//backRightLeg
		n=buildBox(x_side-leg_side,0,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		
		//frontLeftLeg
		n=buildBox(0,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_length);
		
	
		//frontRightLeg
		n=buildBox(x_side-leg_side,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		//sofa back:
		n=buildBox(0,0,leg_length+z_side,points,polyData,n,x_side,leg_side,leg_length);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildBedMesh() {

		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int n=0;
		
	

		//basic sides:
		n=buildBox(0,0,leg_length,points,polyData,n,x_side,y_side,z_side);
		
	
		//back Edge
		n=buildBox(0,0,0,points,polyData,n,x_side,leg_side,leg_length);

		
		
		//front Edge
		n=buildBox(0,y_side-leg_side,0,points,polyData,n,x_side,leg_side,leg_length);
		
		
		
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildChairMesh() {


		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int n=0;

		//basic sides:
		n=buildBox(0,0,leg_length,points,polyData,n,x_side,y_side,z_side);
		
		//legs:	
		//backLeftLeg
		n=buildBox(0,0,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		//backRightLeg
		n=buildBox(x_side-leg_side,0,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		
		//frontLeftLeg
		n=buildBox(0,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_length);
		
	
		//frontRightLeg
		n=buildBox(x_side-leg_side,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		//chair back:
		n=buildBox(0,0,leg_length+z_side,points,polyData,n,x_side,leg_side,back_length);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildTableMesh() {


		Vector points=new Vector();
		points.setSize(50);

		Vector polyData=new Vector();
		
		int n=0;

		//basic sides:
		n=buildBox(0,0,leg_length,points,polyData,n,x_side,y_side,z_side);
		
		//legs:	
		//backLeftLeg
		n=buildBox(0,0,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		//backRightLeg
		n=buildBox(x_side-leg_side,0,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		
		//frontLeftLeg
		n=buildBox(0,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_length);
		
	
		//frontRightLeg
		n=buildBox(x_side-leg_side,y_side-leg_side,0,points,polyData,n,leg_side,leg_side,leg_length);
		
		
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}











}	