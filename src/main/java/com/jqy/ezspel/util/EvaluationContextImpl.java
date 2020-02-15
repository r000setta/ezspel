package com.jqy.ezspel.util;

import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collections;
import java.util.List;

public class EvaluationContextImpl extends StandardEvaluationContext {
    @Override
    public void setConstructorResolvers(List<ConstructorResolver> constructorResolvers) {
        super.setConstructorResolvers(constructorResolvers);
    }

    @Override
    public List<ConstructorResolver> getConstructorResolvers() {
        return Collections.emptyList();
    }
}
