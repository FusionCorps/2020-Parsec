package frc.robot.commands.chassis

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.InputRecording
import frc.robot.subsystems.Chassis
import java.io.File
import java.util.*

class ChassisReplayv2: CommandBase() {

    var scanner = Scanner(File(InputRecording.autoFile))
    var startTime = System.currentTimeMillis()
    var tDelta = 0.0
    var nextDouble = 0.0

    var rotInput = 0.0
    var fwdInput = 0.0

    var onTime = true

    init {
        scanner = Scanner(File(InputRecording.autoFile))
        scanner.useDelimiter("[,\\n]")
    }

    override fun execute() {

        println(scanner.hasNextDouble())

        if ((scanner != null) && (scanner.hasNextDouble())) {
            if (onTime) {
                nextDouble = scanner.nextDouble()
            }

            tDelta = nextDouble - (System.currentTimeMillis() - startTime)

            if (tDelta <= 0.0) {
                Chassis.getLeftMotors().set(TalonFXControlMode.PercentOutput, scanner.nextDouble())
                Chassis.getRightMotors().set(TalonFXControlMode.PercentOutput, scanner.nextDouble())

                onTime = true
            } else {
                onTime = false
            }

        }


    }



}