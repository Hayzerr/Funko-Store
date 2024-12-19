package com.bolashak.onlinestorebackend.deserializer;

import com.bolashak.onlinestorebackend.entities.Image;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ImageDeserializer extends JsonDeserializer<Image> {

    @Override
    public Image deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String url = parser.getText(); // Read the string value from JSON
        return new Image(url, null);   // Create an Image object with only the URL
    }
}
