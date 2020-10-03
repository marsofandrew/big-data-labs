package lab2;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Utils {
    public static void prepareEncryption(){
        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());
    }
}
