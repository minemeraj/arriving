package com.editors.cars.data;

import java.util.ArrayList;
import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.Segments;
import com.main.Renderer3D;

public class Starship extends CustomData {
	
	double x_side=0;
	double y_side=0;
	double z_side=0;

	double front_width=0;
    double front_length=0;
    double front_height=0;
    
	double roof_width=0;
    double roof_length=0;
    double roof_height=0;

    
    public Starship(double x_side, double y_side, double z_side,
			double front_width, double front_length, double front_height,
			double roof_width, double roof_length, double roof_height) {
		super();
		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.front_width = front_width;
		this.front_length = front_length;
		this.front_height = front_height;
		this.roof_width = roof_width;
		this.roof_length = roof_length;
		this.roof_height = roof_height;
		
		initMesh();
	}




	private void initMesh() {
		
		points=new ArrayList();
		polyData=new ArrayList();
		
		
		n=0;
		
		double by=roof_length-y_side;
		
		int hnumx=5;
		int hnumy=5;
		int hnumz=2;
		
		Segments h0=new Segments(0,front_width,by+y_side+50,front_length,100,front_height);
		
		BPoint[][][] head=new BPoint[hnumx][hnumy][hnumz];
		
		head[0][0][0]=addBPoint(-0.5,0.0,0,h0);
		head[1][0][0]=addBPoint(-0.25,0.0,0,h0);
		head[2][0][0]=addBPoint(0.0,0.0,0,h0);	
		head[3][0][0]=addBPoint(0.25,0.0,0,h0);	
		head[4][0][0]=addBPoint(0.5,0.0,0,h0);	
		head[0][0][1]=addBPoint(-0.5,0.0,1.0,h0);
		head[1][0][1]=addBPoint(-0.25,0.0,1.0,h0);
		head[2][0][1]=addBPoint(0.0,0.0,1.0,h0);
		head[3][0][1]=addBPoint(0.25,0.0,1.0,h0);
		head[4][0][1]=addBPoint(0.5,0.0,1.0,h0);
		
		head[0][1][0]=addBPoint(-0.5,0.25,0,h0);
		head[1][1][0]=addBPoint(-0.25,0.25,0,h0);
		head[2][1][0]=addBPoint(0.0,0.25,0,h0);	
		head[3][1][0]=addBPoint(0.25,0.25,0,h0);	
		head[4][1][0]=addBPoint(0.5,0.25,0,h0);	
		head[0][1][1]=addBPoint(-0.5,0.25,1.0,h0);
		head[1][1][1]=addBPoint(-0.25,0.25,1.0,h0);
		head[2][1][1]=addBPoint(0.0,0.25,1.0,h0);
		head[3][1][1]=addBPoint(0.25,0.25,1.0,h0);
		head[4][1][1]=addBPoint(0.5,0.25,1.0,h0);
		
		
		head[0][2][0]=addBPoint(-0.5,0.5,0,h0);
		head[1][2][0]=addBPoint(-0.25,0.5,0,h0);
		head[2][2][0]=addBPoint(0.0,0.5,0,h0);	
		head[3][2][0]=addBPoint(0.25,0.5,0,h0);	
		head[4][2][0]=addBPoint(0.5,0.5,0,h0);	
		head[0][2][1]=addBPoint(-0.5,0.5,1.0,h0);
		head[1][2][1]=addBPoint(-0.25,0.5,1.0,h0);
		head[2][2][1]=addBPoint(0.0,0.5,1.0,h0);
		head[3][2][1]=addBPoint(0.25,0.5,1.0,h0);
		head[4][2][1]=addBPoint(0.5,0.5,1.0,h0);
		
		head[0][3][0]=addBPoint(-0.5,0.75,0,h0);
		head[1][3][0]=addBPoint(-0.25,0.75,0,h0);
		head[2][3][0]=addBPoint(0.0,0.75,0,h0);	
		head[3][3][0]=addBPoint(0.25,0.75,0,h0);	
		head[4][3][0]=addBPoint(0.5,0.75,0,h0);	
		head[0][3][1]=addBPoint(-0.5,0.75,1.0,h0);
		head[1][3][1]=addBPoint(-0.25,0.75,1.0,h0);
		head[2][3][1]=addBPoint(0.0,0.75,1.0,h0);
		head[3][3][1]=addBPoint(0.25,0.75,1.0,h0);
		head[4][3][1]=addBPoint(0.5,0.75,1.0,h0);
		
		head[0][4][0]=addBPoint(-0.5,1.0,0,h0);
		head[1][4][0]=addBPoint(-0.25,1.0,0,h0);
		head[2][4][0]=addBPoint(0.0,1.0,0,h0);	
		head[3][4][0]=addBPoint(0.25,1.0,0,h0);	
		head[4][4][0]=addBPoint(0.5,1.0,0,h0);	
		head[0][4][1]=addBPoint(-0.5,1.0,1.0,h0);
		head[1][4][1]=addBPoint(-0.25,1.0,1.0,h0);
		head[2][4][1]=addBPoint(0.0,1.0,1.0,h0);
		head[3][4][1]=addBPoint(0.25,1.0,1.0,h0);
		head[4][4][1]=addBPoint(0.5,1.0,1.0,h0);
		
		for(int i=0;i<hnumx-1;i++){

			for (int j = 0; j < hnumy-1; j++) {


				for(int k=0;k<hnumz-1;k++){		

					if(i==0){
						addLine(head[i][j][k],head[i][j][k+1],head[i][j+1][k+1],head[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(head[i][j][k],head[i][j+1][k],head[i+1][j+1][k],head[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==hnumx-1){
						addLine(head[i+1][j][k],head[i+1][j+1][k],head[i+1][j+1][k+1],head[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(head[i][j][k],head[i+1][j][k],head[i+1][j][k+1],head[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==hnumy-1)
						addLine(head[i][j+1][k],head[i][j+1][k+1],head[i+1][j+1][k+1],head[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==hnumz-1)
						addLine(head[i][j][k+1],head[i+1][j][k+1],head[i+1][j+1][k+1],head[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
				
		int bnumx=2;
		int bnumy=5;
		int bnumz=2;
		
		
		
		Segments b0=new Segments(0,x_side,by,y_side,0,z_side);
		
		BPoint[][][] body=new BPoint[bnumx][bnumy][bnumz];
		
		body[0][0][0]=addBPoint(-0.5,0.0,0,b0);
		body[1][0][0]=addBPoint(0.5,0.0,0,b0);	
		body[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);
		body[1][0][1]=addBPoint(0.5,0.0,1.0,b0);
		
		body[0][1][0]=addBPoint(-0.5,0.25,0,b0);		
		body[1][1][0]=addBPoint(0.5,0.25,0,b0);	
		body[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);		
		body[1][1][1]=addBPoint(0.5,0.25,1.0,b0);
		
		
		body[0][2][0]=addBPoint(-0.5,0.5,0,b0);		
		body[1][2][0]=addBPoint(0.5,0.5,0,b0);	
		body[0][2][1]=addBPoint(-0.5,0.5,1.0,b0);	
		body[1][2][1]=addBPoint(0.5,0.5,1.0,b0);
		
		body[0][3][0]=addBPoint(-0.5,0.75,0,b0);		
		body[1][3][0]=addBPoint(0.5,0.75,0,b0);	
		body[0][3][1]=addBPoint(-0.5,0.75,1.0,b0);		
		body[1][3][1]=addBPoint(0.5,0.75,1.0,b0);
		
		body[0][4][0]=addBPoint(-0.5,1.0,0,b0);		
		body[1][4][0]=addBPoint(0.5,1.0,0,b0);	
		body[0][4][1]=addBPoint(-0.5,1.0,1.0,b0);
		body[1][4][1]=addBPoint(0.5,1.0,1.0,b0);
		
		for(int i=0;i<bnumx-1;i++){

			for (int j = 0; j < bnumy-1; j++) {


				for(int k=0;k<bnumz-1;k++){		

					if(i==0){
						addLine(body[i][j][k],body[i][j][k+1],body[i][j+1][k+1],body[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(body[i][j][k],body[i][j+1][k],body[i+1][j+1][k],body[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==bnumx-1){
						addLine(body[i+1][j][k],body[i+1][j+1][k],body[i+1][j+1][k+1],body[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(body[i][j][k],body[i+1][j][k],body[i+1][j][k+1],body[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==bnumy-1)
						addLine(body[i][j+1][k],body[i][j+1][k+1],body[i+1][j+1][k+1],body[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==bnumz-1)
						addLine(body[i][j][k+1],body[i+1][j][k+1],body[i+1][j+1][k+1],body[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		int nnumx=2;
		int nnumy=2;
		int nnumz=2;
		
		Segments n0=new Segments(0,50,0,150,0,50);
		
		BPoint[][][] neck=new BPoint[nnumx][nnumy][nnumz];
		
		neck[0][0][0]=body[0][4][0];
		neck[1][0][0]=body[1][4][0];
		neck[0][0][1]=body[0][4][1];
		neck[1][0][1]=body[1][4][1];
		
		neck[0][1][0]=head[1][0][0];
		neck[1][1][0]=head[3][0][0];
		neck[0][1][1]=head[1][0][1];
		neck[1][1][1]=head[3][0][1];
		
	
		
		for(int i=0;i<nnumx-1;i++){

			for (int j = 0; j < nnumy-1; j++) {


				for(int k=0;k<nnumz-1;k++){		

					if(i==0){
						addLine(neck[i][j][k],neck[i][j][k+1],neck[i][j+1][k+1],neck[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(neck[i][j][k],neck[i][j+1][k],neck[i+1][j+1][k],neck[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==nnumx-1){
						addLine(neck[i+1][j][k],neck[i+1][j+1][k],neck[i+1][j+1][k+1],neck[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(neck[i][j][k],neck[i+1][j][k],neck[i+1][j][k+1],neck[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==nnumy-1)
						addLine(neck[i][j+1][k],neck[i][j+1][k+1],neck[i+1][j+1][k+1],neck[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==nnumz-1)
						addLine(neck[i][j][k+1],neck[i+1][j][k+1],neck[i+1][j+1][k+1],neck[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		
		int wnumx=5;
		int wnumy=5;
		int wnumz=2;
		
		Segments lw=new Segments(-x_side-roof_width,roof_width,0,roof_length,100,roof_height);
		
		BPoint[][][] leftWing=new BPoint[wnumx][wnumy][wnumz];
		
		leftWing[0][0][0]=addBPoint(-0.5,0.0,0,lw);
		leftWing[1][0][0]=addBPoint(-0.25,0.0,0,lw);
		leftWing[2][0][0]=addBPoint(0.0,0.0,0,lw);	
		leftWing[3][0][0]=addBPoint(0.25,0.0,0,lw);	
		leftWing[4][0][0]=addBPoint(0.5,0.0,0,lw);	
		leftWing[0][0][1]=addBPoint(-0.5,0.0,1.0,lw);
		leftWing[1][0][1]=addBPoint(-0.25,0.0,1.0,lw);
		leftWing[2][0][1]=addBPoint(0.0,0.0,1.0,lw);
		leftWing[3][0][1]=addBPoint(0.25,0.0,1.0,lw);
		leftWing[4][0][1]=addBPoint(0.5,0.0,1.0,lw);
		
		leftWing[0][1][0]=addBPoint(-0.5,0.25,0,lw);
		leftWing[1][1][0]=addBPoint(-0.25,0.25,0,lw);
		leftWing[2][1][0]=addBPoint(0.0,0.25,0,lw);	
		leftWing[3][1][0]=addBPoint(0.25,0.25,0,lw);	
		leftWing[4][1][0]=addBPoint(0.5,0.25,0,lw);	
		leftWing[0][1][1]=addBPoint(-0.5,0.25,1.0,lw);
		leftWing[1][1][1]=addBPoint(-0.25,0.25,1.0,lw);
		leftWing[2][1][1]=addBPoint(0.0,0.25,1.0,lw);
		leftWing[3][1][1]=addBPoint(0.25,0.25,1.0,lw);
		leftWing[4][1][1]=addBPoint(0.5,0.25,1.0,lw);
		
		
		leftWing[0][2][0]=addBPoint(-0.5,0.5,0,lw);
		leftWing[1][2][0]=addBPoint(-0.25,0.5,0,lw);
		leftWing[2][2][0]=addBPoint(0.0,0.5,0,lw);	
		leftWing[3][2][0]=addBPoint(0.25,0.5,0,lw);	
		leftWing[4][2][0]=addBPoint(0.5,0.5,0,lw);	
		leftWing[0][2][1]=addBPoint(-0.5,0.5,1.0,lw);
		leftWing[1][2][1]=addBPoint(-0.25,0.5,1.0,lw);
		leftWing[2][2][1]=addBPoint(0.0,0.5,1.0,lw);
		leftWing[3][2][1]=addBPoint(0.25,0.5,1.0,lw);
		leftWing[4][2][1]=addBPoint(0.5,0.5,1.0,lw);
		
		leftWing[0][3][0]=addBPoint(-0.5,0.75,0,lw);
		leftWing[1][3][0]=addBPoint(-0.25,0.75,0,lw);
		leftWing[2][3][0]=addBPoint(0.0,0.75,0,lw);	
		leftWing[3][3][0]=addBPoint(0.25,0.75,0,lw);	
		leftWing[4][3][0]=addBPoint(0.5,0.75,0,lw);	
		leftWing[0][3][1]=addBPoint(-0.5,0.75,1.0,lw);
		leftWing[1][3][1]=addBPoint(-0.25,0.75,1.0,lw);
		leftWing[2][3][1]=addBPoint(0.0,0.75,1.0,lw);
		leftWing[3][3][1]=addBPoint(0.25,0.75,1.0,lw);
		leftWing[4][3][1]=addBPoint(0.5,0.75,1.0,lw);
		
		leftWing[0][4][0]=addBPoint(-0.5,1.0,0,lw);
		leftWing[1][4][0]=addBPoint(-0.25,1.0,0,lw);
		leftWing[2][4][0]=addBPoint(0.0,1.0,0,lw);	
		leftWing[3][4][0]=addBPoint(0.25,1.0,0,lw);	
		leftWing[4][4][0]=addBPoint(0.5,1.0,0,lw);	
		leftWing[0][4][1]=addBPoint(-0.5,1.0,1.0,lw);
		leftWing[1][4][1]=addBPoint(-0.25,1.0,1.0,lw);
		leftWing[2][4][1]=addBPoint(0.0,1.0,1.0,lw);
		leftWing[3][4][1]=addBPoint(0.25,1.0,1.0,lw);
		leftWing[4][4][1]=addBPoint(0.5,1.0,1.0,lw);
		
		for(int i=0;i<wnumx-1;i++){

			for (int j = 0; j < wnumy-1; j++) {


				for(int k=0;k<wnumz-1;k++){		

					if(i==0){
						addLine(leftWing[i][j][k],leftWing[i][j][k+1],leftWing[i][j+1][k+1],leftWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(leftWing[i][j][k],leftWing[i][j+1][k],leftWing[i+1][j+1][k],leftWing[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==wnumx-1){
						addLine(leftWing[i+1][j][k],leftWing[i+1][j+1][k],leftWing[i+1][j+1][k+1],leftWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(leftWing[i][j][k],leftWing[i+1][j][k],leftWing[i+1][j][k+1],leftWing[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==wnumy-1)
						addLine(leftWing[i][j+1][k],leftWing[i][j+1][k+1],leftWing[i+1][j+1][k+1],leftWing[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==wnumz-1)
						addLine(leftWing[i][j][k+1],leftWing[i+1][j][k+1],leftWing[i+1][j+1][k+1],leftWing[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		int unx=2;
		int uny=2;
		int unz=2;
		
		BPoint[][][] leftUpright=new BPoint[unx][uny][unz];
		
		leftUpright[0][0][0]=leftWing[4][2][0];		
		leftUpright[0][0][1]=leftWing[4][2][1];		
		leftUpright[0][1][0]=leftWing[4][3][0];		
		leftUpright[0][1][1]=leftWing[4][3][1];
		

		leftUpright[1][0][0]=body[0][2][0];
		leftUpright[1][0][1]=body[0][2][1];
		leftUpright[1][1][0]=body[0][3][0];
		leftUpright[1][1][1]=body[0][3][1];
		
		for(int i=0;i<unx-1;i++){

			for (int j = 0; j < uny-1; j++) {


				for(int k=0;k<unz-1;k++){		

					if(i==0){
						addLine(leftUpright[i][j][k],leftUpright[i][j][k+1],leftUpright[i][j+1][k+1],leftUpright[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(leftUpright[i][j][k],leftUpright[i][j+1][k],leftUpright[i+1][j+1][k],leftUpright[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==unx-1){
						addLine(leftUpright[i+1][j][k],leftUpright[i+1][j+1][k],leftUpright[i+1][j+1][k+1],leftUpright[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(leftUpright[i][j][k],leftUpright[i+1][j][k],leftUpright[i+1][j][k+1],leftUpright[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==uny-1)
						addLine(leftUpright[i][j+1][k],leftUpright[i][j+1][k+1],leftUpright[i+1][j+1][k+1],leftUpright[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==unz-1)
						addLine(leftUpright[i][j][k+1],leftUpright[i+1][j][k+1],leftUpright[i+1][j+1][k+1],leftUpright[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		
		Segments rw=new Segments(x_side+roof_width,roof_width,0,roof_length,100,roof_height);
		
		BPoint[][][] rightWing=new BPoint[wnumx][wnumy][wnumz];
		
		rightWing[0][0][0]=addBPoint(-0.5,0.0,0,rw);
		rightWing[1][0][0]=addBPoint(-0.25,0.0,0,rw);
		rightWing[2][0][0]=addBPoint(0.0,0.0,0,rw);	
		rightWing[3][0][0]=addBPoint(0.25,0.0,0,rw);	
		rightWing[4][0][0]=addBPoint(0.5,0.0,0,rw);	
		rightWing[0][0][1]=addBPoint(-0.5,0.0,1.0,rw);
		rightWing[1][0][1]=addBPoint(-0.25,0.0,1.0,rw);
		rightWing[2][0][1]=addBPoint(0.0,0.0,1.0,rw);
		rightWing[3][0][1]=addBPoint(0.25,0.0,1.0,rw);
		rightWing[4][0][1]=addBPoint(0.5,0.0,1.0,rw);
		
		rightWing[0][1][0]=addBPoint(-0.5,0.25,0,rw);
		rightWing[1][1][0]=addBPoint(-0.25,0.25,0,rw);
		rightWing[2][1][0]=addBPoint(0.0,0.25,0,rw);	
		rightWing[3][1][0]=addBPoint(0.25,0.25,0,rw);	
		rightWing[4][1][0]=addBPoint(0.5,0.25,0,rw);	
		rightWing[0][1][1]=addBPoint(-0.5,0.25,1.0,rw);
		rightWing[1][1][1]=addBPoint(-0.25,0.25,1.0,rw);
		rightWing[2][1][1]=addBPoint(0.0,0.25,1.0,rw);
		rightWing[3][1][1]=addBPoint(0.25,0.25,1.0,rw);
		rightWing[4][1][1]=addBPoint(0.5,0.25,1.0,rw);
		
		
		rightWing[0][2][0]=addBPoint(-0.5,0.5,0,rw);
		rightWing[1][2][0]=addBPoint(-0.25,0.5,0,rw);
		rightWing[2][2][0]=addBPoint(0.0,0.5,0,rw);	
		rightWing[3][2][0]=addBPoint(0.25,0.5,0,rw);	
		rightWing[4][2][0]=addBPoint(0.5,0.5,0,rw);	
		rightWing[0][2][1]=addBPoint(-0.5,0.5,1.0,rw);
		rightWing[1][2][1]=addBPoint(-0.25,0.5,1.0,rw);
		rightWing[2][2][1]=addBPoint(0.0,0.5,1.0,rw);
		rightWing[3][2][1]=addBPoint(0.25,0.5,1.0,rw);
		rightWing[4][2][1]=addBPoint(0.5,0.5,1.0,rw);
		
		rightWing[0][3][0]=addBPoint(-0.5,0.75,0,rw);
		rightWing[1][3][0]=addBPoint(-0.25,0.75,0,rw);
		rightWing[2][3][0]=addBPoint(0.0,0.75,0,rw);	
		rightWing[3][3][0]=addBPoint(0.25,0.75,0,rw);	
		rightWing[4][3][0]=addBPoint(0.5,0.75,0,rw);	
		rightWing[0][3][1]=addBPoint(-0.5,0.75,1.0,rw);
		rightWing[1][3][1]=addBPoint(-0.25,0.75,1.0,rw);
		rightWing[2][3][1]=addBPoint(0.0,0.75,1.0,rw);
		rightWing[3][3][1]=addBPoint(0.25,0.75,1.0,rw);
		rightWing[4][3][1]=addBPoint(0.5,0.75,1.0,rw);
		
		rightWing[0][4][0]=addBPoint(-0.5,1.0,0,rw);
		rightWing[1][4][0]=addBPoint(-0.25,1.0,0,rw);
		rightWing[2][4][0]=addBPoint(0.0,1.0,0,rw);	
		rightWing[3][4][0]=addBPoint(0.25,1.0,0,rw);	
		rightWing[4][4][0]=addBPoint(0.5,1.0,0,rw);	
		rightWing[0][4][1]=addBPoint(-0.5,1.0,1.0,rw);
		rightWing[1][4][1]=addBPoint(-0.25,1.0,1.0,rw);
		rightWing[2][4][1]=addBPoint(0.0,1.0,1.0,rw);
		rightWing[3][4][1]=addBPoint(0.25,1.0,1.0,rw);
		rightWing[4][4][1]=addBPoint(0.5,1.0,1.0,rw);
		
		for(int i=0;i<wnumx-1;i++){

			for (int j = 0; j < wnumy-1; j++) {


				for(int k=0;k<wnumz-1;k++){		

					if(i==0){
						addLine(rightWing[i][j][k],rightWing[i][j][k+1],rightWing[i][j+1][k+1],rightWing[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(rightWing[i][j][k],rightWing[i][j+1][k],rightWing[i+1][j+1][k],rightWing[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==wnumx-1){
						addLine(rightWing[i+1][j][k],rightWing[i+1][j+1][k],rightWing[i+1][j+1][k+1],rightWing[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(rightWing[i][j][k],rightWing[i+1][j][k],rightWing[i+1][j][k+1],rightWing[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==wnumy-1)
						addLine(rightWing[i][j+1][k],rightWing[i][j+1][k+1],rightWing[i+1][j+1][k+1],rightWing[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==wnumz-1)
						addLine(rightWing[i][j][k+1],rightWing[i+1][j][k+1],rightWing[i+1][j+1][k+1],rightWing[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}
		
		BPoint[][][] rightUpright=new BPoint[unx][uny][unz];
		
		rightUpright[0][0][0]=body[1][2][0];
		rightUpright[0][0][1]=body[1][2][1];
		rightUpright[0][1][0]=body[1][3][0];
		rightUpright[0][1][1]=body[1][3][1];
		
		
		rightUpright[1][0][0]=rightWing[0][2][0];		
		rightUpright[1][0][1]=rightWing[0][2][1];		
		rightUpright[1][1][0]=rightWing[0][3][0];		
		rightUpright[1][1][1]=rightWing[0][3][1];
		
		for(int i=0;i<unx-1;i++){

			for (int j = 0; j < uny-1; j++) {


				for(int k=0;k<unz-1;k++){		

					if(i==0){
						addLine(rightUpright[i][j][k],rightUpright[i][j][k+1],rightUpright[i][j+1][k+1],rightUpright[i][j+1][k],Renderer3D.CAR_LEFT);
					}

					if(k==0)
						addLine(rightUpright[i][j][k],rightUpright[i][j+1][k],rightUpright[i+1][j+1][k],rightUpright[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					if(i+1==unx-1){
						addLine(rightUpright[i+1][j][k],rightUpright[i+1][j+1][k],rightUpright[i+1][j+1][k+1],rightUpright[i+1][j][k+1],Renderer3D.CAR_RIGHT);
					}

					if(j==0)
						addLine(rightUpright[i][j][k],rightUpright[i+1][j][k],rightUpright[i+1][j][k+1],rightUpright[i][j][k+1],Renderer3D.CAR_BACK);		

					if(j+1==uny-1)
						addLine(rightUpright[i][j+1][k],rightUpright[i][j+1][k+1],rightUpright[i+1][j+1][k+1],rightUpright[i+1][j+1][k],Renderer3D.CAR_FRONT);

					if(k+1==unz-1)
						addLine(rightUpright[i][j][k+1],rightUpright[i+1][j][k+1],rightUpright[i+1][j+1][k+1],rightUpright[i][j+1][k+1],Renderer3D.CAR_TOP);

				}

			}

		}

	}

}
