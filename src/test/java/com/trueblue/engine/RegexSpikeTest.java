package com.trueblue.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexSpikeTest {

	@Test
	public void findMatchWithNoDirective()
	{
		assertTrue("abcdef<<hello>>ghijkl".matches("\\w+(<<hello>>)\\w+"));
	}
	
	@Test
	public void getTheMatchedPortionAsAGroup()
	{
		String beginDelimeter = "<<";
		String endDelimeter = ">>";
		String patternString = String.format("\\w+(%s(\\w+)(:?([\\w,]*))%s)\\w+", beginDelimeter, endDelimeter);
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher("abcdef<<hello:abc,def>>ghijkl");
		matcher.find();
		assertEquals(4, matcher.groupCount());
	}
}
