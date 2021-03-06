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

/**
 * Represents a line segment.
 */
data class Line(
        /** The x-coordinate of the start of this line segment.  */
        override var x1: Float = 0f,
        /** The y-coordinate of the start of this line segment.  */
        override var y1: Float = 0f,
        /** The x-coordinate of the end of this line segment.  */
        override var x2: Float = 0f,
        /** The y-coordinate of the end of this line segment.  */
        override var y2: Float = 0f
) : ILine {

    /**
     * Creates a line from p1 to p2.
     */
    constructor(p1: XY, p2: XY) : this(p1.x, p1.y, p2.x, p2.y)

    /**
     * Sets the start and end point of this line to the specified values.
     */
    fun setLine(x1: Float, y1: Float, x2: Float, y2: Float) {
        this.x1 = x1
        this.y1 = y1
        this.x2 = x2
        this.y2 = y2
    }

    /**
     * Sets the start and end of this line to the specified points.
     */
    fun setLine(p1: XY, p2: XY) {
        setLine(p1.x, p1.y, p2.x, p2.y)
    }
}
