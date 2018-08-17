package moxproxy.builders;


import moxproxy.exceptions.BuilderValidationException;

interface IBuilder<ParentBuilder extends IBuilder, Model> {

    ParentBuilder backToParent();

    Model build() throws BuilderValidationException;
}
