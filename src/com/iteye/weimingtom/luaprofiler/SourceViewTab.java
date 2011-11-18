package com.iteye.weimingtom.luaprofiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * @author weimingtom
 *
 */
public class SourceViewTab extends Composite {
    private final static int MAX_WIDTH = 640;
    public transient Table table;
    private int lineno = 0;
    public transient Color textBGColor = new Color(Display.getCurrent(),
	    new RGB(0, 0xff, 0));

    private transient Image image = new Image(Display.getCurrent(),
	    SourceViewTab.class.getResourceAsStream("target.gif"));

    public SourceViewTab(final Composite composite) {
	super(composite, 0);
	setLayout(new GridLayout(1, false));
	table = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION
		| SWT.H_SCROLL | SWT.V_SCROLL);
	table.setLinesVisible(true);
	table.setHeaderVisible(true);
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
	data.heightHint = 200;
	table.setLayoutData(data);
	String[] titles = { " ", "Line", "Hit", "Description" };
	for (int i = 0; i < titles.length; i++) {
	    TableColumn column = new TableColumn(table, SWT.NONE);
	    column.setText(titles[i]);
	}
	for (int i = 0; i < titles.length; i++) {
	    table.getColumn(i).pack();
	}
	if (table.getColumn(titles.length - 1).getWidth() < MAX_WIDTH) {
	    table.getColumn(titles.length - 1).setWidth(MAX_WIDTH);
	}
	reset();
    }
    
    public void reset() {
	//table.clearAll();
	table.removeAll();
	openFile();
	if (table.getItemCount() > 0) {
	    table.getItem(0).setImage(0, image);
	}
    }
    
    public void openFile() {
	if (GUIMain.scriptFileName == null) {
	    return;
	}
	lineno = 0;
	FileReader reader = null;
	BufferedReader br = null;
	try {
	    reader = new FileReader(GUIMain.scriptFileName);
	    br = new BufferedReader(reader);
	    while (true) {
		String str = br.readLine();
		if (str == null) {
		    break;
		}
		appendLine(str);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public void appendLine(String str) {
	// System.out.println(str);
	TableItem item;
	if(lineno >= table.getItemCount()) {
	    item = new TableItem(table, SWT.NONE);
	} else {
	    item = table.getItem(lineno);
	}
	// item.setImage(0, image);
	item.setText(1, new Integer(lineno).toString());
	item.setText(2, new Integer(0).toString());
	item.setText(3, str);
	lineno++;
    }

    public void gotoLine(String fileName, int line) {
	//System.out.println(fileName + ", " + line);
	TableItem[] items = table.getItems();
	if (line < 0 || items == null || items.length < line) {
	    return;
	}
	for (int i = 0; i < items.length; i++) {
	    items[i].setImage(0, null);
	}
	items[line].setImage(0, image);
	items[line].setBackground(textBGColor);
    }

    public void updateHit(List linenoList) {
	TableItem[] items = table.getItems();
	for (int i = 0; i < items.length; i++) {
	    items[i].setText(2, new Integer(0).toString());
	}
	Iterator it = linenoList.iterator();
	while (it.hasNext()) {
	    Integer line = (Integer) it.next();
	    //System.out.println(line);
	    IncreaseHit(line);
	}
    }

    public void IncreaseHit(int line) {
	TableItem[] items = table.getItems();
	if (line < 0 || items == null || items.length < line) {
	    return;
	}
	TableItem item = items[line];
	item.setBackground(textBGColor);
	String str = item.getText(2);
	int hit = Integer.parseInt(str);
	hit++;
	item.setText(2, new Integer(hit).toString());
    }
}
