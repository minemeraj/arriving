package com.main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;

import com.BarycentricCoordinates;
import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.LineDataVertex;
import com.Plain;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.EditorData;

public abstract class Renderer3D extends DrivingFrame implements AbstractRenderer3D{

	int XFOCUS=0;
	int YFOCUS=0;
	static final int SCREEN_DISTANCE=600;

	public static final double SCALE=2.0;
	public static final double ONE_TO_ONE_SCALE=1.0;
	
	protected Point3D observerPoint=null;

	protected int HEIGHT=0;
	protected int WIDTH=0;
 
    //REFERENCE SYSTEM COORDINATES	
	protected int POSX=0;
	protected int POSY=0;
	protected int MOVZ=0;
	protected double viewDirection=0;	
	protected double viewDirectionCos=1.0;
	protected double viewDirectionSin=0.0;	

	protected static ZBuffer roadZbuffer;


	protected int greenRgb= CarFrame.BACKGROUND_COLOR.getRGB();

	static final int FRONT_VIEW=+1;
	static final int REAR_VIEW=-1;
	static int VIEW_TYPE=FRONT_VIEW;

	
	public static final int CAR_BACK=0;
	public static final int CAR_TOP=1;
	public static final int CAR_LEFT=2;
	public static final int CAR_RIGHT=3;
	public static final int CAR_FRONT=4;
	public static final int CAR_BOTTOM=5;
	
	public static final int[] faceIndexes={
		CAR_BACK,CAR_TOP,CAR_LEFT,CAR_RIGHT,CAR_FRONT,CAR_BOTTOM
	};
	
	
	public static final String[] faceDesc={
		"Ba","To","Le","Ri","Fr","Bo"
	};
	

	boolean isShadowMap=false;
	boolean isStencilBuffer=false;
	
	LightSource lightPoint=null;
	private double lightIntensity=1.0;


	@Override
	public void buildNewZBuffers() {

		for(int i=0;i<roadZbuffer.getSize();i++){

			roadZbuffer.setRgbColor(greenRgb, i);
			roadZbuffer.setZ(0, i);
			roadZbuffer.setLevel(Road.EMPTY_LEVEL, i);
		}
	

	}
	@Override
	public void buildScreen(BufferedImage buf) {

		int length=roadZbuffer.getSize();
		
		buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,roadZbuffer.getRgbColor());
		
