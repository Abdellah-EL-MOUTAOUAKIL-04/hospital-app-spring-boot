package ma.abdellah.hospitalapp.security.service;

import ma.abdellah.hospitalapp.security.entities.AppRole;
import ma.abdellah.hospitalapp.security.entities.AppUser;


public interface AccountService {
    AppUser addNewUser(String username, String password,String email, String confirmedPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
