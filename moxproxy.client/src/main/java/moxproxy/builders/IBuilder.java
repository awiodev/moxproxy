package moxproxy.builders;


public interface IBuilder<ParentBuilder extends IBuilder, Model> {

    ParentBuilder backToParent();

    Model build();
}
