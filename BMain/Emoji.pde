  
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
  
    void update() {
      location.x += direction.x;
      location.y += direction.y;
    }
  
    void checkEdges() {
      if (location.x > width-r || location.x < 0+r) {
        direction.x = -direction.x;
      }
      if (location.y > height-r || location.y < 0+r) {
        direction.y = -direction.y;
      }
    }
  }
