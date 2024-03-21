package com.caiolobo.desafiopicpay;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Transactional
    @Modifying
    @Query("update Usuario u set u.saldo = :novoSaldo where u.email = :email")
    void atualizaDinheiro(BigDecimal novoSaldo, String email);
}
