package org.mql.autocompletionplugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class DocumentTypingListener implements DocumentListener {
	private Node root;
	private Pattern p = Pattern.compile("\\b\\w*\\b");
	
	public DocumentTypingListener(Node root) {
		this.root = root;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		 try {
			Document doc = e.getDocument();
			String text = doc.getText(0, e.getOffset() + e.getLength());
			// 1 = get the offset of the change
			// 2 = get the offset of last word boundry \b from 0 to change offset
			// get the word from 1 to 2
			// get suggestions
			Matcher m = p.matcher(text);
			String typedWord = "";
			while(m.find()) {
				typedWord = m.group();
			}
			System.out.println("typedWord : " + typedWord);
			System.out.println("suggestions for typed word :");
			System.out.println(root.getSuggestions(typedWord));
			// i need to add new word after a suggestion is inserted or after each complete word \b
//			root.insert(typedWord);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}
