package com.trueblue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

public class Placeholder {
	Delimeter delimeter;

	public static class Builder {
		public String field;
		public Delimeter delimeter;

		public Placeholder build() {
			Placeholder instance = new Placeholder();
			instance.field = field;
			instance.delimeter = delimeter;
			return instance.validate();
		}

		public Builder with(Consumer<Builder> builderFunction) {
			builderFunction.accept(this);
			return this;
		}
	}

	boolean valid = false;
	String field = null;
	String name;
	List<String> directives;

	public Placeholder() {
		this(null);
	}

	public Placeholder validate() {
		String contents = delimeter.validate().getContents(field);
		valid = !StringUtils.isBlank(contents);
		if (valid) {
			String[] nameAndDirectives = contents.split(":");
			name = nameAndDirectives[0];
			directives = (nameAndDirectives.length == 2) ? Arrays.asList(nameAndDirectives[1].split(","))
					: new ArrayList<>();
		}
		return this;
	}
	
	

	public Placeholder(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Placeholder other = (Placeholder) obj;
		if (delimeter == null) {
			if (other.delimeter != null)
				return false;
		} else if (!delimeter.equals(other.delimeter))
			return false;
		if (directives == null) {
			if (other.directives != null)
				return false;
		} else if (!directives.equals(other.directives))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (valid != other.valid)
			return false;
		return true;
	}

	public List<String> getDirectives() {
		return directives;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delimeter == null) ? 0 : delimeter.hashCode());
		result = prime * result + ((directives == null) ? 0 : directives.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (valid ? 1231 : 1237);
		return result;
	}

	public boolean isValid() {
		return valid;
	}

	public String replace(String target, String value) {
		return target.replace(field, Directive.compose(directives).apply(value));
	}

	@Override
	public String toString() {
		return "Placeholder [delimeter=" + delimeter + ", valid=" + valid + ", field=" + field + ", name=" + name
				+ ", directives=" + directives + "]";
	}
}
