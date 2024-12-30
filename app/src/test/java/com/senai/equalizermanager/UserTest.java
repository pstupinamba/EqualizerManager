package com.senai.equalizermanager.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {



    // Testa o comportamento do construtor padrão da classe User.
    // Verifica se o ID é inicializado como 0 e o username como uma string vazia.
    @Test
    public void testDefaultConstructor() {
        try {
            User user = new User();
            Assert.assertEquals("O ID padrão deve ser 0", 0, user.getId());
            Assert.assertEquals("O username padrão deve ser uma string vazia", "", user.getUsername());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do construtor padrão: " + e.getMessage());
        }
    }

    // Testa o comportamento do construtor com um parâmetro de username.
    // Verifica se o username é corretamente atribuído ao objeto User.
    @Test
    public void testParameterizedConstructor() {
        try {
            User user = new User("TestUser");
            Assert.assertEquals("O username deve ser inicializado corretamente", "TestUser", user.getUsername());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do construtor parametrizado: " + e.getMessage());
        }
    }

    // Testa os métodos de acesso e modificação do ID.
    // Verifica se o ID pode ser alterado corretamente.
    @Test
    public void testSetAndGetId() {
        try {
            User user = new User();
            user.setId(1);
            Assert.assertEquals("O ID deve ser igual ao que foi definido", 1, user.getId());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do set e get do ID: " + e.getMessage());
        }
    }

    // Testa os métodos de acesso e modificação do username.
    // Verifica se o username pode ser alterado corretamente.
    @Test
    public void testSetAndGetUsername() {
        try {
            User user = new User();
            user.setUsername("NewUser");
            Assert.assertEquals("O username deve ser igual ao que foi definido", "NewUser", user.getUsername());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do set e get do username: " + e.getMessage());
        }
    }
}
