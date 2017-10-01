package com.koala.cwt.grid;

/**
 * Class GridModel represents one rectangle grid, each point has it's value
 */
public interface GridModel {

    public void fireDataChanged();

	public void addGridModelListener(GridModelListener l);

	public void removeGridModelListener(GridModelListener l);

	public int getColumns();

	public int getRows();

	public Object getObject(int col, int row);

	public void setObject(int col, int row, Object c);

	public boolean isEmpty(int col, int row);

	public void reset(int c, int r);

}
