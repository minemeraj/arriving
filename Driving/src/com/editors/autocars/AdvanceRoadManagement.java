package com.editors.autocars;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.Point3D;
import com.editors.DoubleTextField;


public class AdvanceRoadManagement extends JDialog implements ActionListener {
	
	private JButton exit=null;
	private JPanel bottom=null;
	
	private JTable table=null;
	private JButton save;
	
	private LinkedList newLinkedList=null;
	private JPopupMenu popupMenu;
	private JMenuItem jitem_insert_before;
	private JMenuItem jitem_insert_after;
	private JMenuItem jitem_delete;
	
	private int popup_row=-1;

	
	public AdvanceRoadManagement(LinkedList linkedList){
		
		newLinkedList=null;

		
		setModal(true);
		setTitle("Advance Road Management");
		setSize(500,400);
		
	
	    table=createTable(linkedList);
	    
		JScrollPane jscp=new JScrollPane(table);
		add(jscp);
		
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		buildPopupMenu();
		
		
		bottom=new JPanel();
		
		save=new JButton("Save");
		save.addActionListener(this);
		bottom.add(save);
		
		exit=new JButton("Exit");
		exit.addActionListener(this);
		bottom.add(exit);
		
		add(BorderLayout.SOUTH,bottom);
		
		setVisible(true);
	}

	private void buildPopupMenu() {
	
		
		popupMenu = new JPopupMenu();
		popupMenu.setLayout(null);

		int r=10;
		
		jitem_insert_before=new JMenuItem("Insert row before");
		jitem_insert_before.addActionListener(this);	
		jitem_insert_before.setBounds(10,r,100,20);
		popupMenu.add(jitem_insert_before);
		
		
		jitem_insert_after=new JMenuItem("Insert row after");
		jitem_insert_after.addActionListener(this);	
		jitem_insert_after.setBounds(10,r,100,20);
		popupMenu.add(jitem_insert_after);
		
		popupMenu.addSeparator();
		
		jitem_delete=new JMenuItem("Delete row");
		jitem_delete.addActionListener(this);	
		jitem_delete.setBounds(10,r,100,20);
		popupMenu.add(jitem_delete);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {


		Object o = arg0.getSource();
		
		
		if(o==exit)
			exit();
		else if(o==save){
			
			
			save();
		}
		else if(o==jitem_delete){
			
			deleteRow(popup_row);
			
		}
		else if(o==jitem_insert_after){
			
			insertRowAfter(popup_row);
			
		}
		else if(o==jitem_insert_before){
			
			insertRowBefore(popup_row);
			
		}

		
	}
	


	private void save() {
		
		DefaultTableModel model=(DefaultTableModel) table.getModel();
		
		newLinkedList=new LinkedList();
		
		Vector data = model.getDataVector();
		
		for (int i = 0; i < data.size(); i++) {
			Vector record = (Vector) data.elementAt(i);
			
			Integer pos=(Integer) record.elementAt(0);
			
			Double x=(Double) record.elementAt(1);
			Double y=(Double) record.elementAt(2);
			Double z=(Double) record.elementAt(3);
			
			//System.out.println(x+","+y+","+z);
			newLinkedList.add(new Point3D(x,y,z));
		}
		
		dispose();
	}

	private void exit(){
		
		newLinkedList=null;
		
		dispose();
		
	}
	
	private JTable createTable(LinkedList linkedList) {



	

		JTable table=new JTable(){
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return column>0;
			}
			
			
			
			
		};

		table.addMouseListener(
				
				
				new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						
						
						openPopup(e);
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						openPopup(e);
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
						 int count=e.getClickCount();
						
						
					}
				}
		
		
				
		);

		
		
	

		Vector columns=new Vector();
		columns.add("POS");
		columns.add("X");
		columns.add("Y");
		columns.add("Z");

		
		
		Vector data=new Vector();
		
		int size=linkedList.size();

		for (int i = 0; i <size ; i++) {
			
			Point3D point=(Point3D) linkedList.get(i);
			
			Vector record=new Vector();
			
			
			record.add(new Integer(i));
			record.add(new Double(point.x));
			record.add(new Double(point.y));
			record.add(new Double(point.z));
			
			data.add(record);
		}	

		DefaultTableModel model=new DefaultTableModel(data,columns);
		table.setModel(model);
	
		DoubleCellRenderer cr = new DoubleCellRenderer();
		DoubleCellEditor ce=new DoubleCellEditor();	
	
	
		table.getColumnModel().getColumn(1).setCellRenderer(cr);
		table.getColumnModel().getColumn(1).setCellEditor(ce);
		
		table.getColumnModel().getColumn(2).setCellRenderer(cr);
		table.getColumnModel().getColumn(2).setCellEditor(ce);
		
		table.getColumnModel().getColumn(3).setCellRenderer(cr);
		table.getColumnModel().getColumn(3).setCellEditor(ce);
		
		return table;

	}


	
	public class DoubleCellEditor  extends AbstractCellEditor  implements TableCellEditor{
		
		DoubleTextField field=null;

		
		public DoubleCellEditor(){
			
			
			field=new DoubleTextField();
			field.setBorder(null);
			field.setBackground(Color.WHITE);
			
		}

		@Override
		public Object getCellEditorValue() {
			return new Double(field.getvalue());
		}

		
		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			
			Double content=(Double)value;
			field.setText(content);

			return field;
		}
		
		
		
		
		
	}
	
	public class DoubleCellRenderer implements TableCellRenderer {
		
		DoubleTextField field=null;
		
		public DoubleCellRenderer(){
			
			super();
			field=new DoubleTextField();
			field.setBorder(null);
			
			
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

			Double content=(Double) value;
			field.setText(content);
			

	        if (column==0 && isSelected)
	        {
	        	field.setBackground(table.getSelectionBackground());
	            field.setForeground(table.getSelectionForeground());
	        }
	        else
	        {
	        	field.setBackground(table.getBackground());
	        	field.setForeground(table.getForeground());
	        }


			return field;
		}
		
		


	}

	public LinkedList getList() {
		// TODO Auto-generated method stub
		return newLinkedList;
	}
	

	
	

	private void openPopup(MouseEvent arg0) {
		
	      if (arg0.isPopupTrigger()) {
	    	  
	    	  popup_row=table.rowAtPoint(arg0.getPoint());
	    	  
	       	  
	          popupMenu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
	        }
		
	}
	
	private void deleteRow(int popup_row) {


		
		if(popup_row>=0){
			
			((DefaultTableModel)table.getModel()).removeRow(popup_row);
			
		}
		
		
	}
	

	private void insertRowBefore(int popup_row2) {

		if(popup_row>=0){
			
			if(popup_row>=0){
				
				Vector v_row=getEmptyRow();
				
				((DefaultTableModel)table.getModel()).insertRow(popup_row,v_row);
				
			}
		}
	}

	private Vector getEmptyRow() {
		
		Vector empty_row=new Vector();
		empty_row.add(new Integer(0));
		empty_row.add(new Double(0));
		empty_row.add(new Double(0));
		empty_row.add(new Double(0));

		return empty_row;
	}

	private void insertRowAfter(int popup_row2) {

		if(popup_row>=0){
			
			Vector v_row=getEmptyRow();
			
			((DefaultTableModel)table.getModel()).insertRow(popup_row+1,v_row);
		}
	}
	
	
	

}
