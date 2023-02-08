package com.company.utils;

import com.company.model.Person;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class SessionHolderEmulator {

    private static final Map<String, Person> sessions = new HashMap<>();

    public static void create(String sessionToken, Person person) {
        sessions.put(sessionToken, person);
    }

    public static Person get(String token) {
        return sessions.get(token);
    }
}
