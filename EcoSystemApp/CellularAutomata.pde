//classe do automato celular
class CellularAutomata { 
  int nrows, ncols; 
  int nStates; 
  Cell[][] cells; 
  ArrayList<PImage> images; 
  String[] IMAGE_FILES; 
  color EMPTY_COLOR = color(255, 255, 255); 

  CellularAutomata(int nrows_, int ncols_) {

    nrows = nrows_; 
    ncols = ncols_; 
    cells =new Cell[nrows][ncols]; 
    IMAGE_FILES = new String[5]; 
    IMAGE_FILES[0]= "/IMG/grow"; 
    IMAGE_FILES[1] = "/IMG/free"; 
    IMAGE_FILES[2]= "/IMG/grass"; 
    IMAGE_FILES[3] = "/IMG/fruits"; 
    IMAGE_FILES[4] ="/IMG/trap"; 
    images = setImages(IMAGE_FILES); 
    nStates = IMAGE_FILES.length; 
    createCells(); 
    setNeighbors(); 
    setRandomStates(); 
    majorityRule(); 
    setInitialState();
  }

  //criar celulas
  void createCells() { 
    for (int i = 0; i <nrows; i++) {
      for (int j = 0; j <ncols; j++) {
        cells[i][j] =new Cell(i, j, width/ncols, height/nrows, this);
      }
    }
  } 
  
  //identificar vizinhos
  void setNeighbors() { 

    for (int i = 0; i < nrows; i++) { 
      for (int j = 0; j < ncols; j++) { 
        for (int ii=-1; ii<=1; ii++) { 
          for (int jj=-1; jj<=1; jj++) { 
            if ((ii!=0) || (jj!=0)) { 
              int row = (i + ii) % nrows; 
              if (row < 0) row += nrows; 
              int col =(j + jj) % ncols; 
              if (col < 0) col += ncols; 
              cells[i][j].setNeighbor(cells[row][col]);
            }
          }
        }
      }
    }
  }
  
  //adicionar imagem respectiva a cada celula
  ArrayList<PImage> setImages(String[] IMAGE_FILES) { 
    ArrayList<PImage> images = new ArrayList<PImage>(); 
    PImage img = createImage(width/ncols, height/nrows, RGB); 
    for (int i=0; i < IMAGE_FILES.length; i++) { 
      img = loadImage(IMAGE_FILES[i] +".png"); 
      img. resize(width/ncols, height/nrows); 
      images.add(img);
    } 
    return images;
  } 
  
  //estados aleatorios
  void setRandomStates() { 
    for (int i = 0; i < nrows; i++) { 
      for (int j = 0; j < ncols; j++) { 
        cells[i][j].state= (int) random(1, nStates-1); 
        cells[i][j].initialState =cells[i][j].state;
      }
    }
  } 
  boolean majorityRule()
  {
    for (int i = 0; i < nrows; i++) {
      for (int j = 0; j < ncols; j++) {
        cells[i][j].setHistogram();
      }
    }
    boolean anyChanged = false;    
    for (int i = 0; i < nrows; i++) {
      for (int j = 0; j < ncols; j++) {
        boolean changed = cells[i][j].applyMajorityRule();
        if (changed) anyChanged = true;
      }
    }
    return anyChanged;
  }


  //calcular index da celula no array
  Cell getCell(int x, int y)
  {
    int w = width/ncols;
    
    int h = height/nrows;
    int row = y/h;
    int col = x/w;
    if (row >= nrows) row = nrows - 1;
    if (col >= ncols) col = ncols - 1;
    
    return cells[row][col];
  }
   
   //inicializar estados iniciais
  void setInitialState() {
    for (int i = 0; i < nrows; i++) { 
      for (int j = 0; j < ncols; j++) {
        cells[i][j].initialState = cells[i][j].state; 
        cells[i][j].calculateRegenerationTime();
      }
    }
  } 
  
  //regenerar celulas
  void regenerate(float dt) { 
    for (int i = 0; i < nrows; i++) {
      for (int j = 0; j < ncols; j++) { 
        if (cells[i][j].state ==0) {
          if (cells[i][j].timeToGrow > 0) {
            cells[i][j].timeToGrow -= dt;
          }
          if (cells[i][j].timeToGrow <= 0) { 
            cells[i][j].state = cells[i][j].initialState; 
            cells[i][j].calculateRegenerationTime();
          }
        }
      }
    }
  }
  
  
  //exibir celulas
  void display() {

    for (int i = 0; i < nrows; i++) {
      for (int j = 0; j < ncols; j++) { 

        image(images.get(cells[i][j].state), (width/ncols)*j, (height/nrows)*i);
      }
    }
  }
}