//taken from http://wotug.ukc.ac.uk/parallel/groups/wotug/java/discussion/3.html
package co.edu.icesi.ketal.test.distribution.Starvation.College;

public class College {
  //{{{  
  
  //{{{  COMMENT documentation
  //
  //The College consists of 5 Philosophers, a Chef and the Canteen.  The Chef and
  //the Philosophers are "active" objects.  The Canteen is a "passive" object
  //through which the Philosophers and the Chef have to interact.
  //
  //The Canteen is implemented in the style of the CubbyHole example from Sun's
  //Java Tutorial.  As such, it exposes each Philosopher to the danger of
  //starvation (by infinite overtaking by fellow Philosophers).  Sadly, this
  //does happen with the particular timing circumstances set up in this
  //demonstration.  Happily, it's the greedy Philosopher that starves -- he
  //never even gets one meal, despite being in the Canteen the whole time!
  //
  //}}}
  
  //{{{  main
  public static void main (String argv[]) {
    //{{{  
    
    int n_philosophers = 5;
    
    Canteen canteen = new Canteen ();
    
    Chef chef = new Chef (canteen);
    
    Phil[] phil = new Phil[n_philosophers];
    
    for (int i = 0; i < n_philosophers; i++) {
      phil[i] = new Phil (i, canteen);
    }
    
    //}}}
  }
  //}}}
  
  //}}}
}