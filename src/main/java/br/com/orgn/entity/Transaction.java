package br.com.orgn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private LocalDate date;
  private String type;
  private String description;
  private Float amount;
}
