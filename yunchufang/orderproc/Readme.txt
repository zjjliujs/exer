First, I want to express my admiration for this exercise. Excellent exercise!

It cost my some days to complete the code. And I thint after doing this exercise, My programming skill got improved :)

My code is developed with android studio, and it should be run on an android device. Better a PAD because it has big screen.

Android SDK tool and API version:
    compileSdkVersion 29
    buildToolsVersion "30.0.0"

How to run:
    Open the project in andriod studio(4.0).
    Connect Android device(with developer option turned on) to your PC.
    After project loaded successfully, Open terminal. 
    In the terminal, run : gradlew installDebug
    App name is OrderSimulator, run it on your android device.
    You can configure the simulator on the APP. Or just edit the SimulatorConfig class and re install the APP.

Modules:
    I divide the simulator as 3 modules

    app  -- The UI
    ordersys -- Include kitchen and shelf system and courier system.
    orderserver -- Server interface and a mockup server as the exercise required.


Module dependency:
    App depends on ordersys and orderserver
    ordersys depends an orderserver
    oderserver depends none

There are 5 Junit test class:
    In module orderserver, 1 test class: 
        MockUpOrderServerTest

    In module ordersys, 4 test class: 
        CourierManagerTest, 
        KitchenTest， 
        OrderSimulatorTest,
        ShelfManagerTest

Design idea:
    There 4 threads in the simulator
    1.Order server thread
    2.kitchen thread
    3.shelf manager thread
    4.courier manager thread
