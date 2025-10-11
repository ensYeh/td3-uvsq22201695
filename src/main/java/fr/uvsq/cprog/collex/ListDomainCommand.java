package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;

/**
 * Commande : liste les machines d'un domaine.
 */
public class ListDomainCommand implements Commande {

  private final String domaine;
  private final boolean sortByIp;

  public ListDomainCommand(String domaine, boolean sortByIp) {
    this.domaine = domaine;
    this.sortByIp = sortByIp;
  }

  @Override
  public String execute(Dns dns) {
    List<DnsItem> items = dns.getItems(domaine);
    if (items.isEmpty()) {
      return "";
    }

    if (sortByIp) {
      items.sort(Comparator.comparing(DnsItem::getAdresseIp));
    } else {
      items.sort(Comparator.comparing(i -> i.getNomMachine().getNomQualifie()));
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
}