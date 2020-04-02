package com.trueblue.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.io.Resources;
import com.trueblue.Delimeter;

public class EngineTest {

	@Test
	public void delimeterSettingsAreNullShouldUseDefault() {
		Engine.builder().delimeter(null).start();
	}

	@Test
	public void replacementSettingsAreNullShouldUseDefault() {
		Engine.builder().replacements(null).start();
	}

	@Test
	public void replacementSettingsWithNullKeysOrxValuesShouldBeRemoved() {
		Map<String, String> replacements = new HashMap<>();
		replacements.put("name", "value");
		replacements.put("null", null);
		replacements.put(null, "null");
		Map<String, String> engineReplacements = Engine.builder().replacements(replacements).start().getReplacements();
		assertTrue(engineReplacements.size() == 1 && engineReplacements.containsKey("name"));
	}

	@Test
	public void delimeterSettingsAreValid() {
		Engine.builder().delimeter(new Delimeter("<<", ">>")).start();
	}

	@Test(expected = IllegalArgumentException.class)
	public void delimeterSettingsWithEmptyBeginShouldFail() {
		Engine.builder().delimeter(new Delimeter("", "end")).start();
	}

	@Test(expected = IllegalArgumentException.class)
	public void delimeterSettingsWithEmptyEndShouldFail() {
		Engine.builder().delimeter(new Delimeter("begin", "")).start();
	}

	@Test(expected = IllegalArgumentException.class)
	public void delimeterSettingsWithNullBeginShouldFail() {
		Engine.builder().delimeter(new Delimeter(null, "end")).start();
	}

	@Test(expected = IllegalArgumentException.class)
	public void delimeterSettingsWithNullEndShouldFail() {
		Engine.builder().delimeter(new Delimeter("begin", null)).start();
	}

	@Test
	public void startTheEngineIsOfTypeEngine() {
		assertTrue(Engine.class.isInstance(Engine.builder().start()));
	}

	@Test
	public void startTheEngineIsPresent() {
		assertNotNull(Engine.builder().start());
	}

	@Test
	public void runTheEngine() throws IOException {
		Map<String, String> replacements = new HashMap<>();
		replacements.put("firstName", "Kayla");
		replacements.put("lastName", "Roberts");
		replacements.put(null, "null");
		assertEquals(Arrays.asList("Hi Kayla Roberts!", "Welcome to template engine.", "KAYLA your last name should be roberts"), 
				Engine.builder().replacements(replacements).start()
				.run(createStreamFromResource("name.txt"))
				.collect(Collectors.toList()));
	}
	
	Stream<String> createStreamFromResource(String resourceName) throws IOException
	{
		return Resources.readLines(Resources.getResource(resourceName), Charset.defaultCharset()).stream();
	}

}
