package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.InputRecording
import frc.robot.subsystems.Chassis
import mu.KotlinLogging
import edu.wpi.first.wpilibj.Timer
import frc.robot.fusion.motion.InputRecording.inputs

class ChassisReplay: CommandBase() {

    var inputCurrent = listOf(0.0, 0.0)
    var index = 0

    val timer = Timer()


    override fun initialize() {
        KotlinLogging.logger("Replay").info{"Replay Started"}
        index = 0
    }

    override fun execute() {



        timer.start()

        while (!timer.hasPeriodPassed(0.030)){
            Chassis.joystickDrive(inputs[index][0], inputs[index][1])
        }

        index += 1

        timer.reset()

    }

    override fun isFinished(): Boolean {
        return (index > InputRecording.inputs.size)
    }



}