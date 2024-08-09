package org.mql.autocompletionplugin;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.mql.jcodeeditor.documentHandlers.TextPanesHandler;
import org.mql.jcodeeditor.plugins.FilesHandler;
import org.mql.jcodeeditor.plugins.Reactivable;

public class Autocompleter implements TextPanesHandler, FilesHandler, Reactivable {
	private List<StyledDocument> openDocuments;
	private Node root;
	private List<JTextPane> textPanes;
	private List<DocumentListener> documentListeners;
	private boolean active;

	public Autocompleter() {
		documentListeners = new Vector<DocumentListener>();
		textPanes = new Vector<JTextPane>();
		root = new Node();
		active = true;
	}

	@Override
	public void setTextPanes(List<JTextPane> textPanes) {
		List<StyledDocument> docs = new Vector<StyledDocument>();
		for (JTextPane textPane : textPanes) {
			docs.add((StyledDocument) textPane.getDocument());
		}
		this.openDocuments = docs;
		for (StyledDocument d : openDocuments) {
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
			}
			DocumentListener docListener = new DocumentTypingListener(root, textPane);
			documentListeners.add(docListener);
			doc.addDocumentListener(docListener);
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

	@Override
	public void activate() {
		if (!active) {
			for (JTextPane textPane : textPanes) {
				Document doc = textPane.getDocument();
				doc.addDocumentListener(new DocumentTypingListener(root, textPane));
			}
		}
	}

	@Override
	public void deactivate() {
		if (active) {
			for (int i = 0; i < textPanes.size(); i++) {
				KeyListener keyListeners[] = textPanes.get(i).getKeyListeners();
				for (KeyListener keyListener : keyListeners) {
					textPanes.get(i).removeKeyListener(keyListener);
				}
			}
			for (int i = 0; i < openDocuments.size(); i++) {
				openDocuments.get(i).removeDocumentListener(documentListeners.get(i));
			}
		}
	}

}
