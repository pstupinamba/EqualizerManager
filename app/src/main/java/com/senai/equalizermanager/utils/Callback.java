package com.senai.equalizermanager.utils;

/**
 * Interface genérica para implementar callbacks assíncronos.
 *
 * @param <T> o tipo do resultado esperado na operação de sucesso.
 */
public interface Callback<T> {

    /**
     * Método chamado quando a operação é concluída com sucesso.
     *
     * @param result o resultado da operação bem-sucedida.
     */
    void onSuccess(T result);

    /**
     * Método chamado quando a operação falha.
     *
     * @param e a exceção que descreve a falha.
     */
    void onFailure(Exception e);
}
