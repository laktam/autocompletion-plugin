package org.mql.autocompletionplugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Node {
	private String value;
	private Map<String, Node> children;
	private boolean isLeaf;

	public Node(String value) {
		this.value = value;
		this.children = new HashMap<String, Node>();
		this.isLeaf = true;
	}

	public String getValue() {
		return value;
	}

	public void addChild(String key,Node n) {
		children.put(key, n);
	}
	
	public Node getChild(String key) {
		return children.get(key);
	}
	
	public Map<String, Node> getChildren() {
		return children;
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}
	
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
