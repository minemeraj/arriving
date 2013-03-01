package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.LineData;
import com.Point3D;
import com.Point4D;
import com.PolygonMesh;
import com.editors.ComboElement;
import com.editors.DoubleTextField;
import com.editors.IntegerTextField;

public class BendBuilder extends JDialog implements ActionListener{


	static double pi=Math.PI;
	
	int width=300;
	int height=270;
	
	JPanel center=null;

	private DoubleTextField lineWidth;

	private IntegerTextField sectorsNum;

	private JComboBox direction;
	
	public String DIRECTION_NW="0";
	public String DIRECTION_NE="1";
	public String DIRECTION_SW="2";
	public String DIRECTION_SE="3";

	private JButton generate;

	private JButton exit;

	private DoubleTextField centerX;

	private DoubleTextField centerY;
	
	private PolygonMesh pm=new PolygonMesh();

	private DoubleTextField innerRadius;
	
	
	public BendBuilder(Point3D origin){
		
		pm=null;
		
		setTitle("Bend builder");
		setLayout(null);
		setSize(width,height);
		setModal(true);
		
		center=new JPanel(null);
		center.setBounds(0,0,width,height);
		add(center);
		
		int r=10;
		
		JLabel jlb=new JLabel("Center X:");
		jlb.setBounds(10,r,120,20);
		center.add(jlb);		
		centerX=new DoubleTextField();
		centerX.setBounds(130,r,50,20);
		center.add(centerX);
		
		r+=30;
		
		jlb=new JLabel("Center Y:");
		jlb.setBounds(10,r,120,20);
		center.add(jlb);		
		centerY=new DoubleTextField();
		centerY.setBounds(130,r,50,20);
		center.add(centerY);
		
		if(origin!=null){
			
			centerX.setText(origin.x);
			centerY.setText(origin.y);
			
		}
		
		r+=30;
		
		
		jlb=new JLabel("Inner radius:");
		jlb.setBounds(10,r,120,20);
		center.add(jlb);		
		innerRadius=new DoubleTextField();
		innerRadius.setBounds(130,r,50,20);
		center.add(innerRadius);
		
		r+=30;
		
		
		jlb=new JLabel("Line width:");
		jlb.setBounds(10,r,120,20);
		center.add(jlb);		
		lineWidth=new DoubleTextField();
		lineWidth.setBounds(130,r,50,20);
		center.add(lineWidth);
		
		r+=30;
		
		jlb=new JLabel("Sectors num:");
		jlb.setBounds(10,r,120,20);
		center.add(jlb);
		sectorsNum=new IntegerTextField();
		sectorsNum.setBounds(130,r,50,20);
		center.add(sectorsNum);
		
	
		r+=30;
		
		jlb=new JLabel("Direction:");
		jlb.setBounds(10,r,120,20);
		center.add(jlb);		
		direction=new JComboBox();
		direction.addItem(new ComboElement(DIRECTION_NW,"NW"));
		direction.addItem(new ComboElement(DIRECTION_NE,"NE"));
		direction.addItem(new ComboElement(DIRECTION_SW,"SW"));
		direction.addItem(new ComboElement(DIRECTION_SE,"SE"));
		direction.setBounds(130,r,50,20);
		center.add(direction);
		
		r+=30;
		
		generate=new JButton("Generate");
		generate.setBounds(10,r,90,20);
		generate.addActionListener(this);
		center.add(generate);
		
		exit=new JButton("Exit");
		exit.setBounds(130,r,80,20);
		exit.addActionListener(this);
		center.add(exit);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//debug
		setData();
		
		
		
		setVisible(true);
	}
	
	private void setData() {
		
		//centerX.setText(100);
		//centerY.setText(100);
		lineWidth.setText(100);
		sectorsNum.setText(3);
		innerRadius.setText(100);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		
	
		BendBuilder bd=new BendBuilder(null);

	}
	
	

