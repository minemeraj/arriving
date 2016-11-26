package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;
import com.main.Renderer3D;
/**
 *
 * TWO TEXTURES
 *
 * @author Administrator
 *
 */
public class OceanLiner0Model extends Ship0Model{


	public static String NAME="Ocean liner";

	BPoint[][][] funnels=null;
	private int funnel_number=4;
	private int funnel_meridians=0;
	private int funnel_parallels=3;
	private double funnel_height=0;
	private double funnel_radius=0;

	protected int[][] tFun=null;

	public OceanLiner0Model(
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
				rearOverhang, frontOverhang,
				rearOverhang1,frontOverhang1
				);


		this.funnel_radius=funnel_radius;
		this.funnel_height=funnel_height;
		this.funnel_meridians=funnel_meridians;
	}


	@Override
	public void initMesh() {

		initTexturesArrays();

		points=new Vector<Point3D>();
		texturePoints=new Vector<Point3D>();

		buildHull();
		buildAfterCastle();
		buildForeCastle();
		buildMainDecks();
		buildFunnels();

		buildTextures();


		//faces
		//hull
		int NF=(nxHull-1)*(nyHull-1)+2;
		//hull back closures
		NF+=(nxHull-1)/2;
		//deck
		NF+=nyHull-1;
		//main bridge and top deck
		NF+=mainDecks.length*6;
		//castles
		NF+=6+(4*(nyCastle-1)+1);
		//funnels
		NF+=funnel_number*(funnel_meridians*(funnel_parallels-1)+funnel_meridians);

		faces=new int[NF][3][4];

		//build the hull
		int counter=0;
		counter=buildFaces(counter,nxHull,nyHull,nyCastle);
		counter=buildMainDecksFaces(counter);
	}

	@Override
	protected int initTexturesArrays() {
		int c=super.initTexturesArrays();
		c = initDoubleArrayValues(tFun = new int[1][4], c);
		return c;
	}

	@Override
	protected void buildMainDecks() {
		Segments sDown=new Segments(x0,dxRoof,y0+rearOverhang,dyRoof,z0+dz,dzRoof*0.5);
		double dyUpRoof=dyRoof-rearOverhang1+rearOverhang;
		Segments sUp=new Segments(x0,dxRoof,y0+rearOverhang1,dyUpRoof,z0+dz+dzRoof*0.5,dzRoof*0.5);

		mainDecks=new BPoint[2][2][4];

		mainDecks[0][0][0]=addBPoint(0.0,0.0,0,sDown);
		mainDecks[0][0][1]=addBPoint(1.0,0.0,0,sDown);
		mainDecks[0][0][2]=addBPoint(1.0,1.0,0,sDown);
		mainDecks[0][0][3]=addBPoint(0.0,1.0,0,sDown);

		mainDecks[0][1][0]=addBPoint(0.0,0.0,1.0,sDown);
		mainDecks[0][1][1]=addBPoint(1.0,0.0,1.0,sDown);
		mainDecks[0][1][2]=addBPoint(1.0,1.0,1.0,sDown);
		mainDecks[0][1][3]=addBPoint(0.0,1.0,1.0,sDown);

		mainDecks[1][0][0]=addBPoint(0.0,0.0,0,sUp);
		mainDecks[1][0][1]=addBPoint(1.0,0.0,0,sUp);
		mainDecks[1][0][2]=addBPoint(1.0,1.0,0,sUp);
		mainDecks[1][0][3]=addBPoint(0.0,1.0,0,sUp);

		mainDecks[1][1][0]=addBPoint(0.0,0.0,1.0,sUp);
		mainDecks[1][1][1]=addBPoint(1.0,0.0,1.0,sUp);
		mainDecks[1][1][2]=addBPoint(1.0,1.0,1.0,sUp);
		mainDecks[1][1][3]=addBPoint(0.0,1.0,1.0,sUp);

	}

	private void buildFunnels() {


		funnels=new BPoint[funnel_number][funnel_parallels][funnel_meridians];
		double dyUpRoof=dyRoof-rearOverhang1+rearOverhang;
		double fx=dxRoof*0.5;
		for (int i = 0; i < funnels.length; i++) {

			double fy=y0+rearOverhang1+(i+1)*dyUpRoof/(funnel_number+1);
			double fz=z0+dz+dzRoof;
			funnels[i]=buildFunnel(fx,fy,fz,funnel_height,funnel_radius,funnel_parallels,funnel_meridians);
		}

	}


	@Override
	protected void buildTextures() {

		int shift = 1;
		double y = by;
		double x = bx;

		// hull
		addTRect(x, y, dxTexture, dyTexture);
		x += shift + dxTexture;
		x+=dxTexture+shift;
		buildHullTextures(x,y);
		x+=dx;
		buildMainDeckTexture(x,y);
		x+=dx;
		buildBridgeTexture(x,y);
		x+=dxRoof+2*dzRoof;
		buildFunnelTexture(x, y);
		x+=shift+dxTexture;

		IMG_WIDTH = (int) (bx +x);
		IMG_HEIGHT = (int) (2 * by + dy);
	}


	protected void buildFunnelTexture(double x, double y) {
		addTRect(x, y, dxTexture, dyTexture);
	}

	@Override
	protected int buildFaces(int counter, int nx, int ny,int nyc) {

		counter=super.buildFaces(counter, nx, ny, nyc);

		for (int i = 0; i < funnels.length; i++) {
			for (int k = 0; k < funnel_parallels-1; k++) {
				for (int j = 0; j < funnel_meridians; j++) {
					faces[counter++]=buildFace(Renderer3D.CAR_LEFT,funnels[i][k][j],funnels[i][k][(j+1)%funnel_meridians],funnels[i][k+1][(j+1)%funnel_meridians],funnels[i][k+1][j],tFun[0]);
				}
			}

			for (int j = 0; j < funnel_meridians; j++) {
				int k=funnel_parallels-1;
				faces[counter++]=buildFace(Renderer3D.CAR_TOP,funnels[i][k][0],funnels[i][k][j],funnels[i][k][(j+1)%funnel_meridians],tFun[0]);
			}
		}
		return counter;
	}


	@Override
	public void printTexture(Graphics2D bufGraphics) {
		super.printTexture(bufGraphics);
		bufGraphics.setColor(new Color(255,168,16));
		printTexturePolygon(bufGraphics, tFun[0]);
	}

}
