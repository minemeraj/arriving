package com.editors.models;

import java.util.Vector;

import com.BPoint;
import com.main.Renderer3D;
/**
 * 
 * CROSS HOUSE
 * 
 * @author Administrator
 *
 */
public class Church1Model extends Church0Model{
	
	public static String NAME="Church1";
	
	public Church1Model(double dx, double dy, double dz, double roof_height, double dx1, double dy1, double dy2,
			double dy3, double dz1, double dz2) {
		super(dx, dy, dz, roof_height, dx1, dy1, dy2, dy3, dz1,dz2);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void initMesh() {
		
		BPoint[][][] mainCross=new BPoint[4][4][2];
		
		BPoint[][][] crossRoof=new BPoint[3][3][1];

		BPoint[][][] swAisle=new BPoint[2][2][3];
		BPoint[][][] seAisle=new BPoint[2][2][3];
		BPoint[][][] nwAisle=new BPoint[2][2][3];
		BPoint[][][] neAisle=new BPoint[2][2][3];
		
		points=new Vector();
		//lower and upper base
		for(int k=0;k<2;k++){

			double z=dz*k;
			double z1=dz1*k;
			

			mainCross[1][0][k]=addBPoint(dy3,0.0,z);
			mainCross[2][0][k]=addBPoint(dy3+dx,0.0,z);
			mainCross[2][1][k]=addBPoint(dy3+dx,dy,z);
			mainCross[3][1][k]=addBPoint(dy3+dx+dy1,dy,z);
			mainCross[3][2][k]=addBPoint(dy3+dx+dy1,dy+dx1,z);
			mainCross[2][2][k]=addBPoint(dy3+dx,dy+dx1,z);
			mainCross[2][3][k]=addBPoint(dy3+dx,dy+dx1+dy2,z);
			mainCross[1][3][k]=addBPoint(dy3,dy+dx1+dy2,z);
			mainCross[1][2][k]=addBPoint(dy3,dy+dx1,z);
			mainCross[0][2][k]=addBPoint(0,dy+dx1,z);
			mainCross[0][1][k]=addBPoint(0,dy,z);
			mainCross[1][1][k]=addBPoint(dy3,dy,z);
			
			//adding the 4 aisles in anti-clockwise order,as distinct blocks
			swAisle[0][0][k]=addBPoint(dy3+dx,0.0,z1);
			swAisle[1][0][k]=addBPoint(dy3+dx+dy1,0.0,z1);
			swAisle[1][1][k]=addBPoint(dy3+dx+dy1,dy,z1);
			swAisle[0][1][k]=addBPoint(dy3+dx,dy,z1);			
			if(k==1){
				
				swAisle[0][0][k+1]=addBPoint(dy3+dx+dy1*0.5,0.0,z);
				swAisle[0][1][k+1]=addBPoint(dy3+dx+dy1*0.5,dy,z);
			}
			
			seAisle[0][0][k]=addBPoint(dy3+dx,dy+dx1,z1);
			seAisle[1][0][k]=addBPoint(dy3+dx+dy1,dy+dx1,z1);
			seAisle[1][1][k]=addBPoint(dy3+dx+dy1,dy+dx1+dy2,z1);
			seAisle[0][1][k]=addBPoint(dy3+dx,dy+dx1+dy2,z1);
			if(k==1){
				
				seAisle[0][0][k+1]=addBPoint(dy3+dx+dy1*0.5,dy+dx1,z);
				seAisle[0][1][k+1]=addBPoint(dy3+dx+dy1*0.5,dy+dx1+dy2,z);
			}
			
			nwAisle[0][0][k]=addBPoint(0,dy+dx1,z1);
			nwAisle[1][0][k]=addBPoint(dy3,dy+dx1,z1);
			nwAisle[1][1][k]=addBPoint(dy3,dy+dx1+dy2,z1);
			nwAisle[0][1][k]=addBPoint(0,dy+dx1+dy2,z1);
			if(k==1){
				
				nwAisle[0][0][k+1]=addBPoint(dy3*0.5,dy+dx1,z);
				nwAisle[0][1][k+1]=addBPoint(dy3*0.5,dy+dx1+dy2,z);
			}
			
			neAisle[0][0][k]=addBPoint(0,0.0,z1);
			neAisle[1][0][k]=addBPoint(dy3,0.0,z1);
			neAisle[1][1][k]=addBPoint(dy3,dy,z1);
			neAisle[0][1][k]=addBPoint(0,dy,z1);
			if(k==1){
				
				neAisle[0][0][k+1]=addBPoint(dy3*0.5,0,z);
				neAisle[0][1][k+1]=addBPoint(dy3*0.5,dy,z);
			}
		}
				
		
		//cross roof
		crossRoof[1][0][0]=addBPoint(dy3+dx*0.5,0.0,dz+roof_height);
		crossRoof[0][1][0]=addBPoint(0,dy+dx1*0.5,dz+roof_height);
		crossRoof[1][1][0]=addBPoint(dy3+dx*0.5,dy+dx1*0.5,dz+roof_height);
		crossRoof[2][1][0]=addBPoint(dy3+dx+dy1,dy+dx1*0.5,dz+roof_height);
		crossRoof[1][2][0]=addBPoint(dy3+dx*0.5,dy+dx1+dy2,dz+roof_height);
		
		texturePoints=new Vector();

		buildTextures();
		
		int NF=6*4+8+8;//AISLES+AISLES_ROOF+GABLES
		NF+=12+8+4;//CROSS+CROSS_ROOF+GABLES

		faces=new int[NF][3][4];
		
		int counter=0;
		counter=buildCross(counter,mainCross,crossRoof);
		counter=buildAisle(counter,swAisle);
		counter=buildAisle(counter,seAisle);
		counter=buildAisle(counter,nwAisle);
		counter=buildAisle(counter,neAisle);
		

		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);
	}



	private int buildAisle(int counter, BPoint[][][] swAisle) {
		

		
		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, swAisle[0][0][0],swAisle[0][1][0],swAisle[1][1][0],swAisle[0][1][0],wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, swAisle[0][0][0],swAisle[1][0][0],swAisle[1][0][1],swAisle[0][0][1], wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_LEFT, swAisle[0][0][0],swAisle[0][0][1],swAisle[0][1][1],swAisle[0][1][0], wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, swAisle[1][0][0],swAisle[1][1][0],swAisle[1][1][1],swAisle[1][0][1], wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[0][0][1],swAisle[1][0][1],swAisle[1][1][1],swAisle[0][1][1], wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, swAisle[0][1][0],swAisle[0][1][1],swAisle[1][1][1],swAisle[1][1][0], wa[0]);
		
		
		//roof, to test after
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[0][0][1],swAisle[0][0][2],swAisle[0][1][2],swAisle[0][1][1],wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_TOP, swAisle[1][0][1],swAisle[1][1][1],swAisle[0][1][2],swAisle[0][0][2], wa[0]);
		
		//gables
		faces[counter++]=buildFace(Renderer3D.CAR_BACK, swAisle[0][0][1],swAisle[1][0][1],swAisle[0][0][2],wa[0]);
		faces[counter++]=buildFace(Renderer3D.CAR_FRONT, swAisle[0][1][1],swAisle[0][1][2],swAisle[1][1][1],wa[0]);
		
		return counter;
	}

	

}
