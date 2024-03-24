package com.caiolobo.desafiopicpay;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select count(u) > 0 from Usuario u where u.email = :email")
    boolean userExists(String email);
}
