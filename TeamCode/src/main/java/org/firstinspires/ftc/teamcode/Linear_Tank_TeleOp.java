package org.firstinspires.ftc.teamcode;
/**Basic code from FtcRobotController/java/org.firstinspires.ftc.robotcontroller/external.samples/BasicOpMode_Linear.java
 * To be modified
 * */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Linear Tank TeleOp", group="Linear Opmode")
@Disabled
public class Linear_Tank_TeleOp extends LinearOpMode {

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialize the hardware variables. Note that the strings here are parameters
        //to 'get' must correspond to the names assigned during the robot configuration
        //step (using the FTC Robot controller app on the phone)
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        //Most robots need the motor on one side to be reversed
        //Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        //Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        //run until the end of the match (driver presses STOP)
        while (opModeIsActive()){

            //Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

            //Choose to drive using either Tank Mode, or POV Mode
            //Comment out the method that's not being used. The default below is POV

            //POV Mode uses the left stick to go forward, and right stick to turn.
            //Uses basic math to combine motions.
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            // Show the elapsed game time and wheel power
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
