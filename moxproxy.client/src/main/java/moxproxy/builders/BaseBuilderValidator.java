package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

public abstract class BaseBuilderValidator<Builder extends IBuilder> implements IBuilderValidator<Builder> {

    protected <T>void notNull(T obj, String  objectClass, String field) throws BuilderValidationException {
        if(obj == null){
            throw new BuilderValidationException("Object field: " + field + " member of: " + objectClass + " cannot be null");
        }
    }
}
