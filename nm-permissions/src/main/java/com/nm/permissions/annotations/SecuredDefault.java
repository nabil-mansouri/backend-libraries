package com.nm.permissions.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nm.permissions.constants.ActionDefault;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SecuredDefault {

	public String resource();

	public ActionDefault action();

}
