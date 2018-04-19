package pi_zero;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

import org.bouncycastle.jce.provider.*;
import org.bouncycastle.openssl.*;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

public class SSLFactory
{
	public static SSLSocketFactory getSocketFactory(
			final String certificateSigningRequestPath, 
			final String clientCertificateFilePath, 
			final String clientKeyFilePath,
			final String password) throws Exception
	{
		Security.addProvider(new BouncyCastleProvider());

		X509Certificate caCertificate = null;

		FileInputStream fileInputStream = new FileInputStream(certificateSigningRequestPath);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

		//while (bufferedInputStream.available() > 0)
		{
			caCertificate = (X509Certificate) certificateFactory.generateCertificate(bufferedInputStream);
			System.out.println(caCertificate.toString());
		}

		bufferedInputStream = new BufferedInputStream(new FileInputStream(clientCertificateFilePath));
		X509Certificate certificate = null;
		
		while (bufferedInputStream.available() > 0)
		{
			certificate = (X509Certificate) certificateFactory.generateCertificate(bufferedInputStream);
			System.out.println(caCertificate.toString());
		}

		PEMParser pemParser = new PEMParser(new FileReader(clientKeyFilePath));
		Object object = pemParser.readObject();
		PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
		KeyPair key;
		
		if (object instanceof PEMEncryptedKeyPair)
		{
			System.out.println("Encrypted key - we will use provided password");
			key = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv));
		}
		else
		{
			System.out.println("Unencrypted key - no password needed");
			key = converter.getKeyPair((PEMKeyPair) object);
		}
		
		pemParser.close();

		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", caCertificate);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
		tmf.init(caKs);

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);
		ks.setCertificateEntry("certificate", certificate);
		ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]
		{ certificate });
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, password.toCharArray());

		SSLContext context = SSLContext.getInstance("TLSv1.2");
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return context.getSocketFactory();
	}
}
