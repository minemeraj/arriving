package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
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
	public static int IMG_WIDTH;
	public static int IMG_HEIGHT;
	

	public static void saveBarrelTexture(PolygonMesh mesh, File file, Hashtable specificData) {
		
		
		int N_MERIDIANS=((Integer)specificData.get("N_MERIDIANS")).intValue();
		int N_PARALLELS=((Integer)specificData.get("N_PARALLELS")).intValue();
		double lower_radius=((Double)specificData.get("LOWER_RADIUS")).doubleValue();
		double upper_radius=((Double)specificData.get("UPPER_RADIUS")).doubleValue();
		double zHeight=((Double)specificData.get("HEIGHT")).doubleValue();
		
		double max_radius=Math.max(lower_radius,upper_radius);
		double len=max_radius*2*pi;
		
		texture_side_dy=(int) (zHeight/(N_PARALLELS-1));
		texture_side_dx=(int) (len/(N_MERIDIANS));
		

		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+texture_x0;
		IMG_HEIGHT=(int) (zHeight+lower_radius*2+upper_radius*2)+texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {

				double xc=lower_radius;
				double yc=upper_radius+zHeight+2*lower_radius;

				double teta=(2*pi)/(N_MERIDIANS);

				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);


				//draw lines for reference

				bufGraphics.setColor(Color.RED);
				bufGraphics.setStroke(new BasicStroke(0.1f));
				
				for (int j = 0; j <N_MERIDIANS; j++) {

					double x0= calX(xc+(upper_radius*Math.cos(j*teta)));
					double y0= calY(yc+(upper_radius*Math.sin(j*teta)));
					
					double x1= calX(xc+(upper_radius*Math.cos((j+1)*teta)));
					double y1= calY(yc+(upper_radius*Math.sin((j+1)*teta)));

					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 

				}

				
				//lowerbase
				yc=lower_radius;
				bufGraphics.setColor(Color.BLUE);
				
				for (int j = N_MERIDIANS-1; j >=0; j--) {

					double x0= calX(xc+(lower_radius*Math.cos(j*teta)));
					double y0= calY(yc+(lower_radius*Math.sin(j*teta)));
					
					double x1= calX(xc+(lower_radius*Math.cos((j+1)*teta)));
					double y1= calY(yc+(lower_radius*Math.sin((j+1)*teta)));

					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);			

				}


				//lateral surface
				bufGraphics.setColor(new Color(0,0,0));
				
				double dx=texture_side_dx;
				double dy=texture_side_dy;

				for(int j=0;j<N_PARALLELS-1;j++){

					//texture is open and periodical:

					for (int i = 0; i <N_MERIDIANS; i++) { 

						double x0=calX(dx*i);
						double x1=calX(dx*(i+1));
						double y0=calY(2*lower_radius+dy*j);
						double y1=calY(2*lower_radius+dy*(j+1));
						
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

			double x= texture_x0+xc+(upper_radius*Math.cos(j*teta));
			double y= texture_y0+yc+(upper_radius*Math.sin(j*teta));

			Point3D p=new Point3D(x,y,0);			
			texture_points.setElementAt(p,count++);


		}

		//lowerbase

		for (int j = N_MERIDIANS-1; j >=0; j--) {

			double x= texture_x0+xc+(lower_radius*Math.cos(j*teta));
			double y= texture_y0+yc+(lower_radius*Math.sin(j*teta));

			Point3D p=new Point3D(x,y,0);			
			texture_points.setElementAt(p,count++);

		}


		//lateral surface

		double dx=texture_side_dx;
		double dy=texture_side_dy;

		for(int i=0;i<N_PARALLELS;i++){

			//texture is open and periodical:

			for (int j = 0; j <=N_MERIDIANS; j++) {

				double x=texture_x0+dx*i;
				double y=texture_y0+dy*j;

				Point3D p=new Point3D(x,y,0);

				int texIndex=count+f(i,j,N_PARALLELS,N_MERIDIANS+1);
				texture_points.setElementAt(p,texIndex);
			}

		}	
		
		return texture_points;
	}
	
	public static double calX(double x){
		
		return texture_x0+x;
	}

	public static double calY(double y){
		
		return IMG_HEIGHT-(texture_y0+y);
	}
}
