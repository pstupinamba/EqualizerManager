package com.senai.equalizermanager.utils;

import android.view.View;

/**
 * Interface para lidar com cliques em itens de uma lista.
 */
public interface OnListItemClick {

    /**
     * Método chamado quando um item da lista é clicado.
     *
     * @param view     a view do item que foi clicado.
     * @param position a posição do item clicado na lista.
     */
    void onClick(View view, int position);
}
