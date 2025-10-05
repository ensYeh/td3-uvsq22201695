package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NomMachineTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "www.uvsq.fr",
      "w.u.fr",
      "api.example.com",
      "mail.google.com"
  })
  public void fqdn_valide(String s) {
    NomMachine n = new NomMachine(s);
    int idx = n.getNomQualifie().indexOf('.');
    assertEquals(n.getNomQualifie().substring(0, idx), n.getMachine());
    assertEquals(n.getNomQualifie().substring(idx + 1), n.getDomaine());
  }

  @Test
  public void normalisation_minuscules() {
    NomMachine n = new NomMachine("WWW.UVSQ.FR");
    assertEquals("www", n.getMachine());
    assertEquals("uvsq.fr", n.getDomaine());
    assertEquals("www.uvsq.fr", n.getNomQualifie());
  }

  @Test
  public void supprime_espaces() {
    NomMachine n = new NomMachine("   www.uvsq.fr   ");
    assertEquals("www.uvsq.fr", n.getNomQualifie());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "", "   ", // vide/blanc
      "uvsq", // pas de point
      ".uvsq.fr", // point en début
      "www.uvsq.fr.", // point en fin
      "www..fr", // double point
      "w#w.uvsq.fr", // caractère interdit
      "www.uvsq!.fr", // caractère interdit
      "www.école.fr" // non ASCII (hors regex)
  })
  public void formel_invalide(String s) {
    assertThrows(IllegalArgumentException.class, () -> new NomMachine(s));
  }

  @ParameterizedTest
  @NullSource
  public void null_interdit(String s) {
    assertThrows(IllegalArgumentException.class, () -> new NomMachine(s));
  }
}
