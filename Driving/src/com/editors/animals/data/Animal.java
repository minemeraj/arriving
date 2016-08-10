package com.editors.animals.data;

import com.BPoint;
import com.CustomData;
import com.PolygonMesh;
import com.Segments;

public class Animal extends CustomData{

	double x_side=0;
	double y_side=0;
	double z_side=0;	
	
	double head_DZ=0;
	double head_DX=0;
	double head_DY=0;
	
	double neck_length=0;
	double neck_side=0;
	
	double humerus_length=0;
	double radius_length=0;
	double hand_length=0;
	
	double leg_side=0;
	
	double femur_length=0; 
	double shinbone_length=0;
	
	double foot_length=0;
	
	public static final int ANIMAL_TYPE_QUADRUPED=0;
	public static final int ANIMAL_TYPE_HUMAN=1;
	public static final int ANIMAL_TYPE_MANHEAD=2;
	public static final int ANIMAL_TYPE_MANHAND=3;
	
	int animal_type=ANIMAL_TYPE_HUMAN;
	
	static final int HEAD=0;
	static final int TRUNK=1;
	static final int LEFT_FEMUR=2;
	static final int RIGHT_FEMUR=3;
	static final int LEFT_HOMERUS=4;
	static final int RIGHT_HOMERUS=5;
	static final int LEFT_RADIUS=6;
	static final int RIGHT_RADIUS=7;
	static final int LEFT_SHINBONE=8;
	static final int RIGHT_SHINBONE=9;


	public Animal(){}

	public Animal(double x_side, double y_side,double z_side,int animal_type,
			double femur_length,double shinbone_length,double leg_side,
			double head_DZ,double head_DX,double head_DY,double neck_length,double neck_side,
			double humerus_length,double radius_length,double hand_length,double foot_length
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.animal_type = animal_type;

		this.head_DZ = head_DZ;
		this.head_DX = head_DX;
		this.head_DY = head_DY;
		this.neck_length = neck_length; 
		this.neck_side = neck_side;
		
		this.humerus_length = humerus_length;
		this.radius_length = radius_length;
		this.foot_length = foot_length;
		
		this.femur_length = femur_length;
		this.shinbone_length = shinbone_length;
		this.leg_side = leg_side;
		
		this.hand_length = hand_length;
		this.foot_length = foot_length;
		
	}
	@Override
	public Object clone(){

		Animal animal=new Animal(
				x_side,y_side,z_side,animal_type,femur_length,shinbone_length,leg_side,
				head_DZ,head_DX,head_DY,neck_length,neck_side,humerus_length,radius_length,
				hand_length,foot_length
				);
		return animal;

	}
	
	BPoint addBPoint( double x, double y, double z,int body_part,Segments s){
		
		BPoint point=new BPoint(s.x(x), s.y(y), s.z(z), n++);
		point.setData(""+body_part);
		set(points,point.getIndex(),point);
		return point;
		
	}
	
	BPoint addBPoint(double x, double y, double z,int body_part){
		
		BPoint point=new BPoint(x, y, z, n++);
		point.setData(""+body_part);
		set(points,point.getIndex(),point);
		return point;
		
	}

	public int getAnimal_type() {
		return animal_type;
	}

	public void setAnimal_type(int animal_type) {
		this.animal_type = animal_type;
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

		String ret= "A="+x_side+","+y_side+","+z_side+","+animal_type;
		ret+=","+femur_length+","+shinbone_length+","+leg_side+
				","+head_DZ+","+head_DX+","+head_DY+","+neck_length+","+neck_side+","
				+humerus_length+","+radius_length+","+hand_length+","+foot_length;
				
		return ret;		
	}

	public static Animal buildAnimal(String str) {

		String[] vals = str.split(",");


		double x_side =Double.parseDouble(vals[0]);
		double y_side = Double.parseDouble(vals[1]);
		double z_side = Double.parseDouble(vals[2]); 
		int animal_type =Integer.parseInt(vals[3]);		
		
		double femurLength=Double.parseDouble(vals[4]);		
		double shinboneLength=Double.parseDouble(vals[5]);			
		double legSide = Double.parseDouble(vals[6]);	
		
		double headDZ = Double.parseDouble(vals[7]); 
		double headDX = Double.parseDouble(vals[8]); 
		double headDY = Double.parseDouble(vals[9]);
		double neckLength = Double.parseDouble(vals[10]);
		double neckSide = Double.parseDouble(vals[11]);
		
		double humerusLength =Double.parseDouble(vals[12]);
		double radiusLength = Double.parseDouble(vals[13]);
		
		double handLength=Double.parseDouble(vals[14]);
		double footLength=Double.parseDouble(vals[15]);

		Animal grid=new Animal(x_side,y_side,z_side,animal_type,
				femurLength,shinboneLength,legSide,
				headDZ,headDX,headDY,neckLength,neckSide,
				humerusLength,radiusLength,handLength,footLength);

		return grid;
	}



