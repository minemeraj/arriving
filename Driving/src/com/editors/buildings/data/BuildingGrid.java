package com.editors.buildings.data;

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

}
