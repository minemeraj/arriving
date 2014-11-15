package com.editors.forniture.data;

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
import com.main.Renderer3D;

public class Globe1 extends CustomData { 
	
	double radius=0;
	private int N_MERIDIANS;
	private int N_PARALLELS;
	
	Point3D[][] northernHemisphere=null; 
	Point3D[][] southernHemisphere=null;
	
	Point3D northPole=null;
	Point3D southPole=null;
		
	
	public static int texture_side_dx=10;
	public static int texture_side_dy=10;
	
	public static int texture_x0=10;
	public static int texture_y0=10;
	public static int IMG_WIDTH;
	public static int IMG_HEIGHT;
	
	public double len;
	public double zHeight;
		
	public static boolean isTextureDrawing=false;

	public Globe1(double radius,int N_MERIDIANS,int N_PARALLELS) {
		
		this.radius=radius;
		this.N_MERIDIANS=N_MERIDIANS;
		this.N_PARALLELS=N_PARALLELS;
		
		zHeight=2*radius;
		len=4*radius;
		
		northernHemisphere=new Point3D[N_MERIDIANS][N_PARALLELS/2];
		southernHemisphere=new Point3D[N_MERIDIANS][N_PARALLELS/2];
		
	
		double xc=radius;
		double yc=radius;

		northPole=new Point3D(xc,yc,0);

		double dr=radius/(N_PARALLELS/2);
		double dteta=2*pi/(N_MERIDIANS);

		for(int j=0;j<N_PARALLELS/2;j++){

			double rr=dr*j;

			for (int i = 0; i <N_MERIDIANS; i++) {

				double teta=i*dteta;
				
				double x=xc+rr*Math.cos(teta);
				double y=yc+rr*Math.sin(teta);

				northernHemisphere[i][j]=new Point3D(x,y,0);

			}
			
		}	
		
		xc=3*radius;
		yc=radius;
		
		for(int j=0;j<N_PARALLELS/2;j++){

			double rr=dr*j;

			for (int i = 0; i <N_MERIDIANS; i++) {

				double teta=i*dteta;
				
				double x=xc+rr*Math.cos(teta);
				double y=yc+rr*Math.sin(teta);

				southernHemisphere[i][j]=new Point3D(x,y,0);

			}
			
		}	
	
		southPole=new Point3D(xc,yc,0);
		

		
		initMesh();
	}
	
