package org.firstinspires.ftc.teamcode;

/**
 * So, the way I'm thinking we write autonomous code would probably be as follows:
 * If you're facing the field so that on the left are the two red stones:
 * red1 will be the red stone closest to you
 * red2 will be the farther red stone
 * blue1 will be the blue stone closest to you
 * blue2 will be te farther blue stone
 * */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="red1_auto", group="auto")
//@Disabled

public class red1_autonomous extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor F1, F2, R1, R2; //Declare the 4 motors that should have already configured based on the Holonomic_Basic code.
    DcMotor colorsensor_arm;
    //double heading, heading_degrees, power; //Declare heading, heading in degrees, and power, all useful for driving.
    //private void Drive_Forward_Rotations(double rotations, double fheading){} //Use encoders on the motors to measure a number of rotations.
    @Override
    public void runOpMode() {
        //Sets all the motor directions to forward
        F1 = hardwareMap.get(DcMotor.class, "f1");
        F2 = hardwareMap.get(DcMotor.class, "f2");
        R1 = hardwareMap.get(DcMotor.class, "r1");
        R2 = hardwareMap.get(DcMotor.class, "r2");
        F1.setDirection(DcMotorSimple.Direction.FORWARD);
        F2.setDirection(DcMotorSimple.Direction.FORWARD);
        R1.setDirection(DcMotorSimple.Direction.FORWARD);
        R2.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart(); //Waits for the driver to press play
        Drive_Direction_Time_Improved(950, 0, 1, 0);
    }


    private void Drive_Direction_Time (double milliseconds, double fheading) {
        double start_time = runtime.milliseconds();
        double powerF1, powerF2, powerR1, powerR2;
        while (runtime.milliseconds() <= start_time + milliseconds) {
            powerF1 = (Math.cos((Math.PI / 4) - fheading));
            powerF2 = (Math.cos((Math.PI / 4) - fheading));
            powerR1 = (-Math.cos((Math.PI / 4) - fheading));
            powerR2 = (-Math.cos((Math.PI / 4) - fheading));
            F1.setPower(powerF1);
            F2.setPower(powerF2);
            R1.setPower(powerR1);
            R2.setPower(powerR2);
        }
    }

    private void Drive_Direction_Time_Improved (double milliseconds, double dest_x, double dest_y, double rotation) {
        double start_time = runtime.milliseconds();
        double powerF1, powerF2, powerR1, powerR2;
        double heading;

        if (dest_x == 0 && dest_y == 0) {
            heading = 0;
        } else {
            heading = Math.atan2(dest_y, dest_x);
        }

        while (runtime.milliseconds() <= start_time + milliseconds) {
            powerF1 = (Math.cos((Math.PI / 4) - heading) - rotation);
            powerF2 = (Math.cos((Math.PI / 4) + heading) - rotation);
            powerR1 = (-Math.cos((Math.PI / 4) + heading) - rotation);
            powerR2 = (-Math.cos((Math.PI / 4) - heading) - rotation);
            F1.setPower(powerF1);
            F2.setPower(powerF2);
            R1.setPower(powerR1);
            R2.setPower(powerR2);
        }
    }
}

/*
* if (colorSensor.red() > 20) {
*   redDrive.setPower(.7)
* }
* just do the same for the other colors
* }*/