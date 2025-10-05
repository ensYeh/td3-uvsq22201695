package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DnsTest {

  private final Path dataFile = Path.of("target/test-dns.txt");

  @BeforeEach
  public void setup() throws IOException {
    // On écrit un fichier de données propre pour chaque test
    Files.createDirectories(dataFile.getParent());
    Files.writeString(
        dataFile,
        // format: "nom_machine adresse_ip"
        // + ligne vide pour vérifier que le parseur ignore
        """
            www.uvsq.fr 193.51.31.90
            
            ecampus.uvsq.fr 193.51.25.12
            """
    );
  }

  @Test
  void charge_base_par_nom() throws IOException {
    Dns dns = new Dns();
    Map<String, DnsItem> byName = dns.getByName();

    // On a bien nos 2 items
    assertEquals(2, byName.size());
    assertTrue(byName.containsKey("www.uvsq.fr"));
    assertTrue(byName.containsKey("ecampus.uvsq.fr"));

    // Vérification de l’IP d’un item
    assertEquals("193.51.31.90",
        byName.get("www.uvsq.fr").getAdresseIp().getAdresseIp());
  }

  @Test
  public void charge_base_par_ip() throws IOException {
    Dns dns = new Dns();
    Map<String, DnsItem> byIp = dns.getByIp();

    // on a bien les 2 IP du fichier
    assertEquals(2, byIp.size());
    assertTrue(byIp.containsKey("193.51.31.90"));
    assertTrue(byIp.containsKey("193.51.25.12"));

    // chaque IP pointe vers le bon nom de machine
    assertEquals("www.uvsq.fr",
        byIp.get("193.51.31.90").getNomMachine().getNomQualifie());
    assertEquals("ecampus.uvsq.fr",
        byIp.get("193.51.25.12").getNomMachine().getNomQualifie());
  }

  @Test
  public void ligne_invalide() throws IOException {
    Files.writeString(
        dataFile,
        """
            ligne_invalide_sans_separateur
            """
    );

    assertThrows(IllegalArgumentException.class, Dns::new);
  }

  @Test
  public void getItem_par_nom_trouve() throws IOException {
    Dns dns = new Dns();
    DnsItem item = dns.getItem(new NomMachine("www.uvsq.fr"));

    assertNotNull(item);
    assertEquals("www.uvsq.fr", item.getNomMachine().getNomQualifie());
    assertEquals("193.51.31.90", item.getAdresseIp().getAdresseIp());
  }

  @Test
  public void getItem_par_nom_absent() throws IOException {
    Dns dns = new Dns();
    DnsItem item = dns.getItem(new NomMachine("inconnu.uvsq.fr"));
    assertNull(item);
  }

  @Test
  public void getItem_par_ip_trouve() throws IOException {
    Dns dns = new Dns();
    DnsItem item = dns.getItem(new AdresseIp("193.51.25.12"));

    assertNotNull(item);
    assertEquals("ecampus.uvsq.fr", item.getNomMachine().getNomQualifie());
    assertEquals("193.51.25.12", item.getAdresseIp().getAdresseIp());
  }

  @Test
  public void getItem_par_ip_absent() throws IOException {
    Dns dns = new Dns();
    DnsItem item = dns.getItem(new AdresseIp("10.0.0.1"));
    assertNull(item);
  }

  @Test
  void getItem_nom_null_retourne_null() throws IOException {
    Dns dns = new Dns();
    assertNull(dns.getItem((NomMachine) null));
  }

  @Test
  void getItem_ip_null_retourne_null() throws IOException {
    Dns dns = new Dns();
    assertNull(dns.getItem((AdresseIp) null));
  }

}
