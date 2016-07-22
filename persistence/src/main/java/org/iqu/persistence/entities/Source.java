package org.iqu.persistence.entities;

public class Source {
	private String sourceId;
	private String displayName;
	private String description;

	public Source(String sourceId, String displayName, String description) {
		this.sourceId = sourceId;
		this.displayName = displayName;
		this.description = description;
	}

	public Source() {

	}

	public String getId() {
		return sourceId;
	}

	public void setId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Source [sourceId=" + sourceId + ", displayName=" + displayName + ", description=" + description + "]";
	}

}