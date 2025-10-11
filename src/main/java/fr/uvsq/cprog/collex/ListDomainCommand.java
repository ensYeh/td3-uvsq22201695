package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;

/**
 * Commande : liste les machines d'un domaine.
 */
public class ListDomainCommand implements Commande {

  private final String domaine;
  private final boolean sortByIp;

  /**
   * Construit la commande de listage.
   *
   * @param domaine  le nom de domaine (ex. "uvsq.fr")
   * @param sortByIp true pour trier par IP, false pour trier par nom
   */
  public ListDomainCommand(String domaine, boolean sortByIp) {
    this.domaine = domaine;
    this.sortByIp = sortByIp;
  }

  /**
   * Retourne la liste des items du domaine, triée et formatée.
   *
   * @param dns l'instance de DNS à interroger
   * @return lignes "ip fqdn" séparées par des sauts de ligne, ou chaîne vide si aucun résultat
   */
  @Override
  public String execute(Dns dns) {
    List<DnsItem> items = dns.getItems(domaine);
    if (items.isEmpty()) {
      return "";
    }

    if (sortByIp) {
      // Tri NUMÉRIQUE par octets de l'adresse IP
      items.sort((a, b) -> {
        int[] ia = parseIp(a.getAdresseIp().getAdresseIp());
        int[] ib = parseIp(b.getAdresseIp().getAdresseIp());
        for (int k = 0; k < 4; k++) {
          int cmp = Integer.compare(ia[k], ib[k]);
          if (cmp != 0) {
            return cmp;
          }
        }
        // IP égales : second critère par nom qualifié
        return a.getNomMachine().getNomQualifie().compareTo(b.getNomMachine().getNomQualifie());
      });
    } else {
      // Tri par nom qualifié puis IP (chaînes ok ici)
      items.sort(
          Comparator
              .comparing((DnsItem i) -> i.getNomMachine().getNomQualifie())
              .thenComparing(i -> i.getAdresseIp().getAdresseIp())
      );
    }

    StringBuilder sb = new StringBuilder();
    for (DnsItem i : items) {
      sb.append(i.getAdresseIp().getAdresseIp())
          .append(' ')
          .append(i.getNomMachine().getNomQualifie())
          .append(System.lineSeparator());
    }
    return sb.toString().trim();
  }

  /**
   * Parse "x.x.x.x" en 4 entiers pour un tri numérique fiable.
   */
  private static int[] parseIp(String s) {
    String[] p = s.split("\\.");
    return new int[]{
        Integer.parseInt(p[0]),
        Integer.parseInt(p[1]),
        Integer.parseInt(p[2]),
        Integer.parseInt(p[3])
    };
  }
}