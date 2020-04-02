package com.trueblue;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Delimeter {

	String begin;
	String end;

	public Delimeter(String begin, String end) {
		this.begin = begin;
		this.end = end;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delimeter other = (Delimeter) obj;
		if (begin == null) {
			if (other.begin != null)
				return false;
		} else if (!begin.equals(other.begin))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		return true;
	}

	public String getBegin() {
		return begin;
	}

	public String getEnd() {
		return end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begin == null) ? 0 : begin.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		return result;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Delimeter [begin=" + begin + ", end=" + end + "]";
	}

	public Delimeter validate() {
		checkArgument(!StringUtils.isBlank(begin), "Missing begin");
		checkArgument(!StringUtils.isBlank(end), "Missing end");
		return this;

	}

	public String getContents(String source) {
		checkArgument(source.startsWith(begin));
		return StringUtils.substringBetween(source, begin, end);

	}

	public String withContents(String contents) {
		return String.format("%s%s%s", begin, contents, end);
	}

	public boolean isBalanced(String source) {
		return StringUtils.countMatches(source, begin) == StringUtils.countMatches(source, end);
	}
	
	public String findDelimeted(String source)
	{
		return source.substring(source.indexOf(begin), source.indexOf(end) + end.length());
	}
	
	public List<String> findAllDelimeted(String source)
	{
		String cursor = new String(source);
		List<String> candidates = new ArrayList<>();
		while (position(cursor) != -1) {
			String candidate = findDelimeted(cursor);
			candidates.add(candidate);
			cursor = cursor.substring(positionAfter(cursor));
		}
		return candidates;
	}

	public int positionAfter(String source) {
		return source.indexOf(end) + end.length();
	}

	public int position(String source) {
		int index = source.indexOf(begin);
		return index;
	}

}
