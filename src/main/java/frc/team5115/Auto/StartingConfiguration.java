package frc.team5115.Auto;

public enum StartingConfiguration {
    Right, Middle, Left;
    public static double getX(StartingConfiguration startingConfiguration) {
        //todome update
        switch (startingConfiguration) {
            case Right:
                return 200;
            case Middle:
                return 100;
            case Left:
                return 50;
        }
        return 0;
    }
}
