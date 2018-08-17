package moxproxy.builders;

public class NullType implements IBuilder {

    @Override
    public IBuilder backToParent() {
        return null;
    }

    @Override
    public Object build() {
        return null;
    }
}
