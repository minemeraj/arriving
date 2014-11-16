package com.editors.cars.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Segments;
import com.main.Renderer3D;

public class Ship extends CustomData {
	
	public Ship() {
		

		
		initMesh();
	}

	private void initMesh() {
		
		points=new Vector();
		points.setSize(200);
		polyData=new Vector();

		n=0;
		
		int ny=11;
		int nz=4;
		
		BPoint[][][] rightBroadSide=new BPoint[1][ny][nz];	 
		
		Segments s0=new Segments(0,100,0,400,0,100);
		
		rightBroadSide[0][0][0]=null;
		rightBroadSide[0][0][1]=addBPoint(0.0,0,0.25,s0);
		rightBroadSide[0][0][2]=addBPoint(0.0,0,0.5,s0);
		rightBroadSide[0][0][3]=addBPoint(0.0,0,0.75,s0);
		
		rightBroadSide[0][1][0]=addBPoint(0,0.1,0,s0);
		rightBroadSide[0][1][1]=addBPoint(0.525,0.1,0.25,s0);
		rightBroadSide[0][1][2]=addBPoint(0.65,0.1,0.5,s0);
		rightBroadSide[0][1][3]=addBPoint(0.75,0.1,0.75,s0);
		
		rightBroadSide[0][2][0]=addBPoint(0,0.2,0,s0);
		rightBroadSide[0][2][1]=addBPoint(0.525,0.2,0.25,s0);
		rightBroadSide[0][2][2]=addBPoint(0.65,0.2,0.5,s0);
		rightBroadSide[0][2][3]=addBPoint(0.75,0.2,0.75,s0);
		
		rightBroadSide[0][3][0]=addBPoint(0,0.3,0,s0);
		rightBroadSide[0][3][1]=addBPoint(0.525,0.3,0.25,s0);
		rightBroadSide[0][3][2]=addBPoint(0.65,0.3,0.5,s0);
		rightBroadSide[0][3][3]=addBPoint(0.75,0.3,0.75,s0);
		
		rightBroadSide[0][4][0]=addBPoint(0,0.4,0,s0);
		rightBroadSide[0][4][1]=addBPoint(0.525,0.4,0.25,s0);
		rightBroadSide[0][4][2]=addBPoint(0.65,0.4,0.5,s0);
		rightBroadSide[0][4][3]=addBPoint(0.75,0.4,0.75,s0);
		
		rightBroadSide[0][5][0]=addBPoint(0,0.5,0,s0);
		rightBroadSide[0][5][1]=addBPoint(0.525,0.5,0.25,s0);
		rightBroadSide[0][5][2]=addBPoint(0.65,0.5,0.5,s0);
		rightBroadSide[0][5][3]=addBPoint(0.75,0.5,0.75,s0);
		
		rightBroadSide[0][6][0]=addBPoint(0,0.6,0,s0);
		rightBroadSide[0][6][1]=addBPoint(0.525,0.6,0.25,s0);
		rightBroadSide[0][6][2]=addBPoint(0.65,0.6,0.5,s0);
		rightBroadSide[0][6][3]=addBPoint(0.75,0.6,0.75,s0);
		
		rightBroadSide[0][7][0]=addBPoint(0,0.7,0,s0);
		rightBroadSide[0][7][1]=addBPoint(0.525,0.7,0.25,s0);
		rightBroadSide[0][7][2]=addBPoint(0.65,0.7,0.5,s0);
		rightBroadSide[0][7][3]=addBPoint(0.75,0.7,0.75,s0);
		
		rightBroadSide[0][8][0]=addBPoint(0,0.8,0,s0);
		rightBroadSide[0][8][1]=addBPoint(0.525,0.8,0.25,s0);
		rightBroadSide[0][8][2]=addBPoint(0.65,0.8,0.5,s0);
		rightBroadSide[0][8][3]=addBPoint(0.75,0.8,0.75,s0);
		
		rightBroadSide[0][9][0]=addBPoint(0,0.9,0,s0);
		rightBroadSide[0][9][1]=addBPoint(0.525,0.9,0.25,s0);
		rightBroadSide[0][9][2]=addBPoint(0.65,0.9,0.5,s0);
		rightBroadSide[0][9][3]=addBPoint(0.75,0.9,0.75,s0);
		
		rightBroadSide[0][10][0]=null;
		rightBroadSide[0][10][1]=addBPoint(0.0,1.0,0.25,s0);
		rightBroadSide[0][10][2]=addBPoint(0.0,1.0,0.5,s0);
		rightBroadSide[0][10][3]=addBPoint(0.0,1.0,0.75,s0);
		
		for (int j = 0; j < ny-1; j++) { 
			
			for (int k = 0; k < nz-1; k++) {
				if((j==ny-2) && k==0)
					addLine(rightBroadSide[0][j][k],rightBroadSide[0][j+1][k+1],rightBroadSide[0][j][k+1],null,Renderer3D.CAR_RIGHT);
				else if((j==0) && k==0)
					addLine(rightBroadSide[0][j+1][k],rightBroadSide[0][j+1][k+1],rightBroadSide[0][j][k+1],null,Renderer3D.CAR_RIGHT);
				else	
					addLine(rightBroadSide[0][j][k],rightBroadSide[0][j+1][k],rightBroadSide[0][j+1][k+1],rightBroadSide[0][j][k+1],Renderer3D.CAR_RIGHT);
			}
			
		}
		
		BPoint[][][] leftBroadSide=new BPoint[1][ny][nz];	 
		
		leftBroadSide[0][0][0]=null;
		leftBroadSide[0][0][1]=addBPoint(0.0,0,0.25,s0);
		leftBroadSide[0][0][2]=addBPoint(0.0,0,0.5,s0);
		leftBroadSide[0][0][3]=addBPoint(0.0,0,0.75,s0);
		
		leftBroadSide[0][1][0]=addBPoint(0,0.1,0,s0);
		leftBroadSide[0][1][1]=addBPoint(-0.525,0.1,0.25,s0);
		leftBroadSide[0][1][2]=addBPoint(-0.65,0.1,0.5,s0);
		leftBroadSide[0][1][3]=addBPoint(-0.75,0.1,0.75,s0);
		
		leftBroadSide[0][2][0]=addBPoint(0,0.2,0,s0);
		leftBroadSide[0][2][1]=addBPoint(-0.525,0.2,0.25,s0);
		leftBroadSide[0][2][2]=addBPoint(-0.65,0.2,0.5,s0);
		leftBroadSide[0][2][3]=addBPoint(-0.75,0.2,0.75,s0);
		
		leftBroadSide[0][3][0]=addBPoint(0,0.3,0,s0);
		leftBroadSide[0][3][1]=addBPoint(-0.525,0.3,0.25,s0);
		leftBroadSide[0][3][2]=addBPoint(-0.65,0.3,0.5,s0);
		leftBroadSide[0][3][3]=addBPoint(-0.75,0.3,0.75,s0);
		
		leftBroadSide[0][4][0]=addBPoint(0,0.4,0,s0);
		leftBroadSide[0][4][1]=addBPoint(-0.525,0.4,0.25,s0);
		leftBroadSide[0][4][2]=addBPoint(-0.65,0.4,0.5,s0);
		leftBroadSide[0][4][3]=addBPoint(-0.75,0.4,0.75,s0);
		
		leftBroadSide[0][5][0]=addBPoint(0,0.5,0,s0);
		leftBroadSide[0][5][1]=addBPoint(-0.525,0.5,0.25,s0);
		leftBroadSide[0][5][2]=addBPoint(-0.65,0.5,0.5,s0);
		leftBroadSide[0][5][3]=addBPoint(-0.75,0.5,0.75,s0);
		
		leftBroadSide[0][6][0]=addBPoint(0,0.6,0,s0);
		leftBroadSide[0][6][1]=addBPoint(-0.525,0.6,0.25,s0);
		leftBroadSide[0][6][2]=addBPoint(-0.65,0.6,0.5,s0);
		leftBroadSide[0][6][3]=addBPoint(-0.75,0.6,0.75,s0);
		
		leftBroadSide[0][7][0]=addBPoint(0,0.7,0,s0);
		leftBroadSide[0][7][1]=addBPoint(-0.525,0.7,0.25,s0);
		leftBroadSide[0][7][2]=addBPoint(-0.65,0.7,0.5,s0);
		leftBroadSide[0][7][3]=addBPoint(-0.75,0.7,0.75,s0);
		
		leftBroadSide[0][8][0]=addBPoint(0,0.8,0,s0);
		leftBroadSide[0][8][1]=addBPoint(-0.525,0.8,0.25,s0);
		leftBroadSide[0][8][2]=addBPoint(-0.65,0.8,0.5,s0);
		leftBroadSide[0][8][3]=addBPoint(-0.75,0.8,0.75,s0);
		
		leftBroadSide[0][9][0]=addBPoint(0,0.9,0,s0);
		leftBroadSide[0][9][1]=addBPoint(-0.525,0.9,0.25,s0);
		leftBroadSide[0][9][2]=addBPoint(-0.65,0.9,0.5,s0);
		leftBroadSide[0][9][3]=addBPoint(-0.75,0.9,0.75,s0);
		
		leftBroadSide[0][10][0]=null;
		leftBroadSide[0][10][1]=addBPoint(-0.0,1.0,0.25,s0);
		leftBroadSide[0][10][2]=addBPoint(-0.0,1.0,0.5,s0);
		leftBroadSide[0][10][3]=addBPoint(-0.0,1.0,0.75,s0);
		
		for (int j = 0; j < ny-1; j++) { 
			
			for (int k = 0; k < nz-1; k++) {
				
				if((j==ny-2) && k==0)
					addLine(leftBroadSide[0][j][k],leftBroadSide[0][j][k+1],leftBroadSide[0][j+1][k+1],null,Renderer3D.CAR_LEFT);
				else if((j==0) && k==0)
					addLine(leftBroadSide[0][j][k+1],leftBroadSide[0][j+1][k+1],leftBroadSide[0][j+1][k],null,Renderer3D.CAR_LEFT);
				else
					addLine(leftBroadSide[0][j][k],leftBroadSide[0][j][k+1],leftBroadSide[0][j+1][k+1],leftBroadSide[0][j+1][k],Renderer3D.CAR_LEFT);
			}
			
		}
		
		BPoint[][][] mainDeck=new BPoint[2][ny][1];
		
		mainDeck[0][0][0]=leftBroadSide[0][0][nz-1];
		mainDeck[1][0][0]=rightBroadSide[0][0][nz-1];
		
		mainDeck[0][1][0]=leftBroadSide[0][1][nz-1];
		mainDeck[1][1][0]=rightBroadSide[0][1][nz-1];
		
		mainDeck[0][2][0]=leftBroadSide[0][2][nz-1];
		mainDeck[1][2][0]=rightBroadSide[0][2][nz-1];
		
		mainDeck[0][3][0]=leftBroadSide[0][3][nz-1];
		mainDeck[1][3][0]=rightBroadSide[0][3][nz-1];
		
		mainDeck[0][4][0]=leftBroadSide[0][4][nz-1];
		mainDeck[1][4][0]=rightBroadSide[0][4][nz-1];
		
		mainDeck[0][5][0]=leftBroadSide[0][5][nz-1];
		mainDeck[1][5][0]=rightBroadSide[0][5][nz-1];
		
		mainDeck[0][6][0]=leftBroadSide[0][6][nz-1];
		mainDeck[1][6][0]=rightBroadSide[0][6][nz-1];
		
		mainDeck[0][7][0]=leftBroadSide[0][7][nz-1];
		mainDeck[1][7][0]=rightBroadSide[0][7][nz-1];
		
		mainDeck[0][8][0]=leftBroadSide[0][8][nz-1];
		mainDeck[1][8][0]=rightBroadSide[0][8][nz-1];
		
		mainDeck[0][9][0]=leftBroadSide[0][9][nz-1];
		mainDeck[1][9][0]=rightBroadSide[0][9][nz-1];
		
		mainDeck[0][10][0]=leftBroadSide[0][10][nz-1];
		mainDeck[1][10][0]=rightBroadSide[0][10][nz-1];
		

		
		for (int j = 0; j < ny-1; j++) { 
			
			if(j==0)
				addLine(mainDeck[0][j][0],mainDeck[1][j+1][0],mainDeck[0][j+1][0],null,Renderer3D.CAR_TOP);
			else if (j==ny-2)
				addLine(mainDeck[0][j][0],mainDeck[1][j][0],mainDeck[1][j+1][0],null,Renderer3D.CAR_TOP);
			else			
				addLine(mainDeck[0][j][0],mainDeck[1][j][0],mainDeck[1][j+1][0],mainDeck[0][j+1][0],Renderer3D.CAR_TOP);
		}
	}

}
