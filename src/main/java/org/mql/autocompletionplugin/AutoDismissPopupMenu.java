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
                setVisible(false);
            }
        });

        // Add a mouse listener to check for clicks outside the menu
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (!contains(e.getPoint())) {
                    setVisible(false);
                }
            }
        });
    }

}
