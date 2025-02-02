package br.com.orgn.entity.dto;

import br.com.orgn.entity.Transaction;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record TransactionResponse (
        UUID id,
        LocalDate date,
        String type,
        String description,
        Float amount
) {
  public TransactionResponse(Transaction transaction) {
    this(transaction.getId(), transaction.getDate(), transaction.getType(), transaction.getDescription(), transaction.getAmount());
  }
}
