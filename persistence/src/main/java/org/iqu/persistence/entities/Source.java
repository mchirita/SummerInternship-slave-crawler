package org.iqu.persistence.entities;

public class Source {
	private int sourceId;
	private String displayName;
	private String description;

	public Source(int sourceId, String displayName, String description) {
		this.sourceId = sourceId;
		this.displayName = displayName;
		this.description = description;
	}

	public Source() {

	}

	public int getId() {
		return sourceId;
	}

	public void setId(int sourceId) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Source other = (Source) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Source [sourceId=" + sourceId + ", displayName=" + displayName + ", description=" + description + "]";
	}

}