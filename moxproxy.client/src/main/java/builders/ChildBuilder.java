package builders;

abstract class ChildBuilder<Parent, Model> extends BaseBuilder<Model> {

    Parent parent;

    ChildBuilder(Parent parent){
        this.parent = parent;
    }

    public Parent backToParent(){
        return parent;
    }
}
