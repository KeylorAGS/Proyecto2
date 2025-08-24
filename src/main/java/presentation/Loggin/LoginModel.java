package presentation.Loggin;

import presentation.AbstractModel;
import presentation.Logic.Usuario;

public class LoginModel extends AbstractModel {
    private Usuario currentUser; //Puede ser medico o farmaceutico

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        Usuario tempUser = this.currentUser;
        this.currentUser = currentUser;
        firePropertyChange("currentUser",tempUser,this.currentUser);
    }

    public LoginModel() {}



}
