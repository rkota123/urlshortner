package com.example.demo.controller;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;


@RestController
@RequestMapping(value = "/rest/url")
public class UrlShortnerController {
	Logger logger = LoggerFactory.getLogger(UrlShortnerController.class);
	private Map<String,String> urlToIdMap = new HashMap<String,String>();
	private Map<String,String> idToUrlMap = new HashMap<String,String>();
	
	
	 @PostMapping
	    public String create(@RequestBody String url) {

	        UrlValidator urlValidator = new UrlValidator(
	                new String[]{"http", "https"}
	        );

	        if (urlValidator.isValid(url)) {
	        	logger.info("Valid URL......");
	        	if(urlToIdMap.containsKey(url)) {
	        		logger.info("Getting  URL......:" + urlToIdMap.get(url));
	        		return urlToIdMap.get(url);
	        	}
	        		
	            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
	            logger.info("URL Id generated: "+ id);
	            urlToIdMap.put(url, id);
	            idToUrlMap.put(id, url);
	            return id;
	        }

	        throw new RuntimeException("URL Invalid: " + url);
	    }
	
	
	 @GetMapping("/{id}")
	    public String getUrl(@PathVariable String id) {
		 
		 String url = idToUrlMap.get(id);
		 
		 if (url == null) {
	            throw new RuntimeException("There is no shorter URL for : " + id);
	        }

	       
	        return url;
	    }

}
