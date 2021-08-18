package tran.billy.code.challenge.stream.connector;

public class StreamConnectorManager {

    static private StreamConnector connector;

    static public void registerDataStream(StreamConnector connector){
        StreamConnectorManager.connector = connector;
    }
    static public StreamConnector getStreamConnector(){
        return connector;
    }
}
