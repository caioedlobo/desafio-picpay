package com.caiolobo.desafiopicpay;

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
        if(usuarioRepository.existeUsuario(novoUsuario.email())){
            throw new UsuarioJaExisteException();
        }
        return usuarioRepository.save(new Usuario(novoUsuario));
    }

    /*public void enviarDinheiro(BigDecimal quantidade, String emailRecebedor){
        if (transferenciaValida()){
            usuarioRepository.atualizaDinheiro(quantidade, emailRecebedor);
        }
    }*/
}
