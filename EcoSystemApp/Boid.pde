class Boid {

  PVector loc, vel, acc; 
  float mass; 
  float dim; 
  float maxSpeed, maxForce; 
  float phiWander; 


  
  Boid(PVector loc_) { 
    loc = loc_.get(); 
    vel = new PVector(); 
    acc = new PVector(); 
    mass = 1.; 
    dim = 30.; 
    maxSpeed = 50.; 
    maxForce = 5000.; 
    phiWander = 0;
  } 
  
  //aplicar força
  void applyForce(PVector f) { 
    PVector aux = f.get(); 
    aux.limit(maxForce); 
    aux.div(mass); 
    acc.add(aux);
  } 
   
  //mover boid
  void move(float dt) { 
    vel.add(PVector.mult(acc, dt)); 
    vel.limit(maxSpeed); 
    loc.add(PVector.mult(vel, dt)); 
    stayInBox(); 
    acc.mult(0);
  } 
  
  //metodo que insere novamente na frame os obectos que saem 
  void stayInBox() { 
    if (loc.x < 0) loc.x += width; 
    if (loc.y < 0) loc.y += height; 
    loc.x %= width; 
    loc.y %= height;
  } 
  
  //metodo interceptar
  PVector seek(PVector target) { 
    PVector r = PVector.sub(target, loc); 
    r.normalize(); 
    r.mult(maxSpeed); 
    return PVector.sub(r, vel);
  } 
  
  //metodo vaguear
  PVector wander() { 
    float dt =.5; 
    float radius= 180; 
    float deltaPhi = PI/10; 
    PVector c = loc.get(); 
    c.add(PVector.mult(vel, dt)); 
    PVector t = new PVector(radius*cos(phiWander), radius*sin(phiWander)); 
    t.add(c); 
    phiWander += random(-deltaPhi, deltaPhi); 
    return seek(t);
  } 
  
  //celulas no campo de visao
  ArrayList<Cell> inVision(float dist, float viewAngle, CellularAutomata ca) { 
    ArrayList<Cell> cellsInVision = new ArrayList<Cell>(); 
    for (int i = 0; i < ca.nrows; i++) { 
      for (int j = 0; j < ca.ncols; j++) { 
        Cell c = ca.cells[i][j]; 
        PVector where = c.getCenter(); 
        float d = dist(loc.x, loc.y, where.x, where.y); 
        if (d < dist) { 
          PVector v = PVector.sub(where, loc); 
          float angle = PVector.angleBetween(vel, v); 
          angle *= (180./PI); 
          if (angle < viewAngle/2.) cellsInVision.add(c);
        }
      }
    }
    return cellsInVision;
  } 
  
  //evitar obstaculos
  PVector avoidObstacles(ArrayList<Cell> cells)
  {
    PVector f = new PVector();
    for (Cell c : cells) {
      if (c.state == 3) {
        f = new PVector(vel.y,-vel.x);
        break;
      }
    }
    return f;
  }
  
  //mostrar boid
  void display() { 
    pushMatrix(); 
    translate(loc.x, loc.y); 
    float angle = vel.heading(); 
    rotate(angle); 
    noFill(); 
    triangle(-dim, -dim/2, -dim, dim/2, dim, 0); 
    ellipse(0, 0, 5, 5); 
    popMatrix();
  }
}