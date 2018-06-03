package br.com.upf.sd.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
 
public class CheckboxList {
 
	public static void main(String[] args) {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    Table table = new Table(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	    for (int i = 0; i < 12; i++) {
	      TableItem item = new TableItem(table, SWT.NONE);
	      item.setText("Item " + i);
	    }
	    table.setSize(100, 100);

	    shell.setSize(200, 200);
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	  }
}