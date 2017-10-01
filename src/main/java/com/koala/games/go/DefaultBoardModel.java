package com.koala.games.go;


import java.awt.Point;
import java.util.Vector;

import com.koala.cwt.grid.DefaultGridModel;
import com.koala.cwt.grid.GridModel;


public class DefaultBoardModel implements GoBoardModel{

    private DefaultGridModel grids;

	private Vector moves_put;
    private Vector moves_remove;
    
    private int cur_col, cur_row;
    
	public DefaultBoardModel(int bsize) {
		if ((bsize < MIN_BOARD_SIZE) || (bsize > MAX_BOARD_SIZE)) {
			bsize = DEFAULT_BOARD_SIZE;
		}

        System.out.println("board size is " + bsize);
        grids = new DefaultGridModel(bsize, bsize);

 		for (int i = 0 ; i < getBoardSize() ; i++)
  		for (int j = 0 ; j < getBoardSize() ; j++){
            grids.setObject(i, j, new GoPoint());
		}

		moves_put = new Vector();
        moves_remove = new Vector();
        
        setCurrent(-1, -1);
    }
    
//------------------ Board View Interface ------------------------------

    public GridModel getGridModel() {
        return grids;
    }

	/**
	 * property get board size
	 */
	public int getBoardSize(){
		return grids.getRows();
	}

	/**
	 * property get point
	 */
	public GoPoint getPoint(int col, int row){
		return (GoPoint) grids.getObject(col, row);
	}

	public int getMovementCount(){
		return moves_put.size();
	}

    public Movement createMatchRecord() {
        Movement root = new Movement(-1, -1, GoPlayer.BLACK);
        Movement cur = root;
        for (int i = 0; i < moves_put.size(); i++) {
            Movement move = (Movement) moves_put.elementAt(i);
            Movement c = new Movement(move.col, move.row, move.player);
            cur.add(c);
            cur = c;
        }       
        return root; 
    }

    public Movement getLastMovement(){
        if (moves_put.size() == 0)
            return null;

        return (Movement) moves_put.lastElement();
    }
    

    /**
     * clear all point
     */
    public void reset(){
        for (int i = 0 ; i < getBoardSize() ; i++)
        for (int j = 0 ; j < getBoardSize() ; j++){
            getPoint(i, j).setState(GoPoint.EMPTY);
            getPoint(i, j).setNumber(GoPoint.NONE);
            getPoint(i, j).setHolder(GoPlayer.UNKNOWN);
            getPoint(i, j).setSigned(false);
        }
        moves_put.removeAllElements();
        moves_remove.removeAllElements();
        grids.fireDataChanged();
    }

    public void restore() {
        while (moves_remove.size() > 0) {
            undo();
        }
    }


    /**
     * clear all point
     */
    public void reset(int bsize){
		if ((bsize < MIN_BOARD_SIZE) || (bsize > MAX_BOARD_SIZE)) {
			bsize = DEFAULT_BOARD_SIZE;
		}

        grids.reset(bsize, bsize);

 		for (int i = 0 ; i < getBoardSize() ; i++)
  		for (int j = 0 ; j < getBoardSize() ; j++){
            grids.setObject(i, j, new GoPoint());
		}

        moves_put.removeAllElements();
        moves_remove.removeAllElements();
        grids.fireDataChanged();
    }

    /**
     * clear domain mark so that no point show mark
     */
    public void clearDomainMarks(){
        for (int i = 0 ; i < getBoardSize(); i++)
        for (int j = 0 ; j < getBoardSize(); j++){
            getPoint(i, j).setHolder(GoPlayer.UNKNOWN);
        }
    }
    
   	public void setTip(int col, int row, int tip) {
   		getPoint(col, row).setTip(tip);
        grids.fireDataChanged();
    }
	
    public boolean isCurrent(int col, int row) {
        if ((col == cur_col) && (row == cur_row)) {
            return true;
        }
        
        return false;
    }


//------------------ Operation Interface ------------------------------

