package br.com.orgn.resource;

import br.com.orgn.entity.Balance;
import br.com.orgn.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balances")
@CrossOrigin(origins = "http://localhost:5173")
public class BalanceResource {
  @Autowired
  private BalanceService balanceService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Balance getBalances() {
    return balanceService.calculate(Pageable.unpaged());
  }
}
