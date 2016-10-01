package com.editors.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.Vector;

import com.Point3D;

public class ManModel extends MeshModel{

	private double dx = 0;
	private double dy = 0;
	private double dz = 0;

	private int[][][] faces;
	private int[][][] rightArmFaces;
	private int[][][] leftArmFaces;
	private int[][][] rightLegFaces;
	private int[][][] leftLegFaces;

	private int nBasePoints=4;

	private int bx=10;
	private int by=10;

	public static final String NAME="Man";

	public ManModel(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public void initMesh() {

		points=new Vector();
		texturePoints=new Vector();

		int numSections=body.length;
		int legsNumSections=3;
		int armsNumSections=3;

		double leg_length=dz;


		int totalBodyPoints=0;
		int totalLegPoints=0;
		int totalArmPoints=0;



		Point3D crotch0=null;
		Point3D crotch1=null;
		Point3D armpit0=null;
		Point3D armpit1=null;

		for(int k=0;k<numSections;k++){

			double[] d=body[k];

			double xi=d[1];
			double yi=d[2];
			double zi=d[0];

			double z=zi*dz+leg_length;
			double deltax=dx*xi*0.5;
			double deltay=dy*yi*0.5;

			double x=0;
			double y=0;

			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);

			totalBodyPoints+=4;


			if(k==0)
				crotch0=new Point3D(deltax,deltay,z);
			else if(k==1)
				crotch1=new Point3D(deltax,deltay,z);
			else if(k==3)
				armpit0=new Point3D(deltax,deltay,z);
			else if(k==4)
				armpit1=new Point3D(deltax,deltay,z);
		}

		double leg_width=(crotch1.x-crotch0.y);
		double arm_length=armpit0.z-leg_length;

		//right leg

		for (int i = 0; i < legsNumSections; i++) {

			//join with the bust
			if(i==legsNumSections-1){

				addPoint(-crotch1.x, -crotch1.y, crotch1.z);
				addPoint(-crotch0.x, -crotch0.y, crotch0.z);
				addPoint(-crotch0.x, crotch0.y, crotch0.z);
				addPoint(-crotch1.x, crotch1.y, crotch1.z);

				totalLegPoints+=4;

				continue;
			}

			double z=leg_length/(legsNumSections-1)*i;

			double x=-crotch0.x-leg_width*0.5;
			double y=0;


			double deltax=leg_width*0.5;
			double deltay=leg_width*0.5;

			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);

			totalLegPoints+=4;
		}

