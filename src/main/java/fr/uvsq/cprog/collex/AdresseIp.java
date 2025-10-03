package fr.uvsq.cprog.collex;

/**
 * Représente une adresse IPv4.
 */
public class AdresseIp {

  private final String adresseIp;

  /**
   * Construit une adresse IP à partir d'une chaîne.
   *
   * @param ip la chaîne au format "x.x.x.x"
   */
  public AdresseIp(String ip) {
    this.adresseIp = ip;
  }

  @Override
  public String toString() {
    return adresseIp;
  }
}
