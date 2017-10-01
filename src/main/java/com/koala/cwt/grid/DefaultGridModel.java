package com.koala.cwt.grid;

import javax.swing.event.EventListenerList;

public class DefaultGridModel implements GridModel {
	protected Object blocks[][];
	protected int cols,rows;

    /**
     * Listeners.
     */
   protected EventListenerList listenerList = new EventListenerList();

	public DefaultGridModel(int c, int r){
		cols = c;
		rows = r;

		blocks = new Object[cols][rows];
		for (int i = 0 ; i < cols ; i++)
		for (int j = 0 ; j < rows ; j++) {
			blocks[i][j] = null;
		}
	}

	/**
	 * AbstractGridModel subclasses must call this method <b>after</b>
	 * one or more chessmen removed.
	 *
	 * @see EventListenerList
     */
    public void fireDataChanged(){
		Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
	   		if (listeners[i] == GridModelListener.class) {
				((GridModelListener) listeners[i+1]).gridDataChanged();
	   		}
		}
	}

	/**
	 * Add a listener to the Grid that's notified each time a change
	 * to the data model occurs.
	 * @param l the GridDataListener
	 */
	public void addGridModelListener(GridModelListener l) {
		listenerList.add(GridModelListener.class, l);
    }

    /**
	 * Remove a listener from the Grid that's notified each time a
	 * change to the data model occurs.
	 * @param l the GridDataListener
	 */
	public void removeGridModelListener(GridModelListener l) {
		listenerList.remove(GridModelListener.class, l);
    }

	public int getColumns() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public Object getObject(int x, int y) {
		return blocks[x][y];
	}

	public void setObject(int x, int y, Object obj) {
		blocks[x][y] = obj;
		fireDataChanged();
    }

    public boolean isEmpty(int x, int y) {
        if (blocks[x][y] == null) return true;
        else return false;
    }

    public void reset(int c, int r){
		cols = c;
		rows = r;

		blocks = new Object[cols][rows];
		for (int i = 0 ; i < cols ; i++)
		for (int j = 0 ; j < rows ; j++) {
			blocks[i][j] = null;
		}
	}

}

