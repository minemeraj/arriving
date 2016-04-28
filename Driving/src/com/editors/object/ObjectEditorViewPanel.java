package com.editors.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import com.LineData;
import com.Polygon3D;
import com.PolygonMesh;

public abstract class ObjectEditorViewPanel extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener{
	
	static int HEIGHT=ObjectEditor.HEIGHT;
	static int WIDTH=ObjectEditor.WIDTH;
	
	Graphics2D g2;
	
	ObjectEditorPanel objEditorPanel;
	
	Rectangle currentRect=null;
	
	private boolean isDrawCurrentRect=false;
	
	
	private double fi=0;
	double sinf=Math.sin(fi);
	double cosf=Math.cos(fi);

	private double teta=0;
	protected double costeta=Math.cos(teta);
	protected double sinteta=Math.sin(teta);

	public ObjectEditorViewPanel(ObjectEditorPanel oep) {
		super();
		this.objEditorPanel = oep;
		
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		
		initialize();
	}

	abstract void displayAll();
	
	public void initialize(){
		
		g2=(Graphics2D) getGraphics();
	}
	
	abstract void translate(int i, int j);

	abstract void selectPoint(int x, int y);
	
	abstract void zoom(int n);	

	abstract int calcAssY(double x, double y, double z) ;

	abstract int calcAssX(double x, double y, double z);
	

	void displayCurrentRect(Graphics2D bufGraphics) {

		if(!isDrawCurrentRect)
			return;

		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);

		bufGraphics.setColor(Color.WHITE);
		bufGraphics.drawRect(x0,y0,x1-x0,y1-y0);

	}

	abstract void selectPointsWithRectangle();

	abstract double calculateScreenDistance(Polygon3D p3d, int x, int y);

	Area clipPolygonToArea2D(Polygon p_in, Area area_out){
		return null;
	}
	
	protected void selectPolygon(int x, int y) {
		
		ObjectEditor oe = objEditorPanel.oe;

		PolygonMesh mesh=oe.getMeshes()[oe.getACTIVE_PANEL()];

		ArrayList<LineDataWithDistance> polygonsInLine=new ArrayList<LineDataWithDistance>();


		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);

			if(ld.size()>2){

				Polygon3D p3d=objEditorPanel.buildPolygon(ld,mesh.points);

				Polygon poly=buildProjection(p3d);


				if(poly.contains(x,y)){


					double current_distance=calculateScreenDistance(p3d,x,y);

					polygonsInLine.add(new LineDataWithDistance(ld,current_distance));


				}

			}

			if(!objEditorPanel.checkMultipleSelection.isSelected())
				ld.setSelected(false);
		}
		PolygonComparator cc=new PolygonComparator();

		if(polygonsInLine.size()>0){

			Collections.sort(polygonsInLine,cc);

			LineData ld=((LineDataWithDistance)polygonsInLine.get(polygonsInLine.size()-1)).getLine_data();
			ld.setSelected(true);
			objEditorPanel.chooseFace.setSelectedIndex(1+Integer.parseInt(ld.getData()));
		}

	}
	
	private Polygon buildProjection(Polygon3D p3d) { 



		int size=p3d.npoints;

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];

		//Point3D pn= Polygon3D.findNormal(p3d);
		//boolean clock_versus=pn.z>=0;	

		for(int i=0;i<size;i++){

			double x=p3d.xpoints[i];
			double y=p3d.ypoints[i];
			double z=p3d.zpoints[i];


			cxr[i]=calcAssX(x,y,z); 
			cyr[i]=calcAssY(x,y,z);
			czr[i]=0;

		}



		Polygon poly=new Polygon(cxr,cyr,size);


		return poly;

	}

	
	@Override
	public void mouseMoved(MouseEvent e) {



	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {

		int xp=arg0.getX();
		int yp=arg0.getY();
		currentRect = new Rectangle(xp, yp, 0, 0);

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {

		updateSize(arg0);
		selectPointsWithRectangle();
		isDrawCurrentRect=false;
		displayAll();

	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {

		selectPoint(arg0.getX(),arg0.getY());
		displayAll();

	}
	@Override
	public void mouseDragged(MouseEvent e) {

		isDrawCurrentRect=true;
		updateSize(e);

	}
	


	private void updateSize(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		currentRect.setSize(x - currentRect.x,
				y - currentRect.y);


		displayAll(); 


	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

		int pix=arg0.getUnitsToScroll();
		if(pix>0) 
			translate(0,1);
		else 
			translate(0,-1);

	}



	void rotate(double df){

		fi+=df;
		sinf=Math.sin(fi);
		cosf=Math.cos(fi);

	}

	void rotateTeta(int i) {

		teta+=0.1*i;
		costeta=Math.cos(teta);
		sinteta=Math.sin(teta);

	}

	private class PolygonComparator implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {

			LineDataWithDistance pol1=(LineDataWithDistance) o1;
			LineDataWithDistance pol2=(LineDataWithDistance) o2;

			if(pol1.getScreen_distance()>pol2.getScreen_distance())
				return 1;
			else if(pol1.getScreen_distance()<pol2.getScreen_distance())
				return -1;


			return 0;
		}

	}
	
	private class LineDataWithDistance {


		public LineDataWithDistance(LineData line_data, double screen_distance) {
			super();
			this.line_data = line_data;
			this.screen_distance = screen_distance;
		}
		LineData line_data=null;
		private double screen_distance=0;


		public double getScreen_distance() {
			return screen_distance;
		}
		public void setScreen_distance(double screen_distance) {
			this.screen_distance = screen_distance;
		}
		public LineData getLine_data() {
			return line_data;
		}
		public void setLine_data(LineData line_data) {
			this.line_data = line_data;
		}


	}


}
