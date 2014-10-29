package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;
import com.main.Renderer3D;

public class Wardrobe extends Forniture{
	
	public static int texture_side_dx=10;
	public static int texture_side_dy=10;

	public static int texture_x0=10;
	public static int texture_y0=10;
	public static int IMG_WIDTH;
	public static int IMG_HEIGHT;
	
	public static boolean isTextureDrawing=false;
	
	int N_FACES=4;
	int N_PARALLELS=2;
	
	private double len;
	
	Point3D[] upperBase=null;
	Point3D[] lowerBase=null;
	Point3D[][] lateralFaces=null; 
	

	public Wardrobe(double x_side, double y_side, double z_side) {
		
		this.x_side=x_side;
		this.y_side=y_side;
		this.z_side=z_side;
		
		len=2*(x_side+y_side);
		
		upperBase=new Point3D[N_FACES];
		
		upperBase[0]=new Point3D(0,z_side,0);			
		upperBase[1]=new Point3D(x_side,z_side,0);	
		upperBase[2]=new Point3D(x_side,z_side+y_side,0);	
		upperBase[3]=new Point3D(0,z_side+y_side,0);	
		
		lowerBase=new Point3D[N_FACES];
		
		lowerBase[0]=new Point3D(0,0,0);			
		lowerBase[1]=new Point3D(0,y_side,0);	
		lowerBase[2]=new Point3D(x_side,y_side,0);	
		lowerBase[3]=new Point3D(x_side,0,0);	
		
		double dx=texture_side_dx;
		double dy=texture_side_dy;
		
		lateralFaces=new Point3D[N_FACES+1][N_PARALLELS];

		for(int j=0;j<N_PARALLELS;j++){

			//texture is open and periodical:

			for (int i = 0; i <=N_FACES; i++) {

				double x=dx*i;
				double y=y_side+dy*j;

				lateralFaces[i][j]=new Point3D(x,y,0);

			}
			
		}	
		
		initMesh();
	}
	

	/*public void initMesh() {

		points=new Vector();
		points.setSize(50);

		polyData=new Vector();
		
		n=0;

		//basic sides:
		buildBox(0,0,0,x_side,y_side,z_side);

	}*/

	/*public void saveBaseCubicTexture(PolygonMesh mesh, File file) {

		
		Color backgroundColor=Color.green;
		Color back_color=Color.BLUE;
		Color top_color=Color.RED;
		Color bottom_color=Color.MAGENTA;
		Color left_color=Color.YELLOW;
		Color right_color=Color.ORANGE;
		Color front_color=Color.CYAN;
		
		int IMG_WIDTH=0;
		int IMG_HEIGHT=0;

		int deltaX=0;
		int deltaY=0;
		int deltaX2=0;

		double minx=0;
		double miny=0;
		double minz=0;
		
		double maxx=0;
		double maxy=0;
		double maxz=0;
		
		
	      //find maxs
		for(int j=0;j<mesh.points.length;j++){
			
			Point3D point=mesh.points[j];
			
			if(j==0){
				
				minx=point.x;
				miny=point.y;
				minz=point.z;
				
				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{
				
				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);
				
				
				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}
			
	
		}
		
		deltaX2=(int)(maxx-minx)+1;
		deltaX=(int)(maxz-minz)+1; 
		deltaY=(int)(maxy-miny)+1;
		
		deltaX2=deltaX2+deltaX;
		
		IMG_HEIGHT=(int) deltaY+deltaX+deltaX;
		IMG_WIDTH=(int) (deltaX2+deltaX2);
		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {

	


				int DX=0;

				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(DX+0,0,IMG_WIDTH,IMG_HEIGHT);


				//draw lines for reference

				bufGraphics.setColor(new Color(0,0,0));
				bufGraphics.setStroke(new BasicStroke(0.1f));
				for(int j=0;j<mesh.polygonData.size();j++){ 

					LineData ld=(LineData) mesh.polygonData.elementAt(j);

					for (int k = 0; k < ld.size(); k++) {


						Point3D point0=  mesh.points[ld.getIndex(k)];
						Point3D point1=  mesh.points[ld.getIndex((k+1)%ld.size())];
						//top
						bufGraphics.setColor(top_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.x-minx+deltaX),(int)(-point1.y+maxy+deltaX));
						//front
						bufGraphics.setColor(front_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(point0.z-minz),DX+(int)(point1.x-minx+deltaX),(int)(point1.z-minz));
						//left
						bufGraphics.setColor(left_color);
						bufGraphics.drawLine(DX+(int)(point0.z-minz),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.z-minz),(int)(-point1.y+maxy+deltaX));
						//right
						bufGraphics.setColor(right_color);
						bufGraphics.drawLine(DX+(int)(-point0.z+maxz+deltaX2),(int)(-point0.y+maxy+deltaX),DX+(int)(-point1.z+maxz+deltaX2),(int)(-point1.y+maxy+deltaX));
						//back
						bufGraphics.setColor(back_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(-point0.z+maxz+deltaY+deltaX),DX+(int)(point1.x-minx+deltaX),(int)(-point1.z+maxz+deltaY+deltaX));
						//bottom
						bufGraphics.setColor(bottom_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX+deltaX2),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.x-minx+deltaX+deltaX2),(int)(-point1.y+maxy+deltaX));
				
					}

				}	
			
			ImageIO.write(buf,"gif",file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}*/
	
public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
		
