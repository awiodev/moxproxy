package moxproxy.builders;


import moxproxy.exceptions.BuilderValidationException;

interface Builder<ParentBuilder extends Builder, Model> {

    ParentBuilder backToParent();

    Model build() throws BuilderValidationException;
}
