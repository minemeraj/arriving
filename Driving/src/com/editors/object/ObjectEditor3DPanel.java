package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.BarycentricCoordinates;
import com.CubicMesh;
import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.main.AbstractRenderer3D;
import com.main.Renderer3D;
import com.main.Road;



/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

class ObjectEditor3DPanel extends ObjectEditorPanel implements AbstractRenderer3D{

	private int y0=250;
	private int x0=350;


	private double deltay=0.5;
	private double deltax=0.5;
	
	private BufferedImage buf=null;
	
	private ZBuffer roadZbuffer;
	private int[] rgb;
	
	private double DEPTH_DISTANCE=1000;
	
	private double s2=Math.sqrt(2);
	private  Point3D pAsso;
	
	private double lightIntensity=1.0;
	
	
	private int greenRgb= Color.GREEN.getRGB();
	private int blackRgb= Color.BLACK.getRGB();


	ObjectEditor3DPanel(ObjectEditor oe){

	    super(oe);
		this.oe=oe;
		

		buildRightPanel();
		buildBottomPanel();

		
		initialize();


	}
	
	@Override
	public void initialize() {

		super.initialize();
		

		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		roadZbuffer=new ZBuffer(WIDTH*HEIGHT);
		pAsso=new Point3D(Math.cos(alfa)/s2,Math.sin(alfa)/s2,1/s2);
			
		buildNewZBuffers();
	}
	@Override
	public void displayAll() {

		
		buf=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		if(oe.jmt_show_shading.isSelected() || oe.isShowTexture() ){
			
			buildShading(buf);
			
		}
		else{
			
			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			displayPoints(bufGraphics);
			displayLines(bufGraphics);
			draw3Daxis(bufGraphics,WIDTH,HEIGHT);
			displayCurrentRect(bufGraphics);
			
		}

		g2.drawImage(buf,0,0,WIDTH,HEIGHT,null);
		resetLists();
		
		firePropertyChange("ObjectEditorUpdate",false,true);
	}
	

