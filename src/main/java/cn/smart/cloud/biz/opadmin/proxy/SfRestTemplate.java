package cn.smart.cloud.biz.opadmin.proxy;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

@Component
class SfRestTemplate {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Gson gson;

    @Bean
    public HttpHeaders initHeader() {
        String plainCredentials = "user:sf2015";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.parseMediaType("application/*+json")));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    @Autowired
    HttpHeaders headers;

    @Bean
    public HttpEntity<String> initHttpEntity() {
        return new HttpEntity<>(headers);
    }

    @Autowired
    HttpEntity<String> request;

    public <T> T get(String url, Class<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.GET, request, responseType).getBody();
    }

    public <T> T post(String url, Object src, Class<T> responseType) {
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(src), headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, responseType).getBody();
    }

    public <T> T post(String url, Map<String, String> params, Class<T> responseType) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, responseType, params).getBody();
    }

    public void delete(String url) {
        restTemplate.delete(url);
    }

    public <T> T postFile(String url, File file, Class<T> responseType, Map<String, ?> params) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");
        headers.set("Accept", "text/plain");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parameters, headers);
        if (params == null || params.isEmpty()) {
            return restTemplate.postForObject(url, httpEntity, responseType);
        } else {
            return restTemplate.postForObject(url, httpEntity, responseType, params);
        }
    }
}
