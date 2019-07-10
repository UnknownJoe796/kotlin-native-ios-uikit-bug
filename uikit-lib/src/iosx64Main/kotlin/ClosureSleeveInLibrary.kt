/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package sample.uikit

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.UIKit.*
import platform.darwin.NSObject
import platform.objc.*

class ClosureSleeveInLibrary(val closure: () -> Unit) : NSObject() {

    @ObjCAction
    fun runContainedClosure() {
        closure()
    }
}
