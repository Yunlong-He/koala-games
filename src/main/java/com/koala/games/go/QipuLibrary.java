package com.koala.games.go;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.jar.*;
import java.util.zip.*;
import java.util.*;


public final class QipuLibrary extends Dialog implements ActionListener{
    private Match match = null;
    private boolean submited;

	JButton btSubmit,btCancel;
    JTable table;
    DefaultTableModel dtm;

    public QipuLibrary(JFrame frm, String title) {
		super(frm,title,true);

		addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					submited = false;
					hide();
				}
			}
		);
		setResizable(true);

		dtm = new DefaultTableModel() {
		    public boolean isCellEditable(int c, int r) {
		        return false;
		    }
		};
//		String[] cols = {"Name","Age","weight"};

		dtm.addColumn("���");
		dtm.addColumn("�ļ���");
		dtm.addColumn("����");
		try {
    		String[] values = {"","",""};
            JarFile jarfile = new JarFile("dc.jar");
            ZipEntry indexFile = jarfile.getEntry("Mulu.txt");
            InputStream is = jarfile.getInputStream(indexFile);
            MyProps props = new MyProps();
            props.load(is);
            Enumeration keys = props.propertyNames();
            int i = 1;
            while(keys.hasMoreElements()) {
                values[0] = Integer.toString(i);
                values[1] = (String) keys.nextElement();
                values[2] = (String) props.get(values[1]);
    		    dtm.addRow(values);
    		    i++;
            }
            jarfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        catch(SecurityException se) {
            se.printStackTrace();
            System.exit(1);
        }

		table = new JTable(dtm);
      	JScrollPane scrollpane = new JScrollPane(table);

		btSubmit = new JButton("ȷ��");       
		btSubmit.addActionListener(this);
		btCancel = new JButton("ȡ��");
		btCancel.addActionListener(this);

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(btSubmit);
        p.add(btCancel);

		setBackground(Color.lightGray);
		setLayout(new BorderLayout());
        add("Center", scrollpane);
        add("South", p);

		setSize(500,400);
		/**
		 * initialize data
		 */
		submited = false;
		match = new Match();
    }

	public static Match getMatch(JFrame frm, String title){
		QipuLibrary md = new QipuLibrary(frm, title);
		md.show();
		if (md.submited)
			return md.match;
		else
			return null;
	}


	/**
	 * perform action fired by clicking "button" submit or "cancel"

	 */
    public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
		if (bt == btSubmit) {
            int[] indices = table.getSelectedRows();
            String filename = (String) dtm.getValueAt(indices[0], 1);
    		try {
                JarFile jarfile = new JarFile("dc.jar");
                ZipEntry indexFile = jarfile.getEntry(filename);
                InputStream is = jarfile.getInputStream(indexFile);
                match = SgfAdapter.readMatch(is);
                jarfile.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            catch(SecurityException se) {
                se.printStackTrace();
                System.exit(1);
            }

			submited = true;
			hide();
		} else if (bt == btCancel) {
			submited = false;
			hide();
		}
    }

}
