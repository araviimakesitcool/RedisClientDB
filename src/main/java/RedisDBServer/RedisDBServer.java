package RedisDBServer;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;


@Getter
@Setter
public class RedisDBServer {

    private Jedis jedis;
    public RedisDBServer(String hostName,int port,String password) {
        createRedisConection(hostName,port,password);
    };

    public void createRedisConection(String hostName,int port,String password){
        jedis = new Jedis(hostName,port);
        setJedis(jedis);

        if(!password.equals("")) jedis.auth(password);
    }

    public boolean isHealthGood(){
      return ping().equals("PONG");
    }
    public String ping(){
        return jedis.ping();
    }
    public boolean selectDatabase(int databaseNumber){
        try {
            String set = jedis.select(databaseNumber);
            return true;
        }
        catch (JedisDataException jedisDataException){
            return false;
        }
    }
    public String getValue(String key){
        return jedis.get(key);
    }
    public String setValue(String key,String value){
        return  jedis.set(key,value);
    }
    public long returnDBSize(int databaseNumber){
        try {
            int oldDataBaseNumber = jedis.getDB();
             jedis.select(databaseNumber);
            long size = jedis.dbSize();
            jedis.select(oldDataBaseNumber);
            return size;
        }
        catch (JedisDataException jedisDataException){
            return -1;
        }
    }


}