	private void createBend() {
		
		
		try{
			
			
			int sectors_number= sectorsNum.getvalue(); 
			
			double line_width=lineWidth.getvalue();
			
			double x0=centerX.getvalue();
			double y0=centerY.getvalue();
			
			double radius=innerRadius.getvalue();
			
			double teta0=0;
			
			
			ComboElement ce = (ComboElement)direction.getSelectedItem();
			
			if(DIRECTION_NW.equals(ce.getCode())){
				
				teta0=0;
			}
			else if(DIRECTION_NE.equals(ce.getCode())){
				
				teta0=pi*0.5;
			}
			else if(DIRECTION_SE.equals(ce.getCode())){
				
				teta0=pi;
			}

			else if(DIRECTION_SW.equals(ce.getCode())){
				
				teta0=pi*1.5;
				
			}
	
			//createBend(sectors_number+1,line_width,radius,x0,y0,ns,we);
			createMesh(sectors_number+1,line_width,radius,x0,y0,teta0);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}


	public void createBend(int sectors_number, double line_width, double radius, double x0, 
			double y0, int ns, int we) {
	
		System.out.println("Center: ("+x0+","+y0+")");
		
		double gamma=pi/(4*(sectors_number-1));
		double beta=gamma/2.0;
		
		System.out.println("r:"+radius);
		System.out.println("s:"+line_width);
		
		
		double dr_border=line_width/Math.cos(gamma/2);
		double dr=line_width/Math.cos(gamma);
		
		System.out.println("dr:"+dr);
		System.out.println("dr_border:"+dr_border);
		
		System.out.println("gamma:"+gamma);
		System.out.println("beta:"+beta);
		
		for(int i=0;i<sectors_number;i++){

			double teta=i*pi/2.0/(sectors_number-1);
			
			double x=x0+we*radius*Math.cos(teta);
			double y=y0+ns*radius*Math.sin(teta);
			
			double x1=0;
			double y1=0;
			double x2=0;
			double y2=0;
			
			if(i==0){
				
						
				x1=x0+we*(radius+dr_border*Math.cos(beta));
				y1=y0+ns*(dr_border*Math.sin(beta));
				
				x2=x0+we*(radius+2*dr_border*Math.cos(beta));
				y2=y0+ns*(2*dr_border*Math.sin(beta));
			
			}
			
			else if(i==sectors_number-1){
				
						
				x1=x0+we*(dr_border*Math.sin(beta));
				y1=y0+ns*(radius+dr_border*Math.cos(beta));
				
				x2=x0+we*(2*dr_border*Math.sin(beta));
				y2=y0+ns*(radius+2*dr_border*Math.cos(beta));
			
			}
			else {
				
				x1=x0+we*(radius+dr)*Math.cos(teta);
				y1=y0+ns*(radius+dr)*Math.sin(teta);
				
				x2=x0+we*(radius+2*dr)*Math.cos(teta);
				y2=y0+ns*(radius+2*dr)*Math.sin(teta);
				
				
			}
			System.out.println(i+" a "+teta+"\t "+"("+x+","+y+")"+"\t "+"("+x1+","+y1+")"+"\t "+"("+x2+","+y2+")");
	
		}
		
	}
	
	
	public PolygonMesh createMesh(int sectors_number, double line_width, double radius, double x0, 
			double y0, double teta0) {
		
		Vector v_points=new Vector();
		
		Point4D center=new Point4D(x0,y0,0);		
		v_points.add(center);
		
		pm=new PolygonMesh();
	
		//System.out.println("Center: ("+x0+","+y0+")");
		
		double gamma=pi/(4*(sectors_number-1));
		double beta=gamma/2.0;
		
		/*System.out.println("r:"+radius);
		System.out.println("s:"+line_width);*/
		
		
		double dr_border=line_width/Math.cos(gamma/2);
		double dr=line_width/Math.cos(gamma);
		
		/*System.out.println("dr:"+dr);
		System.out.println("dr_border:"+dr_border);
		
		System.out.println("gamma:"+gamma);
		System.out.println("beta:"+beta);*/
		
		for(int i=0;i<sectors_number;i++){

			double teta=i*pi/2.0/(sectors_number-1)+teta0;
			

			if(i==0){
				
				double x=x0+radius*Math.cos(teta);
				double y=y0+radius*Math.sin(teta);
				
				
				double x1=x0+(radius+line_width)*Math.cos(teta);
				double y1=y0+(radius+line_width)*Math.sin(teta);
				
				double x2=x0+(radius+2*line_width)*Math.cos(teta);
				double y2=y0+(radius+2*line_width)*Math.sin(teta);
				
				Point4D p0=new Point4D(x,y,0);
				Point4D p1=new Point4D(x1,y1,0);
				Point4D p2=new Point4D(x2,y2,0);				
						
				double xx1=x0+(radius+dr_border*Math.cos(beta));
				double yy1=y0+(dr_border*Math.sin(beta));
				
				double xx2=x0+(radius+2*dr_border*Math.cos(beta));
				double yy2=y0+(2*dr_border*Math.sin(beta));
				
				Point4D p11=new Point4D(xx1,yy1,0);
				Point4D p22=new Point4D(xx2,yy2,0);
				
				p11=rotate(p11,x0,y0,teta0);
				p22=rotate(p22,x0,y0,teta0);
				
				v_points.add(p0);
				v_points.add(p1);
				v_points.add(p2);
				
				v_points.add(p11);
				v_points.add(p22);
			
			}
			
			else if(i==sectors_number-1){
				
				double x=x0+radius*Math.cos(teta);
				double y=y0+radius*Math.sin(teta);
				
				
				double x1=x0+(radius+line_width)*Math.cos(teta);
				double y1=y0+(radius+line_width)*Math.sin(teta);
				
				double x2=x0+(radius+2*line_width)*Math.cos(teta);
				double y2=y0+(radius+2*line_width)*Math.sin(teta);
				
				Point4D p0=new Point4D(x,y,0);
				Point4D p1=new Point4D(x1,y1,0);
				Point4D p2=new Point4D(x2,y2,0);
				
						
				double xx1=x0+(dr_border*Math.sin(beta));
				double yy1=y0+(radius+dr_border*Math.cos(beta));
				
				double xx2=x0+(2*dr_border*Math.sin(beta));
				double yy2=y0+(radius+2*dr_border*Math.cos(beta));
				
				Point4D p11=new Point4D(xx1,yy1,0);
				Point4D p22=new Point4D(xx2,yy2,0);
				
				p11=rotate(p11,x0,y0,teta0);
				p22=rotate(p22,x0,y0,teta0);
				
				v_points.add(p0);
				
				v_points.add(p11);
				v_points.add(p22);
								
				v_points.add(p1);
				v_points.add(p2);
			
			}
			else {
				
				double x=x0+radius*Math.cos(teta);
				double y=y0+radius*Math.sin(teta);
				
				
				double x1=x0+(radius+dr)*Math.cos(teta);
				double y1=y0+(radius+dr)*Math.sin(teta);
				
				double x2=x0+(radius+2*dr)*Math.cos(teta);
				double y2=y0+(radius+2*dr)*Math.sin(teta);
				
				Point4D p0=new Point4D(x,y,0);
				Point4D p1=new Point4D(x1,y1,0);
				Point4D p2=new Point4D(x2,y2,0);
								
				v_points.add(p0);
				v_points.add(p1);
				v_points.add(p2);
			}
	
		}
		

		/*for (int j = 0; j < v_points.size(); j++) {
			Point3D p = (Point3D) v_points.elementAt(j);
			System.out.println(p);
		}*/
		
		pm.points=PolygonMesh.fromVectorToArray(v_points);
		
		//start point is the center index=0
		
	
		int cumulative=0;
		
		for(int i=0;i<sectors_number-1;i++){
			
			LineData ld=new LineData();
			
			
			
			int i1=1+3*i+cumulative;			
			
			if(i==0 )
				cumulative+=2;
	

			int i2=1+3*(i+1)+cumulative;
			
			ld.addIndex(i1);
			ld.addIndex(i2);
			ld.addIndex(0);
			
			pm.polygonData.add(ld);
			
			if(i!=0 && i!=sectors_number-2){
				
				LineData ld1=new LineData();
				
				ld1.addIndex(i1+1);
				ld1.addIndex(i2+1);
				ld1.addIndex(i2);
				ld1.addIndex(i1);
				
				pm.polygonData.add(ld1);
			
				
				LineData ld2=new LineData();
				
				ld2.addIndex(i1+2);
				ld2.addIndex(i2+2);
				ld2.addIndex(i2+1);
				ld2.addIndex(i1+1);
				
				pm.polygonData.add(ld2);
				
			}
			else if(i==0){
				
				
				LineData ld1=new LineData();
				ld1.addIndex(i1+1);
				ld1.addIndex(i1+3);
				ld1.addIndex(i1);
				
				pm.polygonData.add(ld1);
				
				LineData ld2=new LineData();
				ld2.addIndex(i1+2);
				ld2.addIndex(i1+4);
				ld2.addIndex(i1+3);
				ld2.addIndex(i1+1);
				
				pm.polygonData.add(ld2);
				
				
				LineData ld3=new LineData();
				ld3.addIndex(i1+3);
				ld3.addIndex(i2+1);
				ld3.addIndex(i2);
				ld3.addIndex(i1);
				
				pm.polygonData.add(ld3);
				
				
				LineData ld4=new LineData();
				
				ld4.addIndex(i1+4);
				ld4.addIndex(i2+2);
				ld4.addIndex(i2+1);
				ld4.addIndex(i1+3);
				
				pm.polygonData.add(ld4);
	
			}		
			else if(i==sectors_number-2){
				
				
				LineData ld1=new LineData();
				
				
				ld1.addIndex(i1+1);
				ld1.addIndex(i2+1);
				ld1.addIndex(i2);
				ld1.addIndex(i1);
								
				pm.polygonData.add(ld1);
	
				LineData ld2=new LineData();
								
				ld2.addIndex(i1+2);
				ld2.addIndex(i2+2);
				ld2.addIndex(i2+1);
				ld2.addIndex(i1+1);
				
				pm.polygonData.add(ld2);
							
				LineData ld3=new LineData();
				
				ld3.addIndex(i2+1);
				ld3.addIndex(i2+3);
				ld3.addIndex(i2);
				
				pm.polygonData.add(ld3);
				
				
				LineData ld4=new LineData();
				
				ld4.addIndex(i2+2);
				ld4.addIndex(i2+4);
				ld4.addIndex(i2+3);
				ld4.addIndex(i2+1);
				
				
				pm.polygonData.add(ld4);
	
			}		
			
			
			//assign road texture 3
			
			for (int j = 0; j < pm.polygonData.size(); j++) {
				LineData lid = (LineData) pm.polygonData.elementAt(j);
				lid.setTexture_index(3);
			}
			
			
		}
		
		return pm;
		
	}

	private Point4D rotate(Point4D p, double x0, double y0, double teta0) {
		
		double x=(p.x-x0)*Math.cos(teta0)-(p.y-y0)*Math.sin(teta0)+x0;
		double y=(p.x-x0)*Math.sin(teta0)+(p.y-y0)*Math.cos(teta0)+y0;
		
		return new Point4D(x,y,0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		Object obj = e.getSource();
		
		if(obj==generate){
			
			createBend();
			dispose();
		}
		else if(obj==exit){
			
			exit();
			
			
		}
		
	}


	private void exit() {
		
		pm=null;
		dispose();
		
	}

	public PolygonMesh getBendMesh() {
		return pm;
	}

}