		for(int i=0;i<length;i++){
			
			//clean
			roadZbuffer.set(0,0,0,0,greenRgb,Road.EMPTY_LEVEL,i,ZBuffer.EMPTY_HASH_CODE);
              
		

		}

	}


	protected double calculPerspY( double x, double y, double z) {
		double newy0= ((z)*SCREEN_DISTANCE*(1.0/y))+YFOCUS;
		return HEIGHT-newy0;
	}

	protected double calculPerspX(double x, double y, double z) {

		return ((x)*SCREEN_DISTANCE*(1.0/y))+XFOCUS;

	}

	private double calculPerspY(Point3D point) {
		return calculPerspY( point.x,  point.y, point.z);
	}

	private double calculPespX(Point3D point) {
		return calculPerspX( point.x,  point.y, point.z);
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
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int rgbColor,Texture texture,ZBuffer zb, 
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc,int hashCode) {

		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
		
		boolean isFacing=isFacing(p3d,normal,observerPoint);

		int level=p3d.getLevel();
	
		double cosin=p3d.getShadowCosin();
	
		
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);


		double x0=(int)calculPespX(p0);
		double y0=(int)calculPerspY(p0);
		double z0=p0.y;


		double x1=(int)calculPespX(p1);
		double y1=(int)calculPerspY(p1);
		double z1=p1.y;

		double x2=(int)calculPespX(p2);
		double y2=(int)calculPerspY(p2);
		double z2=p2.y;

		//check if triangle is visible
		double maxX=Math.max(x0,x1);
		maxX=Math.max(x2,maxX);
		double maxY=Math.max(y0,y1);
		maxY=Math.max(y2,maxY);
		double minX=Math.min(x0,x1);
		minX=Math.min(x2,minX);
		double minY=Math.min(y0,y1);
		minY=Math.min(y2,minY);
		
		if(maxX<0 || minX>WIDTH || maxY<0 || minY>HEIGHT)
			return;

		Point3D[] points=new Point3D[3];

		points[0]=new Point3D(x0,y0,z0,p0.x,p0.y,p0.z);
		points[1]=new Point3D(x1,y1,z1,p1.x,p1.y,p1.z);
		points[2]=new Point3D(x2,y2,z2,p2.x,p2.y,p2.z);
		
		
		int mip_map_level=(int) Math.sqrt(bc.getRealTriangleArea()/BarycentricCoordinates.getTriangleArea(x0, y0, x1, y1, x2, y2));
		if(texture!=null){
			
			int w=texture.getWidth();
			int h=texture.getHeight();
			
			Point3D pt0=bc.pt0;
			Point3D pt1=bc.pt1;
			Point3D pt2=bc.pt2;
			
			Point3D p=bc.getBarycentricCoordinates(new Point3D(points[0].p_x,points[0].p_y,points[0].p_z));
			double x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			double y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[0].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(points[1].p_x,points[1].p_y,points[1].p_z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);	
			points[1].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(points[2].p_x,points[2].p_y,points[2].p_z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[2].setTexurePositions(x,texture.getHeight()-y);
				
			/*
			points[0].setTexurePositions(ZBuffer.pickTexturePositionPCoordinates(texture,points[0].p_x,points[0].p_y,points[0].p_z,xDirection,yDirection,origin,deltaX,deltaY));
			points[1].setTexurePositions(ZBuffer.pickTexturePositionPCoordinates(texture,points[1].p_x,points[1].p_y,points[1].p_z,xDirection,yDirection,origin,deltaX,deltaY));
			points[2].setTexurePositions(ZBuffer.pickTexturePositionPCoordinates(texture,points[2].p_x,points[2].p_y,points[2].p_z,xDirection,yDirection,origin,deltaX,deltaY));
			*/
		}
		
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
		
		Point3D lowP=points[lower];
		Point3D upP=points[upper];
		Point3D midP=points[middle];
		
		int j0=midP.y>0?(int)midP.y:0;
		int j1=upP.y<HEIGHT?(int)upP.y:HEIGHT;
		
		
		for(int j=j0;j<j1;j++){
	

			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			Point3D intersects = Point3D.foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(upP,midP,j);
			Point3D intersecte = Point3D.foundPX_PY_PZ_TEXTURE_Intersection(upP,midP,j);
	
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,intersecte.p_z,intersecte.p_x,intersecte.p_y,intersecte.p_z,intersecte.texture_x,intersecte.texture_y);

			if(pstart.x>pend.x){

				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;



			double inverse=1.0/(end-start);
			double i_pstart_p_y=1.0/(pstart.p_y);
			double i_end_p_y=1.0/(pend.p_y);
			
			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){

				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i;

				double l=(i-start)*inverse;

				double yi=1.0/((1-l)*i_pstart_p_y+l*i_end_p_y);
				double zi=((1-l)*i_pstart_p_y*pstart.p_z+l*i_end_p_y*pend.p_z)*yi;
				
				if(!zb.isToUpdate(yi,zi,tot,level,hashCode) || isStencilBuffer){
					//z-fail stencil buffer with bias
					if(isStencilBuffer){
						if(!zb.isToUpdate(yi+4,zi,tot,level,hashCode))
							stencilBuffer(tot,isFacing);
					}
					continue;
				}	

				
				double xi=((1-l)*i_pstart_p_y*pstart.p_x+l*i_end_p_y*pend.p_x)*yi;  
				
                double texture_x=((1-l)*i_pstart_p_y*pstart.texture_x+l*i_end_p_y*pend.texture_x)*yi;
                double texture_y=((1-l)*i_pstart_p_y*pstart.texture_y+l*i_end_p_y*pend.texture_y)*yi;

                
             
				if(texture!=null)
					rgbColor=texture.getRGBMip((int)texture_x,(int) texture_y,mip_map_level);  
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY,bc);
				if(rgbColor==greenRgb)
					continue;

				//System.out.println(x+" "+y+" "+tot);    			

				zb.set(xi,yi,zi,yi,calculateShadowColor(xi,yi,zi,cosin,rgbColor,p3d.isWaterPolygon()),level,tot,hashCode);
				
			}


		}
		//LOWER TRIANGLE
		
		j0=lowP.y>0?(int)lowP.y:0;
		j1=midP.y<HEIGHT?(int)midP.y:HEIGHT;
		
		for(int j=j0;j<j1;j++){
	

			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			
			Point3D intersects = Point3D.foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(lowP,midP,j);
			
			Point3D insersecte = Point3D.foundPX_PY_PZ_TEXTURE_Intersection(lowP,midP,j);
			
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,insersecte.p_z,insersecte.p_x,insersecte.p_y,insersecte.p_z,insersecte.texture_x,insersecte.texture_y);


			if(pstart.x>pend.x){


				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;

			double inverse=1.0/(end-start);

			double i_pstart_p_y=1.0/(pstart.p_y);
			double i_end_p_y=1.0/(pend.p_y);


			int i0=start>0?start:0;
			
			
			for(int i=i0;i<end;i++){
	
				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i;

				double l=(i-start)*inverse;

				double yi=1.0/((1-l)*i_pstart_p_y+l*i_end_p_y);
				double zi=((1-l)*i_pstart_p_y*pstart.p_z+l*i_end_p_y*pend.p_z)*yi;
			
				if(!zb.isToUpdate(yi,zi,tot,level,hashCode) || isStencilBuffer){
					
					//z-fail stencil buffer with bias
					if(isStencilBuffer){
						
	
						if(!zb.isToUpdate(yi+4,zi,tot,level,hashCode))
							stencilBuffer(tot,isFacing);
					}
					continue;
				}	


			
				double xi=((1-l)*i_pstart_p_y*pstart.p_x+l*i_end_p_y*pend.p_x)*yi;

                double texture_x=((1-l)*i_pstart_p_y*pstart.texture_x+l*i_end_p_y*pend.texture_x)*yi;
                double texture_y=((1-l)*i_pstart_p_y*pstart.texture_y+l*i_end_p_y*pend.texture_y)*yi;

                

				if(texture!=null)
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY,bc);
					rgbColor=texture.getRGBMip((int)texture_x,(int) texture_y,mip_map_level);   
				if(rgbColor==greenRgb)
					continue;

				//System.out.println(x+" "+y+" "+tot);


			
				zb.set(xi,yi,zi,yi,calculateShadowColor(xi,yi,zi,cosin,rgbColor,p3d.isWaterPolygon()),level,tot,hashCode);
	
			}


		}	




	}
	
	public void stencilBuffer(int tot, boolean isFacing) {
		
		
	}


	private int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs,boolean isWater) {

		//double factor=(lightIntensity*(0.25+0.5*Math.exp(-yi*0.001)+0.25*cosin));
		double factor=(lightIntensity*(0.75+0.25*cosin));		

		
		int alphas=0xff & (argbs>>24);
		int rs = 0xff & (argbs>>16);
		int gs = 0xff & (argbs >>8);
		int bs = 0xff & argbs;
		//water effect
		if(isWater){		
		    rs=gs=0;
		}

		rs=(int) (factor*rs);
		gs=(int) (factor*gs);
		bs=(int) (factor*bs);
	
		return alphas <<24 | rs <<16 | gs <<8 | bs;
	
	}
	
	void calculateShadowCosines(DrawObject dro) {
	
		
		CubicMesh cm= (CubicMesh)dro.getMesh();
		
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){
			
			LineData ld=cm.polygonData.get(i);
			
			Polygon3D polygon = PolygonMesh.getBodyPolygon(cm.xpoints,cm.ypoints,cm.zpoints,ld,cm.getLevel());
			
			Point3D centroid = Polygon3D.findCentroid(polygon);
			
			Point3D normal=(Polygon3D.findNormal(polygon)).calculateVersor();
			
			ld.setShadowCosin(Point3D.calculateCosin(lightPoint.position.substract(centroid),normal));
			
		}
		
	}


	@Override
	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,int hashCode){

		Point3D origin=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		decomposeClippedPolygonIntoZBuffer(p3d, color, texture,zbuffer,null,null,origin,0,0,hashCode);

	}
	@Override
	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,
			Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY,int hashCode){		

		Polygon3D[] triangles = Polygon3D.divideIntoTriangles(p3d);
		
		for(int i=0;i<triangles.length;i++){
			
			Polygon3D triangle = triangles[i];
			
			Point3D normal=Polygon3D.findNormal(triangle);

			if(!isStencilBuffer && !isFacing(triangle,normal,observerPoint))
				continue;

			if(texture!=null && xDirection==null && yDirection==null){

				Point3D p0=new Point3D(triangle.xpoints[0],triangle.ypoints[0],triangle.zpoints[0]);
				Point3D p1=new Point3D(triangle.xpoints[1],triangle.ypoints[1],triangle.zpoints[1]);
				xDirection=(p1.substract(p0)).calculateVersor();

				yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

				//yDirection=Point3D.calculateOrthogonal(xDirection);
			}
			
			BarycentricCoordinates bc=new BarycentricCoordinates(triangle);
			
			Polygon3D clippedPolygon=Polygon3D.clipPolygon3DInY(triangle,(int) (SCREEN_DISTANCE*2.0/3.0));

			if(clippedPolygon.npoints==0)
				continue;
			
			Polygon3D[] clippedTriangles = Polygon3D.divideIntoTriangles(clippedPolygon);
			
			for (int j = 0; j < clippedTriangles.length; j++) {
				

				
				decomposeTriangleIntoZBufferEdgeWalking( clippedTriangles[j],color.getRGB(), texture,zbuffer, xDirection,yDirection,origin, deltaX, deltaY,bc,hashCode);
				
			}
	

		}

	}
	
	void drawObjects(DrawObject[] drawObjects,Rectangle totalVisibleFieldBounds,ZBuffer zbuffer){


		for(int i=0;i<drawObjects.length;i++){

			DrawObject dro=drawObjects[i];
     		drawPolygonMesh(dro, totalVisibleFieldBounds, zbuffer);
		}		

	}	

	private void drawPolygonMesh(DrawObject dro,Rectangle rect,ZBuffer zbuffer) {

		Polygon3D objBorder= dro.getBorder();
		
		boolean found=false;
		for (int i = 0; i < objBorder.npoints; i++) {
			
			Point3D p0=new Point3D(objBorder.xpoints[i],objBorder.ypoints[i],0);
			p0=buildTransformedPoint(p0);
			
			int iNext=(i==objBorder.npoints-1?0:i+1);			
			Point3D p1=new Point3D(objBorder.xpoints[iNext],objBorder.ypoints[iNext],0);
			p1=buildTransformedPoint(p1);
			
			if(rect.intersectsLine(p0.x,p0.y,p1.x,p1.y)){
				
				found=true;
				break;
			} 
		}
		
		if(!found)
			return;
		
		
		PolygonMesh mesh = dro.getMesh();
	    
		decomposeCubicMesh((CubicMesh) mesh,EditorData.objectTextures[dro.getIndex()],zbuffer);
		
	}
	
	void decomposeCubicMesh(CubicMesh cm, Texture texture,ZBuffer zBuffer){
		
		
		
		Point3D point000=buildTransformedPoint(cm.point000);				

		Point3D point011=buildTransformedPoint(cm.point011);

		Point3D point001=buildTransformedPoint(cm.point001);
			
		Point3D xVersor=buildTransformedVersor(cm.getXAxis());
		Point3D yVersor=buildTransformedVersor(cm.getYAxis());
		
		Point3D zVersor=buildTransformedVersor(cm.getZAxis());
		Point3D zMinusVersor=new Point3D(-zVersor.x,-zVersor.y,-zVersor.z);

		//////
		
		if(VIEW_TYPE==REAR_VIEW){
			///???
			yVersor=new Point3D(-yVersor.x,-yVersor.y,yVersor.z);
			xVersor=new Point3D(-xVersor.x,-xVersor.y,xVersor.z);
			zVersor=new Point3D(-zVersor.x,-zVersor.y,zVersor.z);
			zMinusVersor=new Point3D(-zMinusVersor.x,-zMinusVersor.y,zMinusVersor.z);
		}

		
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){
			
	
			
			int due=0;
			if(texture==null)
				due=(int)(255-i%15);
			
			Color col=new Color(due,0,0);
			
			LineData ld=cm.polygonData.get(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.xpoints,cm.ypoints,cm.zpoints,ld,cm.getLevel());
			polRotate.setShadowCosin(ld.getShadowCosin());
			
		
			int face=cm.boxFaces[i];
			
			buildTransformedPolygon(polRotate);

			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,texture,zBuffer);
			
          
				
		}
		
	
	}
	
	void decomposeCubiMeshPolygon(
			Polygon3D polRotate, 
			Point3D xVersor, 
			Point3D yVersor, 
			Point3D zVersor, 
			Point3D zMinusVersor,
			CubicMesh cm, 
			Point3D point000, 
			Point3D point011, 
			Point3D point001,
			int face, 
			Color col, 
			Texture texture, 
			ZBuffer zBuffer
			){
		
		int hashCode=cm.hashCode();
		
		Point3D xDirection=null;
		Point3D yDirection=null;
		
		Point3D rotateOrigin=point000;
		
		int deltaWidth=0;
		int deltaHeight=cm.getDeltaY();
		
		int deltaTexture=0;
		

		
	 	if(face==Renderer3D.CAR_BOTTOM){
	 		deltaWidth=cm.getDeltaX()+cm.getDeltaX2();
		 	xDirection=xVersor;
		 	yDirection=yVersor;
	 	} 
		if(face==CAR_FRONT  ){

			
			 deltaWidth=cm.getDeltaX();
			 deltaHeight=cm.getDeltaY2();
			 xDirection=xVersor;
			 yDirection=zMinusVersor;
			 
			 rotateOrigin=point011;


		}
		else if(face==CAR_BACK){
			 deltaWidth=cm.getDeltaX();
			 deltaHeight=0;
			 xDirection=xVersor;
			 yDirection=zVersor;


		}
		else if(face==CAR_TOP){
			 deltaWidth=cm.getDeltaX();
			 xDirection=xVersor;
			 yDirection=yVersor;


		}
		else if(face==CAR_LEFT) {
			
			xDirection=zVersor;
			yDirection=yVersor;

		}
		else if(face==CAR_RIGHT) {
			
			xDirection=zMinusVersor;
			yDirection=yVersor;

			deltaWidth=cm.getDeltaX2();
			rotateOrigin=point001;
		}
		
		
		
		decomposeClippedPolygonIntoZBuffer(polRotate,col,texture,zBuffer,xDirection,yDirection,rotateOrigin,deltaTexture+deltaWidth,deltaHeight,hashCode);
	}

	static boolean isFacing(Polygon3D pol,Point3D normal,Point3D observer){



		//Point3D p0=new Point3D(pol.xpoints[0],pol.ypoints[0],pol.zpoints[0]);
		Point3D p0=Polygon3D.findCentroid(pol);

		Point3D vectorObs=observer.substract(p0);

		double cosin=Point3D.calculateCosin(normal,vectorObs);

		return cosin>=0;
	}
	
	


	

	private void decomposePointIntoZBuffer(double xs, double ys, double zs,
			double ds, int rgbColor,ZBuffer zbuffer) {

		if(ys<=SCREEN_DISTANCE) return;

		double xd=calculPerspX(xs,ys,zs);
		double yd=calculPerspY(xs,ys,zs);

		if(xd<0 || xd>=WIDTH || yd<0 || yd>= HEIGHT)
			return;

		int x=(int) xd;
		int y=(int) yd;

		int tot=y*WIDTH+x;	

		zbuffer.update(xs,ys,zs,rgbColor,tot);

	}

	Polygon3D buildRectangleBase3D(double x,double y,double z,double dx,double dy,double dz){

		int[] cx=new int[4];
		int[] cy=new int[4];
		int[] cz=new int[4];

		cx[0]=(int) x;
		cy[0]=(int) y;
		cz[0]=(int) z;
		cx[1]=(int) (x+dx);
		cy[1]=(int) y;
		cz[1]=(int) z;
		cx[2]=(int) (x+dx);
		cy[2]=(int) (y+dy);
		cz[2]=(int) z;
		cx[3]=(int) x;
		cy[3]=(int) (y+dy);
		cz[3]=(int) z;

		Polygon3D base=new Polygon3D(4,cx,cy,cz);

		return base;

	}
	
	Polygon3D buildTransformedPolygon3D(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints,int level) { 



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];
		
		int[] cxtr=new int[size];
		int[] cytr=new int[size];
		
		for(int i=0;i<size;i++){


			LineDataVertex ldv=(LineDataVertex) ld.getItem(i);
			int num=ldv.getVertex_index();

			Point4D p= new Point4D(xpoints[num],ypoints[num],zpoints[num]);


			cxr[i]=(int)(p.x);
			cyr[i]=(int)(p.y);
			czr[i]=(int)(p.z);
			
			cxtr[i]=(int) ldv.getVertex_texture_x();
			cytr[i]=(int) ldv.getVertex_texture_y();

		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr,cxtr,cytr);
		buildTransformedPolygon(p3dr);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());
		p3dr.setShadowCosin(ld.getShadowCosin());
		p3dr.setLevel(level);
		p3dr.setWaterPolygon(ld.isWaterPolygon());
		p3dr.setIsFilledWithWater(ld.isFilledWithWater());
		
        return p3dr;

	}
	
	/**
	 * 
	 * Lighter polygon creation, with no texture points arrays
	 * 
	 * @param ld
	 * @param points
	 * @param level
	 * @return
	 */
	
	Polygon3D buildTransformedPolygon3DWithoutTexture(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints,int level) { 



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];
		
		for(int i=0;i<size;i++){


			LineDataVertex ldv=(LineDataVertex) ld.getItem(i);
			int num=ldv.getVertex_index();

			Point4D p= new Point4D(xpoints[num],ypoints[num],zpoints[num]);


			cxr[i]=(int)(p.x);
			cyr[i]=(int)(p.y);
			czr[i]=(int)(p.z);


		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr,null,null);
		buildTransformedPolygon(p3dr);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());
		p3dr.setShadowCosin(ld.getShadowCosin());
		p3dr.setLevel(level);
		p3dr.setWaterPolygon(ld.isWaterPolygon());
		p3dr.setIsFilledWithWater(ld.isFilledWithWater());
		
        return p3dr;

	}
	
	
	/**
	 * 
	 * Lighter polygon creation, with no texture points arrays
	 * 
	 * @param ld
	 * @param points
	 * @param level
	 * @return
	 */
	
	Polygon3D buildTransformedPolygonProjection(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints,int level) { 



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		
		for(int i=0;i<size;i++){


			LineDataVertex ldv=(LineDataVertex) ld.getItem(i);
			int num=ldv.getVertex_index();

			Point4D p= new Point4D(xpoints[num],ypoints[num],zpoints[num]);


			cxr[i]=(int)(p.x);
			cyr[i]=(int)(p.y);


		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,null,null,null);
		buildTransformedPolygon(p3dr,false);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());
		p3dr.setShadowCosin(ld.getShadowCosin());
		p3dr.setLevel(level);
		p3dr.setWaterPolygon(ld.isWaterPolygon());
		p3dr.setIsFilledWithWater(ld.isFilledWithWater());
		
        return p3dr;

	}
	
	private Point3D buildTransformedVersor(Point3D point) {

		Point3D newPoint=new Point3D();

	
			
		double x=point.x;
		double y=point.y;
		double z=point.z;
			
		newPoint.x= (viewDirectionCos*x+viewDirectionSin*y);
		newPoint.y= (viewDirectionCos*y-viewDirectionSin*x);	
		newPoint.z=z;

		return newPoint;
	}
	
	Point3D buildTransformedPoint(Point3D point) {

		Point3D newPoint=new Point3D();

		if(VIEW_TYPE==FRONT_VIEW){
			
			double x=point.x-POSX;
			double y=point.y-POSY;
			
			newPoint.x=(int) (viewDirectionCos*x+viewDirectionSin*y);
			newPoint.y=(int) (viewDirectionCos*y-viewDirectionSin*x);	
			newPoint.z=point.z+MOVZ;
		}
		else{

			double x=point.x-POSX;
			double y=point.y-POSY;
			
			newPoint.x=-(int) (viewDirectionCos*x+viewDirectionSin*y);
			newPoint.y=2*SCREEN_DISTANCE-(int) (viewDirectionCos*y-viewDirectionSin*x);
			newPoint.z=point.z+MOVZ;
		}
		return newPoint;
	}
	
	void buildTransformedPolygon(Polygon3D base) {
		buildTransformedPolygon(base,true);
	}
	
	void buildTransformedPolygon(Polygon3D base,boolean is3D) {

		

		for(int i=0;i<base.npoints;i++){

			if(VIEW_TYPE==FRONT_VIEW){
				
				double x=base.xpoints[i]-POSX;
				double y=base.ypoints[i]-POSY;
				
				base.xpoints[i]=(int) (viewDirectionCos*x+viewDirectionSin*y);
				base.ypoints[i]=(int) (viewDirectionCos*y-viewDirectionSin*x);	
				if(is3D)
					base.zpoints[i]=base.zpoints[i]+MOVZ;
			}
			else{
				
				double x=base.xpoints[i]-POSX;
				double y=base.ypoints[i]-POSY;
				
				base.xpoints[i]=-(int) (viewDirectionCos*x+viewDirectionSin*y);
				base.ypoints[i]=2*SCREEN_DISTANCE-(int) (viewDirectionCos*y-viewDirectionSin*x);	
				
				if(is3D)
					base.zpoints[i]=base.zpoints[i]+MOVZ;
			}
		}

	
	}
	
	protected double interpolate(double px, double py, Polygon3D p3d) {
       

		Polygon3D p1=Polygon3D.extractSubPolygon3D(p3d,3,0);

		if(p1.hasInsidePoint(px,py)){

			Plain plane1=Plain.calculatePlain(p1);

			return plane1.calculateZ(px,py);

		}

		Polygon3D p2=Polygon3D.extractSubPolygon3D(p3d,3,2);

		if(p2.hasInsidePoint(px,py)){

			Plain plane2=Plain.calculatePlain(p2);

			return plane2.calculateZ(px,py);

		}


		return 0;
	}

	private static int findBoxFace(Point3D normal,Point3D versusx,Point3D versusy,Point3D versusz) {

		double normx=Point3D.calculateDotProduct(normal,versusx);
		double normy=Point3D.calculateDotProduct(normal,versusy);
		double normz=Point3D.calculateDotProduct(normal,versusz);

		double absx = Math.abs(normx);
		double absy = Math.abs(normy);
		double absz = Math.abs(normz);

		if(absx>=absy && absx>=absz ){

			if(normx>0)
				return CAR_RIGHT;
			else
				return CAR_LEFT;

		}
		if(absy>=absx && absy>=absz ){

			if(normy>0)
				return CAR_FRONT;
			else
				return CAR_BACK;

		}
		if(absz>=absx && absz>=absy ){

			if(normz>0)
				return CAR_TOP;
			else
				return CAR_BOTTOM;

		}

		return -1;
	}

	public static int getFace(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints){
		
		int n=ld.size();
		
		Point3D p0=new Point3D(xpoints[ld.getIndex((n+0-1)%n)],ypoints[ld.getIndex((n+0-1)%n)],zpoints[ld.getIndex((n+0-1)%n)]);
		Point3D p1=new Point3D(xpoints[ld.getIndex(0)],ypoints[ld.getIndex(0)],zpoints[ld.getIndex(0)]);
		Point3D p2=new Point3D(xpoints[ld.getIndex((1+0)%n)],ypoints[ld.getIndex((1+0)%n)],zpoints[ld.getIndex((1+0)%n)]);

		Point3D normal=Point3D.calculateCrossProduct(p1.substract(p0),p2.substract(p1));

		normal=normal.calculateVersor();
		
		int boxFace=Renderer3D.findBoxFace(normal);
		return boxFace;

	}
	
	public static int getFace(LineData ld,Point3D[] points){
		
		int n=ld.size();
		
		Point3D p0=points[ld.getIndex((n+0-1)%n)];
		Point3D p1=points[ld.getIndex(0)];
		Point3D p2=points[ld.getIndex((1+0)%n)];

		Point3D normal=Point3D.calculateCrossProduct(p1.substract(p0),p2.substract(p1));

		normal=normal.calculateVersor();
		
		int boxFace=Renderer3D.findBoxFace(normal);
		return boxFace;

	}
	
	public static int getFace(LineData ld, ArrayList<Point3D> points) {

		int n=ld.size();
		
		Point3D p0=points.get(ld.getIndex((n+0-1)%n));
		Point3D p1=points.get(ld.getIndex(0));
		Point3D p2=points.get(ld.getIndex((1+0)%n));

		Point3D normal=Point3D.calculateCrossProduct(p1.substract(p0),p2.substract(p1));

		normal=normal.calculateVersor();
		
		int boxFace=Renderer3D.findBoxFace(normal);
		return boxFace;
	}


	public static int findBoxFace(Point3D normal ) {

		return findBoxFace(normal,new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
	}
	

	public int getMOVZ() {
		return MOVZ;
	}

	public static void showMemoryUsage() {

		MemoryMXBean xbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap=xbean.getHeapMemoryUsage();
		MemoryUsage nonheap=xbean.getNonHeapMemoryUsage();
		Runtime run=Runtime.getRuntime();

		System.out.println("free:"+run.freeMemory()+" , total: "+run.totalMemory());

		System.out.println("heap:"+heap);
		System.out.println("non heap:"+nonheap);
		System.out.println("*************");
	}
	
	public boolean isShadowMap() {
		return isShadowMap;
	}

	public void setShadowMap(boolean isShadowMap) {
		this.isShadowMap = isShadowMap;
	}

	public boolean isStencilBuffer() {
		return isStencilBuffer;
	}

	public void setStencilBuffer(boolean isStencilBuffer) {
		this.isStencilBuffer = isStencilBuffer;
	}

	public double getViewDirection() { 
		return viewDirection;
	}

	public void setViewDirection(double observerDirection) {
		
		if(observerDirection<0)
			observerDirection+=2*Math.PI;
		else if(observerDirection>Math.PI*2)
			observerDirection-=2*Math.PI;
		
		this.viewDirection = observerDirection;
		viewDirectionCos=Math.cos(observerDirection);
		viewDirectionSin=Math.sin(observerDirection);
		
	}

	

	
	

}
