package com.magic.spring.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target is used to specify the target of the annotation
@Target(ElementType.METHOD)
// @Retention is used to specify the retention policy of the annotation. Runtime is the default.
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodExporter {
}
