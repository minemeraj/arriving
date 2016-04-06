package com.editors.forniture.data;

import com.CustomData;
import com.Point3D;
import com.PolygonMesh;

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
	public static int FORNITURE_TYPE_STREETLIGHT=8;
	public static int FORNITURE_TYPE_BARREL=9;
	public static int FORNITURE_TYPE_GLOBE0=90;
	public static int FORNITURE_TYPE_GLOBE1=91;
	
	public int forniture_type=FORNITURE_TYPE_TABLE;
	
	double leg_side=0;
	double leg_length=0;
	double back_height=0;
	double front_height=0;
	
	double side_width=0;
	double side_length=0;
	double side_height=0;
	
	double upper_width=0;
	double upper_length=0;
	double upper_height=0;
	
	int n_meridians; 
	int n_parallels;

	public Forniture(){}

	public Forniture( double x_side, double y_side,double z_side,int forniture_type,
			double legLength, double legSide,double frontHeight, double backHeight,
			double side_width,double side_length, double side_height,
			double upper_width,double upper_length, double upper_height,
			int N_MERIDIANS,int N_PARALLELS
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.forniture_type = forniture_type;
		this.leg_length = legLength;
		this.leg_side = legSide;
		this.back_height = backHeight;
		this.front_height = frontHeight;
		
		this.side_width = side_width;
		this.side_length = side_length;
		this.side_height = side_height;
		
		this.upper_width = upper_width;
		this.upper_length = upper_length;
		this.upper_height = upper_height;
		
		this.n_meridians = N_MERIDIANS;
		this.n_parallels = N_PARALLELS;
	}
	
	@Override
	public Object clone(){

		Forniture grid=new Forniture(x_side,y_side,z_side,forniture_type,
				leg_length,leg_side,
				front_height,back_height,
				side_width, side_length,side_height,
				upper_width, upper_length, upper_height,
				n_meridians,n_parallels
				);
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

	@Override
	public String toString() {

		String ret="F="+x_side+","+y_side+","+z_side+","+forniture_type;
		ret+=","+leg_length+","+leg_side+","+front_height+","+back_height
				+","+side_width+","+side_length+","+side_height
				+","+upper_width+","+upper_length+","+upper_height;
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
		double frontHeight = Double.parseDouble(vals[6]); 
		double backHeight = Double.parseDouble(vals[7]); 
		double side_width = Double.parseDouble(vals[8]); 
		double side_length = Double.parseDouble(vals[9]); 
		double side_height = Double.parseDouble(vals[10]); 		
		double upper_width=Double.parseDouble(vals[11]); 
		double upper_length=Double.parseDouble(vals[12]); 
		double upper_height=Double.parseDouble(vals[13]); 
		
		int n_parallels=Integer.parseInt(vals[14]);
		int n_meridians=Integer.parseInt(vals[15]);

		Forniture grid=new Forniture(x_side,y_side,z_side,forniture_type,
				legLength,legSide,
				frontHeight,backHeight,
				side_width, side_length,side_height,
				upper_width,upper_length,upper_height,
				n_parallels,n_meridians
				);

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

	public double getBack_height() {
		return back_height;
	}

	public void setBack_height(double back_length) {
		this.back_height = back_length;
	}

	@Override
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
		else if(FORNITURE_TYPE_STREETLIGHT==forniture_type){
			return buildStreetLightMesh();	
		}
		else if(FORNITURE_TYPE_BARREL==forniture_type){
			return buildBarrelMesh();	
		}
		else if(FORNITURE_TYPE_GLOBE0==forniture_type){
			return buildGlobeMesh0();	 
		}
		else if(FORNITURE_TYPE_GLOBE1==forniture_type){
			return buildGlobeMesh1();	 
		}
		else	
			return buildWardrobeMesh();


	}


	private PolygonMesh buildGlobeMesh1() {

		

		specificData=new Globe1(x_side,n_meridians,n_parallels);
		
		PolygonMesh pm=specificData.getMesh();
	
		return pm;
	}

	private PolygonMesh buildGlobeMesh0() {

		

		specificData=new Globe0(x_side,n_meridians,n_parallels);
		
		PolygonMesh pm=specificData.getMesh();
	
		return pm;
	}

	private PolygonMesh buildCupboardMesh() {
		
		Cupboard cupboard=new Cupboard(  x_side,  y_side, z_side, forniture_type,
				leg_length,  leg_side, front_height,  back_height,
				 side_width, side_length,  side_height,
				 upper_width, upper_length,  upper_height
				);
		
		return cupboard.getMesh();
	}

	private PolygonMesh buildBookcaseMesh() {
		
		Bookcase bookcase=new Bookcase(  x_side,  y_side, z_side, forniture_type,
				leg_length,  leg_side, front_height,  back_height,
				 side_width, side_length,  side_height,
				 upper_width, upper_length,  upper_height
				);
		
		return bookcase.getMesh();
		
	}

	private PolygonMesh buildToiletMesh() { 
		
		Toilet toilet=new Toilet(  x_side,  y_side, z_side, forniture_type,
				leg_length,  leg_side, front_height,  back_height,
				 side_width, side_length,  side_height,
				 upper_width, upper_length,  upper_height
				);
		
		return toilet.getMesh();
	}

	private PolygonMesh buildWardrobeMesh() {

		Wardrobe wardrobe=new Wardrobe(x_side,y_side,z_side);
		
		specificData=wardrobe;
		
		return wardrobe.getMesh();
	}

	private PolygonMesh buildSofaMesh() {
		
		Sofa sofa=new Sofa(x_side,y_side,z_side,leg_length,leg_side,back_height,
				side_width,side_length,side_height);
		
		specificData=sofa;
		
		return sofa.getMesh();
	}

	private PolygonMesh buildBedMesh() {

		Bed bed=new Bed(  x_side,  y_side, z_side, forniture_type,
				leg_length,  leg_side, front_height,  back_height,
				 side_width, side_length,  side_height,
				 upper_width, upper_length,  upper_height
				);
		
		return bed.getMesh();
		
	}

	private PolygonMesh buildChairMesh() {
		
		Chair chair=new Chair(x_side,y_side,z_side,leg_length,leg_side,back_height);
	
		specificData=chair;
		
		return chair.getMesh();
	
	}

	private PolygonMesh buildTableMesh() {
		
		
		Table table=new Table(x_side,y_side,z_side,leg_length,leg_side);
		
		specificData=table;
		
		return table.getMesh();
		
	}

	private PolygonMesh buildStreetLightMesh() {
		
		
		StreetLight streetLight=new StreetLight(  x_side,  y_side, z_side, forniture_type,
				leg_length,  leg_side, front_height,  back_height,
				 side_width, side_length,  side_height,
				 upper_width, upper_length,  upper_height
				);
		
		return streetLight.getMesh();
		
	}
	


	private PolygonMesh buildBarrelMesh() {
		
		double rMin=upper_width;
		double rMaX=side_width;
		double h=z_side;	
		
		int num=5;
		
		Point3D[] profile=new Point3D[num];
		
		double dx=h/(num-1);
		
		for (int i = 0; i < profile.length; i++) {
			
			double x=i*dx;
			double y=rMaX-(rMaX-rMin)*(x-h*0.5)*(x-h*0.5)/(h*h*0.25);
			
			profile[i]=new Point3D(x,y,0);
			
		}

		specificData=new Barrel(10,profile);
		
		PolygonMesh pm=specificData.getMesh();
	
		return pm;
	}
	

}	