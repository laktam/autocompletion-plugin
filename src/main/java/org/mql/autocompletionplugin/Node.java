package org.mql.autocompletionplugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/*
 * 
 */
public class Node {
	private Map<Character, Node> children;
	private boolean wordStop;

	public Node() {
		this.children = new HashMap<Character, Node>();
		this.wordStop = false;
	}

	public Node(boolean wordStop) {
		this.children = new HashMap<Character, Node>();
		this.wordStop = wordStop;
	}

	public Node(String s) {
		this();
		if (s.length() == 1) {
			children.put(s.charAt(0), new Node(true));
		} else if (s.length() >= 2) {
			children.put(s.charAt(0), new Node(s.substring(1)));
		}
	}

	public void insert(String s) {
		if (s.length() > 1) {
			if (children.containsKey(s.charAt(0))) {
				children.get(s.charAt(0)).insert(s.substring(1));
			} else {
				children.put(s.charAt(0), new Node(s.substring(1)));
			}
		} else if (s.length() == 1) {
			if (children.containsKey(s.charAt(0))) {
				// already exist no need to do anything
			} else {
				children.put(s.charAt(0), new Node(true));
			}
		}

	}

	/*
	 * the keys for the children of the node returned here are the start for
	 * suggestions
	 */
	public Node getNodeForPrefix(String prefix) {
		if (prefix.length() == 1) {
			return children.get(prefix.charAt(0));
		} else {
			if (children.containsKey(prefix.charAt(0))) {
				return children.get(prefix.charAt(0)).getNodeForPrefix(prefix.substring(1));
			} else {
				return null;
			}
		}
	}

	public List<String> getSuggestions(String prefix) {
		Node n = getNodeForPrefix(prefix);

		List<String> suggestions = new Vector<String>();
		if (n != null) {
			for (Entry<Character, Node> entry : n.children.entrySet()) {
				Node child = entry.getValue();
				if (child.wordStop) {
					suggestions.add(prefix + entry.getKey());
				}
				suggestions.addAll(child.getStrings(prefix + entry.getKey()));
			}
		}
		return suggestions;
	}

	/*
	 * return all the combinations and concat this prefix at the beginning
	 */
	public List<String> getStrings(String prefix) {
		List<String> sList = new Vector<String>();
		for (Entry<Character, Node> entry : children.entrySet()) {
			Node child = entry.getValue();
			if (child.wordStop) {
				sList.add(prefix + entry.getKey());
			}
			sList.addAll(child.getStrings(prefix + entry.getKey()));

		}
		return sList;
	}

	public List<Character> getKeys() {
		List<Character> characters = new Vector<Character>();
		for (Entry<Character, Node> entry : children.entrySet()) {
			characters.add(entry.getKey());
		}
		return characters;
	}

//	public List<String> getStringList(){
//		List<String> result = new Vector<>();
//		Set<Entry<Character, Node>> entrySet = children.entrySet();
//		List<Entry<Character, Node>> entryList = new Vector<>(entrySet);
//		for(int i = 0; i < entryList.size(); i++) {
//			result.add(entryList.get(i).getKey().toString());
//			entryList.get(i);
//		}
//		for(Entry<Character, Node> entry : children.entrySet()) {
//			result.add(entry.getKey());
//			Node nodes = entry.getValue();
//			
//		}
//		return 
//	}

	public Node getChildrenForKey(Character key) {
		return children.get(key);
	}

	public Map<Character, Node> getChildren() {
		return children;
	}

	public boolean isWordStop() {
		return wordStop;
	}

	public void setWordStop(boolean wordStop) {
		this.wordStop = wordStop;
	}

}
