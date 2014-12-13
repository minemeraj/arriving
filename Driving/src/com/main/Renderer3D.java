package com.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Vector;

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

public class Renderer3D implements AbstractRenderer3D{

	int XFOCUS=0;
	int YFOCUS=0;
	public static final int SCREEN_DISTANCE=300;
	
	public Point3D observerPoint=null;
	


	public int HEIGHT=0;
	public int WIDTH=0;
 
    //REFERENCE SYSTEM COORDINATES	
	public int POSX=0;
	public int POSY=0;
	public int MOVZ=0;
	public double viewDirection=0;	
	public double viewDirectionCos=1.0;
	public double viewDirectionSin=0.0;	

	public static ZBuffer roadZbuffer;


	public int greenRgb= CarFrame.BACKGROUND_COLOR.getRGB();

	public static final int FRONT_VIEW=+1;
	public static final int REAR_VIEW=-1;
	public static int VIEW_TYPE=FRONT_VIEW;

	
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
	
	public LightSource lightPoint=null;
	public double lightIntensity=1.0;



	public void buildNewZBuffers() {

		setViewDirection(0);

		for(int i=0;i<roadZbuffer.getSize();i++){

			roadZbuffer.rgbColor[i]=greenRgb;
			roadZbuffer.z[i]=0;
			roadZbuffer.empty[i]=true;
		}
	

	}

	public void buildScreen(BufferedImage buf) {

		int length=roadZbuffer.getSize();
		
		buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,roadZbuffer.rgbColor);
		
