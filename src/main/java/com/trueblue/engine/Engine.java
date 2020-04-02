package com.trueblue.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.MoreObjects;
import com.trueblue.Delimeter;

public class Engine {
	public static final Delimeter DEFAULT_DELIMETER = new Delimeter("<<", ">>");

	private Engine() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Delimeter delimeter;
		private Map<String, String> replacements;

		public Engine start() {
			Engine instance = new Engine();
			instance.delimeter = MoreObjects.firstNonNull(delimeter, DEFAULT_DELIMETER).validate();
			replacements = MoreObjects.firstNonNull(replacements, new HashMap<>());
			replacements.values().removeIf(Objects::isNull);
			replacements.keySet().removeIf(Objects::isNull);
			instance.replacements = replacements;
			return instance;
		}

		public Builder delimeter(Delimeter delimeter) {
			this.delimeter = delimeter;
			return this;
		}

		public Builder replacements(Map<String, String> replacements) {
			this.replacements = replacements;
			return this;
		}
	}

	Delimeter delimeter;
	Map<String, String> replacements = new HashMap<>();

	public Delimeter getDelimeter() {
		return delimeter;
	}

	public Map<String, String> getReplacements() {
		return replacements;
	}

	@Override
	public String toString() {
		return "Engine [delimeter=" + delimeter + ", replacements=" + replacements + "]";
	}

	public Stream<String> run(Stream<String> stream) {
		return stream.map(line -> replace(line));
	}

	String replace(String string) {
		List<Template> templates = delimeter.findAllDelimeted(string).stream().map(Template::new)
				.filter(t -> t.validate(DEFAULT_DELIMETER)).filter(t -> replacements.containsKey(t.getName()))
				.map(t -> t.setValue(replacements.get(t.getName()))).collect(Collectors.toList());

		for (Template template : templates) {
			string = template.apply(string);
		}
		return string;
	}

}
