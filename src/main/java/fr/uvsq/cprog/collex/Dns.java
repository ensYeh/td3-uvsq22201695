package fr.uvsq.cprog.collex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * DNS : charge une base "nom_machine adresse_ip" depuis un fichier texte.
 */
public class Dns {

  private static final String PROP_FILE = "/dns.properties";
  private static final String KEY_DNS_FILE = "dns.file";

  private final Path dataFile;
  private final Map<String, DnsItem> byName = new HashMap<>();
  private final Map<String, DnsItem> byIp = new HashMap<>();

  /**
   * Construit le DNS et charge la base depuis le fichier texte. Format attendu : une entrée par
   * ligne "nom_machine adresse_ip".
   *
   * @throws IOException en cas de problème d'I/O
   */
  public Dns() throws IOException {
    Properties p = new Properties();
    try (InputStream in = Dns.class.getResourceAsStream(PROP_FILE)) {
      if (in == null) {
        throw new IllegalArgumentException("Fichier de propriétés introuvable : " + PROP_FILE);
      }
      p.load(in);
    }

    String file = p.getProperty(KEY_DNS_FILE);
    if (file == null || file.isBlank()) {
      throw new IllegalArgumentException("Propriété manquante ou vide : " + KEY_DNS_FILE);
    }

    this.dataFile = Path.of(file);
    load();
  }

  private void load() throws IOException {
    if (!Files.exists(dataFile)) {
      throw new FileNotFoundException("Fichier introuvable : " + dataFile.toAbsolutePath());
    }

    for (String raw : Files.readAllLines(dataFile, StandardCharsets.UTF_8)) {
      String line = raw.trim();
      if (line.isEmpty()) {
        continue;
      }

      String[] parts = line.split("\\s+");
      if (parts.length != 2) {
        throw new IllegalArgumentException("Ligne invalide : " + raw);
      }

      NomMachine nom = new NomMachine(parts[0]);
      AdresseIp ip = new AdresseIp(parts[1]);
      DnsItem item = new DnsItem(nom, ip);

      // insertion dans les deux maps
      byName.put(nom.getNomQualifie(), item);
      byIp.put(ip.getAdresseIp(), item);
    }
  }

  /**
   * Retourne l'item correspondant à un nom de machine qualifié.
   *
   * @param nom le nom qualifié de la machine
   * @return l'item correspondant, ou null si absent
   */
  public DnsItem getItem(NomMachine nom) {
    if (nom == null) {
      return null;
    }
    return byName.get(nom.getNomQualifie());
  }

  /**
   * Retourne l'item correspondant à une adresse IP.
   *
   * @param ip l'adresse IP
   * @return l'item correspondant, ou null si absent
   */
  public DnsItem getItem(AdresseIp ip) {
    if (ip == null) {
      return null;
    }
    return byIp.get(ip.getAdresseIp());
  }

  /**
   * Retourne la liste des items pour un domaine donné.
   *
   * @param domaine le nom de domaine (ex: "uvsq.fr")
   * @return la liste des DnsItem appartenant à ce domaine
   */
  public List<DnsItem> getItems(String domaine) {
    if (domaine == null || domaine.isBlank()) {
      return List.of();
    }
    String d = domaine.trim().toLowerCase();

    List<DnsItem> result = new ArrayList<>();
    for (DnsItem item : byName.values()) {
      if (item.getNomMachine().getDomaine().equals(d)) {
        result.add(item);
      }
    }
    return result;
  }

  public Map<String, DnsItem> getByName() {
    return Map.copyOf(byName);
  }

  public Map<String, DnsItem> getByIp() {
    return Map.copyOf(byIp);
  }
}
