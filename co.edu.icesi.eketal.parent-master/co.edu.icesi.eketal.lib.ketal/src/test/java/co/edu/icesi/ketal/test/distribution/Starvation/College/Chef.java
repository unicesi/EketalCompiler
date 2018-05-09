//taken from http://wotug.ukc.ac.uk/parallel/groups/wotug/java/discussion/3.html
package co.edu.icesi.ketal.test.distribution.Starvation.College;

public class Chef extends Thread {
  //{{{  
  
  //{{{  COMMENT documentation
  //
  //A chef is an active object.  He cooks chickens in batches of four -- taking
  //around 2 seconds per batch -- and then takes then to the canteen.
  //
  //This cycle continues indefinitely.
  //
  //}}}
  
  private Canteen canteen;
  
  //{{{  constructor
  public Chef (Canteen canteen) {
    this.canteen = canteen;
    start ();
  }
  //}}}
  
  //{{{  run
  public void run () {
    //{{{  
    int n_chickens;
    
    //{{{  starting
    System.out.println ("(" + System.currentTimeMillis() + ")" +
                        " Chef  : starting ... ");
    //}}}
    while (true) {
      //{{{  cook 4 chickens
      System.out.println ("(" + System.currentTimeMillis() + ")" +
                          " Chef  : cooking ... ");
      //{{{  cook for around 2 seconds
      try {sleep (2000);} catch (InterruptedException e) {}
      //}}}
      n_chickens = 4;
      System.out.println ("(" + System.currentTimeMillis() + ")" +
                          " Chef  : " + n_chickens + " chickens, ready-to-go ... ");
      //}}}
      canteen.put (n_chickens);
    }
    //}}}
  }
  //}}}
  
  //}}}
}
