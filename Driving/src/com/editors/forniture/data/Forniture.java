package com.editors.forniture.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class Forniture extends CustomData{



	double x_side=0;
	double y_side=0;
	double z_side=0;
		
	public static int FORNITURE_TYPE_TABLE=0;
	public static int FORNITURE_TYPE_CHAIR=1;
	public static int FORNITURE_TYPE_BED=2;
	public static int FORNITURE_TYPE_SOFA=3;
	public static int FORNITURE_TYPE_WARDROBE=4;
	public static int FORNITURE_TYPE_BOOKCASE=5;
	public static int FORNITURE_TYPE_TOILET=6;
	public static int FORNITURE_TYPE_CUPBOARD=7;
	
	public int forniture_type=FORNITURE_TYPE_TABLE;
	
	double leg_side=0;
	double leg_length=0;
	double back_length=0;

	public Forniture(){}

	public Forniture( double x_side, double y_side,double z_side,int forniture_type,
			double legLength, double legSide, double backLength
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.forniture_type = forniture_type;
		this.leg_length = legLength;
		this.leg_side = legSide;
		this.back_length = backLength;

	}
	

	public Object clone(){

		Forniture grid=new Forniture(x_side,y_side,z_side,forniture_type,leg_length,leg_side,back_length);
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

		String ret="F="+x_side+","+y_side+","+z_side+","+forniture_type;
		ret+=","+leg_length+","+leg_side+","+back_length;
				
		return ret;
	}

	public static Forniture buildForniture(String str) {

		String[] vals = str.split(",");

		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 
		int forniture_type=Integer.parseInt(vals[3]);
		double legLength = Double.parseDouble(vals[4]); 
		double legSide = Double.parseDouble(vals[5]); 
		double backLength = Double.parseDouble(vals[6]); 

		Forniture grid=new Forniture(x_side,y_side,z_side,forniture_type,legLength,legSide,backLength);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
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
		else if(FORNITURE_TYPE_WARDROBE==forniture_type){			
			
			return buildWardrobeMesh();
			
		}
		else if(FORNITURE_TYPE_TOILET==forniture_type){
			
			return buildToiletMesh();
		}
		else if(FORNITURE_TYPE_BOOKCASE==forniture_type){
			
			return buildBookcaseMesh();
		}
		else if(FORNITURE_TYPE_CUPBOARD==forniture_type){
			
			return buildCupboardMesh();
		}
		else
			return buildWardrobeMesh();


	}

	private PolygonMesh buildCupboardMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		n=0;	
		
		Segments b0=new Segments(0,x_side,0,y_side,0,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);
		
		body[0][0][1]=addBPoint(0,0,0.5,b0);
		body[1][0][1]=addBPoint(1.0,0,0.5,b0);
		body[1][1][1]=addBPoint(1.0,1.0,0.5,b0);
		body[0][1][1]=addBPoint(0,1.0,0.5,b0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);
		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		
		BPoint[][][] leftColumn=new BPoint[2][2][2];
		
		leftColumn[0][0][0]=addBPoint(0,0.2,0.5,b0);
		leftColumn[1][0][0]=addBPoint(0.1,0.2,0.5,b0);
		leftColumn[1][1][0]=addBPoint(0.1,0.4,0.5,b0);
		leftColumn[0][1][0]=addBPoint(0,0.4,0.5,b0);
		
		leftColumn[0][0][1]=addBPoint(0,0.2,0.75,b0);
		leftColumn[1][0][1]=addBPoint(0.1,0.2,0.75,b0);
		leftColumn[1][1][1]=addBPoint(0.1,0.4,0.75,b0);
		leftColumn[0][1][1]=addBPoint(0,0.4,0.75,b0);

		addLine(leftColumn[0][0][0],leftColumn[0][0][1],leftColumn[0][1][1],leftColumn[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(leftColumn[1][0][0],leftColumn[1][1][0],leftColumn[1][1][1],leftColumn[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(leftColumn[0][1][0],leftColumn[0][1][1],leftColumn[1][1][1],leftColumn[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(leftColumn[0][0][0],leftColumn[1][0][0],leftColumn[1][0][1],leftColumn[0][0][1],Renderer3D.CAR_BACK);
		
		
		
		BPoint[][][] rightColumn=new BPoint[2][2][2];
		
		rightColumn[0][0][0]=addBPoint(0.9,0.2,0.5,b0);
		rightColumn[1][0][0]=addBPoint(1.0,0.2,0.5,b0);
		rightColumn[1][1][0]=addBPoint(1.0,0.4,0.5,b0);
		rightColumn[0][1][0]=addBPoint(0.9,0.4,0.5,b0);
		
		rightColumn[0][0][1]=addBPoint(0.9,0.2,0.75,b0);
		rightColumn[1][0][1]=addBPoint(1.0,0.2,0.75,b0);
		rightColumn[1][1][1]=addBPoint(1.0,0.4,0.75,b0);
		rightColumn[0][1][1]=addBPoint(0.9,0.4,0.75,b0);

		addLine(rightColumn[0][0][0],rightColumn[0][0][1],rightColumn[0][1][1],rightColumn[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(rightColumn[1][0][0],rightColumn[1][1][0],rightColumn[1][1][1],rightColumn[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(rightColumn[0][1][0],rightColumn[0][1][1],rightColumn[1][1][1],rightColumn[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(rightColumn[0][0][0],rightColumn[1][0][0],rightColumn[1][0][1],rightColumn[0][0][1],Renderer3D.CAR_BACK);
		
		
		BPoint[][][] backPanel=new BPoint[2][2][2];
		
		backPanel[0][0][0]=addBPoint(0.0,0.9,0.5,b0);
		backPanel[1][0][0]=addBPoint(1.0,0.9,0.5,b0);
		backPanel[1][1][0]=addBPoint(1.0,1.0,0.5,b0);
		backPanel[0][1][0]=addBPoint(0.0,1.0,0.5,b0);
		
		backPanel[0][0][1]=addBPoint(0.0,0.9,0.75,b0);
		backPanel[1][0][1]=addBPoint(1.0,0.9,0.75,b0);
		backPanel[1][1][1]=addBPoint(1.0,1.0,0.75,b0);
		backPanel[0][1][1]=addBPoint(0.0,1.0,0.75,b0);

		addLine(backPanel[0][0][0],backPanel[0][0][1],backPanel[0][1][1],backPanel[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(backPanel[1][0][0],backPanel[1][1][0],backPanel[1][1][1],backPanel[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(backPanel[0][1][0],backPanel[0][1][1],backPanel[1][1][1],backPanel[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(backPanel[0][0][0],backPanel[1][0][0],backPanel[1][0][1],backPanel[0][0][1],Renderer3D.CAR_BACK);
	
		
		
		BPoint[][][] upper=new BPoint[2][2][2];
		
		upper[0][0][0]=addBPoint(0,0.2,0.75,b0);
		upper[1][0][0]=addBPoint(1.0,0.2,0.75,b0);
		upper[1][1][0]=addBPoint(1.0,1.0,0.75,b0);
		upper[0][1][0]=addBPoint(0,1.0,0.75,b0);
		
		upper[0][0][1]=addBPoint(0,0.2,1.0,b0);
		upper[1][0][1]=addBPoint(1.0,0.2,1.0,b0);
		upper[1][1][1]=addBPoint(1.0,1.0,1.0,b0);
		upper[0][1][1]=addBPoint(0,1.0,1.0,b0);
		
		addLine(upper[0][0][1],upper[1][0][1],upper[1][1][1],upper[0][1][1],Renderer3D.CAR_TOP);
		

		addLine(upper[0][0][0],upper[0][0][1],upper[0][1][1],upper[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(upper[1][0][0],upper[1][1][0],upper[1][1][1],upper[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(upper[0][1][0],upper[0][1][1],upper[1][1][1],upper[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(upper[0][0][0],upper[1][0][0],upper[1][0][1],upper[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(upper[0][0][0],upper[0][1][0],upper[1][1][0],upper[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildBookcaseMesh() {
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		n=0;
		
		Segments b0=new Segments(0,x_side,0,y_side,0,z_side);
		
		int lev=3;
		int shz=lev*2;
		
		BPoint[][][] shelves=new BPoint[4][4][shz];
		
		double rz0=0.1;
		double rz1=(1.0-lev*rz0)/(lev-1);
		
		for (int i = 0; i < shz; i=i+2) {
			
		
			double rz=(rz0+rz1)*(i/2);
			
			shelves[0][0][i]=addBPoint(-0.5,0,rz,b0);
			shelves[1][0][i]=addBPoint(-0.4,0,rz,b0);
			shelves[2][0][i]=addBPoint(0.4,0,rz,b0);
			shelves[3][0][i]=addBPoint(0.5,0,rz,b0);
			
			shelves[0][1][i]=addBPoint(-0.5,0.1,rz,b0);
			shelves[1][1][i]=addBPoint(-0.4,0.1,rz,b0);
			shelves[2][1][i]=addBPoint(0.4,0.1,rz,b0);
			shelves[3][1][i]=addBPoint(0.5,0.1,rz,b0);
			
			shelves[0][2][i]=addBPoint(-0.5,0.9,rz,b0);
			shelves[1][2][i]=addBPoint(-0.4,0.9,rz,b0);
			shelves[2][2][i]=addBPoint(0.4,0.9,rz,b0);
			shelves[3][2][i]=addBPoint(0.5,0.9,rz,b0);
			
			shelves[0][3][i]=addBPoint(-0.5,1.0,rz,b0);
			shelves[1][3][i]=addBPoint(-0.4,1.0,rz,b0);
			shelves[2][3][i]=addBPoint(0.4,1.0,rz,b0);
			shelves[3][3][i]=addBPoint(0.5,1.0,rz,b0);	
			
			
			shelves[0][0][i+1]=addBPoint(-0.5,0,rz+rz0,b0);
			shelves[1][0][i+1]=addBPoint(-0.4,0,rz+rz0,b0);
			shelves[2][0][i+1]=addBPoint(0.4,0,rz+rz0,b0);
			shelves[3][0][i+1]=addBPoint(0.5,0,rz+rz0,b0);
			
			shelves[0][1][i+1]=addBPoint(-0.5,0.1,rz+rz0,b0);
			shelves[1][1][i+1]=addBPoint(-0.4,0.1,rz+rz0,b0);
			shelves[2][1][i+1]=addBPoint(0.4,0.1,rz+rz0,b0);
			shelves[3][1][i+1]=addBPoint(0.5,0.1,rz+rz0,b0);
			
			shelves[0][2][i+1]=addBPoint(-0.5,0.9,rz+rz0,b0);
			shelves[1][2][i+1]=addBPoint(-0.4,0.9,rz+rz0,b0);
			shelves[2][2][i+1]=addBPoint(0.4,0.9,rz+rz0,b0);
			shelves[3][2][i+1]=addBPoint(0.5,0.9,rz+rz0,b0);
			
			shelves[0][3][i+1]=addBPoint(-0.5,1.0,rz+rz0,b0);
			shelves[1][3][i+1]=addBPoint(-0.4,1.0,rz+rz0,b0);
			shelves[2][3][i+1]=addBPoint(0.4,1.0,rz+rz0,b0);
			shelves[3][3][i+1]=addBPoint(0.5,1.0,rz+rz0,b0);	
		}
		
		for (int i = 0; i < shz; i++) {
			
			if(i%2==0){
			
				addLine(shelves[0][0][i],shelves[0][3][i],shelves[3][3][i],shelves[3][0][i],Renderer3D.CAR_BOTTOM);
				addLine(shelves[0][0][i+1],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_TOP);
			
	
				addLine(shelves[0][0][i],shelves[0][0][i+1],shelves[0][3][i+1],shelves[0][3][i],Renderer3D.CAR_LEFT);				

				addLine(shelves[3][0][i],shelves[3][3][i],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_RIGHT);
				
				addLine(shelves[0][3][i],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][3][i],Renderer3D.CAR_FRONT);
				
				addLine(shelves[0][0][i],shelves[3][0][i],shelves[3][0][i+1],shelves[0][0][i+1],Renderer3D.CAR_BACK);
			
			}
			
			if(i%2==1 && i<shz-1){
				
				//front panel
				addLine(shelves[0][3][i],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][3][i],Renderer3D.CAR_FRONT);
				addLine(shelves[1][2][i],shelves[2][2][i],shelves[2][2][i+1],shelves[1][2][i+1],Renderer3D.CAR_BACK);
				
				//left panel
				addLine(shelves[1][0][i],shelves[1][2][i],shelves[1][2][i+1],shelves[1][0][i+1],Renderer3D.CAR_RIGHT);
				addLine(shelves[0][0][i],shelves[0][0][i+1],shelves[0][3][i+1],shelves[0][3][i],Renderer3D.CAR_LEFT);
				
				//back panel left
				addLine(shelves[0][0][i],shelves[1][0][i],shelves[1][0][i+1],shelves[0][0][i+1],Renderer3D.CAR_BACK);
				
				//right panel
				addLine(shelves[3][0][i],shelves[3][3][i],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_RIGHT);
				addLine(shelves[2][0][i],shelves[2][0][i+1],shelves[2][2][i+1],shelves[2][2][i],Renderer3D.CAR_LEFT);
				
				//back panel right
				addLine(shelves[2][0][i],shelves[3][0][i],shelves[3][0][i+1],shelves[2][0][i+1],Renderer3D.CAR_BACK);
				
			}

		}
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildToiletMesh() { 
		
		points=new Vector();
		points.setSize(400);

		polyData=new Vector();
		
		n=0;
		
		
		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildWardrobeMesh() {

		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;
		
	

		//basic sides:
		buildBox(0,0,0,x_side,y_side,z_side);
	
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildSofaMesh() {

		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n= 0;

		//basic sides:
		buildBox(0,0,leg_length,x_side,y_side,z_side);
		
		//legs:	
		//backLeftLeg
		buildBox(0,0,0,leg_side,leg_side,leg_length);
		
		//backRightLeg
		buildBox(x_side-leg_side,0,0,leg_side,leg_side,leg_length);
		
		
		//frontLeftLeg
		buildBox(0,y_side-leg_side,0,leg_side,leg_side,leg_length);
		
	
		//frontRightLeg
		buildBox(x_side-leg_side,y_side-leg_side,0,leg_side,leg_side,leg_length);
		
		//sofa back:
		buildBox(0,0,leg_length+z_side,x_side,leg_side,back_length);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildBedMesh() {

		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n= 0;
		
	

		//basic sides:
		buildBox(0,0,leg_length,x_side,y_side,z_side);
		
	
		//back Edge
		buildBox(0,0,0,x_side,leg_side,leg_length);

		
		
		//front Edge
		buildBox(0,y_side-leg_side,0,x_side,leg_side,leg_length);
		
		
		
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildChairMesh() {


		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;

		//basic sides:
		buildBox(0,0,leg_length,x_side,y_side,z_side);
		
		//legs:	
		//backLeftLeg
		buildBox(0,0,0,leg_side,leg_side,leg_length);
		
		//backRightLeg
		buildBox(x_side-leg_side,0,0,leg_side,leg_side,leg_length);
		
		
		//frontLeftLeg
		buildBox(0,y_side-leg_side,0,leg_side,leg_side,leg_length);
		
	
		//frontRightLeg
		buildBox(x_side-leg_side,y_side-leg_side,0,leg_side,leg_side,leg_length);
		
		//chair back:
		buildBox(0,0,leg_length+z_side,x_side,leg_side,back_length);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}

	private PolygonMesh buildTableMesh() {


		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;
		
		Segments b0=new Segments(0,x_side,0,y_side,leg_length,z_side);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(0,0,0,b0);
		body[1][0][0]=addBPoint(1.0,0,0,b0);
		body[1][1][0]=addBPoint(1.0,1.0,0,b0);
		body[0][1][0]=addBPoint(0,1.0,0,b0);
		
		body[0][0][1]=addBPoint(0,0,0.5,b0);
		body[1][0][1]=addBPoint(1.0,0,0.5,b0);
		body[1][1][1]=addBPoint(1.0,1.0,0.5,b0);
		body[0][1][1]=addBPoint(0,1.0,0.5,b0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
				
		/// back left leg
		
		Segments balLeg=new Segments(0,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] blLeg=new BPoint[2][2][2];
				
		blLeg[0][0][0]=addBPoint(0,0,0,balLeg);
		blLeg[1][0][0]=addBPoint(1,0,0,balLeg);
		blLeg[1][1][0]=addBPoint(1,1,0,balLeg);
		blLeg[0][1][0]=addBPoint(0,1,0,balLeg);
		
	
		LineData bottom=buildLine(blLeg[0][0][0],blLeg[0][1][0],blLeg[1][1][0],blLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		blLeg[0][0][1]=addBPoint(0,0,1,balLeg);
		blLeg[1][0][1]=addBPoint(1,0,1,balLeg);
		blLeg[1][1][1]=addBPoint(1,1,1,balLeg);
		blLeg[0][1][1]=addBPoint(0,1,1,balLeg);
		
		addLine(blLeg[0][0][0],blLeg[0][0][1],blLeg[0][1][1],blLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(blLeg[0][1][0],blLeg[0][1][1],blLeg[1][1][1],blLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(blLeg[1][1][0],blLeg[1][1][1],blLeg[1][0][1],blLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(blLeg[1][0][0],blLeg[1][0][1],blLeg[0][0][1],blLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// back right leg
		
		Segments barLeg=new Segments(x_side-leg_side,leg_side,0,leg_side,0,leg_length);
		
		BPoint[][][] brLeg=new BPoint[2][2][2];
		
		brLeg[0][0][0]=addBPoint(0,0,0,barLeg);
		brLeg[1][0][0]=addBPoint(1,0,0,barLeg);
		brLeg[1][1][0]=addBPoint(1,1,0,barLeg);
		brLeg[0][1][0]=addBPoint(0,1,0,barLeg);
		
	
		buildLine(brLeg[0][0][0],brLeg[0][1][0],brLeg[1][1][0],brLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		brLeg[0][0][1]=addBPoint(0,0,1,barLeg);
		brLeg[1][0][1]=addBPoint(1,0,1,barLeg);
		brLeg[1][1][1]=addBPoint(1,1,1,barLeg);
		brLeg[0][1][1]=addBPoint(0,1,1,barLeg);
		
		addLine(brLeg[0][0][0],brLeg[0][0][1],brLeg[0][1][1],brLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(brLeg[0][1][0],brLeg[0][1][1],brLeg[1][1][1],brLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(brLeg[1][1][0],brLeg[1][1][1],brLeg[1][0][1],brLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(brLeg[1][0][0],brLeg[1][0][1],brLeg[0][0][1],brLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front left leg
		
		Segments frlLeg=new Segments(0,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] flLeg=new BPoint[2][2][2];
		
		flLeg[0][0][0]=addBPoint(0,0,0,frlLeg);
		flLeg[1][0][0]=addBPoint(1,0,0,frlLeg);
		flLeg[1][1][0]=addBPoint(1,1,0,frlLeg);
		flLeg[0][1][0]=addBPoint(0,1,0,frlLeg);
		
	
		buildLine(flLeg[0][0][0],flLeg[0][1][0],flLeg[1][1][0],flLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		flLeg[0][0][1]=addBPoint(0,0,1,frlLeg);
		flLeg[1][0][1]=addBPoint(1,0,1,frlLeg);
		flLeg[1][1][1]=addBPoint(1,1,1,frlLeg);
		flLeg[0][1][1]=addBPoint(0,1,1,frlLeg);
		
		addLine(flLeg[0][0][0],flLeg[0][0][1],flLeg[0][1][1],flLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(flLeg[0][1][0],flLeg[0][1][1],flLeg[1][1][1],flLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(flLeg[1][1][0],flLeg[1][1][1],flLeg[1][0][1],flLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(flLeg[1][0][0],flLeg[1][0][1],flLeg[0][0][1],flLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/// front right leg
		
		Segments frrLeg=new Segments(x_side-leg_side,leg_side,y_side-leg_side,leg_side,0,leg_length);
		
		BPoint[][][] frLeg=new BPoint[2][2][2];
		
		frLeg[0][0][0]=addBPoint(0,0,0,frrLeg);
		frLeg[1][0][0]=addBPoint(1,0,0,frrLeg);
		frLeg[1][1][0]=addBPoint(1,1,0,frrLeg);
		frLeg[0][1][0]=addBPoint(0,1,0,frrLeg);
		
	
		buildLine(frLeg[0][0][0],frLeg[0][1][0],frLeg[1][1][0],frLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		frLeg[0][0][1]=addBPoint(0,0,1,frrLeg);
		frLeg[1][0][1]=addBPoint(1,0,1,frrLeg);
		frLeg[1][1][1]=addBPoint(1,1,1,frrLeg);
		frLeg[0][1][1]=addBPoint(0,1,1,frrLeg);
		
		addLine(frLeg[0][0][0],frLeg[0][0][1],frLeg[0][1][1],frLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(frLeg[0][1][0],frLeg[0][1][1],frLeg[1][1][1],frLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(frLeg[1][1][0],frLeg[1][1][1],frLeg[1][0][1],frLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(frLeg[1][0][0],frLeg[1][0][1],frLeg[0][0][1],frLeg[0][0][0],Renderer3D.CAR_BACK);
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}











}	