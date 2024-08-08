package org.mql.autocompletionplugin;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Vector;
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
	private String prefix;

	public DocumentTypingListener(Node root, JTextPane textPane) {
		this.prefix = "";
		this.root = root;
		suggestionsMenu = new JPopupMenu();
//		suggestionsMenu.addKeyListener(new JPopupMenuKeyListener(suggestionsMenu));
		this.textPane = textPane;
		textPaneKeyListener = new JTextPaneKeyListener(suggestionsMenu, textPane);
		textPane.addKeyListener(textPaneKeyListener);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			Document doc = e.getDocument();
			String text = doc.getText(0, e.getOffset() + e.getLength());
			String insertion = doc.getText(e.getOffset(), e.getLength());
			char lastCharacter = insertion.charAt(insertion.length() - 1);
			if (Character.isLetterOrDigit(lastCharacter) || lastCharacter == '_') {

				Matcher m = p.matcher(text);
				prefix = "";
				while (m.find()) {
					prefix = m.group();
				}
				System.out.println("typedWord : " + prefix);
				System.out.println("suggestions for typed word :");
				List<String> suggestions = root.getSuggestions(prefix);
				System.out.println(root);
				System.out.println(suggestions);

				displaySuggestions(suggestions, e.getOffset() + e.getLength());

			} else {
				suggestionsMenu.setVisible(false);
				// re insert file after each word
				Matcher m = p.matcher(text);
				while (m.find()) {
					root.insert(m.group());
				}
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			Document doc = e.getDocument();
			String text = doc.getText(0, e.getOffset() - e.getLength() + 1);

			// test if cursor is on a word not on a space
			String insertion = doc.getText(e.getOffset() - e.getLength(), e.getLength());
			char lastCharacter = insertion.charAt(insertion.length() - 1);
			if (Character.isLetterOrDigit(lastCharacter) || lastCharacter == '_') {
				Matcher m = p.matcher(text);
				prefix = "";
				while (m.find()) {
					prefix = m.group();
				}
				System.out.println("typedWord : " + prefix);
				System.out.println("suggestions for typed word :");
				List<String> suggestions = root.getSuggestions(prefix);
				System.out.println(root);
				System.out.println(suggestions);
				displaySuggestions(suggestions, e.getOffset() - e.getLength() + 1);
			}
			// must be inserted without suggestions
//			else if(lastCharacter == '(') {
//				List<String> suggestions = new Vector<String>();
//				suggestions.add(")");
//				displaySuggestions(suggestions, e.getOffset() - e.getLength() + 1);
//			}
			else {
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

			textPaneKeyListener.setPrefix(prefix);
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

	private int getCaretOffset() {
		Caret caret = textPane.getCaret();
		return caret.getDot();
	}
}
