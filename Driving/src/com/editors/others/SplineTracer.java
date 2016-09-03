package com.editors.others;

import java.util.ArrayList;

import com.Point3D;

public class SplineTracer extends ImageTracer {
	
	public static void main(String[] args) {

		SplineTracer st=new SplineTracer();

	}

	
	public SplineTracer(){
		super();
		setTitle("Spline tracer");
		
		CLOSING_TAG = "</spline>";
		OPENING_TAG = "<spline>";
	}
	
	@Override
	protected String decomposePoint(Point3D p, double factor, double outputDX2, double outputDY2) {
		String str="";

		str=(p.x*factor+outputDX2)+" "+(p.y*factor+outputDY2)+" "+(p.z*factor);

		if(p.getData()!=null){
			str=p.getData().toString()+" "+str;
		}else{
			str="T0"+" "+str;
		}
		return str;
	}
	
	@Override
	protected void buildPoint(ArrayList<ArrayList<Point3D>> vLines,String str, 
			double outputScaleValue, 
			double outputDX2, 
			double outputDY2) {


		ArrayList<Point3D> points=null;

		if(vLines.size()==0){

			points= new ArrayList<Point3D>();
			vLines.add(points);

		}else{

			points=vLines.get(vLines.size()-1);
		}

		if(outputScaleValue==0)
			outputScaleValue=1.0;

		String[] vals =str.split(" ");

		Point3D p=new Point3D();

		String textureData=vals[0];
		p.setData(textureData);
		p.x=(Double.parseDouble(vals[1])-outputDX2)/outputScaleValue;
		p.y=(Double.parseDouble(vals[2])-outputDY2)/outputScaleValue;
		p.z=Double.parseDouble(vals[3])/outputScaleValue;

		points.add(p);


	}
}
