package com.nb.org.domain;

import java.util.List;

/**
 * @ClassName: Node
 * @Description: 树的节点实体类
 * @author: Naughtior
 * @date:2016年2月21日 下午1:08:40
 */ 
public class Node {
	/**
	 * @Fields: id
	 * @Todo: 节点的id
	 */ 
	private int id;
	/**
	 * @Fields: text
	 * @Todo: 节点名称
	 */ 
	private String text;
	/**
	 * @Fields: type
	 * @Todo: 节点类型（"staff" or "section"）
	 */ 
	private String type;
	/**
	 * @Fields: url
	 * @Todo: 点击节点的url
	 */ 
	private String url;
	/**
	 * @Fields: leaf
	 * @Todo: 是否是叶子节点
	 */ 
	private boolean leaf;
	/**
	 * @Fields: expanded
	 * @Todo: 节点是否可扩展
	 */ 
	private boolean expanded;
	/**
	 * @Fields: nodes
	 * @Todo: 子节点列表
	 */ 
	private List<Node> nodes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", text=" + text + ", type=" + type + ", url=" + url + ", leaf=" + leaf
				+ ", expanded=" + expanded + ", nodes=" + nodes + "]";
	}
	
}
