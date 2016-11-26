package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.BPoint;
import com.Point3D;
import com.Segments;

/**
 *
 * TWO TEXTURES
 *
 * @author Administrator
 *
 */
public class Yacht0Model extends Ship0Model{

	public static String NAME="Yacht";

	double dxTexture=200;
	double dyTexture=200;

	int mainBridgeYSectionsNum=3;

	public Yacht0Model(
			double dx, double dy, double dz,
			double dxFront, double dyFront, double dzFront,
			double dxRear, double dyRear, double dzRear,
			double dxRoof, double dyRoof, double dzRoof,
			double rearOverhang,double frontOverhang,
			double rearOverhang1,double frontOverhang1
			) {
		super(dx, dy, dz,
				dxFront, dyFront, dzFront,
				dxRear, dyRear, dzRear,
				dxRoof, dyRoof, dzRoof,
				rearOverhang,frontOverhang,
				rearOverhang1,frontOverhang1);

	}

	@Override
	public void initMesh() {

		initTexturesArrays();

		points=new Vector<Point3D>();
		texturePoints=new Vector<Point3D>();

		buildHull();
		buildMainDecks();

		buildTextures();

		//hull
		int NF=(nxHull-1)*(nyHull-1)+2;
		//hull back closures
		NF+=(nxHull-1)/2;
		//deck
		NF+=nyHull-1;
		//main bridge
		NF+=mainDecks.length*(2+4*(mainBridgeYSectionsNum-1));

		faces=new int[NF][3][4];


		//build the hull
		int counter=0;
		counter=buildHullfaces(counter,nxHull,nyHull,nyCastle);
		counter=buildMainDecksYFaces(counter);
	}

	@Override
	protected void buildMainDecks() {

		Segments sDown=new Segments(x0,dxRoof,y0,dyRoof,z0+dz,dzRoof*0.5);
		double dyRoofUp=dy-rearOverhang1-frontOverhang1;
		Segments sUp=new Segments(x0,dxRoof,y0+rearOverhang1,dyRoofUp,z0+dz+dzRoof*0.5,dzRoof*0.5);

		mainDecks=new BPoint[2][mainBridgeYSectionsNum][4];

		mainDecks[0][0][0]=addBPoint(0.0,0.0,0,sDown);
		mainDecks[0][0][1]=addBPoint(1.0,0.0,0,sDown);
		mainDecks[0][0][2]=addBPoint(1.0,0.0,1.0,sDown);
		mainDecks[0][0][3]=addBPoint(0.0,0.0,1.0,sDown);

		mainDecks[0][1][0]=addBPoint(0.0,0.5,0,sDown);
		mainDecks[0][1][1]=addBPoint(1.0,0.5,0,sDown);
		mainDecks[0][1][2]=addBPoint(1.0,0.5,1.0,sDown);
		mainDecks[0][1][3]=addBPoint(0.0,0.5,1.0,sDown);

		mainDecks[0][2][0]=addBPoint(0.0,1.0,0,sDown);
		mainDecks[0][2][1]=addBPoint(1.0,1.0,0,sDown);
		mainDecks[0][2][2]=addBPoint(1.0,1.0,1.0,sDown);
		mainDecks[0][2][3]=addBPoint(0.0,1.0,1.0,sDown);

		mainDecks[1][0][0]=addBPoint(0.0,0.0,0,sUp);
		mainDecks[1][0][1]=addBPoint(1.0,0.0,0,sUp);
		mainDecks[1][0][2]=addBPoint(1.0,0.0,1.0,sUp);
		mainDecks[1][0][3]=addBPoint(0.0,0.0,1.0,sUp);

		mainDecks[1][1][0]=addBPoint(0.0,0.5,0,sUp);
		mainDecks[1][1][1]=addBPoint(1.0,0.5,0,sUp);
		mainDecks[1][1][2]=addBPoint(1.0,0.5,1.0,sUp);
		mainDecks[1][1][3]=addBPoint(0.0,0.5,1.0,sUp);

		mainDecks[1][2][0]=addBPoint(0.0,1.0,0,sUp);
		mainDecks[1][2][1]=addBPoint(1.0,1.0,0,sUp);
		mainDecks[1][2][2]=addBPoint(1.0,1.0,1.0,sUp);
		mainDecks[1][2][3]=addBPoint(0.0,1.0,1.0,sUp);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {
		hullColor=new Color(61,68,78);
		super.printTexture(bufGraphics);
	}

}
