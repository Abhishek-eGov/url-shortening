package org.egov.url.shortening.utils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class HashIdConverterTest {

@Mock
HashIdConverter hashIdConverter;

    @Test
    void testCreateHashStringForId() {
        assertEquals(null, hashIdConverter.createHashStringForId(1L));

    }


    @Test
    void testGGetIdForString() {

        assertEquals(0L, hashIdConverter.getIdForString("https://www.youtube.com/watch?v=Aasp0mWT3Ac&ab_channel=rieckpil").longValue());

    }
}

