package cn.com.jrj.m.config;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix="spring.redis")
public class JedisClusterConfig {
	
	private String clusterNodes;	//	集群节点，逗号分开
	private int connectionTimeout;	//	链接超时时间
	private int soTimeout;	//	
	private int maxAttempts;
	private String password;
	private int poolMaxActive;	//	连接池最大连接数量
	private int poolMaxIdle;	//	链接池最大空闲数量
	private int poolMaxWait;	//	连接词分配等待时间
	private int poolMinIdle;	//	连接池最小空闲数量
	
    @Bean
    public JedisCluster jedisCluster() {
        String[] nodes = clusterNodes.split(",");
        log.info(clusterNodes);
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>(nodes.length);
        for (String node : nodes) {
            String[] nodeAttrs = node.trim().split(":");
            HostAndPort hap = new HostAndPort(nodeAttrs[0], Integer.valueOf(nodeAttrs[1]));
            jedisClusterNode.add(hap);
        }
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(poolMaxActive);
//        poolConfig.setMaxWaitMillis(poolMaxWait);
//        poolConfig.setMinIdle(poolMinIdle);
//        poolConfig.setMaxIdle(poolMaxIdle);
        // TODO:password and poolconfig
        return new JedisCluster(jedisClusterNode, new JedisPoolConfig());
//        return new JedisCluster(jedisClusterNode, connectionTimeout, soTimeout, maxAttempts, poolConfig);
//      return new JedisCluster(jedisClusterNode, connectionTimeout, soTimeout, maxAttempts,password, poolConfig);
    }
}
