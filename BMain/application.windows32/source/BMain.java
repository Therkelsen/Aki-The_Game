import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BMain extends PApplet {

  
  //---------- Import the sound library to play sound -----//
  
  
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
  
  public void settings() {
    //---------- Set the canvas size ----------//
    size(gameWidth, gameHeight);
  }
  
  public void setup() {
    //---------- Everything that only needs to be run once goes in here ----------//
    smooth();
    background(0);
    frameRate(120);
  
    //---------- Make the background music work and turn the volume down to 10% ----------//
    soundtrack = new SoundFile(this, path1);
    soundtrack.amp(0.1f);
  
    //---------- Make the munch sound work and turn the volume down to 10% ----------//
    munch = new SoundFile(this, path2);
    munch.amp(0.1f);
  
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
  
  public void draw() {
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
  public void keyPressed() {
    if (key != CODED) {
      player.keys[key] = true;
    }
  }
  
  //---------- When a key is released, if it's a coded key, store said key as not pressed ----------//
  public void keyReleased() {
    if (key != CODED) {
      player.keys[key] = false;
    }
  }
  
  public void startScreen() {
    background(125);
    image(background, width/2, height/2);
    menu.title();
    menu.buttons();
    menu.interaction();
  }
  
  public void game() {
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
  
  public void soundTrack() {
    if (soundStarted == false) {
      soundtrack.play();
      soundStarted = true;
    }
  }

class Bee {

  boolean keys[] = new boolean[128];

  PVector location;

  float d = 75;
  float r = d/2;

  float moveSpd = 20;

  int score = 0;

  Bee() {
    // Constructor for the class
    location = new PVector(width/2, height/2);
  }

  public void update() {
    if (keys['w']) {
      location.y = location.y - moveSpd;
    }
    if (keys['a']) {
      location.x = location.x - moveSpd;
    }
    if (keys['s']) {
      location.y = location.y + moveSpd;
    }
    if (keys['d']) {
      location.x = location.x + moveSpd;
    }
  }

  public void checkEdges() {
    //Check if the ball is near the edges
    if (location.x > width) {
      location.x = 0;
    } else if (location.x < 0) {  
      location.x = width;
    }
    if (location.y > height) {
      location.y = 0;
    } else if (location.y < 0) {   
      location.y = height;
    }
  }

  public void checkCollision() {
    for (int i = 0; i < emojis.size(); i++) {
      Emoji emoj = emojis.get(i);
      
      if (dist(location.x, location.y, emoj.location.x, emoj.location.y) < r + emoj.r) {
        emoj.dead = true;
        emojis.remove(i);
        munch.play();
        score++;
        if (moveSpd <= 40) {
          moveSpd *= 1.01f;
        }
      }
    }
  }

  public void displayHUD() {
    strokeWeight(1);
    stroke(0);
    fill(255);
    rectMode(CORNER);
    if (score < 10) {
      rect(15, height - 60, 360, 35, 7);
    } else if (score >= 10 && score < 100) {
      rect(15, height - 60, 385, 35, 7);
    } else if (score >= 100 && score < 1000) {
      rect(15, height - 60, 405, 35, 7);
    } else if (score >= 1000 && score < 10000) {
      rect(15, height - 60, 430, 35, 7);
    } else if (score >= 10000 && score < 100000) {
      rect(15, height - 60, 455, 35, 7);
    } else if (score >= 100000 && score < 1000000) {
      rect(15, height - 60, 455, 35, 7);
    } else if (score >= 1000000 && score < 10000000) {
      rect(15, height - 60, 455, 35, 7);
    } else if (score >= 10000000 && score < 100000000) {
      rect(15, height - 60, 455, 35, 7);
    } else if (score >= 100000000 && score < 1000000000) {
      rect(15, height - 60, 455, 35, 7);
    }
    
    fill(0);
    textAlign(LEFT);
    textSize(32);
    text("B's eaten by the Bee: " + score, 25, height - 30);
  }
}
  
  class Emoji {
  
    PVector location;
    PVector direction;
  
    float d = 75;
    float r = d/2;
  
    float moveSpd = random(10, 15);
  
    boolean dead = false;
  
    Emoji() {
      // Constructor for the class
  
      // Make mouse, location, velocity, direction, acceleration and speed work.
      location = new PVector(random(0+r, width-r), random(0+r, height-r));
      direction = new PVector(random(-moveSpd, moveSpd), random(-moveSpd, moveSpd));
    }
  
    public void update() {
      location.x += direction.x;
      location.y += direction.y;
    }
  
    public void checkEdges() {
      if (location.x > width-r || location.x < 0+r) {
        direction.x = -direction.x;
      }
      if (location.y > height-r || location.y < 0+r) {
        direction.y = -direction.y;
      }
    }
  }
  
  class MenuElements {
  
    boolean mouseOverButton1 = false;
    boolean mouseOverButton2 = false;
  
    int buttonWidth;
    int buttonHeight;
  
    int button1PosX;
    int button1PosY;
  
    int button2PosX;
    int button2PosY;
  
    MenuElements() {
      // Constructor for the class
  
      mouseOverButton1 = false;
      mouseOverButton2 = false;
  
      buttonWidth = width/3;
      buttonHeight = height/12;
  
      button1PosX = width/2;
      button1PosY = height/2 + 150;
  
      button2PosX = width/2;
      button2PosY = height/2 + 250;
    }
  
    public void title() {
      textAlign(CENTER, CENTER);
      fill(50, 168, 86);
      textSize(135);
      text("Bee", width/2, 150);
    }
  
    public void loadingScreen() {
      textAlign(CENTER, CENTER);
      fill(0);
      textSize(135);
      text("Loading", width/2, height/2);
    }
  
    public void buttons() {  
      strokeWeight(1);
      stroke(0);
      fill(200);
      rectMode(CENTER);
      rect(button1PosX, button1PosY, buttonWidth, buttonHeight);
      rect(button2PosX, button2PosY, buttonWidth, buttonHeight);
  
      textAlign(CENTER, CENTER);
      fill(0);
      textSize(32);
      text("Start the bee'ening", width/2, height/2 + 147);
      text("Exit ;'(", width/2, height/2 + 247);
    }
  
    public void interaction() {
  
      if (mouseX >= button1PosX - buttonWidth/2 && mouseX <= button1PosX + buttonWidth/2) {
        if (mouseY >= button1PosY - buttonHeight/2 && mouseY <= button1PosY + buttonHeight/2) {
          mouseOverButton1 = true;
        }
      } else {
        mouseOverButton1 = false;
      }
  
      if (mouseX >= button2PosX - buttonWidth/2 && mouseX <= button2PosX + buttonWidth/2) {
        if (mouseY >= button2PosY - buttonHeight/2 && mouseY <= button2PosY + buttonHeight/2) {
          mouseOverButton2 = true;
        }
      } else {
        mouseOverButton2 = false;
      }
  
      if (mouseOverButton1 && mousePressed == true) {
        atStartup = false;
      }
  
      if (mouseOverButton2 && mousePressed == true) {
        exit();
      }
    }   
  }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BMain" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
