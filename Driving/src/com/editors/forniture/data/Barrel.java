package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;

public class Barrel extends CustomData{
	
	public static int texture_side_dx=10;
	public static int texture_side_dy=10;
	
	public static int texture_x0=10;
	public static int texture_y0=10;

	public static void saveBarrelTexture(PolygonMesh mesh, File file) {

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
		
	}

	public static Vector buildTexturePoints(int N_MERIDIANS, int N_PARALLELS,Point3D[] profile) {
		
		
		Vector texture_points=new Vector();
		
		double teta=(2*pi)/(N_MERIDIANS);


		int size=2*N_MERIDIANS+	(N_MERIDIANS+1)*(N_PARALLELS);

		
		texture_points.setSize(size);

		double upper_radius=profile[N_PARALLELS-1].y;
		double lower_radius=profile[0].y;

		double xc=0;
		double yc=0;

		int count=0;
		//upperbase

		for (int j = 0; j <N_MERIDIANS; j++) {

			double x= Barrel.texture_x0+xc+(upper_radius*Math.cos(j*teta));
			double y= Barrel.texture_y0+yc+(upper_radius*Math.sin(j*teta));

			Point3D p=new Point3D(x,y,0);			
			texture_points.setElementAt(p,count++);


		}

		//lowerbase

		for (int j = N_MERIDIANS-1; j >=0; j--) {

			double x= Barrel.texture_x0+xc+(lower_radius*Math.cos(j*teta));
			double y= Barrel.texture_y0+yc+(lower_radius*Math.sin(j*teta));

			Point3D p=new Point3D(x,y,0);			
			texture_points.setElementAt(p,count++);

		}


		//lateral surface

		double dx=Barrel.texture_side_dx;
		double dy=Barrel.texture_side_dy;

		for(int i=0;i<N_PARALLELS;i++){

			//texture is open and periodical:

			for (int j = 0; j <=N_MERIDIANS; j++) {

				double x=Barrel.texture_x0+dx*i;
				double y=Barrel.texture_y0+dy*j;

				Point3D p=new Point3D(x,y,0);

				int texIndex=count+f(i,j,N_PARALLELS,N_MERIDIANS+1);
				texture_points.setElementAt(p,texIndex);
			}

		}	
		
		return texture_points;
	}

}
