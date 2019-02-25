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
        //validateMatchingStrategy(create);
    }

    private void validateBasics(MoxProxyRuleBuilder builder) throws BuilderValidationException {
        notNull(builder.getDirection(), getClassName(builder), "DIRECTION");
        notNull(builder.getAction(), getClassName(builder), "ACTION");
    }

    private void validateActions(MoxProxyRuleBuilder builder){
        //validateHeaderActionsCollection(create, MoxProxyAction.SET_HEADER);
        //validateHeaderActionsCollection(create, MoxProxyAction.SET_HEADER);
        //validateHeaderActionsCollection(create, MoxProxyAction.DELETE_HEADER);
        //validateBodyActions(create);
        //validateHeaderNameForAction(create, MoxProxyAction.SET_HEADER);
        //validateHeaderNameForAction(create, MoxProxyAction.DELETE_HEADER);
        validateRespondAction(builder);
        validateRequestStatusCode(builder);
    }

/*    private void validateBodyActions(MoxProxyRuleBuilder create){
        var action = MoxProxyAction.SET_BODY;
        if(create.getAction() == action){
            MoxProxyHttpRuleDefinitionBuilder httpObjectBuilder = create.getHttpObjectBuilder();
            String body = httpObjectBuilder.getBody();
            notNull(body, getClassName(httpObjectBuilder), "BODY", "Set body when using action: " + action.name());
        }
    }*/

/*    private void validateHeaderActionsCollection(MoxProxyRuleBuilder create, MoxProxyAction action){
        if(create.getAction() == action){
            MoxProxyHeadersCollectionBuilder headersBuilder = create.getHttpObjectBuilder().getHeadersCollectionBuilder();
            List<MoxProxyHeaderBuilder> items = headersBuilder.getItems();
            notEmpty(items.iterator(), getClassName(headersBuilder), "HEADERS", "Set at least one header when using action: " + action.name());
        }
    }*/

/*    private void validateHeaderNameForAction(MoxProxyRuleBuilder create, MoxProxyAction action){
        if(create.getAction() == action){
            MoxProxyHeadersCollectionBuilder headersBuilder = create.getHttpObjectBuilder().getHeadersCollectionBuilder();
            List<MoxProxyHeaderBuilder> items = headersBuilder.getItems();
            items.forEach(header -> notNull(header.getName(), getClassName(headersBuilder), "HEADER_NAME", "Header name cannot be null when using action: " + action.name()));
        }
    }*/

    private void validateRespondAction(MoxProxyRuleBuilder builder){
        var action = MoxProxyAction.RESPOND;
        if(builder.getAction() == action){
            MoxProxyHttpRuleDefinitionBuilder httpObjectBuilder = builder.getHttpObjectBuilder();
            int statusCode = httpObjectBuilder.getStatusCode();
            notValue(statusCode, 0, getClassName(httpObjectBuilder), "STATUS_CODE", "Status code cannot be 0 when using action: " + action.name());
        }

        if(builder.getAction() == action && builder.getDirection() == MoxProxyDirection.RESPONSE){
            throw new BuilderValidationException("ACTION: " + action.name() + " cannot be applied to DIRECTION: " + MoxProxyDirection.RESPONSE);
        }
    }

    private void validateRequestStatusCode(MoxProxyRuleBuilder builder){
        if(builder.getDirection() == MoxProxyDirection.REQUEST && builder.getAction() != MoxProxyAction.RESPOND){
            MoxProxyHttpRuleDefinitionBuilder httpObjectBuilder = builder.getHttpObjectBuilder();
            int statusCode = httpObjectBuilder.getStatusCode();
            shouldBeTheSameAs(statusCode, 0, getClassName(httpObjectBuilder), "STATUS_CODE", "Status code cannot be different than 0 when using direction: " + MoxProxyDirection.REQUEST);
        }
    }

/*    private void validateMatchingStrategy(MoxProxyRuleBuilder create){
        if(create.getStrategyBuilder().isUseSessionId()){
            notNull(create.getSessionId(), getClassName(create), "SESSION_ID", "Set session ID when using session id in matching strategy");
        }
    }*/
}
