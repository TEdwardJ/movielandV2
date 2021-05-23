package edu.ted.web.movieland.security;

public class SessionHandler {
    private static final ThreadLocal<UserSession> userSession = new ThreadLocal<>();

    public static void setUserSession(UserSession session){
        userSession.set(session);
    }

    public static UserSession getUserSession(){
        return userSession.get();
    }

}
