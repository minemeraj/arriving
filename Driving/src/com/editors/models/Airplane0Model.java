package com.editors.models;

import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;
/**
 * One texture model
 * Summing up the best creation logic so far
 * 
 * @author Administrator
 *
 */
public class Airplane0Model extends MeshModel{

	private int bx=10;
	private int by=10;

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private double dxf = 0;
	private double dyf = 0;
	private double dzf = 0;

	private double dxr = 0;
	private double dyr = 0;
	private double dzr = 0;

	double x0=0;
	double y0=0;
	double z0=0;

	private int[][][] faces;

	int basePoints=4;

	public Airplane0Model(
			double dx, double dy, double dz, 
			double dxf, double dyf, double dzf, 
			double dxr, double dyr,	double dzr) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;

		this.dxf = dxf;
		this.dyf = dyf;
		this.dzf = dzf;

		this.dxr = dxr;
		this.dyr = dyr;
		this.dzr = dzr;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();


		Segments s0=new Segments(x0,dx,y0,dy,z0,dz);

		int nz=2;

		BPoint[][] cab=new BPoint[2][4];
		cab[0][0] = addBPoint(0.0,0.0,0.5,s0);
		cab[0][1] = addBPoint(1.0,0.0,0.5,s0);
		cab[0][2] = addBPoint(1.0,1.0,0.5,s0);
		cab[0][3] = addBPoint(0.0,1.0,0.5,s0);

		cab[1][0] = addBPoint(0.0,0.0,1.0,s0);
		cab[1][1] = addBPoint(1.0,0.0,1.0,s0);
		cab[1][2] = addBPoint(1.0,1.0,1.0,s0);
		cab[1][3] = addBPoint(0.0,1.0,1.0,s0);


