package moxproxy.builders;

import moxproxy.enums.MoxProxyAction;
import moxproxy.exceptions.BuilderValidationException;

import java.util.List;

class MoxProxyRuleBuilderValidator extends BaseBuilderValidator<MoxProxyRuleBuilder>{

    @Override
    public void performValidation(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        validateBasics(builder);
        validateActions(builder);
    }

    private void validateBasics(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        notNull(builder.getDirection(), getClassName(builder), "DIRECTION");
        notNull(builder.getAction(), getClassName(builder), "ACTION");
    }

    private void validateActions(MoxProxyRuleBuilder builder){
        validateHeaderActions(builder, MoxProxyAction.ADD_HEADER);
        validateHeaderActions(builder, MoxProxyAction.MODIFY_HEADER);
        validateHeaderActions(builder, MoxProxyAction.DELETE_HEADER);
    }

    private void validateHeaderActions(MoxProxyRuleBuilder builder, MoxProxyAction action){
        if(builder.getAction() == action){
            var headersBuilder = builder.getHttpObjectBuilder().getHeadersCollectionBuilder();
            List<MoxProxyHeaderBuilder> items = headersBuilder.getItems();
            notEmpty(items.iterator(), getClassName(headersBuilder), "HEADERS", " Required when " + action.name() + " action is selected");
        }
    }
}
