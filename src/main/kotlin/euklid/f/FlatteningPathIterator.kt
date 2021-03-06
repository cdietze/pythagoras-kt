/*
 * Copyright 2017 The Euklid Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package euklid.f

import euklid.platform.arrayCopy
import kotlin.math.min

/**
 * A path iterator that flattens curves.
 */
internal class FlatteningPathIterator constructor(
        /** The source PathIterator  */
        private val p: PathIterator,
        /** The flatness of new path  */
        private val flatness: Float,
        /** The curve subdivision limit  */
        private val bufLimit: Int = FlatteningPathIterator.BUFFER_LIMIT) : PathIterator {

    /** The type of current segment to be flat  */
    private var bufType: Int = 0

    /** The current points buffer size  */
    private var bufSize: Int = 0

    /** The inner cursor position in points buffer  */
    private var bufIndex: Int = 0

    /** The current subdivision count  */
    private var bufSubdiv: Int = 0

    /** The points buffer  */
    private var buf: FloatArray

    /** The indicator of empty points buffer  */
    private var bufEmpty = true

    /** The square of flatness  */
    private val flatness2: Float

    /** The x coordinate of previous path segment  */
    private var px: Float = 0f

    /** The y coordinate of previous path segment  */
    private var py: Float = 0f

    /** The tamporary buffer for getting points from PathIterator  */
    private val coords = FloatArray(6)

    init {
        if (flatness < 0) {
            throw IllegalArgumentException("Flatness is less then zero")
        }
        if (bufLimit < 0) {
            throw IllegalArgumentException("Limit is less then zero")
        }
        this.flatness2 = flatness * flatness
        this.bufSize = min(bufLimit, BUFFER_SIZE)
        this.buf = FloatArray(bufSize)
        this.bufIndex = bufSize
    }

    fun flatness(): Float {
        return flatness
    }

    fun recursionLimit(): Int {
        return bufLimit
    }

    override fun windingRule(): Int {
        return p.windingRule()
    }

    override val isDone: Boolean
        get() = bufEmpty && p.isDone

    override fun next() {
        if (bufEmpty) {
            p.next()
        }
    }

    override fun currentSegment(coords: FloatArray): Int {
        if (isDone) {
            throw NoSuchElementException("Iterator out of bounds")
        }
        evaluate()
        var type = bufType
        if (type != PathIterator.SEG_CLOSE) {
            coords[0] = px
            coords[1] = py
            if (type != PathIterator.SEG_MOVETO) {
                type = PathIterator.SEG_LINETO
            }
        }
        return type
    }

    /** Calculates flat path points for the current segment of the source shape. Line segment is
     * flat by itself. Flatness of quad and cubic curves are evaluated by the flatnessSq()
     * method. Curves are subdivided until current flatness is bigger than user defined value and
     * subdivision limit isn't exhausted. Single source segments are translated to a series of
     * buffer points. The smaller the flatness the bigger the series. Every currentSegment() call
     * extracts one point from the buffer. When a series is completed, evaluate() takes the next
     * source shape segment.  */
    protected fun evaluate() {
        if (bufEmpty) {
            bufType = p.currentSegment(coords)
        }

        when (bufType) {
            PathIterator.SEG_MOVETO, PathIterator.SEG_LINETO -> {
                px = coords[0]
                py = coords[1]
            }

            PathIterator.SEG_QUADTO -> {
                if (bufEmpty) {
                    bufIndex -= 6
                    buf[bufIndex + 0] = px
                    buf[bufIndex + 1] = py
                    arrayCopy(coords, 0, buf, bufIndex + 2, 4)
                    bufSubdiv = 0
                }

                while (bufSubdiv < bufLimit) {
                    if (QuadCurves.flatnessSq(buf, bufIndex) < flatness2) {
                        break
                    }

                    // Realloc buffer
                    if (bufIndex <= 4) {
                        val tmp = FloatArray(bufSize + BUFFER_CAPACITY)
                        arrayCopy(buf, bufIndex, tmp, bufIndex + BUFFER_CAPACITY, bufSize - bufIndex)
                        buf = tmp
                        bufSize += BUFFER_CAPACITY
                        bufIndex += BUFFER_CAPACITY
                    }

                    QuadCurves.subdivide(buf, bufIndex, buf, bufIndex - 4, buf, bufIndex)

                    bufIndex -= 4
                    bufSubdiv++
                }

                bufIndex += 4
                px = buf[bufIndex]
                py = buf[bufIndex + 1]

                bufEmpty = bufIndex == bufSize - 2
                if (bufEmpty) {
                    bufIndex = bufSize
                    bufType = PathIterator.SEG_LINETO
                }
            }

            PathIterator.SEG_CUBICTO -> {
                if (bufEmpty) {
                    bufIndex -= 8
                    buf[bufIndex + 0] = px
                    buf[bufIndex + 1] = py
                    arrayCopy(coords, 0, buf, bufIndex + 2, 6)
                    bufSubdiv = 0
                }

                while (bufSubdiv < bufLimit) {
                    if (CubicCurves.flatnessSq(buf, bufIndex) < flatness2) {
                        break
                    }

                    // Realloc buffer
                    if (bufIndex <= 6) {
                        val tmp = FloatArray(bufSize + BUFFER_CAPACITY)
                        arrayCopy(buf, bufIndex, tmp, bufIndex + BUFFER_CAPACITY, bufSize - bufIndex)
                        buf = tmp
                        bufSize += BUFFER_CAPACITY
                        bufIndex += BUFFER_CAPACITY
                    }

                    CubicCurves.subdivide(buf, bufIndex, buf, bufIndex - 6, buf, bufIndex)

                    bufIndex -= 6
                    bufSubdiv++
                }

                bufIndex += 6
                px = buf[bufIndex]
                py = buf[bufIndex + 1]

                bufEmpty = bufIndex == bufSize - 2
                if (bufEmpty) {
                    bufIndex = bufSize
                    bufType = PathIterator.SEG_LINETO
                }
            }
        }
    }

    companion object {

        /** The default points buffer size  */
        private val BUFFER_SIZE = 16

        /** The default curve subdivision limit  */
        private val BUFFER_LIMIT = 16

        /** The points buffer capacity  */
        private val BUFFER_CAPACITY = 16
    }
}
