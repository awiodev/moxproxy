package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;

public abstract class BaseBuilder<Parent extends Builder, BuilderImplementation extends Builder, Model, Validator extends BuilderValidator> implements Builder<Parent, Model> {

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
    public Model build() {
        BuilderImplementation builder = getCurrentBuilder();
        validator.performValidation(builder);
        return performBuild();
    }

    protected abstract Model performBuild() throws BuilderValidationException;

    protected abstract BuilderImplementation getCurrentBuilder();
}
