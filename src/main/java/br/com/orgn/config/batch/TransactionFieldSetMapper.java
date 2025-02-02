package br.com.orgn.config.batch;

import br.com.orgn.entity.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {

  @Override
  public Transaction mapFieldSet(FieldSet fieldSet) {
    Transaction transaction = new Transaction();

    String dateString = fieldSet.readString("date");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDate date = LocalDate.parse(dateString, formatter);

    transaction.setDate(date);
    transaction.setType(fieldSet.readString("type"));
    transaction.setDescription(fieldSet.readString("description"));
    transaction.setAmount(fieldSet.readFloat("amount"));

    return transaction;
  }
}