package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

public abstract class BaseBuilder<Parent extends IBuilder, Builder extends IBuilder, Model, Validator extends IBuilderValidator> implements IBuilder<Parent, Model> {

    private Parent parent;
    private Validator validator;

    BaseBuilder(Parent parent, Validator validator){
        this.parent = parent;
        this.validator = validator;
    }

    @Override
    public Parent backToParent() {
        return parent;
    }

    @Override
    public Model build() throws BuilderValidationException {
        Builder builder = getCurrentBuilder();
        validator.performValidation(builder);
        return performBuild();
    }

    abstract Model performBuild() throws BuilderValidationException;

    abstract Builder getCurrentBuilder();
}
