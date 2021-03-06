package com;
import java.io.File;
import java.util.ArrayList;

/*
 * Created on 31/ott/09
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class CubicMesh extends PolygonMesh{

	public int deltaX=0;
	public int deltaY=0;
	public int deltaY2=0;
	public int deltaX2=0;

	public Point3D point000=null;
	public Point3D point010=null;
	public Point3D point001=null;
	public Point3D point100=null;
	public Point3D point011=null;
	public Point3D point110=null;
	public Point3D point101=null;
	public Point3D point111=null;

	private CubicMesh(double[] xpoints,double[] ypoints,double[] zpoints,ArrayList<LineData> polygonData) {
		super(xpoints,ypoints,zpoints,polygonData);
	}

	public CubicMesh() {

	}

	public static CubicMesh loadMeshFromFile(File file, double scale) {

		PolygonMesh pm= PolygonMesh.loadMeshFromFile(file,scale);

		CubicMesh cm=buildCubicMesh(pm);

		return cm;

	}

	public static CubicMesh buildCubicMesh(PolygonMesh pm) {

		CubicMesh cm=new CubicMesh(pm.xpoints,pm.ypoints,pm.zpoints,pm.polygonData);


		double dx=0;
		double dy=0;
		double dz=0;

		double minx=0;
		double miny=0;
		double minz=0;

		double maxx=0;
		double maxy=0;
		double maxz=0;

		for(int j=0;j<cm.xpoints.length;j++){

			Point3D point= new Point3D(cm.xpoints[j],cm.ypoints[j],cm.zpoints[j]);

			if(j==0){

				minx=point.x;
				miny=point.y;
				minz=point.z;

				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{

				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);


				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}


		}

		dx=maxx-minx;
		dy=maxy-miny;
		dz=maxz-minz;
		//starting points on the car texture
		cm.setDeltaX((int) dz+1);
		cm.setDeltaX2((int) (dx+dz+2));
		cm.setDeltaY((int) dz+1);
		cm.setDeltaY2((int) (dy+dz+2));

		cm.point000=new Point3D(0,0,0);
		cm.point100=new Point3D(dx,0,0);
		cm.point010=new Point3D(0,dy,0);
		cm.point001=new Point3D(0,0,dz);
		cm.point110=new Point3D(dx,dy,0);
		cm.point011=new Point3D(0,dy,dz);
		cm.point101=new Point3D(dx,0,dz);
		cm.point111=new Point3D(dx,dy,dz);

		//only positive data in the cubic mesh

		for (int i = 0; i < cm.xpoints.length; i++) {
			cm.xpoints[i]-=minx;
			cm.ypoints[i]-=miny;
			cm.zpoints[i]-=minz;
		}

		cm.setDescription(pm.getDescription());

		return cm;

	}

	@Override
	public CubicMesh clone() {

		CubicMesh cm=new CubicMesh();

        cm.boxFaces=new int[this.boxFaces.length];

        cm.xpoints=new double[this.xpoints.length];
        cm.ypoints=new double[this.ypoints.length];
        cm.zpoints=new double[this.zpoints.length];
        cm.selected=new boolean[this.xpoints.length];

		for(int i=0;i<this.xpoints.length;i++){

			cm.xpoints[i]=xpoints[i];
			cm.ypoints[i]=ypoints[i];
			cm.zpoints[i]=zpoints[i];

		}

		for(int i=0;i<this.polygonData.size();i++){

			cm.addPolygonData(polygonData.get(i).clone());
		}
		for(int i=0;i<this.normals.size();i++){

			cm.normals.add(normals.get(i).clone());
		}
		for(int i=0;i<this.boxFaces.length;i++){
			cm.boxFaces[i]=this.boxFaces[i];
		}



		cm.setDeltaX(getDeltaX());
		cm.setDeltaX2(getDeltaX2());
		cm.setDeltaY(getDeltaY());
		cm.setDeltaY2(getDeltaY2());

		cm.point000=point000.clone();
		cm.point100=point100.clone();
		cm.point010=point010.clone();
		cm.point001=point001.clone();
		cm.point110=point110.clone();
		cm.point011=point011.clone();
		cm.point101=point101.clone();
		cm.point111=point111.clone();

		return cm;
	}
	@Override
	public void translate(double i, double j, double k) {


		super.translate(i,j,k);
		point000.translate(i,j,k);
		point100.translate(i,j,k);
		point010.translate(i,j,k);
		point001.translate(i,j,k);
		point110.translate(i,j,k);
		point011.translate(i,j,k);
		point101.translate(i,j,k);
		point111.translate(i,j,k);

	}

	public Point3D getXAxis(){

		return point100.substract(point000).calculateVersor();

	}

	public Point3D getYAxis(){

		return point010.substract(point000).calculateVersor();

	}

	public Point3D getZAxis(){

		return point001.substract(point000).calculateVersor();

	}

	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}

	public int getDeltaX2() {
		return deltaX2;
	}

	public void setDeltaX2(int deltaX2) {
		this.deltaX2 = deltaX2;
	}

	public int getDeltaY2() {
		return deltaY2;
	}

	public void setDeltaY2(int deltaY2) {
		this.deltaY2 = deltaY2;
	}

	/**
	 * Rotate CubicMesh of cos, sin around z axis (x0,y0)
	 *
	 * @param x0
	 * @param y0
	 * @param cos
	 * @param sin
	 */
	public void rotateZ(double x0, double y0,double cos, double sin ) {

		for (int i = 0; i < xpoints.length; i++) {


			double xx=xpoints[i];
			double yy=ypoints[i];
			double zz=zpoints[i];

			xpoints[i]=x0+(xx-x0)*cos-(yy-y0)*sin;
			ypoints[i]=y0+(yy-y0)*cos+(xx-x0)*sin;
			zpoints[i]=zz;
		}

		point000.rotateZ(x0,  y0, cos, sin );
		point100.rotateZ(x0,  y0, cos, sin );
		point010.rotateZ(x0,  y0, cos, sin );
		point001.rotateZ(x0,  y0, cos, sin );
		point110.rotateZ(x0,  y0, cos, sin );
		point011.rotateZ(x0,  y0, cos, sin );
		point101.rotateZ(x0,  y0, cos, sin );
		point111.rotateZ(x0,  y0, cos, sin );


	}

	/**
	 * Rotate CubicMesh of cos, sin around y axis (x0,z0)
	 *
	 * @param x0
	 * @param z0
	 * @param cos
	 * @param sin
	 */
	public void rotateY(double x0, double z0,double cos, double sin ) {

		for (int i = 0; i < xpoints.length; i++) {


			double xx=xpoints[i];
			double yy=ypoints[i];
			double zz=zpoints[i];

			xpoints[i]=x0+(xx-x0)*cos-(zz-z0)*sin;
			ypoints[i]=yy;
			zpoints[i]=z0+(zz-z0)*cos+(xx-x0)*sin;
		}

		point000.rotateY(x0,  z0, cos, sin );
		point100.rotateY(x0,  z0, cos, sin );
		point010.rotateY(x0,  z0, cos, sin );
		point001.rotateY(x0,  z0, cos, sin );
		point110.rotateY(x0,  z0, cos, sin );
		point011.rotateY(x0,  z0, cos, sin );
		point101.rotateY(x0,  z0, cos, sin );
		point111.rotateY(x0,  z0, cos, sin );


	}

	public Point3D findCentroid() {



		double x=(point000.x+point100.x+point010.x+point110.x)*0.25;
		double y=(point000.y+point100.y+point010.y+point110.y)*0.25;


        return new Point3D(x,y,0);
	}

	public int getXLen() {
		return getDeltaX2()-getDeltaX();
	}

	public int getYLen() {
		return getDeltaY2()-getDeltaY();
	}

	public int getZLen() {
		return getDeltaX();
	}

}
