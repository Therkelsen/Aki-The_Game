
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

  void update() {
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

  void checkEdges() {
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

  void checkCollision() {
    for (int i = 0; i < emojis.size(); i++) {
      Emoji emoj = emojis.get(i);
      
      if (dist(location.x, location.y, emoj.location.x, emoj.location.y) < r + emoj.r) {
        emoj.dead = true;
        emojis.remove(i);
        munch.play();
        score++;
        if (moveSpd <= 40) {
          moveSpd *= 1.01;
        }
      }
    }
  }

  void displayHUD() {
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
