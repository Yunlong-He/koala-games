package com.koala.cwt.grid;

import java.util.EventListener;

public interface GridListener extends EventListener {

    public void cubicClicked(Grid grid, GridEvent evt);

}
