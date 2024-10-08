package org.mql.autocompletionplugin;

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

public class JTextPaneKeyListener implements KeyListener {
	private AutoDismissPopupMenu popupMenu;
	private int selected;
	private Document document;
	private String prefix;
	private JTextPane textPane;

	public JTextPaneKeyListener(AutoDismissPopupMenu popupMenu, JTextPane textPane) {
		this.prefix = "";
		this.textPane = textPane;
		this.document = textPane.getDocument();
		this.popupMenu = popupMenu;
		selected = 0;
		this.popupMenu.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				selected = 0;
				// selected the first element
				((JMenuItem) popupMenu.getComponent(0)).setOpaque(true);
				((JMenuItem) popupMenu.getComponent(0)).setBackground(new Color(192, 221, 251));
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		// if the popupmenu is visible dont use arrow mouvement in the doc
		// if popup is visible that mean there is items
		if (popupMenu.isVisible()) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				e.consume(); // Prevent default tab behaviors
				if (selected > 0) {
					selected--;
					selectItem(selected);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				e.consume(); // Prevent default tab behaviors
				if (selected < popupMenu.getComponentCount() - 1) {
					selected++;
					selectItem(selected);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();
				String suggestion = ((JMenuItem) popupMenu.getComponent(selected)).getText();
				try {
					// insert only the part that is not already written
					suggestion = suggestion.substring(prefix.length());
					document.insertString(getCaretOffset(), suggestion, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			} else {
				// if menu is visible and other arrows are clicked hide the suggestions
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_LEFT
						|| e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_TAB
						|| (!Character.isLetterOrDigit(e.getKeyChar())
								&& !(e.getKeyCode() == KeyEvent.VK_UNDERSCORE))) {
					System.out.println("hide suggestions @@@@@@@@@@@@@");
					popupMenu.setVisible(false);
				}
			}

		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// ( { [ insertions
		try {
			// in key released to wait the typing of the char
			int offset = getCaretOffset();
			if (e.getKeyChar() == '(') {
				e.consume();
				document.insertString(offset, ")", null);
				setCaretOffset(offset);
			} else if (e.getKeyChar() == '{') {
				e.consume();
				document.insertString(offset , "}", null);
				setCaretOffset(offset);
			} else if (e.getKeyChar() == '[') {
				e.consume();
				document.insertString(offset, "]", null);
				setCaretOffset(offset);
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	private void selectItem(int selected) {
//		SwingUtilities.invokeLater(() -> {
		popupMenu.getSelectionModel().setSelectedIndex(selected);
		System.out.println("selected : " + ((JMenuItem) popupMenu.getComponent(selected)).getText());
		int itemCount = popupMenu.getComponentCount();
		for (int item = 0; item < itemCount; item++) {
			JMenuItem menuItem = (JMenuItem) popupMenu.getComponent(item);
			if (item == selected) {
				menuItem.setOpaque(true);
				menuItem.setBackground(new Color(192, 221, 251));
			} else {
				menuItem.setOpaque(true);
				menuItem.setBackground(UIManager.getColor("MenuItem.background"));
			}
		}
		popupMenu.repaint();
//		});
	}

	private int getCaretOffset() {
		Caret caret = textPane.getCaret();
		return caret.getDot();
	}

	private void setCaretOffset(int offset) {
		Caret caret = textPane.getCaret();
		caret.setDot(offset);
		;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
