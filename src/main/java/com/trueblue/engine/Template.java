package com.trueblue.engine;

import static com.trueblue.engine.Directives.NULL;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.trueblue.Delimeter;

public class Template {

	String match;
	String name;
	Directives directives;

	public Template(String match) {
		this.match = match;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directives == null) ? 0 : directives.hashCode());
		result = prime * result + ((match == null) ? 0 : match.hashCode());
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
		Template other = (Template) obj;
		if (directives == null) {
			if (other.directives != null)
				return false;
		} else if (!directives.equals(other.directives))
			return false;
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean validate(Delimeter delimeter) {
		String contents = delimeter.getContents(match).trim();
		if (StringUtils.isBlank(contents) || !contents.matches("^\\w.*")) {
			return false;
		}
		List<String> fields = Arrays.asList(StringUtils.deleteWhitespace(contents).split(":"));
		this.name = fields.get(0);
		this.directives = fields.size() == 1 ? NULL : Directives.builder().names(fields.get(1).split(",")).build();
		return true;
	}

	public String getMatch() {
		return match;
	}

	@Override
	public String toString() {
		return "Template [match=" + match + ", name=" + name + ", directives=" + directives + "]";
	}

	public String getName() {
		return name;
	}

	String value;

	public Template setValue(String value) {
		this.value = value;
		return this;
	}

	public Directives getDirectives() {
		return directives;
	}
	
	public String apply(String source) {
		return source.replace(match, directives.compose().apply(value));
	}

}
