package tran.billy.code.challenge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.util.*;

import reactor.core.scheduler.Schedulers;
import tran.billy.code.challenge.stream.connector.StreamConnectorManager;


public class GenericDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDAO.class);

    private static final String ID = "id";

    private final String dataSource;

    Map<Object,Object> mainCache;
    HashMap<String, HashMap<String, List<Object>> > index;


    public GenericDAO(String dataSource) {
        this.dataSource = dataSource;
        mainCache = new HashMap<>();
        index = new HashMap<>();
    }

    /**
     * Add an object to cache and build inverted index
     * @param t an object T
     */
    <T> void addToCache(T t) {
            try {
                Field id = t.getClass().getField(ID);
                id.setAccessible(true);
                Object idObj = id.get(t);
                mainCache.put(idObj,t);

                Arrays.stream(t.getClass().getDeclaredFields()).forEach(field -> {
                    final Object fieldObj;
                    if (!field.getName().equals(ID) && !index.containsKey(field.getName())) {
                        index.put(field.getName(), new HashMap<>());

                        try {
                            fieldObj = field.get(t);

                            if (fieldObj instanceof List ) {
                                ((List<?>)fieldObj).forEach(item -> {
                                    if (!index.get(field.getName()).containsKey(item.toString())){
                                        index.get(field.getName()).put(item.toString(), new ArrayList<>());
                                    }
                                    index.get(field.getName()).get(item.toString()).add(idObj);
                                });

                            } else {
                                if (!index.get(field.getName()).containsKey(fieldObj.toString())) {
                                    index.get(field.getName()).put(fieldObj.toString(), new ArrayList<>());
                                }
                                index.get(field.getName()).get(fieldObj.toString()).add(idObj);
                            }
                        } catch (IllegalAccessException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                });

            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOGGER.error(e.getMessage());
            }
    }

    /**
     * Build inverted index of Class<T>
     * @param dataType Class<T>
     */
    <T> void buildCache(Class<T> dataType){

        StreamConnectorManager.getStreamConnector().getData(dataSource, dataType)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Flux.just();
                }).parallel()
                .runOn(Schedulers.boundedElastic())
                .subscribe(this::addToCache);
    }

    /**
     * Find T by field
     * @param fieldName field name
     * @param fieldValue field value
     * @return T t from cache
     */
    public <T> List<T> findByCriteria(String fieldName, String fieldValue){
        List<Object> idx = index.get(fieldName).get(fieldValue);
        List<T> result = new ArrayList<>();
        idx.forEach(id -> result.add((T) mainCache.get(id)));

        return result;
    }
}
