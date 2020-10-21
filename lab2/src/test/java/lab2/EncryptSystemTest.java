package lab2;

import org.bouncycastle.cms.CMSException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static lab2.EncryptSystem.decryptData;
import static lab2.EncryptSystem.encryptData;
import static lab2.Utils.prepareEncryption;

public class EncryptSystemTest {
    private static char[] keystorePassword = "password".toCharArray();
    private static char[] keyPassword = "password".toCharArray();

    private CertificateFactory certFactory;
    private X509Certificate certificate;
    private KeyStore keystore;
    @Before
    public void before() throws CertificateException, NoSuchProviderException, FileNotFoundException, KeyStoreException {
        prepareEncryption();
        certFactory = CertificateFactory.getInstance("X.509", "BC");
        certificate = (X509Certificate) certFactory
                .generateCertificate(new FileInputStream("src/main/resources/lab2/public.cer"));
        keystore = KeyStore.getInstance("PKCS12");
    }

    @Test
    public void testEncryption() throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, CMSException {
        keystore.load(new FileInputStream("src/main/resources/lab2/private.p12"), keystorePassword);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);
        String secretMessage = "ahdghgd45645 ef 4e5454";
        byte[] encryptedData = encryptData(secretMessage.getBytes(), certificate);
        Assert.assertNotEquals(secretMessage, new String(encryptedData));
    }

    @Test
    public void testDecryption() throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, CMSException {
        keystore.load(new FileInputStream("src/main/resources/lab2/private.p12"), keystorePassword);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);
        String secretMessage = "ahdghgd45645 ef 4e5454";
        byte[] encryptedData = encryptData(secretMessage.getBytes(), certificate);
        byte[] rawData = decryptData(encryptedData, privateKey);
        Assert.assertNotEquals(secretMessage, new String(encryptedData));
        Assert.assertArrayEquals(rawData, secretMessage.getBytes());
        Assert.assertEquals(secretMessage, new String(rawData));
    }
}
