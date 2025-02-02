package br.com.orgn.config.batch;

import br.com.orgn.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction> {
  @Override
  public Transaction process(Transaction item) throws Exception {
    return item;
  }
}
