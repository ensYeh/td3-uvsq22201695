package fr.uvsq.cprog.collex;

/**
 * Commande : recherche le nom qualifié à partir d'une adresse IP.
 */
public class LookupNameCommand implements Commande {

  private final String ip;

  /**
   * Construit la commande de recherche de nom.
   *
   * @param ip l'adresse IP (ex. "193.51.31.90")
   */
  public LookupNameCommand(String ip) {
    this.ip = ip;
  }

  /**
   * Retourne le FQDN associé si trouvé, sinon "Adresse inconnue".
   *
   * @param dns l'instance de DNS à interroger
   * @return le FQDN (ex. "www.uvsq.fr") ou "Adresse inconnue"
   */
  @Override
  public String execute(Dns dns) {
    DnsItem it = dns.getItem(new AdresseIp(ip));
    return (it == null) ? "Adresse inconnue" : it.getNomMachine().getNomQualifie();
  }
}
