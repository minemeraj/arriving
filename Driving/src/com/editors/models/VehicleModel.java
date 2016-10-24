package com.editors.models;

import com.BPoint;

public abstract class VehicleModel extends MeshModel {

	protected double dxRear = 0;
	protected double dyRear = 0;
	protected double dzRear = 0;

	protected double dx = 0;
	protected double dy = 0;
	protected double dz = 0;

	protected double dxRoof = 0;
	protected double dyRoof = 0;
	protected double dzRoof = 0;

	protected double dxFront = 0;
	protected double dyFront = 0;
	protected double dzFront = 0;

	protected double rearOverhang;
	protected double frontOverhang;

	protected double rearOverhang1;
	protected double frontOverhang1;

	/*
	 * distance between the center of the wheel and the base of the vehicle
	 * frame
	 */
	protected double wheelZ;

	protected double x0 = 0;
	protected double y0 = 0;
	protected double z0 = 0;

	protected int bx = 10;
	protected int by = 10;

	protected BPoint[][] rear;
	protected BPoint[][][] back;
	protected BPoint[][][] body;
	protected BPoint[][][] front;
	protected BPoint[][][] roof;
	protected BPoint[][][] cab;
	protected BPoint[][] wagon;
	protected BPoint[][] wagon2;
}
