package br.com.orgn.service;

import br.com.orgn.entity.Balance;
import br.com.orgn.entity.dto.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BalanceService {
  @Autowired
  TransactionService transactionService;

  public Balance calculate(Pageable pageable) {
    Page<TransactionResponse> transactions = transactionService.findAll(pageable);

    Float incomes = transactions.stream()
            .map(TransactionResponse::amount)
            .filter(amount -> amount > 0)
            .reduce(0f, Float::sum);
    Float expenses = transactions.stream()
            .map(TransactionResponse::amount)
            .filter(amount -> amount < 0)
            .map(Math::abs)
            .reduce(0f, Float::sum);
    Float balance = incomes - expenses;

    Balance balanceResponse = new Balance(incomes, expenses, balance);

    log.info("c=BalanceService m=calculate msg=Balance calculated successfully");

    return balanceResponse;
  }
}
