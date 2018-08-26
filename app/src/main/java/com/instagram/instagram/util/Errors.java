package com.instagram.instagram.util;

import java.util.HashMap;

public class Errors {

    private HashMap<Integer, String> erros;

    public Errors() {
        this.erros = new HashMap<>();
        erros.put(202, "Usuário já existe");
        erros.put(201, "Informe a senha");
    }

    public String getError(int erro) {
        return this.erros.get(erro).toString();
    }
}
