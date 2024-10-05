package com.nm.utils.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BETTER TO SET MANUALLY ON CONFIG
 * 
 * @author nabilmansouri
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumJsonRegistryAnnotation {

}