	public void undo(){
	   
        if (moves_remove.size() > 0) {
            Movement move = (Movement) moves_remove.lastElement();
            
            for (int i = 0 ; i < move.captiveCount() ; i++){
                Movement.Captive p = move.captiveAt(i);
                if (move.player == GoPlayer.BLACK) {
                    getPoint(p.col, p.row).setState(GoPoint.BLACK);
                }
                else {
                    getPoint(p.col, p.row).setState(GoPoint.WHITE);
                }
                getPoint(p.col, p.row).setNumber(p.number);
            }

            moves_remove.removeElement(move);
        }
        else {
            Movement move = (Movement) moves_put.lastElement();
            if (move == null) return;
    
            getPoint(move.col, move.row).setState(GoPoint.EMPTY);
    
            for (int i = 0 ; i < move.captiveCount() ; i++){
                Movement.Captive p = move.captiveAt(i);
                if (move.player == GoPlayer.BLACK) {
                    getPoint(p.col, p.row).setState(GoPoint.WHITE);
                }
                else {
                    getPoint(p.col, p.row).setState(GoPoint.BLACK);
                }
            }
            moves_put.removeElement(move);
            
            if (moves_put.size() > 0) {
                move = (Movement) moves_put.lastElement();
                setCurrent(move.row, move.col);
            }
            else {
                setCurrent(-1, -1);
            }
        }
        grids.fireDataChanged();

	}

	/**
	 * divide domain to black side and white side
	 * empty body blong to it's closest neibor;
	 * if one empty body's neibor don't have same color, it's in neutrality
	 */
    public void divideDomain(){
    	for (int i = 0 ; i < getBoardSize() ; i++)
  		for (int j = 0 ; j < getBoardSize() ; j++){
			getPoint(i, j).setHolder(GoPlayer.UNKNOWN);
		}

   		for (int i = 0 ; i < getBoardSize() ; i++)
  		for (int j = 0 ; j < getBoardSize() ; j++){
            if (getPoint(i, j).getHolder() == GoPlayer.UNKNOWN){
            	if (getPoint(i, j).getState() == GoPoint.BLACK){
                    getPoint(i, j).setHolder(GoPlayer.BLACK);
            	}
            	else if (getPoint(i, j).getState() == GoPoint.WHITE){
                    getPoint(i, j).setHolder(GoPlayer.WHITE);
            	}
            	else {
            		Vector v = getBody(i,j);
            		findSpaceHolder(v);
            	}
            }
		}
    }

    public void performRemove(int x, int y) throws GoException{

		if (!validatePoint(x, y)){
            throw new GoException("Invalid point");
        }

		if (getPoint(x, y).getState() == GoPoint.EMPTY){
            throw new GoException("No chessman to remove");
        }

        Movement move = new Movement(x, y, getPoint(x, y).getState());
		removeBody(x, y, move);
		
		moves_remove.addElement(move);
		
		grids.fireDataChanged();
	}

    public void forcePut(int x, int y, int state) {
        getPoint(x, y).setState(state);
//        System.out.println("force put [" + x + "," + y + "]");
        grids.fireDataChanged();
    }
    
