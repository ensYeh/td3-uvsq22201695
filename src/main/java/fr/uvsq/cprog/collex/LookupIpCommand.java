package fr.uvsq.cprog.collex;

/**
 * Commande : recherche l'adresse IP à partir d'un nom de machine qualifié (FQDN).
 */
public class LookupIpCommand implements Commande {

  private final String fqdn;

  /**
   * Construit la commande de recherche IP.
   *
   * @param fqdn le nom qualifié de machine (ex. "www.uvsq.fr")
   */
  public LookupIpCommand(String fqdn) {
    this.fqdn = fqdn;
  }

  /**
   * Retourne l'adresse IP associée si elle existe, sinon "Nom inconnu".
   *
   * @param dns l'instance de DNS à interroger
   * @return l'IP (ex. "193.51.31.90") ou "Nom inconnu"
   */
  @Override
  public String execute(Dns dns) {
    DnsItem it = dns.getItem(new NomMachine(fqdn));
    return (it == null) ? "Nom inconnu" : it.getAdresseIp().getAdresseIp();
  }
}
