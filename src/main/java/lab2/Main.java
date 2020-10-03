package lab2;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.security.*;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static lab2.EncryptSystem.decryptData;
import static lab2.EncryptSystem.encryptData;
import static lab2.SignSystem.signData;
import static lab2.SignSystem.verifySignedData;
import static lab2.Utils.prepareEncryption;

public class Main {


    public static void main(String[] args) throws Exception {
        prepareEncryption();
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");

        X509Certificate certificate = (X509Certificate) certFactory
                .generateCertificate(new FileInputStream("src/main/resources/lab2/public.cer"));

        char[] keystorePassword = "password".toCharArray();
        char[] keyPassword = "password".toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("src/main/resources/lab2/private.p12"), keystorePassword);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);
        String secretMessage = "My password is 123456Seven";
        System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = encryptData(stringToEncrypt, certificate);
        System.out.println("Encrypted Message : " + new String(encryptedData));
        byte[] rawData = decryptData(encryptedData, privateKey);
        String decryptedMessage = new String(rawData);
        System.out.println("Decrypted Message : " + decryptedMessage);

        byte[] signedData = signData(rawData, certificate, privateKey);
        Boolean check = verifySignedData(signedData);
        System.out.println(check);
    }

}
