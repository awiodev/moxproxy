package builders;

public class NullType implements IBuilder {

    @Override
    public IBuilder backToParent() {
        return null;
    }
}
