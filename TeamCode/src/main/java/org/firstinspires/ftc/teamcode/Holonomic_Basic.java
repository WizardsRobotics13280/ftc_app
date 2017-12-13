package org.firstinspires.ftc.teamcode;
// Put this in part of teamcode folder.

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
// vcs test

@TeleOp(name = "OMNI_Wheel_Basic2", group = "linear Opmode")                     // Register your Opmode. On the RC the Opmode will show up as the name string.
//@Disabled
/* Extend the LinearOpMode.*/
public class Holonomic_Basic extends LinearOpMode {

    /* Declare OpMode members. */
    DcMotor F1, F2, R1, R2, arm_vert;														// Declares the DcMotor variables F1, F2, R1, R2.
    double heading, heading_degrees, power, leftstick_xsq, leftstick_ysq;       // Declares the double variables for telemetry.
    double powerF1, powerF2, powerR1, powerR2, arm_vertPower;
    double magnitude = 0.80;
    // Declares the double variables for calculating the power levels for each motor.

    //@Override																	// This should be commented out when we want Android Studio to run this OpMode.
    public void runOpMode() 													/* This begins the initialization process fro the robot (after driver presses "INIT" on DS */
    {

        F1 = hardwareMap.get(DcMotor.class, "f1");	// Initialize the motor variable for F1.  Named f1 in the RC phone.
        F2 = hardwareMap.get(DcMotor.class, "f2");								// Initialize the motor variable for F2.  Named f2 in the RC phone.
        R1 = hardwareMap.get(DcMotor.class, "r1");								// Initialize the motor variable for R1.  Named r1 in the RC phone.
        R2 = hardwareMap.get(DcMotor.class, "r2");
        arm_vert = hardwareMap.get(DcMotor.class, "arm_vert");

        F1.setDirection(DcMotorSimple.Direction.FORWARD);
        F2.setDirection(DcMotorSimple.Direction.FORWARD);
        R2.setDirection(DcMotorSimple.Direction.FORWARD);
        R1.setDirection(DcMotorSimple.Direction.FORWARD);
        arm_vert.setDirection(DcMotorSimple.Direction.FORWARD);
        // Initialize the motor variable for R2.  Named r2 in the RC phone.

        waitForStart();															// Wait for the game to start (driver presses PLAY).

        while (opModeIsActive()) 												/* Run until the end of the match (driver presses STOP). */
        {
            /* Calculate the telemetry values for heading (Left stick determines heading). */
            heading = -1 * (Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x));  // Calculates the angle in radians measured counterclockwise from the positive x-axis and the heading (x, y)
            heading_degrees = heading * (180 / Math.PI);                        // Calculates the heading in degrees ??? not used again.
            leftstick_xsq = gamepad1.left_stick_x * gamepad1.left_stick_x;      // ??? this is not used again
            leftstick_ysq = gamepad1.left_stick_y * gamepad1.left_stick_y;              // ??? this is not used again

			/* Report telemetry data on RC phone ??? Should this be below the power settings but inside the if statement? */
            telemetry.clear();
			telemetry.addData("left_stick_x", gamepad1.left_stick_x);
            telemetry.addData("left_stick_y", gamepad1.left_stick_y);
            telemetry.addData("right_stick_x", gamepad1.right_stick_x);
            telemetry.addData("right_stick_y", gamepad1.right_stick_y);
            telemetry.addData("upper_right_encoder_data", F1.getCurrentPosition());
            telemetry.addData("upper_left_encoder_data", F2.getCurrentPosition());
            telemetry.addData("lower_right_encoder_data", R1.getCurrentPosition());
            telemetry.addData("lower_left_encoder_data", R2.getCurrentPosition());
            telemetry.addData("heading", heading);
            telemetry.addData("pwr", power);
            telemetry.update();
            power = Math.sqrt(leftstick_xsq + leftstick_ysq);

            /* Find and set power (Right stick determines power). */
            /* .5 is the speed, .5 = medium speed */
            if (Math.abs(gamepad1.left_stick_x) > .1 || Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.right_stick_x) > .1) {
                powerF1 = ((Math.cos((Math.PI/4) - heading)) - gamepad1.right_stick_x)*magnitude;
                powerF2 = ((Math.cos((Math.PI/4) + heading)) - gamepad1.right_stick_x)*magnitude;
                powerR1 = ((-Math.cos((Math.PI/4) + heading)) - gamepad1.right_stick_x)*magnitude;
                powerR2 = ((-Math.cos((Math.PI/4) - heading)) - gamepad1.right_stick_x)*magnitude; // this works


            }
            else{
                powerF1 = 0;
                powerF2 = 0;
                powerR1 = 0;
                powerR2 = 0;

            }
            // Right bumper for overdrive
            if (gamepad1.right_bumper == true){
                magnitude = 1.0;
            }else{
                magnitude = 0.8;
            }
            // left bumper for extra slow?
            if (gamepad1.left_bumper){
                magnitude = 0.6;
            }else{
                magnitude = 0.8;
            }
            // Is the arm going to be continuous servo or dc motor (or even 180 servo)? DC motor for now but probably changed for later
            //Right stick for arm vertical? I'll probably change it
            if (Math.abs(gamepad1.right_stick_y) > 0.1){
                arm_vertPower = gamepad1.right_stick_y;
            }else{
                arm_vertPower = 0;
            }
//            powerF1 = Range.clip(powerF1, -1, 1);
//            powerF2 = Range.clip(powerF2, -1, 1);
//            powerR1 = Range.clip(powerR1, -1, 1);
//            powerR2 = Range.clip(powerR2, -1, 1);

            // test the motors directly and isolate variable

//            powerF1 = gamepad1.left_stick_y;
//            powerF2 = gamepad1.left_stick_y;
//            powerR1 = -gamepad1.left_stick_y;
//            powerR2 = gamepad1.left_stick_y;

			/* Make the motors move based on values calculated above. */
            F1.setPower(powerF1);
            F2.setPower(powerF2);
            R1.setPower(powerR1);
            R2.setPower(powerR2);


        }


    }
}