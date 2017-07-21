package com.intellitech.springlabs.util;

import java.util.List;

import ma.glasnost.orika.MapperFacade;

public class MapperUtil {

	public static <T, U> List<U> map(final MapperFacade mapper, final List<T> source, final Class<U> destType) {

		if (mapper == null || source == null) {
			return null;
		}
		List<U> dest = mapper.mapAsList(source, destType);
		return dest;
	}
}
