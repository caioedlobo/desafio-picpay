package com.caiolobo.desafiopicpay;

import com.caiolobo.desafiopicpay.exceptions.AccountNotFoundException;
import com.caiolobo.desafiopicpay.exceptions.UsuarioJaExisteException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criaUsuario(CreateUsuarioDTO novoUsuario){
        if(usuarioRepository.userExists(novoUsuario.email())){
            throw new UsuarioJaExisteException();
        }
        return usuarioRepository.save(new Usuario(novoUsuario));
    }

    public Usuario searchAccount(Long accountId) {
        return usuarioRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }

    public void withdraw(Long user, BigDecimal value){
        searchAccount(user);
        usuarioRepository.withdraw(user, value);
    }

    public void deposit(Long user, BigDecimal value){
        searchAccount(user);
        usuarioRepository.deposit(user, value);
        }
}
