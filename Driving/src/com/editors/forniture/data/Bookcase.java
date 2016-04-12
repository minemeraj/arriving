package com.editors.forniture.data;

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

class Bookcase extends Forniture{
	
	private int N_FACES=4;
	private int N_PARALLELS=2;
	
	private double len;
	
	private Point3D[] upperBase=null;
	private Point3D[] lowerBase=null;
	private Point3D[][] lateralFaces=null; 
	

	Bookcase( double x_side, double y_side,double z_side,int forniture_type,
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
		
		Segments u0=new Segments(0,x_side,0,y_side,0,leg_length);
		
		BPoint[][][] body=new BPoint[2][2][2];
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,u0);
		body[1][0][0]=addBPoint(0.5,0.0,0,u0);
		body[1][1][0]=addBPoint(0.5,1.0,0,u0);
		body[0][1][0]=addBPoint(-0.5,1.0,0,u0);
		
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,u0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,u0);
		body[1][1][1]=addBPoint(0.5,1.0,1.0,u0);
		body[0][1][1]=addBPoint(-0.5,1.0,1.0,u0);
		
		addLine(body[0][0][1],body[1][0][1],body[1][1][1],body[0][1][1],Renderer3D.CAR_TOP);		

		addLine(body[0][0][0],body[0][0][1],body[0][1][1],body[0][1][0],Renderer3D.CAR_LEFT);				

		addLine(body[1][0][0],body[1][1][0],body[1][1][1],body[1][0][1],Renderer3D.CAR_RIGHT);
		
		addLine(body[0][1][0],body[0][1][1],body[1][1][1],body[1][1][0],Renderer3D.CAR_FRONT);
		
		addLine(body[0][0][0],body[1][0][0],body[1][0][1],body[0][0][1],Renderer3D.CAR_BACK);
		
		addLine(body[0][0][0],body[0][1][0],body[1][1][0],body[1][0][0],Renderer3D.CAR_BOTTOM);
		
		
		Segments b0=new Segments(0,x_side,0,y_side,leg_length,z_side);
		
		int lev=4;
		double alfa=0.1;
		int shz=lev*2;
		
		
		double rz1=1.0/(lev*(alfa+1)-1);
		double rz0=alfa*rz1;
		
		BPoint[][][] shelves=new BPoint[4][4][shz];
		
		
		
		for (int i = 0; i < shz; i=i+2) {
			
		
			double rz=(rz0+rz1)*(i/2);
			
			double dx=(x_side*0.5-leg_side)/x_side;
			double dy=(leg_side)/y_side;
			
			shelves[0][0][i]=addBPoint(-0.5,0,rz,b0);
			shelves[1][0][i]=addBPoint(-dx,0,rz,b0);
			shelves[2][0][i]=addBPoint(dx,0,rz,b0);
			shelves[3][0][i]=addBPoint(0.5,0,rz,b0);
			
			shelves[0][1][i]=addBPoint(-0.5,dy,rz,b0);
			shelves[1][1][i]=addBPoint(-dx,dy,rz,b0);
			shelves[2][1][i]=addBPoint(dx,dy,rz,b0);
			shelves[3][1][i]=addBPoint(0.5,dy,rz,b0);
			
			shelves[0][2][i]=addBPoint(-0.5,1.0-dy,rz,b0);
			shelves[1][2][i]=addBPoint(-dx,1.0-dy,rz,b0);
			shelves[2][2][i]=addBPoint(dx,1.0-dy,rz,b0);
			shelves[3][2][i]=addBPoint(0.5,1.0-dy,rz,b0);
			
			shelves[0][3][i]=addBPoint(-0.5,1.0,rz,b0);
			shelves[1][3][i]=addBPoint(-dx,1.0,rz,b0);
			shelves[2][3][i]=addBPoint(dx,1.0,rz,b0);
			shelves[3][3][i]=addBPoint(0.5,1.0,rz,b0);	
			
			
			shelves[0][0][i+1]=addBPoint(-0.5,0,rz+rz0,b0);
			shelves[1][0][i+1]=addBPoint(-dx,0,rz+rz0,b0);
			shelves[2][0][i+1]=addBPoint(dx,0,rz+rz0,b0);
			shelves[3][0][i+1]=addBPoint(0.5,0,rz+rz0,b0);
			
			shelves[0][1][i+1]=addBPoint(-0.5,dy,rz+rz0,b0);
			shelves[1][1][i+1]=addBPoint(-dx,dy,rz+rz0,b0);
			shelves[2][1][i+1]=addBPoint(dx,dy,rz+rz0,b0);
			shelves[3][1][i+1]=addBPoint(0.5,dy,rz+rz0,b0);
			
			shelves[0][2][i+1]=addBPoint(-0.5,1.0-dy,rz+rz0,b0);
			shelves[1][2][i+1]=addBPoint(-dx,1.0-dy,rz+rz0,b0);
			shelves[2][2][i+1]=addBPoint(dx,1.0-dy,rz+rz0,b0);
			shelves[3][2][i+1]=addBPoint(0.5,1.0-dy,rz+rz0,b0);
			
			shelves[0][3][i+1]=addBPoint(-0.5,1.0,rz+rz0,b0);
			shelves[1][3][i+1]=addBPoint(-dx,1.0,rz+rz0,b0);
			shelves[2][3][i+1]=addBPoint(dx,1.0,rz+rz0,b0);
			shelves[3][3][i+1]=addBPoint(0.5,1.0,rz+rz0,b0);	
		}
		
		for (int i = 0; i < shz; i++) {
			
			if(i%2==0){
			
				addLine(shelves[0][0][i],shelves[0][3][i],shelves[3][3][i],shelves[3][0][i],Renderer3D.CAR_BOTTOM);
				addLine(shelves[0][0][i],shelves[3][0][i+1],shelves[3][3][i+1],shelves[0][3][i+1],Renderer3D.CAR_TOP);
			
	
				addLine(shelves[0][0][i],shelves[0][0][i+1],shelves[0][3][i+1],shelves[0][3][i],Renderer3D.CAR_LEFT);				

				addLine(shelves[3][0][i],shelves[3][3][i],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_RIGHT);
				
				addLine(shelves[0][3][i],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][3][i],Renderer3D.CAR_FRONT);
				
				addLine(shelves[0][0][i],shelves[3][0][i],shelves[3][0][i+1],shelves[0][0][i+1],Renderer3D.CAR_BACK);
			
			}
			
			if(i%2==1 && i<shz-1){
				
				//front panel
				addLine(shelves[0][3][i],shelves[0][3][i+1],shelves[3][3][i+1],shelves[3][3][i],Renderer3D.CAR_FRONT);
				addLine(shelves[1][2][i],shelves[2][2][i],shelves[2][2][i+1],shelves[1][2][i+1],Renderer3D.CAR_BACK);
				
				//left panel
				addLine(shelves[1][0][i],shelves[1][2][i],shelves[1][2][i+1],shelves[1][0][i+1],Renderer3D.CAR_RIGHT);
				addLine(shelves[0][0][i],shelves[0][0][i+1],shelves[0][3][i+1],shelves[0][3][i],Renderer3D.CAR_LEFT);
				
				//back panel left
				addLine(shelves[0][0][i],shelves[1][0][i],shelves[1][0][i+1],shelves[0][0][i+1],Renderer3D.CAR_BACK);
				
				//right panel
				addLine(shelves[3][0][i],shelves[3][3][i],shelves[3][3][i+1],shelves[3][0][i+1],Renderer3D.CAR_RIGHT);
				addLine(shelves[2][0][i],shelves[2][0][i+1],shelves[2][2][i+1],shelves[2][2][i],Renderer3D.CAR_LEFT);
				
				//back panel right
				addLine(shelves[2][0][i],shelves[3][0][i],shelves[3][0][i+1],shelves[2][0][i+1],Renderer3D.CAR_BACK);
				
			}

		}

		//////// texture points
			
	
			
		texture_points=buildTexturePoints();
		

	}
	
}
