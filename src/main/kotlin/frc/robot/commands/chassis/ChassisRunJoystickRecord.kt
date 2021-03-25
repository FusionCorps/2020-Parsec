package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.fusion.motion.InputRecording
import frc.robot.subsystems.Chassis

class ChassisRunJoystickRecord : CommandBase() {
    private val speedLimiter = SlewRateLimiter(7.5) // Cap accel and sens
    private val rotationLimiter = SlewRateLimiter(3.5)

    private var input1 = 0.0
    private var input2 = 0.0

    init {
        addRequirements(Chassis)
    }

    override fun execute() {

        input1 = -0.7*speedLimiter.calculate(Controls.controller.getRawAxis(4))
        input2 = rotationLimiter.calculate(Controls.controller.getRawAxis(1))

        Chassis.joystickDrive(input1, input2)

        InputRecording.inputs.add(listOf(input1, input2))

    }
}