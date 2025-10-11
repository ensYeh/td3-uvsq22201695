package fr.uvsq.cprog.collex;

/**
 * Interface du modèle Commande. Chaque commande représente une action à exécuter sur un DNS.
 */
public interface Commande {

  /**
   * Exécute l'action associée à cette commande.
   *
   * @param dns l'objet DNS sur lequel exécuter la commande
   * @return le texte à afficher après exécution (chaîne vide si aucun résultat)
   * @throws Exception si une erreur survient pendant l'exécution
   */
  String execute(Dns dns) throws Exception;
}
