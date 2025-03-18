package Interfaces;

import user.User;
public interface Authentication {
    public User login(String email, String password);
    public void forgotPassword();
}
