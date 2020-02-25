// package frc.robot.fusion.motion
//
// import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
// import frc.robot.Constants
//
// class FusionTalonFX(deviceNumber: Int) : WPI_TalonFX(deviceNumber) {
//    private var frameInfo = mutableMapOf<StatusFrameEnhanced, FrameInfo>()
//    private var timeSinceLastUpdate: Int = 0
//
//    var position: Int
//        set(value) {
//            this.selectedSensorPosition = value
//        }
//        get() {
//            return this.selectedSensorPosition
//        }
//    var kF: Double = 0.0
//        set(value) {
//            this.config_kF(value)
//            field = kF
//        }
//    get() {
//        val frame = frameInfo[StatusFrameEnhanced.Status_13_Base_PIDF0]!!
//
//        if (frame.timeSinceLastUpdate > frame.period) {
//            frameInfo
//        }
//    }
//
//    fun addStatusFrame(frame: StatusFrameEnhanced, period: Int = Constants.SCHEDULER_RATE / 2) {
//        frameInfo[frame] = FrameInfo(period)
//
//        this.setStatusFramePeriod(frame, period)
//    }
// }
//
// data class FrameInfo(val period: Int, var timeSinceLastUpdate: Int = 0)
