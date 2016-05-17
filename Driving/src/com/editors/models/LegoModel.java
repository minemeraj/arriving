package com.editors.models;

import java.awt.Graphics2D;
import java.util.Vector;

import com.Point3D;
import com.main.Renderer3D;
/**
 * LEGO LIKE MODEL
 * 
 * 
 * @author Administrator
 *
 */
public class LegoModel extends MeshModel{
	
	private int bx=10;
	private int by=10;
	
	private double dx = 0;
	private double dy = 0;
	private double dz = 0;
	
	private int[][][] faces;
	
	int basePoints=4;
	
	public LegoModel(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}


	@Override
	public void initMesh() {
		points=new Vector<Point3D>();
		texturePoints=new Vector();
		
		//points
		
		for (int i = 0; i < bodyBlocks.length; i++) {
			
			double x=bodyBlocks[i][0][0];
			double y=bodyBlocks[i][0][1];
			double z=bodyBlocks[i][0][2];
			
			
			addPoint(x, y, z);
			addPoint(x+dx, y, z);
			addPoint(x+dx, y+dy, z);
			addPoint(x, y+dy, z);
			
			
			addPoint(x, y, z+dz);
			addPoint(x+dx, y, z+dz);
			addPoint(x+dx, y+dy, z+dz);
			addPoint(x, y+dy, z+dz);
			
		}
		
		//texture points
		
		for (int k = 0; k < bodyBlocks.length; k++) {
			
			double y=by+dy+k*(dy+2*dz);
			double x=bx;

			for (int p0 = 0; p0 <= basePoints; p0++) {
				addTPoint(x,y,0);

				if(p0==0 || p0==2)
					x+=dz;
				else if(p0==1 || p0==3)
					x+=dx;

			}
			
			//bottom base
			double bbx=bx+dz+k*(dy+2*dz);
			
			addTPoint(bbx,by,0);
			addTPoint(bbx+dx,by,0);
			addTPoint(bbx+dx,by+dz,0);
			addTPoint(bbx,by+dz,0);
			
			
			//top base
			
			double bby=by+dz+dy+k*(dy+2*dz);
			addTPoint(bbx,bby,0);
			addTPoint(bbx+dx,bby,0);
			addTPoint(bbx+dx,bby+dz,0);
			addTPoint(bbx,bby+dz,0);
		}
		
		//faces
		int NF=bodyBlocks.length*6;
		faces=new int[NF][3][4];
		
		int count=0;
		
		for (int i = 0; i < bodyBlocks.length; i++) {
			
			int b=i*basePoints*2;
			int c0=i*((basePoints+1)+2);
			int c3=(i+1)*(basePoints+1)+2*i;
			
			faces[count++]=buildFace(Renderer3D.CAR_BACK,b,b+3,b+2,b+1,c0,c0+1,c3+1,c3);
			faces[count++]=buildFace(Renderer3D.CAR_LEFT,b,b+4,b+3+4,b+3,c0+1,c0+1+1,c3+1+1,c3+1);
			faces[count++]=buildFace(Renderer3D.CAR_RIGHT,b+1,b+2,b+2+4,b+1+4,c0+2,c0+2+1,c3+2+1,c3+2);
			faces[count++]=buildFace(Renderer3D.CAR_FRONT,b+2,b+3,b+3+4,b+2+4,c0+3,c0+3+1,c3+3+1,c3+3);
			
			int cc0=c0+2*(basePoints+1);			
			faces[count++]=buildFace(Renderer3D.CAR_BOTTOM,b,b+3,b+2,b+1,cc0,cc0+1,cc0+2,cc0+3);
			
			cc0+=4;
			faces[count++]=buildFace(Renderer3D.CAR_TOP,b+4,b+1+4,b+2+4,b+3+4,cc0,cc0+1,cc0+2,cc0+3);
		}
		
		IMG_WIDTH=(int) (2*bx+bodyBlocks.length*2*(dx+dz));
		IMG_HEIGHT=(int) (2*by+bodyBlocks.length*(dy+2*dz));
		
	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {
		
		for (int i = 0; i < bodyBlocks.length; i++) {
			
			int cb=i*((basePoints+1)*2+2);
			
			for (int p0 = 0; p0 < basePoints; p0++) {
				
				int c0=i*(basePoints+1)+p0+cb;
				int c3=(i+1)*(basePoints+1)+p0+cb;
				
				printTexturePolygon(bufGraphics,c0,c0+1,c3+1,c3);
	
			}	
			
			//lower base
			int cc=4*(basePoints+1)+cb;
			printTexturePolygon(bufGraphics,cc,cc+1,cc+2,cc+3);
			//upperbase
			cc+=basePoints+cb;
			printTexturePolygon(bufGraphics,cc,cc+1,cc+2,cc+3);
		
		}
	}
	//blocks x,y,z
	public double[][][] bodyBlocks={
			{{0.0,0.0,0.0}},
	};

}
