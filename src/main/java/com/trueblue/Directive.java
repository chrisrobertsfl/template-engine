package com.trueblue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public enum Directive {
	UPPERCASE("uppercase", s -> s.toUpperCase()), LOWERCASE("lowercase", s -> s.toLowerCase());

	String name;

	Function<String, String> function;

	private Directive(String name, Function<String, String> function) {
		this.name = name;
		this.function = function;
	}

	public String apply(String target) {
		return function.apply(target);
	}

	public String getName() {
		return this.name;
	}

	public Function<String, String> getFunction() {
		return this.function;
	}

	static Optional<Directive> findByNameIgnoreCase(String name) {
		return EnumSet.allOf(Directive.class).stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
	}

	public static Function<String, String> compose(String... names) {
		return compose(Arrays.asList(names));
	}	
	
	public static Function<String, String> compose(List<String> names) {
		return names.stream().map(name -> Directive.findByNameIgnoreCase(name))
				.filter(optionalDirective -> optionalDirective.isPresent())
				.map(optionalDirective -> optionalDirective.get().getFunction()).reduce((d1, d2) -> d2.compose(d1))
				.get();
	}
}
