package com.senai.equalizermanager.models;

import org.junit.Assert;
import org.junit.Test;

public class EqualizerSettingsTest {

    // Testa o construtor padrão da classe EqualizerSettings.
    // Verifica se os valores de frequência e o nome são inicializados corretamente.
    @Test
    public void testDefaultConstructor() {
        try {
            EqualizerSettings settings = new EqualizerSettings();
            Assert.assertEquals("A frequência low_freq deve ser 0", 0, settings.getLow_freq());
            Assert.assertEquals("A frequência mid_freq deve ser 0", 0, settings.getMid_freq());
            Assert.assertEquals("A frequência high_freq deve ser 0", 0, settings.getHigh_freq());
            Assert.assertEquals("O nome deve ser uma string vazia", "", settings.getName());
            Assert.assertFalse("O campo isActive deve ser false por padrão", settings.isActive());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do construtor padrão: " + e.getMessage());
        }
    }

    // Testa o construtor com parâmetros para a classe EqualizerSettings.
    // Verifica se os valores de frequência e nome são corretamente atribuídos.
    @Test
    public void testParameterizedConstructor() {
        try {
            EqualizerSettings settings = new EqualizerSettings(50, 100, 150, "Rock");
            Assert.assertEquals("A frequência low_freq deve ser 50", 50, settings.getLow_freq());
            Assert.assertEquals("A frequência mid_freq deve ser 100", 100, settings.getMid_freq());
            Assert.assertEquals("A frequência high_freq deve ser 150", 150, settings.getHigh_freq());
            Assert.assertEquals("O nome deve ser 'Rock'", "Rock", settings.getName());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do construtor parametrizado: " + e.getMessage());
        }
    }

    // Testa os métodos set e get para os campos de frequência.
    // Verifica se os valores de low_freq, mid_freq e high_freq podem ser alterados corretamente.
    @Test
    public void testSetAndGetFrequencies() {
        try {
            EqualizerSettings settings = new EqualizerSettings();
            settings.setLow_freq(10);
            settings.setMid_freq(20);
            settings.setHigh_freq(30);

            Assert.assertEquals("A frequência low_freq deve ser 10", 10, settings.getLow_freq());
            Assert.assertEquals("A frequência mid_freq deve ser 20", 20, settings.getMid_freq());
            Assert.assertEquals("A frequência high_freq deve ser 30", 30, settings.getHigh_freq());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do set e get das frequências: " + e.getMessage());
        }
    }

    // Testa os métodos set e get para o nome e campo isActive.
    // Verifica se o nome pode ser alterado e se o valor de isActive pode ser modificado.
    @Test
    public void testSetAndGetNameAndIsActive() {
        try {
            EqualizerSettings settings = new EqualizerSettings();
            settings.setName("Jazz");
            settings.setActive(true);

            Assert.assertEquals("O nome deve ser 'Jazz'", "Jazz", settings.getName());
            Assert.assertTrue("O campo isActive deve ser true", settings.isActive());
        } catch (Exception e) {
            Assert.fail("Exceção inesperada no teste do set e get do nome e isActive: " + e.getMessage());
        }
    }

}
