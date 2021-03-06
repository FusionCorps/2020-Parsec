package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Hopper

// Run hopper at fixed speed

class HopperRunAt(controlMode: ControlMode = ControlMode.DutyCycle, value: Double) : CommandBase() {
    val mControlMode = controlMode
    val mValue = value

    // ControlMode is a type (like int) that lets us define how the motors take inputs
    // e.g. Duty Cycle, Voltage, etc.

    init {
        addRequirements(Hopper)
    }

    override fun initialize() {
        Hopper.control(mControlMode, DutyCycleConfig(mValue))
    }

    override fun end(interrupted: Boolean) {
        Hopper.control(ControlMode.Disabled)
    }
}
