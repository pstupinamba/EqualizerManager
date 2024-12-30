package com.senai.equalizermanager.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.senai.equalizermanager.models.EqualizerSettings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EqualizerSettingsControllerTest {
    private EqualizerSettingsController controller;

    @Before
    public void setUp() {
        // Simula a criação do controller com um contexto fictício
        controller = new EqualizerSettingsController(null);
    }

    // Testa a criação de configurações de equalizador com frequências válidas
    @Test
    public void testCreateEqualizerSettings_ValidFrequencies() {
        try {
            EqualizerSettings settings = new EqualizerSettings(100, 1000, 5000, "Test");
            controller.createEqualizerSettings(settings);
            // Se a exceção não for lançada, o teste passará
        } catch (Exception e) {
            Assert.fail("Exceção inesperada durante a criação das configurações de equalizador: " + e.getMessage());
        }
    }

    // Testa a criação de configurações de equalizador com frequências negativas
    @Test
    public void testCreateEqualizerSettings_NegativeFrequency() {
        try {
            EqualizerSettings settings = new EqualizerSettings(-100, 1000, 5000, "Test");
            controller.createEqualizerSettings(settings);
            fail("Deveria ter lançado uma exceção por frequências negativas.");
        } catch (Exception e) {
            Assert.assertEquals("Os valores das frequências não podem ser negativos.", e.getMessage());
        }
    }

    // Testa a criação de configurações de equalizador com valores de frequência fora do intervalo permitido
    @Test
    public void testCreateEqualizerSettings_OutOfRangeFrequency() {
        try {
            EqualizerSettings settings = new EqualizerSettings(50, 500, 5000, "Test");
            controller.createEqualizerSettings(settings);
            fail("Deveria ter lançado uma exceção por valores fora do intervalo.");
        } catch (Exception e) {
            Assert.assertEquals("O valor do Grave deve estar entre 60 e 250", e.getMessage());
        }
    }

    // Testa a exclusão de configurações de equalizador quando não encontrada
    @Test
    public void testDeleteEqualizerSettings_NotFound() {
        try {
            controller.deleteEqualizerSettings(1);
            fail("Deveria ter lançado uma exceção por configuração não encontrada.");
        } catch (Exception e) {
            Assert.assertEquals("Configuração de equalização não encontrada.", e.getMessage());
        }
    }
}
