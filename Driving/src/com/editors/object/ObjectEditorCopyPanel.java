package com.editors.object;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;

public class ObjectEditorCopyPanel extends JDialog implements ActionListener {
	
	private final int WIDTH=300;
	private final int BOTTOM_HEIGHT=100;
	private final int HEIGHT=200;
	
	private PolygonMesh pm=null;
	
	private boolean saved=false;
	private JButton copy;
	private JButton delete;
	private JTabbedPane jtb;
	private DoubleTextField translate_dx;
	private DoubleTextField translate_dy;
	private DoubleTextField translate_dz;
	private JCheckBox invert_polygons;

	public ObjectEditorCopyPanel(Point3D[] points, ArrayList<LineData> lines) {
		
		
		setSize(WIDTH,HEIGHT+BOTTOM_HEIGHT);
		setLayout(null);
		setModal(true);
		setTitle("Choose template");
		
		saved=false;
		
		pm=new PolygonMesh(points,lines);
		
		jtb=new JTabbedPane();
		jtb.setBounds(0, 0, WIDTH, HEIGHT);
        add(jtb); 
        
        jtb.add("translate",getBoxTranslate());
		
        copy=new JButton("Copy");
        copy.setBounds(10,HEIGHT+10,150,20);
        copy.addActionListener(this);
        add(copy);
        
        delete=new JButton("Cancel");
        delete.setBounds(180,HEIGHT+10,100,20);
        delete.addActionListener(this);
        add(delete);

		
		setVisible(true);
	}

	private Component getBoxTranslate() {
	
		
		JPanel box=new JPanel();
		box.setLayout(null);
		
		int r=10;
		int column=45;

		JLabel jlb=new JLabel("DX");
		jlb.setBounds(5, r, 40, 20);
		box.add(jlb);

		translate_dx=new DoubleTextField();
		translate_dx.setBounds(column, r, 100, 20);
		box.add(translate_dx);
		
		
		r+=30;
		
		jlb=new JLabel("DY");
		jlb.setBounds(5, r, 40, 20);
		box.add(jlb);

		translate_dy=new DoubleTextField();
		translate_dy.setBounds(column, r, 100, 20);
		box.add(translate_dy);
		
		r+=30;
		
		jlb=new JLabel("DZ");
		jlb.setBounds(5, r, 40, 20);
		box.add(jlb);

		translate_dz=new DoubleTextField();
		translate_dz.setBounds(column, r, 100, 20);
		box.add(translate_dz);
		
		r+=30;
		
		jlb=new JLabel("Invert polygons");
		jlb.setBounds(5, r, 140, 20);
		box.add(jlb);

		invert_polygons=new JCheckBox();
		invert_polygons.setBounds(150, r, 40, 20);
		box.add(invert_polygons);
		
		return box;
	}

	public PolygonMesh getCopy() {
		
		if(saved)
			return pm;
		else
			return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object obj = arg0.getSource();
		
		if(obj==delete){
			
			saved=false;
			dispose();
		}
		else if(obj==copy){
			
			copy();
			saved=true;
			dispose();
			
		}
		
	}

	private void copy() {
		
		if(translate_dx.getvalue()==0 && translate_dy.getvalue()==0 && translate_dz.getvalue()==0 )
		{
			
			saved=false;
			
			return;
		}	
		
		double dx=translate_dx.getvalue();
		double dy=translate_dy.getvalue();
		double dz=translate_dz.getvalue();
		
		int num_points=pm.points.length;
		
	
		
		int counter=0;
		
		for (int i = 0; i < pm.points.length; i++) {
			
			Point3D p=pm.points[i];
			
			if(p.isSelected){
				
				
				counter++;
			}
			
		}
		
		Point3D[] new_points=new Point3D[num_points+counter];
		ArrayList<LineData> new_lines=new ArrayList<LineData>();
		
		counter=0;
		
		Hashtable<Integer,Integer> codes=new Hashtable<Integer,Integer>();

		for (int i = 0; i < pm.points.length; i++) {
			
			Point3D p=pm.points[i];
			
			new_points[i]=p.clone();
			
			if(p.isSelected){
				
				int new_pos=counter+num_points;
				
				codes.put(i,new_pos);
				
				new_points[new_pos]=new Point3D(p.x+dx,p.y+dy,p.z+dz);
				counter++;
				
			}
			
			

		}
		
		for (int i = 0; i < pm.polygonData.size(); i++) {
			LineData ld = (LineData) pm.polygonData.get(i);
			
			new_lines.add(ld);
		}
		
		for (int i = 0; i < pm.polygonData.size(); i++) {
			LineData ld = (LineData) pm.polygonData.get(i);
			
			LineData new_ld=new LineData();
			
			for (int j = 0; j < ld.size(); j++) {
				
				int index=ld.getIndex(j);
				
				Integer code=(Integer) codes.get(index);
				
				if(code!=null){
					
					
					new_ld.addIndex(code.intValue());
					
				}
				else{
					
					new_ld.lineDatas.clear();
					break;
					
				}
				
			}
			
			if(new_ld.size()>0){
				
				if(invert_polygons.isSelected())
					new_lines.add(invertLine(new_ld));
				else	
					new_lines.add(new_ld);
				
			}
			
			
		}
		
		pm.points=new_points;
		pm.polygonData=new_lines;
	}
	

	private LineData invertLine(LineData new_ld) {
	
		
		LineData ld=new LineData();
		
		for (int i = new_ld.size()-1; i >=0; i--) {
			
		
			ld.addIndex(new_ld.getIndex(i));
			
		}
		
		return ld;
	
	}

}
