package com.caiolobo.desafiopicpay.account;

import com.caiolobo.desafiopicpay.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Account account;
    @Test
    void itShouldThrowInsufficientFundsWhenWithdrawing() {

        //GIVEN
        BDDMockito.given(account.getSaldo()).willReturn(BigDecimal.valueOf(5));
        BDDMockito.given(accountRepository.findById(1L)).willReturn(Optional.of(account));

        //WHEN + ASSERT
        Assertions.assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(1L, BigDecimal.valueOf(10)));

    }

}