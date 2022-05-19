package org.egov.url.shortening.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.url.shortening.model.ShortenRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.clients.jedis.Jedis;

@ContextConfiguration(classes = {URLRedisRepository.class})
@ExtendWith(SpringExtension.class)
class URLRedisRepositoryTest {
    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    private URLRedisRepository uRLRedisRepository;


    @Test
    void IncrementID() {
        Jedis jedis = mock(Jedis.class);
        when(jedis.incr((String) any())).thenReturn(1L);
        assertEquals(0L,
                (new URLRedisRepository(jedis, "https://example.org/example", "https://example.org/example")).incrementID()
                        .longValue());
        verify(jedis).incr((String) any());
    }

    @Test

    void testSaveUrl() throws JsonProcessingException {
        when(this.jdbcTemplate.execute((String) any(),
                (org.springframework.jdbc.core.PreparedStatementCallback<Boolean>) any())).thenReturn(Boolean.valueOf("42"));
        this.uRLRedisRepository.saveUrl("https://example.org/example", new ShortenRequest());
        verify(this.jdbcTemplate).execute((String) any(),
                (org.springframework.jdbc.core.PreparedStatementCallback<Boolean>) any());

    }

    @Test

    void testGetUrl() throws Exception {
        when(this.jdbcTemplate.queryForObject((String) any(), (Object[]) any(), (Class<String>) any()))
                .thenReturn("Query For Object");
        assertEquals("Query For Object", this.uRLRedisRepository.getUrl(123L));
        verify(this.jdbcTemplate).queryForObject((String) any(), (Object[]) any(), (Class<String>) any());

    }
}

