package com.zbcn.demo.config;

import com.zbcn.demo.redis.RedisUtil;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * redis 配置
 *
 * @author Administrator
 * @date 2019/1/16 18:16
 */
@Data
@Component
@ConfigurationProperties(prefix="spring.redis")
public class RedisConfig {

    private String host;

    private Integer port;

    private String password;
    /**
     * #客户端超时时间单位是毫秒 默认是2000
     */
    private Integer timeout;
    /**
     * #最大空闲数
     */
    private Integer maxIdle;

    /**
     * #连接池的最大数据库连接数。设为0表示无限制,如果是jedis 2.4以后用redis.maxTotal
     * redis.maxActive=600
     * 控制一个pool可分配多少个jedis实例,用来替换上面的redis.maxActive,如果是jedis 2.4以后用该属性
     */
    private Integer maxTotal;
    /**
     * #最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
     */
    private Integer maxWaitMillis;

    /**
     * #连接的最小空闲时间 默认1800000毫秒(30分钟)
     */
    private Integer minEvictableIdleTimeMillis;
    /**
     * #每次释放连接的最大数目,默认3
     */
    private Integer numTestsPerEvictionRun;

    /**
     * #逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
     */
    private Integer timeBetweenEvictionRunsMillis;
    /**
     * #是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
     */
    private boolean testOnBorrow;

    /**
     * #在空闲时检查有效性, 默认false
     */
    private boolean testWhileIdle;

    /*******集群****/
    /**
     * 结点个数
     */
    private String clusterNodes;

    /**
     *结点最大重定向个数
     */
    private Integer mmaxRedirectsac;

    /*******哨兵****/
    /**
     * 哨兵
     */
    private String senHost1;
    private Integer senPort1;


    /**
     * JedisPoolConfig 连接池
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    /**
     * 单机版配置
     * @Title: JedisConnectionFactory
     * @param @param jedisPoolConfig
     * @param @return
     * @return JedisConnectionFactory
     * @throws
     */
    @Bean
    public JedisConnectionFactory JedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        //连接池
        JedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        //IP地址
        JedisConnectionFactory.setHostName(host);
        //端口号
        JedisConnectionFactory.setPort(port);
        //如果Redis设置有密码
        //JedisConnectionFactory.setPassword(password);
        //客户端超时时间单位是毫秒
        JedisConnectionFactory.setTimeout(timeout);
        return JedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String,Object>  functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     *
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }


//    /**
//     * Redis集群的配置
//     * @return RedisClusterConfiguration
//     * @autor lpl
//     * @date 2017年12月22日
//     * @throws
//     */
//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration(){
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        //Set<RedisNode> clusterNodes
//        String[] serverArray = clusterNodes.split(",");
//
//        Set<RedisNode> nodes = new HashSet<RedisNode>();
//
//        for(String ipPort:serverArray){
//            String[] ipAndPort = ipPort.split(":");
//            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
//        }
//
//        redisClusterConfiguration.setClusterNodes(nodes);
//        redisClusterConfiguration.setMaxRedirects(mmaxRedirectsac);
//
//        return redisClusterConfiguration;
//    }

    /**
     * 配置redis的哨兵
     * @return RedisSentinelConfiguration
     * @autor lpl
     * @date 2017年12月21日
     * @throws
     */
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置matser的名称
        RedisNode redisNode = new RedisNode(host, port);
        redisNode.setName("mymaster");
        redisSentinelConfiguration.master(redisNode);
        //配置redis的哨兵sentinel
        RedisNode senRedisNode = new RedisNode(senHost1,senPort1);
        Set<RedisNode> redisNodeSet = new HashSet<>();
        redisNodeSet.add(senRedisNode);
        redisSentinelConfiguration.setSentinels(redisNodeSet);
        return redisSentinelConfiguration;
    }


    /**
     * 注入封装RedisTemplate
     * @Title: redisUtil
     * @return RedisUtil
     * @autor lpl
     * @date 2017年12月21日
     * @throws
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }


}
