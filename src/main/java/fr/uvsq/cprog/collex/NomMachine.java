package fr.uvsq.cprog.collex;

/**
 * Représente un nom qualifié de machine (FQDN).
 * Exemple : www.uvsq.fr
 */
public class NomMachine {

  private final String machine;
  private final String domaine;
  private final String nomQualifie;

  /**
   * Construit un nom qualifié de machine à partir d'une chaîne.
   *
   * @param nom le nom qualifié au format "machine.domaine"
   */
  public NomMachine(String nom) {
    this.nomQualifie = nom;
    this.machine = nom;
    this.domaine = "";
  }

  public String getDomaine() {
    return domaine;
  }

  public String getMachine() {
    return machine;
  }

  public String getNomQualifie() {
    return nomQualifie;
  }
}