	public double getZ_side() {
		return z_side;
	}
	
	public void setZ_side(double z_side) {
		this.z_side = z_side;
	}

	public double getLeg_side() {
		return leg_side;
	}

	public void setLeg_side(double leg_side) {
		this.leg_side = leg_side;
	}

	
	public double getFemur_length() {
		return femur_length;
	}

	public void setFemur_length(double leg_length) {
		this.femur_length = leg_length;
	}
	

	public double getHead_DY() {
		return head_DY;
	}

	public void setHead_DY(double head_DY) {
		this.head_DY = head_DY;
	}

	public double getHead_DZ() {
		return head_DZ;
	}

	public void setHead_DZ(double head_DZ) {
		this.head_DZ = head_DZ;
	}

	public double getHead_DX() {
		return head_DX;
	}

	public void setHead_DX(double head_DX) {
		this.head_DX = head_DX;
	}

	public double getNeck_length() {
		return neck_length;
	}

	public void setNeck_length(double neck_length) {
		this.neck_length = neck_length;
	}

	public double getHumerus_length() {
		return humerus_length;
	}

	public void setHumerus_length(double arm_length) {
		this.humerus_length = arm_length;
	}

	public double getNeck_side() {
		return neck_side;
	}

	public void setNeck_side(double neck_side) {
		this.neck_side = neck_side;
	}

	public double getRadius_length() {
		return radius_length;
	}

	public void setRadius_length(double radius_length) {
		this.radius_length = radius_length;
	}

	public double getShinbone_length() {
		return shinbone_length;
	}

	public void setShinbone_length(double shinbone_length) {
		this.shinbone_length = shinbone_length;
	}
	
	public double getFoot_length() {
		return foot_length;
	}

	public void setFoot_length(double foot_length) {
		this.foot_length = foot_length;
	}
	
	public double getHand_length() {
		return hand_length;
	}

	public void setHand_length(double hand_length) {
		this.hand_length = hand_length;
	}
	@Override
	public PolygonMesh buildMesh(){
		
		
		if(ANIMAL_TYPE_HUMAN==animal_type){	
			
			return buildHumanMesh();
			
			
			
		}
		else if(ANIMAL_TYPE_QUADRUPED==animal_type){			
			
			return buildQuadrupedMesh();
			
		}
		else if(ANIMAL_TYPE_MANHEAD==animal_type){			
			
			return  buildManHeadMesh();
			
		}
		else
			return buildManHandMesh();

		


	}

	

	private PolygonMesh buildHumanMesh() {
		
		
		Man man=new Man( x_side,  y_side, z_side, animal_type,
				 femur_length, shinbone_length, leg_side,
				 head_DZ, head_DX, head_DY, neck_length, neck_side,
				 humerus_length, radius_length, hand_length, foot_length
			);
		
		specificData=man;
		
		return man.getMesh();
		
		
	}

	private PolygonMesh buildQuadrupedMesh() {
		
		Quadruped quadruped=new Quadruped( x_side,  y_side, z_side, animal_type,
				 femur_length, shinbone_length, leg_side,
				 head_DZ, head_DX, head_DY, neck_length, neck_side,
				 humerus_length, radius_length, hand_length, foot_length);
		
		specificData=quadruped;
		
		return quadruped.getMesh();


	
	}



	private PolygonMesh buildManHeadMesh() {
		
		
		ManHead head=new ManHead( x_side,  y_side, z_side, animal_type,
				 femur_length, shinbone_length, leg_side,
				 head_DZ, head_DX, head_DY, neck_length, neck_side,
				 humerus_length, radius_length, hand_length, foot_length
			);
		
		specificData=head;
		
		return head.getMesh();
	}
	

	private PolygonMesh buildManHandMesh() {
		
		
		ManHand hand=new ManHand( x_side,  y_side, z_side, animal_type,
				 femur_length, shinbone_length, leg_side,
				 head_DZ, head_DX, head_DY, neck_length, neck_side,
				 humerus_length, radius_length, hand_length, foot_length
			);
		return hand.getMesh();
		
	
	}






}	