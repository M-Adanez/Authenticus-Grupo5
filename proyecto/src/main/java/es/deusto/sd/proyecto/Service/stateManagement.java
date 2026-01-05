package es.deusto.sd.proyecto.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.deusto.sd.proyecto.Entity.User;


public class stateManagement {
	private static volatile stateManagement instance;

    private Map<UUID,User> user_token;

    private stateManagement() {
		this.user_token = new HashMap<>();
	}

    public static stateManagement getInstance() {
		if (instance == null) {
			synchronized (stateManagement.class) {
				if (instance == null) {
					instance = new stateManagement();
				}
			}
		}
		return instance;
	}

    public Map<UUID, User> getLoggedUssers(){
        return this.user_token;
    }

    public synchronized void addLogin(UUID token, User user){
        user_token.put(token, user);
    }

    public User getUserByToken(UUID token){
        User us = user_token.get(token);
        return us;
    }

    public synchronized void logout(UUID token){
        user_token.remove(token);
    }

}
