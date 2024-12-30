package com.senai.equalizermanager.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserWithSettingsTest {

    // Testa os métodos set e get para o usuário.
    // Verifica se o objeto User pode ser atribuído e recuperado corretamente dentro de UserWithSettings.
    @Test
    public void testSetAndGetUser() {
        try {
            UserWithSettings userWithSettings = new UserWithSettings();
            User user = new User();
            user.setId(1);
            user.setUsername("TestUser");

            userWithSettings.setUser(user);

            Assert.assertNotNull("User não deve ser nulo após ser definido", userWithSettings.getUser());
            Assert.assertEquals("O ID do usuário deve ser igual ao definido", 1, userWithSettings.getUser().getId());
            Assert.assertEquals("O nome de usuário deve ser igual ao definido", "TestUser", userWithSettings.getUser().getUsername());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste de set e get do User: " + e.getMessage());
        }
    }

    // Testa os métodos set e get para a lista de configurações.
    // Verifica se a lista de EqualizerSettings pode ser atribuída e recuperada corretamente.
    @Test
    public void testSetAndGetSettings() {
        try {
            UserWithSettings userWithSettings = new UserWithSettings();

            EqualizerSettings setting1 = new EqualizerSettings();
            setting1.setId(1);
            setting1.setId_user(1);
            setting1.setName("Rock");

            EqualizerSettings setting2 = new EqualizerSettings();
            setting2.setId(2);
            setting2.setId_user(1);
            setting2.setName("Pop");

            List<EqualizerSettings> settings = new ArrayList<>();
            settings.add(setting1);
            settings.add(setting2);

            userWithSettings.setSettings(settings);

            Assert.assertNotNull("Settings não deve ser nulo após ser definido", userWithSettings.getSettings());
            Assert.assertEquals("A quantidade de configurações deve ser igual à definida", 2, userWithSettings.getSettings().size());
            Assert.assertEquals("O nome da primeira configuração deve ser igual ao definido", "Rock", userWithSettings.getSettings().get(0).getName());
            Assert.assertEquals("O nome da segunda configuração deve ser igual ao definido", "Pop", userWithSettings.getSettings().get(1).getName());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste de set e get do Settings: " + e.getMessage());
        }
    }

    // Testa o caso em que a lista de configurações está vazia.
    // Verifica se a lista de configurações pode ser definida como vazia sem causar problemas.
    @Test
    public void testEmptySettings() {
        try {
            UserWithSettings userWithSettings = new UserWithSettings();
            userWithSettings.setSettings(new ArrayList<>());
            Assert.assertTrue("A lista de configurações deve estar vazia", userWithSettings.getSettings().isEmpty());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste de lista de configurações vazia: " + e.getMessage());
        }
    }
}
