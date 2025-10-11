package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AdresseIpTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "1.1.1.1",
      "127.0.0.1"
  })
  public void testAdresseIP(String ip) {
    assertEquals(ip, new AdresseIp(ip).getAdresseIp());
  }

  @ParameterizedTest
  @NullSource
  public void testAdresseIPNull(String ip) {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp(ip));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "1-1.1.1",  // Format invalide
      "127.0.0",  // Format invalide
      "12a.0.0.1",  // Caractère interdit
      "456.0.0.1",  // Borne illegal
      "-356.0.0.1"  // Borne illegal
  })
  public void testAdresseIPIllegal(String ip) {
    assertThrows(IllegalArgumentException.class, () -> new AdresseIp(ip));
  }

  @Test
  void testCompareTo() {
    AdresseIp ip1 = new AdresseIp("192.168.0.1");
    AdresseIp ip2 = new AdresseIp("192.168.0.2");
    AdresseIp ip3 = new AdresseIp("192.168.0.1");

    assertTrue(ip1.compareTo(ip2) < 0);
    assertTrue(ip2.compareTo(ip1) > 0);
    assertEquals(0, ip1.compareTo(ip3));
  }
}
