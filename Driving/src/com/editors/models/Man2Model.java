package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
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

	int bustFacesNum=0;

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
		BPoint[][] leftArm=buildLeftArm(bust,arm_length);
		BPoint[][] rightArm=buildRightArm(bust,arm_length);

		double deltax=100;
		double deltay=100;

		int xNumSections=19;  
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

				bustFacesNum++;
			}
		}

		int nl=4;

		for(int k=0;k<leftLeg.length-1;k++){
			for (int i = 0; i < nl; i++) {

				buildFace(tFaces,counter++,
						leftLeg[k][i],
						leftLeg[k][(i+1)%nl],
						leftLeg[k+1][(i+1)%nl],
						leftLeg[k+1][(i)],
						xNumSections,zNumSections);

			}
		}

		for(int k=0;k<rightLeg.length-1;k++){
			for (int i = 0; i < nl; i++) {

				buildFace(tFaces,counter++,
						rightLeg[k][i],
						rightLeg[k][(i+1)%nl],
						rightLeg[k+1][(i+1)%nl],
						rightLeg[k+1][(i)],
						xNumSections,zNumSections);

			}
		}

		for(int k=0;k<leftArm.length-1;k++){
			for (int i = 0; i < nl; i++) {

				buildFace(tFaces,counter++,
						leftArm[k][i],
						leftArm[k][(i+1)%nl],
						leftArm[k+1][(i+1)%nl],
						leftArm[k+1][(i)],
						xNumSections,zNumSections);

			}
		}

		for(int k=0;k<rightArm.length-1;k++){
			for (int i = 0; i < nl; i++) {

				buildFace(tFaces,counter++,
						rightArm[k][i],
						rightArm[k][(i+1)%nl],
						rightArm[k+1][(i+1)%nl],
						rightArm[k+1][(i)],
						xNumSections,zNumSections);

			}
		}

		faces=new int[counter][3][];

		for (int i = 0; i < counter; i++) {

			faces[i] = (int[][]) tFaces[i];

		}
		//writeNewCode();

		IMG_WIDTH=(int) (2*bx+deltax*(xNumSections-1));
		IMG_HEIGHT=(int) (2*by+deltay*((zNumSections-1)));
	}


	private BPoint[][] buildRightArm(BPoint[][] bust, double arm_lenght) {

		BPoint[][] arm=new BPoint[3][4];

		double deltay=dy*0.5;

		double arm_width=20;
		double zm=arm_lenght;

		arm[0][0]= addBPoint(bust[3][8].x,-deltay,bust[3][4].z-zm);
		arm[0][1]= addBPoint(bust[3][8].x+arm_width,-deltay,bust[3][4].z-zm);
		arm[0][2]= addBPoint(bust[3][9].x+arm_width,deltay,bust[3][5].z-zm);
		arm[0][3]= addBPoint(bust[3][9].x,deltay,bust[3][5].z-zm);

		arm[1][0]= addBPoint(bust[3][8].x,-deltay,bust[3][4].z-zm*0.5);
		arm[1][1]= addBPoint(bust[3][8].x+arm_width,-deltay,bust[3][4].z-zm*0.5);
		arm[1][2]= addBPoint(bust[3][9].x+arm_width,deltay,bust[3][5].z-zm*0.5);
		arm[1][3]= addBPoint(bust[3][9].x,deltay,bust[3][5].z-zm*0.5);

		arm[2][0]= bust[3][8];
		arm[2][1]= bust[4][8];
		arm[2][2]= bust[4][9];
		arm[2][3]= bust[3][9];


		return arm;
	}



	private BPoint[][] buildLeftArm(BPoint[][] bust, double arm_length) {

		BPoint[][] arm=new BPoint[3][4];

		double deltay=dy*0.5;

		double arm_width=20;

		double zm=arm_length;

		arm[0][0]= addBPoint(bust[3][0].x-arm_width,-deltay,bust[3][0].z-zm);
		arm[0][1]= addBPoint(bust[3][0].x,-deltay,bust[3][0].z-zm);
		arm[0][2]= addBPoint(bust[3][17].x,deltay,bust[3][9].z-zm);
		arm[0][3]= addBPoint(bust[3][17].x-arm_width,deltay,bust[3][9].z-zm);

		arm[1][0]= addBPoint(bust[3][0].x-arm_width,-deltay,bust[3][0].z-zm*0.5);
		arm[1][1]= addBPoint(bust[3][0].x,-deltay,bust[3][0].z-zm*0.5);
		arm[1][2]= addBPoint(bust[3][17].x,deltay,bust[3][9].z-zm*0.5);
		arm[1][3]= addBPoint(bust[3][17].x-arm_width,deltay,bust[3][9].z-zm*0.5);

		arm[2][0]= bust[4][0];
		arm[2][1]= bust[3][0];
		arm[2][2]= bust[3][17];
		arm[2][3]= bust[4][17];


		return arm;
	}

	private BPoint[][] buildRightLeg(BPoint[][] bust, double leg_lenght) {

		BPoint[][] leg=new BPoint[3][4];

		double deltay=dy*0.5;
		double deltaz=leg_lenght;

		double leg_width=20;

		leg[0][0]= addBPoint(bust[0][8].x,-deltay,0);
		leg[0][1]= addBPoint(bust[0][8].x+leg_width,-deltay,0);
		leg[0][2]= addBPoint(bust[0][9].x+leg_width,deltay,0);
		leg[0][3]= addBPoint(bust[0][9].x,deltay,0);

		leg[1][0]= addBPoint(bust[0][8].x,-deltay,deltaz*0.5);
		leg[1][1]= addBPoint(bust[0][8].x+leg_width,-deltay,deltaz*0.5);
		leg[1][2]= addBPoint(bust[0][9].x+leg_width,deltay,deltaz*0.5);
		leg[1][3]= addBPoint(bust[0][9].x,deltay,deltaz*0.5);

		leg[2][0]= bust[0][8];
		leg[2][1]= bust[1][8];
		leg[2][2]= bust[1][9];
		leg[2][3]= bust[0][9];


		return leg;
	}

	private BPoint[][] buildLeftLeg(BPoint[][] bust, double leg_lenght) {
		BPoint[][] leg=new BPoint[3][4];

		double deltay=dy*0.5;
		double deltaz=leg_lenght;

		double leg_width=20;

		leg[0][0]= addBPoint(bust[0][0].x-leg_width,-deltay,0);
		leg[0][1]= addBPoint(bust[0][0].x,-deltay,0);
		leg[0][2]= addBPoint(bust[0][17].x,deltay,0);
		leg[0][3]= addBPoint(bust[0][17].x-leg_width,deltay,0);

		leg[1][0]= addBPoint(bust[0][0].x-leg_width,-deltay,deltaz*0.5);
		leg[1][1]= addBPoint(bust[0][0].x,-deltay,deltaz*0.5);
		leg[1][2]= addBPoint(bust[0][17].x,deltay,deltaz*0.5);
		leg[1][3]= addBPoint(bust[0][17].x-leg_width,deltay,deltaz*0.5);		

		leg[2][0]= bust[1][0];
		leg[2][1]= bust[0][0];
		leg[2][2]= bust[0][17];
		leg[2][3]= bust[1][17];


		return leg;
	}

	private BPoint[][] buildBustBPoints(double dx, double dy, double dz, double leg_lenght) {


		BPoint[][] bust=new BPoint[11][18];

		double deltax=dx*0.5;
		double deltay=dy*0.5;
		double deltaz=leg_lenght;

		bust[0][0]= addBPoint(-deltax*0.1111,-deltay*1.0,deltaz*1.0);
		bust[0][1]= addBPoint(-deltax*0.08335000000000001,-deltay*1.0,deltaz*1.0);
		bust[0][2]= addBPoint(-deltax*0.0556,-deltay*1.0,deltaz*1.0);
		bust[0][3]= addBPoint(-deltax*0.0278,-deltay*1.0,deltaz*1.0);
		bust[0][4]= addBPoint(-deltax*0.0,-deltay*1.0,deltaz*1.0);
		bust[0][5]= addBPoint(deltax*0.0278,-deltay*1.0,deltaz*1.0);
		bust[0][6]= addBPoint(deltax*0.0556,-deltay*1.0,deltaz*1.0);
		bust[0][7]= addBPoint(deltax*0.08335000000000001,-deltay*1.0,deltaz*1.0);
		bust[0][8]= addBPoint(deltax*0.1111,-deltay*1.0,deltaz*1.0);
		bust[0][9]= addBPoint(deltax*0.1111,deltay*1.0,deltaz*1.0);
		bust[0][10]= addBPoint(deltax*0.08335000000000001,deltay*1.0,deltaz*1.0);
		bust[0][11]= addBPoint(deltax*0.0556,deltay*1.0,deltaz*1.0);
		bust[0][12]= addBPoint(deltax*0.0278,deltay*1.0,deltaz*1.0);
		bust[0][13]= addBPoint(deltax*0.0,deltay*1.0,deltaz*1.0);
		bust[0][14]= addBPoint(-deltax*0.0278,deltay*1.0,deltaz*1.0);
		bust[0][15]= addBPoint(-deltax*0.0556,deltay*1.0,deltaz*1.0);
		bust[0][16]= addBPoint(-deltax*0.08335000000000001,deltay*1.0,deltaz*1.0);
		bust[0][17]= addBPoint(-deltax*0.1111,deltay*1.0,deltaz*1.0);

		bust[1][0]= addBPoint(-deltax*0.6032,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][1]= addBPoint(-deltax*0.45239999999999997,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][2]= addBPoint(-deltax*0.3016,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][3]= addBPoint(-deltax*0.1508,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][5]= addBPoint(deltax*0.1508,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][6]= addBPoint(deltax*0.3016,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][7]= addBPoint(deltax*0.45239999999999997,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][8]= addBPoint(deltax*0.6032,-deltay*1.0,dz+deltaz*0.1239);
		bust[1][9]= addBPoint(deltax*0.6032,deltay*1.0,dz+deltaz*0.1239);
		bust[1][10]= addBPoint(deltax*0.45239999999999997,deltay*1.0,dz+deltaz*0.1239);
		bust[1][11]= addBPoint(deltax*0.3016,deltay*1.0,dz+deltaz*0.1239);
		bust[1][12]= addBPoint(deltax*0.1508,deltay*1.0,dz+deltaz*0.1239);
		bust[1][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.1239);
		bust[1][14]= addBPoint(-deltax*0.1508,deltay*1.0,dz+deltaz*0.1239);
		bust[1][15]= addBPoint(-deltax*0.3016,deltay*1.0,dz+deltaz*0.1239);
		bust[1][16]= addBPoint(-deltax*0.45239999999999997,deltay*1.0,dz+deltaz*0.1239);
		bust[1][17]= addBPoint(-deltax*0.6032,deltay*1.0,dz+deltaz*0.1239);

		bust[2][0]= addBPoint(-deltax*0.5556,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][1]= addBPoint(-deltax*0.41669999999999996,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][2]= addBPoint(-deltax*0.2778,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][3]= addBPoint(-deltax*0.1389,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][5]= addBPoint(deltax*0.1389,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][6]= addBPoint(deltax*0.2778,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][7]= addBPoint(deltax*0.41669999999999996,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][8]= addBPoint(deltax*0.5556,-deltay*1.0,dz+deltaz*0.2743);
		bust[2][9]= addBPoint(deltax*0.5556,deltay*1.0,dz+deltaz*0.2743);
		bust[2][10]= addBPoint(deltax*0.41673,deltay*1.0,dz+deltaz*0.2743);
		bust[2][11]= addBPoint(deltax*0.27786,deltay*1.0,dz+deltaz*0.2743);
		bust[2][12]= addBPoint(deltax*0.13893,deltay*1.0,dz+deltaz*0.2743);
		bust[2][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.2743);
		bust[2][14]= addBPoint(-deltax*0.13893,deltay*1.0,dz+deltaz*0.2743);
		bust[2][15]= addBPoint(-deltax*0.27786,deltay*1.0,dz+deltaz*0.2743);
		bust[2][16]= addBPoint(-deltax*0.41673,deltay*1.0,dz+deltaz*0.2743);
		bust[2][17]= addBPoint(-deltax*0.5556,deltay*1.0,dz+deltaz*0.2743);

		bust[3][0]= addBPoint(-deltax*0.6508,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][1]= addBPoint(-deltax*0.48810000000000003,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][2]= addBPoint(-deltax*0.3254,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][3]= addBPoint(-deltax*0.1627,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][5]= addBPoint(deltax*0.1627,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][6]= addBPoint(deltax*0.3254,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][7]= addBPoint(deltax*0.48810000000000003,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][8]= addBPoint(deltax*0.6508,-deltay*1.0,dz+deltaz*0.4867);
		bust[3][9]= addBPoint(deltax*0.6508,deltay*1.0,dz+deltaz*0.4867);
		bust[3][10]= addBPoint(deltax*0.48810000000000003,deltay*1.0,dz+deltaz*0.4867);
		bust[3][11]= addBPoint(deltax*0.3254,deltay*1.0,dz+deltaz*0.4867);
		bust[3][12]= addBPoint(deltax*0.1627,deltay*1.0,dz+deltaz*0.4867);
		bust[3][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.4867);
		bust[3][14]= addBPoint(-deltax*0.1627,deltay*1.0,dz+deltaz*0.4867);
		bust[3][15]= addBPoint(-deltax*0.3254,deltay*1.0,dz+deltaz*0.4867);
		bust[3][16]= addBPoint(-deltax*0.48810000000000003,deltay*1.0,dz+deltaz*0.4867);
		bust[3][17]= addBPoint(-deltax*0.6508,deltay*1.0,dz+deltaz*0.4867);

		bust[4][0]= addBPoint(-deltax*1.0,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][1]= addBPoint(-deltax*0.75,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][2]= addBPoint(-deltax*0.5,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][3]= addBPoint(-deltax*0.25,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][5]= addBPoint(deltax*0.25,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][6]= addBPoint(deltax*0.5,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][7]= addBPoint(deltax*0.75,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][8]= addBPoint(deltax*1.0,-deltay*1.0,dz+deltaz*0.5221);
		bust[4][9]= addBPoint(deltax*1.0,deltay*1.0,dz+deltaz*0.5221);
		bust[4][10]= addBPoint(deltax*0.75,deltay*1.0,dz+deltaz*0.5221);
		bust[4][11]= addBPoint(deltax*0.5,deltay*1.0,dz+deltaz*0.5221);
		bust[4][12]= addBPoint(deltax*0.25,deltay*1.0,dz+deltaz*0.5221);
		bust[4][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.5221);
		bust[4][14]= addBPoint(-deltax*0.25,deltay*1.0,dz+deltaz*0.5221);
		bust[4][15]= addBPoint(-deltax*0.5,deltay*1.0,dz+deltaz*0.5221);
		bust[4][16]= addBPoint(-deltax*0.75,deltay*1.0,dz+deltaz*0.5221);
		bust[4][17]= addBPoint(-deltax*1.0,deltay*1.0,dz+deltaz*0.5221);

		bust[5][0]= addBPoint(-deltax*0.9206,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][1]= addBPoint(-deltax*0.69045,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][2]= addBPoint(-deltax*0.4603,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][3]= addBPoint(-deltax*0.23015,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][5]= addBPoint(deltax*0.23015,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][6]= addBPoint(deltax*0.4603,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][7]= addBPoint(deltax*0.69045,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][8]= addBPoint(deltax*0.9206,-deltay*1.0,dz+deltaz*0.5929);
		bust[5][9]= addBPoint(deltax*0.9206,deltay*1.0,dz+deltaz*0.5929);
		bust[5][10]= addBPoint(deltax*0.69045,deltay*1.0,dz+deltaz*0.5929);
		bust[5][11]= addBPoint(deltax*0.4603,deltay*1.0,dz+deltaz*0.5929);
		bust[5][12]= addBPoint(deltax*0.23015,deltay*1.0,dz+deltaz*0.5929);
		bust[5][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.5929);
		bust[5][14]= addBPoint(-deltax*0.23015,deltay*1.0,dz+deltaz*0.5929);
		bust[5][15]= addBPoint(-deltax*0.4603,deltay*1.0,dz+deltaz*0.5929);
		bust[5][16]= addBPoint(-deltax*0.69045,deltay*1.0,dz+deltaz*0.5929);
		bust[5][17]= addBPoint(-deltax*0.9206,deltay*1.0,dz+deltaz*0.5929);

		bust[6][0]= addBPoint(-deltax*0.7778,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][1]= addBPoint(-deltax*0.58335,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][2]= addBPoint(-deltax*0.3889,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][3]= addBPoint(-deltax*0.19445,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][5]= addBPoint(deltax*0.19445,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][6]= addBPoint(deltax*0.3889,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][7]= addBPoint(deltax*0.58335,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][8]= addBPoint(deltax*0.7778,-deltay*1.0,dz+deltaz*0.6372);
		bust[6][9]= addBPoint(deltax*0.7778,deltay*1.0,dz+deltaz*0.6372);
		bust[6][10]= addBPoint(deltax*0.58335,deltay*1.0,dz+deltaz*0.6372);
		bust[6][11]= addBPoint(deltax*0.3889,deltay*1.0,dz+deltaz*0.6372);
		bust[6][12]= addBPoint(deltax*0.19445,deltay*1.0,dz+deltaz*0.6372);
		bust[6][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.6372);
		bust[6][14]= addBPoint(-deltax*0.19445,deltay*1.0,dz+deltaz*0.6372);
		bust[6][15]= addBPoint(-deltax*0.3889,deltay*1.0,dz+deltaz*0.6372);
		bust[6][16]= addBPoint(-deltax*0.58335,deltay*1.0,dz+deltaz*0.6372);
		bust[6][17]= addBPoint(-deltax*0.7778,deltay*1.0,dz+deltaz*0.6372);

		bust[7][0]= addBPoint(-deltax*0.2381,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][1]= addBPoint(-deltax*0.1786,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][2]= addBPoint(-deltax*0.1191,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][3]= addBPoint(-deltax*0.05955,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][5]= addBPoint(deltax*0.05955,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][6]= addBPoint(deltax*0.1191,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][7]= addBPoint(deltax*0.1786,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][8]= addBPoint(deltax*0.2381,-deltay*1.0,dz+deltaz*0.6991);
		bust[7][9]= addBPoint(deltax*0.2381,deltay*1.0,dz+deltaz*0.6991);
		bust[7][10]= addBPoint(deltax*0.1786,deltay*1.0,dz+deltaz*0.6991);
		bust[7][11]= addBPoint(deltax*0.1191,deltay*1.0,dz+deltaz*0.6991);
		bust[7][12]= addBPoint(deltax*0.05955,deltay*1.0,dz+deltaz*0.6991);
		bust[7][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.6991);
		bust[7][14]= addBPoint(-deltax*0.05955,deltay*1.0,dz+deltaz*0.6991);
		bust[7][15]= addBPoint(-deltax*0.1191,deltay*1.0,dz+deltaz*0.6991);
		bust[7][16]= addBPoint(-deltax*0.1786,deltay*1.0,dz+deltaz*0.6991);
		bust[7][17]= addBPoint(-deltax*0.2381,deltay*1.0,dz+deltaz*0.6991);

		bust[8][0]= addBPoint(-deltax*0.254,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][1]= addBPoint(-deltax*0.1905,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][2]= addBPoint(-deltax*0.127,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][3]= addBPoint(-deltax*0.0635,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][5]= addBPoint(deltax*0.0635,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][6]= addBPoint(deltax*0.127,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][7]= addBPoint(deltax*0.1905,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][8]= addBPoint(deltax*0.254,-deltay*1.0,dz+deltaz*0.7611);
		bust[8][9]= addBPoint(deltax*0.254,deltay*1.0,dz+deltaz*0.7611);
		bust[8][10]= addBPoint(deltax*0.1905,deltay*1.0,dz+deltaz*0.7611);
		bust[8][11]= addBPoint(deltax*0.127,deltay*1.0,dz+deltaz*0.7611);
		bust[8][12]= addBPoint(deltax*0.0635,deltay*1.0,dz+deltaz*0.7611);
		bust[8][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.7611);
		bust[8][14]= addBPoint(-deltax*0.0635,deltay*1.0,dz+deltaz*0.7611);
		bust[8][15]= addBPoint(-deltax*0.127,deltay*1.0,dz+deltaz*0.7611);
		bust[8][16]= addBPoint(-deltax*0.1905,deltay*1.0,dz+deltaz*0.7611);
		bust[8][17]= addBPoint(-deltax*0.254,deltay*1.0,dz+deltaz*0.7611);

		bust[9][0]= addBPoint(-deltax*0.381,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][1]= addBPoint(-deltax*0.28575,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][2]= addBPoint(-deltax*0.1905,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][3]= addBPoint(-deltax*0.09525,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][5]= addBPoint(deltax*0.09525,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][6]= addBPoint(deltax*0.1905,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][7]= addBPoint(deltax*0.28575,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][8]= addBPoint(deltax*0.381,-deltay*1.0,dz+deltaz*0.9027);
		bust[9][9]= addBPoint(deltax*0.381,deltay*1.0,dz+deltaz*0.9027);
		bust[9][10]= addBPoint(deltax*0.28575,deltay*1.0,dz+deltaz*0.9027);
		bust[9][11]= addBPoint(deltax*0.1905,deltay*1.0,dz+deltaz*0.9027);
		bust[9][12]= addBPoint(deltax*0.09525,deltay*1.0,dz+deltaz*0.9027);
		bust[9][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.9027);
		bust[9][14]= addBPoint(-deltax*0.09525,deltay*1.0,dz+deltaz*0.9027);
		bust[9][15]= addBPoint(-deltax*0.1905,deltay*1.0,dz+deltaz*0.9027);
		bust[9][16]= addBPoint(-deltax*0.28575,deltay*1.0,dz+deltaz*0.9027);
		bust[9][17]= addBPoint(-deltax*0.381,deltay*1.0,dz+deltaz*0.9027);

		bust[10][0]= addBPoint(-deltax*0.1905,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][1]= addBPoint(-deltax*0.1429,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][2]= addBPoint(-deltax*0.0953,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][3]= addBPoint(-deltax*0.04765,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][4]= addBPoint(-deltax*0.0,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][5]= addBPoint(deltax*0.04765,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][6]= addBPoint(deltax*0.0953,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][7]= addBPoint(deltax*0.1429,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][8]= addBPoint(deltax*0.1905,-deltay*1.0,dz+deltaz*0.9823);
		bust[10][9]= addBPoint(deltax*0.1905,deltay*1.0,dz+deltaz*0.9823);
		bust[10][10]= addBPoint(deltax*0.1429,deltay*1.0,dz+deltaz*0.9823);
		bust[10][11]= addBPoint(deltax*0.0953,deltay*1.0,dz+deltaz*0.9823);
		bust[10][12]= addBPoint(deltax*0.04765,deltay*1.0,dz+deltaz*0.9823);
		bust[10][13]= addBPoint(deltax*0.0,deltay*1.0,dz+deltaz*0.9823);
		bust[10][14]= addBPoint(-deltax*0.04765,deltay*1.0,dz+deltaz*0.9823);
		bust[10][15]= addBPoint(-deltax*0.0953,deltay*1.0,dz+deltaz*0.9823);
		bust[10][16]= addBPoint(-deltax*0.1429,deltay*1.0,dz+deltaz*0.9823);
		bust[10][17]= addBPoint(-deltax*0.1905,deltay*1.0,dz+deltaz*0.9823);




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


		bg.setStroke(new BasicStroke(0.1f));	

		bg.setColor(Color.RED);
		printTextureFaces(bg,faces,0,bustFacesNum);

		bg.setColor(Color.BLACK);
		printTextureFaces(bg,faces,bustFacesNum,faces.length);

	}



	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}


}
