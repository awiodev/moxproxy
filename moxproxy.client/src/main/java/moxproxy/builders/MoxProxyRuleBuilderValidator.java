package moxproxy.builders;

import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
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
        validateHeaderActionsCollection(builder, MoxProxyAction.SET_HEADER);
        validateHeaderActionsCollection(builder, MoxProxyAction.SET_HEADER);
        validateHeaderActionsCollection(builder, MoxProxyAction.DELETE_HEADER);
        validateBodyActions(builder);
        validateHeaderNameForAction(builder, MoxProxyAction.SET_HEADER);
        validateHeaderNameForAction(builder, MoxProxyAction.DELETE_HEADER);
        validateRespondAction(builder);
        validateRequestStatusCode(builder);
    }

    private void validateBodyActions(MoxProxyRuleBuilder builder){
        var action = MoxProxyAction.SET_BODY;
        if(builder.getAction() == action){
            MoxProxyHttpObjectBuilder httpObjectBuilder = builder.getHttpObjectBuilder();
            String body = httpObjectBuilder.getBody();
            notNull(body, getClassName(httpObjectBuilder), "BODY", "Set body when using action: " + action.name());
        }
    }

    private void validateHeaderActionsCollection(MoxProxyRuleBuilder builder, MoxProxyAction action){
        if(builder.getAction() == action){
            MoxProxyHeadersCollectionBuilder headersBuilder = builder.getHttpObjectBuilder().getHeadersCollectionBuilder();
            List<MoxProxyHeaderBuilder> items = headersBuilder.getItems();
            notEmpty(items.iterator(), getClassName(headersBuilder), "HEADERS", "Set at least one header when using action: " + action.name());
        }
    }

    private void validateHeaderNameForAction(MoxProxyRuleBuilder builder, MoxProxyAction action){
        if(builder.getAction() == action){
            MoxProxyHeadersCollectionBuilder headersBuilder = builder.getHttpObjectBuilder().getHeadersCollectionBuilder();
            List<MoxProxyHeaderBuilder> items = headersBuilder.getItems();
            items.forEach(header -> notNull(header.getName(), getClassName(headersBuilder), "HEADER_NAME", "Header name cannot be null when using action: " + action.name()));
        }
    }

    private void validateRespondAction(MoxProxyRuleBuilder builder){
        var action = MoxProxyAction.RESPOND;
        if(builder.getAction() == action){
            MoxProxyHttpObjectBuilder httpObjectBuilder = builder.getHttpObjectBuilder();
            int statusCode = httpObjectBuilder.getStatusCode();
            notValue(statusCode, 0, getClassName(httpObjectBuilder), "STATUS_CODE", "Status code cannot be 0 when using action: " + action.name());
        }
    }

    private void validateRequestStatusCode(MoxProxyRuleBuilder builder){
        if(builder.getDirection() == MoxProxyDirection.REQUEST && builder.getAction() != MoxProxyAction.RESPOND){
            MoxProxyHttpObjectBuilder httpObjectBuilder = builder.getHttpObjectBuilder();
            int statusCode = httpObjectBuilder.getStatusCode();
            shouldBeTheSameAs(statusCode, 0, getClassName(httpObjectBuilder), "STATUS_CODE", "Status code cannot be different than 0 when using direction: " + MoxProxyDirection.REQUEST);
        }
    }
}
