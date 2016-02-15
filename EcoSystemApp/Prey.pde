//classe geral das presas
class Prey extends Animal { 
  Prey(PVector loc_,String type, World world_) { 
    super(loc_,type, world_); 
    energy = 75.;
  } 
  
  //animal alimenta-se caso celula seja alimento ou pisa obstaculo
  void eat (CellularAutomata terrain) { 
    Cell c = terrain.getCell((int)loc.x, (int)loc.y); // Obter a celula onde se localiza 
    if (c.state == 2) { 
      c.state = 0; // Animal come erva 
      this.energy += 1.5; //
    } 
    if (c.state == 3) {
      c.state=0; // Animal come cogumelo 
      this.energy += 3.0; //
    } 
    if (c.state == 4) { 
      c.state = 0; // Animal pisa terreno com obstaculos 
      this.energy -= 5.0;
    } 
  }

  //tentar reproduzir a presa
  void reproduce (CellularAutomata terrain) { 
    if (energy > 150.) { 
      Cell c = terrain.getCell((int)loc.x, (int)loc.y); // Obter a celula onde se encontra
      int n = (int)random(c.neighbors.size()); // Escolher urns celula aleateria da vizinhanca 
      c = c.neighbors.get(n); // Obter a celula vizinha 
      PVector loc = c.getCenter(); // Obter coordenadas da celula vizinha 
      Prey p = new Prey(loc,this.type, world); // Crier press na celula vizinha 
      p.energy = energy/2; 
      this.energy = energy/2; 
      world.animals.add(p);
    }
  }
  
  //mover presa
  void move(float dt, CellularAutomata terrain) {

    PVector f = wander();
    applyForce(f);
    super.move(dt);
    this.energy -=0.5*dt;
  }
}