package pl.cleankod.exchange.core.usecase;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import pl.cleankod.exchange.core.domain.Account;

import java.util.Currency;
import java.util.Optional;

public class FindAccountAndConvertCurrencyIfNeededUseCase {

    private final FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase;
    private final FindAccountUseCase findAccountUseCase;

    public FindAccountAndConvertCurrencyIfNeededUseCase(FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase, FindAccountUseCase findAccountUseCase) {
        this.findAccountAndConvertCurrencyUseCase = findAccountAndConvertCurrencyUseCase;
        this.findAccountUseCase = findAccountUseCase;
    }

    public ResponseEntity<Account> findAccountById(@Nullable String currency, Account.Id accountId) {
        return Optional.ofNullable(currency)
                .map(s ->
                        findAccountAndConvertCurrencyUseCase.execute(accountId, Currency.getInstance(s))
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build())
                )
                .orElseGet(() ->
                        findAccountUseCase.execute(accountId)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build())
                );
    }

    public ResponseEntity<Account> findAccountByNumber(@Nullable String currency, Account.Number accountNumber) {
        return Optional.ofNullable(currency)
                .map(s ->
                        findAccountAndConvertCurrencyUseCase.execute(accountNumber, Currency.getInstance(s))
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build())
                )
                .orElseGet(() ->
                        findAccountUseCase.execute(accountNumber)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build())
                );
    }
}
