package com.github.chic.admin.component.config;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Jackson 配置类
 */
@JsonComponent("JacksonNumberConfig")
public class JacksonConfig {
    /**
     * Long 序列化配置
     */
    public static class LongSerializer extends JsonSerializer<Long> {
        @Override
        public void serialize(Long number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(Convert.toStr(number));
        }
    }

    /**
     * Long 反序列化配置
     */
    public static class LongDeserializer extends JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return jsonParser.getValueAsLong();
        }
    }
}
