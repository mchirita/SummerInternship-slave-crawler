package org.iqu.parsers.entities;

public class Event {

	String title;
	String subtitle;
	long startDate;
	long endDate;
	String id;
	String description;
	String[] authors;
	String categories;
	String source;
	String body;
	String image_id;
	String thumbnail_id;
	String external_url;

	public Event(String title, long startDate, long endDate, String id, String desription, String categories,
			String source, String image_id, String external_url) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.id = id;
		this.description = desription;
		this.categories = categories;
		this.source = source;
		this.image_id = image_id;
		this.external_url = external_url;
	}

	public Event() {
		// TODO Auto-generated constructor stub
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
				+ ", desription = " + description + ", categories = " + categories + ", source = " + source
				+ ", image_id = " + image_id + ", external_url = " + external_url + "]";
	}

}
