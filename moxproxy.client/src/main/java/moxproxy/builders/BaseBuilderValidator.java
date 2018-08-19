package moxproxy.builders;

import com.google.common.collect.Iterators;
import moxproxy.exceptions.BuilderValidationException;

import java.util.Iterator;

abstract class BaseBuilderValidator<Builder extends IBuilder> implements IBuilderValidator<Builder> {

    <T>void notValue(T obj, T notExpected, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(obj == notExpected){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be " + notExpected + ". " + errorDescription);
        }
    }

    void shouldBeDifferentThanValue(int actual, int notExpected, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(actual != notExpected){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " should be different than " + notExpected + ". " + errorDescription);
        }
    }

    <T>void notNull(T obj, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(obj == null){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be null. " + errorDescription);
        }
    }

    <T>void notNull(T obj, String objectClass, String field) throws BuilderValidationException {
        notNull(obj, objectClass, field, "");
    }

    void notEmpty(Iterator collection, String objectClass, String field){
        notEmpty(collection, objectClass, field, "");
    }

    void notEmpty(Iterator collection, String objectClass, String field, String errorDescription){
        if(Iterators.size(collection) == 0){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be empty. " + errorDescription);
        }
    }

    String getClassName(IBuilder builder) {
        return builder.getClass().getCanonicalName();
    }
}
