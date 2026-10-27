package fantasy.weapons.definitions

import fantasy.gradle.buildTargets

val targets = buildTargets(releaseVersion = "1.0") {
    forge("1.12.2")
    forge("1.14.4")
    fabric("1.14.4") {
        fabricApi()
        clothConfig()
        autoConfig1u()
    }
    forge("1.15.2")
    fabric("1.15.2") {
        fabricApi()
        clothConfig()
        autoConfig1u()
    }
    forge("1.16.5")
    fabric("1.16.5") {
        fabricApi()
        clothConfig()
    }
    forge("1.18.2")
    fabric("1.18.2") {
        fabricApi()
        clothConfig()
    }
    forge("1.19.2")
    fabric("1.19.2") {
        fabricApi()
        clothConfig()
    }
    forge("1.19.3")
    fabric("1.19.3") {
        fabricApi()
        clothConfig()
    }
    forge("1.19.4")
    fabric("1.19.4") {
        fabricApi()
        clothConfig()
    }
    forge("1.20.1")
    fabric("1.20.1") {
        fabricApi()
        clothConfig()
    }
    forge("1.21.1")
    neoforge("1.21.1")
    fabric("1.21.1") {
        fabricApi()
        clothConfig()
    }
    forge("1.21.10")
    neoforge("1.21.10")
    fabric("1.21.10") {
        fabricApi()
        clothConfig()
    }
    forge("1.21.11")
    neoforge("1.21.11")
    fabric("1.21.11") {
        fabricApi()
        clothConfig()
    }
    forge("26.1.2")
    neoforge("26.1.2")
    fabric("26.1.2") {
        fabricApi()
        clothConfig()
    }
    forge("26.2")
    neoforge("26.2")
    fabric("26.2") {
        fabricApi()
        clothConfig()
    }
    forge("26.3")
    neoforge("26.3")
    fabric("26.3") {
        fabricApi()
        clothConfig()
    }
}
