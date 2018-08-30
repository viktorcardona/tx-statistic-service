package com.tx.controllers;

import com.tx.model.Transaction;
import com.tx.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class TransactionController {

    public static final String URL = "/transactions";

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(URL)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@Valid @RequestBody Transaction transaction){
        this.transactionService.addTransaction(transaction);
    }

    @DeleteMapping(URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransactions(){
        this.transactionService.deleteAllTransactions();
    }

}
