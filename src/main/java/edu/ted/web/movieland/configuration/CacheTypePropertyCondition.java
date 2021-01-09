package edu.ted.web.movieland.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.StandardMethodMetadata;

public class CacheTypePropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return ((StandardMethodMetadata) annotatedTypeMetadata).getIntrospectedMethod().getReturnType().getTypeName().endsWith("."+conditionContext.getEnvironment().getProperty("cacheType"))||
                conditionContext.getEnvironment().getProperty("cacheType") == null;
    }
}
