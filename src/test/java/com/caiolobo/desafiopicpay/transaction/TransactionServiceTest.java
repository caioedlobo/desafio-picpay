package com.caiolobo.desafiopicpay.transaction;

import com.caiolobo.desafiopicpay.account.Account;
import com.caiolobo.desafiopicpay.account.AccountService;
import com.caiolobo.desafiopicpay.account.AccountType;
import com.caiolobo.desafiopicpay.exceptions.InsufficientFundsException;
import com.caiolobo.desafiopicpay.exceptions.ValidateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private Account account;

    @Test
    void itShouldThrowInsufficientFundsWithdrawing() {
        Long id = 1L;
        BigDecimal value = new BigDecimal(5);
        //GIVEN
        BDDMockito.given(accountService.searchAccount(id)).willReturn(account);
        BDDMockito.given(account.getBalance()).willReturn(value);

        //WHEN + ASSERT
        assertThrows(InsufficientFundsException.class, () -> transactionService.withdraw(id, BigDecimal.valueOf(10)));

    }

    @Test
    void itShouldWithdraw() {
        //GIVEN
        BigDecimal value = new BigDecimal(20);
        Long id = 1L;

        BDDMockito.given(accountService.searchAccount(id)).willReturn(account);
        BDDMockito.given(account.getBalance()).willReturn(value);

        //WHEN
        transactionService.withdraw(id, value);

        //THEN
        BDDMockito.verify(accountService).searchAccount(id);
        BDDMockito.verify(transactionRepository).withdraw(id, value);

    }

    @Test
    void itShouldThrowValidateException(){
        //GIVEN
        Transaction transaction = new Transaction();
        transaction.setPayer(1L);
        transaction.setPayee(2L);
        transaction.setValue(new BigDecimal(100));

        Account account = new Account();
        account.setType(AccountType.PJ.getValue());
        account.setBalance(new BigDecimal(200));

        BDDMockito.given(accountService.searchAccount(transaction.getPayer())).willReturn(account);

        //WHEN + THEN
        assertThrows(ValidateException.class, () ->transactionService.transfer(transaction));
    }

    @Test
    public void itShouldThrowInsufficientFundsOnValidate() {
        // GIVEN
        Transaction transaction = new Transaction();
        transaction.setPayer(1L);
        transaction.setPayee(2L);
        transaction.setValue(new BigDecimal(100));

        Account account = new Account();
        account.setType(AccountType.PF.getValue());
        account.setBalance(new BigDecimal(99));

        when(accountService.searchAccount(transaction.getPayer())).thenReturn(account);

        // WHEN + THEN
        assertThrows(InsufficientFundsException.class, () -> transactionService.transfer(transaction));
    }


}