		isTextureDrawing=true;


		
		texture_side_dy=(int) (z_side/(N_PARALLELS-1));
		texture_side_dx=(int) (len/(N_FACES));
		

		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) (z_side+y_side*2)+2*texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {


				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);


				//draw lines for reference

				bufGraphics.setColor(Color.RED);
				bufGraphics.setStroke(new BasicStroke(0.1f));
				
				for (int j = 0; j <upperBase.length; j++) {

					double x0= calX(upperBase[j].x);
					double y0= calY(upperBase[j].y);
					
					double x1= calX(upperBase[(j+1)%upperBase.length].x);
					double y1= calY(upperBase[(j+1)%upperBase.length].y);

					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 

				}

				
				//lowerbase
		
				bufGraphics.setColor(Color.BLUE);
				
				for (int j = 0; j <lowerBase.length; j++) {

					double x0= calX(lowerBase[j].x);
					double y0= calY(lowerBase[j].y);
					
					double x1= calX(lowerBase[(j+1)%lowerBase.length].x);
					double y1= calY(lowerBase[(j+1)%lowerBase.length].y);

					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);			

				}


				//lateral surface
				bufGraphics.setColor(new Color(0,0,0));


				for(int j=0;j<N_PARALLELS-1;j++){

					//texture is open and periodical:

					for (int i = 0; i <N_FACES; i++) { 

						double x0=calX(lateralFaces[i][j].x);
						double x1=calX(lateralFaces[i+1][j].x);
						double y0=calY(lateralFaces[i][j].y);
						double y1=calY(lateralFaces[i][j+1].y);
						
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y0);
						bufGraphics.drawLine((int)x1,(int)y0,(int)x1,(int)y1);
						bufGraphics.drawLine((int)x1,(int)y1,(int)x0,(int)y1);
						bufGraphics.drawLine((int)x0,(int)y1,(int)x0,(int)y0);
					}

				}	
				
			
				ImageIO.write(buf,"gif",file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	public Vector buildTexturePoints() {
		
		isTextureDrawing=false;
		
		Vector texture_points=new Vector();
		


		int size=2*N_FACES+	(N_FACES+1)*(N_PARALLELS);
		
		texture_points.setSize(size);
		
		int count=0;
		//upperbase

		for (int j = 0; j <upperBase.length; j++) {

			
			
			double x= calX(upperBase[j].x);
			double y= calY(upperBase[j].y);

			Point3D p=new Point3D(x,y,0);			
			texture_points.setElementAt(p,count++);


		}
	
		
		for (int j = 0; j <lowerBase.length; j++) {

			double x= calX(lowerBase[j].x);
			double y= calY(lowerBase[j].y);

			Point3D p=new Point3D(x,y,0);			
			texture_points.setElementAt(p,count++);

		}


		//lateral surface


		for(int j=0;j<N_PARALLELS;j++){

			//texture is open and periodical:

			for (int i = 0; i <=N_FACES; i++) {

				double x=calX(lateralFaces[i][j].x);
				double y=calY(lateralFaces[i][j].y);

				Point3D p=new Point3D(x,y,0);

				int texIndex=count+f(i,j,N_FACES+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				texture_points.setElementAt(p,texIndex);
			}
			
		}	
		
		return texture_points;
	}
	
	public static double calX(double x){
		
		return texture_x0+x;
	}

	public static double calY(double y){
		if(isTextureDrawing)
			return IMG_HEIGHT-(texture_y0+y);
		else
			return texture_y0+y;
	}
	
	public void initMesh( ) {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();
		
		n=0;

		BPoint[][] aPoints=new BPoint[N_PARALLELS][N_FACES];
		
		aPoints[0][0]=addBPoint(0,0,0);			
		aPoints[0][1]=addBPoint(x_side,0,0);	
		aPoints[0][2]=addBPoint(x_side,y_side,0);	
		aPoints[0][3]=addBPoint(0,y_side,0);
		
		aPoints[1][0]=addBPoint(0,0,z_side);			
		aPoints[1][1]=addBPoint(x_side,0,z_side);	
		aPoints[1][2]=addBPoint(x_side,y_side,z_side);	
		aPoints[1][3]=addBPoint(0,y_side,z_side);	

		for(int j=0;j<N_PARALLELS;j++){  

				for (int i = 0; i <N_FACES; i++) {  
					
					points.setElementAt(aPoints[j][i],aPoints[j][i].getIndex());

				}


		}


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		
		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();
		
		int count=0;

		for (int j = 0; j <N_FACES; j++) {

			upperBase.addIndex(aPoints[N_PARALLELS-1][j].getIndex(),count++,0,0);

			upperBase.setData(""+Renderer3D.getFace(upperBase,points));

		}	

		for (int j = N_FACES-1; j >=0; j--) {

			lowerBase.addIndex(aPoints[0][j].getIndex(),count++,0,0);

			lowerBase.setData(""+Renderer3D.getFace(lowerBase,points));
		}

		polyData.add(upperBase);
		polyData.add(lowerBase);

		for(int j=0;j<N_PARALLELS-1;j++){ 




			for (int i = 0; i <N_FACES; i++) { 


				LineData ld=new LineData();

				int texIndex=count+f(i,j,N_FACES+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j][i].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j,N_FACES+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j][(i+1)%N_FACES].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j+1,N_FACES+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j+1][(i+1)%N_FACES].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i,j+1,N_FACES+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j+1][i].getIndex(),texIndex,0,0);

				ld.setData(""+Renderer3D.getFace(ld,points));
				
				

				polyData.add(ld);

			}
		

		}


	}
	
}
