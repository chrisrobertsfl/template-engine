package com.trueblue.engine;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import static com.trueblue.engine.Engine.DEFAULT_DELIMETER;

public class SplitSpikeTest {

	@Test
	public void splitSingleWithNoDelimeter() {
		assertEquals("abc", "abc".split(":")[0]);
		assertEquals("abc", "abc:".split(":")[0]);
	}

	@Test
	public void delimetedFound() {
		assertEquals(Arrays.asList("<<firstName:uppercase>>", "<<lastName:lowercase>>"), DEFAULT_DELIMETER
				.findAllDelimeted("<<firstName:uppercase>> your last name should be <<lastName:lowercase>>"));
	}
}
