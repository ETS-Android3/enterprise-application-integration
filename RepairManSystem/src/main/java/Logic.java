public class Logic {
    public static String getRegion(ScooterError se){
        double xRange = 0.121616;
        double yRange = 0.054169;
        double xMin = 6.510863;
        double yMin = 53.198403;

        if(se.getyLoc() < yMin + yRange / 2){
            return "sg";//south groningen
        } else {
            return "ng"; //north groningen
        }

    }
}
