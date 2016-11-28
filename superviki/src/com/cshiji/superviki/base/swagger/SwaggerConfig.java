package com.cshiji.superviki.base.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfig
{
    private SpringSwaggerConfig springSwaggerConfig;
    /**
     * Required to autowireSpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
    {
       this.springSwaggerConfig = springSwaggerConfig;
    }
    /**
     * EverySwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. samecode base
     * multipleswagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation()
    {
       return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
    }
    private ApiInfo apiInfo()
    {
       ApiInfo apiInfo = new ApiInfo(
                "Api测试",
                "第一个swagger Api",
                "create by MrOrange",
                "chiji",
                "我是一头小毛炉",
                "我重来也不骑");
       return apiInfo;
    }
}