package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;



/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ObjectEditorTopBottomPanel extends ObjectEditorPanel {




	int NX=4;
	int NY=0;

	int MOVX=0;
	int MOVY=0;
	
	static int y0=250;
	static int x0=350;

	int dx=2;
	int dy=2;

	double deltay=0.5;
	double deltax=0.5;


	public ObjectEditorTopBottomPanel(ObjectEditor oe){

	    super(oe);
		this.oe=oe;
		

		buildRightPanel();
		buildBottomPanel();

		
		initialize();


	}



	public void displayAll() {


		BufferedImage buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

		Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
        

		
		displayPoints(bufGraphics);
		displayLines(bufGraphics);
		draw2Daxis(bufGraphics,WIDTH,HEIGHT);
		displayCurrentRect(bufGraphics);

		if(g2==null)
			g2=(Graphics2D) getGraphics();
		
		g2.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		resetLists();

	}


	public void draw2Daxis(Graphics2D graphics2D, int i, int j) {
		
		int length=60;
				
		// x axis
		graphics2D.setColor(Color.GREEN);
        int x1=(int) (calcAssX(0,0,0));
        int y1=(int)(calcAssY(0,0,0));
        int x2=(int)(calcAssX(length,0,0));
        int y2=(int)(calcAssY(length,0,0));
	
        if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		 graphics2D.drawLine(x1,y1,x2,y2);
		
		//y axis
        graphics2D.setColor(Color.YELLOW);
		 x1=(int)(calcAssX(0,0,0));
		 y1=(int)(calcAssY(0,0,0));
		 x2=(int)(calcAssX(0,length,0));
		 y2=(int)(calcAssY(0,length,0));
		 
		 if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		  graphics2D.drawLine(x1,y1,x2,y2);
		 
		
	}

	private void displayLines(Graphics2D bufGraphics) {

		PolygonMesh mesh=oe.meshes[oe.ACTIVE_PANEL];

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();


			bufGraphics.setColor(Color.WHITE);


			for(int j=0;j<numLInes;j++){

				Point3D p0=mesh.points[ld.getIndex(j)];
				Point3D p1=mesh.points[ld.getIndex((j+1)%ld.size())];


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			if(oe.jmt_show_normals.isSelected())
				showNormals(mesh.points,ld,bufGraphics);

		}	

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();

			if(!ld.isSelected())
				continue;

			bufGraphics.setColor(Color.RED);

			for(int j=0;j<numLInes;j++){

				Point3D p0=mesh.points[ld.getIndex(j)];;
				Point3D p1=mesh.points[ld.getIndex((j+1)%ld.size())];


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			

		}	

	}








	private void showNormals(Point3D[] points, LineData ld, Graphics2D bufGraphics) {
		
		Polygon3D p3d=new Polygon3D();
		int numLInes=ld.size();
		
		if(numLInes<3)
			return;
		
			
		for(int j=0;j<numLInes;j++){

			Point3D p0=points[ld.getIndex(j)];
			p3d.addPoint(p0);
		}
		
		Point3D normal = Polygon3D.findNormal(p3d).calculateVersor();
		normal=normal.multiply(50.0);
		Point3D centroid=Polygon3D.findCentroid(p3d);
		Point3D normalTip=centroid.sum(normal);
		bufGraphics.setColor(Color.GREEN);
		bufGraphics.drawLine(calcAssX(centroid),calcAssY(centroid),calcAssX(normalTip),calcAssY(normalTip));
		
	}

	private void displayPoints(Graphics2D bufGraphics) {
		
		PolygonMesh mesh=oe.meshes[oe.ACTIVE_PANEL];

		for(int i=0;i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			if(p.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.white);

			//TOP
			bufGraphics.fillOval(calcAssX(p)-2,calcAssY(p)-2,5,5);
		


		}



	}





	public void selectPoint(int x, int y) {

		boolean found=false;
		
		PolygonMesh mesh=oe.meshes[oe.ACTIVE_PANEL];
		
		//select point from lines
		if(!checkMultipleSelection.isSelected()) 
			polygon=new LineData();
		for(int i=0;i<mesh.points.length;i++){

			Point3D p=mesh.points[i];



			int xo=calcAssX(p);
			int yo=calcAssY(p);

	
			Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
			if(rect.contains(x,y)){

				selectPoint(p);

			    polygon.addIndex(i);
			    
			    found=true;

			}
			else if(!checkMultipleSelection.isSelected()) 
				p.setSelected(false);
		}
		
		
		if(!found)
			selectPolygon(x,y);

	}
	




	public double calculateScreenDistance(Polygon3D p3d, int x, int y) {

		Point3D centroid=Polygon3D.findCentroid(p3d);
		
	
		return centroid.z;
	}



	public void selectPointsWithRectangle() {

		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		
		PolygonMesh mesh=oe.meshes[oe.ACTIVE_PANEL];

		for (int i = 0; i < mesh.points.length; i++) {

			Point3D p = mesh.points[i];


			int x=calcAssX(p);
			int y=calcAssY(p);

			if(x>=x0 && x<=x1 && y>=y0 && y<=y1  ){

				p.setSelected(true);

			}

		}

	}


	
	public void zoomOut(){
		
		deltay=deltax=deltax*2;
	
		moveCenter(2.0);
	}
	
	public void zoomIn(){
		

		deltay=deltax=deltax/2;
	
	    moveCenter(0.5);
		
	
	
	}

  
    private void moveCenter(double d) {
		
    	
    	int dx=(int) ((getWidth()/2-x0)*(1-1.0/d));
		int dy=(int) ((getHeight()/2-y0)*(1-1.0/d));
		moveCenter(dx,dy); 
		
	}



	public void moveCenter(int dx, int dy) {
    
    	x0+=dx;
    	y0+=dy;
    }
	
	public int calcAssX(double x, double y, double z) {
		 

		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		
		return (int) (xx/deltax+x0);
	}
	
	public int calcAssY(double x, double y, double z) {
		
		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		 
		return HEIGHT-(int) (yy/deltay+y0);
	}
	

	
	private int calcAssX(Point3D p) {
		 
		return calcAssX(p.x,p.y,p.z);
	}
	
	private int calcAssY(Point3D p) {
		 
		return  calcAssY(p.x,p.y,p.z);
	}

	public void right() {
		
		
		x0=x0-5;
		displayAll();
		
	}

	public void left() {
		x0=x0+5;
		displayAll();
		
	}

	public void up(){
		y0=y0+2;
		displayAll();

	}

	public void down(){
		
		y0=y0-2;
		
		displayAll();

	}
	

		



}
