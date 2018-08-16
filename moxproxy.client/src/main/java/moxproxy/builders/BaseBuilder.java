package moxproxy.builders;

public abstract class BaseBuilder<Parent extends IBuilder, Model> implements IBuilder<Parent> {

    private Parent parent;

    BaseBuilder(Parent parent){
        this.parent = parent;
    }

    /*
        For builders without patent
     */
    BaseBuilder(){
        this(null);
    }

    @Override
    public Parent backToParent() {
        return parent;
    }

    public abstract Model build();
}
