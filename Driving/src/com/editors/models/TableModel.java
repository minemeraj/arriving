package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.editors.DoubleTextField;

public class TableModel extends MeshModel{

	double dx=100;
	double dy=200;
	double dz=20;
	double leg_length=50;
	double leg_side=10;

	int bx=10;
	int by=10;

	String title="Cube model";
	private DoubleTextField dz_text;
	private DoubleTextField dy_text;
	private DoubleTextField dx_text;



	public TableModel(){

		super(200,250);
		setTitle(title);

	}


	public static void main(String[] args) {

		TableModel cm=new TableModel();
        //cm.codeGeneration();  
	}








	public void buildCenter() {

		double dx=100;
		double dy=200;
		double dz=20;

		int r=10;

		JLabel lx=new JLabel("dx:");
		lx.setBounds(5,r,20,20);
		center.add(lx);
		dx_text=new DoubleTextField(8);
		dx_text.setBounds(30,r,120,20);
		dx_text.setText(dx);
		center.add(dx_text);

		r+=30;

		JLabel ly=new JLabel("dy:");
		ly.setBounds(5,r,20,20);
		center.add(ly);
		dy_text=new DoubleTextField(8);
		dy_text.setBounds(30,r,120,20);
		dy_text.setText(dy);
		center.add(dy_text);


		r+=30;

		JLabel lz=new JLabel("dz:");
		lz.setBounds(5,r,20,20);
		center.add(lz);
		dz_text=new DoubleTextField(8);
		dz_text.setBounds(30,r,120,20);
		dz_text.setText(dz);
		center.add(dz_text);

		r+=30;

		meshButton=new JButton("Mesh");
		meshButton.setBounds(10,r,80,20);
		meshButton.addActionListener(this);
		center.add(meshButton);

		r+=30;

		textureButton=new JButton("Texture");
		textureButton.setBounds(10,r,90,20);
		textureButton.addActionListener(this);
		center.add(textureButton);

	}

	public void printMeshData() {

		super.printMeshData();


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

			print(line);

		}

