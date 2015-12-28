package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Point3D;
import com.main.Renderer3D;

public abstract class MeshModel {


	Vector texturePoints=null;
	Vector points=null;

	Color backgroundColor=Color.green;

	int IMG_WIDTH=100;
	int IMG_HEIGHT=100;



	String title="Mesh model";

	public static int FACE_TYPE_ORIENTATION=0;
	public static int FACE_TYPE_BODY_INDEXES=1;
	public static int FACE_TYPE_TEXTURE_INDEXES=2;

	public MeshModel(){		



	}



	public void printMeshData(PrintWriter pw) {

		for(int i=0;i<points.size();i++){


			Point3D p=(Point3D) points.elementAt(i);
			print(pw,"v="+p.x+" "+p.y+" "+p.z);

		}


		for (int i = 0; i < texturePoints.size(); i++) {
			Point3D p = (Point3D) texturePoints.elementAt(i);
			print(pw,"vt="+p.x+" "+p.y);
		}

	}
	/***
	 * Format:
	 * 
	 * faces[nf][type][values]
	 * 
	 * nf=face number
	 * type= o =orientation, 1=body polygon indexes 2=texture polygon indexes
	 * values=orientation number or indexes
	 * 
	 * @param pw
	 * @param faces
	 */
	public void printFaces(PrintWriter pw,int[][][] faces) {

		if(faces==null)
			return;

		for (int i = 0; i < faces.length; i++) {

			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];

			String line="f=["+fts[0]+"]";

			int len=pts.length; 

			for (int j = 0; j < len; j++) {

				if(j>0)
					line+=" ";
				line+=(pts[j]+"/"+tts[j]);
			}

			print(pw,line);

		}

	}

	public abstract void initMesh();

	public abstract void printTexture(Graphics2D bufGraphics);

	public void print(PrintWriter pw, String string) {

		pw.println(string);

	}

	public void printTextureLine(Graphics2D graphics, int indx0,int indx1){


		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);


		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	

	}

	public void printTextureLine(Graphics2D graphics, int indx0,int indx1,int indx2){


		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);
		Point3D p2=(Point3D) texturePoints.elementAt(indx2);

		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	
		graphics.drawLine(cX(p1.x),cY(p1.y),cX(p2.x),cY(p2.y));	

	}

	public void printTextureLine(Graphics2D graphics, int indx0,int indx1,int indx2,int indx3){


		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);
		Point3D p2=(Point3D) texturePoints.elementAt(indx2);
		Point3D p3=(Point3D) texturePoints.elementAt(indx3);

		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	
		graphics.drawLine(cX(p1.x),cY(p1.y),cX(p2.x),cY(p2.y));	
		graphics.drawLine(cX(p2.x),cY(p2.y),cX(p3.x),cY(p3.y));	
	}

	public void printTexturePolygon(Graphics2D graphics, int indx0,int indx1,int indx2){


		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);
		Point3D p2=(Point3D) texturePoints.elementAt(indx2);

		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	
		graphics.drawLine(cX(p1.x),cY(p1.y),cX(p2.x),cY(p2.y));	
		graphics.drawLine(cX(p2.x),cY(p2.y),cX(p0.x),cY(p0.y));	
	}

	public void printTexturePolygon(Graphics2D graphics, int indx0,int indx1,int indx2,int indx3){


		Point3D p0=(Point3D) texturePoints.elementAt(indx0);
		Point3D p1=(Point3D) texturePoints.elementAt(indx1);
		Point3D p2=(Point3D) texturePoints.elementAt(indx2);
		Point3D p3=(Point3D) texturePoints.elementAt(indx3);

		graphics.drawLine(cX(p0.x),cY(p0.y),cX(p1.x),cY(p1.y));	
		graphics.drawLine(cX(p1.x),cY(p1.y),cX(p2.x),cY(p2.y));	
		graphics.drawLine(cX(p2.x),cY(p2.y),cX(p3.x),cY(p3.y));	
		graphics.drawLine(cX(p3.x),cY(p3.y),cX(p0.x),cY(p0.y));	
	}

	private int cX(double x) {
		return (int) x;
	}

	private int cY(double y) {

		return (int) (IMG_HEIGHT-y);
	}


	public void printTexture(File file){


		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);

		try {


			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(backgroundColor);
			bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);

			printTexture(bufGraphics);   


			ImageIO.write(buf,"gif",file);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	public void printTextureFaces(Graphics2D bg, int[][][] faces) {

		for (int i = 0; i < faces.length; i++) {

			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];

			if(tts.length==4){

				int idx0=tts[0];
				int idx1=tts[1];
				int idx2=tts[2];
				int idx3=tts[3];

				printTexturePolygon(bg,idx0,idx1,idx2,idx3);

			}
		}

	}

	public void addPoint(double x, double y, double z) {

		points.add(new Point3D(x,y,z));

	}

	public void addTPoint(double x, double y, double z) {

		texturePoints.add(new Point3D(x,y,z));

	}

	public static int[][][] buildSingleBlockFaces(
			int nBasePoints,
			int numSections,
			int pOffset,
			int tOffset
			) {

		int NUM_FACES=nBasePoints*(numSections-1);
		int[][][] faces=new int[NUM_FACES][3][nBasePoints];
		
		

		int counter=0;
		for (int k = 0;k < numSections-1; k++) {
			
			int numLevelPoints=nBasePoints*(k+1);

			for (int p0 = 0; p0 < nBasePoints; p0++) {

				int p=p0+k*nBasePoints;
				int t=p0+k*(nBasePoints+1);

				faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

				int[] pts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;
				pts[0]=p+pOffset;
				int pl=(p+1)%numLevelPoints;
				if(pl==0)
					pl=k*nBasePoints;
				pts[1]=pl+pOffset;
				pts[2]=pl+nBasePoints+pOffset;
				pts[3]=p+nBasePoints+pOffset;

				int[] tts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;
				tts[0]=t+tOffset;
				tts[1]=t+1+tOffset;
				tts[2]=t+1+nBasePoints+1+tOffset;
				tts[3]=t+nBasePoints+1+tOffset;

				counter++;

			}

		}

		return faces;

	}
	
	public static int[][][] buildDoubleBlockFaces(
			int nBasePoints,
			int numSections,
			int pOffset,
			int tOffset
			) {

		int NUM_FACES=nBasePoints*(numSections-1);
		int[][][] faces=new int[NUM_FACES][3][nBasePoints];
		
		

		int counter=0;
		for (int k = 0;k < numSections-1; k++) {
			
			int numLevelPoints=nBasePoints*(k+1);
			int texLevelPoints=nBasePoints/2+1;
			int sigma=texLevelPoints*numSections;

			for (int p0 = 0; p0 < nBasePoints; p0++) {

				int p=p0+k*nBasePoints;
				int t=0;
				if(p0<nBasePoints/2){
					t=p0+k*texLevelPoints;
				}else{
					t=p0+k*texLevelPoints-nBasePoints/2+sigma;
				}
				

				faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

				int[] pts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;
				pts[0]=p+pOffset;
				int pl=(p+1)%numLevelPoints;
				if(pl==0)
					pl=k*nBasePoints;
				pts[1]=pl+pOffset;
				pts[2]=pl+nBasePoints+pOffset;
				pts[3]=p+nBasePoints+pOffset;

				int[] tts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;
				tts[0]=t+tOffset;
				tts[1]=t+1+tOffset;
				tts[2]=t+1+texLevelPoints+tOffset;
				tts[3]=t+texLevelPoints+tOffset;

				counter++;

			}

		}

		return faces;

	}
	

	public static int[][][] buildSinglePlaneFaces(int nBasePoints, int numSections, int pOffset, int tOffset) {

		int NUM_FACES=(nBasePoints-1)*(numSections-1);
		int[][][] faces=new int[NUM_FACES][3][nBasePoints];
		
		

		int counter=0;
		for (int k = 0;k < numSections-1; k++) {
			
			//int numLevelPoints=nBasePoints*(k+1);

			for (int p0 = 0; p0 < nBasePoints-1; p0++) {

				int p=p0+k*nBasePoints;
				int t=p0+k*nBasePoints;

				faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION]=Renderer3D.CAR_BACK;

				int[] pts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES]=pts;
				pts[0]=p+pOffset;
				int pl=(p+1);
				pts[1]=pl+pOffset;
				pts[2]=pl+nBasePoints+pOffset;
				pts[3]=p+nBasePoints+pOffset;

				int[] tts = new int[4];
				faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES]=tts;
				tts[0]=t+tOffset;
				tts[1]=t+1+tOffset;
				tts[2]=t+1+nBasePoints+tOffset;
				tts[3]=t+nBasePoints+tOffset;

				counter++;

			}

		}

		return faces;

	}


}
