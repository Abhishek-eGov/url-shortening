package org.egov.url.shortening.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.egov.url.shortening.model.ShortenRequest;
import org.egov.url.shortening.producer.Producer;
import org.egov.url.shortening.repository.URLRepository;
import org.egov.url.shortening.repository.UrlDBRepository;
import org.egov.url.shortening.utils.HashIdConverter;
import org.egov.url.shortening.validator.URLValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class URLConverterServiceTest {
    @Mock
    private Boolean aBoolean;

    @Mock
    private HashIdConverter hashIdConverter;

    @Autowired
    private List<URLRepository> list;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Producer producer;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private URLConverterService urlConverterService;


    private URLRepository urlRepository;

    @Mock
    private URLValidator urlValidator;


    @Test
    void testGetUserUUIDSuccess() throws RestClientException {

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn("Post For Object");
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).getUserUUID("foo"));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());
    }


    @Test
    void testGetUserUUIDMapUser() throws RestClientException {

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn(new HashMap<>());
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).getUserUUID("foo"));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());
    }


    @Test
    void testGetUserUUIDNull() throws RestClientException {

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn(null);
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).getUserUUID("foo"));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());
    }


    @Test
    void testGetUserUUIDErrorOccur() throws RestClientException {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenThrow(new CustomException("type", "An error occurred"));
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).getUserUUID("foo"));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());
    }


    @Test
    void testGetUserUUIDUserNotFound() throws RestClientException {
        HashMap<Object, Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put("Key", "Value");
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn(objectObjectMap);
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).getUserUUID("foo"));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());
    }


    @Test
    void testIndexData()  {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).indexData("https://www.youtube.com/watch?v=Aasp0mWT3Ac&ab_channel=rieckpil","1"));

    }

  /*  @Test
    void testGetLongURLFromIDIsEmpty() throws Exception {
        HashMap<Object, Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put("Key", "Value");
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn(objectObjectMap);
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        assertNull(
                (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).getLongURLFromID("1as"));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());

    }



   @Test
    void testShortenURL() throws Exception  {
       RestTemplate restTemplate = mock(RestTemplate.class);
       ArrayList<URLRepository> urlRepositories = new ArrayList<>();
       ObjectMapper objectMapper = new ObjectMapper();
       assertNull(
               (new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer())).shortenURL(new ShortenRequest("1","https://www.youtube.com/watch?v=Aasp0mWT3Ac&ab_channel=rieckpil",1L,5L)));

    }
*/

}

