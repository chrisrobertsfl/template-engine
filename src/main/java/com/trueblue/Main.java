package com.trueblue;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.trueblue.engine.Engine;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().run();
	}

	String getFromCommandLine(String string) {
		String property = System.getProperty(string);
		return checkNotNull(property, String.format("Missing %s", string));
	}

	PrintStream getPrintStream(String propertyName) throws FileNotFoundException {
		String outputFile = System.getProperty(propertyName);
		return StringUtils.isBlank(outputFile) ? System.out : new PrintStream(outputFile);
	}

	Stream<String> getStreamFromProperty(String propertyName) throws IOException {
		return Files.lines(Paths.get(getFromCommandLine(propertyName)));
	}

	Map<String, String> parseReplacements(String propertyName) throws IOException {
		return getStreamFromProperty(propertyName).map(line -> line.split("="))
				.collect(Collectors.toMap(s -> s[0], s -> s[1]));
	}

	void run() throws IOException {
		PrintStream printStream = getPrintStream("output-file");
		Engine.builder().replacements(parseReplacements("replacement-file")).start()
				.run(getStreamFromProperty("template-file")).forEach(s -> printStream.println(s));
	}

}
