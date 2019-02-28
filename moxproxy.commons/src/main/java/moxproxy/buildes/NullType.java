package moxproxy.buildes;

public class NullType implements Builder {

    @Override
    public Builder backToParent() {
        return null;
    }

    @Override
    public Object build() {
        return null;
    }
}
