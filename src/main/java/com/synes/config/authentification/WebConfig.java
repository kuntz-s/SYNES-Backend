package com.synes.config.authentification;

/*
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsMappingConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
              //  WebConfigProperties.Cors cors = webConfigProperties.getCors();
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedMethods("GET","POST","PUT","DELETE")
                        .allowedHeaders(String.valueOf(List.of("Authorization","token", "Cache-Control", "Content-Type")))
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

}
*/
//@Component
//public class WebConfig implements Filter {
/*
    private final Logger log = LoggerFactory.getLogger(WebConfig.class);

    public WebConfig() {
        log.info("WebConfig init");
    }



    public WebMvcConfigurer corsMappingConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                //  WebConfigProperties.Cors cors = webConfigProperties.getCors();
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedMethods("GET","POST","PUT","DELETE")
                        .allowedHeaders(String.valueOf(List.of("Authorization","token", "Cache-Control", "Content-Type")))
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
*/
//}