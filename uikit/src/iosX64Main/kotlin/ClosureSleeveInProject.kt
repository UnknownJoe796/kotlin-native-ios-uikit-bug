package sample.uikit

import kotlinx.cinterop.ObjCAction
import platform.darwin.NSObject

class ClosureSleeveInProject(val closure: () -> Unit) : NSObject() {

    @ObjCAction
    fun runContainedClosure() {
        closure()
    }
}
