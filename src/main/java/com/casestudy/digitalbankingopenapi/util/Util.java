package com.casestudy.digitalbankingopenapi.util;

import java.security.SecureRandom;
import java.util.Objects;

public class Util {

    private SecureRandom secureRandom;

    public int getSecureRandom(){
        if(Objects.isNull(secureRandom)){
            secureRandom=new SecureRandom();
        }
        return secureRandom.nextInt(100000,999999);
    }
}
