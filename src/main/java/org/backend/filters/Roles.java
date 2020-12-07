package org.backend.filters;

import org.backend.modals.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Roles{
    private static final Role[] roles = new Role[]{Role.ADMIN, Role.URGENCIER, Role.SIMPLEUSER, Role.CANDIDAT};
    private static final String ADMINS_PATH = "admins";
    private static final String CANDIDATS_PATH = "candidats";
    private static final String USERS_PATH = "users";
    private static final String URGENCIERS_PATH = "urgenciers";
    //NEWROLE to add
    public static String getAdminsPath(){
        return ADMINS_PATH;
    }
    public static String getCandidatsPath(){
        return CANDIDATS_PATH;
    }
    public static String getUsersPath(){
        return USERS_PATH;
    }
    public static String getUrgenciersPath(){
        return URGENCIERS_PATH;
    }
    // function returning new role base path

    //return roles
    public static List<Role> getRoles(){
        return new ArrayList<>(Arrays.asList(roles));
    }
}
//PS: change it using Factory pattern
enum Role {
    ADMIN,
    SIMPLEUSER,
    URGENCIER,
    CANDIDAT;
    //NEWROLE
    public Personne roleClass(){
        switch (this){
            case ADMIN:
                return new Admin();
            case CANDIDAT:
                return new Candidat();
            case URGENCIER:
                return new Urgencier();
            case SIMPLEUSER:
                return new SimpleUser();
            /*case NEWROLE:
             * return NEWROLECLASS;
             * */
            default:
                return null;
        }
    }
    public String roleBaseResource(){
        switch (this){
            case ADMIN:
                return Roles.getAdminsPath();
            case CANDIDAT:
                return Roles.getCandidatsPath();
            case URGENCIER:
                return Roles.getUrgenciersPath();
            case SIMPLEUSER:
                return Roles.getUsersPath();
            /*case NEWROLE:
            * return RolesBaseRessource.getNEWROLEPath();
            * */

            default:
                return null;
        }
    }
}