	public void performPut(int x, int y, int player) throws GoException{
        if (moves_remove.size() > 0) {
            throw new GoException("current state: removing");
        }
        
		if (!validatePoint(x, y)){
    		throw new GoException("invalid point!");
		}

        if (getPoint(x, y).getState() != GoPoint.EMPTY){
            throw new GoException("There is already one chessman");
        }
        
        // test put it and check it
        Movement move = new Movement(x, y, player);

        boolean maybeProhibited = false;
		if (moves_put.size() > 0){
            Movement last = (Movement) moves_put.lastElement();
			if (last.captiveCount() == 1){
                Movement.Captive cp = last.captiveAt(0);
				if((cp.col == x) && (cp.row == y)){
					maybeProhibited = true;
				}
			}
		}

        //Ԥ�����ã������ɹ�����ȥ
		if (player == GoPlayer.BLACK) {
	       	getPoint(x, y).setState(GoPoint.BLACK);
		} else {
	       	getPoint(x, y).setState(GoPoint.WHITE);
		}


		//�ж��ڽ������
		//��ʼʱpHand.life=4,������Χ�ĸ���Ϊ�յ�
		//����ɸ���pHand.life�Լ�pHand.rub ���ýٵ�״̬
       	int a[] = {x - 1 , x + 1 , x , x};
       	int b[] = {y , y , y - 1 , y + 1};

		int tmpLife=0;
    	for (int i = 0 ; i < 4 ; i++){
       		if (!validatePoint(a[i], b[i]))
        		continue;
		   	else {
		       	int xx = a[i];
				int yy = b[i];
	    		if (getPoint(xx, yy).getState() == GoPoint.EMPTY){
		      		//��Χ���пյ�,tmpLife!=0,��������
		       		tmpLife = tmpLife + 1;
		      	}else {
		        	//����з����������������
	           		if (GoPoint.isEnemy(getPoint(x, y), getPoint(xx, yy))){
	           			if (isDead(xx,yy)){
	           				if (maybeProhibited){
	           					if (isSingle(xx,yy)){
		      	       				getPoint(x, y).setState(GoPoint.EMPTY);
					    			throw new GoException("Prohibited put");
	                    		}
	                    	}
	                       	removeBody(xx,yy, move);
			             	tmpLife = tmpLife + 1;
				       	}
					}
				}
	       	}
		}

       	//����������ӣ��������Ӻ�Ϊ���ӣ���˴���������
        if (tmpLife == 0){
            if (isDead(x, y)){	//�����Ϊ���ӣ���������
                getPoint(x, y).setState(GoPoint.EMPTY);
                throw new GoException("Prohibited put");
            }
		}

		moves_put.addElement(move);
        getPoint(x, y).setNumber(moves_put.size());
        setCurrent(x, y);
		grids.fireDataChanged();
	}
/*
	public void setNumber(int col, int row, int number) {
		getPoint(col, row).setSerialNumber(number);
		grids.fireDataChanged();		
	}

	
	public void showNumbers(int startIndex, int destIndex, int startNumber) {
		for (int i = startIndex; i <= destIndex; i++) {
			Movement move = (Movement) moves_put.elementAt(i);
			getPoint(move.col, move.row).setSerialNumber(i + startNumber);
		}
		grids.fireDataChanged();		
	}
	
	public void hideNumbers() {
        for (int i = 0 ; i < getBoardSize() ; i++)
        for (int j = 0 ; j < getBoardSize() ; j++){
            getPoint(i, j).setSerialNumber(GoPoint.NONE);
        }
	}
*/	
    protected void removeBody(int x, int y, Movement move){
       	int a[] = {x - 1 , x + 1 , x , x};
       	int b[] = {y , y , y - 1 , y + 1};

    	int curState = getPoint(x, y).getState();
       	getPoint(x, y).setState(GoPoint.EMPTY);
        if (move != null) move.addCaptive(x, y, getPoint(x,y).getNumber());

       	for (int i = 0 ; i < 4 ; i++){
            if (!validatePoint(a[i], b[i]))
                continue;
            else {
	       	    int xx = a[i];
		        int yy = b[i];
            	if (getPoint(xx, yy).getState() == curState){
                    removeBody(xx, yy, move);
                }
        	}
		} // end for
	}

	/**
	 * get owner of the body
	 * @v: points in the body
	 */
	protected void findSpaceHolder(Vector v){
		int holder = GoPlayer.UNKNOWN;

		for (int i = 0 ; i < v.size() ; i++){
			Point p = (Point) v.elementAt(i);
			int x = p.x;
			int y = p.y;
       	    int a[] = {x - 1 , x + 1 , x , x};
       		int b[] = {y , y , y - 1 , y + 1};

       		for (int j = 0 ; j < 4 ; j++){
                if (!validatePoint(a[j], b[j]))
                    continue;
   				else {
      				int xx = a[j];
	       			int yy = b[j];
       				if (getPoint(xx, yy).getState() == GoPoint.BLACK){
                		if (holder == GoPlayer.UNKNOWN) {
                            holder = GoPlayer.BLACK;
                		}
                        else if (holder == GoPlayer.WHITE){
                            holder = GoPlayer.BOTH;
                			break;
                		}
                	}
            		if (getPoint(xx, yy).getState() == GoPoint.WHITE){
                        if (holder == GoPlayer.UNKNOWN){
                            holder = GoPlayer.WHITE;
                		}
                        else if (holder == GoPlayer.BLACK){
                			holder = GoPlayer.BOTH;
                			break;
                		}
                	}
               	}
        	}
		} // end for

		for (int i = 0 ; i < v.size() ; i++) {
			Point p = (Point) v.elementAt(i);
			getPoint(p.x, p.y).setHolder(holder);
		}
	}

	/**
	 * get chessmen with same color and adjacent to each other
	 * @v: points in the body
	 */
	protected Vector getBody(int x, int y) {
		clearAllSigns();
		Vector v = new Vector();
		createBody(x, y, v);
		return v;
	}
	

