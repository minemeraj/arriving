package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.BPoint;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class Toilet extends Forniture{
	
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
	

	public Toilet( double x_side, double y_side,double z_side,int forniture_type,
			double legLength, double leg_side,double front_height, double back_height,
			double side_width,double side_length, double side_height,
			double upper_width,double upper_length, double upper_height
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.forniture_type = forniture_type;
		this.leg_length = legLength;
		this.leg_side = leg_side;
		this.back_height = back_height;
		this.front_height = front_height;
		
		this.side_width = side_width;
		this.side_length = side_length;
		this.side_height = side_height;
		
		this.upper_width = upper_width;
		this.upper_length = upper_length;
		this.upper_height = upper_height;
		
		len=2*(x_side+y_side);
		
		upperBase=new Point3D[N_FACES];
		
		upperBase[0]=new Point3D(0,y_side+z_side,0);			
		upperBase[1]=new Point3D(x_side,y_side+z_side,0);	
		upperBase[2]=new Point3D(x_side,y_side+z_side+y_side,0);	
		upperBase[3]=new Point3D(0,y_side+z_side+y_side,0);	
		
		lowerBase=new Point3D[N_FACES];
		
		lowerBase[0]=new Point3D(0,0,0);			
		lowerBase[1]=new Point3D(0,y_side,0);	
		lowerBase[2]=new Point3D(x_side,y_side,0);	
		lowerBase[3]=new Point3D(x_side,0,0);	
		

		
		lateralFaces=new Point3D[N_FACES+1][N_PARALLELS];

		for(int j=0;j<N_PARALLELS;j++){

			//texture is open and periodical:
			
			double x=0; 

			for (int i = 0; i <=N_FACES; i++) {

			
				double y=y_side+z_side*j;

				lateralFaces[i][j]=new Point3D(x,y,0);
				
				double dx=x_side;
				
				if(i%2==1)
					dx=y_side;
				
				x+=dx;

			}
			
		}
		
		initMesh();
	}
	

	
	
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

	public ArrayList buildTexturePoints() {
		
		isTextureDrawing=false;
		
		ArrayList texture_points=new ArrayList();
		


		
		
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
		

		
		points=new ArrayList();
		polyData=new ArrayList();
		
		n=0;
		
		int hnx=9;
		int hny=9;
		int hnz=9;

		BPoint[][][] body=new BPoint[hnx][hny][hnz];
		
		double xc=0;
		
		Segments h0=new Segments(xc,x_side,0,y_side,0,z_side);
		/*Segments h1=new Segments(xc,x_side,0,y_side,0,z_side);
		Segments h2=new Segments(xc,x_side,0,y_side,0,z_side);
		Segments h3=new Segments(xc,x_side,0,y_side,0,z_side);
		Segments h4=new Segments(xc,x_side,0,y_side,0,z_side);*/
	
		body[0][0][0]=addBPoint(-0.29,0.202,0.0,h0);
		body[1][0][0]=addBPoint(-0.217,0.202,0.0,h0);
		body[2][0][0]=addBPoint(-0.145,0.202,0.0,h0);
		body[3][0][0]=addBPoint(-0.073,0.202,0.0,h0);
		body[4][0][0]=addBPoint(0.0,0.202,0.0,h0);
		body[5][0][0]=addBPoint(0.073,0.202,0.0,h0);
		body[6][0][0]=addBPoint(0.145,0.202,0.0,h0);
		body[7][0][0]=addBPoint(0.217,0.202,0.0,h0);
		body[8][0][0]=addBPoint(0.29,0.202,0.0,h0);
		body[0][0][1]=addBPoint(-0.247,0.181,0.248,h0);
		body[1][0][1]=addBPoint(-0.185,0.181,0.248,h0);
		body[2][0][1]=addBPoint(-0.123,0.181,0.248,h0);
		body[3][0][1]=addBPoint(-0.062,0.181,0.248,h0);
		body[4][0][1]=addBPoint(0.0,0.181,0.248,h0);
		body[5][0][1]=addBPoint(0.062,0.181,0.248,h0);
		body[6][0][1]=addBPoint(0.123,0.181,0.248,h0);
		body[7][0][1]=addBPoint(0.185,0.181,0.248,h0);
		body[8][0][1]=addBPoint(0.247,0.181,0.248,h0);
		body[0][0][2]=addBPoint(-0.228,0.137,0.416,h0);
		body[1][0][2]=addBPoint(-0.171,0.137,0.416,h0);
		body[2][0][2]=addBPoint(-0.114,0.137,0.416,h0);
		body[3][0][2]=addBPoint(-0.057,0.137,0.416,h0);
		body[4][0][2]=addBPoint(0.0,0.137,0.416,h0);
		body[5][0][2]=addBPoint(0.057,0.137,0.416,h0);
		body[6][0][2]=addBPoint(0.114,0.137,0.416,h0);
		body[7][0][2]=addBPoint(0.171,0.137,0.416,h0);
		body[8][0][2]=addBPoint(0.228,0.137,0.416,h0);
		body[0][0][3]=addBPoint(-0.216,0.116,0.478,h0);
		body[1][0][3]=addBPoint(-0.162,0.116,0.478,h0);
		body[2][0][3]=addBPoint(-0.108,0.116,0.478,h0);
		body[3][0][3]=addBPoint(-0.054,0.116,0.478,h0);
		body[4][0][3]=addBPoint(0.0,0.116,0.478,h0);
		body[5][0][3]=addBPoint(0.054,0.116,0.478,h0);
		body[6][0][3]=addBPoint(0.108,0.116,0.478,h0);
		body[7][0][3]=addBPoint(0.162,0.116,0.478,h0);
		body[8][0][3]=addBPoint(0.216,0.116,0.478,h0);
		body[0][0][4]=addBPoint(-0.302,0.101,0.565,h0);
		body[1][0][4]=addBPoint(-0.226,0.101,0.565,h0);
		body[2][0][4]=addBPoint(-0.151,0.101,0.565,h0);
		body[3][0][4]=addBPoint(-0.076,0.101,0.565,h0);
		body[4][0][4]=addBPoint(0.0,0.101,0.565,h0);
		body[5][0][4]=addBPoint(0.076,0.101,0.565,h0);
		body[6][0][4]=addBPoint(0.151,0.101,0.565,h0);
		body[7][0][4]=addBPoint(0.226,0.101,0.565,h0);
		body[8][0][4]=addBPoint(0.302,0.101,0.565,h0);
		body[0][0][5]=addBPoint(-0.401,0.079,0.671,h0);
		body[1][0][5]=addBPoint(-0.301,0.079,0.671,h0);
		body[2][0][5]=addBPoint(-0.2,0.079,0.671,h0);
		body[3][0][5]=addBPoint(-0.1,0.079,0.671,h0);
		body[4][0][5]=addBPoint(0.0,0.079,0.671,h0);
		body[5][0][5]=addBPoint(0.1,0.079,0.671,h0);
		body[6][0][5]=addBPoint(0.2,0.079,0.671,h0);
		body[7][0][5]=addBPoint(0.301,0.079,0.671,h0);
		body[8][0][5]=addBPoint(0.401,0.079,0.671,h0);
		body[0][0][6]=addBPoint(-0.451,0.054,0.783,h0);
		body[1][0][6]=addBPoint(-0.338,0.054,0.783,h0);
		body[2][0][6]=addBPoint(-0.225,0.054,0.783,h0);
		body[3][0][6]=addBPoint(-0.113,0.054,0.783,h0);
		body[4][0][6]=addBPoint(0.0,0.054,0.783,h0);
		body[5][0][6]=addBPoint(0.113,0.054,0.783,h0);
		body[6][0][6]=addBPoint(0.225,0.054,0.783,h0);
		body[7][0][6]=addBPoint(0.338,0.054,0.783,h0);
		body[8][0][6]=addBPoint(0.451,0.054,0.783,h0);
		body[0][0][7]=addBPoint(-0.481,0.025,0.907,h0);
		body[1][0][7]=addBPoint(-0.361,0.025,0.907,h0);
		body[2][0][7]=addBPoint(-0.24,0.025,0.907,h0);
		body[3][0][7]=addBPoint(-0.12,0.025,0.907,h0);
		body[4][0][7]=addBPoint(0.0,0.025,0.907,h0);
		body[5][0][7]=addBPoint(0.12,0.025,0.907,h0);
		body[6][0][7]=addBPoint(0.24,0.025,0.907,h0);
		body[7][0][7]=addBPoint(0.361,0.025,0.907,h0);
		body[8][0][7]=addBPoint(0.481,0.025,0.907,h0);
		body[0][0][8]=addBPoint(-0.5,0.0,1.0,h0);
		body[1][0][8]=addBPoint(-0.375,0.0,1.0,h0);
		body[2][0][8]=addBPoint(-0.25,0.0,1.0,h0);
		body[3][0][8]=addBPoint(-0.125,0.0,1.0,h0);
		body[4][0][8]=addBPoint(0.0,0.0,1.0,h0);
		body[5][0][8]=addBPoint(0.125,0.0,1.0,h0);
		body[6][0][8]=addBPoint(0.25,0.0,1.0,h0);
		body[7][0][8]=addBPoint(0.375,0.0,1.0,h0);
		body[8][0][8]=addBPoint(0.5,0.0,1.0,h0);

		body[0][1][0]=addBPoint(-0.29,0.265,0.0,h0);
		body[1][1][0]=addBPoint(-0.217,0.278,0.0,h0);
		body[2][1][0]=addBPoint(-0.145,0.281,0.0,h0);
		body[3][1][0]=addBPoint(-0.073,0.283,0.0,h0);
		body[4][1][0]=addBPoint(0.0,0.284,0.0,h0);
		body[5][1][0]=addBPoint(0.073,0.283,0.0,h0);
		body[6][1][0]=addBPoint(0.145,0.281,0.0,h0);
		body[7][1][0]=addBPoint(0.217,0.278,0.0,h0);
		body[8][1][0]=addBPoint(0.29,0.265,0.0,h0);
		body[0][1][1]=addBPoint(-0.247,0.246,0.248,h0);
		body[8][1][1]=addBPoint(0.247,0.246,0.248,h0);
		body[0][1][2]=addBPoint(-0.228,0.207,0.416,h0);
		body[8][1][2]=addBPoint(0.228,0.207,0.416,h0);
		body[0][1][3]=addBPoint(-0.216,0.189,0.478,h0);
		body[8][1][3]=addBPoint(0.216,0.189,0.478,h0);
		body[0][1][4]=addBPoint(-0.302,0.179,0.565,h0);
		body[8][1][4]=addBPoint(0.302,0.179,0.565,h0);
		body[0][1][5]=addBPoint(-0.401,0.16,0.671,h0);
		body[8][1][5]=addBPoint(0.401,0.16,0.671,h0);
		body[0][1][6]=addBPoint(-0.451,0.138,0.75,h0);
		body[8][1][6]=addBPoint(0.451,0.138,0.75,h0);
		body[0][1][7]=addBPoint(-0.481,0.113,0.875,h0);
		body[8][1][7]=addBPoint(0.481,0.113,0.875,h0);
		body[0][1][8]=addBPoint(-0.5,0.093,1.0,h0);
		body[1][1][8]=addBPoint(-0.375,0.114,1.0,h0);
		body[2][1][8]=addBPoint(-0.25,0.121,1.0,h0);
		body[3][1][8]=addBPoint(-0.125,0.124,1.0,h0);
		body[4][1][8]=addBPoint(0.0,0.125,1.0,h0);
		body[5][1][8]=addBPoint(0.125,0.124,1.0,h0);
		body[6][1][8]=addBPoint(0.25,0.121,1.0,h0);
		body[7][1][8]=addBPoint(0.375,0.114,1.0,h0);
		body[8][1][8]=addBPoint(0.5,0.093,1.0,h0);

		body[0][2][0]=addBPoint(-0.29,0.328,0.0,h0);
		body[1][2][0]=addBPoint(-0.217,0.353,0.0,h0);
		body[2][2][0]=addBPoint(-0.145,0.361,0.0,h0);
		body[3][2][0]=addBPoint(-0.073,0.365,0.0,h0);
		body[4][2][0]=addBPoint(0.0,0.366,0.0,h0);
		body[5][2][0]=addBPoint(0.073,0.365,0.0,h0);
		body[6][2][0]=addBPoint(0.145,0.361,0.0,h0);
		body[7][2][0]=addBPoint(0.217,0.353,0.0,h0);
		body[8][2][0]=addBPoint(0.29,0.328,0.0,h0);
		body[0][2][1]=addBPoint(-0.247,0.311,0.248,h0);
		body[8][2][1]=addBPoint(0.247,0.311,0.248,h0);
		body[0][2][2]=addBPoint(-0.228,0.277,0.416,h0);
		body[8][2][2]=addBPoint(0.228,0.277,0.416,h0);
		body[0][2][3]=addBPoint(-0.216,0.262,0.478,h0);
		body[8][2][3]=addBPoint(0.216,0.262,0.478,h0);
		body[0][2][4]=addBPoint(-0.302,0.258,0.565,h0);
		body[8][2][4]=addBPoint(0.302,0.258,0.565,h0);
		body[0][2][5]=addBPoint(-0.401,0.241,0.671,h0);
		body[8][2][5]=addBPoint(0.401,0.241,0.671,h0);
		body[0][2][6]=addBPoint(-0.451,0.222,0.783,h0);
		body[8][2][6]=addBPoint(0.451,0.222,0.783,h0);
		body[0][2][7]=addBPoint(-0.481,0.2,0.907,h0);
		body[8][2][7]=addBPoint(0.481,0.2,0.907,h0);
		body[0][2][8]=addBPoint(-0.5,0.185,1.0,h0);
		body[1][2][8]=addBPoint(-0.375,0.228,1.0,h0);
		body[2][2][8]=addBPoint(-0.25,0.241,1.0,h0);
		body[3][2][8]=addBPoint(-0.125,0.248,1.0,h0);
		body[4][2][8]=addBPoint(0.0,0.25,1.0,h0);
		body[5][2][8]=addBPoint(0.125,0.248,1.0,h0);
		body[6][2][8]=addBPoint(0.25,0.241,1.0,h0);
		body[7][2][8]=addBPoint(0.375,0.228,1.0,h0);
		body[8][2][8]=addBPoint(0.5,0.185,1.0,h0);

		body[0][3][0]=addBPoint(-0.29,0.391,0.0,h0);
		body[1][3][0]=addBPoint(-0.217,0.428,0.0,h0);
		body[2][3][0]=addBPoint(-0.145,0.439,0.0,h0);
		body[3][3][0]=addBPoint(-0.073,0.445,0.0,h0);
		body[4][3][0]=addBPoint(0.0,0.447,0.0,h0);
		body[5][3][0]=addBPoint(0.073,0.445,0.0,h0);
		body[6][3][0]=addBPoint(0.145,0.439,0.0,h0);
		body[7][3][0]=addBPoint(0.217,0.428,0.0,h0);
		body[8][3][0]=addBPoint(0.29,0.391,0.0,h0);
		body[0][3][1]=addBPoint(-0.247,0.376,0.248,h0);
		body[8][3][1]=addBPoint(0.247,0.376,0.248,h0);
		body[0][3][2]=addBPoint(-0.228,0.347,0.416,h0);
		body[8][3][2]=addBPoint(0.228,0.347,0.416,h0);
		body[0][3][3]=addBPoint(-0.216,0.335,0.478,h0);
		body[8][3][3]=addBPoint(0.216,0.335,0.478,h0);
		body[0][3][4]=addBPoint(-0.302,0.336,0.565,h0);
		body[8][3][4]=addBPoint(0.302,0.336,0.565,h0);
		body[0][3][5]=addBPoint(-0.401,0.322,0.671,h0);
		body[8][3][5]=addBPoint(0.401,0.322,0.671,h0);
		body[0][3][6]=addBPoint(-0.451,0.306,0.783,h0);
		body[8][3][6]=addBPoint(0.451,0.306,0.783,h0);
		body[0][3][7]=addBPoint(-0.481,0.287,0.907,h0);
		body[8][3][7]=addBPoint(0.481,0.287,0.907,h0);
		body[0][3][8]=addBPoint(-0.5,0.278,1.0,h0);
		body[1][3][8]=addBPoint(-0.375,0.342,1.0,h0);
		body[2][3][8]=addBPoint(-0.25,0.347,0.6,h0);
		body[3][3][8]=addBPoint(-0.125,0.347,0.5,h0);
		body[4][3][8]=addBPoint(0.0,0.347,0.5,h0);
		body[5][3][8]=addBPoint(0.125,0.347,0.5,h0);
		body[6][3][8]=addBPoint(0.25,0.347,0.6,h0);
		body[7][3][8]=addBPoint(0.375,0.342,1.0,h0);
		body[8][3][8]=addBPoint(0.5,0.278,1.0,h0);

		body[0][4][0]=addBPoint(-0.29,0.454,0.0,h0);
		body[1][4][0]=addBPoint(-0.217,0.504,0.0,h0);
		body[2][4][0]=addBPoint(-0.145,0.519,0.0,h0);
		body[3][4][0]=addBPoint(-0.073,0.527,0.0,h0);
		body[4][4][0]=addBPoint(0.0,0.529,0.0,h0);
		body[5][4][0]=addBPoint(0.073,0.527,0.0,h0);
		body[6][4][0]=addBPoint(0.145,0.519,0.0,h0);
		body[7][4][0]=addBPoint(0.217,0.504,0.0,h0);
		body[8][4][0]=addBPoint(0.29,0.454,0.0,h0);
		body[0][4][1]=addBPoint(-0.247,0.442,0.248,h0);
		body[8][4][1]=addBPoint(0.247,0.442,0.248,h0);
		body[0][4][2]=addBPoint(-0.228,0.418,0.416,h0);
		body[8][4][2]=addBPoint(0.228,0.418,0.416,h0);
		body[0][4][3]=addBPoint(-0.216,0.408,0.478,h0);
		body[8][4][3]=addBPoint(0.216,0.408,0.478,h0);
		body[0][4][4]=addBPoint(-0.302,0.414,0.565,h0);
		body[8][4][4]=addBPoint(0.302,0.414,0.565,h0);
		body[0][4][5]=addBPoint(-0.401,0.403,0.671,h0);
		body[8][4][5]=addBPoint(0.401,0.403,0.671,h0);
		body[0][4][6]=addBPoint(-0.451,0.39,0.783,h0);
		body[8][4][6]=addBPoint(0.451,0.39,0.783,h0);
		body[0][4][7]=addBPoint(-0.481,0.375,0.907,h0);
		body[8][4][7]=addBPoint(0.481,0.375,0.907,h0);
		body[0][4][8]=addBPoint(-0.5,0.37,1.0,h0);
		body[1][4][8]=addBPoint(-0.375,0.456,1.0,h0);
		body[2][4][8]=addBPoint(-0.25,0.408,0.7,h0);
		body[3][4][8]=addBPoint(-0.125,0.408,0.25,h0);
		body[4][4][8]=addBPoint(0.0,0.408,0.25,h0);
		body[5][4][8]=addBPoint(0.125,0.408,0.25,h0);
		body[6][4][8]=addBPoint(0.25,0.408,0.7,h0);
		body[7][4][8]=addBPoint(0.375,0.456,1.0,h0);
		body[8][4][8]=addBPoint(0.5,0.37,1.0,h0);

		body[0][5][0]=addBPoint(-0.29,0.517,0.0,h0);
		body[1][5][0]=addBPoint(-0.217,0.579,0.0,h0);
		body[2][5][0]=addBPoint(-0.145,0.598,0.0,h0);
		body[3][5][0]=addBPoint(-0.073,0.608,0.0,h0);
		body[4][5][0]=addBPoint(0.0,0.611,0.0,h0);
		body[5][5][0]=addBPoint(0.073,0.608,0.0,h0);
		body[6][5][0]=addBPoint(0.145,0.598,0.0,h0);
		body[7][5][0]=addBPoint(0.217,0.579,0.0,h0);
		body[8][5][0]=addBPoint(0.29,0.517,0.0,h0);
		body[0][5][1]=addBPoint(-0.247,0.507,0.248,h0);
		body[8][5][1]=addBPoint(0.247,0.507,0.248,h0);
		body[0][5][2]=addBPoint(-0.228,0.487,0.416,h0);
		body[8][5][2]=addBPoint(0.228,0.487,0.416,h0);
		body[0][5][3]=addBPoint(-0.216,0.481,0.478,h0);
		body[8][5][3]=addBPoint(0.216,0.481,0.478,h0);
		body[0][5][4]=addBPoint(-0.302,0.492,0.565,h0);
		body[8][5][4]=addBPoint(0.302,0.492,0.565,h0);
		body[0][5][5]=addBPoint(-0.401,0.484,0.671,h0);
		body[8][5][5]=addBPoint(0.401,0.484,0.671,h0);
		body[0][5][6]=addBPoint(-0.451,0.473,0.783,h0);
		body[8][5][6]=addBPoint(0.451,0.473,0.783,h0);
		body[0][5][7]=addBPoint(-0.481,0.463,0.907,h0);
		body[8][5][7]=addBPoint(0.481,0.463,0.907,h0);
		body[0][5][8]=addBPoint(-0.5,0.463,1.0,h0);
		body[1][5][8]=addBPoint(-0.375,0.57,1.0,h0);
		body[2][5][8]=addBPoint(-0.25,0.481,0.7,h0);
		body[3][5][8]=addBPoint(-0.125,0.481,0.25,h0);
		body[4][5][8]=addBPoint(0.0,0.481,0.25,h0);
		body[5][5][8]=addBPoint(0.125,0.481,0.25,h0);
		body[6][5][8]=addBPoint(0.25,0.481,0.7,h0);
		body[7][5][8]=addBPoint(0.375,0.57,1.0,h0);
		body[8][5][8]=addBPoint(0.5,0.463,1.0,h0);

		body[0][6][0]=addBPoint(-0.29,0.579,0.0,h0);
		body[1][6][0]=addBPoint(-0.217,0.654,0.0,h0);
		body[2][6][0]=addBPoint(-0.145,0.677,0.0,h0);
		body[3][6][0]=addBPoint(-0.073,0.688,0.0,h0);
		body[4][6][0]=addBPoint(0.0,0.692,0.0,h0);
		body[5][6][0]=addBPoint(0.073,0.688,0.0,h0);
		body[6][6][0]=addBPoint(0.145,0.677,0.0,h0);
		body[7][6][0]=addBPoint(0.217,0.654,0.0,h0);
		body[8][6][0]=addBPoint(0.29,0.579,0.0,h0);
		body[0][6][1]=addBPoint(-0.247,0.572,0.248,h0);
		body[8][6][1]=addBPoint(0.247,0.572,0.248,h0);
		body[0][6][2]=addBPoint(-0.228,0.557,0.416,h0);
		body[8][6][2]=addBPoint(0.228,0.557,0.416,h0);
		body[0][6][3]=addBPoint(-0.216,0.554,0.478,h0);
		body[8][6][3]=addBPoint(0.216,0.554,0.478,h0);
		body[0][6][4]=addBPoint(-0.302,0.57,0.565,h0);
		body[8][6][4]=addBPoint(0.302,0.57,0.565,h0);
		body[0][6][5]=addBPoint(-0.401,0.565,0.671,h0);
		body[8][6][5]=addBPoint(0.401,0.565,0.671,h0);
		body[0][6][6]=addBPoint(-0.451,0.558,0.783,h0);
		body[8][6][6]=addBPoint(0.451,0.558,0.783,h0);
		body[0][6][7]=addBPoint(-0.481,0.55,0.907,h0);
		body[8][6][7]=addBPoint(0.481,0.55,0.907,h0);
		body[0][6][8]=addBPoint(-0.5,0.555,1.0,h0);
		body[1][6][8]=addBPoint(-0.375,0.684,1.0,h0);
		body[2][6][8]=addBPoint(-0.25,0.554,0.75,h0);
		body[3][6][8]=addBPoint(-0.125,0.554,0.75,h0);
		body[4][6][8]=addBPoint(0.0,0.554,0.75,h0);
		body[5][6][8]=addBPoint(0.125,0.554,0.75,h0);
		body[6][6][8]=addBPoint(0.25,0.554,0.75,h0);
		body[7][6][8]=addBPoint(0.375,0.684,1.0,h0);
		body[8][6][8]=addBPoint(0.5,0.555,1.0,h0);

		body[0][7][0]=addBPoint(-0.29,0.642,0.0,h0);
		body[1][7][0]=addBPoint(-0.217,0.73,0.0,h0);
		body[2][7][0]=addBPoint(-0.145,0.756,0.0,h0);
		body[3][7][0]=addBPoint(-0.073,0.77,0.0,h0);
		body[4][7][0]=addBPoint(0.0,0.774,0.0,h0);
		body[5][7][0]=addBPoint(0.073,0.77,0.0,h0);
		body[6][7][0]=addBPoint(0.145,0.756,0.0,h0);
		body[7][7][0]=addBPoint(0.217,0.73,0.0,h0);
		body[8][7][0]=addBPoint(0.29,0.642,0.0,h0);
		body[0][7][1]=addBPoint(-0.247,0.637,0.248,h0);
		body[8][7][1]=addBPoint(0.247,0.637,0.248,h0);
		body[0][7][2]=addBPoint(-0.228,0.627,0.416,h0);
		body[8][7][2]=addBPoint(0.228,0.627,0.416,h0);
		body[0][7][3]=addBPoint(-0.216,0.627,0.478,h0);
		body[8][7][3]=addBPoint(0.216,0.627,0.478,h0);
		body[0][7][4]=addBPoint(-0.302,0.649,0.565,h0);
		body[8][7][4]=addBPoint(0.302,0.649,0.565,h0);
		body[0][7][5]=addBPoint(-0.401,0.646,0.671,h0);
		body[8][7][5]=addBPoint(0.401,0.646,0.671,h0);
		body[0][7][6]=addBPoint(-0.451,0.642,0.783,h0);
		body[8][7][6]=addBPoint(0.451,0.642,0.783,h0);
		body[0][7][7]=addBPoint(-0.481,0.637,0.907,h0);
		body[8][7][7]=addBPoint(0.481,0.637,0.907,h0);
		body[0][7][8]=addBPoint(-0.5,0.648,1.0,h0);
		body[1][7][8]=addBPoint(-0.375,0.798,1.0,h0);
		body[2][7][8]=addBPoint(-0.25,0.845,1.0,h0);
		body[3][7][8]=addBPoint(-0.125,0.868,1.0,h0);
		body[4][7][8]=addBPoint(0.0,0.875,1.0,h0);
		body[5][7][8]=addBPoint(0.125,0.868,1.0,h0);
		body[6][7][8]=addBPoint(0.25,0.845,1.0,h0);
		body[7][7][8]=addBPoint(0.375,0.798,1.0,h0);
		body[8][7][8]=addBPoint(0.5,0.648,1.0,h0);

		body[0][8][0]=addBPoint(-0.29,0.705,0.0,h0);
		body[1][8][0]=addBPoint(-0.217,0.805,0.0,h0);
		body[2][8][0]=addBPoint(-0.145,0.836,0.0,h0);
		body[3][8][0]=addBPoint(-0.073,0.851,0.0,h0);
		body[4][8][0]=addBPoint(0.0,0.856,0.0,h0);
		body[5][8][0]=addBPoint(0.073,0.851,0.0,h0);
		body[6][8][0]=addBPoint(0.145,0.836,0.0,h0);
		body[7][8][0]=addBPoint(0.217,0.805,0.0,h0);
		body[8][8][0]=addBPoint(0.29,0.705,0.0,h0);
		body[0][8][1]=addBPoint(-0.247,0.702,0.248,h0);
		body[1][8][1]=addBPoint(-0.185,0.787,0.248,h0);
		body[2][8][1]=addBPoint(-0.123,0.813,0.248,h0);
		body[3][8][1]=addBPoint(-0.062,0.826,0.248,h0);
		body[4][8][1]=addBPoint(0.0,0.83,0.248,h0);
		body[5][8][1]=addBPoint(0.062,0.826,0.248,h0);
		body[6][8][1]=addBPoint(0.123,0.813,0.248,h0);
		body[7][8][1]=addBPoint(0.185,0.787,0.248,h0);
		body[8][8][1]=addBPoint(0.247,0.702,0.248,h0);
		body[0][8][2]=addBPoint(-0.228,0.697,0.416,h0);
		body[1][8][2]=addBPoint(-0.171,0.776,0.416,h0);
		body[2][8][2]=addBPoint(-0.114,0.8,0.416,h0);
		body[3][8][2]=addBPoint(-0.057,0.812,0.416,h0);
		body[4][8][2]=addBPoint(0.0,0.816,0.416,h0);
		body[5][8][2]=addBPoint(0.057,0.812,0.416,h0);
		body[6][8][2]=addBPoint(0.114,0.8,0.416,h0);
		body[7][8][2]=addBPoint(0.171,0.776,0.416,h0);
		body[8][8][2]=addBPoint(0.228,0.697,0.416,h0);
		body[0][8][3]=addBPoint(-0.216,0.7,0.478,h0);
		body[1][8][3]=addBPoint(-0.162,0.774,0.478,h0);
		body[2][8][3]=addBPoint(-0.108,0.797,0.478,h0);
		body[3][8][3]=addBPoint(-0.054,0.808,0.478,h0);
		body[4][8][3]=addBPoint(0.0,0.812,0.478,h0);
		body[5][8][3]=addBPoint(0.054,0.808,0.478,h0);
		body[6][8][3]=addBPoint(0.108,0.797,0.478,h0);
		body[7][8][3]=addBPoint(0.162,0.774,0.478,h0);
		body[8][8][3]=addBPoint(0.216,0.7,0.478,h0);
		body[0][8][4]=addBPoint(-0.302,0.727,0.565,h0);
		body[1][8][4]=addBPoint(-0.226,0.831,0.565,h0);
		body[2][8][4]=addBPoint(-0.151,0.863,0.565,h0);
		body[3][8][4]=addBPoint(-0.076,0.879,0.565,h0);
		body[4][8][4]=addBPoint(0.0,0.884,0.565,h0);
		body[5][8][4]=addBPoint(0.076,0.879,0.565,h0);
		body[6][8][4]=addBPoint(0.151,0.863,0.565,h0);
		body[7][8][4]=addBPoint(0.226,0.831,0.565,h0);
		body[8][8][4]=addBPoint(0.302,0.727,0.565,h0);
		body[0][8][5]=addBPoint(-0.401,0.726,0.671,h0);
		body[1][8][5]=addBPoint(-0.301,0.864,0.671,h0);
		body[2][8][5]=addBPoint(-0.2,0.907,0.671,h0);
		body[3][8][5]=addBPoint(-0.1,0.928,0.671,h0);
		body[4][8][5]=addBPoint(0.0,0.935,0.671,h0);
		body[5][8][5]=addBPoint(0.1,0.928,0.671,h0);
		body[6][8][5]=addBPoint(0.2,0.907,0.671,h0);
		body[7][8][5]=addBPoint(0.301,0.864,0.671,h0);
		body[8][8][5]=addBPoint(0.401,0.726,0.671,h0);
		body[0][8][6]=addBPoint(-0.451,0.725,0.783,h0);
		body[1][8][6]=addBPoint(-0.338,0.881,0.783,h0);
		body[2][8][6]=addBPoint(-0.225,0.929,0.783,h0);
		body[3][8][6]=addBPoint(-0.113,0.953,0.783,h0);
		body[4][8][6]=addBPoint(0.0,0.96,0.783,h0);
		body[5][8][6]=addBPoint(0.113,0.953,0.783,h0);
		body[6][8][6]=addBPoint(0.225,0.929,0.783,h0);
		body[7][8][6]=addBPoint(0.338,0.881,0.783,h0);
		body[8][8][6]=addBPoint(0.451,0.725,0.783,h0);
		body[0][8][7]=addBPoint(-0.481,0.725,0.907,h0);
		body[1][8][7]=addBPoint(-0.361,0.89,0.907,h0);
		body[2][8][7]=addBPoint(-0.24,0.942,0.907,h0);
		body[3][8][7]=addBPoint(-0.12,0.967,0.907,h0);
		body[4][8][7]=addBPoint(0.0,0.975,0.907,h0);
		body[5][8][7]=addBPoint(0.12,0.967,0.907,h0);
		body[6][8][7]=addBPoint(0.24,0.942,0.907,h0);
		body[7][8][7]=addBPoint(0.361,0.89,0.907,h0);
		body[8][8][7]=addBPoint(0.481,0.725,0.907,h0);
		body[0][8][8]=addBPoint(-0.5,0.74,1.0,h0);
		body[1][8][8]=addBPoint(-0.375,0.912,1.0,h0);
		body[2][8][8]=addBPoint(-0.25,0.965,1.0,h0);
		body[3][8][8]=addBPoint(-0.125,0.992,1.0,h0);
		body[4][8][8]=addBPoint(0.0,1.0,1.0,h0);
		body[5][8][8]=addBPoint(0.125,0.992,1.0,h0);
		body[6][8][8]=addBPoint(0.25,0.965,1.0,h0);
		body[7][8][8]=addBPoint(0.375,0.912,1.0,h0);
		body[8][8][8]=addBPoint(0.5,0.74,1.0,h0);

		//**********
		

		for (int i = 0;  i < hnx-1; i++) {			

		

			for (int j = 0; j < hny-1; j++) {
				
		
				for (int k = 0; k < hnz-1; k++) {			
					
					//if(j!=5) continue;

					if(j==0){


						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					}



                   // System.out.println(i+" "+j+" "+k); 

					if(k+1==hnz-1)
						addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);

					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);

					if(i==0)
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);

					if(i+1==hnx-1)
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);


					if(j+1==hny-1){

						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);

					}

				}

			}


		}


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		

	}
	
}
