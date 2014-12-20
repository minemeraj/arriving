package com.editors.animals.data;

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
import com.Segments;
import com.editors.DoubleTextField;
import com.main.Renderer3D;

public class ManHead extends Animal{
	
	public static int texture_side_dx=10;
	public static int texture_side_dy=10;

	public static int texture_x0=10;
	public static int texture_y0=10;
	public static int IMG_WIDTH;
	public static int IMG_HEIGHT;
	
	public static boolean isTextureDrawing=false;
	
	private double len;
	
	Point3D[][] upperBase=null;
	Point3D[][] lowerBase=null;
	Point3D[][] lateralFaces=null; 
	

	int numx=9;
	int numy=5;
	int numz=9;
	
	int N_FACES=numx*2+(numy-2)*2;
	


	public ManHead(double x_side, double y_side,double z_side,int animal_type,
			double femur_length,double shinbone_length,double leg_side,
			double head_DZ,double head_DX,double head_DY,double neck_length,double neck_side,
			double humerus_length,double radius_length,double hand_length,double foot_length
			) {
		super();

		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.animal_type = animal_type;

		this.head_DZ = head_DZ;
		this.head_DX = head_DX;
		this.head_DY = head_DY;
		this.neck_length = neck_length; 
		this.neck_side = neck_side;
		
		this.humerus_length = humerus_length;
		this.radius_length = radius_length;
		this.foot_length = foot_length;
		
		this.femur_length = femur_length;
		this.shinbone_length = shinbone_length;
		this.leg_side = leg_side;
		
		this.hand_length = hand_length;
		this.foot_length = foot_length;
		
		len=2*(head_DX+head_DY);
		
		
		texture_side_dy=(int) (head_DY/(numy-1));
		texture_side_dx=(int) (head_DX/(numx-1));
		
		upperBase=new Point3D[numx][numy];
		
		double baseY=head_DZ+head_DY;
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
				
				upperBase[i][j]=new Point3D(texture_side_dx*i,texture_side_dy*j+baseY,0);
			}
			
		}
	
		
		lowerBase=new Point3D[numx][numy];
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
				
				lowerBase[i][j]=new Point3D(texture_side_dx*i,texture_side_dy*j,0);
			}
			
		}
		
		texture_side_dy=(int) (head_DZ/(numz-1));
		texture_side_dx=(int) (len/(N_FACES));
		
		lateralFaces=new Point3D[N_FACES+1][numz];

		for(int j=0;j<numz;j++){

			//texture is open and periodical:
			
			double x=0; 

			for (int i = 0; i <=N_FACES; i++) {

			
				double y=head_DY+texture_side_dy*j;

				lateralFaces[i][j]=new Point3D(x,y,0);
				
				double dx=texture_side_dx;
				
				if(i%2==1)
					dx=texture_side_dy;
				
				x+=dx;

			}
			
		}	
		
		initMesh();
	}
	

	
	
