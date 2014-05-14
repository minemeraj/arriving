package com.editors.buildings.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class BuildingPlan extends CustomData{



	double x_side=0;
	double y_side=0;
	double z_side=0;

	public static int ROOF_TYPE_FLAT=0;
	public static int ROOF_TYPE_HIP=1;
	public static int ROOF_TYPE_SHED=2;
	public static int ROOF_TYPE_GAMBREL=3;
	public static int ROOF_TYPE_MANSARD=4;

	public int roof_type=ROOF_TYPE_HIP;

	double roof_top_height=0;
	double roof_top_width=0;
	double roof_top_length=0;

	public BuildingPlan(){}

	public BuildingPlan( double x_side, double y_side,double z_side
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
	


	}

	public Object clone(){

		BuildingPlan grid=new BuildingPlan(x_side,y_side,z_side);
		grid.setRoof_type(getRoof_type());
		grid.setRoof(roof_type,roof_top_height,roof_top_width,roof_top_length);
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

		return x_side+","+y_side+","+z_side;
	}

	public static BuildingPlan buildPlan(String str) {

		String[] vals = str.split(",");


		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 


		BuildingPlan grid=new BuildingPlan(x_side,y_side,z_side);

		return grid;
	}

	public String getRoofData() {
		String str=getRoof_type()+","+
				getRoof_top_height()+","+getRoof_top_width()+","+getRoof_top_length();
		return str;
	}


	public void buildRoof(String str) {

		String[] vals = str.split(",");

		int rooType= Integer.parseInt(vals[0]);
		double rooHeight = Double.parseDouble(vals[1]);
		double rooWidth =Double.parseDouble(vals[2]);
		double rooLength =Double.parseDouble(vals[3]);

		setRoof(rooType,rooHeight,rooWidth,rooLength);
	}

	public double getZ_side() {
		return z_side;
	}

	public void setZ_side(double z_side) {
		this.z_side = z_side;
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


	public void setRoof(int roof_type,double roof_top_height,double roof_top_width,double roof_top_length){

		this.roof_type = roof_type;
		this.roof_top_height = roof_top_height;
		this.roof_top_width = roof_top_width;
		this.roof_top_length = roof_top_length;
	}



	public double getRoof_top_height() {
		return roof_top_height;
	}

	public void setRoof_top_height(double roof_top_height) {
		this.roof_top_height = roof_top_height;
	}

	public double getRoof_top_length() {
		return roof_top_length;
	}

	public void setRoof_top_length(double roof_top_length) {
		this.roof_top_length = roof_top_length;
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


		points=new Vector();
		points.setSize(14);

		polyData=new Vector();
		
		n=0;

		//basic sides:
		
		Segments p=new Segments(0,x_side,0,y_side,0,z_side);

		BPoint p000=addBPoint(-0.5,0,0,p);
		BPoint p100=addBPoint(0.5,0,0,p);
		BPoint p010=addBPoint(-0.5,1.0,0,p);
		BPoint p001=addBPoint(-0.5,0,1.0,p);
		BPoint p110=addBPoint(0.5,1.0,0,p);
		BPoint p011=addBPoint(-0.5,1.0,1.0,p);
		BPoint p101=addBPoint(0.5,0,1.0,p);
		BPoint p111=addBPoint(0.5,1.0,1.0,p);
		
		double roof_rim=0;


		LineData bottomLD=addLine(p000,p010,p110,p100,Renderer3D.CAR_BOTTOM);

		LineData leftLD=addLine(p000,p001,p011,p010,Renderer3D.CAR_LEFT);

		LineData rightLD=addLine(p100,p110,p111,p101,Renderer3D.CAR_RIGHT);

		LineData backLD=addLine(p000,p100,p101,p001,Renderer3D.CAR_BACK);

		LineData frontLD=addLine(p010,p011,p111,p110,Renderer3D.CAR_FRONT);


		//roof:

			if(roof_type==ROOF_TYPE_FLAT){

				LineData topLD=addLine(p001,p101,p111,p011,Renderer3D.CAR_TOP);
	

			}else if( roof_type==ROOF_TYPE_HIP || roof_type==ROOF_TYPE_SHED){



				double y_indentation=(getY_side()-getRoof_top_length())/2.0;

				BPoint[][] pr=new BPoint[3][2];
				

				if(roof_type==ROOF_TYPE_HIP){			
							
					
					pr[1][0]=addBPoint((p001.x+p101.x)/2.0,(p001.y+p101.y)/2.0+y_indentation,roof_top_height+(p001.z+p101.z)/2.0);
					
					//double d00=Point3D.distance(pr[1][0],p000);
							
					pr[0][0]=addBPoint(p001.x-roof_rim,p001.y,p001.z);
					pr[2][0]=addBPoint(p101.x+roof_rim,p101.y,p101.z);
					
					pr[1][1]=addBPoint((p011.x+p111.x)/2.0,(p011.y+p111.y)/2.0-y_indentation,roof_top_height+(p011.z+p111.z)/2.0);
					pr[0][1]=addBPoint(p011.x-roof_rim,p011.y,p011.z);
					pr[2][1]=addBPoint(p111.x+roof_rim,p111.y,p111.z);
				}
				else if(roof_type==ROOF_TYPE_SHED){

					pr[0][0]=addBPoint(p001.x,p001.y,p001.z);
					pr[1][0]=addBPoint(p001.x,(p001.y+p101.y)/2.0+y_indentation,roof_top_height+(p001.z+p101.z)/2.0);
					pr[2][0]=addBPoint(p101.x,p101.y,p101.z);
					pr[0][1]=addBPoint(p011.x,p011.y,p011.z);
					pr[1][1]=addBPoint(p011.x,(p011.y+p111.y)/2.0-y_indentation,roof_top_height+(p011.z+p111.z)/2.0);
					pr[2][1]=addBPoint(p111.x,p011.y,p111.z);

				}

				for(int i=0;i<2;i++){
					
					LineData topRoof1=addLine(pr[i][0],pr[i+1][0],pr[i+1][1],pr[i][1],Renderer3D.CAR_TOP);
					
				}
				
				LineData backRoof=addLine(p001,p101,pr[1][0],null,Renderer3D.CAR_BACK);

				LineData frontRoof=addLine(p011,pr[1][1],p111,null,Renderer3D.CAR_FRONT);
				
				
				

				

		


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

				pr001=addBPoint(xc-r*Math.cos(teta),yc,zc+r*Math.sin(teta));
				pr101=addBPoint(xc-r*Math.cos(2*teta),yc,zc+r*Math.sin(2*teta));
				pr201=addBPoint(xc-r*Math.cos(3*teta),yc,zc+r*Math.sin(3*teta));


				xc=(p011.x+p111.x)/2.0;
				yc=(p011.y+p111.y)/2.0;
				zc=(p011.z+p111.z)/2.0;

				pr011=addBPoint(xc-r*Math.cos(teta),yc,zc+r*Math.sin(teta));
				pr111=addBPoint(xc-r*Math.cos(2*teta),yc,zc+r*Math.sin(2*teta));
				pr211=addBPoint(xc-r*Math.cos(3*teta),yc,zc+r*Math.sin(3*teta));


				LineData backRoof=buildLine(p001,p101,pr201,pr101,Renderer3D.CAR_BACK);
				backRoof.addIndex(pr001.getIndex());
				polyData.add(backRoof);
				LineData frontRoof=buildLine(p011,pr011,pr111,pr211,Renderer3D.CAR_FRONT);
				frontRoof.addIndex(p111.getIndex());
				polyData.add(frontRoof);

				LineData topRoof1=addLine(p001,pr001,pr011,p011,Renderer3D.CAR_TOP);
			
				LineData topRoof2=addLine(pr001,pr101,pr111,pr011,Renderer3D.CAR_TOP);
		
				LineData topRoof3=addLine(pr101,pr201,pr211,pr111,Renderer3D.CAR_TOP);

				LineData topRoof4=addLine(p101,p111,pr211,pr201,Renderer3D.CAR_TOP);
	

			}else if( roof_type==ROOF_TYPE_MANSARD){
				
				double roofDY=(getY_side()-getRoof_top_length())/2.0; 
				double roofDX=(getX_side()-getRoof_top_width())/2.0; 
				
				BPoint pr001=addBPoint(p001.x+roofDX,p001.y+roofDY,p001.z+roof_top_height);
				BPoint pr101=addBPoint(p101.x-roofDX,p101.y+roofDY,p101.z+roof_top_height);
				BPoint pr111=addBPoint(p111.x-roofDX,p111.y-roofDY,p111.z+roof_top_height);
				BPoint pr011=addBPoint(p011.x+roofDX,p011.y-roofDY,p011.z+roof_top_height);
				
				LineData topRoof=addLine(pr001,pr101,pr111,pr011,Renderer3D.CAR_TOP);
				
				LineData topRoof1=addLine(p001,pr001,pr011,p011,Renderer3D.CAR_TOP);

				LineData topRoof2=addLine(p001,p101,pr101,pr001,Renderer3D.CAR_TOP);

				
				LineData topRoof3=addLine(p101,p111,pr111,pr101,Renderer3D.CAR_TOP);
				
				LineData topRoof4=addLine(p111,p011,pr011,pr111,Renderer3D.CAR_TOP);

				
				BPoint pr002=addBPoint((pr001.x+pr101.x)/2.0,(pr001.y+pr101.y)/2.0+roofDY,roof_top_height*0.5+(pr001.z+pr101.z)/2.0);
				BPoint pr012=addBPoint((pr011.x+pr111.x)/2.0,(pr011.y+pr111.y)/2.0-roofDY,roof_top_height*0.5+(pr011.z+pr111.z)/2.0);

				LineData backRoof00=addLine(pr001,pr101,pr002,null,Renderer3D.CAR_BACK); 

				LineData frontRoof01=addLine(pr011,pr012,pr111,null,Renderer3D.CAR_FRONT);

				LineData topRoof01=addLine(pr001,pr002,pr012,pr011,Renderer3D.CAR_TOP);
				
				LineData topRoof02=addLine(pr101,pr111,pr012,pr002,Renderer3D.CAR_TOP);

				
			}

			//translatePoints(points,nw_x,nw_y);

			PolygonMesh pm=new PolygonMesh(points,polyData);

			PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
			return spm;


	}







}	