		/*

		  int pnx=2;
		int bny=4;
		int pny=2;
		int fny=4;
		int pnz=2;

		int numy=bny+pny+fny;

		BPoint[][][] body=new BPoint[pnx][numy][pnz];

		double back_width1=back_width*(1-0.3)+x_side*0.3; 
		double back_width2=back_width*(1-0.6)+x_side*0.6; 
		double back_width3=back_width*(1-0.9)+x_side*0.9; 

		double back_height1=back_height*(1-0.3)+z_side*0.3; 
		double back_height2=back_height*(1-0.6)+z_side*0.6; 
		double back_height3=back_height*(1-0.9)+z_side*0.9; 		

		Segments b0=new Segments(0,back_width,0,back_length,z_side-back_height,back_height);
		Segments b1=new Segments(0,back_width1,0,back_length,z_side-back_height1,back_height1);
		Segments b2=new Segments(0,back_width2,0,back_length,z_side-back_height2,back_height2);
		Segments b3=new Segments(0,back_width3,0,back_length,z_side-back_height3,back_height3);

		body[0][0][0]=addBPoint(-0.5,0.0,0,b0);
		body[1][0][0]=addBPoint(0.5,0.0,0,b0);
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,b0);

		body[0][1][0]=addBPoint(-0.5,0.25,0,b1);
		body[1][1][0]=addBPoint(0.5,0.25,0,b1);
		body[0][1][1]=addBPoint(-0.5,0.25,1.0,b1);
		body[1][1][1]=addBPoint(0.5,0.25,1.0,b1);

		body[0][2][0]=addBPoint(-0.5,0.5,0,b2);
		body[1][2][0]=addBPoint(0.5,0.5,0,b2);
		body[0][2][1]=addBPoint(-0.5,0.5,1.0,b2);
		body[1][2][1]=addBPoint(0.5,0.5,1.0,b2);

		body[0][3][0]=addBPoint(-0.5,0.75,0,b3);
		body[1][3][0]=addBPoint(0.5,0.75,0,b3);
		body[0][3][1]=addBPoint(-0.5,0.75,1.0,b3);
		body[1][3][1]=addBPoint(0.5,0.75,1.0,b3);


		Segments p0=new Segments(0,x_side,back_length,y_side,0,z_side);

		body[0][bny][0]=addBPoint(-0.5,0.0,0,p0);
		body[1][bny][0]=addBPoint(0.5,0.0,0,p0);
		body[0][bny][1]=addBPoint(-0.5,0.0,1.0,p0);
		body[1][bny][1]=addBPoint(0.5,0.0,1.0,p0);

		body[0][bny+1][0]=addBPoint(-0.5,1.0,0,p0);
		body[1][bny+1][0]=addBPoint(0.5,1.0,0,p0);
		body[0][bny+1][1]=addBPoint(-0.5,1.0,1.0,p0);
		body[1][bny+1][1]=addBPoint(0.5,1.0,1.0,p0);

		double front_width0=front_width*(1-0.75)+x_side*0.75; 
		double front_width1=front_width*(1-0.7)+x_side*0.7; 
		double front_width2=front_width*(1-0.65)+x_side*0.65; 

		double front_height0=front_height*(1-0.75)+z_side*0.75; 
		double front_height1=front_height*(1-0.7)+z_side*0.7; 
		double front_height2=front_height*(1-0.65)+z_side*0.65; 			

		Segments f0=new Segments(0,front_width0,back_length+y_side,front_length,0,front_height0);
		Segments f1=new Segments(0,front_width1,back_length+y_side,front_length,0,front_height1);
		Segments f2=new Segments(0,front_width2,back_length+y_side,front_length,0,front_height2);
		Segments f3=new Segments(0,front_width,back_length+y_side,front_length,0,front_height);


		body[0][bny+pny][0]=addBPoint(-0.5,0.25,0,f0);
		body[1][bny+pny][0]=addBPoint(0.5,0.25,0,f0);
		body[0][bny+pny][1]=addBPoint(-0.5,0.25,1.0,f0);
		body[1][bny+pny][1]=addBPoint(0.5,0.25,1.0,f0);

		body[0][bny+pny+1][0]=addBPoint(-0.5,0.5,0,f1);
		body[1][bny+pny+1][0]=addBPoint(0.5,0.5,0,f1);
		body[0][bny+pny+1][1]=addBPoint(-0.5,0.5,1.0,f1);
		body[1][bny+pny+1][1]=addBPoint(0.5,0.5,1.0,f1);

		body[0][bny+pny+2][0]=addBPoint(-0.5,0.75,0,f2);
		body[1][bny+pny+2][0]=addBPoint(0.5,0.75,0,f2);
		body[0][bny+pny+2][1]=addBPoint(-0.5,0.75,1.0,f2);
		body[1][bny+pny+2][1]=addBPoint(0.5,0.75,1.0,f2);

		body[0][bny+pny+3][0]=addBPoint(-0.5,1.0,0,f3);
		body[1][bny+pny+3][0]=addBPoint(0.5,1.0,0,f3);
		body[0][bny+pny+3][1]=addBPoint(-0.5,1.0,1.0,f3);
		body[1][bny+pny+3][1]=addBPoint(0.5,1.0,1.0,f3);



		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < numy-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						LineData bottomLD=addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==pnz-1){
						LineData topLD=addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						LineData backLD=addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==numy-1){
						LineData frontLD=addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==pnx-1){

						LineData rightLD=addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		///////tail


		int twnx=2;
		int twny=2;
		int twnz=2;	

		double twDX=65;

		BPoint[][][] tailRightWing=new BPoint[twnx][twny][twnz];	

		Segments trWing0=new Segments(0,twDX,0,back_length*0.125,z_side-back_height,back_height);

		tailRightWing[0][0][0]=body[1][0][0];
		tailRightWing[1][0][0]=addBPoint(1.0,0.0,0.0,trWing0);
		tailRightWing[0][0][1]=body[1][0][1]; 
		tailRightWing[1][0][1]=addBPoint(1.0,0.0,1.0,trWing0);

		tailRightWing[0][1][0]=body[1][1][0];
		tailRightWing[1][1][0]=addBPoint(1.0,1.0,0,trWing0);
		tailRightWing[0][1][1]=body[1][1][1];
		tailRightWing[1][1][1]=addBPoint(1.0,1.0,1.0,trWing0); 

		for (int i = 0; i < twnx-1; i++) {


			for (int j = 0; j < twny-1; j++) {

				for (int k = 0; k < twnz-1; k++) {




					if(i==0){

						addLine(tailRightWing[i][j][k],tailRightWing[i][j][k+1],tailRightWing[i][j+1][k+1],tailRightWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						addLine(tailRightWing[i][j][k],tailRightWing[i][j+1][k],tailRightWing[i+1][j+1][k],tailRightWing[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==twnz-1){
						addLine(tailRightWing[i][j][k+1],tailRightWing[i+1][j][k+1],tailRightWing[i+1][j+1][k+1],tailRightWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						addLine(tailRightWing[i][j][k],tailRightWing[i+1][j][k],tailRightWing[i+1][j][k+1],tailRightWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==twny-1){
						addLine(tailRightWing[i][j+1][k],tailRightWing[i][j+1][k+1],tailRightWing[i+1][j+1][k+1],tailRightWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==twnx-1){

						addLine(tailRightWing[i+1][j][k],tailRightWing[i+1][j+1][k],tailRightWing[i+1][j+1][k+1],tailRightWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}


		BPoint[][][] tailLeftWing=new BPoint[twnx][twny][twnz];		

		Segments tlWing0=new Segments(0,twDX,0,back_length*0.125,z_side-back_height,back_height);

		tailLeftWing[0][0][0]=addBPoint(-1.0,0,0,tlWing0);
		tailLeftWing[1][0][0]=body[0][0][0];
		tailLeftWing[0][0][1]=addBPoint(-1.0,0.0,1.0,tlWing0);
		tailLeftWing[1][0][1]=body[0][0][1];

		tailLeftWing[0][1][0]=addBPoint(-1.0,1.0,0,tlWing0);
		tailLeftWing[1][1][0]=body[0][1][0];
		tailLeftWing[0][1][1]=addBPoint(-1.0,1.0,1.0,tlWing0);
		tailLeftWing[1][1][1]=body[0][1][1];

		for (int i = 0; i < twnx-1; i++) {


			for (int j = 0; j < twny-1; j++) {

				for (int k = 0; k < twnz-1; k++) {




					if(i==0){

						addLine(tailLeftWing[i][j][k],tailLeftWing[i][j][k+1],tailLeftWing[i][j+1][k+1],tailLeftWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						addLine(tailLeftWing[i][j][k],tailLeftWing[i][j+1][k],tailLeftWing[i+1][j+1][k],tailLeftWing[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==twnz-1){
						addLine(tailLeftWing[i][j][k+1],tailLeftWing[i+1][j][k+1],tailLeftWing[i+1][j+1][k+1],tailLeftWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						addLine(tailLeftWing[i][j][k],tailLeftWing[i+1][j][k],tailLeftWing[i+1][j][k+1],tailLeftWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==twny-1){
						addLine(tailLeftWing[i][j+1][k],tailLeftWing[i][j+1][k+1],tailLeftWing[i+1][j+1][k+1],tailLeftWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==twnx-1){

						addLine(tailLeftWing[i+1][j][k],tailLeftWing[i+1][j+1][k],tailLeftWing[i+1][j+1][k+1],tailLeftWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}


		int trnx=2;
		int trny=2;
		int trnz=2;		

		BPoint[][][] tailRudder=new BPoint[trnx][trny][trnz];	

		Segments rudder0=new Segments(0,back_width,0,back_length*0.25,z_side-back_height,back_height+71);

		tailRudder[0][0][0]=body[0][0][1];
		tailRudder[1][0][0]=body[1][0][1];
		tailRudder[0][1][0]=body[0][1][1];
		tailRudder[1][1][0]=body[1][1][1];


		tailRudder[0][0][1]=addBPoint(0,0.0,1.0,rudder0);
		tailRudder[0][1][1]=addBPoint(0.0,1.0,1.0,rudder0);

		addLine(tailRudder[0][0][0],tailRudder[0][0][1],tailRudder[0][1][1],tailRudder[0][1][0],Renderer3D.CAR_LEFT);

		//addLine(tailRudder[0][0][0],tailRudder[0][1][0],tailRudder[1][1][0],tailRudder[1][0][0],Renderer3D.CAR_BOTTOM);	

		addLine(tailRudder[0][0][0],tailRudder[1][0][0],tailRudder[0][0][1],null,Renderer3D.CAR_BACK);

		addLine(tailRudder[0][1][0],tailRudder[0][1][1],tailRudder[1][1][0],null,Renderer3D.CAR_FRONT);	

		addLine(tailRudder[1][0][0],tailRudder[1][1][0],tailRudder[0][1][1],tailRudder[0][0][1],Renderer3D.CAR_RIGHT);



		//////wings

		int wnx=2;
		int wny=2;
		int wnz=2;

		double q=Math.PI*10.0/180.0;
		double sq=Math.sin(q);
		double cq=Math.cos(q);

		double sq1=sq*roof_width/roof_height;
		double ry=back_length+0.2*y_side;

		BPoint[][][] rightWing=new BPoint[wnx][wny][wnz];
		Segments rWing0=new Segments(x_side*0.5,roof_width/cq,ry,roof_length,0,roof_height);

		rightWing[0][0][0]=addBPoint(0,0.0,0,rWing0);
		rightWing[1][0][0]=addBPoint(cq,0.0,sq1,rWing0);
		rightWing[0][0][1]=addBPoint(0.0,0.0,1.0,rWing0);
		rightWing[1][0][1]=addBPoint(cq,0.0,1.0+sq1,rWing0);

		rightWing[0][1][0]=addBPoint(0.0,1.0,0,rWing0);
		rightWing[1][1][0]=addBPoint(cq,0.5,sq1,rWing0);
		rightWing[0][1][1]=addBPoint(0.0,1.0,1.0,rWing0);
		rightWing[1][1][1]=addBPoint(cq,0.5,1.0+sq1,rWing0);



		for (int i = 0; i < wnx-1; i++) {


			for (int j = 0; j < wny-1; j++) {

				for (int k = 0; k < wnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(rightWing[i][j][k],rightWing[i][j][k+1],rightWing[i][j+1][k+1],rightWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						LineData bottomLD=addLine(rightWing[i][j][k],rightWing[i][j+1][k],rightWing[i+1][j+1][k],rightWing[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==wnz-1){
						LineData topLD=addLine(rightWing[i][j][k+1],rightWing[i+1][j][k+1],rightWing[i+1][j+1][k+1],rightWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						LineData backLD=addLine(rightWing[i][j][k],rightWing[i+1][j][k],rightWing[i+1][j][k+1],rightWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==wny-1){
						LineData roofLD=addLine(rightWing[i][j+1][k],rightWing[i][j+1][k+1],rightWing[i+1][j+1][k+1],rightWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==wnx-1){

						LineData rightLD=addLine(rightWing[i+1][j][k],rightWing[i+1][j+1][k],rightWing[i+1][j+1][k+1],rightWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}


		BPoint[][][] leftWing=new BPoint[wnx][wny][wnz];

		Segments lWing0=new Segments(-x_side*0.5,roof_width/cq,ry,roof_length,0,roof_height);

		leftWing[0][0][0]=addBPoint(-cq,0.0,sq1,lWing0);
		leftWing[1][0][0]=addBPoint(0.0,0.0,0,lWing0);
		leftWing[0][0][1]=addBPoint(-cq,0.0,1.0+sq1,lWing0);
		leftWing[1][0][1]=addBPoint(0.0,0.0,1.0,lWing0);

		leftWing[0][1][0]=addBPoint(-cq,0.5,sq1,lWing0);
		leftWing[1][1][0]=addBPoint(0.0,1.0,0,lWing0);
		leftWing[0][1][1]=addBPoint(-cq,0.5,1.0+sq1,lWing0);
		leftWing[1][1][1]=addBPoint(0.0,1.0,1.0,lWing0);



		for (int i = 0; i < wnx-1; i++) {


			for (int j = 0; j < wny-1; j++) {

				for (int k = 0; k < wnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(leftWing[i][j][k],leftWing[i][j][k+1],leftWing[i][j+1][k+1],leftWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k==0){

						LineData bottomLD=addLine(leftWing[i][j][k],leftWing[i][j+1][k],leftWing[i+1][j+1][k],leftWing[i+1][j][k],Renderer3D.CAR_BOTTOM);

					}

					if(k+1==wnz-1){
						LineData topLD=addLine(leftWing[i][j][k+1],leftWing[i+1][j][k+1],leftWing[i+1][j+1][k+1],leftWing[i][j+1][k+1],Renderer3D.CAR_TOP);
					}

					if(j==0){
						LineData backLD=addLine(leftWing[i][j][k],leftWing[i+1][j][k],leftWing[i+1][j][k+1],leftWing[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==wny-1){
						LineData roofLD=addLine(leftWing[i][j+1][k],leftWing[i][j+1][k+1],leftWing[i+1][j+1][k+1],leftWing[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}


					if(i+1==wnx-1){

						LineData rightLD=addLine(leftWing[i+1][j][k],leftWing[i+1][j+1][k],leftWing[i+1][j+1][k+1],leftWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		 */