	/*private void buildShading(BufferedImage buf) {
		
		
		for(int i=0;i<oe.lines.size();i++){

			LineData ld=(LineData) oe.lines.elementAt(i);
			Polygon3D p3D=LineData.buildPolygon(ld,oe.points);
			decomposeClippedPolygonIntoZBuffer(p3D,Color.GRAY,null,roadZbuffer);
		}	
		
	

		buildScreen(buf); 
		
	}*/
	
	
	private void buildShading(BufferedImage buf) {
		
		PolygonMesh mesh=oe.getMeshes()[oe.getACTIVE_PANEL()];
		
	
		
		PolygonMesh pm=new PolygonMesh(mesh.points,mesh.polygonData);
		CubicMesh cm=CubicMesh.buildCubicMesh(pm).clone();
		rotateMesh(cm);
		
		int hashCode=mesh.hashCode();
		
		Texture texture=null;
		if(oe.isShowTexture()){
			
			texture=oe.currentTexture;
			if(texture==null){
				oe.setShowTexture(false);
				JOptionPane.showMessageDialog(this,"No texture loaded","Error",JOptionPane.ERROR_MESSAGE);				
				return;
			}
			
		}
		
		
		
		
		
		Point3D xVersor=cm.getXAxis();
		Point3D yVersor=cm.getYAxis();
		
		Point3D zVersor=cm.getZAxis();
		Point3D zMinusVersor=new Point3D(-zVersor.x,-zVersor.y,-zVersor.z);
		
		for(int i=0;i<cm.polygonData.size();i++){
			
			Point3D rotateOrigin=cm.point000; 
			
			Point3D xDirection=null;
			Point3D yDirection=null;
			
			int deltaWidth=0;
			int deltaHeight=cm.getDeltaY();		

			LineData ld=(LineData) cm.polygonData.get(i);
			Polygon3D p3D=LineData.buildPolygon(ld,cm.points);
			Color col=Color.GRAY;
			
			if(ld.isSelected)
				col=Color.RED;
			
			int face=cm.boxFaces[i];
			
			int deltaTexture=0;
			
			if(oe.isShowTexture()){
			
				

				
				
			 	if(face==Renderer3D.CAR_BOTTOM){
			 		deltaWidth=cm.getDeltaX()+cm.getDeltaX2();
				 	xDirection=xVersor;
				 	yDirection=yVersor;
			 	} 	
			 	else if(face==Renderer3D.CAR_FRONT){

					
					 deltaWidth=cm.getDeltaX();
					 deltaHeight=cm.getDeltaY2();
					 xDirection=xVersor;
					 yDirection=zMinusVersor;
					 
					 rotateOrigin=cm.point011;


				}
				else if(face==Renderer3D.CAR_BACK){
					 deltaWidth=cm.getDeltaX();
					 deltaHeight=0;
					 xDirection=xVersor;
					 yDirection=zVersor;


				}
				else if(face==Renderer3D.CAR_TOP){
					 deltaWidth=cm.getDeltaX();
					 xDirection=xVersor;
					 yDirection=yVersor;


				}
				else if(face==Renderer3D.CAR_LEFT) {
					
					xDirection=zVersor;
					yDirection=yVersor;

				}
				else if(face==Renderer3D.CAR_RIGHT) {  
					
					xDirection=zMinusVersor;
					yDirection=yVersor;

					deltaWidth=cm.getDeltaX2();
					rotateOrigin=cm.point001;
				}
			}
			
			
	    	decomposeClippedPolygonIntoZBuffer(p3D, col, texture,roadZbuffer,xDirection,yDirection,rotateOrigin,deltaTexture+deltaWidth,deltaHeight,hashCode);
		   
		}	
		
		int length=60;
		
		Color clr= Color.WHITE;
		
		decomposeLine(0,0,0,length,0,0,Road.OBJECT_LEVEL,roadZbuffer,clr.getRGB()); 
		decomposeLine(0,0,0,0,length,0,Road.OBJECT_LEVEL,roadZbuffer,clr.getRGB()); 
		decomposeLine(0,0,0,0,0,length,Road.OBJECT_LEVEL,roadZbuffer,clr.getRGB()); 

		for(int i=0;i<cm.points.length;i++){

			Point3D p= cm.points[i];
			
			Color col=Color.white;

			if(p.isSelected())
				col=Color.RED;

			//TOP
			decomposePointIntoZBfuffer(p,2,col,roadZbuffer);

		}

		buildScreen(buf); 
		
	}
	
	private void rotateMesh(CubicMesh cm) {
		
		cm.point000=rotatePoint(cm.point000);
		cm.point001=rotatePoint(cm.point001);
		cm.point010=rotatePoint(cm.point010);
		cm.point100=rotatePoint(cm.point100);
		cm.point110=rotatePoint(cm.point110);
		cm.point011=rotatePoint(cm.point011);
		cm.point101=rotatePoint(cm.point101);
		cm.point111=rotatePoint(cm.point111);
		
		for (int i = 0; i < cm.points.length; i++) {
			cm.points[i]=rotatePoint(cm.points[i]);
		}
		
		
	}

	private void decomposePointIntoZBfuffer(Point3D p, int radius, Color col, ZBuffer zbuffer) {
		
				
				double x=calcProspX(p);
				double y=calcProspY(p);
				
				for(int k=-radius;k<=radius;k++){
					
					for(int l=-radius;l<=radius;l++){
						
						
						int i=(int) x+k;
						int j=(int) y+l;
						
						if(i<0 || i>=WIDTH || j<0 || j>= HEIGHT)
							return;
						
						int tot=WIDTH*j+i;
						//System.out.println(x+" "+y+" "+tot);
		
			
		
						zbuffer.update(p.x,DEPTH_DISTANCE-p.y-radius,p.z,col.getRGB(),tot);
						
					}

				}
		
	}

