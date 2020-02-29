package frc.robot.commands.lift

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Lift

class LiftExtend : CommandBase() { // Run the extension motor at 10%
    init {
        addRequirements(Lift)
    }

    override fun initialize() {
        Lift.setExtend(TalonSRXControlMode.PercentOutput, 0.4)
    }

    override fun end(interrupted: Boolean) {
        Lift.extendStop()
    }
}
