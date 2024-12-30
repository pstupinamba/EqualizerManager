package com.senai.equalizermanager.controllers;

import android.content.Context;
import android.util.Log;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.dao.UserDao;
import com.senai.equalizermanager.models.User;
import com.senai.equalizermanager.utils.Callback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private AppDatabase mockDb;  // Mock da classe AppDatabase

    @Mock
    private UserDao mockUserDao;  // Mock do UserDao

    @Mock
    private Callback<List<User>> mockCallback;

    @Mock
    private Callback<User> mockUserCallback;

    @Mock
    private User mockUser;

    private UserController userController;

    @Before
    public void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.initMocks(this);

        // Cria uma instância do UserController passando um contexto mockado
        userController = new UserController(mock(Context.class));

        // Configura o mock do db para retornar o mock do UserDao
        when(mockDb.userDao()).thenReturn(mockUserDao);  // Configura o retorno do mockUserDao quando chamamos mockDb.userDao()
    }

    @Test
    public void testCreateUser_withValidUsername_shouldInsertUser() throws Exception {
        // Nome de usuário válido
        String username = "validUser";

        // Chama o método createUser
        userController.createUser(username);

        // Verifica se o método insertUser foi chamado no mock do UserDao
        verify(mockUserDao).insertUser(any(User.class));
    }

    @Test(expected = Exception.class)
    public void testCreateUser_withEmptyUsername_shouldThrowException() throws Exception {
        // Nome de usuário vazio (deve lançar exceção)
        String username = "";

        // Chama o método createUser (deve lançar exceção)
        userController.createUser(username);
    }

    @Test
    public void testGetAllUsers_shouldReturnUserList() {
        // Lista mock de usuários
        List<User> mockUsers = List.of(mockUser);

        // Configura o mock do UserDao para retornar a lista mockada de usuários
        when(mockUserDao.getAll()).thenReturn(mockUsers);

        // Chama o método getAllUsers
        userController.getAllUsers(mockCallback);

        // Verifica se o método onSuccess foi chamado no callback com a lista de usuários
        verify(mockCallback).onSuccess(mockUsers);
    }

    @Test
    public void testGetUserById_shouldReturnUser() {
        // Define o usuário esperado
        User user = new User();
        user.setUsername("Test User");

        // Configura o mock do UserDao para retornar o usuário
        when(mockUserDao.getUserById(1)).thenReturn(user);

        // Chama o método getUserById
        userController.getUserById(mockUserCallback, 1);

        // Verifica se o método onSuccess foi chamado no callback com o usuário
        verify(mockUserCallback).onSuccess(user);
    }

    @Test
    public void testDeleteUser_shouldCallDelete() {
        // Define o usuário a ser deletado
        User user = new User();
        user.setUsername("Test User");

        // Configura o mock do UserDao para retornar o usuário
        when(mockUserDao.getUserById(1)).thenReturn(user);

        // Chama o método deleteUser
        userController.deleteUser(1);

        // Verifica se o método delete foi chamado no UserDao
        verify(mockUserDao).delete(user);
    }
}
