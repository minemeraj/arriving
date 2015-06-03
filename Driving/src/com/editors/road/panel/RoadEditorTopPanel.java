package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Vector;

import com.BarycentricCoordinates;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;
import com.Texture;
import com.ZBuffer;
import com.editors.EditorData;
import com.editors.ValuePair;
import com.editors.road.RoadEditor;
import com.main.Road;

public class RoadEditorTopPanel extends RoadEditorPanel {
	

	
	int MOVX=-50;
	int MOVY=100;

	int dx=2;
	int dy=2;
	
	

	
	public RoadEditorTopPanel(RoadEditor editor, int cENTER_WIDTH,int cENTER_HEIGHT) {
		
		super(editor, cENTER_WIDTH,cENTER_HEIGHT);
		intialize();
	}
	

	private void intialize() {
		
		selectionColor=new Color(255,0,0,127);
		EditorData.initialize();
		
	}


	public void drawRoad(PolygonMesh[] meshes, Vector drawObjects,Vector splines,ZBuffer landscapeZbuffer,
			Graphics2D graph) {
		
		displayTerrain(landscapeZbuffer,meshes);
		displaySPLines(landscapeZbuffer,splines);
		//displayRoad(landscapeZbuffer,meshes,1);
		if(!isHide_objects())
			displayObjects(landscapeZbuffer,drawObjects);
	
	}
	
	



	private void displayTerrain(ZBuffer landscapeZbuffer,PolygonMesh[] meshes) {

	
		PolygonMesh mesh=meshes[RoadEditor.TERRAIN_INDEX];
		
		if(mesh.points==null)
			return;
		
		int lsize=mesh.polygonData.size();
		

		for(int j=0;j<lsize;j++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(j);

	
			Texture texture = EditorData.worldTextures[ld.getTexture_index()];
			drawPolygon(ld,mesh.points,landscapeZbuffer,texture,RoadEditor.TERRAIN_INDEX);

		} 

		//mark row angles
		
		int size=mesh.points.length;
		for(int j=0;j<size;j++){


		    Point4D p=(Point4D) mesh.points[j];

				int xo=convertX(p);
				int yo=convertY(p);
				
				int rgbColor=Color.white.getRGB();

				if(p.isSelected()){
					rgbColor=Color.RED.getRGB();

				}	
	
	
			    fillOval(landscapeZbuffer,xo,yo,2,2,rgbColor);

			}
		
		
		drawCurrentRect(landscapeZbuffer);
	}
	
	private void displaySPLines(ZBuffer landscapeZbuffer, Vector splines) {
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
			
			Vector meshes = sp.getMeshes();
			
			for (int j = 0; j < meshes.size(); j++) {//if(j!=4)continue;
				
				PolygonMesh mesh = (PolygonMesh) meshes.elementAt(j);
				            
				drawPolygon(mesh,landscapeZbuffer,1);
                 
				
			}



			
		}
		
		
		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.elementAt(i);
			
