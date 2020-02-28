package frc.robot.fusion.motion

data class FPIDCharacteristics(val kF: Double = 0.0, val kP: Double = 0.0, val kI: Double = 0.0, val kD: Double = 0.0) {
    fun toDoubleArray(): DoubleArray {
        return doubleArrayOf(kF, kP, kI, kD)
    }
}
