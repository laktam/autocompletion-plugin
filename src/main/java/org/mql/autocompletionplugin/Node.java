package org.mql.autocompletionplugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Node {
	private String value;
	private Map<Character, Node> children;
	private boolean isLeaf;

	
	public Node() {
		this.children = new HashMap<Character, Node>();
		this.isLeaf = true;
	}
	
	public Node(String value) {
		super();
		this.value = value;
		
	}

	public String getValue() {
		return value;
	}

	public void addChild(Character key,Node n) {
		children.put(key, n);
	}
	
	public Node getChild(Character key) {
		return children.get(key);
	}
	
	public Map<Character, Node> getChildren() {
		return children;
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}
	
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
