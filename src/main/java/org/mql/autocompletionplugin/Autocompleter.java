package org.mql.autocompletionplugin;

import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
				List<String> words = getWords(text);
				for (String w : words) {
					if (w.length() > 0)
						root.insert(w);
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

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
			System.out.println("text : "+ text);
			List<String> words = getWords(text);
			for (String w : words) {
				if (w.length() > 0)
					root.insert(w);
			}
			doc.addDocumentListener(new DocumentTypingListener(root));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private List<String> getWords(String text) {
		Pattern p = Pattern.compile("\\b\\w*\\b");
		Matcher m = p.matcher(text);
		List<String> sl = new Vector<String>();
		while (m.find()) {
			sl.add(m.group());
		}
		return sl;
	}
}
