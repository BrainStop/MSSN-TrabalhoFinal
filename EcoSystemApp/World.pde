class World { 


  CellularAutomata terrain; 
  ArrayList <Animal> animals; 
  int nL, nW; 
  float lastUpdateTime, dt; 
  boolean preysSelected;
  int lastClick= millis();

  World (int nD, int nL, int nR, int nO) { 


    preysSelected= true;

    terrain = new CellularAutomata(40, 40); 
    // Numero de obstaculos distribuidos aleatoriamente pelo terreno 

    int row, col;

    for (int i = 0; i < nO; i++) {
      row = (int) random(0, terrain.nrows);
      col = (int) random(0, terrain.ncols);
      if (terrain.cells[row][col].state == 4 && terrain.cells[row][col].initialState == 4) {
        while (terrain.cells[row][col].state == 4 && terrain.cells[row][col].initialState == 4) {
          row = (int) random(0, terrain.nrows);
          col = (int) random(0, terrain.ncols);
          terrain.cells[row][col].state = 4;
          terrain.cells[row][col].initialState = 4;
          terrain.cells[row][col].calculateRegenerationTime();
        }
      } else {
        terrain.cells[row][col].state = 4;
        terrain.cells[row][col].initialState = 4;
        terrain.cells[row][col].calculateRegenerationTime();
      }
    }
    // Criar Array com animais
    animals = new ArrayList <Animal>(); 

    for (int i = 0; i < nD; i++) { 
      PVector loc = new PVector(random(0, height), random(0, width)); 
      Prey d = new Prey(loc, "deer", this); 
      animals.add(d);
    } 

    for (int i = 0; i < nR; i++) { 
      PVector loc = new PVector(random(0, height), random(0, width)); 
      Prey r = new Prey(loc, "rabbit", this); 
      animals.add(r);
    }

    for (int i = 0; i < nL; i++) { 
      PVector loc = new PVector(random(0, height), random(0, width)); 
      Predator l = new Predator(loc, "lion", this); 
      animals.add(l);
    }
  } 


  //metodo que faz update do ambiente
  void update () {
    dt = (millis() - lastUpdateTime)/1000; 
    terrain.regenerate(dt); 
    for (int i =animals.size()-1; i>=0; i--) {
      Animal a = animals.get(i); 
      a.move(dt, terrain); // Mover animal 
      a.reproduce(terrain); 
      a.eat(terrain); // Verificar se animal come 
      if (a.energy<=0) { // Verificar se animal morre 
        animals.remove(a);
      }
    } 
    // Retirar obstaculos com input do rato 
    if (mousePressed) {
      if (mouseButton == RIGHT) { 
        Cell c = terrain.getCell(mouseX, mouseY); // Obter a cElula onde se localize 
        if (c.state == 4) {
          c.state = 0;
        }
      }
    } 
    //adicionar obstaculos com o click do rato
    if (mousePressed) { 
      if (mouseButton ==LEFT) { 
        Cell c= terrain.getCell(mouseX, mouseY); 
        if (c.state == 0) c.state =4;
      }
    }

    //detectar se foi acionada uma tecla da GUI
    //setas na horizontal aumentam tipo de animal seleccionado
    //setas na vertical seleccionam outro tipo de animal
    if (key == CODED && millis() >lastClick+750) {
      lastClick = millis();
      if (keyCode == UP) {

        if (preysSelected == true ) {

          preysSelected = false;
        } else {        
          preysSelected = true;
        }
        keyCode= -10;
      }  
      if (keyCode == DOWN) {
        if (preysSelected == true) {

          preysSelected = false;
        } else {        
          preysSelected = true;
        }

        keyCode= -10;
      }

      if (keyCode == RIGHT) {


        if (preysSelected == true) {

          animals.add(randomPrey());
        } else {        
          animals.add(new Predator(new PVector(width/2, height/2), "lion", this));
        }

        keyCode= -10;
      }
      if (keyCode == LEFT) {


        if (preysSelected == true) {

          removeLast("prey");
        } else {        
          removeLast("predator");
        }

        keyCode= -10;
      }
    }
    lastUpdateTime = millis();
  }
  
  //metodo que faz o display do terreno, animais e GUI
  void display() { 
    System.out.println(preysSelected);
    lastUpdateTime = millis(); 
    terrain.display(); 
    for (Animal a : animals) {
      a.display();
    }

    textSize(18);
    fill(255, 0, 0);
    if (preysSelected) {
      fill(255, 0, 0);
      text("Nº de Presas: "+ howManyPreys(), 10, 25);
      fill(255, 255, 255);

      text("Nº de Predadores: "+ howManyPredators(), 10, 40);
    } else {
      fill(255, 255, 255);

      text("Nº de Presas: "+ howManyPreys(), 10, 25);
      fill(255, 0, 0);

      text("Nº de Predadores: "+ howManyPredators(), 10, 40);
    }
  }

  //metodo que conta o numero de presas
  int howManyPreys() { 
    int nPreys = 0; 
    for (Animal a : animals) { 
      if (a.type != "lion") { 
        nPreys += 1;
      }
    } 
    return nPreys;
  } 

  //metodo que conta o numero de predadores
  int howManyPredators() { 
    int nPredators = 0; 
    for (Animal a : animals) { 
      if (a.type == "lion") { 
        nPredators += 1;
      }
    } 
    return nPredators;
  }

  //metodo que adicionar uma presa aleatoria
  Animal randomPrey() {

    String[]types= {"rabbit", "deer"};
    int type = (int) random(0, 2); 

    return new Prey(new PVector(width/2, height/2), types[type], this);
  }

  //metodo que remove a uma presa ou predador
  boolean removeLast(String type) {

    if (type =="prey") {

      for (Animal a : animals) {
        if (a.type!="lion") {

          animals.remove(a);
          return true;
        }
      }
    } else {
      for (Animal a : animals) {
        if (a.type=="lion") {

          animals.remove(a);
          return true;
        }
      }
    }
          return false;
  }
}