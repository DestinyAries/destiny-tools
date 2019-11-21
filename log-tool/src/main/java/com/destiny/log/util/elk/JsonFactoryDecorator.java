package com.destiny.log.util.elk;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class JsonFactoryDecorator implements net.logstash.logback.decorate.JsonFactoryDecorator {
    @Override
    public JsonFactory decorate(JsonFactory factory) {
        // 禁用对非ascii码进行escape编码的特性
        factory.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        return this.decorate((MappingJsonFactory)factory);
    }
}
