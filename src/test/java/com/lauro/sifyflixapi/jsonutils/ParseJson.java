package com.lauro.sifyflixapi.jsonutils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ParseJson {

    private static final String JSON_FOLDER = "src/test/resources/json/";

    /**
     * Convert a JSON file in to a java object
     *
     * @param file  json
     * @param clazz to be converted
     * @param <T>   type of class
     * @return the java object
     */
    protected static <T> T parseToJavaObject(final File file, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(file, clazz);
        } catch (IOException ex) {
            throw new JsonParseException(ex.getCause());
        }
    }

    /**
     * @param json file
     * @return Json {@link File} passed as argument
     */
    protected static File getJsonFile(final String json) {

        try {
            return ResourceUtils.getFile(JSON_FOLDER + json);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Error to get json file", ex.getCause());
        }
    }

    /**
     * Convert a JSON file in to java List object
     *
     * @param file  json
     * @param clazz to be converted
     * @param <T>   type of class
     * @return the java object
     */
    protected static <T> List<T> parseToList(final File file, Class<T> clazz) {
        try {
            final var collectionType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz);
            return new ObjectMapper().readValue(file, collectionType);
        } catch (IOException ex) {
            throw new JsonParseException(ex.getCause());
        }
    }
}
