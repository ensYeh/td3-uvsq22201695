package fr.uvsq.cprog.collex;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

/**
 * - nextCommande() lit la ligne et construit une Commande (sans l'exécuter). - affiche() affiche un
 * résultat. Les classes de commandes et l'interface Commande sont définies dans d'autres fichiers.
 */
public final class DnsTui {

  // Regex légères pour distinguer IP et FQDN (la validation fine est déléguée aux classes dédiées).
  private static final String REGEX_IP = "^[0-9]{1,3}(\\.[0-9]{1,3}){3}$";
  private static final String REGEX_FQDN = "^[A-Za-z0-9]+(\\.[A-Za-z0-9-]+)+$";

  private final Scanner in;
  private final PrintStream out;

  /**
   * Construit une interface textuelle pour le DNS.
   */
  public DnsTui(InputStream input, PrintStream output) {
    this.in = new Scanner(input);
    this.out = output;
  }

  /**
   * Affiche une chaîne de caractères (si non nulle ou vide).
   */
  public void affiche(String s) {
    if (s != null && !s.isBlank()) {
      out.println(s);
    }
  }

  /**
   * Lit et parse la prochaine commande utilisateur puis retourne un objet Commande. Q3 uniquement :
   * aucune exécution ici, juste la construction de la commande.
   */
  public Commande nextCommande() {
    out.print("> ");
    if (!in.hasNextLine()) {
      return new QuitCommand(); // Fin de flux
    }

    String raw = in.nextLine().trim();
    if (raw.isEmpty()) {
      return new NopCommand(); // Ligne vide => no-op
    }

    String[] p = raw.split("\\s+");
    String head = p[0].toLowerCase(Locale.ROOT);

    // quit / exit / q
    if ("q".equals(head) || "quit".equals(head) || "exit".equals(head)) {
      return new QuitCommand();
    }

    // ls [-a] domaine
    if ("ls".equals(head)) {
      if (p.length == 2) {
        return new ListDomainCommand(p[1], /*sortByIp=*/false);
      } else if (p.length == 3 && "-a".equals(p[1])) {
        return new ListDomainCommand(p[2], /*sortByIp=*/true);
      } else {
        return new ErrorCommand("Usage : ls [-a] domaine");
      }
    }

    // add <ip> <fqdn>
    if ("add".equals(head)) {
      if (p.length == 3) {
        return new AddCommand(p[1], p[2]);
      } else {
        return new ErrorCommand("Usage : add <ip> <fqdn>");
      }
    }

    // Ligne simple : IP => lookup nom ; FQDN => lookup IP
    if (p.length == 1) {
      String token = p[0];
      if (token.matches(REGEX_IP)) {
        return new LookupNameCommand(token);
      } else if (token.matches(REGEX_FQDN)) {
        return new LookupIpCommand(token);
      } else {
        return new ErrorCommand("Commande ou format non reconnu");
      }
    }

    return new ErrorCommand("Commande inconnue");
  }
}
