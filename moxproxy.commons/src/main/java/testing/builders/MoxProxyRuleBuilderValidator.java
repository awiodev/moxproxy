package testing.builders;

import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.exceptions.BuilderValidationException;

class MoxProxyRuleBuilderValidator extends BaseBuilderValidator<MoxProxyRuleBuilder> {

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
        validateRespondAction(builder);
        validateRequestStatusCode(builder);
    }

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
}