	/**
	 * get chessmen with same color and adjacent to each other
	 * @v: points in the body

	 * @see #getBody(int x, in y)#
	 */
	protected void createBody(int x, int y, Vector v){
       	int a[] = {x - 1 , x + 1 , x , x};
       	int b[] = {y , y , y - 1 , y + 1};

        int curState = getPoint(x, y).getState();
		if (v != null) v.addElement(new Point(x, y));

		getPoint(x, y).setSigned(true);

       	for (int i = 0 ; i < 4 ; i++){
            if (!validatePoint(a[i], b[i]))
                continue;
            else {
	   			int xx = a[i];
	      		int yy = b[i];
	       		if (getPoint(xx, yy).isSigned()) continue;
       			if (getPoint(xx, yy).getState() == curState){
           			createBody(xx, yy, v);
           		}
      		}
		} // end for
	}

	/**
	 * check whether the chessman in position x,y have no neibor with the same color
	 */
    protected boolean isSingle(int x, int y){
    	int a[] = {x - 1 , x + 1 , x , x};
    	int b[] = {y , y , y - 1 , y + 1};

        for (int i = 0 ; i < 4 ; i++){
            if (!validatePoint(a[i], b[i]))
                continue;
            int xx = a[i];
       		int yy = b[i];
       		if (!GoPoint.isEnemy(getPoint(x, y), getPoint(xx, yy))) return false;
       	}
       	return true;
    }

	/**
	 * check whether the chessman in position x,y be in "dead" state
	 */
    protected boolean isDead(int x, int y){
    	clearAllSigns();
    	return noLife(x, y);
	}

	/**
	 * recursively check whether the chessmen are in "dead" state
	 */
    protected boolean noLife(int x, int y){
    	int a[] = {x - 1 , x + 1 , x , x};
    	int b[] = {y , y , y - 1 , y + 1};
        getPoint(x, y).setSigned(true);

    	for (int i = 0 ; i < 4 ; i++){
       		if (!validatePoint(a[i], b[i]))
        		continue;
       		else {
		   		int xx = a[i];
		      	int yy = b[i];
	       		if (getPoint(xx, yy).isSigned()) continue;
	           	if (getPoint(xx, yy).getState() == GoPoint.EMPTY){
	           		return false;
	           	}
	           	if (getPoint(xx, yy).getState() == getPoint(x, y).getState())
	           		if (!noLife(a[i], b[i]))
	           			return false;
		    }
		}
		return true;
    }

    private void setCurrent(int c, int r) {
        if ((cur_col > 0) && (cur_row > 0)) {
            getPoint(cur_col, cur_row).setStyle(GoPoint.GENERAL);
        }
        if ((c > 0) && (r > 0)) {
            getPoint(c, r).setStyle(GoPoint.HIGHLIGHT);
        }
        cur_col = c;
        cur_row = r;
    }

	/**
	 * clear signs
	 */
	private void clearAllSigns(){
        for (int i = 0 ; i < getBoardSize(); i++)
        for (int j = 0 ; j < getBoardSize(); j++){
            getPoint(i, j).setSigned(false);
        }
    }

    private boolean validatePoint(int col, int row) {
        if ((col < 0) || (col >= getBoardSize()) || (row < 0) || (row >= getBoardSize()))
            return false;
        else
            return true;
    }

	/**
	 * save qipu
	 *	<chessman>
	 *		<color>black</color>
	 *		<x>13</x>
	 *		<y>3</y>
	 *	</chessman>
	 * /
	public String getActionRecords() {
		StringBuffer records = new StringBuffer();
		records.appends("<records>\n");
		for (int i = 0; i < moves_put.size(); i++) {
			GoBoardAction action = (GoBoardAction) moves_put.elementAt(i);
			records.appends("\t<chessman>\n");
			records.appends("\t\t<color>");
			if (action.getSource() == GoBoardAction.SOURCE_BLACK) {
				records.appends("black");
			}
			else {
				records.appends("white");
			}
			records.appends("</color>\n");
			records.appends("\t\t<x>" + action.getX() + "</x>\n");
			records.appends("\t\t<y>" + action.getY() + "</y>\n");
			records.appends("\t</chessman>\n");
		}
		records.appends("</records>\n");
		return records.toString();
	}

*/

}

