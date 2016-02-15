

World world; 
PImage mouse, rabbit, wolf; 
int xc, yc; 

void setup() {
  //tamanho da janela
  size(1200, 720); 
  //criado um novo mundo 
  world = new World(10, 5, 10,10); 
  frameRate(30); 
  // Carregamento das imagens e ajuste de tamanho 
  xc = width/world.terrain.ncols; 
  yc =height/world.terrain.nrows; 
  mouse = loadImage("/IMG/rabbit.png"); 
  mouse.resize(xc, yc); 
  rabbit = loadImage("/IMG/deer.png"); 
  rabbit.resize(xc, yc); 
  wolf = loadImage("/IMG/lion.png"); 
  wolf.resize(xc, yc);
} 
void draw() { 
  background(125, 105, 75); 
  world.update(); 
  world.display();
} 