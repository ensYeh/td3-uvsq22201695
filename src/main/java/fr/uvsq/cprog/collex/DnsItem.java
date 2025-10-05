package fr.uvsq.cprog.collex;

/**
 * Représente une entrée dans la base DNS :
 * un couple (nom de machine, adresse IP).
 */
public class DnsItem {

  private final NomMachine nomMachine;
  private final AdresseIp adresseIp;

  /**
   * Construit un item DNS.
   *
   * @param nomMachine le nom de machine
   * @param adresseIp  l'adresse IP
   */
  public DnsItem(NomMachine nomMachine, AdresseIp adresseIp) {
    this.nomMachine = nomMachine;
    this.adresseIp = adresseIp;
  }

  public NomMachine getNomMachine() {
    return nomMachine;
  }

  public AdresseIp getAdresseIp() {
    return adresseIp;
  }

}
