package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Vector;

import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.road.RoadEditor;
import com.main.Road;

public class RoadEditorTopPanel extends RoadEditorPanel {
	

	
	int MOVX=-50;
	int MOVY=100;

	int dx=2;
	int dy=2;
	
	public Color selectionColor=null;

	
	public RoadEditorTopPanel(RoadEditor editor, int cENTER_WIDTH,int cENTER_HEIGHT) {
		
		super(editor, cENTER_WIDTH,cENTER_HEIGHT);
		intialize();
	}
	

	private void intialize() {
		
		selectionColor=new Color(255,0,0,127);
		
	}


	public void drawRoad(PolygonMesh[] meshes, Vector drawObjects,ZBuffer landscapeZbuffer,
			Graphics2D graph) {
		
		displayRoad(landscapeZbuffer,meshes,0);
		displayRoad(landscapeZbuffer,meshes,1);
		displayObjects(landscapeZbuffer,drawObjects);
	
	}
	
	
	private void displayRoad(ZBuffer landscapeZbuffer,PolygonMesh[] meshes,int index) {

	
		PolygonMesh mesh=meshes[index];
		
		if(mesh.points==null)
			return;
		
		int lsize=mesh.polygonData.size();
		

		for(int j=0;j<lsize;j++){
			
			
			LineData ld=(LineData) mesh.polygonData.elementAt(j);

			drawPolygon(ld,mesh.points,landscapeZbuffer,index);

		} 

		//mark row angles
		
		int size=mesh.points.length;
		for(int j=0;j<size;j++){


		    Point4D p=(Point4D) mesh.points[j];

				int xo=convertX(p.x);
				int yo=convertY(p.y);
				
				int rgbColor=Color.white.getRGB();

				if(p.isSelected()){
					rgbColor=Color.RED.getRGB();

				}	
	
	
			    fillOval(landscapeZbuffer,xo,yo,2,2,rgbColor);

			}
		
		
		drawCurrentRect(landscapeZbuffer);
	}
	
	private void drawCurrentRect(ZBuffer landscapeZbuffer) {
		
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
	
	private void drawPolygon(LineData ld,Point3D[] points,ZBuffer landscapeZbuffer,int indx) {


		Area totArea=new Area(new Rectangle(0,0,WIDTH,HEIGHT));

		int size=ld.size();

		int[] cx=new int[size];
		int[] cy=new int[size];
		int[] cz=new int[size];

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points[num];

			//bufGraphics.setColor(ZBuffer.fromHexToColor(is[i].getHexColor()));

			cx[i]=convertX(p.x);
			cy[i]=convertY(p.y);
			cz[i]=(int)p.z;

			//real coordinates
			cxr[i]=(int)(p.x);
			cyr[i]=(int)(p.y);
			czr[i]=(int)p.z;

			if(indx==1){

				cz[i]+=Road.ROAD_THICKNESS;
				czr[i]+=Road.ROAD_THICKNESS;
			}

		}

		Polygon p_in=new Polygon(cx,cy,ld.size());
		Area partialArea = clipPolygonToArea2D( p_in,totArea);

		if(partialArea.isEmpty())
			return;

		Polygon3D p3d=new Polygon3D(size,cx,cy,cz);
		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

		//calculate texture angle






		int index=ld.getTexture_index();//????
		Color selected=null;

		if(ld.isSelected()){

			if(indx<0 || indx==editor.ACTIVE_PANEL){
				selected=selectionColor;
			}
		}



		drawTexture(RoadEditor.worldTextures[index],p3d,p3dr,landscapeZbuffer,selected);



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
				
					
				int rgb=-1;
				
				if(selected!=null)
					rgb=selected.getRGB();
				
				decomposeTriangleIntoZBufferEdgeWalking(tria,rgb,texture,landscapeZbuffer, xDirection, yDirection, p0r, 0, 0,null); 

				
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

			int y=convertY(dro.y);
			int x=convertX(dro.x);

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

		cx[0]=convertX(dro.x);
		cy[0]=convertY(dro.y);
		cx[1]=convertX(dro.x);
		cy[1]=convertY(dro.y+versus*dro.dy);
		cx[2]=convertX(dro.x+dro.dx);
		cy[2]=convertY(dro.y+versus*dro.dy);
		cx[3]=convertX(dro.x+dro.dx);
		cy[3]=convertY(dro.y);

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
				
		editor.drawTextImage(landscapeZbuffer,RoadEditor.objectIndexes[dro.getIndex()]
						,cx[0]-5,cy[0]-5,editor.indexWidth,editor.indexHeight,Color.BLACK,pColor);		
		
		

	}
	

	public int convertX(double i) {

		return (int) (i/dx-MOVX);
	}
	public int convertY(double j) {

		return (int) (HEIGHT-(j/dy+MOVY));
	}

	public int invertX(int i) {

		return (i+MOVX)*dx;
	}
	public int invertY(int j) {

		return dy*(HEIGHT-j-MOVY);
	}

	
}
