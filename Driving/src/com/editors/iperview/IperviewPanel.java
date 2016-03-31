package com.editors.iperview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;


public class IperviewPanel extends JPanel implements MouseWheelListener,MouseMotionListener,MouseListener{
	
	public static final int TYPE_FRONT=0;
	public static final int TYPE_LEFT=1;
	public static final int TYPE_RIGHT=2;
	public static final int TYPE_TOP=3;
	
	
    int type=-1;
    int WIDTH=0;
    int HEIGHT=0;
    
	BufferedImage buf=null;
	private Graphics2D comp;
	
	public double fi=0;
	public double sinf=Math.sin(fi);
	public double cosf=Math.cos(fi);
	
	int y0=-1;
	int x0=-1;

	double deltay=0.5;
	double deltax=0.5;
	
	
	
	IperviewEditor iperviewEditor=null;
	
	public Rectangle currentRect=null;
	public boolean isDrawCurrentRect=false;


	public IperviewPanel(IperviewEditor iperviewEditor, int type,int WIDTH,int HEIGHT){
		
		setLayout(null);
		
		this.type=type;
		this.iperviewEditor=iperviewEditor;
		setSize(WIDTH,HEIGHT);
		
		this.WIDTH=WIDTH;
		this.HEIGHT=HEIGHT;
		
		if(type==TYPE_FRONT){
			
			y0=250;
			x0=250;
		}
		else if(type==TYPE_LEFT){
			
			y0=250;
			x0=50;
		}
		else if(type==TYPE_RIGHT){
			
			y0=250;
			x0=50;
		}
		else if(type==TYPE_TOP){
			
			y0=250;
			x0=100;
		}
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		setTransferHandler(new FileTransferhandler());
		
		
	}
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		draw();
	}
	
	public void draw() {
		if(comp==null)
			comp=(Graphics2D) getGraphics();
		draw(comp);
	}

	private void draw(Graphics2D comp) {
		
		
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);		

		Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

		if(type==TYPE_FRONT)
			bufGraphics.setColor(getBackground());
		else
			bufGraphics.setColor(getBackground());
		bufGraphics.fillRect(0,0,WIDTH,HEIGHT);	
		
	    displayAxes(bufGraphics);
		displayLines(bufGraphics);
		displayPoints(bufGraphics);
		
		drawCurrentRect(bufGraphics);
		
		comp.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		
		firePropertyChange("ObjectEditorUpdate",false,true);
		
	}
	
	private void drawCurrentRect(Graphics2D bufGraphics) {
		
		if(!isDrawCurrentRect)
			return;
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		
		bufGraphics.setColor(Color.WHITE);
		bufGraphics.drawRect(x0,y0,x1-x0,y1-y0);
		
	}


	
