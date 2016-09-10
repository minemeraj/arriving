package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.editors.road.RoadEditor;
import com.main.Road;

public class RoadEditorTopPanel extends RoadEditorPanel {



	private int MOVX=-50;
	private int MOVY=100;

	private int deltax=2;
	private int deltay=2;

	private int minMovement=5;
	private Rectangle totAreaNounds;
	private Area totArea;


	public RoadEditorTopPanel(RoadEditor editor, int cENTER_WIDTH,int cENTER_HEIGHT) {

		super(editor, cENTER_WIDTH,cENTER_HEIGHT);
		initialize();
	}

	@Override
	public void initialize() {

		selectionColor=new Color(255,0,0,127);
		xMovement=2*minMovement;
		yMovement=2*minMovement;

		totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));
		totAreaNounds=totArea.getBounds();
	}

	@Override
	public void drawRoad(PolygonMesh[] meshes, 
			ArrayList<DrawObject> drawObjects,
			ArrayList<SPLine> splines,
			Point3D startPosition,
			ZBuffer landscapeZbuffer,
			Graphics2D graph) {


		displayTerrain(landscapeZbuffer,meshes);
		if(!isHide_splines()){
			displaySPLines(landscapeZbuffer,splines);
		}
		//displayRoad(landscapeZbuffer,meshes,1);
		if(!isHide_objects()){

			displayObjects(drawObjects,totArea,landscapeZbuffer);
		}
		displayStartPosition(landscapeZbuffer, startPosition);

	}

	public void displayTerrain(ZBuffer landscapeZbuffer,PolygonMesh[] meshes) {


		PolygonMesh mesh=meshes[RoadEditor.TERRAIN_INDEX];

		if(mesh.xpoints==null)
			return;

		int lsize=mesh.polygonData.size();


		for(int j=0;j<lsize;j++){


			LineData ld=(LineData) mesh.polygonData.get(j);


			Texture texture = EditorData.worldTextures[ld.getTexture_index()];
			drawPolygon(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,landscapeZbuffer,texture,RoadEditor.TERRAIN_INDEX,mesh.hashCode());

		} 

		//mark row angles

		int size=mesh.xpoints.length;
		//draw points until they're visible
		if(deltax<16){
			for(int j=0;j<size;j++){


				int xo=convertX(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);
				int yo=convertY(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

				int rgbColor=Color.white.getRGB();

				if(mesh.selected[j]){
					rgbColor=Color.RED.getRGB();

				}	


				fillOval(landscapeZbuffer,xo,yo,2,2,rgbColor);

			}

		}
		drawCurrentRect(landscapeZbuffer);
	}

	public void displaySPLines(ZBuffer landscapeZbuffer, ArrayList<SPLine> splines) {

		for (int i = 0; i < splines.size(); i++) {
			SPLine sp = (SPLine) splines.get(i);

			ArrayList<PolygonMesh> meshes = sp.getMeshes();

			for (int j = 0; j < meshes.size(); j++) {

				PolygonMesh mesh = (PolygonMesh) meshes.get(j);

				drawSPLinePolygon(mesh,landscapeZbuffer,1,mesh.hashCode());


			}

		}


		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);

			ArrayList<SPNode> nodes = spline.getNodes();
			if(nodes==null)
				continue;
			int sz=nodes.size();

			for (int k = 0; k < sz; k++) {

				SPNode node = (SPNode) nodes.get(k);

				PolygonMesh pm=node.getRing();

				Texture texture=EditorData.whiteTexture;

				if(node.isSelected())
					texture=EditorData.redTexture;

				for(int l=0;l<pm.polygonData.size();l++){


					LineData ld=(LineData) pm.polygonData.get(l);



					drawPolygon(ld,pm.xpoints,pm.ypoints,pm.zpoints,landscapeZbuffer,texture,1,pm.hashCode());

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

	private void drawObjectPolygon(ZBuffer landscapeZbuffer, Polygon pTot, int rgbColor) {



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

	private void drawSPLinePolygon(PolygonMesh mesh, ZBuffer landscapeZbuffer,
			int rgbColor,int hashCode) {


		int lsize=mesh.polygonData.size();


		for(int k=0;k<lsize;k++){


			LineData ld=(LineData) mesh.polygonData.get(k);
			Texture texture = EditorData.splinesEditorTextures[ld.getTexture_index()];

			drawPolygon(ld,mesh.xpoints,mesh.ypoints,mesh.zpoints,landscapeZbuffer,texture,0,hashCode);

		} 

	}





	private void drawPolygon(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints,ZBuffer landscapeZbuffer,Texture texture,int indx,int hashCode) {


		

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=new Point4D(xpoints[num],ypoints[num],zpoints[num]);

			cx[i]=convertX(p);
			cy[i]=convertY(p);
			cz[i]=(int)p.z;

		}

		Polygon p_in=new Polygon(cx,cy,ld.size());

		if(!Polygon3D.isIntersect(p_in,totAreaNounds))
			return;

		Polygon3D p3d=new Polygon3D(size,cx,cy,cz);
		Polygon3D p3dr=PolygonMesh.getBodyPolygon(xpoints,ypoints,zpoints,ld,Road.OBJECT_LEVEL);

		//calculate texture angle


		Color selected=null;

		if(ld.isSelected()){

			if(indx<0 || indx==editor.getACTIVE_PANEL()){
				selected=selectionColor;
			}
		}




		drawTexture(texture,p3d,p3dr,landscapeZbuffer,selected,hashCode);



	}

	private void drawTexture(Texture texture,  Polygon3D p3d, Polygon3D p3dr,ZBuffer landscapeZbuffer, Color selected,int hashCode) {

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

				decomposeTriangleIntoZBufferEdgeWalking(tria,rgb,texture,landscapeZbuffer, xDirection, yDirection, p0r, 0, 0,bc,hashCode); 


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
			BarycentricCoordinates bc,int hashCode) {

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

		p0.rotate(MOVX,MOVY,cosf,sinf);
		p1.rotate(MOVX,MOVY,cosf,sinf);
		p2.rotate(MOVX,MOVY,cosf,sinf);

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

				if(!zb.isToUpdate(-zi,zi,tot,level,p3d.hashCode())){

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

				zb.set(xi,yi,zi,-zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor,p3d.isFilledWithWater(),level),level,tot,p3d.hashCode());


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


				if(!zb.isToUpdate(-zi,zi,tot,level,p3d.hashCode()) ){

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



				zb.set(xi,yi,zi,-zi,calculateShadowColor(xi,yi,zi,cosin,rgbColor,p3d.isFilledWithWater(),level),level,tot,p3d.hashCode());

			}


		}	




	}
	@Override
	public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs,boolean hasWater,int level) {
		
		boolean useSelectionColor=isUseSelectionColor(xi, yi, zi, cosin,  argbs, hasWater,level);
		if(useSelectionColor){
			return 0xffffffff;
		}
		
		//water effect
		if(hasWater && zi<-Road.WATER_FILLING+MOVZ){	
			
			int alphas=0xff & (argbs>>24);
			int rs = 0xff & (argbs>>16);
			int gs = 0xff & (argbs >>8);
			int bs = 0xff & argbs;
	
			rs=gs=0;

		
			return alphas <<24 | rs <<16 | gs <<8 | bs;
			
		}else{
			
			return argbs;
		}

	
	
	}



	private Area clipPolygonToArea2D(Polygon p_in,Area area_out){


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




	public void displayObjects(ArrayList<DrawObject> drawObjects,Area area,ZBuffer landscapeZbuffer) {

		int objSize=drawObjects.size();

		for(int i=0;i<objSize;i++){

			DrawObject dro=(DrawObject) drawObjects.get(i);

			int y=convertY(dro.getX(),dro.getY(),dro.getZ());
			int x=convertX(dro.getX(),dro.getY(),dro.getZ());

			int index=dro.getIndex();

			int dw=(int) (dro.getDx()/deltax);
			int dh=(int) (dro.getDy()/deltay);


			if(!area.intersects(new Rectangle(x,y-dh,dw,dh))){

				//System.out.println(totalVisibleField+" "+new Rectangle(x,y,dw,dh));

				continue;
			}




			drawObject(landscapeZbuffer,dro);

		}	
	}
	
	/**
	 * Array used to draw the object bounds
	 */
	int[] droX=new int[4];
	int[] droY=new int[4];

	/**
	 * Draw the bounds of the objects
	 * as rectangle
	 * 
	 * @param landscapeZbuffer
	 * @param dro
	 */

	
	private void drawObject(ZBuffer landscapeZbuffer, DrawObject dro) {

	

		int versus=1;
		if(!DrawObject.IS_3D)
			versus=-1;

		droX[0]=convertX(dro.getX(),dro.getY(),dro.getZ());
		droY[0]=convertY(dro.getX(),dro.getY(),dro.getZ());
		droX[1]=convertX(dro.getX(),dro.getY(),dro.getZ());
		droY[1]=convertY(dro.getX(),dro.getY()+versus*dro.getDy(),dro.getZ());
		droX[2]=convertX(dro.getX()+dro.getDx(),dro.getY(),dro.getZ());
		droY[2]=convertY(dro.getX(),dro.getY()+versus*dro.getDy(),dro.getZ());
		droX[3]=convertX(dro.getX()+dro.getDx(),dro.getY(),dro.getZ());
		droY[3]=convertY(dro.getX(),dro.getY(),dro.getZ());

		Polygon p_in=new Polygon(droX,droY,4);

		Point3D center=Polygon3D.findCentroid(p_in);
		Polygon3D.rotate(p_in,center,dro.getRotation_angle());

		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		Polygon pTot=Polygon3D.fromAreaToPolygon2D(partialArea);
		//if(cy[0]<0 || cy[0]>HEIGHT || cx[0]<0 || cx[0]>WIDTH) return;


		Color pColor=ZBuffer.fromHexToColor(dro.getHexColor());


		if(dro.isSelected())
		{
			pColor=Color.RED;

		}
		int rgbColor = pColor.getRGB();
		drawObjectPolygon(landscapeZbuffer,pTot,rgbColor);
		if(deltax<16){
			drawTextImage(landscapeZbuffer,RoadEditor.objectIndexes[dro.getIndex()]
					,droX[0]-5,droY[0]-5,editor.indexWidth,editor.indexHeight,Color.BLACK,pColor);		
		}


	}

	@Override
	public int convertX(double i,double j,double k) {

		return (int) (i/deltax-MOVX);
	}
	@Override
	public int convertY(double i,double j,double k) {

		return (int) (HEIGHT-(j/deltay+MOVY));
	}
	@Override
	public int invertX(int i,int j) {

		return (i+MOVX)*deltax;
	}
	@Override
	public int invertY(int i,int j) {

		return deltay*(HEIGHT-j-MOVY);
	}


	public void zoom(int i) {



		double alfa=1.0;
		if(i>0){
			alfa=0.5;

			if(deltax==1 || deltay==1)
				return;
		}
		else {
			alfa=2.0;

		}


		deltax=(int) (deltax*alfa);
		deltay=(int) (deltay*alfa);

		MOVX+=(int) ((WIDTH/2+MOVX)*(1.0/alfa-1.0));
		MOVY+=(int) ((-HEIGHT/2+MOVY)*(1.0/alfa-1.0));
	}


	@Override
	public void mouseDown() {
		translate(0,-1);
	}

	@Override
	public void mouseUp() {
		translate(0,1);
	}

	@Override
	public void translate(int i, int j) {
		MOVX=MOVX+i*xMovement;
		MOVY=MOVY+j*yMovement;

	}


	@Override
	public void gotoPosition(int goPOSX, int goPOSY) {
		MOVX=goPOSX;
		MOVY=-goPOSY;
		editor.draw();
	}

	private void drawTextImage(ZBuffer landscapeZbuffer,
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
	@Override
	public HashMap<Integer,Boolean> pickUpPoygonsWithFastCircle(PolygonMesh mesh) {

		HashMap<Integer, Boolean> map = new HashMap<Integer,Boolean>();


		if(mesh.xpoints==null || editor.fastSelectionCircle==null)
			return map;


		int xc=editor.fastSelectionCircle.x;
		int yc=editor.fastSelectionCircle.y;

		int rx=editor.fastSelectionCircle.width;

		int sizel=mesh.polygonData.size();


		for(int j=0;j<sizel;j++){

			LineData ld=(LineData) mesh.polygonData.get(j);

			Polygon3D drawPolygon= buildPolygon(ld, mesh.xpoints,mesh.ypoints,mesh.zpoints, false);

			boolean isVisible = Polygon3D.isIntersect(drawPolygon,totArea.getBounds());
			boolean selected=false;	

			if(isVisible ){

				Polygon3D pol=buildPolygon(ld, mesh.xpoints,mesh.ypoints,mesh.zpoints, true);

				boolean selectByVertex=false;
				if(selectByVertex){
					for (int i = 0; i < pol.xpoints.length; i++) {

						double distance=Point3D.distance(xc, yc, 0, pol.xpoints[i], pol.ypoints[i], 0);


						if(distance<rx){

							map.put(new Integer(j), new Boolean(true));
							selected=true;
							break;
						}
					} 
				}
				else{
					Point3D centroid = Polygon3D.findCentroid(pol);

					double distance=Point3D.distance(xc, yc, 0, centroid.x,centroid.y, 0);				

					if(distance<rx){

						map.put(new Integer(j), new Boolean(true));
						selected=true;
					}

				}

				if(!selected){

					if(Polygon3D.isIntersect(new Point3D(xc,yc,0),pol.getBounds())){

						map.put(new Integer(j), new Boolean(true));
						selected=true;
					}

				}

			}

		}


		return map;


	}


	public boolean selectSPNodes(int x, int y, ArrayList<SPLine> splines) {

		//System.out.println(x+" "+y);

		boolean isToselect=true;



		for (int i = 0; i < splines.size(); i++) {

			SPLine spline = (SPLine) splines.get(i);

			ArrayList<SPNode> nodes = spline.getNodes();
			if(nodes==null)
				continue;
			int sz=nodes.size();

			boolean found=false;

			for(int j=0;j<sz;j++){

				SPNode spnode = (SPNode) nodes.get(j);
				PolygonMesh circle = spnode.getCircle();

				for (int k = 0; k < circle.polygonData.size(); k++) {

					LineData ld=(LineData) circle.polygonData.get(k);
					Polygon3D pol=buildPolygon(ld,circle.xpoints,circle.ypoints,circle.zpoints,false);
					// System.out.println(k+" "+pol);
					if(pol.contains(x,y)){

						if(isToselect){

							spnode.setSelected(true);
							found=true;

							editor.setSPLineData(spline,spnode);

							break;
						}

					}else{

						if(!editor.checkMultipleSpnodeSelection.isSelected())
							spnode.setSelected(false);
					}	
				}



			}

			if(found){
				spline.calculateRibs();
				spline.calculate3DMeshes();
			}
		}




		return false;
	}

	@Override
	public boolean selectSPnodesWithRectangle(ArrayList<SPLine> splines) {

		//System.out.println(x+" "+y);

		boolean isToselect=true;

		int x0=Math.min(editor.currentRect.x,editor.currentRect.x+editor.currentRect.width);
		int x1=Math.max(editor.currentRect.x,editor.currentRect.x+editor.currentRect.width);
		int y0=Math.min(editor.currentRect.y,editor.currentRect.y+editor.currentRect.height);
		int y1=Math.max(editor.currentRect.y,editor.currentRect.y+editor.currentRect.height);

		for (int i = 0; i < splines.size(); i++) {

			SPLine spline = (SPLine) splines.get(i);

			ArrayList<SPNode> nodes = spline.getNodes();
			if(nodes==null)
				continue;
			int sz=nodes.size();

			boolean found=false;

			for(int j=0;j<sz;j++){

				SPNode spnode = (SPNode) nodes.get(j);
				PolygonMesh circle = spnode.getCircle();

				int xo=convertX(spnode.getX(),spnode.getY(),spnode.getZ());
				int yo=convertY(spnode.getX(),spnode.getY(),spnode.getZ());


				if(xo>=x0 && xo<=x1 && yo>=y0 && yo<=y1  ){

					if(isToselect){

						spnode.setSelected(true);
						found=true;

						editor.setSPLineData(spline,spnode);


					}

				}else{

					if(!editor.checkMultipleSpnodeSelection.isSelected())
						spnode.setSelected(false);
				}	




			}

			if(found){
				spline.calculateRibs();
				spline.calculate3DMeshes();			
			}
		}




		return false;
	}
	
	public void selectObjects(int x, int y, ArrayList<DrawObject> drawObjects) {

		ArrayList<DrawObject> vec=selectObjects(x,y,drawObjects,true);


	}

	@Override
	public ArrayList<DrawObject> selectObjects(int x, int y, ArrayList<DrawObject> drawObjects,boolean toSelect) {

		ArrayList<DrawObject> ret=new ArrayList<DrawObject>();

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.get(i);
			if(!editor.checkMultipleObjectsSelection.isSelected())
				dro.setSelected(false);


			int[] cx=new int[4];
			int[] cy=new int[4];

			int versus=1;
			if(!DrawObject.IS_3D)
				versus=-1;

			cx[0]=convertX(dro.getX(),dro.getY(),dro.getZ());
			cy[0]=convertY(dro.getX(),dro.getY(),dro.getZ());
			cx[1]=convertX(dro.getX(),dro.getY(),dro.getZ());
			cy[1]=convertY(dro.getX(),dro.getY()+versus*dro.getDy(),dro.getZ());
			cx[2]=convertX(dro.getX()+dro.getDx(),dro.getY(),dro.getZ());
			cy[2]=convertY(dro.getX(),dro.getY()+versus*dro.getDy(),dro.getZ());
			cx[3]=convertX(dro.getX()+dro.getDx(),dro.getY(),dro.getZ());
			cy[3]=convertY(dro.getX(),dro.getY(),dro.getZ());

			Polygon p_in=new Polygon(cx,cy,4);			
			Point3D center=Polygon3D.findCentroid(p_in);
			Polygon3D.rotate(p_in,center,dro.getRotation_angle());

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
	
	@Override
	public boolean selectObjectsWithRectangle(ArrayList<DrawObject> drawObjects) {
		
		if(drawObjects==null)
			return false;
	
		int x0=Math.min(editor.currentRect.x,editor.currentRect.x+editor.currentRect.width);
		int x1=Math.max(editor.currentRect.x,editor.currentRect.x+editor.currentRect.width);
		int y0=Math.min(editor.currentRect.y,editor.currentRect.y+editor.currentRect.height);
		int y1=Math.max(editor.currentRect.y,editor.currentRect.y+editor.currentRect.height);


		//select point from road
		boolean found=false;
		int sz=drawObjects.size();

		for(int j=0;j<sz;j++){


			DrawObject dro=(DrawObject)drawObjects.get(j);

			int xo=convertX(dro.getX(),dro.getY(),dro.getZ());
			int yo=convertY(dro.getX(),dro.getY(),dro.getZ());


			if(xo>=x0 && xo<=x1 && yo>=y0 && yo<=y1  ){

				dro.setSelected(true);
				found=true;


			}
			else if(!editor.checkMultipleObjectsSelection.isSelected())
				dro.setSelected(false);


		}

		return found;
	}


	private Polygon3D buildPolygon(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints, boolean isReal) {

		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p= new Point4D(xpoints[num],ypoints[num],zpoints[num]);


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


	@Override
	public void displayStartPosition(ZBuffer landscapeZbuffer, Point3D startPosition) {

		if(startPosition==null)
			return;

		PolygonMesh ring = EditorData.getRing(startPosition.getX(),startPosition.getY(),25,40,50);


		int lsize=ring.polygonData.size();


		for(int j=0;j<lsize;j++){


			LineData ld=(LineData) ring.polygonData.get(j);

			drawPolygon(ld,ring.xpoints,ring.ypoints,ring.zpoints,landscapeZbuffer,EditorData.cyanTexture,RoadEditor.TERRAIN_INDEX,ring.hashCode());

		} 
	}


	@Override
	public void rotate(int signum) {
		//nothing to do

	}

	@Override
	public int getPOSX() {
		return MOVX;
	}

	@Override
	public int getPOSY() {
		return -MOVY;
	}

	@Override
	double getDeltaY() {
		return deltay;
	}

	@Override
	double getDeltaX() {
		return deltax;
	}

	@Override
	public void changeMotionIncrement(int i) {
		if(i>0){
			
			xMovement=2*xMovement;
			yMovement=2*yMovement;
			
		}else{
			
			if(xMovement==minMovement)
				return;
			
			xMovement=xMovement/2;
			yMovement=yMovement/2;
			
		}
		
	}

	@Override
	public Rectangle buildSelecctionCircle(MouseEvent e, int rad) {

		return new Rectangle(invertX(e.getX(), e.getY()),invertY(e.getX(), e.getY()), rad, rad);
	}
	



}
