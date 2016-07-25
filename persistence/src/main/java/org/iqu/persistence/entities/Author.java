package org.iqu.persistence.entities;

public class Author {

	private int authorID;
	private String ame;

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public String getName() {
		return ame;
	}

	public void setName(String authorName) {
		this.ame = authorName;
	}

	@Override
	public String toString() {
		return "Author [authorID=" + authorID + ", ame=" + ame + "]";
	}
}
