package org.iqu.parsers.entities;

/**
 * Holds information about a news source.
 * 
 * @author Mitroi Stefan, Cristi Badoi
 *
 */

public class SourceDTO {

	private long id;
	private String displayName;
	private String description;
	private String image;

	public SourceDTO() {
	}

	public SourceDTO(long id, String displayName, String description, String image) {
		this.id = id;
		this.displayName = displayName;
		this.description = description;
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "Source [id=" + id + ", displayName=" + displayName + ", description=" + description + ", image=" + image
				+ "]";
	}

	/**
	 * Based solely on displayName field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		return result;
	}

	/**
	 * Based solely on displayName field.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceDTO other = (SourceDTO) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		return true;
	}

}
