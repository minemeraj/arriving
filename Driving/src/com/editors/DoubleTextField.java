package com.editors;
/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


public class DoubleTextField extends JTextField{
	
	
	private DecimalFormat dfc;

	
	
	public DoubleTextField(int cols) {
		super(cols);
		setFormat();

	}
	
	private void setFormat() {
		DecimalFormatSymbols dfs=new DecimalFormatSymbols(Locale.UK);
		dfc=new DecimalFormat(".#########");
		dfc.setDecimalFormatSymbols(dfs);
		
	}

	public DoubleTextField() {
			super();
			setFormat();
			
		}
	@Override
	protected Document createDefaultModel() {
		return new IntegerDocument();
	}

	static class IntegerDocument extends PlainDocument {
		
		@Override
		public void insertString(int offs, String str, AttributeSet a) 
		throws BadLocationException {
			
			if (str == null) {
				return;
			}
			char[] upper = str.toCharArray();
			boolean isValid=true;
			
			for (int i = 0; i < upper.length; i++) {
				
				if(!Character.isDigit(upper[i]) && upper[i]!='-' && upper[i]!='.')
				{
					isValid=false;
					break;
				}
			
			}
			if(isValid)
				super.insertString(offs, new String(upper), a);
		}
	}
	
	public void setText(double d){
		
		setText(dfc.format(d));
	}
	
	public double getvalue(){
		
		String str=getText();
		if(str==null || str.length()==0)
			return 0;
		
		return Double.parseDouble(str);
		
	}

}
