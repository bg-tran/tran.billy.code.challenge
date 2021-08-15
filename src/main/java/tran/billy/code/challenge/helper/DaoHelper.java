package tran.billy.code.challenge.helper;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class DaoHelper {

    public static <T> Predicate<T> createCriteria(String fieldName, String fieldValue){

        return p -> {
            try {
                Field field = p.getClass().getField(fieldName);

                return field.get(p).equals(fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        };
    }
}
