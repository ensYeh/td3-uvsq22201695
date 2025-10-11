package fr.uvsq.cprog.collex;

/**
 * Commande de terminaison de l'application DNS.
 */
public class QuitCommand implements Commande {

  /**
   * Exécute la commande de sortie (aucune action concrète ici).
   *
   * @param dns le DNS (non utilisé)
   * @return une chaîne vide
   */
  @Override
  public String execute(Dns dns) {
    return "";
  }
}
