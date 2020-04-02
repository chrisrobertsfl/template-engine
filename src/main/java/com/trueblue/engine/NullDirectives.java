package com.trueblue.engine;

import java.util.ArrayList;
import java.util.function.Function;

import com.google.common.base.Functions;

public class NullDirectives extends Directives {

	public NullDirectives() {
		this.names = new ArrayList<>();
	}

	@Override
	Function<String, String> compose() {
		return Functions.identity();
	}

}
