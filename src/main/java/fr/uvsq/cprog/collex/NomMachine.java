package fr.uvsq.cprog.collex;

/**
 * Représente un nom qualifié de machine (FQDN).
 * Exemple : www.uvsq.fr
 */
public class NomMachine {

  private static final String REGEX = "^[a-z0-9]+(\\.[a-z0-9-]+)+$";

  private final String machine;
  private final String domaine;
  private final String nomQualifie;

  /**
   * Construit un nom qualifié de machine à partir d'une chaîne.
   *
   * @param nom le nom qualifié au format "machine.domaine"
   * @throws IllegalArgumentException si le nom est invalide
   */
  public NomMachine(String nom) {
    if (nom == null || nom.isBlank()) {
      throw new IllegalArgumentException("Nom de machine null ou vide.");
    }

    String s = nom.trim().toLowerCase();
    if (!s.matches(REGEX)) {
      throw new IllegalArgumentException("Nom de machine invalide : " + nom);
    }

    int index = s.indexOf('.');
    this.machine = s.substring(0, index);
    this.domaine = s.substring(index + 1);
    this.nomQualifie = s;
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
