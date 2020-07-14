package com.luxiaofeng.smartfind;


import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luxiaofeng
 * <p>
 *     <li>绑定视图控件 </li>
 * </p>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FindView {
    int value() default View.NO_ID;
}