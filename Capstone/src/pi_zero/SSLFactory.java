package pi_zero;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

import org.bouncycastle.jce.provider.*;
import org.bouncycastle.openssl.*;

public class SSLFactory 
{
	public static SSLSocketFactory getSocketFactory(final String caCrtFile, final String crtFile, final String keyFile,
			final String password) throws Exception 
	{
		Security.addProvider(new BouncyCastleProvider());

		// load CA certificate
		PEMParser parser = new PEMParser(
				new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
		X509Certificate caCert = (X509Certificate) parser.readObject();
		parser.close();

		// load client certificate
		parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)))));
		X509Certificate cert = (X509Certificate) parser.readObject();
		parser.close();

		parser = new PEMParser(new FileReader(keyFile));

		KeyPair key = (KeyPair) parser.readObject();
		parser.close();

		// CA certificate is used to authenticate server
		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", caCert);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(caKs);

		// client key and certificates are sent to server so it can authenticate us
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);
		ks.setCertificateEntry("certificate", cert);
		ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(),
				new java.security.cert.Certificate[] { cert });
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, password.toCharArray());

		// finally, create SSL socket factory
		SSLContext context = SSLContext.getInstance("TLSv1");
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return context.getSocketFactory();
	}

}
