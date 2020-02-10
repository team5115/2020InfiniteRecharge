package frc.team5115.Auto;

public class Loc2D {
    double x;
    double y;
    // x and y are field relative. X is the location of the robot from the left wall when looking out from the driver station.
    //Y is the location of the robot looking from
    public Loc2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public Loc2D clone() {
        return new Loc2D(getX(), getY());
    }

    public double deltaX(double x) {
        this.x+=x;
        return this.x;
    }

    public double deltaY(double y) {
        this.y+=y;
        return this.y;
    }

    public double angleFrom(Loc2D other) {
//        System.out.println("Delta x" + (other.getX() - this.getX()));
//        System.out.println("Delta y = " + (other.getY() - this.getY()));
        return Math.toDegrees(Math.atan2(other.getY() - this.getY(), other.getX() - this.getX()));
    }

    public double distanceFrom(Loc2D other) {
        return Math.sqrt(
                Math.pow(other.getY() - this.getY(), 2) +
                Math.pow(other.getX() - this.getX(), 2)
        );
    }

    public String toString() {
        return "X:" + getX() + " Y:" + getY();
    }

}
