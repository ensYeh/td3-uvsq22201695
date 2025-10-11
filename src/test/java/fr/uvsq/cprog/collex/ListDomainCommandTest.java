package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListDomainCommandTest {

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
            poste.ens.fr 193.51.30.1
            """
    );
  }

  @Test
  void testTriParNom() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new ListDomainCommand("uvsq.fr", false);

    String out = cmd.execute(dns);

    // Attendu : ecampus < mail < www (puis IPs affichées devant)
    String attendu = String.join(System.lineSeparator(),
        "193.51.25.12 ecampus.uvsq.fr",
        "193.51.31.100 mail.uvsq.fr",
        "193.51.31.90 www.uvsq.fr"
    );
    assertEquals(attendu, out);
  }

  @Test
  void testTriParIp() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new ListDomainCommand("uvsq.fr", true);

    String out = cmd.execute(dns);

    // IPs (en ordre lexicographique des chaînes IP utilisées ici)
    String attendu = String.join(System.lineSeparator(),
        "193.51.25.12 ecampus.uvsq.fr",
        "193.51.31.90 www.uvsq.fr",
        "193.51.31.100 mail.uvsq.fr"
    );
    assertEquals(attendu, out);
  }

  @Test
  void testDomaineInconnuRetourVide() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new ListDomainCommand("inconnu.fr", false);
    assertEquals("", cmd.execute(dns));
  }

  @Test
  void testDomaineCasseEspaces() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new ListDomainCommand("  UVSQ.FR  ", false);

    String out = cmd.execute(dns);
    // Même attendu que le tri par nom
    String attendu = String.join(System.lineSeparator(),
        "193.51.25.12 ecampus.uvsq.fr",
        "193.51.31.100 mail.uvsq.fr",
        "193.51.31.90 www.uvsq.fr"
    );
    assertEquals(attendu, out);
  }
}