		/*print("f=[1]4/14 5/15 6/16 7/17");//TOP

		print("f=[0]0/4 1/5 5/10 4/9");//BACK
		print("f=[3]1/5 2/6 6/11 5/10");//RIGHT
		print("f=[4]2/6 3/7 7/12 6/11");//FRONT
		print("f=[2]3/7 0/8 4/13 7/12");///LEFT

		print("f=[5]0/0 3/1 2/2 1/3");//BOTTOM

		//plane

		print("f=[1]12/32 13/33 14/34 15/35");//TOP

		print("f=[0]8/22 9/23 13/28 12/27");//BACK
		print("f=[3]9/23 10/24 14/29 13/28");//RIGHT
		print("f=[4]10/24 11/25 15/30 14/29");//FRONT
		print("f=[2]11/25 8/26 12/31 15/30");///LEFT

		print("f=[5]8/18 11/19 10/20 9/21");//BOTTOM*/

	}



	public void initMesh() {

		dx=dx_text.getvalue();
		dy=dy_text.getvalue();
		dz=dz_text.getvalue();

		points=new Vector();

		//legs
		
		for(int l=0;l<4;l++){
			for(int k=0;k<2;k++){
	
				double z=leg_length*k;
				double x=0;
				double y=0;
				
				if(l==1){
					
					x=dx-leg_side;
					
				}else if(l==2){
					
					y=dy-leg_side;
					
				}else if(l==3){
					
					y=dy-leg_side;
					x=dx-leg_side;
				}
	
				addPoint(x,y,z);
				addPoint(x+leg_side,y,z);
				addPoint(x+leg_side,y+leg_side,z);
				addPoint(x,y+leg_side,z);
	
			}
		}
		//lower and upper base
		for(int k=0;k<2;k++){

			double z=leg_length+dz*k;

			addPoint(0.0,0.0,z);
			addPoint(dx,0.0,z);
			addPoint(dx,dy,z);
			addPoint(0.0,dy,z);

		}

		texturePoints=new Vector();

		/////ll leg

		//lower base
		double y=by;
		double x=bx;

		addTPoint(x,y,0);
		addTPoint(x+leg_side,y,0);
		addTPoint(x+leg_side, y+leg_side,0);
		addTPoint(x,y+leg_side,0);

		//faces
		y=by+leg_side;

		addTPoint(x,y,0);
		addTPoint(x+leg_side,y,0);
		addTPoint(x+leg_side+leg_side,y,0);
		addTPoint(x+leg_side+2*leg_side,y,0);
		addTPoint(x+2*leg_side+2*leg_side,y,0);    	


		addTPoint(x,y+leg_length,0);
		addTPoint(x+leg_side,y+leg_length,0);
		addTPoint(x+leg_side+leg_side,y+leg_length,0);
		addTPoint(x+leg_side+2*leg_side,y+leg_length,0);
		addTPoint(x+2*leg_side+2*leg_side,y+leg_length,0);

		y=by+leg_side+leg_length;

		//upper base
		addTPoint(x,y,0);
		addTPoint(x+leg_side,y,0);
		addTPoint(x+leg_side,y+leg_side,0);
		addTPoint(x,y+leg_side,0);

		///////main plane

		//lower base
		y=by+leg_length+2*leg_side;
		x=bx;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx, y+dy,0);
		addTPoint(x,y+dy,0);

		//faces
		y=by+dy+leg_length+2*leg_side;

		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dy+dx,y,0);
		addTPoint(x+dy+2*dx,y,0);
		addTPoint(x+2*dy+2*dx,y,0);    	


		addTPoint(x,y+dz,0);
		addTPoint(x+dx,y+dz,0);
		addTPoint(x+dy+dx,y+dz,0);
		addTPoint(x+dy+2*dx,y+dz,0);
		addTPoint(x+2*dy+2*dx,y+dz,0);

		y=by+dy+dz+leg_length+2*leg_side;

		//upper base
		addTPoint(x,y,0);
		addTPoint(x+dx,y,0);
		addTPoint(x+dx,y+dy,0);
		addTPoint(x,y+dy,0);


		IMG_WIDTH=(int) (2*dy+2*dx+2*bx);
		IMG_HEIGHT=(int) (dy*2+dz+2*by+leg_length+2*leg_side);
	}





	public void printTexture(Graphics2D bg) {


		//draw lines for reference

		bg.setColor(Color.RED);
		bg.setStroke(new BasicStroke(0.1f));

		//lower base
		printTextureLine(bg,0,1);
		printTextureLine(bg,1,2);
		printTextureLine(bg,2,3);
		printTextureLine(bg,3,0);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTextureLine(bg,4,5);
		printTextureLine(bg,5,10);
		printTextureLine(bg,10,9);
		printTextureLine(bg,9,4);

		printTextureLine(bg,5,6);
		printTextureLine(bg,6,11);
		printTextureLine(bg,11,10);

		printTextureLine(bg,6,7);
		printTextureLine(bg,7,12);
		printTextureLine(bg,12,11);

		printTextureLine(bg,7,8);
		printTextureLine(bg,8,13);
		printTextureLine(bg,13,12);

		//upper base
		bg.setColor(Color.BLUE);
		printTextureLine(bg,14,15);
		printTextureLine(bg,15,16);
		printTextureLine(bg,16,17);
		printTextureLine(bg,17,14);


		int c=18;

		//lower base
		bg.setColor(Color.RED);
		printTextureLine(bg,c+0,c+1);
		printTextureLine(bg,c+1,c+2);
		printTextureLine(bg,c+2,c+3);
		printTextureLine(bg,c+3,c+0);

		//lateral faces
		bg.setColor(Color.BLACK);
		printTextureLine(bg,c+4,c+5);
		printTextureLine(bg,c+5,c+10);
		printTextureLine(bg,c+10,c+9);
		printTextureLine(bg,c+9,c+4);

		printTextureLine(bg,c+5,c+6);
		printTextureLine(bg,c+6,c+11);
		printTextureLine(bg,c+11,c+10);

		printTextureLine(bg,c+6,c+7);
		printTextureLine(bg,c+7,c+12);
		printTextureLine(bg,c+12,c+11);

		printTextureLine(bg,c+7,c+8);
		printTextureLine(bg,c+8,c+13);
		printTextureLine(bg,c+13,c+12);

		//upper base
		bg.setColor(Color.BLUE);
		printTextureLine(bg,c+14,c+15);
		printTextureLine(bg,c+15,c+16);
		printTextureLine(bg,c+16,c+17);
		printTextureLine(bg,c+17,c+14);


	}

	int[][][] faces={

			//leg
			{{1},{4,5,6,7},{14,15,16,17}},
			{{0},{0,1,5,4},{4,5,10,9}},
			{{3},{1,2,6,5},{5,6,11,10}},
			{{4},{2,3,7,6},{6,7,12,11}},
			{{2},{3,0,4,7},{7,8,13,12}},
			{{5},{0,3,2,1},{0,1,2,3}},
			
			{{1},{12,13,14,15},{14,15,16,17}},
			{{0},{8,9,13,12},{4,5,10,9}},
			{{3},{9,10,14,13},{5,6,11,10}},
			{{4},{10,11,15,14},{6,7,12,11}},
			{{2},{11,8,12,15},{7,8,13,12}},
			{{5},{8,11,10,9},{0,1,2,3}},
			
			{{1},{20,21,22,23},{14,15,16,17}},
			{{0},{16,17,21,20},{4,5,10,9}},
			{{3},{17,18,22,21},{5,6,11,10}},
			{{4},{18,19,23,22},{6,7,12,11}},
			{{2},{19,16,20,23},{7,8,13,12}},
			{{5},{16,19,18,17},{0,1,2,3}},
			
			{{1},{28,29,30,31},{14,15,16,17}},
			{{0},{24,25,29,28},{4,5,10,9}},
			{{3},{25,26,30,29},{5,6,11,10}},
			{{4},{26,27,31,30},{6,7,12,11}},
			{{2},{27,24,28,31},{7,8,13,12}},
			{{5},{24,27,26,25},{0,1,2,3}},

			//body
			{{1},{36,37,38,39},{32,33,34,35}},
			{{0},{32,33,37,36},{22,23,28,27}},
			{{3},{33,34,38,37},{23,24,29,28}},
			{{4},{34,35,39,38},{24,25,30,29}},
			{{2},{35,32,36,39},{25,26,31,30}},
			{{5},{32,35,34,33},{18,19,20,21}},

	};
	



	String ub0="34-35";
	String ub1="32-33";
	String lf0="27-28-39-30-31";
	String lf1="22-23-24-25-26";
	String lb0="21-20";
	String lb1="18-19";

	String lub0="17-16";
	String lub1="14-15";
	String llf0="09-10-11-12-13";
	String llf1="04-05-06-07-08";
	String llb0="03-02";
	String llb1="00-01";
	
	
	private void codeGeneration() {
		
		int c=0;
		int t=0;
		
		for (int i = 0; i < faces.length; i++) {

			int[][] face=faces[i];

			int[] fts=face[0];
			int[] pts=face[1];
			int[] tts=face[2];

			String line="{"+"{"+fts[0]+"}";
			
			line+=",{";

			for (int j = 0; j < pts.length; j++) {

				if(j>0)
					line+=",";
				line+=(c+pts[j]);
			}
			
			line+="}";
			
			line+=",{";
			
			for (int j = 0; j < pts.length; j++) {

				if(j>0)
					line+=",";
				line+=(t+tts[j]);
			}
			
			line+="}";

			line+="},";
			
			System.out.println(line);

		}
		
	}
}
