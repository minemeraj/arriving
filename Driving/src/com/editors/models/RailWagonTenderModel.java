package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * One texture model
 * Summing up the best creation logic so far
 *
 * @author Administrator
 *
 */
public class RailWagonTenderModel extends RailWagon0Model{

	public static String NAME="Tender Wagon";

	public RailWagonTenderModel(
			double dx, double dy, double dz,
			double dxf, double dyf, double dzf,
			double dxr, double dyr,	double dzr,
			double dxRoof,double dyRoof,double dzRoof,
			double rearOverhang, double frontOverhang,
			double wheelRadius, double wheelWidth, int wheelRays
			) {
		super(dx, dy, dz,
				dxf, dyf, dzf,
				dxr, dyr, dzr,
				dxRoof, dyRoof, dzRoof,
				rearOverhang, frontOverhang,
				wheelRadius, wheelWidth, wheelRays);

	}

	@Override
	public void printTexture(Graphics2D bufGraphics) {

		bodyColor=new Color(0, 0, 0);
		wheelColor=new Color(255, 0, 0);
		super.printTexture(bufGraphics);
	}


}
