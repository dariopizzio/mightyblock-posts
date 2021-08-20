package com.mightyblock.posts.utils;

import com.mightyblock.posts.model.exceptions.PropertyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UtilsService {

    @Autowired
    Environment env;

    /**
     * Returns a value given a property name and the expected type of value
     * @param name  property to find
     * @param clazz expected type of value
     * @return value for the property
     * @throws PropertyNotFoundException
     */
    @SuppressWarnings("unchecked")
    public <T> T getProperty(String name, Class<T> clazz) throws PropertyNotFoundException {
        Object prop = env.getProperty(name, clazz);
        if(prop != null) {
            return (T) prop;
        }
        log.error("Empty property: "+name);
        throw new PropertyNotFoundException(HttpStatus.NOT_FOUND, "EMPTY PROPERTY "+name);
    }
}
