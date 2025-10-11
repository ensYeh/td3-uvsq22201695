package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NopCommandTest {

  @Test
  void testExecuteRetourneChaineVide() throws Exception {
    NopCommand commande = new NopCommand();
    String resultat = commande.execute(null);
    assertEquals("", resultat);
  }

  @Test
  void testExecuteNeLancePasException() {
    NopCommand commande = new NopCommand();
    assertDoesNotThrow(() -> commande.execute(null));
  }

}
