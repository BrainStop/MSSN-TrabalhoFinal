//histograma da aplicaçao
class Histogram
{
  
  int[] hist;
  int nbins;
  
  Histogram(int[] data, int nbins_)
  {
    nbins = nbins_;
    hist = new int[nbins];
    for (int i=0; i<data.length;i++)
      hist[data[i]]++;
  }
  
  int getMode()
  {
    int maxVal = 0;
    int mode = 0;
    for (int i=0;i<nbins;i++) {
      if (hist[i] > maxVal) {
        maxVal = hist[i];
        mode = i;
      }
    }
    return mode;
  }
  
  void display()
  {
    for (int i=0;i<nbins;i++) println(hist[i]);
    println();
  }
}