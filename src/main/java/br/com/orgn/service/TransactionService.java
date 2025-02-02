package br.com.orgn.service;

import br.com.orgn.entity.Transaction;
import br.com.orgn.entity.dto.TransactionRequest;
import br.com.orgn.entity.dto.TransactionResponse;
import br.com.orgn.entity.exception.TransactionNotFoundException;
import br.com.orgn.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TransactionService {
  @Autowired
  private TransactionRepository transactionRepository;

  public TransactionResponse save(TransactionRequest transactionRequest) {
    Transaction savedTransaction = transactionRepository.save(convertTransactionRequestToTransaction(transactionRequest));

    TransactionResponse transactionResponse = new TransactionResponse(savedTransaction);

    log.info("c=TransactionService m=save transactionId={} msg=Saved successfully", transactionResponse.id());

    return transactionResponse;
  }

  public void delete(UUID id) {
    Transaction transactionToDelete = transactionRepository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(
            String.format("c=TransactionService m=delete transactionId=%s msg=Transaction not found", id)
    ));

    transactionRepository.delete(transactionToDelete);

    log.info("c=TransactionService m=delete msg=Deleted successfully");
  }

  public TransactionResponse update(TransactionRequest transactionRequest) {
    Transaction transactionToUpdate = convertTransactionRequestToTransaction(transactionRequest);
    transactionRepository.findById(transactionToUpdate.getId())
            .orElseThrow(() -> new TransactionNotFoundException(
                    String.format("c=TransactionService m=update transactionId=%s msg=Transaction not found", transactionToUpdate.getId())
            ));

    Transaction updatedTransaction = transactionRepository.save(transactionToUpdate);

    TransactionResponse transactionResponse = new TransactionResponse(updatedTransaction);

    log.info("c=TransactionService m=update transactionId={} msg=Updated successfully", transactionResponse.id());

    return transactionResponse;
  }

  public TransactionResponse findById(UUID id) {
    Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(
                    String.format("c=TransactionService m=findById transactionId=%s msg=Transaction not found", id)
            ));

    TransactionResponse transactionResponse = new TransactionResponse(transaction);

    log.info("c=TransactionService m=findById transactionId={} msg=Transaction found", transactionResponse.id());

    return transactionResponse;
  }

  public Page<TransactionResponse> findAll(Pageable pageable) {
    Page<Transaction> transactionsPage = transactionRepository.findAll(pageable);

    return transactionsPage.map(TransactionResponse::new);
  }

  private Transaction convertTransactionRequestToTransaction(TransactionRequest transactionRequest) {
    Transaction transaction = new Transaction();
    BeanUtils.copyProperties(transactionRequest, transaction);
    return transaction;
  }
}
