package org.mql.autocompletionplugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

/*
 * 
 */
public class Node {
	private Map<Character, List<Node>> children;
	private boolean isLeaf;

	public Node() {
		this.children = new HashMap<Character, List<Node>>();
		this.isLeaf = true;
	}

	public Node(String s) {
		super();
		if (s.length() >= 2) {
			addChild(s.charAt(0), new Node(s.substring(1)));
		} else if (s.length() == 1) {
			this.isLeaf = true;
		}
	}

	public void addChild(Character key, Node n) {
		if (children.containsKey(key)) {
			children.get(key).add(n);
		} else {
			List<Node> nl = new Vector<Node>();
			nl.add(n);
			children.put(key, nl);
		}
	}

	public List<Node> getChildrenForKey(Character key) {
		return children.get(key);
	}

	public Map<Character, List<Node>> getChildren() {
		return children;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public String toString() {
		String s = "";

		for (Entry<Character, List<Node>> entry : children.entrySet()) {
			s += "key : " + entry.getKey();
			for (Node n : children.get(entry.getKey())) {
				s += n.toString();
			}
			s += "\n";
		}
		return s;
	}

}
