package org.mql.autocompletionplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.mql.jcodeeditor.documentHandlers.TextPanesHandler;
import org.mql.jcodeeditor.plugins.FilesHandler;

public class Autocompleter implements TextPanesHandler, FilesHandler {
	private List<StyledDocument> openDocuments;
	private Node root;

	public Autocompleter() {
		root = new Node();
		System.out.println(" init : " +root);
	}

	@Override
	public void setTextPanes(List<JTextPane> textPanes) {
		List<StyledDocument> docs = new Vector<StyledDocument>();
		for (JTextPane textPane : textPanes) {
			docs.add((StyledDocument) textPane.getDocument());
		}
		this.openDocuments = docs;
		// TODO create a set of words
		for (StyledDocument d : openDocuments) {
			// TODO the listener should take the collection of words to add to it new words
			try {
				String text = d.getText(0, d.getLength());
				List<String> words = getWords(text);
				for (String w : words) {
					if (w.length() > 0)
						System.out.println(root);
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
	public void addTextPane(JTextPane textPane) {
		StyledDocument doc = (StyledDocument) textPane.getDocument();
		openDocuments.add(doc);
		try {
			String text = doc.getText(0, doc.getLength());
			List<String> words = getWords(text);
			for (String w : words) {
				if (w.length() > 0)
					root.insert(w);
				System.out.println(root);
			}
			doc.addDocumentListener(new DocumentTypingListener(root, textPane));
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

	@Override
	public void addFile(File file) {
		if (!file.isDirectory()) {
			String text;
			try {
				text = readFile(file);
				List<String> words = getWords(text);
				for (String w : words) {
					if (w.length() > 0) {
						System.out.println(root);
						root.insert(w);

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String readFile(File file) throws IOException {
		StringBuilder text = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(file)) {
			int ch;
			while ((ch = fis.read()) != -1) {
				text.append((char) ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString();
	}

}
