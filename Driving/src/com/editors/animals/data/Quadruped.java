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
import com.TextureBlock;
import com.editors.DoubleTextField;
import com.main.Renderer3D;

public class Quadruped extends Animal{
	
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
	private double vlen;
	
	
	TextureBlock headBlock=null;
	TextureBlock neckBlock;
	TextureBlock bodyBlock=null;
	TextureBlock lLegBlock=null;
	TextureBlock rLegBlock=null;
	TextureBlock lFootBlock=null;
	TextureBlock rFootBlock=null;
	TextureBlock lArmBlock=null;
	TextureBlock rArmBlock=null;
	
	int numx=5;
	int numy=6;
	int numz=5;	

	int ney=3;
	int nez=3;	
	
	int hnx=5;
	int hny=5;
	int hnz=5;
	

	public Quadruped(double x_side, double y_side,double z_side,int animal_type,
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
		rFootBlock=new TextureBlock(2,2,2,leg_side,foot_length,leg_side,
				0,0,0);
		lFootBlock=new TextureBlock(2,2,2,leg_side,foot_length,leg_side,
				rFootBlock.getLen(),0,rFootBlock.exitIndex);
		rLegBlock=new TextureBlock(2,2,3,leg_side,leg_side,femur_length+shinbone_length,
				0,rFootBlock.getVlen(),lFootBlock.exitIndex);
		lLegBlock=new TextureBlock(2,2,3,leg_side,leg_side,femur_length+shinbone_length,
				rLegBlock.getLen(),rFootBlock.getVlen(),rLegBlock.exitIndex);
		
		rArmBlock=new TextureBlock(2,2,5,leg_side,leg_side,humerus_length+radius_length+hand_length,
				0,rFootBlock.getVlen()+rLegBlock.getVlen(),lLegBlock.exitIndex);
		bodyBlock=new TextureBlock(numx,numy,numz,x_side,y_side,z_side,
				rArmBlock.getLen(),rFootBlock.getVlen()+rLegBlock.getVlen(),rArmBlock.exitIndex);
		
		lArmBlock=new TextureBlock(2,2,5,leg_side,leg_side,humerus_length+radius_length+hand_length,
				rArmBlock.getLen()+bodyBlock.getLen(),rFootBlock.getVlen()+rLegBlock.getVlen(),bodyBlock.exitIndex);
		neckBlock=new TextureBlock(hnx,ney,nez,head_DX,head_DY,head_DZ,
				rArmBlock.getLen(),rFootBlock.getVlen()+rLegBlock.getVlen()+bodyBlock.getVlen(),lArmBlock.exitIndex);
		headBlock=new TextureBlock(hnx,hny,hnz,head_DX,head_DY,head_DZ,
				rArmBlock.getLen(),rFootBlock.getVlen()+rLegBlock.getVlen()+bodyBlock.getVlen()+neckBlock.getVlen(),neckBlock.exitIndex);
		
		len=bodyBlock.getLen()+lArmBlock.getLen()+rArmBlock.getLen();
		vlen=headBlock.getVlen()+neckBlock.getVlen()+bodyBlock.getVlen()+lLegBlock.getVlen()+lFootBlock.getVlen();
		numTexturePoints=headBlock.exitIndex;
		
		initMesh();
	}
	

	
	
public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
		
		isTextureDrawing=true;


		
		texture_side_dy=(int) (z_side/(N_PARALLELS-1));
		texture_side_dx=(int) (len/(N_FACES));
		

