package tran.billy.code.challenge.dao;

import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

import tran.billy.code.challenge.helper.JsonHelper;


public class GenericDAO {

    private String dataSource;

    public GenericDAO(String dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Create a predicate by <fieldName,fieldValue> for filtering
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private <T> Predicate<T> createCriteria(String fieldName, String fieldValue){

        return p -> {
            try {
                Field field = p.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldObj = field.get(p);

		        if (fieldObj instanceof List ) {
                   return ((List)fieldObj).stream()
                            .anyMatch(x -> x.equals(fieldValue));
		        }

                return fieldObj.toString().equals(fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return false;
            }
        };
    }


    /**
     * Find T by field
     * @param fieldName
     * @param fieldValue
     * @return a stream of T
     */
    public <T> Flux<T> findByCriteria(String fieldName, String fieldValue, Class<T> valueType){

        Predicate<T> predicate = createCriteria(fieldName, fieldValue);
        Flux<T> tFlux = JsonHelper.readJSONFile(dataSource, valueType);

        return tFlux.filter(predicate);

    }
}
