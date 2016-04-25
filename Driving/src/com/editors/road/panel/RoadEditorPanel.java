package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.BarycentricCoordinates;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPLine;
import com.Texture;
import com.ZBuffer;
import com.editors.road.RoadEditor;
import com.main.Road;

public abstract class RoadEditorPanel extends JPanel {
	
	static final int greenRgb= Color.GREEN.getRGB();
	
	RoadEditor editor;
	
	int WIDTH=0;
	int HEIGHT=0;
	
	protected Color selectionColor=null;
	
	private boolean hide_objects=false;
	private boolean hide_splines=false;
	
	protected int POSX=0;
	protected int POSY=0;
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
	
	public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs,boolean hasWater) {
		
		//water effect
		if(hasWater && zi<-Road.WATER_FILLING+MOVZ){	
			
			int alphas=0xff & (argbs>>24);
			int rs = 0xff & (argbs>>16);
			int gs = 0xff & (argbs >>8);
			int bs = 0xff & argbs;
	
			rs=gs=0;

		
			return alphas <<24 | rs <<16 | gs <<8 | bs;
			
		}else{
			
			return argbs;
		}

	
	
	}
	
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

	public int invertX(int i){
		return i;
	}
	
	public int invertY(int j){
		
		return j;
		
	}
	public void drawCurrentRect(ZBuffer landscapeZbuffer) {		
		
	}
	
	public abstract void zoom(int i);
	
	public abstract void translate(int i, int j);
	
	public abstract void mouseDown();


	public abstract void mouseUp();
	
	public boolean selectPointsWithRectangle(PolygonMesh mesh) {
		
		return false;
	}
	

	public abstract boolean selectPolygons(int x, int y,PolygonMesh mesh);
	
	public abstract ArrayList<LineData> selectPolygons(int x, int y,PolygonMesh mesh,boolean toSelect);

	public abstract void selectObjects(int x, int y, ArrayList<DrawObject> drawObjects);
	
	public ArrayList<DrawObject> selectObjects(int x, int y, ArrayList<DrawObject> drawObjects,boolean toSelect) {
		
		return null;
		
	}

	public boolean selectPoints(int x, int y, PolygonMesh mesh, LineData polygon) {
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

	public abstract ArrayList<LineData> getClickedPolygons(int x, int y, PolygonMesh mesh);

	public abstract void rotate(int signum);
	
	public abstract void displayObjects(ArrayList<DrawObject> drawObjects,Area totalVisibleField,ZBuffer zbuffer);
	
	public abstract void displaySPLines(ZBuffer landscapeZbuffer, ArrayList<SPLine> splines);

	
	public abstract void displayTerrain(ZBuffer landscapeZbuffer,PolygonMesh[] meshes);
	
	public abstract void displayStartPosition(ZBuffer landscapeZbuffer,Point3D startPosition);


}
