package java.lang;

import java.lang.reflect.Field;
/**
 * A java reflection helper class for JavaTown
 *
 * @author Emily Zhou
 * @version 9-1-2018
 */
public class ReflectionHelper
{
        public ReflectionHelper() {}

        public Class getClassFromObject(Object o)
        {
               return o.getClass();
        }

        public Field getClassDeclaredFieldFromClass(Class c, String fieldName) throws Exception
        {
               return c.getDeclaredField(fieldName);
        }

        public void setPrivateFieldValueOfObject(Object o, Field field, Object value) throws Exception
        {
               field.setAccessible(true);
               field.set(o, value);
        }

}

