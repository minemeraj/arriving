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

	private int[][][] faces; 



	int bx=10;
	int by=10;

	public Man2Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points=new Vector();
		texturePoints=new Vector();
		
		double leg_lenght=100;

		///BUST
		BPoint[][] bust=buildBustBPoints(dx,dy,dz);
		BPoint[][] leftLeg=buildLeftLeg(bust,leg_lenght);

		double deltax=100;
		double deltay=100;

		int xNumSections=5;  
		int zNumSections=bust.length;

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

		for(int k=0;k<zNumSections-1;k++){
			for (int i = 0; i < 4; i++) {
	
				buildFace(tFaces,counter++,
						bust[k][i],
						bust[k][(i+1)%4],
						bust[k+1][(i+1)%4],
						bust[k+1][(i)],
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




	private BPoint[][] buildLeftLeg(BPoint[][] bust, double leg_lenght) {
		// TODO Auto-generated method stub
		return null;
	}

	private BPoint[][] buildBustBPoints(double dx, double dy, double dz) {


		BPoint[][] bust=new BPoint[11][4];
		
		double deltax=dx*0.5;
		double deltay=dy;

		bust[0][0]= addBPoint(-deltax*0.1111,0,0);
		bust[0][1]= addBPoint(deltax*0.1111,0,0);
		bust[0][2]= addBPoint(deltax*0.1111,deltay,0);
		bust[0][3]= addBPoint(-deltax*0.1111,deltay,0);
		
		bust[1][0]= addBPoint(-deltax*0.6032,0,dz*0.1239);
		bust[1][1]= addBPoint(deltax*0.6032,0,dz*0.1239);
		bust[1][2]= addBPoint(deltax*0.6032,deltay,dz*0.1239);
		bust[1][3]= addBPoint(-deltax*0.6032,deltay,dz*0.1239);
		
		bust[2][0]= addBPoint(-deltax*0.5556,0,dz*0.2743);
		bust[2][1]= addBPoint(deltax*0.5556,0,dz*0.2743);
		bust[2][2]= addBPoint(deltax*0.5556,deltay,dz*0.2743);
		bust[2][3]= addBPoint(-deltax*0.5556,deltay,dz*0.2743);
		
		bust[3][0]= addBPoint(-deltax*0.6508,0,dz*0.4867);
		bust[3][1]= addBPoint(deltax*0.6508,0,dz*0.4867);
		bust[3][2]= addBPoint(deltax*0.6508,deltay,dz*0.4867);
		bust[3][3]= addBPoint(-deltax*0.6508,deltay,dz*0.4867);

		bust[4][0]= addBPoint(-deltax*1.0,0,dz*0.5221);
		bust[4][1]= addBPoint(deltax*1.0,0,dz*0.5221);
		bust[4][2]= addBPoint(deltax*1.0,deltay,dz*0.5221);
		bust[4][3]= addBPoint(-deltax*1.0,deltay,dz*0.5221);
		
		bust[5][0]= addBPoint(-deltax*0.9206,0,dz*0.5929);
		bust[5][1]= addBPoint(deltax*0.9206,0,dz*0.5929);
		bust[5][2]= addBPoint(deltax*0.9206,deltay,dz*0.5929);
		bust[5][3]= addBPoint(-deltax*0.9206,deltay,dz*0.5929);
		
		bust[6][0]= addBPoint(-deltax*0.7778,0,dz*0.6372);
		bust[6][1]= addBPoint(deltax*0.7778,0,dz*0.6372);
		bust[6][2]= addBPoint(deltax*0.7778,deltay,dz*0.6372);
		bust[6][3]= addBPoint(-deltax*0.7778,deltay,dz*0.6372);
		
		bust[7][0]= addBPoint(-deltax*0.2381,0,dz*0.6991);
		bust[7][1]= addBPoint(deltax*0.2381,0,dz*0.6991);
		bust[7][2]= addBPoint(deltax*0.2381,deltay,dz*0.6991);
		bust[7][3]= addBPoint(-deltax*0.2381,deltay,dz*0.6991);
		
		bust[8][0]= addBPoint(-deltax*0.2540,0,dz*0.7611);
		bust[8][1]= addBPoint(deltax*0.2540,0,dz*0.7611);
		bust[8][2]= addBPoint(deltax*0.2540,deltay,dz*0.7611);
		bust[8][3]= addBPoint(-deltax*0.2540,deltay,dz*0.7611);
		
		bust[9][0]= addBPoint(-deltax*0.3810,0,dz*0.9027);
		bust[9][1]= addBPoint(deltax*0.3810,0,dz*0.9027);
		bust[9][2]= addBPoint(deltax*0.3810,deltay,dz*0.9027);
		bust[9][3]= addBPoint(-deltax*0.3810,deltay,dz*0.9027);
		
		bust[10][0]= addBPoint(-deltax*0.1905,0,dz*0.9823);
		bust[10][1]= addBPoint(deltax*0.1905,0,dz*0.9823);
		bust[10][2]= addBPoint(deltax*0.1905,deltay,dz*0.9823);
		bust[10][3]= addBPoint(-deltax*0.1905,deltay,dz*0.9823);

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
