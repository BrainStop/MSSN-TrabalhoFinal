class Cell { 

  int row, col; 
  int w, h; 
  CellularAutomata ca; 
  int state, initialState; 
  ArrayList<Cell> neighbors; 
  Histogram hist; 
  float timeToGrow; 

  Cell(int row_, int col_, int w_, int 
    h_, CellularAutomata ca_) { 
    row = row_; 
    col = col_; 
    w = w_; 
    h = h_; 
    state = 0;
    initialState = 0; 
    ca = ca_; 
    timeToGrow = 0; 
    neighbors = new ArrayList<Cell>();
  } 
  
  //calcular e alterar tempo de regenaraçao oda celula conforme o seu estado
  void calculateRegenerationTime() { 
    if (this.initialState == 2) { 
      timeToGrow = 20.;
    } 
    if (this.initialState == 3) { 
      timeToGrow = 30.;
    } 
    if (this.initialState == 4) { 
      timeToGrow = 5.;
    }
  }

  //adicionar vizinhos
  void setNeighbor (Cell c) {

    neighbors.add(c);
  }
  
  //criar histograma
  void setHistogram() {
    int[] data = new int[9]; 
    for (int i=0; i<8; i++) {

      data[i] = neighbors.get(i).state;
    }
    data[8] = state; 
    hist = new Histogram(data, 9);
  } 
  
  //aplicar regra da maioridade
  boolean applyMajorityRule() {
    int mode = hist.getMode(); 
    boolean changed = false; 
    if (state != mode) { 
      state = mode; 
      changed = true;
    } 
    return changed;
  } 
  
  //calcular centro da celula
  PVector getCenter() {

    int x = (int)((col + 0.5) * w); 
    int y = (int)((row + 0.5) * h); 
    return new PVector(x, y);
  } 

//detectar presas na celula
  ArrayList <Animal> whichPreysAreOn(ArrayList<Animal> animals)
  {
    ArrayList<Animal> preysOn = new ArrayList<Animal>();
    for (Animal a : animals) {
      if ((a.type == "deer" || a.type == "rabbit") && ca.getCell((int)a.loc.x,(int)a.loc.y) == this) {
        preysOn.add(a);
      }
    }
    return preysOn;
  }
  
  //fazer display da celula conforme o seu estado
  void display() { 
    image(ca.images.get(state), col*w, row*h);
  }
} 