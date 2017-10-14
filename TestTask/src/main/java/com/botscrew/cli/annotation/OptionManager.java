package com.botscrew.cli.annotation;

import com.botscrew.cli.option.MainArgument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionManager {
    Class<? extends MainArgument> argumentType();
}
