package testing.builders;

import moxproxy.exceptions.BuilderValidationException;

interface BuilderValidator<BuilderImplementation extends Builder> {

    void performValidation(BuilderImplementation builderImplementation) throws BuilderValidationException;
}
