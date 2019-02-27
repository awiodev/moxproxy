package testing.builders;

import java.util.ArrayList;
import java.util.List;

public abstract class CollectionBuilder<Parent extends Builder, BuilderImplementation extends Builder, ChildBuilder, Model, Validator extends BuilderValidator> extends BaseBuilder<Parent, BuilderImplementation, Model, Validator> {

    private List<ChildBuilder> items;

    CollectionBuilder(Parent parent, Validator validator){
        super(parent, validator);
        items = new ArrayList<>();
    }

    ChildBuilder addItem(){
        ChildBuilder newChild = createChildBuilder();
        items.add(newChild);
        return newChild;
    }

    List<ChildBuilder> getItems(){
        return items;
    }

    protected abstract ChildBuilder createChildBuilder();
}
