package moxproxy.builders;

import java.util.ArrayList;
import java.util.List;

public abstract class CollectionBuilder<Parent extends IBuilder, ChildBuilder, Model> extends BaseBuilder<Parent, Model> {

    private List<ChildBuilder> items;

    CollectionBuilder(Parent parent){
        super(parent);
        items = new ArrayList<>();
    }

    public ChildBuilder addChildItem(){
        ChildBuilder newChild = CreateChildBuilder();
        items.add(newChild);
        return newChild;
    }

    List<ChildBuilder> getItems(){
        return items;
    }

    protected abstract ChildBuilder CreateChildBuilder();
}
