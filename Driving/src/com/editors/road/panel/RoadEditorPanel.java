package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import com.BarycentricCoordinates;
import com.DrawObject;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPLine;
import com.SquareMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.road.RoadEditor;

public abstract class RoadEditorPanel extends JPanel {

	static final int greenRgb= Color.GREEN.getRGB();

	RoadEditor editor;

	int WIDTH=0;
	int HEIGHT=0;

	protected Color selectionColor=null;

	private boolean hide_objects=false;
	private boolean hide_splines=false;


	protected int MOVZ=0;

	protected double fi=0;
	protected double sinf=Math.sin(fi);
	protected double cosf=Math.cos(fi);

	protected int mask = 0xFF;
	protected double a1=0.6;
	protected double a2=1.0-a1;

	protected double rr=0;
	protected double gg=0;
	protected double bb=0;

	protected int xMovement=0;
	protected int yMovement=0;

	RoadEditorPanel(RoadEditor editor,
			int cENTER_WIDTH, int cENTER_HEIGHT) {

		this.HEIGHT=cENTER_HEIGHT;
		this.WIDTH=cENTER_WIDTH;
		this.editor=editor;
	}

	public abstract void drawRoad(PolygonMesh[] meshes, ArrayList<DrawObject> drawObjects,ArrayList<SPLine> splines,
			Point3D startPosition,
			ZBuffer landscapeZbuffer,Graphics2D graph);


	static Point3D foundPX_PY_PZ_TEXTURE_Intersection(Point3D pstart, Point3D pend,
			double y) {

		Point3D intersect=new Point3D(); 



		double l=(y-pstart.y)/(pend.y-pstart.y);


		intersect.p_x= (1-l)*pstart.p_x+l*pend.p_x;
		intersect.p_y= (1-l)*pstart.p_y+l*pend.p_y;		
		intersect.p_z= (1-l)*pstart.p_z+l*pend.p_z;

		intersect.texture_x=  (1-l)*pstart.texture_x+l*pend.texture_x;
		intersect.texture_y=  (1-l)*pstart.texture_y+l*pend.texture_y;

		return intersect;

	}

	public abstract int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs,boolean hasWater,int level);

	public double calculateCosin(Polygon3D polReal) {

		return 1.0;
	}


	protected int convertX(Point3D p){


		return convertX(p.x,p.y,p.z);
	}
	protected int convertY(Point3D p){


		return convertY(p.x,p.y,p.z);
	}

	public abstract void initialize();

	public abstract int convertX(double i,double j,double k);

	public abstract int convertY(double i,double j,double k);

	public abstract int invertX(int i,int j);

	public abstract int invertY(int i,int j);

	public void drawCurrentRect(ZBuffer landscapeZbuffer) {		

	}

	public abstract void zoom(int i);

	public abstract void translate(int i, int j);

	public abstract void mouseDown();

	public abstract void mouseUp();

	public abstract HashMap<Integer,Boolean> pickUpPoygonsWithFastCircle(PolygonMesh mesh);

	public HashMap<Integer,Boolean> pickUpPointsWithFastCircle(PolygonMesh mesh) {

		HashMap<Integer, Boolean> map = new HashMap<Integer,Boolean>();


		if(mesh.xpoints==null || editor.fastSelectionCircle==null)
			return map;


		int xc=editor.fastSelectionCircle.x;
		int yc=editor.fastSelectionCircle.y;

		int rx=editor.fastSelectionCircle.width;


		editor.coordinatesx[editor.getACTIVE_PANEL()].setText("");
		editor.coordinatesy[editor.getACTIVE_PANEL()].setText("");
		editor.coordinatesz[editor.getACTIVE_PANEL()].setText("");


		for(int j=0;j<mesh.xpoints.length;j++){

			double distance=Point3D.distance(xc, yc, 0, mesh.xpoints[j], mesh.ypoints[j], 0);


			if(distance<rx){

				map.put(new Integer(j), new Boolean(true));


			}

		}


		return map;


	}

	public boolean selectSPnodesWithRectangle(ArrayList<SPLine> splines) {
		return false;
	}

	public abstract void selectObjects(int x, int y, ArrayList<DrawObject> drawObjects);

	public ArrayList<DrawObject> selectObjects(int x, int y, ArrayList<DrawObject> drawObjects,boolean toSelect) {

		return null;

	}

	public boolean selectObjectsWithRectangle(ArrayList<DrawObject> drawObjects) {

		return false;

	}

	public abstract boolean selectSPNodes(int x, int y, ArrayList<SPLine> splines);

	public Polygon3D builProjectedPolygon(Polygon3D p3d) {

		return null;
	}

	public abstract void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int selected,Texture texture,ZBuffer zb,  
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc,int hashCode);

	public boolean isHide_objects() {
		return hide_objects;
	}

	public void setHide_objects(boolean hide_objects) {
		this.hide_objects = hide_objects;
	}

	public boolean isHide_splines() {
		return hide_splines;
	}

	public void setHide_splines(boolean hide_splines) {
		this.hide_splines = hide_splines;
	}

	public abstract void rotate(int signum);

	public abstract void displayObjects(ArrayList<DrawObject> drawObjects,Area totalVisibleField,ZBuffer zbuffer);

	public abstract void displaySPLines(ZBuffer landscapeZbuffer, ArrayList<SPLine> splines);


	public abstract void displayTerrain(ZBuffer landscapeZbuffer,PolygonMesh[] meshes);

	public abstract void displayStartPosition(ZBuffer landscapeZbuffer,Point3D startPosition);

	public abstract void gotoPosition(int goPOSX, int goPOSY);

	public abstract int getPOSX();

	public abstract int getPOSY();

	public Point3D getMiddle(SquareMesh sqMesh){

		if(sqMesh==null)
			return null;


		return new Point3D(

				sqMesh.getDx()* sqMesh.getNumx()/(2*xMovement*getDeltaX()),	
				sqMesh.getDy()* sqMesh.getNumy()/(2*yMovement*getDeltaY()),
				0
				);
	}

	abstract double getDeltaY();

	abstract double getDeltaX();

	public abstract void changeMotionIncrement(int i);

	public abstract Rectangle buildSelecctionCircle(MouseEvent e, int rad);

	protected boolean isUseSelectionColor(double xi, double yi, double zi, double cosin,
			int argbs, boolean hasWater, int level) {

		if(editor.isDrawFastSelectionCircle() && editor.getFastSelectionCircle()!=null){

			double xx=editor.getFastSelectionCircle().getX();
			double yy=editor.getFastSelectionCircle().getY();
			double r=editor.getFastSelectionCircle().getWidth();

			//faster test than sqrt
			if(Math.abs(xx-xi)>r || Math.abs(yy-yi)>r)
				return false;

			double d=Point3D.distance(xx, yy, 0, xi, yi, 0);

			if(d<r && d>r-10){
				return true;
			}


		}
		return false;


	}



}
