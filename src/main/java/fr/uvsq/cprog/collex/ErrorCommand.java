package fr.uvsq.cprog.collex;

/**
 * Commande représentant une erreur de saisie ou d'exécution. Cette commande ne modifie pas la base
 * DNS. Lors de son exécution, elle retourne simplement un message d'erreur préfixé par
 * "ERREUR : ".
 */
public class ErrorCommand implements Commande {

  private final String message;

  /**
   * Construit une commande d'erreur avec le message donné.
   *
   * @param message le message d'erreur (non null)
   */
  public ErrorCommand(String message) {
    this.message = message;
  }

  /**
   * Exécute la commande d'erreur.
   *
   * @param dns non utilisé, fourni pour respecter l'interface
   * @return le message d'erreur complet au format "ERREUR : message"
   */
  @Override
  public String execute(Dns dns) {
    return "ERREUR : " + message;
  }
}
