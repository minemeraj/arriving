package com.editors.models;

public class RiderManModel extends Man3Model{

	public static final String NAME="Rider";

	public RiderManModel(
			double dx, double dy, double dz,
			double dxFront, double dyfront, double dzFront,
			double dxr,	double dyr, double dzr,
			double dxRoof, double dyRoof, double dzRoof) {

		super(dx, dy, dz, dxFront, dyfront, dzFront, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof);
	}

}