private void displayAxes(Graphics2D bufGraphics) {
		
		bufGraphics.setColor(Color.WHITE);
		
		Point3D origin = new Point3D(0,0,0);
		Point3D originTox = new Point3D(10,0,0);
		Point3D originToy = new Point3D(0,10,0);
		Point3D originToz = new Point3D(0,0,10);
		
		//y axis
		bufGraphics.drawLine(calcAssX(origin),calcAssY(origin),calcAssX(originToy),calcAssY(originToy));
		//x axis
		bufGraphics.drawLine(calcAssX(origin),calcAssY(origin),calcAssX(originTox),calcAssY(originTox));
		//z axis
		bufGraphics.drawLine(calcAssX(origin),calcAssY(origin),calcAssX(originToz),calcAssY(originToz));
		
	}

	private void displayPoints(Graphics2D bufGraphics) {
		
		 PolygonMesh mesh = iperviewEditor.getMeshes()[iperviewEditor.getACTIVE_PANEL()];	
		
		if(mesh.points==null)
			return;
			
		for (int i = 0; i < mesh.points.length; i++) {
			
			Point3D p3d = mesh.points[i];
			if(p3d.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.WHITE);
			
			int x=calcAssX(p3d);
			int y=calcAssY(p3d);
			bufGraphics.fillOval(x-2,y-2,5,5);
			//System.out.println(x+" "+y);
			
		}
		
	}

	private void displayLines(Graphics2D bufGraphics) {


		PolygonMesh mesh = iperviewEditor.getMeshes()[iperviewEditor.getACTIVE_PANEL()];
		
		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData)mesh.polygonData.get(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();


			bufGraphics.setColor(Color.WHITE);


			for(int j=0;j<numLInes;j++){

				Point3D p0=mesh.points[ld.getIndex(j)];
				Point3D p1=mesh.points[ld.getIndex((j+1)%numLInes)];


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			
			//if(jmt42.isSelected())
			//	showNormals(points,ld,bufGraphics);
			
		}	

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData)mesh.polygonData.get(i);
			int numLInes=1;
			if(ld.size()>2)
				numLInes=ld.size();

			if(!ld.isSelected())
				continue;

			bufGraphics.setColor(Color.RED);

			for(int j=0;j<numLInes;j++){

				Point3D p0=mesh.points[ld.getIndex(j)];
				Point3D p1=mesh.points[ld.getIndex((j+1)%ld.size())];


				bufGraphics.drawLine(calcAssX(p0),calcAssY(p0),calcAssX(p1),calcAssY(p1));
			}
			

		}	

	}

	
	public void moveCenter(int dx, int dy) {
	    
    	x0+=dx;
    	y0+=dy;
    }
	
	private int calcAssX(double x, double y, double z) {
		 

		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		
		
		if(type==TYPE_FRONT)
			return (int) (xx/deltax+x0);
		else if(type==TYPE_LEFT)
			return (int) (yy/deltax+x0);
		else if(type==TYPE_RIGHT)
			return (int) (yy/deltax+x0);
		else
			return (int) (xx/deltax+x0);
	}
	
	private int calcAssY(double x, double y, double z) {
		
		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		 
		if(type==TYPE_FRONT)
			return HEIGHT-(int) (zz/deltay+y0);
		else if(type==TYPE_LEFT)
			return HEIGHT-(int) (zz/deltay+y0);
		else if(type==TYPE_RIGHT)
			return HEIGHT-(int) (zz/deltay+y0);
		else
			return HEIGHT-(int) (yy/deltay+y0);
	}
	
	private int calcAssX(Point3D p) {
		 
		return calcAssX(p.x,p.y,p.z);
	}
	
	private int calcAssY(Point3D p) {
		 
		return  calcAssY(p.x,p.y,p.z);
	}
	

	private Point3D invertPoint(int xp, int yp, int zp) {
		
		Point3D p=null;
		
		if(type==TYPE_TOP){
			
			double xx= (xp-x0)*deltax;
			double yy=deltay*(-yp+HEIGHT-y0);
			double zz=zp;
	
			double x=(cosf*(xx)+sinf*(yy));
			double y=(cosf*(yy)-sinf*(xx));
			
			p=new Point3D(x,y,zz);
		

		}
		else if(type==TYPE_FRONT){
			
			double xx= (xp-x0)*deltax;
			double zz=deltay*(-zp+HEIGHT-y0);
			double yy=yp;
	
			double x=(cosf*(xx)+sinf*(yy));
			double y=(cosf*(yy)-sinf*(xx));
			
			p=new Point3D(x,y,zz);
			

		}
		else if(type==TYPE_LEFT){
			
			double yy= (yp-x0)*deltax;
			double zz=deltay*(-zp+HEIGHT-y0);
			double xx=xp;
	
			double x=(cosf*(xx)+sinf*(yy));
			double y=(cosf*(yy)-sinf*(xx));
			
			p=new Point3D(x,y,zz);
			
	
		}
		
		return p;
	}


	
	@Override	
	public void mouseMoved(MouseEvent e) {
		Point p=e.getPoint();
		
		//screenPoint.setText(addBlanksLeft(3,invertX(p.getX()))+","+invertX(p.getY()));
	

	}
	
	


	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		
		if(iperviewEditor.isControlMode){
			
			if(iperviewEditor.ACTIVE_KEY==KeyEvent.VK_CONTROL && !iperviewEditor.checkMultipleSelection.isSelected())
			{
				updateSize(arg0);
				iperviewEditor.displayAll();
			}
			
		} 
		else{
			isDrawCurrentRect=true;
			updateRectangleSize(arg0);
			
		}
				
		
		

		
		//System.out.println((x1-x0)+" "+(y1-y0));
	}
	
	
	
    void updateRectangleSize(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentRect.setSize(x - currentRect.x,
                            y - currentRect.y);
       
        
        draw(); 
        
   
    }
    
    void updateSize(MouseEvent arg0) {
    	
    	int xp=arg0.getX();
		int yp=arg0.getY();
   
        if(iperviewEditor.selectedPoint!=null){	
        	
    		Point3D p3d=null;
			if(type==TYPE_FRONT){
				p3d=invertPoint(xp,0,yp);
				p3d.setY(iperviewEditor.selectedPoint.getY());
			}	
			else if(type==TYPE_LEFT){
				p3d=invertPoint(0,xp,yp);
				p3d.setX(iperviewEditor.selectedPoint.getX());
			}	
			else if(type==TYPE_RIGHT){
				p3d=invertPoint(0,xp,yp);
				p3d.setX(iperviewEditor.selectedPoint.getX());
			}	
			else if(type==TYPE_TOP)
			{
				p3d=invertPoint(xp,yp,0);
				p3d.setZ(iperviewEditor.selectedPoint.getZ());
			}
        	
        	iperviewEditor.selectedPoint.setX(p3d.getX());
        	iperviewEditor.selectedPoint.setY(p3d.getY());
        	iperviewEditor.selectedPoint.setZ(p3d.getZ());
        }
     
   
    }
    


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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

		
		if(!iperviewEditor.checkMultipleSelection.isSelected()) 
			iperviewEditor.setPolygon(new LineData());
		
		int xp=arg0.getX();
		int yp=arg0.getY();
		
		currentRect = new Rectangle(xp, yp, 0, 0);

		int button=arg0.getButton();
		
		PolygonMesh mesh = iperviewEditor.getMeshes()[iperviewEditor.getACTIVE_PANEL()];	

		if(button==MouseEvent.BUTTON3){
			
			iperviewEditor.prepareUndo();			

			Point3D p3d=null;
			if(type==TYPE_FRONT)
				p3d=invertPoint(xp,0,yp);
			else if(type==TYPE_LEFT)
				p3d=invertPoint(0,xp,yp);
			else if(type==TYPE_RIGHT)
				p3d=invertPoint(0,xp,yp);
			else if(type==TYPE_TOP)
				p3d=invertPoint(xp,yp,0);
			
			/*if(iperviewEditor.ACTIVE_KEY==KeyEvent.VK_CONTROL && !iperviewEditor.checkMultipleSelection.isSelected())
				iperviewEditor.moveSelectedPointWithMouse(p3d,type);
			else*/
				mesh.addPoint(p3d);

				iperviewEditor.displayAll();
			return;
		}	

		for (int i = 0; i <mesh.points.length; i++) {

			Point3D p3d = mesh.points[i];


			int x=calcAssX(p3d);
			int y=calcAssY(p3d);

			Rectangle rect=new Rectangle(x-2,y-2,5,5);

			if(rect.contains(xp,yp)){

				p3d.setSelected(true);
				iperviewEditor.selectedPoint=p3d;
		
				if(!iperviewEditor.checkCoordinatesx.isSelected())
					iperviewEditor.coordinatesx.setText(iperviewEditor.dfc.format(p3d.x));
				if(!iperviewEditor.checkCoordinatesy.isSelected())
					iperviewEditor.coordinatesy.setText(iperviewEditor.dfc.format(p3d.y));
				if(!iperviewEditor.checkCoordinatesz.isSelected())
					iperviewEditor.coordinatesz.setText(iperviewEditor.dfc.format(p3d.z));
				if(!iperviewEditor.checkExtraData.isSelected())
					iperviewEditor.extraData.setText(p3d.getData()!=null?p3d.getData().toString():null);
				
				iperviewEditor.getPolygon().addIndex(i);
				
				iperviewEditor.deselectAllLines();
			}
			else if(!iperviewEditor.checkMultipleSelection.isSelected()) 
				p3d.setSelected(false);


			//System.out.println(x+" "+y);

		}

		iperviewEditor.displayAll();
	}




	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		updateRectangleSize(arg0);
        selectPointsWithRectangle();
        isDrawCurrentRect=false;
        draw();
		
	}

	private void selectPointsWithRectangle() {
		
		if(!isDrawCurrentRect)
			return;
		

		PolygonMesh mesh = iperviewEditor.getMeshes()[iperviewEditor.getACTIVE_PANEL()];
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
        
        for (int i = 0; i < mesh.points.length; i++) {
        
        	Point3D p3d = mesh.points[i];


			int x=calcAssX(p3d);
			int y=calcAssY(p3d);

			if(x>=x0 && x<=x1 && y>=y0 && y<=y1  ){

				p3d.setSelected(true);

			}

		}
	
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		
		int move=arg0.getUnitsToScroll();
		
		if(move>0)
			translate(0,1);
		else
			translate(0,-1);
	
	}
	
	public class FileTransferhandler extends TransferHandler{
		
		@Override
		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
			
			for(int i=0;i<transferFlavors.length;i++){
				
				 if (!transferFlavors[i].equals(DataFlavor.javaFileListFlavor))
					 return false;
			}
		    return true;
		}
		@Override
		public boolean importData(JComponent comp, Transferable t) {
			
			iperviewEditor.forceReading=true;
			
			try {
				List list=(List) t.getTransferData(DataFlavor.javaFileListFlavor);
				Iterator itera = list.iterator();
				while(itera.hasNext()){
					
					Object o=itera.next();
					if(!(o instanceof File))
						continue;
					File file=(File) o;
					iperviewEditor.loadPointsFromFile(file);
					iperviewEditor.reloadPanels();
					iperviewEditor.draw();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	public void rotate(double df){
		
		 fi+=df;
		 sinf=Math.sin(fi);
		 cosf=Math.cos(fi);
		
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


	public void translate(int i, int j) {
		
		int dx=(int) (2*i*deltax);
		int dy=(int) (2*j*deltay);
		
		moveCenter(dx,dy);
		iperviewEditor.displayAll();
		
	}

	public void setMesh(PolygonMesh mesh) {
		this.iperviewEditor.getMeshes()[iperviewEditor.getACTIVE_PANEL()]=mesh;
		
	}


	


}
