package br.com.orgn.entity.exception;

public class TransactionNotFoundException extends RuntimeException {
  public TransactionNotFoundException(String message) {
    super(message);
  }
}
