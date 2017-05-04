/*
 * Copyright 2017 The Pythagoras.kt Authors
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



package pythagoras.i

/**
 * Provides most of the implementation of [IDimension], obtaining only width and height from
 * the derived class.
 */
abstract class AbstractDimension : IDimension {
    override // from interface IDimension
    fun clone(): Dimension {
        return Dimension(this)
    }

    override fun hashCode(): Int {
        return width xor height
    }

    override fun equals(obj: Any?): Boolean {
        if (obj === this) {
            return true
        }
        if (obj is AbstractDimension) {
            val d = obj
            return d.width == width && d.height == height
        }
        return false
    }

    override fun toString(): String {
        return Dimensions.dimenToString(width, height)
    }
}