package com.cshiji.superviki.base.mybatis;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class SqlSessionFactory extends org.mybatis.spring.SqlSessionFactoryBean {
	private String mapperLocations = null;
	
	public SqlSessionFactory() {
	}

	public void setMapperLocations(String mapperLocations) {
		System.err.println("mapperLocations="+mapperLocations);
		this.mapperLocations = mapperLocations;
		
		ResourcePatternResolver resourcePatternResolver;
		
		try {
			resourcePatternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resourcePatternResolver.getResources(this.mapperLocations);
			System.err.println("resources="+resources);
			if(resources != null){
				System.err.println("resources.length="+resources.length);
				for (int i = 0; i < resources.length; i++) {
					System.err.println(resources[i]+"--->"+resources[i].getFilename());
				}
			}
			super.setMapperLocations(resources);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
