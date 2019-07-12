/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package sample.uikit

import kotlinx.cinterop.*
import platform.Foundation.NSBundle
import platform.Foundation.NSCoder
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSStringFromClass
import platform.UIKit.*
import platform.darwin.NSObject
import platform.objc.class_copyMethodList
import platform.objc.method_getName
import platform.objc.object_getClass
import platform.objc.sel_getName

fun main(args: Array<String>) {
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("konan") + args).map { it.cstr.ptr }.toCValues()

        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(AppDelegate))
        }
    }
}

class AppDelegate : UIResponder, UIApplicationDelegateProtocol {

    @OverrideInit constructor() : super()

    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta {}

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) { _window = window }
}

@ExportObjCClass
class ViewController : UIViewController {

    @ObjCOutlet
    lateinit var label: UILabel

    @ObjCOutlet
    lateinit var textField: UITextField

    @ObjCOutlet
    lateinit var button: UIButton

    var strongReference: Any? = null

    @OverrideInit constructor(coder: NSCoder) : super(coder) {
        commonInit()
    }

    @OverrideInit constructor(nibName: String?, bundle: NSBundle?): super(nibName, bundle) {
        commonInit()
    }

    fun commonInit() {
    }

    override fun viewDidLoad() {

        val inProjectInstance = ClosureSleeveInProject{
            println("In Project")
        }
        inProjectInstance.printMethods()

//        val inLibraryInstance = ClosureSleeveInLibrary{
//            println("In Library")
//        }
//        inLibraryInstance.printMethods()


        //Works
        strongReference = inProjectInstance

        //Does not work
//        strongReference = inLibraryInstance

        button.addTarget(
            strongReference,
            NSSelectorFromString("runContainedClosure"),
            UIControlEventTouchUpInside
        )

    }

    @ObjCAction
    fun buttonPressed() {
        label.text = "Konan says: 'Hello, ${textField.text}!'"
    }
}
