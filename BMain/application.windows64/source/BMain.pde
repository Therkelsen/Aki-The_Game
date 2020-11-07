  
  //---------- Import the sound library to play sound -----//
  import processing.sound.*;
  
  //---------- Variables needed for sound ----------//
  SoundFile soundtrack;
  SoundFile munch;
  String path1 = "data\\bumblebee.mp3";
  String path2 = "data\\munch.mp3";
  boolean soundStarted = false;
  
  //---------- Variables needed for character sprites ----------//
  PImage beeSprite;
  PImage emojiSprite;
  PImage background;
  
  //---------- Variables needed for characters ----------//
  ArrayList<Emoji> emojis = new ArrayList<Emoji>();
  Bee player = new Bee();
  MenuElements menu = new MenuElements();
  
  //---------- Variables needed for the main game ----------//
  int initialEmojis = 10;
  int minimumEmojis = 10;
  int amountToSpawn = 10;
  
  //---------- Variables needed for the main game ----------//
  int gameWidth = 1280;
  int gameHeight = 900;
  boolean atStartup = true;
  boolean startScreenMousePressed = false;
  
  void settings() {
    //---------- Set the canvas size ----------//
    size(gameWidth, gameHeight);
  }
  
  void setup() {
    //---------- Everything that only needs to be run once goes in here ----------//
    smooth();
    background(0);
    frameRate(120);
  
    //---------- Make the background music work and turn the volume down to 10% ----------//
    soundtrack = new SoundFile(this, path1);
    soundtrack.amp(0.1);
  
    //---------- Make the munch sound work and turn the volume down to 10% ----------//
    munch = new SoundFile(this, path2);
    munch.amp(0.1);
  
    //---------- Call the constructors for the needed classes ----------//
    player = new Bee();
    menu = new MenuElements();
  
    for (int i = 0; i < initialEmojis; i++) { 
      emojis.add(new Emoji());
    }
  
    //---------- Load the images for the characters and set the anchorpoint of the images ----------//
    beeSprite = loadImage("bee.png");
    emojiSprite = loadImage("b.png");
    background = loadImage("flowers.jpg");
    imageMode(CENTER);
  }
  
  void draw() {
    //---------- If the game isn't at startup, run the game and start the soundtrack ----------//
    if (!atStartup) {
      game();
      soundTrack();
      //---------- If not, run the start screen ----------//
    } else {
      startScreen();
    }
  }
  
  //---------- When a key is pressed, if it's a coded key, store said key as pressed ----------//
  void keyPressed() {
    if (key != CODED) {
      player.keys[key] = true;
    }
  }
  
  //---------- When a key is released, if it's a coded key, store said key as not pressed ----------//
  void keyReleased() {
    if (key != CODED) {
      player.keys[key] = false;
    }
  }
  
  void startScreen() {
    background(125);
    image(background, width/2, height/2);
    menu.title();
    menu.buttons();
    menu.interaction();
  }
  
  void game() {
    background(125);
    image(background, width/2, height/2);
    
    player.displayHUD();
    player.update();
    player.checkEdges();
    player.checkCollision();
    
    image(beeSprite, player.location.x, player.location.y, player.d, player.d);
  
    for (int i = 0; i < emojis.size(); i++) {
      Emoji emoj = emojis.get(i);
      emoj.update();
      emoj.checkEdges();
      image(emojiSprite, emoj.location.x, emoj.location.y, emoj.d, emoj.d);
    }
  
    if (emojis.size() < minimumEmojis) {
      emojis.add(new Emoji());
    }
  }
  
  void soundTrack() {
    if (soundStarted == false) {
      soundtrack.play();
      soundStarted = true;
    }
  }
