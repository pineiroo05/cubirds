
package gal.uvigo.esei.aed1.cubirds.iu;

import gal.uvigo.esei.aed1.cubirds.core.Game;


public class Main {

  public static void main(String[] args) {
    IU iu = new IU();
    Game cubirds = new Game(iu);
    cubirds.play();
  }
}
