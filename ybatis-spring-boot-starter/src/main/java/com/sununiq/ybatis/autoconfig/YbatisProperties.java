package com.sununiq.ybatis.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(prefix = "ybatis")
public class YbatisProperties {

  private Properties properties = new Properties();

  public Properties getProperties() {
    return properties;
  }

  public String getDomainLocations() {
    return properties.getProperty("domainLocations");
  }

  public String getDomainClasses() {
    return properties.getProperty("domainClasses");
  }

  public String getOutputLocation() {
    return properties.getProperty("outputLocation");
  }

  public String getEnable() {
    return properties.getProperty("enable");
  }


  public void setDomainLocations(String domainLocations) {
    properties.setProperty("domainLocations", domainLocations);
  }

  public void setDomainClasses(String domainClasses) {
    properties.setProperty("domainClasses", domainClasses);
  }

  public void setOutputLocation(String outputLocation) {
    properties.setProperty("outputLocation", outputLocation);
  }

  public void setEnable(String enable) {
    properties.setProperty("enable", enable);
  }
}
