package com.zbcn.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "elastic")
public class EsConfig {

    private Map<String, Conversion> modelConversions;

    private Map<String,Conversion>  storeModelConversions;

    private Map<String,Conversion> approvedStoreModelConversions;

    @Data
    public static class Conversion {
        private List<String> includes = new ArrayList<>();
        private List<String> excludes = new ArrayList<>();
        private List<Mapping> mappings = new ArrayList<>();
    }

    @Data
    public static class Mapping {
        private String source;
        private String target;
        private String action;
    }

    private InternalApi internalApi;

    @Data
    public static class InternalApi {
        private Map<String, String> urls;
        private Authentication authentication;
    }

    @Data
    public static class Authentication {
        private String authUrl;
        private String username;
        private String password;
        private int tokenExpire;
    }

    private List<EsIndexConfig> esIndexConfigs;

    @Data
    public static class EsIndexConfig {
        private String bizCode;
        private String clusterName;
        private String indexName;
        private String indexType;
        private String documentClass;
    }
    /**
     * 因為使用別名均為v，所以 type不可能存在重複，並且同一索引的主索引和備索引cluster肯定一致，所以可通過type直接獲取cluster
     * @param bizCode
     * @return
     */
    public EsIndexConfig getIndexConfigByCode(String bizCode){
        for(EsIndexConfig indexConfig : esIndexConfigs){
            if(indexConfig.getBizCode().equals(bizCode)){
                return indexConfig;
            }
        }
        return null;
    }
}
