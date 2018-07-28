package org.dedunu.transaction.controller;

import org.dedunu.transaction.model.Statistics;
import org.dedunu.transaction.model.Transaction;
import org.dedunu.transaction.manager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TransactionController {
    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @RequestMapping("/")
    public String index() {
        return "Greetings from Transaction Demo!";
    }

    @PostMapping("/transaction")
    ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        boolean isSuccess = TransactionManager.INSTANCE.addTransaction(transaction);

        if (isSuccess) {
            logger.debug("Successful transaction " + transaction.toString());
            return ResponseEntity.ok().build();
        } else {
            logger.debug("Invalid transaction " + transaction.toString());
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping("/statistics")
    @ResponseBody
    public Statistics getStatistics() {
        return TransactionManager.INSTANCE.getStatistics();
    }
}