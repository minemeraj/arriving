package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.editors.cars.data.Car;
import com.main.Renderer3D;

public class Man2Model extends MeshModel{

	double dx = 0;
	double dy = 0;
	double dz = 0;
	
	double leg_length = 0;
	double arm_length = 0;

	private int[][][] faces; 



	int bx=10;
	int by=10;

	public Man2Model(double dx, double dy, double dz,
			double leg_length,
			double arm_length
			) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		this.leg_length = leg_length;
		this.arm_length = arm_length;
	}

	@Override
	public void initMesh() {

		points=new Vector();
		texturePoints=new Vector();

		///BUST
		BPoint[][] bust=buildBustBPoints(dx,dy,dz,leg_length);
		BPoint[][] leftLeg=buildLeftLeg(bust,leg_length);
		BPoint[][] rightLeg=buildRightLeg(bust,leg_length);
		BPoint[][] leftArm=buildLeftArm(bust,leg_length);
		BPoint[][] rightArm=buildRightArm(bust,leg_length);

		double deltax=100;
		double deltay=100;

		int xNumSections=7;  
		int zNumSections=bust.length+leftLeg.length+rightLeg.length
				+leftArm.length+rightArm.length;

		int  NUMFACES=(xNumSections-1)*(zNumSections-1);

		for (int k = 0; k < zNumSections; k++) { 


			double y=by+deltay*k;

			for (int i = 0; i < xNumSections; i++) {

				double x=bx+deltax*i;


				addTPoint(x, y, 0);

			}

		}


		int[][][] tFaces = new int[NUMFACES][3][4];

		int counter=0;

		int ns=xNumSections-1;
		for(int k=0;k<bust.length-1;k++){
			for (int i = 0; i < ns; i++) {
	
				buildFace(tFaces,counter++,
						bust[k][i],
						bust[k][(i+1)%ns],
						bust[k+1][(i+1)%ns],
						bust[k+1][(i)],
						xNumSections,zNumSections);
	
			}
		}
		
		for(int k=0;k<leftLeg.length-1;k++){
			for (int i = 0; i < 4; i++) {
	
				buildFace(tFaces,counter++,
						leftLeg[k][i],
						leftLeg[k][(i+1)%4],
						leftLeg[k+1][(i+1)%4],
						leftLeg[k+1][(i)],
						xNumSections,zNumSections);
	
			}
		}
		
		for(int k=0;k<rightLeg.length-1;k++){
			for (int i = 0; i < 4; i++) {
	
				buildFace(tFaces,counter++,
						rightLeg[k][i],
						rightLeg[k][(i+1)%4],
						rightLeg[k+1][(i+1)%4],
						rightLeg[k+1][(i)],
						xNumSections,zNumSections);
	
			}
		}
		
		for(int k=0;k<leftArm.length-1;k++){
			for (int i = 0; i < 4; i++) {
	
				buildFace(tFaces,counter++,
						leftArm[k][i],
						leftArm[k][(i+1)%4],
						leftArm[k+1][(i+1)%4],
						leftArm[k+1][(i)],
						xNumSections,zNumSections);
	
			}
		}
		
		for(int k=0;k<rightArm.length-1;k++){
			for (int i = 0; i < 4; i++) {
	
				buildFace(tFaces,counter++,
						rightArm[k][i],
						rightArm[k][(i+1)%4],
						rightArm[k+1][(i+1)%4],
						rightArm[k+1][(i)],
						xNumSections,zNumSections);
	
			}
		}
		
		faces=new int[counter][3][];

		for (int i = 0; i < counter; i++) {

			faces[i] = (int[][]) tFaces[i];

		}


		IMG_WIDTH=(int) (2*bx+deltax*(xNumSections-1));
		IMG_HEIGHT=(int) (2*by+deltay*((zNumSections-1)));
	}


	private BPoint[][] buildRightArm(BPoint[][] bust, double leg_lenght) {
		
		BPoint[][] arm=new BPoint[2][4];
		
		double deltay=dy*0.5;

		double leg_width=20;
		double zm=leg_lenght;

		arm[0][0]= addBPoint(bust[3][2].x,-deltay,zm);
		arm[0][1]= addBPoint(bust[3][2].x+leg_width,-deltay,zm);
		arm[0][2]= addBPoint(bust[3][3].x+leg_width,deltay,zm);
		arm[0][3]= addBPoint(bust[3][3].x,deltay,zm);
		
		arm[1][0]= bust[3][2];
		arm[1][1]= bust[4][2];
		arm[1][2]= bust[4][3];
		arm[1][3]= bust[3][3];
		
		
		return arm;
	}



	private BPoint[][] buildLeftArm(BPoint[][] bust, double leg_lenght) {
		
		BPoint[][] arm=new BPoint[2][4];
		
		double deltay=dy*0.5;

		double arm_width=20;
		
		double zm=leg_lenght;

		arm[0][0]= addBPoint(bust[3][0].x-arm_width,-deltay,zm);
		arm[0][1]= addBPoint(bust[3][0].x,-deltay,zm);
		arm[0][2]= addBPoint(bust[3][5].x,deltay,zm);
		arm[0][3]= addBPoint(bust[3][5].x-arm_width,deltay,zm);
		
		arm[1][0]= bust[4][0];
		arm[1][1]= bust[3][0];
		arm[1][2]= bust[3][5];
		arm[1][3]= bust[4][5];
		
		
		return arm;
	}

	private BPoint[][] buildRightLeg(BPoint[][] bust, double leg_lenght) {
		
		BPoint[][] leg=new BPoint[2][4];
		
		double deltay=dy*0.5;

		double leg_width=20;

		leg[0][0]= addBPoint(bust[0][2].x,-deltay,0);
		leg[0][1]= addBPoint(bust[0][2].x+leg_width,-deltay,0);
		leg[0][2]= addBPoint(bust[0][3].x+leg_width,deltay,0);
		leg[0][3]= addBPoint(bust[0][3].x,deltay,0);
		
		leg[1][0]= bust[0][2];
		leg[1][1]= bust[1][2];
		leg[1][2]= bust[1][3];
		leg[1][3]= bust[0][3];
		
		
		return leg;
	}

	private BPoint[][] buildLeftLeg(BPoint[][] bust, double leg_lenght) {
		BPoint[][] leg=new BPoint[2][4];
		
		double deltay=dy*0.5;

		double leg_width=20;

		leg[0][0]= addBPoint(bust[0][0].x-leg_width,-deltay,0);
		leg[0][1]= addBPoint(bust[0][0].x,-deltay,0);
		leg[0][2]= addBPoint(bust[0][5].x,deltay,0);
		leg[0][3]= addBPoint(bust[0][5].x-leg_width,deltay,0);
		
		leg[1][0]= bust[1][0];
		leg[1][1]= bust[0][0];
		leg[1][2]= bust[0][5];
		leg[1][3]= bust[1][5];
		
		
		return leg;
	}

	private BPoint[][] buildBustBPoints(double dx, double dy, double dz, double leg_lenght) {


		BPoint[][] bust=new BPoint[11][6];
		
		double deltax=dx*0.5;
		double deltay=dy*0.5;
		double deltaz=leg_lenght;

		bust[0][0]= addBPoint(-deltax*0.1111,-deltay,deltaz);
		bust[0][1]= addBPoint(deltax*0.0,-deltay,deltaz);
		bust[0][2]= addBPoint(deltax*0.1111,-deltay,deltaz);
		bust[0][3]= addBPoint(deltax*0.1111,deltay,deltaz);
		bust[0][4]= addBPoint(deltax*0.0,deltay,deltaz);
		bust[0][5]= addBPoint(-deltax*0.1111,deltay,deltaz);
		
		bust[1][0]= addBPoint(-deltax*0.6032,-deltay,dz*0.1239+deltaz);
		bust[1][1]= addBPoint(-deltax*0.0,-deltay,dz*0.1239+deltaz);
		bust[1][2]= addBPoint(deltax*0.6032,-deltay,dz*0.1239+deltaz);
		bust[1][3]= addBPoint(deltax*0.6032,deltay,dz*0.1239+deltaz);
		bust[1][4]= addBPoint(deltax*0.0,deltay,dz*0.1239+deltaz);
		bust[1][5]= addBPoint(-deltax*0.6032,deltay,dz*0.1239+deltaz);
		
		bust[2][0]= addBPoint(-deltax*0.5556,-deltay,dz*0.2743+deltaz);
		bust[2][1]= addBPoint(-deltax*0.0,-deltay,dz*0.2743+deltaz);
		bust[2][2]= addBPoint(deltax*0.5556,-deltay,dz*0.2743+deltaz);
		bust[2][3]= addBPoint(deltax*0.5556,deltay,dz*0.2743+deltaz);
		bust[2][4]= addBPoint(deltax*0.0,deltay,dz*0.2743+deltaz);
		bust[2][5]= addBPoint(-deltax*0.5556,deltay,dz*0.2743+deltaz);
		
		bust[3][0]= addBPoint(-deltax*0.6508,-deltay,dz*0.4867+deltaz);
		bust[3][1]= addBPoint(-deltax*0.0,-deltay,dz*0.4867+deltaz);
		bust[3][2]= addBPoint(deltax*0.6508,-deltay,dz*0.4867+deltaz);
		bust[3][3]= addBPoint(deltax*0.6508,deltay,dz*0.4867+deltaz);
		bust[3][4]= addBPoint(deltax*0.0,deltay,dz*0.4867+deltaz);
		bust[3][5]= addBPoint(-deltax*0.6508,deltay,dz*0.4867+deltaz);

		bust[4][0]= addBPoint(-deltax*1.0,-deltay,dz*0.5221+deltaz);
		bust[4][1]= addBPoint(-deltax*0.0,-deltay,dz*0.5221+deltaz);
		bust[4][2]= addBPoint(deltax*1.0,-deltay,dz*0.5221+deltaz);
		bust[4][3]= addBPoint(deltax*1.0,deltay,dz*0.5221+deltaz);
		bust[4][4]= addBPoint(deltax*0.0,deltay,dz*0.5221+deltaz);
		bust[4][5]= addBPoint(-deltax*1.0,deltay,dz*0.5221+deltaz);
		
		bust[5][0]= addBPoint(-deltax*0.9206,-deltay,dz*0.5929+deltaz);
		bust[5][1]= addBPoint(-deltax*0.0,-deltay,dz*0.5929+deltaz);
		bust[5][2]= addBPoint(deltax*0.9206,-deltay,dz*0.5929+deltaz);
		bust[5][3]= addBPoint(deltax*0.9206,deltay,dz*0.5929+deltaz);
		bust[5][4]= addBPoint(deltax*0.0,deltay,dz*0.5929+deltaz);
		bust[5][5]= addBPoint(-deltax*0.9206,deltay,dz*0.5929+deltaz);
		
		bust[6][0]= addBPoint(-deltax*0.7778,-deltay,dz*0.6372+deltaz);
		bust[6][1]= addBPoint(-deltax*0.0,-deltay,dz*0.6372+deltaz);
		bust[6][2]= addBPoint(deltax*0.7778,-deltay,dz*0.6372+deltaz);
		bust[6][3]= addBPoint(deltax*0.7778,deltay,dz*0.6372+deltaz);
		bust[6][4]= addBPoint(deltax*0.0,deltay,dz*0.6372+deltaz);
		bust[6][5]= addBPoint(-deltax*0.7778,deltay,dz*0.6372+deltaz);
		
		bust[7][0]= addBPoint(-deltax*0.2381,-deltay,dz*0.6991+deltaz);
		bust[7][1]= addBPoint(-deltax*0.0,-deltay,dz*0.6991+deltaz);
		bust[7][2]= addBPoint(deltax*0.2381,-deltay,dz*0.6991+deltaz);
		bust[7][3]= addBPoint(deltax*0.2381,deltay,dz*0.6991+deltaz);
		bust[7][4]= addBPoint(deltax*0.0,deltay,dz*0.6991+deltaz);
		bust[7][5]= addBPoint(-deltax*0.2381,deltay,dz*0.6991+deltaz);
		
		bust[8][0]= addBPoint(-deltax*0.2540,-deltay,dz*0.7611+deltaz);
		bust[8][1]= addBPoint(-deltax*0.0,-deltay,dz*0.7611+deltaz);
		bust[8][2]= addBPoint(deltax*0.2540,-deltay,dz*0.7611+deltaz);
		bust[8][3]= addBPoint(deltax*0.2540,deltay,dz*0.7611+deltaz);
		bust[8][4]= addBPoint(deltax*0.0,deltay,dz*0.7611+deltaz);
		bust[8][5]= addBPoint(-deltax*0.2540,deltay,dz*0.7611+deltaz);
		
		bust[9][0]= addBPoint(-deltax*0.3810,-deltay,dz*0.9027+deltaz);
		bust[9][1]= addBPoint(-deltax*0.0,-deltay,dz*0.9027+deltaz);
		bust[9][2]= addBPoint(deltax*0.3810,-deltay,dz*0.9027+deltaz);
		bust[9][3]= addBPoint(deltax*0.3810,deltay,dz*0.9027+deltaz);
		bust[9][4]= addBPoint(deltax*0.0,deltay,dz*0.9027+deltaz);
		bust[9][5]= addBPoint(-deltax*0.3810,deltay,dz*0.9027+deltaz);
		
		bust[10][0]= addBPoint(-deltax*0.1905,-deltay,dz*0.9823+deltaz);
		bust[10][1]= addBPoint(-deltax*0.0,-deltay,dz*0.9823+deltaz);
		bust[10][2]= addBPoint(deltax*0.1905,-deltay,dz*0.9823+deltaz);
		bust[10][3]= addBPoint(deltax*0.1905,deltay,dz*0.9823+deltaz);
		bust[10][4]= addBPoint(deltax*0.0,deltay,dz*0.9823+deltaz);
		bust[10][5]= addBPoint(-deltax*0.1905,deltay,dz*0.9823+deltaz);

		return bust;
	}

	private void buildFace(int[][][] faces, 
			int counter, 
			BPoint p0, BPoint p1, BPoint p2, BPoint p3, 
			int xNumSections, 
			int zNumSections
			) {

		int sz=3;
		if(p3!=null)
			sz=4;


		faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

		int[] pts = new int[sz];
		faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;

		pts[0]=p0.getIndex();
		pts[1]=p1.getIndex();
		pts[2]=p2.getIndex();
		if(p3!=null)
			pts[3]=p3.getIndex();

		int nx=(xNumSections-1);
		int m=counter/nx;
		int n=counter-m*nx;

		int p=(nx+1)*m+n;

		int[] tts = new int[sz];
		faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;

		tts[0]=p;
		tts[1]=p+1;

		if(p3!=null){

			tts[2]=p+1+(nx+1);
			tts[3]=p+(nx+1);

		}else{

			tts[2]=p+(nx+1);
		}
	}

	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);

	}



	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}




}
