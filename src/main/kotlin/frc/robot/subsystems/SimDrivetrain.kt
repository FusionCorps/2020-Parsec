package frc.robot.subsystems

import edu.wpi.first.wpilibj.AnalogGyro
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim
import edu.wpi.first.wpilibj.simulation.EncoderSim
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.system.plant.DCMotor
import edu.wpi.first.wpilibj.util.Units
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpiutil.math.VecBuilder
import frc.robot.Controls.controller


class SimDrivetrain: SubsystemBase() {

    private val m_field = Field2d()


    // These represent our regular encoder objects, which we would
// create to use on a real robot.
    private val m_leftEncoder: Encoder = Encoder(0, 1)
    private val m_rightEncoder: Encoder = Encoder(2, 3)

    // These are our EncoderSim objects, which we will only use in
// simulation. However, you do not need to comment out these
// declarations when you are deploying code to the roboRIO.
    private val m_leftEncoderSim = EncoderSim(m_leftEncoder)
    private val m_rightEncoderSim = EncoderSim(m_rightEncoder)

    // Create our gyro object like we would on a real robot.
    private val m_gyro = AnalogGyro(1)

    // Create the simulated gyro object, used for setting the gyro
// angle. Like EncoderSim, this does not need to be commented out
// when deploying code to the roboRIO.
    private val m_gyroSim = AnalogGyroSim(m_gyro)

    // Create the simulation model of our drivetrain.
    var m_driveSim = DifferentialDrivetrainSim(
            DCMotor.getNEO(2),  // 2 NEO motors on each side of the drivetrain.
            7.29,  // 7.29:1 gearing reduction.
            7.5,  // MOI of 7.5 kg m^2 (from CAD model).
            60.0,  // The mass of the robot is 60 kg.
            Units.inchesToMeters(3.0),  // The robot uses 3" radius wheels.
            0.7112,  // The track width is 0.7112 meters.
// The standard deviations for measurement noise:
// x and y:          0.001 m
// heading:          0.001 rad
// l and r velocity: 0.1   m/s
// l and r position: 0.005 m
            VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005))

    init {
        SmartDashboard.putData("Field", m_field)
    }

    override fun simulationPeriodic() { // Set the inputs to the system. Note that we need to convert
// the [-1, 1] PWM signal to voltage by multiplying it by the
// robot controller voltage.

        SmartDashboard.putData("Field", m_field)

        m_driveSim.setInputs(12.0*controller.getRawAxis(1) - 6.0* controller.getRawAxis(0),
                12.0*controller.getRawAxis(1) - 6.0* controller.getRawAxis(0))
        // Advance the model by 20 ms. Note that if you are running this
// subsystem in a separate thread or have changed the nominal timestep
// of TimedRobot, this value needs to match it.
        m_driveSim.update(0.02)
        // Update all of our sensors.
        m_leftEncoderSim.distance = m_driveSim.leftPositionMeters
        m_leftEncoderSim.rate = m_driveSim.leftVelocityMetersPerSecond
        m_rightEncoderSim.distance = m_driveSim.rightPositionMeters
        m_rightEncoderSim.rate = m_driveSim.rightVelocityMetersPerSecond
        m_gyroSim.angle = -m_driveSim.heading.degrees
    }

}