	public void initMesh( ) {


		Hashtable values=new Hashtable();

		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;

		double fi=(2*pi)/(N_MERIDIANS);
		double teta=pi/(N_PARALLELS+1);
		
		BPoint northPole=addBPoint(0,0,radius);

		BPoint[][] nPoints=new BPoint[N_MERIDIANS][N_PARALLELS/2];
		BPoint[][] sPoints=new BPoint[N_MERIDIANS][N_PARALLELS/2];

		for (int i = 0; i < N_MERIDIANS; i++) {

			for (int j = 0; j <N_PARALLELS/2; j++) {

				double x= radius*Math.cos(i*fi)*Math.sin(teta+j*teta);
				double y= radius*Math.sin(i*fi)*Math.sin(teta+j*teta);
				double z= radius*Math.cos(teta+j*teta);

				nPoints[i][j]=
						addBPoint(x,y,z);

				points.setElementAt(nPoints[i][j],nPoints[i][j].getIndex());

			}
		}
		
		for (int i = 0; i < N_MERIDIANS; i++) {

			for (int j = 0; j <N_PARALLELS/2; j++) {

				int jj=j+N_PARALLELS/2;
				
				double x= radius*Math.cos(i*fi)*Math.sin(teta+jj*teta);
				double y= radius*Math.sin(i*fi)*Math.sin(teta+jj*teta);
				double z= radius*Math.cos(teta+jj*teta);

				sPoints[i][j]=
						addBPoint(x,y,z);

				points.setElementAt(nPoints[i][j],nPoints[i][j].getIndex());

			}
		}
		
		BPoint southPole=addBPoint(0,0,-radius);
		
		texture_points=buildTexturePoints();
	

		int count=N_MERIDIANS;

		for (int i = 0; i <N_MERIDIANS; i++) { 
			
			LineData ld=new LineData();

			int texIndex=count+f(i,0,N_MERIDIANS,N_PARALLELS/2);
			//System.out.print(texIndex+"\t");
			ld.addIndex(nPoints[i][0].getIndex(),texIndex,0,0);

			texIndex=count+f(i+1,0,N_MERIDIANS,N_PARALLELS/2);
			//System.out.print(texIndex+"\t");
			ld.addIndex(nPoints[(i+1)%N_MERIDIANS][0].getIndex(),texIndex,0,0);

			
			ld.addIndex(northPole.getIndex(),i,0,0);

			ld.setData(""+Renderer3D.getFace(ld,points));



			polyData.add(ld);
		}

		for (int i = 0; i <N_MERIDIANS; i++) { 

			for(int j=0;j<N_PARALLELS/2-1;j++){ 

				LineData ld=new LineData();

				int texIndex=count+f(i,j,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(nPoints[i][j].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i,j+1,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(nPoints[i][j+1].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j+1,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(nPoints[(i+1)%N_MERIDIANS][j+1].getIndex(),texIndex,0,0);

				texIndex=count+f(i+1,j,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(nPoints[(i+1)%N_MERIDIANS][j].getIndex(),texIndex,0,0);


				ld.setData(""+Renderer3D.getFace(ld,points));



				polyData.add(ld);

			}



		}
		
		count=1+N_MERIDIANS*N_PARALLELS/2;

		for (int i = 0; i <N_MERIDIANS; i++) { 

			for(int j=0;j<N_PARALLELS/2-1;j++){ 

				LineData ld=new LineData();

				int texIndex=count+f(i,j,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(sPoints[i][j].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i,j+1,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(sPoints[i][j+1].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j+1,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(sPoints[(i+1)%N_MERIDIANS][j+1].getIndex(),texIndex,0,0);

				texIndex=count+f(i+1,j,N_MERIDIANS,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(sPoints[(i+1)%N_MERIDIANS][j].getIndex(),texIndex,0,0);


				ld.setData(""+Renderer3D.getFace(ld,points));



				polyData.add(ld);

			}



		}
		
		int spIndex=2+N_PARALLELS*(N_MERIDIANS);
		
		for (int i = 0; i <N_MERIDIANS; i++) { 
			
			LineData ld=new LineData();			

			int texIndex=count+f(i,(N_PARALLELS/2-1),N_MERIDIANS,N_PARALLELS/2);
			//System.out.print(texIndex+"\t");
			ld.addIndex(sPoints[i][(N_PARALLELS/2-1)].getIndex(),texIndex,0,0);
			
			ld.addIndex(southPole.getIndex(),spIndex+i,0,0);

			texIndex=count+f(i+1,(N_PARALLELS/2-1),N_MERIDIANS,N_PARALLELS/2);
			//System.out.print(texIndex+"\t");
			ld.addIndex(sPoints[(i+1)%N_MERIDIANS][(N_PARALLELS/2-1)].getIndex(),texIndex,0,0);
		

			ld.setData(""+Renderer3D.getFace(ld,points));



			polyData.add(ld);
		}
	}	
	
	public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
	
		isTextureDrawing=true;
		
	
		
		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) (zHeight)+2*texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {


				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);
				
				bufGraphics.setColor(Color.RED);
				bufGraphics.setStroke(new BasicStroke(0.1f));
		
	
				for (int i = 0; i <N_MERIDIANS; i++) { 
						
						double x0=calX(northernHemisphere[i][0].x);
						double y0=calY(northernHemisphere[i][0].y);
						double x1=calX(northernHemisphere[(i+1)%N_MERIDIANS][0].x);
						double y1=calY(northernHemisphere[(i+1)%N_MERIDIANS][0].y);
						
						double xp=calX(northPole.x);
						double yp=calY(northPole.y);
						
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);
						bufGraphics.drawLine((int)x1,(int)y1,(int)xp,(int)yp);
						bufGraphics.drawLine((int)xp,(int)yp,(int)x0,(int)y0);
				}
		
				//lateral surface
				bufGraphics.setColor(new Color(0,0,0));

				for(int j=0;j<N_PARALLELS/2-1;j++){
					
					//texture is open and periodical:

					for (int i = 0; i <N_MERIDIANS; i++) { 

						double x0=calX(northernHemisphere[i][j].x);
						double y0=calY(northernHemisphere[i][j].y);						
						
						double x1=calX(northernHemisphere[(i+1)%N_MERIDIANS][j].x);
						double y1=calY(northernHemisphere[(i+1)%N_MERIDIANS][j].y);
						
						double x2=calX(northernHemisphere[(i+1)%N_MERIDIANS][j+1].x);
						double y2=calY(northernHemisphere[(i+1)%N_MERIDIANS][j+1].y);
						
						double x3=calX(northernHemisphere[i][j+1].x);
						double y3=calY(northernHemisphere[i][j+1].y);
						
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);
						bufGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
						bufGraphics.drawLine((int)x2,(int)y2,(int)x3,(int)y3);
						bufGraphics.drawLine((int)x3,(int)y3,(int)x0,(int)y0);
					}

				}	
				
				bufGraphics.setColor(new Color(100,100,100));

				for(int j=0;j<N_PARALLELS/2-1;j++){

					//texture is open and periodical:

					for (int i = 0; i <N_MERIDIANS; i++) { 

						double x0=calX(southernHemisphere[i][j].x);
						double y0=calY(southernHemisphere[i][j].y);						
						
						double x1=calX(southernHemisphere[(i+1)%N_MERIDIANS][j].x);
						double y1=calY(southernHemisphere[(i+1)%N_MERIDIANS][j].y);
						
						double x2=calX(southernHemisphere[(i+1)%N_MERIDIANS][j+1].x);
						double y2=calY(southernHemisphere[(i+1)%N_MERIDIANS][j+1].y);
						
						double x3=calX(southernHemisphere[i][j+1].x);
						double y3=calY(southernHemisphere[i][j+1].y);
						
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);
						bufGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
						bufGraphics.drawLine((int)x2,(int)y2,(int)x3,(int)y3);
						bufGraphics.drawLine((int)x3,(int)y3,(int)x0,(int)y0);
					}

				}	
				
				bufGraphics.setColor(Color.BLUE);
				bufGraphics.setStroke(new BasicStroke(0.1f));
				
				for (int i = 0; i <N_MERIDIANS; i++) { 
					
					double x0=calX(southernHemisphere[i][N_PARALLELS/2-1].x);
					double x1=calX(southernHemisphere[(i+1)%N_MERIDIANS][N_PARALLELS/2-1].x);
					double y0=calY(southernHemisphere[i][N_PARALLELS/2-1].y);
					double y1=calY(southernHemisphere[(i+1)%N_MERIDIANS][N_PARALLELS/2-1].y);
					
					double xp=calX(southPole.x);
					double yp=calY(southPole.y);
					
					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);
					bufGraphics.drawLine((int)x1,(int)y1,(int)xp,(int)yp);
					bufGraphics.drawLine((int)xp,(int)yp,(int)x0,(int)y0);
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


	int size=2+	(N_MERIDIANS)*(N_PARALLELS);

	
	texture_points.setSize(size);
	
	texture_side_dy=(int) (zHeight/(N_PARALLELS-1));
	texture_side_dx=(int) (len/(N_MERIDIANS));
	
	IMG_WIDTH=(int) len+2*texture_x0;
	IMG_HEIGHT=(int) (zHeight+radius*2)+2*texture_y0;

	int count=0;

	
	
	double xp=calX(northPole.x);
	double yp=calY(northPole.y);
	
	Point3D pn=new Point3D(xp,yp,0);	
	texture_points.setElementAt(pn,count++);

	//lateral surface


	for(int j=0;j<N_PARALLELS/2;j++){

		//texture is open and periodical:

		for (int i = 0; i <N_MERIDIANS; i++) {

			double x=calX(northernHemisphere[i][j].x);
			double y=calY(northernHemisphere[i][j].y);

			Point3D p=new Point3D(x,y,0);

			int texIndex=count+f(i,j,N_MERIDIANS,N_PARALLELS);
			//System.out.print(texIndex+"\t");
			texture_points.setElementAt(p,texIndex);
		}
		
	}	
	
	count=1+N_PARALLELS/2*(N_MERIDIANS);
	
	for(int j=0;j<N_PARALLELS/2;j++){

		//texture is open and periodical:

		for (int i = 0; i <N_MERIDIANS; i++) {

			double x=calX(southernHemisphere[i][j].x);
			double y=calY(southernHemisphere[i][j].y);

			Point3D p=new Point3D(x,y,0);

			int texIndex=count+f(i,j,N_MERIDIANS,N_PARALLELS);
			//System.out.print(texIndex+"\t");
			texture_points.setElementAt(p,texIndex);
		}
		
	}
	
	count=1+N_PARALLELS*(N_MERIDIANS);

		
	xp=calX(southPole.x);
	yp=calY(southPole.y);
		
	Point3D ps=new Point3D(xp,yp,0);	
	texture_points.setElementAt(ps,count++);

	
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

}
