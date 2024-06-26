package com.caiolobo.desafiopicpay.transaction;

import com.caiolobo.desafiopicpay.account.Account;
import com.caiolobo.desafiopicpay.account.AccountService;
import com.caiolobo.desafiopicpay.account.AccountType;
import com.caiolobo.desafiopicpay.authorization.AuthorizationService;
import com.caiolobo.desafiopicpay.exceptions.InsufficientFundsException;
import com.caiolobo.desafiopicpay.exceptions.ValidateException;
import com.caiolobo.desafiopicpay.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private Account account;
    @Mock
    private NotificationService notificationService;

    @Test
    void itShouldTransfer(){
        //GIVEN
        Transaction transaction = new Transaction();
        transaction.setPayer(1L);
        transaction.setPayee(2L);
        transaction.setValue(new BigDecimal(100));

        Account account = new Account();
        account.setType(AccountType.PF.getValue());
        account.setBalance(new BigDecimal(200));

        BDDMockito.given(accountService.searchAccount(transaction.getPayer())).willReturn(account);

        //WHEN
        transactionService.transfer(transaction);

        //THEN
        BDDMockito.verify(transactionRepository).save(transaction);
        BDDMockito.verify(transactionRepository).withdraw(transaction.getPayer(), transaction.getValue());
        BDDMockito.verify(transactionRepository).deposit(transaction.getPayee(), transaction.getValue());
        BDDMockito.verify(authorizationService).authorize(transaction);
        BDDMockito.verify(notificationService).notify(transaction);
    }

    @Test
    void itShouldThrowInsufficientFundsWithdrawing() {
        Long id = 1L;
        BigDecimal value = new BigDecimal(5);
        //GIVEN
        BDDMockito.given(accountService.searchAccount(id)).willReturn(account);
        BDDMockito.given(account.getBalance()).willReturn(value);

        //WHEN + THEN
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
    void itShouldDeposit() {
        //GIVEN
        BigDecimal value = new BigDecimal(20);
        Long id = 1L;

        BDDMockito.given(accountService.searchAccount(id)).willReturn(account);

        //WHEN
        transactionService.deposit(id, value);

        //THEN
        BDDMockito.verify(accountService).searchAccount(id);
        BDDMockito.verify(transactionRepository).deposit(id, value);

    }

    @Test
    void itShouldThrowValidateTransactionException(){
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
    public void itShouldThrowInsufficientFunds() {
        // GIVEN
        Transaction transaction = new Transaction();
        transaction.setPayer(1L);
        transaction.setPayee(2L);
        transaction.setValue(new BigDecimal(100));

        Account account = new Account();
        account.setType(AccountType.PF.getValue());
        account.setBalance(new BigDecimal(99));

        BDDMockito.given(accountService.searchAccount(transaction.getPayer())).willReturn(account);

        // WHEN + THEN
        assertThrows(InsufficientFundsException.class, () -> transactionService.transfer(transaction));
    }


}