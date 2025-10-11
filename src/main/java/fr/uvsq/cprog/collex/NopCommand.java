package fr.uvsq.cprog.collex;

/**
 * Commande ne réalisant aucune action.
 * Cette commande est utilisée lorsque l'utilisateur saisit une ligne vide. Elle ne modifie pas la
 * base de données DNS et retourne simplement une chaîne vide.
 */
public class NopCommand implements Commande {

  /**
   * Exécute la commande vide.
   *
   * @param dns l'objet DNS (non utilisé ici)
   * @return une chaîne vide
   */
  @Override
  public String execute(Dns dns) {
    return "";
  }
}
