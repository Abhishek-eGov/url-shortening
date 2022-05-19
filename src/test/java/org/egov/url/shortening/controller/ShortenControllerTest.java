package org.egov.url.shortening.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.egov.url.shortening.model.ShortenRequest;
import org.egov.url.shortening.producer.Producer;
import org.egov.url.shortening.repository.URLRepository;
import org.egov.url.shortening.repository.UrlDBRepository;
import org.egov.url.shortening.service.URLConverterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ShortenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private  URLConverterService urlConverterService;

    @MockBean
    private UrlDBRepository urlDBRepository;

    @Test
    public void ShortenUrlPostFail() throws Exception {
        mockMvc.perform(post("/shortener")
                        .accept(MediaType.APPLICATION_JSON).content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ShortenUrlPostSuccess() throws Exception {
        ShortenRequest shortenRequest = new ShortenRequest();
        shortenRequest.setUrl("https://www.youtube.com/watch?v=Aasp0mWT3Ac&ab_channel=rieckpil");
        shortenRequest.setId("10");
        shortenRequest.setValidFrom(1L);
        shortenRequest.setValidTill(18L);
        ObjectMapper objectMapper = new ObjectMapper();
        String Json = objectMapper.writeValueAsString(shortenRequest);

        mockMvc.perform(post("/shortener")
                        .accept(MediaType.APPLICATION_JSON).content(Json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



   /* @Test
    public void ShortenUrlGetFail() throws Exception {
        mockMvc.perform(get("/id")
                        .accept(MediaType.APPLICATION_JSON).content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ShortenUrlGetSuccess0() throws Exception {
        mockMvc.perform(get("/8")
                        .accept(MediaType.APPLICATION_JSON).content("")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void ShortenUrlGetSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/8");
        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .string("GET Response");
        mockMvc.perform(builder)
                .andExpect(contentMatcher)
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ShortenUrlGetSuccess1() throws Exception {
        String id = "8";
        ShortenRequest shortenRequest = new ShortenRequest("8","https://example.org/example",1L,5L);
        when(urlConverterService.getLongURLFromID(id)).thenReturn(String.valueOf(Optional.of(shortenRequest)));
        mockMvc.perform(get("/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.url").value(shortenRequest.getUrl()))
                .andExpect(jsonPath("$.ValidFrom").value(shortenRequest.getValidFrom()))
                .andExpect(jsonPath("$.ValidTill").value(shortenRequest.getValidTill()))
                .andDo(print());
    }
*/
    @Test
    void ShortenUrlSetUrl() throws Exception {
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = mock(RestTemplate.class);
        ShortenController shortenController = new ShortenController(
                new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer()));
        ShortenRequest shortenRequest = new ShortenRequest();
        shortenRequest.setUrl("https://example.org/example");

    }

    @Test
    void ShortenUrl() throws Exception {
        ArrayList<URLRepository> urlRepositories = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = mock(RestTemplate.class);
        ShortenController shortenController = new ShortenController(
                new URLConverterService(urlRepositories, objectMapper, restTemplate, new Producer()));
        ShortenRequest shortenRequest = new ShortenRequest();
        shortenRequest.setUrl("Shortening {}");
        assertThrows(CustomException.class, () -> shortenController.shortenUrl(shortenRequest));
    }
}