			for (int k = 0; k < sp.nodes.size(); k++) {
				
				SPNode node = (SPNode) sp.nodes.elementAt(k);
				
				PolygonMesh pm=node.getRing();
				
				Texture texture=EditorData.whiteTexture;
				
				if(node.isSelected())
					texture=EditorData.redTexture;
				
				for(int l=0;l<pm.polygonData.size();l++){
					
					
					LineData ld=(LineData) pm.polygonData.elementAt(l);
					
				
											
					drawPolygon(ld,pm.points,landscapeZbuffer,texture,1);

				} 
				
			
			}


			
		}
		
		
	}
	


	public void drawCurrentRect(ZBuffer landscapeZbuffer) {
		
		if(!editor.isDrawCurrentRect())
			return;
		//System.out.println(isDrawCurrentRect);
		
		Rectangle currentRect = editor.currentRect;
		
		int x0=Math.min(currentRect.x,currentRect.x+currentRect.width);
		int x1=Math.max(currentRect.x,currentRect.x+currentRect.width);
		int y0=Math.min(currentRect.y,currentRect.y+currentRect.height);
		int y1=Math.max(currentRect.y,currentRect.y+currentRect.height);
		
		int rgbColor=Color.WHITE.getRGB();
	
		drawLine(landscapeZbuffer,x0,y0,x1,y0,rgbColor);
		drawLine(landscapeZbuffer,x0,y1,x1,y1,rgbColor);
		drawLine(landscapeZbuffer,x0,y0,x0,y1,rgbColor);
		drawLine(landscapeZbuffer,x1,y0,x1,y1,rgbColor);
	}
	
	private void drawPolygon(ZBuffer landscapeZbuffer, Polygon pTot, int rgbColor) {
		
		
		
		for (int n = 0; n < pTot.npoints; n++) {
			
			int i=pTot.xpoints[n];
			int j=pTot.ypoints[n];
			
			int ii=pTot.xpoints[(n+1)%pTot.npoints];
			int jj=pTot.ypoints[(n+1)%pTot.npoints];
			
			drawLine(landscapeZbuffer,i,j,ii,jj,rgbColor);
			
			
		
		}
		
	}
	
	private void drawLine(ZBuffer landscapeZbuffer, int i, int j, int ii,
			int jj, int rgbColor) {

		int mini=i;
		int maxi=ii;

		int minj=j;
		int maxj=jj;

		if(ii<i){

			mini=ii;
			maxi=i;

		}

		if(jj<j){

			minj=jj;
			maxj=j;

		}

		if(j==jj){			

			for (int k = mini; k < maxi; k++) {

				if(k>=0 && k<WIDTH && j>=0 && j<HEIGHT){

					int tot=k+j*WIDTH;
					landscapeZbuffer.setRgbColor(rgbColor,tot);
				}
			}
		}
		else if(i==ii){

			for (int k = minj; k < maxj; k++) {
				if(i>=0 && i<WIDTH && k>=0 && k<HEIGHT){

					int tot=i+k*WIDTH;

					landscapeZbuffer.setRgbColor(rgbColor,tot);
				}
			}

		}
		else{

			//use coordinate with more points:
			if(Math.abs(ii-i)>Math.abs(jj-j)){

				double dy=(jj-j)*1.0/(ii-i);

				if(ii>i){
					for (int k = i; k < ii; k++) {

						int y=(int) (dy*(k-i)+j);

						if(k>=0 && k<WIDTH && y>=0 && y<HEIGHT){
							int tot=(k+y*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}	
				}
				else{
					for (int k = ii; k < i; k++) {

						int y=(int) (dy*(k-i)+j);

						if(k>=0 && k<WIDTH && y>=0 && y<HEIGHT){
							int tot=(k+y*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}			

				}


			}else{
				
				double dx=(ii-i)*1.0/(jj-j);

				if(jj>j){
					for (int q = j; q < jj; q++) {

						int x=(int) (dx*(q-j)+i);

						if(x>=0 && x<WIDTH && q>=0 && q<HEIGHT){
							int tot=(x+q*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}	
				}
				else{
					for (int q = jj; q < j; q++) {

						int x=(int) (dx*(q-j)+i);

						if(x>=0 && x<WIDTH && q>=0 && q<HEIGHT){
							int tot=(x+q*WIDTH);					
							landscapeZbuffer.setRgbColor(rgbColor,tot);
						}
					}			

				}

				
			}

		}

	}
	
	private void drawPolygon(PolygonMesh mesh, ZBuffer landscapeZbuffer,
			int rgbColor) {

		
		int lsize=mesh.polygonData.size();
		

		for(int k=0;k<lsize;k++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(k);
			Texture texture = EditorData.worldTextures[ld.getTexture_index()];
			
			drawPolygon(ld,mesh.points,landscapeZbuffer,texture,0);

		} 
		
	}
	
	
	

	
	private void drawPolygon(LineData ld,Point3D[] points,ZBuffer landscapeZbuffer,Texture texture,int indx) {


		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points[num];

			//bufGraphics.setColor(ZBuffer.fromHexToColor(is[i].getHexColor()));

			cx[i]=convertX(p);
			cy[i]=convertY(p);
			cz[i]=(int)p.z;


            /*
			if(indx==1){

				cz[i]+=Road.ROAD_THICKNESS;

			}*/

		}

		Polygon p_in=new Polygon(cx,cy,ld.size());
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty())
			return;

		Polygon3D p3d=new Polygon3D(size,cx,cy,cz);
		Polygon3D p3dr=PolygonMesh.getBodyPolygon(points,ld);

		//calculate texture angle

		
		Color selected=null;

		if(ld.isSelected()){

			if(indx<0 || indx==editor.ACTIVE_PANEL){
				selected=selectionColor;
			}
		}
		



		drawTexture(texture,p3d,p3dr,landscapeZbuffer,selected);



	}
	
	private void drawTexture(Texture texture,  Polygon3D p3d, Polygon3D p3dr,ZBuffer landscapeZbuffer, Color selected) {

		Point3D normal=Polygon3D.findNormal(p3dr);



		if(texture!=null){
			
			Rectangle rect = p3d.getBounds();
			
			
			
			int i0=rect.x;
			int i1=rect.x+rect.width;
					
			int j0=rect.y;
			int j1=rect.y+rect.height;

			//System.out.println(i0+" "+i1+" "+j0+" "+j1);
			
			
			Point3D p0r=new Point3D(p3dr.xpoints[0],p3dr.ypoints[0],p3dr.zpoints[0]);
			Point3D p1r=new Point3D(p3dr.xpoints[1],p3dr.ypoints[1],p3dr.zpoints[1]);
			Point3D pNr=new Point3D(p3dr.xpoints[p3dr.npoints-1],p3dr.ypoints[p3dr.npoints-1],p3dr.zpoints[p3dr.npoints-1]);

			Point3D xDirection = (p1r.substract(p0r)).calculateVersor();
			//Point3D yDirection= (pNr.substract(p0r)).calculateVersor();
			Point3D yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

	
			
			Polygon3D[] triangles=Polygon3D.divideIntoTriangles(p3dr);
			
			for (int i = 0; i < triangles.length; i++) {
				
				Polygon3D tria=triangles[i];
				
				BarycentricCoordinates bc=new BarycentricCoordinates(triangles[i]);
				
					
				int rgb=-1;
				
				if(selected!=null)
					rgb=selected.getRGB();
				
				decomposeTriangleIntoZBufferEdgeWalking(tria,rgb,texture,landscapeZbuffer, xDirection, yDirection, p0r, 0, 0,bc); 

				
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
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int selected,Texture texture,ZBuffer zb,  
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc) {

		int rgbColor=selected;
		
		rr=a2*(selected>>16 & mask);
		gg=a2*(selected>>8 & mask);
		bb=a2*(selected & mask);

		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
		
		//boolean isFacing=isFacing(p3d,normal,observerPoint);


	
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
		double z0=p0.z;

		double x1=(int)convertX(p1.x,p1.y,p1.z);
		double y1=(int)convertY(p1.x,p1.y,p1.z);
		double z1=p1.z;

		
		double x2=(int)convertX(p2.x,p2.y,p2.z);
		double y2=(int)convertY(p2.x,p2.y,p2.z);
		double z2=p2.z;
        //System.out.println(x0+" "+y0+", "+x1+" "+y1+", "+x2+" "+y2);
		
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
		
				if(!zb.isToUpdate(-zi,tot)){
				
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
				
				zb.set(xi,yi,zi,-zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),false,tot);
				
				
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
				
				
				if(!zb.isToUpdate(-zi,tot) ){
					
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


			
				zb.set(xi,yi,zi,-zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor),false,tot);
				
			}


		}	




	}

	
	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}
	
	private void fillOval(ZBuffer landscapeZbuffer, int cx, int cy, int dx,
			int dy, int rgbColor) {
		
		int x=cx-dx;		
		int xx=cx+dx;
		
		int y=cy-dy;
		int yy=cy+dy;
		
		double r=(dx+dy)/2.0+0.5;
		
		for (int i = x; i <= xx; i++) {
		
			for (int j = y; j <= yy; j++) {
				
				double d=Point3D.distance(cx,cy,0,i,j,0);
				
				if(d>r)
					continue;
				
				
				if(i>=0 && i<WIDTH && j>=0 && j<HEIGHT){
					
					int tot=i+j*WIDTH;
					landscapeZbuffer.setRgbColor(rgbColor,tot);
				}
				
			}
		
		}
	}

	
	
	
	private void displayObjects(ZBuffer landscapeZbuffer,Vector drawObjects) {

		Rectangle totalVisibleField=new Rectangle(0,0,WIDTH,HEIGHT);

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);

			int y=convertY(dro.x,dro.y,dro.z);
			int x=convertX(dro.x,dro.y,dro.z);

			int index=dro.getIndex();

			int dw=(int) (dro.dx/dx);
			int dh=(int) (dro.dy/dy);

			
			if(!totalVisibleField.intersects(new Rectangle(x,y-dh,dw,dh))){
				
				//System.out.println(totalVisibleField+" "+new Rectangle(x,y,dw,dh));
				
				continue;
			}
			
			
		
			
			drawObject(landscapeZbuffer,dro);

		}	
	}
	
	private void drawObject(ZBuffer landscapeZbuffer, DrawObject dro) {

		int[] cx=new int[4];
		int[] cy=new int[4];
		
		int versus=1;
		if(!DrawObject.IS_3D)
			versus=-1;

		cx[0]=convertX(dro.x,dro.y,dro.z);
		cy[0]=convertY(dro.x,dro.y,dro.z);
		cx[1]=convertX(dro.x,dro.y,dro.z);
		cy[1]=convertY(dro.x,dro.y+versus*dro.dy,dro.z);
		cx[2]=convertX(dro.x+dro.dx,dro.y,dro.z);
		cy[2]=convertY(dro.x,dro.y+versus*dro.dy,dro.z);
		cx[3]=convertX(dro.x+dro.dx,dro.y,dro.z);
		cy[3]=convertY(dro.x,dro.y,dro.z);

		Polygon p_in=new Polygon(cx,cy,4);
		
		Point3D center=Polygon3D.findCentroid(p_in);
		Polygon3D.rotate(p_in,center,dro.rotation_angle);
	

		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty()){
			
			return;
		}	

		Polygon pTot=Polygon3D.fromAreaToPolygon2D(partialArea);
		//if(cy[0]<0 || cy[0]>HEIGHT || cx[0]<0 || cx[0]>WIDTH) return;
		
		
		Color pColor=ZBuffer.fromHexToColor(dro.getHexColor());
		
		
		if(dro.isSelected())
		{
			pColor=Color.RED;
			
		}
		int rgbColor = pColor.getRGB();
		drawPolygon(landscapeZbuffer,pTot,rgbColor);
				
		drawTextImage(landscapeZbuffer,RoadEditor.objectIndexes[dro.getIndex()]
						,cx[0]-5,cy[0]-5,editor.indexWidth,editor.indexHeight,Color.BLACK,pColor);		
		
		

	}
	

	public int convertX(double i,double j,double k) {

		return (int) (i/dx-MOVX);
	}
	public int convertY(double i,double j,double k) {

		return (int) (HEIGHT-(j/dy+MOVY));
	}

	public int invertX(int i) {

		return (i+MOVX)*dx;
	}
	public int invertY(int j) {

		return dy*(HEIGHT-j-MOVY);
	}
	
	
	public void zoom(int i) {
		
		
		
		double alfa=1.0;
		if(i>0){
			alfa=0.5;
			
			if(dx==1 || dy==1)
				return;
		}
		else {
			alfa=2.0;
		      	
		}
			
				
		dx=(int) (dx*alfa);
		dy=(int) (dy*alfa);
		
		MOVX+=(int) ((WIDTH/2+MOVX)*(1.0/alfa-1.0));
		MOVY+=(int) ((-HEIGHT/2+MOVY)*(1.0/alfa-1.0));
	}
	
	public void up(){
		MOVY=MOVY-10;
		

	}

	public void down(){
		MOVY=MOVY+10;
		

	}
	

	public void mouseDown() {
		down();
	}
	
	@Override
	public void mouseUp() {
		up();
	}
	
	public void left(){
		MOVX=MOVX-10;
	}

	public void right(){
		MOVX=MOVX+10;	

	}
	

	public void drawTextImage(ZBuffer landscapeZbuffer,
			Texture textImage, int x, int y, int dx,
			int dy, Color transparentColor, Color fixedColor) {

		double alfax=textImage.getWidth()*1.0/dx;
		double alfay=textImage.getHeight()*1.0/dy;

		for(int i=0;i<dx;i++)
			for(int j=0;j<dy;j++){

				if(i+x<0 || i+x>=WIDTH || j+y<0 || j+y>=HEIGHT)
					continue;

				int rgbColor=textImage.getRGB((int)(alfax*i),(int)(alfay*j));

				if(transparentColor!=null && transparentColor.getRGB()==rgbColor)
					continue;

				if(fixedColor!=null){


					rgbColor=fixedColor.getRGB();
				}

				int tot=(int)(i+x+(j+y)*WIDTH);

				landscapeZbuffer.setRgbColor(rgbColor,tot);
			}

	}
	
	public boolean selectPointsWithRectangle(PolygonMesh mesh) {
		
	
		if(mesh.points==null)
			return false;


		int x0=Math.min(editor.currentRect.x,editor.currentRect.x+editor.currentRect.width);
		int x1=Math.max(editor.currentRect.x,editor.currentRect.x+editor.currentRect.width);
		int y0=Math.min(editor.currentRect.y,editor.currentRect.y+editor.currentRect.height);
		int y1=Math.max(editor.currentRect.y,editor.currentRect.y+editor.currentRect.height);

		if(!editor.checkCoordinatesx[editor.ACTIVE_PANEL].isSelected())
			editor.coordinatesx[editor.ACTIVE_PANEL].setText("");
		if(!editor.checkCoordinatesy[editor.ACTIVE_PANEL].isSelected())
			editor.coordinatesy[editor.ACTIVE_PANEL].setText("");
		if(!editor.checkCoordinatesz[editor.ACTIVE_PANEL].isSelected())
			editor.coordinatesz[editor.ACTIVE_PANEL].setText("");

		//select point from road
		boolean found=false;
		

		for(int j=0;j<mesh.points.length;j++){


			Point4D p=(Point4D) mesh.points[j];

			int xo=convertX(p);
			int yo=convertY(p);


			if(xo>=x0 && xo<=x1 && yo>=y0 && yo<=y1  ){

				p.setSelected(true);
				found=true;


			}
			else if(!editor.checkMultiplePointsSelection[editor.ACTIVE_PANEL].isSelected())
				p.setSelected(false);


		}
		
		return found;
		

		

	}
	


	public boolean selectSPNodes(int x, int y, Vector splines) {
		
       //System.out.println(x+" "+y);
		
		boolean isToselect=true;
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.elementAt(i);
			
			Vector nodes=spline.nodes;
			
			for(int j=0;j<nodes.size();j++){
			
				SPNode spnode = (SPNode) nodes.elementAt(j);
				PolygonMesh circle = spnode.getCircle();
				
				for (int k = 0; k < circle.polygonData.size(); k++) {
					
					LineData ld=(LineData) circle.polygonData.elementAt(k);
				    Polygon3D pol=buildPolygon(ld,circle.points,false);
				   // System.out.println(k+" "+pol);
				    if(pol.contains(x,y)){
				    	
				    	if(isToselect){
				    		
				    		spnode.setSelected(true);
				    		break;
				    	}
				    	
				    }else{
				    	
				    	if(!editor.checkMultiplePointsSelection[editor.ACTIVE_PANEL].isSelected())
						   	spnode.setSelected(false);
				    }	
				}
				
			
		    
			}
	
		}

		return false;
	}
	
    public boolean selectPolygons(int x, int y, PolygonMesh mesh) {
    	
    	Vector vec=selectPolygons(x,y,mesh,true);
    	
    	return vec!=null && vec.size()>0;
    	
    }
    
  
    public Vector getClickedPolygons(int x, int y, PolygonMesh mesh) {
    	
    	return selectPolygons(x,y,mesh,false);
    }


	public Vector selectPolygons(int x, int y, PolygonMesh mesh,boolean isToselect) {
		
		Vector ret=new Vector(); 
		
		if(mesh==null)
    		return ret;
	
	
		
		//select polygon
		int sizel=mesh.polygonData.size();

		for(int j=0;j<sizel;j++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(j);
		    Polygon3D pol=buildPolygon(ld,mesh.points,false);
		    
		    if(pol.contains(x,y)){
		    	
		    	if(isToselect){
		    	
			    	ld.setSelected(true);
			    	
					for(int k=0;k<editor.chooseTexture[editor.ACTIVE_PANEL].getItemCount();k++){
	
						ValuePair vp=(ValuePair) editor.chooseTexture[editor.ACTIVE_PANEL].getItemAt(k);
						if(vp.getId().equals(""+ld.getTexture_index())) 
							editor.chooseTexture[editor.ACTIVE_PANEL].setSelectedItem(vp);
					}
				
		    	}
		    	
		    	
		    	
		    	ret.add(ld);
		    	
		    }
			else if(!editor.checkMultiplePointsSelection[editor.ACTIVE_PANEL].isSelected())
				ld.setSelected(false);
			
		}
		
		return ret;
	}
	
	
    public void selectObjects(int x, int y, Vector drawObjects) {
    	
    	Vector vec=selectObjects(x,y,drawObjects,true);
    	
       	
    }

	public Vector selectObjects(int x, int y, Vector drawObjects,boolean toSelect) {

		Vector ret=new Vector();

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
			if(!editor.checkMultipleObjectsSelection.isSelected())
				dro.setSelected(false);


			int[] cx=new int[4];
			int[] cy=new int[4];

			int versus=1;
			if(!DrawObject.IS_3D)
				versus=-1;

			cx[0]=convertX(dro.x,dro.y,dro.z);
			cy[0]=convertY(dro.x,dro.y,dro.z);
			cx[1]=convertX(dro.x,dro.y,dro.z);
			cy[1]=convertY(dro.x,dro.y+versus*dro.dy,dro.z);
			cx[2]=convertX(dro.x+dro.dx,dro.y,dro.z);
			cy[2]=convertY(dro.x,dro.y+versus*dro.dy,dro.z);
			cx[3]=convertX(dro.x+dro.dx,dro.y,dro.z);
			cy[3]=convertY(dro.x,dro.y,dro.z);

			Polygon p_in=new Polygon(cx,cy,4);			
			Point3D center=Polygon3D.findCentroid(p_in);
			Polygon3D.rotate(p_in,center,dro.rotation_angle);

			if(p_in.contains(x,y))
			{
				dro.setSelected(true);
				editor.setObjectData(dro);
                ret.add(dro);

			
			}else if(!editor.checkMultipleObjectsSelection.isSelected()){
				
				dro.setSelected(false);
			}

		}
		
		return ret;
		
	}
	
	
	public Polygon3D buildPolygon(LineData ld,Point3D[] points, boolean isReal) {

		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points[num];


			if(isReal){

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				czr[i]=(int)(p.z);
			}
			else {
				
				cxr[i]=convertX(p);
				cyr[i]=convertY(p);
				czr[i]=(int)(p.z);
			}
			


		}


		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

        return p3dr;

	}
	
	public boolean selectPoints(int x, int y, PolygonMesh mesh, LineData polygon) {
	
		boolean found=false;
		
		for(int j=0;mesh.points!=null && j<mesh.points.length;j++){


		    Point4D p=(Point4D) mesh.points[j];

				int xo=convertX(p);
				int yo=convertY(p);

				Rectangle rect=new Rectangle(xo-5,yo-5,10,10);
				if(rect.contains(x,y)){

					if(!editor.checkCoordinatesx[editor.ACTIVE_PANEL].isSelected())
						editor.coordinatesx[editor.ACTIVE_PANEL].setText(p.x);
					if(!editor.checkCoordinatesy[editor.ACTIVE_PANEL].isSelected())
						editor.coordinatesy[editor.ACTIVE_PANEL].setText(p.y);
					if(!editor.checkCoordinatesz[editor.ACTIVE_PANEL].isSelected())
						editor.coordinatesz[editor.ACTIVE_PANEL].setText(p.z);
				
					found=true;
					p.setSelected(true);
					
					polygon.addIndex(j);

		

				}
				else if(!editor.checkMultiplePointsSelection[editor.ACTIVE_PANEL].isSelected())
					p.setSelected(false);
			

		}
		
		return found;
	}
	
	/**
	 * For the old Cardriving2D version
	 * 
	 * 
	 * @param landscapeZbuffer
	 * @param bufferedImage
	 * @param x
	 * @param y
	 * @param xDirection
	 * @param yDirection
	 * @param transparentColor
	 */
	private void drawImage(ZBuffer landscapeZbuffer,
			BufferedImage bufferedImage, int x, int y, Point3D xDirection, Point3D yDirection,Color transparentColor) {
		
		int dx=(int) Point3D.calculateNorm(xDirection);
		int dy=(int) Point3D.calculateNorm(yDirection);
		
		Point3D xVersor=xDirection.calculateVersor();
		Point3D yVersor=yDirection.calculateVersor();

		int w=bufferedImage.getWidth();
		int h=bufferedImage.getHeight();
		
		double alfax=w*1.0/dx;
		double alfay=h*1.0/dy;
		
	
		for(int i=0;i<dx;i++)
			for(int j=0;j<dy;j++){
				
				
				int ix=(int) (i*xVersor.x+j*yVersor.x);
				int jy=(int) (i*xVersor.y+j*yVersor.y);
				
				if(ix+x<0 || ix+x>=WIDTH || jy+y<0 || jy+y>=HEIGHT)
					continue;
              
				int rgbColor=bufferedImage.getRGB((int)(alfax*i),(int)(alfay*j));
				
				if(transparentColor!=null && transparentColor.getRGB()==rgbColor)
					continue;
		
				int tot=(int)(ix+x+(jy+y)*WIDTH);
				
				landscapeZbuffer.setRgbColor(rgbColor,tot);
			}
		
		
	}

	
}