		for(int i=0;i<length;i++){

			
			//clean
			roadZbuffer.set(0,0,0,greenRgb,false,i);
              
		

		}
		
		
		//buf.setRGB(0,0,WIDTH,HEIGHT,rgb,0,WIDTH);


	}

	/*private void cleanZBuffer() {

	for(int i=0;i<roadZbuffer.length;i++){

		roadZbuffer[i].set(0,greenRgb);

	}*/
	
	/*public double calculPerspY( double x, double y, double z) {
		double newy0= YFOCUS+((z-YFOCUS)*SCREEN_DISTANCE/y);
		return HEIGHT-newy0;
	}

	public double calculPerspX(double x, double y, double z) {

		return ((x-XFOCUS)*SCREEN_DISTANCE/y);

	}*/



	public double calculPerspY( double x, double y, double z) {
		double newy0= ((z)*SCREEN_DISTANCE/y)+YFOCUS;
		return HEIGHT-newy0;
	}

	public double calculPerspX(double x, double y, double z) {

		return ((x)*SCREEN_DISTANCE/y)+XFOCUS;

	}

	public double calculPerspY(Point3D point) {
		return calculPerspY( point.x,  point.y, point.z);
	}

	public double calculPespX(Point3D point) {
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
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int rgbColor,Texture texture,ZBuffer zb, 
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc) {

		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
		
		boolean isFacing=isFacing(p3d,normal,observerPoint);


	
		double cosin=p3d.getShadowCosin();
	
		
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);



		//System.out.println(p3d+" "+rgbColor);

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
				
				if(!zb.isToUpdate(yi,tot) || isStencilBuffer){
					//z-fail stencil buffer with bias
					if(isStencilBuffer){
						if(!zb.isToUpdate(yi+4,tot))
							stencilBuffer(tot,isFacing);
					}
					continue;
				}	

				double zi=((1-l)*i_pstart_p_y*pstart.p_z+l*i_end_p_y*pend.p_z)*yi;
				double xi=((1-l)*i_pstart_p_y*pstart.p_x+l*i_end_p_y*pend.p_x)*yi;  
				
                double texture_x=((1-l)*i_pstart_p_y*pstart.texture_x+l*i_end_p_y*pend.texture_x)*yi;
                double texture_y=((1-l)*i_pstart_p_y*pstart.texture_y+l*i_end_p_y*pend.texture_y)*yi;

				if(texture!=null)
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);  
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY,bc);
				if(rgbColor==greenRgb)
					continue;

				//System.out.println(x+" "+y+" "+tot);    			

				zb.set(xi,yi,zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),false,tot);
				
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
			
				if(!zb.isToUpdate(yi,tot) || isStencilBuffer){
					
					//z-fail stencil buffer with bias
					if(isStencilBuffer){
						
	
						if(!zb.isToUpdate(yi+4,tot))
							stencilBuffer(tot,isFacing);
					}
					continue;
				}	


				double zi=((1-l)*i_pstart_p_y*pstart.p_z+l*i_end_p_y*pend.p_z)*yi;
				double xi=((1-l)*i_pstart_p_y*pstart.p_x+l*i_end_p_y*pend.p_x)*yi;

                double texture_x=((1-l)*i_pstart_p_y*pstart.texture_x+l*i_end_p_y*pend.texture_x)*yi;
                double texture_y=((1-l)*i_pstart_p_y*pstart.texture_y+l*i_end_p_y*pend.texture_y)*yi;


				if(texture!=null)
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY,bc);
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);   
				if(rgbColor==greenRgb)
					continue;

				//System.out.println(x+" "+y+" "+tot);


			
				zb.set(xi,yi,zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),false,tot);
	
			}


		}	




	}
	
	public void decomposeLine( double x1,
			double y1,double z1,double x2,double y2,double z2,ZBuffer zb,int rgbColor) { 

		int xx1=(int)calculPerspX(x1,y1,z1);
		int yy1=(int)calculPerspY(x1,y1,z1);

		int xx2=(int)calculPerspX(x2,y2,z2);
		int yy2=(int)calculPerspY(x2,y2,z2);



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

					double yi=y2*l+y1*(1-l);

					int tot=WIDTH*yy+xx;

					if(!zb.isToUpdate(yi,tot))
						continue;			

					zb.set(xx,yi,yy,rgbColor,false,tot);
				}
			else
				for (int yy = yy2; yy <= yy1; yy++) {

					double l=-(yy-yy2)*i_yy;

					int xx=(int) (xx1*l+xx2*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

	    			double yi=y2*l+y1*(1-l);

					int tot=WIDTH*yy+xx;


					if(!zb.isToUpdate(yi,tot))
						continue;			


					zb.set(xx,yi,yy,rgbColor,false,tot);
				}

		}
		else if(xx1!=xx2){
			
			double i_xx=1.0/(xx2-xx1);

			if(xx2>xx1)
				for (int xx = xx1; xx <= xx2; xx++) {

					double l=(xx-xx1)*i_xx;
					double yi=y2*l+y1*(1-l);

					int yy=(int) (yy2*l+yy1*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					int tot=WIDTH*yy+xx;
 
			
					if(!zb.isToUpdate(yi,tot))
						continue;


					zb.set(xx,yi,yy,rgbColor,false,tot);
				}
			else
				for (int xx = xx2; xx <= xx1; xx++) {

					double l=-(xx-xx2)*i_xx;
					double yi=y2*l+y1*(1-l);
 
					int yy=(int) (yy1*l+yy2*(1-l));
					
					if(xx<0 || yy<0 )
	    				continue;
	    			
	    			if(xx>=WIDTH || yy>= HEIGHT)
	    				break;

					int tot=WIDTH*yy+xx;

					if(!zb.isToUpdate(yi,tot))
						continue;


					zb.set(xx,yi,yy,rgbColor,false,tot);
				}

		}
		else {
			
			if(xx1<0 || yy1<0 )
				return;
			
			if(xx1>=WIDTH || yy1>= HEIGHT)
				return;

			int tot=WIDTH*yy1+xx1;


			if(!zb.isToUpdate(y1,tot))
				return;


			zb.set(xx1,y1,yy1,rgbColor,false,tot);

		}


	}
	


	public void stencilBuffer(int tot, boolean isFacing) {
		
		
	}

	public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs) {

		//double factor=(lightIntensity*(0.25+0.5*Math.exp(-yi*0.001)+0.25*cosin));
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
	
	public void calculateShadowCosines(DrawObject dro) {
	
		
		CubicMesh cm= (CubicMesh)dro.getMesh();
		
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){
			
			LineData ld=cm.polygonData.elementAt(i);
			
			Polygon3D polygon = PolygonMesh.getBodyPolygon(cm.points,ld);
			
			Point3D centroid = Polygon3D.findCentroid(polygon);
			
			Point3D normal=(Polygon3D.findNormal(polygon)).calculateVersor();
			
			ld.setShadowCosin(Point3D.calculateCosin(lightPoint.position.substract(centroid),normal));
			
		}
		
	}



	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer){

		Point3D origin=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		decomposeClippedPolygonIntoZBuffer(p3d, color, texture,zbuffer,null,null,origin,0,0);

	}

	public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,
			Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY){		

		Point3D normal=Polygon3D.findNormal(p3d);

		if(!isStencilBuffer && !isFacing(p3d,normal,observerPoint))
			return;

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
			
			Polygon3D clippedPolygon=Polygon3D.clipPolygon3DInY(triangles[i],(int) (SCREEN_DISTANCE*2.0/3.0));

			if(clippedPolygon.npoints==0)
				continue;
			
			Polygon3D[] clippedTriangles = Polygon3D.divideIntoTriangles(clippedPolygon);
			
			for (int j = 0; j < clippedTriangles.length; j++) {
				
				decomposeTriangleIntoZBufferEdgeWalking( clippedTriangles[j],color.getRGB(), texture,zbuffer, xDirection,yDirection,origin, deltaX, deltaY,bc);
				
			}

		

		}

	}
	
	public void drawObjects(DrawObject[] drawObjects,Area totalVisibleField,ZBuffer zbuffer){


        Rectangle rect = totalVisibleField.getBounds();
		for(int i=0;i<drawObjects.length;i++){

			DrawObject dro=drawObjects[i];
     		drawPolygonMesh(dro, rect, zbuffer);
		}		

	}	

	private void drawPolygonMesh(DrawObject dro,Rectangle rect,ZBuffer zbuffer) {
	
		//if(!totalVisibleField.contains(dro.x-POSX,VIEW_DIRECTION*(dro.y-POSY)))
	
		if(rect.y+rect.height<dro.y-POSY)
			return;
		
		PolygonMesh mesh = dro.getMesh();
	    
		decomposeCubicMesh((CubicMesh) mesh,CarFrame.objectTextures[dro.getIndex()],zbuffer);
		
	}
	
	public void decomposeCubicMesh(CubicMesh cm, Texture texture,ZBuffer zBuffer){
		
		
		
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
			
	
			
			int due=(int)(255-i%15);			
			Color col=new Color(due,0,0);
			
			LineData ld=cm.polygonData.elementAt(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld);
			polRotate.setShadowCosin(ld.getShadowCosin());
			
		
			int face=cm.boxFaces[i];
			
			buildTransformedPolygon(polRotate);
			


			
			
			
			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,texture,zBuffer);
			
          
				
		}
		
	
	}
	
	public void decomposeCubiMeshPolygon(
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
		
		
		
		decomposeClippedPolygonIntoZBuffer(polRotate,col,texture,zBuffer,xDirection,yDirection,rotateOrigin,deltaTexture+deltaWidth,deltaHeight);
	}

	public static boolean isFacing(Polygon3D pol,Point3D normal,Point3D observer){



		//Point3D p0=new Point3D(pol.xpoints[0],pol.ypoints[0],pol.zpoints[0]);
		Point3D p0=Polygon3D.findCentroid(pol);

		Point3D vectorObs=observer.substract(p0);

		double cosin=Point3D.calculateCosin(normal,vectorObs);

		return cosin>=0;
	}
	
	


	public void decomposeObject2D(Texture image,double x,double y,double z,double dx,double dy,double dz,ZBuffer zbuffer){



		double drx=dx/image.getWidth();
		double drz=dz/image.getHeight();
		double deltaz=z+dz;
		int h=image.getHeight();
		int w=image.getWidth();


		for(int i=0;i<w;i++)
			for(int j=0;j<h;j++){


				int argb = image.getRGB(i, j);
				if(greenRgb==argb)
					continue;
				int alpha=0xff & (argb>>24);


				double xs=x+i*drx;
				double ys=y;
				double zs=deltaz-j*drz;

				decomposePointIntoZBuffer(xs,ys,zs,0,argb,zbuffer);


			}


	}
	
	/*public void drawObject2D(DrawObject dro,Area totalVisibleField,ZBuffer[] zbuffer){


	if(!totalVisibleField.contains(dro.x-POSX,VIEW_DIRECTION*(dro.y-POSY)))
		return;

	int index=dro.getIndex();

	if(index<0 || index> CarFrame.objects.length)
		return;

	decomposeObject2D(CarFrame.objects[index],dro.x-POSX,VIEW_DIRECTION*(dro.y-POSY),dro.z+MOVZ,dro.dx,dro.dy,dro.dz, zbuffer);

}

public void drawObject3D(DrawObject dro,Area totalVisibleField,ZBuffer[] zbuffer){

	int index=dro.getIndex();

	if(index<0 || index> CarFrame.objects.length)
		return;

	Rectangle rect = totalVisibleField.getBounds();
	
	for(int j=0;j<dro.getPolygons().size();j++){

		Polygon3D polig=(Polygon3D) dro.getPolygons().elementAt(j);
		Polygon3D translatedPolygon=calculateTranslated3D(polig);

        if(!isVisible(rect,translatedPolygon))
        	continue;

		decomposeClippedPolygonIntoZBuffer(translatedPolygon,Color.RED,CarFrame.objects[index],zbuffer);		
	
	}	


}*/

	private void decomposePointIntoZBuffer(double xs, double ys, double zs,
			double ds, int rgbColor,ZBuffer zbuffer) {

		if(ys<=SCREEN_DISTANCE) return;

		//double gamma=SCREEN_DISTANCE/(SCREEN_DISTANCE+ys);

		double xd=calculPerspX(xs,ys,zs);
		double yd=calculPerspY(xs,ys,zs);

		if(xd<0 || xd>=WIDTH || yd<0 || yd>= HEIGHT)
			return;

		int x=(int) xd;
		int y=(int) yd;

		//double i_ys=1.0/ys;

		int tot=y*WIDTH+x;	

		zbuffer.update(xs,ys,zs,rgbColor,tot);




	}

	public void buildRectanglePolygons(Vector polygons, double x, double y,
			double z, double dx, double dy, double dz) {



		Polygon3D base=buildRectangleBase3D(x,y,z,dx,dy,dz);
		polygons.add(base);		
		Polygon3D ubase=base.buildTranslatedPolygon(0,0,dz);
		polygons.add(ubase);	
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,0));
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,1));
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,2));
		polygons.add(Polygon3D.buildPrismIFace(ubase,base,3));
	}


	public Polygon3D buildRectangleBase3D(double x,double y,double z,double dx,double dy,double dz){

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
	
	public Polygon3D buildTransformedPolygon3D(LineData ld,Point3D[] points) { 



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];
		
		int[] cxtr=new int[size];
		int[] cytr=new int[size];
		
		for(int i=0;i<size;i++){


			LineDataVertex ldv=(LineDataVertex) ld.getItem(i);
			int num=ldv.getVertex_index();

			Point4D p=(Point4D) points[num];


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
		
        return p3dr;

	}
	
	public Point3D buildTransformedVersor(Point3D point) {

		Point3D newPoint=new Point3D();

	
			
		double x=point.x;
		double y=point.y;
		double z=point.z;
			
		newPoint.x= (viewDirectionCos*x+viewDirectionSin*y);
		newPoint.y= (viewDirectionCos*y-viewDirectionSin*x);	
		newPoint.z=z;

		return newPoint;
	}
	
	public Point3D buildTransformedPoint(Point3D point) {

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
	
	
	public void buildTransformedPolygon(Polygon3D base) {

		

		for(int i=0;i<base.npoints;i++){

			if(VIEW_TYPE==FRONT_VIEW){
				
				double x=base.xpoints[i]-POSX;
				double y=base.ypoints[i]-POSY;
				
				base.xpoints[i]=(int) (viewDirectionCos*x+viewDirectionSin*y);
				base.ypoints[i]=(int) (viewDirectionCos*y-viewDirectionSin*x);	
				
				//base.xpoints[i]=(int) (viewDirectionCos*(x-observerPoint.x)+viewDirectionSin*(y-observerPoint.y)+observerPoint.x);
				//base.ypoints[i]=(int) (viewDirectionCos*(y-observerPoint.y)-viewDirectionSin*(x-observerPoint.x)+observerPoint.y);	
				
				base.zpoints[i]=base.zpoints[i]+MOVZ;
			}
			else{
				
				double x=base.xpoints[i]-POSX;
				double y=base.ypoints[i]-POSY;
				
				base.xpoints[i]=-(int) (viewDirectionCos*x+viewDirectionSin*y);
				base.ypoints[i]=2*SCREEN_DISTANCE-(int) (viewDirectionCos*y-viewDirectionSin*x);	
				
				base.zpoints[i]=base.zpoints[i]+MOVZ;
			}
		}

	
	}
	
	public void rotate(){
		// TODO Auto-generated method stub
	}
	
	
	public void rotatePolygon(DrawObject dro,double xo,double yo,double cosTur,double sinTur){
		
		int size=dro.getPolygons().size();
		for(int j=0;j<size;j++){
			
			Polygon3D polig=(Polygon3D) dro.getPolygons().elementAt(j);
			rotatePolygon( polig, xo, yo, cosTur, sinTur);
		}
		
		
	}

	public void rotatePolygon(Polygon3D polig,double xo,double yo,double cosTur,double sinTur){



		for(int i=0;i<polig.npoints;i++){

			int x=polig.xpoints[i];
			int y=polig.ypoints[i];	

			polig.xpoints[i]=(int)(xo+(x-xo)*cosTur-(y-yo)*sinTur);
			polig.ypoints[i]=(int)(yo+(y-yo)*cosTur+(x-xo)*sinTur);

		}


	}
	
	public void rotateMesh(CubicMesh mesh, double xo, double yo, double ct,
			double st) {
		
		for(int i=0;i<mesh.points.length;i++){
			
			rotatePoint(mesh.points[i],xo,yo,ct,st);

		}
		int nsize=mesh.normals.size();
		for(int i=0;i<nsize;i++){
			
			
			Point3D normal = mesh.normals.elementAt(i);
			rotatePoint(normal,0,0,ct,st);

		}
		
		rotatePoint(mesh.point000,xo,yo,ct,st);
		rotatePoint(mesh.point100,xo,yo,ct,st);
		rotatePoint(mesh.point010,xo,yo,ct,st);
		rotatePoint(mesh.point001,xo,yo,ct,st);
		rotatePoint(mesh.point110,xo,yo,ct,st);
		rotatePoint(mesh.point011,xo,yo,ct,st);
		rotatePoint(mesh.point101,xo,yo,ct,st);
		rotatePoint(mesh.point111,xo,yo,ct,st);
		
	}
	
	public void rotatePoint(Point3D p,double xo,double yo,double cosTur,double sinTur){


			double x=p.x;
			double y=p.y;	

			p.x=xo+(x-xo)*cosTur-(y-yo)*sinTur;
			p.y=yo+(y-yo)*cosTur+(x-xo)*sinTur;

	

	}

	public Polygon3D interpolateAreaToPolygon3D(Area a, Polygon3D p3d){




		Vector points=new Vector();

		PathIterator pathIter = a.getPathIterator(null);
		//if(isDebug)System.out.println(p3d);
		while(!pathIter.isDone()){

			double[] coords = new double[6];

			int type=pathIter.currentSegment(coords);

			double px= coords[0];
			double py= coords[1];	



			if(type==PathIterator.SEG_MOVETO || type==PathIterator.SEG_LINETO)
			{		//here i could use the 3d polygon to interpolate the z value ?


				double pz=interpolate(px,py,p3d);
				points.add(new Point4D(px,py,pz));

			}
			pathIter.next();
		}

		Polygon3D draw_p=new Polygon3D(points);
		return draw_p;

	}
	
	protected double interpolate(double px, double py, Polygon3D p3d) {
       
		/*Plain plain=Plain.calculatePlain(p3d);
		return plain.calculateZ(px,py);

		 */
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

	public static int findBoxFace(Point3D normal,Point3D versusx,Point3D versusy,Point3D versusz) {

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

	public static int findBoxFace(Polygon3D pol) {

		Point3D normal = Polygon3D.findNormal(pol);
		return findBoxFace(normal);

	}
	
	public static int getFace(LineData ld,Vector points){
		
		int n=ld.size();
		
		Point3D p0=(Point3D)points.elementAt(ld.getIndex((n+0-1)%n));
		Point3D p1=(Point3D)points.elementAt(ld.getIndex(0));
		Point3D p2=(Point3D)points.elementAt(ld.getIndex((1+0)%n));

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

	public static int findBoxFace(Point3D normal ) {

		return findBoxFace(normal,new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
	}
	

	public int getMOVZ() {
		return MOVZ;
	}

	public void setPOSX(int posx) {
		POSX = posx;
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
