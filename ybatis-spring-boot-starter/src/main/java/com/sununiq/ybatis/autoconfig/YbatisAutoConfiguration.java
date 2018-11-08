package com.sununiq.ybatis.autoconfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import com.sununiq.Ybatis;
import com.sununiq.configure.DefaultConfigure;
import com.sununiq.configure.IConfigure;
import com.sununiq.exception.YbatisException;
import com.sununiq.loader.YbatisLoader;

import javax.annotation.PostConstruct;

import java.util.List;

@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(YbatisProperties.class)
public class YbatisAutoConfiguration {

  @Autowired
  private List<SqlSessionFactory> sqlSessionFactories;

  @Autowired(required = false)
  private IConfigure configure;

  @Autowired
  private YbatisProperties properties;

  @PostConstruct
  public void ybatisLoader() {
    if (!"true".equalsIgnoreCase(properties.getEnable())) {
      return;
    }

    doConfigure();

    doRebuildMapper();
  }

  private void doRebuildMapper() {
    if (CollectionUtils.isEmpty(sqlSessionFactories)) {
      return;
    }

    for (final SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
      MapperRegistry mapperRegistry = sqlSessionFactory.getConfiguration().getMapperRegistry();
      if (CollectionUtils.isEmpty(mapperRegistry.getMappers())) {
        continue;
      }

      String mapperLocations = mapperRegistry.getMappers().iterator().next().getPackage().getName();
      YbatisLoader loader =
          new YbatisLoader(sqlSessionFactory, Ybatis.INSTANCE.getTemplateLocation(), mapperLocations);

      String[] domainLocationArray = properties.getDomainLocations().split(";");
      for (String domainLocation : domainLocationArray) {
        loader.add(domainLocation);
      }

      String domainClassArrayStr = properties.getDomainClasses();
      if (StringUtils.isNotBlank(domainClassArrayStr)) {
        String[] domainClassArray = domainClassArrayStr.split(";");
        for (String domainClassUri : domainClassArray) {
          try {
            Class<?> domainClass = Class.forName(domainClassUri);
            loader.add(domainClass);
          } catch (ClassNotFoundException e) {
            throw new YbatisException(e);
          }
        }
      }

      loader.build();
    }
  }

  private void doConfigure() {
    if (configure == null) {
      configure = new DefaultConfigure();
    }
    if (StringUtils.isNotBlank(properties.getOutputLocation())) {
      Ybatis.INSTANCE.setOutputLocation(properties.getOutputLocation());
    }

    configure.configNodeProcessor(Ybatis.INSTANCE.getAssistHandlers());
    configure.configWrapper(
        Ybatis.INSTANCE.getJdbcNameWrappers(),
        Ybatis.INSTANCE.getTableNameWrappers(),
        Ybatis.INSTANCE.getMapperNameWrappers(),
        Ybatis.INSTANCE.getJdbcTypeWrappers()
    );
  }
}
