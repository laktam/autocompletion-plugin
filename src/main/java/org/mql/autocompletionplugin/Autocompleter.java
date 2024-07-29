package org.mql.autocompletionplugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.documentHandlers.OpenDocumentsHandler;

public class Autocompleter implements OpenDocumentsHandler {
	private List<StyledDocument> openDocuments;
	private Node root;

	public Autocompleter() {
		root = new Node();
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
				String words[] = text.split("\\b\\w*\\b");
				for (String w : words) {
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
		openDocuments.add(doc);
		try {
			String text = doc.getText(0, doc.getLength());
//			words.addAll(Arrays.asList());
			String words[] = text.split("\\b\\w*\\b");
			for (String w : words) {
				root.addChild(w.charAt(0), new Node(w.substring(1)));
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		System.out.println(root);

	}

}
