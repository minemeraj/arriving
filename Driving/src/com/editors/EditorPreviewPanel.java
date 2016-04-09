package com.editors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.BarycentricCoordinates;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.main.AbstractRenderer3D;
import com.main.Road;



/*
 * Created on 24/ott/09
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class EditorPreviewPanel extends JDialog implements KeyListener, PropertyChangeListener,AbstractRenderer3D{

	private int WIDTH=800;
	private int BOTTOM_HEIGHT=100;
	private int HEIGHT=500;
	
	public BufferedImage buf=null;

		
	public static ZBuffer roadZbuffer;
	private int greenRgb= Color.BLACK.getRGB();


	public double deltay=1;
	public double deltax=1;
	public int y0=200;
	public int x0=100;
	
	private double DEPTH_DISTANCE=1000;
	
	public  Point3D pAsso;
	public double alfa=Math.PI/3;
	public double cosAlfa=Math.cos(alfa);
	public double sinAlfa=Math.sin(alfa);
	public double s2=Math.sqrt(2);
	public boolean isUseTextures=true;
	
	private Graphics2D graphics2D;
	private JPanel center;
	private JPanel bottom;
	
	private double lightIntensity=1.0;
	
	private double fi=0;
	private double cosf=Math.cos(fi);
	private double sinf=Math.sin(fi);
	private double teta=0;
	private double costeta=Math.cos(teta);
	private double sinteta=Math.sin(teta);
	
	public PolygonMesh mesh=null;
	public ArrayList polygons;	
	public ArrayList clonedPoints=null;
	
	public EditorPreviewPanel(){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Preview 3D");
		setLayout(null);
		
		setSize(WIDTH,HEIGHT+BOTTOM_HEIGHT);
		center=new JPanel();
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		bottom=new JPanel();
		bottom.setBounds(0,HEIGHT,WIDTH,BOTTOM_HEIGHT);
		
		JLabel usage=new JLabel();
		usage.setText("Move with arrow keys,zoom with F1,F2");
		bottom.add(usage);
		add(bottom);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		draw();
	}
	
	public void draw() {
		
		if(graphics2D==null)
			graphics2D=(Graphics2D) center.getGraphics();
		
		graphics2D.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		
	}
	@Override
	public void setVisible(boolean b) {
		
		super.setVisible(b);
		if(!b)
			dispose();
	}

	public void initialize() {

		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		roadZbuffer=new ZBuffer(WIDTH*HEIGHT);
		pAsso=new Point3D(Math.cos(alfa)/s2,Math.sin(alfa)/s2,1/s2);
				
		buildNewZBuffers();
	}





	@Override
    public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,int hashCode){
   	 
    	Point3D origin=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
    	decomposeClippedPolygonIntoZBuffer(p3d, color, texture,zbuffer,null,null,origin,0,0,hashCode);

    }
	@Override
    public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,
			Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY,int hashCode){		

		Point3D normal=Polygon3D.findNormal(p3d);



		if(texture!=null && xDirection==null && yDirection==null){

			Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
			Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
			xDirection=(p1.substract(p0)).calculateVersor();

			yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

			//yDirection=Point3D.calculateOrthogonal(xDirection);
		}

		Polygon3D[] triangles = Polygon3D.divideIntoTriangles(p3d);
		
		for(int i=0;i<triangles.length;i++){
			
			BarycentricCoordinates bc=new BarycentricCoordinates(triangles[i]);
			
			//Polygon3D clippedPolygon=Polygon3D.clipPolygon3DInY(triangles[i],(int) (Renderer3D.SCREEN_DISTANCE*2.0/3.0));

			//if(clippedPolygon.npoints==0)
			//	return ;
			
			Polygon3D[] clippedTriangles = Polygon3D.divideIntoTriangles(triangles[i]);
			
			for (int j = 0; j < clippedTriangles.length; j++) {
				
				decomposeTriangleIntoZBufferEdgeWalking( clippedTriangles[j],color.getRGB(), texture,zbuffer, xDirection,yDirection,origin, deltaX, deltaY,bc,hashCode);
				
			}

		

		}

	}
    
    /**
	 * 
	 * DECOMPOSE PROJECTED TRIANGLE USING EDGE WALKING AND
	 * PERSPECTIVE CORRECT MAPPING
	 * 
	 * @param p3d
	 * @param color
	 * @param texture
	 * @param useLowResolution
	 * @param xDirection
	 * @param yDirection
	 * @param origin 
	 */
	@Override
    public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int rgbColor,Texture texture,ZBuffer zbuffer,
    		Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
    		BarycentricCoordinates bc,int hashCode
    		) {

		
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);
		
	    

		//System.out.println(p3d+" "+rgbColor);

		double x0=calcAssX(p0);
		double y0=calcAssY(p0);
		double z0=p0.z;


		double x1=calcAssX(p1);
		double y1=calcAssY(p1);
		double z1=p1.z;

		double x2=calcAssX(p2);
		double y2=calcAssY(p2);
		double z2=p2.z;
		
		Point3D[] points=new Point3D[3];
		
		points[0]=new Point3D(x0,y0,z0,p0.x,p0.y,p0.z);
		points[1]=new Point3D(x1,y1,z1,p1.x,p1.y,p1.z);
		points[2]=new Point3D(x2,y2,z2,p2.x,p2.y,p2.z);
		
		
		int upper=0;
		int middle=1;
		int lower=2;
		
		for(int i=0;i<3;i++){
			
			if(points[i].y>points[upper].y)
				upper=i;
			
			if(points[i].y<points[lower].y)
				lower=i;
			
		}
		for(int i=0;i<3;i++){
			
			if(i!=upper && i!=lower)
				middle=i;
		}
	
		
    	//double i_depth=1.0/zs;
    	//UPPER TRIANGLE
		
		double inv_up_lo_y=1.0/(points[upper].y-points[lower].y);
		double inv_up_mi_y=1.0/(points[upper].y-points[middle].y);
		double inv_lo_mi_y=1.0/(points[lower].y-points[middle].y);
		
    	for(int j=(int) points[middle].y;j<points[upper].y;j++){
    		
    		//if(j>258)
    		//	continue;

    		double middlex=Point3D.foundXIntersection(points[upper],points[lower],j);
    		double middlex2=Point3D.foundXIntersection(points[upper],points[middle],j);

    		double l1=(j-points[lower].y)*inv_up_lo_y;
			double l2=(j-points[middle].y)*inv_up_mi_y;
			double zs=l1*points[upper].p_z+(1-l1)*points[lower].p_z;
			double ze=l2*points[upper].p_z+(1-l2)*points[middle].p_z;
			double ys=l1*points[upper].p_y+(1-l1)*points[lower].p_y;
			double ye=l2*points[upper].p_y+(1-l2)*points[middle].p_y;
			double xs=l1*points[upper].p_x+(1-l1)*points[lower].p_x;
			double xe=l2*points[upper].p_x+(1-l2)*points[middle].p_x;
    		
			Point3D pstart=new Point3D(middlex,j,zs,xs,ys,zs);
    		Point3D pend=new Point3D(middlex2,j,ze,xe,ye,ze);
    		
    		if(pstart.x>pend.x){

    			Point3D swap= pend.clone();
    			pend= pstart.clone();
    			pstart=swap;

    		}
    		
    		int start=(int)pstart.x;
    		int end=(int)pend.x;
    		
    		double inverse=1.0/(end-start);
    		//double i_pstart_p_y=1.0/(SCREEN_DISTANCE+pstart.p_y);
    		//double i_end_p_y=1.0/(SCREEN_DISTANCE+pend.p_y);
    		
    		for(int i=start;i<end;i++){

    			if(i<0 || i>=WIDTH || j<0 || j>= HEIGHT)
    				continue;
               
    			double l=(i-start)*inverse;
    			
    			
    			double xi=(1-l)*pstart.p_x+l*pend.p_x;
    			double yi=(1-l)*pstart.p_y+l*pend.p_y;
    			double zi=(1-l)*pstart.p_z+l*pend.p_z;
    			
    			
    			if(texture!=null)
    			  rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY,bc);
    			if(rgbColor==greenRgb)
    				continue;
    			int tot=WIDTH*j+i;
    			//System.out.println(x+" "+y+" "+tot);

    			zbuffer.update(xi,DEPTH_DISTANCE+yi,zi,rgbColor,tot);
    			
    		} 


    	}
    
      	//LOWER TRIANGLE
    	for(int j=(int) points[lower].y;j<points[middle].y;j++){

    		double middlex=Point3D.foundXIntersection(points[upper],points[lower],j);
    		double middlex2=Point3D.foundXIntersection(points[lower],points[middle],j);

			double l1=(j-points[lower].y)*inv_up_lo_y;
			double l2=(j-points[middle].y)*inv_lo_mi_y;
			double zs=l1*points[upper].p_z+(1-l1)*points[lower].p_z;
			double ze=l2*points[lower].p_z+(1-l2)*points[middle].p_z;
			double ys=l1*points[upper].p_y+(1-l1)*points[lower].p_y;
			double ye=l2*points[lower].p_y+(1-l2)*points[middle].p_y;
			double xs=l1*points[upper].p_x+(1-l1)*points[lower].p_x;
			double xe=l2*points[lower].p_x+(1-l2)*points[middle].p_x;
    		
			Point3D pstart=new Point3D(middlex,j,zs,xs,ys,zs);
    		Point3D pend=new Point3D(middlex2,j,ze,xe,ye,ze);
    	
    		    		
       		if(pstart.x>pend.x){
       			
       		
    			Point3D swap= pend.clone();
    			pend= pstart.clone();
    			pstart=swap;

    		}
       	
    		int start=(int)pstart.x;
    		int end=(int)pend.x;
        		
    		double inverse=1.0/(end-start);
    		
    		//double i_pstart_p_y=1.0/(SCREEN_DISTANCE+pstart.p_y);
    		//double i_end_p_y=1.0/(SCREEN_DISTANCE+pend.p_y);
    		
    		for(int i=start;i<end;i++){
    			
    			if(i<0 || i>=WIDTH || j<0 || j>= HEIGHT)
    				continue;
    			
    			double l=(i-start)*inverse;
    			
    			double xi=(1-l)*pstart.p_x+l*pend.p_x;
    			double yi=(1-l)*pstart.p_y+l*pend.p_y;
    			double zi=(1-l)*pstart.p_z+l*pend.p_z;
    		
    			
    			if(texture!=null)
    			  rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY,bc);
    			if(rgbColor==greenRgb)
    				continue;
    			int tot=WIDTH*j+i;
    			//System.out.println(x+" "+y+" "+tot);

    			zbuffer.update(xi,DEPTH_DISTANCE+yi,zi,rgbColor,tot);
    			
    		}


    	}	




	}
	
	private int calcAssX(Point3D p){


		return calcAssX(p.x,p.y,p.z);
	}
	private int calcAssY(Point3D p){


		return calcAssY(p.x,p.y,p.z);
	}

	private int calcAssX(double sx,double sy,double sz){

		
		//return x0+(int) (deltax*(sy-sx*sinAlfa));//axonometric formula
		return x0+(int) ((sx*sinAlfa-sy*sinAlfa)/deltay);
	}

	private int calcAssY(double sx,double sy,double sz){

		
		//return y0+(int) (deltay*(sz-sx*cosAlfa));
		return y0-(int) ((sz+sy*cosAlfa+sx*cosAlfa)/deltay);
	}
	

	
	public ArrayList clonePoints(Point3D[] points) {
		
		ArrayList clonePoints=new ArrayList();
		
		for (int i = 0; i < points.length; i++) {
			
			Point3D p = points[i];
			
		
			double xx=(cosf*(p.x)-sinf*(p.y));
			double yy=(cosf*(p.y)+sinf*(p.x));
			double zz=p.z;
			
			//clonePoints[i]=new BodyPoint3D(xx,yy,zz);
			
			double xxx=(zz)*sinteta+(xx)*costeta;
			double yyy= yy;
			double zzz=(zz)*costeta-(xx)*sinteta;
			
			clonePoints.add(new Point3D(xxx,yyy,zzz));

			
		}
		
	
		
		return clonePoints;
	
	}
	@Override
	public void buildNewZBuffers() {
		
		int lenght=roadZbuffer.getSize();
		
		for(int i=0;i<lenght;i++){
			
			roadZbuffer.setRgbColor(greenRgb,i);
			
			
		}
				
	}

	
	@Override
	public void buildScreen(BufferedImage buf) {

		 buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,roadZbuffer.getRgbColor()); 
		
		int length=roadZbuffer.getSize();
		
		for(int i=0;i<length;i++){
			   

				   //clean
			 	  roadZbuffer.set(0,0,0,0,greenRgb,Road.EMPTY_LEVEL,i,ZBuffer.EMPTY_HASH_CODE);
				  
 
		 }	   
		   
	              

	}
	
	private void zoom(int i) {
		if(i>0){
			deltax=deltax*2;
			deltay=deltay*2;
			
		}	
		else if(i<0){
			deltax=deltax/2;
			deltay=deltay/2;
			
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		int code =arg0.getKeyCode();
		
		if(code==KeyEvent.VK_F1  )
		{ 
		  zoom(-1);
		  draw();
		}
		else if(code==KeyEvent.VK_F2  )
		{  zoom(+1);
		   draw();
		}
		else if(code==KeyEvent.VK_Q  )
		{  
			rotateFI(+1);
		    draw();
		}
		else if(code==KeyEvent.VK_W  )
		{  
		
			rotateFI(-1);
		    draw();
		}
		else if(code==KeyEvent.VK_A){
			rotateTeta(+1);
			draw();
		}	
		else if(code==KeyEvent.VK_S){
			rotateTeta(-1);
			draw();
		}	
	}



	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	

		
	}
	
	public int calculateShadowColor(double cosin, int argbs) {


			double factor=(lightIntensity*(0.75+0.25*cosin));

			int alphas=0xff & (argbs>>24);
			int rs = 0xff & (argbs>>16);
			int gs = 0xff & (argbs >>8);
			int bs = 0xff & argbs;

			rs=(int) (factor*rs);
			gs=(int) (factor*gs);
			bs=(int) (factor*bs);

			return alphas <<24 | rs <<16 | gs <<8 | bs;
	
	}
	
	private void rotateFI(int i) {
		
		fi+=0.1*i;
		cosf=Math.cos(fi);
		sinf=Math.sin(fi);
		
		clonedPoints=clonePoints(mesh.points);
		PolygonMesh pm=new PolygonMesh(clonedPoints,mesh.polygonData);
		polygons = PolygonMesh.getBodyPolygons(pm);
	}
	
	private void rotateTeta(int i) {
		
		teta+=0.1*i;
		costeta=Math.cos(teta);
		sinteta=Math.sin(teta);
		
		clonedPoints=clonePoints(mesh.points);
		PolygonMesh pm=new PolygonMesh(clonedPoints,mesh.polygonData);
		polygons = PolygonMesh.getBodyPolygons(pm);
	}
	
	public void setFi(double angle){
		
		fi=angle;
		cosf=Math.cos(fi);
		cosf=Math.sin(fi);
		
		clonedPoints=clonePoints(mesh.points);
		PolygonMesh pm=new PolygonMesh(clonedPoints,mesh.polygonData);
		polygons = PolygonMesh.getBodyPolygons(pm);
	}
	
	public void setTeta(double angle){
		
		teta=angle;
		costeta=Math.cos(fi);
		sinteta=Math.sin(fi);
		
		clonedPoints=clonePoints(mesh.points);
		PolygonMesh pm=new PolygonMesh(clonedPoints,mesh.polygonData);
		polygons = PolygonMesh.getBodyPolygons(pm);
	}

}
