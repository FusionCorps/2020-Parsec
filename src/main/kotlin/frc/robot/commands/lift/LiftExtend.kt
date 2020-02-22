package frc.robot.commands.lift

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.button.Button
import frc.robot.subsystems.Lift

class LiftExtend(lift: Lift): CommandBase() { // Run the extension motor at 70%
    private val mLift = lift


    init {
        addRequirements(mLift)
    }

    override fun initialize() {
        mLift.setExtend(TalonSRXControlMode.PercentOutput, 0.7)
    }

    override fun execute() {}

    override fun end(interrupted: Boolean) {
        mLift.extendOff()
    }





}