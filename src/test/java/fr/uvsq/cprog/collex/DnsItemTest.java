package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DnsItemTest {

  @Test
  public void constructeur_refuse_null() {
    AdresseIp ip = new AdresseIp("193.51.31.90");
    NomMachine nm = new NomMachine("www.uvsq.fr");

    assertThrows(IllegalArgumentException.class, () -> new DnsItem(null, ip));
    assertThrows(IllegalArgumentException.class, () -> new DnsItem(nm, null));
  }

  @Test
  public void getters() {
    AdresseIp ip = new AdresseIp("193.51.31.90");
    NomMachine nm = new NomMachine("www.uvsq.fr");

    DnsItem item = new DnsItem(nm, ip);

    assertSame(nm, item.getNomMachine());
    assertSame(ip, item.getAdresseIp());
  }

}
