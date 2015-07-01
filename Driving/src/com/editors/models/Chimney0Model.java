package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.editors.DoubleTextField;
import com.main.Renderer3D;

public class Chimney0Model extends MeshModel{

	double dx=100;
	double dx1=200;
	double dz=20;


	int bx=10;
	int by=10;
	
	int N=10;
	
	public static String NAME="Chimney0";
	
	public Chimney0Model(double dx, double dx1, double dz) {
		super();
		this.dx = dx;
		this.dx1 = dx1;
		this.dz = dz;
	}


	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);

		for (int i = 0; i < faces.length; i++) {

			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];

			String line="f=["+fts[0]+"]";

			for (int j = 0; j < pts.length; j++) {

				if(j>0)
					line+=" ";
				line+=(pts[j]+"/"+tts[j]);
			}

			print(pw,line);

		}

	}



	public void initMesh() {

		points=new Vector();


		//lower base
	
		
		double dteta=Math.PI*2.0/N;
		
		//lower base
		for(int i=0;i<N;i++){
			
			double teta=dteta*i;
			double xx=dx*0.5+dx*0.5*Math.cos(teta);
			double yy=dx*0.5+dx*0.5*Math.sin(teta);
			addPoint(xx,yy,0);
		}
		
		//upper base
		for(int i=0;i<N;i++){
			
			double teta=dteta*i;
			double xx=dx1*0.5+dx1*0.5*Math.cos(teta);
			double yy=dx1*0.5+dx1*0.5*Math.sin(teta);
			addPoint(xx,yy,dz);
		}
		
		
		texturePoints=new Vector();
		
		double y=by+dx*0.5;
		double x=bx+dx*0.5;
		
		//lower base
		for(int i=0;i<N;i++){
			
			double teta=dteta*i;
			double xx=dx*0.5*Math.cos(teta);
			double yy=dx*0.5*Math.sin(teta);
			addTPoint(x+xx,y+yy,0);
		}
		
		
		y=by+dx;
		
		double dl=Math.PI*dx/N;
		
		for(int i=0;i<N;i++){
			
			
			double xx=dl*i;
			double yy=dx;
			addTPoint(x+xx,y+yy,dz);
		}
		
		y=by+dx+dz;
		
		for(int i=0;i<N;i++){
			
			
			double xx=dl*i;
			double yy=dx;
			addTPoint(x+xx,y+yy,dz);
		}
		
		y=by+dx+dz+dx1*0.5;
		
		for(int i=0;i<N;i++){
			
			double teta=dteta*i;
			double xx=dx*0.5*Math.cos(teta);
			double yy=dx*0.5*Math.sin(teta);
			addTPoint(x+xx,y+yy,dz);
		}

		
		

		IMG_WIDTH=(int) (2*bx+Math.PI*dx);
		IMG_HEIGHT=(int) (2*by+dx+dz+dx1);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		for(int i=0;i<N;i++){
			
			printTextureLine(bg,i,(i+1)%N);
		}
		
		//upper base
		bg.setColor(Color.BLUE);
		int start=3*N;
		for(int i=0;i<N;i++){
			
			printTextureLine(bg,start+i,start+(i+1)%N);
		}

	}

	int[][][] faces={

			//base

			{{Renderer3D.CAR_BACK},{0,1,5,4},{4,5,10,9}},
			{{Renderer3D.CAR_RIGHT},{1,2,6,5},{5,6,11,10}},
			{{Renderer3D.CAR_FRONT},{2,3,7,6},{6,7,12,11}},
			{{Renderer3D.CAR_LEFT},{3,0,4,7},{7,8,13,12}},
			{{Renderer3D.CAR_BOTTOM},{0,3,2,1},{0,1,2,3}},
			

			//gables
			{{Renderer3D.CAR_BACK},{4,5,10,9,8},{9,10,16,15,14}},
			{{Renderer3D.CAR_FRONT},{6,7,11,12,13},{11,12,19,18,17}},
						
			//roof pitches
			{{Renderer3D.CAR_TOP},{4,8,11,7},{28,26,27,29}},
			{{Renderer3D.CAR_TOP},{8,9,12,11},{26,24,25,27}},
			{{Renderer3D.CAR_TOP},{9,10,13,12},{24,22,23,25}},
			{{Renderer3D.CAR_TOP},{10,5,6,13},{22,20,21,23}},
			
	};
	

	
	

}
