package br.com.orgn.resource;

import br.com.orgn.entity.dto.TransactionRequest;
import br.com.orgn.entity.dto.TransactionResponse;
import br.com.orgn.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

  @Autowired
  private TransactionService transactionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionResponse saveTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
    return transactionService.save(transactionRequest);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTransaction(@PathVariable UUID id) {
    transactionService.delete(id);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public TransactionResponse updateTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
    return transactionService.update(transactionRequest);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public TransactionResponse getTransaction(@PathVariable UUID id) {
    return transactionService.findById(id);
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public Page<TransactionResponse> getTransactions(@PageableDefault(size = 5, page = 0) Pageable pageable) {
    return transactionService.findAll(pageable);
  }
}
