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
	private int[][][] faces;
	
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
		
		x=bx;
		y=by+dx;
		
		//faces
		double dl=Math.PI*dx/N;
		
		for(int i=0;i<=N;i++){
			
			
			double xx=dl*i;
			double yy=0;
			addTPoint(x+xx,y+yy,dz);
		}
		
		x=bx;
		y=by+dx+dz;		
		
		for(int i=0;i<=N;i++){
			
			
			double xx=dl*i;
			double yy=0;
			addTPoint(x+xx,y+yy,dz);
		}
		
		x=bx+dx*0.5;
		y=by+dx+dz+dx1*0.5;
		
		for(int i=0;i<N;i++){
			
			double teta=dteta*i;
			double xx=dx1*0.5*Math.cos(teta);
			double yy=dx1*0.5*Math.sin(teta);
			addTPoint(x+xx,y+yy,dz);
		}

		
		

		IMG_WIDTH=(int) (2*bx+Math.PI*dx);
		IMG_HEIGHT=(int) (2*by+dx+dz+dx1);
		
		int NF=2+N;
		faces=new int[NF][3][N];
		
		//bottom
		faces[0][0][0]=Renderer3D.CAR_BOTTOM;
		for(int i=0;i<N;i++){
			faces[0][1][i]=i;
			faces[0][2][i]=i;
		}
		
		//faces
		int start=N;
		for(int l=0;l<N;l++){
			
			faces[1+l][0][0]=Renderer3D.CAR_BACK;
			
			
			faces[1+l][1][0]=l;
			faces[1+l][1][1]=(l+1)%N;
			faces[1+l][1][2]=(l+1)%N+N;
			faces[1+l][1][3]=l+N;
			
			faces[1+l][2][0]=l;
			faces[1+l][2][1]=(l+1)%N;
			faces[1+l][2][2]=(l+1)%N+N;
			faces[1+l][2][3]=l+N;
			
		}

		//top
		start=N+2*(N+1);
		faces[NF-1][0][0]=Renderer3D.CAR_TOP;
		for(int i=0;i<N;i++){
			faces[NF-1][1][i]=i+N;
			faces[NF-1][2][i]=i+start;
		}
		
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		for(int i=0;i<N;i++){
			
			printTextureLine(bg,i,(i+1)%N);
		}
		
		//faces
		int start=N;
		bg.setColor(Color.BLACK);
		for(int i=0;i<N;i++){
			
			printTextureLine(bg,start+i,start+i+1);
			printTextureLine(bg,start+i,start+i+(N+1));
			printTextureLine(bg,start+i+1,start+i+1+(N+1));
			printTextureLine(bg,start+i+(N+1),start+i+1+(N+1));
		}
		
		//upper base
		bg.setColor(Color.BLUE);
		start=N+2*(N+1);
		for(int i=0;i<N;i++){
			
			printTextureLine(bg,start+i,start+(i+1)%N);
		}

	}

	

	
	

}
