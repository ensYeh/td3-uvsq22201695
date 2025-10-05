package fr.uvsq.cprog.collex;

/**
 * Représente une adresse IPv4.
 */
public class AdresseIp {

  private final int[] adresseIp = new int[4];

  /**
   * Construit une adresse IP à partir d'une chaîne.
   *
   * @param ip la chaîne au format "x.x.x.x"
   * @throws IllegalArgumentException si l'adresse est invalide
   * @throws NumberFormatException    si l'adresse est invalide
   */
  public AdresseIp(String ip) {
    if (ip == null) {
      throw new IllegalArgumentException("Null n'est pas une IP");
    }

    String[] parts = ip.split("\\.");
    if (parts.length != 4) {
      throw new IllegalArgumentException("L'adresse ne comporte pas 4 parties");
    }

    for (int i = 0; i < 4; i++) {
      int x = Integer.parseInt(parts[i]);
      if (x < 0 || x > 255) {
        throw new IllegalArgumentException("Numéro invalide : " + x);
      }
      adresseIp[i] = x;
    }
  }

  @Override
  public String toString() {
    return adresseIp[0] + "." + adresseIp[1] + "." + adresseIp[2] + "." + adresseIp[3];
  }
}
