package org.mql.autocompletionplugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.documentHandlers.OpenDocumentsHandler;
import org.mql.jcodeeditor.highlighting.Token;
import org.mql.jcodeeditor.highlighting.TokenType;

public class Autocompleter implements OpenDocumentsHandler {
	private List<StyledDocument> openDocuments;
	private Node root;

	public Autocompleter() {
		root = new Node();
		System.out.println("autocompleter");
	}

	@Override
	public void setDocuments(List<StyledDocument> openDocuments) {
		this.openDocuments = openDocuments;
		// TODO create a set of words
		for (StyledDocument d : openDocuments) {
			// TODO the listener should take the collection of words to add to it new words
			// and test
			try {
				String text = d.getText(0, d.getLength());
//				words.addAll(Arrays.asList());
				List<String> words = getWords(text);
				for (String w : words) {
					System.out.println(w);
					if (w.length() > 0)
						root.addChild(w.charAt(0), new Node(w.substring(1)));
				}

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		System.out.println(root);

	}

	@Override
	public void execute() {
		// TODO i need to create a DocumentListener and add it to docs here
//		for (StyledDocument d : openDocuments) {
//			// TODO the listener should take the collection of words to add to it new words and test 
//			d.addDocumentListener(null);
//		}
	}

	@Override
	public void addDocument(StyledDocument doc) {
		System.out.println("add document");
		openDocuments.add(doc);
		try {
			String text = doc.getText(0, doc.getLength());
			System.out.println("text : "+ text);
			List<String> words = getWords(text);
			System.out.println("words : " + words);
			for (String w : words) {
				System.out.println(w);
				if (w.length() > 0)
					root.addChild(w.charAt(0), new Node(w.substring(1)));
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		System.out.println(root);

	}

	private List<String> getWords(String text) {
		Pattern p = Pattern.compile("\\b\\w*\\b");
		Matcher m = p.matcher(text);
		List<String> sl = new Vector<String>();
		while (m.find()) {
			System.out.println(m.group());
			sl.add(m.group());
		}
		return sl;
	}

}
