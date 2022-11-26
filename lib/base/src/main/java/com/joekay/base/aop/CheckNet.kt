package com.joekay.base.aop

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：网络检测注解
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
annotation class CheckNet()
