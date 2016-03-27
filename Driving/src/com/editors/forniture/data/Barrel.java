package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Barrel extends CustomData{
	
	public static int texture_side_dx=10;
	public static int texture_side_dy=10;
	
	public static int texture_x0=10;
	public static int texture_y0=10;
	public static int IMG_WIDTH;
	public static int IMG_HEIGHT;
	
	public static boolean isTextureDrawing=false;
	
	int N_MERIDIANS=0;
	int N_PARALLELS=0;
	
	Point3D[] profile=null;
	
	private double upper_radius;
	private double lower_radius;
	private double zHeight;
	private double max_radius;
	private double len;
	
	Point3D[] upperBase=null;
	Point3D[] lowerBase=null;
	Point3D[][] lateralFaces=null; 

	public Barrel(int N_MERIDIANS,Point3D[] profile) {
		
		this.N_MERIDIANS=N_MERIDIANS;
		N_PARALLELS=profile.length;
		
		this.profile=profile;
		
		
		upper_radius=profile[N_PARALLELS-1].y;
		lower_radius=profile[0].y;
		zHeight=profile[N_PARALLELS-1].x;
		
		max_radius=Math.max(lower_radius,upper_radius);
		len=max_radius*2*pi;
		
		double xc=upper_radius;
		double yc=upper_radius+zHeight+2*lower_radius;
		
		double teta=(2*pi)/(N_MERIDIANS);
		
		upperBase=new Point3D[N_MERIDIANS];
		
		for (int j = 0; j <N_MERIDIANS; j++) {
			

			double x= xc+(upper_radius*Math.cos(j*teta));
			double y= yc+(upper_radius*Math.sin(j*teta));

			upperBase[j]=new Point3D(x,y,0);			

		}
		
		xc=lower_radius;
		yc=lower_radius;
		
		lowerBase=new Point3D[N_MERIDIANS];
		
		for (int j = N_MERIDIANS-1; j >=0; j--) {
			
			int jj=N_MERIDIANS-1-j;

			double x= xc+(lower_radius*Math.cos(j*teta));
			double y= yc+(lower_radius*Math.sin(j*teta));

			lowerBase[jj]=new Point3D(x,y,0);			

		}
		
		double dx=texture_side_dx;
		double dy=texture_side_dy;
		
		lateralFaces=new Point3D[N_MERIDIANS+1][N_PARALLELS];

		for(int j=0;j<N_PARALLELS;j++){

			//texture is open and periodical:

			for (int i = 0; i <=N_MERIDIANS; i++) {

				double x=dx*i;
				double y=2*lower_radius+dy*j;

				lateralFaces[i][j]=new Point3D(x,y,0);

			}
			
		}	
		
		initMesh();
	}

	public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
		
		isTextureDrawing=true;


		
		texture_side_dy=(int) (zHeight/(N_PARALLELS-1));
		texture_side_dx=(int) (len/(N_MERIDIANS));
		

		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) (zHeight+lower_radius*2+upper_radius*2)+2*texture_y0;

		
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

					for (int i = 0; i <N_MERIDIANS; i++) { 

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
		
		double teta=(2*pi)/(N_MERIDIANS);


		int size=2*N_MERIDIANS+	(N_MERIDIANS+1)*(N_PARALLELS);

		
		texture_points.setSize(size);
		
		texture_side_dy=(int) (zHeight/(N_PARALLELS-1));
		texture_side_dx=(int) (len/(N_MERIDIANS));
		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) (zHeight+lower_radius*2+upper_radius*2)+2*texture_y0;


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

			for (int i = 0; i <=N_MERIDIANS; i++) {

				double x=calX(lateralFaces[i][j].x);
				double y=calY(lateralFaces[i][j].y);

				Point3D p=new Point3D(x,y,0);

				int texIndex=count+f(i,j,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				texture_points.setElementAt(p,texIndex);
			}
			
		}	
		
		return texture_points;
	}
	
	public double calX(double x){
		
		return texture_x0+x;
	}

	public double calY(double y){
		if(isTextureDrawing)
			return IMG_HEIGHT-(texture_y0+y);
		else
			return texture_y0+y;
	}
	
	public void initMesh( ) {

	
	    Hashtable values=new Hashtable();
		
		points=new Vector();
		points.setSize(300);
		polyData=new ArrayList();
		
		n=0;
		


		double teta=(2*pi)/(N_MERIDIANS);


		int	N_PARALLELS=profile.length;


		BPoint[][] aPoints=new BPoint[N_PARALLELS][N_MERIDIANS];

			for(int j=0;j<N_PARALLELS;j++){  

				double radius=profile[j].y;

				for (int i = 0; i <N_MERIDIANS; i++) {  


					double x= (radius*Math.cos(i*teta));
					double y= (radius*Math.sin(i*teta));
					double z= profile[j].x;

					aPoints[j][i]=
							addBPoint(x,y,z);
					
					points.setElementAt(aPoints[j][i],aPoints[j][i].getIndex());

				}


			}


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		
		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();
		
		int count=0;

		for (int j = 0; j <N_MERIDIANS; j++) {

			upperBase.addIndex(aPoints[N_PARALLELS-1][j].getIndex(),count++,0,0);

			upperBase.setData(""+Renderer3D.getFace(upperBase,points));

		}	

		for (int j = N_MERIDIANS-1; j >=0; j--) {

			lowerBase.addIndex(aPoints[0][j].getIndex(),count++,0,0);

			lowerBase.setData(""+Renderer3D.getFace(lowerBase,points));
		}

		polyData.add(upperBase);
		polyData.add(lowerBase);

		for(int j=0;j<N_PARALLELS-1;j++){ 




			for (int i = 0; i <N_MERIDIANS; i++) { 


				LineData ld=new LineData();

				int texIndex=count+f(i,j,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j][i].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j][(i+1)%N_MERIDIANS].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j+1,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j+1][(i+1)%N_MERIDIANS].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i,j+1,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[j+1][i].getIndex(),texIndex,0,0);

				ld.setData(""+Renderer3D.getFace(ld,points));
				
				

				polyData.add(ld);

			}
		

		}


	}


}