	private Point3D rotatePoint(Point3D p) {
		
		double xx=(cosf*(p.x)-sinf*(p.y));
		double yy=(cosf*(p.y)+sinf*(p.x));
		double zz=p.z;
		
	
		double xxx=(zz)*sinteta+(xx)*costeta;
		double yyy= yy;
		double zzz=(zz)*costeta-(xx)*sinteta;
		
		Point3D pp=new Point3D(xxx,yyy,zzz);
		pp.setSelected(p.isSelected());
		
		return pp;
	}

	private void draw3Daxis(Graphics2D graphics2D, int i, int j) {
		
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
		 
		 //z axis
		 graphics2D.setColor(Color.BLUE);
		 x1=(int)(calcAssX(0,0,0)); 
		 y1=(int)(calcAssY(0,0,0));
		 x2=(int)(calcAssX(0,0,length));
		 y2=(int)(calcAssY(0,0,length));
	
		 if((y1<=j || y2<=j || y1>=0 || y2>=0  )&& (x1<=i || x2<=i ||  x1>=0 || x2>=0 ) )
		   graphics2D.drawLine(x1,y1,x2,y2);
	
	}

	private void displayLines(Graphics2D bufGraphics) {

		PolygonMesh mesh=oe.getMeshes()[oe.getACTIVE_PANEL()];

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
			if(oe.isShowNornals())
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

				Point3D p0=mesh.points[ld.getIndex(j)];
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
		
		PolygonMesh mesh=oe.getMeshes()[oe.getACTIVE_PANEL()];
		
		if(mesh.points==null)
			return;

		for(int i=0;i<mesh.points.length;i++){

			Point3D p= mesh.points[i];

			if(p.isSelected())
				bufGraphics.setColor(Color.RED);
			else
				bufGraphics.setColor(Color.white);

			//TOP
			bufGraphics.fillOval(calcAssX(p)-2,calcAssY(p)-2,5,5);
		


		}



	}


	
	@Override
	public void selectPoint(int x, int y) {

		//select point from lines
		if(!checkMultipleSelection.isSelected()) 
			polygon=new LineData();
		
		boolean found=false;
		
		PolygonMesh mesh=oe.getMeshes()[oe.getACTIVE_PANEL()];
		if(mesh.points==null)
			return; 
		
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
	
	@Override
	public double calculateScreenDistance(Polygon3D p3d, int xp, int yp) { 
		
		//calculate screen projection of each face
		


		//calculate the distance of the face in the selected point
		
		Point3D centroid=Polygon3D.findCentroid(p3d);
		
		double xx=(cosf*(centroid.x)-sinf*(centroid.y));
		double yy=(cosf*(centroid.y)+sinf*(centroid.x));
		double zz=centroid.z;
		
		double xxx=(zz)*sinteta+(xx)*costeta;

		
		return xxx;

	}
	

	
	
	@Override
	public void selectPointsWithRectangle() {
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		
		PolygonMesh mesh=oe.getMeshes()[oe.getACTIVE_PANEL()];
		if(mesh.points==null)
			return; 
		
		for (int i = 0; i <mesh.points.length; i++) {

			Point3D p = (Point3D) mesh.points[i];


			int x=calcAssX(p);
			int y=calcAssY(p);

			if(x>=x0 && x<=x1 && y>=y0 && y<=y1  ){

				p.setSelected(true);

			}

		}
		
	}

	@Override
	public void zoom(int n){
		
		if(n>0){
			deltay=deltax=deltax/2;
			moveCenter(0.5);
			
		}
		else{
			deltay=deltax=deltax*2;
			moveCenter(2.0);
		}
		
	}

    private void moveCenter(double d) {
		
    	
    	int dx=(int) ((getWidth()/2-x0)*(1-1.0/d));
		int dy=(int) ((getHeight()/2-y0)*(1-1.0/d));
		moveCenter(dx,dy);
		
	}



    private void moveCenter(int dx, int dy) {
    
    	x0+=dx;
    	y0+=dy;
    }
	@Override
	public int calcAssX(double x, double y, double z) {
		 

		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		
		double xxx=(zz)*sinteta+(xx)*costeta;
		double yyy= yy;
		double zzz=(zz)*costeta-(xx)*sinteta;
		
		return (int) ((yyy-xxx*sinAlfa)/deltax+x0);
	}
	@Override
	public int calcAssY(double x, double y, double z) {
		
		
		double xx=(cosf*(x)-sinf*(y));
		double yy=(cosf*(y)+sinf*(x));
		double zz=z;
		
		double xxx=(zz)*sinteta+(xx)*costeta;
		double yyy= yy;
		double zzz=(zz)*costeta-(xx)*sinteta;
		 
		return HEIGHT-(int) ((zzz-xxx*cosAlfa)/deltay+y0);
	}
	
	private double calcProspX(Point3D p) {
	
		return (int) ((p.y-p.x*sinAlfa)/deltax+x0);
	}

	private double calcProspY(Point3D p) {
		
		return HEIGHT-(int)((p.z-p.x*cosAlfa)/deltay+y0);
	}
	

	
	private int calcAssX(Point3D p) {
		 
		return calcAssX(p.x,p.y,p.z);
	}
	
	private int calcAssY(Point3D p) {
		 
		return  calcAssY(p.x,p.y,p.z);
	}
	
	@Override
	public void translate(int i, int j) {
	
		x0=x0-i*5;
		y0=y0-j*2;
		displayAll();
	}
	
	
		
	@Override
	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d, Color color,
			Texture texture, ZBuffer zbuffer,int hashCode) {
		
			//NOT USED, BUT IT MUST IMPLEMENT IT
		
	}

