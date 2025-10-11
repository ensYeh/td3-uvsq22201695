package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LookupIpCommandTest {

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
  void testRetourIpPourNomConnu() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new LookupIpCommand("www.uvsq.fr");
    String out = cmd.execute(dns);
    assertEquals("193.51.31.90", out);
  }

  @Test
  void testNomInconnu() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new LookupIpCommand("inconnu.uvsq.fr");
    String out = cmd.execute(dns);
    assertEquals("Nom inconnu", out);
  }

}
