package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddCommandTest {

  private final Path dataFile = Path.of("target/test-dns.txt");

  @BeforeEach
  void preparerFichierDeDonnees() throws Exception {
    Files.createDirectories(dataFile.getParent());
    // Fichier initial minimal "nom_machine adresse_ip" (une entrée par ligne)
    Files.writeString(
        dataFile,
        """
            www.uvsq.fr 193.51.31.90
            ecampus.uvsq.fr 193.51.25.12
            """
    );
  }

  @Test
  void testAjoutReussiRetourneVide() throws Exception {
    Dns dns = new Dns();
    Commande cmd = new AddCommand("193.51.25.24", "pikachu.uvsq.fr");

    String resultat = cmd.execute(dns);
    assertEquals("", resultat);

    // Vérifie que l'item est présent en mémoire
    DnsItem item = dns.getItem(new NomMachine("pikachu.uvsq.fr"));
    assertEquals("193.51.25.24", item.getAdresseIp().getAdresseIp());

    // Vérifie la persistance en rechargeant un nouveau Dns
    Dns dnsRech = new Dns();
    DnsItem itemRech = dnsRech.getItem(new NomMachine("pikachu.uvsq.fr"));
    assertEquals("193.51.25.24", itemRech.getAdresseIp().getAdresseIp());
  }

  @Test
  void testDoublonNomLanceIllegalStateException() throws Exception {
    Dns dns = new Dns();
    // www.uvsq.fr existe dans le fichier initial
    Commande cmd = new AddCommand("10.0.0.1", "www.uvsq.fr");
    assertThrows(IllegalStateException.class, () -> cmd.execute(dns));
  }

  @Test
  void testDoublonIpLanceIllegalStateException() throws Exception {
    Dns dns = new Dns();
    // 193.51.31.90 existe déjà
    Commande cmd = new AddCommand("193.51.31.90", "nouveau.uvsq.fr");
    assertThrows(IllegalStateException.class, () -> cmd.execute(dns));
  }
}
