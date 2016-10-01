package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.main.Renderer3D;

public class Man2Model extends MeshModel{

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private double leg_length = 0;
	private double arm_length = 0;

	private int[][][] faces;

	private int bx=10;
	private int by=10;

	private int bustFacesNum=0;

	public static final String NAME="Man2";

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

			faces[i] = tFaces[i];

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

		for (int i = 0; i < data.length; i++) {

			double[][] pts = data[i];

			for (int j = 0; j < pts.length; j++) {

				bust[i][j]= addBPoint(deltax*pts[j][0],deltay*pts[j][1],deltaz+dz*pts[j][2]);
			}


		}


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



	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}

	/**
	 * DIAGNOSTICS UTILITY
	 *
	 */
	public void printSections(){

		int w=(int) (dx)+2*bx;
		int h=(int) (2*dy)+2*by;

		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

		File file=new File("sections.jpg");

		try {



			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(Color.BLACK);
			bufGraphics.fillRect(0,0,w,h);

			bufGraphics.setColor(Color.WHITE);

			for (int i = 0; i < data.length; i++) {

				double[][] pts = data[i];


				if(i==8){

					bufGraphics.setColor(Color.WHITE);
				}else if(i==9){

					bufGraphics.setColor(Color.RED);
				}else if(i==10){

					bufGraphics.setColor(Color.GREEN);
					continue;
				}
				else
					continue;

				for (int j = 0; j < pts.length-1; j++) {

					int x=(int) (pts[j][0]*dx*0.5)+(int) (dx*0.5)+bx;
					int y=(int) (pts[j][1]*dy)+(int) (dy)+by;

					bufGraphics.fillRect(x-1, y-1, 2, 2);
				}


			}

			ImageIO.write(buf,"gif",file);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	private double[][][] data=
		{
				{

					{-0.1111,-1.0,0.0},
					{-0.08335000000000001,-1.0,0.0},
					{-0.0556,-1.0,0.0},
					{-0.0278,-1.0,0.0},
					{-0.0,-1.0,0.0},
					{0.0278,-1.0,0.0},
					{0.0556,-1.0,0.0},
					{0.08335000000000001,-1.0,0.0},
					{0.1111,-1.0,0.0},
					{0.1111,1.0,0.0},
					{0.08335000000000001,1.0,0.0},
					{0.0556,1.0,0.0},
					{0.0278,1.0,0.0},
					{0.0,1.0,0.0},
					{-0.0278,1.0,0.0},
					{-0.0556,1.0,0.0},
					{-0.08335000000000001,1.0,0.0},
					{-0.1111,1.0,0.0},
				},
				{
					{-0.6032,-1.0,0.1239},
					{-0.45239999999999997,-1.0,0.1239},
					{-0.3016,-1.0,0.1239},
					{-0.1508,-1.0,0.1239},
					{-0.0,-1.0,0.1239},
					{0.1508,-1.0,0.1239},
					{0.3016,-1.0,0.1239},
					{0.45239999999999997,-1.0,0.1239},
					{0.6032,-1.0,0.1239},
					{0.6032,1.0,0.1239},
					{0.45239999999999997,1.0,0.1239},
					{0.3016,1.0,0.1239},
					{0.1508,1.0,0.1239},
					{0.0,1.0,0.1239},
					{-0.1508,1.0,0.1239},
					{-0.3016,1.0,0.1239},
					{-0.45239999999999997,1.0,0.1239},
					{-0.6032,1.0,0.1239},
				},
				{
					{-0.5556,-1.0,0.2743},
					{-0.41669999999999996,-1.0,0.2743},
					{-0.2778,-1.0,0.2743},
					{-0.1389,-1.0,0.2743},
					{-0.0,-1.0,0.2743},
					{0.1389,-1.0,0.2743},
					{0.2778,-1.0,0.2743},
					{0.41669999999999996,-1.0,0.2743},
					{0.5556,-1.0,0.2743},
					{0.5556,1.0,0.2743},
					{0.41673,1.0,0.2743},
					{0.27786,1.0,0.2743},
					{0.13893,1.0,0.2743},
					{0.0,1.0,0.2743},
					{-0.13893,1.0,0.2743},
					{-0.27786,1.0,0.2743},
					{-0.41673,1.0,0.2743},
					{-0.5556,1.0,0.2743},
				},
				{
					{-0.6508,-1.0,0.4867},
					{-0.48810000000000003,-1.0,0.4867},
					{-0.3254,-1.0,0.4867},
					{-0.1627,-1.0,0.4867},
					{-0.0,-1.0,0.4867},
					{0.1627,-1.0,0.4867},
					{0.3254,-1.0,0.4867},
					{0.48810000000000003,-1.0,0.4867},
					{0.6508,-1.0,0.4867},
					{0.6508,1.0,0.4867},
					{0.48810000000000003,1.0,0.4867},
					{0.3254,1.0,0.4867},
					{0.1627,1.0,0.4867},
					{0.0,1.0,0.4867},
					{-0.1627,1.0,0.4867},
					{-0.3254,1.0,0.4867},
					{-0.48810000000000003,1.0,0.4867},
					{-0.6508,1.0,0.4867},
				},
				{
					{-1.0,-1.0,0.5221},
					{-0.75,-1.0,0.5221},
					{-0.5,-1.0,0.5221},
					{-0.25,-1.0,0.5221},
					{-0.0,-1.0,0.5221},
					{0.25,-1.0,0.5221},
					{0.5,-1.0,0.5221},
					{0.75,-1.0,0.5221},
					{1.0,-1.0,0.5221},
					{1.0,1.0,0.5221},
					{0.75,1.0,0.5221},
					{0.5,1.0,0.5221},
					{0.25,1.0,0.5221},
					{0.0,1.0,0.5221},
					{-0.25,1.0,0.5221},
					{-0.5,1.0,0.5221},
					{-0.75,1.0,0.5221},
					{-1.0,1.0,0.5221},
				},
				{
					{-0.9206,-1.0,0.5929},
					{-0.69045,-1.0,0.5929},
					{-0.4603,-1.0,0.5929},
					{-0.23015,-1.0,0.5929},
					{-0.0,-1.0,0.5929},
					{0.23015,-1.0,0.5929},
					{0.4603,-1.0,0.5929},
					{0.69045,-1.0,0.5929},
					{0.9206,-1.0,0.5929},
					{0.9206,1.0,0.5929},
					{0.69045,1.0,0.5929},
					{0.4603,1.0,0.5929},
					{0.23015,1.0,0.5929},
					{0.0,1.0,0.5929},
					{-0.23015,1.0,0.5929},
					{-0.4603,1.0,0.5929},
					{-0.69045,1.0,0.5929},
					{-0.9206,1.0,0.5929},
				},
				{
					{-0.7778,-1.0,0.6372},
					{-0.58335,-1.0,0.6372},
					{-0.3889,-1.0,0.6372},
					{-0.19445,-1.0,0.6372},
					{-0.0,-1.0,0.6372},
					{0.19445,-1.0,0.6372},
					{0.3889,-1.0,0.6372},
					{0.58335,-1.0,0.6372},
					{0.7778,-1.0,0.6372},
					{0.7778,1.0,0.6372},
					{0.58335,1.0,0.6372},
					{0.3889,1.0,0.6372},
					{0.19445,1.0,0.6372},
					{0.0,1.0,0.6372},
					{-0.19445,1.0,0.6372},
					{-0.3889,1.0,0.6372},
					{-0.58335,1.0,0.6372},
					{-0.7778,1.0,0.6372},
				},
				{
					{-0.2381,-1.0,0.6991},
					{-0.1786,-1.0,0.6991},
					{-0.1191,-1.0,0.6991},
					{-0.05955,-1.0,0.6991},
					{-0.0,-1.0,0.6991},
					{0.05955,-1.0,0.6991},
					{0.1191,-1.0,0.6991},
					{0.1786,-1.0,0.6991},
					{0.2381,-1.0,0.6991},
					{0.2381,1.0,0.6991},
					{0.1786,1.0,0.6991},
					{0.1191,1.0,0.6991},
					{0.05955,1.0,0.6991},
					{0.0,1.0,0.6991},
					{-0.05955,1.0,0.6991},
					{-0.1191,1.0,0.6991},
					{-0.1786,1.0,0.6991},
					{-0.2381,1.0,0.6991},
				},
				{
					{-0.254,-1.0,0.7611},
					{-0.1905,-1.0,0.7611},
					{-0.127,-1.0,0.7611},
					{-0.0635,-1.0,0.7611},
					{-0.0,-1.0,0.7611},
					{0.0635,-1.0,0.7611},
					{0.127,-1.0,0.7611},
					{0.1905,-1.0,0.7611},
					{0.254,-1.0,0.7611},
					{0.254,1.0,0.7611},
					{0.1905,1.0,0.7611},
					{0.127,1.0,0.7611},
					{0.0635,1.0,0.7611},
					{0.0,1.0,0.7611},
					{-0.0635,1.0,0.7611},
					{-0.127,1.0,0.7611},
					{-0.1905,1.0,0.7611},
					{-0.254,1.0,0.7611},
				},
				{
					{-0.381,-1.0,0.9027},
					{-0.28575,-1.0,0.9027},
					{-0.1905,-1.0,0.9027},
					{-0.09525,-1.0,0.9027},
					{-0.0,-1.0,0.9027},
					{0.09525,-1.0,0.9027},
					{0.1905,-1.0,0.9027},
					{0.28575,-1.0,0.9027},
					{0.381,-1.0,0.9027},
					{0.381,1.0,0.9027},
					{0.28575,1.0,0.9027},
					{0.1905,1.0,0.9027},
					{0.09525,1.0,0.9027},
					{0.0,1.0,0.9027},
					{-0.09525,1.0,0.9027},
					{-0.1905,1.0,0.9027},
					{-0.28575,1.0,0.9027},
					{-0.381,1.0,0.9027},
				},
				{
					{-0.1905,-1.0,0.9823},
					{-0.1429,-1.0,0.9823},
					{-0.0953,-1.0,0.9823},
					{-0.04765,-1.0,0.9823},
					{-0.0,-1.0,0.9823},
					{0.04765,-1.0,0.9823},
					{0.0953,-1.0,0.9823},
					{0.1429,-1.0,0.9823},
					{0.1905,-1.0,0.9823},
					{0.1905,1.0,0.9823},
					{0.1429,1.0,0.9823},
					{0.0953,1.0,0.9823},
					{0.04765,1.0,0.9823},
					{0.0,1.0,0.9823},
					{-0.04765,1.0,0.9823},
					{-0.0953,1.0,0.9823},
					{-0.1429,1.0,0.9823},
					{-0.1905,1.0,0.9823},
				}
		};




}
