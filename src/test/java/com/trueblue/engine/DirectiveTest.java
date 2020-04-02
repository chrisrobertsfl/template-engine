package com.trueblue.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.trueblue.Directive;

public class DirectiveTest {

	@Test
	public void reduceDirectives() {
		assertEquals("chris", Directive.compose("uppercase", "lowercase", "uppercase", "lowercase").apply("Chris"));
	}

}
