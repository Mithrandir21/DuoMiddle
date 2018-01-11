package com.duopoints;

import com.duopoints.errorhandling.NullResultException;

public class Utils {

    public static <T> T returnOrException(T object){
        if (object != null) {
            return object;
        } else {
            throw new NullResultException();
        }
    }
}
