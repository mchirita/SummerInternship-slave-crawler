package org.iqu.webcrawler.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Category - Entity that represents a category.
 * 
 * @author Alex Dragomir
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {
	private String name;

	public Category(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "\"" + name + "\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
