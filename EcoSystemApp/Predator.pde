//classe geral dos predadores
class Predator extends Animal {

  Predator(PVector loc_, String type,World world_) {

    super(loc_, type,world_);
    energy = 100;
  }

  //predador alimenta-se 
  void eat(CellularAutomata terrain) {

    Cell c = terrain.getCell((int)loc.x, (int)loc.y); // Obter a célula onde se localiza

    if (c.state == 4){ c.state = 0; this.energy -= 10.0;}

    ArrayList<Animal> presasEmVisao = new ArrayList<Animal>();
    presasEmVisao = c.whichPreysAreOn(world.animals);

    for (Animal a : presasEmVisao) {
      if (a.type == "deer"){ this.energy += a.energy/2.; a.energy = 0.;}
      if (a.type == "rabbit"){ this.energy += a.energy/3.; a.energy = 0.;}
    }
  }
  
  //predador reproduze-se
  void reproduce (CellularAutomata terrain) { 
    if (energy > 150.) { 
      Cell c = terrain.getCell((int)loc.x, (int)loc.y); // Obter a celula onde se encontra
      int n = (int)random(c.neighbors.size()); // Escolher urns celula aleateria da vizinhanca 
      c = c.neighbors.get(n); // Obter a celula vizinha 
      PVector loc = c.getCenter(); // Obter coordenadas da celula vizinha 
      Predator p = new Predator(loc,this.type, world); // Crier press na celula vizinha 
      p.energy = energy/2; 
      this.energy = energy/2; 
      world.animals.add(p);
    }
  }

  //predador move-se
  void move(float dt, CellularAutomata terrain) {

    PVector f = wander();
    applyForce(f);
    super.move(dt);
    this.energy -=0.5*dt;
  }

}