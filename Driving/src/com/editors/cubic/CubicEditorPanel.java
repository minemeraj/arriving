package com.editors.cubic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;
import com.Point3D;
import com.PolygonMesh;
import com.editors.cubic.CubicEditor.CubeData;
import com.editors.cubic.CubicEditor.CubeListItem;

class CubicEditorPanel extends JPanel implements MouseListener{

	


	private BufferedImage buf=null;
	private Graphics2D comp;
	
	private double fi=0;
	private double sinf=Math.sin(fi);
	private double cosf=Math.cos(fi);
	
	private double alfa=Math.PI/4;
	private double sinAlfa=Math.sin(alfa);
	private double cosAlfa=Math.cos(alfa);
	
	private int y0=-1;
	private int x0=-1;

	private double deltay=0.5;
	private double deltax=0.5;
	
	private int WIDTH;
	private int HEIGHT;
	
	private CubicEditor cubicEditor=null;
	
	private Vector points=null;
	


	CubicEditorPanel(int WIDTH, int HEIGHT, CubicEditor cubicEditor) {
		
		
		y0=250;
		x0=350;
		
		this.WIDTH=WIDTH;
		this.HEIGHT=HEIGHT;
		this.cubicEditor=cubicEditor;
		
		addMouseListener(this);
	}
	
	void draw() {
		if(comp==null)
			comp=(Graphics2D) getGraphics();
		draw(comp);
	}
	
	private int calcAssX(double x, double y, double z) {
		 

		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		

		return (int) ((yy-xx*sinAlfa)/deltax+x0);
	}
	
	private int calcAssY(double x, double y, double z) {
		
		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		 

		return HEIGHT-(int) ((zz-xx*cosAlfa)/deltay+y0);
	}
	
	private int calcAssX(Point3D p) {
		 
		return calcAssX(p.x,p.y,p.z);
	}
	
	private int calcAssY(Point3D p) {
		 
		return  calcAssY(p.x,p.y,p.z);
	}
	
	private void draw(Graphics2D comp){
	
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);		
	
		Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
	
		bufGraphics.setColor(getBackground());
		bufGraphics.fillRect(0,0,WIDTH,HEIGHT);	
		
