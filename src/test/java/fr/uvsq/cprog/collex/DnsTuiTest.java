package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class DnsTuiTest {

  private static DnsTui creerTui(String saisie, ByteArrayOutputStream sortieBuffer) {
    ByteArrayInputStream entree = new ByteArrayInputStream(saisie.getBytes());
    PrintStream sortie = new PrintStream(sortieBuffer, true);
    return new DnsTui(entree, sortie);
  }

  @Test
  void testQuitRetourneQuitCommand() {
    var buffer = new ByteArrayOutputStream();

    DnsTui tui = creerTui("quit\n", buffer);
    assertInstanceOf(QuitCommand.class, tui.nextCommande());

    tui = creerTui("q\n", buffer);
    assertInstanceOf(QuitCommand.class, tui.nextCommande());

    tui = creerTui("exit\n", buffer);
    assertInstanceOf(QuitCommand.class, tui.nextCommande());
  }

  @Test
  void testLigneVideRetourneNopCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("\n", buffer);
    assertInstanceOf(NopCommand.class, tui.nextCommande());
  }

  @Test
  void testLsSansOptionCreeListDomainCommandTriNom() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("ls uvsq.fr\n", buffer);
    assertInstanceOf(ListDomainCommand.class, tui.nextCommande());
  }

  @Test
  void testLsAvecOptionMoinsACreeListDomainCommandTriIp() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("ls -a uvsq.fr\n", buffer);
    assertInstanceOf(ListDomainCommand.class, tui.nextCommande());
  }

  @Test
  void testAddCreeAddCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("add 193.51.25.24 pikachu.uvsq.fr\n", buffer);
    assertInstanceOf(AddCommand.class, tui.nextCommande());
  }

  @Test
  void testAdresseIpRetourneLookupNameCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("193.51.31.90\n", buffer);
    assertInstanceOf(LookupNameCommand.class, tui.nextCommande());
  }

  @Test
  void testNomMachineRetourneLookupIpCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("www.uvsq.fr\n", buffer);
    assertInstanceOf(LookupIpCommand.class, tui.nextCommande());
  }

  @Test
  void testCommandeInvalideRetourneErrorCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("invalide truc\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());
  }

  @Test
  void testAfficheIgnoreNullEtVide() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("", buffer);

    tui.affiche(null);
    tui.affiche("   ");
    assertEquals("", buffer.toString());

    tui.affiche("OK");
    assertEquals("OK" + System.lineSeparator(), buffer.toString());
  }
}
