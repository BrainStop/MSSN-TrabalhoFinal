//classe geral de todos os animais(presas e predadores)
class Animal extends Boid {

  String type; 
  PImage shape; 
  float energy; 
  World world;
  
  //iniciali
  Animal (PVector loc_,String type, World world_) { 
    super(loc_); 
    this.type = type;
    this.world = world_;
  } 
  
  //metodos abstratos
  void move (float dt, CellularAutomata terrain) {
  } 
  void eat (CellularAutomata terrain) {
  } 
  void reproduce (CellularAutomata terrain) {
  } 
  
  //metodo que verifica se o Animal morreu ( energia <=0)
  void die() { 
    if (energy <= 0) { 
    world.animals.remove(this);    } 
  } 

  //display do animal conforme o seu tipo
  void display() {

    if (type == "lion") {

      image(wolf, loc.x-(xc/2), loc.y-(yc/2));
    }
    if (type == "rabbit") {

      image(rabbit, loc.x-(xc/2), loc.y-(yc/2));
    }
    if (type == "deer") {

      image(mouse, loc.x-(xc/2), loc.y-(yc/2));
    }
  }
}