	    displayAxes(bufGraphics);
		displayCubeUnits(bufGraphics);
	
		
		comp.drawImage(buf,0,0,WIDTH,HEIGHT,null);
	
	}

	private void displayCubeUnits(Graphics2D bufGraphics) {
		
		CubeData cd = cubicEditor.cubeData;
		
		if(cd==null)
			return;
		
		int NX=cd.NX;
		int NY=cd.NY;
		int NZ=cd.NZ;
		double LX=cd.LX;
		double LY=cd.LY;
		double LZ=cd.LZ;
		
		double dx=LX/NX;
		double dy=LY/NY;
		double dz=LZ/NZ;
		
		bufGraphics.setColor(Color.WHITE);
		
		Vector selectedCubes=cubicEditor.getSelectedCubeUnit();
		
		for(int i=0;i<NX-1;i++)
			for(int j=0;j<NY-1;j++){

				for(int k=0;k<NZ-1;k++){

					double x=dx*i;
					double y=dy*j;
					double z=dz*k;
					
										
					short display=cd.selectionMask[i][j][k];
					
			
					
					if(display==0)
						continue;
					
					displayCubeUnit(x,y,z,dx,dy,dz,bufGraphics);
				}
			}
		
		if(selectedCubes!=null && selectedCubes.size()>0 && cubicEditor.checkDiplaySelectedCube.isSelected()){
		
				
			bufGraphics.setColor(Color.RED);
			
			for (int i = 0; i < selectedCubes.size(); i++) {
				CubeListItem cli = (CubeListItem) selectedCubes.elementAt(i);
				
				double x=dx*cli.i;
				double y=dy*cli.j;
				double z=dz*cli.k;
				
				displayCubeUnit(x,y,z,dx,dy,dz,bufGraphics);
			}
			
								
			
			bufGraphics.setColor(Color.WHITE);
			
			
		}

		PolygonMesh mesh = cubicEditor.getMeshes()[cubicEditor.getACTIVE_PANEL()];
		displayPoints(bufGraphics,mesh.xpoints,mesh.ypoints,mesh.zpoints,mesh.selected);
	}

	private void displayCubeUnit(double x, double y, double z, double dx,
			double dy, double dz, Graphics2D bufGraphics) {
		
		
		Point3D p000=new Point3D(x,y,z);
		Point3D p100=new Point3D(x+dx,y,z);
		Point3D p110=new Point3D(x+dx,y+dy,z);
		Point3D p010=new Point3D(x,y+dy,z);
		
		
		Point3D p001=new Point3D(x,y,z+dz);
		Point3D p011=new Point3D(x,y+dy,z+dz);
		Point3D p111=new Point3D(x+dx,y+dy,z+dz);
		Point3D p101=new Point3D(x+dx,y,z+dz);
		
		//top
		displayPolygon(p001,p101,p111,p011,bufGraphics);		
		//bottom
		displayPolygon(p000,p010,p110,p100,bufGraphics);
		
		//front
		displayPolygon(p100,p110,p111,p101,bufGraphics);
		//back
		displayPolygon(p000,p001,p011,p010,bufGraphics);
		//left
		displayPolygon(p000,p100,p101,p001,bufGraphics);
		//right
		displayPolygon(p010,p011,p111,p110,bufGraphics);
	
	}

	private void displayPolygon(Point3D p0, Point3D p1,Point3D p2, Point3D p3, Graphics2D bufGraphics) {
		
		bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
		bufGraphics.drawLine(calcAssX(p1),calcAssY(p1),calcAssX(p2),calcAssY(p2));
		bufGraphics.drawLine(calcAssX(p2),calcAssY(p2),calcAssX(p3),calcAssY(p3));
		bufGraphics.drawLine(calcAssX(p3),calcAssY(p3),calcAssX(p0),calcAssY(p0));
	}
	
	private void displayPoints(Graphics2D bufGraphics,
			double[] xpoints,
			double[] ypoints,
			double[] zpoints, 
			boolean[] selected) {
		
		for (int pos = 0; pos < xpoints.length; pos++) {
			
			Point3D p =new Point3D(xpoints[pos],ypoints[pos],zpoints[pos]);
			
			int i=(int)p.p_x;
			int j=(int)p.p_y;
			int k=(int)p.p_z;	
			
			if(selected[pos])
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.WHITE);
			
			int x=calcAssX(p);
			int y=calcAssY(p);
			bufGraphics.fillOval(x-2,y-2,5,5);
			//System.out.println(x+" "+y);
			
		}
		
	}
	


	/*private void displayLines(Graphics2D bufGraphics) {		


		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();


			bufGraphics.setColor(Color.WHITE);


			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)points.elementAt(ld.getIndex(j));
				Point3D p1=(Point3D)points.elementAt(ld.getIndex((j+1)%ld.size()));
				
				int i0=(int)p0.p_x;
				int j0=(int)p0.p_y;
				int k0=(int)p0.p_z;	
				
				if(cubicEditor.cubeData.selectionMask[i0][j0][k0]==0)
					continue;
				
				int i1=(int)p1.p_x;
				int j1=(int)p1.p_y;
				int k1=(int)p1.p_z;	
				
				if(cubicEditor.cubeData.selectionMask[i1][j1][k1]==0)
					continue;


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			
			if(cubicEditor.jmt31.isSelected())    		
				showNormals(ld,bufGraphics);

		}	

		for(int i=0;i<lines.size();i++){

			LineData ld=(LineData) lines.elementAt(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();

			if(!ld.isSelected())
				continue;

			bufGraphics.setColor(Color.RED);

			for(int j=0;j<numLInes;j++){

				Point3D p0=(Point3D)points.elementAt(ld.getIndex(j));
				Point3D p1=(Point3D)points.elementAt(ld.getIndex((j+1)%ld.size()));


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
		


		}}*/

	private void displayAxes(Graphics2D bufGraphics) {
		// TODO Auto-generated method stub
		
	}
	
	private void moveCenter(int dx, int dy) {
	    
    	x0+=dx;
    	y0+=dy;
    }

	void rotate(double df) {
		
		 fi+=df;
		 sinf=Math.sin(fi);
		 cosf=Math.cos(fi);
		
	}
	
	void zoom(int n){
		
		if(n>0){
			
			if(deltax==0.25)
				return;
			deltay=deltax=deltax/2;
		
			 moveCenter(0.5);
			
		}else{
		
			deltay=deltax=deltax*2;
		
			moveCenter(2.0);
		
		}
	}


    private void moveCenter(double d) {
		
    	
    	int dx=(int) ((getWidth()/2-x0)*(1-1.0/d));
		int dy=(int) ((getHeight()/2-y0)*(1-1.0/d));
		moveCenter(dx,dy);
		
	}
    
    
    void translate(int i, int j) {
		
		int dx=(int) (2*i/deltax);
		int dy=(int) (2*j/deltay);
		
		moveCenter(dx,dy);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		int buttonNum=arg0.getButton();
		//right button click

		selectPoint(arg0.getX(),arg0.getY());
		cubicEditor.displayAll();
		
	}

	private void selectPoint(int x, int y) {
		
		PolygonMesh mesh = cubicEditor.getMeshes()[cubicEditor.getACTIVE_PANEL()];
	
		for(int c=0;mesh.xpoints!=null && c<mesh.xpoints.length;c++){

			Point3D p=new Point3D(mesh.xpoints[c],mesh.ypoints[c],mesh.zpoints[c]);

			int xo=calcAssX(p);
			int yo=calcAssY(p);

	
			Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
			if(rect.contains(x,y)){

				int i=(int) p.p_x;
				int j=(int) p.p_y;
				int k=(int) p.p_z;
				
				CubeData cc = cubicEditor.cubeData;
				
				if(i==cc.NX-1) 
					i=cc.NX-2;
				
				if(j==cc.NY-1) 
					j=cc.NY-2;
				
				if(k==cc.NZ-1) 
					k=cc.NZ-2;
				
				cubicEditor.updateCubeSelectedCubeUnit( i, j, k);
				cubicEditor.updateComoboBoxes( i, j, k);
				cubicEditor.displayAll();
				
			}
		
		
		}
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Vector getPoints() {
		return points;
	}

	public void setPoints(Vector points) {
		this.points = points;
	}
}
