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
import com.Point3D;
import com.editors.cars.data.Car;
import com.main.Renderer3D;

public class Head1Model extends MeshModel{

	double dx = 0;
	double dy = 0;
	double dz = 0;

	private int[][][] faces; 



	int bx=10;
	int by=10;

	public Head1Model(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points=new Vector();
		texturePoints=new Vector();

		///BUST
		BPoint[][] head=buildHeadBPoints(dx,dy,dz);

		double deltax=100;
		double deltay=100;

		int xNumSections=35;  
		int zNumSections=head.length;
		int  NUMFACES=(xNumSections-1)*(zNumSections-1);



		for (int k = 0; k < zNumSections; k++) { 


			double y=by+deltay*k;

			for (int i = 0; i < xNumSections; i++) {

				double x=bx+deltax*i;


				addTPoint(x, y, 0);

			}

		}

		int ns=xNumSections-1;

		int[][][] tFaces = new int[NUMFACES][3][4];

		int counter=0;

		for(int k=0;k<head.length-1;k++){
			for (int i = 0; i < ns; i++) {

				buildFace(tFaces,counter++,
						head[k][i],
						head[k][(i+1)%ns],
						head[k+1][(i+1)%ns],
						head[k+1][(i)],
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


	private BPoint[][] buildHeadBPoints(double dx, double dy, double dz) {


		BPoint[][] head=new BPoint[21][34];

		double deltax=dx*0.5;
		double deltay=dy;
		double deltaz=dz;



		for (int i = 0; i < data.length; i++) {

			double[][] pts = data[i];

			for (int j = 0; j < pts.length; j++) {

				head[i][j]= addBPoint(deltax*pts[j][0],deltay*pts[j][1],deltaz*pts[j][2]);
			}


		}

		return head;
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

	public static void main(String[] args) {
		//new Head1Model(200,200,284).writeNewCode();

		new Head1Model(200,200,284).printSections();
	}

	/**
	 * DIAGNOSTICS UTILITY
	 * 
	 */
	public void printSections(){

		int w=(int) (dx)+2*bx;
		int h=(int) (2*dy)+2*by;

		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);



		try {



			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			for(int k=0;k<2;k++){

				File file=new File("sections"+k+".jpg");

				bufGraphics.setColor(Color.BLACK);
				bufGraphics.fillRect(0,0,w,h);

				bufGraphics.setColor(Color.WHITE);

				for (int i = 0; i < data.length; i++) {



					double[][] pts = data[i];


					if(i<=7){

						bufGraphics.setColor(Color.GREEN);
						if(k==1)
							continue;

					}else if(i==8){

						bufGraphics.setColor(Color.RED);
						if(k==1)
							continue;
					}
					else
						continue;

					for (int j = 0; j < pts.length; j++) {

						int x=(int) (pts[j][0]*dx*0.5)+(int) (dx*0.5)+bx;
						int y=(int) (pts[j][1]*dy)+(int) (dy)+by;

						bufGraphics.fillRect(x-1, y-1, 2, 2);
					}


				}

				ImageIO.write(buf,"gif",file);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * DEBUGGING UTILITY
	 * 
	 */
	public void writeNewCode(){

		double deltax=dx*0.5;

		for (int i = 0; i < data.length; i++) {

			System.out.println("\t{");

			double[][] pts = data[i];


			for (int j = 0; j < pts.length; j++) {

				System.out.print("\t\t{");

				double x=pts[j][0];
				double y=pts[j][1];

				if(j>16){
					if(j<=21 || j>=29)
						y=y*Math.sqrt(1.0-(i>3?1.0:0.9)*x*x);
				}
				System.out.print(x+","+y+","+pts[j][2]);

				System.out.println("},");
			}

			System.out.println("\t},");

		}


	}

	private double m(String str0,String str1){

		double d0=v(str0);
		double d1=v(str1);

		return (d0+d1)*0.5;
	}

	private double v(String str){
		return Double.parseDouble(str);

	}

	private String[] getSextuple(String str) {

		String[] sex=new String[6];
		//extract data:

		str=str.substring(str.indexOf("(")+1,str.indexOf(")"));

		String[] vals=str.split(",");


		for (int j = 0; j < vals.length; j++) {


			String value=vals[j];
			String numVal="";
			String charVal="";
			for (int k = 0; k < value.length(); k++) {
				char ch=value.charAt(k);
				if(Character.isDigit(ch) || ch=='.')
					numVal+=ch;
				else if(ch!='*')
					charVal+=ch;
			}
			if(numVal.equals(""))
				numVal="1";
			//System.out.println(charVal+"->"+numVal);

			sex[2*j]=charVal;
			sex[2*j+1]=numVal;



		}
		return sex;
	}

	double[][][] data=
		{		
				//8 nose tip, 10 eyes line

				{
					{-1.0,-0.0,0.0},
					{-0.875,-0.16151388,0.0},
					{-0.75,-0.32302776,0.0},
					{-0.625,-0.37299108000000003,0.0},
					{-0.5,-0.4229544,0.0},
					{-0.375,-0.44791164,0.0},
					{-0.25,-0.47286888,0.0},
					{-0.125,-0.48063444,0.0},
					{-0.0,-0.4884,0.0},
					{0.125,-0.48063444,0.0},
					{0.25,-0.47286888,0.0},
					{0.375,-0.44791164,0.0},
					{0.5,-0.4229544,0.0},
					{0.625,-0.37299108000000003,0.0},
					{0.75,-0.32302776,0.0},
					{0.875,-0.16151388,0.0},
					{1.0,-0.0,0.0},
					{1.0,0.16877575512188944,0.0},
					{0.875,0.3080910007196098,0.0},
					{0.75,0.40144444820079705,0.0},
					{0.625,0.47027433843174915,0.0},
					{0.5,0.525300842433396,0.0},
					{0.375,0.6040972599999999,0.0},
					{0.25,0.6114928599999999,0.0},
					{0.125,0.6138964299999999,0.0},
					{0.0,0.6163,0.0},
					{-0.125,0.6138964299999999,0.0},
					{-0.25,0.6114928599999999,0.0},
					{-0.375,0.6040972599999999,0.0},
					{-0.5,0.525300842433396,0.0},
					{-0.625,0.47027433843174915,0.0},
					{-0.75,0.40144444820079705,0.0},
					{-0.875,0.3080910007196098,0.0},
					{-1.0,0.16877575512188944,0.0},
				},
				{
					{-1.0,-0.0,0.0574},
					{-0.875,-0.14998898500000002,0.0574},
					{-0.75,-0.29997797000000004,0.0574},
					{-0.625,-0.34637613500000003,0.0574},
					{-0.5,-0.3927743,0.0574},
					{-0.375,-0.415950705,0.0574},
					{-0.25,-0.43912711,0.0574},
					{-0.125,-0.44633855499999997,0.0574},
					{-0.0,-0.45355,0.0574},
					{0.125,-0.44633855499999997,0.0574},
					{0.25,-0.43912711,0.0574},
					{0.375,-0.415950705,0.0574},
					{0.5,-0.3927743,0.0574},
					{0.625,-0.34637613500000003,0.0574},
					{0.75,-0.29997797000000004,0.0574},
					{0.875,-0.14998898500000002,0.0574},
					{1.0,-0.0,0.0574},
					{1.0,0.17196614543045668,0.0574},
					{0.875,0.3139148854484488,0.0574},
					{0.75,0.40903300543191706,0.0574},
					{0.625,0.47916399613535104,0.0574},
					{0.5,0.5352306733831753,0.0574},
					{0.375,0.6155165899999999,0.0574},
					{0.25,0.62305199,0.0574},
					{0.125,0.6255009949999999,0.0574},
					{0.0,0.62795,0.0574},
					{-0.125,0.6255009949999999,0.0574},
					{-0.25,0.62305199,0.0574},
					{-0.375,0.6155165899999999,0.0574},
					{-0.5,0.5352306733831753,0.0574},
					{-0.625,0.47916399613535104,0.0574},
					{-0.75,0.40903300543191706,0.0574},
					{-0.875,0.3139148854484488,0.0574},
					{-1.0,0.17196614543045668,0.0574},
				},
				{
					{-1.0,-0.0,0.1148},
					{-0.875,-0.13846409,0.1148},
					{-0.75,-0.27692818,0.1148},
					{-0.625,-0.31976119000000003,0.1148},
					{-0.5,-0.36259420000000003,0.1148},
					{-0.375,-0.38398977,0.1148},
					{-0.25,-0.40538534,0.1148},
					{-0.125,-0.41204267,0.1148},
					{-0.0,-0.4187,0.1148},
					{0.125,-0.41204267,0.1148},
					{0.25,-0.40538534,0.1148},
					{0.375,-0.38398977,0.1148},
					{0.5,-0.36259420000000003,0.1148},
					{0.625,-0.31976119000000003,0.1148},
					{0.75,-0.27692818,0.1148},
					{0.875,-0.13846409,0.1148},
					{1.0,-0.0,0.1148},
					{1.0,0.17515653573902398,0.1148},
					{0.875,0.3197387701772878,0.1148},
					{0.75,0.4166215626630371,0.1148},
					{0.625,0.48805365383895305,0.1148},
					{0.5,0.5451605043329548,0.1148},
					{0.375,0.62693592,0.1148},
					{0.25,0.63461112,0.1148},
					{0.125,0.63710556,0.1148},
					{0.0,0.6396,0.1148},
					{-0.125,0.63710556,0.1148},
					{-0.25,0.63461112,0.1148},
					{-0.375,0.62693592,0.1148},
					{-0.5,0.5451605043329548,0.1148},
					{-0.625,0.48805365383895305,0.1148},
					{-0.75,0.4166215626630371,0.1148},
					{-0.875,0.3197387701772878,0.1148},
					{-1.0,0.17515653573902398,0.1148},
				},
				{
					{-1.0,-0.0,0.14755},
					{-0.875,-0.13846409,0.14755},
					{-0.75,-0.27692818,0.14755},
					{-0.625,-0.31976119000000003,0.14755},
					{-0.5,-0.36259420000000003,0.14755},
					{-0.375,-0.38398977,0.14755},
					{-0.25,-0.40538534,0.14755},
					{-0.125,-0.41204267,0.14755},
					{-0.0,-0.4187,0.14755},
					{0.125,-0.41204267,0.14755},
					{0.25,-0.40538534,0.14755},
					{0.375,-0.38398977,0.14755},
					{0.5,-0.36259420000000003,0.14755},
					{0.625,-0.31976119000000003,0.14755},
					{0.75,-0.27692818,0.14755},
					{0.875,-0.13846409,0.14755},
					{1.0,-0.0,0.14755},
					{1.0,0.17515653573902398,0.14755},
					{0.875,0.3197387701772878,0.14755},
					{0.75,0.4166215626630371,0.14755},
					{0.625,0.48805365383895305,0.14755},
					{0.5,0.5451605043329548,0.14755},
					{0.375,0.62693592,0.14755},
					{0.25,0.63461112,0.14755},
					{0.125,0.63710556,0.14755},
					{0.0,0.6396,0.14755},
					{-0.125,0.63710556,0.14755},
					{-0.25,0.63461112,0.14755},
					{-0.375,0.62693592,0.14755},
					{-0.5,0.5451605043329548,0.14755},
					{-0.625,0.48805365383895305,0.14755},
					{-0.75,0.4166215626630371,0.14755},
					{-0.875,0.3197387701772878,0.14755},
					{-1.0,0.17515653573902398,0.14755},
				},
				{
					{-1.0,-0.0,0.1803},
					{-0.875,-0.13846409,0.1803},
					{-0.75,-0.27692818,0.1803},
					{-0.625,-0.31976119000000003,0.1803},
					{-0.5,-0.36259420000000003,0.1803},
					{-0.375,-0.38398977,0.1803},
					{-0.25,-0.40538534,0.1803},
					{-0.125,-0.41204267,0.1803},
					{-0.0,-0.4187,0.1803},
					{0.125,-0.41204267,0.1803},
					{0.25,-0.40538534,0.1803},
					{0.375,-0.38398977,0.1803},
					{0.5,-0.36259420000000003,0.1803},
					{0.625,-0.31976119000000003,0.1803},
					{0.75,-0.27692818,0.1803},
					{0.875,-0.13846409,0.1803},
					{1.0,-0.0,0.1803},
					{1.0,0.1,0.1803},
					{0.875,0.2775967591115022,0.1803},
					{0.75,0.39217257331056443,0.1803},
					{0.625,0.47312492025742164,0.1803},
					{0.5,0.5362955150858421,0.1803},
					{0.375,0.62693592,0.1803},
					{0.25,0.63461112,0.1803},
					{0.125,0.63710556,0.1803},
					{0.0,0.6396,0.1803},
					{-0.125,0.63710556,0.1803},
					{-0.25,0.63461112,0.1803},
					{-0.375,0.62693592,0.1803},
					{-0.5,0.5362955150858421,0.1803},
					{-0.625,0.47312492025742164,0.1803},
					{-0.75,0.39217257331056443,0.1803},
					{-0.875,0.2775967591115022,0.1803},
					{-1.0,0.1,0.1803},
				},
				{
					{-1.0,-0.0,0.22949999999999998},
					{-0.875,-0.14613633,0.22949999999999998},
					{-0.75,-0.29227266,0.22949999999999998},
					{-0.625,-0.33747903,0.22949999999999998},
					{-0.5,-0.3826854,0.22949999999999998},
					{-0.375,-0.40526649,0.22949999999999998},
					{-0.25,-0.42784758,0.22949999999999998},
					{-0.125,-0.43487379000000004,0.22949999999999998},
					{-0.0,-0.4419,0.22949999999999998},
					{0.125,-0.43487379000000004,0.22949999999999998},
					{0.25,-0.42784758,0.22949999999999998},
					{0.375,-0.4052664925,0.22949999999999998},
					{0.5,-0.38268540500000003,0.22949999999999998},
					{0.625,-0.3374790325,0.22949999999999998},
					{0.75,-0.29227266,0.22949999999999998},
					{0.875,-0.14613633,0.22949999999999998},
					{1.0,-0.0,0.22949999999999998},
					{1.0,0.1,0.22949999999999998},
					{0.875,0.2876876356739513,0.22949999999999998},
					{0.75,0.4064283774529513,0.22949999999999998},
					{0.625,0.4903234105575859,0.22949999999999998},
					{0.5,0.5557903098415423,0.22949999999999998},
					{0.375,0.64972557,0.22949999999999998},
					{0.25,0.65767977,0.22949999999999998},
					{0.125,0.660264885,0.22949999999999998},
					{0.0,0.6628499999999999,0.22949999999999998},
					{-0.125,0.660264885,0.22949999999999998},
					{-0.25,0.65767977,0.22949999999999998},
					{-0.375,0.64972557,0.22949999999999998},
					{-0.5,0.5557903098415423,0.22949999999999998},
					{-0.625,0.4903234105575859,0.22949999999999998},
					{-0.75,0.4064283774529513,0.22949999999999998},
					{-0.875,0.2876876356739513,0.22949999999999998},
					{-1.0,0.1,0.22949999999999998},
				},
				{
					{-1.0,-0.0,0.2787},
					{-0.875,-0.15380857,0.2787},
					{-0.75,-0.30761714,0.2787},
					{-0.625,-0.35519687,0.2787},
					{-0.5,-0.4027766,0.2787},
					{-0.375,-0.42654320999999995,0.2787},
					{-0.25,-0.45030981999999997,0.2787},
					{-0.125,-0.45770491,0.2787},
					{-0.0,-0.4651,0.2787},
					{0.125,-0.45770491,0.2787},
					{0.25,-0.45030981999999997,0.2787},
					{0.375,-0.426543215,0.2787},
					{0.5,-0.40277661,0.2787},
					{0.625,-0.35519687499999997,0.2787},
					{0.75,-0.30761714,0.2787},
					{0.875,-0.15380857,0.2787},
					{1.0,-0.0,0.2787},
					{1.0,0.1,0.2787},
					{0.875,0.29777851223640034,0.2787},
					{0.75,0.4206841815953381,0.2787},
					{0.625,0.5075219008577502,0.2787},
					{0.5,0.5752851045972426,0.2787},
					{0.375,0.67251522,0.2787},
					{0.25,0.68074842,0.2787},
					{0.125,0.6834242100000001,0.2787},
					{0.0,0.6861,0.2787},
					{-0.125,0.6834242100000001,0.2787},
					{-0.25,0.68074842,0.2787},
					{-0.375,0.67251522,0.2787},
					{-0.5,0.5752851045972426,0.2787},
					{-0.625,0.5075219008577502,0.2787},
					{-0.75,0.4206841815953381,0.2787},
					{-0.875,0.29777851223640034,0.2787},
					{-1.0,0.1,0.2787},
				},
				{
					{-1.0,-0.0,0.3033},
					{-0.875,-0.15766122500000002,0.3033},
					{-0.75,-0.31532245000000003,0.3033},
					{-0.625,-0.364093975,0.3033},
					{-0.5,-0.4128655,0.3033},
					{-0.375,-0.437227425,0.3033},
					{-0.25,-0.46158935,0.3033},
					{-0.125,-0.469169675,0.3033},
					{-0.0,-0.47675,0.3033},
					{0.125,-0.469169675,0.3033},
					{0.25,-0.46158935,0.3033},
					{0.375,-0.4372274275,0.3033},
					{0.5,-0.41286550499999997,0.3033},
					{0.625,-0.3640939775,0.3033},
					{0.75,-0.31532245000000003,0.3033},
					{0.875,-0.15766122500000002,0.3033},
					{1.0,-0.0,0.3033},
					{1.0,0.1,0.3033},
					{0.875,0.31796026536129846,0.3033},
					{0.75,0.44919578988011183,0.3033},
					{0.625,0.5419188814580786,0.3033},
					{0.5,0.6142746941086429,0.3033},
					{0.375,0.71809452,0.3033},
					{0.25,0.7268857200000001,0.3033},
					{0.125,0.72974286,0.3033},
					{0.0,0.7326,0.3033},
					{-0.125,0.72974286,0.3033},
					{-0.25,0.7268857200000001,0.3033},
					{-0.375,0.71809452,0.3033},
					{-0.5,0.6142746941086429,0.3033},
					{-0.625,0.5419188814580786,0.3033},
					{-0.75,0.44919578988011183,0.3033},
					{-0.875,0.31796026536129846,0.3033},
					{-1.0,0.1,0.3033},
				},
				//8 nose tip
				{
					{-1.0,-0.0,0.3279},
					{-0.875,-0.16151388,0.3279},
					{-0.75,-0.32302776,0.3279},
					{-0.625,-0.37299108000000003,0.3279},
					{-0.5,-0.4229544,0.3279},
					{-0.375,-0.44791164,0.3279},
					{-0.25,-0.47286888,0.3279},
					{-0.125,-0.48063444,0.3279},
					{-0.0,-0.4884,0.3279},
					{0.125,-0.48063444,0.3279},
					{0.25,-0.47286888,0.3279},
					{0.375,-0.44791164,0.3279},
					{0.5,-0.4229544,0.3279},
					{0.625,-0.37299108000000003,0.3279},
					{0.75,-0.32302776,0.3279},
					{0.875,-0.16151388,0.3279},
					{1.0,-0.0,0.3279},					
					{1.0,0.1,0.3279},
					{0.875,0.31796026536129846,0.3279},
					{0.75,0.55,0.3279},
					{0.625,0.59,0.3279},
					{0.5,0.65,0.3279},
					{0.375,0.69,0.3279},
					{0.25,0.70,0.3279},
					{0.125,0.7268857200000001,0.3279},
					{0.0,0.7791,0.3279},
					{-0.125,0.7268857200000001,0.3279},
					{-0.25,0.7,0.3279},					
					{-0.375,0.69,0.3279},
					{-0.5,0.65,0.3279},
					{-0.625,0.59,0.3279},
					{-0.75,0.55,0.3279},
					{-0.875,0.31796026536129846,0.3279},
					{-1.0,0.1,0.3279},
				},
				{
					{-1.0,-0.0,0.40165},
					{-0.875,-0.17497337,0.40165},
					{-0.75,-0.34994674,0.40165},
					{-0.625,-0.40407367,0.40165},
					{-0.5,-0.45820059999999996,0.40165},
					{-0.375,-0.48523761,0.40165},
					{-0.25,-0.51227462,0.40165},
					{-0.125,-0.52068731,0.40165},
					{-0.0,-0.5291,0.40165},
					{0.125,-0.52068731,0.40165},
					{0.25,-0.51227462,0.40165},
					{0.375,-0.48523761,0.40165},
					{0.5,-0.45820059999999996,0.40165},
					{0.625,-0.40407367,0.40165},
					{0.75,-0.34994674,0.40165},
					{0.875,-0.17497337,0.40165},
					{1.0,-0.0,0.40165},						
					{1.0,0.2,0.40165},
					{0.875,0.36,0.40165},
					{0.75,0.54,0.40165},
					{0.625,0.6,0.40165},
					{0.5,0.63,0.40165},
					{0.375,0.67251522,0.40165},
					{0.25,0.68074842,0.40165},
					{0.125,0.68074842,0.40165},
					{0.0,0.7326,0.40165},
					{-0.125,0.68074842,0.40165},
					{-0.25,0.68074842,0.40165},
					{-0.375,0.67251522,0.40165},
					{-0.5,0.636,0.40165},
					{-0.625,0.6,0.40165},
					{-0.75,0.54,0.40165},
					{-0.875,0.36,0.40165},
					{-1.0,0.2,0.40165},
				},
				//10 eyes line
				{
					{-1.0,-0.0,0.4754},
					{-0.875,-0.18843285999999998,0.4754},
					{-0.75,-0.37686571999999996,0.4754},
					{-0.625,-0.43515625999999996,0.4754},
					{-0.5,-0.49344679999999996,0.4754},
					{-0.375,-0.5225635799999999,0.4754},
					{-0.25,-0.55168036,0.4754},
					{-0.125,-0.56074018,0.4754},
					{-0.0,-0.5698,0.4754},
					{0.125,-0.56074018,0.4754},
					{0.25,-0.55168036,0.4754},
					{0.375,-0.5225635799999999,0.4754},
					{0.5,-0.49344679999999996,0.4754},
					{0.625,-0.43515625999999996,0.4754},
					{0.75,-0.37686571999999996,0.4754},
					{0.875,-0.18843285999999998,0.4754},
					{1.0,-0.0,0.4754},					
					{1.0,0.1,0.4754},
					{0.875,0.3,0.4754},
					{0.75,0.5,0.4754},
					{0.625,0.6,0.4754},
					{0.5,0.62,0.4754},
					{0.375,0.6,0.4754},
					{0.25,0.58,0.4754},
					{0.125,0.61,0.4754},
					{0.0,0.63,0.4754},
					{-0.125,0.61,0.4754},
					{-0.25,0.58,0.4754},
					{-0.375,0.6,0.4754},
					{-0.5,0.62,0.4754},
					{-0.625,0.6,0.4754},
					{-0.75,0.5,0.4754},
					{-0.875,0.3,0.4754},
					{-1.0,0.1,0.4754},
				},
				{//11
					{-1.0,-0.0,0.541},
					{-0.875,-0.19035091999999998,0.541},
					{-0.75,-0.38070183999999996,0.541},
					{-0.625,-0.43958572,0.541},
					{-0.5,-0.4984696,0.541},
					{-0.375,-0.52788276,0.541},
					{-0.25,-0.5572959200000001,0.541},
					{-0.125,-0.5664479600000001,0.541},
					{-0.0,-0.5756,0.541},
					{0.125,-0.5664479600000001,0.541},
					{0.25,-0.5572959200000001,0.541},
					{0.375,-0.52788276,0.541},
					{0.5,-0.4984696,0.541},
					{0.625,-0.43958572,0.541},
					{0.75,-0.38070183999999996,0.541},
					{0.875,-0.19035091999999998,0.541},
					{1.0,-0.0,0.541},					
					{1.0,0.1,0.541},
					{0.875,0.29272222355026994,0.541},
					{0.75,0.5,0.541},
					{0.625,0.59,0.541},
					{0.5,0.63,0.541},
					{0.375,0.62,0.541},
					{0.25,0.61,0.541},
					{0.125,0.65,0.541},
					{0.0,0.66,0.541},
					{-0.125,0.65,0.541},
					{-0.25,0.61,0.541},
					{-0.375,0.62,0.541},
					{-0.5,0.63,0.541},
					{-0.625,0.59,0.541},
					{-0.75,0.5,0.541},
					{-0.875,0.29272222355026994,0.541},
					{-1.0,0.1,0.541},
				},
				{//12
					{-1.0,-0.0,0.6066},
					{-0.875,-0.19226898,0.6066},
					{-0.75,-0.38453796,0.6066},
					{-0.625,-0.44401518000000006,0.6066},
					{-0.5,-0.5034924000000001,0.6066},
					{-0.375,-0.5332019400000001,0.6066},
					{-0.25,-0.56291148,0.6066},
					{-0.125,-0.57215574,0.6066},
					{-0.0,-0.5814,0.6066},
					{0.125,-0.57215574,0.6066},
					{0.25,-0.56291148,0.6066},
					{0.375,-0.5332019400000001,0.6066},
					{0.5,-0.5034924000000001,0.6066},
					{0.625,-0.44401518000000006,0.6066},
					{0.75,-0.38453796,0.6066},
					{0.875,-0.19226898,0.6066},
					{1.0,-0.0,0.6066},
					
					{1.0,0.1,0.6066},
					{0.875,0.28766593486413955,0.6066},					
					{0.75,0.5,0.6066},
					{0.625,0.55,0.6066},					
					{0.5,0.6,0.6066},					
					{0.375,0.62,0.6066},
					{0.25,0.63,0.6066},					
					{0.125,0.66021508,0.6066},
					{0.0,0.6628,0.6066},
					{-0.125,0.66021508,0.6066},
					{-0.25,0.63,0.6066},
					{-0.375,0.62,0.6066},
					{-0.5,0.6,0.6066},
					{-0.625,0.55,0.6066},
					{-0.75,0.5,0.6066},
					{-0.875,0.28766593486413955,0.6066},
					{-1.0,0.1,0.6066},
				},
				{//13
					{-1.0,-0.0,0.6475500000000001},
					{-0.875,-0.19612163500000002,0.6475500000000001},
					{-0.75,-0.39224327000000003,0.6475500000000001},
					{-0.625,-0.452912285,0.6475500000000001},
					{-0.5,-0.5135813,0.6475500000000001},
					{-0.375,-0.543886155,0.6475500000000001},
					{-0.25,-0.5741910100000001,0.6475500000000001},
					{-0.125,-0.5836205050000001,0.6475500000000001},
					{-0.0,-0.5930500000000001,0.6475500000000001},
					{0.125,-0.5836205050000001,0.6475500000000001},
					{0.25,-0.5741910100000001,0.6475500000000001},
					{0.375,-0.543886155,0.6475500000000001},
					{0.5,-0.5135813,0.6475500000000001},
					{0.625,-0.452912285,0.6475500000000001},
					{0.75,-0.39224327000000003,0.6475500000000001},
					{0.875,-0.19612163500000002,0.6475500000000001},
					{1.0,-0.0,0.6475500000000001},
					{1.0,0.1,0.6475500000000001},
					{0.875,0.2775750583016905,0.6475500000000001},
					{0.75,0.39214191566724743,0.6475500000000001},
					{0.625,0.47308793425677614,0.6475500000000001},
					{0.5,0.536253590796045,0.6475500000000001},
					{0.375,0.6268869099999999,0.6475500000000001},
					{0.25,0.6345615099999999,0.6475500000000001},
					{0.125,0.637055755,0.6475500000000001},
					{0.0,0.63955,0.6475500000000001},
					{-0.125,0.637055755,0.6475500000000001},
					{-0.25,0.6345615099999999,0.6475500000000001},
					{-0.375,0.6268869099999999,0.6475500000000001},
					{-0.5,0.536253590796045,0.6475500000000001},
					{-0.625,0.47308793425677614,0.6475500000000001},
					{-0.75,0.39214191566724743,0.6475500000000001},
					{-0.875,0.2775750583016905,0.6475500000000001},
					{-1.0,0.1,0.6475500000000001},
				},
				{
					{-1.0,-0.0,0.6885},
					{-0.875,-0.19997429,0.6885},
					{-0.75,-0.39994858,0.6885},
					{-0.625,-0.46180939,0.6885},
					{-0.5,-0.5236702,0.6885},
					{-0.375,-0.55457037,0.6885},
					{-0.25,-0.58547054,0.6885},
					{-0.125,-0.59508527,0.6885},
					{-0.0,-0.6047,0.6885},
					{0.125,-0.59508527,0.6885},
					{0.25,-0.58547054,0.6885},
					{0.375,-0.55457037,0.6885},
					{0.5,-0.5236702,0.6885},
					{0.625,-0.46180939,0.6885},
					{0.75,-0.39994858,0.6885},
					{0.875,-0.19997429,0.6885},
					{1.0,-0.0,0.6885},
					{1.0,0.1,0.6885},
					{0.875,0.26748418173924143,0.6885},
					{0.75,0.3778861115248606,0.6885},
					{0.625,0.4558894439566119,0.6885},
					{0.5,0.5167587960403447,0.6885},
					{0.375,0.6040972599999999,0.6885},
					{0.25,0.6114928599999999,0.6885},
					{0.125,0.6138964299999999,0.6885},
					{0.0,0.6163,0.6885},
					{-0.125,0.6138964299999999,0.6885},
					{-0.25,0.6114928599999999,0.6885},
					{-0.375,0.6040972599999999,0.6885},
					{-0.5,0.5167587960403447,0.6885},
					{-0.625,0.4558894439566119,0.6885},
					{-0.75,0.3778861115248606,0.6885},
					{-0.875,0.26748418173924143,0.6885},
					{-1.0,0.1,0.6885},
				},
				{
					{-1.0,-0.0,0.7705},
					{-0.875,-0.177437085,0.7705},
					{-0.75,-0.35487417,0.7705},
					{-0.625,-0.409763235,0.7705},
					{-0.5,-0.4646523,0.7705},
					{-0.375,-0.492070005,0.7705},
					{-0.25,-0.5194877099999999,0.7705},
					{-0.125,-0.528018855,0.7705},
					{-0.0,-0.53655,0.7705},
					{0.125,-0.528018855,0.7705},
					{0.25,-0.5194877099999999,0.7705},
					{0.375,-0.492070005,0.7705},
					{0.5,-0.4646523,0.7705},
					{0.625,-0.409763235,0.7705},
					{0.75,-0.35487417,0.7705},
					{0.875,-0.177437085,0.7705},
					{1.0,-0.0,0.7705},
					{1.0,0.1,0.7705},
					{0.875,0.2372115520518942,0.7705},
					{0.75,0.3351186990977001,0.7705},
					{0.625,0.40429397305611914,0.7705},
					{0.5,0.4582744117732442,0.7705},
					{0.375,0.5357283099999999,0.7705},
					{0.25,0.54228691,0.7705},
					{0.125,0.544418455,0.7705},
					{0.0,0.54655,0.7705},
					{-0.125,0.544418455,0.7705},
					{-0.25,0.54228691,0.7705},
					{-0.375,0.5357283099999999,0.7705},
					{-0.5,0.4582744117732442,0.7705},
					{-0.625,0.40429397305611914,0.7705},
					{-0.75,0.3351186990977001,0.7705},
					{-0.875,0.2372115520518942,0.7705},
					{-1.0,0.1,0.7705},
				},
				{
					{-1.0,-0.0,0.8525},
					{-0.875,-0.15489988,0.8525},
					{-0.75,-0.30979976,0.8525},
					{-0.625,-0.35771708,0.8525},
					{-0.5,-0.4056344,0.8525},
					{-0.375,-0.42956964,0.8525},
					{-0.25,-0.45350487999999994,0.8525},
					{-0.125,-0.46095243999999996,0.8525},
					{-0.0,-0.4684,0.8525},
					{0.125,-0.46095243999999996,0.8525},
					{0.25,-0.45350487999999994,0.8525},
					{0.375,-0.42956964,0.8525},
					{0.5,-0.4056344,0.8525},
					{0.625,-0.35771708,0.8525},
					{0.75,-0.30979976,0.8525},
					{0.875,-0.15489988,0.8525},
					{1.0,-0.0,0.8525},
					{1.0,0.1,0.8525},
					{0.875,0.206938922364547,0.8525},
					{0.75,0.2923512866705396,0.8525},
					{0.625,0.3526985021556264,0.8525},
					{0.5,0.39979002750614373,0.8525},
					{0.375,0.46735936,0.8525},
					{0.25,0.47308096,0.8525},
					{0.125,0.47494048,0.8525},
					{0.0,0.4768,0.8525},
					{-0.125,0.47494048,0.8525},
					{-0.25,0.47308096,0.8525},
					{-0.375,0.46735936,0.8525},
					{-0.5,0.39979002750614373,0.8525},
					{-0.625,0.3526985021556264,0.8525},
					{-0.75,0.2923512866705396,0.8525},
					{-0.875,0.206938922364547,0.8525},
					{-1.0,0.1,0.8525},
				},
				{
					{-1.0,-0.0,0.8934500000000001},
					{-0.875,-0.137058615,0.8934500000000001},
					{-0.75,-0.27411723,0.8934500000000001},
					{-0.625,-0.316515465,0.8934500000000001},
					{-0.5,-0.3589137,0.8934500000000001},
					{-0.375,-0.38009209499999996,0.8934500000000001},
					{-0.25,-0.4012704899999999,0.8934500000000001},
					{-0.125,-0.40786024499999995,0.8934500000000001},
					{-0.0,-0.41445,0.8934500000000001},
					{0.125,-0.40786024499999995,0.8934500000000001},
					{0.25,-0.4012704899999999,0.8934500000000001},
					{0.375,-0.38009209499999996,0.8934500000000001},
					{0.5,-0.3589137,0.8934500000000001},
					{0.625,-0.316515465,0.8934500000000001},
					{0.75,-0.27411723,0.8934500000000001},
					{0.875,-0.137058615,0.8934500000000001},
					{1.0,-0.0,0.8934500000000001},
					{1.0,0.1,0.8934500000000001},
					{0.875,0.1842181744916778,0.8934500000000001},
					{0.75,0.26025273411768146,0.8934500000000001},
					{0.625,0.31397415947977275,0.8934500000000001},
					{0.5,0.3558952960884704,0.8934500000000001},
					{0.375,0.41604589,0.8934500000000001},
					{0.25,0.42113928999999994,0.8934500000000001},
					{0.125,0.42279464499999997,0.8934500000000001},
					{0.0,0.42445,0.8934500000000001},
					{-0.125,0.42279464499999997,0.8934500000000001},
					{-0.25,0.42113928999999994,0.8934500000000001},
					{-0.375,0.41604589,0.8934500000000001},
					{-0.5,0.3558952960884704,0.8934500000000001},
					{-0.625,0.31397415947977275,0.8934500000000001},
					{-0.75,0.26025273411768146,0.8934500000000001},
					{-0.875,0.1842181744916778,0.8934500000000001},
					{-1.0,0.1,0.8934500000000001},
				},
				{
					{-1.0,-0.0,0.9344},
					{-0.875,-0.11921735,0.9344},
					{-0.75,-0.2384347,0.9344},
					{-0.625,-0.27531384999999997,0.9344},
					{-0.5,-0.312193,0.9344},
					{-0.375,-0.33061454999999995,0.9344},
					{-0.25,-0.34903609999999996,0.9344},
					{-0.125,-0.35476805,0.9344},
					{-0.0,-0.3605,0.9344},
					{0.125,-0.35476805,0.9344},
					{0.25,-0.34903609999999996,0.9344},
					{0.375,-0.33061454999999995,0.9344},
					{0.5,-0.312193,0.9344},
					{0.625,-0.27531384999999997,0.9344},
					{0.75,-0.2384347,0.9344},
					{0.875,-0.11921735,0.9344},
					{1.0,-0.0,0.9344},
					{1.0,0.1,0.9344},
					{0.875,0.1614974266188086,0.9344},
					{0.75,0.22815418156482334,0.9344},
					{0.625,0.275249816803919,0.9344},
					{0.5,0.3120005646707971,0.9344},
					{0.375,0.36473241999999995,0.9344},
					{0.25,0.36919761999999995,0.9344},
					{0.125,0.37064880999999994,0.9344},
					{0.0,0.3721,0.9344},
					{-0.125,0.37064880999999994,0.9344},
					{-0.25,0.36919761999999995,0.9344},
					{-0.375,0.36473241999999995,0.9344},
					{-0.5,0.3120005646707971,0.9344},
					{-0.625,0.275249816803919,0.9344},
					{-0.75,0.22815418156482334,0.9344},
					{-0.875,0.1614974266188086,0.9344},
					{-1.0,0.1,0.9344},
				},
				{
					{-1.0,-0.0,0.9672000000000001},
					{-0.875,-0.059608675,0.9672000000000001},
					{-0.75,-0.11921735,0.9672000000000001},
					{-0.625,-0.13765692499999999,0.9672000000000001},
					{-0.5,-0.1560965,0.9672000000000001},
					{-0.375,-0.16530727499999998,0.9672000000000001},
					{-0.25,-0.17451804999999998,0.9672000000000001},
					{-0.125,-0.177384025,0.9672000000000001},
					{-0.0,-0.18025,0.9672000000000001},
					{0.125,-0.177384025,0.9672000000000001},
					{0.25,-0.17451804999999998,0.9672000000000001},
					{0.375,-0.16530727499999998,0.9672000000000001},
					{0.5,-0.1560965,0.9672000000000001},
					{0.625,-0.13765692499999999,0.9672000000000001},
					{0.75,-0.11921735,0.9672000000000001},
					{0.875,-0.059608675,0.9672000000000001},
					{1.0,-0.0,0.9672000000000001},
					{1.0,0.1,0.9672000000000001},
					{0.875,0.0807487133094043,0.9672000000000001},
					{0.75,0.11407709078241167,0.9672000000000001},
					{0.625,0.1376249084019595,0.9672000000000001},
					{0.5,0.15600028233539856,0.9672000000000001},
					{0.375,0.18236620999999997,0.9672000000000001},
					{0.25,0.18459880999999997,0.9672000000000001},
					{0.125,0.18532440499999997,0.9672000000000001},
					{0.0,0.18605,0.9672000000000001},
					{-0.125,0.18532440499999997,0.9672000000000001},
					{-0.25,0.18459880999999997,0.9672000000000001},
					{-0.375,0.18236620999999997,0.9672000000000001},
					{-0.5,0.15600028233539856,0.9672000000000001},
					{-0.625,0.1376249084019595,0.9672000000000001},
					{-0.75,0.11407709078241167,0.9672000000000001},
					{-0.875,0.0807487133094043,0.9672000000000001},
					{-1.0,0.1,0.9672000000000001},
				},
				{
					{-1.0,-0.0,1.0},
					{-0.875,-0.0,1.0},
					{-0.75,-0.0,1.0},
					{-0.625,-0.0,1.0},
					{-0.5,-0.0,1.0},
					{-0.375,-0.0,1.0},
					{-0.25,-0.0,1.0},
					{-0.125,-0.0,1.0},
					{-0.0,-0.0,1.0},
					{0.125,-0.0,1.0},
					{0.25,-0.0,1.0},
					{0.375,-0.0,1.0},
					{0.5,-0.0,1.0},
					{0.625,-0.0,1.0},
					{0.75,-0.0,1.0},
					{0.875,-0.0,1.0},
					{1.0,-0.0,1.0},
					{1.0,0.0,1.0},
					{0.875,0.0,1.0},
					{0.75,0.0,1.0},
					{0.625,0.0,1.0},
					{0.5,0.0,1.0},
					{0.375,0.0,1.0},
					{0.25,0.0,1.0},
					{0.125,0.0,1.0},
					{0.0,0.0,1.0},
					{-0.125,0.0,1.0},
					{-0.25,0.0,1.0},
					{-0.375,0.0,1.0},
					{-0.5,0.0,1.0},
					{-0.625,0.0,1.0},
					{-0.75,0.0,1.0},
					{-0.875,0.0,1.0},
					{-1.0,0.0,1.0},
				},

		};	

}
