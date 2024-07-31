package org.mql.autocompletionplugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class DocumentTypingListener implements DocumentListener {
	private Node root;
	private Pattern p = Pattern.compile("\\b\\w+\\b");

	public DocumentTypingListener(Node root) {
		this.root = root;
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
				System.out.println(root.getSuggestions(typedWord));
				// i need to add new word after a suggestion is inserted or after each complete
				// word \b
				// root.insert(typedWord);
			}
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			Document doc = e.getDocument();
			String text = doc.getText(0, e.getOffset() - e.getLength());
			System.out.println("text from 0 to change :");
			System.out.println(text);

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
				System.out.println(root.getSuggestions(typedWord));
				//

				// i need to add new word after a suggestion is inserted or after each complete
				// word \b
				// root.insert(typedWord);
			}

		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}
