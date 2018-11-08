package com.sununiq.util

import java.lang.reflect.Field
import java.lang.reflect.Modifier

fun <T : Annotation> getAnnotation(field: Field, annotationType: Class<T>): T? {
    return field.getDeclaredAnnotation(annotationType)
}

fun <T : Annotation> getAnnotation(clazz: Class<*>, annotationType: Class<T>): T? {
    return clazz.getDeclaredAnnotation(annotationType)
}

fun isAnnotation(field: Field, annotationType: Class<out Annotation>): Boolean {
    return getAnnotation(field, annotationType) != null
}

fun isAnnotation(clazz: Class<*>, annotationType: Class<out Annotation>): Boolean {
    return getAnnotation(clazz, annotationType) != null
}

fun getValue(key: String, obj: Any): Any? = when (obj) {
    is Map<*, *> -> obj[key]
    is List<*> -> obj[key.toInt()]
    else -> getFieldValue(key, obj)
}

fun getFieldsExceptStatic(clazz: Class<*>): List<Field> {
    return clazz.declaredFields.filterNot {
        Modifier.isStatic(it.modifiers)
    }
}

fun getFieldValue(fieldName: String, obj: Any): Any? {
    var target: Any? = null
    val field = getField(fieldName, obj.javaClass)
    if (field != null) {
        try {
            field.isAccessible = true
            target = field.get(obj)
            //jump
        } catch (e: IllegalArgumentException) {
        } catch (e: IllegalAccessException) {
        }
    }
    return target
}

fun getField(fieldName: String, clazz: Class<*>): Field? {
    return try {
        clazz.getDeclaredField(fieldName)
    } catch (e: NoSuchFieldException) {
        if (clazz.superclass != null) {
            getField(fieldName, clazz.superclass)
        } else {
            return null
        }
    }
}