package org.mql.autocompletionplugin;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

public class DocumentTypingListener implements DocumentListener {
	private Node root;
	private Pattern p = Pattern.compile("\\b\\w+\\b");
	private JPopupMenu suggestionsMenu;
	private JTextPane textPane;
	private JTextPaneKeyListener textPaneKeyListener;

	public DocumentTypingListener(Node root, JTextPane textPane) {
		this.root = root;
		suggestionsMenu = new JPopupMenu();
//		suggestionsMenu.addKeyListener(new JPopupMenuKeyListener(suggestionsMenu));
		this.textPane = textPane;
		textPaneKeyListener = new JTextPaneKeyListener(suggestionsMenu,textPane.getDocument());
		textPane.addKeyListener(textPaneKeyListener);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			Document doc = e.getDocument();
			String text = doc.getText(0, e.getOffset() + e.getLength());
			String insertion = doc.getText(e.getOffset(), e.getLength());
			char c = insertion.charAt(insertion.length() - 1);
			//
			if (c != ' ') {

				Matcher m = p.matcher(text);
				String typedWord = "";
				while (m.find()) {
					typedWord = m.group();
				}
				System.out.println("typedWord : " + typedWord);
				System.out.println("suggestions for typed word :");
				List<String> suggestions = root.getSuggestions(typedWord);
				System.out.println(suggestions);
				// JPopMenu
//				suggestionsMenu.setVisible(false);
				displaySuggestions(suggestions, e.getOffset());

				// i need to add new word after a suggestion is inserted or after each complete
				// word \b
				// root.insert(typedWord);
			} else {
				suggestionsMenu.setVisible(false);
			}

		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	// TODO when removing if there is only one character left i get no suggestions
	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			Document doc = e.getDocument();
			String text = doc.getText(0, e.getOffset() - e.getLength() + 1);

			// test if cursor is on a word not on a space
			String insertion = doc.getText(e.getOffset() - e.getLength(), e.getLength());
			char c = insertion.charAt(insertion.length() - 1);
			if (c != ' ') {
				Matcher m = p.matcher(text);
				String typedWord = "";
				while (m.find()) {
					typedWord = m.group();
				}
				System.out.println("typedWord : " + typedWord);
				System.out.println("suggestions for typed word :");
				List<String> suggestions = root.getSuggestions(typedWord);
				System.out.println(suggestions);
				//
//				suggestionsMenu.setVisible(false);
				displaySuggestions(suggestions, e.getOffset());

				// i need to add new word after a suggestion is inserted or after each complete
				// word \b
				// root.insert(typedWord);
			} else {
				suggestionsMenu.setVisible(false);
			}

		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	private void displaySuggestions(List<String> suggestions, int offset) {
		suggestionsMenu.setVisible(false);
		suggestionsMenu.removeAll();
		if (!suggestions.isEmpty()) {
			textPaneKeyListener.setOffset(offset);
			for (String suggestion : suggestions) {
				suggestionsMenu.add(new JMenuItem(suggestion));
			}
			suggestionsMenu.setLocation(getCaretPosition());
			suggestionsMenu.setVisible(true);
			

		} else {
			suggestionsMenu.setVisible(false);
		}
	}

	private Point getCaretPosition() {
		Caret caret = textPane.getCaret();
		Point p = caret.getMagicCaretPosition();
		p.x += textPane.getLocationOnScreen().x;
		p.y += textPane.getLocationOnScreen().y + textPane.getFont().getSize2D();
		return p;
	}
}
