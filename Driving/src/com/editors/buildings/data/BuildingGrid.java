package com.editors.buildings.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;

public class BuildingGrid {
	

	double nw_x=0;
	double nw_y=0;
	double x_side=0;
	double y_side=0;
	double z_side=0;
	
	int xnum=0;
	int ynum=0;
	
	public BuildingCell[][] cells=null;
	
	public BuildingGrid(){}
	
	public BuildingGrid(double nw_x, double nw_y, double x_side, double y_side,double z_side,
			int xnum, int ynum) {
		super();
		this.nw_x = nw_x;
		this.nw_y = nw_y;
		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.xnum = xnum;
		this.ynum = ynum;
		
		cells=new BuildingCell[xnum][ynum];
		
		for (int i = 0; i < xnum; i++) {
			
			for (int j = 0; j <ynum; j++) {
				cells[i][j]=new BuildingCell(nw_x+(i-1)*x_side,nw_y+(j-1)*y_side,x_side,y_side,z_side,i,j);
			}
			
		}
	}
	
	public Object clone(){
		
		BuildingGrid grid=new BuildingGrid(nw_x,nw_y,x_side,y_side,z_side,xnum,ynum);
		
		for (int i = 0; i < xnum; i++) {
			
			for (int j = 0; j <ynum; j++) {
				grid.cells[i][j].setFilled(cells[i][j].isFilled());
			}
			
		}
		
		return grid;
		
	}
	
	public double getNw_x() {
		return nw_x;
	}
	public void setNw_x(double nw_x) {
		this.nw_x = nw_x;
	}
	public double getNw_y() {
		return nw_y;
	}
	public void setNw_y(double nw_y) {
		this.nw_y = nw_y;
	}
	public double getX_side() {
		return x_side;
	}
	public void setX_side(double x_side) {
		this.x_side = x_side;
	}
	public double getY_side() {
		return y_side;
	}
	public void setY_side(double y_side) {
		this.y_side = y_side;
	}
	public int getXnum() {
		return xnum;
	}
	public void setXnum(int xnum) {
		this.xnum = xnum;
	}
	public int getYnum() {
		return ynum;
	}
	public void setYnum(int ynum) {
		this.ynum = ynum;
	}

	public String toString() {
		
		return nw_x+","+nw_y+","+x_side+","+y_side+","+z_side+","+xnum+","+ynum;
	}
	
	public static BuildingGrid buildGrid(String str) {
		
		String[] vals = str.split(",");
		
		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]);
		int xnum = Integer.parseInt(vals[5]);
		int ynum =  Integer.parseInt(vals[6]);
		
		BuildingGrid grid=new BuildingGrid(nw_x,nw_y,x_side,y_side,z_side,xnum,ynum);
	
		return grid;
	}

	public BuildingCell[][] getCells() {
		return cells;
	}

	public void setCells(BuildingCell[][] cells) {
		this.cells = cells;
	}
	
	public PolygonMesh buildMesh(){



		Vector points=new Vector();
		points.setSize(xnum*ynum*2);

		Vector polyData=new Vector();

		for (int k = 0; k < 2; k++) {

			for (int i = 0; i < xnum; i++) {

				for (int j = 0; j <ynum; j++) {

					Point3D p=new Point3D(i*x_side,j*y_side,k*z_side);
					points.setElementAt(p,pos(i,j,k));
				}

			}

		}


		for (int i = 0; i < xnum-1; i++) {

			for (int j = 0; j <ynum-1; j++) {



				//if(!cells[i][j].isFilled())
				//	continue;

				LineData uld=new LineData();
				uld.addIndex(pos(i,j,1));
				uld.addIndex(pos(i+1,j,1));					
				uld.addIndex(pos(i+1,j+1,1));
				uld.addIndex(pos(i,j+1,1));						
				polyData.add(uld);


				LineData dld=new LineData();
				dld.addIndex(pos(i,j,0));
				dld.addIndex(pos(i,j+1,0));					
				dld.addIndex(pos(i+1,j+1,0));
				dld.addIndex(pos(i+1,j,0));
				polyData.add(dld);
				
				LineData backld=new LineData();
				backld.addIndex(pos(i,j,0));
				backld.addIndex(pos(i,j+1,0));					
				backld.addIndex(pos(i,j+1,1));
				backld.addIndex(pos(i,j,1));				
				polyData.add(backld);
				
				
				LineData frontld=new LineData();
				frontld.addIndex(pos(i+1,j,0));
				frontld.addIndex(pos(i+1,j,1));					
				frontld.addIndex(pos(i+1,j+1,1));
				frontld.addIndex(pos(i+1,j+1,0));
				polyData.add(frontld);
				
				
				LineData leftld=new LineData();
				leftld.addIndex(pos(i,j,0));
				leftld.addIndex(pos(i+1,j,0));					
				leftld.addIndex(pos(i+1,j,1));
				leftld.addIndex(pos(i,j,1));				
				polyData.add(leftld);
				
				LineData rightld=new LineData();
				rightld.addIndex(pos(i,j+1,0));
				rightld.addIndex(pos(i,j+1,1));					
				rightld.addIndex(pos(i+1,j+1,1));
				rightld.addIndex(pos(i+1,j+1,0));								
				polyData.add(rightld);

			}
		}	



		PolygonMesh pm=new PolygonMesh(points,polyData);

		return pm;


	}
	
	public int pos(int i, int j, int k){
		
		return (i+xnum*j)*2+k;
	}

}