		Color backgroundColor=Color.green;

				
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) vlen+2*texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {


				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);


			//draw lines for reference
				
				drawTextureBlock(bufGraphics,headBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,neckBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,lArmBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,bodyBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,rArmBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,lLegBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,rLegBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,lFootBlock,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureBlock(bufGraphics,rFootBlock,Color.RED,Color.BLUE,Color.BLACK);				
	
				
			
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
		points.setSize(400);

		polyData=new Vector();
		
		
		
		n=0;
		

		double xc=0;
		
		//legs angles
		
		double bq0=-0.1;
		double bq1=0.5;
		double bq2=-0.4;
		
		double fq0=0.2;
		double fq1=-0.2;
		double fq2=-Math.PI/4;

		
		double rearZ=femur_length*Math.cos(bq0)+shinbone_length*Math.cos(bq0+bq1)+foot_length*Math.cos(bq0+bq1+bq2);
		double frontZ=humerus_length*Math.cos(fq0)+radius_length*Math.cos(fq0+fq1)+hand_length*Math.cos(fq0+fq1+fq2);
		
		//System.out.println("rear:"+(foot_length+femur_length+shinbone_length)+","+rearZ);
		//System.out.println("front:"+(radius_length+humerus_length+hand_length)+","+frontZ);
		//main body:
		
		double dz=rearZ-(frontZ);
		

		
		BPoint[][][] body=new BPoint[numx][numy][numz]; 
		
		Segments pb0=new Segments(xc,x_side,0,y_side,rearZ,z_side);
		Segments pf0=new Segments(xc,x_side,0,y_side,frontZ,z_side+dz);

		body[0][0][0]=addBPoint(-0.5,0.0,0.4,TRUNK,pb0);
		body[1][0][0]=addBPoint(-0.25,0.0,0.0,TRUNK,pb0);
		body[2][0][0]=addBPoint(0.0,0.0,0.0,TRUNK,pb0);
		body[3][0][0]=addBPoint(0.25,0.0,0.0,TRUNK,pb0);
		body[4][0][0]=addBPoint(0.5,0.0,0.4,TRUNK,pb0);
		body[0][0][1]=addBPoint(-0.5,0.0,0.45,TRUNK,pb0);	
		body[1][0][1]=addBPoint(-0.25,0.0,0.25,TRUNK,pb0);
		body[2][0][1]=addBPoint(0.0,0.0,0.25,TRUNK,pb0);
		body[3][0][1]=addBPoint(0.25,0.0,0.25,TRUNK,pb0);
		body[4][0][1]=addBPoint(0.5,0.0,0.45,TRUNK,pb0);
		body[0][0][2]=addBPoint(-0.5,0.0,0.5,TRUNK,pb0);	
		body[1][0][2]=addBPoint(-0.25,0.0,0.5,TRUNK,pb0);
		body[2][0][2]=addBPoint(0.0,0.0,0.5,TRUNK,pb0);
		body[3][0][2]=addBPoint(0.25,0.0,0.5,TRUNK,pb0);
		body[4][0][2]=addBPoint(0.5,0.0,0.5,TRUNK,pb0);
		body[0][0][3]=addBPoint(-0.5,0.0,0.55,TRUNK,pb0);	
		body[1][0][3]=addBPoint(-0.25,0.0,0.75,TRUNK,pb0);
		body[2][0][3]=addBPoint(0.0,0.0,0.75,TRUNK,pb0);
		body[3][0][3]=addBPoint(0.25,0.0,0.75,TRUNK,pb0);
		body[4][0][3]=addBPoint(0.5,0.0,0.55,TRUNK,pb0);
		body[0][0][4]=addBPoint(-0.5,0.0,0.7,TRUNK,pb0);	
		body[1][0][4]=addBPoint(-0.25,0.0,1.0,TRUNK,pb0);
		body[2][0][4]=addBPoint(0.0,0.0,1.0,TRUNK,pb0);
		body[3][0][4]=addBPoint(0.25,0.0,1.0,TRUNK,pb0);
		body[4][0][4]=addBPoint(0.5,0.0,0.7,TRUNK,pb0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0.4,TRUNK,pb0);
		body[1][1][0]=addBPoint(-0.25,0.25,0.0,TRUNK,pb0);
		body[2][1][0]=addBPoint(0.0,0.25,0.0,TRUNK,pb0);
		body[3][1][0]=addBPoint(0.25,0.25,0.0,TRUNK,pb0);
		body[4][1][0]=addBPoint(0.5,0.25,0.4,TRUNK,pb0);	
		body[0][1][1]=addBPoint(-0.5,0.25,0.45,TRUNK,pb0);	
		body[1][1][1]=addBPoint(-0.25,0.25,0.25,TRUNK,pb0);
		body[2][1][1]=addBPoint(0.0,0.25,0.25,TRUNK,pb0);
		body[3][1][1]=addBPoint(0.25,0.25,0.25,TRUNK,pb0);
		body[4][1][1]=addBPoint(0.5,0.25,0.45,TRUNK,pb0);
		body[0][1][2]=addBPoint(-0.5,0.25,0.5,TRUNK,pb0);	
		body[1][1][2]=addBPoint(-0.25,0.25,0.5,TRUNK,pb0);
		body[2][1][2]=addBPoint(0.0,0.25,0.5,TRUNK,pb0);
		body[3][1][2]=addBPoint(0.25,0.25,0.5,TRUNK,pb0);
		body[4][1][2]=addBPoint(0.5,0.25,0.5,TRUNK,pb0);
		body[0][1][3]=addBPoint(-0.5,0.25,0.55,TRUNK,pb0);	
		body[1][1][3]=addBPoint(-0.25,0.25,0.75,TRUNK,pb0);
		body[2][1][3]=addBPoint(0.0,0.25,0.75,TRUNK,pb0);
		body[3][1][3]=addBPoint(0.25,0.25,0.75,TRUNK,pb0);
		body[4][1][3]=addBPoint(0.5,0.25,0.55,TRUNK,pb0);
		body[0][1][4]=addBPoint(-0.5,0.25,0.7,TRUNK,pb0);	
		body[1][1][4]=addBPoint(-0.25,0.25,1.0,TRUNK,pb0);
		body[2][1][4]=addBPoint(0.0,0.25,1.0,TRUNK,pb0);
		body[3][1][4]=addBPoint(0.25,0.25,1.0,TRUNK,pb0);
		body[4][1][4]=addBPoint(0.5,0.25,0.7,TRUNK,pb0);
		
		body[0][2][0]=addBPoint(-0.5,0.5,0.4,TRUNK,pb0);
		body[1][2][0]=addBPoint(-0.25,0.5,0.0,TRUNK,pb0);
		body[2][2][0]=addBPoint(0.0,0.5,0.0,TRUNK,pb0);
		body[3][2][0]=addBPoint(0.25,0.5,0.0,TRUNK,pb0);
		body[4][2][0]=addBPoint(0.5,0.5,0.4,TRUNK,pb0);
		body[0][2][1]=addBPoint(-0.5,0.5,0.45,TRUNK,pb0);	
		body[1][2][1]=addBPoint(-0.25,0.5,0.25,TRUNK,pb0);
		body[2][2][1]=addBPoint(0.0,0.5,0.25,TRUNK,pb0);
		body[3][2][1]=addBPoint(0.25,0.5,0.25,TRUNK,pb0);
		body[4][2][1]=addBPoint(0.5,0.5,0.45,TRUNK,pb0);
		body[0][2][2]=addBPoint(-0.5,0.5,0.5,TRUNK,pb0);	
		body[1][2][2]=addBPoint(-0.25,0.5,0.5,TRUNK,pb0);
		body[2][2][2]=addBPoint(0.0,0.5,0.5,TRUNK,pb0);
		body[3][2][2]=addBPoint(0.25,0.5,0.5,TRUNK,pb0);
		body[4][2][2]=addBPoint(0.5,0.5,0.5,TRUNK,pb0);
		body[0][2][3]=addBPoint(-0.5,0.5,0.55,TRUNK,pb0);	
		body[1][2][3]=addBPoint(-0.25,0.5,0.75,TRUNK,pb0);
		body[2][2][3]=addBPoint(0.0,0.5,0.75,TRUNK,pb0);
		body[3][2][3]=addBPoint(0.25,0.5,0.75,TRUNK,pb0);
		body[4][2][3]=addBPoint(0.5,0.5,0.55,TRUNK,pb0);
		body[0][2][4]=addBPoint(-0.5,0.5,0.7,TRUNK,pb0);	
		body[1][2][4]=addBPoint(-0.25,0.5,1.0,TRUNK,pb0);
		body[2][2][4]=addBPoint(0.0,0.5,1.0,TRUNK,pb0);
		body[3][2][4]=addBPoint(0.25,0.5,1.0,TRUNK,pb0);
		body[4][2][4]=addBPoint(0.5,0.5,0.7,TRUNK,pb0);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0.4,TRUNK,pf0);
		body[1][3][0]=addBPoint(-0.25,0.75,0.0,TRUNK,pf0);
		body[2][3][0]=addBPoint(0.0,0.75,0.0,TRUNK,pf0);	
		body[3][3][0]=addBPoint(0.25,0.75,0.0,TRUNK,pf0);
		body[4][3][0]=addBPoint(0.5,0.75,0.4,TRUNK,pf0);
		body[0][3][1]=addBPoint(-0.5,0.75,0.45,TRUNK,pf0);		
		body[1][3][1]=addBPoint(-0.25,0.75,0.25,TRUNK,pf0);
		body[2][3][1]=addBPoint(0.0,0.75,0.25,TRUNK,pf0);
		body[3][3][1]=addBPoint(0.25,0.75,0.25,TRUNK,pf0);
		body[4][3][1]=addBPoint(0.5,0.75,0.45,TRUNK,pf0);
		body[0][3][2]=addBPoint(-0.5,0.75,0.5,TRUNK,pf0);		
		body[1][3][2]=addBPoint(-0.25,0.75,0.5,TRUNK,pf0);
		body[2][3][2]=addBPoint(0.0,0.75,0.5,TRUNK,pf0);
		body[3][3][2]=addBPoint(0.25,0.75,0.5,TRUNK,pf0);
		body[4][3][2]=addBPoint(0.5,0.75,0.5,TRUNK,pf0);
		body[0][3][3]=addBPoint(-0.5,0.75,0.75,TRUNK,pf0);		
		body[1][3][3]=addBPoint(-0.25,0.75,0.75,TRUNK,pf0);
		body[2][3][3]=addBPoint(0.0,0.75,0.75,TRUNK,pf0);
		body[3][3][3]=addBPoint(0.25,0.75,0.75,TRUNK,pf0);
		body[4][3][3]=addBPoint(0.5,0.75,0.75,TRUNK,pf0);
		body[0][3][4]=addBPoint(-0.5,0.75,1.0,TRUNK,pf0);		
		body[1][3][4]=addBPoint(-0.25,0.75,1.0,TRUNK,pf0);
		body[2][3][4]=addBPoint(0.0,0.75,1.0,TRUNK,pf0);
		body[3][3][4]=addBPoint(0.25,0.75,1.0,TRUNK,pf0);
		body[4][3][4]=addBPoint(0.5,0.75,1.0,TRUNK,pf0);
		
		body[0][4][0]=addBPoint(-0.5,0.875,0.4,TRUNK,pf0);
		body[1][4][0]=addBPoint(-0.25,0.875,0.0,TRUNK,pf0);	
		body[2][4][0]=addBPoint(0.0,0.875,0.0,TRUNK,pf0);	
		body[3][4][0]=addBPoint(0.25,0.875,0.0,TRUNK,pf0);		
		body[4][4][0]=addBPoint(0.5,0.875,0.4,TRUNK,pf0);	
		body[0][4][1]=addBPoint(-0.5,0.875,0.45,TRUNK,pf0);		
		body[1][4][1]=addBPoint(-0.25,0.875,0.25,TRUNK,pf0);
		body[2][4][1]=addBPoint(0.0,0.875,0.25,TRUNK,pf0);
		body[3][4][1]=addBPoint(0.25,0.875,0.25,TRUNK,pf0);
		body[4][4][1]=addBPoint(0.5,0.875,0.45,TRUNK,pf0);
		body[0][4][2]=addBPoint(-0.5,0.875,0.5,TRUNK,pf0);		
		body[1][4][2]=addBPoint(-0.25,0.875,0.5,TRUNK,pf0);
		body[2][4][2]=addBPoint(0.0,0.875,0.5,TRUNK,pf0);
		body[3][4][2]=addBPoint(0.25,0.875,0.5,TRUNK,pf0);
		body[4][4][2]=addBPoint(0.5,0.875,0.5,TRUNK,pf0);
		body[0][4][3]=addBPoint(-0.5,0.875,0.75,TRUNK,pf0);		
		body[1][4][3]=addBPoint(-0.25,0.875,0.75,TRUNK,pf0);
		body[2][4][3]=addBPoint(0.0,0.875,0.75,TRUNK,pf0);
		body[3][4][3]=addBPoint(0.25,0.875,0.75,TRUNK,pf0);
		body[4][4][3]=addBPoint(0.5,0.875,0.75,TRUNK,pf0);
		body[0][4][4]=addBPoint(-0.5,0.875,1.0,TRUNK,pf0);		
		body[1][4][4]=addBPoint(-0.25,0.875,1.0,TRUNK,pf0);
		body[2][4][4]=addBPoint(0.0,0.875,1.0,TRUNK,pf0);
		body[3][4][4]=addBPoint(0.25,0.875,1.0,TRUNK,pf0);
		body[4][4][4]=addBPoint(0.5,0.875,1.0,TRUNK,pf0);
		
		body[0][5][0]=addBPoint(-0.5,1.0,0.4,TRUNK,pf0);
		body[1][5][0]=addBPoint(-0.25,1.0,0.0,TRUNK,pf0);	
		body[2][5][0]=addBPoint(0.0,1.0,0.0,TRUNK,pf0);	
		body[3][5][0]=addBPoint(0.25,1.0,0.0,TRUNK,pf0);		
		body[4][5][0]=addBPoint(0.5,1.0,0.4,TRUNK,pf0);	
		body[0][5][1]=addBPoint(-0.5,1.0,0.45,TRUNK,pf0);		
		body[1][5][1]=addBPoint(-0.25,1.0,0.25,TRUNK,pf0);
		body[2][5][1]=addBPoint(0.0,1.0,0.25,TRUNK,pf0);
		body[3][5][1]=addBPoint(0.25,1.0,0.25,TRUNK,pf0);
		body[4][5][1]=addBPoint(0.5,1.0,0.45,TRUNK,pf0);
		body[0][5][2]=addBPoint(-0.5,1.0,0.5,TRUNK,pf0);		
		body[1][5][2]=addBPoint(-0.25,1.0,0.5,TRUNK,pf0);
		body[2][5][2]=addBPoint(0.0,1.0,0.5,TRUNK,pf0);
		body[3][5][2]=addBPoint(0.25,1.0,0.5,TRUNK,pf0);
		body[4][5][2]=addBPoint(0.5,1.0,0.5,TRUNK,pf0);
		body[0][5][3]=addBPoint(-0.5,1.0,0.75,TRUNK,pf0);		
		body[1][5][3]=addBPoint(-0.25,1.0,0.75,TRUNK,pf0);
		body[2][5][3]=addBPoint(0.0,1.0,0.75,TRUNK,pf0);
		body[3][5][3]=addBPoint(0.25,1.0,0.75,TRUNK,pf0);
		body[4][5][3]=addBPoint(0.5,1.0,0.75,TRUNK,pf0);
		body[0][5][4]=addBPoint(-0.5,1.0,1.0,TRUNK,pf0);		
		body[1][5][4]=addBPoint(-0.25,1.0,1.0,TRUNK,pf0);
		body[2][5][4]=addBPoint(0.0,1.0,1.0,TRUNK,pf0);
		body[3][5][4]=addBPoint(0.25,1.0,1.0,TRUNK,pf0);
		body[4][5][4]=addBPoint(0.5,1.0,1.0,TRUNK,pf0);
		
		for (int i = 0; i < numx-1; i++) {
			
		
		
			for (int j = 0; j < numy-1; j++) {
				
				for (int k = 0; k < numz-1; k++) {
			
					if(j==0){

						addLine(body,i,j,k,bodyBlock.lf(i,j,k,Renderer3D.CAR_BACK),Renderer3D.CAR_BACK);
						
					}				
						
					if(i==0)	
						addLine(body,i,j,k,bodyBlock.lf(i,j,k,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);

						
					if(k==0)
						addLine(body,i,j,k,bodyBlock.lf(i,j,k,Renderer3D.CAR_BOTTOM),Renderer3D.CAR_BOTTOM);
					
					if(k+1==numz-1)							
						addLine(body,i,j,k,bodyBlock.lf(i,j,k,Renderer3D.CAR_TOP),Renderer3D.CAR_TOP);
				
					

					if(i+1==numx-1)
						addLine(body,i,j,k,bodyBlock.lf(i,j,k,Renderer3D.CAR_RIGHT),Renderer3D.CAR_RIGHT);
						
				
					
					if(j+1==numy-1){
						
						addLine(body,i,j,k,bodyBlock.lf(i,j,k,Renderer3D.CAR_FRONT),Renderer3D.CAR_FRONT);
						
					}
				
				}
				
			}
			
		}

		/////////head and neck:		
		
		double nz0=frontZ+z_side+dz;		
		
		BPoint[][][] head=buildQuadrupedHeadMesh(nz0+neck_length,xc);		
	
		
		Segments n0=new Segments(xc,neck_side,y_side-neck_side,neck_side,nz0,neck_length);
		
		BPoint[][][] neck=new BPoint[numx][ney][nez];
	
		neck[0][0][0]=body[0][3][numz-1];
		neck[1][0][0]=body[1][3][numz-1];
		neck[2][0][0]=body[2][3][numz-1];
		neck[3][0][0]=body[3][3][numz-1];
		neck[4][0][0]=body[4][3][numz-1];
		neck[0][1][0]=body[0][4][numz-1];
		neck[1][1][0]=body[1][4][numz-1];
		neck[2][1][0]=body[2][4][numz-1];
		neck[3][1][0]=body[3][4][numz-1];
		neck[4][1][0]=body[4][4][numz-1];
		neck[0][2][0]=body[0][5][numz-1];
		neck[1][2][0]=body[1][5][numz-1];
		neck[2][2][0]=body[2][5][numz-1];
		neck[3][2][0]=body[3][5][numz-1];
		neck[4][2][0]=body[4][5][numz-1];
		
		neck[0][0][1]=addBPoint(-0.5,0.0,0.5,TRUNK,n0);
		neck[1][0][1]=addBPoint(-0.25,0.0,0.5,TRUNK,n0);
		neck[2][0][1]=addBPoint(0.0,0.0,0.5,TRUNK,n0);
		neck[3][0][1]=addBPoint(0.25,0.0,0.5,TRUNK,n0);
		neck[4][0][1]=addBPoint(0.5,0.0,0.5,TRUNK,n0);
		neck[0][1][1]=addBPoint(-0.5,0.5,0.5,TRUNK,n0);
		neck[1][1][1]=addBPoint(-0.25,0.5,0.5,TRUNK,n0);
		neck[2][1][1]=addBPoint(0.0,0.5,0.5,TRUNK,n0);
		neck[3][1][1]=addBPoint(0.25,0.5,0.5,TRUNK,n0);
		neck[4][1][1]=addBPoint(0.5,0.5,0.5,TRUNK,n0);
		neck[0][2][1]=addBPoint(-0.5,1.0,0.5,TRUNK,n0);
		neck[1][2][1]=addBPoint(-0.25,1.0,0.5,TRUNK,n0);
		neck[2][2][1]=addBPoint(0.0,1.0,0.5,TRUNK,n0);
		neck[3][2][1]=addBPoint(0.25,1.0,0.5,TRUNK,n0);
		neck[4][2][1]=addBPoint(0.5,1.0,0.5,TRUNK,n0);
		
		neck[0][0][2]=head[0][0][0];
		neck[1][0][2]=head[1][0][0];
		neck[2][0][2]=head[2][0][0];
		neck[3][0][2]=head[3][0][0];
		neck[4][0][2]=head[4][0][0];		
		neck[0][1][2]=head[0][1][0];
		neck[1][1][2]=head[1][1][0];
		neck[2][1][2]=head[2][1][0];
		neck[3][1][2]=head[3][1][0];			
		neck[4][1][2]=head[4][1][0];
		neck[0][2][2]=head[0][2][0];
		neck[1][2][2]=head[1][2][0];
		neck[2][2][2]=head[2][2][0];
		neck[3][2][2]=head[3][2][0];			
		neck[4][2][2]=head[4][2][0];

		
		for (int i = 0; i < numx-1; i++) {

			for (int j = 0; j < ney-1; j++) {
				
				for (int k = 0; k < nez-1; k++) {

					if(i==0){

						addLine(neck[i][j][k],neck[i][j][k+1],neck[i][j+1][k+1],neck[i][j+1][k],Renderer3D.CAR_LEFT);
					}


					if(j==0){
					
						addLine(neck[i][j][k],neck[i+1][j][k],neck[i+1][j][k+1],neck[i][j][k+1],Renderer3D.CAR_BACK);

					}
					
					if(j+1==ney-1){		

						addLine(neck[i][j+1][k],neck[i][j+1][k+1],neck[i+1][j+1][k+1],neck[i+1][j+1][k],Renderer3D.CAR_FRONT);

					}

					if(i+1==numx-1){

						addLine(neck[i+1][j][k],neck[i+1][j+1][k],neck[i+1][j+1][k+1],neck[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

				}
			}

		}
		
		
	
		
		//limbs:
		
		Segments lefLegFoot0=new Segments(xc-x_side*0.5,leg_side,0,leg_side,0,foot_length);
		Segments leflegShin0=new Segments(xc-x_side*0.5,leg_side,0,leg_side,foot_length,shinbone_length);
		Segments lefLegFem0=new Segments(xc-x_side*0.5,leg_side,0,leg_side,foot_length+shinbone_length,femur_length);
		
		Segments rigLegFoot0=new Segments(xc+x_side*0.5-leg_side,leg_side,0,leg_side,0,foot_length);
		Segments rigLegShin0=new Segments(xc+x_side*0.5-leg_side,leg_side,0,leg_side,foot_length,shinbone_length);
		Segments rigLegFem0=new Segments(xc+x_side*0.5-leg_side,leg_side,0,leg_side,foot_length+shinbone_length,femur_length);
	
		Segments lefArmHan0=new Segments(xc-x_side*0.5,leg_side,y_side-leg_side,leg_side,0,hand_length);
		Segments lefArmRad0=new Segments(xc-x_side*0.5,leg_side,y_side-leg_side,leg_side,hand_length,radius_length);
		Segments lefArmHum0=new Segments(xc-x_side*0.5,leg_side,y_side-leg_side,leg_side,hand_length+radius_length,humerus_length);
		
		Segments rigArmHan0=new Segments(xc+x_side*0.5-leg_side,leg_side,y_side-leg_side,leg_side,0,hand_length);
		Segments rigArmRad0=new Segments(xc+x_side*0.5-leg_side,leg_side,y_side-leg_side,leg_side,hand_length,radius_length);
		Segments rigArmHum0=new Segments(xc+x_side*0.5-leg_side,leg_side,y_side-leg_side,leg_side,hand_length+radius_length,humerus_length);
		
		//legs:	


		
		//back left thigh
		
		BPoint[][][] pBackLeftThigh=new BPoint[2][2][2];
		
		pBackLeftThigh[0][0][0]=addBPoint(0,0,0.0,LEFT_FEMUR,lefLegFem0);
		pBackLeftThigh[1][0][0]=addBPoint(1.0,0,0.0,LEFT_FEMUR,lefLegFem0);
		pBackLeftThigh[1][1][0]=addBPoint(1.0,1.0,0.0,LEFT_FEMUR,lefLegFem0);
		pBackLeftThigh[0][1][0]=addBPoint(0,1.0,0.0,LEFT_FEMUR,lefLegFem0);
		
		pBackLeftThigh[0][0][1]=body[0][0][0];
		pBackLeftThigh[1][0][1]=body[1][0][0];
		pBackLeftThigh[1][1][1]=body[1][1][0];
		pBackLeftThigh[0][1][1]=body[0][1][0];
		
		rotateYZ(pBackLeftThigh,pBackLeftThigh[0][1][1].y,pBackLeftThigh[0][1][1].z,bq0);

		LineData backLeftThighS0=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[0][0][1],pBackLeftThigh[0][1][1],pBackLeftThigh[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backLeftThighS1=addLine(pBackLeftThigh[0][1][0],pBackLeftThigh[0][1][1],pBackLeftThigh[1][1][1],pBackLeftThigh[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftThighS2=addLine(pBackLeftThigh[1][1][0],pBackLeftThigh[1][1][1],pBackLeftThigh[1][0][1],pBackLeftThigh[1][0][0],Renderer3D.CAR_RIGHT);

		LineData backLeftThighS3=addLine(pBackLeftThigh[1][0][0],pBackLeftThigh[1][0][1],pBackLeftThigh[0][0][1],pBackLeftThigh[0][0][0],Renderer3D.CAR_BACK);

		//LineData backLeftTopThigh=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[1][0][0],pBackLeftThigh[1][1][0],pBackLeftThigh[0][1][0],Renderer3D.CAR_TOP);
		
		//backLeftLeg
			
		
		BPoint[][][] pBackLeftLeg=new BPoint[2][2][2];	
		
		pBackLeftLeg[0][0][0]=addBPoint(0,0,0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][0][0]=addBPoint(1.0,0,0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][1][0]=addBPoint(1.0,1.0,0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[0][1][0]=addBPoint(0,1.0,0,LEFT_SHINBONE,leflegShin0);
		
		pBackLeftLeg[0][0][1]=addBPoint(0,0,1.0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][0][1]=addBPoint(1.0,0,1.0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_SHINBONE,leflegShin0);
		pBackLeftLeg[0][1][1]=addBPoint(0,1.0,1.0,LEFT_SHINBONE,leflegShin0);
		
		rotateYZ(pBackLeftLeg,pBackLeftThigh[0][1][1].y,pBackLeftThigh[0][1][1].z,bq0);
		rotateYZ(pBackLeftLeg,pBackLeftLeg[0][0][1].y,pBackLeftLeg[0][0][1].z,bq1);

		
		LineData backLeftLeg=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],Renderer3D.CAR_BOTTOM);
				
		LineData backLeftLegS0=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][0][1],pBackLeftLeg[0][1][1],pBackLeftLeg[0][1][0],Renderer3D.CAR_LEFT);

		LineData backLeftLegS1=addLine(pBackLeftLeg[0][1][0],pBackLeftLeg[0][1][1],pBackLeftLeg[1][1][1],pBackLeftLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftLegS2=addLine(pBackLeftLeg[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[1][0][1],pBackLeftLeg[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backLeftLegS3=addLine(pBackLeftLeg[1][0][0],pBackLeftLeg[1][0][1],pBackLeftLeg[0][0][1],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backLeftKnee0=addLine(pBackLeftThigh[0][1][0],pBackLeftThigh[1][1][0],pBackLeftLeg[1][1][1],pBackLeftLeg[0][1][1],Renderer3D.CAR_FRONT);
		LineData backLeftKnee1=addLine(pBackLeftThigh[0][0][0],pBackLeftThigh[0][1][0],pBackLeftLeg[0][1][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftKnee2=addLine(pBackLeftThigh[1][1][0],pBackLeftThigh[1][0][0],pBackLeftLeg[1][1][1],null,Renderer3D.CAR_RIGHT);
		
		//back left foot
		
		BPoint[][][] pBackLeftFoot=new BPoint[2][2][2];
		
		pBackLeftFoot[0][0][0]=addBPoint(0,0,0.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][0][0]=addBPoint(1.0,0,0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][1][0]=addBPoint(1.0,1.0,0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[0][1][0]=addBPoint(0,1.0,0,LEFT_SHINBONE,lefLegFoot0);
		
		pBackLeftFoot[0][0][1]=addBPoint(0,0,1.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][0][1]=addBPoint(1.0,0,1.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_SHINBONE,lefLegFoot0);
		pBackLeftFoot[0][1][1]=addBPoint(0,1.0,1.0,LEFT_SHINBONE,lefLegFoot0);
		
		rotateYZ(pBackLeftFoot,pBackLeftThigh[0][1][1].y,pBackLeftThigh[0][1][1].z,bq0);
		rotateYZ(pBackLeftFoot,pBackLeftLeg[0][0][1].y,pBackLeftLeg[0][0][1].z,bq1);
		rotateYZ(pBackLeftFoot,pBackLeftFoot[0][1][1].y,pBackLeftFoot[0][1][1].z,bq2);
		
		LineData backLeftFoot=addLine(pBackLeftFoot[0][0][0],pBackLeftFoot[0][1][0],pBackLeftFoot[1][1][0],pBackLeftFoot[1][0][0],Renderer3D.CAR_BOTTOM);
				
		LineData backLeftFootS0=addLine(pBackLeftFoot[0][0][0],pBackLeftFoot[0][0][1],pBackLeftFoot[0][1][1],pBackLeftFoot[0][1][0],Renderer3D.CAR_LEFT);

		LineData backLeftFootS1=addLine(pBackLeftFoot[0][1][0],pBackLeftFoot[0][1][1],pBackLeftFoot[1][1][1],pBackLeftFoot[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backLeftFootS2=addLine(pBackLeftFoot[1][1][0],pBackLeftFoot[1][1][1],pBackLeftFoot[1][0][1],pBackLeftFoot[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backLeftFootS3=addLine(pBackLeftFoot[1][0][0],pBackLeftFoot[1][0][1],pBackLeftFoot[0][0][1],pBackLeftFoot[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backLeftAnkle0=addLine(pBackLeftFoot[0][0][1],pBackLeftFoot[1][0][1],pBackLeftLeg[1][0][0],pBackLeftLeg[0][0][0],Renderer3D.CAR_BACK);
		LineData backLeftAnkle1=addLine(pBackLeftLeg[0][0][0],pBackLeftLeg[0][1][0],pBackLeftFoot[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftAnkle2=addLine( pBackLeftFoot[1][0][1],pBackLeftLeg[1][1][0],pBackLeftLeg[1][0][0],null,Renderer3D.CAR_RIGHT);

		//back right thigh
		
		
		BPoint[][][] pBackRightThigh=new BPoint[2][2][2];
		
		pBackRightThigh[0][0][0]=addBPoint(0,0,0,RIGHT_FEMUR,rigLegFem0);
		pBackRightThigh[1][0][0]=addBPoint(1.0,0,0,RIGHT_FEMUR,rigLegFem0);
		pBackRightThigh[1][1][0]=addBPoint(1.0,1.0,0,RIGHT_FEMUR,rigLegFem0);
		pBackRightThigh[0][1][0]=addBPoint(0,1.0,0,RIGHT_FEMUR,rigLegFem0);
		
		pBackRightThigh[0][0][1]=body[numx-2][0][0];
		pBackRightThigh[1][0][1]=body[numx-1][0][0];
		pBackRightThigh[1][1][1]=body[numx-1][1][0];
		pBackRightThigh[0][1][1]=body[numx-2][1][0];
		
		rotateYZ(pBackRightThigh,pBackRightThigh[1][1][1].y,pBackRightThigh[1][1][1].z,bq0);

		LineData backRightThighS0=addLine(pBackRightThigh[0][0][0],pBackRightThigh[0][0][1],pBackRightThigh[0][1][1],pBackRightThigh[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightThighS1=addLine(pBackRightThigh[0][1][0],pBackRightThigh[0][1][1],pBackRightThigh[1][1][1],pBackRightThigh[1][1][0],Renderer3D.CAR_FRONT);

		LineData backRightThighS2=addLine(pBackRightThigh[1][1][0],pBackRightThigh[1][1][1],pBackRightThigh[1][0][1],pBackRightThigh[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightThighS3=addLine(pBackRightThigh[1][0][0],pBackRightThigh[1][0][1],pBackRightThigh[0][0][1],pBackRightThigh[0][0][0],Renderer3D.CAR_BACK);

				
		
		//LineData backRightTopThigh=addLine(pBackRightThigh[0][0][0],pBackRightThigh[1][0][0],pBackRightThigh[1][1][0],pBackRightThigh[0][1][0],Renderer3D.CAR_TOP);
		
		
		//backRightLeg
		
		
		BPoint[][][] pBackRightLeg=new BPoint[2][2][2];
		
		pBackRightLeg[0][0][0]=addBPoint(0,0,0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][0][0]=addBPoint(1.0,0,0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][1][0]=addBPoint(1.0,1.0,0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[0][1][0]=addBPoint(0,1.0,0,RIGHT_SHINBONE,rigLegShin0);
		
		pBackRightLeg[0][0][1]=addBPoint(0,0,1.0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][0][1]=addBPoint(1.0,0,1.0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[1][1][1]=addBPoint(1.0,1.0,1.0,RIGHT_SHINBONE,rigLegShin0);
		pBackRightLeg[0][1][1]=addBPoint(0,1.0,1.0,RIGHT_SHINBONE,rigLegShin0);
		
		rotateYZ(pBackRightLeg,pBackRightThigh[1][1][1].y,pBackRightThigh[1][1][1].z,bq0);
		rotateYZ(pBackRightLeg,pBackRightLeg[0][0][1].y,pBackRightLeg[0][0][1].z,bq1);
		
		LineData backRightLeg=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData backRightLegS0=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][0][1],pBackRightLeg[0][1][1],pBackRightLeg[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightLegS1=addLine(pBackRightLeg[0][1][0],pBackRightLeg[0][1][1],pBackRightLeg[1][1][1],pBackRightLeg[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backRightLegS2=addLine(pBackRightLeg[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[1][0][1],pBackRightLeg[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightLegS3=addLine(pBackRightLeg[1][0][0],pBackRightLeg[1][0][1],pBackRightLeg[0][0][1],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backRightKnee0=addLine(pBackRightThigh[0][1][0],pBackRightThigh[1][1][0],pBackRightLeg[1][1][1],pBackRightLeg[0][1][1],Renderer3D.CAR_FRONT);
		LineData backRightKnee1=addLine(pBackRightThigh[0][0][0],pBackRightThigh[0][1][0],pBackRightLeg[0][1][1],null,Renderer3D.CAR_LEFT);
		LineData backRightKnee2=addLine(pBackRightThigh[1][1][0],pBackRightThigh[1][0][0],pBackRightLeg[1][1][1],null,Renderer3D.CAR_RIGHT);
	
		//back Right Foot
		
		
		BPoint[][][] pBackRightFoot=new BPoint[2][2][2];
		
		pBackRightFoot[0][0][0]=addBPoint(0,0,0,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][0][0]=addBPoint(1.0,0,0,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][1][0]=addBPoint(1.0,1,0,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[0][1][0]=addBPoint(0,1,0,RIGHT_SHINBONE,rigLegFoot0);
		
		pBackRightFoot[0][0][1]=addBPoint(0,0,1,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][0][1]=addBPoint(1.0,0,1,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[1][1][1]=addBPoint(1.0,1.0,1,RIGHT_SHINBONE,rigLegFoot0);
		pBackRightFoot[0][1][1]=addBPoint(0,1.0,1,RIGHT_SHINBONE,rigLegFoot0);
		
		rotateYZ(pBackRightFoot,pBackRightThigh[1][1][1].y,pBackRightThigh[1][1][1].z,bq0);
		rotateYZ(pBackRightFoot,pBackRightLeg[0][0][1].y,pBackRightLeg[0][0][1].z,bq1);
		rotateYZ(pBackRightFoot,pBackRightFoot[0][1][1].y,pBackRightFoot[0][1][1].z,bq2);
		
		LineData backRightFoot=addLine(pBackRightFoot[0][0][0],pBackRightFoot[0][1][0],pBackRightFoot[1][1][0],pBackRightFoot[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData backRightFootS0=addLine(pBackRightFoot[0][0][0],pBackRightFoot[0][0][1],pBackRightFoot[0][1][1],pBackRightFoot[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData backRightFootS1=addLine(pBackRightFoot[0][1][0],pBackRightFoot[0][1][1],pBackRightFoot[1][1][1],pBackRightFoot[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData backRightFootS2=addLine(pBackRightFoot[1][1][0],pBackRightFoot[1][1][1],pBackRightFoot[1][0][1],pBackRightFoot[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData backRightFootS3=addLine(pBackRightFoot[1][0][0],pBackRightFoot[1][0][1],pBackRightFoot[0][0][1],pBackRightFoot[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backRightAnkle0=addLine(pBackRightFoot[0][0][1],pBackRightFoot[1][0][1],pBackRightLeg[1][0][0],pBackRightLeg[0][0][0],Renderer3D.CAR_BACK);
		LineData backRightAnkle1=addLine(pBackRightLeg[0][0][0],pBackRightLeg[0][1][0],pBackRightFoot[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backRightAnkle2=addLine( pBackRightFoot[1][0][1],pBackRightLeg[1][1][0],pBackRightLeg[1][0][0],null,Renderer3D.CAR_RIGHT);


		//left arm
		
		
		BPoint[][][] pFrontLeftArm=new BPoint[2][2][2];
		
		pFrontLeftArm[0][0][0]=addBPoint(0,0,0,LEFT_HOMERUS,lefArmHum0);
		pFrontLeftArm[1][0][0]=addBPoint(1.0,0,0,LEFT_HOMERUS,lefArmHum0);
		pFrontLeftArm[1][1][0]=addBPoint(1.0,1.0,0,LEFT_HOMERUS,lefArmHum0);
		pFrontLeftArm[0][1][0]=addBPoint(0,1.0,0,LEFT_HOMERUS,lefArmHum0);
		
		pFrontLeftArm[0][0][1]=body[0][numy-2][0];
		pFrontLeftArm[1][0][1]=body[1][numy-2][0];
		pFrontLeftArm[1][1][1]=body[1][numy-1][0];
		pFrontLeftArm[0][1][1]=body[0][numy-1][0];
		
		rotateYZ(pFrontLeftArm,pFrontLeftArm[0][0][1].y,pFrontLeftArm[0][0][1].z,fq0);
		
		LineData bottomLeftArm=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],Renderer3D.CAR_BOTTOM);	
		
		LineData FrontLeftArmS0=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][0][1],pFrontLeftArm[0][1][1],pFrontLeftArm[0][1][0],Renderer3D.CAR_LEFT);

		LineData FrontLeftArmS1=addLine(pFrontLeftArm[0][1][0],pFrontLeftArm[0][1][1],pFrontLeftArm[1][1][1],pFrontLeftArm[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData FrontLeftArmS2=addLine(pFrontLeftArm[1][1][0],pFrontLeftArm[1][1][1],pFrontLeftArm[1][0][1],pFrontLeftArm[1][0][0],Renderer3D.CAR_RIGHT);

		LineData FrontLeftArmS3=addLine(pFrontLeftArm[1][0][0],pFrontLeftArm[1][0][1],pFrontLeftArm[0][0][1],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
	
		
		//frontLeftForearm
		
		BPoint[][][] pFrontLeftForearm=new BPoint[2][2][2];
		
		pFrontLeftForearm[0][0][0]=addBPoint(0,0,0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][0][0]=addBPoint(1.0,0,0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][1][0]=addBPoint(1.0,1.0,0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[0][1][0]=addBPoint(0,1.0,0,LEFT_RADIUS,lefArmRad0);
		
		pFrontLeftForearm[0][0][1]=addBPoint(0,0,1.0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][0][1]=addBPoint(1.0,0,1.0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_RADIUS,lefArmRad0);
		pFrontLeftForearm[0][1][1]=addBPoint(0,1.0,1.0,LEFT_RADIUS,lefArmRad0);
		
		rotateYZ(pFrontLeftForearm,pFrontLeftArm[0][0][1].y,pFrontLeftArm[0][0][1].z,fq0);
		rotateYZ(pFrontLeftForearm,pFrontLeftForearm[0][1][1].y,pFrontLeftForearm[0][1][1].z,fq1);
		
		LineData FrontLeftForearm=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][1][0],pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData FrontLeftForearmS0=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][1][1],pFrontLeftForearm[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData FrontLeftForearmS1=addLine(pFrontLeftForearm[0][1][0],pFrontLeftForearm[0][1][1],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][1][0],Renderer3D.CAR_FRONT);

		LineData FrontLeftForearmS2=addLine(pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][1][1],pFrontLeftForearm[1][0][1],pFrontLeftForearm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftForearmS3=addLine(pFrontLeftForearm[1][0][0],pFrontLeftForearm[1][0][1],pFrontLeftForearm[0][0][1],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
	
		LineData backLeftElbow0=addLine(pFrontLeftForearm[0][0][1],pFrontLeftForearm[1][0][1],pFrontLeftArm[1][0][0],pFrontLeftArm[0][0][0],Renderer3D.CAR_BACK);
		LineData backLeftElbow1=addLine(pFrontLeftArm[0][0][0],pFrontLeftArm[0][1][0],pFrontLeftForearm[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftElbow2=addLine( pFrontLeftForearm[1][0][1],pFrontLeftArm[1][1][0],pFrontLeftArm[1][0][0],null,Renderer3D.CAR_RIGHT);
		
		//frontLeft Hand
		
		BPoint[][][] pFrontLeftHand=new BPoint[2][2][2];
		
		pFrontLeftHand[0][0][0]=addBPoint(0,0,0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][0][0]=addBPoint(1.0,0,0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][1][0]=addBPoint(1.0,1.0,0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[0][1][0]=addBPoint(0,1.0,0,LEFT_RADIUS,lefArmHan0);
		
		pFrontLeftHand[0][0][1]=addBPoint(0,0,1.0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][0][1]=addBPoint(1.0,0,1.0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[1][1][1]=addBPoint(1.0,1.0,1.0,LEFT_RADIUS,lefArmHan0);
		pFrontLeftHand[0][1][1]=addBPoint(0,1.0,1.0,LEFT_RADIUS,lefArmHan0);
		
		rotateYZ(pFrontLeftHand,pFrontLeftArm[0][0][1].y,pFrontLeftArm[0][0][1].z,fq0);
		rotateYZ(pFrontLeftHand,pFrontLeftForearm[0][1][1].y,pFrontLeftForearm[0][1][1].z,fq1);
		rotateYZ(pFrontLeftHand,pFrontLeftHand[0][1][1].y,pFrontLeftHand[0][1][1].z,fq2);
		
		LineData FrontLeftHand=addLine(pFrontLeftHand[0][0][0],pFrontLeftHand[0][1][0],pFrontLeftHand[1][1][0],pFrontLeftHand[1][0][0],Renderer3D.CAR_BOTTOM);
		
		LineData FrontLeftHandS0=addLine(pFrontLeftHand[0][0][0],pFrontLeftHand[0][0][1],pFrontLeftHand[0][1][1],pFrontLeftHand[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData FrontLeftHandS1=addLine(pFrontLeftHand[0][1][0],pFrontLeftHand[0][1][1],pFrontLeftHand[1][1][1],pFrontLeftHand[1][1][0],Renderer3D.CAR_FRONT);

		LineData FrontLeftHandS2=addLine(pFrontLeftHand[1][1][0],pFrontLeftHand[1][1][1],pFrontLeftHand[1][0][1],pFrontLeftHand[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData FrontLeftHandS3=addLine(pFrontLeftHand[1][0][0],pFrontLeftHand[1][0][1],pFrontLeftHand[0][0][1],pFrontLeftHand[0][0][0],Renderer3D.CAR_BACK);
		
		LineData backLeftWrist0=addLine(pFrontLeftHand[0][0][1],pFrontLeftHand[1][0][1],pFrontLeftForearm[1][0][0],pFrontLeftForearm[0][0][0],Renderer3D.CAR_BACK);
		LineData backLeftWrist1=addLine(pFrontLeftForearm[0][0][0],pFrontLeftForearm[0][1][0],pFrontLeftHand[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backLeftWrist2=addLine( pFrontLeftHand[1][0][1],pFrontLeftForearm[1][1][0],pFrontLeftForearm[1][0][0],null,Renderer3D.CAR_RIGHT);
		
		//right arm
		
		BPoint[][][] pFrontRightArm=new BPoint[2][2][2];
		
		pFrontRightArm[0][0][0]=addBPoint(0,0.0,0,RIGHT_HOMERUS,rigArmHum0);
		pFrontRightArm[1][0][0]=addBPoint(1.0,0.0,0,RIGHT_HOMERUS,rigArmHum0);
		pFrontRightArm[1][1][0]=addBPoint(1.0,1.0,0,RIGHT_HOMERUS,rigArmHum0);
		pFrontRightArm[0][1][0]=addBPoint(0,1.0,0,RIGHT_HOMERUS,rigArmHum0);
		
		pFrontRightArm[0][0][1]=body[numx-2][numy-2][0];
		pFrontRightArm[1][0][1]=body[numx-1][numy-2][0];
		pFrontRightArm[1][1][1]=body[numx-1][numy-1][0];
		pFrontRightArm[0][1][1]=body[numx-2][numy-1][0];
		
		rotateYZ(pFrontRightArm,pFrontRightArm[1][0][1].y,pFrontRightArm[1][0][1].z,fq0);
		
		LineData bottomRightArm=addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],Renderer3D.CAR_BOTTOM);	
		
		LineData frontRightArmS0=addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][0][1],pFrontRightArm[0][1][1],pFrontRightArm[0][1][0],Renderer3D.CAR_LEFT);
	
		LineData frontRightArmS1=addLine(pFrontRightArm[0][1][0],pFrontRightArm[0][1][1],pFrontRightArm[1][1][1],pFrontRightArm[1][1][0],Renderer3D.CAR_FRONT);
	
		LineData frontRightArmS2=addLine(pFrontRightArm[1][1][0],pFrontRightArm[1][1][1],pFrontRightArm[1][0][1],pFrontRightArm[1][0][0],Renderer3D.CAR_RIGHT);
	
		LineData frontRightArmS3=addLine(pFrontRightArm[1][0][0],pFrontRightArm[1][0][1],pFrontRightArm[0][0][1],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
	
	
			
		//frontRightForeArm
		
		BPoint[][][] pFrontRightForearm=new BPoint[2][2][2];
		
		pFrontRightForearm[0][0][0]=addBPoint(0,0,0,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][0][0]=addBPoint(1,0,0,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][1][0]=addBPoint(1,1,0,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[0][1][0]=addBPoint(0,1,0,RIGHT_RADIUS,rigArmRad0);
		
		pFrontRightForearm[0][0][1]=addBPoint(0,0,1,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][0][1]=addBPoint(1,0,1,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[1][1][1]=addBPoint(1,1,1,RIGHT_RADIUS,rigArmRad0);
		pFrontRightForearm[0][1][1]=addBPoint(0,1,1,RIGHT_RADIUS,rigArmRad0);
		
		rotateYZ(pFrontRightForearm,pFrontRightArm[1][0][1].y,pFrontRightArm[1][0][1].z,fq0);
		rotateYZ(pFrontRightForearm,pFrontRightForearm[0][1][1].y,pFrontRightForearm[0][1][1].z,fq1);
			
		addLine(pFrontRightForearm,0,0,0,rArmBlock.lf(0,0,1,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);

		addLine(pFrontRightForearm,0,0,0,rArmBlock.lf(0,0,1,Renderer3D.CAR_FRONT),Renderer3D.CAR_FRONT);

		addLine(pFrontRightForearm,0,0,0,rArmBlock.lf(0,0,1,Renderer3D.CAR_RIGHT),Renderer3D.CAR_RIGHT);
	
		addLine(pFrontRightForearm,0,0,0,rArmBlock.lf(0,0,1,Renderer3D.CAR_BACK),Renderer3D.CAR_BACK);
			
		
		LineData backRightElbow0=addLine(pFrontRightForearm[0][0][1],pFrontRightForearm[1][0][1],pFrontRightArm[1][0][0],pFrontRightArm[0][0][0],Renderer3D.CAR_BACK);
		LineData backRightElbow1=addLine(pFrontRightArm[0][0][0],pFrontRightArm[0][1][0],pFrontRightForearm[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backRightElbow2=addLine( pFrontRightForearm[1][0][1],pFrontRightArm[1][1][0],pFrontRightArm[1][0][0],null,Renderer3D.CAR_RIGHT);
		
		//frontRight Hand
		
		BPoint[][][] pFrontRightHand=new BPoint[2][2][2];
		
		pFrontRightHand[0][0][0]=addBPoint(0,0,0,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][0][0]=addBPoint(1,0,0,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][1][0]=addBPoint(1,1,0,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[0][1][0]=addBPoint(0,1,0,RIGHT_RADIUS,rigArmHan0);
		
		pFrontRightHand[0][0][1]=addBPoint(0,0,1,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][0][1]=addBPoint(1,0,1,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[1][1][1]=addBPoint(1,1,1,RIGHT_RADIUS,rigArmHan0);
		pFrontRightHand[0][1][1]=addBPoint(0,1,1,RIGHT_RADIUS,rigArmHan0);
		
		
		rotateYZ(pFrontRightHand,pFrontRightArm[1][0][1].y,pFrontRightArm[1][0][1].z,fq0);
		rotateYZ(pFrontRightHand,pFrontRightForearm[0][1][1].y,pFrontRightForearm[0][1][1].z,fq1);
		rotateYZ(pFrontRightHand,pFrontRightHand[0][1][1].y,pFrontRightHand[0][1][1].z,fq2);
			
		addLine(pFrontRightHand,0,0,0,rArmBlock.lf(0,0,0,Renderer3D.CAR_BACK),Renderer3D.CAR_BACK);
			
		addLine(pFrontRightHand,0,0,0,rArmBlock.lf(0,0,0,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);

		addLine(pFrontRightHand,0,0,0,rArmBlock.lf(0,0,0,Renderer3D.CAR_FRONT),Renderer3D.CAR_FRONT);

		addLine(pFrontRightHand,0,0,0,rArmBlock.lf(0,0,0,Renderer3D.CAR_RIGHT),Renderer3D.CAR_RIGHT);
	
		addLine(pFrontRightHand,0,0,0,rArmBlock.lf(0,0,0,Renderer3D.CAR_BACK),Renderer3D.CAR_BACK);
		
		LineData backRightWrist0=addLine(pFrontRightHand[0][0][1],pFrontRightHand[1][0][1],pFrontRightForearm[1][0][0],pFrontRightForearm[0][0][0],Renderer3D.CAR_BACK);
		LineData backRightWrist1=addLine(pFrontRightForearm[0][0][0],pFrontRightForearm[0][1][0],pFrontRightHand[0][0][1],null,Renderer3D.CAR_LEFT);
		LineData backRightWrist2=addLine( pFrontRightHand[1][0][1],pFrontRightForearm[1][1][0],pFrontRightForearm[1][0][0],null,Renderer3D.CAR_RIGHT);
	
	


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		
		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();
		
		/*int count=0;

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
		

		}*/


	}
	
private BPoint[][][] buildQuadrupedHeadMesh(double hz, double xc) {

		
		double height0=head_DZ;		
		double height1=head_DZ;
		double height2=head_DZ;
		double height3=head_DZ*0.6;
		double height4=head_DZ*0.4;
		
		double with0=head_DX*0.5;
		double with1=head_DX*0.957;
		double with2=head_DX*0.79;
		double with3=head_DX*0.39;
		double with4=head_DX*0.3;
		
		Segments h0=new Segments(xc,with0,y_side-neck_side,head_DY,hz,height0);
		Segments h1=new Segments(xc,with1,y_side-neck_side,head_DY,hz,height1);
		Segments h2=new Segments(xc,with2,y_side-neck_side,head_DY,hz,height2);
		Segments h3=new Segments(xc,with3,y_side-neck_side,head_DY,hz,height3);
		Segments h4=new Segments(xc,with4,y_side-neck_side,head_DY,hz,height4);


		BPoint[][][] head=new BPoint[hnx][hny][hnz];

		head[0][0][0]=addBPoint(-0.5,0.0,0,HEAD,h0);
		head[1][0][0]=addBPoint(-0.25,0.0,0,HEAD,h0);
		head[2][0][0]=addBPoint(0.0,0.0,0,HEAD,h0);	
		head[3][0][0]=addBPoint(0.25,0.0,0,HEAD,h0);	
		head[4][0][0]=addBPoint(0.5,0.0,0,HEAD,h0);	
		head[0][0][1]=addBPoint(-0.5,0.0,0.25,HEAD,h0);
		head[1][0][1]=addBPoint(-0.25,0.0,0.25,HEAD,h0);
		head[2][0][1]=addBPoint(0.0,0.0,0.25,HEAD,h0);
		head[3][0][1]=addBPoint(0.25,0.0,0.25,HEAD,h0);
		head[4][0][1]=addBPoint(0.5,0.0,0.25,HEAD,h0);
		head[0][0][2]=addBPoint(-0.5,0.0,0.5,HEAD,h0);
		head[1][0][2]=addBPoint(-0.25,0.0,0.5,HEAD,h0);
		head[2][0][2]=addBPoint(0.0,0.0,0.5,HEAD,h0);
		head[3][0][2]=addBPoint(0.25,0.0,0.5,HEAD,h0);
		head[4][0][2]=addBPoint(0.5,0.0,0.5,HEAD,h0);
		head[0][0][3]=addBPoint(-0.5,0.0,0.75,HEAD,h0);
		head[1][0][3]=addBPoint(-0.25,0.0,0.75,HEAD,h0);
		head[2][0][3]=addBPoint(0.0,0.0,0.75,HEAD,h0);
		head[3][0][3]=addBPoint(0.25,0.0,0.75,HEAD,h0);
		head[4][0][3]=addBPoint(0.5,0.0,0.75,HEAD,h0);
		head[0][0][4]=addBPoint(-0.5,0.0,1.0,HEAD,h0);
		head[1][0][4]=addBPoint(-0.25,0.0,1.0,HEAD,h0);
		head[2][0][4]=addBPoint(0.0,0.0,1.0,HEAD,h0);
		head[3][0][4]=addBPoint(0.25,0.0,1.0,HEAD,h0);
		head[4][0][4]=addBPoint(0.5,0.0,1.0,HEAD,h0);
		
		head[0][1][0]=addBPoint(-0.5,.25,0,HEAD,h1);
		head[1][1][0]=addBPoint(-0.25,.25,0,HEAD,h1);
		head[2][1][0]=addBPoint(0.0,.25,0,HEAD,h1);		
		head[3][1][0]=addBPoint(0.25,.25,0,HEAD,h1);	
		head[4][1][0]=addBPoint(0.5,.25,0,HEAD,h1);
		head[0][1][1]=addBPoint(-0.5,.25,0.25,HEAD,h1);
		head[4][1][1]=addBPoint(0.5,.25,0.25,HEAD,h1);
		head[0][1][2]=addBPoint(-0.5,.25,0.5,HEAD,h1);		
		head[4][1][2]=addBPoint(0.5,.25,0.5,HEAD,h1);
		head[0][1][3]=addBPoint(-0.5,.25,0.75,HEAD,h1);		
		head[4][1][3]=addBPoint(0.5,.25,0.75,HEAD,h1);
		head[0][1][4]=addBPoint(-0.5,.25,1.0,HEAD,h1);	
		head[1][1][4]=addBPoint(-0.25,.25,1.0,HEAD,h1);		
		head[2][1][4]=addBPoint(0.0,.25,1.0,HEAD,h1);	
		head[3][1][4]=addBPoint(0.25,.25,1.0,HEAD,h1);	
		head[4][1][4]=addBPoint(0.5,.25,1.0,HEAD,h1);	
		
		head[0][2][0]=addBPoint(-0.5,0.5,0,HEAD,h2);
		head[1][2][0]=addBPoint(-0.25,0.5,0,HEAD,h2);
		head[2][2][0]=addBPoint(0.0,0.5,0,HEAD,h2);	
		head[3][2][0]=addBPoint(0.25,0.5,0,HEAD,h2);
		head[4][2][0]=addBPoint(0.5,0.5,0,HEAD,h2);
		head[0][2][1]=addBPoint(-0.5,0.5,0.25,HEAD,h2);		
		head[4][2][1]=addBPoint(0.5,0.5,0.25,HEAD,h2);
		head[0][2][2]=addBPoint(-0.5,0.5,0.5,HEAD,h2);		
		head[4][2][2]=addBPoint(0.5,0.5,0.5,HEAD,h2);
		head[0][2][3]=addBPoint(-0.5,0.5,0.75,HEAD,h2);		
		head[4][2][3]=addBPoint(0.5,0.5,0.75,HEAD,h2);
		head[0][2][4]=addBPoint(-0.5,0.5,1.0,HEAD,h2);	
		head[1][2][4]=addBPoint(-0.25,0.5,1.0,HEAD,h2);	
		head[2][2][4]=addBPoint(0.0,0.5,1.0,HEAD,h2);
		head[3][2][4]=addBPoint(0.25,0.5,1.0,HEAD,h2);
		head[4][2][4]=addBPoint(0.5,0.5,1.0,HEAD,h2);
		
		head[0][3][0]=addBPoint(-0.5,0.75,0,HEAD,h3);
		head[1][3][0]=addBPoint(-0.25,0.75,0,HEAD,h3);
		head[2][3][0]=addBPoint(0.0,0.75,0,HEAD,h3);
		head[3][3][0]=addBPoint(0.25,0.75,0,HEAD,h3);
		head[4][3][0]=addBPoint(0.5,0.75,0,HEAD,h3);
		head[0][3][1]=addBPoint(-0.5,0.75,0.25,HEAD,h3);
		head[4][3][1]=addBPoint(0.5,0.75,0.25,HEAD,h3);
		head[0][3][2]=addBPoint(-0.5,0.75,0.5,HEAD,h3);
		head[4][3][2]=addBPoint(0.5,0.75,0.5,HEAD,h3);
		head[0][3][3]=addBPoint(-0.5,0.75,0.75,HEAD,h3);
		head[4][3][3]=addBPoint(0.5,0.75,0.75,HEAD,h3);
		head[0][3][4]=addBPoint(-0.5,0.75,1.0,HEAD,h3);	
		head[1][3][4]=addBPoint(-0.25,0.75,1.0,HEAD,h3);	
		head[2][3][4]=addBPoint(0.0,0.75,1.0,HEAD,h3);	
		head[3][3][4]=addBPoint(0.25,0.75,1.0,HEAD,h3);	
		head[4][3][4]=addBPoint(0.5,0.75,1.0,HEAD,h3);	
		
		head[0][4][0]=addBPoint(-0.5,1.0,0.0,HEAD,h4);
		head[1][4][0]=addBPoint(-0.25,1.0,0.0,HEAD,h4);
		head[2][4][0]=addBPoint(0.0,1.0,0.0,HEAD,h4);
		head[3][4][0]=addBPoint(0.25,1.0,0.0,HEAD,h4);
		head[4][4][0]=addBPoint(0.5,1.0,0.0,HEAD,h4);
		head[0][4][1]=addBPoint(-0.5,1.0,0.25,HEAD,h4);
		head[1][4][1]=addBPoint(-0.25,1.0,0.25,HEAD,h4);
		head[2][4][1]=addBPoint(0.0,1.0,0.25,HEAD,h4);
		head[3][4][1]=addBPoint(0.25,1.0,0.25,HEAD,h4);
		head[4][4][1]=addBPoint(0.5,1.0,0.25,HEAD,h4);
		head[0][4][2]=addBPoint(-0.5,1.0,0.5,HEAD,h4);
		head[1][4][2]=addBPoint(-0.25,1.0,0.5,HEAD,h4);
		head[2][4][2]=addBPoint(0.0,1.0,0.5,HEAD,h4);
		head[3][4][2]=addBPoint(0.25,1.0,0.5,HEAD,h4);
		head[4][4][2]=addBPoint(0.5,1.0,0.5,HEAD,h4);
		head[0][4][3]=addBPoint(-0.5,1.0,0.75,HEAD,h4);
		head[1][4][3]=addBPoint(-0.25,1.0,0.75,HEAD,h4);
		head[2][4][3]=addBPoint(0.0,1.0,0.75,HEAD,h4);
		head[3][4][3]=addBPoint(0.25,1.0,0.75,HEAD,h4);
		head[4][4][3]=addBPoint(0.5,1.0,0.75,HEAD,h4);
		head[0][4][4]=addBPoint(-0.5,1.0,1.0,HEAD,h4);
		head[1][4][4]=addBPoint(-0.25,1.0,1.0,HEAD,h4);
		head[2][4][4]=addBPoint(0.0,1.0,1.0,HEAD,h4);
		head[3][4][4]=addBPoint(0.25,1.0,1.0,HEAD,h4);
		head[4][4][4]=addBPoint(0.5,1.0,1.0,HEAD,h4);

		for (int i = 0; i < hnx-1; i++) {			


			for (int j = 0; j < hny-1; j++) {

				for (int k = 0; k < hnz-1; k++) {				

					if(j==0){


						addLine(head,i,j,k,headBlock.lf(i,j,k,Renderer3D.CAR_LEFT),Renderer3D.CAR_BACK);
					}





					if(k+1==hnz-1)
						addLine(head,i,j,k,headBlock.lf(i,j,k,Renderer3D.CAR_LEFT),Renderer3D.CAR_TOP);

					if(k==0)
						addLine(head,i,j,k,headBlock.lf(i,j,k,Renderer3D.CAR_BOTTOM),Renderer3D.CAR_BOTTOM);

					if(i==0)
						addLine(head,i,j,k,headBlock.lf(i,j,k,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);

					if(i+1==hnx-1)
						addLine(head,i,j,k,headBlock.lf(i,j,k,Renderer3D.CAR_RIGHT),Renderer3D.CAR_RIGHT);


					if(j+1==hny-1){

						addLine(head,i,j,k,headBlock.lf(i,j,k,Renderer3D.CAR_FRONT),Renderer3D.CAR_FRONT);

					}

				}

			}


		}

		
		////
		
		return head;
	}
	
}
