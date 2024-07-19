package taewookim.WebGame.util;

import org.apache.tomcat.util.codec.binary.Base64;
import taewookim.WebGame.exception.ExceptionType;
import taewookim.WebGame.exception.HTTPApiException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA {

    public static String sha3_512(String text) throws HTTPApiException {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(md.digest());
        }catch(Exception e) {
            throw new HTTPApiException(ExceptionType.Server_Error);
        }
    }

}
