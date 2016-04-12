package com.editors.animals.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.BPoint;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

class ManHand extends Animal{

	private int N_FACES=4;
	private int N_PARALLELS=2;

	private Point3D[] upperBase=null;
	private Point3D[] lowerBase=null;
	private Point3D[][] lateralFaces=null; 
	

	ManHand(double x_side, double y_side,double z_side,int animal_type,
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
	

	
	@Override	
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

	private ArrayList<Point3D> buildTexturePoints() {
		
		isTextureDrawing=false;
		
		ArrayList<Point3D> texture_points=new ArrayList<Point3D>();
		


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
	@Override
	public double calX(double x){
		
		return texture_x0+x;
	}
	@Override
	public double calY(double y){
		if(isTextureDrawing)
			return IMG_HEIGHT-(texture_y0+y);
		else
			return texture_y0+y;
	}
	
	private void initMesh( ) {
		
		points=new ArrayList<Point3D>();
		polyData=new ArrayList<LineData>();

		n=0;
		
		
		
		int pnx=9;
		int pny=5;
		int pnz=2;
		
		BPoint[][][] palm=new BPoint[pnx][pny][pnz];
		
		double xc=0;
		
		double df=0.175;
		double ds=(1.0-df*4.0)/4.0;
		
		double[] x={0,ds,ds+df,2*ds+df,2*ds+2*df,3*ds+2*df,3*ds+3*df,4*ds+3*df,4*ds+4*df};
		//middle fingers
		double[] mx={(ds+df)-df*0.5,2*(ds+df)-df*0.5,3*(ds+df)-df*0.5,4*(ds+df)-df*0.5};
		
		Segments p0=new Segments(xc,x_side,0,y_side,0,z_side);

		palm[0][0][0]=addBPoint(x[0],0,0,TRUNK,p0);
		palm[1][0][0]=addBPoint(x[1],0.0,0.4,TRUNK,p0);
		palm[2][0][0]=addBPoint(x[2],0.0,0.4,TRUNK,p0);
		palm[3][0][0]=addBPoint(x[3],0.0,0.6,TRUNK,p0);
		palm[4][0][0]=addBPoint(x[4],0.0,0.6,TRUNK,p0);
		palm[5][0][0]=addBPoint(x[5],0.0,0.4,TRUNK,p0);
		palm[6][0][0]=addBPoint(x[6],0.0,0.4,TRUNK,p0);
		palm[7][0][0]=addBPoint(x[7],0.0,0,TRUNK,p0);
		palm[8][0][0]=addBPoint(x[8],0.0,0,TRUNK,p0);
		palm[0][1][0]=addBPoint(x[0],0.25,0,TRUNK,p0);
		palm[1][1][0]=addBPoint(x[1],0.25,0.4,TRUNK,p0);
		palm[2][1][0]=addBPoint(x[2],0.25,0.4,TRUNK,p0);
		palm[3][1][0]=addBPoint(x[3],0.25,0.6,TRUNK,p0);
		palm[4][1][0]=addBPoint(x[4],0.25,0.6,TRUNK,p0);
		palm[5][1][0]=addBPoint(x[5],0.25,0.4,TRUNK,p0);
		palm[6][1][0]=addBPoint(x[6],0.25,0.4,TRUNK,p0);
		palm[7][1][0]=addBPoint(x[7],0.25,0,TRUNK,p0);
		palm[8][1][0]=addBPoint(x[8],0.25,0,TRUNK,p0);
		palm[0][2][0]=addBPoint(x[0],0.5,0,TRUNK,p0);
		palm[1][2][0]=addBPoint(x[1],0.5,0.4,TRUNK,p0);
		palm[2][2][0]=addBPoint(x[2],0.5,0.4,TRUNK,p0);
		palm[3][2][0]=addBPoint(x[3],0.5,0.6,TRUNK,p0);
		palm[4][2][0]=addBPoint(x[4],0.5,0.6,TRUNK,p0);
		palm[5][2][0]=addBPoint(x[5],0.5,0.4,TRUNK,p0);
		palm[6][2][0]=addBPoint(x[6],0.5,0.4,TRUNK,p0);
		palm[7][2][0]=addBPoint(x[7],0.5,0,TRUNK,p0);
		palm[8][2][0]=addBPoint(x[8],0.5,0,TRUNK,p0);
		palm[0][3][0]=addBPoint(x[0],0.75,0.5,TRUNK,p0);
		palm[1][3][0]=addBPoint(x[1],0.75,0.9,TRUNK,p0);
		palm[2][3][0]=addBPoint(x[2],0.75,0.9,TRUNK,p0);
		palm[3][3][0]=addBPoint(x[3],0.75,1.1,TRUNK,p0);
		palm[4][3][0]=addBPoint(x[4],0.75,1.1,TRUNK,p0);
		palm[5][3][0]=addBPoint(x[5],0.75,0.9,TRUNK,p0);
		palm[6][3][0]=addBPoint(x[6],0.75,0.9,TRUNK,p0);
		palm[7][3][0]=addBPoint(x[7],0.75,0.5,TRUNK,p0);
		palm[8][3][0]=addBPoint(x[8],0.75,0.5,TRUNK,p0);
		palm[0][4][0]=addBPoint(x[0],1.0,1.0,TRUNK,p0);
		palm[1][4][0]=addBPoint(x[1],1.0,1.4,TRUNK,p0);
		palm[2][4][0]=addBPoint(x[2],1.0,1.4,TRUNK,p0);
		palm[3][4][0]=addBPoint(x[3],1.0,1.6,TRUNK,p0);
		palm[4][4][0]=addBPoint(x[4],1.0,1.6,TRUNK,p0);
		palm[5][4][0]=addBPoint(x[5],1.0,1.4,TRUNK,p0);
		palm[6][4][0]=addBPoint(x[6],1.0,1.4,TRUNK,p0);
		palm[7][4][0]=addBPoint(x[7],1.0,1.0,TRUNK,p0);
		palm[8][4][0]=addBPoint(x[8],1.0,1.0,TRUNK,p0);
		
		palm[0][0][1]=addBPoint(x[0],0,1.0,TRUNK,p0);
		palm[1][0][1]=addBPoint(x[1],0.0,1.4,TRUNK,p0);
		palm[2][0][1]=addBPoint(x[2],0.0,1.4,TRUNK,p0);
		palm[3][0][1]=addBPoint(x[3],0.0,1.6,TRUNK,p0);
		palm[4][0][1]=addBPoint(x[4],0.0,1.6,TRUNK,p0);
		palm[5][0][1]=addBPoint(x[5],0.0,1.4,TRUNK,p0);
		palm[6][0][1]=addBPoint(x[6],0.0,1.4,TRUNK,p0);
		palm[7][0][1]=addBPoint(x[7],0.0,1.0,TRUNK,p0);
		palm[8][0][1]=addBPoint(x[8],0.0,1.0,TRUNK,p0);
		palm[0][1][1]=addBPoint(x[0],0.25,1.0,TRUNK,p0);
		palm[1][1][1]=addBPoint(x[1],0.25,1.4,TRUNK,p0);
		palm[2][1][1]=addBPoint(x[2],0.25,1.4,TRUNK,p0);
		palm[3][1][1]=addBPoint(x[3],0.25,1.6,TRUNK,p0);
		palm[4][1][1]=addBPoint(x[4],0.25,1.6,TRUNK,p0);
		palm[5][1][1]=addBPoint(x[5],0.25,1.4,TRUNK,p0);
		palm[6][1][1]=addBPoint(x[6],0.25,1.4,TRUNK,p0);
		palm[7][1][1]=addBPoint(x[7],0.25,1.0,TRUNK,p0);
		palm[8][1][1]=addBPoint(x[8],0.25,1.0,TRUNK,p0);
		palm[0][2][1]=addBPoint(x[0],0.5,1.0,TRUNK,p0);
		palm[1][2][1]=addBPoint(x[1],0.5,1.4,TRUNK,p0);
		palm[2][2][1]=addBPoint(x[2],0.5,1.4,TRUNK,p0);
		palm[3][2][1]=addBPoint(x[3],0.5,1.6,TRUNK,p0);
		palm[4][2][1]=addBPoint(x[4],0.5,1.6,TRUNK,p0);
		palm[5][2][1]=addBPoint(x[5],0.5,1.4,TRUNK,p0);
		palm[6][2][1]=addBPoint(x[6],0.5,1.4,TRUNK,p0);
		palm[7][2][1]=addBPoint(x[7],0.5,1.0,TRUNK,p0);
		palm[8][2][1]=addBPoint(x[8],0.5,1.0,TRUNK,p0);
		palm[0][3][1]=addBPoint(x[0],0.75,1.5,TRUNK,p0);
		palm[1][3][1]=addBPoint(x[1],0.75,1.9,TRUNK,p0);
		palm[2][3][1]=addBPoint(x[2],0.75,1.9,TRUNK,p0);
		palm[3][3][1]=addBPoint(x[3],0.75,2.1,TRUNK,p0);
		palm[4][3][1]=addBPoint(x[4],0.75,2.1,TRUNK,p0);
		palm[5][3][1]=addBPoint(x[5],0.75,1.9,TRUNK,p0);
		palm[6][3][1]=addBPoint(x[6],0.75,1.9,TRUNK,p0);
		palm[7][3][1]=addBPoint(x[7],0.75,1.5,TRUNK,p0);
		palm[8][3][1]=addBPoint(x[8],0.75,1.5,TRUNK,p0);
		palm[0][4][1]=addBPoint(x[0],1.0,2.0,TRUNK,p0);
		palm[1][4][1]=addBPoint(x[1],1.0,2.4,TRUNK,p0);
		palm[2][4][1]=addBPoint(x[2],1.0,2.4,TRUNK,p0);
		palm[3][4][1]=addBPoint(x[3],1.0,2.6,TRUNK,p0);
		palm[4][4][1]=addBPoint(x[4],1.0,2.6,TRUNK,p0);
		palm[5][4][1]=addBPoint(x[5],1.0,2.4,TRUNK,p0);
		palm[6][4][1]=addBPoint(x[6],1.0,2.4,TRUNK,p0);
		palm[7][4][1]=addBPoint(x[7],1.0,2.0,TRUNK,p0);
		palm[8][4][1]=addBPoint(x[8],1.0,2.0,TRUNK,p0);

		
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {

                    //if(j!=0) continue;


					if(i==0){

						LineData leftLD=addLine(palm[i][j][k],palm[i][j][k+1],palm[i][j+1][k+1],palm[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(palm[i][j][k],palm[i][j+1][k],palm[i+1][j+1][k],palm[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						LineData topLD=addLine(palm[i][j][k+1],palm[i+1][j][k+1],palm[i+1][j+1][k+1],palm[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(palm[i][j][k],palm[i+1][j][k],palm[i+1][j][k+1],palm[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						LineData frontLD=addLine(palm[i][j+1][k],palm[i][j+1][k+1],palm[i+1][j+1][k+1],palm[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						LineData rightLD=addLine(palm[i+1][j][k],palm[i+1][j+1][k],palm[i+1][j+1][k+1],palm[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		int ffnx=2;
		int ffny=4;
		int ffnz=2;
		
		BPoint[][][] foreFinger=new BPoint[ffnx][ffny][ffnz];
		
		Segments ff0=new Segments(mx[0]*x_side,df*x_side,y_side,y_side*0.9,z_side,z_side);
		
		foreFinger[0][0][0]=palm[1][pny-1][0];
		foreFinger[1][0][0]=palm[2][pny-1][0];
		foreFinger[0][0][1]=palm[1][pny-1][1];
		foreFinger[1][0][1]=palm[2][pny-1][1];
		
		foreFinger[0][1][0]=addBPoint(-0.5,0.5,0.4,LEFT_HOMERUS,ff0);
		foreFinger[1][1][0]=addBPoint(0.5,0.5,0.4,LEFT_HOMERUS,ff0);
		foreFinger[0][1][1]=addBPoint(-0.5,0.5,1.4,LEFT_HOMERUS,ff0);
		foreFinger[1][1][1]=addBPoint(0.5,0.5,1.4,LEFT_HOMERUS,ff0);
		
		foreFinger[0][2][0]=addBPoint(-0.5,0.75,0.4,LEFT_HOMERUS,ff0);
		foreFinger[1][2][0]=addBPoint(0.5,0.75,0.4,LEFT_HOMERUS,ff0);
		foreFinger[0][2][1]=addBPoint(-0.5,0.75,1.4,LEFT_HOMERUS,ff0);
		foreFinger[1][2][1]=addBPoint(0.5,0.75,1.4,LEFT_HOMERUS,ff0);
		
		foreFinger[0][3][0]=addBPoint(-0.4,1.0,0.4,LEFT_HOMERUS,ff0);
		foreFinger[1][3][0]=addBPoint(0.4,1.0,0.4,LEFT_HOMERUS,ff0);
		foreFinger[0][3][1]=addBPoint(-0.4,1.0,1.4,LEFT_HOMERUS,ff0);
		foreFinger[1][3][1]=addBPoint(0.4,1.0,1.4,LEFT_HOMERUS,ff0);
		
		
		
		for (int i = 0; i < ffnx-1; i++) {


			for (int j = 0; j < ffny-1; j++) {

				for (int k = 0; k < ffnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(foreFinger[i][j][k],foreFinger[i][j][k+1],foreFinger[i][j+1][k+1],foreFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(foreFinger[i][j][k],foreFinger[i][j+1][k],foreFinger[i+1][j+1][k],foreFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==ffnz-1){
						LineData topLD=addLine(foreFinger[i][j][k+1],foreFinger[i+1][j][k+1],foreFinger[i+1][j+1][k+1],foreFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(foreFinger[i][j][k],foreFinger[i+1][j][k],foreFinger[i+1][j][k+1],foreFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==ffny-1){
						LineData frontLD=addLine(foreFinger[i][j+1][k],foreFinger[i][j+1][k+1],foreFinger[i+1][j+1][k+1],foreFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==ffnx-1){

						LineData rightLD=addLine(foreFinger[i+1][j][k],foreFinger[i+1][j+1][k],foreFinger[i+1][j+1][k+1],foreFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}

		
		int mfnx=2;
		int mfny=4;
		int mfnz=2;
		
		BPoint[][][] middleFinger=new BPoint[mfnx][mfny][mfnz];
		
		Segments mf0=new Segments(mx[1]*x_side,df*x_side,y_side,y_side,z_side,z_side);
		
		middleFinger[0][0][0]=palm[3][pny-1][0];
		middleFinger[1][0][0]=palm[4][pny-1][0];
		middleFinger[0][0][1]=palm[3][pny-1][1];
		middleFinger[1][0][1]=palm[4][pny-1][1];
		
		middleFinger[0][1][0]=addBPoint(-0.5,0.5,0.6,LEFT_FEMUR,mf0);
		middleFinger[1][1][0]=addBPoint(0.5,0.5,0.6,LEFT_FEMUR,mf0);
		middleFinger[0][1][1]=addBPoint(-0.5,0.5,1.6,LEFT_FEMUR,mf0);
		middleFinger[1][1][1]=addBPoint(0.5,0.5,1.6,LEFT_FEMUR,mf0);
		
		middleFinger[0][2][0]=addBPoint(-0.5,0.75,0.6,LEFT_FEMUR,mf0);
		middleFinger[1][2][0]=addBPoint(0.5,0.75,0.6,LEFT_FEMUR,mf0);
		middleFinger[0][2][1]=addBPoint(-0.5,0.75,1.6,LEFT_FEMUR,mf0);
		middleFinger[1][2][1]=addBPoint(0.5,0.75,1.6,LEFT_FEMUR,mf0);
		
		middleFinger[0][3][0]=addBPoint(-0.4,1.0,0.6,LEFT_FEMUR,mf0);
		middleFinger[1][3][0]=addBPoint(0.4,1.0,0.6,LEFT_FEMUR,mf0);
		middleFinger[0][3][1]=addBPoint(-0.4,1.0,1.6,LEFT_FEMUR,mf0);
		middleFinger[1][3][1]=addBPoint(0.4,1.0,1.6,LEFT_FEMUR,mf0);
		
		for (int i = 0; i < mfnx-1; i++) {


			for (int j = 0; j < mfny-1; j++) {

				for (int k = 0; k < mfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(middleFinger[i][j][k],middleFinger[i][j][k+1],middleFinger[i][j+1][k+1],middleFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(middleFinger[i][j][k],middleFinger[i][j+1][k],middleFinger[i+1][j+1][k],middleFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==mfnz-1){
						LineData topLD=addLine(middleFinger[i][j][k+1],middleFinger[i+1][j][k+1],middleFinger[i+1][j+1][k+1],middleFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(middleFinger[i][j][k],middleFinger[i+1][j][k],middleFinger[i+1][j][k+1],middleFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==mfny-1){
						LineData frontLD=addLine(middleFinger[i][j+1][k],middleFinger[i][j+1][k+1],middleFinger[i+1][j+1][k+1],middleFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==mfnx-1){

						LineData rightLD=addLine(middleFinger[i+1][j][k],middleFinger[i+1][j+1][k],middleFinger[i+1][j+1][k+1],middleFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		int rfnx=2;
		int rfny=4;
		int rfnz=2;
		
		BPoint[][][] ringFinger=new BPoint[rfnx][rfny][rfnz];
		
		Segments rf0=new Segments(mx[2]*x_side,df*x_side,y_side,y_side*0.8,z_side,z_side);
		
		ringFinger[0][0][0]=palm[5][pny-1][0];
		ringFinger[1][0][0]=palm[6][pny-1][0];
		ringFinger[0][0][1]=palm[5][pny-1][1];
		ringFinger[1][0][1]=palm[6][pny-1][1];
		
		ringFinger[0][1][0]=addBPoint(-0.5,0.5,0.4,RIGHT_FEMUR,rf0);
		ringFinger[1][1][0]=addBPoint(0.5,0.5,0.4,RIGHT_FEMUR,rf0);
		ringFinger[0][1][1]=addBPoint(-0.5,0.5,1.4,RIGHT_FEMUR,rf0);
		ringFinger[1][1][1]=addBPoint(0.5,0.5,1.4,RIGHT_FEMUR,rf0);

		ringFinger[0][2][0]=addBPoint(-0.5,0.75,0.4,RIGHT_FEMUR,rf0);
		ringFinger[1][2][0]=addBPoint(0.5,0.75,0.4,RIGHT_FEMUR,rf0);
		ringFinger[0][2][1]=addBPoint(-0.5,0.75,1.4,RIGHT_FEMUR,rf0);
		ringFinger[1][2][1]=addBPoint(0.5,0.75,1.4,RIGHT_FEMUR,rf0);
		
		ringFinger[0][3][0]=addBPoint(-0.4,1.0,0.4,RIGHT_FEMUR,rf0);
		ringFinger[1][3][0]=addBPoint(0.4,1.0,0.4,RIGHT_FEMUR,rf0);
		ringFinger[0][3][1]=addBPoint(-0.4,1.0,1.4,RIGHT_FEMUR,rf0);
		ringFinger[1][3][1]=addBPoint(0.4,1.0,1.4,RIGHT_FEMUR,rf0);
		
		for (int i = 0; i < rfnx-1; i++) {


			for (int j = 0; j < rfny-1; j++) {

				for (int k = 0; k < rfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(ringFinger[i][j][k],ringFinger[i][j][k+1],ringFinger[i][j+1][k+1],ringFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(ringFinger[i][j][k],ringFinger[i][j+1][k],ringFinger[i+1][j+1][k],ringFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==rfnz-1){
						LineData topLD=addLine(ringFinger[i][j][k+1],ringFinger[i+1][j][k+1],ringFinger[i+1][j+1][k+1],ringFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(ringFinger[i][j][k],ringFinger[i+1][j][k],ringFinger[i+1][j][k+1],ringFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==rfny-1){
						LineData frontLD=addLine(ringFinger[i][j+1][k],ringFinger[i][j+1][k+1],ringFinger[i+1][j+1][k+1],ringFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==rfnx-1){

						LineData rightLD=addLine(ringFinger[i+1][j][k],ringFinger[i+1][j+1][k],ringFinger[i+1][j+1][k+1],ringFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		
		int lfnx=2;
		int lfny=4;
		int lfnz=2;
		
		BPoint[][][] littleFinger=new BPoint[lfnx][lfny][lfnz];
		
		Segments lf0=new Segments(mx[3]*x_side,df*x_side,y_side,y_side*0.7,z_side,z_side);
		
		littleFinger[0][0][0]=palm[7][pny-1][0];
		littleFinger[1][0][0]=palm[8][pny-1][0];
		littleFinger[0][0][1]=palm[7][pny-1][1];
		littleFinger[1][0][1]=palm[8][pny-1][1];
		
		littleFinger[0][1][0]=addBPoint(-0.5,0.5,0,RIGHT_HOMERUS,lf0);
		littleFinger[1][1][0]=addBPoint(0.5,0.5,0,RIGHT_HOMERUS,lf0);
		littleFinger[0][1][1]=addBPoint(-0.5,0.5,1.0,RIGHT_HOMERUS,lf0);
		littleFinger[1][1][1]=addBPoint(0.5,0.5,1.0,RIGHT_HOMERUS,lf0);
		
		littleFinger[0][2][0]=addBPoint(-0.5,0.75,0,RIGHT_HOMERUS,lf0);
		littleFinger[1][2][0]=addBPoint(0.5,0.75,0,RIGHT_HOMERUS,lf0);
		littleFinger[0][2][1]=addBPoint(-0.5,0.75,1.0,RIGHT_HOMERUS,lf0);
		littleFinger[1][2][1]=addBPoint(0.5,0.75,1.0,RIGHT_HOMERUS,lf0);
		
		littleFinger[0][3][0]=addBPoint(-0.4,1.0,0,RIGHT_HOMERUS,lf0);
		littleFinger[1][3][0]=addBPoint(0.4,1.0,0,RIGHT_HOMERUS,lf0);
		littleFinger[0][3][1]=addBPoint(-0.4,1.0,1.0,RIGHT_HOMERUS,lf0);
		littleFinger[1][3][1]=addBPoint(0.4,1.0,1.0,RIGHT_HOMERUS,lf0);
		
		
		for (int i = 0; i < lfnx-1; i++) {


			for (int j = 0; j < lfny-1; j++) {

				for (int k = 0; k < lfnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(littleFinger[i][j][k],littleFinger[i][j][k+1],littleFinger[i][j+1][k+1],littleFinger[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(littleFinger[i][j][k],littleFinger[i][j+1][k],littleFinger[i+1][j+1][k],littleFinger[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==lfnz-1){
						LineData topLD=addLine(littleFinger[i][j][k+1],littleFinger[i+1][j][k+1],littleFinger[i+1][j+1][k+1],littleFinger[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(littleFinger[i][j][k],littleFinger[i+1][j][k],littleFinger[i+1][j][k+1],littleFinger[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==lfny-1){
						LineData frontLD=addLine(littleFinger[i][j+1][k],littleFinger[i][j+1][k+1],littleFinger[i+1][j+1][k+1],littleFinger[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==lfnx-1){

						LineData rightLD=addLine(littleFinger[i+1][j][k],littleFinger[i+1][j+1][k],littleFinger[i+1][j+1][k+1],littleFinger[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		int tnx=2;
		int tny=5;
		int tnz=2;
		
		BPoint[][][] thumb=new BPoint[tnx][tny][tnz];
		
		
		double dl0=1.125*y_side*0.5;
		double dl1=1.125*y_side*0.5;
		double dl1p=Math.sqrt(dl1*dl1-(df*x_side)*(df*x_side));
		
		
		double thumb_dy=dl0+dl1p;
		double df1=dl1p/thumb_dy;
		
		Segments tf0=new Segments(xc,df*2*x_side,y_side*0.25,thumb_dy,0,z_side);
		
		double thumb_y0=-y_side*0.25/thumb_dy;
		
		
		thumb[0][0][0]=addBPoint(-0.5,thumb_y0,0,TRUNK,tf0);		
		thumb[1][0][0]=addBPoint(0.0,thumb_y0,0.0,TRUNK,tf0);
		thumb[0][0][1]=addBPoint(-0.5,thumb_y0,1.0,TRUNK,tf0);
		thumb[1][0][1]=addBPoint(0.0,thumb_y0,1.0,TRUNK,tf0);
		
		thumb[0][1][0]=addBPoint(-0.5,0.0,0,TRUNK,tf0);		
		thumb[1][1][0]=addBPoint(0.0,0.0,0.0,TRUNK,tf0);
		thumb[0][1][1]=addBPoint(-0.5,0.0,1.0,TRUNK,tf0);
		thumb[1][1][1]=addBPoint(0.0,0.0,1.0,TRUNK,tf0);
		
		thumb[0][2][0]=addBPoint(-1.0,df1,0,TRUNK,tf0);		
		thumb[1][2][0]=addBPoint(-0.5,df1,0.0,TRUNK,tf0);
		thumb[0][2][1]=addBPoint(-1.0,df1,1.0,TRUNK,tf0);
		thumb[1][2][1]=addBPoint(-0.5,df1,1.0,TRUNK,tf0);
		
		thumb[0][3][0]=addBPoint(-1.0,0.75,0,TRUNK,tf0);		
		thumb[1][3][0]=addBPoint(-0.5,0.75,0.0,TRUNK,tf0);
		thumb[0][3][1]=addBPoint(-1.0,0.75,1.0,TRUNK,tf0);
		thumb[1][3][1]=addBPoint(-0.5,0.75,1.0,TRUNK,tf0);
		
		thumb[0][4][0]=addBPoint(-1.0,1.0,0,TRUNK,tf0);		
		thumb[1][4][0]=addBPoint(-0.5,1.0,0.0,TRUNK,tf0);
		thumb[0][4][1]=addBPoint(-1.0,1.0,1.0,TRUNK,tf0);
		thumb[1][4][1]=addBPoint(-0.5,1.0,1.0,TRUNK,tf0);

		
		for (int i = 0; i < tnx-1; i++) {


			for (int j = 0; j < tny-1; j++) {

				for (int k = 0; k < tnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(thumb[i][j][k],thumb[i][j][k+1],thumb[i][j+1][k+1],thumb[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(thumb[i][j][k],thumb[i][j+1][k],thumb[i+1][j+1][k],thumb[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==tnz-1){
						LineData topLD=addLine(thumb[i][j][k+1],thumb[i+1][j][k+1],thumb[i+1][j+1][k+1],thumb[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(thumb[i][j][k],thumb[i+1][j][k],thumb[i+1][j][k+1],thumb[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==tny-1){
						LineData frontLD=addLine(thumb[i][j+1][k],thumb[i][j+1][k+1],thumb[i+1][j+1][k+1],thumb[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==tnx-1){

						if(j>=2){
							LineData rightLD=addLine(thumb[i+1][j][k],thumb[i+1][j+1][k],thumb[i+1][j+1][k+1],thumb[i+1][j][k+1],Renderer3D.CAR_RIGHT);
						}
					}
				}
			}

		}
		//additional thumb polygons:
		addLine(thumb[1][1][0],thumb[1][2][0],palm[0][3][0],null,Renderer3D.CAR_BOTTOM);		
		addLine(thumb[1][2][0],thumb[1][2][1],palm[0][3][1],palm[0][3][0],Renderer3D.CAR_FRONT);
		addLine(palm[0][3][1],thumb[1][2][1],thumb[1][1][1],null,Renderer3D.CAR_TOP);


		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		
	/*	LineData lowerBase=new LineData();
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
		

		}*/


	}
	
}
