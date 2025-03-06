package br.com.orgn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Balance {
  private Float incomes;
  private Float expenses;
  private Float balance;
}
