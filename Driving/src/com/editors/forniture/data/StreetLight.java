package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class StreetLight extends Forniture{
	
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
	

	public StreetLight( double x_side, double y_side,double z_side,int forniture_type,
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

	public Vector buildTexturePoints() {
		
		isTextureDrawing=false;
		
		Vector texture_points=new Vector();
		


		
		
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
		
 
		
		points=new Vector();
		points.setSize(600);

		polyData=new ArrayList();
		
		n=0;
		
		int trunk_meridians=10;
		int trunk_parallels=10;
		double trunk_radius=x_side*0.5;
		double trunk_lenght=z_side;
		
		//trunk: 
		
		BPoint[][] trunkpoints=new BPoint[trunk_parallels][trunk_meridians];
		
		for (int k = 0; k < trunk_parallels; k++) {
			
			
			for (int i = 0; i < trunk_meridians; i++) {
				
				double x=trunk_radius*Math.cos(2*Math.PI/trunk_meridians*i);
				double y=trunk_radius*Math.sin(2*Math.PI/trunk_meridians*i);
				
				double z=0;
				
				double dz0=trunk_lenght*2.0/3.0;
				double dz1=(trunk_lenght-dz0);
				
				if(k<2){
					
					z=dz0*k;
				}
				else{
					
					z=dz0+dz1/(trunk_parallels-2.0)*(k-1);
				}
				
								
				trunkpoints[k][i]=addBPoint(x,y,z);
				
			}
			
		}
		


		LineData topLD=new LineData();
		
		for (int i = 0; i < trunk_meridians; i++) {
			
			topLD.addIndex(trunkpoints[trunk_parallels-1][i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		



		LineData bottomLD=new LineData();
		
		for (int i = trunk_meridians-1; i >=0; i--) {
			
			bottomLD.addIndex(trunkpoints[0][i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		for (int k = 0; k < trunk_parallels-1; k++) {
			
			for (int i = 0; i < trunk_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				sideLD.addIndex(trunkpoints[k][i].getIndex());
				sideLD.addIndex(trunkpoints[k][(i+1)%trunk_meridians].getIndex());
				sideLD.addIndex(trunkpoints[k+1][(i+1)%trunk_meridians].getIndex());
				sideLD.addIndex(trunkpoints[k+1][i].getIndex());	
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
		}
		
		//lamp


		Segments l0=new Segments(0,upper_width,-0.457*upper_height,upper_height,z_side,upper_length);

		int pnumx=2;
		int pnumy=2;
		int pnumz=10;

		BPoint[][][] lamp=new BPoint[pnumx][pnumy][pnumz];

			
		lamp[0][0][0]=addBPoint(-0.116,0.215,0,l0);
		lamp[1][0][0]=addBPoint(0.116,0.215,0,l0);
		lamp[0][1][0]=addBPoint(-0.116,0.699,0,l0);
		lamp[1][1][0]=addBPoint(0.116,0.699,0,l0);	
		
	
		lamp[0][0][1]=addBPoint(-0.113,0.221,0.156,l0);
		lamp[1][0][1]=addBPoint(0.113,0.221,0.156,l0);
		lamp[0][1][1]=addBPoint(-0.113,0.699,0.156,l0);
		lamp[1][1][1]=addBPoint(0.113,0.699,0.156,l0);	
					
		lamp[0][0][2]=addBPoint(-0.175,0.202,0.192,l0);
		lamp[1][0][2]=addBPoint(0.175,0.202,0.192,l0);
		lamp[0][1][2]=addBPoint(-0.175,1.0,0.192,l0);
		lamp[1][1][2]=addBPoint(0.175,1.0,0.192,l0);	
		
		
		lamp[0][0][3]=addBPoint(-0.322,0.16,0.259,l0);
		lamp[1][0][3]=addBPoint(0.322,0.16,0.259,l0);
		lamp[0][1][3]=addBPoint(-0.322,1.0,0.259,l0);
		lamp[1][1][3]=addBPoint(0.322,1.0,0.259,l0);	
		
	
		lamp[0][0][4]=addBPoint(-0.472,0.049,0.44,l0);
		lamp[1][0][4]=addBPoint(0.472,0.049,0.44,l0);
		lamp[0][1][4]=addBPoint(-0.472,1.0,0.44,l0);
		lamp[1][1][4]=addBPoint(0.472,1.0,0.44,l0);	
		
	
		lamp[0][0][5]=addBPoint(-0.5,0.0,0.578,l0);
		lamp[1][0][5]=addBPoint(0.5,0.0,0.578,l0);
		lamp[0][1][5]=addBPoint(-0.5,1.0,0.578,l0);
		lamp[1][1][5]=addBPoint(0.5,1.0,0.578,l0);	
		
		
		lamp[0][0][6]=addBPoint(-0.463,0.074,0.748,l0);
		lamp[1][0][6]=addBPoint(0.463,0.074,0.748,l0);
		lamp[0][1][6]=addBPoint(-0.463,1.0,0.748,l0);
		lamp[1][1][6]=addBPoint(0.463,1.0,0.748,l0);
		
		
		lamp[0][0][7]=addBPoint(-0.391,0.221,0.846,l0);
		lamp[1][0][7]=addBPoint(0.391,0.221,0.846,l0);
		lamp[0][1][7]=addBPoint(-0.391,1.0,0.846,l0);
		lamp[1][1][7]=addBPoint(0.391,1.0,0.846,l0);
		
		
		lamp[0][0][8]=addBPoint(-0.307,0.417,0.918,l0);
		lamp[1][0][8]=addBPoint(0.307,0.417,0.918,l0);
		lamp[0][1][8]=addBPoint(-0.307,1.0,0.918,l0);
		lamp[1][1][8]=addBPoint(0.307,1.0,0.918,l0);
		
	
		lamp[0][0][9]=addBPoint(-0.163,0.706,0.98,l0);
		lamp[1][0][9]=addBPoint(0.163,0.706,0.98,l0);
		lamp[0][1][9]=addBPoint(-0.163,1.0,0.98,l0);
		lamp[1][1][9]=addBPoint(0.163,1.0,0.98,l0);	


		for (int i = 0; i < pnumx-1; i++) {


			for (int j = 0; j < pnumy-1; j++) {	

				for (int k = 0; k < pnumz-1; k++) {


					if(i==0){

						LineData leftFrontLD=addLine(lamp[i][j][k],lamp[i][j][k+1],lamp[i][j+1][k+1],lamp[i][j+1][k],Renderer3D.CAR_LEFT);
					}



					if(k+1==pnumz-1){
						LineData topillowLD=addLine(lamp[i][j][k+1],lamp[i+1][j][k+1],lamp[i+1][j+1][k+1],lamp[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					if(k==0){
						LineData bottomFrontLD=addLine(lamp[i][j][k],lamp[i][j+1][k],lamp[i+1][j+1][k],lamp[i+1][j][k],Renderer3D.CAR_BOTTOM);
					}

					if(j==0){
						LineData backFrontLD=addLine(lamp[i][j][k],lamp[i+1][j][k],lamp[i+1][j][k+1],lamp[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pnumy-1){
						LineData frontFrontLD=addLine(lamp[i][j+1][k],lamp[i][j+1][k+1],lamp[i+1][j+1][k+1],lamp[i+1][j+1][k],Renderer3D.CAR_FRONT);
					}


					if(i+1==pnumx-1){

						LineData rightFrontLD=addLine(lamp[i+1][j][k],lamp[i+1][j+1][k],lamp[i+1][j+1][k+1],lamp[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}

				}

			}

		}


		/////////



		
		//bending of the streetlight
		
		double[] q=new double[trunk_parallels];
		
		q[1]=0.2;
		q[2]=0.2;
		q[3]=0.2;
		q[4]=0.2;
		q[5]=0.2;
		q[6]=0.2;
		q[7]=0.2;
		q[8]=0.2;
	

		
		for (int i = 0; i < q.length; i++) {
			
			if(q[i]==0)
				continue;
			
			BPoint[] refSlice = trunkpoints[i];
			BPoint p0= refSlice[0];
			
			rotateYZ(lamp,  p0.y,  p0.z,  q[i]);
			
			for (int k = 0; k < trunk_parallels; k++) {
				
				if(k<=i)
					continue;				

				BPoint[] slice = trunkpoints[k];
				
				rotateYZ(slice,  p0.y,  p0.z,  q[i]);
				
			
			}
			
		}
	


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		

	}
	
}