	@Override
	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d, Color color,
			Texture texture, ZBuffer zbuffer, Point3D xDirection,
			Point3D yDirection, Point3D origin, int deltaX, int deltaY,int hashCode) {

    	//avoid clipping:
    	Polygon3D clippedPolygon=p3d;//Polygon3D.clipPolygon3DInY(p3d,0);

    	Polygon3D[] triangles = Polygon3D.divideIntoTriangles(clippedPolygon);

    	double cosin=0;

    	if(texture!=null && xDirection==null && yDirection==null){

    		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
    		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);

    		xDirection=(p1.substract(p0)).calculateVersor();
    		Point3D normal=Polygon3D.findNormal(p3d);
    		yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

    	}
    	else{
    		
    		Point3D normal=Polygon3D.findNormal(p3d);
    		cosin=Point3D.calculateCosin(normal,pAsso);
    		
    	}
    	
    	

    	for(int i=0;i<triangles.length;i++){
    		
    		BarycentricCoordinates bc=new BarycentricCoordinates(triangles[i]);

    		decomposeTriangleIntoZBufferEdgeWalking( triangles[i],calculateShadowColor(
    				cosin,color.getRGB()), texture,zbuffer,
    				xDirection,yDirection,origin, deltaX, deltaY,
    				bc,hashCode
    				);

    	}

    }
	
	private int calculateShadowColor(double cosin, int argbs) {


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

	@Override
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,
			int rgbColor, Texture texture, ZBuffer zbuffer,
			Point3D xDirection, Point3D yDirection, Point3D origin, int deltaX,
			int deltaY,BarycentricCoordinates bc,int hashCode) {

		
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);

		double x0=calcProspX(p0);
		double y0=calcProspY(p0);
		double z0=p0.z;


		double x1=calcProspX(p1);
		double y1=calcProspY(p1);
		double z1=p1.z;

		double x2=calcProspX(p2);
		double y2=calcProspY(p2);
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

    			zbuffer.update(xi,DEPTH_DISTANCE-yi,zi,rgbColor,tot);
    			
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

    			zbuffer.update(xi,DEPTH_DISTANCE-yi,zi,rgbColor,tot);
    			
    		}


    	}	




	}

	private void decomposeLine( 
			double x1,	double y1,double z1,
			double x2,double y2,double z2,
			int level,
			ZBuffer zb,int rgbColor) {

		int xx1=(int)calcAssX(x1,y1,z1); 
		int yy1=(int)calcAssY(x1,y1,z1);

		int xx2=(int)calcAssX(x2,y2,z2);
		int yy2=(int)calcAssY(x2,y2,z2);



		if(yy1!=yy2){

			double i_yy=1.0/(yy2-yy1);

			if(yy2>yy1)

				for (int yy = yy1; yy <= yy2; yy++) { 
					
			

					double l=(yy-yy1)*i_yy;

					int xx=(int) (xx2*l+xx1*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);

					int tot=WIDTH*yy+xx;

					if(!zb.isToUpdate(yi,0,tot,level,-1))
						continue;			

					zb.set(xx,yi,yy,yi,rgbColor,Road.OBJECT_LEVEL,tot,ZBuffer.EMPTY_HASH_CODE);
				}
			else
				for (int yy = yy2; yy <= yy1; yy++) {

					double l=-(yy-yy2)*i_yy;

					int xx=(int) (xx1*l+xx2*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

	    			double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);

					int tot=WIDTH*yy+xx;

					if(!zb.isToUpdate(yi,0,tot,level,-1))
						continue;			


					zb.set(xx,yi,yy,yi,rgbColor,Road.OBJECT_LEVEL,tot,ZBuffer.EMPTY_HASH_CODE);
				}

		}
		else if(xx1!=xx2){
			
			double i_xx=1.0/(xx2-xx1);

			if(xx2>xx1)
				for (int xx = xx1; xx <= xx2; xx++) {

					double l=(xx-xx1)*i_xx;
					double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);

					int yy=(int) (yy2*l+yy1*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					int tot=WIDTH*yy+xx;
 
					if(!zb.isToUpdate(yi,0,tot,level,-1))
						continue;


					zb.set(xx,yi,yy,yi,rgbColor,Road.OBJECT_LEVEL,tot,ZBuffer.EMPTY_HASH_CODE);
				}
			else
				for (int xx = xx2; xx <= xx1; xx++) {

					double l=-(xx-xx2)*i_xx;
					double yi=DEPTH_DISTANCE+y2*l+y1*(1-l);
 
					int yy=(int) (yy1*l+yy2*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					int tot=WIDTH*yy+xx;


					if(!zb.isToUpdate(yi,0,tot,level,-1))
						continue;


					zb.set(xx,yi,yy,yi,rgbColor,Road.OBJECT_LEVEL,tot,ZBuffer.EMPTY_HASH_CODE);
				}

		}
		else {
			
			if(xx1<0 || yy1<0 )
				return;
			
			if(xx1>=WIDTH || yy1>= HEIGHT)
				return;

			int tot=WIDTH*yy1+xx1;

			if(!zb.isToUpdate(DEPTH_DISTANCE+y1,0,tot,level,-1))
				return;


			zb.set(xx1,y1,yy1,y1,rgbColor,Road.OBJECT_LEVEL,tot,ZBuffer.EMPTY_HASH_CODE);

		}


	}
	

	@Override
	public void buildNewZBuffers() {
		
       
		
		for(int i=0;i<roadZbuffer.getSize();i++){
			
			roadZbuffer.setRgbColor(blackRgb,i);
			
			
		}
		 int lenght=roadZbuffer.getSize();
		 rgb = new int[lenght];	

				
	}

	@Override
	public void buildScreen(BufferedImage buf) {

		int length=rgb.length;
		
		for(int i=0;i<length;i++){
			   
		
				   //set
			 	   rgb[i]=roadZbuffer.getRgbColor(i); 
				   //clean
			 	  roadZbuffer.set(0,0,0,0,blackRgb,Road.EMPTY_LEVEL,i,ZBuffer.EMPTY_HASH_CODE);
				  
 
		 }	   
		 buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,rgb);    
	              

	}
}
