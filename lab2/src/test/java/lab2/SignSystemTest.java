package lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static lab2.SignSystem.signData;
import static lab2.SignSystem.verifySignedData;
import static lab2.Utils.prepareEncryption;

public class SignSystemTest {
    private static char[] keystorePassword = "password".toCharArray();
    private static char[] keyPassword = "password".toCharArray();

    private CertificateFactory certFactory;
    private X509Certificate certificate;
    private KeyStore keystore;
    private PrivateKey privateKey;

    @Before
    public void before() throws CertificateException, NoSuchProviderException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        prepareEncryption();
        certFactory = CertificateFactory.getInstance("X.509", "BC");
        certificate = (X509Certificate) certFactory
                .generateCertificate(new FileInputStream("src/main/resources/lab2/public.cer"));
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("src/main/resources/lab2/private.p12"), keystorePassword);
        privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);

    }

    @Test
    public void testSign() throws Exception {
        String message = "secretMessage";
        byte[] signedData = signData(message.getBytes(), certificate, privateKey);

        Assert.assertNotEquals(message, new String(signedData));
    }

    @Test
    public void testSignVerification() throws Exception {
        String message = "secretMessage";
        byte[] signedData = signData(message.getBytes(), certificate, privateKey);
        boolean check = verifySignedData(signedData);
        Assert.assertNotEquals(message, new String(signedData));
        Assert.assertTrue(check);
    }

}
