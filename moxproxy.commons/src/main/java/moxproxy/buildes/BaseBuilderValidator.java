package moxproxy.buildes;

import com.google.common.collect.Iterators;
import moxproxy.exceptions.BuilderValidationException;

import java.util.Iterator;

public abstract class BaseBuilderValidator<BuilderImplementation extends Builder> implements BuilderValidator<BuilderImplementation> {

    <T>void notValue(T obj, T notExpected, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(obj == notExpected){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be " + notExpected + ". " + errorDescription);
        }
    }

    protected void shouldBeDifferentThanValue(int actual, int notExpected, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(actual == notExpected){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " should be different than " + notExpected + ". " + errorDescription);
        }
    }

    void shouldBeTheSameAs(int actual, int notExpected, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(actual != notExpected){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " should be equal to " + notExpected + ". " + errorDescription);
        }
    }

    private <T>void notNull(T obj, String objectClass, String field, String errorDescription) throws BuilderValidationException {
        if(obj == null){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be null. " + errorDescription);
        }
    }

    protected <T>void notNull(T obj, String objectClass, String field) throws BuilderValidationException {
        notNull(obj, objectClass, field, "");
    }

    protected void notEmpty(Iterator collection, String objectClass, String field){
        notEmpty(collection, objectClass, field, "");
    }

    private void notEmpty(Iterator collection, String objectClass, String field, String errorDescription){
        if(Iterators.size(collection) == 0){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be empty. " + errorDescription);
        }
    }

    protected String getClassName(Builder builder) {
        return builder.getClass().getCanonicalName();
    }
}
