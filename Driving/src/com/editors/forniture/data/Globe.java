package com.editors.forniture.data;

import java.util.Hashtable;
import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.main.Renderer3D;

public class Globe extends CustomData {
	
	double radius=0;
	private int N_MERIDIANS;
	private int N_PARALLELS;

	public Globe(double radius,int N_MERIDIANS,int N_PARALLELS) {
		this.radius=radius;
		this.N_MERIDIANS=N_MERIDIANS;
		this.N_PARALLELS=N_PARALLELS;
		
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

		BPoint[][] aPoints=new BPoint[N_MERIDIANS][N_PARALLELS];

		for (int i = 0; i < N_MERIDIANS; i++) {

			for (int j = 0; j <N_PARALLELS; j++) {

				double x= radius*Math.cos(i*fi)*Math.sin(teta+j*teta);
				double y= radius*Math.sin(i*fi)*Math.sin(teta+j*teta);
				double z= radius*Math.cos(teta+j*teta);

				aPoints[i][j]=
						addBPoint(x,y,z);

				points.setElementAt(aPoints[i][j],aPoints[i][j].getIndex());

			}
		}

		BPoint southPole=addBPoint(0,0,-radius);

		int count=1;

		for (int i = 0; i <N_MERIDIANS; i++) { 
			
			LineData ld=new LineData();

			int texIndex=count+f(i,0,N_MERIDIANS+1,N_PARALLELS);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[i][0].getIndex(),texIndex,0,0);

			texIndex=count+f(i+1,0,N_MERIDIANS+1,N_PARALLELS);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[(i+1)%N_MERIDIANS][0].getIndex(),texIndex,0,0);

			
			ld.addIndex(northPole.getIndex(),0,0,0);

			ld.setData(""+Renderer3D.getFace(ld,points));



			polyData.add(ld);
		}

		for (int i = 0; i <N_MERIDIANS; i++) { 

			for(int j=0;j<N_PARALLELS-1;j++){ 

				LineData ld=new LineData();

				int texIndex=count+f(i,j,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[i][j].getIndex(),texIndex,0,0);

				texIndex=count+f(i+1,j,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[(i+1)%N_MERIDIANS][j].getIndex(),texIndex,0,0);

				texIndex=count+f(i+1,j+1,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[(i+1)%N_MERIDIANS][j+1].getIndex(),texIndex,0,0);

				texIndex=count+f(i,j+1,N_MERIDIANS+1,N_PARALLELS);
				//System.out.print(texIndex+"\t");
				ld.addIndex(aPoints[i][j+1].getIndex(),texIndex,0,0);

				ld.setData(""+Renderer3D.getFace(ld,points));



				polyData.add(ld);

			}



		}
		
		
		for (int i = 0; i <N_MERIDIANS; i++) { 
			
			LineData ld=new LineData();
			
			ld.addIndex(southPole.getIndex(),N_MERIDIANS*N_PARALLELS+1,0,0);

			int texIndex=count+f(i,(N_PARALLELS-1),N_MERIDIANS+1,N_PARALLELS);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[i][(N_PARALLELS-1)].getIndex(),texIndex,0,0);

			texIndex=count+f(i+1,(N_PARALLELS-1),N_MERIDIANS+1,N_PARALLELS);
			//System.out.print(texIndex+"\t");
			ld.addIndex(aPoints[(i+1)%N_MERIDIANS][(N_PARALLELS-1)].getIndex(),texIndex,0,0);
		

			ld.setData(""+Renderer3D.getFace(ld,points));



			polyData.add(ld);
		}
	}	

}
