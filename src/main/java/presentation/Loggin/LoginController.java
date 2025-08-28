package presentation.Loggin;

import javax.swing.*;
import presentation.Logic.Service;
import presentation.Logic.Usuario;

public class LoginController {
    LoginModel model;
    View_Login view;

    public LoginController(LoginModel model, View_Login view) {
        this.model = model;
        this.view = view;
        view.setController(this);
        view.setModel(model);
    }

    public void login(String id,String clave){
        Usuario tempUser = Service.instance().login(id,clave);
        model.setCurrentUser(tempUser);
    }

}
