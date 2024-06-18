package com.yervand.feature.spots.info.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.yervand.core.entities.Spot
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sqrt


class SpotsGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var spots: List<Spot> = emptyList()

    private var circlePaint: Paint = Paint()
    private var axesPaint: Paint = Paint()
    private var linePaint: Paint = Paint()
    private var thresholdLinePaint: Paint = Paint()
    private var textPaint: Paint = Paint()
    private var wholeCanvasPaint: Paint = Paint()

    private val wholeCanvasRect: Rect
        get() = Rect(0, 0, 0 + width, 0 + height)

    private val circleRadiusPx = CIRCLE_RADIUS_DP.toPx()
    private val paddingPx = PADDING_DP.toPx()

    private var maxY = 0f
    private var minY = 0f
    private var maxX = 0f
    private var minX = 0f

    private val canvasStart: Float
        get() = paddingPx

    private val canvasEnd: Float
        get() = width - paddingPx

    private val canvasTop: Float
        get() = paddingPx

    private val canvasBottom: Float
        get() = height - paddingPx

    private val usableWidth: Float
        get() = canvasEnd - canvasStart

    private val usableHeight: Float
        get() = canvasBottom - canvasTop

    private val unitSizePx: Float
        get() = min(usableWidth, usableHeight) /
                max(minX.absoluteValue + maxX.absoluteValue, minY.absoluteValue + maxY.absoluteValue)

    private val horizontalCenter: Float
        get() = if (minX * maxX <= 0) { // if they have different signs
            canvasStart + minX.absoluteValue * unitSizePx
        } else {
            if (maxX < 0 || minX < 0) {
                canvasEnd
            } else {
                canvasStart
            }
        }

    private val verticalCenter: Float
        get() = if (minY * maxY <= 0) { // if they have different signs
            canvasBottom - minY.absoluteValue * unitSizePx
        } else {
            if (maxY < 0 || minY < 0) {
                canvasTop
            } else {
                canvasBottom
            }
        }

    init {
        wholeCanvasPaint.color = Color.RED
        wholeCanvasPaint.style = Paint.Style.FILL

        circlePaint.color = Color.WHITE
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = LINE_WIDTH_DP.toPx()

        linePaint.color = Color.WHITE
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = LINE_WIDTH_DP.toPx()

        axesPaint.color = Color.WHITE
        axesPaint.style = Paint.Style.STROKE
        axesPaint.strokeWidth = AXES_WIDTH_DP.toPx()

        thresholdLinePaint.color = Color.WHITE
        thresholdLinePaint.style = Paint.Style.STROKE
        thresholdLinePaint.strokeWidth = THRESHOLD_LINE_WIDTH_DP.toPx()

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = TEXT_SIZE.toPx()
        textPaint.textAlign = Paint.Align.CENTER
    }

    fun setSpots(spots: List<Spot>) {
        maxY = spots.maxOf { it.y }
        minY = spots.minOf { it.y }
        maxX = spots.maxOf { it.x }
        minX = spots.minOf { it.x }
        this.spots = spots.sortedBy { it.x }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(
            wholeCanvasRect,
            wholeCanvasPaint
        )

        canvas.apply {
            drawAxes()
            drawAndConnectSpots()
            drawThresholds()
        }
    }

    private fun Canvas.drawAxes() {
        drawLine(
            canvasStart,
            verticalCenter,
            canvasEnd,
            verticalCenter,
            axesPaint
        )
        drawLine(
            horizontalCenter,
            canvasTop,
            horizontalCenter,
            canvasBottom,
            axesPaint
        )
    }

    private fun Canvas.drawAndConnectSpots() {
        val spotCoordinates = mutableListOf<Spot>() // actual positions of all spots on the graph
        spots.forEach { spot ->
            val x = horizontalCenter + spot.x * unitSizePx
            val y = verticalCenter - spot.y * unitSizePx

            drawCircle(x, y, circleRadiusPx, circlePaint)
            spotCoordinates.add(Spot(x, y))
        }
        for (i in 0..spotCoordinates.size - 2) {
            drawLine(
                start = spotCoordinates[i] + ((spotCoordinates[i + 1] - spotCoordinates[i]).normalized() * circleRadiusPx),
                end = spotCoordinates[i + 1] + ((spotCoordinates[i] - spotCoordinates[i + 1]).normalized() * circleRadiusPx),
            )
        }
    }

    private fun Canvas.drawThresholds() {
        val thresholdFrequencyPx = THRESHOLD_FREQUENCY * unitSizePx

        val thresholdNumberSkipCount = (TEXT_SIZE.toPx() / thresholdFrequencyPx).roundToInt()

        // drawing vertical thresholds
        var lastThresholdPosition = horizontalCenter + thresholdFrequencyPx
        var thresholdNumber = THRESHOLD_FREQUENCY
        var lastThresholdIndex = 0
        while (lastThresholdPosition < canvasEnd) {
            drawLine(
                lastThresholdPosition,
                canvasTop,
                lastThresholdPosition,
                canvasBottom,
                thresholdLinePaint
            )
            if(thresholdNumberSkipCount == 0 || lastThresholdIndex % (thresholdNumberSkipCount+1) == 0) {
                drawText(
                    thresholdNumber.toString(),
                    lastThresholdPosition,
                    canvasBottom + TEXT_PADDING_MULTIPLIER * TEXT_SIZE.toPx(),
                    textPaint
                )
            }
            lastThresholdIndex++
            thresholdNumber += THRESHOLD_FREQUENCY
            lastThresholdPosition += thresholdFrequencyPx
        }

        lastThresholdPosition = horizontalCenter - thresholdFrequencyPx
        thresholdNumber = -THRESHOLD_FREQUENCY
        lastThresholdIndex = 0
        while (lastThresholdPosition > canvasStart) {
            drawLine(
                lastThresholdPosition,
                canvasTop,
                lastThresholdPosition,
                canvasBottom,
                thresholdLinePaint
            )
            if(thresholdNumberSkipCount == 0 || lastThresholdIndex % (thresholdNumberSkipCount+1) == 0) {
                drawText(
                    thresholdNumber.toString(),
                    lastThresholdPosition,
                    canvasBottom + TEXT_PADDING_MULTIPLIER * TEXT_SIZE.toPx(),
                    textPaint
                )
            }
            lastThresholdIndex++
            thresholdNumber -= THRESHOLD_FREQUENCY
            lastThresholdPosition -= thresholdFrequencyPx
        }

        // drawing horizontal thresholds
        lastThresholdPosition = verticalCenter + thresholdFrequencyPx
        thresholdNumber = -THRESHOLD_FREQUENCY
        while (lastThresholdPosition < canvasBottom) {
            drawLine(
                canvasStart,
                lastThresholdPosition,
                canvasEnd,
                lastThresholdPosition,
                thresholdLinePaint
            )
            drawText(
                thresholdNumber.toString(),
                canvasStart - TEXT_PADDING_MULTIPLIER * TEXT_SIZE.toPx(),
                lastThresholdPosition,
                textPaint
            )
            thresholdNumber -= THRESHOLD_FREQUENCY
            lastThresholdPosition += thresholdFrequencyPx
        }

        lastThresholdPosition = verticalCenter - thresholdFrequencyPx
        thresholdNumber = THRESHOLD_FREQUENCY
        while (lastThresholdPosition > canvasTop) {
            drawLine(
                canvasStart,
                lastThresholdPosition,
                canvasEnd,
                lastThresholdPosition,
                thresholdLinePaint
            )
            drawText(
                thresholdNumber.toString(),
                canvasStart - TEXT_PADDING_MULTIPLIER * TEXT_SIZE.toPx(),
                lastThresholdPosition,
                textPaint
            )
            thresholdNumber += THRESHOLD_FREQUENCY
            lastThresholdPosition -= thresholdFrequencyPx
        }
    }

    /**
     * Converts a dp value to pixels
     * */
    private fun Number.toPx(): Float = this.toFloat() * resources.displayMetrics.density

    /**
     * Think of [Spot] as vectors in the following extension functions on it.
     * */

    /**
     * Returns the vector with the same direction but with length 1.
     * */
    private fun Spot.normalized(): Spot {
        val length = this.length
        return Spot(this.x / length, this.y / length)
    }

    /**
     * Returns the length of this vector
     * */
    private val Spot.length: Float
        get() = sqrt(x * x + y * y)

    /**
     * Adds two vectors and returns the result
     * */
    private operator fun Spot.plus(other: Spot): Spot = Spot(this.x + other.x, this.y + other.y)

    /**
     * Subtracts two vectors and returns the result
     * */
    private operator fun Spot.minus(other: Spot): Spot = Spot(this.x - other.x, this.y - other.y)

    /**
     * Returns the vector multiplied by a scalar
     * */
    private operator fun Spot.times(f: Number) =
        Spot(this.x * f.toFloat(), this.y * f.toFloat())

    /**
     * Draws a line between [start] and [end] using the given [paint]
     * */
    private fun Canvas.drawLine(
        start: Spot,
        end: Spot,
        paint: Paint = linePaint
    ) {
        drawLine(start.x, start.y, end.x, end.y, paint)
    }

    companion object {
        private const val CIRCLE_RADIUS_DP = 3
        private const val LINE_WIDTH_DP = 1
        private const val AXES_WIDTH_DP = 2
        private const val THRESHOLD_LINE_WIDTH_DP = 0.3f
        private const val PADDING_DP = 36
        private const val THRESHOLD_FREQUENCY = 10
        private const val TEXT_SIZE = 16
        private const val TEXT_PADDING_MULTIPLIER = 1.5f
    }
}