package fr.uvsq.cprog.collex;

/**
 * Commande : ajoute un couple (adresse IP, nom de machine) dans la base DNS.
 * <p>
 * Succès silencieux (retourne une chaîne vide). En cas de doublon (nom ou IP), l'exécution lève une
 * IllegalStateException.</p>
 */
public class AddCommand implements Commande {

  private final String ip;
  private final String fqdn;

  /**
   * Construit la commande d'ajout.
   *
   * @param ip   l'adresse IP à ajouter (format x.x.x.x)
   * @param fqdn le nom qualifié de machine (FQDN)
   */
  public AddCommand(String ip, String fqdn) {
    this.ip = ip;
    this.fqdn = fqdn;
  }

  /**
   * Exécute l'ajout dans le DNS.
   *
   * @param dns l'instance de DNS cible
   * @return chaîne vide en cas de succès
   * @throws Exception si l'écriture fichier échoue ou si les paramètres sont invalides (par ex.
   *                   doublon => IllegalStateException)
   */
  @Override
  public String execute(Dns dns) throws Exception {
    dns.addItem(new AdresseIp(ip), new NomMachine(fqdn));
    return ""; // succès silencieux (comme l’énoncé)
  }
}
