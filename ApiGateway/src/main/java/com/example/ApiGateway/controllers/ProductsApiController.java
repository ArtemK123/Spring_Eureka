package com.example.ApiGateway.controllers;

import java.io.IOException;

import com.example.ApiGateway.config.ApplicationPropertiesProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@RestController
public class ProductsApiController {
    private final String CONTENT_TYPE_HEADER_NAME = "Content-type";
    private final String JSON_CONTENT_TYPE_HEADER_VALUE = "application/json";

    private DefaultHttpClient  defaultHttpClient;
    private String productsApiUrl;

    @Autowired
    public ProductsApiController(ApplicationPropertiesProvider applicationPropertiesProvider) {
        this.defaultHttpClient = new DefaultHttpClient();
        this.productsApiUrl = applicationPropertiesProvider.get("products.api.url");
    }
 
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "<span>Hello from ApiGateway</span>";
    }
 
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll() throws IOException {
        HttpGet request = new HttpGet(productsApiUrl);
        HttpResponse response = defaultHttpClient.execute(request);
        String productsJson = EntityUtils.toString(response.getEntity());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE_HEADER_NAME, JSON_CONTENT_TYPE_HEADER_VALUE);
        
        return new ResponseEntity<Object>(productsJson, responseHeaders, HttpStatus.OK);
    }
 
    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getById(@PathVariable Integer id) throws IOException {
        HttpGet request = new HttpGet(productsApiUrl + id);
        HttpResponse response = defaultHttpClient.execute(request);
        String productJson = EntityUtils.toString(response.getEntity());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE_HEADER_NAME, JSON_CONTENT_TYPE_HEADER_VALUE);

        return new ResponseEntity<Object>(productJson, responseHeaders, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String add(@RequestBody String productJson) throws IOException {
        HttpPost postRequest = new HttpPost(productsApiUrl);

        StringEntity input = new StringEntity(productJson);
        input.setContentType("application/json");
        postRequest.setEntity(input);

        HttpResponse response = defaultHttpClient.execute(postRequest);
        return EntityUtils.toString(response.getEntity());
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable Integer id, @RequestBody String productJson) throws IOException {
        HttpPut putRequest = new HttpPut(productsApiUrl + id);

        StringEntity input = new StringEntity(productJson);
        input.setContentType("application/json");
        putRequest.setEntity(input);

        HttpResponse response = defaultHttpClient.execute(putRequest);
        return EntityUtils.toString(response.getEntity());
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(productsApiUrl + id);

        HttpResponse response = defaultHttpClient.execute(deleteRequest);
        return EntityUtils.toString(response.getEntity());
    }
}