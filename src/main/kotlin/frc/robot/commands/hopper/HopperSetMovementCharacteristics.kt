package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Hopper

class HopperSetMovementCharacteristics(hopper: Hopper, value: Double) : InstantCommand() {
    private val mHopper = hopper
    val mValue = value

    init {
        addRequirements(mHopper)
    }

    override fun initialize() {
        mHopper.currentHopperVelocity = mValue
    }
}
