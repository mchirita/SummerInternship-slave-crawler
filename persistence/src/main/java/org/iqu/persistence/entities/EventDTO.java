package org.iqu.persistence.entities;

public class EventDTO {

	private String title;
	private String subtitle;
	private long startDate;
	private long endDate;
	private long id;
	private String description;
	private String[] authors;
	private String categories;
	private String source;
	private String body;
	private String image_id;
	private String thumbnail_id;
	private String external_url;

	public EventDTO() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desription) {
		this.description = desription;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getThumbnail_id() {
		return thumbnail_id;
	}

	public void setThumbnail_id(String thumbnail_id) {
		this.thumbnail_id = thumbnail_id;
	}

	public String getExternal_url() {
		return external_url;
	}

	public void setExternal_url(String external_url) {
		this.external_url = external_url;
	}

	@Override
	public String toString() {
		return "Event [title = " + title + ", startDate = " + startDate + ", endDate=" + endDate + ", id = " + id
				+ ", desription = " + description + ", categories = " + categories + ", source = " + source + ", image_id = "
				+ image_id + ", external_url = " + external_url + "]";
	}

	/**
	 * Based solely on external_url field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((external_url == null) ? 0 : external_url.hashCode());
		return result;
	}

	/**
	 * Based solely on external_url field.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventDTO other = (EventDTO) obj;
		if (external_url == null) {
			if (other.external_url != null)
				return false;
		} else if (!external_url.equals(other.external_url))
			return false;
		return true;
	}
}