public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
		
		isTextureDrawing=true;

	
		

		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) (head_DZ+head_DY*2)+2*texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {


				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);


				//draw lines for reference

				bufGraphics.setColor(Color.RED);
				bufGraphics.setStroke(new BasicStroke(0.1f));
				
				for (int i = 0; i <numx; i++) {
					
					for (int j = 0; j < numy; j++) {

						double x0= calX(upperBase[i][j].x);
						double y0= calY(upperBase[i][j].y);
						
						double x1= calX(upperBase[(i+1)%numx][j].x);
						double y1= calY(upperBase[(i+1)%numx][j].y);
						
						double x2= calX(upperBase[(i+1)%numx][(j+1)%numy].x);
						double y2= calY(upperBase[(i+1)%numx][(j+1)%numy].y);
						
						double x3= calX(upperBase[i][(j+1)%numy].x);
						double y3= calY(upperBase[i][(j+1)%numy].y);
	
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 
						bufGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);	 
						bufGraphics.drawLine((int)x2,(int)y2,(int)x3,(int)y3);	 
						bufGraphics.drawLine((int)x3,(int)y3,(int)x0,(int)y0);	 

					}
				}
				
				//lowerbase
		
				bufGraphics.setColor(Color.BLUE);
				

				for (int i = 0; i <numx; i++) {
					
					for (int j = 0; j < numy; j++) {

						double x0= calX(lowerBase[i][j].x);
						double y0= calY(lowerBase[i][j].y);
						
						double x1= calX(lowerBase[(i+1)%numx][j].x);
						double y1= calY(lowerBase[(i+1)%numx][j].y);
						
						double x2= calX(lowerBase[(i+1)%numx][(j+1)%numy].x);
						double y2= calY(lowerBase[(i+1)%numx][(j+1)%numy].y);
						
						double x3= calX(lowerBase[i][(j+1)%numy].x);
						double y3= calY(lowerBase[i][(j+1)%numy].y);
	
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 
						bufGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);	 
						bufGraphics.drawLine((int)x2,(int)y2,(int)x3,(int)y3);	 
						bufGraphics.drawLine((int)x3,(int)y3,(int)x0,(int)y0);	 		
					}
				}


				//lateral surface
				bufGraphics.setColor(new Color(0,0,0));


				for(int j=0;j<numz-1;j++){

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
		


		//upperbase

		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
			
				
				double x= calX(upperBase[i][j].x);
				double y= calY(upperBase[i][j].y);
	
				Point3D p=new Point3D(x,y,0);			
				texture_points.add(p);
			}

		}
	


		
		
	

		//lateral surface


		for(int j=0;j<numz;j++){

			//texture is open and periodical:

			for (int i = 0; i <=N_FACES; i++) {

				double x=calX(lateralFaces[i][j].x);
				double y=calY(lateralFaces[i][j].y);

				Point3D p=new Point3D(x,y,0);

			
				//System.out.print(texIndex+"\t");
				texture_points.add(p);
			}
			
		}	
		
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {

				double x= calX(lowerBase[i][j].x);
				double y= calY(lowerBase[i][j].y);
	
				Point3D p=new Point3D(x,y,0);			
				texture_points.add(p);
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
		points.setSize(400);

		polyData=new Vector();

		n=0;
		
		double xc=x_side/2.0;
		double yc=y_side/2.0;
	
		Segments n0=new Segments(xc,head_DX,yc,head_DY,0,head_DZ);
		Segments h0=new Segments(xc,head_DX,yc,head_DY,0,head_DZ);
		
		BPoint[][][] head=new BPoint[numx][numy][numz];
		
		

		head[0][0][0]=addBPoint(-0.02,-0.358,0.0,n0);
		head[1][0][0]=addBPoint(-0.015,-0.358,0.0,n0);
		head[2][0][0]=addBPoint(-0.01,-0.358,0.0,n0);
		head[3][0][0]=addBPoint(-0.005,-0.358,0.0,n0);
		head[4][0][0]=addBPoint(0.0,-0.358,0.0,n0);
		head[5][0][0]=addBPoint(0.005,-0.358,0.0,n0);
		head[6][0][0]=addBPoint(0.01,-0.358,0.0,n0);
		head[7][0][0]=addBPoint(0.015,-0.358,0.0,n0);
		head[8][0][0]=addBPoint(0.02,-0.358,0.0,n0);
		head[0][1][0]=addBPoint(-0.02,-0.179,0.0,n0);
		head[1][1][0]=addBPoint(-0.015,-0.179,0.0,n0);
		head[2][1][0]=addBPoint(-0.01,-0.179,0.0,n0);
		head[3][1][0]=addBPoint(-0.005,-0.179,0.0,n0);
		head[4][1][0]=addBPoint(0.0,-0.179,0.0,n0);
		head[5][1][0]=addBPoint(0.005,-0.179,0.0,n0);
		head[6][1][0]=addBPoint(0.01,-0.179,0.0,n0);
		head[7][1][0]=addBPoint(0.015,-0.179,0.0,n0);
		head[8][1][0]=addBPoint(0.02,-0.179,0.0,n0);
		head[0][2][0]=addBPoint(-0.02,0.0,0.0,n0);
		head[1][2][0]=addBPoint(-0.015,0.0,0.0,n0);
		head[2][2][0]=addBPoint(-0.01,0.0,0.0,n0);
		head[3][2][0]=addBPoint(-0.005,0.0,0.0,n0);
		head[4][2][0]=addBPoint(0.0,0.0,0.0,n0);
		head[5][2][0]=addBPoint(0.005,0.0,0.0,n0);
		head[6][2][0]=addBPoint(0.01,0.0,0.0,n0);
		head[7][2][0]=addBPoint(0.015,0.0,0.0,n0);
		head[8][2][0]=addBPoint(0.02,0.0,0.0,n0);
		head[0][3][0]=addBPoint(-0.02,0.209,0.0,n0);
		head[1][3][0]=addBPoint(-0.015,0.209,0.0,n0);
		head[2][3][0]=addBPoint(-0.01,0.209,0.0,n0);
		head[3][3][0]=addBPoint(-0.005,0.209,0.0,n0);
		head[4][3][0]=addBPoint(0.0,0.209,0.0,n0);
		head[5][3][0]=addBPoint(0.005,0.209,0.0,n0);
		head[6][3][0]=addBPoint(0.01,0.209,0.0,n0);
		head[7][3][0]=addBPoint(0.015,0.209,0.0,n0);
		head[8][3][0]=addBPoint(0.02,0.209,0.0,n0);
		head[0][4][0]=addBPoint(-0.02,0.418,0.0,n0);
		head[1][4][0]=addBPoint(-0.015,0.418,0.0,n0);
		head[2][4][0]=addBPoint(-0.01,0.418,0.0,n0);
		head[3][4][0]=addBPoint(-0.005,0.418,0.0,n0);
		head[4][4][0]=addBPoint(0.0,0.418,0.0,n0);
		head[5][4][0]=addBPoint(0.005,0.418,0.0,n0);
		head[6][4][0]=addBPoint(0.01,0.418,0.0,n0);
		head[7][4][0]=addBPoint(0.015,0.418,0.0,n0);
		head[8][4][0]=addBPoint(0.02,0.418,0.0,n0);

		head[0][0][1]=addBPoint(-0.153,-0.347,0.022,n0);
		head[1][0][1]=addBPoint(-0.115,-0.348,0.022,n0);
		head[2][0][1]=addBPoint(-0.077,-0.35,0.022,n0);
		head[3][0][1]=addBPoint(-0.038,-0.35,0.022,n0);
		head[4][0][1]=addBPoint(0.0,-0.351,0.022,n0);
		head[5][0][1]=addBPoint(0.039,-0.35,0.022,n0);
		head[6][0][1]=addBPoint(0.077,-0.35,0.022,n0);
		head[7][0][1]=addBPoint(0.115,-0.348,0.022,n0);
		head[8][0][1]=addBPoint(0.153,-0.347,0.022,n0);
		head[0][1][1]=addBPoint(-0.153,-0.173,0.022,n0);
		head[8][1][1]=addBPoint(0.153,-0.173,0.022,n0);
		head[0][2][1]=addBPoint(-0.153,0.0,0.022,n0);
		head[8][2][1]=addBPoint(0.153,0.0,0.022,n0);
		head[0][3][1]=addBPoint(-0.153,0.225,0.022,n0);
		head[8][3][1]=addBPoint(0.153,0.225,0.022,n0);
		head[0][4][1]=addBPoint(-0.153,0.45,0.022,n0);
		head[1][4][1]=addBPoint(-0.115,0.452,0.022,n0);
		head[2][4][1]=addBPoint(-0.077,0.454,0.022,n0);
		head[3][4][1]=addBPoint(-0.038,0.455,0.022,n0);
		head[4][4][1]=addBPoint(0.0,0.455,0.022,n0);
		head[5][4][1]=addBPoint(0.039,0.455,0.022,n0);
		head[6][4][1]=addBPoint(0.077,0.454,0.022,n0);
		head[7][4][1]=addBPoint(0.115,0.452,0.022,n0);
		head[8][4][1]=addBPoint(0.153,0.45,0.022,n0);

		head[0][0][2]=addBPoint(-0.337,-0.307,0.11,n0);
		head[1][0][2]=addBPoint(-0.253,-0.315,0.11,n0);
		head[2][0][2]=addBPoint(-0.169,-0.323,0.11,n0);
		head[3][0][2]=addBPoint(-0.084,-0.326,0.11,n0);
		head[4][0][2]=addBPoint(0.0,-0.328,0.11,n0);
		head[5][0][2]=addBPoint(0.085,-0.326,0.11,n0);
		head[6][0][2]=addBPoint(0.169,-0.323,0.11,n0);
		head[7][0][2]=addBPoint(0.253,-0.315,0.11,n0);
		head[8][0][2]=addBPoint(0.337,-0.307,0.11,n0);
		head[0][1][2]=addBPoint(-0.337,-0.153,0.11,n0);
		head[8][1][2]=addBPoint(0.337,-0.153,0.11,n0);
		head[0][2][2]=addBPoint(-0.337,0.0,0.11,n0);
		head[8][2][2]=addBPoint(0.337,0.0,0.11,n0);
		head[0][3][2]=addBPoint(-0.337,0.213,0.11,n0);
		head[8][3][2]=addBPoint(0.337,0.213,0.11,n0);
		head[0][4][2]=addBPoint(-0.337,0.425,0.11,n0);
		head[1][4][2]=addBPoint(-0.253,0.437,0.11,n0);
		head[2][4][2]=addBPoint(-0.169,0.448,0.11,n0);
		head[3][4][2]=addBPoint(-0.084,0.452,0.11,n0);
		head[4][4][2]=addBPoint(0.0,0.455,0.11,n0);
		head[5][4][2]=addBPoint(0.085,0.452,0.11,n0);
		head[6][4][2]=addBPoint(0.169,0.448,0.11,n0);
		head[7][4][2]=addBPoint(0.253,0.437,0.11,n0);
		head[8][4][2]=addBPoint(0.337,0.425,0.11,n0);

		head[0][0][3]=addBPoint(-0.462,-0.309,0.287,n0);
		head[1][0][3]=addBPoint(-0.347,-0.332,0.287,n0);
		head[2][0][3]=addBPoint(-0.231,-0.356,0.287,n0);
		head[3][0][3]=addBPoint(-0.115,-0.361,0.287,n0);
		head[4][0][3]=addBPoint(0.0,-0.366,0.287,n0);
		head[5][0][3]=addBPoint(0.116,-0.361,0.287,n0);
		head[6][0][3]=addBPoint(0.231,-0.356,0.287,n0);
		head[7][0][3]=addBPoint(0.347,-0.332,0.287,n0);
		head[8][0][3]=addBPoint(0.462,-0.309,0.287,n0);
		head[0][1][3]=addBPoint(-0.462,-0.154,0.287,n0);
		head[8][1][3]=addBPoint(0.462,-0.154,0.287,n0);
		head[0][2][3]=addBPoint(-0.462,0.0,0.287,n0);
		head[8][2][3]=addBPoint(0.462,0.0,0.287,n0);
		head[0][3][3]=addBPoint(-0.462,0.212,0.287,n0);
		head[8][3][3]=addBPoint(0.462,0.212,0.287,n0);
		head[0][4][3]=addBPoint(-0.462,0.423,0.287,n0);
		head[1][4][3]=addBPoint(-0.288,0.455,0.287,n0);
		head[2][4][3]=addBPoint(-0.114,0.486,0.287,n0);
		head[3][4][3]=addBPoint(-0.057,0.493,0.287,n0);
		head[4][4][3]=addBPoint(0.0,0.5,0.287,n0);
		head[5][4][3]=addBPoint(0.057,0.493,0.287,n0);
		head[6][4][3]=addBPoint(0.114,0.486,0.287,n0);
		head[7][4][3]=addBPoint(0.288,0.455,0.287,n0);
		head[8][4][3]=addBPoint(0.462,0.423,0.287,n0);

		head[0][0][4]=addBPoint(-0.5,-0.352,0.471,n0);
		head[1][0][4]=addBPoint(-0.375,-0.403,0.471,n0);
		head[2][0][4]=addBPoint(-0.25,-0.454,0.471,n0);
		head[3][0][4]=addBPoint(-0.125,-0.462,0.471,n0);
		head[4][0][4]=addBPoint(0.0,-0.47,0.471,n0);
		head[5][0][4]=addBPoint(0.125,-0.462,0.471,n0);
		head[6][0][4]=addBPoint(0.25,-0.454,0.471,n0);
		head[7][0][4]=addBPoint(0.375,-0.403,0.471,n0);
		head[8][0][4]=addBPoint(0.5,-0.352,0.471,n0);
		head[0][1][4]=addBPoint(-0.5,-0.176,0.471,n0);
		head[8][1][4]=addBPoint(0.5,-0.176,0.471,n0);
		head[0][2][4]=addBPoint(-0.5,0.0,0.471,n0);
		head[8][2][4]=addBPoint(0.5,0.0,0.471,n0);
		head[0][3][4]=addBPoint(-0.5,0.14,0.471,n0);
		head[8][3][4]=addBPoint(0.5,0.14,0.471,n0);
		head[0][4][4]=addBPoint(-0.5,0.28,0.471,n0);
		head[1][4][4]=addBPoint(-0.28,0.321,0.471,n0);
		head[2][4][4]=addBPoint(-0.06,0.361,0.471,n0);
		head[3][4][4]=addBPoint(-0.03,0.367,0.471,n0);
		head[4][4][4]=addBPoint(0.0,0.373,0.471,n0);
		head[5][4][4]=addBPoint(0.03,0.367,0.471,n0);
		head[6][4][4]=addBPoint(0.06,0.361,0.471,n0);
		head[7][4][4]=addBPoint(0.28,0.321,0.471,n0);
		head[8][4][4]=addBPoint(0.5,0.28,0.471,n0);

		head[0][0][5]=addBPoint(-0.5,-0.375,0.625,n0);
		head[1][0][5]=addBPoint(-0.375,-0.429,0.625,n0);
		head[2][0][5]=addBPoint(-0.25,-0.483,0.625,n0);
		head[3][0][5]=addBPoint(-0.125,-0.491,0.625,n0);
		head[4][0][5]=addBPoint(0.0,-0.5,0.625,n0);
		head[5][0][5]=addBPoint(0.125,-0.491,0.625,n0);
		head[6][0][5]=addBPoint(0.25,-0.483,0.625,n0);
		head[7][0][5]=addBPoint(0.375,-0.429,0.625,n0);
		head[8][0][5]=addBPoint(0.5,-0.375,0.625,n0);
		head[0][1][5]=addBPoint(-0.5,-0.187,0.625,n0);
		head[8][1][5]=addBPoint(0.5,-0.187,0.625,n0);
		head[0][2][5]=addBPoint(-0.5,0.0,0.625,n0);
		head[8][2][5]=addBPoint(0.5,0.0,0.625,n0);
		head[0][3][5]=addBPoint(-0.5,0.168,0.625,n0);
		head[8][3][5]=addBPoint(0.5,0.168,0.625,n0);
		head[0][4][5]=addBPoint(-0.5,0.336,0.551,n0);
		head[1][4][5]=addBPoint(-0.375,0.385,0.588,n0);
		head[2][4][5]=addBPoint(-0.25,0.433,0.625,n0);
		head[3][4][5]=addBPoint(-0.125,0.44,0.625,n0);
		head[4][4][5]=addBPoint(0.0,0.448,0.625,n0);
		head[5][4][5]=addBPoint(0.125,0.44,0.625,n0);
		head[6][4][5]=addBPoint(0.25,0.433,0.625,n0);
		head[7][4][5]=addBPoint(0.375,0.385,0.588,n0);
		head[8][4][5]=addBPoint(0.5,0.336,0.551,n0);

		head[0][0][6]=addBPoint(-0.462,-0.392,0.779,n0);
		head[1][0][6]=addBPoint(-0.347,-0.421,0.779,n0);
		head[2][0][6]=addBPoint(-0.231,-0.45,0.779,n0);
		head[3][0][6]=addBPoint(-0.115,-0.457,0.779,n0);
		head[4][0][6]=addBPoint(0.0,-0.463,0.779,n0);
		head[5][0][6]=addBPoint(0.116,-0.457,0.779,n0);
		head[6][0][6]=addBPoint(0.231,-0.45,0.779,n0);
		head[7][0][6]=addBPoint(0.347,-0.421,0.779,n0);
		head[8][0][6]=addBPoint(0.462,-0.392,0.779,n0);
		head[0][1][6]=addBPoint(-0.462,-0.196,0.779,n0);
		head[8][1][6]=addBPoint(0.462,-0.196,0.779,n0);
		head[0][2][6]=addBPoint(-0.462,0.0,0.779,n0);
		head[8][2][6]=addBPoint(0.462,0.0,0.779,n0);
		head[0][3][6]=addBPoint(-0.462,0.168,0.779,n0);
		head[8][3][6]=addBPoint(0.462,0.168,0.779,n0);
		head[0][4][6]=addBPoint(-0.462,0.335,0.779,n0);
		head[1][4][6]=addBPoint(-0.347,0.36,0.779,n0);
		head[2][4][6]=addBPoint(-0.231,0.385,0.779,n0);
		head[3][4][6]=addBPoint(-0.115,0.39,0.779,n0);
		head[4][4][6]=addBPoint(0.0,0.396,0.779,n0);
		head[5][4][6]=addBPoint(0.116,0.39,0.779,n0);
		head[6][4][6]=addBPoint(0.231,0.385,0.779,n0);
		head[7][4][6]=addBPoint(0.347,0.36,0.779,n0);
		head[8][4][6]=addBPoint(0.462,0.335,0.779,n0);

		head[0][0][7]=addBPoint(-0.277,-0.264,0.956,n0);
		head[1][0][7]=addBPoint(-0.208,-0.268,0.956,n0);
		head[2][0][7]=addBPoint(-0.139,-0.273,0.956,n0);
		head[3][0][7]=addBPoint(-0.069,-0.274,0.956,n0);
		head[4][0][7]=addBPoint(0.0,-0.276,0.956,n0);
		head[5][0][7]=addBPoint(0.07,-0.274,0.956,n0);
		head[6][0][7]=addBPoint(0.139,-0.273,0.956,n0);
		head[7][0][7]=addBPoint(0.208,-0.268,0.956,n0);
		head[8][0][7]=addBPoint(0.277,-0.264,0.956,n0);
		head[0][1][7]=addBPoint(-0.277,-0.132,0.956,n0);
		head[8][1][7]=addBPoint(0.277,-0.132,0.956,n0);
		head[0][2][7]=addBPoint(-0.277,0.0,0.956,n0);
		head[8][2][7]=addBPoint(0.277,0.0,0.956,n0);
		head[0][3][7]=addBPoint(-0.277,0.1,0.956,n0);
		head[8][3][7]=addBPoint(0.277,0.1,0.956,n0);
		head[0][4][7]=addBPoint(-0.277,0.2,0.956,n0);
		head[1][4][7]=addBPoint(-0.208,0.204,0.956,n0);
		head[2][4][7]=addBPoint(-0.139,0.207,0.956,n0);
		head[3][4][7]=addBPoint(-0.069,0.208,0.956,n0);
		head[4][4][7]=addBPoint(0.0,0.209,0.956,n0);
		head[5][4][7]=addBPoint(0.07,0.208,0.956,n0);
		head[6][4][7]=addBPoint(0.139,0.207,0.956,n0);
		head[7][4][7]=addBPoint(0.208,0.204,0.956,n0);
		head[8][4][7]=addBPoint(0.277,0.2,0.956,n0);

		head[0][0][8]=addBPoint(-0.141,-0.147,0.993,n0);
		head[1][0][8]=addBPoint(-0.106,-0.148,0.993,n0);
		head[2][0][8]=addBPoint(-0.071,-0.149,0.993,n0);
		head[3][0][8]=addBPoint(-0.035,-0.149,0.993,n0);
		head[4][0][8]=addBPoint(0.0,-0.149,0.993,n0);
		head[5][0][8]=addBPoint(0.036,-0.149,0.993,n0);
		head[6][0][8]=addBPoint(0.071,-0.149,0.993,n0);
		head[7][0][8]=addBPoint(0.106,-0.148,0.993,n0);
		head[8][0][8]=addBPoint(0.141,-0.147,0.993,n0);
		head[0][1][8]=addBPoint(-0.141,-0.073,1.0,n0);
		head[1][1][8]=addBPoint(-0.106,-0.073,1.0,n0);
		head[2][1][8]=addBPoint(-0.071,-0.074,1.0,n0);
		head[3][1][8]=addBPoint(-0.035,-0.074,1.0,n0);
		head[4][1][8]=addBPoint(0.0,-0.074,1.0,n0);
		head[5][1][8]=addBPoint(0.036,-0.074,1.0,n0);
		head[6][1][8]=addBPoint(0.071,-0.074,1.0,n0);
		head[7][1][8]=addBPoint(0.106,-0.073,1.0,n0);
		head[8][1][8]=addBPoint(0.141,-0.073,1.0,n0);
		head[0][2][8]=addBPoint(-0.141,0.0,1.0,n0);
		head[1][2][8]=addBPoint(-0.106,0.0,1.0,n0);
		head[2][2][8]=addBPoint(-0.071,0.0,1.0,n0);
		head[3][2][8]=addBPoint(-0.035,0.0,1.0,n0);
		head[4][2][8]=addBPoint(0.0,0.0,1.0,n0);
		head[5][2][8]=addBPoint(0.036,0.0,1.0,n0);
		head[6][2][8]=addBPoint(0.071,0.0,1.0,n0);
		head[7][2][8]=addBPoint(0.106,0.0,1.0,n0);
		head[8][2][8]=addBPoint(0.141,0.0,1.0,n0);
		head[0][3][8]=addBPoint(-0.141,0.056,1.0,n0);
		head[1][3][8]=addBPoint(-0.106,0.056,1.0,n0);
		head[2][3][8]=addBPoint(-0.071,0.056,1.0,n0);
		head[3][3][8]=addBPoint(-0.035,0.056,1.0,n0);
		head[4][3][8]=addBPoint(0.0,0.056,1.0,n0);
		head[5][3][8]=addBPoint(0.036,0.056,1.0,n0);
		head[6][3][8]=addBPoint(0.071,0.056,1.0,n0);
		head[7][3][8]=addBPoint(0.106,0.056,1.0,n0);
		head[8][3][8]=addBPoint(0.141,0.056,1.0,n0);
		head[0][4][8]=addBPoint(-0.141,0.111,0.993,n0);
		head[1][4][8]=addBPoint(-0.106,0.112,0.993,n0);
		head[2][4][8]=addBPoint(-0.071,0.112,0.993,n0);
		head[3][4][8]=addBPoint(-0.035,0.112,0.993,n0);
		head[4][4][8]=addBPoint(0.0,0.112,0.993,n0);
		head[5][4][8]=addBPoint(0.036,0.112,0.993,n0);
		head[6][4][8]=addBPoint(0.071,0.112,0.993,n0);
		head[7][4][8]=addBPoint(0.106,0.112,0.993,n0);
		head[8][4][8]=addBPoint(0.141,0.111,0.993,n0);

/*

		
		
			//////////
		
		for (int k = 0; k < numz; k++) {

			for (int j = 0; j < numy; j++) {


	
				for (int i = 0; i < numx; i++) {
					
					
			
					if(head[i][j][k]!=null) {
						
						int xIndex=i*2;
								
						double x=head[i][j][k].x;
						double y=head[i][j][k].y;
						double z=head[i][j][k].z;
						
						if(i>0 && head[i-1][j][k]!=null){ 
							
							double xx=(head[i-1][j][k].x+head[i][j][k].x)*0.5;
							double yy=(head[i-1][j][k].y+head[i][j][k].y)*0.5;
							
							System.out.println("head["+(xIndex-1)+"]["+j+"]["+k+"]=" +
									"addBPoint("
											+Math.round(xx/head_DX*1000)/1000.0+","
											+Math.round(yy/head_DY*1000)/1000.0+","
											+Math.round(z/head_DZ*1000)/1000.0+",n0);");
				
							
							
						}

						System.out.println("head["+xIndex+"]["+j+"]["+k+"]=" +
								"addBPoint("
										+Math.round(x/head_DX*1000)/1000.0+","
										+Math.round(y/head_DY*1000)/1000.0+","
										+Math.round(z/head_DZ*1000)/1000.0+",n0);");


					}
				}

				

			}
			System.out.println();
		}

*/

		
		for (int i = 0; i < numx-1; i++) {


			for (int j = 0; j < numy-1; j++) {

				for (int k = 0; k < numz-1; k++) {

             

					if(i==0){

						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],lfi(i,j,0,Renderer3D.CAR_LEFT),lfi(i,j,1,Renderer3D.CAR_LEFT),lfi(i,j,2,Renderer3D.CAR_LEFT),lfi(i,j,3,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(head[i][j][k],head[i][j+1][k],head[i+1][j+1][k],head[i+1][j][k],bfi(i,j,0),bfi(i,j,1),bfi(i,j,2),bfi(i,j,3),Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==numz-1){
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],tfi(i,j,0),tfi(i,j,1),tfi(i,j,2),tfi(i,j,3),Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],lfi(i,j,0,Renderer3D.CAR_BACK),lfi(i,j,1,Renderer3D.CAR_BACK),lfi(i,j,2,Renderer3D.CAR_BACK),lfi(i,j,3,Renderer3D.CAR_BACK),Renderer3D.CAR_BACK);
					}
					if(j+1==numy-1){
						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],lfi(i,j,0,Renderer3D.CAR_FRONT),lfi(i,j,1,Renderer3D.CAR_FRONT),lfi(i,j,2,Renderer3D.CAR_FRONT),lfi(i,j,3,Renderer3D.CAR_FRONT),Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==numx-1){

						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],lfi(i,j,0,Renderer3D.CAR_RIGHT),lfi(i,j,1,Renderer3D.CAR_RIGHT),lfi(i,j,2,Renderer3D.CAR_RIGHT),lfi(i,j,3,Renderer3D.CAR_RIGHT),Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//noze
		
		int nox=3;
		int noz=3;

		
		BPoint[][] noze=new BPoint[nox][noz];
		
		noze[0][0]=head[2][4][3];
		noze[1][0]=addBPoint(0.0,0.537,0.361,n0);
		noze[2][0]=head[6][4][3];
		noze[0][1]=head[2][4][4];
		noze[1][1]=addBPoint(0.0,0.463,0.5,n0);
		noze[2][1]=head[6][4][4];
		
		noze[1][2]=head[4][4][5];
		
		//base
		addLine(noze[0][0],noze[1][0],noze[2][0],null,0,0,0,0,Renderer3D.CAR_FRONT);
		//lower		
		addLine(noze[0][0],noze[0][1],noze[1][1],noze[1][0],0,0,0,0,Renderer3D.CAR_FRONT);	
		addLine(noze[1][0],noze[1][1],noze[2][1],noze[2][0],0,0,0,0,Renderer3D.CAR_FRONT);	
		//upper
		addLine(noze[0][1],noze[1][2],noze[1][1],null,0,0,0,0,Renderer3D.CAR_FRONT);
		addLine(noze[1][1],noze[1][2],noze[2][1],null,0,0,0,0,Renderer3D.CAR_FRONT);


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		



	}

	
	public int tfi(int i,int j,int n){
		
		int deltax=0;
		int deltay=0;
		
		if(n==1){
			deltax=1;
		} else if(n==2){
			deltax=1;
			deltay=1;
		}	
		else if(n==3){
			
			deltay=1;
		}
		return upperFaceIndexes[i+deltax][j+deltay];
	
	}
	
	public int lfi(int i,int j,int n,int type){
		
		int deltax=0;
		int deltay=0;
		
		if(n==1){
			deltax=1;
		} else if(n==2){
			deltax=1;
			deltay=1;
		}	
		else if(n==3){
			
			deltay=1;
		}
		
		if(type==Renderer3D.CAR_BACK){
			
			
		}else if(type==Renderer3D.CAR_RIGHT){
			
			deltax+=numx-1;
			
		}else if(type==Renderer3D.CAR_FRONT){
			
			deltax+=numx-1+numy-2;
			
		}else if(type==Renderer3D.CAR_LEFT){
			
			deltax+=2*numx+numy-2;
		}
		
		
		return lateralFaceIndexes[j+deltay][i+deltax];
	}
	
	public int bfi(int i,int j,int n){
		
		int deltax=0;
		int deltay=0;
		
		if(n==1){
			deltax=1;
		} else if(n==2){
			deltax=1;
			deltay=1;
		}	
		else if(n==3){
			
			deltay=1;
		}
		
		return bottomFaceIndexes[i+deltax][j+deltay];
	}
	
	int[][] upperFaceIndexes={
			
			{0,1,2,3,4},
			{5,6,7,8,9},
			{10,11,12,13,14},
			{15,16,17,18,19},
			{20,21,22,23,24},
			{25,26,27,28,29},
			{30,31,32,33,34},
			{35,36,37,38,39},
			{40,41,42,43,44}
			
	};

	int[][] lateralFaceIndexes={
			
			{45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69},
			{70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94},
			{95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119},
			{120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144},
			{145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169},
			{170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194},
			{195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219},
			{220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244},
			{245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269},
			
			
	};
	
	int[][] bottomFaceIndexes={
			
			{270,271,272,273,274},
			{275,276,277,278,279},
			{280,281,282,283,284},
			{285,286,287,288,289},
			{290,291,292,293,294},
			{295,296,297,298,299},
			{300,301,302,303,304},
			{305,306,307,308,309},
			{310,311,312,313,314},
			
	};

	
}
