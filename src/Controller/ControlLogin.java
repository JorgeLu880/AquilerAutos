package Controller;

import Dao.DaoTrabajador;
import Model.Trabajador;

public class ControlLogin {
    private DaoTrabajador dao = new DaoTrabajador();

    public Trabajador autenticar(String usuario, String contraseña) {
        return dao.autenticar(usuario, contraseña);
    }
}