		//Texture points

		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		int NF=6;

		faces=new int[NF][3][4];

		int counter=0;

		faces[counter++]=buildFace(Renderer3D.CAR_BOTTOM, cab[0][0],cab[0][3],cab[0][2],cab[0][1], 0, 1, 2, 3);

		for (int k = 0; k < nz; k++) {

			faces[counter++]=buildFace(Renderer3D.CAR_LEFT, cab[k][0],cab[k+1][0],cab[k+1][3],cab[k][3], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_BACK, cab[k][0],cab[k][1],cab[k+1][1],cab[k+1][0], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_RIGHT, cab[k][1],cab[k][2],cab[k+1][2],cab[k+1][1], 0, 1, 2, 3);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT, cab[k][2],cab[k][3],cab[k+1][3],cab[k+1][2], 0, 1, 2, 3);

		}

		faces[counter++]=buildFace(Renderer3D.CAR_TOP,cab[nz-1][0],cab[nz-1][1],cab[nz-1][2],cab[nz-1][3], 0, 1, 2, 3);


		IMG_WIDTH=(int) (2*bx+dx);
		IMG_HEIGHT=(int) (2*by+dy);

	}


	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);

	}


	@Override
	public void printTexture(Graphics2D bufGraphics) {

		for (int i = 0; i < faces.length; i++) {

			int[][] face = faces[i];
			int[] tPoints = face[2];
			if(tPoints.length==4)
				printTexturePolygon(bufGraphics, tPoints[0],tPoints[1],tPoints[2],tPoints[3]);
			else if(tPoints.length==3)
				printTexturePolygon(bufGraphics, tPoints[0],tPoints[1],tPoints[2]);

		}


	}

}
