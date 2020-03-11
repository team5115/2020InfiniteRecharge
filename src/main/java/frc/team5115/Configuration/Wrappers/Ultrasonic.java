package frc.team5115.Configuration.Wrappers;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {
    private AnalogInput ultrasonic;

    public Ultrasonic(int channel){
        ultrasonic = new AnalogInput(channel);
    }

    public double getUltrasonicDistanceInches(){
        double voltage = ultrasonic.getVoltage();
        return (1/0.0121022)*voltage + 0.0138023;
    }

    public double getUltrasonicDistanceFeet(){
        return getUltrasonicDistanceInches()/12;
    }

    public void printValues(){
        System.out.println("ultrasonic = " + this.getUltrasonicDistanceInches());
    }
}
