package com.editors.road.panel;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.ZBuffer;
import com.editors.road.RoadEditor;
import com.main.Road;

public class RoadEditorTopPanel extends RoadEditorPanel {

	
	
	public RoadEditorTopPanel(RoadEditor editor,int cENTER_HEIGHT, int cENTER_WIDTH) {
		
		
	}
	
	
	
	/*private void displayRoad(ZBuffer landscapeZbuffer,int index) {

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

			if(indx<0 || indx==ACTIVE_PANEL){
				selected=selectionColor;
			}
		}



		drawTexture(worldTextures[index],p3d,p3dr,landscapeZbuffer,selected);



	}
	
	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}
	
	private void fillOval(ZBuffer landscapeZbuffer2, int cx, int cy, int dx,
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

	
	
	
	private void displayObjects(ZBuffer landscapeZbuffer) {

		Rectangle totalVisibleField=new Rectangle(0,0,WIDTH,HEIGHT);

		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);

			int y=convertY(dro.y);
			int x=convertX(dro.x);

			int index=dro.getIndex();

			int dw=(int) (dro.dx/dx);
			int dh=(int) (dro.dy/dy);


			if(DrawObject.IS_3D){
			
				if(!totalVisibleField.intersects(new Rectangle(x,y-dh,dw,dh))){
					
					//System.out.println(totalVisibleField+" "+new Rectangle(x,y,dw,dh));
					
					continue;
				}
			
			}else{
				
				if(!totalVisibleField.intersects(new Rectangle(x,y,dw,dh))){
					
					//System.out.println(totalVisibleField+" "+new Rectangle(x,y,dw,dh));
					
					continue;
				}
				
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
				
		drawTextImage(landscapeZbuffer,objectIndexes[dro.getIndex()]
						,cx[0]-5,cy[0]-5,indexWidth,indexHeight,Color.BLACK,pColor);		
		
		if(!DrawObject.IS_3D){
			
			Point3D xDirection=new Point3D(p_in.xpoints[3]-p_in.xpoints[0],p_in.ypoints[3]-p_in.ypoints[0],0);
			Point3D yDirection=new Point3D(p_in.xpoints[1]-p_in.xpoints[0],p_in.ypoints[1]-p_in.ypoints[0],0);
	
			drawImage(landscapeZbuffer,DrawObject.fromImageToBufferedImage(objectImages[dro.index],null)
					,p_in.xpoints[0],p_in.ypoints[0],xDirection,yDirection,Color.WHITE);
		}
		

	}
	

	private int convertX(double i) {

		return (int) (i/dx-MOVX);
	}
	private int convertY(double j) {

		return (int) (HEIGHT-(j/dy+MOVY));
	}

	private int invertX(int i) {

		return (i+MOVX)*dx;
	}
	private int invertY(int j) {

		return dy*(HEIGHT-j-MOVY);
	}*/

	
}
