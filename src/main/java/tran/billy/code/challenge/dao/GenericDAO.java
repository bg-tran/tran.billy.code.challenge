package tran.billy.code.challenge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.scheduler.Schedulers;
import tran.billy.code.challenge.dao.exception.EntityNotFoundException;
import tran.billy.code.challenge.stream.connector.StreamConnectorManager;


public class GenericDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDAO.class);

    static final String ID = "id";

    private final String dataSource;

    ConcurrentHashMap<Object,Object> mainCache;
    ConcurrentHashMap<String, ConcurrentHashMap<String, List<Object>> > index;


    public GenericDAO(String dataSource) {
        this.dataSource = dataSource;
        mainCache = new ConcurrentHashMap<>();
        index = new ConcurrentHashMap<>();
    }

    /**
     * Add an object to cache and build inverted index
     * @param t an object T
     */
    <T> void addToCache(T t) {
            try {
                Field id = t.getClass().getDeclaredField(ID);
                id.setAccessible(true);
                Object idObj = id.get(t);
                mainCache.put(idObj,t);
                Object fieldObj;
                for(Field field : t.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    fieldObj = field.get(t);
                    if (fieldObj != null
                            && Modifier.isPrivate(field.getModifiers())
                            && !field.getName().equals(ID)) {

                        if (!index.containsKey(field.getName())) {
                            index.put(field.getName(), new ConcurrentHashMap<>());
                        }

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

                    }
                }

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
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .subscribe(this::addToCache);
    }

    /**
     * Find T by field
     * @param fieldName field name
     * @param fieldValue field value
     * @return List<T> from cache
     */
    public <T> List<T> findByCriteria(String fieldName, String fieldValue){
        List<Object> idx = index.get(fieldName).get(fieldValue);
        List<T> result = new ArrayList<>();
        if (idx != null) {
            idx.forEach(id -> result.add((T) mainCache.get(id)));
        }
        return result;
    }

    /**
     * Find T by ID
     * @param fieldValue field value
     * @return T t from cache
     * @throws EntityNotFoundException if not found
     */
    public <T> T findByID(Object fieldValue) throws EntityNotFoundException {

        T entity = (T) mainCache.get(fieldValue);

        if (entity == null)
            throw new EntityNotFoundException("Entity not found : " + fieldValue);

        return entity;
    }
}
