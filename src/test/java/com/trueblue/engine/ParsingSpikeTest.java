package com.trueblue.engine;

import static com.trueblue.engine.Engine.DEFAULT_DELIMETER;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class ParsingSpikeTest {

	@Test
	public void parse() {
		Map<String, String> replacements = new HashMap<>();
		replacements.put("abc", "Chris");
		replacements.put("c", "Roberts");
 		List<Template> templates = DEFAULT_DELIMETER.findAllDelimeted("abc<<c>>de<<>>f<< abc : def,   ghi >> <<nan>><< << << >> >>").stream()
				.map(Template::new)
				.filter(t -> t.validate(DEFAULT_DELIMETER))
				.filter(t -> replacements.containsKey(t.getName()))
				.collect(Collectors.toList());
 		assertEquals(Arrays.asList("c", "abc"), templates.stream().map(t -> t.getName()).collect(Collectors.toList()));

	}

}
