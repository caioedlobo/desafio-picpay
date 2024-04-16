package com.caiolobo.desafiopicpay.transaction;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional
    @Modifying
    @Query("update Usuario u set u.saldo = u.saldo - :value where u.id = :payer")
    void withdraw(Long payer, BigDecimal value);

    @Transactional
    @Modifying
    @Query("update Usuario u set u.saldo = u.saldo + :value where u.id = :payee")
    void deposit(Long payee, BigDecimal value);

}
