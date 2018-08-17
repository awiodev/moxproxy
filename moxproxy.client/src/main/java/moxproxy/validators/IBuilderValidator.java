package moxproxy.validators;

import moxproxy.builders.IBuilder;

public interface IBuilderValidator<Builder extends IBuilder> {

    void performValidation(Builder builder);
}
