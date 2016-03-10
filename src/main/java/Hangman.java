public class Hangman {

  public static void main(String[] args) {
      // Enter amazing code here:
    Game game = new Game("epicodus");
    Prompter prompter = new Prompter(game);
    prompter.play();
  }
}
