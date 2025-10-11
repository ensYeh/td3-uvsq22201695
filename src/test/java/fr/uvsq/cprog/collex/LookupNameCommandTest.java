package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LookupNameCommandTest {

  private final Path dataFile = Path.of("target/test-dns.txt");

  @BeforeEach
  void preparerFichier() throws Exception {
    Files.createDirectories(dataFile.getParent());
    Files.writeString(
        dataFile,
        """
            www.uvsq.fr 193.51.31.90
            ecampus.uvsq.fr 193.51.25.12
            mail.uvsq.fr 193.51.31.100
            """
    );
  }

  @Test
  void testRetourNomPourIpConnue() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new LookupNameCommand("193.51.31.90");
    String out = cmd.execute(dns);
    assertEquals("www.uvsq.fr", out);
  }

  @Test
  void testAdresseInconnue() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new LookupNameCommand("10.0.0.1");
    String out = cmd.execute(dns);
    assertEquals("Adresse inconnue", out);
  }
}
