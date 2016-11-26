package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.main.Renderer3D;
/**
 *
 * TWO TEXTURES
 *
 * @author Administrator
 *
 */
public class Sailer0Model extends Ship0Model{


	public static String NAME="Sailer";

	double dxTexture=200;
	double dyTexture=200;

	BPoint[][][] masts=null;
	private int mast_number=3;
	private int mast_meridians=0;
	private int mast_parallels=3;
	private double mast_height=0;
	private double mast_radius=0;

	BPoint[][][] sails=null;
	private double sail_width=0;
	private double sail_length=0;

	protected int[][] tFun={{8,9,10,11}};
	protected int[][] tSai={{12,13,14,15}};

	double[] mastYPosition={0.18,0.41,0.79};
	double[] mastHeight={0.8,1.0,0.93};

	public Sailer0Model(
			double dx, double dy, double dz,
			double dxFront, double dyFront, double dzFront,
			double dxRear, double dyRear, double dzRear,
			double dxRoof, double dyRoof, double dzRoof,
			double rearOverhang, double frontOverhang,
			double rearOverhang1,double frontOverhang1,
			double funnel_radius,
			double funnel_height,
			int funnel_meridians
			) {

		super(dx, dy, dz,
				dxFront, dyFront, dzFront,
				dxRear, dyRear, dzRear,
				dxRoof, dyRoof, dzRoof,
				rearOverhang,frontOverhang,
				rearOverhang1,frontOverhang1);

		this.mast_radius=funnel_radius;
		this.mast_height=funnel_height;
		this.mast_meridians=funnel_meridians;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector<Point3D>();

		int c = 0;
		c = initDoubleArrayValues(tHull = new int[1][4], c);
		c = initDoubleArrayValues(tDeck = new int[1][4], c);

		buildHull();
		buildAfterCastle();
		buildForeCastle();
		buildMasts();
		buildSails();

		buildTextures();


		//faces
		//hull
		int NF=(nxHull-1)*(nyHull-1)+2;
		//hull back closures
		NF+=(nxHull-1)/2;
		//deck
		NF+=nyHull-1;
		//castles
		NF+=6+(4*(nyCastle-1)+1);
		//masts
		NF+=mast_number*(mast_meridians*(mast_parallels-1)+mast_meridians);
		//sails
		NF+=sails.length*2;

		faces=new int[NF][3][4];

		//build the hull
		int counter=0;
		counter=buildFaces(counter,nxHull,nyHull,nyCastle);

	}


	private void buildSails() {

		sail_width=dx;
		sail_length=sail_width*0.5;

		sails=new BPoint[masts.length][2][2];

		for (int i = 0; i < masts.length; i++) {

			double fx=dx*0.5;
			double fy=y0+mastYPosition[i]*dy;

			double xTop=fx;
			double yTop=fy+mast_radius;
			double zTop=mast_height*mastHeight[i]*0.75;

			sails[i][0][0]=addBPoint(xTop-sail_width*0.5, yTop, zTop-sail_length);
			sails[i][1][0]=addBPoint(xTop+sail_width*0.5, yTop, zTop-sail_length);
			sails[i][1][1]=addBPoint(xTop+sail_width*0.5, yTop, zTop);
			sails[i][0][1]=addBPoint(xTop-sail_width*0.5, yTop, zTop);
		}


	}


	private void buildMasts() {

		masts=new BPoint[mast_number][mast_parallels][mast_meridians];

		double fx=dx*0.5;
		for (int i = 0; i < masts.length; i++) {

			double fy=y0+mastYPosition[i]*dy;
			double fz=z0+dz;
			masts[i]=buildFunnel(fx,fy,fz,mast_height*mastHeight[i],mast_radius,mast_parallels,mast_meridians);
		}

	}


	@Override
	protected void buildTextures() {

		int shift=1;

		//Texture points

		double y=by;
		double x=bx;

		//hull
		addTRect(x, y, dxTexture, dyTexture);

		x+=shift+dxTexture;
		//main deck
		addTRect(x, y, dxTexture, dyTexture);

		x+=shift+dxTexture;
		//funnel
		buildMastTexture(x,y);

		x+=shift+dxTexture;
		//sails
		buildSailTexture(x,y);

		IMG_WIDTH=(int) (2*bx+4*dxTexture+3*shift);
		IMG_HEIGHT=(int) (2*by+dyTexture);

	}

	protected void buildMastTexture(double x, double y) {
		addTRect(x, y, dxTexture, dyTexture);
	}

	protected void buildSailTexture(double x, double y) {
		addTRect(x, y, dxTexture, dyTexture);
	}

	@Override
	protected int buildFaces(int counter, int nx, int ny,int nyc) {

		counter=super.buildFaces(counter, nx, ny, nyc);
		counter=buildMastsFaces(counter);
		counter=buildSailsFaces(counter);

		return counter;
	}



	private int buildSailsFaces(int counter) {

		for (int l = 0; l < sails.length; l++) {

			BPoint[][] sail = sails[l];
			faces[counter++]=buildFace(Renderer3D.CAR_BACK,sail[0][0],sail[1][0],sail[1][1],sail[0][1],tSai[0]);
			faces[counter++]=buildFace(Renderer3D.CAR_FRONT,sail[0][0],sail[0][1],sail[1][1],sail[1][0],tSai[0]);
		}


		return counter;
	}


	private int buildMastsFaces(int counter) {

		for (int i = 0; i < masts.length; i++) {
			for (int k = 0; k < mast_parallels-1; k++) {
				for (int j = 0; j < mast_meridians; j++) {
					faces[counter++]=buildFace(Renderer3D.CAR_LEFT,masts[i][k][j],masts[i][k][(j+1)%mast_meridians],masts[i][k+1][(j+1)%mast_meridians],masts[i][k+1][j],tFun[0]);
				}
			}

			for (int j = 0; j < mast_meridians; j++) {
				int k=mast_parallels-1;
				faces[counter++]=buildFace(Renderer3D.CAR_TOP,masts[i][k][0],masts[i][k][j],masts[i][k][(j+1)%mast_meridians],tFun[0]);
			}
		}

		return counter;
	}


	@Override
	public void printTexture(Graphics2D bufGraphics) {


		bufGraphics.setColor(new Color(37,35,36));
		printTexturePolygon(bufGraphics, tHull[0]);

		bufGraphics.setColor(Color.WHITE);
		printTexturePolygon(bufGraphics, tDeck[0]);

		bufGraphics.setColor(new Color(160,99,16));
		printTexturePolygon(bufGraphics, tFun[0]);

		//ivory
		bufGraphics.setColor(new Color(255,255,240));
		printTexturePolygon(bufGraphics, tSai[0]);
	}

}
