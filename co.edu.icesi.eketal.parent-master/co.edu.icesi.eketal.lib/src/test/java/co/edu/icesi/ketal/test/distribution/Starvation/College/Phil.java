//taken from http://wotug.ukc.ac.uk/parallel/groups/wotug/java/discussion/3.html
package co.edu.icesi.ketal.test.distribution.Starvation.College;

public class Phil extends Thread {
  //{{{  
  
  //{{{  COMMENT documentation
  //
  //A philosopher thinks for a while -- around 3 seconds -- and then goes to the
  //canteen for food, consuming what he gets straight away.   This cycle continues
  //indefinitely.
  //
  //Except, that is, for philosopher 0 ...  who refuses to think and just keeps
  //going to the canteen.  He will get his come-uppance shortly.
  //
  //}}}
  
  private int id;
  private Canteen canteen;
  
  //{{{  constructor
  public Phil (int id, Canteen canteen) {
    this.id = id;
    this.canteen = canteen;
    start ();
  }
  //}}}
  
  //{{{  run
  public void run () {
    //{{{  
    int chicken;
    
    //{{{  starting
    System.out.println ("(" + System.currentTimeMillis() + ")" +
                        " Phil " + id + ": starting ... ");
    //}}}
    while (true) {
      //{{{  everyone, bar philosopher 0, has a little think
      if (id > 0) {
        //{{{  think for around 3 seconds
        try {sleep (3000);} catch (InterruptedException e) {}
        //}}}
      }
      //}}}
      //{{{  want chicken
      System.out.println ("(" + System.currentTimeMillis() + ")" +
                          " Phil " + id + ": gotta eat ... ");
      //}}}
      chicken = canteen.get (id);
      //{{{  consume chicken
      System.out.println ("(" + System.currentTimeMillis() + ")" +
                          " Phil " + id + ": mmm ... that's good ... ");
      //}}}
    }
    //}}}
  }
  //}}}
  
  //}}}
}