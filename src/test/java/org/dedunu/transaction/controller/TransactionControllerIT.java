package org.dedunu.transaction.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URL;

import org.dedunu.transaction.model.Statistics;
import org.dedunu.transaction.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Transaction Demo!"));
    }

    @Test
    public void firstGetTransaction() throws Exception {
        ResponseEntity<Statistics> response = template.getForEntity(base.toString() + "statistics",
                Statistics.class);
        assertThat(response.getBody().getCount(), equalTo(0L));
        assertThat(response.getBody().getMin(), equalTo(0.0));
        assertThat(response.getBody().getMax(), equalTo(0.0));
        assertThat(response.getBody().getSum(), equalTo(0.0));
        assertThat(response.getBody().getAvg(), equalTo(0.0));
    }


    @Test
    public void olderTransactionUpdate() throws Exception {
        Transaction transaction = new Transaction(1000.0, System.currentTimeMillis() - 61000);

        HttpEntity<Transaction> entity = new HttpEntity<>(transaction, new HttpHeaders());

        ResponseEntity<String> response = template.exchange(
                base.toString() + "transaction",
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void futureTransactionUpdate() throws Exception {
        Transaction transaction = new Transaction(1000.0, System.currentTimeMillis() + 1000);

        HttpEntity<Transaction> entity = new HttpEntity<>(transaction, new HttpHeaders());

        ResponseEntity<String> response = template.exchange(
                base.toString() + "transaction",
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

