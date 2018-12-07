package com.s.t.m.common.jsonserializer;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("rawtypes")
public class LongJsonSerializer extends JsonSerializer{

	public LongJsonSerializer() {
	}

	public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		if (Objects.isNull(o))
			return;
		if (o instanceof Long)
			jsonGenerator.writeString(o.toString());
	}

}
