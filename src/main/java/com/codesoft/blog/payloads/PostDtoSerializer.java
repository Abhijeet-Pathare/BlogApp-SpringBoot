//package com.codesoft.blog.payloads;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//
//public class PostDtoSerializer extends JsonSerializer<PostDto> {
//
//    @Override
//    public void serialize(PostDto value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeStartObject();
//        gen.writeStringField("title",value.getTitle());
//        gen.writeStringField("content",value.getContent());
//        gen.writeStringField("imageName",value.getImageName());
//        gen.writeStringField("addedDate", String.valueOf(value.getAddedDate()));
//        gen.writeStringField("category",String.valueOf(value.getCategory()));
//        gen.writeStringField("user", String.valueOf(value.getUser()));
//        gen.writeEndObject();
//    }
//}
