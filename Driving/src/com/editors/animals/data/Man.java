package com.editors.animals.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class Man extends Animal {
	
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
	

	public Man(double x_side, double y_side,double z_side,int animal_type,
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
		


		/*int size=2*N_FACES+	(N_FACES+1)*(N_PARALLELS);
		
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
			
		}*/	
		
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
		

		
		double q_angle=12*2*Math.PI/360.0;

		points=new Vector();
		points.setSize(400);

		polyData=new Vector();

		n=0;

		double xc=0.0;
		double yc=0;//=y_side*0.5;

		//body:
		
	
		double SHOULDER_DX=leg_side*0.5;	
		double SHOULDER_WIDTH=SHOULDER_DX+x_side*0.5;		
		double WAIST_DX=x_side*0.1;
		double WAIST_WIDTH=x_side*0.5-WAIST_DX;

		Segments b0=new Segments(xc,x_side,yc,y_side,femur_length+shinbone_length,z_side);
		
		int numx=5;
		int numy=5;
		int numz=7;
		

		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 

		body[0][0][0]=addBPoint(-0.5,-0.5,0.1,TRUNK,b0);
		body[1][0][0]=addBPoint(-0.15,-0.5,0.0,TRUNK,b0);
		body[2][0][0]=addBPoint(0.0,-0.5,0.0,TRUNK,b0);
		body[3][0][0]=addBPoint(0.15,-0.5,0.0,TRUNK,b0);
		body[4][0][0]=addBPoint(0.5,-0.5,0.1,TRUNK,b0);
		body[0][1][0]=addBPoint(-0.5,-0.25,0.1,TRUNK,b0);
		body[1][1][0]=addBPoint(-0.15,-0.25,0.0,TRUNK,b0);
		body[2][1][0]=addBPoint(0.0,-0.25,0.0,TRUNK,b0);
		body[3][1][0]=addBPoint(0.15,-0.25,0.0,TRUNK,b0);
		body[4][1][0]=addBPoint(0.5,-0.25,0.1,TRUNK,b0);	
		body[0][2][0]=addBPoint(-0.5,0.0,0.1,TRUNK,b0);
		body[1][2][0]=addBPoint(-0.15,0.0,0.0,TRUNK,b0);
		body[2][2][0]=addBPoint(0.0,0.0,0.0,TRUNK,b0);
		body[3][2][0]=addBPoint(0.15,0.0,0.0,TRUNK,b0);
		body[4][2][0]=addBPoint(0.5,0.0,0.1,TRUNK,b0);
		body[0][3][0]=addBPoint(-0.5,0.25,0.1,TRUNK,b0);
		body[1][3][0]=addBPoint(-0.15,0.25,0.0,TRUNK,b0);
		body[2][3][0]=addBPoint(0.0,0.25,0.0,TRUNK,b0);
		body[3][3][0]=addBPoint(0.15,0.25,0.0,TRUNK,b0);
		body[4][3][0]=addBPoint(0.5,0.25,0.1,TRUNK,b0);
		body[0][4][0]=addBPoint(-0.5,0.5,0.1,TRUNK,b0);
		body[1][4][0]=addBPoint(-0.15,0.5,0.0,TRUNK,b0);
		body[2][4][0]=addBPoint(0.0,0.5,0.0,TRUNK,b0);
		body[3][4][0]=addBPoint(0.15,0.5,0.0,TRUNK,b0);
		body[4][4][0]=addBPoint(0.5,0.5,0.1,TRUNK,b0);
		
		body[0][0][1]=addBPoint(-0.5,-0.5,0.25,TRUNK,b0);
		body[1][0][1]=addBPoint(-0.25,-0.5,0.25,TRUNK,b0);
		body[2][0][1]=addBPoint(0.0,-0.5,0.25,TRUNK,b0);
		body[3][0][1]=addBPoint(0.25,-0.5,0.25,TRUNK,b0);
		body[4][0][1]=addBPoint(0.5,-0.5,0.25,TRUNK,b0);		
		body[0][1][1]=addBPoint(-0.5,-0.25,0.25,TRUNK,b0);
		body[4][1][1]=addBPoint(0.5,-0.25,0.25,TRUNK,b0);
		body[0][2][1]=addBPoint(-0.5,0.0,0.25,TRUNK,b0);
		body[4][2][1]=addBPoint(0.5,0.0,0.25,TRUNK,b0);
		body[0][3][1]=addBPoint(-0.5,0.25,0.25,TRUNK,b0);
		body[4][3][1]=addBPoint(0.5,0.25,0.25,TRUNK,b0);
		body[0][4][1]=addBPoint(-0.5,0.5,0.25,TRUNK,b0);
		body[1][4][1]=addBPoint(-0.25,0.5,0.25,TRUNK,b0);
		body[2][4][1]=addBPoint(0.0,0.5,0.25,TRUNK,b0);
		body[3][4][1]=addBPoint(0.25,0.5,0.25,TRUNK,b0);
		body[4][4][1]=addBPoint(0.5,0.5,0.25,TRUNK,b0);
		
		double wr=WAIST_WIDTH/x_side;
		
		body[0][0][2]=addBPoint(-wr,-0.5,0.5,TRUNK,b0);
		body[1][0][2]=addBPoint(-wr*0.5,-0.5,0.5,TRUNK,b0);
		body[2][0][2]=addBPoint(0.0,-0.5,0.5,TRUNK,b0);
		body[3][0][2]=addBPoint(wr*0.5,-0.5,0.5,TRUNK,b0);
		body[4][0][2]=addBPoint(wr,-0.5,0.5,TRUNK,b0);	
		body[0][1][2]=addBPoint(-wr,-0.25,0.5,TRUNK,b0);		
		body[4][1][2]=addBPoint(wr,-0.25,0.5,TRUNK,b0);
		body[0][2][2]=addBPoint(-wr,0.0,0.5,TRUNK,b0);		
		body[4][2][2]=addBPoint(wr,0.0,0.5,TRUNK,b0);
		body[0][3][2]=addBPoint(-wr,0.25,0.5,TRUNK,b0);
		body[4][3][2]=addBPoint(wr,0.25,0.5,TRUNK,b0);
		body[0][4][2]=addBPoint(-wr,0.5,0.5,TRUNK,b0);
		body[1][4][2]=addBPoint(-wr*0.5,0.5,0.5,TRUNK,b0);
		body[2][4][2]=addBPoint(0.0,0.5,0.5,TRUNK,b0);
		body[3][4][2]=addBPoint(wr*0.5,0.5,0.5,TRUNK,b0);
		body[4][4][2]=addBPoint(wr,0.5,0.5,TRUNK,b0);
		
		double sr=SHOULDER_WIDTH/x_side;
		double sw=leg_side/y_side;
		
		body[0][0][3]=addBPoint(-sr,-sw*0.5,0.75,TRUNK,b0);
		body[1][0][3]=addBPoint(-sr*0.5,-0.5,0.75,TRUNK,b0);
		body[2][0][3]=addBPoint(0.0,-0.5,0.75,TRUNK,b0);
		body[3][0][3]=addBPoint(sr*0.5,-0.5,0.75,TRUNK,b0);
		body[4][0][3]=addBPoint(sr,-sw*0.5,0.75,TRUNK,b0);
		body[0][1][3]=addBPoint(-sr,-sw*0.25,0.75,TRUNK,b0);		
		body[4][1][3]=addBPoint(sr,-sw*0.25,0.75,TRUNK,b0);
		body[0][2][3]=addBPoint(-sr,0.0,0.75,TRUNK,b0);	
		body[4][2][3]=addBPoint(sr,0.0,0.75,TRUNK,b0);
		body[0][3][3]=addBPoint(-sr,sw*0.25,0.75,TRUNK,b0);		
		body[4][3][3]=addBPoint(sr,sw*0.25,0.75,TRUNK,b0);
		body[0][4][3]=addBPoint(-sr,sw*0.5,0.75,TRUNK,b0);
		body[1][4][3]=addBPoint(-sr*0.5,0.5,0.75,TRUNK,b0);
		body[2][4][3]=addBPoint(0.0,0.5,0.75,TRUNK,b0);
		body[3][4][3]=addBPoint(sr*0.5,0.5,0.75,TRUNK,b0);
		body[4][4][3]=addBPoint(sr,sw*0.5,0.75,TRUNK,b0);
		
		body[0][0][4]=addBPoint(-sr,-sw*0.5,1.0,TRUNK,b0);
		body[1][0][4]=addBPoint(-sr*0.5,-0.5,1.0,TRUNK,b0);
		body[2][0][4]=addBPoint(0.0,-0.5,1.0,TRUNK,b0);
		body[3][0][4]=addBPoint(sr*0.5,-0.5,1.0,TRUNK,b0);
		body[4][0][4]=addBPoint(sr,-sw*0.5,1.0,TRUNK,b0);
		body[0][1][4]=addBPoint(-sr,-sw*0.25,1.0,TRUNK,b0);		
		body[4][1][4]=addBPoint(sr,-sw*0.25,1.0,TRUNK,b0);
		body[0][2][4]=addBPoint(-sr,0.0,1.0,TRUNK,b0);		
		body[4][2][4]=addBPoint(sr,0.0,1.0,TRUNK,b0);
		body[0][3][4]=addBPoint(-sr,sw*0.25,1.0,TRUNK,b0);		
		body[4][3][4]=addBPoint(sr,sw*0.25,1.0,TRUNK,b0);
		body[0][4][4]=addBPoint(-sr,sw*0.5,1.0,TRUNK,b0);
		body[1][4][4]=addBPoint(-sr*0.5,0.5,1.0,TRUNK,b0);
		body[2][4][4]=addBPoint(0.0,0.5,1.0,TRUNK,b0);
		body[3][4][4]=addBPoint(sr*0.5,0.5,1.0,TRUNK,b0);
		body[4][4][4]=addBPoint(sr,sw*0.5,1.0,TRUNK,b0);
		
		///////////
		//head:
		
		BPoint[][][] head=buildHumanHeadMesh(xc);
			
		Segments n0=new Segments(xc,neck_side,yc,y_side,femur_length+shinbone_length+z_side,neck_length);

		body[0][0][5]=addBPoint(-0.5,-0.5,0.5,TRUNK,n0);
		body[1][0][5]=addBPoint(-0.25,-0.5,0.5,TRUNK,n0);
		body[2][0][5]=addBPoint(0.0,-0.5,0.5,TRUNK,n0);
		body[3][0][5]=addBPoint(0.25,-0.5,0.5,TRUNK,n0);
		body[4][0][5]=addBPoint(0.5,-0.5,0.5,TRUNK,n0);
		body[0][1][5]=addBPoint(-0.5,-0.25,0.5,TRUNK,n0);		
		body[4][1][5]=addBPoint(0.5,-0.25,0.5,TRUNK,n0);
		body[0][2][5]=addBPoint(-0.5,0.0,0.5,TRUNK,n0);		
		body[4][2][5]=addBPoint(0.5,0.0,0.5,TRUNK,n0);
		body[0][3][5]=addBPoint(-0.5,0.25,0.5,TRUNK,n0);
		body[4][3][5]=addBPoint(0.5,0.25,0.5,TRUNK,n0);
		body[0][4][5]=addBPoint(-0.5,0.5,0.5,TRUNK,n0);
		body[1][4][5]=addBPoint(-0.25,0.5,0.5,TRUNK,n0);
		body[2][4][5]=addBPoint(0.0,0.5,0.5,TRUNK,n0);
		body[3][4][5]=addBPoint(0.25,0.5,0.5,TRUNK,n0);
		body[4][4][5]=addBPoint(0.5,0.5,0.5,TRUNK,n0);

		body[0][0][6]=head[0][0][0];
		body[1][0][6]=head[1][0][0];
		body[2][0][6]=head[2][0][0];
		body[3][0][6]=head[3][0][0];
		body[4][0][6]=head[4][0][0];		
		body[0][1][6]=head[0][1][0];	
		body[4][1][6]=head[4][1][0];		
		body[0][2][6]=head[0][2][0];
		body[4][2][6]=head[4][2][0];
		body[0][3][6]=head[0][3][0];
		body[4][3][6]=head[4][3][0];
		body[0][4][6]=head[0][4][0];	
		body[1][4][6]=head[1][4][0];
		body[2][4][6]=head[2][4][0];
		body[3][4][6]=head[3][4][0];
		body[4][4][6]=head[4][4][0];
		
		for(int i=0;i<numx-1;i++){

			for (int j = 0; j < numy-1; j++) {

				for(int k=0;k<numz-1;k++){

					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);			

					if(i==0)
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);

					if(i+1==numx-1)
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					
					if(j==0)
						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);
					if(j+1==numy-1)
						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);

				}
			}
		}




		//legs:	
		
		double thigh_indentation=femur_length*Math.sin(q_angle);
		double thigh_side=leg_side+thigh_indentation;
		double LEG_DY=(-leg_side)/2.0;

		//LeftLeg
		
		
		BPoint[][][] pBackLeftLeg=new BPoint[2][2][2];

		pBackLeftLeg[0][0][0]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY,0,LEFT_SHINBONE);
		pBackLeftLeg[1][0][0]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY,0,LEFT_SHINBONE);
		pBackLeftLeg[1][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,leg_side+LEG_DY,0,LEFT_SHINBONE);
		pBackLeftLeg[0][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation,leg_side+LEG_DY,0,LEFT_SHINBONE);

		addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],Renderer3D.CAR_FRONT);


		pBackLeftLeg[0][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY,shinbone_length,LEFT_SHINBONE);
		pBackLeftLeg[1][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY,shinbone_length,LEFT_SHINBONE);
		pBackLeftLeg[1][1][1]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY+leg_side,shinbone_length,LEFT_SHINBONE);
		pBackLeftLeg[0][1][1]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY+leg_side,shinbone_length,LEFT_SHINBONE);


		addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][0][1],pBackLeftLeg[0][1][1],pBackLeftLeg[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackLeftLeg[0][1][0],pBackLeftLeg[0][1][1],pBackLeftLeg[1][1][1],pBackLeftLeg[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackLeftLeg[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[1][0][1],pBackLeftLeg[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackLeftLeg[1][0][0],pBackLeftLeg[1][0][1],pBackLeftLeg[0][0][1],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		
		//left thigh
		
		
		BPoint[][][] pBackLeftThigh=new BPoint[2][2][2];

		
		pBackLeftThigh[0][0][0]=pBackLeftLeg[0][0][1];
		pBackLeftThigh[1][0][0]=pBackLeftLeg[1][0][1];
		pBackLeftThigh[1][1][0]=pBackLeftLeg[1][1][1];
		pBackLeftThigh[0][1][0]=pBackLeftLeg[0][1][1];
		
		pBackLeftThigh[0][0][1]=body[0][0][0];
		pBackLeftThigh[1][0][1]=body[1][0][0];
		pBackLeftThigh[1][1][1]=body[1][numy-1][0];
		pBackLeftThigh[0][1][1]=body[0][numy-1][0];

		addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[0][0][1],pBackLeftThigh[0][1][1],pBackLeftThigh[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackLeftThigh[0][1][0],pBackLeftThigh[0][1][1],pBackLeftThigh[1][1][1],pBackLeftThigh[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackLeftThigh[1][1][0],pBackLeftThigh[1][1][1],pBackLeftThigh[1][0][1],pBackLeftThigh[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackLeftThigh[1][0][0],pBackLeftThigh[1][0][1],pBackLeftThigh[0][0][1],pBackLeftThigh[0][0][0],Renderer3D.CAR_BACK);

		//left foot
		
		BPoint[][][] leftFoot=new BPoint[2][2][2];
		
		leftFoot[0][0][0]=pBackLeftLeg[0][1][0];
		leftFoot[1][0][0]=pBackLeftLeg[1][1][0];
		leftFoot[0][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY+leg_side,leg_side,LEFT_SHINBONE);
		leftFoot[1][0][1]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY+leg_side,leg_side,LEFT_SHINBONE);

		leftFoot[0][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation,LEG_DY+foot_length,0,LEFT_SHINBONE);
		leftFoot[1][1][0]=addBPoint(xc-x_side*0.5+thigh_indentation+leg_side,LEG_DY+foot_length,0,LEFT_SHINBONE);
		
		addLine(leftFoot[0][0][0],leftFoot[0][0][1],leftFoot[0][1][0],null,Renderer3D.CAR_LEFT);
		addLine(leftFoot[1][0][0],leftFoot[1][1][0],leftFoot[1][0][1],null,Renderer3D.CAR_RIGHT);
		addLine(leftFoot[0][0][0],leftFoot[0][1][0],leftFoot[1][1][0],leftFoot[1][0][0],Renderer3D.CAR_BOTTOM);
		addLine(leftFoot[0][0][1],leftFoot[1][0][1],leftFoot[1][1][0],leftFoot[0][1][0],Renderer3D.CAR_TOP);
		
		///RightLeg
		
		BPoint[][][] pBackRightLeg=new BPoint[2][2][2];

		pBackRightLeg[0][0][0]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY,0,RIGHT_SHINBONE);
		pBackRightLeg[1][0][0]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY,0,RIGHT_SHINBONE);
		pBackRightLeg[1][1][0]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+leg_side,0,RIGHT_SHINBONE);
		pBackRightLeg[0][1][0]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+leg_side,0,RIGHT_SHINBONE);

		addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],Renderer3D.CAR_FRONT);

		pBackRightLeg[0][0][1]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY,shinbone_length,RIGHT_SHINBONE);
		pBackRightLeg[1][0][1]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY,shinbone_length,RIGHT_SHINBONE);
		pBackRightLeg[1][1][1]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+leg_side,shinbone_length,RIGHT_SHINBONE);
		pBackRightLeg[0][1][1]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+leg_side,shinbone_length,RIGHT_SHINBONE);

		addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][0][1],pBackRightLeg[0][1][1],pBackRightLeg[0][1][0],Renderer3D.CAR_LEFT);
	
		addLine(pBackRightLeg[0][1][0],pBackRightLeg[0][1][1],pBackRightLeg[1][1][1],pBackRightLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		addLine(pBackRightLeg[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[1][0][1],pBackRightLeg[1][0][0],Renderer3D.CAR_RIGHT);
		
		addLine(pBackRightLeg[1][0][0],pBackRightLeg[1][0][1],pBackRightLeg[0][0][1],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
	
		
		//right thigh
		
		BPoint[][][] pBackRightThigh=new BPoint[2][2][2];
		
		pBackRightThigh[0][0][0]=pBackRightLeg[0][0][1];
		pBackRightThigh[1][0][0]=pBackRightLeg[1][0][1];
		pBackRightThigh[1][1][0]=pBackRightLeg[1][1][1];
		pBackRightThigh[0][1][0]=pBackRightLeg[0][1][1];
		
		pBackRightThigh[0][0][1]=body[numx-2][0][0];
		pBackRightThigh[1][0][1]=body[numx-1][0][0];
		pBackRightThigh[1][1][1]=body[numx-1][numy-1][0];
		pBackRightThigh[0][1][1]=body[numx-2][numy-1][0];

		addLine(pBackRightThigh[0][0][0],pBackRightThigh[0][0][1],pBackRightThigh[0][1][1],pBackRightThigh[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pBackRightThigh[0][1][0],pBackRightThigh[0][1][1],pBackRightThigh[1][1][1],pBackRightThigh[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pBackRightThigh[1][1][0],pBackRightThigh[1][1][1],pBackRightThigh[1][0][1],pBackRightThigh[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pBackRightThigh[1][0][0],pBackRightThigh[1][0][1],pBackRightThigh[0][0][1],pBackRightThigh[0][0][0],Renderer3D.CAR_BACK);
	
		//right foot
		
		BPoint[][][] rightFoot=new BPoint[2][2][2];
		
		rightFoot[0][0][0]=pBackRightLeg[0][1][0];
		rightFoot[1][0][0]=pBackRightLeg[1][1][0];
		rightFoot[0][0][1]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+leg_side,leg_side,RIGHT_SHINBONE);
		rightFoot[1][0][1]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+leg_side,leg_side,RIGHT_SHINBONE);

		rightFoot[0][1][0]=addBPoint(xc+x_side*0.5-leg_side-thigh_indentation,LEG_DY+foot_length,0,RIGHT_SHINBONE);
		rightFoot[1][1][0]=addBPoint(xc+x_side*0.5-thigh_indentation,LEG_DY+foot_length,0,RIGHT_SHINBONE);
		
		addLine(rightFoot[0][0][0],rightFoot[0][0][1],rightFoot[0][1][0],null,Renderer3D.CAR_LEFT);
		addLine(rightFoot[1][0][0],rightFoot[1][1][0],rightFoot[1][0][1],null,Renderer3D.CAR_RIGHT);
		addLine(rightFoot[0][0][0],rightFoot[0][1][0],rightFoot[1][1][0],rightFoot[1][0][0],Renderer3D.CAR_BOTTOM);
		addLine(rightFoot[0][0][1],rightFoot[1][0][1],rightFoot[1][1][0],rightFoot[0][1][0],Renderer3D.CAR_TOP);
		
		
		//Arms:
		
		//Left fore arm
		
		double ax=x_side*0.5+SHOULDER_DX+leg_side;
		double az=femur_length+shinbone_length+z_side-humerus_length-radius_length;
		double ay=(-leg_side)/2.0;
		
		
		BPoint[][][] pFrontLeftForearm=new BPoint[2][2][2];
		
		pFrontLeftForearm[0][0][0]=addBPoint(xc-ax,ay,az,LEFT_RADIUS);
		pFrontLeftForearm[1][0][0]=addBPoint(xc-ax+leg_side,ay,az,LEFT_RADIUS);
		pFrontLeftForearm[1][1][0]=addBPoint(xc-ax+leg_side,ay+leg_side,az,LEFT_RADIUS);
		pFrontLeftForearm[0][1][0]=addBPoint(xc-ax,ay+leg_side,az,LEFT_RADIUS);

	

		
		pFrontLeftForearm[0][0][1]=addBPoint(xc-ax,ay,az+radius_length,LEFT_RADIUS);
		pFrontLeftForearm[1][0][1]=addBPoint(xc-ax+leg_side,ay,az+radius_length,LEFT_RADIUS);
		pFrontLeftForearm[1][1][1]=addBPoint(xc-ax+leg_side,ay+leg_side,az+radius_length,LEFT_RADIUS);
		pFrontLeftForearm[0][1][1]=addBPoint(xc-ax,ay+leg_side,az+radius_length,LEFT_RADIUS);
		

		
		addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][1][1],pFrontLeftForearm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm[0][1][0],pFrontLeftForearm[0][1][1],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][0][1],pFrontLeftForearm[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm[1][0][0],pFrontLeftForearm[1][0][1],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
		
		
		//left arm
		
		BPoint[][][] pFrontLeftArm=new BPoint[2][2][2];
		
		pFrontLeftArm[0][0][0]=addBPoint(xc-ax,ay,body[0][0][3].z,LEFT_HOMERUS);
		pFrontLeftArm[1][0][0]=addBPoint(xc-ax+leg_side,ay,body[0][0][3].z,LEFT_HOMERUS);
		pFrontLeftArm[1][1][0]=addBPoint(xc-ax+leg_side,ay+leg_side,body[0][0][3].z,LEFT_HOMERUS);
		pFrontLeftArm[0][1][0]=addBPoint(xc-ax,ay+leg_side,body[0][0][3].z,LEFT_HOMERUS);
		
		
		pFrontLeftArm[0][0][1]=addBPoint(xc-ax,ay,body[0][0][4].z,LEFT_HOMERUS);
		pFrontLeftArm[1][0][1]=addBPoint(xc-ax+leg_side,ay,body[0][0][4].z,LEFT_HOMERUS);
		pFrontLeftArm[1][1][1]=addBPoint(xc-ax+leg_side,ay+leg_side,body[0][0][4].z,LEFT_HOMERUS);
		pFrontLeftArm[0][1][1]=addBPoint(xc-ax,ay+leg_side,body[0][0][4].z,LEFT_HOMERUS);
			
		addLine(pFrontLeftForearm[0][0][1],pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftForearm[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftForearm[0][1][1],pFrontLeftArm[0][1][0],pFrontLeftArm[1][1][0],pFrontLeftForearm[1][1][1],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftForearm[1][1][1],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],pFrontLeftForearm[1][0][1],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftForearm[1][0][1],pFrontLeftArm[1][0][0],pFrontLeftArm[0][0][0],pFrontLeftForearm[0][0][1],Renderer3D.CAR_BACK);
		
		
		addLine(pFrontLeftArm[0][0][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][1][1],pFrontLeftArm[0][1][1],Renderer3D.CAR_TOP);
		
		
		addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][0][1],pFrontLeftArm[0][1][1],pFrontLeftArm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontLeftArm[0][1][0],pFrontLeftArm[0][1][1],pFrontLeftArm[1][1][1],pFrontLeftArm[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pFrontLeftArm[1][1][0],pFrontLeftArm[1][1][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pFrontLeftArm[1][0][0],pFrontLeftArm[1][0][1],pFrontLeftArm[0][0][1],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
	
		//left hand
		
		BPoint[][][] pLeftHand=new BPoint[2][2][2];	
		
		pLeftHand[0][0][1]=pFrontLeftForearm[0][0][0];
		pLeftHand[1][0][1]=pFrontLeftForearm[1][0][0];
		pLeftHand[1][1][1]=pFrontLeftForearm[1][1][0];
		pLeftHand[0][1][1]=pFrontLeftForearm[0][1][0];
		
		pLeftHand[0][0][0]=addBPoint(pLeftHand[0][0][1].x,pLeftHand[0][0][1].y,pLeftHand[0][0][1].z-hand_length,LEFT_RADIUS);
		pLeftHand[1][0][0]=addBPoint(pLeftHand[1][0][1].x,pLeftHand[1][0][1].y,pLeftHand[1][0][1].z-hand_length,LEFT_RADIUS);
		pLeftHand[1][1][0]=addBPoint(pLeftHand[1][1][1].x,pLeftHand[1][1][1].y,pLeftHand[1][1][1].z-hand_length,LEFT_RADIUS);
		pLeftHand[0][1][0]=addBPoint(pLeftHand[0][1][1].x,pLeftHand[0][1][1].y,pLeftHand[0][1][1].z-hand_length,LEFT_RADIUS);
		
		addLine(pLeftHand[0][0][0],pLeftHand[0][0][1],pLeftHand[0][1][1],pLeftHand[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pLeftHand[0][1][0],pLeftHand[0][1][1],pLeftHand[1][1][1],pLeftHand[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pLeftHand[1][1][0],pLeftHand[1][1][1],pLeftHand[1][0][1],pLeftHand[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pLeftHand[1][0][0],pLeftHand[1][0][1],pLeftHand[0][0][1],pLeftHand[0][0][0],Renderer3D.CAR_BACK);		
		
		addLine(pLeftHand[0][0][0],pLeftHand[0][1][0],pLeftHand[1][1][0],pLeftHand[1][0][0],Renderer3D.CAR_BOTTOM);
		
		//Right forearm
		
		
		BPoint[][][] pFrontRightForearm=new BPoint[2][2][2];
		
		pFrontRightForearm[0][0][0]=addBPoint(xc+ax-leg_side,ay,az,RIGHT_RADIUS);
		pFrontRightForearm[1][0][0]=addBPoint(xc+ax,ay,az,RIGHT_RADIUS);
		pFrontRightForearm[1][1][0]=addBPoint(xc+ax,ay+leg_side,az,RIGHT_RADIUS);
		pFrontRightForearm[0][1][0]=addBPoint(xc+ax-leg_side,ay+leg_side,az,RIGHT_RADIUS);

		
		pFrontRightForearm[0][0][1]=addBPoint(xc+ax-leg_side,ay,az+radius_length,RIGHT_RADIUS);
		pFrontRightForearm[1][0][1]=addBPoint(xc+ax,ay,az+radius_length,RIGHT_RADIUS);
		pFrontRightForearm[1][1][1]=addBPoint(xc+ax,ay+leg_side,az+radius_length,RIGHT_RADIUS);
		pFrontRightForearm[0][1][1]=addBPoint(xc+ax-leg_side,ay+leg_side,az+radius_length,RIGHT_RADIUS);

		
		addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][0][1],pFrontRightForearm[0][1][1],pFrontRightForearm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm[0][1][0],pFrontRightForearm[0][1][1],pFrontRightForearm[1][1][1],pFrontRightForearm[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(pFrontRightForearm[1][1][0],pFrontRightForearm[1][1][1],pFrontRightForearm[1][0][1],pFrontRightForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		addLine(pFrontRightForearm[1][0][0],pFrontRightForearm[1][0][1],pFrontRightForearm[0][0][1],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);

		
		//right arm
		
		BPoint[][][] pFrontRightArm=new BPoint[2][2][2];
		
		pFrontRightArm[0][0][0]=addBPoint(xc+ax-leg_side,ay,body[0][0][3].z,RIGHT_HOMERUS);
		pFrontRightArm[1][0][0]=addBPoint(xc+ax,ay,body[0][0][3].z,RIGHT_HOMERUS);
		pFrontRightArm[1][1][0]=addBPoint(xc+ax,ay+leg_side,body[0][0][3].z,RIGHT_HOMERUS);
		pFrontRightArm[0][1][0]=addBPoint(xc+ax-leg_side,ay+leg_side,body[0][0][3].z,RIGHT_HOMERUS);
		
		
		pFrontRightArm[0][0][1]=addBPoint(xc+ax-leg_side,ay,body[0][0][4].z,RIGHT_HOMERUS);
		pFrontRightArm[1][0][1]=addBPoint(xc+ax,ay,body[0][0][4].z,RIGHT_HOMERUS);
		pFrontRightArm[1][1][1]=addBPoint(xc+ax,ay+leg_side,body[0][0][4].z,RIGHT_HOMERUS);
		pFrontRightArm[0][1][1]=addBPoint(xc+ax-leg_side,ay+leg_side,body[0][0][4].z,RIGHT_HOMERUS);
					
		addLine(pFrontRightForearm[0][0][1],pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightForearm[0][1][1],Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm[0][1][1],pFrontRightArm[0][1][0],pFrontRightArm[1][1][0],pFrontRightForearm[1][1][1],Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightForearm[1][1][1],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],pFrontRightForearm[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightForearm[1][0][1],pFrontRightArm[1][0][0],pFrontRightArm[0][0][0],pFrontRightForearm[0][0][1],Renderer3D.CAR_BACK);
		
		
		addLine(pFrontRightArm[0][0][1],pFrontRightArm[1][0][1],pFrontRightArm[1][1][1],pFrontRightArm[0][1][1],Renderer3D.CAR_TOP);
		
		
		addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][0][1],pFrontRightArm[0][1][1],pFrontRightArm[0][1][0],Renderer3D.CAR_LEFT);

		addLine(pFrontRightArm[0][1][0],pFrontRightArm[0][1][1],pFrontRightArm[1][1][1],pFrontRightArm[1][1][0],Renderer3D.CAR_FRONT);
	
		addLine(pFrontRightArm[1][1][0],pFrontRightArm[1][1][1],pFrontRightArm[1][0][1],pFrontRightArm[1][0][0],Renderer3D.CAR_RIGHT);
		
		addLine(pFrontRightArm[1][0][0],pFrontRightArm[1][0][1],pFrontRightArm[0][0][1],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
		
		
		//left hand
		
		BPoint[][][] pRightHand=new BPoint[2][2][2];	
		
		pRightHand[0][0][1]=pFrontRightForearm[0][0][0];
		pRightHand[1][0][1]=pFrontRightForearm[1][0][0];
		pRightHand[1][1][1]=pFrontRightForearm[1][1][0];
		pRightHand[0][1][1]=pFrontRightForearm[0][1][0];
		
		pRightHand[0][0][0]=addBPoint(pRightHand[0][0][1].x,pRightHand[0][0][1].y,pRightHand[0][0][1].z-hand_length,RIGHT_RADIUS);
		pRightHand[1][0][0]=addBPoint(pRightHand[1][0][1].x,pRightHand[1][0][1].y,pRightHand[1][0][1].z-hand_length,RIGHT_RADIUS);
		pRightHand[1][1][0]=addBPoint(pRightHand[1][1][1].x,pRightHand[1][1][1].y,pRightHand[1][1][1].z-hand_length,RIGHT_RADIUS);
		pRightHand[0][1][0]=addBPoint(pRightHand[0][1][1].x,pRightHand[0][1][1].y,pRightHand[0][1][1].z-hand_length,RIGHT_RADIUS);
		
		addLine(pRightHand[0][0][0],pRightHand[0][0][1],pRightHand[0][1][1],pRightHand[0][1][0],Renderer3D.CAR_RIGHT);

		addLine(pRightHand[0][1][0],pRightHand[0][1][1],pRightHand[1][1][1],pRightHand[1][1][0],Renderer3D.CAR_FRONT);

		addLine(pRightHand[1][1][0],pRightHand[1][1][1],pRightHand[1][0][1],pRightHand[1][0][0],Renderer3D.CAR_RIGHT);

		addLine(pRightHand[1][0][0],pRightHand[1][0][1],pRightHand[0][0][1],pRightHand[0][0][0],Renderer3D.CAR_BACK);		
		
		addLine(pRightHand[0][0][0],pRightHand[0][1][0],pRightHand[1][1][0],pRightHand[1][0][0],Renderer3D.CAR_BOTTOM);
	


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		
		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();
		
		int count=0;

		/*for (int j = 0; j <N_FACES; j++) {

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
		

		}*/


	}
	
	private BPoint[][][] buildHumanHeadMesh(double xc) {
		
		double yc=0;
	
		Segments n0=new Segments(xc,neck_side*0.5,yc,y_side,femur_length+shinbone_length+z_side+neck_length,head_DZ);
		Segments h0=new Segments(xc,head_DX*0.5,yc,head_DY*0.5,femur_length+shinbone_length+z_side+neck_length,head_DZ);
		
		
		Point3D e0=new Point3D(1.0,0.0,0.0);
		Point3D e1=new Point3D(Math.cos(Math.PI/8.0),Math.sin(Math.PI/8.0),0.0);
		Point3D e2=new Point3D(Math.cos(Math.PI/4.0),Math.sin(Math.PI/4.0),0.0);
		Point3D e3=new Point3D(Math.cos(Math.PI*3.0/8.0),Math.sin(Math.PI*3.0/8.0),0.0);
		Point3D e4=new Point3D(0,1.0,0.0);

		int numx=5;
		int numy=5;
		int numz=5;
		
		BPoint[][][] head=new BPoint[numx][numy][numz];

		head[0][0][0]=addBPoint(-e2.x*1.0,-e2.y*0.5,0.0,HEAD,n0);
		head[1][0][0]=addBPoint(-e3.x,-e3.y*0.5,0.0,HEAD,n0);
		head[2][0][0]=addBPoint(e4.x,-e4.y*0.5,0.0,HEAD,n0);
		head[3][0][0]=addBPoint(e3.x,-e3.y*0.5,0.0,HEAD,n0);
		head[4][0][0]=addBPoint(e2.x,-e2.y*0.5,0.0,HEAD,n0);
		head[0][1][0]=addBPoint(-e1.x,-e2.y*0.25,0.0,HEAD,n0);		
		head[4][1][0]=addBPoint(e1.x,-e2.y*0.25,0.0,HEAD,n0);
		head[0][2][0]=addBPoint(-e0.x,0.0,0.0,HEAD,n0);	
		head[4][2][0]=addBPoint(e0.x,0.0,0.0,HEAD,n0);
		head[0][3][0]=addBPoint(-e1.x,e2.y*0.25,0.0,HEAD,n0);	
		head[4][3][0]=addBPoint(e1.x,e2.y*0.25,0.0,HEAD,n0);
		head[0][4][0]=addBPoint(-e2.x,e2.y*0.5,0.0,HEAD,n0);
		head[1][4][0]=addBPoint(-e3.x,e3.y*0.5,0.0,HEAD,n0);
		head[2][4][0]=addBPoint(e4.x,e4.y*0.5,0.0,HEAD,n0);
		head[3][4][0]=addBPoint(e3.x,e3.y*0.5,0.0,HEAD,n0);
		head[4][4][0]=addBPoint(e2.x,e2.y*0.5,0.0,HEAD,n0);

		head[0][0][1]=addBPoint(-e2.x*0.85,-e2.y*0.85,0.25,HEAD,h0);
		head[1][0][1]=addBPoint(-e3.x*0.85,-e3.y*0.85,0.25,HEAD,h0);
		head[2][0][1]=addBPoint(e4.x,-e4.y*0.85,0.25,HEAD,h0);
		head[3][0][1]=addBPoint(e3.x*0.85,-e3.y*0.85,0.25,HEAD,h0);
		head[4][0][1]=addBPoint(e2.x*0.85,-e2.y*0.85,0.25,HEAD,h0);		
		head[0][1][1]=addBPoint(-e1.x*0.85,-e1.y*0.85,0.25,HEAD,h0);		
		head[4][1][1]=addBPoint(e1.x*0.85,-e1.y*0.85,0.25,HEAD,h0);
		head[0][2][1]=addBPoint(-e0.x*0.85,e0.y,0.25,HEAD,h0);	
		head[4][2][1]=addBPoint(e0.x*0.85,e0.y,0.25,HEAD,h0);
		head[0][3][1]=addBPoint(-e1.x*0.85,e1.y*0.85,0.25,HEAD,h0);		
		head[4][3][1]=addBPoint(e1.x*0.85,e1.y*0.85,0.25,HEAD,h0);
		head[0][4][1]=addBPoint(-e2.x*0.85,e2.y*0.85,0.25,HEAD,h0);
		head[1][4][1]=addBPoint(-e3.x*0.85,e3.y*0.85,0.25,HEAD,h0);
		head[2][4][1]=addBPoint(e4.x,e4.y*0.85,0.25,HEAD,h0);
		head[3][4][1]=addBPoint(e3.x*0.85,e3.y*0.85,0.25,HEAD,h0);
		head[4][4][1]=addBPoint(e2.x*0.85,e2.y*0.85,0.25,HEAD,h0);
		
		head[0][0][2]=addBPoint(-e2.x,-e2.y,0.5,HEAD,h0);
		head[1][0][2]=addBPoint(-e3.x,-e3.y,0.5,HEAD,h0);
		head[2][0][2]=addBPoint(e4.x,-e4.y,0.5,HEAD,h0);
		head[3][0][2]=addBPoint(e3.x,-e3.y,0.5,HEAD,h0);
		head[4][0][2]=addBPoint(e2.x,-e2.y,0.5,HEAD,h0);
		head[0][1][2]=addBPoint(-e1.x,-e1.y,0.5,HEAD,h0);		
		head[4][1][2]=addBPoint(e1.x,-e1.y,0.5,HEAD,h0);
		head[0][2][2]=addBPoint(-e0.x,e0.y,0.5,HEAD,h0);	
		head[4][2][2]=addBPoint(e0.x,e0.y,0.5,HEAD,h0);
		head[0][3][2]=addBPoint(-e1.x,e1.y,0.5,HEAD,h0);		
		head[4][3][2]=addBPoint(e1.x,e1.y,0.5,HEAD,h0);
		head[0][4][2]=addBPoint(-e2.x,e2.y,0.5,HEAD,h0);
		head[1][4][2]=addBPoint(-e3.x,e3.y,0.5,HEAD,h0);
		head[2][4][2]=addBPoint(e4.x,e4.y,0.5,HEAD,h0);
		head[3][4][2]=addBPoint(e3.x,e3.y,0.5,HEAD,h0);
		head[4][4][2]=addBPoint(e2.x,e2.y,0.5,HEAD,h0);	
	
		head[0][0][3]=addBPoint(-e2.x,-e2.y,0.75,HEAD,h0);
		head[1][0][3]=addBPoint(-e3.x,-e3.y,0.75,HEAD,h0);
		head[2][0][3]=addBPoint(e4.x,-e4.y,0.75,HEAD,h0);
		head[3][0][3]=addBPoint(e3.x,-e3.y,0.75,HEAD,h0);
		head[4][0][3]=addBPoint(e2.x,-e2.y,0.75,HEAD,h0);
		head[0][1][3]=addBPoint(-e1.x,-e1.y,0.75,HEAD,h0);		
		head[4][1][3]=addBPoint(e1.x,-e1.y,0.75,HEAD,h0);
		head[0][2][3]=addBPoint(-e0.x,e0.y,0.75,HEAD,h0);	
		head[4][2][3]=addBPoint(e0.x,e0.y,0.75,HEAD,h0);
		head[0][3][3]=addBPoint(-e1.x,e1.y,0.75,HEAD,h0);		
		head[4][3][3]=addBPoint(e1.x,e1.y,0.75,HEAD,h0);
		head[0][4][3]=addBPoint(-e2.x,e2.y,0.75,HEAD,h0);
		head[1][4][3]=addBPoint(-e3.x,e3.y,0.75,HEAD,h0);
		head[2][4][3]=addBPoint(e4.x,e4.y,0.75,HEAD,h0);
		head[3][4][3]=addBPoint(e3.x,e3.y,0.75,HEAD,h0);
		head[4][4][3]=addBPoint(e2.x,e2.y,0.75,HEAD,h0);
		
		head[0][0][4]=addBPoint(-e2.x*0.75,-e2.y*0.75,1.0,HEAD,h0);
		head[1][0][4]=addBPoint(-e3.x*0.75,-e3.y*0.75,1.0,HEAD,h0);
		head[2][0][4]=addBPoint(e4.x*0.75,-e4.y*0.75,1.0,HEAD,h0);
		head[3][0][4]=addBPoint(e3.x*0.75,-e3.y*0.75,1.0,HEAD,h0);
		head[4][0][4]=addBPoint(e2.x*0.75,-e2.y*0.75,1.0,HEAD,h0);	
		head[0][1][4]=addBPoint(-e1.x*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[1][1][4]=addBPoint(-e1.x*0.5*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[2][1][4]=addBPoint(0.0,-e1.y*0.75,1.0,HEAD,h0);
		head[3][1][4]=addBPoint(e1.x*0.5*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[4][1][4]=addBPoint(e1.x*0.75,-e1.y*0.75,1.0,HEAD,h0);
		head[0][2][4]=addBPoint(-e0.x*0.75,e0.y,1.0,HEAD,h0);
		head[1][2][4]=addBPoint(-e0.x*0.5*0.75,e0.y,1.0,HEAD,h0);
		head[2][2][4]=addBPoint(0.0,e0.y,1.0,HEAD,h0);
		head[3][2][4]=addBPoint(e0.x*0.5*0.75,e0.y,1.0,HEAD,h0);
		head[4][2][4]=addBPoint(e0.x*0.75,e0.y,1.0,HEAD,h0);
		head[0][3][4]=addBPoint(-e1.x*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[1][3][4]=addBPoint(-e1.x*0.5*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[2][3][4]=addBPoint(0.0,e1.y*0.75,1.0,HEAD,h0);
		head[3][3][4]=addBPoint(e1.x*0.5*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[4][3][4]=addBPoint(e1.x*0.75,e1.y*0.75,1.0,HEAD,h0);
		head[0][4][4]=addBPoint(-e2.x*0.75,e2.y*0.75,1.0,HEAD,h0);
		head[1][4][4]=addBPoint(-e3.x*0.75,e3.y*0.75,1.0,HEAD,h0);
		head[2][4][4]=addBPoint(e4.x,e4.y*0.75,1.0,HEAD,h0);
		head[3][4][4]=addBPoint(e3.x*0.75,e3.y*0.75,1.0,HEAD,h0);
		head[4][4][4]=addBPoint(e2.x*0.75,e2.y*0.75,1.0,HEAD,h0);

		
		for(int i=0;i<numx-1;i++){

			for (int j = 0; j < numy-1; j++) {


				for(int k=0;k<numz-1;k++){		

					if(i==0){
						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(i+1==numx-1){
						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==numy-1)
						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==numz-1)
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		

		
		return head;
	}

	
}
