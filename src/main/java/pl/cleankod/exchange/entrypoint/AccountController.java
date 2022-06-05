package pl.cleankod.exchange.entrypoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cleankod.exchange.core.domain.Account;
import pl.cleankod.exchange.core.usecase.FindAccountAndConvertCurrencyIfNeededUseCase;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final FindAccountAndConvertCurrencyIfNeededUseCase findAccountAndConvertCurrencyIfNeededUseCase;

    public AccountController(FindAccountAndConvertCurrencyIfNeededUseCase findAccountAndConvertCurrencyIfNeededUseCase) {
        this.findAccountAndConvertCurrencyIfNeededUseCase = findAccountAndConvertCurrencyIfNeededUseCase;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable String id, @RequestParam(required = false) String currency) {
        Account.Id accountId = Account.Id.of(id);
        return findAccountAndConvertCurrencyIfNeededUseCase.findAccountById(currency, accountId);
    }

    @GetMapping(path = "/number={number}")
    public ResponseEntity<Account> findAccountByNumber(@PathVariable String number, @RequestParam(required = false) String currency) {
        Account.Number accountNumber = Account.Number.of(URLDecoder.decode(number, StandardCharsets.UTF_8));
        return findAccountAndConvertCurrencyIfNeededUseCase.findAccountByNumber(currency, accountNumber);
    }
}
