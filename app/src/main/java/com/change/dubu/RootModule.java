package com.change.dubu;

import dagger.Module;

/**
 * Add all the other modules to this one.
 */
@Module
(
    includes = {
            AndroidModule.class,
            BootstrapModule.class
    }
)
public class RootModule {
}
