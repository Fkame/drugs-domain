package ru.drugsdomain.MasterParser.slave_parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
public class SlaveParserClientTest {
    private MockWebServer mockWebServer;

    private SlaveParserClient slaveParserClient;

    private int serverPort = 2208;

    @BeforeEach
    @SneakyThrows
    public void setup() {
        mockWebServer = new MockWebServer();
        slaveParserClient = new SlaveParserClient();
        mockWebServer.start(serverPort);
    }

    @Test
    @SneakyThrows
    public void checkRequest() {
        String port = String.valueOf(serverPort);
        String host = "localhost";
        String resource = "/resource";

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody("")
        );

        slaveParserClient.getDrugInfoFromDictionary(host, port, resource, List.of("1", "2", "3"));

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo(resource);
        System.out.println(request.getRequestLine());
    }

    @AfterEach
    @SneakyThrows
    public void shutdown() {
        mockWebServer.shutdown();
    }

    /*
    @Test
    public void testOnReal() {
        String port = "8082";
        String host = "localhost";
        String resource = "/ping";

        var response =
                slaveParserClient.getDrugInfoFromDictionary(host, port, resource, List.of("1", "2", "3"));
        assertThat(response).isNotNull();
    }
     */
}
