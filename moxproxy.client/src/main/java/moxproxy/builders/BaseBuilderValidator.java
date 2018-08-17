package moxproxy.builders;

import com.google.common.collect.Iterators;
import moxproxy.exceptions.BuilderValidationException;

import java.util.Iterator;

abstract class BaseBuilderValidator<Builder extends IBuilder> implements IBuilderValidator<Builder> {

    <T>void notNull(T obj, String objectClass, String field) throws BuilderValidationException {
        if(obj == null){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be null");
        }
    }

    String getClassName(IBuilder builder){
        return builder.getClass().getCanonicalName();
    }

    void notEmpty(Iterator collection, String objectClass, String field){
        if(Iterators.size(collection) == 1){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be empty");
        }
    }
}
