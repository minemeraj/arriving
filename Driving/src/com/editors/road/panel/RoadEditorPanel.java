package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import javax.swing.JPanel;

import com.BarycentricCoordinates;
import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.road.RoadEditor;

public class RoadEditorPanel extends JPanel {
	
	public int blackRgb= Color.BLACK.getRGB();
	
	public RoadEditor editor;
	
	int WIDTH=0;
	int HEIGHT=0;
	
	public Color selectionColor=null;
	
	boolean hide_objects=false;
	
	public int POSX=0;
	public int POSY=0;
	public int MOVZ=0;
	
	public double fi=0;
	public double sinf=Math.sin(fi);
	public double cosf=Math.cos(fi);
	
	public int mask = 0xFF;
	public double a1=0.6;
	public double a2=1.0-a1;
	
	public double rr=0;
	public double gg=0;
	public double bb=0;
	
	public int greenRgb= Color.GREEN.getRGB();

	public RoadEditorPanel(RoadEditor editor,
			int cENTER_WIDTH, int cENTER_HEIGHT) {
	
		this.HEIGHT=cENTER_HEIGHT;
		this.WIDTH=cENTER_WIDTH;
		this.editor=editor;
	}

	public void drawRoad(PolygonMesh[] meshes, Vector drawObjects,Vector splines,
			ZBuffer landscapeZbuffer,Graphics2D graph) {
		
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
	/*public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int selected,Texture texture,ZBuffer zbuffer, 
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc) {

		

		
		int rgbColor=selected;

		rr=a2*(selected>>16 & mask);
		gg=a2*(selected>>8 & mask);
		bb=a2*(selected & mask);
		
		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
	
		double cosin=p3d.getShadowCosin();
		
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);

		//System.out.println(p3d+" "+rgbColor);

		double x0=convertX(p0);
		double y0=convertY(p0);
		double z0=p0.z;


		double x1=convertX(p1);
		double y1=convertY(p1);
		double z1=p1.z;

		double x2=convertX(p2);
		double y2=convertY(p2);
		double z2=p2.z;

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
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(upP,midP,j);
			Point3D intersecte = foundPX_PY_PZ_TEXTURE_Intersection(upP,midP,j);
	
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

				double yi=pstart.p_y*(1-l)+l*pend.p_y;
				double xi=pstart.p_x*(1-l)+l*pend.p_x;
				double zi=pstart.p_z*(1-l)+l*pend.p_z;
						
				if(!zbuffer.isEmpty(tot) && zbuffer.getZ(tot)>zi){

					continue;
				}	

			
				
                //double texture_x=((1-l)*i_pstart_p_y*pstart.texture_x+l*i_end_p_y*pend.texture_x)*yi;
                //double texture_y=((1-l)*i_pstart_p_y*pstart.texture_y+l*i_end_p_y*pend.texture_y)*yi;

			

				if(texture!=null)
					//rgbColor=texture.getRGB((int)texture_x,(int) texture_y);  
					rgbColor=pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY,null);
				if(rgbColor==blackRgb)
					continue;

				//System.out.println(x+" "+y+" "+tot);   
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}
				

				zbuffer.set(xi,zi,yi,rgbColor,false,tot);
				
			}


		}
		//LOWER TRIANGLE
		
		j0=lowP.y>0?(int)lowP.y:0;
		j1=midP.y<HEIGHT?(int)midP.y:HEIGHT;
		
		for(int j=j0;j<j1;j++){
	

			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(lowP,midP,j);
			
			Point3D insersecte = foundPX_PY_PZ_TEXTURE_Intersection(lowP,midP,j);
			
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

			int i0=start>0?start:0;
			
			
			for(int i=i0;i<end;i++){
	
				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i;

				double l=(i-start)*inverse;

				double yi=pstart.p_y*(1-l)+l*pend.p_y;
				double xi=pstart.p_x*(1-l)+l*pend.p_x;
				double zi=pstart.p_z*(1-l)+l*pend.p_z;
		
				if(!zbuffer.isEmpty(tot) && zbuffer.getZ(tot)>zi){

					continue;
				}	


				//double zi=((1-l)*i_pstart_p_y*pstart.p_z+l*i_end_p_y*pend.p_z)*yi;
				//double xi=((1-l)*i_pstart_p_y*pstart.p_x+l*i_end_p_y*pend.p_x)*yi;

                //double texture_x=((1-l)*i_pstart_p_y*pstart.texture_x+l*i_end_p_y*pend.texture_x)*yi;
                //double texture_y=((1-l)*i_pstart_p_y*pstart.texture_y+l*i_end_p_y*pend.texture_y)*yi;


				if(texture!=null)
					rgbColor=pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY,null);
					//rgbColor=texture.getRGB((int)texture_x,(int) texture_y);   
				if(rgbColor==blackRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);


			
				zbuffer.set(xi,zi,yi,rgbColor,false,tot);
	
			}


		}	




	}*/
	
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
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int selected,Texture texture,ZBuffer zb,  
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc) {

		int rgbColor=selected;
		
		rr=a2*(selected>>16 & mask);
		gg=a2*(selected>>8 & mask);
		bb=a2*(selected & mask);

		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
		
		//boolean isFacing=isFacing(p3d,normal,observerPoint);

		int level=p3d.getLevel();
	
		double cosin=calculateCosin(p3d);
		
		
		Point3D po0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D po1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D po2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);
	
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);

		p0.rotate(POSX,POSY,cosf,sinf);
		p1.rotate(POSX,POSY,cosf,sinf);
		p2.rotate(POSX,POSY,cosf,sinf);

		double x0=(int)convertX(p0.x,p0.y,p0.z);
		double y0=(int)convertY(p0.x,p0.y,p0.z);
		double z0=p0.y;

		double x1=(int)convertX(p1.x,p1.y,p1.z);
		double y1=(int)convertY(p1.x,p1.y,p1.z);
		double z1=p1.y;

		
		double x2=(int)convertX(p2.x,p2.y,p2.z);
		double y2=(int)convertY(p2.x,p2.y,p2.z);
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
			
			Point3D p=bc.getBarycentricCoordinates(new Point3D(po0.x,po0.y,po0.z));
			double x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			double y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[0].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(po1.x,po1.y,po1.z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);	
			points[1].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(po2.x,po2.y,po2.z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[2].setTexurePositions(x,texture.getHeight()-y);
				
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
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(upP,midP,j);
			Point3D intersecte = foundPX_PY_PZ_TEXTURE_Intersection(upP,midP,j);
	
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,intersecte.p_z,intersecte.p_x,intersecte.p_y,intersecte.p_z,intersecte.texture_x,intersecte.texture_y);
			
			
			//pstart.p_y=pstart.p_x*projectionNormal.x+pstart.p_y*projectionNormal.y+pstart.p_z*projectionNormal.z;
			//pend.p_y=pend.p_x*projectionNormal.x+pend.p_y*projectionNormal.y+pend.p_z*projectionNormal.z;

			if(pstart.x>pend.x){

				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;



			double inverse=1.0/(end-start);


			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){

				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i;

				double l=(i-start)*inverse;

				double yi=((1-l)*pstart.p_y+l*pend.p_y);
				double zi=((1-l)*pstart.p_z+l*pend.p_z);
				double xi=((1-l)*pstart.p_x+l*pend.p_x);  
		
				if(!zb.isToUpdate(yi,zi,tot,level)){
				
					continue;
				}	

			
				
                double texture_x=(1-l)*pstart.texture_x+l*pend.texture_x;
                double texture_y=(1-l)*pstart.texture_y+l*pend.texture_y;

			

				if(texture!=null)
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);  
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY);
				if(rgbColor==greenRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);    	
				
				zb.set(xi,yi,zi,yi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),level,tot);
				
				
			}


		}
		//LOWER TRIANGLE
		j0=lowP.y>0?(int)lowP.y:0;
		j1=midP.y<HEIGHT?(int)midP.y:HEIGHT;
		
		for(int j=j0;j<j1;j++){
		
			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(lowP,midP,j);
			
			Point3D insersecte = foundPX_PY_PZ_TEXTURE_Intersection(lowP,midP,j);
			
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,insersecte.p_z,insersecte.p_x,insersecte.p_y,insersecte.p_z,insersecte.texture_x,insersecte.texture_y);


			//pstart.p_y=pstart.p_x*projectionNormal.x+pstart.p_y*projectionNormal.y+pstart.p_z*projectionNormal.z;
			//pend.p_y=pend.p_x*projectionNormal.x+pend.p_y*projectionNormal.y+pend.p_z*projectionNormal.z;

			
			if(pstart.x>pend.x){


				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;

			double inverse=1.0/(end-start);

			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){
				
				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i; 

				double l=(i-start)*inverse;

				double yi=((1-l)*pstart.p_y+l*pend.p_y);

				double zi=((1-l)*pstart.p_z+l*pend.p_z);
				double xi=((1-l)*pstart.p_x+l*pend.p_x);  
				
				
				if(!zb.isToUpdate(yi,zi,tot,level) ){
					
					continue;
				}	



                double texture_x=(1-l)*pstart.texture_x+l*pend.texture_x;
                double texture_y=(1-l)*pstart.texture_y+l*pend.texture_y;


				if(texture!=null)
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY);
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);   
				if(rgbColor==greenRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);


			
				zb.set(xi,yi,zi,yi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),level,tot);
				
			}


		}	




	}

	
	public static int  pickRGBColorFromTexture(
			Texture texture,double px,double py,double pz,
			Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc
			){
		
		
		int x=0;
		int y=0;
		
	
		
       if(origin!=null){
			

			 x=(int) Math.round(Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,xDirection))+deltaX;
			 y=(int) Math.round(Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,yDirection))+deltaY;
			 
			 
		}
		else
		{
			
			  x=(int) Math.round(Point3D.calculateDotProduct(px,py, pz,xDirection))+deltaX;
			  y=(int) Math.round(Point3D.calculateDotProduct(px,py, pz,yDirection))+deltaY;

		}	
		
		
		int w=texture.getWidth();
		int h=texture.getHeight();
		
		//border fixed condition
		/*if(x<0) x=0;
		if(x>=w) x=w-1;
		if(y<0) y=0;
		if(y>=h) y=h-1;*/
		
		//border periodic condition
		if(x<0) x=w-1+x%w;
		if(x>=w) x=x%w;
		if(y<0) y=h-1+y%h;
		if(y>=h) y=y%h;
		
	
		
		int argb= texture.getRGB(x, h-y-1);
		
		return argb;
		
	}
	
	public static Point3D foundPX_PY_PZ_TEXTURE_Intersection(Point3D pstart, Point3D pend,
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
	
	public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs) {

		return argbs;
	
	}
	
	public double calculateCosin(Polygon3D polReal) {
		
		return 1.0;
	}
	
	
	public int convertX(Point3D p){


		return convertX(p.x,p.y,p.z);
	}
	public int convertY(Point3D p){


		return convertY(p.x,p.y,p.z);
	}
	
	public int convertX(double i,double j,double k) {

		return (int) i;
	}
	public int convertY(double i,double j,double k) {

		return (int) j;
	}

	public int invertX(int i) {

		return (int) i;
	}
	public int invertY(int j) {

		return (int) j;
	}
	
	public void drawCurrentRect(ZBuffer landscapeZbuffer) {		
		
	}
	
	public void zoom(int i) {		
		
	}
	
	public void up(){	

	}

	public void down(){		

	}
	
	public void left(){
		
	}

	public void right(){		

	}
	
	public void mouseDown() {
		
	}


	public void mouseUp() {
		
	}
	
	public boolean selectPointsWithRectangle(PolygonMesh mesh) {
		
		return false;
	}
	

	public boolean selectPolygons(int x, int y,PolygonMesh mesh) {
		
		return false;
	}
	
	public Vector selectPolygons(int x, int y,PolygonMesh mesh,boolean toSelect) {		
		return null;			
	}

	public void selectObjects(int x, int y, Vector drawObjects) {
		
	}
	
	public Vector selectObjects(int x, int y, Vector drawObjects,boolean toSelect) {
		
		return null;
		
	}

	public boolean selectPoints(int x, int y, PolygonMesh mesh, LineData polygon) {
		return false;
	}
	

	public boolean selectSPNodes(int x, int y, Vector splines) {
		return false;
	}
	
	public Polygon3D builProjectedPolygon(Polygon3D p3d) {
		
		return null;
	}

	public boolean isHide_objects() {
		return hide_objects;
	}

	public void setHide_objects(boolean hide_objects) {
		this.hide_objects = hide_objects;
	}

	public Vector getClickedPolygons(int x, int y, PolygonMesh mesh) {
		return null;
	}

	public void rotate(int signum) {
		// TODO Auto-generated method stub
		
	}

	
	

}
