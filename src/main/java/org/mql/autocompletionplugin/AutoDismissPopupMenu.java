package org.mql.autocompletionplugin;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class AutoDismissPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	public AutoDismissPopupMenu() {
        super();
        
        addFocusListener(new FocusAdapter() {
		
            @Override
            public void focusLost(FocusEvent e) {
            	System.out.println("focus lost");
                setVisible(false);
            }
        });
        
    }

}
