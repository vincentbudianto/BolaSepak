package com.bolasepak.util

import com.bolasepak.listener.StepListener

class StepDetector {
    private val ACCEL_RING_SIZE = 50
    private val VEL_RING_SIZE = 10
    private val STEP_THRESHOLD = 50f
    private val STEP_DELAY_NS = 250000000
    private var accelRingCounter = 0
    private val accelX = FloatArray(ACCEL_RING_SIZE)
    private val accelY = FloatArray(ACCEL_RING_SIZE)
    private val accelZ = FloatArray(ACCEL_RING_SIZE)
    private var velCounter = 0
    private val vel = FloatArray(VEL_RING_SIZE)
    private var lastStepTimeNs: Long = 0
    private var oldVelocityEstimate = 0f
    private var listener: StepListener? = null

    fun registerListener(listener: StepListener) {
        this.listener = listener
    }

    fun updateAccelerometer(timeNs: Long, x: Float, y: Float, z: Float) {
        val currentAccel = FloatArray(3)
        currentAccel[0] = x
        currentAccel[1] = y
        currentAccel[2] = z

        accelRingCounter++
        accelX[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[0]
        accelY[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[1]
        accelZ[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[2]

        val worldZ = FloatArray(3)
        worldZ[0] = SensorFilter().sum(accelX) / Math.min(accelRingCounter, ACCEL_RING_SIZE)
        worldZ[1] = SensorFilter().sum(accelY) / Math.min(accelRingCounter, ACCEL_RING_SIZE)
        worldZ[2] = SensorFilter().sum(accelZ) / Math.min(accelRingCounter, ACCEL_RING_SIZE)

        val normalization_factor = SensorFilter().norm(worldZ)

        worldZ[0] = worldZ[0] / normalization_factor
        worldZ[1] = worldZ[1] / normalization_factor
        worldZ[2] = worldZ[2] / normalization_factor

        val currentZ = SensorFilter().dot(worldZ, currentAccel) - normalization_factor
        velCounter++
        vel[velCounter % VEL_RING_SIZE] = currentZ

        val velocityEstimate = SensorFilter().sum(vel)

        if (velocityEstimate > STEP_THRESHOLD && oldVelocityEstimate <= STEP_THRESHOLD
            && timeNs - lastStepTimeNs > STEP_DELAY_NS) {
            listener!!.step(timeNs)
            lastStepTimeNs = timeNs
        }
        oldVelocityEstimate = velocityEstimate
    }
}