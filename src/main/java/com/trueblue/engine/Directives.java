package com.trueblue.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.trueblue.Directive;

public class Directives {

	public static Directives NULL = new NullDirectives();
	
	public static class Builder {

		String[] names;

		public Directives build() {
			Directives instance = new Directives();
			instance.names = names == null || names.length == 0 ? new ArrayList<>() : Arrays.asList(names);
			return instance;
		}

		public Builder names(String... names) {
			this.names = names;
			return this;
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	List<String> names;

	 Directives() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Directives other = (Directives) obj;
		if (names == null) {
			if (other.names != null)
				return false;
		} else if (!names.equals(other.names))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((names == null) ? 0 : names.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Directives [names=" + names + "]";
	}

	Function<String, String> compose() {
		return names.stream().map(name -> findByNameIgnoreCase(name))
				.filter(optionalDirective -> optionalDirective.isPresent())
				.map(optionalDirective -> optionalDirective.get().getFunction()).reduce((d1, d2) -> d2.compose(d1)).get();
	}

	Optional<Directive> findByNameIgnoreCase(String name) {
		return EnumSet.allOf(Directive.class).stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
	}

	public String apply(String target) {
		return compose().apply(target);
	}

}
