package com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

import com.editors.Editor;

public class Renderer2D extends Editor {
	
	public int HEIGHT=0;
	public int WIDTH=0;
	
	public Area totalVisibleField=null;
	
	public JFileChooser fc;
	
	
	public void drawRoadPolygon(Polygon3D p3d,Polygon3D p3dr, Area totalVisibleField, Color fromHexToColor,
			Texture texture, boolean b, Graphics2D bufGraphics,int buf_witdh,int buf_height,int[] rgb ) {
	
		
		Area partialArea= p3d.clipPolygonToArea2D(totalVisibleField);
          
     
		if(partialArea.isEmpty())
				return;
		
		bufGraphics.setColor(fromHexToColor);
		
		if(texture!=null){
			

			//bufGraphics.setClip(partialArea);
			drawTexture(texture,p3d,p3dr,buf_witdh,buf_height,rgb);
			//bufGraphics.drawImage(texture,rect.x,rect.y,rect.width,rect.height,null);
			//bufGraphics.setClip(null);
		}
		else 
			bufGraphics.fill(partialArea);
		
		
	}
	
	public void drawTexture(Texture texture,  Polygon3D p3d, Polygon3D p3dr,int buf_witdh,int buf_heigh, int[] rgb) {

		Point3D normal=Polygon3D.findNormal(p3dr);



		if(texture!=null){
			
			Rectangle rect = p3d.getBounds();
			
			
			/*int i0=Math.min(p3d.xpoints[0],p3d.xpoints[3]);
			int i1=Math.max(p3d.xpoints[1],p3d.xpoints[2]);
					
			int j0=Math.min(p3d.ypoints[3],p3d.ypoints[2]);
			int j1=Math.max(p3d.ypoints[0],p3d.ypoints[1]);*/
			
			int i0=rect.x;
			int i1=rect.x+rect.width;
					
			int j0=rect.y;
			int j1=rect.y+rect.height;

			//System.out.println(i0+" "+i1+" "+j0+" "+j1);
			
			
			Point3D p0r=new Point3D(p3dr.xpoints[0],p3dr.ypoints[0],p3dr.zpoints[0]);
			Point3D p1r=new Point3D(p3dr.xpoints[1],p3dr.ypoints[1],p3dr.zpoints[1]);

			Point3D xDirection = (p1r.substract(p0r)).calculateVersor();
			//Point3D yDirection= (pNr.substract(p0r)).calculateVersor();
			Point3D yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();
			
			/*System.out.println(normal.x+" "+normal.y+" "+normal.z);
			System.out.println(xDirection.x+" "+xDirection.y);
			System.out.println(yDirection.x+" "+yDirection.y);*/

			for(int i=i0;i<i1;i++){
				
				//how to calculate the real limits?
				
			
				for (int j = j0; j < j1; j++) {
					
					if(p3d.contains(i,j))
					{	

						
						int ii=invertX(i);
						int jj=invertY(j);
				
								
						if(i>=0 && i<buf_witdh && j>=0 && j<buf_heigh){
							
						
							int tot=buf_witdh*j+i;
							
							rgb[tot]=pickRGBColorFromTexture(texture,ii,jj,p3d.zpoints[0],xDirection,yDirection,p0r,0,0);

							
				
						}
						
						
						
					}
					//bufGraphics.setColor(new Color(rgbColor));
					//bufGraphics.fillRect(i,j,1,1);
					


				}

			}

		}

	}
	
	public static int  pickRGBColorFromTexture(Texture texture,double px,double py,double pz,Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY){
		
		
		int x=0;
		int y=0;
		
		if(origin!=null){
			

			 x=(int) Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,xDirection)+deltaX;
			 y=(int) Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,yDirection)+deltaY;
			 
			 
		}
		else
		{
			
			  x=(int) Point3D.calculateDotProduct(px,py, pz,xDirection)+deltaX;
			  y=(int) Point3D.calculateDotProduct(px,py, pz,yDirection)+deltaY;

		}	
		
		
		int w=texture.getWidth();
		int h=texture.getHeight();
		
		//border fixed condition
		/*if(x<0) x=0;
		if(x>=w) x=w-1;
		if(y<0) y=0;
		if(y>=h) y=h-1;*/
		
		//border periodic condition
		if(x<0) x=w+x%w;
		if(x>=w) x=x%w;
		if(y<0) y=h+y%h;
		if(y>=h) y=y%h;
		
		int argb= texture.getRGB(x, h-y-1);
		
		return argb;
		
	}
	
	public Polygon3D buildPolygon(LineData ld,Point3D[] points, boolean isReal) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point3D p=(Point3D) points[num];


			if(isReal){

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				czr[i]=(int)(p.z);
				
			}
			else {
				
				cxr[i]=convertX(p.x);
				cyr[i]=convertY(p.y);
				czr[i]=(int)(p.z);
				
			}
			


		}


		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);
		
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());

        return p3dr;

	}

	public int convertY(double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int convertX(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int invertY(int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int invertX(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Area buildVisibileArea(int y1, int y2) {
		int[] cx=new int[4];
		int[] cy=new int[4];



		cx[0]=0;
		cy[0]=y1;
		cx[1]=WIDTH;
		cy[1]=y1;
		cx[2]=WIDTH;
		cy[2]=y2;
		cx[3]=0;
		cy[3]=y2;

		Polygon p=new Polygon(cx,cy,4);

		Area a = new Area(p);

		//System.out.println(Polygon3D.fromAreaToPolygon2D(a));

		return a;
	}
	
	boolean forceReading=false;

	public void buildLines(ArrayList lines, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			LineData ld=new LineData();

			ld.texture_index=Integer.parseInt(vals[0].substring(1));
			
			ld.hexColor=vals[1].substring(1);
			
			for(int i=2;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			lines.add(ld);
		}

	}
	
	public void buildLines(PolygonMesh meshes, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			LineData ld=new LineData();

			ld.texture_index=Integer.parseInt(vals[0].substring(1));
			
			ld.hexColor=vals[1].substring(1);
			
			for(int i=2;i<vals.length;i++)
				ld.addIndex(Integer.parseInt(vals[i]));


			meshes.polygonData.add(ld);
		}

	}
	
	
	public void buildPoints(ArrayList points, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			Point4D p=new Point4D();
			
			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			points.add(p);
		}

	}
	
	public void buildPoints(PolygonMesh mesh, String str) {

		StringTokenizer sttoken=new StringTokenizer(str,"_");
		
		ArrayList aPoints=new ArrayList();

		while(sttoken.hasMoreElements()){

			String[] vals = sttoken.nextToken().split(",");

			Point4D p=new Point4D();
			
			p.x=Double.parseDouble(vals[0]);
			p.y=Double.parseDouble(vals[1]);
			p.z=Double.parseDouble(vals[2]);

			aPoints.add(p);
		}

		mesh.setPoints(aPoints);
	}
	
	
	public static String decomposeColor(Color tcc) {
		return addZeros(tcc.getRed())+","+addZeros(tcc.getGreen())+","+addZeros(tcc.getBlue());

	}
	
	public static String addZeros(int numb){
		String newStr=""+numb;
		newStr="00"+newStr;
		newStr=newStr.substring(newStr.length()-3,newStr.length());

		return newStr;

	}

}
