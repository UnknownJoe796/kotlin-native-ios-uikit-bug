package sample.uikit

import kotlinx.cinterop.*
import platform.darwin.NSObject
import platform.objc.class_copyMethodList
import platform.objc.method_getName
import platform.objc.object_getClass
import platform.objc.sel_getName


fun NSObject.printMethods(){
    val type = object_getClass(this)

    println("Now there are implementations:")
    memScoped {
        val sizeOutput = this.alloc<UIntVar>()
        println("My class: ${type}")
        val mlist = class_copyMethodList(type, sizeOutput.ptr)
        if(mlist == null){
            println("Failed to pull selectors, null response.")
            return@memScoped
        }
        val size = sizeOutput.value.toInt()
        println("Selector size: $size")
        for (i in 0 until size) {
            println("Method $i: ${sel_getName(method_getName(mlist[i]))?.toKString()}")
        }
    }
    println("End selectors.")
}
