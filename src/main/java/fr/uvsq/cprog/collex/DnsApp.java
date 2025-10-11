package fr.uvsq.cprog.collex;

/**
 * Application principale : lit les commandes via la TUI, exécute et affiche le résultat.
 * <p>Cette classe joue le rôle d'« invoker » dans le patron Commande :
 * - DnsTui construit une instance de Commande (sans l'exécuter), - DnsApp appelle
 * commande.execute(dns) et affiche le résultat, - on quitte proprement si la commande est une
 * QuitCommand.</p>
 */
public final class DnsApp {

  private DnsApp() {
    // utilitaire
  }

  public static void main(String[] args) throws Exception {
    run();
  }

  /**
   * Boucle principale de l'application.
   *
   * @throws Exception si une erreur non gérée survient à l'initialisation
   */
  public static void run() throws Exception {
    Dns dns = new Dns();
    DnsTui tui = new DnsTui(System.in, System.out);

    while (true) {
      Commande commande = tui.nextCommande();

      if (commande instanceof QuitCommand) {
        break;
      }

      String resultat;
      try {
        resultat = commande.execute(dns);
      } catch (IllegalStateException e) {
        // ex. add : doublon de nom/IP
        resultat = "ERREUR : " + e.getMessage();
      } catch (Exception e) {
        // I/O, parsing, etc.
        resultat = "ERREUR : " + e.getClass().getSimpleName() + " : " + e.getMessage();
      }

      tui.affiche(resultat);
    }
  }
}
