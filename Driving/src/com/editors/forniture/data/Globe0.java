package com.editors.forniture.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.BPoint;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Globe0 extends Forniture { 
	
	double radius=0;

	
	private Point3D[][] lateralFaces=null; 
	private Point3D[]northPoles=null;
	private Point3D[]southPoles=null;
	
	private static int texture_side_dx=10;
	private static int texture_side_dy=10;
	
	private static int texture_x0=10;
	private static int texture_y0=10;
	private static int IMG_WIDTH;
	private static int IMG_HEIGHT;
	
	private double len;
	private double zHeight;
	
	private static boolean isTextureDrawing=false;

	public Globe0(double radius,int N_MERIDIANS,int N_PARALLELS) {
		
		this.radius=radius;
		this.n_meridians=N_MERIDIANS;
		this.n_parallels=N_PARALLELS;
		
		zHeight=2*radius;
		len=2*pi*radius;
		
		lateralFaces=new Point3D[N_MERIDIANS+1][N_PARALLELS];
		
		texture_side_dy=(int) (zHeight/(N_PARALLELS-1));
		texture_side_dx=(int) (len/(N_MERIDIANS));
		
		double dx=texture_side_dx;
		double dy=texture_side_dy;
		
		northPoles=new Point3D[N_MERIDIANS+1];
		
		for (int i = 0; i <=N_MERIDIANS; i++) {

			double x=dx*(i+0.5);
			double y=2*radius+zHeight;

			northPoles[i]=new Point3D(x,y,0);

		}

		for(int j=0;j<N_PARALLELS;j++){

			//texture is open and periodical:

			for (int i = 0; i <=N_MERIDIANS; i++) {

				double x=dx*i;
				double y=radius+zHeight-dy*j;

				lateralFaces[i][j]=new Point3D(x,y,0);

			}
			
		}	
		
	
		southPoles=new Point3D[N_MERIDIANS+1];
		
		for (int i = 0; i <=N_MERIDIANS; i++) {

			double x=dx*(i+0.5);
			double y=0;

			southPoles[i]=new Point3D(x,y,0);

		}
		
		initMesh();
	}
	
	public void initMesh( ) {


		Hashtable values=new Hashtable();

		points=new ArrayList();
		polyData=new ArrayList();

		n=0;

		double fi=(2*pi)/(n_meridians);
		double teta=pi/(n_parallels+1);
		
		BPoint northPole=addBPoint(0,0,radius);

		BPoint[][] aPoints=new BPoint[n_meridians][n_parallels];

		for (int i = 0; i < n_meridians; i++) {

			for (int j = 0; j <n_parallels; j++) {

				double x= radius*Math.cos(i*fi)*Math.sin(teta+j*teta);
				double y= radius*Math.sin(i*fi)*Math.sin(teta+j*teta);
				double z= radius*Math.cos(teta+j*teta);

				aPoints[i][j]=
						addBPoint(x,y,z);

				points.set(aPoints[i][j].getIndex(),aPoints[i][j]);

			}
		}
		
		BPoint southPole=addBPoint(0,0,-radius);
		
		texture_points=buildTexturePoints();
	

		int count=n_meridians;

		for (int i = 0; i <n_meridians; i++) { 
			
			LineData ld=new LineData();

			int texIndex=count+f(i,0,n_meridians+1,n_parallels);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[i][0].getIndex(),texIndex,0,0);

			texIndex=count+f(i+1,0,n_meridians+1,n_parallels);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[(i+1)%n_meridians][0].getIndex(),texIndex,0,0);

			
			ld.addIndex(northPole.getIndex(),i,0,0);

			ld.setData(""+Renderer3D.getFace(ld,points));



			polyData.add(ld);
		}

		for (int i = 0; i <n_meridians; i++) { 

			for(int j=0;j<n_parallels-1;j++){ 

				LineData ld=new LineData();

				int texIndex=count+f(i,j,n_meridians+1,n_parallels);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[i][j].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i,j+1,n_meridians+1,n_parallels);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[i][j+1].getIndex(),texIndex,0,0);
				
				texIndex=count+f(i+1,j+1,n_meridians+1,n_parallels);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[(i+1)%n_meridians][j+1].getIndex(),texIndex,0,0);

				texIndex=count+f(i+1,j,n_meridians+1,n_parallels);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[(i+1)%n_meridians][j].getIndex(),texIndex,0,0);


				ld.setData(""+Renderer3D.getFace(ld,points));



				polyData.add(ld);

			}



		}
		
		int spIndex=n_meridians+n_parallels*(n_meridians+1);
		
		for (int i = 0; i <n_meridians; i++) { 
			
			LineData ld=new LineData();			

			int texIndex=count+f(i,(n_parallels-1),n_meridians+1,n_parallels);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[i][(n_parallels-1)].getIndex(),texIndex,0,0);
			
			ld.addIndex(southPole.getIndex(),spIndex+i,0,0);

			texIndex=count+f(i+1,(n_parallels-1),n_meridians+1,n_parallels);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[(i+1)%n_meridians][(n_parallels-1)].getIndex(),texIndex,0,0);
		

			ld.setData(""+Renderer3D.getFace(ld,points));



			polyData.add(ld);
		}
	}	
	@Override
	public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
	
		isTextureDrawing=true;
		
	
		
		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) (zHeight+radius*2)+2*texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {


				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);
				
				bufGraphics.setColor(Color.RED);
				bufGraphics.setStroke(new BasicStroke(0.1f));
		
	
				for (int i = 0; i <n_meridians; i++) { 
						
						double x0=calX(lateralFaces[i][0].x);
						double x1=calX(lateralFaces[i+1][0].x);
						double y0=calY(lateralFaces[i][0].y);
						
						double xp=calX(northPoles[i].x);
						double yp=calY(northPoles[i].y);
						
						bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y0);
						bufGraphics.drawLine((int)x1,(int)y0,(int)xp,(int)yp);
						bufGraphics.drawLine((int)xp,(int)yp,(int)x0,(int)y0);
				}
		
				//lateral surface
				bufGraphics.setColor(new Color(0,0,0));

				for(int j=0;j<n_parallels-1;j++){

					//texture is open and periodical:

					for (int i = 0; i <n_meridians; i++) { 

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
				
				bufGraphics.setColor(Color.BLUE);
				bufGraphics.setStroke(new BasicStroke(0.1f));
				
				for (int i = 0; i <n_meridians; i++) { 
					
					double x0=calX(lateralFaces[i][n_parallels-1].x);
					double x1=calX(lateralFaces[i+1][n_parallels-1].x);
					double y0=calY(lateralFaces[i][n_parallels-1].y);
					
					double xp=calX(southPoles[i].x);
					double yp=calY(southPoles[i].y);
					
					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y0);
					bufGraphics.drawLine((int)x1,(int)y0,(int)xp,(int)yp);
					bufGraphics.drawLine((int)xp,(int)yp,(int)x0,(int)y0);
			    }
				
			
				ImageIO.write(buf,"gif",file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

public ArrayList buildTexturePoints() {
	
	isTextureDrawing=false;
	
	
	
	double teta=(2*pi)/(n_meridians);


	int size=2*n_meridians+	(n_meridians+1)*(n_parallels);

	ArrayList texture_points=new ArrayList(size);
	
	texture_side_dy=(int) (zHeight/(n_parallels-1));
	texture_side_dx=(int) (len/(n_meridians));
	
	IMG_WIDTH=(int) len+2*texture_x0;
	IMG_HEIGHT=(int) (zHeight+radius*2)+2*texture_y0;

	int count=0;

	
	for (int i = 0; i <n_meridians; i++) { 
		
		double xp=calX(northPoles[i].x);
		double yp=calY(northPoles[i].y);
		
		Point3D p=new Point3D(xp,yp,0);	
		texture_points.set(count++,p);
}

	//lateral surface


	for(int j=0;j<n_parallels;j++){

		//texture is open and periodical:

		for (int i = 0; i <=n_meridians; i++) {

			double x=calX(lateralFaces[i][j].x);
			double y=calY(lateralFaces[i][j].y);

			Point3D p=new Point3D(x,y,0);

			int texIndex=count+f(i,j,n_meridians+1,n_parallels);
			//System.out.print(texIndex+"\t");
			texture_points.set(texIndex,p);
		}
		
	}	
	
	count=n_meridians+n_parallels*(n_meridians+1);
	
	for (int i = 0; i <n_meridians; i++) { 
		
		double xp=calX(southPoles[i].x);
		double yp=calY(southPoles[i].y);
		
		Point3D p=new Point3D(xp,yp,0);	
		texture_points.set(count++,p);

    }
	
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

}