		//left leg
		for (int i = 0; i < legsNumSections; i++) {

			//join with the bust
			if(i==legsNumSections-1){

				addPoint(crotch0.x, -crotch0.y, crotch0.z);
				addPoint(crotch1.x, -crotch1.y, crotch1.z);
				addPoint(crotch1.x, crotch1.y, crotch1.z);
				addPoint(crotch0.x, crotch0.y, crotch0.z);

				continue;
			}

			double z=leg_length/(legsNumSections-1)*i;

			double x=crotch0.x+leg_width*0.5;
			double y=0;

			double deltax=leg_width*0.5;
			double deltay=leg_width*0.5;

			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);
		}

		//right arm
		for (int i = 0; i < armsNumSections; i++) {

			//join with the bust
			if(i==armsNumSections-1){

				addPoint(-armpit1.x, -armpit1.y, armpit1.z);
				addPoint(-armpit0.x, -armpit0.y, armpit0.z);
				addPoint(-armpit0.x, armpit0.y, armpit0.z);
				addPoint(-armpit1.x, armpit1.y, armpit1.z);

				totalArmPoints+=4;

				continue;
			}

			double z=i*arm_length/(armsNumSections-1.0)+leg_length;

			double x=-dx*0.5+10;
			double y=0;

			double deltax=10;
			double deltay=10;

			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);

			totalArmPoints+=4;
		}

		//left arm
		for (int i = 0; i < armsNumSections; i++) {

			//join with the bust
			if(i==armsNumSections-1){

				addPoint(armpit0.x, -armpit0.y, armpit0.z);
				addPoint(armpit1.x, -armpit1.y, armpit1.z);
				addPoint(armpit1.x, armpit1.y, armpit1.z);
				addPoint(armpit0.x, armpit0.y, armpit0.z);

				continue;
			}

			double z=i*arm_length/(armsNumSections-1.0)+leg_length;

			double x=dx*0.5-10;
			double y=0;

			double deltax=10;
			double deltay=10;

			addPoint(x-deltax, y-deltay, z);
			addPoint(x+deltax, y-deltay, z);
			addPoint(x+deltax, y+deltay, z);
			addPoint(x-deltax, y+deltay, z);
		}


		int totalBodyTexturePoints=0;
		int totalLegTexturePoints=0;
		int totalArmTexturePoints=0;

		//double block texture
		for(int tex=0;tex<2;tex++){
			for(int k=0;k<numSections;k++){

				double[] d=body[k];

				double xi=d[1];
				double yi=d[2];
				double zi=d[0];

				double z=by+zi*dz+leg_length+arm_length;

				double x0=bx+dx/2;
				if(tex==1)
					x0=x0+(2*dy+dx);

				for (int p0 = 0; p0 <= nBasePoints/2; p0++) {

					double x=0;

					if(p0<nBasePoints/4){

						x=x0-dx*0.5*xi;

					}else if(p0>=nBasePoints/4 && p0<nBasePoints/2)
					{
						x=x0+dx*0.5*xi;
					}
					else if(p0==nBasePoints/2){

						x=x0+dx*0.5*xi+dy*yi;
					}



					addTPoint(x,z,0);

					totalBodyTexturePoints++;


				}
			}
		}

		//right leg
		for (int k = 0; k < legsNumSections; k++) {

			double z=k*leg_length/(legsNumSections-1.0)+arm_length;

			double x=bx;

			for(int s=0;s<=4;s++){

				addTPoint(x,z,0);

				x+=10;

				totalLegTexturePoints++;
			}

		}

		//left leg
		for (int k = 0; k < legsNumSections; k++) {

			double z=k*leg_length/(legsNumSections-1.0)+arm_length;

			double x=bx+dx+dy;

			for(int s=0;s<=4;s++){

				addTPoint(x,z,0);

				x+=10;
			}

		}


		//right arm
		for (int k = 0; k < armsNumSections; k++) {


			double z=k*arm_length/(armsNumSections-1.0);

			double x=bx;

			for(int s=0;s<=4;s++){

				addTPoint(x,z,0);

				x+=10;

				totalArmTexturePoints++;
			}

		}

		//left arm
		for (int k = 0; k < armsNumSections; k++) {

			double z=k*arm_length/(armsNumSections-1.0);

			double x=bx+dx+dy;

			for(int s=0;s<=4;s++){

				addTPoint(x,z,0);

				x+=10;
			}

		}

		faces=buildDoubleBlockFaces(nBasePoints,numSections,0,0);
		rightLegFaces=buildSingleBlockFaces(4, legsNumSections,
				totalBodyPoints,
				totalBodyTexturePoints);
		leftLegFaces=buildSingleBlockFaces(4, legsNumSections,
				totalBodyPoints+totalLegPoints,
				totalBodyTexturePoints+totalLegTexturePoints);
		rightArmFaces=buildSingleBlockFaces(4, armsNumSections,
				totalBodyPoints+2*totalLegPoints,
				totalBodyTexturePoints+2*totalLegTexturePoints);
		leftArmFaces=buildSingleBlockFaces(4, armsNumSections,
				totalBodyPoints+2*totalLegPoints+totalArmPoints,
				totalBodyTexturePoints+2*totalLegTexturePoints+totalArmTexturePoints);

		IMG_WIDTH=(int) (2*bx+2*(dx+dy)+dy);
		IMG_HEIGHT=(int) (2*by+dz+leg_length+arm_length);
	}




	@Override
	public void printTexture(Graphics2D bg) {
		//draw lines for reference

		bg.setColor(Color.BLACK);
		bg.setStroke(new BasicStroke(0.1f));
		printTextureFaces(bg,faces);
		bg.setColor(Color.RED);
		printTextureFaces(bg,rightLegFaces);
		bg.setColor(Color.BLUE);
		printTextureFaces(bg,leftLegFaces);
		bg.setColor(Color.BLUE);
		printTextureFaces(bg,leftArmFaces);
		bg.setColor(Color.RED);
		printTextureFaces(bg,rightArmFaces);
	}



	@Override
	public void printMeshData(PrintWriter pw) {

		super.printMeshData(pw);
		super.printFaces(pw, faces);
		super.printFaces(pw, rightLegFaces);
		super.printFaces(pw, leftLegFaces);
		super.printFaces(pw, rightArmFaces);
		super.printFaces(pw, leftArmFaces);
	}

	/**
	 * BOTTOM-UP SECTIONS
	 * z,x,y
	 *
	 */
	private final double[][] body={

			{0.0,0.1111,1.0},
			{0.1239,0.6032,1.0},
			{0.2743,0.5556,1.0},
			{0.4867,0.6508,1.0},
			{0.5221,1.0,1.0},
			{0.5929,0.9206,1.0},
			{0.6372,0.7778,1.0},
			{0.6991,0.2381,1.0},
			{0.7611,0.2540,1.0},
			{0.9027,0.3810,1.0},
			{0.9823,0.1905,1.0},

	};


}
