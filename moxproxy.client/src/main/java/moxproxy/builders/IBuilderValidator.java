package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

interface IBuilderValidator<Builder extends IBuilder> {

    void performValidation(Builder builder) throws BuilderValidationException;
}
