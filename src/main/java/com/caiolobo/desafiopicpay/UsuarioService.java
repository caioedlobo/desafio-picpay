package com.caiolobo.desafiopicpay;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criaUsuario(Usuario novoUsuario){
        return usuarioRepository.save(novoUsuario);
    }

    /*public void enviarDinheiro(BigDecimal quantidade, String emailRecebedor){
        if (transferenciaValida()){
            usuarioRepository.atualizaDinheiro(quantidade, emailRecebedor);
        }
    }*/
}
